define(function(require, exports, module) {
	require('zyw/publicPage');
	require('Y-msg');
	//require('input.limit');
	var util = require('util');


	$('#clearCache').click(function() {

		util.ajax({
			url: '/systemMg/clearCache.htm',
			success: function(res) {
				Y.alert('提示', res.message);
			}
		});

	});

	$('.del').on('click', function() {

		var _this = $(this);

		Y.confirm('提示', '确认删除参数：' + _this.parents('tr').attr('paramName') + '吗？', function(opn) {
			if (opn == 'yes') {

				util.ajax({
					url: '/systemMg/sysparam/delete.json',
					data: {
						paramName: _this.parents('tr').attr('paramName')
					},
					success: function(res) {
						Y.alert('提示', res.message);
						if (res.success) {
							$('#searchForm').submit();
						}
					}
				});

			}
		});

	});

	var $editorBox = $('#editorBox'),
		$editorTiele = $editorBox.find('#editorTitle'),
		$fnInput = $('.fnInput'),
		$paramName = $('#paramName'),
		actionUrl = {
			add: '/systemMg/sysparam/add.json',
			update: '/systemMg/sysparam/mod.json'
		},
		thisUrl;

	$editorBox.on('click', '.close', function() {

		$editorBox.addClass('fn-hide').find('input,textarea').each(function(index, el) {
			el.value = '';
		});

		$paramName.removeAttr('readonly');

	}).on('click', '#editorSure', function() {

		var _isPass = true;

		$fnInput.each(function(index, el) {
			if (!!!el.value) {
				_isPass = false;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '请填写参数名和参数值');
			return;
		}

		util.ajax({
			url: actionUrl[thisUrl],
			data: $editorBox.serialize(),
			success: function(res) {

				if (res.success) {

					if (thisUrl == 'add') {
						window.location.href = '/systemMg/sysparam/list.htm';
					} else {
						window.location.reload(true);
					}

				} else {
					Y.alert('提醒', res.message);
				}

			}
		});

	});

	//新增
	$('#addParam').on('click', function() {
		$editorTiele.html('新增参数');
		$editorBox.removeClass('fn-hide');
		thisUrl = 'add';
	});
	//编辑
	$('.edit').on('click', function() {

		var _$tr = $(this).parents('tr');

		$editorBox.find('input,textarea').each(function(index, el) {

			var _this = $(this),
				_alias = _this.attr('alias');

			_this.val(_$tr.attr(_alias));

		});
		$paramName.attr('readonly', 'readonly');
		$editorTiele.html('编辑参数');
		$editorBox.removeClass('fn-hide');
		thisUrl = 'update';

	});

});