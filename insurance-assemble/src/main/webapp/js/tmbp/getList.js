/**
 * getList 通用弹出层
 *
 * 初略统计，接口
 *
 * width  string  像素或百分比
 * title  string  标题
 * ajaxUrl  string  ajax的接口地址，可通过resetAjaxUrl重置
 * btn  string  触发显示弹出层的 jQuery 选择器，委托点击事件
 * tpl  object  表格的模板对象
 * resetAjaxUrl  function  重置ajaxUrl，参数时重置后的url
 *
 * multiple 是否是多选 checkbox
 *  
 * callback  function  点击 .choose 元素后的回调函数，参数是当前被点击元素的jQuery对象
 * renderCallBack  function 列表渲染完成后的回调函数，参数是当前ajax请求后的返回值
 *
 * keyup 搜索框即使反馈
 * 
 *
  var getList = require('zyw/getList');
  var _getList = new getList();
  _getList.init({
        witdh: '88px/33%', 像素或百分比
        title: 'xxx',
        ajaxUrl: '/x/x.json / /x/x.json?xx=xx', 数据请求的接口，参数最好使用 `encodeURIComponent` 转码一下，中文才有保障
        btn: '#choose', jQuery的一个选择器
        tpl: { 列表模板，用table做的，里面包含 `tbody` 列表、`thead` 表头、`form` 搜索框，符合arttemplate模板语法就好
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td class="item">{{item.projectCode}}</td>',
                '        <td>{{(item.customerName && item.customerName.length > 6)?item.customerName.substr(0,6):item.customerName}}</td>',
                '        <td>{{(item.projectName && item.projectName.length > 6)?item.projectName.substr(0,6):item.projectName}}</td>',
                '        <td>{{item.busiTypeName}}</td>',
                '        <td>{{item.amount}}</td>',
                '        <td><a class="choose" projectCode="{{item.projectCode}}" href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['项目名称：',
                '<input class="ui-text fn-w160" type="text" name="projectName">',
                '&nbsp;&nbsp;',
                '客户名称：',
                '<input class="ui-text fn-w160" type="text" name="customerName">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th width="100">项目编号</th>',
                '<th width="120">客户名称</th>',
                '<th width="120">项目名称</th>',
                '<th width="100">授信类型</th>',
                '<th width="100">授信金额</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 6
        },
        callback: function($a) { 点击`选择`回调函数，`$a` 是选的那个DOM的jQuery对象
            window.location.href = '/projectMg/contract/addProjectContract.htm?projectCode=' + $a.attr('projectCode');
        }
  });
 
 *
 * 
 */

define(function (require, exports, module) {
    var template = require('arttemplate');
    var util = require('util');
    template.helper('getYYYMMDD', function (ts) {

        var d = util.getNowTime(ts);

        return d.YY + '-' + d.MM + '-' + d.DD

    });

    //弹窗获取列表
    function getList() {
        this.input = []; //缓存、储存数据
        this.btn;
        this.tpl = {
            tbody: '',
            thead: '',
            form: '',
            item: 0,
        };
        this.title = '请选择';
        this.ajaxUrl;
        this.allChoose = '';
    }
    getList.prototype = {
        constructor: getList,
        init: function (obj) {

            /**
             * [初始化操作]
             * obj
             * title string
             * ajaxUrl string
             * btn string jQuery select
             * input arr item->jQuery id select 与tpl.tbody选择中的属性一一对应
             * tpl obj tbody\form\thead\item
             * callback
             */
            var self = this;
            //合并参数
            for (var k in obj) {
                this[k] = obj[k];
            }

            self.$box = $(_html);
            self.$box.find('#boxTitle').html(self.title).end().find('#boxSearch').html(self.tpl.form).end().find('#allChoose').html(self.allChoose).end().find('#boxThead').html(self.tpl.thead);
            self.$body.append(self.$box);
            self.$list = self.$box.find('#boxList');
            self.$pages = self.$box.find('#boxPage');
            self.$load = self.$box.find('#boxLoad');
            self.$foot = self.$box.find('#boxFoot');
            self.$modal = self.$box.find('#modal');
            //无数据
            self.$foot.find('td').attr('colspan', self.$box.find('#boxThead th').length);

            // 如果是多选
            if (!!self.multiple) {

                // var $boxSearch = self.$box.find('#boxSearch');

                // self.$modal.css({
                //     height: '520px',
                //     marginTop: '-300px'
                // });
                // self.$pages.after('<div class="fn-tac"><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-fwb fnChooselist fn-ml10 fn-mr10">确定</a><a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-fwb close fn-ml10 fn-mr10">取消</a></div>');

                // self.$modal.css('marginTop', '-261px');

                // var $boxSearch_ = $('<div class="fn-hide"></div>');

                // self.$box.find('#boxSearch').after($boxSearch_);
                // self.$box.on('scroll', '.m-modal-body', function () {
                //     console.log('1')
                // })

                // self.$box.find('.m-modal-title').append('<div class="m-modal-btns"><span href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-fwb fnChooselist fn-ml10 fn-mr10 fn-csp">确定</span></div>')

                self.$box.find('.m-modal-body').css('height', '95%').after([
                    '<div class="fn-tac fn-pt10">',
                    '<span href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-fwb fnChooselist fn-ml10 fn-mr10 fn-csp">确定</span>',
                    '<span href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-fwb close fn-ml10 fn-mr10 fn-csp">取消</span>',
                    '</div>'
                ].join(''))

                self.$pages.remove()

                self.$box.on('click', '.fnChooselist', function () {

                    if (self.callback) {
                        self.callback(self);
                    }

                    if (self.autoHide) {
                        self.hide()
                    }

                });

            }

            // 是否开启即时反馈
            if (!!self.keyup) {
                self.timer = setTimeout(function () {}, 500)
                self.$box.on('keyup', '#boxSearch input[type="text"]', function () {
                    clearTimeout(self.timer)
                    self.timer = setTimeout(function () {
                        self.getDate(1)
                    }, 200)
                })

            }

            //事件绑定
            self.$body.on('click', self.btn, function () {
                self.getDate(1);
                self.show();
                self.isLoad = false;
            });
            self.$box.find('#boxSearch').submit(function (e) {
                e.preventDefault();
                self.getDate(1);
            });
            self.$box.on('click', '.close,.boxOff', function () {
                self.$box.addClass('fn-hide');
                //清空输入项
                self.$box.find('form').find('input:not(.fnGetListIgnore),select:not(.fnGetListIgnore)').each(function (index, el) {
                    var _this = $(this);
                    switch (_this.attr('type') || _this[0].nodeName) {
                        case 'hidden':
                            break;
                        case 'text':
                        case 'data':
                            _this.val('');
                            break;
                        case 'radio':
                        case 'checkbox':
                            _this.removeProp('checked');
                            break;
                        case 'SELECT':
                            _this.find('option').removeProp('selected')
                            break;
                    }
                });
            }).on('click', '#boxPage a', function () {

                var _this = $(this);
                if (_this.hasClass('disabled')) {
                    return;
                }
                self.getDate(parseInt(_this.attr('page'), 10));

            }).on('blur', '#boxPage .input', function () {

                var _this = $(this),
                    _max = +_this.attr('maxitem');
                if (_this.val() > _max) {
                    _this.val(_max)
                }

            }).on('click', '#boxPage .btn', function () {

                var _this = $(this),
                    _val = _this.prev().val();

                if (!!!_val.replace(/\s/g, '')) {
                    return;
                }

                self.getDate(parseInt(_val, 10));

            }).on('click', '.choose', function () {

                var _this = $(this);
                if (self.callback) {
                    self.callback(_this);
                } else {
                    for (var i = self.input.length - 1; i >= 0; i--) {
                        self.$body.find('#' + self.input[i]).val(_this.attr(self.input[i])).trigger('change');
                    }
                }
                self.$box.addClass('fn-hide');

            }).on('click', '.allChoose', function () {

                var _this = self.$box.find('input[type="checkbox"]'),
                    i, _all = '';
                for (i = 0; i < _this.length; i++) {
                    if (_this.eq(i).is(":checked")) {
                        _all += _this.eq(i).val() + ';'
                    }
                }
                for (var i = self.input.length - 1; i >= 0; i--) {
                    self.$body.find('#' + self.input[i]).val(_all);
                }
                self.$box.addClass('fn-hide');

            }).on('click', '[type=checkbox]', function () {

                var _this = $(this);
                if(_this.prop('checked')){
                    _this.parents('tr').addClass('hasCheckItem');
                }else {
                    _this.parents('tr').removeClass('hasCheckItem');
                }

            });

            return self

        },
        $body: $('body'),
        isLoad: false,
        show: function () {
            var self = this
            self.$box.removeClass('fn-hide');
            // 设置宽度
            if (self.width) {
                self.$modal.width(self.width).css('margin-left', '-' + self.$modal.outerWidth() / 2 + 'px');
            }
            return self;
        },
        hide: function () {
            this.$box.addClass('fn-hide');
            return this;
        },
        autoHide: true,
        getDate: function (index) {
            var self = this;
            if (self.isLoad) {
                //正在加载退出
                return;
            }
            self.isLoad = true;
            self.$load.removeClass('fn-hide');
            self.$list.addClass('fn-hide');
            self.$foot.addClass('fn-hide');
            // setTimeout(function() {
            //  self.isLoad = false;
            //  self.$load.addClass('fn-hide');
            //  if (res.success) {
            //      if (res.data.list.length) {
            //          self.$foot.addClass('fn-hide');
            //          self.$list.html(template.compile(self.tpl.tbody)(res.data)).removeClass('fn-hide');
            //          //page
            //          self.$pages.html(getPage(res.data, index));
            //      } else {
            //          self.$list.addClass('fn-hide');
            //          self.$foot.removeClass('fn-hide');
            //      }
            //  } else {
            //      self.$list.addClass('fn-hide');
            //      self.$foot.removeClass('fn-hide');
            //  }
            // }, 1000);
            // return;
            var _q = self.$box.find('#boxSearch').serialize(),
                _judge = /\?/.test(self.ajaxUrl),
                _symbol = _judge ? '&' : '?',
                _judge2 = /\?/.test((_q ? (_symbol + _q) : '')),
                _symbo2 = _judge2 ? '&' : '?',
                _ajaxListUrlFun = this.ajaxListUrlFun ? _symbo2 + this.ajaxListUrlFun() : '';
            //console.log(self.ajaxUrl + (_q ? (_symbol + _q) : ''));

            $.ajax({
                url: self.ajaxUrl + (_q ? (_symbol + _q) : '') + _ajaxListUrlFun,
                data: {
                    _time: (new Date()).getTime(), //无查询意义，防止浏览器缓存
                    pageSize: self.multiple ? 999 : 6,
                    pageNumber: index
                },
                dataType: 'json',
                success: function (res) {
                    // console.log(res);
                    self.isLoad = false;
                    self.$load.addClass('fn-hide');
                    if (!res.success) {
                        res.data = {
                            pageList: [],
                            pageSize: 6,
                            pageNumber: index,
                            totalCount: 0,
                            pageCount: 1,
                            pageCount: 1
                        }
                    }
                    if (res.success && res.data.pageList.length) {
                        self.$foot.addClass('fn-hide');
                        self.$list.html(template.compile(self.tpl.tbody)(res.data)).removeClass('fn-hide');
                    } else {
                        self.$list.addClass('fn-hide');
                        self.$foot.removeClass('fn-hide');
                    }
                    //page
                    self.$pages.html(getPage(res.data, index));

                    // renderCallBack
                    if (self.renderCallBack) {
                        self.renderCallBack(res, self);
                    }
                },
                error: function () {
                    self.isLoad = false;
                    self.$load.addClass('fn-hide');
                    self.$list.addClass('fn-hide');
                    self.$foot.removeClass('fn-hide');
                }
            });
        },
        resetAjaxUrl: function (ajaxUrl) {
            this.ajaxUrl = ajaxUrl;
        }
    };
    //拼接page
    /**
     * [getPage 拼接分页]
     * @param  {[type]} data  [ajax请求返回值中的data]
     * @param  {[type]} index [当前第几页]
     * @return {[type]}       [完整的分页]
     */
    function getPage(data, index) {
        //通过a标签上的page确定请求第几页数据
        var _txt = '第' + index + '页，共' + data.pageCount + '页，合计' + data.totalCount + '条&nbsp;|&nbsp;',
            _firstBtn, _preBtn, _nextBtn, _lastBtn;
        //第一页、上一页
        if (index == 1) {
            _firstBtn = '<a class="disabled" href="javascript:void(0);">首页</a>';
            _preBtn = '<a class="disabled" href="javascript:void(0);">上一页</a>';
        } else {
            _firstBtn = '<a page="1" href="javascript:void(0);">首页</a>';
            _preBtn = '<a page="' + (index - 1) + '" href="javascript:void(0);">上一页</a>';
        }
        //最后页、下一页
        if (index >= data.pageCount) {
            _nextBtn = '<a class="disabled" href="javascript:void(0);">下一页</a>';
            _lastBtn = '<a class="disabled" href="javascript:void(0);">尾页</a>';
        } else {
            _nextBtn = '<a page="' + (index + 1) + '" href="javascript:void(0);">下一页</a>';
            _lastBtn = '<a page="' + data.pageCount + '" href="javascript:void(0);">尾页</a>';
        }
        return _txt + _firstBtn + _preBtn + _nextBtn + _lastBtn + '<input class="text input fnMakeNumber" type="text" maxitem="' + data.pageCount + '"><input class="btn" type="button" value="GO">';
    }
    var _html = [
        '<div class="m-modal-box fn-hide">',
        '    <div class="m-modal-overlay"></div>',
        '    <div class="m-modal m-modal-default" id="modal">',
        '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title" id="boxTitle"></span></div>',
        '        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner">',
        '            <form class="fn-mb10" id="boxSearch"></form>',
        '            <table class="m-table m-table-list">',
        '                <thead>',
        '                    <tr id="boxThead"></tr>',
        '                </thead>',
        '                <tbody class="fn-tac fn-hide" id="boxList"></tbody>',
        '                <tfoot class="fn-hide" id="boxFoot">',
        '                    <tr>',
        '                        <td colspan="4" class="fn-tac">没有对应数据..</td>',
        '                    </tr>',
        '                </tfoot>',
        '            </table>',
        '            <div class="m-loading fn-hide" id="boxLoad"><span>加载中..</span></div>',
        '            <div class="m-pages fn-usn" id="boxPage"></div>',
        '        </div>',
        '        <div id="allChoose"></div>',
        '    </div></div></div>',
        '</div>'
    ].join('');

    module.exports = getList;
});