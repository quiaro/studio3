(function (window) {

    var Crafter = {
        Util: {
            between: function ( value, upper, lower ) {
                return (value <= upper && value >= lower);
            }
        }
    }

    window.Crafter = Crafter;

}) (window, jQuery);


/** ***** ***** ***** ***** ***** ***** **/

(function ($, window) {

        // lightbox element id
    var lbid = 'lightbox_' + new Date().getTime(),
        // player container id
        pcid = lbid + '_playerContainer',
        // player placeholder
        playerPlaceholder = '<div class="player" id="' + pcid + '"></div>';

    var tmpl = [
        '<div class="modal lightbox hide fade" aria-hidden="true" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">',
            '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>',
            playerPlaceholder,
        '</div>'
    ].join('');

    function Lightbox () {
        this.init();
    }

    Lightbox.prototype = {};

    $.extend(Lightbox.prototype, {
        init: function () {
            var me = this,
                $lightbox = this.lightbox();
            $lightbox.modal({ show: false });
            $lightbox.on('hidden', function (e) {
                me.hidden(e);
            });
        },
        lightbox: function () {
            var $e = $('#' + lbid);
            if (!$e.size()) {
                $e = $(tmpl);
                $e.attr('id', lbid);
                $e.appendTo('body');
            }
            return $e;
        },
        player: function (cfg) {
            (!cfg) && (cfg = {  });

            var me = this,
                $e = this.$element,
                proportion = parseFloat($e.attr('data-video-proportion')) || 1.7777777778;

            var player = new YT.Player(pcid, $.extend({
                width: 560,
                height: (cfg.width / proportion),
                videoId: $e.attr('data-video-id'),
                events: {
                    'onReady': function(e){
                        player.setPlaybackQuality($e.attr('data-video-quality'));
                        e.target.playVideo();
                    }
                }
            }, cfg));

            return player;
        },
        show: function () {

            var url,
                $element = this.$element,
                $lightbox = this.lightbox();

            var playerWidth = $(window).width() * 0.70;

            $lightbox.css({
                width: playerWidth + 'px',
                marginLeft: (-1 * (playerWidth/2)) + 'px'
            });

            this.player({ width: playerWidth });

            $lightbox.modal('show');

        },
        hide: function () {
            this.lightbox().modal('hide');
        },

        hidden: function (e) {
            this.lightbox().find('iframe:first').replaceWith(playerPlaceholder);
        },
        playerReady: function (player) {
            player.playVideo();
        }
    });

    $.fn.lightbox = function () {
        return this.each(function () {

            var $e = $(this),
                instance = new Lightbox();

            $e.data('lighbox', instance);

            function clickfn ( e ) {
                e.preventDefault();
                e.stopPropagation();
                instance.show();
            }

            if (this.tagName.toLowerCase() === 'iframe') {

                var pos = $e.offset(),
                    $overlay = $('<div class="lightbox-iframe-overlay"/>');

                $overlay.css({
                    top: pos.top,
                    left: pos.left,
                    width: $e.width(),
                    height: $e.height()
                }).appendTo('body');

            }

            if ($overlay) {
                instance.$overlay = $overlay;
                $overlay.click(clickfn);
            } else {
                $e.click(clickfn);
            }

            instance.$element = $e;

        });
    }

}) (jQuery, window);

$(function () {

    var $carousel = $('.carousel');

    $carousel.find('.carousel-indicators li')
        .each(function (i, e) {
            $(this).attr('slide-to', i);
        })
        .click(function () {
            var i = $(this).attr('slide-to');
            $carousel.carousel(parseInt(i));
        });

    if (typeof $carousel.hammer !== 'undefined') {

    $carousel.hammer().on('swipeleft', function () {
        $carousel.carousel('next');
    });

    $carousel.hammer().on('swiperight', function () {
        $carousel.carousel('prev');
    });

    }

    $carousel.carousel();

    $('.lightboxed').lightbox();

    /**
     *
     * /

    $(window).resize(function () {

    });

    */

});

function onYouTubeIframeAPIReady () {

}
