define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	require('zyw/upAttachModify');
	require('validate');

	var $body = $('body'),
	     BPMiframe = require('BPMiframe');



	//------ 选择组织 start
	// 初始化弹出层
	var selectOrgDialog = new BPMiframe('', {
		'title': '组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name:'name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$body.on('click', '.fnListSearchOrgChoose', function () {
		var $this = $(this),
			_isSingle = $this.attr('single') ? true : false,
			_$id = $("#contractingAgencyId"),
			_$name = $("#contractingAgencyName");

		// 更新弹窗的单选、多选
		selectOrgDialog.resetSrc('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=' + (_isSingle ? 'true' : 'false'));

		//这里也可以更新已选择机构
		selectOrgDialog.obj.arrys = [{
			id: _$id.val(),
			name: _$name.val()
		}];

		selectOrgDialog.init(function (relObj) {

			_$id.val(relObj.orgId).trigger('change').trigger('blur');
			_$name.val(relObj.orgName).trigger('change').trigger('blur');

		});

	});



	var $form = $("#form");
	var validateRules = {
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
		submitHandler: function(form) {
		},
		rules: {
			name: {
				required: true
			}

		},
		messages: {
			name: {
				required: '责任名称必填'
			}
		}
	};
	$form.validate(validateRules);



	//提交
	$('.submit').on('click', function() {
		var url = "/insurance/insuranceExpense/list.htm";
			util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '已提交', function() {
						util.direct(url);
					});
				} else {
					Y.alert('提示', res.message);
				}
			}
		});
	});
});