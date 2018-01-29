define	(function(require, exports, module) {

	require('Y-msg');

	require('input.limit');

	require('validate');
	
	require('zyw/publicPage');

	var util = require('util');

	var getList = require('zyw/getList');
	
	var fnListSearchBtn = $("#fnListSearchBtn");
	var deleteObj = $(".deleteObj");
	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
	
	$.each(deleteObj,function(i,v){
		var _cur = $(this);
		_cur.click(function(){
			Y.confirm('提示','确认删除参数:'+_cur.attr("paramName")+'吗？',function(opn){
				if (opn == 'yes') {
					util.ajax({
						url:'/insurance/insuranceLiability/deleteConfirm.json?',
						data:{id:_cur.attr("id")},
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
	
});