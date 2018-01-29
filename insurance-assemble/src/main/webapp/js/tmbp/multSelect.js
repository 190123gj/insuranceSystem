define(function (require, exports, module) {
    //2016.03.26 需新增一个 value text 取值对象
    var template = require('arttemplate');
    var util = require('util');
    //多级选择
    function Select(title) {
        this.title = title || '选择';
        this.$box;
        this.list = []; //主要数据列表
        this.listHtml = ''; //主要数据html
    };

    Select.prototype = {
        constructor: Select,
        init: function (obj) {
            var self = this;
            /*
             *初始化时先合并选项
             *有点不合理，一般情况是在new的时候把默认值带上
             */
            for (var k in obj) {
                self[k] = obj[k]
            }

            //建立jQuery对象
            self.$box = $(_html);
            self.$box.find('#boxTitle').html(self.title);

            self.$box.find('#boxBody').prepend(creatItem(self.listTitle));

            //绑定事件
            self.$box.appendTo($body).on('click', '.fnChoose:not(.active)', function () {
                //仅仅是添加active状态
                /*
                 *给所有非激活li.c-li添加点击效果
                 *先出某个顶级父级下所有的激活效果，再给自身添加
                 *
                 */
                var _this = $(this);
                _this.parents('.multiple-list').find('.fnChoose').removeClass('active');
                _this.addClass('active');
            }).on('click', '.multiple:first input.ui-btn', function () {
                /*
                 *点击搜索一级下拉
                 *
                 */
                var _key = $(this).parent().find('input.ui-text').val();
                self.searchMainMenu(_key);
            }).on('keyup', '.multiple:first input.ui-text', function (e) {
                //搜索的输入框 回车
                /*
                 *如果是回车，搜索一级下拉
                 *
                 */
                if (e.keyCode == 13) {
                    var _key = this.value;
                    self.searchMainMenu(_key);
                }
            }).on('click', '#boxAdd', function () {
                //添加
                /*
                 *找到最后一个multiple中激活的li，并移除激活效果
                 *对比结果中是否已有value
                 *添加或不添加
                 *
                 */
                var _thisLi = self.$box.find('.multiple:last').find('li.active').removeClass('active'),
                    _res = self.result(),
                    canAdd = true,
                    _valArr = _res[self.markKey.val] || [];

                for (var i = 0; i < _valArr.length; i++) {
                    if (_valArr[i] == _thisLi.attr('val')) {
                        return;
                    }
                }

                self.$box.find('#boxHasChoose .ul').append(_thisLi.clone());

            }).on('click', '#boxDel', function () {
                //删除
                /*
                 *找到已选择下已激活的li
                 *给它的下一个或下一个添加active并移除本身
                 *
                 */
                var _active = self.$box.find('#boxHasChoose li.active');
                if (_active.next().length) {
                    _active.next().addClass('active');
                } else {
                    _active.prev().addClass('active');
                }
                _active.remove();

            }).on('click', '#boxClose', function () {
                //点击关闭按钮
                var _input = self.input,
                    _someResArr = self.result()[_input[0]] || [],
                    _obj = {};

                self.selected.length = 0;
                for (var i = 0; i < _someResArr.length; i++) {
                    for (var j = 0; j < _input.length; j++) {
                        _obj[_input[j]] = self.result()[_input[j]][i];
                    }
                    self.selected.push(_obj);
                    _obj = {};
                }

                /*
                 *如果在init的时候有close
                 *就使用close，并把当前self传入
                 *
                 */

                if (!self.close) {
                    self.saveValue();

                } else {
                    self.close(self);
                }

            }).on('click', '.close', function () {

                self.hide();
            });

            $(self.btn).on('click', function () {
                self.show();
            });

            return self;

        },
        show: function () {
            var self = this;
            //还原选择框
            self.$box.find('#boxBody').find('#boxHasChoose').html(creatSubItem(self.selected, self.input, self.markKey));
            self.$box.removeClass('fn-hide');
        },
        hide: function () {
            var self = this;
            self.$box.addClass('fn-hide').find('.multiple:first input.ui-text').val('');
            self.searchMainMenu(null);
        },
        searchMainMenu: function (key) {

            key = $.trim(key);
            var self = this;
            if (!key) {
                self.$box.find('.multiple').eq(0).find('.multiple-list').html(self.listHtml);
                return;
            }

            var _reg = new RegExp('\\' + key),
                keyName = self.keyName,
                $html = $(self.listHtml),
                $div = $('<div></div>');

            $html.find('.fnChoose').each(function (index, el) {
                var _this = $(this);
                if (_reg.test(_this.text())) {
                    _this.appendTo($div);
                }
            });

            self.$box.find('.multiple').eq(0).find('.multiple-list').html($div.html());

        },
        result: function () {

            var self = this,
                _liArr = self.$box.find('#boxHasChoose li'),
                _input = self.input,
                _resObj = {};

            _liArr.each(function (index, el) {
                var _this = $(this);

                for (var i = 0; i < _input.length; i++) {

                    if (!_resObj[_input[i]]) {
                        _resObj[_input[i]] = [];
                    }
                    _resObj[_input[i]].push(_this.attr(_input[i]));
                }

            });

            return _resObj;

        },
        saveValue: function () {

            var self = this;
            //假若有节点
            var _res = self.result();
            if (self.input.length) {
                for (var i = 0; i < self.input.length; i++) {
                    document.getElementById(self.input[i]).value = _res[self.input[i]] || '';
                }
            }
            self.hide();

        },
        query: function (callback) {

            var self = this;

            var subClassKey = self.sublistKey,
                propArr = self.input,
                markKey = self.markKey;

            util.ajax({
                url: self.ajaxUrl,
                success: function (res) {
                    if (res.success) {
                        var _s = '',
                            _arr = res.data;

                        if (_arr.length) {

                            for (var i = 0; i < _arr.length; i++) {
                                _s += '<li class="fn-csp li fnChoose"' + (joinProp(propArr, _arr[i])) + ' val="' + _arr[i][markKey.val] + '">[' + _arr[i][markKey.val] + ']' + _arr[i][markKey.txt] + '</li>';
                            }

                        }

                        _s = '<ul class="ul">' + _s + '</ul>';

                        self.listHtml = _s;
                        self.$box.find('.multiple').eq(0).find('.multiple-list').html(self.listHtml);

                        if (callback) {
                            callback(self)
                        }

                    } else {
                        Y.alert('提示', res.message);
                    }
                }
            });

            // util.getMultilevel({
            //  url: self.ajaxUrl,
            //  sublist: subClassKey,
            //  insert: function(html) {
            //      self.$box.find('.multiple').eq(0).find('.multiple-list').html(html);
            //      self.listHtml = html;
            //      if (callback) {
            //          callback(self)
            //      }
            //  },
            //  liTpl: function(obj, i, subHtml) {
            //      var _html = '';
            //      if (obj[subClassKey] && obj[subClassKey].length) {
            //          //标题
            //          _html = '<li class="li"><p class="fn-csp li-hd fnChoose"' + (joinProp(propArr, obj)) + ' val="' + obj[markKey.val] + '">' + obj[markKey.txt] + '</p>' + subHtml + '</li>';
            //      } else {
            //          //选项
            //          //joinProp(arr, obj)
            //          _html = '<li class="fn-csp li fnChoose"' + (joinProp(propArr, obj)) + ' val="' + obj[markKey.val] + '">' + obj[markKey.txt] + '</li>';
            //      }
            //      return _html;
            //  },
            //  ulTpl: function(str, i) {
            //      return '<ul class="ul">' + str + '</ul>';
            //  },
            //  callback: function(res) {
            //      self.list = res.data.list;
            //  }
            // });

        }
    };

    var $body = $('body');

    var _html = [
        '<div class="m-modal-box fn-hide">',
        '    <div class="m-modal-overlay"></div>',
        '    <div class="m-modal m-modal-default">',
        '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title" id="boxTitle"></span></div>',
        // '        <div class="apply-org-hd">',
        // '            <span class="fn-right fn-usn fn-csp close" href="javascript:void(0);">&times;</span>',
        // '            <span id="boxTitle"></span>',
        // '        </div>',
        // '        <div class="apply-org-body fn-clear" id="boxBody">',
        '        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner fn-clear" id="boxBody">',
        '            <div class="fn-left apply-org-multiple last">',
        '                <h3 class="multiple-hd">已选</h3>',
        '                <div class="btns">',
        '                    <a href="javascript:void(0);" class="btn add" id="boxAdd">添加&nbsp;&nbsp;&gt;</a>',
        '                    <a href="javascript:void(0);" class="btn del" id="boxDel">&lt;&nbsp;&nbsp;删除</a>',
        '                </div>',
        '                <div class="multiple-box has-choose">',
        '                    <div class="multiple-list" id="boxHasChoose">',
        '                    </div>',
        '                </div>',
        '            </div>',
        '        </div></div></div>',
        '        <div class="fn-mt10 fn-tac">',
        '            <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-mr20" id="boxClose">确定</a>',
        '            <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-ml20 close">取消</a>',
        '        </div>',
        '    </div>',
        '</div>'
    ].join('');

    function creatItem(title) {
        return ['<div class="fn-left apply-org-multiple multiple">',
            '    <h3 class="multiple-hd">' + title + '</h3>',
            '    <div class="multiple-box">',
            '        <div class="search">',
            '            <input class="ui-text fn-w200" type="text">',
            '            <input class="ui-btn ui-btn-fill ui-btn-green" type="button" value="搜索">',
            '        </div>',
            '        <div class="multiple-list">',
            '        </div>',
            '    </div>',
            '</div>'
        ].join('');
    }

    function creatSubItem(arr, propArr, markKey) {

        var _s = '';
        for (var i = 0; i < arr.length; i++) {
            _s += '<li class="fn-csp li fnChoose"' + (joinProp(propArr, arr[i])) + ' val="' + arr[i][markKey.val] + '">[' + arr[i][markKey.val] + ']' + arr[i][markKey.txt] + '</li>';
        }

        return '<ul class="ul">' + _s + '</ul>';


        // var _htmlTpl = ['<ul class="ul">',
        //  '    {{each list as item i}}',
        //  '    <li class="fn-csp li fnChoose" val="{{item.val}}">{{item.text}}</li>',
        //  '    {{/each}}',
        //  '</ul>'
        // ].join('');
        // return template.compile(_htmlTpl)({
        //  list: arr
        // });
    }

    function joinProp(arr, obj) {
        var _s = '';
        for (var i = 0; i < arr.length; i++) {
            _s += ' ' + arr[i] + '="' + obj[arr[i]] + '"';

        }
        return _s;
    }

    // function creatFirsUl(arr, keyName) {
    //  var _htmlTpl = ['<ul class="f-ul">',
    //      '    {{each list as item i}}',
    //      '    <li class="f-li">',
    //      '        <p class="fn-csp f-hd">&nbsp;&gt;&nbsp;{{item[' + keyName.text + ']}}</p>',
    //      '        <ul class="c-ul">',
    //      '            {{each item.subclass as subitem subi}}',
    //      '            <li class="c-li" value="{{subitem[' + keyName.value + ']}}">{{subitem[' + keyName.text + ']}</li>',
    //      '            {{/each}}',
    //      '        </ul>',
    //      '    </li>',
    //      '    {{/each}}',
    //      '</ul>'
    //  ].join('');
    //  return template.compile(_htmlTpl)({
    //      list: arr
    //  });
    // }
    module.exports = Select;

});