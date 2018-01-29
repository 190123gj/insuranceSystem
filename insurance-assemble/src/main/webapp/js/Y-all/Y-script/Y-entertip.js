
(function($){

define(function(require, exports, module){
	require("./Y-tip");
	require("./Y-number");
    
	Y.inherit('EnterTip','Tip',{
	    doInit: function(cfg){
		    if(this.callBase('doInit', 'Tip', cfg) === false) return false;;
			var _this = this;
			this.bind(this.target, 'keyup', function(e){
				var target = $(this);
			    _this.update();
			    if(target.val() && !_this.showwing) _this.show();
			    else if(!target.val() && _this.showwing && !cfg.showEmpty) _this.hide();
			});
			this.bind(this.target, 'blur', function(e){
			    _this.hide();
			});
			this.bind(this.target, 'focus', function(e){
				_this.update();
			    if($(this).val() || cfg.showEmpty) _this.show();
			});
			var formatString = this.getMode().formatString;
			if(formatString) {
			    this.target.attr('maxlength', formatString.replace(/[^x]/g,'').length);
			}
		},
		getMode: function(){
		    if(this.mode) return this.mode;
		    var cfg = this.cfg;
			var mode = cfg.mode;
			if(mode) {
			    if(typeof mode == 'string') {
				    mode = $.extend({},this.modes[mode] || {}, cfg);
				} else if(typeof mode == 'object') {
				    mode = $.extend({},mode, cfg);
				}
			} else {
			    mode = $.extend({}, cfg);
			}
			this.mode = mode;
			return mode;
		},
		update: function(){
		    var str = this.processing();
			this.el.html(str);
		},
		processing: function(){
		    var cfg = this.cfg;
			var mode = this.getMode();
			var val = this.target.val();
			var result = val;
			if(typeof mode.translate == 'function') {
			    result = mode.translate(val);
			}
			if(mode.formatString && typeof mode.formatString == 'string') {
			    result = this.format(result, mode.formatString, mode.endPriority, mode.keepFormat);
			}
			return (result||result===0)?result:'&nbsp;';
		},
		format: function(v, formatString, endPriority, keepFormat){
		    var result = v;
			var i = 0;
		    if(!endPriority) {
			    result = formatString.replace(/./g, function(chr){
				    if(i >= v.length) {
					    chr = keepFormat?chr:'';
					} else if(chr == 'x') {
					    chr = v.charAt(i);
						i++;
					}
					return chr;
				});
			} else {
			    v = this.reverse(v);
			    result = this.reverse(this.reverse(formatString).replace(/./g, function(chr,num){
				    if(i >= v.length) {
					    chr = keepFormat?chr:'';
					} else if(chr == 'x') {
					    chr = v.charAt(i);
						i++;
					}
					return chr;
				}));
			}
			return result;
		},
		reverse:function(str){//颠倒字符串
			return ('' + str).split('').reverse().join('');
		},
		modes: {
		    amount: {
			    formatString: 'xxx,xxx,xxx,xxx',
				endPriority: true
			},
		    uppercaseAmount: {
			    translate: function(n){
			        if(!$.isNumeric(n)) return n;
				    return Y.Number(n).digitUppercase(n);
			    }
			},
			bankCard: {
			    formatString: 'xxxx xxxx xxxx xxxx xxxx xxxx xxxx xxxx',
				endPriority: false
			}
		}
	});
	Y.EnterTip.addMode = function(name,mode){
	    this.prototype.modes[name] = mode;
	}
	Y.EnterTip.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear: false,
		showEmpty: false,
		css: 'wnd-tip-translatetip',
		renderTo : 'body',
		spacing : 3 ,
		speed : 0 ,
		align: 'top',
		autoShow: false
	});
	
});
  
})($);