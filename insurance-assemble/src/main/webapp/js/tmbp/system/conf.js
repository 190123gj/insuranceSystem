/**
 * Created by eryue
 * Create in 2016-10-28 11:38
 * Description:
 */

'use strict';
define(function(require, exports, module) {
    var util = require('util');

    require('Y-msg');
    require('validate');
    //上传
    require('tmbp/upAttachModifyOnlyOne');

    var $body = $('body'),
        $form = $('#form');

    $body.on('click','.versionChange',function () {
        var _this = $(this);
        var _modTab = _this.attr('mod');
        _this.addClass('active').siblings().removeClass('active')
        $('.version_mod').addClass('fn-hide');
        $('.' + _modTab).removeClass('fn-hide');
    })
    var validateRules = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element) {
            // element.parent().append(error);
            if (element.hasClass('fnErrorAfter')) {
                element.after(error);
            } else if(element.hasClass('parentsMitem')){
                element.parent('.m-item ').append(error);
            }else{
                element.parent().append(error);
            }
        },
        submitHandler: function(form) {},
        rules: {
            assetType: {
                required: true
            },
            ownershipName: {
                required: true
            }
        },
        messages: {
            assetType: {
                required: '必填'
            },
            ownershipName: {
                required: '必填'
            }
        }
    };
    $form.validate(validateRules);

    function dynamAddRules(ele) {
        if (!ele) ele = $('body');
        var $nameList = ele.find('[name].fn-validate');
        // dynamicRemoveRules($form);
        $nameList.each(function(i, e) {
            var _this = $(this);
            if (_this.hasClass('leesOneItemInput')) {
                _this.rules('add', {
                    required: true,
                    lessOne: true,
                    messages: {
                        required: '附件和文本必填一项！',
                        lessOne: '附件和文本必填一项！'
                    }
                });
            }else {
                _this.rules('add', {
                    required: true,
                    messages: {
                        required: '必填'
                    }
                });
            }

        })
    };

    // function dynamicRemoveRules(ele) {
    //     if (!ele) ele = $('body');
    //     var $nameList = ele.find('[name].fn-validate');
    //     $nameList.each(function(i, e) {
    //         $(this).rules("remove");
    //     })
    // };
    dynamAddRules();
    $('#submitBtn').on('click', function() { //提交前验证
        if (!$form.valid()) return;
        var _isPass = true,
            _isPassEq,
            $thisError;

        var $validateList = $('.fn-validate');
        $validateList.each(function(index, el) {
            var _this = $(this);
            var thisVal = _this.val();
            if(_this.hasClass('radio')) thisVal = _this.prop('checked') || _this.parents('.m-item').find('.fn-validate').not(_this).prop('checked') ? 'checked' : '';
            console.log('编号是：' + index + '，值为：' + thisVal)
            if (!thisVal.replace(/\s/g, '')) {
                _isPass = false;
                _isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
                console.log('编号是：' + index + '，错误编号为：' + _isPassEq)
            }
        });

        if (!_isPass) {
            $thisError = $validateList.eq(_isPassEq);

            Y.alert('提醒', '请把表单填写完整', function() {
                $thisError.val('').focus();
            });
            return;
        }
        // return;
        submitForm();

    });


    function submitForm() {

        util.ajax({
            url: '/systemMg/saveAppConf.json',
            type: 'post',
            data: $form.serialize(),
            success: function(res) {
                if (res.success) {
                    Y.alert('提醒', res.message, function() {
                        window.location.href = '/systemMg/toAppConf.htm';
                    });
                } else {
                    Y.alert('提醒', res.message);
                }
            }

        });
    };
})