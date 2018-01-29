define(function(require, exports, module) {

	require('zyw/publicPage');
	require('Y-msg');

	require('input.limit');

	require('validate');

	var util = require('util');

	var $form = $('#form');

	var REQUIRERULES = { // 验证规则
		rules : {},
		messages : {}
	};

	// 必填
	$('.fnRequired').each(function(index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			required : true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			required : '必填'
		});

	});

	// 时间格式化
	$('.fnInputTime').on('change', function() {

		if (!!!this.value) {
			return;
		}

		var _thisVal = '';

		// 通过`:`分割

		var _thisValArr = this.value.replace(/：/g, ':').replace(/[^\d:]/g, '').split(':');

		_thisVal += getMM(+_thisValArr[0]);

		if (_thisValArr[1]) {
			_thisVal += ':' + getSS(+_thisValArr[1]);
		} else {
			_thisVal += ':00';
		}

		this.value = _thisVal;

	});

	function getMM(val) {

		if (val < 0) {
			val = 0;
		}

		if (val > 23) {
			val = 23
		}

		return util.getTwoIntegers(val);

	}

	function getSS(val) {

		if (val < 0) {
			val = 0;
		}

		if (val > 59) {
			val = 59
		}

		return util.getTwoIntegers(val);

	}

	$form.validate($.extend(true, REQUIRERULES, {
		errorClass : 'error-tip',
		errorElement : 'b',
		ignore : '.ignore',
		onkeyup : false,
		errorPlacement : function(error, element) {

			if (element.hasClass('fnErrorAfter')) {

				element.after(error);

			} else {

				element.parent().append(error);

			}

		},
		submitHandler : function() {

			// 时间段的校验

			var _st = (new Date('2015-07-25 ' + $('[name="backupTimeBegin"]').val())).getTime(), _et = (new Date('2015-07-25 ' + $('[name="backupTimeEnd"]').val())).getTime();

			if (_st >= _et) {

				Y.alert('提示', '备份开始时间不能大约或等于结束时间');
				return;

			}

			util.ajax({
				url : $form.attr('action'),
				data : $form.serialize(),
				success : function(res) {

					if (res.success) {
						Y.alert('提示', '操作成功', function() {
							window.location.href = '/systemMg/dbbackup/config/list.htm';
						});
					} else {
						Y.alert('操作失败', res.message);
					}

				}
			});

		}
	}));

	$(".delete").click(function() {
		var configId = $(this).parents("tr").attr("configId");
		Y.confirm('提示', '确认删除该配置么', function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url : "/systemMg/dbbackup/config/delete.htm",
					data : {
						"configId" : configId
					},
					success : function(res) {
						if (res.success) {
							window.location.href = '/systemMg/dbbackup/config/list.htm';
						} else {
							Y.alert('提示', res.message);
						}
					}
				});
			}
		});
	});
	
	$(".delFile").click(function() {
		var _tr =  $(this).parents("tr");
		var logId = _tr.attr("logId"),
			fileName = _tr.attr("fileName");
		Y.confirm('提示', '确认删除文件 ' + fileName, function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url : "/systemMg/dbbackup/log/delFile.htm",
					data : {
						"logId" : logId
					},
					success : function(res) {
						if (res.success) {
							window.location.href = '/systemMg/dbbackup/log/list.htm';
						} else {
							Y.alert('提示', res.message);
						}
					}
				});
			}
		});
	});

	$(".backup").click(function() {
		var _tr =  $(this).parents("tr");
		var configId = _tr.attr("configId"),
			schemeName = _tr.attr("schemeName");
		Y.confirm('提示', '确认立即备份数据库 ' + schemeName, function(opn) {
			if (opn == 'yes') {
				util.ajax({
					url : "/systemMg/dbbackup/manual.htm",
					data : {
						"configId" : configId
					},
					success : function(res) {
						if (res.success) {
							Y.alert('提示', res.message);
						} else {
							Y.alert('提示', res.message);
						}
					}
				});
			}
		});
	});

	$(".changeInUse").click(function() {
		var configId = $(this).parents("tr").attr("configId");
		util.ajax({
			url : "/systemMg/dbbackup/config/changeInUse.htm",
			data : {
				"configId" : configId
			},
			success : function(res) {
				if (res.success) {
					window.location.href = '/systemMg/dbbackup/config/list.htm';
				} else {
					Y.alert('提示', res.message);
				}
			}
		});
	});
});