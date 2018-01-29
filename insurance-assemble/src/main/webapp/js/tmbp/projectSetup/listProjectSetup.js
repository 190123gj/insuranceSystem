define	(function(require, exports, module) {
	require('zyw/publicPage');
	var util = require('util');
	var deleteObj = $(".deleteObj");
	var revokeObj = $(".revoke");
	var endObj = $(".end");
	
	$.each(deleteObj,function(i,v){
		var _cur = $(this);
		_cur.click(function(){
			Y.confirm('提示','确认删除:'+_cur.attr("paramName")+'吗？',function(opn){
				if (opn == 'yes') {
					util.ajax({
						url:'/insurance/projectSetup/deleteConfirm.json',
						data:{projectSetupId:_cur.attr("projectSetupId")},
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
	
		$.each(revokeObj,function(i,v){
		var _cur = $(this);
		_cur.click(function(){
			Y.confirm('提示','确认撤销'+_cur.attr("paramName")+'吗？',function(opn){
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
				Y.confirm('提示','确认作废:'+_cur.attr("paramName")+'吗？',function(opn){
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
});