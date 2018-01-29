define(function(require, exports, module) {

	require('zyw/publicPage');
	require('input.limit');

	var util = require('util');

	//------ 新增模板 start
	var editorUE,
		$form = $('#form'),
		$fnInput = $('.fnInput'),
		$fnBPMCheckbox = $('#fnBPMCheckbox'), //BPM配置
		$fnNotBPMCheckbox = $('.fnNotBPMCheckbox'), //非BPM配置
		$fnMsgTpl = $('#fnMsgTpl'), //消息模板
		$fnNoticeType = $('#fnNoticeType'); //通知类型

	if (document.getElementById('editor')) {
		editorUE = UE.getEditor('editor');
		editorUE.ready(function() {
			editorUE.setHeight(300);
		})

	}

	//结果通知，不能设置BPM配置
	function disabledBPMSet(value) {
		if (value == 'RESULT') {
			$fnBPMCheckbox.prop('disabled', 'disabled');
			$fnBPMCheckbox.removeProp('checked');
			$fnNotBPMCheckbox.removeProp('disabled');
		} else {
			$fnBPMCheckbox.removeProp('disabled');
		}
	}

	disabledBPMSet($fnNoticeType.val());

	//BPM配置的配置与其他互斥
	$fnBPMCheckbox.on('click', function() {
		if ($fnBPMCheckbox.prop('checked')) {
			$fnNotBPMCheckbox.prop('disabled', 'disabled').removeProp('checked');
		} else {
			$fnNotBPMCheckbox.removeProp('disabled');
		}
	});

	$form.on('change', '#fnMsgTpl,#fnNoticeType', function() {
		// 是否已有模板

		disabledBPMSet($fnNoticeType.val());

		if (!!!$fnMsgTpl.val() || !!!$fnNoticeType.val()) {
			return;
		}

		var _data = 'formCode=' + $fnMsgTpl.val() + '&type=' + $fnNoticeType.val()

		$.ajax({
			url: '/systemMg/messageTemplete/checkExist.json?' + _data + '&_time=' + (new Date()).getTime(),
			type: 'POST',
			dataType: 'json',
			success: function(res) {
				if (res.success && res.isExist) {
					window.location.href = '/systemMg/messageTemplete/form.htm?' + _data
				}
			}
		});

	}).on('focus', '#fnContentTxt', function() {

		// 如果文本内容没有值，就使用纯文本的消息内容填充
		if (!!this.value.replace(/\s/g, '')) {
			return;
		}
		this.value = editorUE.getContentTxt();

	}).on('click', '#fnUpdateMsg', function() {

		//同步消息
		Y.confirm('提示', '确定同步 消息内容 到 文本内容 ？<br>此操作会覆盖原文本内容', function(opn) {

			if (opn == 'yes') {

				document.getElementById('fnContentTxt').value = editorUE.getContentTxt();

			}

		});

	}).on('click', '#submit', function() {

		var _isPass = true,
			_isPassEq;

		$fnInput.each(function(index, el) {
			if (!!!el.value.replace(/\s/g, '')) {
				_isPass = false;
				_isPassEq = (_isPassEq >= 0) ? _isPassEq : index;
			}
		});

		if (!_isPass) {
			Y.alert('提示', '还有未填写的内容', function() {
				$fnInput.eq(_isPassEq).focus();
			});
			return;
		}

		document.getElementById('contentHtml').value = escape(editorUE.getContent());

		util.ajax({
			url: $form.attr('action'),
			data: $form.serialize(),
			success: function(res) {
				if (res.success) {
					Y.alert('提示', '操作成功', function() {
						window.location.href = '/systemMg/messageTemplete/list.htm';
					})
				} else {
					Y.alert('提示', res.message)
				}
			}
		});

	}).on('click', '#fnLabelBtn', function() {
		$fnLabelBox.removeClass('fn-hide');
	});
	
	
	$(".del").click(function(){
		
		var _tr = $(this).parent().parent(),
			templeteId = _tr.attr("itemId");
		
		//同步消息
		Y.confirm('提示', '确认删除该模板么', function(opn) {

			if (opn == 'yes') {
				util.ajax({
					url: "/systemMg/messageTemplete/delete.json",
					data: {"templeteId" : templeteId},
					success: function(res) {
						if (res.success) {
							window.location.href = '/systemMg/messageTemplete/list.htm';
						} else {
							Y.alert('提示', res.message)
						}
					}
				});
			}
		});		
		
	});

	// 常用变量
	var $fnLabelBox = $('#fnLabelBox'),
		$fnLabelSelect = $fnLabelBox.find('#fnLabelSelect');

	$fnLabelBox.on('click', '.close', function() {
		$fnLabelBox.addClass('fn-hide');
		$fnLabelSelect.find('option').removeProp('selected');
	}).on('click', '.sure', function() {
		editorUE.execCommand('insertHtml', $fnLabelSelect.val());
		$fnLabelBox.addClass('fn-hide');
		$fnLabelSelect.find('option').removeProp('selected');
	})

	//------ 新增模板 end

});