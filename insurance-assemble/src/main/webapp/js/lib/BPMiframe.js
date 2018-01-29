define(function(require, exports, module) {
	/**
	 * BPM弹出层
	 *
	 * 使用方法
	 *
	 * var BPMiframe = require('BPMiframe');
	 *
	 * var BPMiframeName = BPMiframe(900,500);
	 *
	 * 需要显示弹出层
	 *
	 * $body.on('click','#xxx', function(){
	 * 		BPMiframeName.init(function(){
	 * 		    console.log('回调要做的事');
	 * 		});
	 * });
	 * 
	 */

	/**
	 * 测试bpm弹出
	 */
	require('Y-msg');
	var util = require('util');
	var $body = $('body');

	//BPMiframe
	function BPMiframe(url, obj) {
		var self = this;
		self.width = parseInt((obj.width || 700), 10);
		self.height = parseInt((obj.height || 460), 10);
		self.title = obj.title || '请选择';
		self.obj = obj;
		self.url = url;

		//创建一个jQ弹出层对象
		self.$popup = creatJQHtml(self.title, url); //弹出层JQ对象
		//初始化弹出层
		//初始化可视化modal大小、位置
		self.$popup.find('.m-modal').width(self.width).height(self.height).css({
			'margin-left': -(self.width / 2 + 1),
			'margin-top': -(self.height / 2 + 1)
		});
	}

	BPMiframe.prototype = {
		constructor: BPMiframe,
		init: function(callback) {
			//需要弹出的时候才初始化
			var self = this;

			function getAjaxDate(url) {

				return $.ajax({
					url: url,
					type: 'POST',
					data: {
						_t: (new Date()).getTime()
					},
					dataType: 'text'
				});

			}

			getAjaxDate(self.obj.bpmIsloginUrl).then(function(res) {

				if (res.indexOf('{code:0}') >= 0) {

					// 未登录，需要登录
					return getAjaxDate(self.obj.makeLoginUrl);

				} else {
					// 登录状态不需要再次登录
					return false;

				}

			}).then(function(res) {

				// 登录状态不需要登录
				if (!res) {
					return false;
				}

				var res = $.parseJSON(res);

				if (res.code == 1) {
					// 获取到登录地址，去登录
					return getAjaxDate(res.loginUrl);

				} else {
					// 登录地址获取失败
					return '{code:0}';

				}


			}).then(function(res) {

				// 登录状态直接初始化
				if (!res) {
					return true;
				}

				// 根据登录反馈信息，是否登录
				if (res.indexOf('{code:1}') < 0) {

					return false;

				} else {

					return true;

				}

			}).then(function(boole) {

				if (!boole) {
					return;
				}

				//初始化iframe的url 
				//每次打开页面你加一个时间戳
				self.setSrc();

				//初始化callback
				self.diycallback = callback;
				//初始化回调后的执行对象
				//window.doBPMiframe = self;
				//添加到body
				$body.append(self.$popup);

				self.$iframe = $body.find('#fnPopupIframeIframe');
				self.$iframe[0].dialog = {
					get: function(str) {
						return this.obj[str];
					},
					close: function() {
						self.cancel();
					},
					obj: self.obj
				}
				self.obj.sucCall = callback;
				//初始化iframeBox大小
				//如果不把dom添加到body，获取不到domd的高度
				self.$popup.find('.iframe-box').height(self.height - self.$popup.find('.iframe-hd').outerHeight());

			});

			//是否登录状体

			// $.ajax({
			// 	url: self.obj.bpmIsloginUrl,
			// 	data: {
			// 		_time: (new Date()).getTime()
			// 	},
			// 	async: false,
			// 	dataType: 'text',
			// 	type: 'POST',
			// 	success: function(res) {
			// 		//非登录状体，获取登录地址

			// 		if (res.indexOf('{code:0}') >= 0) {

			// 			$.ajax({
			// 				url: self.obj.makeLoginUrl,
			// 				data: {
			// 					_time: (new Date()).getTime()
			// 				},
			// 				async: false,
			// 				dataType: 'text',
			// 				type: 'GET',
			// 				error: function() {
			// 					return;
			// 				},
			// 				success: function(res1) {
			// 					var res1 = $.parseJSON(res1);
			// 					//获取到登录地址，登录
			// 					if (res1.code == 1) {
			// 						$.ajax({
			// 							url: res1.loginUrl,
			// 							data: {
			// 								_time: (new Date()).getTime()
			// 							},
			// 							async: false,
			// 							dataType: 'text',
			// 							type: 'GET',
			// 							error: function() {
			// 								return;
			// 							},
			// 							success: function(res2) {
			// 								//假若不成功，退出初始化，并弹出错误信息
			// 								if (res2.indexOf('{code:1}') < 0) {
			// 									// Y.alert('提示', res2.message);
			// 									return;

			// 								}
			// 							}
			// 						});

			// 					} else {
			// 						// Y.alert('提示', res1.message);
			// 						return;
			// 					}
			// 				}
			// 			});
			// 		}
			// 	}
			// });
			// //初始化iframe的url 
			// //每次打开页面你加一个时间戳
			// self.setSrc();

			// //初始化callback
			// self.diycallback = callback;
			// //初始化回调后的执行对象
			// //window.doBPMiframe = self;
			// //添加到body
			// $body.append(self.$popup);

			// self.$iframe = $body.find('#fnPopupIframeIframe');
			// self.$iframe[0].dialog = {
			// 	get: function(str) {
			// 		return this.obj[str];
			// 	},
			// 	close: function() {
			// 		self.cancel();
			// 	},
			// 	obj: self.obj
			// }
			// self.obj.sucCall = callback;
			// //初始化iframeBox大小
			// //如果不把dom添加到body，获取不到domd的高度
			// self.$popup.find('.iframe-box').height(self.height - self.$popup.find('.iframe-hd').outerHeight());
		},
		resetSrc: function(src) {
			var self = this;
			self.url = src;
		},
		setSrc: function() {
			var self = this;
			self.$popup.find('#fnPopupIframeIframe').attr('src', self.url + ((self.url.indexOf('?') > -1) ? '&' : '?') + '_time=' + (new Date()).getTime());
		},
		callback: function() {
			var self = this;
			//每次删选结束，就执行这个callback

			self.diycallback.apply(self.diycallback, arguments);
			self.cancel();
		},
		cancel: function() {
			var self = this;
			self.$popup.find('#fnPopupIframeX').trigger('click');
		},
		get: function(str) {
			return this.obj[str];
		},
		close: function() {
			var self = this;
			self.$popup.find('#fnPopupIframeX').trigger('click');
		}
	}

	function creatJQHtml(title, url) {
		var _$html = $('<div class="m-modal-box" id="fnPopupIframe"></div>'),
			_html = ['<div class="m-modal-overlay"></div>',
				'<div class="m-modal m-popup-iframe">',
				'    <div class="iframe-hd"><span class="fn-right fn-usn fn-csp fn-mr10" id="fnPopupIframeX" href="javascript:void(0);">&times;</span><span class="fn-ml10">' + title + '</span></div>',
				'    <div class="iframe-box">',
				'        <iframe id="fnPopupIframeIframe" noresize="noresize" frameborder="0" allowtransparency="yes" scrolling="yes" width="100%" height="100%" src="' + url + '"></iframe>',
				'    </div>',
				'</div>'
			].join('');
		_$html.html(_html);
		return _$html;
	}

	//统一清楚弹出层
	$body.on('click', '#fnPopupIframeX', function() {
		$(this).parents('#fnPopupIframe').remove();
	});

	module.exports = BPMiframe;
});