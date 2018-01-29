
(function($){

define(function(require, exports, module){
	require("./Y-window");
    Y.inherit('Tip','Window',{
	    doInit:function(cfg){
			cfg.renderTo = 'body';
		    this.callBase('doInit','Window',cfg);
			if(!cfg.noStyle) this.el.addClass(cfg.css);
			if(!cfg.target){
			    Y.handlerError('Tip类必须配置target');
				return false;
			}
			var target = this.toJqObj(cfg.target);
			this.target = target;
			if(cfg.autoDisappear) {
			    var _this = this;
			    this.bind($(document),'click',function(e){
			    	if(e.target == _this.target.get(0)) return;
			    	if(e.target == _this.el.get(0) || $.contains(_this.el.get(0), e.target)) return;
				    _this.close();
				});
			}
			this.align = cfg.align;
			this.spacing = cfg.spacing;
			this.on('show',function(){
			    this.layout();
			});
		},
		setAlign:function(align){
		    this.align = align;
		},
		setSpacing:function(spacing){
		    this.spacing = spacing;
		},
		countPosition:function(){
		    var cfg = this.cfg;
			var align = this.align;
			var spacing = this.spacing || 0, spacingX = 0, spacingY = 0;
			if(typeof spacing == 'object') {
			    spacingX = parseInt(spacing.x) || 0;
				spacingY = parseInt(spacing.y) || 0;
			} else {
			    if(!isNaN(parseInt(spacing))) {
				    spacingX = spacingY = parseInt(spacing) || 0;
				}
			}
		    var pos = this.target.offset();
			var info = this.el;
	        var eleLeft = pos.left,eleTop = pos.top,eleWidth = this.target.outerWidth(),eleHeight = this.target.outerHeight();
			var left,top;
	        switch(align) {
			  case 'top': 
				  left = eleLeft,top = eleTop - info.outerHeight();
			  break;
			  case 'bottom': 
				  left = eleLeft,top = eleTop + eleHeight;
			  break;
			  case 'left': 
				  left = eleLeft - info.outerWidth(),top = eleTop;
			  break;
			  case 'right': 
				  left = eleLeft + eleWidth,top = eleTop;
			  break;
			  case 'left top': 
			  case 'top left':
				  left = eleLeft - info.outerWidth(),top = eleTop - info.outerHeight();
			  break;
			  case 'right bottom': 
			  case 'bottom right': 
				  left = eleLeft + eleWidth,top = eleTop + eleHeight;
			  break;
			  case 'left bottom': 
			  case 'bottom left':
				  left = eleLeft - info.outerWidth(),top = eleTop + eleHeight;
			  break;
			  case 'right top': 
			  case 'top right':
				  left = eleLeft + eleWidth,top = eleTop - info.outerHeight();
			  break;
		    }
			if((/left/).test(align)) {
			    left -= spacingX;
			} 
			if((/right/).test(align)) {
			    left += spacingX;
			}
			if((/top/).test(align)) {
			    top -= spacingY;
			} 
			if((/bottom/).test(align)) {
			    top += spacingY;
			}
		    this.moveTo({x: left,y: top});
		}
    });
	
	Y.Tip.defaults = $.extend({},Y.Window.defaults,{
		simple: true,
		modal:false,
		minWidth: 10,
		autoSize: true,
		align: 'right bottom',
		autoDisappear: true,
		spacing: 0,
		css: 'wnd-tip',
		el: 'span',
		speed : 0
	});

});
  
})($);