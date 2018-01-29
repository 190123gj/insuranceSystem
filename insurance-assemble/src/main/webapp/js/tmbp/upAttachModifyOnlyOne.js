/**
 * 通用上传附件
 *
 * 上传按钮(fnUpAttachBtn)上添加属性todownload，其值为true则表示开启下载功能，false为不开启。默认不添加为false.其中图片不支持下载
 * demo1：
    <div class="fnUpAttach">
        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn"><i class="icon icon-add"></i>上传附件</a>
        <input class="fnUpAttachVal" type="hidden" name="">
        <div class="fn-blank5"></div>
        <div class="m-attach fnUpAttachUl"></div>
        <div class="m-attach fnUpAttachUlImgs"></div>
    </div>
 *
 *
 */

define(function(require, exports, module) {
    //通用代码
    //查看图片
    require('Y-imageplayer');
    //上传图片
    require('Y-htmluploadify');
    require('Y-msg');
    var util = require('util');

    var $body = $('body');

    $body.on('click', '.fnUpAttachBtn', function() {
        //上传附件
        var _this = $(this),
            _filetype = _this.attr('filetype') || util.fileType.all, //自定义上传类型
            _fileSizeLimit = _this.attr('fileSizeLimit') || '5000MB',
            $fnUpAttachBox = _this.parents('.fnUpAttach');

        var _load = new util.loading();

        var htmlupload = Y.create('HtmlUploadify', {
            key: 'up1',
            uploader: '/upload/imagesUpload.htm',
            multi: false,
            auto: true,
            addAble: false,
            fileSizeLimit: _fileSizeLimit,
            fileTypeExts: _filetype,
            fileObjName: 'UploadFile',
            onUploadStart: function($this) {
                _load.open();
            },
            onQueueComplete: function(a, b) {
                _load.close();
            },
            onUploadSuccess: function($this, res, resfile) {
                var _res = $.parseJSON(res || '{"success": false}');
                if (_res.success) {
                    var _file = {
                        fileName: _res.data.oldFileName,
                        serverPath: _res.data.serverPath,
                        fileUrl: _res.data.url
                    }

                    upFileCallBack($fnUpAttachBox, _file);
                } else {
                    Y.alert('操作失败', '上传失败');
                }
            },
            onUploadError: function($this, file) {
                console.log('出事啦');
                _load.close();
            },
            renderTo: 'body'
        });

    }).on('click', '.fnUpAttachDel', function() {
        //删除某个附件
        var _this = $(this),
            $fnUpAttachBox = _this.parents('.fnUpAttach');
        _this.parent().remove();

        totalVal($fnUpAttachBox);

    });

    function totalVal($fnUpAttachBox) {
        //统计当前上传后的链接

        var _arr = [],
            url = [];

        var targetId = $fnUpAttachBox.find('.fnUpAttachBtn').attr('targetId');

        $fnUpAttachBox.find('.fnAttachItem').each(function(index, el) {
            _arr.push($(this).attr('val'));
            url.push($(this).attr('fileUrl'));
        });

        $fnUpAttachBox.find('.fnUpAttachVal').val(_arr.join(';')).trigger('blur'); //统计个数在隐藏域
        $('#' + targetId).val(url);

    }

    function upFileCallBack($fnUpAttachBox, res) {
        var _html;
        var _$fnUpAttachUl = $fnUpAttachBox.find('.fnUpAttachUl'),
            _$fnUpAttachVal = $fnUpAttachBox.find('.fnUpAttachVal'),
            val = res.fileName + ',' + res.serverPath + ',' + res.fileUrl,
            isCanDownload = $fnUpAttachBox.find('.fnUpAttachBtn').attr('todownload') || false, //是否开启下载功能
            isImg = util.isImg(res.fileName); //文件名，判断是否是图片

        var _html1 = '<span class="attach-item fnAttachItem" fileUrl="' + res.fileUrl + '" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" title="' + res.fileName + '"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        var _html2 = '<span class="attach-item fnAttachItem" fileUrl="' + res.fileUrl + '" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '"><a title="点击下载' + res.fileName + '" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0"><i class="icon' + (isImg ? ' icon-img' : ' icon-file') + '" target="_blank"></i><span class="fileItems ' + (isImg ? ' fnAttachView fn-csp' : '') + '">' + res.fileName + '</span></a><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        var _html3 = '<span class="attach-item fnAttachItem fn-dib imgPreItem" fileUrl="' + res.fileUrl + '" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" style="margin-right:20px;"><img src="' + res.fileUrl + '"><span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span></span>';
        _html = isImg ? _html3 : !!isCanDownload ? _html2 : _html1;//下载开启(isCanDownload=true)并且不为图片(isImg=false)
        _$fnUpAttachUl.html(_html);
        _$fnUpAttachVal.val(val);

        totalVal($fnUpAttachBox);
    }
});