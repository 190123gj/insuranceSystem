define(function(require, exports, module) {
	//选择单个项目，通过编号查询其详情
	//
	var util = require('util');

	var getList = require('zyw/getList');

	var $body = $('body');

	function searchForDetails(obj) {

		/**
		 * obj
		 * ajaxListUrl string
		 * btn string jQuery select
		 * codeInput string
		 * tpl: obj tbody\form\thead\item
		 * ajaxDetailsUrl: string 查询详情的ajaxUrl
		 * fills: arr 如果没有回调，使用fills里面的填充方式
		 * callback 查询详情后的ajax回调
		 * DetailCode  查询详情时的code
		 */

		//初始化弹出列表
		var _getList = new getList();

		_getList.init({
			title: '选择',
			ajaxUrl: obj.ajaxListUrl,
			ajaxListUrlFun: obj.ajaxListUrlFun,
			btn: obj.btn,
			input: obj.codeInput.split(','),
			tpl: obj.tpl
		});

		var DetailCode = obj.DetailCode || 'projectCode';

		var _data = {};


		//绑定事件
		$body.on('change', '#' + obj.codeInput.split(',')[0], function() {

			var self = this;

			_data[DetailCode] = self.value;

			util.ajax({
				url: obj.ajaxDetailsUrl,
				data: _data,
				success: function(res) {

					if (res.success) {
						var _data = res.data;
						var _fills;

						if (obj.callback) {

							obj.callback(_data);

						} else {

							for (var i = obj.fills.length - 1; i >= 0; i--) {

								_fills = obj.fills[i];
								toFill(_fills.type, $body.find(_fills.select), _data[_fills.key]);

							}

						}

					} else {
						Y.alert('提示', res.message);
					}
				}
			});

		});

		function toFill(type, $select, value) {
			switch (type) {
				case 'html':
					$select.html(value);
					break;
				case 'text':
					$select.val(value);
					break;
			}
		}

	}

	module.exports = searchForDetails;

});