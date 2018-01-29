define(function(require, exports, module) {
	//修改密码
	require('validate');
	require('Y-msg');
	var util = require('util');

	var $form = $('#form');
	$form.validate({
		errorClass: 'error-tip',
		errorElement: 'b',
		ignore: '.ignore',
		onkeyup: false,
		errorPlacement: function(error, element) {
			element.after(error);
		},
		submitHandler: function(form) {
			util.ajax({
				url: 'url',
				data: $form.serialize(),
				success: function(res) {
					Y.alert('提示', res.messages, function() {
						if (res.success) {
							//修改成功，退出
							window.top.document.getElementById('logout').click();
						}
					});
				}
			});
		},
		rules: {
			oldpassword: {
				required: true
			},
			newpassword: {
				required: true,
				rangelength: [6, 16]
			},
			relpassword: {
				required: true,
				equalTo: '#newPWD'
			}
		},
		messages: {
			oldpassword: {
				required: '请输入当前的密码'
			},
			newpassword: {
				required: '请输入新密码',
				rangelength: '请输入长度在 {0} 到 {1} 之间的密码'
			},
			relpassword: {
				required: '请二次确认新密码',
				equalTo: '请输入正确的新密码'
			}
		}
	});
});