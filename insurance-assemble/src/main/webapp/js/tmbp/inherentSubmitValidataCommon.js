define(function(require, exports, module) {

    //弹窗
    require('Y-window');
    //弹窗提示
    var hintPopup = require('zyw/hintPopup');
    //必填集合

    function inherentSubmitValidataCommon(specialList) {

        var contentBtnBasis = [{
                clickObj: '#submit',
                eventFun: function(jsonObj) {

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'Y',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }],
            broadsideBtnBasis = [{
                name: '暂存',
                alias: 'temporarStorage',
                events: function(jsonObj) {

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], false).allAddValidata(jsonObj.objList['rulesAll']); //否必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'N',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }, {
                name: '提交',
                alias: 'fulfilSubmit',
                events: function(jsonObj) {

                    $.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

                    jsonObj.portInitVal['submitHandlerContent'] = {

                        validateData: {

                            submitStatus: 'Y',
                            checkStatus: jsonObj.setInitVal['checkStatus']

                        },
                        fm5: jsonObj.setInitVal['fm5']

                    }; //向validate文件里的submitHandler暴露数据接口

                    jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

                    jsonObj.objList['form'].submit(); //提交

                }
            }],
            successBeforeFunBasis = function(res) { //请求前操作

                var util = require('util'),
                    loading = new util.loading();

                loading.open();

                $.fn.orderName();

                if (res.validateData['submitStatus'] == 'Y') { //提交

                    return true;

                } else {

                    if (res['fm5']) { //有改变

                        return true;

                    } else { //无改变

                        $('.m-modal-box').remove();
                        hintPopup('数据未改变，保持原有储存');
                        return false;

                    }

                }

            },
            successFunBasis = function(res) { //响应成功操作

                if (res['success']) {

                    if (res['submitStatus'] == 'Y') { //提交

                        $.ajax({

                            url: '/projectMg/form/submit.htm',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                formId: res['form']['formId']
                            },
                            success: function(data) {

                                hintPopup(data.message, function() {

                                    if (data.success) {

                                        window.location.href = '/projectMg/chargeNotification/list.htm';

                                    }

                                });

                                $('.m-modal-box').remove();

                            }

                        });

                    } else { //暂存

                        hintPopup(res['message'], '/projectMg/chargeNotification/editChargeNotification.htm?formId=' + res['formId']);

                    }

                } else {

                    $('.m-modal-box').remove();
                    hintPopup(res['message']);

                }

            },
            errorClassBasis = 'error-tip',
            errorElementBasis = 'em',
            errorPlacementBasis = function(error, element) {

                element.after(error)

            },
            callbackBasis = function(objList) {

                //验证方法集初始化
                $('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
                    .initAllOrderValidata()
                    .assignGroupAddValidataUp();

            },
            contentBtnArr = specialList.allEvent ? (specialList.allEvent['contentBtn'] ? ((specialList.allEvent['replaceContentBtn'] == true) ? specialList.allEvent['contentBtn'] : specialList.allEvent['contentBtn'].concat(contentBtnBasis)) : contentBtnBasis) : contentBtnBasis,
            broadsideBtnArr = specialList.allEvent ? (specialList.allEvent['broadsideBtn'] ? ((specialList.allEvent['replaceBroadsideBtn'] == true) ? specialList.allEvent['broadsideBtn'] : specialList.allEvent['broadsideBtn'].concat(broadsideBtnBasis)) : broadsideBtnBasis) : broadsideBtnBasis,
            successFunConfirm = specialList.ValidataInit ? (specialList.ValidataInit['successFun'] ? specialList.ValidataInit['successFun'] : successFunBasis) : successFunBasis,
            successBeforeFunConfirm = specialList.ValidataInit ? (specialList.ValidataInit['successBeforeFun'] ? specialList.ValidataInit['successBeforeFun'] : successBeforeFunBasis) : successBeforeFunBasis,
            errorClassConfirm = specialList.ValidataInit ? (specialList.ValidataInit.errorAll ? (specialList.ValidataInit.errorAll['errorClass'] ? specialList.ValidataInit.errorAll['errorClass'] : errorClassBasis) : errorClassBasis) : errorClassBasis,
            errorElementConfirm = specialList.ValidataInit ? (specialList.ValidataInit.errorAll ? (specialList.ValidataInit.errorAll['errorElement'] ? specialList.ValidataInit.errorAll['errorElement'] : errorElementBasis) : errorElementBasis) : errorElementBasis,
            errorPlacementConfirm = specialList.ValidataInit ? (specialList.ValidataInit.errorAll ? (specialList.ValidataInit.errorAll['errorPlacement'] ? specialList.ValidataInit.errorAll['errorPlacement'] : errorPlacementBasis) : errorPlacementBasis) : errorPlacementBasis,
            callbackConfirm = specialList['callback'] ? specialList['callback'] : callbackBasis;


        var submitValidataCommonUp = require('zyw/submitValidataCommonUp');

        submitValidataCommonUp.submitValidataCommonUp({

            rulesAll: specialList['rulesAll'],
            form: specialList['form'],
            allWhetherNull: specialList['allWhetherNull'],
            especiallyRules: specialList['especiallyRules'],
            changeStringFile: specialList['changeStringFile'],

            allEvent: {

                contentBtn: contentBtnArr,
                broadsideBtn: broadsideBtnArr

            },

            ValidataInit: {

                successFun: successFunConfirm,
                successBeforeFun: successBeforeFunConfirm,

                errorAll: { //validata error属性集

                    errorClass: errorClassConfirm,
                    errorElement: errorElementConfirm,
                    errorPlacement: errorPlacementConfirm

                }


            },

            callback: callbackConfirm

        });

    }


    module.exports = inherentSubmitValidataCommon;



});