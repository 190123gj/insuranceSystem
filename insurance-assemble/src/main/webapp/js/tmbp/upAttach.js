define(function(require, exports, module) {
	//通用代码
	// <div class="m-item m-item2 fnUpAttach">
	//     <label class="m-label">{{title}}：</label>
	//     <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn"><i class="icon icon-add"></i>上传附件</a>
	//     <input class="fnUpAttachVal" type="hidden" name="">
	//     <div class="fn-blank5"></div>
	//     <div class="m-attach fnUpAttachUl"></div>
	// </div>
	//通用上传附件代码集合
	//查看图片
	require('Y-imageplayer');
	//上传图片
	require('Y-htmluploadify');
	var util = require('util');

	var $body = $('body');

	$body.on('click', '.fnUpAttachBtn', function() {
		//上传附件
		var _this = $(this),
			_filetype = _this.attr('filetype') || '*.jpg; *.jpeg; *.png; *.doc; *.xls; *.docx; *.xlsx; *.pdf', //自定义上传类型
			_maxitem = _this.attr('maxitem') || 99,
			$fnUpAttachBox = _this.parents('.fnUpAttach');
		//是否超过
		if ($fnUpAttachBox.find('.fnAttachItem').length >= _maxitem) {
			Y.alert('提醒', '已超过最大上传个数限制');
			return;
		}
		var htmlupload = Y.create('HtmlUploadify', {
			key: 'up1',
			uploader: '/upload/imagesUpload.htm',
			multi: false,
			auto: true,
			addAble: false,
			fileTypeExts: _filetype,
			fileObjName: 'UploadFile',
			onQueueComplete: function(a, b) {},
			onUploadSuccess: function($this, res, resfile) {
				var _res = $.parseJSON(res);
				if (_res.success) {
					var _file = {
						fileName: _res.data.oldFileName,
						fileUrl: _res.data.url
					}
					upFileCallBack($fnUpAttachBox, _file);
				}
			},
			renderTo: 'body'
		});

	}).on('click', '.fnUpAttachDel', function() {
		//删除某个附件
		var _this = $(this),
			$fnUpAttachBox = _this.parents('.fnUpAttach');

		_this.parent().remove();

		totalVal($fnUpAttachBox);

	}).on('click', '.fnAttachView', function() {
		//查看所有上传的附件
		var _imgs = [];

		$('.fnAttachView').each(function(index, el) {
			var _this = $(this);
			_imgs.push({
				src: _this.parent().attr('val'),
				desc: _this.text()
			});
		});

		Y.create('ImagePlayer', {
			imgs: _imgs,
			index: $(this).index('.fnAttachView'),
			scaleLowerLimit: 0.1,
			loop: false,
			fullAble: false,
			firstTip: '这是第一张了',
			firstTip: '这是最后一张了'
		}).show();

	});

	function totalVal($fnUpAttachBox) {
		//统计当前上传后的链接

		var _arr = [];

		$fnUpAttachBox.find('.fnAttachItem').each(function(index, el) {
			_arr.push($(this).attr('val'));
		});

		$fnUpAttachBox.find('.fnUpAttachVal').val(_arr.join(','));

	}

	function upFileCallBack($fnUpAttachBox, res) {
		//上传后的回调

		var _$fnUpAttachUl = $fnUpAttachBox.find('.fnUpAttachUl'),
			isImg = util.isImg(res.fileName); //文件名，判断是否是图片

		var _html = '<span class="attach-item fnAttachItem" val="' + res.fileUrl + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';

		_$fnUpAttachUl.append(_html);

		totalVal($fnUpAttachBox);
	}



});