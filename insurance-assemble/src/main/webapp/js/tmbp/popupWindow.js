define(function(require, exports, module) {

	//配置
	// fundDitch = new popupWindow({

	//        YwindowObj: {
	//            content: 'newPopupScript', //弹窗对象，支持拼接dom、template、template.compile
	//            closeEle: '.close', //find关闭弹窗对象
	//            dragEle: '.newPopup dl dt' //find拖拽对象,
	//			  parentBox:'parentBox'//弹窗位置
	//        },

	//        ajaxObj: {
	//            url: '/projectMg/investigation/findFInvestigationFinancialReviewData.htm',
	//            type: 'post',
	//            dataType: 'json',
	//            data: {
	//                id: 46677
	//            }
	//        },

	//        pageObj: { //翻页
	//            clickObj: '.pageBox a', //find翻页对象
	//            attrObj: 'page', //翻页对象获取值得属性
	//            jsonName: 'pagesss' ,//请求翻页页数的dataName
	//            callback :function($wnd){//点击回调
	//                console.log($wnd)
	//            }
	//        },

	//        formAll: { //搜索
	//            submitBtn: '#PopupSubmit', //find搜索按钮
	//            formObj: '#PopupFrom', //find from
	//            callback :function($wnd){//点击回调
	//                console.log($wnd)
	//            }
	//        },

	//        navObj: { //选卡
	//            clickObj: '.name li', //find 选卡对象
	//            attrObj: 'code', //选卡对象获取值得属性
	//            jsonName: 'navCode', //请求选卡code的dataName
	//            activeObj: 'active', //find 选中样式
	//            defaultObj: '555554',//默认选中项index、attrObj val
	//            callback :function($wnd){//点击回调
	//                console.log($wnd)
	//            }
	//        },

	//        callback: function($Y) {//初始化回调

	//            $Y.wnd.on('click', 'a.choose', function(event) {

	//                // var _this = $(this),
	//                //     _parents = _this.parents('.tableAll'),
	//                //     _name = _parents.find('.name').text(),
	//                //     _marketPrice = _parents.find('.marketPrice').text();

	//                var _this = $(this),
	//                    _index = _this.index(),
	//                    _parents = _this.parents('.tableAll'),
	//                    //_name = _parents.find('.name li').eq(_index).text(),
	//                    _marketPrice = _parents.find('.marketPrice li').eq(_index).text();

	//                $this.siblings('[name="capitalChannelName"]').val(_marketPrice);

	//                $Y.close();

	//            });

	//        },

	//        console: false //打印返回数据

	//    });

	// //js引擎
	var template = require('arttemplate');
	//弹窗
	require('Y-window');
	//测试数据
	var res = {
		success: true,
		data: {
			pageSize: 6,
			totalCount: 50,
			pageCount: 5,
			pageIndex: 3,
			list: [{
				"id": "1",
				"name": "测试数据啦1~~~",
				"marketPrice": "6666661",
				"price": "555551"
			}, {
				"id": "1",
				"name": "测试数据啦2~~~",
				"marketPrice": "6666662",
				"price": "555552"
			}, {
				"id": "1",
				"name": "测试数据啦3~~~",
				"marketPrice": "6666663",
				"price": "555553"
			}, {
				"id": "1",
				"name": "测试数据啦4~~~",
				"marketPrice": "6666664",
				"price": "555554"
			}, {
				"id": "1",
				"name": "测试数据啦5~~~",
				"marketPrice": "6666656",
				"price": "555555"
			}]
		}
	};

	function popupWindow(obj) {

		this.YwindowObj = obj.YwindowObj;
		// this.ajaxObj = {
		// 	url: obj.ajaxObj.url||'',
		// 	data: obj.ajaxObj.data || '',
		// 	type: obj.ajaxObj.type || 'post',
		// 	dataType: obj.ajaxObj.dataType || 'json'
		// };
		this.ajaxObj = obj.ajaxObj || ''
			// this.pageObj = {
			// 	clickObj: obj.pageObj.clickObj || '',
			// 	attrObj: obj.pageObj.attrObj || '',
			// 	jsonName: obj.pageObj.jsonName || '',
			// 	callback: obj.pageObj.callback || ''
			// };
		this.pageObj = obj.pageObj || '';
		this.formAll = obj.formAll || '';
		this.navObj = obj.navObj || '';
		// this.formAll = {
		// 	submitBtn: obj.formAll.submitBtn || '',
		// 	formObj: obj.formAll.formObj || '',
		// 	callback: obj.formAll.callback || ''
		// };
		// this.navObj = {
		// 	clickObj: obj.navObj.clickObj || '',
		// 	attrObj: obj.navObj.attrObj || '',
		// 	jsonName: obj.navObj.jsonName || '',
		// 	activeObj: obj.navObj.activeObj || '',
		// 	defaultObj: obj.navObj.defaultObj || '',
		// 	callback: obj.navObj.callback || ''
		// };
		this.console = obj.console || false;
		this.recordInput = {};
		this.recordNavCode = {};
		this.callback = obj.callback;
		this.init(obj);

	}
	popupWindow.prototype = {

		init: function(obj) {

			if (obj.YwindowObj.content) this.boxObj = this.YwindowFun(obj);
			if (obj.pageObj && obj.ajaxObj.url) this.pageFun(this.boxObj.wnd, this.YwindowObj.content, obj);
			if (obj.formAll && obj.ajaxObj.url) this.formFun(this.boxObj.wnd, this.YwindowObj.content, obj);
			if (obj.navObj && obj.ajaxObj.url) this.navFun(this.boxObj.wnd, this.YwindowObj.content, obj);
			if (obj.callback) this.callback(this.boxObj);

		},

		ajaxFun: function(_data) {

			var _post,
				_sel = this;

			$.ajax({
				url: this.ajaxObj.url,
				type: this.ajaxObj.type,
				dataType: this.ajaxObj.dataType,
				async: false,
				data: _data || this.ajaxObj.data,
				success: function(res) {

					_post = res;
					if (_sel.console) console.log(res);

				},
				error: function(x) {

					if (_sel.console) console.log('return error~~~~~on on on success')

				}
			})

			return _post;

		},

		YwindowFun: function(obj) {

			var basic = {
					modal: true,
					key: 'modalwnd',
					simple: true,
					closeEle: this.YwindowObj.closeEle,
					dragEle: this.YwindowObj.dragEle
				},
				parentBox = this.YwindowObj.parentBox || 'body',
				YwindowConfig;

			YwindowConfig = $.extend(true, basic, {

				content: '<div>' + this.contentTypeFun() + '</div>'

			});

			$(parentBox).Y('Window', YwindowConfig);

			if (YwindowConfig.key) {

				if (basic.closeEle) {

					Y.getCmp(YwindowConfig.key).wnd.on('click', basic.closeEle, function() {

						Y.getCmp(YwindowConfig.key).close();

					});

				}

				return Y.getCmp(YwindowConfig.key);

			}

		},

		pageFun: function(wndBox, YwindowObjContent, obj) {

			var _requestCommon = this.requestCommon,
				_sel = this;

			wndBox.on('click', this.pageObj.clickObj, function() {

				var $this = $(this),
					_page = $this.attr(obj.pageObj.attrObj),
					_ajaxObj = {};

				_ajaxObj[_sel.pageObj.jsonName] = _page;

				_requestCommon.call(_sel, wndBox, YwindowObjContent, obj, _ajaxObj, _sel.pageObj.callback);

			});

		},

		formFun: function(wndBox, YwindowObjContent, obj) {

			var _requestCommon = this.requestCommon,
				_sel = this;

			wndBox.on('click', this.formAll.submitBtn, function(event) {

				$(_sel.formAll.formObj).submit(function(event) {

					if (document.all) event.returnValue = false;
					else event.preventDefault();

				});

				var _ajaxObj = {};

				if (_sel.pageObj.jsonName) _ajaxObj[_sel.pageObj.jsonName] = 1;

				wndBox.find(_sel.formAll.formObj).find('input[type!="submit"][type!="button"],select,textarea').each(function(index, el) {

					var _this = $(el),
						_name = _this.attr('name'),
						_val = _this.val();

					_sel.recordInput[_name] = _val;

				});

				_requestCommon.call(_sel, wndBox, YwindowObjContent, obj, _ajaxObj, _sel.formAll.callback, {
					recordInput: _sel.recordInput
				});

			})

		},

		navFun: function(wndBox, YwindowObjContent, obj) {

			var _sel = this,
				_requestCommon = this.requestCommon;

			wndBox.on('click', this.navObj.clickObj, function() {

				var $this = $(this),
					_navAttr = $this.attr(_sel.navObj.attrObj),
					_ajaxObj = {};

				if (_sel.pageObj.jsonName) _ajaxObj[_sel.pageObj.jsonName] = 1;

				_sel.recordNavCode[_sel.navObj.jsonName] = _navAttr;

				_requestCommon.call(_sel, wndBox, YwindowObjContent, obj, _ajaxObj, _sel.navObj.callback, {
					recordNavCode: _sel.recordNavCode
				});

			});

			(typeof this.navObj.defaultObj == 'number') ? wndBox.find(this.navObj.clickObj).eq(this.navObj.defaultObj).addClass(this.navObj.activeObj): wndBox.find(this.navObj.clickObj + '[' + this.navObj.attrObj + '="' + this.navObj.defaultObj + '"]').addClass(this.navObj.activeObj);

		},

		requestCommon: function(wndBox, YwindowObjContent, obj, _ajaxObj, callback, record) {

			var _data = $.extend(true, obj.ajaxObj.data, _ajaxObj, record && record.recordInput ? record.recordInput : {}, record && record.recordNavCode ? record.recordNavCode : {});

			if (obj.ajaxObj.url) {

				//var _post = res; //console.log(_ajaxFun.call(_sel,_data));

				wndBox.html(this.contentTypeFun(_data));

			}

			if (this.formAll) {

				for (i in this.recordInput) {

					$('[name=' + i + ']').val(this.recordInput[i]);

				}

			}

			if (this.navObj.clickObj && this.navObj.attrObj && this.navObj.jsonName && this.navObj.activeObj) {

				wndBox.find(this.navObj.clickObj + '[' + this.navObj.attrObj + '="' + this.recordNavCode[this.navObj.jsonName] + '"]').addClass(this.navObj.activeObj);

			}

			if (callback) callback(this.boxObj);

		},

		contentTypeFun: function(_data) {

			var domJudge = /^\</.test(this.YwindowObj.content),
				selectorJudge = /^\#|^\./.test(this.YwindowObj.content),
				compileJudge = /\{\{/.test(this.YwindowObj.content);

			if (this.ajaxObj.url) this.post = this.ajaxFun(_data); //this.ajaxFun();

			if (domJudge && !compileJudge) {

				contentType = this.YwindowObj.content;

			} else if (selectorJudge) {

				contentType = $(this.YwindowObj.content).html();

			} else if (compileJudge) {

				contentType = template.compile(this.YwindowObj.content)(this.post);

			} else {

				if (this.post) {

					contentType = template(this.YwindowObj.content, this.post);

				} else {

					contentType = $('#' + this.YwindowObj.content).html();

				}

			}

			return contentType;

		}

		// pageClickFun: function(wndBox,YwindowObjContent,obj){

		// 	var $this = $(this),
		// 		_page = $this.attr(obj.pageObj.attrObj);

		// 	if(obj.ajaxObj.url) var _post = res; //console.log(this.ajaxFun());

		// 	wndBox.html(template(YwindowObjContent,_post));

		// }

	}

	module.exports = popupWindow
})