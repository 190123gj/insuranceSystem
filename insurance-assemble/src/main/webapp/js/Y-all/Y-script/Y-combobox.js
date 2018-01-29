
(function($){

define(function(require, exports, module){
	require("./Y-base");
	Y.inherit('Combobox','box',{
	    doInit: function(cfg){   
		    this.callBase('doInit','box',cfg);
		    cfg.renderTo = cfg.renderTo || cfg.target || 'body';
            var _this = this;		
		    var combo = $('<span>').addClass('select-combo');
			var main = $('<input>').attr({type:'text',name: cfg.name, autocomplete: 'off'}).addClass('select-input');
			var list = $('<ul>').css({position:'absolute',zIndex:6000,overflow:'hidden',display:'none'}).addClass('select-list');
			if(cfg.itemsHeight) {
			    list.css({height: cfg.itemsHeight,'overflow-y':'auto'});
			}
			var img = $('<a>').addClass(cfg.openCss).attr({
			    'href':'javascript:;',
				title: this.strings.open
			});
			var store = [];
			$.each(cfg.store,function(i,item){
			    var arecord;
			    if(typeof item == 'string') {
				    arecord = {text: item,value: item};
				} else if(typeof item == 'object'){
				    arecord = item;
				}
				store.push(arecord);
			});
			this.store = store;
			$.each(store,function(i,item){
			    var aselect = $('<li>').addClass(item.css || cfg.itemCss).html(item.text).attr('title',item.text);
				aselect.data({'index':i,value:item.value});
				list.append(aselect);
			});
			combo.append(main).append(img);
			$('body').append(list);
			cfg.content = combo;
			this.selectObj = img;
			this.list = list;
			this.main = main;
			this.combo = combo;
			img.click(function(){
			    if(!list.isShow) {
				    _this.showList();
					_this.getItemList().show();
				} else {
				    _this.hideList();
				}
			});
			this.bind($(document),'click',function(e){
				if(e.target !== img.get(0) && e.target !== main.get(0)) 
				    _this.hideList();
			});
			this.bind(this.getItemList(),'click',function(){
			    var _self = $(this);
			    _this.select(_self);
				_this.hideList();
			});
			this.bind(this.getItemList(),'mouseover',function(){
			    var _self = $(this);
				if(_this.selectItem != _self) {
					_this.setLight(_self);
				}
			});
			this.bind(main,'blur',function(){
				if($(this).val() === _this.selectText || !cfg.mustSelect) {
				    return;
				}
				if(_this.selectItem && cfg.mustSelect) {
				    main.val(_this.selectText);
				} else {
				    main.val('');
				}
			});
			this.bind(main,'keyup',function(ev){
			    if(!cfg.editAble) return;
			    var key = ev.keyCode || ev.witch;
				if(key == 13 || key == 38 || key == 40) {
				    return;
				}
			    var txt = main.val();
			    var itemList = _this.quary(txt);
				_this.getItemList().data({'isShow':false}).hide();
				$.each(itemList,function(i,item){
				    item.data({'isShow':true}).show();
				});
			    if(itemList.length) {
				    if(!_this.list.isShow) {
					    _this.showList();
					}
					_this.setLight(itemList[0]);
			    } else {
				    if(_this.list.isShow) {
					    _this.hideList();
					}
				}
			});
			this.bind(main,'keydown',function(ev){
			    var key = ev.keyCode || ev.witch;
				if(key == 13) {
				    if(_this.list.lightIndex || _this.list.lightIndex == 0) {
					    _this.select(_this.list.lightIndex);
						_this.hideList();
					}
				} else if(key == 38 || key == 40){
				    var allItems = _this.getItemList();
					var items = [];
					allItems.each(function(i,item){
					    if($(item).css('display')!='none') {
						    items.push($(item));
						}
					});
				    if(items.length > 0 && key == 38) {
					    var setLight = function(index){
						    if(index<0) return;
						    if(_this.getItem(index).css('display')!='none'){
							    _this.setLight(index);
								return;
							} else {
							    setLight(index-1);
							}
						}
					    setLight(_this.list.lightIndex-1);
					}
					if(items.length > 0 && key == 40) {
					    var setLight = function(index){
						    if(index >= _this.getItemList().length) return;
						    if(_this.getItem(index).css('display')!='none'){
							    _this.setLight(index);
								return;
							} else {
							    setLight(index+1);
							}
						}
					    setLight(_this.list.lightIndex+1);
					}
				}
			});
			
			if(!cfg.editAble) {
				main.attr('readonly','readonly');
			}
			if(cfg.change) {
			    this.change(cfg.change);
			}
			if(cfg.select) {
			    this.select(cfg.select);
			}
			this.el = combo;
		},
		doShow: function(){
		    this.callBase('doShow','box');
			var _this = this;
			var cfg = this.cfg;
			_this.doSize();
		},
		doSize: function(){
			var _this = this, cfg = this.cfg;
			var inputWidth;
			var selectWidth = this.selectObj.outerWidth() || 20;
			if(cfg.autoFill) {
			   this.combo.css({width: '100%',height:'100%'});
			   this.main.css({width: this.combo.innerWidth() - this.selectObj.outerWidth() - 6,height:'100%'});
			   return;
			} else if(!cfg.width || cfg.autoSize) {
				var strWidth = 0, inputWidth, comboWidth = 0;
				var list = this.getItemList();
				var fontSize = Math.max(parseInt(list.eq(0).css('font-size')),parseInt(_this.main.css('font-size')));
			    var strEl = $('<span>').css({
					'font-size': fontSize,
					visibility: 'hidden'
				}).appendTo('body');
			    list.each(function(i,item){
				    var str = $(item).html();
					//var clone = $(item).clone().css('width','auto');
					strEl.html(str);
					var width =strEl.outerWidth();
					if(width > strWidth) {
					    strWidth = width;
					}
				});
				strEl.remove();
				inputWidth = strWidth;
				comboWidth = inputWidth + selectWidth+6;
			} else {
				comboWidth = cfg.width;
				inputWidth = comboWidth - selectWidth - 6;
			}
			if(cfg.maxWidth && comboWidth > cfg.maxWidth) {
				comboWidth = cfg.maxWidth;
				inputWidth = comboWidth - selectWidth - 6;
			} else if(cfg.minWidth && comboWidth < cfg.minWidth){
				comboWidth = cfg.minWidth;
				inputWidth = comboWidth - selectWidth - 6;
			}
			this.main.css('width',inputWidth);
			this.combo.css('width',comboWidth);
		},
		showList: function(){
		    this.list.css({width: this.combo.width(),left: this.combo.offset().left,top: this.combo.offset().top + this.combo.outerHeight()});
		    this.main.focus();
		    this.list.fadeIn();
			var _this = this;
			var setLight = function(index){
			    if(index >= _this.getItemList().length) return;
			    if(_this.getItem(index).css('display')!='none'){
				    _this.setLight(index);
				    return;
			    } else {
				    setLight(index+1);
			    }
			}
			setLight(this.selectIndex || 0);
			this.list.isShow = true;
		},
		hideList: function(){
		    if(this.list.lightIndex || this.list.lightIndex == 0) {
			    this.getItem(this.list.lightIndex).removeClass(this.cfg.lightCss);
				this.list.lightIndex = null;
			}
		    this.list.fadeOut();
			this.list.isShow = false;
		},
		getItemList: function(){
		    var itemList = this.list.children('li');
			return itemList;
		},
		select: function(param,type,noEv){
		    if(typeof param == 'function') {
			    this.on('select',param);
				return;
			}
			this.doSelect(param,type);
			if(noEv) return;
			this.fire('select',this.selectValue,this.selectText,this.selectItem);
			if(this.lastValue != this.selectValue) {
				this.fire('change',this.selectValue,this.selectText,this.selectItem);
			}
		},
		setSelect: function(){
		    this.select.apply(this,arguments);
		},
		doSelect:function(param,type){
		    var cfg = this.cfg;
		    var item = (typeof param == 'object' && type !== 'value')?param : this.getItem(param,type);
			if(!item) return false;
			if(this.selectItem !== item) {
			    if(this.selectItem) {
				    this.selectItem.removeClass(cfg.selectCss);
			    }
			    item.addClass(cfg.selectCss);
		    }
			var lastValue = this.selectValue;
		    this.selectIndex = item.data('index');
			this.selectValue = item.data('value');
			this.selectText = item.html();
		    this.selectItem = item;
			this.lastValue = lastValue;
			this.main.val(item.html());
		},
		setValue: function(value){
		    this.select(value,'value');
		},
		setIndex: function(index){
		    this.select(index,'index');
		},
		setText: function(text){
			this.select(text,'text');
		},
		getText: function(){
			return this.selectText;
		},
		getValue: function(){
		    return this.selectValue;
		},
		setLight: function(param){
		    var cfg = this.cfg;
		    var item,index;
		    if(typeof param == 'object') {
			    item = param;
				index = this.getIndex(param);
		    } else {
		        item = this.getItem(param,'index');
			    index = param;
		    }
			item.addClass(cfg.lightCss);
			if(this.list.lightIndex || this.list.lightIndex == 0 && index != this.list.lightIndex) {
			    this.getItem(this.list.lightIndex).removeClass(cfg.lightCss);
			}
			this.list.lightIndex = index;
		},
		addItem: function(param,value,type){
		    var item;
			var _this = this;
		    if(typeof param == 'string') {
			    item = {text: param,value: param};
			}  else if($.isArray(param)){
			    $.each(param,function(i,aparam){
				    _this.addItem(aparam);
				});
			    if(value) {
			    	_this.select(value,type || 'value');
			    }
				if(_this.cfg.autoSize) {
			        _this.doSize();
			    }
				return;
			} else if(typeof param == 'object') {
			    item = param;
			}
			var aselect = $('<li>').html(item.text);
			aselect.addClass(this.cfg.itemCss);
			if(item.css) {
			    aselect.addClass(item.css);
			}
			aselect.data({'index':this.list.children().length,value:item.value});
			this.list.append(aselect);
			aselect.bind('mouseover',function(){
			    var _self = $(this);
				if(_this.selectItem != _self) {
					_this.setLight(_self);
				}
			});
			aselect.click(function(){
			    var _self = $(this);
			    _this.select(_self);
				_this.hideList();
			});
		},
		clear: function(){
		    this.list.empty();
			this.main.val('');
			var val = this.selectValue;
			this.selectValue = null;
			this.selectIndex = null;
			this.selectItem = null;
			this.selectText = null;
			if(val) {
				this.fire('change', '', '', null);
			}
		},
		getItem: function(param,type){
		    var result;
		    this.getItemList().each(function(i,item){
			    var mask;
				if((typeof param == 'number' && type !== 'value') || type === 'index') {
				    mask = i;
				} else if(type === 'text') {
					mask = $(item).html();
				} 
				else {
				    mask = $(item).data('value');
				}
			    if(mask == param) {
				    result = $(item);
				    return false;
				}
			});
			return result;
		},
		getIndex: function(obj){
		    var result;
		    this.getItemList().each(function(i,item){
			    if(item == obj.get(0)) {
				    result = i;
				    return false;
				}
			});
			return result;
		},
		change: function(fn){
		    this.on('change',fn);
		},
		compare : function(str,exp/*类似于SQL中的模糊查询字符串*/, i/*是否区分大小写*/) {
            var str = str;
            i = i == null ? false : i;
            if (exp.constructor == String) {
               var s=exp.replace(/\./g,"\\.");//by xyz 把.替换为\.
               /*将表达式中的‘%’替换成‘.’，但是‘[%]’表示对‘%’的转义，所以做特殊处理*/
                s = s.replace(/%/g, function(m, i) {
                    if (i == 0 || i == s.length - 1) {
                    return ".*";
                    }
                    else {
                        if (s.charAt(i - 1) == "[" && s.charAt(i + 1) == "]") {
                            return m;
                        }
                    return ".*";
                    }
                });

                /*将表达式中的‘[_]’、‘[%]’分别替换为‘_’、‘%’*/

                s = s.replace(/\[_\]/g, "_").replace(/\[%\]/g, "%");

                /*对表达式处理完后构造一个新的正则表达式，用以判断当前字符串是否和给定的表达式相似*/

                var regex = new RegExp("^.*" + s, i ? "" : "i");
                return regex.test(str);
            }
            return false;
        },
		quary: function(str){
		    var itemList = [];
			var _this = this;
			$.each(this.getItemList(),function(i,item){
			    var txt = $(item).html();
				if(_this.compare(txt,str)) {
				    itemList.push($(item));
				}
			});
			return itemList;
		},
		doDestroy: function(){
			this.list.remove();
			this.callBase('doDestroy', 'box');
		},
		getStrSize: function(param){
		    var strSize = parseInt(this.list.find('li').css('font-size')) || 10;
			var strSpacing = 0;
			if(this.cfg.style && this.cfg.style['letter-spacing']) {
			    strSpacing = this.cfg.style['letter-spacing'];
			}
			var width = 0;
			param = param.split('');
			for(var i = 0;i < param.length;i++ ) {
			    if((/^[\u4e00-\u9fa5]$/).test(param[i])) {
				        width += strSize;
						width += strSpacing*2;
			    } else {
				    width += strSize/2;
			    }
			}
			return {width: width,height: strSize};
		}
		
	});
    Y.Combobox.defaults = {
		disabled: false,
		editAble: true,
		store: [],
		name: 'select',
		autoSize: true,
		mustSelect: true,
		itemCss: 'select-item',
		selectCss: 'select-selected',
		lightCss:'select-light',
		openCss:'select-open',
        el:'span',
        minWidth: 60,
		autoShow: true
	}
});

})($);