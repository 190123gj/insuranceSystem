/*
name: 图片播放器组件
*/
(function($){

define(function(require, exports, module){
	require("./Y-window");
    Y.inherit('ImagePlayer','Window',{
		doInit: function(cfg){
		    var el = $('<div>').addClass('photo-browse');
			var main = $('<div>').addClass('main-photo');
			var img = $('<img>').addClass('transform0 imageplayer-img')
			    .css('position','absolute');
			var bar = $('<div>').addClass('mod-area');
			var closeBtn = $('<a>').attr('href','javascript:;')
			    .addClass('close-photo close');
			var prevBtn = $('<a>').attr('href','javascript:;')
			    .addClass('prev-photo');
			var nextBtn = $('<a>').attr('href','javascript:;')
			    .addClass('next-photo');
			var infoArea = $('<p>').addClass('info-title');
			el.append(main.append(img)).append(bar)
			    .append(prevBtn).append(nextBtn)
				.append(closeBtn);
			$.extend(cfg,{
			    content: el,
				closeEle: closeBtn,
				simple: true
			});
			$.extend(this,{
			    main: main,
				img: img,
				bar: bar,
				closeBtn: closeBtn,
				prevBtn: prevBtn,
				nextBtn: nextBtn,
				infoArea: infoArea
			});
			this.callBase('doInit','Window',cfg);
		},
		doRender: function(){
		    var _this = this,cfg = this.cfg;
		    this.callBase('doRender','Window');
			this.renderBar(this.bar);
			this.loadImage();
			this.bind(this.nextBtn,'click',function(){
			    _this.next();
			});
			this.bind(this.prevBtn,'click',function(){
			    _this.prev();
			});
			if(cfg.mouseWheelAble) {
			    this.liveMouseWheel();
			}
			if(cfg.dragImageAble) {
			    this.liveDragImage();
			}
			if(cfg.escCancelAble) {
			    this.bind(this.renderTo,'keyup',function(e){
					if(e.keyCode === 27) {
						if(!_this.isFull) {
							_this.close();
							return;
						}
						if(_this.bar.btns.full) {
							_this.bar.btns.full.trigger('click');
						} else {
							_this.cancelFull();
						}
					}
				});
			}
		},
		//渲染工具栏
		renderBar: function(bar){
		    var _this = this,cfg = this.cfg;
		    var s = this.strings;
			bar.append(this.infoArea);
		    var btns = [
			    {name:'bigger',cls:'magnify',tip:s.bigger,handler:function(){
				    _this.bigger();
				}}, 
				{name:'smaller',cls:'lessen',tip:s.smaller,handler:function(){
				    _this.smaller();
				}},
				{name:'rotaL',cls:'rota-l',tip:s.rotaL,handler:function(){
				    _this.rotaLeft();
				}},
				{name:'rotaR',cls:'rota-r',tip:s.rotaR,handler:function(){
				    _this.rotaRight();
				}},
				{name:'full',cls:'full',tip:s.full,handler:function(){
				    if(_this.isFull) {
					    _this.cancelFull();
					} else {
					    _this.full();
					}
				}}
			];
			btns = $.map(btns,function(item){
			    if(!cfg[item.name+"Able"]) {
				    return null;
				}
				return item;
			});
			if($.isArray(cfg.btns)) {
			    $.merge(btns,cfg.btns);
			}
			var barBtns = {};
			$.each(btns,function(i,item){
			    var btn = $('<a>')
				    .attr('href','javascript:;')
					.attr('title',item.tip)
					.addClass(item.cls).data('info',item);
				_this.bind(btn,'click',function(){
				    item.handler.call(this);
				});
				bar.append(btn);
				barBtns[item.name] = btn;
			});
			$.extend(bar,{
			    btns: barBtns
			});
		},
		//激活鼠标滚轮缩放效果,会以鼠标位置为中心点缩放
		liveMouseWheel: function(){
		    var _this = this,cfg = this.cfg;
			var mousewheel = navigator.userAgent.indexOf("Firefox") > 0 ? 'DOMMouseScroll' : 'mousewheel';
			this.el.unbind(mousewheel);
			this.bind(this.el,mousewheel,function(e,t){
			    var oe = e.originalEvent;
			    var detail = oe.detail || oe.wheelDelta;
				var key;
				if(Math.abs(detail) >= 120) {
				    key = detail>0?'up':'down';
				} else {
				    key = detail<0?'up':'down';
				}
			    var point = _this.mapping({
				    x: oe.pageX || oe.clientX,
					y: oe.pageY || oe.clientY
				});
				var fn = key==='up'?'bigger':'smaller';
				_this[fn](cfg.mouseWheelScale,point);
				return false;
			});
		},
		//激活鼠标拖动图片效果
		liveDragImage: function(){
		    var _this = this;
			var down = false,sp,lp,now,rect;
			this.bind(this.el,'mousedown',function(e){
			    down = true;
				sp = {x: e.pageX, y: e.pageY};
				rect = _this.imageRect();
				return false;
			});
			this.bind(this.el,'mousemove',function(e){
			    if(!down) return false;
			    down = true;
				now = {x: e.pageX, y: e.pageY};
				var dx = now.x - sp.x, dy = now.y - sp.y;
				_this.imageMoveTo({x: rect.x + dx, y:rect.y + dy});
			    return false;
			});
			this.bind(this.el,'mouseup',function(e){
			    down = false;
				return false;
			});
		},
		//播放器全屏
		full: function(){
		    if(this.isFull) return;
		    if(!this.originalSize) {
			    this.originalSize = {
				    width: this.el.width(),
					height: this.el.height()
				}
			}
			this.el.css({
			    width: $(window).width(),
				height: $(window).height()
			});
			this.doSize();
			this.centerImage();
			this.bar.btns.full.attr('title',this.strings.cancelFull);
			this.isFull = true;
			this.fire('full');
		},
		//取消全屏
		cancelFull: function(){
		    if(!this.isFull) return;
		    this.el.css(this.originalSize);
			this.doSize();
			this.centerImage();
			var fullBtn = this.bar.btns.full;
			fullBtn.attr('title',fullBtn.data('info').tip);
			this.isFull = false;
		},
		//放大
		bigger: function(scale,centerPoint){
		    scale = scale || this.cfg.scale;
		    this.zoom(this.scale + scale,centerPoint);
		},
		//缩小
		smaller: function(scale,centerPoint){
		    scale = scale || this.cfg.scale;
		    this.zoom(this.scale - scale,centerPoint);
		},
		/*缩放到目标比例，并修正缩放造成的偏移，
		  参数：比例,中心点坐标
		*/
		zoom: function(scale,centerPoint){
		    var img = this.img,_this = this,cfg = this.cfg;
		    if(scale > cfg.scaleUpperLimit ||
			   scale < cfg.scaleLowerLimit) {
			    return;
			}
		    var ow = this.imgOriginalSize.width,
			    oh = this.imgOriginalSize.height;
		    var rect = this.imageRect();
			centerPoint = centerPoint || {
			    x: rect.x + rect.w/2,
				y: rect.y + rect.h/2
			}
			var dx = centerPoint.x - rect.x,
			    dy = centerPoint.y - rect.y;
			var dScale = scale - this.scale;
			img.css('width',ow*scale);
			img.css('height',oh*scale);
			this.imageMove(-1*dx * dScale/scale,-1*dy * dScale/scale);
			this.scale = scale;
			this.fire('zoom');
		},
		//将图片还原为原始大小并重新居中
		imageRestore: function(){
		    var img = this.img, _this = this;
		    var src = img.attr('src');
			if(!src) return;
			img.css({
			    width: 'auto',
				height: 'auto'
			});
			this.rotation(0);
			this.scale = 1;
			this.imgOriginalSize = {
			    width: img.width(),
				height: img.height()
			};
			this.img.bind('load',function(){
			    _this.imgOriginalSize = {
			        width: img.width(),
				    height: img.height()
			    };
			    _this.centerImage();
				$(this).unbind('load',arguments.callee);
			});
			this.centerImage();
		},
		//居中图片
		centerImage: function(center){
		    var img = this.img;
			this.imageMoveTo({
			    x: (this.el.width()-img.width())/2,
				y: (this.el.height()-img.height())/2
			});
		},
		//逆时针旋转
		rotaLeft: function(){
		    var angle = this.angle?this.angle - 90:-90;
			this.rotation(angle);
		},
		//顺时针旋转
		rotaRight: function(){
		    var angle = this.angle?this.angle + 90:90;
			this.rotation(angle);
		},
		//变换角度，参数：目标角度（目前仅支持90的倍数）
		rotation: function(angle){
		    angle = parseInt(angle);
			if(isNaN(angle)) return false;
			angle = angle % 360;
			if(angle<0) angle = 360 + angle;
		    var clses = {
			    0: 'transform0',
			    90: 'transform90',
				180: 'transform180',
				270: 'transform270'
			};
			this.img.removeClass(clses[this.angle])
			    .addClass(clses[angle])
				.css('filter','progid:DXImageTransform.Microsoft.BasicImage(rotation='+angle/90+')');
			this.centerImage();
			this.angle = angle;
			this.fire('rotation');
		},
		//载入图片，如已有则重新载入,参数：图片信息
		loadImage: function(imgs){
		    var cfg = this.cfg;
		    imgs = imgs || cfg.images || cfg.imgs;
			if(!imgs) return false;
			if(typeof imgs === 'string') {
			    imgs = $(imgs);
			}
			imgs = $.map(imgs,function(item){
			    if(!$.isPlainObject(item)) {
				    if(typeof item == 'string') {
					    return {src: item};
					} 
					return {
					    src: $(item).attr('src'),
						desc: $(item).attr('title')
				    };
				} else {
				    return item;
				}
			});
			this.imgInfos = imgs;
			this.change(cfg.index || cfg.firstIndex || 0);
			this.fire('loadimage');
		},
		//切换到下一张
		next: function(){
		    var cfg = this.cfg;
		    var index = this.index + 1;
			if(index >= this.imgInfos.length) {
			    if(cfg.loop) index = 0;
				else return false;
			}
			this.change(index);
		},
		//切换到上一张
		prev: function(){
		    var cfg = this.cfg;
		    var index = this.index - 1;
			if(index < 0) {
			    if(cfg.loop) index = this.imgInfos.length-1;
				else return false;
			};
			this.change(index);
		},
		//切换图片，参数：目标图片index
		change: function(index){
		    if(this.playing) return;
		    var info = this.imgInfos[index],
			    _this = this,cfg = this.cfg;
			if(!info) return false;
			var img = this.img;
			var lastIndex = this.index;
			if(lastIndex && index == lastIndex) return false;
			this.playimg = true;
			var changeInfo = function(){
			    img.attr('src',info.src);
				_this.infoArea.html(info.desc || '');
				img.attr('title',info.desc || '');
			    img.fadeIn(function(){
				    _this.playimg = false;
				});
				if(index === 0) {
				    _this.prevBtn.attr('title',_this.strings.firstTip);
				} else if(index === _this.imgInfos.length-1) {
				    _this.nextBtn.attr('title',_this.strings.lastTip);
				} else {
				    _this.nextBtn.add(_this.prevBtn)
					    .removeAttr('title');
				}
				_this.imageRestore();
			}
			if(img.attr('src')) {
			    img.fadeOut(function(){
				    changeInfo();
				});
			} else {
			    changeInfo();
			}
			this.index = index;
			this.fire('change',index,lastIndex);
		},
		//把页面坐标映射为播放器坐标
		mapping: function(point){
		    var off = this.el.offset();
		    return {
			    x: point.x - off.left,
				y: point.y - off.top
			}
		},
		//获得图片尺寸位置信息
		imageRect: function(){
		    var img = this.img;
		    return {
			    x: parseFloat(img.css('left')),
			    y: parseFloat(img.css('top')),
				w: img.width(),
				h: img.height()
			}
		},
		imageMoveTo: function(point){
			this.img.css({left: point.x, top: point.y});
		},
		imageMove: function(x,y){
		    var point = this.imageRect();
			this.imageMoveTo({x: point.x + x,y: point.y + y});
		}
	});
	Y.ImagePlayer.defaults = $.extend({}, Y.Window.defaults, {
		scale: 0.1,
		mouseWheelScale: 0.02,
		scaleUpperLimit: 3,
		scaleLowerLimit: 1,
		mouseWheelAble: true,
		dragImageAble: true,
		biggerAble: true,
		smallerAble: true,
		rotaLAble: true,
		rotaRAble: true,
		fullAble: true,
		escCancelAble: true,
		btns: null,
		loop: true
	});	
});
  
})($);
