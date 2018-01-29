/**
 * Created by eryue
 * Create in 2016-12-28 17:15
 * Description:
 * var submitObj = {//定义一堆默认的参数和validate插件验证规则
    submitBtnClass: 'submitBtn',//提交按钮的className
    formObj: $('#form'),//form表单
    ignoreClass: '',//额外的排除被添加验证规则的元素的className
    validateEle: 'fn-validate',//需要验证元素的className
    renderAddRules: true,//页面渲染时就给元素添加验证规则
    renderValidate: true,//页面渲染时就验证值
    validateRules: { //validate插件需要的规则
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function(error, element, callback) {
            if(typeof callback === "function") {
                callback(error,element);
                return;
            };
            // console.log(element)
            if(element.hasClass('appendAfterEle')){
                element.after(error);
            }else if(element.hasClass('parentsError') || element.parents('.parentsErrorBox').length > 0){
                element.parents('.parentsErrorBox').append(error);
            }else {
                element.parent().append(error);
            }
        },
        rules: {
            test:{
                required: true,
            }
        },
        messages: {
            test:{
                required: '必填',
            }
        }
    }
};
 */



'use strict';
define(function(require, exports, module) {

    require('validate');
    require('validate.extend');

    var submitObj = {//定义一堆默认的参数和validate插件验证规则
        submitBtnClass: 'submitBtn',
        formObj: $('#form'),//jq对象
        validateFn:{},
        ignoreClass: '',//需要排除验证元素的class
        validateEle: 'fn-validate',//需要验证元素的obj
        renderAddRules: true,
        renderValidate: false,
        validateRules: { //validate插件需要的规则
            errorClass: 'error-tip',
            errorElement: 'b',
            ignore: '.ignore',
            onkeyup: false,
            errorPlacement: function(error, element, callback) {
                if(typeof callback === "function") {
                    callback(error,element);
                    return;
                };
                // console.log(element)
                if(element.hasClass('appendAfterEle')){
                    element.after(error);
                }else if(element.hasClass('parentsError') || element.parents('.parentsErrorBox').length > 0){
                    element.parents('.parentsErrorBox').append(error);
                }else {
                    element.parent().append(error);
                }
            },
            rules: {
                test:{
                    required: true,
                }
            },
            messages: {
                test:{
                    required: '必填',
                }
            }
        }
    };

    //判断一个对象是否为空
    function isEmptyObj(obj) {
        var res = true;
        //Object.prototype.toString.apply(obj) !== '[object Object]'
        if(!obj || Object.prototype.toString.apply(obj) !== '[object Object]') return false;
        $.each(obj,function (key,keyval) {
            if(!keyval) {
                res = false;
                return false;
            }
        });
        return res;
    };

    //组件开始
    function validateForm(obj) {
        var self = this;
        //检查初始化选项，如果初始化未设置选择，则采用默认的配置submitObj；
        if(!obj || isEmptyObj(obj)) {
            obj = submitObj;
        }else {
            // 使用默认配置补齐未设置的配置项
            $.each(submitObj,function (_k,_v) {
                (!obj[_k] || (Object.prototype.toString.apply(obj[_k]) !== '[object Object]' && isEmptyObj(obj[_k]))) && (obj[_k] = _v);
            })
        }

        self.init(obj);
    };

    validateForm.prototype = {

        init: function (obj) {
            // console.log(obj)
            var self = this;

            self.formObj = obj.formObj;
            self.submitBtnClass = obj.submitBtnClass;
            self.ignoreClass = obj.ignoreClass;
            self.validateEle = obj.validateEle;
            self.renderAddRules = obj.renderAddRules || !!self.formObj.attr('renderrules') && typeof(self.formObj.attr('renderrules')) != 'undefined'; //是否页面加载就添加验证规则
            self.renderValidate = obj.renderValidate || !!self.formObj.attr('rendervalidate') && typeof(self.formObj.attr('rendervalidate')) != 'undefined'; //是否页面加载完成就执行验证规则
            self.validateRules = obj.validateRules; //验证插件需要的验证规则
            self.submitHandler = obj.submitHandler  || false;

            // $form = self.formObj;

            self.validateRules = {
                errorClass: obj.validateRules.errorClass || submitObj.validateRules.errorClass,
                errorElement: obj.validateRules.errorElement || submitObj.validateRules.errorElement,
                ignore: obj.validateRules.ignore || submitObj.validateRules.ignore,
                onkeyup: obj.validateRules.onkeyup || submitObj.validateRules.onkeyup,
                errorPlacement: obj.validateRules.errorPlacement || submitObj.validateRules.errorPlacement,
                rules: obj.validateRules.rules || submitObj.validateRules.rules,
                messages: obj.validateRules.messages || submitObj.validateRules.messages,
            };
            self.validateFn = self.formObj.validate(self.validateRules);
            // console.log(self.validateFn,11);
            if(self.renderAddRules) self.diyName(self.addRules());
            if(self.renderValidate) self.formObj.valid();
            if(!!self.submitHandler && typeof self.submitHandler == 'function') self.submitHandler();

        },
        addRules: function($addObj) {//$addObj为需要验证的name元素
            var self = this;
            var $validateList;
            if(!$addObj || $addObj.length == 0){
                $validateList = !self.ignoreClass ? self.formObj.find('.' + self.validateEle) : self.formObj.find('.' + self.validateEle).not('.' + self.ignoreClass);
            }else {
                var isName = !!$addObj.attr('name');
                if(isName){
                    var ignoreClassName = !self.ignoreClass ? '' : '.' + self.ignoreClass;
                    $validateList = $addObj.not(ignoreClassName);
                    if(!$addObj.hasClass(self.validateEle)) $validateList = [];
                }else {
                    $validateList = !self.ignoreClass ? $addObj.find('.' + self.validateEle) : $addObj.find('.' + self.validateEle).not('.' + self.ignoreClass);
                }
            };
            // console.log($validateList)
            $.each($validateList,function (i, e) {
                var $this = $(this);
                $this.rules("remove");
                if($this.hasClass('oneInThree')){
                    // $this.hasClass('oneInThree') && console.log('33')
                    $this.rules('add', {
                        required: true,
                        min:0.001,
                        messages: {
                            required: '三项必须完善一项！',
                            min: '三项必须完善一项！'
                        }
                    });
                }else if($this.hasClass('allScaleNum')){
                    $this.rules('add', {
                        required: true,
                        min:0.001,
                        max:100.00001,
                        messages: {
                            required: '受益比例总和范围为0(不含0) ~ 100%！',
                            min: '受益比例总和范围为0(不含0) ~ 100%！',
                            max: '受益比例总和范围为0(不含0) ~ 100%！'
                        }
                    });
                }else{
                    $this.rules('add', {
                        required: true,
                        messages: {
                            required: '必填'
                        }
                    });
                }

            })
        },
        // genRules: function ($ele) {
        //     var
        // },
        diyName: function (cb) {
            var self = this;
            var $diyNameBox = self.formObj.find('.diyNameBox');
            var $parnetDiyName = $('[parentdiyname]');

            $.each($parnetDiyName,function (_pIndex,elWarp) {

                var $this = $(this);
                var _indexWarp = $(elWarp).parents('.parentDiyNameBox').length == 0 ? _pIndex : $this.parents('.parentDiyNameBox').find('[parentdiyname]').index($this);
                var PARENT_DIY_NAME = $this.attr('parentdiyname');
                // console.log(_indexWarp)
                //找到普通[name]的元素
                $.each($this.find('[name]'),function (_index1,el) {

                    //父级元素存在diyname则跳过，使用下面的方法验证
                    if($(el).parents('[diyname]').length > 0) return;

                    var name = $(el).attr('name');
                    if (name.indexOf('.') > 0) name = name.substring(name.lastIndexOf('.') + 1);
                    name = PARENT_DIY_NAME + '[' + _indexWarp + '].' + name;
                    $(el).attr('name', name);
                    // self.addRules($(el));
                });

                //找到需要被多次[diyname]的元素
                var $thisDiyNameBox = $this.find('.diyNameBox');
                // console.log($thisDiyNameBox)
                $.each($thisDiyNameBox,function () {

                    var $thisBox = $(this);
                    $.each($thisBox.find('[diyname]'),function (index, ele) {
                        var $thisDiyName = $(this);
                        var diyName = $thisDiyName.attr('diyname');
                        var noParentDiyName = $thisBox.hasClass('noParentDiyName');
                        var parentDiyName = !PARENT_DIY_NAME ? '' : PARENT_DIY_NAME + '[' + _indexWarp + '].';

                        $.each($thisDiyName.find('[name]'),function () {
                            var _this = $(this),
                                name = _this.attr('name');

                            if (name.indexOf('.') > 0) name = name.substring(name.lastIndexOf('.') + 1);
                            name = parentDiyName + diyName + '[' + index + '].' + name;
                            _this.attr('name', name);
                            // if (index == $thisBox.find('[diyname]').length - 1) self.addRules($thisBox.find('[diyname]'));

                        });

                    });
                });
            })

            $.each($diyNameBox,function () {

                var $thisBox = $(this);
                $.each($thisBox.find('[diyname]'),function(index,ele) {

                    var $thisDiyName= $(this);
                    var diyName = $thisDiyName.attr('diyname');
                    //如果父级元素存在parentdiyname，那么则以parentdiyname方式渲染
                    if($thisBox.parents('[parentdiyname]').length > 0) return;

                    $.each($thisDiyName.find('[name]'),function() {

                        var _this = $(this),
                            name = _this.attr('name');

                        if (name.indexOf('.') > 0) name = name.substring(name.lastIndexOf('.') + 1);
                        name = diyName + '[' + index + '].' + name;
                        _this.attr('name', name);
                        // if(index == $thisBox.find('[diyname]').length - 1) self.addRules($thisBox.find('[diyname]'));

                    });

                });
            })
            if(!cb && typeof cb === 'function') cb();
        }
    };

    module.exports = validateForm;
})