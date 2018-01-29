// JavaScript Document
define(function(require, exports, module){
	require("./Y-base");
	if(Y.AjaxUpload)return;
	
    Y.inherit('AjaxUpload', 'component', {
	    init: function(cfg){
	        if(!cfg.fileElementId) cfg.fileElementId = cfg.target;
			this.callBase('init','component',cfg);
		},
		createUploadIframe: function(id, uri){
			var frameId = 'jUploadFrame' + id;   
		    var io = $('<iframe id="' + frameId + '" name="' + frameId + '"></iframe>').get(0);
			io.style.position = 'absolute';
			io.style.top = '-1000px';
			io.style.left = '-1000px';
			document.body.appendChild(io);
			io.src = uri;
			return io;
		},
		show:function(){},
		createUploadForm: function(id, fileElementId){
			//create form	
			var formId = 'jUploadForm' + id;
			var fileId = 'jUploadFile' + id;
			var form = $('<form  action="" method="POST" name="' + formId + '" id="' + formId 
				+ '" enctype="multipart/form-data"></form>');	
			var oldElement = typeof fileElementId == 'object' ? fileElementId : $('#' + fileElementId);
			var newElement = $(oldElement).clone();
			oldElement.attr('id', fileId);
			oldElement.after(newElement);
			oldElement.appendTo(form);
			form.data({oldElement: oldElement, newElement: newElement});
			//set attributes
			$(form).css('position', 'absolute');
			$(form).css('top', '-1200px');
			$(form).css('left', '-1200px');
			$(form).appendTo('body');	
			var formData = this.cfg.formData;
			if(typeof formData == 'object') {
				for(var name in formData) {
					var val = typeof formData[name]=="function"?formData[name]():formData[name];
					$(form).append('<input name="'+name+'" value="'+val+'" />');
				}
			}
			return form;
		},	
		upload: function() {
			// TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
			s = jQuery.extend({}, jQuery.ajaxSettings, this.cfg);
			var _this = this;
			var id = new Date().getTime();     
			var form = this.createUploadForm(id, s.fileElementId);
			var io = this.createUploadIframe(id, s.secureuri);
			var frameId = 'jUploadFrame' + id;
			var formId = 'jUploadForm' + id;
			if ( s.global && ! jQuery.active++ ){
				jQuery.event.trigger( "ajaxStart" );
			}            
			var requestDone = false;
			var xml = {}   
			if ( s.global ){	
				jQuery.event.trigger("ajaxSend", [xml, s]);
			}
			var status;
			var uploadCallback = function(isTimeout){	
				var io = document.getElementById(frameId);
				try {				
					if(io.contentWindow){
						 xml.responseText = io.contentWindow.document.body ? 
							$(io.contentWindow.document.body).html(): null;
						 xml.responseXML = io.contentWindow.document.XMLDocument ? 
							io.contentWindow.document.XMLDocument : io.contentWindow.document;			 
					}
					else if(io.contentDocument){
						xml.responseText = io.contentDocument.document.body ? 
							$(io.contentDocument.document.body).html(): null;
						xml.responseXML = io.contentDocument.document.XMLDocument ? 
							io.contentDocument.document.XMLDocument : io.contentDocument.document;
					}						
				}catch(e){
					Y.handlerError(e);
					status = 'error';
				}
				if ( xml || isTimeout == "timeout"){	
					requestDone = true;
					try {
						if(!status) status = isTimeout != "timeout" ? "success" : "error";
						// Make sure that the request was successful or notmodified
						
						if ( status != "error" ){
							// process the data (runs the xml through httpData regardless of callback)
							var data = _this.uploadHttpData( xml, s.dataType ); 
							// If a local callback was specified, fire it and pass it the data
							if(data === undefined) {
							    status = 'error';
							} else {
							    _this.fire('success', data, status,xml.responseText);
							// Fire the global callback
							    if( s.global)
								    jQuery.event.trigger( "ajaxSuccess", [xml, s] );
							}
						}else
							Y.handlerError('error');
					} catch(e) {
						status = "error";
						Y.handlerError(e);
					}
					// The request was completed
					if( s.global )
						jQuery.event.trigger( "ajaxComplete", [xml, s] );
					// Handle the global AJAX counter
					if ( s.global && ! --jQuery.active )
						jQuery.event.trigger( "ajaxStop" );
					// Process result
					_this.fire('complete',data, status,xml.responseText);
					jQuery(io).unbind();
					setTimeout(function(){	
						try{
							var newElement = $(form).data('newElement');
							var oldElement = $(form).data('oldElement');
							var oid = newElement.attr('id');
							newElement.after(oldElement);
							newElement.remove();
							oldElement.attr('id',oid);
							
							$(io).remove();
							$(form).remove();			
						} catch(e) {
							_this.handleError(s, xml, null, e);
						}									
					}, 100)
					xml = null;
				}
			}
			// Timeout checker
			if ( s.timeout > 0 ) {
				setTimeout(function(){
					if( !requestDone ) 	uploadCallback( "timeout" );
				}, s.timeout);
			}
			try{
			   // var io = $('#' + frameId);
				var form = $('#' + formId);
				$(form).attr('action', s.url);
				$(form).attr('method', 'POST');
				$(form).attr('target', frameId);
				if(form.encoding){
					form.encoding = 'multipart/form-data';				
				}else{				
					form.enctype = 'multipart/form-data';
				}			
				$(form).submit();
	
			} catch(e){			
				_this.handleError(s, xml, null, e);
			}
			if(window.attachEvent){
				document.getElementById(frameId).attachEvent('onload', uploadCallback);
			}else{
				document.getElementById(frameId).addEventListener('load', uploadCallback, false);
			} 		
			return {abort: function () {}};	
		},
		uploadHttpData: function( r, type ) {
			var data = !type;
			data = type == "xml" || data ? r.responseXML : r.responseText;
			//if(data === undefined) return data;
			// If the type is "script", eval it in global context
			if ( type == "script" )
				jQuery.globalEval( data );
			// Get the JavaScript object, if JSON is used.
			if ( type == "json" )
				try{
					eval( "data = " + data );
				}catch(e){
					Y.handlerError(e);
				}
			// evaluate scripts within html
			if(type == 'text') {
                data = data.replace(/^(<pre(.)*?>)/i,'').replace(/<\/pre>$/i,'');
			}
			if ( type == "html" )
				jQuery("<div>").html(data).evalScripts();
				//alert($('param', data).each(function(){alert($(this).attr('value'));}));
			return data;
		},
		handleError:function(){
		}
	});
	Y.AjaxUpload.defaults = $.extend({}, {
		
	});	 
})
