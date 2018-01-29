define(function(require, exports, module) {

    var md5 = require('md5'),

        validateDataReturnGather = { //数据是否改变、否必填

            formSerializeMd5: function(_form) { //返回表单序列值

                var _formSerialize = _form.serialize();

                return md5(_formSerialize);

            },

            fm5WhetherChange: function(objList) { //数据是否有改变

                var _newSerializeMd5 = validateDataReturnGather['formSerializeMd5'](objList['form']),
                    fm5 = (_newSerializeMd5 != objList['initSerializeMd5']) ? 1 : 0; //数据是否有改变

                return fm5;

            },

            rulesAllFalse: function(objList) { //数据是否完整

                var string = (objList['changeStringFile'] && require(objList['changeStringFile']).portInitString) ? require(objList['changeStringFile']).portInitString : objList['allWhetherNull']['stringObj'],
                    boll = (objList['allWhetherNull']['especiallyBoll'] != undefined) ? objList['allWhetherNull']['especiallyBoll'] : objList['allWhetherNull']['boll'],
                    checkStatus = objList['form'].allWhetherNullUp(string, boll); //是否填写完整

                return checkStatus;

            },

        }

    collectJsonFun = function(objList) {

        /*
        objList:{
            form:'',
            initSerializeMd5:'',
            allWhetherNull:{
                stringObj:'',
                boll:''
            }
        }
        */


        return {
            fm5: validateDataReturnGather['fm5WhetherChange'](objList), //页面是否有改动
            checkStatus: validateDataReturnGather['rulesAllFalse'](objList) //当前页面是否填写完整
        }

    }



    module.exports = {
        formSerializeMd5: validateDataReturnGather.formSerializeMd5,
        collectJsonFun: collectJsonFun
    }

})