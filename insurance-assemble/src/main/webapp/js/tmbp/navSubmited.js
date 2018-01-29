define(function(require, exports, module) {

    //弹窗提示
    var hintPopup = require('zyw/hintPopup');

    function navSubmited(_submited) {

        var _arr = _submited.split('');

        for (var i = 0; i < _arr.length; i++) {

            // if (_arr[i] == '0' || _arr[i] == '2') {

            //     if ($('.apply-step .item').eq(i).find('em.remind').length) {

            //         $('.apply-step .item').eq(i).find('em.remind').html('<b>请完整该页必填项</b>');

            //     } else {

            //         $('.apply-step .item').eq(i).append('<em class="remind"><b>请完整该页必填项</b></em>');

            //     }

            // } else {

            //     $('.apply-step .item').eq(i).find('em.remind').remove();

            // }

            if (_arr[i] == '0') {

                if ($('.apply-step .item[index="' + i + '"]').find('em.remind').length) {

                    $('.apply-step .item[index="' + i + '"]').find('em.remind').html('<b>请完整该页必填项</b>');

                } else {

                    $('.apply-step .item[index="' + i + '"]').append('<em class="remind"><b>请完整该页必填项</b></em>');

                }

            } else {

                $('.apply-step .item[index="' + i + '"]').find('em.remind').remove();

            }

        }

    }

    function navSubmited3(_submited) {

        var _arr = _submited.split('');

        for (var i = 0; i < _arr.length; i++) {

            // if (_arr[i] == '0' || _arr[i] == '2') {

            //     if ($('.apply-step .item').eq(i).find('em.remind').length) {

            //         $('.apply-step .item').eq(i).find('em.remind').html('<b>请完整该页必填项</b>');

            //     } else {

            //         $('.apply-step .item').eq(i).append('<em class="remind"><b>请完整该页必填项</b></em>');

            //     }

            // } else {

            //     $('.apply-step .item').eq(i).find('em.remind').remove();

            // }

            if (_arr[i] == '3') {

                if ($('.apply-step .item[index="' + i + '"]').find('em.remind').length) {

                    $('.apply-step .item[index="' + i + '"]').find('em.remind').html('<b>请完整该页必填项</b>');

                } else {

                    $('.apply-step .item[index="' + i + '"]').append('<em class="remind"><b>请完整该页必填项</b></em>');

                }

            } else {

                $('.apply-step .item[index="' + i + '"]').find('em.remind').remove();

            }

        }

    }


    function navSubmitedNew(_submited) {

        var _arr = _submited.split('');

        for (var i = 0; i < _arr.length; i++) {

            if (_arr[i] == '0' || _arr[i] == '2') {

                if ($('.apply-step .item').eq(i).find('em.remind').length) {

                    $('.apply-step .item').eq(i).find('em.remind').html('<b>请完整该页必填项</b>');

                } else {

                    $('.apply-step .item').eq(i).append('<em class="remind"><b>请完整该页必填项</b></em>');

                }

            } else {

                $('.apply-step .item').eq(i).find('em.remind').remove();

            }

        }

    }

    module.exports = {

        navSubmited: navSubmited,
        navSubmitedNew: navSubmitedNew,
        navSubmited3: navSubmited3

    }

})