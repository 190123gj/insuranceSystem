~ function() {
    var base = '/js/';
    var lPath = 'lib/'; // /js/lib/
    var yPath = 'Y-all/Y-script/'; // /js/Y-all/Y-script/
    var mPath = 'module/'; // /js/Y-all/Y-script/
    var zyw = 'tmbp/'; // /js/tmbp/
    seajs.config({
        base: base,
        debug: false,
        map: [
            [/^(.*\.(?:css|js))(.*)$/i, '$1?v=20161111']
        ], // 时间戳
        alias: {
            /*Y-all组件库*/
            'Y-countdown': yPath + 'Y-countdown', //倒计时
            'Y-combobox': yPath + 'Y-combobox', //组合框
            'Y-selectbranch': yPath + 'Y-selectbranch', //省市支行联动组合框
            'Y-selectarea': yPath + 'Y-selectarea', //省市联动
            'Y-tip': yPath + 'Y-tip', //提示
            'Y-entertip': yPath + 'Y-entertip', //输入提示
            'Y-tooltip': yPath + 'Y-tooltip', //tooltip
            'Y-rarewordtip': yPath + 'Y-rarewordtip', //生僻字输入
            'Y-autocomplete': yPath + 'Y-autocomplete', //自动完成
            'Y-window': yPath + 'Y-window', //弹出层
            'Y-msg': yPath + 'Y-msg', //消息框
            'Y-htmluploadify': yPath + 'Y-htmluploadify', //ajax上传
            'Y-imageplayer': yPath + 'Y-imageplayer', //图片播放器
            'Y-tree': yPath + 'Y-tree', //树形
            'Y-select': yPath + 'Y-select', //封装了几个操作select的函数，操作完后自动调用jqtransform处理
            'Y-selectarea': yPath + 'Y-selectarea', //自动给有特定标志的元素下的两个select框添加省市联动功能
            'Y-number': yPath + 'Y-number', //自动给有特定标志的元素下的两个select框添加省市联动功能

            /*page-zyw*/
            //对象集合
            'zyw/setUE': zyw + 'setUE',
            'zyw/getCA': zyw + 'getCA', //选项合作机构 大概已废弃
            'zyw/getList': zyw + 'getList', //普通列表选择
            'zyw/multSelect': zyw + 'multSelect', //多级联动选择
            'zyw/getTypesOfCredit': zyw + 'getTypesOfCredit', //获取授信种类
            'zyw/getChannel': zyw + 'getChannel', //获取渠道
            'zyw/publicOPN': zyw + 'publicOPN', //通用侧边栏
            'zyw/publicPage': zyw + 'publicPage', //通用分页、列表
            'zyw/auditProject': zyw + 'auditProject', //审核通用部分
            'zyw/changeApply': zyw + 'changeApply', //审核通用部分
            'zyw/yearsTime': zyw + 'yearsTime', //年月插件
            'zyw/mergeTable': zyw + 'mergeTable', //合并表格、灵活增删
            'zyw/bfcg.itn.addValidataCommon': zyw + 'bfcg.itn.addValidataCommon', //d验证共用
            'zyw/assistsys.smy.fillReviewTypeCommon': zyw + 'assistsys.smy.fillReviewTypeCommon', //d validata在封装

            //代码片段集合
            'zyw/opsLine': zyw + 'opsLine', //增删行
            'zyw/getNav': zyw + 'getNav', //获取左侧导航
            'zyw/orderName': zyw + 'orderName', //提交纠正表格name序列
            'zyw/requiredRules': zyw + 'requiredRules', //必填集合
            'zyw/hintPopup': zyw + 'hintPopup', //提示弹窗
            'zyw/HanZiToSpell': zyw + 'HanZiToSpell', //汉字转拼音
            'zyw/hintFont': zyw + 'hintFont', //字数提示
            'zyw/chooseIndustry': zyw + 'chooseIndustry', //选择行业
            'zyw/chooseRegion': zyw + 'chooseRegion', //选择地区
            'zyw/chooseLoanPurpose': zyw + 'chooseLoanPurpose', //选择授信用途
            'zyw/upAttach': zyw + 'upAttach', //上传图片或附件
            'zyw/upAttachModify': zyw + 'upAttachModify', //上传图片或附件upAttachModify
            'zyw/searchForDetails': zyw + 'searchForDetails', //选择某个code查询详情
            'zyw/popupWindow': zyw + 'popupWindow', //弹窗
            'zyw/submitValidataCommon': zyw + 'submitValidataCommon', //会议纪要验证共用
            'zyw/submitValidataCommonUp': zyw + 'submitValidataCommonUp', //升级
            'zyw/inherentSubmitValidataCommon': zyw + 'inherentSubmitValidataCommon', //固有暂存与提交侧边栏公用文件
            'zyw/submitBeforeVal': zyw + 'submitBeforeVal', //提交前参数
            'zyw/process.result': zyw + 'process.result',
            'zyw/submited': zyw + 'submited', //all提交为没提交完整的页面加标记
            'zyw/navSubmited': zyw + 'navSubmited',
            'zyw/chooseDate': zyw + 'chooseDate',
            'zyw/riskQuery': zyw + 'riskQuery',

            'zyw/init': zyw + 'init',
            'zyw/login': zyw + 'login',
            'zyw/modifyPassword': zyw + 'modifyPassword',

            //授信前 复议申请表列表
            'zyw/project/bfcg.recon.list': zyw + 'project/bfcg.recon.list',
            'zyw/project/bfcg.recon.add': zyw + 'project/bfcg.recon.add',
            //授信前 调查报告
            'zyw/project/bfcg.rkrv.list': zyw + 'project/bfcg.rkrv.list',
            'zyw/project/bfcg.rkrv.add.audit': zyw + 'project/bfcg.rkrv.add.audit',

            //授信前 尽职调查
            'zyw/project/bfcg.itn.auditDueDiligence': zyw + 'project/bfcg.itn.auditDueDiligence', //审核
            'zyw/project/bfcg.itn.auditRisk': zyw + 'project/bfcg.itn.auditRisk',
            'zyw/project/bfcg.itn.auditLawWorksLawsuit': zyw + 'project/bfcg.itn.auditLawWorksLawsuit',
            'zyw/project/bfcg.itn.creditPlan': zyw + 'project/bfcg.itn.creditPlan',
            'zyw/project/bfcg.itn.clienteleSubjectEvaluate': zyw + 'project/bfcg.itn.clienteleSubjectEvaluate',
            'zyw/project/bfcg.itn.clienteleManageCapacity': zyw + 'project/bfcg.itn.clienteleManageCapacity',
            'zyw/project/bfcg.itn.clienteleOperateCapacity': zyw + 'project/bfcg.itn.clienteleOperateCapacity',
            'zyw/project/bfcg.itn.clienteleFinanceEvaluate': zyw + 'project/bfcg.itn.clienteleFinanceEvaluate',
            'zyw/project/bfcg.itn.greatMatterSurvey': zyw + 'project/bfcg.itn.greatMatterSurvey',
            'zyw/project/bfcg.itn.projectConditionSurvey': zyw + 'project/bfcg.itn.projectConditionSurvey',
            'zyw/project/bfcg.itn.creditSchemeRationality': zyw + 'project/bfcg.itn.creditSchemeRationality',
            'zyw/project/bfcg.itn.riskAndConclusion': zyw + 'project/bfcg.itn.riskAndConclusion',
            'zyw/project/bfcg.itn.counterGuaranteeCommon': zyw + 'project/bfcg.itn.counterGuaranteeCommon',
            'zyw/project/bfcg.itn.viewCreditPlan': zyw + 'project/bfcg.itn.viewCreditPlan', //编辑
            'zyw/project/bfcg.itn.viewClienteleSubjectEvaluate': zyw + 'project/bfcg.itn.viewClienteleSubjectEvaluate',
            'zyw/project/bfcg.itn.viewClienteleManageCapacity': zyw + 'project/bfcg.itn.viewClienteleManageCapacity',
            'zyw/project/bfcg.itn.viewClienteleOperateCapacity': zyw + 'project/bfcg.itn.viewClienteleOperateCapacity',
            'zyw/project/bfcg.itn.viewClienteleFinanceEvaluate': zyw + 'project/bfcg.itn.viewClienteleFinanceEvaluate',
            'zyw/project/bfcg.itn.viewGreatMatterSurvey': zyw + 'project/bfcg.itn.viewGreatMatterSurvey',
            'zyw/project/bfcg.itn.viewProjectConditionSurvey': zyw + 'project/bfcg.itn.viewProjectConditionSurvey',
            'zyw/project/bfcg.itn.viewCreditSchemeRationality': zyw + 'project/bfcg.itn.viewCreditSchemeRationality',
            'zyw/project/bfcg.itn.viewRiskAndConclusion': zyw + 'project/bfcg.itn.viewRiskAndConclusion', //查看
            'zyw/project/bfcg.itn.dueDiligenceMeta': zyw + 'project/bfcg.itn.dueDiligenceMeta',
            'zyw/project/bfcg.itn.toIndex': zyw + 'project/bfcg.itn.toIndex',
            'zyw/project/bfcg.itn.allWhetherNull': zyw + 'project/bfcg.itn.allWhetherNull', //不验证必填项时必填项是否有null
            'zyw/project/bfcg.itn.addValidataCommon': zyw + 'project/bfcg.itn.addValidataCommon', //验证方法集
            'zyw/project/bfcg.itn.ValidataCommon': zyw + 'project/bfcg.itn.ValidataCommon', //页面共用
            'zyw/project/bfcg.itn.lawsuitGuaranteeNew': zyw + 'project/bfcg.itn.lawsuitGuaranteeNew',
            'zyw/project/bfcg.itn.viewConsignment': zyw + 'project/bfcg.itn.viewConsignment',
            'zyw/project/bfcg.itn.recordCommon': zyw + 'project/bfcg.itn.recordCommon',


            //辅助系统 会议纪要
            'zyw/project/assistsys.smy.Common': zyw + 'project/assistsys.smy.Common', //页面共用
            'zyw/project/assistsys.smy.fillReviewTypeCommon': zyw + 'project/assistsys.smy.fillReviewTypeCommon',
            'zyw/project/assistsys.smy.auditFillReviewType': zyw + 'project/assistsys.smy.auditFillReviewType',
            'zyw/project/assistsys.smy.auditFillReviewTypeCommon': zyw + 'project/assistsys.smy.auditFillReviewTypeCommon',
            'zyw/project/assistsys.smy.unitChange': zyw + 'project/assistsys.smy.unitChange',
            'zyw/project/assistsys.smy.init': zyw + 'project/assistsys.smy.init',
            //特种纸
            'zyw/project/assistsys.sp.SPcommon': zyw + 'project/assistsys.sp.SPcommon',

            //授信后 保后项目
            'zyw/project/afcg.atcp.add': zyw + 'project/afcg.atcp.add',
            //授信后 授信条件落实情况信息维护
            'zyw/project/afcg.rklv.add': zyw + 'project/afcg.rklv.add',
            'zyw/project/afcg.Spect.list': zyw + 'project/afcg.Spect.list',
            'zyw/project/afcg.addSpect.DB.WD': zyw + 'project/afcg.addSpect.DB.WD',
            'zyw/project/afcg.addSpect.Currency': zyw + 'project/afcg.addSpect.Currency',
            'zyw/project/afcg.addSpect.DB.SS': zyw + 'project/afcg.addSpect.DB.SS',
            'zyw/project/afcg.editpub': zyw + 'project/afcg.editpub',

            //授信后 到期项目
            'zyw/project/afcg.tont.list': zyw + 'project/afcg.tont.list',

            //追偿
            'zyw/project/afcg.recovery.list': zyw + 'project/afcg.recovery.list',
            'zyw/project/afcg.addRecovery.Business': zyw + 'project/afcg.addRecovery.Business',
            'zyw/project/afcg.print.Data': zyw + 'project/afcg.print.Data',


            //资金收付管理 收费通知
            'zyw/project/bfcg.cgnt.list': zyw + 'project/bfcg.cgnt.list',
            'zyw/project/bfcg.cgnt.add.audit': zyw + 'project/bfcg.cgnt.add.audit',
            //风险管控 上会申报记录
            'zyw/project/rkct.mtrt.list': zyw + 'project/rkct.mtrt.list',

            // 保后房地产详情
            'zyw/project/afcg.addSpect.realEstateDetail': zyw + 'project/afcg.addSpect.realEstateDetail',

            //客户管理配置共用
            'zyw/customer/cRe.commonOneHierarchy': zyw + 'customer/cRe.commonOneHierarchy',
            'zyw/customer/cRe.commonTwoHierarchy': zyw + 'customer/cRe.commonTwoHierarchy',
            'zyw/customer/gae.gradeIndexCommon': zyw + 'customer/gae.gradeIndexCommon',
            'zyw/customer/gae.gradeIndexInitCommon': zyw + 'customer/gae.gradeIndexInitCommon',
            //资金管理配置共用
            'zyw/fund/fme.ibw.validataCommon': zyw + 'fund/fme.ibw.validataCommon',
            'zyw/fund/fme.applyAddPopupCommom': zyw + 'fund/fme.applyAddPopupCommom',

            //公共组件
            'submit.common': zyw + 'sumbit.common.js', //通用的提交(包含验证)
            'validate.common': zyw + 'validate.common.js', //单独的验证


            /*module*/
            'M-upfile': mPath + 'upfile',
            /*第三方库*/
            'validate': lPath + 'jquery.validate.min',
            'validate.extend': lPath + 'validate.extend',
            'arttemplate': lPath + 'arttemplate',
            'input.limit': lPath + 'input.limit',
            'util': lPath + 'util',
            'md5': lPath + 'md5.min',
            'base64': lPath + 'base64.min',
            'echarts': lPath + 'echarts.min',
            'BPMiframe': lPath + 'BPMiframe',
            'form': lPath + 'jquery.form'
        }
    });
}();
$_GLOBAL = typeof $_GLOBAL == 'object' ? $_GLOBAL : {};
$_GLOBAL.zyw = '/js/tmbp';
// 因为jquery ajax是使用utf-8来编码发送数据的，ie在发送时却没加上charset=utf-8，从而导致乱码(IE默认使用iso-8859-1编码)
$.ajaxSetup({
    contentType: 'application/x-www-form-urlencoded; charset=utf-8',
    cache: false
});
// console
(function() {

    //创建空console对象，避免JS报错  

    if (!window.console)
        window.console = {};
    var console = window.console;

    var funcs = ['assert', 'clear', 'count', 'debug', 'dir', 'dirxml',
        'error', 'exception', 'group', 'groupCollapsed', 'groupEnd',
        'info', 'log', 'markTimeline', 'profile', 'profileEnd',
        'table', 'time', 'timeEnd', 'timeStamp', 'trace', 'warn'
    ];
    for (var i = 0, l = funcs.length; i < l; i++) {
        var func = funcs[i];
        if (!console[func])
            console[func] = function() {};
    }
    if (!console.memory)
        console.memory = {};

})();
// 关闭input文本输入框的自动提示功能
$('form').attr('autocomplete', 'off');
// 审核返回按钮？？
$('.ui-btn-back:not(.diy-back)').attr({
    href: 'javascript:void(0);',
    onclick: ''
}).on('click', function() {
    var isBack = ((/(\/insurance\/index\.htm|\/projectMg\/index\.htm|\/assetMg\/index\.htm|\/fundMg\/index\.htm|\/reportMg\/index\.htm)/g).test(document.referrer)) ? false : true;

    if (window.top === window.self) {
        isBack = false
    }

    if (isBack) {
        window.location.href = document.referrer;
    } else {
        window.top.location.href = '/';
    }
});

$('input[type="text"]:visible:not([readonly], #fnListSearchInput), textarea:visible:not([readonly])').eq(0).trigger('blur');

// 是否是App访问
$_GLOBAL.isApp = (navigator.userAgent.indexOf('jinchukou') >= 0) ? true : false

// 满足app webview 查看
setTimeout(function() {

    if ($_GLOBAL.isApp) {

        $('.ui-btn-submit, #fnMOPN, ul.ui-tool').remove()

    }

}, 1000)