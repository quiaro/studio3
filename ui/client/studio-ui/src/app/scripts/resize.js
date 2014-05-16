'use strict';

angular.module('crafter.studio-ui.Resize', [])

        .directive('sdoYDivider', ['$document', function($document) {

        return {
            restrict: 'E',
            link: function($scope, $element, $attrs) {

                var $topEl = $($attrs.top),
                    $bottomEl = $($attrs.bottom),
                    bottomOffset = +($attrs.bottomOffset) || 0,
                    bottomMin = +($attrs.bottomMin) || 0,
                    dividerHeight = $element.height(),
                    overlayClass = 'resize-overlay',
                    overlay = '<div class="' + overlayClass + '"' +
                              ' style="position: absolute; top: 0; bottom: 0; left: 0; right: 0; z-index: 1000"></div>';

                $element.on('mousedown', function(event) {
                    event.preventDefault();

                    // Put an overlay over the top and bottom elements so that any mouse over
                    // events in them do not interfere with the resizing
                    $topEl.append(overlay);
                    $bottomEl.append(overlay);

                    $document.on('mousemove', mousemove);
                    $document.on('mouseup', mouseup);
                });

                function mousemove(event) {

                    var windowHeight = window.innerHeight - bottomOffset,
                        y = windowHeight - event.pageY,
                        bottomMax = +($attrs.bottomMax) || windowHeight,
                        bottomLowerLimit = Math.min(windowHeight, bottomMin),
                        bottomUpperLimit = Math.min(windowHeight, bottomMax);

                    y = (y > bottomLowerLimit) ? y : bottomLowerLimit;
                    y = (y < bottomUpperLimit) ? y : bottomUpperLimit;

                    $element.css({
                        bottom: y + 'px'
                    });

                    $topEl.css({
                        bottom: y + dividerHeight + 'px'
                    });

                    $bottomEl.css({
                        height: y + 'px'
                    });
                }

                function mouseup() {
                    // Remove the overlays
                    $topEl.children().remove('.' + overlayClass);
                    $bottomEl.children().remove('.' + overlayClass);

                    $document.unbind('mousemove', mousemove);
                    $document.unbind('mouseup', mouseup);
                }
            }
        };
    }]);
