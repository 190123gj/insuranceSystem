define(function(require, exports, module) {

    /*

    var mergeTable = require('zyw/mergeTable');

        new mergeTable({

        obj: '.demandMerge',//需要合并的td(jq对象、jq selector)

        valType: false,//默认为false。true为td下有input或者select

        mergeCallback: function(Dom //Dom为每次遍历的合并对象和被合并对象) {//每次遍历合并会调用的callback

        },
        callback: function() {//表格合并完毕后

        },
        transform: {

            open: true, //开启增删模式
            active: '.addDemand', //允许增加项对象（支持任何JQselect）
            on: '.active',//选中样式（支持任何JQselect）
            addBtn: '.fnAddLineSubitem', //选中增加项后单击增加的按钮（单个）
            addHandleEachExcept: function(callObj) {//选中对象active、新增tr对象newTr

                //return false;//如不满足条件 return false 则不再往下执行
                console.log(1);

            },
            addHandleEachTd: function(callObj) {//添加时参与循环改变rowspan的对象

                console.log(2);

            },
            addCallback:function(){//添加完毕后回调

            },
            addContent: $('#addList').html(), //Dom或拼接字符串

            removeBtn: '.removeDemand', //对应tr内td或td内元素
            removeBeforeHandle :function(callObj){//当前删除按钮对象,删除前操作

            },
            removeHandleEachExcept: function(callObj) {//重新显示的TD

                console.log(3);

            },
            removeHandleEachTd: function(callObj) {//移除时参与循环改变rowspan的对象

                console.log(4);

            },
            removeCallback:function(){//删除完毕后回调

            },

        })

        or

        newMergeTable = new mergeTable();

        newMergeTable.init({

        obj: '.demandMerge',//需要合并的td(jq对象、jq selector)

        valType: false,//默认为false。true为td下有input或者select

        mergeCallback: function(Dom //Dom为每次遍历的合并对象和被合并对象) {//每次遍历合并会调用的callback

        },
        callback: function() {//表格合并完毕后

        },
        transform: {

            open: true, //开启增删模式
            active: '.addDemand', //允许增加项对象（支持任何JQselect）
            on: '.active',//选中样式（支持任何JQselect）
            addBtn: '.fnAddLineSubitem', //选中增加项后单击增加的按钮（单个）
            addHandleEachExcept: function(callObj) {//选中对象active、新增tr对象newTr

                //return false;//如不满足条件 return false 则不再往下执行
                console.log(1);

            },
            addHandleEachTd: function(callObj) {//添加时参与循环改变rowspan的对象

                console.log(2);

            },
            addCallback:function(){//添加完毕后回调

            },
            addContent: $('#addList').html(), //Dom或拼接字符串

            removeBtn: '.removeDemand', //对应tr内td或td内元素
            removeBeforeHandle :function(callObj){//当前删除按钮对象,删除前操作

            },
            removeHandleEachExcept: function(callObj) {//重新显示的TD

                console.log(3);

            },
            removeHandleEachTd: function(callObj) {//移除时参与循环改变rowspan的对象

                console.log(4);

            },
            removeCallback:function(){//删除完毕后回调

            },

        })

    */


    function mergeTable(objList) {

        if (objList) this.init(objList);

    }

    mergeTable.prototype = {

        init: function(objList) {

            this.obj = objList && objList.obj;
            this.objVal = objList && objList.objVal;
            this.mergeCallback = objList && objList.mergeCallback;
            this.callback = objList && objList.callback;
            this.transform = objList && objList.transform;
            this.executeInit();
            if (this.transform && (this.transform.open === true)) this.transformFun();

        },
        typeJust: function(getObj) {

            if (typeof getObj == 'object') {

                return getObj;

            } else if (typeof getObj == 'string') {

                return $(getObj);

            }

        },
        executeInit: function() {

            if (this.obj) this.executeText();
            if (this.objVal) this.executeVal();
            if (this.callback) this.callback();

        },
        executeText: function() {

            this.execute(this.typeJust(this.obj), false);

        },
        executeVal: function() {

            this.execute(this.typeJust(this.objVal), true);

        },
        execute: function(thisObjse, jusdese) { //初始合并表格

            var self = this;

            thisObjse.each(function(index, el) {

                var $el, $index, $price, $parent, $parentPrev, $prev;

                $el = $(el);
                $index = $el.index();
                $price = jusdese ? $el.find('input,select').val() : $el.text();
                $parent = $el.parent();
                $parentPrev = $parent.prevAll();

                if (!$parent.index()) return true;

                $parentPrev.each(function(index, el) {

                    var $prevEl, $prevPrice, prevRowspan, prevLength, rowspan;

                    $prevEl = $(el).children().eq($index);
                    $prevPrice = jusdese ? $prevEl.find('input,select').val() : $prevEl.text();
                    rowspan = parseInt($prevEl.attr('rowspan') || 1);
                    prevLength = $prevEl.prev().length;
                    prevRowspan = parseInt($prevEl.prev().attr('rowspan') || 1);

                    if (($prevPrice == $price) && ((prevLength) ? prevRowspan > rowspan : true)) {

                        $el.hide();
                        $prevEl.attr('rowspan', rowspan + 1);

                        if (self.mergeCallback && $prevEl.is(':visible')) {

                            self.mergeCallback({

                                mergeObj: $prevEl,
                                byMergeObj: $el

                            });

                        }

                    } else {

                        return false;

                    }

                });

            });

        },
        transformFun: function() {

            if (this.transform.active && this.transform.on && this.transform.addBtn) {

                this.signFun();
                this.addFun();

            }

            if (this.transform.removeBtn) this.removeFun();

        },
        signFun: function() {

            var self = this;

            $('body').on('click', this.transform.active, function(event) { //标记

                var $this, $siblings;

                $this = $(this);
                $siblings = $this.parents('tbody').find('td');

                $siblings.removeClass(self.transform.on.replace(/^[\.\#]/, ''));
                $this.addClass(self.transform.on.replace(/^[\.\#]/, ''));

            })

        },
        addFun: function() {

            var self = this;

            $('body').on('click', this.transform.addBtn, function(event) { //增加任意行

                var $active, rowspan, tdIndex, $parent, trIndex, $Tbody, $next, $hideNextAll, $prevAll;

                //加到哪里相关操作
                $active = $(self.transform.active + self.transform.on);
                rowspan = parseInt($active.attr('rowspan') || 1);
                tdIndex = $active.index();
                $parent = $active.parent();
                trIndex = parseInt($parent.index());
                $Tbody = $active.parents('tbody');
                $next = $Tbody.find('tr').eq(trIndex + rowspan - 1);
                $hideNextAll = $active.nextAll().length;

                if (!$hideNextAll && $active.length) return false;

                if (self.transform.addHbefore) {

                    var addHbeforeReturn;

                    addHbeforeReturn = self.transform.addHbefore({ //选中对象active、新增tr对象newTr
                        active: $active,
                        This: $(this)
                    });

                    if (addHbeforeReturn === false) return false;

                }

                $next.after(self.transform.addContent);
                $next.next().find('td').eq(tdIndex).hide().prevAll().hide();

                if (self.transform.addHandleEachExcept) {

                    var addHandleEachExceptReturn;

                    addHandleEachExceptReturn = self.transform.addHandleEachExcept({ //选中对象active、新增tr对象newTr
                        active: $active,
                        newTr: $next.next(),
                        This: $(this)
                    });

                    if (addHandleEachExceptReturn === false) return false;

                }


                //加到哪里相关操作end


                //控制各级rowspan

                $prevAll = $parent.prevAll();

                $active.attr('rowspan', rowspan + 1); //选中对象改变rowspan

                tdIndex = self.commonEachTwo($active.prevAll().not(':hidden'), tdIndex, 1, self.transform.addHandleEachTd); //选中对象前面的td改变rowspan

                if (tdIndex < 0) return false;

                self.commonEachOne($prevAll, tdIndex, 1, self.transform.addHandleEachTd); //遍历prevAll tr

                //控制各级rowspan end

                if (self.transform.addCallback) self.transform.addCallback();

            });

        },
        removeFun: function() {

            var self = this;

            $('body').on('click', this.transform.removeBtn, function(event) {

                var $this, $removeTr, $active, $prevAll, tdIndex, $removeTrNext, $removeTd, $removeNextTd, $thisRowspan, $removeTrIndex, $Tbody, $on;

                //控制前级rowspan
                $this = $(this);
                $Tbody = $this.parents('tbody');
                $thisRowspan = parseInt($this.attr('rowspan') || 1);

                $removeTr = $this.parents('tr');
                $removeTd = $removeTr.find('td').not(':hidden');
                $removeTrIndex = $removeTr.index();
                $active = $($removeTr.find('td').not(':hidden')[0]);
                $prevAll = $removeTr.prevAll();
                tdIndex = $active.index();
                $on = $(self.transform.active + self.transform.on);

                if (self.transform.removeBeforeHandle) {

                    var removeBeforeHandle;

                    removeBeforeHandle = self.transform.removeBeforeHandle({ //当前删除按钮
                        element: $this
                    });

                    if (removeBeforeHandle === false) return false;

                }

                $removeTr.nextUntil($Tbody.find('tr').eq($removeTrIndex + $thisRowspan)).remove(); //移除大行后面的所有小行

                $removeTrNext = $this.parents('tr').next();
                $removeNextTd = $removeTrNext.find('td');

                self.commonEachOne($prevAll, tdIndex, -$thisRowspan, self.transform.removeHandleEachExcept); //遍历prevAll tr

                //控制前级rowspan end

                //控制后级rowspan

                $removeTd.each(function(index, el) {

                    var $el, $index, rowspan;

                    $el = $(el);
                    $index = $el.index();
                    rowspan = parseInt($el.attr('rowspan') || 1);

                    if ($removeNextTd.eq($index).is(':hidden')) {

                        $removeNextTd.eq($index).show().attr('rowspan', (rowspan > 1) ? (rowspan - $thisRowspan) : rowspan);

                        if (($on.index() == $index) && ($on.parent('tr').index() == $removeTr.index())) $removeNextTd.eq($index).addClass(self.transform.on.replace(/^[\.\#]/, '')); //删除后重新找到同组中改被标记的项

                        if (self.transform.removeHandleEachTd) {

                            self.transform.removeHandleEachTd({
                                showTd: $removeNextTd.eq($index)
                            });

                        }

                    }

                });
                //控制后级rowspan end

                $removeTr.remove(); //移除该行

                if (self.transform.removeCallback) self.transform.removeCallback();

            });

        },
        commonEachOne: function($prevAll, tdIndex, $thisRowspan, handleEachTd) {

            var self = this;

            $prevAll.each(function(index, el) { //遍历prevAll tr

                var $el, $td, $prevTd;

                $el = $(el);
                $td = $el.find('td').eq(tdIndex);
                $prevTd = $td.prevAll('td');

                if (tdIndex <= 0) return false;

                if ($td.is(':visible')) {

                    tdIndex = self.commonEachTwo($prevTd.not(':hidden'), tdIndex, $thisRowspan, handleEachTd);

                }

            });

        },
        commonEachTwo: function($obj, tdIndex, $thisRowspan, handleEachTd) {

            $obj.each(function(index, el) {

                var $el, rowspan;

                $el = $(el);
                rowspan = parseInt($el.attr('rowspan') || 1);

                $el.attr('rowspan', rowspan + $thisRowspan);

                if (handleEachTd) {

                    handleEachTd({
                        EachTd: $el
                    });

                }

                tdIndex--;

            });

            return tdIndex;

        }

    }

    module.exports = mergeTable

})