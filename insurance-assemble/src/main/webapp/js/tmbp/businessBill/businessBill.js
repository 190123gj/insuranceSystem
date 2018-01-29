define(function(require, exports, module) {
	require('zyw/publicPage');
	 //Y对话框
    require('Y-msg');

    //md5加密
    var md5 = require('md5');
    //模板引擎
    var template = require('arttemplate');
    //通用方法
    var util = require('util');
    require('Y-htmluploadify');
    require('Y-window');
    var  $body = $('body');
    
    
	var _load = new util.loading();
    $('.fnUpFile').click(function () {
        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/insurance/businessBill/upLoadExcel.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileTypeExts: '*.xls;*.xlsx',
            fileObjName: 'UploadFile',
            onUploadStart: function ($this) {
                _load.open();
            },
            onQueueComplete: function (a, b) {
                _load.close();
            },
            onUploadSuccess: function ($this, data, resfile) {
            		Y.alert("提示信息",'上传成功',function(){
                        window.location="/insurance/businessBill/list.htm";
                    });
            		 
            }
        })
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
        var ids = !singleItems ? $self.siblings('.countCheckedItensData').find('.businessBillIds').val() : '';//用来判断是否用数据被选中
        var insuranceTypeNames = !singleItems ? $self.siblings('.countCheckedItensData').find('.insuranceTypeNames').val() : '';
        if(!singleItems && !!ids){
        	var typeNames = insuranceTypeNames.split(",");
        	//将业务员去重
        	typeNames = unique(typeNames);
        	if (typeNames.length>1) {
        		 Y.alert('提示','请选择车险类型的保单申请结算！');
        		return;
        	}
        	//重新赋值，只能是同一个业务员申请的结算数据
        	$self.siblings('.countCheckedItensData').find('.insuranceTypeNames').val(typeNames);
        	window.location.href = "/insurance/billSettlementApply/list.htm";
        }else {
            Y.alert('提示','请先选择要批量申请结算的数据！');
            return;
        };
    });
    
    
    //单条数据选择
    $body.on('click','.checkItem', function () {
        var $self = $(this);
        var $allCheckBoxs = $self.parents('table').find('.checkItem');
        var countIds = [];//缓存id
        var insuranceTypeNames = [];
        var countHasChecked = 0;//缓存已经选中的条数，用于和所有数据总条数对比，如果两者相等，则全选的checkbox按钮则更新状态为选中

        $.each($allCheckBoxs,function (i,el) {
            if($(el).prop('checked') == true) {
            	var typeNames = $(el).attr("typeName");
            		 countIds.push($(el).val());
            		 insuranceTypeNames.push(typeNames);
                     countHasChecked++;
            }
        });
        //更新全选按钮的状态
        if(countHasChecked == $allCheckBoxs.length) {
            $('input.checkAllItems').prop('checked',true);
        }else {
            $('input.checkAllItems').removeProp('checked');
        };

        $('.countCheckedItensData')
        	.find('.businessBillIds').val(countIds).end()
            .find('.allItems').html(countIds.length).end()
            .find('.insuranceTypeNames').val(insuranceTypeNames);

    });
    
    //全选，反选
    $body.on('click','.checkAllItems', function () {
        var $self = $(this);
        var countIds = [];
        var insuranceTypeNames = [];
        var $allCheckBoxs = $('.checkItem');
        var checked = $self.prop('checked');

        //当不是checkbox时，只支持全选,并且更新全选按钮状态
        if(typeof checked == 'undefined'){
            checked = true;
            $('input.checkAllItems').prop('checked',true);
        };
        $.each($allCheckBoxs,function (i,el) {
            $(el).prop('checked',checked);
            if(checked){
            	insuranceTypeNames.push($(el).attr("typeName"));
            	countIds.push($(el).val());
            }
        });
        
        $('.countCheckedItensData').find('.businessBillIds').val(countIds).end()
        .find('.allItems').html(countIds.length).end()
        .find('.insuranceTypeNames').val(insuranceTypeNames);
    });
    
    
    var $apply = $(".apply")
    
    $.each($apply,function(){
    	
    	$(this).click(function(){
    		
    		util.ajax({
    			url : '/insurance/billSettlementApply/editSubmit.json',
    			data : {businessBillId:$(this).attr("businessBillId")},
    			success : function(res) {
    				if (res.success) {
    					Y.alert('提示信息','结算申请成功');
    					setTimeout(function(){
    						window.location.href = '/insurance/billSettlementApply/list.htm';
    					},1000)
    				} else {
    					Y.alert('操作失败', res.message);
    				}
    			}
    		});
    		
    	});
    })

    
	var deleteObj = $(".deleteObj");
	var revokeObj = $(".revoke");
	var endObj = $(".end");
	var submitObj = $(".submit");
	
	$.each(deleteObj,function(i,v){
		var _cur = $(this);
		_cur.click(function(){
			Y.confirm('提示','确认删除吗？',function(opn){
				if (opn == 'yes') {
					util.ajax({
						url:'/insurance/form/delete.json',
						data:{formId:_cur.attr("formId")},
						success:function(reg){
							if (reg.success) {
								util.ajax({
									url:'/insurance/businessBill/deleteConfirm.json',
									data:{businessBillId:_cur.attr("businessBillId")},
									success:function(reg){
										if (reg.success) {
											Y.alert("提示信息：",reg.message);
											$("#fnListSearchForm").submit();
										} else{
											Y.alert("错误信息：",reg.message);	
										}
									}
								});
							} else{
								Y.alert("错误信息：",reg.message);	
							}
						}
					});
				}
			})
		});
	});
	
		$.each(revokeObj,function(i,v){
		var _cur = $(this);
		_cur.click(function(){
			Y.confirm('提示','确认撤销吗？',function(opn){
				if (opn == 'yes') {
					util.ajax({
						url:'/insurance/form/cancel.json',
						data:{formId:_cur.attr("formId")},
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
	});
		
		
		
		$.each(endObj,function(i,v){
			var _cur = $(this);
			_cur.click(function(){
				Y.confirm('提示','确认作废吗？',function(opn){
					if (opn == 'yes') {
						util.ajax({
							url:'/insurance/form/end.json',
							data:{formId:_cur.attr("formId")},
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
		});
		
		$.each(submitObj,function(i,v){
			var _cur = $(this);
			_cur.click(function(){
				Y.confirm('提示','确认提交？',function(opn){
					if (opn == 'yes') {
						util.ajax({
							url:'/insurance/form/submit.json',
							data:{formId:_cur.attr("formId")},
							success:function(reg){
								if (reg.success) {
									Y.alert('提示 ',reg.message, function() {
										$("#fnListSearchForm").submit();
									});
									
								} else{
									Y.alert("错误信息：",reg.message);	
								}
							}
						});
					}
				})
			});
		});
});