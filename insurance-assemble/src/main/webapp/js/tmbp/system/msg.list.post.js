define(function(require, exports, module) {

	require('Y-msg');
	var util = require('util');

	//选择人 start

	var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选
	// 初始化弹出层
	var singleSelectUserDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=false', {
		'title': '人员',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'selectUsers': {
			selectUserIds: '', //已选id,多用户用,隔开
			selectUserNames: '' //已选Name,多用户用,隔开
		},
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});

	var fnChooseName = document.getElementById('fnChooseName'),
		fnChooseId = document.getElementById('fnChooseId'),
		fnChooseAccounts = document.getElementById('fnChooseAccounts');

	// 添加选择后的回调，以及显示弹出层
	$('#fnChooseBtn').on('click', function() {

		//这里也可以更新已选择用户

		singleSelectUserDialog.obj.selectUsers.selectUserIds = fnChooseId.value;
		singleSelectUserDialog.obj.selectUsers.selectUserNames = fnChooseName.value;
		singleSelectUserDialog.obj.selectUsers.selectUserAccounts = fnChooseAccounts.value;

		singleSelectUserDialog.init(function(relObj) {

			fnChooseId.value = relObj.userIds;
			fnChooseName.value = relObj.fullnames;
			fnChooseAccounts.value = relObj.accounts;

		});

	});
	//选择人 end

	var $form = $('#form'),
		$fnInput = $('.fnInput');

	$form.on('click', '#submit', function() {

		var _isPass = true,
			_isPassEq;

		$fnInput.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '还未填写完整', function() {
				$fnInput.eq(_isPassEq).focus();
			});
			return;
		}

		// 接收人、接收人角色 二选一
		if (!!!fnChooseId.value && $('[name="sendUserRole"]:checked').length === 0) {

			Y.alert('提示', '请选择接受人');
			return;

		}

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {

				if (res.success) {
					Y.alert('提示', '已发布', function() {
						window.location.reload(true);
					});
				} else {
					Y.alert('提示', res.message);
				}

			}
		});

	});

});