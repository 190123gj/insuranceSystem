define(function (require, exports, module) {

    /**
     * 基本使用方法
     * 必要html结构 只有审核按钮
     * <form class="m-audit" id="auditForm">
     *     #parse("/layout/common/audit.vm")
     * </form>
     *
     * 包含其他东西
     * <form id="auditForm">
     *     <div class="m-audit" id="btnId">
     *         #parse("/layout/common/audit.vm")
     *     </div>
     *     ## 其他审核条件 必填
     *     <textarea class="fnAuditRequired" name=""></textarea>
     *     ## 其他审核条件 非必填
     *     <textarea name=""></textarea>
     * </form>
     *
     * 审核意见非必填 在对应按钮上加上 noreason ，并且有值
     *
     * 
     */

    require('Y-msg');
    require('input.limit');
    var util = require('util');
    var $body = $('body');
    var BPMiframe = require('BPMiframe');

    /**
     * ajaxUrl 自定义各个操作的ajax的接口地址
     * btnId   自定义按钮区域的id
     *
     * 若第一个参数是string，就是btnId
     */

    function auditProject(ajaxUrl, btnId) {

        var self = this;

        var _ajaxUrl = ((typeof ajaxUrl) === 'object') ? ajaxUrl : {},
            _btnId = ((typeof ajaxUrl) === 'string') ? ajaxUrl : (btnId || 'auditForm');

        self.$form = $body.find('#auditForm');
        // 默认借口
        var urls = {
            fnAuditBtnPass: '/insurance/form/workflow/processs/donext.json', // 通过
            fnAuditBtnRefuse: '/insurance/form/workflow/processs/dorefuse.json', // 反对
            fnAuditBtnNoPass: '/insurance/form/workflow/processs/doback.json', // 不通过
            fnAuditBtnBack: '/insurance/form/workflow/processs/doGoToBack.json', // 驳回
            fnNoPassToNodeBtn: '/insurance/form/workflow/processs/doGoToBackNode.json', // 驳回到指定步骤
            fnSaveFormBtn: '', // 保存
            fnEndFormBtn: '/insurance/form/workflow/processs/doEndWorkflow.json', // 终止
            fnAssignBtn: '/insurance/form/workflow/processs/doTaskAssign.json' // 交办
        };
        self.ajaxUrl = $.extend({}, urls, _ajaxUrl);

        // 浮动
        var _$formBox = $('<div id="fnFormBox"></div>'),
            _fixedTop = $('.m-main').offset().top,
            $window = $(window),
            $auditForm = $('#' + _btnId);

        $auditForm.after(_$formBox).width(self.$form.width());

        $window.scroll(function () {

            if ($auditForm.hasClass('static')) {
                return;
            }

            if ($window.scrollTop() >= _fixedTop) {
                $auditForm.addClass('fixed');
                _$formBox.height($auditForm.outerHeight());
            } else {
                $auditForm.removeClass('fixed');
                _$formBox.height(0);
            }
        });

        // 假若没有审核按钮，就删了
        if (!!!$auditForm.find('.fnAuditBtn').length) {
            $auditForm.remove()
        }

    }

    auditProject.prototype = {
        constructor: auditProject,
        initAudit: function (obj) {
            /**
             * obj -> isPassId\auditBoxId\doPass\doBack\doNoPass
             */
            // 初始化审核相关
            var self = this;

            // 默认参数
            var options = {
                    isPassId: 'fnAuditIsPass', // 是否通过
                    auditBoxId: 'fnAuditBox' // 审核弹出层
                }
                // 请求url
            var thisAjax;
            // 公共操作
            function clickBtn(el, isPass) {
                $title.html(el.value);
                thisAjax = el.id;
                self.audit.$box.find('.fnHiddenItem').addClass('fn-hide');
                self.audit.$isPass.val(isPass);
                self.audit.$box.removeClass('fn-hide');
                self.audit.$box.removeClass('fn-hide').find('#fnAuditWorkflowVoteContent').focus();

                if (eval(isPass)) {
                    self.audit.$box.find('.fnAuditPathBox').removeClass('fn-hide');
                } else {
                    self.audit.$box.find('.fnAuditPathBox').addClass('fn-hide');
                }

                // 是否必填理由
                setAuditOpinion(el)

            }
            // 审核意见 是否必填
            function setAuditOpinion(el) {

                var NO_REASON_CLASS = (self.$form.attr('noreason') || '').split(','),
                    _set = true

                $.each(NO_REASON_CLASS, function (index, cname) {

                    if (cname && util.hasClass(el, cname.replace(/\s/g, ''))) {
                        _set = false
                    }

                })

                var $text = self.audit.$box.find('#fnAuditWorkflowVoteContent')

                if (_set) {
                    $text.addClass('fnAuditInput').parent().find('.m-required').removeClass('fn-hide');
                } else {
                    $text.removeClass('fnAuditInput').parent().find('.m-required').addClass('fn-hide');
                }

            }
            // 合并参数
            var opt = $.extend({}, options, obj);
            // 缓存、向外暴露 审核弹出框
            self.audit = {
                $box: self.$form.find('#' + opt.auditBoxId),
                $isPass: self.$form.find('#' + opt.isPassId)
            }
            var $title = self.audit.$box.find('#fnAuditTitle');
            self.$form.on('click', '#fnAuditBtnPass,#fnAuditBtnRefuse', function () {
                // 必填项
                var _isRe = true,
                    _isReEq;
                $('.fnAuditRequired').each(function (index, el) {
                    if (!!!el.value.replace(/\s/g, '')) {
                        _isRe = false;
                        _isReEq = index;
                        return false;
                    }
                });
                if (!_isRe) {
                    Y.alert('提示', '请先填写必填选择', function () {
                        $('.fnAuditRequired').eq(_isRe).focus();
                    });
                    return;
                }
                // 通过操作 带入 值到 审核意见
                // 2016.10.29 排除 提交 
                if (this.id === 'fnAuditBtnPass' && $.inArray(this.value, ['提交']) == -1) {
                    var $fnAuditWorkflowVoteContent = $('#fnAuditWorkflowVoteContent')
                    if (!!!$fnAuditWorkflowVoteContent.val()) {
                        $fnAuditWorkflowVoteContent.val(this.value)
                    }
                }
                clickBtn(this, 'true');
            }).on('click', '#fnAuditBtnNoPass', function () {
                // 不通过
                clickBtn(this, 'false');
            }).on('click', '#fnNoPassToNodeBtn', function () {
                clickBtn(this, 'false');
            }).on('click', '#fnEndFormBtn', function () {
                clickBtn(this, 'false');
            }).on('click', '#fnAuditBtnBack', function () {
                // 驳回
                clickBtn(this, 'false');
            }).on('click', '.close', function () {
                // 关闭弹出
                self.audit.$isPass.val('');
                self.audit.$box.addClass('fn-hide');
                self.audit.$box.find('.audit-textarea').val('').trigger('keyup');

                // 路径？？
                self.audit.$box.find('.fnAuditPathBox input[type="radio"]').eq(0).trigger('click')

            }).on('click', '#fnAuditSure', function () {

                $.when(self.deferred())
                    .then(function () {

                        // 执行路径分支？？？？
                        var $fnAuditPath = $('.fnAuditPath:visible');
                        if (!!$fnAuditPath.length) {
                            // 如果有分支
                            if (!!!$('.fnAuditPath:checked').length) {
                                Y.alert('提示', '请选择一条执行路径');
                                return;
                            }
                        }

                        var $fnAuditWorkflowVoteContent = $('#fnAuditWorkflowVoteContent'),
                            _text = $fnAuditWorkflowVoteContent.val(),
                            _contentMust = $fnAuditWorkflowVoteContent.hasClass('fnAuditInput');

                        // 无意义审核内容
                        var _regAllStr = /^[A-za-z\~\!\@\#\$\%\^\&\*\(\)\_\-\+\=\{\}\[\]\:\;\"\'\<\,\>\.\?\/\！\￥\…\（\）\—\—\《\，\》\。\？\、]+$/g,
                            _regAllStr2 = /^[\１\２\３\４\５\６\７\８\９\０\～\！\＠\＃\￥\％\＆\×\（\）\＋\＝\－\｛\｝\［\］\；\：\＇\＂\，\＜\．\＞\／\？]+$/g,
                            _regAllNum = /^\d+$/g,
                            _regAllSpa = /^\s+$/g,

                            _regAllQuan = /[\u3000]/g,
                            _regNumAStr = /[a-zA-Z0-9]/g,
                            _regSpcail = /[\^\.<>%&',;=?$"':#@!~\]\[{}\\/`\|]/g,
                            _regHzSyb = /[\u3002|\uff1f|\uff01|\uff0c|\u3001|\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]/g;
                        // _regAllArr = [_regAllStr, _regAllStr2, _regAllNum, _regAllSpa, _regAllQuan];

                        function toSBC(str) { //全角转换成半角
                            var result = "";
                            var len = str.length;
                            for (var i = 0; i < len; i++) {
                                var cCode = str.charCodeAt(i);
                                //全角与半角相差（除空格外）：65248（十进制）
                                cCode = (cCode >= 0xFF01 && cCode <= 0xFF5E) ? (cCode - 65248) : cCode;
                                //处理空格
                                cCode = (cCode == 0x03000) ? 0x0020 : cCode;
                                result += String.fromCharCode(cCode);
                            };
                            result = result.replace(_regNumAStr, '').replace(_regSpcail, '').replace(/\s/g, '').replace(_regHzSyb, '');
                            return result;
                        }

                        var _isRegPass = true;
                        var swicthText = toSBC(_text);
                        if (!swicthText) {
                            _isRegPass = false;
                            // return false;
                        }
                        // $.each(_regAllArr, function(i, reg) {
                        //    console.log(reg.test(swicthText))
                        //  if (reg.test(swicthText)) {
                        //      _isRegPass = false;
                        //      return false;
                        //  }
                        //
                        // });
                        // return;

                        // if (!_isRegPass) {
                        //  Y.alert('提示', '审核意见不能为纯字符、数字、空格');
                        //  return;
                        // }

                        // 必填 必须验证
                        // 非必填 有值验证 无值不验证
                        if (_contentMust || (!_contentMust && !!$fnAuditWorkflowVoteContent.val())) {


                            if (!_isRegPass) {
                                Y.alert('提示', '审核意见不能为纯字符、数字、空格');
                                return;
                            }

                        }

                        // return;
                        var _can = true;
                        self.audit.$box.find('.fnAuditInput').each(function (index, el) {
                            if (!!!el.value.replace(/\s/g, '')) {
                                _can = false;
                            }
                        });

                        if (!_can) {
                            Y.alert('提示', '请填写完必填选项');
                            return;
                        }

                        // return

                        var _isPass = eval(self.audit.$isPass.val());

                        if (opt.doPass && _isPass) {
                            // 若有通过callback
                            opt.doPass(self);
                        } else if (opt.doBack && !_isPass) {
                            // 若有驳回callback
                            opt.doBack(self);
                        } else {
                            // 提交审核
                            util.ajax({
                                url: self.ajaxUrl[thisAjax],
                                data: self.$form.serialize(),
                                success: function (res) {
                                    if (res.success) {
                                        Y.alert('提示', res.message, function () {
                                            if (self.auditCallBack) {
                                                self.auditCallBack()
                                            } else {
                                                util.doBack()
                                            }
                                        });
                                    } else {
                                        Y.alert('提示', res.message, function () {
                                            // window.top.location.href = '/userHome/mainIndex.htm'
                                            // 新开页面回到工作台
                                            // 有历史记录回到上一页
                                            util.doBack()
                                        });
                                    }
                                }
                            });
                        }

                    })
                    .fail(function (res) {
                        Y.alert('提示', res.message);
                    });

            });

            return self;
        },
        deferred: function () {

            var self = this;

            var dtd = $.Deferred();

            if (self.auditBefore) {

                self.auditBefore(dtd);

            } else {

                return dtd.resolve();

            }

            return dtd.promise();

        },
        addAuditBefore: function (fn) {
            //审核前的操作
            if (typeof fn == 'function') {
                this.auditBefore = fn;
            }
            return this;
        },
        addAuditCallback: function (fn) {
            //审核通过后，点击确定执行
            if (typeof fn == 'function') {
                this.auditCallBack = fn;
            }
            return this;
        },
        initFlowChart: function () {
            /**
             * obj -> BPMiframe\BPMCallBack
             * 
             * BPMiframe -> url\cfg cfg ->
             * title\width\height\bpmIsloginUrl\makeLoginUrl
             * 
             */
            // 初始化流程图
            var self = this;

            // 合并参数
            var opn = $
                .extend({}, {
                    // 初始化流程图
                    BPMiframe: {
                        url: '/bornbpm/platform/bpm/processRun/processImage.do?actInstId=' + $('#hddFormActInstId').val(),
                        cfg: {
                            'title': '流程图',
                            'width': 850,
                            'height': 500,
                            'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
                            'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
                        }
                    },
                    BPMCallBack: function () {}
                });

            // 初始化弹出层

            var showWorkFlowDialog = new BPMiframe(opn.BPMiframe.url,
                opn.BPMiframe.cfg);

            $body.on('click', '#fnFlowChartBtnShow', function () {
                // 弹出层回调
                if (opn.BPMCallBack) {
                    showWorkFlowDialog.init(opn.BPMCallBack);
                } else {
                    showWorkFlowDialog.init();
                }
            });

            return self;

        },
        initSaveForm: function (obj) {
            /**
             * obj -> doSave
             * 
             * 
             */
            // 初始化保存流程
            var self = this;

            // 合并参数
            var opn = $.extend({}, obj);

            $body.on('click', '#fnSaveFormBtn', function () {
                if (opn.doSave) {
                    opn.doSave();
                } else {
                    // 保存单据
                    util.ajax({
                        url: self.ajaxUrl.fnSaveFormBtn,
                        data: self.$form.serialize(),
                        success: function (res) {
                            if (res.success) {
                                //
                            } else {
                                Y.alert('提示', res.message);
                            }
                        }
                    });
                }
            });

            return self;

        },
        initPrint: function (url) {
            // 初始化打印
            var self = this;

            if (url) {
                $body.on('click', '#fnPrintBtn', function () {
                    window.top.open(url);
                });
            }

            return self;
        },
        initAssign: function () {
            var self = this;

            // 默认参数
            var options = {
                assignBoxId: 'fnAssignBox' // 交办设置弹出层
            }

            // 合并参数
            var opn = $.extend({}, options, {
                // 初始化流程图
                BPMiframe: {
                    url: '/bornbpm/platform/system/sysUser/dialog.do?isSingle=true',
                    cfg: {
                        'title': '选择交办用户',
                        'width': 850,
                        'height': 500,
                        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
                        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
                    }
                },
                BPMCallBack: function (relObj) {
                    for (var i = 0; i < relObj.userIds.length; i++) {

                        $('#fnAssignManName').val(relObj.fullnames);
                        $('#fnAssignManCode').val(relObj.userIds);
                    }
                }
            });

            self.assign = {
                $box: $body.find('#' + opn.assignBoxId)
            }

            $body.on('click', '#fnAssignBtn', function () {
                self.assign.$box.removeClass('fn-hide');
            });

            self.assign.$box.on('click', '.close', function () {
                self.assign.$box.addClass('fn-hide');
            }).on('click', '#fnAssignSure', function () {

                $.when(self.deferred())
                    .then(function () {

                        var _can = true;
                        self.assign.$box.find('.fnAuditInput').each(function (index, el) {
                            if (!!!el.value.replace(/\s/g, '')) {
                                _can = false;
                            }
                        });

                        if (!_can) {
                            Y.alert('提示', '请填写完必填选项');
                            return;
                        }

                        // 提交审核
                        util.ajax({
                            url: self.ajaxUrl['fnAssignBtn'],
                            data: self.$form.serialize(),
                            success: function (res) {
                                if (res.success) {
                                    Y.alert('提示', res.message, function () {
                                        if (self.auditCallBack) {
                                            self.auditCallBack()
                                        } else {
                                            util.doBack()
                                        }
                                    });
                                } else {
                                    Y.alert('提示', res.message, function () {
                                        // window.top.location.href = '/userHome/mainIndex.htm'
                                        // 新开页面回到工作台
                                        // 有历史记录回到上一页
                                        util.doBack()
                                    });
                                }
                            }
                        });

                    })
                    .fail(function (res) {
                        Y.alert('提示', res.message);
                    });

            });

            // 初始化弹出层
            if (opn.BPMiframe && opn.BPMiframe.url) {

                var showChooseManDialog = new BPMiframe(opn.BPMiframe.url, opn.BPMiframe.cfg);

                $body.on('click', '#fnAssignChoose', function () {
                    // 弹出层回调
                    if (opn.BPMCallBack) {
                        showChooseManDialog.init(opn.BPMCallBack);
                    } else {
                        showChooseManDialog.init();
                    }
                });
            }

            return self;
        }
    }

    /**
     * 返回来源页面
     */
    // function doBack() {

    //  // 新开页面回到工作台
    //  // 有历史记录回到上一页
    //  // 通过请求页面是来自 /projectMg/index.htm? 判断是否返回，还是回到首页

    //  var isBack = ((/(\/customerMg\/index\.htm|\/projectMg\/index\.htm|\/assetMg\/index\.htm|\/fundMg\/index\.htm|\/reportMg\/index\.htm)/g).test(document.referrer)) ? false : true;

    //  if (isBack) {
    //      window.location.href = document.referrer;
    //  } else {
    //      window.top.location.href = '/';
    //  }

    // }

    module.exports = auditProject;

});