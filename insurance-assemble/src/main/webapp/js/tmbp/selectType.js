/*
 * @Author: erYue
 * @Date:   2016-08-08 15:09:22
 * @Last Modified by:   erYue
 * @Last Modified time: 2016-08-08 15:40:53
 *
 * demo
 * selectFn 上添加属性 isreadonly="true"，将会禁用选择
 <div class="selectFnBox">
     <span class="ui-text fn-w200 fnInput selectFn fn-text-overflow">
         <label>请选择</label>
         <i class="icon icon-select-down"></i>
         <input type="hidden" name="id" id="selectedId">##隐藏域，存放当前分类的id，多个存在建议使用class
     </span>
     <div class="selectRslt fn-p-abs fn-usn">
     </div>
 </div>
### 1.页面加载默认会对$('.selectFnBox')进行初始化
 // new selectType({
 //     selectBoxObj: $('.selectFnBox'), //必须参数。外层JQ对象，多个会依次遍历-->初始化
 //     isReadOnly: false,//缺省false。统一配置的只读属性，只读属性也可以通过 <div class="selectFnBox" isreadonly="true">...</div>这种方式（设置属性）来单独设置，优先级：属性 > 统一配置 > 默认值（false）
 //     afterCreatCallback: {//缺省false。实例初始化完成后（事件未绑定）的回调
 //         callbackTargetCommon: function ($thisBox) {//如果$('.selectFnBox')上未定义属性callbacktarget，那么将会执行此回调函数，如果此函数未定义将执行插件内部的回调
 //
 //         },
 //         callbackTarget1: function ($thisBox) {//通过$('.selectFnBox')，获得属性callbacktarget="callbackTarget1"的值，从而判断进行哪个回调操作，会覆盖callbackTargetCommon的回调操作
 //
 //         },
 //         callbackTarget2: function ($thisBox) {
 //
 //         }
 //     },
 //     afterChoosedCallback: {//缺省false。完成选择之后的回调
 //         callbackTargetCommon: function ($this) {//如果$('.selectFnBox')上未定义属性callbacktarget，那么将会执行此回调函数，如果此函数未定义将执行插件内部的回调
 //
 //         },
 //         callbackTarget1: function ($this) {//通过$('.selectFnBox')，获得属性callbacktarget="callbackTarget1"的值，从而判断进行哪个回调操作，会覆盖callbackTargetCommon的回调操作
 //
 //         },
 //         callbackTarget2: function ($this) {
 //
 //         }
 //     }
 // });
 */

define(function(require, exports, module) {

    function selectType(obj) {
        var self = this;
         // console.log(obj.selectBoxObj)
        self.selectBoxObj = obj.selectBoxObj || false;
        if (obj && self.selectBoxObj.length > 0) self.init(obj);
    };
    selectType.prototype = {
        init: function(obj) {
            var self = this;
            self.isReadOnly = obj.isReadOnly || false; //统一配置的只读属性，只读属性也可以通过 <div class="selectFnBox" isreadonly="true">...</div>这种方式（设置属性）来单独设置，优先级：属性 > 统一配置 > 默认值
            self.afterCreatCallback = obj.afterCreatCallback || false; //实例初始化完成后的回调
            self.afterChoosedCallback = obj.afterChoosedCallback || false; //选择之后的回调
            self.timer = [];
            $.each(self.selectBoxObj, function(index, ele) {
                var $this = $(this);
                self.loadInit($this);
                self.bindEvent($this);
                self.timer[index] = '';
                $this.attr('index', index);
            })

        },
        loadInit: function($thisBox, index) {
            var self = this;
            var $selectRslt = $thisBox.find('.selectRslt');
            var callbackTarget = $thisBox.attr('callbacktarget') || 'callbackTargetCommon';
            self.loadType($selectRslt, 0); //初始化一级数据
            //初始化完成，回调，对结构的修改，此时还未绑定事件
            if (self.afterCreatCallback && self.afterCreatCallback[callbackTarget] && typeof self.afterCreatCallback[callbackTarget] == 'function') self.afterCreatCallback[callbackTarget]($thisBox);
        },
        bindEvent: function($thisBox) {
            var self = this;
            var timer = null; //定时器
            var index = $thisBox.attr('index');
            var $selectFn = $thisBox.find('.selectFn');
            var $selectRslt = $thisBox.find('.selectRslt');
            $thisBox.on('click', '.selectFn', function() {

                var isReadOnly = !!$thisBox.attr('isreadonly') && $thisBox.attr('isreadonly') == 'true' ? true : self.isReadOnly || false;
                if (isReadOnly) {
                    return;
                };
                $thisBox.find('.selectRslt').addClass('showBox');
                return false;
            }).on('click', '.selectRslt p', function() {
                var $this = $(this);

                if ($this.attr('haschildren') != 'YES') {//是否有子集

                    $thisBox.find('.selectFn label').html($this.attr('valuedata'));
                    var callbackTarget = $thisBox.attr('callbacktarget') || 'callbackTargetCommon';
                    if (self.afterChoosedCallback && self.afterChoosedCallback[callbackTarget] && typeof self.afterChoosedCallback[callbackTarget] == 'function') {

                        self.afterChoosedCallback[callbackTarget]($this);
                    } else { //默认选项
                        $thisBox.find('#selectedId').val($this.attr('kindsid')).blur();
                        $thisBox.find('#selectedName').val($this.attr('valuedata')).blur();
                    }

                    hideBox(20);
                    return false;
                }

                var isLoad = $this.hasClass('isLoad'); //isLoad表示已经加载并缓存过了，不一定显示
                var isShow = $this.hasClass('isShow'); //isShow表示已经加载并显示

                if (isShow) {//如果是展开的就收缩
                    console.log('isShow')
                    $this.removeClass('isShow').find('i').removeClass('down');
                    //$this.siblings("div").removeClass('isDiv');
                    $this.next("div").removeClass('isShow');

                } else {
                    console.log('isLoad:' + isLoad);
                    if (!isLoad) { //是否已经加载过

                        self.loadType($this); //请求加载分类

                    };

                    $this.addClass('isShow').find('i').addClass('down');

                    if ($this.next().hasClass('isDiv')) {

                        $this.next("div").addClass('isShow');

                    }

                }

                $this.parents('.selectRslt').show();
                return false;

            }).on('dblclick', '.selectRslt p', function() {
                var $this = $(this);
//                if($this.parents('.selectFnBox').hasClass('onlyChildrenCanChoose')) return;
                if($this.parent().hasClass('onlyChildrenCanChoose')) return;
                var enableChoose = $this.attr('enableChoose') == 'true';
                var haschildren = $this.attr('haschildren') == 'YES';
                // if(!enableChoose || !haschildren) return false;
                //显示当前选择项
                $thisBox.find('.selectFn label').html($this.attr('valuedata'));
                var callbackTarget = $thisBox.attr('callbacktarget') || 'callbackTargetCommon';
                if (self.afterChoosedCallback && self.afterChoosedCallback[callbackTarget] && typeof self.afterChoosedCallback[callbackTarget] == 'function') {
                    self.afterChoosedCallback[callbackTarget]($this);
                } else { //默认选项
                    $thisBox.find('#selectedId').val($this.attr('kindsid')).blur();
                    $thisBox.find('#selectedName').val($this.attr('valuedata')).blur();
                }

                hideBox(20);
            }).on('mouseover', '.selectRslt p, .selectFn', function() {
                clearTimeout(timer);
            }).on('mouseout', '.selectRslt p, .selectFn', function() {
                hideBox(200);
            });
            function hideBox(t) { //初始化状态

                timer = setTimeout(function() {
                    $thisBox.find('.selectRslt').removeClass('showBox').find('div,p').removeClass('isShow');
                    $thisBox.find('i').removeClass('down')
                }, t);

            };
        },
        loadType: function($ele, parentCatalogId) { //加载下一级数据，$ele为当前被点击的P标签JQ对象，parentCatalogId为上一级的id，parentCatalogId=0时表示初始化一级数据
            var data = {};
            var certainIdsVal = $("#certainIds").val();
            if(!certainIdsVal){
                certainIdsVal="";
            }

            data.parentCatalogId = parentCatalogId || $ele.attr('parentcatalogid');
            if(data.parentCatalogId != undefined){
                certainIdsVal="";
            }
            // console.log($("#certainIds").val(),1111,data);
            $.ajax({
                url: '/insurance/insuranceCatalog/tree.json?certainIds='+certainIdsVal,
                type: 'post',
                data: data,
                success: function(res) {
                    // console.log(res)

                    if (res.length > 0) {

                        var $temp = $('<div class="isShow isDiv"></div>');
                        var tempStr = '';

                        $.each(res, function(i, list) {
                            var valueData = list.name;
                            var paddingLeft = (list.depth - 2) * 10 + 35 + 'px';
                            var marginLeft = (list.depth - 2) * 10 + 15 + 'px';
                            var tempStr1 = '<p class="fn-text-overflow" style="padding-left:' + paddingLeft + ';"' + 'parentcatalogid="' + list.parentCatalogId + '" haschildren="' + list.hasChildren + '" enableChoose="' + list.enableChoose + '" title="' + list.name + '" valuedata="' + valueData + '" isLifeInsuranceText="' + list.isLifeInsuranceText + '" isLifeInsurance="' + list.isLifeInsurance + '" kindsid="' + list.id + '" itemtype="' + list.type + '"><i class="icon icon-select-left"  style="margin-left:' + marginLeft + ';"' + '></i>' + valueData + '</p>';
                            var tempStr2 = '<p class="fn-text-overflow" style="padding-left:' + paddingLeft + ';"' + 'parentcatalogid="' + list.parentCatalogId + '" haschildren="' + list.hasChildren + '" enableChoose="' + list.enableChoose + '" title="' + list.name + '" valuedata="' + valueData + '" isLifeInsuranceText="' + list.isLifeInsuranceText + '" isLifeInsurance="' + list.isLifeInsurance + '" kindsid="' + list.id + '" itemtype="' + list.type + '">' + valueData + '</p>';

                            if (list.hasChildren == 'YES') { //判断是否为末级
                                tempStr += tempStr1;
                            } else {
                                tempStr += tempStr2;
                            }
                        });
                        if (parentCatalogId == 0) { //判断是否是初始化
                            $ele.html(tempStr).addClass('isLoad');
                        } else {
                            $temp.append(tempStr);
                            $ele.after($temp).addClass('isLoad');
                        }

                    }

                }

            });

        }
    };

    if ($('.selectFnBox').length > 0) {
        new selectType({
            selectBoxObj: $('.selectFnBox'), //最外层jq对象，如果目标为多个则会遍历-->初始化
            // afterChoosedCallback:function ($this) {//回调，返回当前被点击的p标签jq对象（数据以属性的方式存在返回元素上）。回调未定义会执行默认的语句
            // }
        });
    }
    module.exports = selectType;
});