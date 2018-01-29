define(function(require, exports, module) {
    //验证方法集
    require('zyw/bfcg.itn.addValidataCommon');
    //弹窗提示
    // var hintPopup = require('zyw/hintPopup');
    // submitedFun = require('zyw/submited');

    function submitValidataCommonUp(objList) {

        var fillReviewTypeCommon = require('zyw/assistsys.smy.fillReviewTypeCommon');

        fillReviewTypeCommon.ValidataInit({

            form: objList['form'],
            errorAll: objList['ValidataInit']['errorAll'],
            successBeforeFun: objList['ValidataInit']['successBeforeFun'],
            successFun: objList['ValidataInit']['successFun'],
            RulesInit: objList['ValidataInit']['RulesInit']

        }); //validate初始化

        //非侧边栏提交
        if (objList.allEvent && objList.allEvent.contentBtn) {

            for (var i = 0; i < objList.allEvent.contentBtn.length; i++) {

                (function(i) {
                    //闭包保证每个事件i值的私有性

                    $(objList.allEvent.contentBtn[i]['clickObj']).unbind('click').click(function(event) {

                        objList.allEvent.contentBtn[i]['eventFun']({

                            objList: objList,
                            self: this,
                            portInitVal: exports, //接口对象
                            setInitVal: submitBeforeVal.collectJsonFun(submitBeforeObjList), //执行时所需要的值
                            getInitVal: fillReviewTypeCommon.submitHandlerContent //validata文件获取外部接口提供值的方法

                        });

                    });

                }(i));

            }

        }


        //侧边栏
        if (objList.allEvent && objList.allEvent.broadsideBtn) {

            var arrBroadsideBtn = [];

            for (var j = 0; j < objList.allEvent.broadsideBtn.length; j++) {

                arrBroadsideBtn.push((function(j) { //闭包保证每个事件j值的私有性

                    return objList.allEvent.broadsideBtn[j]['alias'] ? {

                        name: objList.allEvent.broadsideBtn[j]['name'],
                        alias: objList.allEvent.broadsideBtn[j]['alias'],
                        event: function() {

                            objList.allEvent.broadsideBtn[j]['events']({

                                objList: objList,
                                self: this,
                                portInitVal: exports,
                                setInitVal: submitBeforeVal.collectJsonFun(submitBeforeObjList), //执行时所需要的值
                                getInitVal: fillReviewTypeCommon.submitHandlerContent

                            });

                        }

                    } : {

                        name: objList.allEvent.broadsideBtn[j]['name'],
                        url: objList.allEvent.broadsideBtn[j]['url'],

                    }

                }(j)));
            }

            (new(require('zyw/publicOPN'))()).addOPN(arrBroadsideBtn || []).init().doRender();

        }

        //回调
        if (objList['callback']) {

            objList['callback'](objList);

        }

        //提交前计算的参数
        var submitBeforeVal = require('zyw/submitBeforeVal'),
            initSerializeMd5 = submitBeforeVal.formSerializeMd5(objList['form']), //初始化页面数据MD5
            submitBeforeObjList = {

                form: objList['form'],
                initSerializeMd5: initSerializeMd5,
                allWhetherNull: objList['allWhetherNull'],
                changeStringFile: objList['changeStringFile']

            };

    }


    exports.submitValidataCommonUp = submitValidataCommonUp;


});