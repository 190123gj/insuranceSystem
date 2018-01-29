define(function(require, exports, module) {

	require('zyw/publicPage');
	require('zyw/init');
	require('Y-msg');

	var util = require('util');

	var $opnBtn = $('#opnBtn'),
		$list = $('#list');

	$list.on('click', '.fnDel', function() {
		var _id = $(this).parents('tr').find('.fnCheck').val();
		doAjax(_id, true);
	}).on('click', '.fnAuditMoreExamineRecord', function(event) {
		event.preventDefault();
		var content = $(this).find(".opinion-detail").html();
		if(content){
			content = content.replace(/\s+/g, '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
		}
		$('body').Y('Window', {
			content: creatMoreApprovalOpinion(content),
			contentClone: true,
			key: 'modalwnd',
			title: '审批意见'
		});	
	});
	
	// 审批意见
	function creatMoreApprovalOpinion(str) {
		return '<div class="apply-org-form-in fn-w500">' + str + '<div class="fn-blank20"></div></div>';
	}

	//已选择的id数据
	function getChecked(isAll) {

		var _arr = [];
		$list.find('.fnCheck' + (isAll ? '' : ':checked')).each(function(index, el) {
			_arr.push(el.value);
		});

		return _arr.toString();

	}

	var urls = {};
	urls['COLLECT'] = '/systemMg/message/user/update.json?type=COLLECT';
	urls['READ'] = '/systemMg/message/user/update.json?type=READ';
	urls['DELETE'] = '/systemMg/message/user/delete.json';

	//删除的操作
	function doAjax(ids, type) {
		util.ajax({
			url: urls[type],
			data: {
				'receivedId': ids
			},
			success: function(res) {
				Y.alert('提示', res.success ? '操作成功' : res.message, function() {
					window.location.reload(true);
				});
			}
		});

	}

	$opnBtn.on('click', '.fnBtnDel,.fnBtnRead,.fnBtnCollect', function() {

		var _this = $(this),
			_ids = getChecked(_this.hasClass('all') ? true : false);

		if (!!!_ids) {
			Y.alert('提示', '请选择消息');
			return;
		}

		var type = 'READ';
		if (_this.hasClass('fnBtnDel')) {
			type = 'DELETE';
		} else if (_this.hasClass('fnBtnRead')) {
			type = 'READ';
		} else {
			type = 'COLLECT';
		}
		doAjax(_ids, type);
	});

	//全部标记为已读
	$(".fnBtnReadAll").click(function() {
		$.ajax({
			url: '/systemMg/message/user/update.json?readAll=YES',
			type: 'get',
			async: false,
			success: function(data, success) {

				var _tr = $(".row");

				if (!data.success) {
					return;
				}

				_tr.find('b').css({
					'fontWeight': 'normal'
				});

				_tr.attr("isRead", "yes")

				checkUnReadMessage();
			}
		});
	});


	/**
	 * table行展开折叠
	 */
	var table = $('#list'),
		expandCls = 'j-expand';

	table.on('click', '.row td', function() {
		var _this = $(this),
			_tr = $(this).parent(),
			_sibling = _tr.next('.detail');

		var receivedId = _tr.attr("receivedId");
		var status = $('[name=status]').val();
		if (!_tr.is('.' + expandCls)) {
			_sibling.fadeIn();
			_tr.addClass(expandCls);
			if (_tr.attr("isRead") == "no") {
				$.ajax({
					url: '/systemMg/message/user/update.json?receivedId=' + receivedId + '&type=READ&status=' + status,
					type: 'get',
					async: false,
					success: function(data, success) {
						if (!data.success) {
							return;
						}
						_tr.find('b').css({
							'fontWeight': 'normal'
						});

						_tr.attr("isRead", "yes")

						checkUnReadMessage();
					}
				});
			}
		} else {
			_sibling.fadeOut();
			_tr.removeClass(expandCls);
		}
	});


	//截断字符串

});