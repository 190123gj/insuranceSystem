
(function($){

define(function(require, exports, module){
	require("./Y-tip");
	require("./Y-string");
    
	Y.inherit('AutoComplete','Tip',{
	    doInit: function(cfg){
			var _this = this;
			var data = cfg.data || [];
			var list = $('<ul>').addClass('select-list');
			list.hide();
			cfg.content = list;
			$.extend(this,{
				data: cfg.data || [],
				list: list
			});
			if(this.callBase('doInit', 'Tip', cfg) === false) return false;
			this.add(data);
			this.render();
		},
		doRender: function(){
			this.callBase('doRender', 'Tip');
			var cfg = this.cfg, _this = this;
			this.bind(this.target, 'keyup', function(e){
				var keyCode = e.keyCode;
				if(keyCode == 13) {
					var light = _this.list.children('li.select-light').eq(0);
					if(light.length) {
						_this.selected(light);
						_this.hide();
					}
					return;
				} 
				if(keyCode == 38 || keyCode == 40) return;
				var val = $(this).val();
				var list = _this.filter(val);
				if(list.length > 0) {
					_this.show();
				} else {
					_this.hide();
				}
				_this.list.children('li').hide();
				list.show();
			});
			this.bind(this.target, 'keydown', function(e){
				if(!_this.showwing) return;
				var keyCode = e.keyCode;
				var light = _this.list.children('li.select-light').eq(0);
				if(keyCode == 40) {
					if(!light.length) {
						_this.setLight(_this.list.children('li:visible').eq(0));
					} else {
						var li = light.nextAll(':visible').eq(0);
						if(li.length) {
							_this.setLight(li);
						}
					}
				} else if(keyCode == 38) {
					if(!light.length) {
						_this.setLight(_this.list.children('li:visible').eq(-1));
					} else {
						var li = light.prevAll(':visible').eq(0);
						if(li.length) {
							_this.setLight(li);
						}
					}
				}
			});
			if(cfg.focusShow) {
				this.bind(this.target, 'focus', function(e){
					_this.target.triggerHandler('keyup', e);
					_this.show();
				});
			}
			this.delegate('li.select-item', 'click', function(e){
				_this.selected($(this));
				_this.hide();
			});
			this.delegate('li.select-item', 'mouseenter', function(e){
				_this.setLight($(this));
			});
		},
		filter: function(str){
		    var list = this.list.children('li').filter(function(index){
		    	var txt = $(this).html();
		    	return Y.String(txt).isLike(str)
		    });
		    return list;
		},
		selected: function(li){
			var val = li.html();
			this.list.children('li').removeClass('select-selected');
			li.addClass('select-selected');
			this.target.val(val);
			this.fire('select', li.html());
		},
		setLight: function(li){
			this.list.children('li').removeClass('select-light');
			li.addClass('select-light');
		},
		add: function(param){
			var _this = this;
			param = $.isArray(param)?param:[param];
			$.each(param, function(i,item){
				var li = $('<li>').addClass('select-item').html(item);
				_this.list.append(li);
			});
		},
		remove: function(val){
			var _this = this;
			this.list.children('li').each(function(){
				if($(this).html() == param) {
					$(this).remove();
				}
			});
		},
		countSize: function(){
			this.el.css('width', this.target.outerWidth());
		}
	});
	Y.AutoComplete.defaults = $.extend({},Y.Tip.defaults,{
		autoDisappear: true,
		closeAction: 'hide',
		css: '',
		renderTo : 'body',
		spacing : 3 ,
		speed : 0 ,
		align: 'bottom',
		autoShow: false
	});
	
});
  
})($);