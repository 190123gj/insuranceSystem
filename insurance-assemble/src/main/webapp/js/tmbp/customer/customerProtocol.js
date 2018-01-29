define	(function(require, exports, module) {

	require('Y-msg');

	require('input.limit');

	require('validate');
	
	require('tmbp/upAttachModifyNew');
	 
	require('zyw/publicPage');
	var util = require('util');

	var getList = require('zyw/getList');
	
	var fnListSearchBtn = $("#fnListSearchBtn");
	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
	
	$("a.fnUpAttachBtn").remove();
});