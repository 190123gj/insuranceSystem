/**
 * Created by eryue
 * Create in 2017-01-04 13:54
 * Description:
 */

'use strict';
define(function(require, exports, module) {

    require('Y-msg');
    require('input.limit');
    require('zyw/upAttachModify');

    var util = require('util');
    var template = require('arttemplate');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
    var validateInit = require('tmbp/validate.common');
    var $body = $('body');

    var ADDRENWAL_M, RENEWALPAYMENT_M;

    template.helper('getYYYMMDDhhmmss', function (ts) {//添加一个格式化时间的方法

        var d = util.getNowTime(ts);

        return d.YY + '-' + d.MM + '-' + d.DD + ' ' + d.hh + ':' + d.mm + ':' + d.ss;

    });

/*    $body.on('click','.addRenewal',function () {//添加续期
        var $this = $(this);
        var _html = $('#RENEVALINFO').html();
        $body.Y('Window', {
            content: [
                '<div class="m-modal m-modal-default view-files" style="width: 80%;">',
                '    <div class="m-modal-title"><span class="m-modal-close closeAddRenewal">&times;</span><span class="title">添加续期</span></div>',
                '    <div class="m-modal-body">',
                '        <div class="view-files-body">',_html,
                '        </div>',
                '    </div>',
                '</div>'
            ].join(''),
            modal: true,
            key: 'addRenewal',
            simple: true,
            closeEle: '.closeAddRenewal'
        });

        ADDRENWAL_M = Y.getCmp('addRenewal');
        var $addRenewal = ADDRENWAL_M.wnd;
    });*/
    $body.on('click','.renewalPayment',function () {//续期缴费
    	 var _html = $('#RENEVALPAYMENT').html();
         var $tr = $(this).parents('tr');
         var period = $tr.attr('period');
         var businessBillId =  $tr.attr('businessBillId');
         var trIndex = $tr.parents('table').find('tr').index($tr);
         var data = $tr.find('[name*=insuranceBillRenewal]').val();
         var payPlanId = $tr.find('.paymentStatus').val();
         
         if($tr.find('.newAddVal').length > 0){//填写过一次之后
             var info = !data ? {} : JSON.parse(data);
             showDailog(_html,info,payPlanId);
         }else {
             $.ajax({
                 url:'/insurance/businessBill/getInsuranceBillRenewal.json',
                 type:'post',
                 data:{
                     businessBillId:businessBillId,
                     period:period
                 },
                 success:function (res) {
                     if(res.success){
                     	console.log(res.returnObject);
                         var resData = !res.returnObject ? {} : res.returnObject;
                         showDailog(_html,resData,payPlanId)
                     }else {
                         Y.alert('提示',res.message);
                     }
                 }
             });
         }
         
         function showDailog(_content,_d,_payPlanId) {
             $body.Y('Window', {
                 content: [
                     '<div class="m-modal m-modal-default view-files renewalPaymentBody" style="width: 60%;min-width: 830px;height: auto;" targetlist="',trIndex,'">',
                     '    <div class="m-modal-title"><span class="m-modal-close closeRenewalPayment">&times;</span><span class="title">续期缴费</span></div>',
                     '    <div class="m-modal-body">',
                     '        <div class="view-files-body">',_content,
                     '        </div>',
                     '    </div>',
                     '</div>'
                 ].join(''),
                 modal: true,
                 key: 'renewalPayment',
                 simple: true,
                 closeEle: '.closeRenewalPayment'
             });

             RENEWALPAYMENT_M = Y.getCmp('renewalPayment');
             var $renewalPayment = RENEWALPAYMENT_M.wnd;
             $renewalPayment.find('.fnUpAttachUl').hide();
             $renewalPayment.find('#businessBillId').val(businessBillId);//标记ID
             $renewalPayment.find('#period').val(period);//标记ID
             //还原数据

             $.each(_d,function (key,v) {
                 var $el = $renewalPayment.find('[name=' + key + ']').not('[disabled]');
                 if($renewalPayment.find('[name=' + key + ']').not('[disabled]').length == 0) return;
                 if(key == 'tradingTime') v = v == null ? "" :util.formtDate(new Date(v),'yyyy-MM-dd');
                 if(key == 'money') v=v.amount == "undefined" ? v :v.amount; 
                 if(key == 'paymentMethod') {
                     $renewalPayment.find('[name=paymentMethod] option[value=' + _d.paymentMethod + ']').attr('selected','selected');
                     changePayeMentWay(_d.paymentMethod);
                 }else {
                     $el.val(v);
                 }
                 //根据是否未缴费字段判断，已经缴费了禁用。没有缴费，则填过一次信息后，仍然显示输入的信息
                 if(_payPlanId > 0) $el.attr('disabled','disabled');
             });
             if(_payPlanId > 0 && JSON.stringify(_d) !== '{}' && Object.prototype.toString.call(_d) === '[object Object]') $renewalPayment.find('.submitRenewalPayment').addClass('fn-hide');

         }	
    });

    $body.on('change','.paymentWaySelect', function () {//选择缴费方式
        var val = $(this).val();
        $('.paymentWayItems').find('.paymentWayitemInfo').addClass('fn-hide').find('[name]').attr('disabled','disabled');
        !!val && $('.paymentWayItems').find('#' + val).removeClass('fn-hide').find('[name]').removeAttr('disabled');
    });
    
    $body.on('click','.submitRenewalPayment',function () {//提交续期缴费
        var $self = $(this);
        var $form = $self.parents('form');
        if($form.length == 0) return;
        var targetIndex = $self.parents('.m-modal').attr('targetlist');
        validatForm($form);
        if (!$form.valid()) return;
        var data = {};
        $.each($self.parents('.m-modal-body').find('[name]').not('[disabled]'),function (i,el) {
            var val = $(el).val();
            !!val && (data[$(el).attr('name')] = val);
        });
        var str = JSON.stringify(data);
        var paymentWay = $form.find("select").find("option:selected").val();
        //获取当前缴费计划中的保费
        var premiumAmount = $('.renvalwalBillTable tr').eq(targetIndex).find('[name*=premiumAmount]').val();
        var money = $("#"+paymentWay).find('[name*=money]').val();
        if (premiumAmount != money) {
        	 Y.alert('提示信息','缴费金额与缴费计划中的保险费不符')
        	return ;
        }
        !!targetIndex && $('.renvalwalBillTable tr').eq(targetIndex).find('[name*=insuranceBillRenewal]').val(str).addClass('newAddVal');
        if (!!targetIndex) var $currentTr = $('.renvalwalBillTable tr').eq(targetIndex);
        //获取当前缴费计划当中的实收日期
        var $currentPayDate = $currentTr.find('input[name*=actualPayDate]');
        //如果实收日期没有填写，就取输入缴费信息，就自动带入缴费信息中的交易时间
        if ($.trim($currentPayDate.val()).length == 0) {
            //获取缴费信息中的交易时间
            var tradingTime = $("#"+paymentWay).find('[name*=tradingTime]').val();
            $currentPayDate.val(tradingTime);
        }
        RENEWALPAYMENT_M.close();
    }).on('blur','.actualPayDate',function(){
    	var $self = $(this);
    	var _val = $self.val();
    	var $thisTr = $(this).parents('tr');
    	//如果没有实收日期，清空缴费信息
    	if (!_val) {$thisTr.find("input[name*=insuranceBillRenewal]").val("")}
    	
    });

    function validatForm($thisForm) {//验证初始化
        new validateInit({
            formObj: $thisForm,//jq对象
            ignoreClass: '',//需要排除验证元素的class
            validateEle: 'fn-validate',//需要验证元素的obj
            validateRules: {
            }
        });
    }
    
    
    $body.on('click','.paymentDocument',function () {//续期缴费
        var _html = $('#PAYMENTDOCUMENT').html();
        var $tr = $(this).parents('tr');
        var period = $tr.attr('period');
        var businessBillId =  $tr.attr('businessBillId');
        var trIndex = $tr.parents('table').find('tr').index($tr);
        var payPlanId = $tr.find('.paymentStatus').val();

        $.ajax({
            url:'/insurance/businessBill/getInsuranceBillRenewal.json',
            type:'post',
            data:{
                businessBillId:businessBillId,
                period:period
            },
            success:function (res) {
                if(res.success){
                	console.log(res.returnObject);
                    var resData = !res.returnObject ? {} : res.returnObject;
                    showDailog(_html,resData,payPlanId)
                }else {
                    Y.alert('提示',res.message);
                }
            }
        });
        
        function showDailog(_content,_d,_payPlanId) {
            $body.Y('Window', {
                content: [
                    '<div class="m-modal m-modal-default view-files renewalPaymentBody" style="width: 60%;min-width: 830px;height: auto;" targetlist="',trIndex,'">',
                    '    <div class="m-modal-title"><span class="m-modal-close closeRenewalPayment">&times;</span><span class="title">续期缴费</span></div>',
                    '    <div class="m-modal-body">',
                    '        <div class="view-files-body">',_content,
                    '        </div>',
                    '    </div>',
                    '</div>'
                ].join(''),
                modal: true,
                key: 'renewalPayment',
                simple: true,
                closeEle: '.closeRenewalPayment'
            });

            RENEWALPAYMENT_M = Y.getCmp('renewalPayment');
            var $renewalPayment = RENEWALPAYMENT_M.wnd;
            $renewalPayment.find('.fnUpAttachUl').hide();
            //还原数据

            $.each(_d,function (key,v) {
                var $el = $renewalPayment.find('[name=' + key + ']').not('[disabled]');
                if($renewalPayment.find('[name=' + key + ']').not('[disabled]').length == 0) return;
                if(key == 'tradingTime') v = v == null ? "" :util.formtDate(new Date(v),'yyyy-MM-dd');
                if(key == 'money') v=v.amount == "undefined" ? v :v.amount; 
                if(key == 'paymentMethod') {
                    $renewalPayment.find('[name=paymentMethod] option[value=' + _d.paymentMethod + ']').attr('selected','selected');
                    changePayeMentWay(_d.paymentMethod);
                }else {
                    $el.val(v);
                }
                //根据是否未缴费字段判断，已经缴费了禁用。没有缴费，则填过一次信息后，仍然显示输入的信息
                if(_payPlanId > 0) $el.attr('disabled','disabled');
            });
            if(_payPlanId > 0 && JSON.stringify(_d) !== '{}' && Object.prototype.toString.call(_d) === '[object Object]') $renewalPayment.find('.submitRenewalPayment').addClass('fn-hide');

        }
    });
    
    
    function changePayeMentWay(val) {
        $('.paymentWayItems').find('.paymentWayitemInfo').addClass('fn-hide').find('[name]').attr('disabled','disabled').val('');
        !!val && $('.paymentWayItems').find('#' + val).removeClass('fn-hide').find('[name]').removeAttr('disabled');
    };

})