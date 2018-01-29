define	(function(require, exports, module) {
    require('Y-msg');
    require('zyw/publicPage');
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
        var $content = $($('#PERSONCOMMISSIONUPDATE').html());
        var singleItems = !$self.hasClass('updateAllcheckedItems');
        var ids = !singleItems ? $self.siblings('.countCheckedItensData').find('.commissionProcessIds').val() : '';//用来判断是否用数据被选中
        var businessUserIds = !singleItems ? $self.siblings('.countCheckedItensData').find('.businessUserId').val() : '';
        
        if(singleItems){
            $content.find('.commissionProcessIds').val($self.parents('tr').find('.checkItem').val());
            $content.find('.businessUserId').val($self.parents('tr').find('.businessUserId').val());
            $content.find('.applyDrawAmount').val(parseFloat($self.parents('tr').find('.applyDrawAmount').html()));
        }else if(!singleItems && !!ids){
        	var userIds = businessUserIds.split(",");
        	//将业务员去重
        	userIds = unique(userIds);
        	if (userIds.length>1) {
        		 Y.alert('提示','请选择同一业务员的申请进行结算！');
        		return;
        	}
        	//重新赋值，只能是同一个业务员申请的结算数据
        	$self.siblings('.countCheckedItensData').find('.businessUserId').val(userIds);
            $content.find('.allCHoosedItems').html($self.siblings('.countCheckedItensData').clone(true));
        }else {
            Y.alert('提示','请先选择要批量更新的数据！');
            return;
        };

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
        // var modalwnd = Y.getCmp('modalwnd');
    }).on('click',".lookReason", function () {
    	var $self = $(this);
    	var $content = $($('#PERSONCOMMISSIONREASON').html());
    	  $body.Y('Window', {
              content: $content,
              modal: true,
              width: 250,
              height:200,
              key: 'modalwnd',
              simple: true,
              closeEle: '.closeBtn'
          });
    	  $content.find("div.reason").html($self.attr("reason"));
    }).on("change","[name='status']",function(){
    	var _this = $(this).val();
    	if (_this == "fail") {
    		$("div.reason").removeClass("fn-hide");
    		$("div.reason").find("textarea").removeAttr("disabled")
    	} else {
    		$("div.reason").addClass("fn-hide");
    		$  ("div.reason").find("textarea").attr("disabled",true)
    	}
    }).on('click','.updateSuccess',function(){
    	var $self = $(this);
    	var $content = $($('#PERSONCOMMISSIONUPDATE').html());
    	var commissionProcessIds =  $self.parents('tr').find('.checkItem').val();
    	var businessUserId = $self.parents('tr').find('.businessUserId').val();
    	var applyDrawAmount = parseFloat($self.parents('tr').find('.applyDrawAmount').html());
          $.ajax({
               url:'/insurance/personCommissionProcess/editSubmit.json',
               type:'post',
               data:{
            	   commissionProcessIds:commissionProcessIds,
            	   businessUserId:businessUserId,
            	   applyDrawAmount:applyDrawAmount,
            	   status:"success"
               },
               success:function (res) {
                   if(res.success){
                	   Y.alert('提示',"结算成功");
                	   setTimeout(function(){
                		   $("#fnListSearchForm").submit();
                	   },1000)
                	 
                   }else {
                       Y.alert('提示',res.message);
                   }
               }
          });
    }).on('click','.updateFail',function(){
    	var $self = $(this);
    	var $content = $($('#PERSONCOMMISSIONUPDATEFAIL').html());
	    	 $content.find('.commissionProcessIds').val($self.parents('tr').find('.checkItem').val());
	         $content.find('.businessUserId').val($self.parents('tr').find('.businessUserId').val());
	         $content.find('.applyDrawAmount').val(parseFloat($self.parents('tr').find('.applyDrawAmount').html()));
	    	 $body.Y('Window', {
	             content: $content,
	             modal: true,
	             width: 410,
	             key: 'modalwnd',
	             simple: true,
	             closeEle: '.closeBtn'
	         });
    }).on('click','.failBtn',function(){
    	var commissionProcessIds = $("#failForm").find("input[name='commissionProcessIds']").val();
    	var status = "fail";
    	var businessUserId = $("#failForm").find("input[name='businessUserId']").val();
    	var applyDrawAmount = $("#failForm").find("input[name='applyDrawAmount']").val();
    	var reason = $("#failForm").find("[name='reason']").val();
        $.ajax({
            url:'/insurance/personCommissionProcess/editSubmit.json',
            type:'post',
            data:{
         	   commissionProcessIds:commissionProcessIds,
         	   businessUserId:businessUserId,
         	   applyDrawAmount:applyDrawAmount,
         	   reason:reason,
         	   status:status
            },
            success:function (res) {
                if(res.success){
             	   Y.alert('提示',"结算成功");
             	   setTimeout(function(){
             		   $("#fnListSearchForm").submit();
             	   },1000)
             	 
                }else {
                    Y.alert('提示',res.message);
                }
            }
       });
    });
    //单条数据选择
    $body.on('click','.checkItem', function () {
        var $self = $(this);
        var $allCheckBoxs = $self.parents('table').find('.checkItem');
        var countIds = [];//缓存id
        var countBusinessUserIds = [];
        var countMoney = 0;//缓存金额
        var countHasChecked = 0;//缓存已经选中的条数，用于和所有数据总条数对比，如果两者相等，则全选的checkbox按钮则更新状态为选中

        $.each($allCheckBoxs,function (i,el) {
            if($(el).prop('checked') == true) {
            	var businessUserId = $(el).parents('tr').find('.businessUserId').val();
            	var successStatus =  $(el).parents('tr').find('.status').val();
            	if (successStatus == "processing") {
            		 countIds.push($(el).val());
                     countBusinessUserIds.push(businessUserId);
                     countMoney += parseFloat($(el).parents('tr').find('.applyDrawAmount').html());
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
        	.find('.commissionProcessIds').val(countIds).end()
            .find('.allItems').html(countIds.length).end()
            .find('.accountBalance').html(countMoney).end()
            .find('.applyDrawAmount').val(countMoney).end()
            .find('.businessUserId').val(countBusinessUserIds);

    });

    //全选，反选
    $body.on('click','.checkAllItems', function () {
        var $self = $(this);
        var $allCheckBoxs = $('.checkItem');
        var countIds = [];
        var countBusinessUserIds = [];
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
            	var businessUserId = $(el).parents('tr').find('.businessUserId').val();
            	var successStatus =  $(el).parents('tr').find('.status').val();
            	if (successStatus =="processing") {
            		countBusinessUserIds.push(businessUserId);
            		countIds.push($(el).val());
            		countMoney += parseFloat($(el).parents('tr').find('.applyDrawAmount').html());
            	}
            }
        });

        $('.countCheckedItensData').find('.commissionProcessIds').val(countIds).end()
        .find('.allItems').html(countIds.length).end()
        .find('.accountBalance').html(countMoney).end()
        .find('.applyDrawAmount').val(countMoney).end()
        .find('.businessUserId').val(countBusinessUserIds);

    });

});