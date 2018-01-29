define	(function(require, exports, module) {
    require('Y-msg');
    require('Y-number');
    require('zyw/publicPage');
    require('tmbp/operate.common');
    require('tmbp/submit.common');
    require('tmbp/upAttachModifyNew');
    var util = require('util');
    var $body = $('body');
	var fnListSearchBtn = $("#fnListSearchBtn");
	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
	
	var packetObj = $(".packet");
	
	function showVal(val) {
		return val = (val == null || val == "undefined") ? "":val;
	}
	
    $.each(packetObj,function(i,v){
        var _cur = $(this);
        var id = _cur.parents('tr').attr("id");
        _cur.click(function(){
            Y.confirm('提示','确认要进行拆包操作吗？',function(opn){
                if (opn == 'yes') {
                    util.ajax({
                        url:'/insurance/settlementCompanyBillProcess/packet.json',
                        data:{id:id},
                        success:function(reg){
                            if (reg.success) {
                                Y.alert("提示信息：",reg.message);
                                $("#fnListSearchForm").submit();
                            } else{
                                Y.alert("错误信息：",reg.message);
                            }
                        }
                    });
                }
            })
        });
    })
	
    
    $body.on('click','.confirmInvoice',function(){
    	var $content = $($('#CONFIRMINVOICE').html());
    	 $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn'
        });
    	 $(".invoice_billNo").html($(this).parents('tr').attr("billNo"));
    	 $("#invoiceBillNo").val($(this).parents('tr').attr("billNo"));
   }).on('click','.confirmRecieve',function(){
    	var $content = $($('#CONFIRMRECIEVE').html());
     	 $body.Y('Window', {
             content: $content,
             modal: true,
             key: 'modalwnd',
             simple: true,
             closeEle: '.closeBtn',
             close: function () {
                 $self.parents('tr').removeClass('checkedItem');
             }
         });
     	 $("#settlementCompanyBill").val($(this).parents('tr').attr("id"));
    }).on('click','.confirm',function(){
    	//更改结算单状态为已收款
  	  util.ajax({
          url:'/insurance/settlementCompanyBillProcess/recieve.json',
          data:{id:$("#settlementCompanyBill").val()},
          success:function(reg){
              if (reg.success) {
            	var modalwnd = Y.getCmp('modalwnd');
            	modalwnd.close();
            	$("#fnListSearchForm").submit();
              } else{
                  Y.alert("错误信息：",reg.message);
              }
          }
      });
    }).on('click','.status',function(){
    	var $self = $(this);
    	var val = $self.attr("val");
    	$("#status").val(val);
    	$("#fnListSearchForm").submit();
    }).on('click','.lookDetail',function(){
    	var $self = $(this);
    	var $content = $($('#PERSONCOMMISSIONUPDATE').html());
    	  util.ajax({
              url:'/insurance/settlementCompanyBillProcess/detail.json',
              data:{id:$self.parents('tr').attr("id")},
              success:function(reg){
                  if (reg.success) {
                	  console.log(reg);
                	  var billNo = reg.data.billNo; //结算编号
                	  var list = reg.data.datas; //经纪费明细
                	  var content = "";
                	  $.each(list,function(i,el){
                		  content += "<tr>";
                		  content += "<td>"+(i+1)+"</td>"
                		  content += "<td>"+showVal($(el).attr("insuranceNo"))+"</td>"
                		  content += "<td>"+showVal($(el).attr("customerUserName"))+"</td>"
                		  content += "<td>"+showVal($(el).attr("insuancePersonName"))+"</td>"
                		  content += "<td>"+showVal($(el).attr("premiumAmount").amount)+"</td>"
                		  content += "<td>"+$(el).attr("rate")+"</td>"
                		  content += "<td>"+$(el).attr("brokerAmount").amount+"</td>"
                		  content += "</tr>";
                	  });
                	  $content.find('.billNo').html(billNo);
                	  $content.find('tbody').html(content);
                  } else{
                      Y.alert("错误信息：",reg.message);
                  }
              }
          });
    	 $body.Y('Window', {
             content: $content,
             modal: true,
             key: 'modalwnd',
             simple: true,
             closeEle: '.closeBtn',
             close: function () {
                 $self.parents('tr').removeClass('checkedItem');
             }
         });


    });

	$(window).on('resize',function () {
	    var $this = $("#billingForm");
	    console.log($this.width(),$body.width());
	    if($this.width()>$body.width()){
            $("#billingForm").addClass('billingForm');
        }else {
            $("#billingForm").removeClass('billingForm');
        }

    });
    $body.on('click','.billing',function(){
        var $self = $(this);
        var $content = $($('#BILLING').html());
        $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn',
            close: function () {
                $self.parents('tr').removeClass('checkedItem');
            }
        });
        var billNo =  $self.parents('tr').attr("billNo");
        var insuranceCompanyId = $self.parents('tr').attr("insuranceCompanyId");
        var insuranceCompanyName = $self.parents('tr').attr("insuranceCompanyName");
        //结算单号
         $content.find('.billNo').html(billNo);
         $content.find('input[name="settlementNo"]').val(billNo);
         var $applyTime = $content.find('.applyTime');
         var $applyUserId = $content.find('.applyUserId');
         var $applyUserName = $content.find('.applyUserName');
         var $applyUserDept =  $content.find('.applyUserDept');
     	  util.ajax({
              url:'/insurance/invoiceRequisition/getInvoiceInfo.json',
              data:{billNo:billNo,insuranceCompanyId:insuranceCompanyId,insuranceCompanyName:insuranceCompanyName},
              success:function(ret){
                 if (ret.success) {
                	 var applyId = ret.applyId;
                	 //申请时间
                	 $applyTime.html(ret.applyTime);
                	 $content.find('input[name="applyTime"]').val(ret.applyTime);
                	 //申请人
                	 $applyUserName.html(ret.applyName);
                	 $content.find('input[name="applyUserId"]').val(applyId);
                	 $content.find('input[name="applyUserName"]').val(ret.applyName);
                	 //申请人所属部门
                	 $applyUserDept.html(ret.deptName);
                	 $content.find('input[name="applyDeptment"]').val(ret.deptName);
                	 //保险公司
                	 $content.find('input[name="insuranceCompanyId"]').val(insuranceCompanyId);
                	 $content.find('input[name="insuranceCompanyName"]').val(insuranceCompanyName);
                	 $content.find('.insuranceCompanyName').html(insuranceCompanyName);
                	 //保单号
                	 $content.find('.insurance_no').html(ret.billNos);
                	 $content.find('input[name="insuranceNo"]').val(ret.billNos);
                	 //保险公司纳税信息
                	 var valueTaxInfo = ret.valueTaxInfo;
                	 $content.find('.identifyNumber').html(valueTaxInfo.identifyNumber);
                	 $content.find('.orgAddress').html(valueTaxInfo.orgAddress);
                	 $content.find('.mobile').html(valueTaxInfo.mobile);
                	 $content.find('.openBankName').html(valueTaxInfo.openBankName);
                	 $content.find('.bankCardNo').html(valueTaxInfo.bankCardNo);
                	 //大小写金额
                	 $content.find('.lowercase').html(ret.lowercaseAmount.amount);
                	 $content.find('.capital').html(Y.Number(ret.lowercaseAmount.amount).digitUppercase());
                	 //结算单清单
                	 var data = ret.data;
                	 var _content = "";
                	 if (!!data){
                		 $.each(data,function(i,v){
                			 _content += "<tr>"
                				 _content += "<td>"+$(v).attr("insuranceNo")+"</td>"
                				 _content += "<td>"+$(v).attr("catalogNames")+"</td>"
                				 _content += "<td>"+$(v).attr("insuranceDate")+"</td>"
                				 _content += "<td>"+$(v).attr("billCustomerName")+"</td>"
                				 _content += "<td>"+$(v).attr("premiumAmount").amount+"</td>"
                				 _content += "<td>"+$(v).attr("borkerageRate")+"</td>"
                				 _content += "<td>"+$(v).attr("brokerageFee").amount+"</td>"
                		     _content += "</tr>"
                		 });
                	 }
                	 $("#settlementBillInfo").html(_content);
                 }
              }
          });
    }).on('click','.supplyInvoiceInfo',function(){
        var $self = $(this);
        var $content = $($('#CONFIRMINVOICE').html());
        $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn',
            close: function () {
                $self.parents('tr').removeClass('checkedItem');
            }
        });
        var invoiceId =  $self.parents('tr').attr("invoiceId");
        var billNo = $self.parents('tr').attr("billNo");
        var invoiceNo = $self.parents('tr').attr("invoiceNo");
        var invoiceIssuingTime = $self.parents('tr').attr("invoiceIssuingTime");
        var logisticsCompanyName = $self.parents('tr').attr("logisticsCompanyName");
        var logisticsNumber = $self.parents('tr').attr("logisticsNumber");
        $("#invoiceId").val(invoiceId);
        $("#invoiceBillNo").val(billNo);
        $("#invoiceNo").val(invoiceNo);
        $("#invoiceIssuingTime").val(invoiceIssuingTime);
        $("#logisticsCompanyName").val(logisticsCompanyName);
        $("#logisticsNumber").val(logisticsNumber);
    });
    $body.on('click','.invoiceInfo',function(){
        var $self = $(this);
        var $content = $($('#INVOICEINFO').html());
        $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn',
            close: function () {
                $self.parents('tr').removeClass('checkedItem');
            }
        });
      var billNo = $self.parents('tr').attr("billNo");
      var invoiceNo = $self.parents('tr').attr("invoiceNo");
      var invoiceIssuingTime = $self.parents('tr').attr("invoiceIssuingTime");
      var logisticsCompanyName = $self.parents('tr').attr("logisticsCompanyName");
      var logisticsNumber = $self.parents('tr').attr("logisticsNumber");
      $(".settlementNo").html(billNo);
      $(".settlementInvoiceNo").html(invoiceNo);
      $(".time").html(invoiceIssuingTime);
      $(".companyName").html(logisticsCompanyName);
      $(".flowNo").html(logisticsNumber);
    });

});