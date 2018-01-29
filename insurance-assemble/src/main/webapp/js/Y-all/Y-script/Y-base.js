/*
   Y-base 0.0.0
*/
(function ($){

define(function(require, exports, module){

    if(typeof window.Y != 'object') window.Y = {};
    
    require("./language/Y-language");
    require("./Y-browser");

	$.extend(Y,{
		config: {
			language: 'zh'
		},
		all: {},
		getKey:function(head){
		    head = head || 'Y-';
		    var key = head + Math.round(Math.random()*10000);
			if(Y.all[key]) {
			   key = Y.getKey(head + 'R-');
			} else {
			    return key;
			}
			return key;
		},
		join: function(cmp){
		    var all = Y.all;
		    if(all[cmp.key]) {
			    Y.handlerError('组件key('+cmp.key+')重复，前组件印象被覆盖');
			}
		    all[cmp.key] = cmp;
		},
		getCmp: function(key){
		    return Y.all[key];  
		},
		remove: function(el){
		    var cmp = typeof el == 'object'?el:Y.getCmp(el);
			if(cmp) {
			    Y.all[cmp.key] = null;
			}
		},
		inherit: function(name,base,opt){//新增组件，并可继承一个基类
		    if(Y[name]) return;
		    if(base) {
			    var str = base;
			    base = typeof base=='function'?base:Y[base];
				if(!base) {
				    Y.handlerError("基类("+str+")不存在");
					return;
				}
				Y[name] = function(cfg){
				    this.initConfig = this.initConfig || cfg;
					cfg = $.extend(true,{},Y[name].defaults || {},cfg);
	                this.cfg = cfg;
					this.xType = this.xType?this.xType + "," + name:name;
				    base.call(this,cfg);
	            }
				Y[name].base = base;
				$.extend(Y[name].prototype,base.prototype,opt,{
				    base:base
				});
			} else {
			    Y[name] = function(cfg){
				    this.key = cfg.key || Y.getKey();
			        Y.join(this);
				    cfg = cfg || {};
	                this.cfg = cfg;
					this.xType = this.xType?this.xType + "," + name:name;
					this.listeners = {};
				    if(this.init(cfg) === false) this.createFail = true;
					if(!this.createFail && cfg.autoShow && typeof this.show === 'function'){
					    this.show();
					}
	            }
				$.extend(Y[name].prototype,opt);
			}
			Y[name].on = function(ev,handler){
				Y[name].listeners = Y[name].listeners || {};
				Y[name].listeners[ev] = Y[name].listeners[ev] || [];
				Y[name].listeners[ev].push(handler);
			}
			setTimeout(function(){
			    $.each(Y.componentReadys, function(i,fn){
				    fn(name);
			    });
			},1);
	    },
		cmpReady: function(fn){//注册组件代码加载完成的回调，回调获得参数组件名称
			Y.componentReadys.push(fn);
		},
		create: function(name,option){//推荐的组件创建函数
		    if(typeof Y[name] != 'function') {
			    Y.handlerError("不存在该成员或该成员不是函数方法");
				return;
			}
			var y_obj = new Y[name](option);
			return y_obj;
		},
		errorList: [],
		componentReadys: [],
		handlerError: function(err){
		    Y.errorList.push(err);
		},
		init: function(){
		    require.async('../Y-source/css/Y-all.css');
		    if(!Y.config.language || Y.config.language == 'auto') {
		    	Y.language.name = $('#code_language').html() || Y.browser.language || 'zh';
		    } else {
		    	Y.language.name = Y.config.language || 'zh';
		    }
		}
	});
	
	Y.init();
	
	$.extend($.fn,{
	    Y: function(name,option){
		    option = option || {};
			if(typeof option != 'object') {
			    option = {content: option};
			}
			this.each(function(i,item){
			    if(!option.target) option.target = $(item);
			    var y_obj = Y.create(name,option);
			});
			return this;
		}
	});
	
	Y.inherit('component',null,{
	    init: function(cfg){
			if(typeof cfg.listeners == 'object') {
				for(var i in cfg.listeners) {
					this.listeners[i] = [cfg.listeners[i]];
				}
			}
			var strings = {};
			$.each(this.getXTypes().reverse(), function(i,item){
				$.extend(strings, Y.language.getPact(item));
			});
			this.strings = $.extend(strings, cfg.strings || {});
		    if(this.doInit(cfg) === false) return false;
			this.fire('init');
		},
		doInit: function(cfg){
		},
		distroy: function(){
		    this.destory();
		},
		getXType: function(){
			return this.xType.split(",")[0];
		},
		getXTypes: function(){
			return this.xType.split(',');
		},
		destroy: function(){
		    if(this.isDestroy) {
			    return false;
			}
		    this.doDestroy();
			this.isDestroy = true;
			this.isDistroy = this.isDestroy;
			this.fire('destroy');
		},
		doDestroy: function(){
		},
		callBase: function(name,base){
		    base = typeof base == 'function'?base:Y[base];
			if(!base) base = Y.component;
			return base.prototype[name].apply(this,Array.prototype.slice.call(arguments,2));
		},
		method: function(name){
		    if(this.isDestroy) {
			    Y.handlerError("组件已销毁");
			    return;
			}
			var self = this;
			try {
			    return self[name].apply(self,Array.prototype.slice.call(arguments,1));
			} catch(e) {
			    Y.handlerError(e);
			}
		},
		on: function(name,handler){
		    if(!this.listeners[name]) {
			    this.listeners[name] = [];
			}
			this.listeners[name].push(handler);
		},
		fire: function(name){
		    var args = Array.prototype.slice.call(arguments,1);
		    var evs = this.listeners[name];
			var _this = this;
			var mark;

			/*调用组件全局事件*/
			if(this.cfg.fireCommon !== false) {
				var types = this.xType.split(',');
				$.each(types,function(i,type){
					if(!Y[type]) return;
					if(Y[type].listeners && Y[type].listeners[name]) {
						if($.isArray(Y[type].listeners[name])) {
							$.each(Y[type].listeners[name],function(j,item){
								if(item.apply(_this, args) === false) mark =false;
							});
						} else {//兼容原写法
							if(Y[type].listeners[name].apply(_this,args) === false) {
								mark = false;
							}
						}
					}
				});
			}
			
			
			/*调用事件名配置的事件*/
			if(this.cfg[name]) {
			    if(this.cfg[name].apply(_this,args) === false) {
				    mark = false;
				};
			}
			
			if(!evs) {
			    return mark;
			}
			/*调用listeners配置的及on方法注册的事件*/
			$.each(evs,function(i,item){
			    if(item.apply(_this,args) === false) {
				    mark = false;
				}
			});
			return mark;
		}
	});
	Y.inherit('box','component',{
		doInit: function(cfg){
		    var el = typeof cfg.el == 'object'?$(cfg.el):$('<' + (cfg.el || 'div') + '>');
			if(cfg.style) {
			    el.css(cfg.style);
			}
			if(cfg.css && !cfg.noStyle) {
			    el.addClass(cfg.css);
			}
			el.hide();
			this.el = el;
			this.handlers = [];
			this.callBase('doInit','component');
		},
		render:function(renderTo){
		    if(this.isRender || this.isDistoy) return false;
			this.renderTo = renderTo || this.renderTo || this.toJqObj(this.cfg.renderTo || 'body');
		    this.doRender();
			this.isRender = true;
			this.fire('render');
		},
		doRender: function(){
			this.renderTo.append(this.el);
		},
		show:function(){
		    if(this.isDestroy || this.createFail) return false;
		    if(this.showwing) return;
		    if(!this.isRender) this.render();
		    if(this.fire('beforeshow') === false) {
				return false;
			}
			this.doShow();
			this.isShow = true;
			this.showwing = true;
			this.fire('show');
		},
		doShow: function(){
		    var cfg = this.cfg;
			if(this.isShow) {
			    this.el.fadeIn();
			} else {
			    this.el.stop(true,true);
			    this.el.fadeIn();
				this.el.css({width:cfg.width,height:cfg.height});
			}
		},
		hide: function(callback){
		    var _this = this;
            this.doHide(function(){
			    _this.el.hide();
			    _this.fire('hide');
				if(callback) callback();
			});
			this.showwing = false;
		},
		doHide:function(callback){
		    this.el.stop(true,true);
		    this.el.fadeOut(callback);
		},
		close: function(){
		    if(this.isDestroy) {
			    return false;
			}
			if(this.fire('beforeclose') === false) {
				return false;
			}
			var _this = this;
			if(this.cfg.closeAction == 'hide') {
			    this.hide(function(){
				    _this.fire('close');
				});
			} else {
			    this.el.fadeOut(function(){
				    _this.doClose();
					_this.fire('close');
				});
			}
			this.showwing = false;
		},
		doClose:function(){
		    var _this = this;
			setTimeout(function(){
				_this.destroy();
			},1);
		},
		doDestroy: function(){
		    this.clearHandlers();
		    if(this.el) this.el.remove();
			var _this = this;
			setTimeout(function(){
			    _this.el = null;
			    _this.container = null;
			    _this.item = null;
			},1);
			this.callBase('doDestroy', 'component');
		},
		clearHandlers: function(){
		    $.each(this.handlers || [],function(i,item){
			    if(item.type && item.type == 'live') {
				    item.obj.die(item.ev,item.fn);
				} else if(item.type && item.type == 'delegate') {
				    if(this.el) this.el.undelegate(item.obj,item.ev,item.fn);
				} else {
				    item.obj.unbind(item.ev,item.fn);
				}
			});
		},
		rect: function(){
		    var off = this.el.offset();
		    return {
			    x: off.left, y: off.top,
				w: this.el.outerWidth(), h: this.el.outerHeight()
			};
		},
		bind: function(obj,ev,fn,type){
		    if(!this.handlers) this.handlers = [];
		    if(typeof ev!='string' || typeof fn!='function') {
			    return;
			}
		    obj = this.toJqObj(obj);
			obj[type || 'bind'](ev,fn);
			this.handlers.push({
			    obj: obj,ev: ev,fn: fn,type: type
			});
		},
		delegate:function(selector,ev,fn){
		    this.el.delegate(selector,ev,fn);
			this.handlers.push({
			    obj: selector,ev: ev,fn: fn,type: 'delegate'
			});
		},
		toJqObj: function(param){
		    var obj;
		    if(typeof param != 'object') {
	            var jobj = $(param);
		        if(jobj.length) {
		            obj = jobj;
		        } else {
		            obj = $('<span>').css({'width': this.cfg.width || 'auto',height: this.cfg.height || 'auto'}).html(param);
		        }    
	        } else {
	            obj = $(param);
	        }
		    return obj;
	    }
	});
	
	Y.inherit('container','box',{
	    doInit:function(cfg){
			if(cfg.content) {
			    var item = this.toJqObj(cfg.content);
				this.item = item;
			}
			this.callBase('doInit','box',cfg);
		},
		doRender: function(){
			this.callBase('doRender','box');
			var item = this.item;
			if(!item) return;
			if(this.body) {
			    this.el.append(this.body);
				this.body.append(item);
			} else {
			    this.el.append(item);
			}
		},
	    countSize: function(){
		    var size = {};
		    if(this.cfg.autoFill) {
			    size = {width: '100%',height: '100%'}
			} else if(this.cfg.autoSize) {
			    if(this.el.get(0).tagName != 'DIV') {
				    size = {width: 'auto',height: 'auto'};
			    }
			    size = {width: this.item.outerWidth(),height: 'auto'};
			} else {
			    size = {width: this.cfg.width || 'auto',height: this.cfg.height || 'auto'};
			}
			this.el.css(size);
		},
		doSize: function(){
		    this.layout();
		},
		layout: function(){
		    this.doLayout();
			this.isLayout = true;
			this.fire('layout');
		},
		doLayout: function(){
		    if(!this.item) return;
		    this.countSize();
		},
		show:function(){
		    if(this.callBase('show','box') === false) return false;
			if(!this.isLayout) this.layout();
		}
	});

});


})($);
