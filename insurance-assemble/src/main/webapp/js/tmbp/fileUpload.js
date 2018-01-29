/**
 * 文件上传功能集成
 */

define(function(require, exports, module) {

    require('tmbp/jquery.uploadify.js');
    require('Y-all/Y-script/Y-htmluploadifyNew.js');
    var uploadHost = $("#uploadHost").val();
    var $input = $('.upfile');
    $input.uploadify({
        height: 31,
        width: 160,
        buttonText: '<span class="u-btn u-btn-gray">选择文件</span>',
        fileTypeExts: '*.jpg; *.jpeg; *.bmp; *.png',
        multi: true,
        queueSizeLimit: '5',
        auto: true,
        queueID: 'queueDiv',
        swf: '/resources/swf/uploadify.swf?tag=' + new Date().getTime(),
        uploader: '/upload/imagesUpload;jsessionid=' + $_GLOBAL.sessionID,
        fileSizeLimit: '4MB',
        scriptData: {
            JSESSIONID: $_GLOBAL.sessionID
        },
        //formData      : 'oldFilePath',
        onInit: function() {},
        onUploadSuccess: function(file, data, response) {
            var info = $.parseJSON(data);
            if (!info) return;
            console.log(info)
            $('.uploadimg').attr('src', info.resData);
            var _img = $('<img class="guaranteeLicenseUrl_Img">');
            var imgcss = {
                width: '80px',
                height: '80px'
            };
            _img.attr('src', info.resData);
            _img.css(imgcss);
            _img.attr('serverPath', info.serverPath);

            _attach.parent().parent().append(_img);
            // alert(_img.printArea());
        },

        onUploadError: function(file, errorCode, errorMsg, errorString) {

        },
        onQueueComplete: function(queueData) {
            var successs = queueData.uploadsSuccessful;
            var errors = queueData.uploadsErrored;
            var allnum = input.data('fileNum');
            if (successs >= allnum || errors > 0) {
                //submitUpload();
            }
        },
        onDialogClose: function(swfuploadifyQueue) {
            $input.data('fileNum', swfuploadifyQueue.queueLength);
        },
        onCancel: function(file) {}
    });
});