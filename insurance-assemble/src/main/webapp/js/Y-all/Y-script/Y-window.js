(function($) {

	define(function(require, exports, module) {

		require("./Y-base");
		if (Y.Window) return;
		Y.inherit('Window', 'container', {
			doInit: function(cfg) {
				var _this = this;
				if (cfg.url) {
					this.loadRemote();
				}
				if (cfg.simple && typeof cfg.contentClone == 'undefined') {
					cfg.contentClone = false;
				}
				var defaults = Y.Window.defaults;
				var renderTo = this.toJqObj(cfg.renderTo || cfg.target || 'body');
				var wndStyle = cfg.wndStyle,
					headerStyle = cfg.headerStyle,
					bodyStyle = cfg.bodyStyle;
				var item = this.toJqObj(cfg.content);
				if (item.parent().length > 0) {
					if (cfg.contentClone) {
						item = item.clone(true, true);
					} else {
						this.contentParent = item.parent();
					}
					item.show();
				}
				if (cfg.removeLocation) {
					if (item.css('position') && item.css('position') != 'static') {
						item.css({
							left: '',
							top: '',
							position: ''
						});
					}
				}
				if (cfg.simple) {
					$.extend(this, {
						container: renderTo,
						renderTo: renderTo,
						el: item,
						body: item,
						wnd: item,
						item: item,
						cfg: cfg
					});
					this.wnd.css(wndStyle);
					$.extend(cfg, {
						noheader: true
					});
					this.simple = true;
					this.setManager(Y.Window.manager);
					return;
				}
				var $header = $("<div>").append($('<span>').html(cfg.title).addClass('wnd-title'));
				var $body = $("<" + (cfg.bodyEle || "div") + ">").css('display', 'block');
				var $closeBtn = $("<a>").attr({
					href: 'javascript:void(0)'
				});
				if (cfg.closeAble === false) {
					$closeBtn.hide();
				}
				var $wnd = $("<" + cfg.el + ">");
				$header.append($closeBtn).css(headerStyle);
				$body.css(bodyStyle);
				$wnd.css(wndStyle);
				$header.addClass(cfg.headerCss);
				$body.addClass(cfg.bodyCss);
				$wnd.addClass(cfg.wndCss);
				$closeBtn.addClass(cfg.closeBtnCss);
				$wnd.hide();
				$.extend(this, {
					container: renderTo,
					renderTo: renderTo,
					header: $header,
					body: $body,
					wnd: $wnd,
					el: $wnd,
					closeBtn: $closeBtn,
					item: item
				});
				this.setManager(Y.Window.manager);
			},
			loadRemote: function() {
				var cfg = this.cfg,
					_this = this;
				if (cfg.iframe === false) {
					$.ajax({
						url: cfg.url,
						async: false,
						success: function(res) {
							res = res.replace(/<!\-\-.*>/g, ''); //移除html注释
							cfg.content = $(res);
						}
					});
				} else {
					var frm = $("<iframe src='javascript:;' scroll='no' frameborder='no'></iframe>");
					cfg.content = frm;
					this.on('render', function() {
						frm.attr('src', cfg.url);
						this.bind(frm, 'load', function() {
							frm.css({
								width: 'auto',
								height: 'auto'
							});
							_this.layout();
							_this.iframeLoad = true;
						});
					});
					this.iframe = frm;
				}
			},
			doRender: function() {
				if (!this.cfg.noheader) {
					this.el.append(this.header);
				}
				this.renderTo.append(this.el);
				if (!this.cfg.simple) {
					this.el.append(this.body);
					this.body.append(this.item);
				}
				if (!this.cfg.noheader) {
					if (this.cfg.dragAble) {
						this.liveDrag(this.header);
					}
				} else {
					if (this.cfg.dragAble && this.cfg.dragEle) {
						this.liveDrag(this.cfg.dragEle);
					}
				}
				var _this = this;
				var closeEle;
				var optEle = this.cfg.closeEle;
				if (!optEle) {
					closeEle = this.closeBtn;
				} else {
					closeEle = this.cfg.noheader ? (typeof optEle == "object" ? optEle : this.wnd.find(optEle)) : this.closeBtn;
				}
				if (closeEle) {
					closeEle.attr('title', this.strings.close);
					this.bind(closeEle, 'click', function(e) {
						_this.close();
						e.stopPropagation();
					});
				}
				if (this.cfg.modal) {
					if (this.renderTo.get(0) == $('body').get(0)) {
						this.bind($(window), 'resize', function() {
							var docuSize = {
								width: $(document).width(),
								height: $(document).height()
							};
							if (_this.isDestroy) return;
							var rect = _this.rect();
							var wndH = $(window).height(),
								wndW = $(window).width();
							if (rect.y > wndH - rect.h && !_this.moving) {
								_this.center(true);
							}
							if (rect.x > wndW - rect.w && !_this.moving) {
								_this.center(true);
							}
							if (_this.over) {
								_this.over.css(docuSize);
							}
						});
					}
				}
			},
			liveDrag: function(ele) { //该函数激活拖拽
				var cfg = this.cfg,
					_this = this;
				var dragEle = $(ele, this.wnd) || this.header;
				var down = false,
					sp, lp, now, rect;
				this.bind(dragEle, 'mousedown', function(e) {
					down = true;
					sp = {
						x: e.pageX,
						y: e.pageY
					};
					rect = _this.wndRect();
				});
				this.bind($(document), 'mousemove', function(e) {
					if (!down) return false;
					down = true;
					now = {
						x: e.pageX,
						y: e.pageY
					};
					var dx = now.x - sp.x,
						dy = now.y - sp.y;
					_this.moveTo({
						x: rect.x + dx,
						y: rect.y + dy
					});
				});
				this.bind($(document), 'mouseup', function(e) {
					down = false;
				});
				dragEle.attr('title', this.strings.canDrag).css('cursor', 'move');
				this.dragAble = true;
			},
			showOver: function() {
				var over = $("<div>").addClass('wnd-over');
				over.css(this.cfg.overStyle);
				var left = this.container.offset().left,
					top = this.container.offset().top;
				var width = this.container.outerWidth(),
					height = this.container.outerHeight();
				if (this.container.get(0) == $('body').get(0)) {
					width = $(document).width();
					height = $(document).height();
					top = 0, left = 0;
				}
				var overIndex = parseInt(this.wnd.css('z-index')) - 1;
				over.css({
					width: width,
					height: height,
					left: left,
					top: top,
					position: 'fixed',
					zIndex: overIndex
				});
				over.hide();
				if ($.browser.msie && ($.browser.version == "6.0") && !$.support.style) {
					over.append($('<iframe>').attr('src', '').css({
						width: '100%',
						height: '100%',
						opacity: 0.01
					}));
				}
				this.container.append(over);
				over.fadeIn();
				this.over = over;
				this.isModal = true;
			},
			doShow: function() {
				this.callBase('doShow', 'container');
				if (this.cfg.modal) {
					this.showOver();
				}
				if (this.manager) {
					this.manager.toFront(this);
				}
			},
			doHide: function() {
				if (this.isModal && this.over) {
					this.over.remove();
					this.over = null;
				}
				this.callBase('doHide', 'container');
			},
			close: function() {
				if (this.callBase('close', 'container') === false) {
					return;
				}
				if (this.over) {
					this.over.fadeOut();
				}
			},
			doDestroy: function() {
				if (this.isModal && this.over) {
					this.over.remove();
					this.over = null;
				}
				if (this.contentParent && this.cfg.contentRecovery) {
					this.item.hide();
					this.contentParent.append(this.item);
				}
				if (this.manager) {
					this.manager.remove(this);
					this.manager = null;
				}
				this.clearHandlers();
				if (this.simple && this.contentParent) {
					this.contentParent.append(this.el);
					this.el = null;
				} else this.el.remove();
				var _this = this;
				setTimeout(function() {
					var props = ['item', 'wnd', 'header', 'closeBtn', 'body', 'el', 'iframe', 'over'];
					for (var i = 0; i < props.length; i++) {
						if (_this[props[i]]) delete _this[props[i]];
					}
				}, 1);
			},
			wndRect: function() {
				var wnd = this.wnd;
				var x = wnd.css('left'),
					y = wnd.css('top'),
					w = wnd.outerWidth(),
					h = wnd.outerHeight();
				var rect = {
					x: parseInt(x),
					y: parseInt(y),
					h: h,
					w: w
				};
				return rect;
			},
			setManager: function(manager) {
				this.manager = manager;
				var _this = this;
				manager.add(this);
				this.bind(this.wnd, 'mousedown', function() {
					manager.toFront(_this);
				});
			},
			removeManager: function() {
				if (this.manager) {
					this.manager.remove(this);
					this.manager = null;
				}
			},
			center: function(animate) {
				var x = ($(window).width() - this.wnd.width()) / 2 + ($(document).scrollLeft() || 0);
				var y = ($(window).height() - this.wnd.height()) / 2 + ($(document).scrollTop() || 0);
				this.moveTo({
					x: x,
					y: y
				}, animate);
			},
			centerX: function(animate) {
				var x = ($(window).width() - this.wnd.width()) / 2 + ($(document).scrollLeft() || 0);
				this.moveTo({
					x: x
				}, animate);
			},
			centerY: function(animate) {
				var y = ($(window).height() - this.wnd.height()) / 2 + ($(document).scrollTop() || 0);
				this.moveTo({
					y: y
				}, animate);
			},
			moveTo: function(point, animate, callback) {
				var _this = this;
				if (animate) {
					this.moving = true;
					var speed = $.isNumeric(animate) ? animate : 'fast';
					this.wnd.animate({
						left: point.x,
						top: point.y
					}, speed, function() {
						delete _this.moving;
						if (callback) callback();
					});
				} else {
					this.wnd.css({
						left: point.x,
						top: point.y
					});
				}
				this.fire('move');
			},
			countFrmSize: function() {
				var iframe = this.iframe;
				var cfg = this.cfg;
				var headerHeight = cfg.noheader ? 0 : this.header.outerHeight();
				var frmWnd = iframe[0].contentWindow;
				var cfgWidth = parseInt(cfg.width || cfg.wndStyle.width),
					cfgHeight = parseInt(cfg.height || cfg.wndStyle.height);
				if (!isNaN(cfgWidth) && !cfg.autoSize) {
					iframe.css('width', cfgWidth - (this.simple ? 0 : 20));
				} else {
					try {
						iframe.css('width', $(frmWnd.document).width() + 10);
					} catch (e) {}
				}
				if (!isNaN(cfgHeight) && !cfg.autoSize) {
					iframe.css('height', cfgHeight - (this.simple ? 0 : (headerHeight + 30)));
				} else {
					try {
						iframe.css('height', $(frmWnd.document).height() + 10);
					} catch (e) {}
				}
			},
			countSize: function() {
				var _this = this,
					cfg = this.cfg;
				if (this.iframe) {
					this.countFrmSize();
				}
				if (this.simple) return;
				var headerHeight = cfg.noheader ? 0 : _this.header.outerHeight();
				var itemWidth = _this.item.outerWidth(),
					itemHeight = _this.item.outerHeight();
				var minWidth = cfg.minWidth,
					minHeight = cfg.minHeight;
				var width = itemWidth;
				var height = _this.body.outerHeight() + headerHeight;
				var wndIW = _this.el.innerWidth(),
					wndW = _this.el.outerWidth();
				var wndIH = _this.el.innerHeight(),
					wndH = _this.el.outerHeight();
				if (this.cfg.autoSize) {
					width = width + wndW - wndIW, height = 'auto';
				} else {
					width = this.cfg.width || cfg.wndStyle.width || 'auto',
						height = this.cfg.height || cfg.wndStyle.height || 'auto'
				}
				if (width < minWidth) width = minWidth;
				if (height < minHeight) height = minHeight;
				var bodyHeight = height;
				if (height != 'auto') {
					bodyHeight = height - 20;
					if (this.header) bodyHeight -= this.header.outerHeight();

				}
				this.body.css({
					width: width,
					height: bodyHeight,
					'overflow-y': 'auto',
					'overflow-x': 'auto'
				});
				this.el.css({
					width: width,
					height: height
				});
				if (this.header) {
					this.header.css('width', width);
				}
			},
			countPosition: function() {
				var cfg = this.cfg;
				var x = cfg.left || cfg.wndStyle.left,
					y = cfg.top || cfg.wndStyle.top;
				if ($.isNumeric(x)) {
					this.moveTo({
						x: x
					});
				} else {
					this.centerX();
				}
				if ($.isNumeric(y)) {
					this.moveTo({
						y: y
					});
				} else {
					this.centerY();
				}
			},
			doLayout: function() {
				var cfg = this.cfg;
				this.countSize();
				this.countPosition();
				if (this.over) {
					this.over.css({
						width: $(document).width(),
						height: $(document).height()
					});
				}
			}
		});


		Y.Window.manager = $.extend(new Array(), {
			getTop: function() {
				var topOne = {
					index: 0,
					wnd: null,
					zIndex: 0
				};
				for (var i = 0; i < this.length; i++) {
					var zIndex = this.getZIndex(this[i]);
					if (zIndex > topOne.zIndex) {
						topOne.zIndex = zIndex;
						topOne.wnd = this[i];
					}
				}
				return topOne.wnd;
			},
			getHigher: function(wnd) {
				if (!wnd) return [];
				var _this = this;
				var zIndex = this.getZIndex(wnd);
				var higherWnds = [];
				$.each(this, function(i, item) {
					var aindex = _this.getZIndex(item);
					if (aindex > zIndex) {
						higherWnds.push(item);
					}
				})
				return higherWnds;
			},
			getZIndex: function(wnd) {
				return parseInt(wnd.wnd.css('zIndex'));;
			},
			remove: function(wnd) {
				if (!wnd || !wnd.manager) return;
				var index = $.inArray(wnd, this);
				if (index == -1) return false;
				this.splice(index, 1);
				wnd.manager = null;
			},
			add: function(wnd) {
				if (!wnd) return;
				if ($.inArray(wnd, this) != -1) return;
				var topone = this.getTop();
				var newIndex;
				if (!topone) {
					newIndex = 5000;
				} else {
					newIndex = this.getZIndex(topone) + 2;
				}
				wnd.wnd.css('z-index', newIndex);
				if (wnd.over) {
					wnd.over.css('z-index', newIndex - 1);
				}
				this.push(wnd);
			},
			toFront: function(wnd) {
				if (!wnd) return;
				var _this = this;
				var topone = this.getTop();
				var topIndex = this.getZIndex(topone);
				var higherWnds = this.getHigher(wnd);
				$.each(higherWnds, function(i, item) {
					var newIndex = _this.getZIndex(item) - 2;
					item.wnd.css('z-index', newIndex);
					if (item.over) item.over.css('z-index', newIndex - 1);
				});
				wnd.wnd.css('zIndex', topIndex);
			}
		});


		Y.Window.defaults = {
			title: '',
			content: '',
			dragAble: true,
			closeAble: true,
			modal: true,
			contentClone: false,
			removeLocation: true,
			contentRecovery: true,
			minWidth: 100,
			minHeight: 100,
			wndCss: 'wnd-wnd',
			bodyCss: 'wnd-body',
			headerCss: 'wnd-header',
			closeBtnCss: 'wnd-close',
			autoSize: true,
			wndStyle: {
				'z-index': 5000,
				position: 'absolute'
			},
			el: 'div',
			headerStyle: {},
			bodyStyle: {},
			overStyle: {
				opacity: 0.5,
				background: '#666666'
			},
			autoShow: true
		}

	});

})($);