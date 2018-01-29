define(function(require, exports, module) {

    var hintPopup = require('zyw/hintPopup');

    $.extend($.fn, {
        addValidataCommon: function(_rulesAll, _bool) {
            this.rulesAll = _rulesAll;
            this.bool = _bool;
            return this;
        },
        initAllOrderValidata: function(_rulesAll, _bool) { //进入页面初始化验证及键值order前缀
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll,
                _bool = (_bool != undefined) ? _bool : this.bool;
            this.orderName().whetherMust(_rulesAll, _bool).allAddValidata(_rulesAll);
            return this;
        },
        allAddValidata: function(_rulesAll) { //添加全部有键值验证
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            $('[name]').each(function(index, el) {
                var _this = $(el);
                _this.addValidataFun(_this, _rulesAll);
            });

            return this;
        },
        formAllAddValidata: function(_rulesAll) { //form添加全部有键值验证
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.find('[name]').each(function(index, el) {
                var _this = $(el);
                _this.addValidataFun(_this, _rulesAll);
            });
            return this;
        },
        orderName: function() { //添加所有order前缀
            this.eachFun($("[orderName]"), function(_this, _orderName, _index) {
                _this.nameJointFun(_this, _orderName, _index);
            });
            return this;
        },
        whetherMust: function(_rulesAll, _bool) { //提交时是否验证必填
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll,
                _bool = (_bool != undefined) ? _bool : this.bool;
            for (i in _rulesAll) {
                if (_rulesAll[i].messages.required) _rulesAll[i].required = _bool;
            }
            return this;
        },
        clickAddValidata: function(_rulesAll, _this, _obj) { //添加时重新初始化该列表序列及验证方法
            //增加行
            var _tplID = _this.attr('tplID'),
                _cttID = _this.attr('cttID');
            $('#' + _cttID).append($('#' + _tplID).html());
            _this.numOrder($('#' + _cttID).children(), '.testNum', '.valNum');
            //添加验证
            _this.eachFunaddValidata(_obj, _rulesAll);
            return this;
        },
        clickAddValidataUp: function(_rulesAll, _this, Num, laterFun) { //添加时重新初始化该列表序列及验证方法
            //增加行
            var _tplID = _this.attr('tplID'),
                _cttID = _this.attr('cttID');
            $('#' + _cttID).append($('#' + _tplID).html());
            Num ? _this.numOrderUp($('#' + _cttID).children(), '.testNum', '.valNum') : _this.numOrder($('#' + _cttID).children(), '.testNum', '.valNum');
            if (laterFun) {
                laterFun($('#' + _cttID).children().last());
            }
            //添加验证
            _this.eachFunaddValidata($('#' + _cttID), _rulesAll);
            return this;
        },
        assignGroupAddValidataUp: function(_rulesAll, fun) { //添加时重新初始化该列表序列及验证4
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.click(function(event) {
                var _this = $(this);
                if (fun) {
                    if (!fun(hintPopup)) return false;
                }
                _this.clickAddValidataUp(_rulesAll, _this);
            });
            this.fnDelLineFun();
        },
        assignGroupAddValidataUpUp: function(_rulesAll, objList) { //添加时重新初始化该列表序列及验证4
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.click(function(event) {
                var _this = $(this);
                if (objList && objList.fun) {
                    if (!objList.fun(hintPopup)) return false;
                }
                _this.clickAddValidataUp(_rulesAll, _this, objList && objList.Num, objList && objList.laterFun);
            });
            this.fnDelLineFun(objList && objList.length, objList && objList.later);
        },
        eachFunaddValidata: function(_obj, _rulesAll) {
            //添加验证
            this.eachFun(_obj.find('[orderName]'), function(_this, _orderName, _index) {
                _this.nameJointFun(_this, _orderName, _index).addValidataFun(_this, _rulesAll);
            }, _rulesAll);
            return this;
        },
        fnDelLineFun: function(DelLineFun, later) {
            //删除行
            $('body').on('click', '.fnDelLine', function() {
                var _this = $(this);
                if (DelLineFun) {
                    if (!DelLineFun({
                            This: _this,
                            hintPopup: hintPopup
                        })) return false;
                }
                _this.parents('.' + _this.attr('parentsClass')).remove();
                if (later) {
                    later({
                        This: _this,
                        hintPopup: hintPopup
                    });
                }
            });
            return this;
        },
        groupAddValidata: function(_rulesAll) { //添加时重新初始化该列表序列及验证1
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.click(function(event) {
                var _this = $(this);
                _this.clickAddValidata(_rulesAll, _this, _this.prev());
            });
            this.fnDelLineFun();
        },
        fillGroupAddValidata: function(_rulesAll) { //添加时重新初始化该列表序列及验证2
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.click(function(event) {
                var _this = $(this);
                _this.clickAddValidata(_rulesAll, _this, $('#test20'));
            });
            this.fnDelLineFun();
            return this;
        },
        assignGroupAddValidata: function(_rulesAll, _choice) { //添加时重新初始化该列表序列及验证4
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.click(function(event) {
                var _this = $(this);
                _this.clickAddValidata(_rulesAll, _this, _choice);
            });
            this.fnDelLineFun();
        },
        popupGroupAddValidata: function(_rulesAll) { //添加时重新初始化该列表序列及验证3
            var _rulesAll = (_rulesAll != undefined) ? _rulesAll : this.rulesAll;
            this.click(function(event) {
                var _this = $(this);
                _this.clickAddValidata(_rulesAll, _this, _this.next());
            });
            this.fnDelLineFun();
        },
        addValidataFun: function(_this, _rulesAll) { //动态添加验证方法

            var _name = _this.realNameOperationFun(_this);
            if (_name != undefined && _rulesAll[_name] && _this.parents('form').length) {
                _this.rules('add', _rulesAll[_name]);
            }
            return this;
        },
        nameJointFun: function(_this, _orderName, _index) { //拼接order前缀方法
            var _name = _this.nameOperationFun(_this),
                _nameNew = _orderName + "[" + _index + "]." + _name;
            _this.attr("name", _nameNew);
            return this;
        },
        realNameOperationFun: function(_this) { //操作键值字符串方法
            var _name = _this.attr('name');
            //console.log(_this.attr('name'), _this.attr('class'), _this.attr('type'), _this.val());
            if (_name != undefined && _name.indexOf("].") > 0) {
                _name = _name.match(/.*\.(.*)/)[1];
            }
            return _name;
        },
        nameOperationFun: function(_this) { //操作键值字符串方法
            var _name = _this.attr('name');
            //console.log(_this.attr('name'), _this.attr('class'), _this.attr('type'), _this.val());
            if (_name != undefined && _name.indexOf("].") > 0) {
                _name = _name.substring(_name.indexOf("].") + 2);
            }
            return _name;
        },
        eachFun: function(_this, eachFunContent, _rulesAll) { //共用循环方法
            _this.each(function(_index, el) {
                var _this = $(el),
                    _index = _this.index(),
                    _orderName = _this.attr('orderName');
                _this.find('[name]').each(function(index, el) {
                    var _this = $(el);
                    eachFunContent(_this, _orderName, _index);
                });
            });
        },
        numOrder: function(obj, _write, _val) { //有序列号的列表添加列表号
            obj.each(function(index, el) {
                var _this = $(this);
                _index = _this.index() + 1;
                _this.find(_write).text(_index);
                if (_val) _this.find(_val).val(_index)
            });
        },
        numOrderUp: function(obj, _write, _val) { //有序列号的列表添加列表号
            obj.each(function(index, el) {
                var _this = $(this);
                _index = _this.index();
                _this.find(_write).text(_index + 1);
                if (_val) _this.find(_val).val(_index)
            });
        },
        allWhetherNull: function(exp, _reverse) {
            var _rules = 1,
                _reverses = _reverse ? _reverse : false;
            //console.log(exp)
            if (exp && !$.isArray(exp)) exp = exp.split(',');
            this.find('input,textarea,select').each(function() {
                var _this = $(this),
                    name = _this.realNameOperationFun(_this),
                    _WhetherToReverse = _reverse ? ($.inArray(name, exp) != -1) : !($.inArray(name, exp) != -1);
                if (exp) {
                    if (_WhetherToReverse || _this.hasClass('cancel') || _this.attr('name') == 'editorValue' || _this.hasClass('checkStatusCancel')) return;
                };
                _rules = _rules && _this.val() ? 1 : 0
                    //console.log(_rules + '-------' + name + '----------' + _this.val() + '--------------' + _this.attr('class'))
            });
            //console.log(_rules)
            return _rules;
        },
        allWhetherNullUp: function(exp, _reverse) {
            var _rules = 1,
                _reverses = _reverse ? _reverse : false;
            //console.log(exp)
            if (exp && !$.isArray(exp)) exp = exp.split(',');
            this.find('input,textarea,select').each(function() {
                var _this = $(this),
                    name = _this.realNameOperationFun(_this),
                    _WhetherToReverse = _reverse ? ($.inArray(name, exp) != -1) : !($.inArray(name, exp) != -1);
                if (exp) {
                    if (_WhetherToReverse || _this.hasClass('checkStatusCancel') || _this.hasClass('cancel') || _this.attr('name') == 'editorValue') return;
                };
                //console.log(_rules + '-------' + name + '----------' + _this.val())
                _rules = _rules && _this.val() ? 1 : 0
            });
            //console.log(_rules)
            return _rules;
        }
    });
})