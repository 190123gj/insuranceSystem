
(function($){

define(function(require, exports, module){
	require("./Y-window");
    Y.inherit('Msg','Window',{
	    doInit: function(cfg){
		    var _this = this;
			cfg.content = $('<div>').css('display','inline').append(cfg.content);
		    this.callBase('doInit','Window',cfg);
			var wndItem = this.item.addClass(cfg.msgClass);
			this.item = $("<div>");
			var bottomBar = $('<div>').addClass(cfg.bottomClass);
			bottomBar.append($('<a>').attr({
			    href: 'javascript:;'
			}).addClass(cfg.yesClass)
			  .append($('<span>').html(cfg.yesText || this.strings.ok))
			  .click(function(){
			    _this.close();
			    if(cfg.callback) {
				    cfg.callback('yes');
				}
				return false;
			}));
			if(cfg.type == 'confirm') {
			    bottomBar.append($('<a>').attr({
			        href: 'javascript:;'
			    }).css({
				    'margin-left': 10
				}).addClass(cfg.yesClass)
				  .append($('<span>').html(cfg.noText || this.strings.cancel))
				  .click(function(){
				    _this.close();
			        if(cfg.callback) {
				        cfg.callback('no');
				    }
					return false;
			    }));
			}
			this.bind(this.closeBtn,'click',function(){
			    if(cfg.callback) {
				    cfg.callback('close');
				}
			});
			if(cfg.icon) {
			    var iconClass = Y.Msg.icons[cfg.icon] || '';
				if(iconClass) {
				    wndItem.prepend($('<a>').attr({
				        href: 'javascript:;'
				    }).addClass(iconClass));
				}
			}
			this.item.append(wndItem)
			         .append(bottomBar);
		}
    });
	
	$.extend(Y,{
	    alert: function(title,msg,callback,cfg){
		    cfg = cfg || {};
		    $('body').Y('Msg',$.extend(cfg,{
			    title: title,
				content: msg,
				callback: callback
			}));
		},
		confirm: function(title,msg,callback,cfg){
		    cfg = cfg || {};
		    $('body').Y('Msg',$.extend(cfg,{
			    title: title,
				content: msg,
				type: 'confirm',
				callback: callback
			}));
		}
	});
	
	Y.Msg.defaults = $.extend({},Y.Window.defaults,{
	    type: 'alert',
		title: '',
		simple: false,
		closeAble: true,
		yesClass: 'base-btn base-btn-green',
		noClass: 'base-btn base-btn-gray',
		msgClass: 'wnd-msg-msg',
		width: 360,
		autoSize: true,
		bottomClass: 'wnd-msg-bottom',
	    bodyStyle: {
		    'text-align': 'center'
		}
	});
	Y.Msg.icons = {
	    success:'wnd-msg-success',
		fail: 'wnd-msg-fail',
		warn: 'wnd-msg-warn',
		error: 'wnd-msg-error',
		ask: 'wnd-msg-ask'
	}
});
  
})($);
