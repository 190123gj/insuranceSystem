define(function(require, exports, module) {
	//账户激活
	require('validate');
	require('validate.extend');

	require('Y-msg');
	require('input.limit');

	var util = require('util');

	var $form = $('#form'),
		$getCode = $('#getCode'),
		$mobile = $('#mobile'),
		countdown = 60,
		msgTimer;

	// 自定义密码验证
	$.validator.addMethod('pwAuth', function(value, element, params) {
		if (value.length < 6) {
			return false;
		}
		if (value.length > 20) {
			return false;
		}
		var level = 0, // 密码等级
			isNumber = /\d/g, // 包含数字
			isCapitalLetters = /[A-Z]/g, // 包含大写字母
			isLowercase = /[a-z]/g, // 包含小写字母
			isIllegal = /[^\w\~\!\@\#\$\%\^\&\*]/g; // 除了合法意外的字符

		if (isIllegal.test(value)) {
			return false;
		}

		if (isNumber.test(value)) {
			level += 10;
		}
		if (isCapitalLetters.test(value)) {
			level += 1;
		}
		if (isLowercase.test(value)) {
			level += 1;
		}
		if (level < 11) {
			return false;
		}

		return true;

	}, '请按要求输入密码');

	$form.validate({
		errorClass: 'error-tip',
		errorElement: 'b',
		ignore: '.ignore',
		onkeyup: false,
		errorPlacement: function(error, element) {

			if (element.hasClass('fnErrorAfter')) {

				element.after(error);

			} else {

				element.parent().append(error);

			}

		},
		rules: {
			userName: {
				required: true
			},
			mobile: {
				required: true,
				isMobile: true,
				remote: { // 返回数据中只要 ture 或 false，例如 res.end('true')
					url: '/login/checkUserAndMobile.json', //后台处理程序
					type: 'POST', //数据发送方式
					dataType: 'json', //接受数据格式
					data: { //要传递的数据
						mobile: function() {
							return $mobile[0].value
						},
						userName: function() {
							return document.getElementById('userName').value;
						},
						_time: function() {
							return (new Date()).getTime()
						}
					}
				}
			},
			code: {
				required: true,
				remote: { // 返回数据中只要 ture 或 false，例如 res.end('true')
					url: '/login/equalActiveValidateCode.json', //后台处理程序
					type: 'POST', //数据发送方式
					dataType: 'json', //接受数据格式
					data: { //要传递的数据
						code: function() {
							return document.getElementById('code').value;
						},
						_time: function() {
							return (new Date()).getTime()
						}
					}
				}
			},
			password: {
				required: true,
				pwAuth: true
			},
			repassword: {
				required: true,
				equalTo: '#password'
			}
		},
		messages: {
			userName: {
				required: '请输入用户名'
			},
			mobile: {
				required: '请输入手机号码',
				isMobile: '请输入正确的手机号码',
				remote: '您输入的手机号码和系统数据不匹配，请重新输入或联系管理员'
			},
			code: {
				required: '请输入验证码',
				remote: '请输入正确的验证码'
			},
			password: {
				required: '请输入密码'
			},
			repassword: {
				required: '请再次确认密码',
				equalTo: '确认密码不一致，请重新输入'
			}
		}
	});

	// 提交
	$form.on('click', '#submit', function() {

		if ($form.valid()) {
			util.ajax({
				url: $form.attr('action'),
				data: $form.serialize(),
				success: function(res) {
					if (res.success) {
						Y.alert('提示', '操作成功，现在去登录账户', function() {
							window.location.href = '/'
						})
					} else {
						Y.alert('激活失败', res.message);
					}
				}
			});
		} else {
			Y.alert('提示', '表单填写内容有误，请重新核对');
		}

	});

	// 获取验证码
	$getCode.live('click', function() {

		var _this = $(this);

		if (_this.hasClass('ui-btn-danger')) {
			return;
		}
		_this.removeClass('ui-btn-green').addClass('ui-btn-danger');
		// 发送短信

		setTimeout(function() {

			if ($mobile.hasClass('valid')) {
				util.ajax({
					url: '/login/sendMobileValidateCode.json',
					dataType: 'json', //接受数据格式
					data: {
						mobile: $mobile[0].value,
						userName: document.getElementById('userName').value
					},
					success: function(res) {
						//alert(res);
						if (res.success) {
							Y.alert('提示', '发送成功');

							msgTimer = setInterval(function() {
								countdown--;
								if (countdown >= 1) {
									$getCode.html(countdown + ' s');
								} else {
									$getCode.html('获取验证码').addClass('ui-btn-green').removeClass('ui-btn-danger');
									countdown = 60;
									clearInterval(msgTimer);
									return;
								}
							}, 1000);

						} else {
							Y.alert('发送失败', res.message);
							_this.addClass('ui-btn-green').removeClass('ui-btn-danger');
						}
					}
				});
			} else {
				Y.alert('提示', '您输入的手机号码和系统数据不匹配，请重新输入或联系管理员', function() {
					$mobile.focus();
					_this.addClass('ui-btn-green').removeClass('ui-btn-danger');
				});
			}
		}, 1000);

	});

});