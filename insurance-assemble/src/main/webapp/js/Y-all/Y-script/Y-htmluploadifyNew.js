// JavaScript Document
(function($){
define(function(require, exports, module){
    require("../Y-script/Y-window.js");
	require("../Y-script/Y-ajaxupload.js");
	if(Y.HtmlUploadify){
		return;
	}
	Y.inherit('HtmlUploadify', 'Window', {
	    doInit: function(cfg){
			var _this = this;
			var content = $("<div>").append($("<div class='fn-main'></div>"))
			                        .append($("<div class='fn-bottom'></div>"));
			var useType = this.cfg.fileTypeExts;
			if(useType) {
			    content.find('.fn-bottom').html('<span style="color:#999999;font-size:14px;">温馨提示：允许的文件类型为：'+useType.replace(/\./g,'').replace(/\*|\s/g,'')+"</span>");
			}                   
			if(!cfg.multi){
				cfg.queueSizeLimit = 1;
			}
			cfg.content = content;
			cfg.renderTo = "body";
			this.callBase('doInit','Window',cfg);
			this.delegate('.fn-updelete','click', function(){
				_this.removeItem($(this).parent());
			});
			this.delegate('.fn-upclear','click', function(){
				_this.clearValue($(this).parent());
			});
			if(cfg.addAble){
				var addbtn = $('<span class="base-btn"><span>新增</span><span/>');
				addbtn.click(function(){
					_this.addItem();
				});
				content.find('.fn-bottom').append(addbtn);
			}
			var upbtn = $('<span class="base-btn" style="float:right;"><span></span></span>')
			upbtn.find('span').html(cfg.upBtnText);
			upbtn.click(function(){
				_this.upload();
			});
			content.find('.fn-bottom').append(upbtn);
			for(var i = 0 ; i < cfg.queueSizeLimit; i++){
				this.addItem();
			}
			this.delegate("input[type=file]","change",function(){
			    $(this).removeAttr("upload");
			    if($(this).val() && _this.checkFile($(this).val()) === false) {
			        _this.showInfo($(this).parent(),"类型不符合");
			        _this.clearValue($(this).parent());
			    }
			});
		},
		clearValue: function(item){
			var cfg = this.cfg;
			var fileObjName = cfg.fileObjName;
			obj = item.find('input[type=file]');
			var newObj = $('body').find('#upfile').clone(true);
			obj.after(newObj);
			obj.remove();
		},
		checkFile:function(fileName){
			var cfg = this.cfg;
			var useType = this.cfg.fileTypeExts;
			if(useType && !useType.match(/\.\*/)) {
				useType = useType.replace(/\./g,'').replace(/\*|\s/g,'');
				var types = useType.split(';');
				var arr = fileName.toLowerCase().split('.');
				var type = arr[arr.length-1];
				if($.inArray(type,types) == -1){
					return false;
				}
			}
			return true;
		},
		addItem:function(){
			var cfg = this.cfg;
			var fileObjName = cfg.fileObjName;
			var div = $('<div>').css({
			    height: cfg.itemHeight || 39
			});
			div.append($('body').find('#upfile').clone(true));
			div.append("<span class='fn-upinfo wnd-upload-info' style='display:none;'></span>");
			div.append($('<span class="base-btn fn-upclear" style="float:right;"><span>清空</span></span>'));
			if(cfg.deleteAble){
				div.append($('<span class="base-btn fn-updelete" style="float:right;"><span>删除</span></span>'));
			}
			this.item.find(".fn-main").append(div);
			
		},
		showInfo:function(item,msg,callback){
		    var info = item.find('.fn-upinfo');
		    info.html(msg || '').fadeIn();
		    setTimeout(function(){
		        info.fadeOut();
				if(callback) callback();
		    },2000);
		},
		removeItem:function(el){
		    var queue = this.getItems();
		    el = el || queue.eq(-1);
		    el.remove();
		},
		getItems: function(){
		    return this.item.find("div");
		},
		getQueue: function(){
		    var queue = this.item.find('[type=file]');
		    queue = queue.filter(function(i,item){
		    	return !!$(item).val();
		    });
			return queue;
		},
		doUpload:function(ele,callback){
		    var cfg = this.cfg;
		    var _this = this;
		    var url = typeof cfg.realUrl == 'function'?cfg.realUrl():cfg.uploader;
			var uploader = Y.create('AjaxUpload',{
			    url: url,
			    secureuri:false,
				fileElementId:ele,
				dataType: 'text',
				timeout: cfg.timeout,
				formData: cfg.formData,
				success: function (data,status,res){
					
				},
				complete: function(data,status,res){
					var result = status == 'success'?true:false;
					if(callback) callback(result,data,res);
				}
			});
			uploader.upload();
			uploader.distroy();
		},
		upload : function(){
			var _this = this;
			var cfg = this.cfg;
			var item = this.item;
			var eles = this.getQueue();
			var removes = [];
			eles.each(function(i,item){
				var $this = $(item);
				var mark = true;
				if($this.attr('upload')){
					mark = false;
				}
				if(!$this.val()){
					mark = false;
				}
				var item = $this.parent();
				if($this.val() && !_this.checkFile($this.val())){
				    _this.showInfo(item,"类型不符合");
				    mark = false;
				}
			    $this.attr('upload',true);
			    if(!mark) return;
			    if(cfg.onUploadStart) {
				    _this.fire('onUploadStart'); 
			    }
				_this.doUpload($this,function(result,data,res){
				    var info = "";
				    if(result) {
				        _this.fire('onUploadSuccess',$this,data,res,true);
				        info = "已上传";
				        _this.successAccount = _this.successAccount?_this.successAccount +1:1;
				    } else {
				        _this.fire('onUploadError',$this,0,'',''); 
				        info = "上传未成功";
				        _this.errorAccount = _this.errorAccount?_this.errorAccount +1:1;
				    }
					_this.showInfo(item,info);
				    _this.fire('onUploadComplete',$this,data,res,result);
				    if(cfg.auto && i < eles.length-1) {
				        _this.upload();
				    }
				    if(i == eles.length-1) {
				        _this.fire("onQueueComplete",{uploadsSuccessful:_this.successAccount || 0,uploadsErrored:_this.errorAccount || 0});
				        if(cfg.autoDisappear) {
						    setTimeout(function(){
							    _this.close();
							},2000);
						}
					}
				});
				return false;
			});
		}
	});
	Y.HtmlUploadify.defaults = $.extend({}, Y.Window.defaults, {
	    width: 600,
	    title: '文件上传',
		auto: false,
		autoDisappear: true,
		multi: true,
		upBtnText: '上传',
		queueSizeLimit: 3,//显示多少个上传文件
		addAble: false
	});	
});
  
})($);
