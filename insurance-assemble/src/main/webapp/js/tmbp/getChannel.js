define(function (require, exports, module) {

    var util = require('util');

    //获取授信种类，返回弹窗
    function getChannel(btn) {
        // btn name code beforeQuery
        this.btn = btn;
    }

    getChannel.prototype = {
        constructor: getChannel,
        init: function (obj) {
            var self = this;

            for (var k in obj) {
                self[k] = obj[k];
            }

            self.$btn = $body.find(self.btn);

            self.$box = $(boxHtml);

            self.$box.on('click', 'dt', function () {
                var _this = $(this);
                _this.parent().find('dt,dd').removeClass('active');
                _this.addClass('active').next('dd').addClass('active');
            }).on('click', '.channel-dl a', function () {
                //赋值操作
                self.hide()
                if (self.$ccode) {
                    self.$ccode.val(this.getAttribute('ccode')).valid();
                }
                if (self.$cname) {
                    self.$cname.val(this.getAttribute('cname')).valid();
                }

                if (self.callback) {
                    self.callback($(this))
                }

            }).on('click', '.close', function () {
                self.hide()
            });

            self.$btn.on('click', function () {
                var _this = $(this);
                if (_this.hasClass('ing')) {
                    return;
                }
                _this.addClass('ing');
                if (self.beforeQuery && !self.beforeQuery()) {
                    self.$btn.removeClass('ing');
                    return;
                }
                self.query();
            });

            $body.append(self.$box);

            return self;
        },
        resetCode: function ($code) {
            this.$ccode = $code;
        },
        resetName: function ($name) {
            this.$cname = $name;
        },
        hide: function () {
            var self = this
            this.$box.find('.channel-box').animate({
                scrollTop: 0
            }, 10, function () {
                self.$box.addClass('fn-hide')
            })

        },
        query: function () {
            var self = this;

            util.ajax({
                url: '/baseDataLoad/allChannel.json',
                success: function (res) {

                    if (res.success) {

                        var list = res.data;

                        var _html = '',
                            _subhtml = '';
                        for (var i = 0; i < list.length; i++) {
                            if (list[i].channelList && list[i].channelList) {
                                _subhtml = '';
                                for (var j = 0; j < list[i].channelList.length; j++) {
                                    _subhtml += '<tr><td width="240" title="' + list[i].channelList[j].channelName + '">' + util.subStr(list[i].channelList[j].channelName, 10) + '</td><td width="130" title="' + list[i].channelList[j].creditAmount + '元">' + util.subStr(list[i].channelList[j].creditAmount, 10) + '</td><td width="64"><a channetypecode="' + list[i].code + '" channetypename="' + list[i].name + '" channelcode="' + list[i].channelList[j].channelCode + '" cname="' + list[i].channelList[j].channelName + '" ccode="' + list[i].channelList[j].channelId + '" href="javascript:void(0);">[ 选择 ]</a></td></tr>';
                                }
                            } else {
                                _subhtml = '';
                            }
                            _html += '<dt>' + list[i].name + '</dt><dd><table class="channel-table"><tbody>' + _subhtml + '</tbody></table></dd>';
                        }

                        self.$box.find('.channel-dl').html(_html).find('dt:first').addClass('active').end().find('dd:first').addClass('active');
                        self.$box.removeClass('fn-hide');
                        self.$btn.removeClass('ing');

                    }

                }
            });

            //查询
            // setTimeout(function() {
            //  if (res.success) {
            //      var _html = '',
            //          _subhtml = '';
            //      for (var i = 0; i < res.data.list.length; i++) {
            //          if (res.data.list[i].subclass && res.data.list[i].subclass) {
            //              _subhtml = '';
            //              for (var j = 0; j < res.data.list[i].subclass.length; j++) {
            //                  _subhtml += '<tr><td width="240">进出口银行</td><td width="130">1,000,000,0</td><td width="64"><a href="javascript:void(0);">选择</a></td></tr>';
            //              }
            //          } else {
            //              _subhtml = '';
            //          }
            //          _html += '<dt>大的标题</dt><dd><table class="channel-table"><tbody>' + _subhtml + '</tbody></table></dd>';
            //      }
            //      self.$box.find('.channel-dl').html(_html).find('dt:first').addClass('active').end().find('dd:first').addClass('active');
            //      self.$box.removeClass('fn-hide');
            //      self.$btn.removeClass('ing');
            //  }
            // }, 500);
        },
        setQueryData: function (obj) {
            this.queryData = $.extend({}, this.queryData, obj);
            return this;
        }
    }

    var $body = $('body');

    var boxHtml = ['<div class="m-modal-box fn-hide">',
        '    <div class="m-modal-overlay"></div>',
        '    <div class="m-modal m-modal-default">',
        '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title">选择渠道</span></div>',
        // '        <div class="apply-org-body channel">',
        '        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner channel">',
        '            <table class="channel-table th"><thead><tr><th width="200px">渠道分类</th><th width="240px">渠道名称</th><th width="130px">授信额度<br>(元)</th><th>操作</th></tr></thead></table>',
        '            <div class="channel-box">',
        '                <dl class="channel-dl"></dl>',
        '                <div class="channel-dl-line"></div>',
        '            </div>',
        '        </div></div></div>',
        '    </div>',
        '</div>'
    ].join('');


    module.exports = getChannel;

});