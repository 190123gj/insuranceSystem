define(function (require, exports, module) {


    /**
     * 使用方法
     * js
     * (new(require('zyw/publicOPN'))()).addOPN([{
     *      name: '普通链接',
     *      url: 'dfsd'
     *  },{
     *      name: '操作',
     *      alias: 'yourAlias',
     *      event: function(){}
     *  }]).init().doRender();
     *
     * html
     * <input type="hidden" id="projectDetailId" value="1"> 开启查看项目详情
     * 
     */

    require('Y-msg');
    var $body = $('body');
    var BPMiframe = require('BPMiframe');
    var util = require('util');
    var template = require('arttemplate');

    function publicOPN() {

        var self = this;

        self.$box = $('<div class="ui-opn fnTransitionAll fn-usn" id="fnMOPN"></div>');
        self.queue = [];
        self.callbacks = {};

        $('body').append(self.$box);

    }

    publicOPN.prototype = {
        constructor: publicOPN,
        init: function () {
            var self = this;
            // 初始化 
            // 是否添加通用操作
            // 查看审批流程、查看审批记录、查看修改记录、查看项目批复、查看合同、查看项目详情、打印页面、编辑表单
            // 是否开启对应操作，需在html写入对应的空间,例如
            // <input type="hidden" id="projectDetailId" value="1"> 查看项目详情 需要 项目id
            // <input type="hidden" id="contractListId" value="1"> 查看合同 需要项目code
            // <input type="hidden" id="projectApprovalId" value="1"> 查看项目批复
            // <input type="hidden" id="amendRecordId" value="1"> 查看修改记录
            // <input type="hidden" id="endTaskFormId"  sysname="sysName" name="endTaskFormId" value="$!formId" /> 终止流程
            // 
            // 查看审批流程
            var _hddFormActDefId = $('#hddFooterFormActDefId').val(),
                _hddUserName = $('#hddUserName').val(),
                showWorkFlowOPN = {
                    //初始化流程图
                    BPMiframe: {
                        url: '/bornbpm/platform/bpm/processRun/processImage.do?actInstId=' + _hddFormActDefId,
                        cfg: {
                            'title': '审批流程',
                            'width': $(window).width() - 20,
                            'height': $(window).height() - 20,
                            'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + _hddUserName,
                            'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
                        }
                    }
                };
            if (_hddFormActDefId && _hddUserName) {
                //如果有这两个值，添加进来
                self.showWorkFlowDialog = new BPMiframe(showWorkFlowOPN.BPMiframe.url, showWorkFlowOPN.BPMiframe.cfg);
                self.queue.push({
                    name: '查看审批流程',
                    alias: 'showWorkFlowDialog',
                    event: function () {
                        self.showWorkFlowDialog.init();
                    }
                });
            }
            // 查看审批记录
            var _examineRecordId = $('#examineRecordId').val();
            if (_examineRecordId) {

                $body.on('click', '.fnAuditMoreExamineRecord', function () {
                    var _txt = $(this).attr('more') || '暂无意见';
                    $('body').Y('Window', {
                        content: creatMoreApprovalOpinion(_txt.replace(/\s+/g, '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;')),
                        contentClone: true,
                        key: 'modalwnd',
                        title: '审批意见'
                    });
                });

                self.queue.push({
                    name: '查看审批记录',
                    alias: 'viewExamineRecord',
                    event: function () {
                        // 如果没有，请求数据
                        util.ajax({
                            url: '/baseDataLoad/loadWorkflowProcessList.json',
                            data: {
                                actInstId: _examineRecordId
                            },
                            success: function (res) {
                                if (res.success) {
                                    self.examineRecord = creatExamineRecord({
                                        list: res.data.list
                                    });
                                    //弹出层
                                    $('body').Y('Window', {
                                        content: self.examineRecord,
                                        simple: true,
                                        modal: false,
                                        closeEle: '.close'
                                    });
                                } else {
                                    Y.alert('提示', res.message);
                                }
                            }
                        });
                    }
                });
            }

            //查看修改记录
            // var _amendRecordId = $('#amendRecordId').val();
            // if (_amendRecordId) {
            //  self.queue.push({
            //      name: '查看修改记录',
            //      alias: 'viewAmendRecord',
            //      event: function() {
            //          // 如果没有，请求数据
            //          if (!self.amendRecord) {
            //              util.ajax({
            //                  url: 'xcsadfsdf/fsdf.do?formId=' + _amendRecordId,
            //                  async: false,
            //                  success: function(res) {
            //                      if (res.success) {
            //                          self.amendRecord = creatAmendRecord({
            //                              list: res.data.list
            //                          });
            //                      } else {
            //                          Y.alert('提示', res.message);
            //                      }
            //                  }
            //              });
            //          }
            //          //弹出层
            //          $('body').Y('Window', {
            //              content: self.amendRecord,
            //              simple: true,
            //              modal: false,
            //              closeEle: '.close'
            //          });
            //      }
            //  });
            // }

            //查看项目批复
            var _projectApprovalId = $('#projectApprovalId').val();
            if (_projectApprovalId) {
                self.queue.push({
                    name: '查看项目批复',
                    url: '/projectMg/meetingMg/summary/approval.htm?spCode=' + encodeURIComponent(_projectApprovalId)
                });
                // self.queue.push({
                //  name: '查看项目批复',
                //  alias: 'lookProjectReply',
                //  event: function() {
                //      util.openDirect('/projectMg/index.htm', '/projectMg/meetingMg/summary/approval.htm?spCode=' + _projectApprovalId)
                //  }
                // });
                //              self.queue.push({
                //                  name: '查看项目批复',
                //                  alias: 'viewProjectApproval',
                //                  event: function() {
                //                      // 如果没有，请求数据
                //                      if (!self.projectApproval) {
                //                          util.ajax({
                //                              url: 'xcsadfsdf/fsdf.do?ddd=' + _projectApprovalId,
                //                              async: false,
                //                              success: function(res) {
                //                                  if (res.success) {
                //                                      self.projectApproval = creatProjectApproval(res.data);
                //                                  } else {
                //                                      Y.alert('提示', res.message);
                //                                  }
                //                              }
                //                          });
                //                      }
                //                      //弹出层
                //                      $('body').Y('Window', {
                //                          content: self.projectApproval,
                //                          simple: true,
                //                          modal: false,
                //                          closeEle: '.close'
                //                      });
                //                  }
                //              });
            }

            //查看合同
            var _contractListId = $('#contractListId').val(); //项目code
            if (_contractListId) {
                self.queue.push({
                    name: '查看项目合同',
                    alias: 'viewContractList',
                    event: function () {
                        // 如果没有，请求数据
                        util.ajax({
                            url: '/projectMg/contract/contractChoose.htm?projectCode=' + _contractListId,
                            data: {
                                pageSize: 9999
                            },
                            success: function (res) {
                                if (res.success) {
                                    self.contractList = creatContractList({
                                        list: res.data.pageList
                                    });
                                    //弹出层
                                    $('body').Y('Window', {
                                        content: self.contractList,
                                        simple: true,
                                        modal: false,
                                        closeEle: '.close'
                                    });
                                } else {
                                    Y.alert('提示', res.message);
                                }
                            }
                        });
                    }
                });
            }

            //查看项目详情
            var _projectDetailId = $('#projectDetailId').val();
            if (_projectDetailId) {
                // self.queue.push({
                //  name: '查看项目详情',
                //  alias: 'lookProjectInfo',
                //  event: function() {
                //      util.openDirect('/projectMg/index.htm', '/projectMg/viewProjectAllMessage.htm?projectCode=' + _projectDetailId)
                //  }
                // });
                self.queue.push({
                    name: '查看项目详情',
                    url: '/projectMg/viewProjectAllMessage.htm?projectCode=' + _projectDetailId
                });
            }

            //打印页面
            var _printUrl = document.getElementById('printUrl');

            if (_printUrl && _printUrl.value) {

                self.queue.push({
                    name: '打印页面',
                    url: _printUrl.value
                });

            }

            //编辑表单
            var _editUrl = document.getElementById('editUrl');

            if (_editUrl && _editUrl.value) {

                self.queue.push({
                    name: '编辑',
                    alias: 'editForm',
                    event: function () {
                        window.location.href = _editUrl.value;
                    }
                });

            }

            //作废表单
            var _endTaskFormId = $("#endTaskFormId");
            if (_endTaskFormId && _endTaskFormId.val()) {
                self.queue.push({
                    name: '作废',
                    alias: 'endForm',
                    event: function () {
                        Y.confirm('提示', '确定作废该表单？', function (opn) {
                            if (opn == 'yes') {
                                util.ajax({
                                    url: '/projectMg/form/end.htm',
                                    data: {
                                        'formId': _endTaskFormId.val(),
                                        '_SYSNAME': _endTaskFormId.attr('sysname')
                                    },
                                    success: function (res) {
                                        Y.alert(res.success ? '操作成功' : '操作失败', res.message, function () {
                                            if (res.success) {
                                                //跳转回去
                                                // window.location.href = _endTaskFormId.attr('redirect');
                                                util.doBack();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });

            }

            // 返回顶部，提取出来

            $('body').append('<ul class="ui-tool"><li><a style="display: none;" class="fn-hide feed" id="fnFooterGoBack" href="javascript: void(0);">返回<br>顶部</a></li></ul>');

            var $win = $(window),
                winH = $win.height(),
                $nFooterGoBack = $('#fnFooterGoBack');

            $nFooterGoBack.on('click', function () {

                $('body,html').animate({
                    scrollTop: 0
                }, 600);

            });

            $win.scroll(function () {

                if ($win.scrollTop() > (winH / 3 * 2)) {

                    if ($nFooterGoBack.hasClass('fn-hide')) {
                        $nFooterGoBack.removeClass('fn-hide').show(600);
                    }

                } else {

                    if (!$nFooterGoBack.hasClass('fn-hide')) {
                        $nFooterGoBack.addClass('fn-hide').hide(600);
                    }

                }

            });

            // 点击事件
            self.bingEvent();

            return self;
        },
        addOPN: function (arr) {

            //添加其他操作到队列

            var self = this;
            // 合并数组
            self.queue = arr.concat(self.queue);

            return self;

        },
        doRender: function () {

            //渲染页面，并且绑定事件

            var self = this,
                _html = '';

            // 如果队列中没有东西，退出渲染
            // 返回顶部踢出去了
            if (self.queue.length == 0) {
                return;
            }

            // 添加操作、缓存callback
            for (var i = 0; i < self.queue.length; i++) {
                _html += self.creatLi(self.queue[i]);
            }


            self.$box.html(creatHtml(_html));

        },
        creatLi: function (obj) {
            var self = this,
                _html;
            // 如果有url 添加utl
            // 如果没有，添加回调
            if (obj.url) {
                _html = '<li><a href="' + obj.url + '">' + obj.name + '</a></li>';
            } else {
                _html = '<li><a href="javascript:void(0);" trigger="' + obj.alias + '">' + obj.name + '</a></li>';
                self.callbacks[obj.alias] = obj.event;
            }

            return _html;

        },
        bingEvent: function () {

            //事件绑定

            var self = this;

            self.$box.on('click', '.title', function () {
                //打开或关闭更多操作
                self.$box.toggleClass('active');

            }).on('click', 'a:not(.ing)', function () {

                var _this = $(this),
                    _trigger = _this.attr('trigger');

                if (!!!_trigger) {
                    return;
                }

                self.callbacks[_trigger]();

                _this.addClass('ing')

                setTimeout(function () {
                    _this.removeClass('ing')
                }, 1000)

            });

            self.resize();
        },
        resize: function () {

            //调整浮动的位置

            var self = this,
                MAXH = $(window).height() - 100,
                MINH = self.$box.find('.title').outerHeight(),
                ULH = self.$box.outerHeight();

            if (ULH <= MINH) {
                //如果没有撑开最小高度，不许调整
                return;
            }

            //理想高度
            var _idealH = (ULH <= MAXH) ? ULH : MAXH;

            //限制高度
            self.$box.outerHeight(_idealH).css('margin-top', -(_idealH / 2));
            self.$box.find('.ul').outerHeight(_idealH);

        },
        indexOf: function (t) {

            var _index = -1;

            $.each(this.queue, function (index, obj) {
                if (t == obj.name) {
                    _index = index;
                    return false;
                }
            });

            return _index;

        },
        removeOPN: function (t) {

            var _i;

            if (typeof t == 'number') {
                _i = t;
            } else {
                _i = this.indexOf(t);
            }

            if (_i > -1) {
                this.queue.splice(_i, 1);
            }

            return this;

        },
        remove: function () {
            this.$box.html('');
            return this;
        }
    }

    //创建更多操作的所有html
    function creatHtml(str) {
        // return '<div class="title fn-usn">其<br>他<br>操<br>作</div><ul class="ul">' + str + '<li><a href="javascript:scrollTo(0,0);">返回顶部</a></li></ul>'
        return '<div class="title fn-usn">其<br>他<br>操<br>作</div><ul class="ul">' + str + '</ul>'
    }
    //创建 查看修改记录 html
    /**
     * 创建 查看修改记录 所需要的html
     * @param  {[type]} obj [description]
     * @return {[type]}     [description]
     */
    function creatAmendRecord(obj) {

        var _tpl = ['<div class="m-modal-box">',
            '    <div class="m-modal-overlay"></div>',
            '    <div class="m-modal apply-org">',
            '        <div class="apply-org-hd"><span class="fn-usn fn-csp fn-right close">&times;</span>查看修改记录</div>',
            '        <div class="apply-org-form-in">',
            '            <div class="fn-blank10"></div>',
            '            <table class="m-table fn-tac">',
            '                <thead>',
            '                    <tr>',
            '                        <th>修改时间</th>',
            '                        <th>修改人</th>',
            '                        <th>修改字段</th>',
            '                        <th>原值</th>',
            '                        <th>新值</th>',
            '                    </tr>',
            '                </thead>',
            '                <tbody>',
            '                    {{if !list.length}}',
            '                    <tr>',
            '                        <td colspan="5">暂无记录</td>',
            '                    </tr>',
            '                    {{/if}}',
            '                    {{each list as item i}}',
            '                        <tr>',
            '                            <td>{{item.time}}</td>',
            '                            <td>{{item.time}}</td>',
            '                            <td>{{item.time}}</td>',
            '                            <td>{{item.time}}</td>',
            '                            <td>{{item.time}}</td>',
            '                        </tr>',
            '                    {{/each}}',
            '                </tbody>',
            '            </table>',
            '            <div class="fn-blank10"></div>',
            '        </div>',
            '        <div class="apply-org-form-bt fn-tac">',
            '            <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray close">关闭</a>',
            '        </div>',
            '    </div>',
            '</div>'
        ].join('');

        return template.compile(_tpl)(obj);

    }

    //创建 查看项目批复 html
    /**
     * 创建 查看项目批复 所需要的html
     * 模板变量参数参考返回数据的修改
     * @param  {[type]} obj [description]
     * @return {[type]}     [description]
     */
    function creatProjectApproval(obj) {

        var _tpl = ['<div class="m-modal-box">',
            '    <div class="m-modal-overlay"></div>',
            '    <div class="m-modal apply-org">',
            '        <div class="apply-org-hd"><span class="fn-usn fn-csp fn-right close">&times;</span>查看项目批复</div>',
            '        <div class="apply-org-form-in">',
            '            <div class="m-item">',
            '                <label class="m-label">批复编号：</label>',
            '                {{numner}}',
            '            </div>',
            '            <div class="m-item">',
            '                <label class="m-label">项目编号：</label>',
            '                {{numner}}',
            '            </div>',
            '            <div class="m-item">',
            '                <label class="m-label">客户名称：</label>',
            '                {{name}}',
            '            </div>',
            '            <div class="m-item">',
            '                <label class="m-label">授信额度：</label>',
            '                {{name}}万元',
            '            </div>',
            '            <div class="m-item">',
            '                <label class="m-label">授信期限：</label>',
            '                {{name}}{{name}}',
            '            </div>',
            '            <div class="m-item">',
            '                <label class="m-label">费（利）率：</label>',
            '                {{name}}{{name}}',
            '            </div>',
            '            <div class="m-item">',
            '                <label class="m-label">授信条件：</label>',
            '                <table class="m-table m-table-list fn-tac">',
            '                    <thead>',
            '                        <tr>',
            '                            <th width="50">序号</th>',
            '                            <th>授信条件</th>',
            '                        </tr>',
            '                    </thead>',
            '                    <tbody>',
            '                        {{if !list.length}}',
            '                            <tr>',
            '                                <td colspan="2">暂无记录</td>',
            '                            </tr>',
            '                        {{/if}}',
            '                        {{each list as item i}}',
            '                            <tr>',
            '                                <td>{{i+1}}</td>',
            '                                <td class="fn-tal">{{item.ctt}}</td>',
            '                            </tr>',
            '                        {{/each}}',
            '                    </tbody>',
            '                </table>',
            '            </div>',
            '        </div>',
            '        <div class="apply-org-form-bt fn-tac">',
            '            <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray close">关闭</a>',
            '        </div>',
            '    </div>',
            '</div>'
        ].join('');

        return template.compile(_tpl)(obj);

    }

    //创建 查看合同 html
    function creatContractList(obj) {

        var _tpl = ['<div class="m-modal-box">',
            '    <div class="m-modal-overlay"></div>',
            '    <div class="m-modal apply-org">',
            '        <div class="apply-org-hd"><span class="fn-usn fn-csp fn-right close">&times;</span>查看合同</div>',
            '        <div class="apply-org-form-in">',
            '            <div class="fn-blank10"></div>',
            '            <table class="m-table fn-tac">',
            '                <thead>',
            '                    <tr>',
            '                        <th width="50">序号</th>',
            '                        <th>合同编号</th>',
            '                        <th width="150">合同名称</th>',
            '                        <th width="50">操作</th>',
            '                    </tr>',
            '                </thead>',
            '                <tbody>',
            '                    {{if !list.length}}',
            '                    <tr>',
            '                        <td colspan="4">暂无记录</td>',
            '                    </tr>',
            '                    {{/if}}',
            '                    {{each list as item i}}',
            '                        <tr>',
            '                            <td>{{i+1}}</td>',
            '                            <td>{{item.contractCode}}</td>',
            '                            <td>{{item.contractName}}</td>',
            '                            <td>{{if item.url}}<a href="{{item.url}}" class="fnOPNNewOpen" {{if item.contractType != "STANDARD"}} download {{/if}} _blank="IS">查看</a>{{/if}}</td>',
            // '                            <td><a href="{{item.url}}" class="fnOPNNewOpen" {{if item.contractType != "STANDARD"}} download {{/if}} _blank="IS">查看</a></td>',
            // '                            <td><a href="{{item.url}}" mainurl="/projectMg/index.htm" class="fnOPNNewOpen" _blank="{{(item.contractType == \'STANDARD\')?\'IS\':\'NO\'}}">查看</a></td>',
            '                        </tr>',
            '                    {{/each}}',
            '                </tbody>',
            '            </table>',
            '            <div class="fn-blank10"></div>',
            '        </div>',
            '        <div class="apply-org-form-bt fn-tac">',
            '            <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray close">关闭</a>',
            '        </div>',
            '    </div>',
            '</div>'
        ].join('');

        return template.compile(_tpl)(obj);

    }

    //创建 查看审批记录 html
    function creatExamineRecord(obj) {

        var _tpl = ['<div class="m-modal-box">',
            '    <div class="m-modal-overlay"></div>',
            '    <div class="m-modal apply-org">',
            '        <div class="apply-org-hd"><span class="fn-usn fn-csp fn-right close">&times;</span>查看审批记录</div>',
            '        <div class="apply-org-form-in">',
            '            <div class="fn-blank10"></div>',
            '            <table class="m-table fn-tac">',
            '                <thead>',
            '                    <tr>',
            '                        <th width="30">序号</th>',
            '                        <th>任务名称</th>',
            '                        <th width="80">开始时间</th>',
            '                        <th width="80">结束时间</th>',
            '                        <th width="80">执行人</th>',
            '                        <th width="100">审批意见</th>',
            '                        <th width="80">审批状态</th>',
            '                    </tr>',
            '                </thead>',
            '                <tbody>',
            '                    {{if !list.length}}',
            '                    <tr>',
            '                        <td colspan="7">暂无记录</td>',
            '                    </tr>',
            '                    {{/if}}',
            '                    {{each list as item i}}',
            '                        <tr>',
            '                            <td>{{i+1}}</td>',
            '                            <td>{{item.taskName}}</td>',
            '                            <td>{{item.startTime}}</td>',
            '                            <td>{{item.endTime}}</td>',
            '                            <td>{{item.exeFullname}}</td>',
            '                            <td class="fn-csp fnAuditMoreExamineRecord" more="{{item.opinion}}">{{=substr(item.opinion,12)}}</td>',
            '                            <td>{{item.checkStatusMessage}}</td>',
            '                        </tr>',
            '                    {{/each}}',
            '                </tbody>',
            '            </table>',
            '            <div class="fn-blank10"></div>',
            '        </div>',
            '        <div class="apply-org-form-bt fn-tac">',
            '            <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray close">关闭</a>',
            '        </div>',
            '    </div>',
            '</div>'
        ].join('');

        return template.compile(_tpl)(obj);

    }

    // 审批意见
    function creatMoreApprovalOpinion(str) {

        return '<div class="apply-org-form-in fn-w500">' + str + '<div class="fn-blank20"></div></div>';

    }

    //template hepl
    template.helper('getTime', function (timestamp) {
        if (!timestamp) {
            return '';
        }
        var _time = new Date(timestamp),
            _date = _time.getFullYear() + '-' + (_time.getMonth() + 1) + '-' + _time.getDate() + ' ' + _time.getHours() + ':' + _time.getMinutes() + ':' + _time.getSeconds();
        return _date;
    });

    template.helper('substr', function (str, length) {
        if (!!!str) {
            return;
        }
        var _str = str.replace(/\s+/g, '');
        if (_str.length > length) {
            _str = _str.substr(0, length) + '...';
        }
        return _str;
    });

    // 针对新打开窗口绑定事件
    $('body').on('click', '.fnOPNNewOpen', function (e) {
        e.preventDefault();
        //是否直接新窗口打开
        if (this.getAttribute('_blank') == 'IS') {
            window.open(this.href);
            return;
        }
        util.openDirect(this.getAttribute('mainurl'), this.href);
    });

    module.exports = publicOPN;

});