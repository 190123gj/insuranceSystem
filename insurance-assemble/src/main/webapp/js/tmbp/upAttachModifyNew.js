/**
 * 通用上传附件
 *
 * 上传按钮(fnUpAttachBtn)上添加属性todownload，其值为true则表示开启下载功能，false为不开启。默认不添加为false.其中图片不支持下载
	<div class="fnUpAttach">
		<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn"><i class="icon icon-add"></i>上传附件</a>
		<input class="fnUpAttachVal" type="hidden" name="">
		<div class="fn-blank5"></div>
		<div class="m-attach fnUpAttachUl"></div>
	</div>
 *
	<div class="fnUpAttach">
		<span class="item-files fn-dib m-up-files">共有[<strong class="upFilesNumber">0</strong>]附件</span>
		<span class="fn-csp ui-btn ui-btn-fill ui-btn-blue viewDaliog"><i class="icon icon-look"></i>弹窗查看</span>
		<span class="fn-csp ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn"><i class="icon icon-add"></i>上传附件</span>
		<input class="fnUpAttachVal" type="hidden" name="">
		<div class="fn-blank5"></div>
		<div class="m-attach fnUpAttachUl fn-hide"></div>
	</div>

 */

define(function(require, exports, module) {
	//通用代码
	//查看图片
	require('Y-imageplayer');
	//上传图片
	require('Y-msg');
	var util = require('util');
    //引入插件jquery.fileupload.js
    $.each(['/js/lib/jquery.upload/jquery.fileupload.js'], function(index, url) {
        var _script = document.createElement('script');
        _script.src = url;
        document.getElementsByTagName('body')[0].appendChild(_script)
    });
	var $body = $('body');
	$body.on('click', '.fnUpAttachBtn', function() {

        //上传附件
        var _this = $(this),
            _filetype = _this.attr('filetype') || util.fileType.all, //自定义上传类型
			_maxitem = _this.attr('maxitem') || 99999,
			_fileSizeLimit = _this.attr('fileSizeLimit') || '500', //单位位MB
			$fnUpAttachBox = _this.parents('.fnUpAttach'),
            hasUploadNum = $fnUpAttachBox.find('.fnAttachItem').length;
        //是否超过个数
        if (hasUploadNum >= _maxitem) {
            Y.alert('提醒', '已超过最大上传个数限制');
            return;
        };
        var $file = $('<input type="file" id="upfile" name="files[]" multiple>').hide(),
            //上传产生的信息存储
            uploadMsgBox = {
                selectNum:0,
                thisUploadFileNum:0,
                uploadFileNum:hasUploadNum,
                allSelectNum:0
            },
            $content = $('<div class="wnd-wnd uploadFileM">' +
                         '<div class="wnd-header" title="可拖动" style="cursor: move; width: 600px;"><span class="wnd-title">文件上传</span>' +
                         '<a href="javascript:void(0)" class="wnd-close" title="关闭"></a>' +
                         '</div>' +
                         '<div class="wnd-body" style="display: block; width: 600px; height: auto; overflow: auto;">' +
                         '<div>' +
                         '<div class="fn-main">' +
                         '<div style="height: 39px;">' +
                         '<div class="uploadBtnBox">' +
                         '<span class="uploadify-button"><span class="uploadify-button-text"><span class="u-btn u-btn-gray">选择文件</span></span></span>'+
                         '<span class="upload_msg">总共上传' + uploadMsgBox.uploadFileNum +'个文件，本次选择'+ uploadMsgBox.selectNum +'个文件</span>' +
                         // '<a href="javascript:void(0)" class="fn-right"><span class="base-btn fn-upclear"><span>清空</span></span></a>' +
                         '</div>' +
                         '<span class="fn-upinfo wnd-upload-info" style="display:none;"></span>' +
                         '</div>' +
                         '<div class="error_msg"></div>'+
                         '</div>' +
                         '<div id="queueDiv"></div>'+
                         '<div class="fn-bottom">' +
                         '<p class="tips_ttl">温馨提示，允许的文件类型（如格式不支持，请打包成常见压缩包格式上传）为：</p>' +
                         '<p class="surpport_type">' + _filetype + '</p>' +
                         // '<a href="javascript:void(0)" class="fn-right"><span class="base-btn" style="float:right;"><span>上传</span></span></a>' +
                         '</div>' +
                         '</div>' +
                         '</div>' +
                         '</div>'),
            $mgsShow = $content.find('.upload_msg'),
            $mgsError = $content.find('.error_msg'),
            $itemBox = $content.find('#queueDiv'),
            //进度条模板
            $progress = $('<div class="uploadify-queue-item">' +
                '<div class="cancel">' +
                '<a href="javascript:void(0)">X</a>' +
                '</div>' +
                '<span class="fileName"></span><span class="data"></span>' +
                '<div class="uploadify-progress">' +
                '<div class="uploadify-progress-bar"></div>' +
                '</div>' +
                '</div>');

        $file.fileupload({
            url: '/upload/imagesUpload.htm',
            dataType: 'json',
            // forceIframeTransport:true,
            autoUpload:false,
            change: function (e, data) {
                // console.log(data.files)
                // uploadMsgBox.thisUploadFileNum = 0;//清零上次上传个数
                // $content.find('.uploadify-button').addClass('disUpload');
                $mgsError.html();
                $.each(data.files, function (index, file) {

                    var fileName = file.name;
                    var fileSize = file.size / 1024 / 1024;
                    var type = fileName.substr(fileName.lastIndexOf('.') + 1,fileName.length);
                    var $errorTips = '';

                    uploadMsgBox.allSelectNum++;
                    if(_filetype.indexOf(type) < 0 && !!_filetype){
                        $errorTips = $('<p>文件：[' + fileName + ']&nbsp;&nbsp;--&nbsp;&nbsp;文件类型出错</p>');
                    }else if(_fileSizeLimit < fileSize && !!_fileSizeLimit){
                        $errorTips = $('<p>文件：[' + fileName + ']&nbsp;&nbsp;--&nbsp;&nbsp;文件大小超出' + _fileSizeLimit + 'MB限制</p>');
                    }else{
                        file.uploadtStatus = true; //添加允许上传标记
                        var timeF = !!file.lastModified ? file.lastModified : new Date().getTime();
                        file.index = timeF + uploadMsgBox.selectNum; //设置唯一索引，识别对应进度条
                        uploadMsgBox.selectNum++;
                        return;
                    };
                    file.uploadtStatus = false; //添加不允许上传标记
                    $mgsError.append($errorTips);
                });
            },
            send:function (e, data) {
                var file = data.files[0];
                var fileName = file.name;
                var fileIndex = file.index;
                addProgressBar(fileName,0,fileIndex);
                showUploadMsg();
            },
            add: function (e, data) {
                var file = data.files[0];
                if(!!file.uploadtStatus){
                    data.submit().success(function (result, textStatus, jqXHR) {
                        if(!!result.success){
                            uploadMsgBox.thisUploadFileNum++;
                            uploadMsgBox.uploadFileNum++;
                        }else {
                            var $errorTips = $('<p>文件：[' + file.name + ']&nbsp;&nbsp;--&nbsp;&nbsp;' + result.message + '</p>');
                            $mgsError.append($errorTips);
                        }
                        setTimeout(function () {
                            removeProgressBar(file.index) //移除队列
                        },500);
                        showUploadMsg('','upload')

                    }).error(function (jqXHR, textStatus, errorThrown) {

                    }).complete(function (result, textStatus, jqXHR) {

                    });
                }else {
                    data.abort();
                };
            },
            progress: function (e, data) {
                var file = data.files[0];
                var fileIndex = file.index;
                var progress = parseInt(data.loaded / data.total * 100, 10);
                addProgressBar('',progress,fileIndex);
                if(progress == 100){

                }
            },
            progressall:function (e,data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                if(progress == 100){
                    // setTimeout(function () {
                    //     uploadMsgBox.selectNum = 0;
                    //     uploadMsgBox.thisUploadFileNum = 0;
                    // },300);
                    // $.each($mgsError.find('p'),function (index,e) {
                    //     var _this = $(this);
                    //     var time = 2000 + index * 200;
                    //     // setTimeout(function () {
                    //     //     _this.hide('600')
                    //     // },(time-600))
                    //     setTimeout(function () {
                    //         _this.remove();
                    //     },time)
                    // })
                    // setTimeout(function () {
                    //     $content.find('.uploadify-button').removeClass('disUpload');
                    // },2000)

                }
            },
            done: function (e, data) {
                var file = data.result;
                if(!!file.success){
                    var _file = {
                        fileName: file.data.oldFileName,
                        serverPath: file.data.serverPath,
                        fileUrl: file.data.url
                    }
                    upFileCallBack($fnUpAttachBox, _file);
                }
            }
        });
        $content.append($file);
        $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.wnd-close'
        });

        function addProgressBar(filename,data,index) {
            if(!data) data = 0;
            if(!!filename) filename = longStrSwtich(filename);
            var msg = (data >= 100) ? 'Complate!' : + data + '%';
            var $progressBar = $content.find('#file_up_quede_' + index);
            if($progressBar.length > 0) {
                $progressBar.find('.uploadify-progress-bar').css('width',data + '%').end()
                    .find('.data').html(msg);
            }else{
                $progressBar = $progress.clone(true);
                $progressBar.attr('id','file_up_quede_' + index).find('.fileName').html(filename + '&nbsp;&nbsp;---&nbsp;&nbsp;').end()
                    .find('.uploadify-progress-bar').css('width',data + '%').end()
                    .find('.data').html(msg)
                $itemBox.append($progressBar)
            }


        };
        function removeProgressBar(index) {
            if(!index) return;
            var $progressBar = $content.find('#file_up_quede_' + index);
            var time = 1000;

            if($progressBar.length > 0){
                setTimeout(function () {
                    $progressBar.hide('400')
                },(time-400))
                setTimeout(function () {
                    $progressBar.remove();
                },time)
            }
        };
        function longStrSwtich(str,maxLen) {
            if(!str) return;
            var sLen = str.length;
            maxLen = !maxLen ? 50 : maxLen;
            if(maxLen < sLen){
                var order = parseInt(maxLen / 2 - 3);
                var startStr = str.substr(0,order),
                    endStr = str.substr((sLen - order),order);
                str = startStr + '******' + endStr;
            }
            return str;
        }
        function showUploadMsg(numArry,status) {
            if(!status) status = true;
            if(!numArry) numArry = uploadMsgBox;
            var allUploadNum = numArry.uploadFileNum,
                thisAllSelectNum = numArry.selectNum,
                allSelectNum = numArry.allSelectNum,
                thisAllUploadNum = numArry.thisUploadFileNum;
            var messages = '';
            switch (status){
                case 'select':
                    messages = '总共' + allUploadNum +'个附件，本次选择'+ allSelectNum + '个，有效选择' + thisAllSelectNum + '个文件';
                    break;
                case 'upload':
                    messages = '上传完成！总共' + allUploadNum +'个附件。本次：选择'+ allSelectNum + '个，有效选择'+ thisAllSelectNum + '个，上传成功'+ thisAllUploadNum  +'个';
                    break;
                case 'end':
                    messages = '上传完成！总共' + allUploadNum +'个附件。本次：选择'+ allSelectNum + '个，有效选择'+ thisAllSelectNum + '个，上传成功'+ thisAllUploadNum  +'个';
                    break;
                default:
                    messages = '总共' + allUploadNum +'个附件，本次选择'+ allSelectNum + '个，有效选择' + thisAllSelectNum + '个文件';
            }
            $mgsShow.html(messages);
        }
    }).on('click', '.uploadify-button', function (event) {//防止正在上传附件时再次添加
        var _this = $(this);
        var e = event;
        var ListItem = _this.parents('.uploadFileM').find('#queueDiv .uploadify-queue-item').length
        if(ListItem > 0){
            Y.alert('提示','有文件正在上传，请等待文件上传完成！');
            e.preventDefault();
        }else {
            $('#upfile').click();
        }
    }).on('click', '.fnUpAttachDel', function() {
		//删除某个附件
		var _this = $(this),
			$fnUpAttachBox = _this.parents('.fnUpAttach');

		if ($fnUpAttachBox.length == 0) { //判断点击的弹窗的删除按钮
			var flag = _this.parents('.viewUpfiles').attr('flag'); //从弹窗获取对应上传组件的索引标记
			var index = _this.parents('.fnUpAttachUl').find('.fnUpAttachDel').index(_this); //获取当前删除按钮在当前弹窗的索引

			$('.fnUpAttach').eq(flag).find('.fnUpAttachDel').eq(index).trigger('click'); //找到对应上传组件对应的删除按钮触发其点击事件
            $('.fnUpAttach').eq(flag).parents('td').find('.focusInput').trigger('blur');
		}else {
            _this.parents('td').find('.focusInput').trigger('blur');//触发失焦
        }

		_this.parent().remove();
        _this.parents('td').find('[name]').trigger('click');
		totalVal($fnUpAttachBox);

	}).on('click', '.fnAttachView', function() {
		//查看所有上传的附件
		var _imgs = [];

		$('.fnAttachView').each(function(index, el) {
			var _this = $(this);
			_imgs.push({
				src: _this.parent().attr('val').split(',')[2],
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

	}).on('click', '.viewDaliog', function() { //触发弹窗查看附件
		var _this = $(this);
		var $fnUpAttachBox = _this.parents('.fnUpAttach');
		var fnUpAttachUlHtml = $fnUpAttachBox.find('.fnUpAttachUl').html(); //获取组件内容
		var index = $fnUpAttachBox.index('.fnUpAttach'); //获取当前上传组件的索引，并标记到当前弹窗
		var length = $fnUpAttachBox.find('.fnUpAttachVal').attr('length');

		if (!!!length || parseInt(length) == 0) {
			Y.alert('提示', '暂无附件');
			return;
		}

		$body.Y('Window', {
			content: '<div class="m-modal view-files viewUpfiles" flag="' + index + '">' +
				'    <div class="view-files-hd">' +
				'        <span class="fn-right fn-usn fn-csp closeBtn" href="javascript:void(0);">×</span>' +
				'        <span class="view-files-ttl">附件列表</span>' +
				'    </div>' +
				'    <div class="view-files-body">' +
				'	 	 <div class="m-attach fnUpAttachUl">' + fnUpAttachUlHtml + '</div>' +
				'    </div>' +
				'</div>',
			modal: true,
			key: 'modalwnd',
			simple: true,
			closeEle: '.closeBtn'
		});

	}).on('click','.attach',function () {
        Y.create('Window',{
            content: '.upload-scan',
            title: '上传扫描件',
            key: 'uplodWin'
        }).show();
        var _attach = $(this);
    })
	function totalVal($fnUpAttachBox) {
		//统计当前上传后的链接

		var _arr = [];

		$fnUpAttachBox.find('.fnAttachItem').each(function(index, el) {
			_arr.push($(this).attr('val'));
		});

		$fnUpAttachBox.find('.fnUpAttachVal').val(_arr.join(';')).attr('length', _arr.length).next('.radioSpan').hide(); //统计个数在隐藏域
		$fnUpAttachBox.find('.fnUpAttachVal').trigger('blur');
		$fnUpAttachBox.find('.upFilesNumber').html(_arr.length); //统计附件个数

	}

	function upFileCallBack($fnUpAttachBox, res) {
		//上传后的回调

		var _html = '';
		var _$fnUpAttachUl = $fnUpAttachBox.find('.fnUpAttachUl'),
			isCanDownload = $fnUpAttachBox.find('.fnUpAttachBtn').attr('todownload') || false, //是否开启下载功能
			isImg = util.isImg(res.fileName); //文件名，判断是否是图片

		var _html1 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" title="' + res.fileName + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';

		var _html2 = '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';

		_html = (!!isCanDownload && !isImg) ? _html2 : _html1; //下载开启(isCanDownload=true)并且不为图片(isImg=false)

		_$fnUpAttachUl.append(_html);

		totalVal($fnUpAttachBox);

	}
});