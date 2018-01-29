define	(function(require, exports, module) {
	require('zyw/publicPage');
	 //Y对话框
    require('Y-msg');
    //通用方法
    var util = require('util');
	var fnListSearchBtn = $("#fnListSearchBtn");

	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
	
	var revokeObj = $(".revoke");
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
});