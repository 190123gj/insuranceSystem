
(function($){

define(function(require, exports, module){
	require("./Y-tip");
   
	Y.inherit('ToolTip','Tip',{
	    doInit: function(cfg){
		    cfg.renderTo = 'body';
		    this.callBase('doInit','Tip',cfg);
			var target = this.target;
			if(!target) {
			   Y.handlerError('ToolTip类必须配置target');
			   return;
			}
			var _this = this;
			var timer;
			this.bind(target, 'mouseenter', function(e){
			    _this.show(cfg.speed);
			});
			this.bind(target, 'mouseleave', function(e){
			    timer = setTimeout(function(){
				    _this.hide(cfg.speed);
				},cfg.delay);
			});
			this.bind(this.el, 'mouseenter',function(e){
			    if(timer) clearTimeout(timer);
			});
			this.bind(this.el, 'mouseleave',function(e){
			    _this.hide(cfg.speed);
			});
		}
	});
	Y.ToolTip.tooltip = function(target,el){
	    var timer;
		this.bind(target, 'mouseenter', function(e){
			_this.show(cfg.speed);
		});
		this.bind(target, 'mouseleave', function(e){
			timer = setTimeout(function(){
				_this.hide(cfg.speed);
			},cfg.delay);
		});
		this.bind(this.el, 'mouseenter',function(e){
			if(timer) clearTimeout(timer);
		});
		this.bind(this.el, 'mouseout',function(e){
			_this.hide(cfg.speed);
		});
	}
	Y.ToolTip.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear: false,
		css: 'wnd-tip wnd-tip-tooltip',
		delay: 300,
		autoShow: false
	});
	
});
	
})($);