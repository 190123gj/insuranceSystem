define	(function(require, exports, module) {
    require('Y-msg');
    require('zyw/publicPage');
    require('tmbp/upAttachModifyNew');
    require('tmbp/submit.common');

    var $body = $('body');
	var fnListSearchBtn = $("#fnListSearchBtn");
	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
	
	function unique(arr) {
		   var result = [], hash = {};
		   for (var i = 0, elem; (elem = arr[i]) != null; i++) {
		       if (!hash[elem]) {
		           result.push(elem);
		           hash[elem] = true;
		       }
		   }
		return result;
	}

    $body.on('click','.updateStatus', function () {
        var $self = $(this);
        var singleItems = !$self.hasClass('updateAllcheckedItems');
        var ids = !singleItems ? $self.siblings('.countCheckedItensData').find('.settlementCompanyIds').val() : '';//用来判断是否用数据被选中
        var insuranceCompanyIds = !singleItems ? $self.siblings('.countCheckedItensData').find('.insuranceCompanyId').val() : '';
        var brokerAmount = !singleItems ? $self.siblings('.countCheckedItensData').find('.brokerAmount').val() : '';
        if(!singleItems && !!ids){
        	var companyIds = insuranceCompanyIds.split(",");
        	//将业务员去重
        	companyIds = unique(companyIds);
        	if (companyIds.length>1) {
        		 Y.alert('提示','请选择同一保险公司的保单进行结算！');
        		return;
        	}
        	//重新赋值，只能是同一个业务员申请的结算数据
        	$self.siblings('.countCheckedItensData').find('.insuranceCompanyId').val(companyIds);
        	window.location.href = "/insurance/settlementCompanyBillProcess/addSettle.htm?insuranceCompanyId="+companyIds+"&settlementCompanyId="+ids+"&brokerAmount="+brokerAmount;
        }else {
            Y.alert('提示','请先选择要批量更新的数据！');
            return;
        };
    });
    //单条数据选择
    $body.on('click','.checkItem', function () {
        var $self = $(this);
        var $allCheckBoxs = $self.parents('table').find('.checkItem');
        var countIds = [];//缓存id
        var countCompanyIds = [];
        var countMoney = 0;//缓存金额
        var countHasChecked = 0;//缓存已经选中的条数，用于和所有数据总条数对比，如果两者相等，则全选的checkbox按钮则更新状态为选中

        $.each($allCheckBoxs,function (i,el) {
            if($(el).prop('checked') == true) {
            	var companyId = $(el).parents('tr').find('.insuranceCompanyId').val();
            	var successStatus =  $(el).parents('tr').find('.status').val();
            	if (successStatus == "untreated") {
            		 countIds.push($(el).val());
            		 countCompanyIds.push(companyId);
                     countMoney += parseFloat($(el).parents('tr').find('.brokerAmount').html());
                     countHasChecked++;
            	}
            }
        });
        //更新全选按钮的状态
        if(countHasChecked == $allCheckBoxs.length) {
            $('input.checkAllItems').prop('checked',true);
        }else {
            $('input.checkAllItems').removeProp('checked');
        };

        $('.countCheckedItensData')
        	.find('.settlementCompanyIds').val(countIds).end()
            .find('.allItems').html(countIds.length).end()
            .find('.accountBalance').html(countMoney).end()
            .find('.brokerAmount').val(countMoney).end()
            .find('.insuranceCompanyId').val(countCompanyIds);

    });

    //全选，反选
    $body.on('click','.checkAllItems', function () {
        var $self = $(this);
        var $allCheckBoxs = $('.checkItem');
        var countIds = [];
        var countCompanyIds = [];
        var countMoney = 0;
        var checked = $self.prop('checked');

        //当不是checkbox时，只支持全选,并且更新全选按钮状态
        if(typeof checked == 'undefined'){
            checked = true;
            $('input.checkAllItems').prop('checked',true);
        };

        $.each($allCheckBoxs,function (i,el) {
            $(el).prop('checked',checked);
            if(checked){
            	var companyId = $(el).parents('tr').find('.insuranceCompanyId').val();
            	var successStatus =  $(el).parents('tr').find('.status').val();
            	if (successStatus =="untreated") {
            		countCompanyIds.push(companyId);
            		countIds.push($(el).val());
            		countMoney += parseFloat($(el).parents('tr').find('.brokerAmount').html());
            	}
            }
        });

        $('.countCheckedItensData').find('.settlementCompanyIds').val(countIds).end()
        .find('.allItems').html(countIds.length).end()
        .find('.accountBalance').html(countMoney).end()
        .find('.brokerAmount').val(countMoney).end()
        .find('.insuranceCompanyId').val(countCompanyIds);

    });

});