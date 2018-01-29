define(function (require, exports, module) {

    var template = require('arttemplate');
    var util = require('util');

    //------ 查看项目情况 start
    /**
     * 查看项目详情 首先把所有的事件集合预计好 eventList
     *
     * 非业务进来不能查看到
     *
     * 点击列表中的 td.fnOpenInfo 获取当前项目的信息，例如 项目类型、项目阶段
     *
     * 不同的项目，有不同的事件；不同的项目阶段，激活不同事件
     *
     * 根据项目类型分类添加 项目事件、项目激活规则，项目阶段不同，激活的事件个数不同
     *
     * 更具激活规则、项目阶段信息，汇总激活事件
     *
     * 通过对比 事件名称 是否激活事件
     *  
     * 
     * @type {Boolean}
     */

    template.helper('getState', function (code) {
        switch (code) {
            case 0:
                return 'cannotdo';
                break;
            case 1:
                return 'cando active';
                break;
            case 2:
                return 'repeat active';
                break;
            case 3:
                return 'complete';
                break;
        }
    });

    var $fnMyWorkbenchFlow = $('#fnMyWorkbenchFlow'),
        showFlowMax = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

    // 事件集合
    var eventList = [{
        name: '合同申请',
        url: '/projectMg/contract/list.htm',
        code: 'contract',
        sub: [{
            name: '申请',
            url: '/projectMg/contract/list.htm',
            code: 'contract_apply'
        }, {
            name: '用印',
            url: '/projectMg/contract/list.htm',
            code: 'contract_stamp',
            target: true
        }, {
            name: '回传',
            url: '/projectMg/contract/list.htm',
            code: 'contract_receipt',
            target: true
        }]
    }, {
        name: '授信条件落实',
        url: '/projectMg/projectCreditCondition/list.htm',
        code: 'creditConfirm'
    }, {
        name: '费用收取通知',
        url: '/projectMg/chargeNotification/list.htm',
        code: 'chargeNotice'
    }, {
        name: '放用款申请',
        url: '/projectMg/loanUseApply/list.htm',
        code: 'loanUse',
        sub: [{
            name: '申请',
            url: '/projectMg/loanUseApply/list.htm',
            code: 'loanUse_apply',
            target: true
        }, {
            name: '回执',
            url: '/projectMg/loanUseApply/list.htm',
            code: 'loanUse_receipt',
            target: true
        }]
    }, {
        name: '保后检查报告',
        url: '/projectMg/afterwardsCheck/list.htm',
        code: 'afterCheck'
    }, {
        name: '项目风险等级评定',
        url: '/projectMg/riskLevel/list.htm',
        feed: false, // 显示两行
        code: 'riskLevel'
    }, {
        name: '解保申请',
        url: '/projectMg/counterGuarantee/list.htm',
        code: 'release'
    }, {
        name: '发售信息维护',
        url: '/projectMg/projectIssueInformation/projectIssueInformationList.htm',
        code: 'sellConfirm'
    }, {
        name: '到期通知',
        url: '/projectMg/expireProject/list.htm',
        code: 'expireNotice'
    }, {
        name: '立项申请单',
        url: '/projectMg/setUp/list.htm',
        code: 'setup'
    }, {
        name: '尽职调查报告',
        url: '/projectMg/investigation/list.htm',
        code: 'investigation'
    }, {
        name: '会议评审',
        url: '/projectMg/approvalList.htm',
        code: 'council'
    }, {
        name: '会议评审',
        url: '/projectMg/approvalList.htm',
        code: 'council'
    }, {
        name: '征信查询',
        url: '/projectMg/creditRefrerenceApply/list.htm',
        code: 'crediInfo'
    }, {
        name: '资料归档',
        url: '/projectMg/file/list.htm',
        code: 'fileInput'
    }, {
        name: '风险处理',
        url: '/projectMg/riskWarning/list.htm',
        code: 'riskHandle'
    }, {
        name: '风险上会申报',
        url: '/projectMg/riskHandle/list.htm',
        code: 'riskReport'
    }, {
        name: '文书申请',
        url: '/projectMg/contract/list.htm?applyType=PROJECT_WRIT',
        code: 'paper'
    }, {
        name: '函、通知书申请',
        url: '/projectMg/contract/list.htm?applyType=PROJECT_LETTER',
        code: 'letter'
    }, {
        name: '资金划付',
        url: '/projectMg/fCapitalAppropriationApply/list.htm',
        code: 'capital',
        target: true
    }, {
        name: '项目追偿',
        url: '/projectMg/recovery/projectRecoveryList.htm',
        code: 'recovery',
        target: true
    }];

    /**
     * 获取事件列表
     * 根据不同的状态、行列设置不同的事件
     * @param  {[type]} arr [需要的事件名称 根据 eventList 筛选]
     * @return {[type]}     [description]
     */
    function getEventList(arr) {

        var _arr = [];

        $.each(arr, function (index, el) {

            $.each(eventList, function (i, o) {
                if (o.name == el) {
                    _arr.push(o);
                    return false;
                }
            });

        });

        return _arr;

    }

    function getThisFlow(res) {

        var tplObj = {};

        tplObj.list1 = getEventList(['立项申请单', '尽职调查报告', '会议评审']);

        //项目类型 担保/委贷==DB，承销==CX，诉讼==SB.债券担保==FZ
        switch (res.templete) {
            case 'DB':
                tplObj.type = 'DBWD';
                tplObj.list2 = getEventList(['合同申请', '授信条件落实', '费用收取通知']);
                tplObj.list3 = getEventList(['放用款申请', '保后检查报告', '项目风险等级评定', '解保申请']);
                tplObj.list5 = getEventList(['资金划付', '项目追偿']);
                tplObj.branch = {
                    left: 0,
                    startWidth: 182,
                    endWidth: 195
                };
                break;
            case 'CX':
                tplObj.type = 'CX';
                tplObj.list2 = getEventList(['合同申请', '费用收取通知', '发售信息维护']);
                tplObj.list3 = getEventList(['到期通知']);
                tplObj.list5 = []
                break;
            case 'SB':
                tplObj.type = 'SS';
                tplObj.list2 = getEventList(['合同申请']);
                tplObj.list3 = getEventList(['费用收取通知', '保后检查报告', '解保申请']);
                tplObj.list5 = getEventList(['资金划付', '项目追偿']);
                tplObj.branch = {
                    left: 0,
                    startWidth: 60,
                    endWidth: 45
                };
                break;
            case 'FZ':
                tplObj.type = 'ZDB';
                tplObj.list2 = getEventList(['合同申请', '授信条件落实', '发售信息维护', '费用收取通知']);
                tplObj.list3 = getEventList(['放用款申请', '保后检查报告', '项目风险等级评定', '解保申请']);
                tplObj.list5 = getEventList(['资金划付', '项目追偿']);
                tplObj.branch = {
                    left: 0,
                    startWidth: 182,
                    endWidth: 195
                };
                break;
        }

        var _list4 = ['征信查询', '资料归档'];

        if (res.templete != 'CX') {
            _list4 = _list4.concat(['风险处理', '风险上会申报']);
        }

        _list4 = _list4.concat(['文书申请', '函、通知书申请']);

        tplObj.list4 = getEventList(_list4);

        tplObj.message = res.message;

        return tplObj;

    }

    $('#fnProjectStatus').on('click', '.fnOpenInfo', function () {

        var _this = $(this),
            _offset = _this.offset(),
            _type = _this.find('.thisBusiType').val(); //项目类型 担保/委贷==1，承销==2，诉讼==3.债券担保==4


        util.ajax({
            url: '/baseDataLoad/projectCando.json?projectCode=' + _this.text().replace(/\s/g, ''),
            success: function (res) {

                var tplObj = getThisFlow(res);

                tplObj.subNum = [];

                $.each(['1', '2', '3', '4', '5'], function (index, str) {

                    var _subNum = 0;

                    $.each(tplObj['list' + str], function (index, obj) {

                        obj.active = res[obj.code];

                        if (obj.sub) {
                            $.each(obj.sub, function (index, sobj) {
                                sobj.active = res[sobj.code];
                            });
                            _subNum += obj.sub.length
                        }

                    });

                    tplObj.subNum.push(_subNum)

                });

                tplObj.projectCode = res.projectCode;

                if (!!tplObj.list5.length) {
                    $fnMyWorkbenchFlow.addClass('branch')
                } else {
                    $fnMyWorkbenchFlow.removeClass('branch')
                }

                $fnMyWorkbenchFlow.html(template('t-info', tplObj)).removeClass('fn-hide').css({
                    // left: '50%',
                    // top: '50%',
                    // marginLeft: -$fnMyWorkbenchFlow.outerWidth() / 2 + 'px',
                    // marginTop: -$fnMyWorkbenchFlow.outerHeight() / 2 + 'px'
                });

                // 是否超过当前屏高度
                // var _needH = $fnMyWorkbenchFlow.outerHeight() + _offset.top + _this.height();

                // console.log(_needH);

                // $fnMyWorkbenchFlow.css({
                //  left: _offset.left,
                //  top: (_needH > showFlowMax) ? (_offset.top - $fnMyWorkbenchFlow.outerHeight()) : (_offset.top + _this.height())
                // }).removeClass('fn-hide');

            }
        });

    });
    $fnMyWorkbenchFlow.on('click', '.close', function () {
        $fnMyWorkbenchFlow.addClass('fn-hide');
    }).on('click', 'a:not(.active)', function (e) {
        // 阻止非激活事件
        e.preventDefault();
    });

    //------ 查看项目情况 end

});