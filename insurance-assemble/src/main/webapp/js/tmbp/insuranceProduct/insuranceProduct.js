define	(function(require, exports, module) {

	require('Y-msg');

	require('input.limit');

	require('validate');
	require('zyw/publicPage');

	var util = require('util');

	var getList = require('zyw/getList');

	var selectType = require('tmbp/selectType'); //下拉选择
	var fnListSearchBtn = $("#fnListSearchBtn");
	new selectType({
		selectBoxObj: fnListSearchBtn.find('.selectFnBox'),
		afterChoosedCallback: {
			callbackTargetCommon: function($this) {

				var arrHtml, text, Box;

				text = $this.text();
				Box = $this.parents('.selectFnBox');
				kindsid = $this.attr('kindsid');
				catalogName = $this.attr('valuedata');

				Box.find('.catalogId').val(kindsid);
				Box.find('.catalogName').val(catalogName);

			}
		}
	})

	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
});