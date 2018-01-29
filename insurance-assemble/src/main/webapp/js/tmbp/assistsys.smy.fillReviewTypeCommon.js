define(function(require, exports, module) {

    //异步提交
    require('form')();
    //表单验证
    require('validate');
    require('validate.extend');

    var submitHandlerContentVal;

    module.exports = {

        ValidataInit: function(ValidataInit) {

            ValidataInit['form'].validate($.extend(true, ValidataInit['RulesInit'] || {}, {

                errorClass: ValidataInit['errorAll'] ? (ValidataInit['errorAll']['errorClass'] || 'error-tip') : 'error-tip',

                errorElement: ValidataInit['errorAll'] ? (ValidataInit['errorAll']['errorElement'] || 'em') : 'em',

                errorPlacement: ValidataInit['errorAll'] ? (ValidataInit['errorAll']['errorPlacement'] || function(error, element) {

                    element.after(error);

                }) : function(error, element) {

                    element.after(error);

                },
                onkeyup: false,
                ignore: '.cancel',
                submitHandler: function(form) {

                    if (ValidataInit['successBeforeFun']) {

                        var down = ValidataInit['successBeforeFun'](submitHandlerContentVal);

                        if (!down) return false;

                    }

                    $(form).ajaxSubmit({
                        type: 'post',
                        dataType: 'json',
                        data: submitHandlerContentVal ? (submitHandlerContentVal['validateData'] || {}) : {},
                        success: ValidataInit['successFun'],
                        error: function(x) {

                            alert('return error,on success');

                        }
                    });

                }
            }));
        },

        submitHandlerContent: function(Url) {

            submitHandlerContentVal = require(Url).submitHandlerContent;
            //console.log(submitHandlerContentVal)

        }

    }


})