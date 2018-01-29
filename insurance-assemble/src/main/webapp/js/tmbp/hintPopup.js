define(function(require, exports, module) {

    require("Y-msg");

    function hintPopup(text, url) {

        $('.util-loading').remove();

        Y.alert('', text, function(opn) {

            if (url && typeof url == 'function') {

                url();

            } else if (url && typeof url == 'string') {

                window.location.href = url;

            }

        });


        // $('body').Y('Window', {
        //     content: ['<div class="hintPopup">',
        //         '   <p>',
        //         text,
        //         '   </p>',
        //         '</div>'
        //     ].join(""),
        //     modal: true,
        //     key: 'hintwnd',
        //     simple: true
        // });
        // var hintwnd = Y.getCmp('hintwnd');
        // window.setTimeout(function() {
        //     hintwnd.close()
        //         //console.log(typeof url)
        //     if (url && typeof url == 'function') {
        //         url();
        //     } else if (url && typeof url == 'string') {
        //         window.location.href = url
        //     }
        // }, 1500);
    }
    module.exports = hintPopup
})