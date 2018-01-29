define(function(require, exports, module) {
	//保险责任
	require('Y-msg');
	require('input.limit');
	require('tmbp/upAttachModifyNew');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
	require('validate');

    var util = require('util');
	var getList = require('zyw/getList');
    var validateInit = require('tmbp/validate.common');

    var customerChoose1 = new getList();
    var customerChoose2 = new getList();
    var $body = $('body');

    var RENEWALPAYMENT_M;

    customerChoose1.init({
		title: '客户信息',
		ajaxUrl: '/baseDataLoad/customer.json',
		btn: '#fnToChooseCustomer1',
        multiple: true,
        tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="itemLine">',
				'        <td class="fn-tac"title="{{item.name}}">{{item.name}}</td>',
				'        <td class="fn-tac"title="{{item.certType}}">{{item.certType}}</td>',
				'        <td class="fn-tac"title="{{item.certNo}}">{{item.certNo}}</td>',
                '        <td class="fn-tac fn-hide listData" userid="{{item.userId}}"><a href="/insurance/customer/{{item.customerTypeCode == "INDIVIDUAL_CUSTOMER" ? "person" : "company"}}/info.htm?customerId={{item.customerId}}" target="_blank">[ 详情 ]</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="del">[ 删除 ]</a></td>',
                '        <td class="fn-tac"><a class="choose" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['客户名称：',
				'<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
				'证件类型：',
				'<select class="ui-select fn-w200 fn-validate" name="certType">'
	            +'<option value="">请选择</option>'
	            +'<option value="IDENTITY_CARD">身份证</option>'
	            +'<option value="ARMY_IDENTITY_CARD">军官证</option>'
	            +'<option value="STUDENT_CARD">学生证</option>'
	            +'<option value="PASSPORT">护照</option>'
	            +'<option value="OTHER">其他证件</option>'
	            +'<option value="BUSINESS_LICENSE">营业执照</option>'
	            +'<option value="TAX_REGISTRATION_CERTIFICATE">税务登记证</option>'
	            +'<option value="ORGANIZATION_CODE_CERTIFICATE">组织机构代码证</option>'
	            +'<option value="BUSINESS_PERMIT_CERTIFICATE">业务许可证</option>'
	            +'</select>',
				'&nbsp;&nbsp;',
				'证件号码：',
				'<input class="ui-text fn-w100" type="text" name="certNo">',
				'<input class="ui-text fn-w100" type="hidden" name="customerType" value="COMPANY_CUSTOMER">',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>客户名称</th>',
				'<th>证件类型</th>',
				'<th>证件号码</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#policyHolder").val($a.attr("name"));
			$("#policyHolderId").val($a.attr("userId"));
			$(".certType1").html($a.attr("certType"));
			$(".certNo1").html($a.attr("certNo"));
			$("#certType1").val($a.attr("certType"));
			$("#certNo1").val($a.attr("certNo"));
		}
	});
    
    
    customerChoose2.init({
		title: '客户信息',
		ajaxUrl: '/baseDataLoad/customer.json',
		btn: '#fnToChooseCustomer2',
        multiple: true,
        tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="itemLine">',
				'        <td class="fn-tac"title="{{item.name}}">{{item.name}}</td>',
				'        <td class="fn-tac"title="{{item.certType}}">{{item.certType}}</td>',
				'        <td class="fn-tac"title="{{item.certNo}}">{{item.certNo}}</td>',
                '        <td class="fn-tac fn-hide listData" userid="{{item.userId}}"><a href="/insurance/customer/{{item.customerTypeCode == "INDIVIDUAL_CUSTOMER" ? "person" : "company"}}/info.htm?customerId={{item.customerId}}" target="_blank">[ 详情 ]</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="del">[ 删除 ]</a></td>',
                '        <td class="fn-tac"><a class="choose" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['客户名称：',
				'<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
				'证件类型：',
				'<select class="ui-select fn-w200 fn-validate" name="certType">'
	            +'<option value="">请选择</option>'
	            +'<option value="IDENTITY_CARD">身份证</option>'
	            +'<option value="ARMY_IDENTITY_CARD">军官证</option>'
	            +'<option value="STUDENT_CARD">学生证</option>'
	            +'<option value="PASSPORT">护照</option>'
	            +'<option value="OTHER">其他证件</option>'
	            +'<option value="BUSINESS_LICENSE">营业执照</option>'
	            +'<option value="TAX_REGISTRATION_CERTIFICATE">税务登记证</option>'
	            +'<option value="ORGANIZATION_CODE_CERTIFICATE">组织机构代码证</option>'
	            +'<option value="BUSINESS_PERMIT_CERTIFICATE">业务许可证</option>'
	            +'</select>',
				'&nbsp;&nbsp;',
				'证件号码：',
				'<input class="ui-text fn-w100" type="text" name="certNo">',
				'<input class="ui-text fn-w100" type="hidden" name="customerType" value="COMPANY_CUSTOMER">',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>客户名称</th>',
				'<th>证件类型</th>',
				'<th>证件号码</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#insuredPerson").val($a.attr("name"));
			$("#insuredPersonId").val($a.attr("userId"));
			$(".certType2").html($a.attr("certType"));
			$(".certNo2").html($a.attr("certNo"));
			$("#certType2").val($a.attr("certType"));
			$("#certNo2").val($a.attr("certNo"));

		}
	});
    
    
    (new getList()).init({
		title: '选择渠道',
		ajaxUrl: '/baseDataLoad/customer.json',
		btn: '#fnToChooseCompany',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td title="{{item.name}}">{{item.name}}</td>',
				'        <td title="{{item.certType}}">{{item.certType}}</td>',
				'        <td title="{{item.certNo}}">{{item.certNo}}</td>',
				'<td><a class="choose" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['保险公司名称：',
				'<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
				'<input class="ui-text fn-w100" type="hidden" name="customerType" value="COMPANY_CUSTOMER">',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>保险公司名称</th>',
				'<th>证件类型</th>',
				'<th>证件号码</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#insuranceCompanyName").val($a.attr("name")).blur();
			$("#insuranceCompanyId").val($a.attr("userId")).blur();
			
		}
	});
    
    
    function isEmptyObject(e) {  
        var t;  
        for (t in e)  
            return !1;  
        return !0  
    }  

    $body.on('click','.benefitPlan',function () {
        var $this = $(this);
        var $benefitPlanBox = $('.benefitPlanBox');
        var $tabel =$benefitPlanBox.find('table');
        var radioTempalteId = $this.attr('templateid');
        var addTempalteId = $benefitPlanBox.find('.addLine').attr('templateid');
        if(!radioTempalteId || radioTempalteId == addTempalteId) return;
        // console.log(radioTempalteId)
        if(radioTempalteId == 'TMP_BENEFIT_PLAN_ORDER'){
            $tabel.find('tr:first th ').eq(1).addClass('fn-hide');
            $tabel.find('tr:first th').eq(0).removeClass('fn-hide');
            $benefitPlanBox.find('.addLine').attr('templateid','TMP_BENEFIT_PLAN_ORDER').end()
            $benefitPlanBox.find('.allScaleNum').addClass('ignore');
        }else {
            $tabel.find('tr:first th').eq(0).addClass('fn-hide').end()
            $tabel.find('tr:first th').eq(1).removeClass('fn-hide').end()
            $benefitPlanBox.find('.addLine').attr('templateid','TMP_BENEFIT_PLAN_SCALE')
            $benefitPlanBox.find('.allScaleNum').removeClass('ignore');
        }
        $tabel.find('tr').not(':first').remove();
    }).on('blur','.benefitPlanScale',function () {
        var $allBenefitPlanScaleInput = $('.benefitPlanScale');
        var allScale  = 0;
        $.each($allBenefitPlanScaleInput,function () {
            var $this = $(this);
            var num = $this.val();
            if(!num) return;
            num = num.replace(/%/g,'');
            allScale += (+num);
        });
        if(allScale > 100){
            Y.alert('提示','受益比例总和不能超过100%',function () {
                $('.allScaleNum').val('0');
            });
            return;
        };
        $('.allScaleNum').val(allScale);
    }).on('click','.addBillCoins',function(){
    	$(this).hide();
    	$(".removeBillCoins").show();
    	$(".billCoins").removeClass("fn-hide");
    	$(".billCoins").find("input").removeAttr("disabled");
    }).on('click','.removeBillCoins',function(){
    	$(this).hide();
    	$(".addBillCoins").show();
    	$(".billCoins").addClass("fn-hide");
    	$(".billCoins").find("input").attr("disabled",true);
    }).on('click','.addBillReins',function(){
    	$(this).hide();
    	$(".removeBillReins").show();
    	$(".billReins").removeClass("fn-hide");
    	$(".billReins").find("input").removeAttr("disabled");
    }).on('click','.removeBillReins',function(){
    	$(this).hide();
    	$(".addBillReins").show();
    	$(".billReins").addClass("fn-hide");
    	$(".billReins").find("input").attr("disabled",true);
    });

    $body.on('click','.renewalPayment',function () {//续期缴费
        var _html = $('#RENEVALPAYMENT').html();
        var $tr = $(this).parents('tr');
        var period = 0;
        if ($(this).hasClass("quota")) {
        	 period = 1;
        } else if ($(this).hasClass("noQuota")){
        	period =  $tr.attr('period');
        } else {
        	 period = $tr.find('.appserialPeriod').html();
        }
      
        var businessBillId =  $tr.attr('businessBillId');
        var trIndex = $tr.parents('table').find('tr').index($tr);
        var data = $tr.find('[name*=insuranceBillRenewal]').val();
        var payPlanId = $tr.find('.paymentStatus').val();;

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
                    // console.log(res);
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
            // console.log(JSON.stringify(_d))
            // console.log(Object.prototype.toString.call(_d))
            if(_payPlanId > 0 && JSON.stringify(_d) !== '{}' && Object.prototype.toString.call(_d) === '[object Object]') $renewalPayment.find('.submitRenewalPayment').addClass('fn-hide');

        }
    });

    $body.on('blur','.actualPayDate',function(){
    	var $self = $(this);
    	var _val = $self.val();
    	var $thisTr = $(this).parents('tr');
    	//如果没有实收日期，清空缴费信息
    	if (!_val) {$thisTr.find("input[name*=insuranceBillRenewal]").val("")}
    	var targetIndex =  $(this).parents('.itemBox').find('table').find('tr').index($thisTr);
    	 //将当前缴费信息中的交易时间，也就是经纪费明细中的应付日期 和 佣金明细的应付日期
        var $brokerageTr = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr').eq(targetIndex);
        $brokerageTr.find('td.planPayDate').html(_val);
        $brokerageTr.find('input.planPayDate').val(_val);
        $brokerageTr.find('input.extactTime').val(_val);
        
        var $commissionTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr').eq(targetIndex);
        $commissionTr.find('td.planPayDate').html(_val);
        $commissionTr.find('input.planPayDate').val(_val);
        $commissionTr.find('input.extactTime').val(_val);
    }).on('change','.paymentWaySelect', function () {//选择缴费方式
        var val = $(this).val();

        changePayeMentWay(val);
    }).on('blur','.lifeActualPayDate',function(){
    	var $self = $(this);
    	var $index= $self.parents('table').find("input[value=首年]").length;
    	var $periodTotal = $self.parents('table').find('.appserialPeriod').length;
    	var period = parseInt($periodTotal) / parseInt($index)
    	for(var i = 0;i<period;i++){
    		if($self.hasClass("index_"+i)){
    			var a = $(".index_"+i).length;
    			var val = $(".index_"+i).eq(0).val();
    			$(".brokerageTab").find('tr').eq(i+1).find("input[name*=extactTime]").val(val);
    			$(".commissionTab").find('tr').eq(i+1).find("input[name*=extactTime]").val(val);
    		}
    	}
    	
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
        var premiumAmount = $('.renvalwayPlanTable tr').eq(targetIndex).find('[name*=premiumAmount]').val();
        var money = $("#"+paymentWay).find('[name*=money]').val();
        if (premiumAmount != money) {
        	 Y.alert('提示信息','缴费金额与缴费计划中的保险费不符')
        	return ;
        }
        !!targetIndex && $('.renvalwayPlanTable tr').eq(targetIndex).find('[name*=insuranceBillRenewal]').val(str).addClass('newAddVal');
        if (!!targetIndex) var $currentTr = $('.renvalwayPlanTable tr').eq(targetIndex);
        //获取当前缴费计划当中的实收日期
        var $currentPayDate = $currentTr.find('input[name*=actualPayDate]');
        //如果实收日期没有填写，就取输入缴费信息，就自动带入缴费信息中的交易时间
        if ($.trim($currentPayDate.val()).length == 0) {
            //获取缴费信息中的交易时间
            var tradingTime = $("#"+paymentWay).find('[name*=tradingTime]').val();
            $currentPayDate.val(tradingTime);
            //将当前缴费信息中的交易时间，也就是经纪费明细中的应付日期 和 佣金明细的应付日期
            var $brokerageTr = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr').eq(targetIndex);
            $brokerageTr.find('td.planPayDate').html(tradingTime);
            $brokerageTr.find('input.planPayDate').val(tradingTime);
            $brokerageTr.find('input.extactTime').val(tradingTime);
            
            var $commissionTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr').eq(targetIndex);
            $commissionTr.find('td.planPayDate').html(tradingTime);
            $commissionTr.find('input.planPayDate').val(tradingTime);
            $commissionTr.find('input.extactTime').val(tradingTime);
            
        }
        
       RENEWALPAYMENT_M.close();


    });

    function validatForm($thisForm) {//验证初始化
        new validateInit({
            formObj: $thisForm,//jq对象
            ignoreClass: '',//需要排除验证元素的class
            validateEle: 'fn-validate',//需要验证元素的obj
            validateRules: {
            }
        });
    };

    function changePayeMentWay(val) {
        $('.paymentWayItems').find('.paymentWayitemInfo').addClass('fn-hide').find('[name]').attr('disabled','disabled').val('');
        !!val && $('.paymentWayItems').find('#' + val).removeClass('fn-hide').find('[name]').removeAttr('disabled');
    };

    window.addOtherLine =  function ($self) {
    	$('[templateid="TMP_JJFDETAIL"]').click();
    	$('[templateid="TMP_YJDETAIL"]').click();
    	var $table = $self.parents('.itemBox').find('table').find('tr.itemLine');
    	var $currentTr = $self.parents('.itemBox').find('table').find('tr.itemLine:last');
    	var index = $table.index($currentTr);
        $(".brokerDetail").find('tr').eq(index+1).find(".appserialPeriod").val(index+1);
        $(".commissionDetail").find('tr').eq(index+1).find(".appserialPeriod").val(index+1);
        $(".brokerDetail").find('tr').eq(index+1).find(".currentYear").val(index+1);
        $(".commissionDetail").find('tr').eq(index+1).find(".currentYear").val(index+1);
    };
    window.deleteIndexLine =  function (index) {
    	//删除缴费计划，对应的经纪费明细和佣金明细也需删除

        var $brokerageRateTr = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr');
        var $commissionRateTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr');
        
        var $firstPremiumTr = $('[templateid="TMP_PAYMENT_PLAN"]').parents('.itemBox').find('tr.itemLine').eq(0).find("input.premiumAmount");
        var $firstBrokerageRateTr = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0).find("td.receivableAmount");
        var $firstCommissionRateTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0).find("td.receivableAmount");
        
        //比例
        var $firstBrokerageTdRate = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0).find("td.brokerageRate");
        var $firstBrokerageInputRate = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0).find("input.brokerageRate");


        var $thisCommissionRate = $commissionRateTr.eq(index).find("td.receivableAmount").html();
        var $thisBrokerageAmount = $brokerageRateTr.eq(index).find("td.receivableAmount").html();

        var _thisCommissionRate = (Number($firstBrokerageRateTr.html())+Number($thisBrokerageAmount)).toFixed(2);
        var _thisBrokerageAmount = (Number($firstCommissionRateTr.html())+Number($thisCommissionRate)).toFixed(2);

        $firstBrokerageRateTr.html(_thisCommissionRate);
        $firstCommissionRateTr.html(_thisBrokerageAmount);
        
        var inComeAll = $(".inCome").html();//经纪费总额
        var inComeRate = Number(_thisCommissionRate/inComeAll).toFixed(2);
        $firstBrokerageTdRate.html(inComeRate);
        $firstBrokerageInputRate.val(inComeRate);

        $brokerageRateTr.eq(index).remove();
        $commissionRateTr.eq(index).remove();

    };
    $body.on("click",".deleteLine",function () {
        var $this = $(this);
        var $thisPremium = $this.parents("tr").find("input.premiumAmount").val();
        var $firstPremiumTr = $('[templateid="TMP_PAYMENT_PLAN"]').parents('.itemBox').find('tr.itemLine').eq(0).find("input.premiumAmount");
        var _firstPremium = (Number($firstPremiumTr.val())+Number($thisPremium)).toFixed(2);
        $firstPremiumTr.val(_firstPremium);
    });


    $body.on("change",".premiumAmount",function () {
        var $this = $(this);
        var $thisTr = $(this).parents('tr');
        var index =  $(this).parents('.itemBox').find('table').find('tr').index($thisTr);
        var judge = 0 - $(this).val();
        if (judge >= 0) {
        	 $(this).val(0.00);
        }
        
        var $premiumAmount= $this.parents("table.itemsList").find(".premiumAmount");
        var $inCome = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine').find("td.receivableAmount");//经纪费
        var $payAmount =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr.itemLine').find("td.receivableAmount");//佣金
        var $firstbrokerageTr =$('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0);
        var $firstCommissionTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0);

        //最后一条缴费计划值
        var $lastPremiumAmount= $this.parents("table.itemsList").find(".premiumAmount:last");


        // var $firstbrokerage = $firstbrokerageTr.find("td.receivableAmount");//明细第一条
        var $firstbrokerageInput = $firstbrokerageTr.find("input.receivableAmount");//明细第一条
        var $payAmountInput =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr.itemLine').find("input.receivableAmount");//佣金

        var $firstbrokerageProportion = $firstbrokerageTr.find("td.brokerageRate");//明细第一条比例
        var $firstbrokerageProportionInput = $firstbrokerageTr.find("input.brokerageRate");//明细第一条比例


        var AmountAll = $("#premiumAmountAll").val();//保费总额
        var inComeAll = $(".inCome").html();//经纪费总额
        var payAmountAll = $(".payAmount").html();//佣金总额

        inComeAll = Number(inComeAll);
        payAmountAll = Number(payAmountAll);
        AmountAll = Number(AmountAll);
        var $premiumAmountAll = 0,
            $inComeAll=0,
            $payAmountAll =0 ;

        var firstAmount = Number($premiumAmount.eq(0).val());
        var $firstBrokerage = Number($inCome.eq(0).html());
        var $firstPayAmount = Number($payAmount.eq(0).html());
        
        if(index == '1'){
            for(var i = 0 ; i<($premiumAmount.length-1); i++){
                $premiumAmountAll += Number($premiumAmount.eq(i).val());
            }
            if(AmountAll-$premiumAmountAll < 0){
                Y.alert("金额输入错误");
                $premiumAmount.eq(0).val(((firstAmount-$lastPremiumAmount.val())-($premiumAmountAll-AmountAll)).toFixed(2));
                return false;
            } else if (!$lastPremiumAmount.val() || $lastPremiumAmount.val()  == 0) {
            	  $premiumAmount.eq(0).val(AmountAll);
            } else {
                $lastPremiumAmount.val(AmountAll-$premiumAmountAll);
            }
            commonAmount($this);
        } else {
            //计算第一期的缴费计划，佣金，经纪费
            $this.val(!$this.val()?0:$this.val());

            for(var i = 1 ; i<$premiumAmount.length; i++){
            	if(i == (index-1))continue;
                $premiumAmountAll += Number($premiumAmount.eq(i).val());
                $inComeAll += Number($inCome.eq(i).html());
                $payAmountAll += Number($payAmount.eq(i).html());
            }

            
        
            if(AmountAll-$premiumAmountAll >= $this.val()){//对比各期总和与保费总额
            	commonAmount($this);
                $premiumAmount.eq(0).val((AmountAll-$premiumAmountAll-$this.val()).toFixed(2));
                $inCome.eq(0).html((inComeAll-$inComeAll-$inCome.eq(index-1).html()).toFixed(2));
                $firstbrokerageInput.eq(0).val((inComeAll-$inComeAll-$inCome.eq(index-1).html()).toFixed(2));
                $payAmount.eq(0).html((payAmountAll-$payAmountAll-$payAmount.eq(index-1).html()).toFixed(2));
                $payAmountInput.eq(0).val((payAmountAll-$payAmountAll-$payAmount.eq(index-1).html()).toFixed(2));
                
            }else{
            	Y.alert("金额输入错误");
            	$this.val(AmountAll-$premiumAmountAll-firstAmount);
            	commonAmount($this);
            	
            }
          var temp_JJF =   $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine');
          $firstbrokerageProportion.html(($inCome.eq(0).html()/AmountAll*100).toFixed(2));
          $firstbrokerageProportionInput.val(($inCome.eq(0).html()/AmountAll*100).toFixed(2));
            for(var i = 1 ; i<$premiumAmount.length; i++){
            	var scale = ($inCome.eq(i).html()/inComeAll).toFixed(2);
            	temp_JJF.eq(i).find('input.brokerageRate').val(scale);
            }
            
        }

    });
    
    
    function commonAmount($this){
    	var _val = !$this.val() ? 0:$this.val();
    	var $thisTr = $this.parents('tr');
    	var index =  $this.parents('.itemBox').find('table').find('tr').index($thisTr);
        //总保费
        var premiumAmountAll = $("#premiumAmountAll").val();
        var firstLine = $this.parents('.itemBox').find('tr.itemLine').eq(0).find('.premiumAmount');
       
        //总佣金
        var payAmountAll = $(".payAmount").html();
        //经纪费总额
        var brokerageAmountAll = $(".inCome").html();

    	//经纪费明细中对应的tr
        var $firstbrokerageTr =$('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0);
        var $brokerageTr = $('[templateid="TMP_JJFDETAIL"]').parents('.itemBox').find('tr').eq(index);
        //佣金明细中对应tr
        var $firstCommissionTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr.itemLine').eq(0);
        var $commissionTr =  $('[templateid="TMP_YJDETAIL"]').parents('.itemBox').find('tr').eq(index);
        var commissionRate = $commissionTr.find('td.commissionRate').html();
        
        //计算经纪费明细中的应收金额和经纪费比例 
        var receivableAmount = (_val/premiumAmountAll)*brokerageAmountAll;
        receivableAmount = receivableAmount.toFixed(2);
        var brokerageRate = receivableAmount/premiumAmountAll*100;
        brokerageRate = brokerageRate.toFixed(2);


        $brokerageTr.find('td.receivableAmount').html(receivableAmount);
        $brokerageTr.find('input.receivableAmount').val(receivableAmount);

        $brokerageTr.find('td.brokerageRate').html(brokerageRate); //比例

        //计算佣金
        var payAmount = (_val/premiumAmountAll)*payAmountAll;
        payAmount = payAmount.toFixed(2);
        var firstPayAmount = (payAmountAll-payAmount).toFixed(2);

        $commissionTr.find('td.receivableAmount').html(payAmount);
        $commissionTr.find('input.receivableAmount').val(payAmount);
    }
    

    addTimeToList();

    function showVal(val){
    	return (val == 0 ? "" :val);
    }
    
    /**
     * 将缴费计划中的应收日期分别赋值的经纪费的应付日期 和 佣金中的应付日期
     */
    function addTimeToList () {
    	var payPlanLen = $("#payPlanLen").val();
    	var len = parseInt(payPlanLen);
    	if (len > 0) {
    		for (var i = 0;i<len;i++) {
    			if (i == 0) {
    				var $Tr = $(".renvalwayPlanTable").find("input[value=首年]").eq(0).parents('tr');
    				var firstPlanPayDate = $Tr.find('td.planPayDate').html();
    				$(".brokerageTab").find('tr').eq(i+1).find('.planPayDate').html(firstPlanPayDate);
    				$(".brokerageTab").find('tr').eq(i+1).find('input[name*=planPayDate]').val(firstPlanPayDate);
    				$(".commissionTab").find('tr').eq(i+1).find('.planPayDate').html(firstPlanPayDate);
    				$(".commissionTab").find('tr').eq(i+1).find('input[name*=planPayDate]').val(firstPlanPayDate);
    			} else {
    				var $Tr = $(".renvalwayPlanTable").find("input[value=第"+(i+1)+"年]").eq(0).parents('tr');
    				var firstPlanPayDate = $Tr.find('td.planPayDate').html();
    				$(".brokerageTab").find('tr').eq(i+1).find('.planPayDate').html(firstPlanPayDate);
    				$(".brokerageTab").find('tr').eq(i+1).find('input[name*=planPayDate]').val(firstPlanPayDate);
    				$(".commissionTab").find('tr').eq(i+1).find('.planPayDate').html(firstPlanPayDate);
    				$(".commissionTab").find('tr').eq(i+1).find('input[name*=planPayDate]').val(firstPlanPayDate);
    			}
    			
    		}
    	}
    }
    
	// ------ 审核通用部分 start
	var auditProject = require('zyw/auditProject');
	var _auditProject = new auditProject("auditSubmitBtn");
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit({}).initPrint('打印的url');

	// ------ 审核通用部分 end
	//
	//
	// ------ 侧边栏 start
	var publicOPN = new (require('zyw/publicOPN'))();

	publicOPN.init().doRender();
	// ------ 侧边栏 end


});