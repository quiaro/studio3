/* global CKEDITOR, require */
'use strict';

/* --- Preview Editor config --- */
require.config({
	baseUrl: '/',
	paths: {
		'ckeditor' : 'lib/ckeditor/ckeditor',
		'config' : 'scripts/editor/modules/config',
		'event-bridge' : 'scripts/editor/modules/eventBridge',
		'domReady' : 'lib/requirejs-domready/js/domReady',
		'jquery' : 'lib/jquery/js/jquery',
		'jquery-private' : 'scripts/editor/modules/jquery-private',
		'pubsub' : 'lib/pubsub-js/pubsub',
        'css' : 'lib/require-css/js/css',
        'editor-css' : 'styles/editor'
	},
	shim: {
		'ckeditor' : {
			exports: 'ckeditor'
		}
	},
	map: {
    '*': { 'jquery': 'jquery-private' },

    // 'jquery-private' wants the real jQuery module.
    // If this line was not here, there would
    // be an unresolvable cyclic dependency.
    'jquery-private': { 'jquery': 'jquery' }
  }
});

require(['config',
	'domReady',
	'jquery',
	'ckeditor',
	'pubsub',
	'event-bridge',
	'lib/require-css/js/css!editor-css'],
		function (config, domReady, $, ckeditor, PubSub) {

	// Disable inline CKEditors
    CKEDITOR.disableAutoInline = true;

	// Load CKEditor custom toolbar configs
    // The "instanceCreated" event is fired for every editor instance created.
	CKEDITOR.on( 'instanceCreated', function( event ) {
		var editor = event.editor,
			element = editor.element,
			type = element.getAttribute(config.cmp.typeAttr);

		// Change the first letter of the 'type' string to uppercase
		// e.g. "list" => "List"
		type = type && type.replace(/^[a-z]/, type.charAt(0).toUpperCase());

		// Customize editors depending on their type
		editor.on( 'configLoaded', function() {
			editor.config.toolbar = type;
		});

	});

    domReady(function () {
		var activeElement, fm, cc;

		function changeElementState(el, state, inlineStyles) {

			function setInlineStyles(element) {
				if (element.selector) {
					$(element.selector, el).css(element.styles);
				} else {
					$(el).css(element.styles);
				}
			}

			if (typeof el === 'object' && el !== null) {
				for (var st in inlineStyles) {
					if (st === state) {
						$(el).attr(config.cmp.statusAttr, state);

						inlineStyles[state].elements.forEach( setInlineStyles );
						return;
					}
				}
				throw new Error('Invalid element status: ' + state);
			}
		}

		function removeElementStyles(el, stylesObj) {
			var styleStr, reStr, re;

			if (typeof el === 'object' && el !== null) {
				styleStr = el.getAttribute('style');
				for (var style in stylesObj) {
					if (stylesObj.hasOwnProperty(style)) {
						reStr = style + ':.+?;';
						re = new RegExp(reStr, 'g');
						styleStr = styleStr.replace(re, '');
					}
				}
				$.trim(styleStr);
				el.setAttribute('style', styleStr);
			}
		}

		/*
         * @param: Element to reset
         * @param: UI configuration object
         * Reset the element's styles (i.e. remove any studio styles from the element)
         */
		function resetElementState(el, inlineStyles) {
			var state;

			function rmElementStyles (element) {
				var styles = element.styles;

				if (element.selector) {
					removeElementStyles($(element.selector, el)[0], styles);
				} else {
					removeElementStyles(el, styles);
				}
			}

			if (typeof el === 'object' && el !== null) {
				state = $(el).attr(config.cmp.statusAttr);

				if (state) {
					Object.keys(inlineStyles).forEach( function(key) {
						if (key === state) {
							$(el).removeAttr(config.cmp.statusAttr);

							// Make component fields un-editable again
							$('[' + config.cmp.parentAttr + '="' + el.getAttribute(config.cmp.idAttr) +'"]')
								.attr('contenteditable', false);

							inlineStyles[state].elements.forEach( rmElementStyles );
							return;
						}
					});

				}
			}
		}

        /*
         * Create RTE's for all editable children fields of parentEl
         */
		function setupEditors (parentEl) {
			var parentId = $(parentEl).attr(config.cmp.idAttr),
					editableFields = $('[' + config.cmp.parentAttr + '="' + parentId +'"]'),
					el;

			if (editableFields.length) {
				el = CKEDITOR.dom.element.get(editableFields.get(0));

				if (!el.getEditor()) {
					// No editors detected => create them
					editableFields
						.attr('contenteditable', true)
						.each( function(i, el) {
							CKEDITOR.inline( el );
						});
				}
			}
		}

		/*
		 * Menu with controls for components
		 * @param buttonsArr : An array of button objects
         */
		function ComponentControls (buttonsArr) {

            var $controlEl,
                $btnContainer,
                buttons = '',
                $body = $('body');

            function createButton (btnObj) {
                var btnStr, $btn;

                btnStr =  '<li>';
                btnStr += '  <a href="#" class="s2do-btn ' + btnObj.class + '" title="' + btnObj.text + '">';
                btnStr += '    <i class="' + btnObj.iconClass + '" ></i>'
                btnStr += '  </a>'
                btnStr += '</li>';

                $btn = $(btnStr);

                if (btnObj.events) {
                    btnObj.events.forEach ( function(evtObj) {

                        $btn.children('.s2do-btn').on(evtObj.on, function (evt) {
                            var self = this;

                            evt.preventDefault();
                            PubSub.publish(evtObj.publish, {
                                // TODO: function that extracts the value of evtObj.data (e.g. if it's "component",
                                // the component should be returned; if it's "parent", the component's parent
                                // should be returned)
                                data: evtObj.data
                            });
                        })
                    })
                }
                return $btn;
            }

            function add (control) {
                // TO-DO: how do we plug in a new control?
                // if (control instanceof ComponentControl) {
                // }
            }

            function hide () {
                $controlEl
                    .addClass('hidden')
                    .appendTo($body)
                    .removeAttr(config.cmpControls.bindAttr);
            }

            /*
             * @param component : jquery object of component over which we want to show the controls
             */
            function show ($component) {
                $controlEl
                    .prependTo($component)
                    .removeClass('hidden')
                    .attr(config.cmpControls.bindAttr, $component.attr(config.cmp.idAttr));
            }

			// Add the controls container
			$('<div id="' + config.cmpControls.id + '" class="hidden">' +
					'<ul class="btn-container"></ul>' +
				'</div>').appendTo('body');

			$controlEl = $('#' + config.cmpControls.id);

            if ($controlEl.length) {
                $btnContainer = $controlEl.children('.btn-container');

                // Append each buttons to the button container
                buttonsArr.forEach( function (btnObj) {
                    var $btn = createButton(btnObj);
                    $btnContainer.append($btn);
                })

            } else {
                throw new Error('Component controls element with id: ' + config.cmpControls.id + ' doesn\'t exist');
            }

			return {
				add: add,
				hide: hide,
				show: show
			};
		}
		/* --- EO: Component Controls --- */


		/*
         * Determines which component should be given focus or be blurred
         * @param delayFocus : Number of milliseconds before the focus is finally carried out
         */
		function FocusManager (delayFocus, componentControls) {
			var componentQueue = {},
					focusTimer = null,
					df = delayFocus,
					cmpControls = componentControls;

			function findComponentToFocus() {
				var numParents = 0,
						cmpToFocus = null,
						component,
						numParentsComponent;

				// Find the component to focus on based on the greatest number of parents
				// (the more the parents, the deeper the component is in the tree)
				for (var componentId in componentQueue) {
					if (componentQueue.hasOwnProperty(componentId)) {
						component = componentQueue[componentId];
						numParentsComponent = component.parents;

						if (numParentsComponent > numParents) {
							numParents = numParentsComponent;
							cmpToFocus = component.element;
						}
					}
				}
				return cmpToFocus;
			}

			function focusComponent () {
				var cmpFocused = null,
						$cmpFocused = null,
						cmpToFocus = null;

				cmpToFocus = findComponentToFocus();

				if (cmpToFocus) {
					$cmpFocused = $('.' + config.cmp.focusClass);
					cmpFocused = $cmpFocused.get(0);

					if (cmpFocused) {

						// If the component focused and the component to focus are the same, then there's nothing to do
						if (cmpFocused !== cmpToFocus) {
							cmpControls.hide();
							$cmpFocused.removeClass(config.cmp.focusClass);
							$(cmpToFocus).addClass(config.cmp.focusClass);
							cmpControls.show($(cmpToFocus));
						}
					} else {
						$(cmpToFocus).addClass(config.cmp.focusClass);
						cmpControls.show($(cmpToFocus));
					}
				}

				// Reset timer
				focusTimer = null;
			}

			function processFocus (evtObj) {
				var componentId = evtObj.currentTarget.getAttribute(config.cmp.idAttr);

				if (focusTimer) {
					// Cancel the current timer
					clearTimeout(focusTimer);
				}

				// Insert the component's info into the component queue (if it hasn't already been done)
				componentQueue[componentId] = componentQueue[componentId] || {
					parents: $(evtObj.currentTarget).parents().length,
					element: evtObj.currentTarget
				};
				focusTimer = setTimeout(focusComponent, df);
			}

			function processBlur (evtObj) {
				var component = evtObj.currentTarget,
						$component = $(component),
						componentId = component.getAttribute(config.cmp.idAttr),
						fakeEvtObj = {},
						cmpToFocus = null;

				// Remove the component from the component queue
				delete componentQueue[componentId];
				if ($component.hasClass(config.cmp.focusClass)) {
					// The element might have already been blurred so check if it still has the "focus class"
					cmpControls.hide();
					$(component).removeClass(config.cmp.focusClass);

					cmpToFocus = findComponentToFocus();
					if (cmpToFocus) {
						// Create a fake event object to pass to the processFocus function
						fakeEvtObj.currentTarget = cmpToFocus;
						processFocus(fakeEvtObj);
					}
				}
			}

			return {
				processFocus : processFocus,
				processBlur : processBlur
			};
		}
		/* --- EO: Focus Manager --- */


		/* --- Initialize the app --- */
		cc = ComponentControls(config.cmpControls.buttons);
		fm = FocusManager(500, cc);

		$('body').on('click', function () {
			if (activeElement) {
				resetElementState(activeElement, config.cmpInlineStyles);
				changeElementState(activeElement, 'idle', config.cmpInlineStyles);

				PubSub.publish('editor/element/select', {
					id: 'none'
				});

				activeElement = null;
			}
		});

		$('[data-studio-component]').bind('click', function (evt) {
			evt.stopPropagation();
			if (this !== activeElement) {
				if (activeElement) {
					resetElementState(activeElement, config.cmpInlineStyles);
					changeElementState(activeElement, 'idle', config.cmpInlineStyles);
				}
				activeElement = this;

				PubSub.publish('editor/element/select', {
					id: this.getAttribute(config.cmp.idAttr)
				});

				setupEditors(activeElement);
			}
		});

		$('[data-studio-component]').hover(fm.processFocus, fm.processBlur);


		/* Subscriptions */
		PubSub.subscribe('app/element/update', function(msg, data) {
			var el;

			if (data.id && data.state) {
				el = $('[' + config.cmp.idAttr + '="' + data.id + '"]')[0];
				if (el) {
					changeElementState(el, data.state, config.cmpInlineStyles);
				}
			}
		});

	});
});
