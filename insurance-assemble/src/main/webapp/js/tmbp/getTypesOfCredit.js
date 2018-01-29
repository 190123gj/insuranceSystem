define(function(require, exports, module) {
	var util = require('util');

	//获取授信种类，返回弹窗
	function getTypesOfCredit(queryData) {
		// btn callback nameID codeID beforeQuery chooseAll
		this.queryData = queryData;
		this.chooseAll = false;
	}

	getTypesOfCredit.prototype = {
		constructor: getTypesOfCredit,
		init: function(obj) {
			var self = this;

			for (var k in obj) {
				self[k] = obj[k];
			}

			self.$btn = $body.find(self.btn);

			self.$box = $(boxHtml);

			self.$box.on('click', '.fnCanChoose', function() {
				var _this = $(this);
				self.$box.addClass('fn-hide');
				if (self.diychoose) {
					self.diychoose(self,_this)
				} else {
					$body.find(self.name).val(_this.find('.name').text()).trigger('change');
					$body.find(self.code).val(_this.attr('businessTypeCode')).trigger('change');
				}
			}).on('click', '.close', function() {
				self.$box.addClass('fn-hide');
			});

			self.$btn.on('click', function() {
				var _this = $(this);
				if (_this.hasClass('ing')) {
					return;
				}
				_this.addClass('ing');
				if (self.beforeQuery && !self.beforeQuery()) {
					self.$btn.removeClass('ing');
					return;
				}
				self.query();
			});
			$body.append(self.$box);

			return self;
		},
		query: function() {
			var self = this;
			//查询
			util.getMultilevel({
				url: '/baseDataLoad/busiType.json',
				data: self.queryData,
				sublist: 'children',
				insert: function(html) {
					self.$box.find('.credit-types').html(html);
				},
				liTpl: function(obj, i, subHtml, arr) {
					var _html = '',
						_codeIndex = obj.code.length,
						_isAlone = ((arr[i - 1] && arr[i - 1].children) || (arr[i + 1] && arr[i + 1].children)) ? false : true;
					if (obj.children) {
						//标题
						_html = '<li><p class="' + ((_codeIndex == 1) ? 'h-title ' : '') + 'fn-fwb' + (self.chooseAll ? ' fnCanChoose' : '') + '" businessTypeCode="' + obj.code + '" customerType="' + obj.customerType + '"><span class="fn-ml10 name">' + obj.name + '</span>' + '</p>' + subHtml + '</li>';
					} else {
						//选项
						_html = '<li class="' + (_isAlone ? 'fn-left' : '') + ' fn-w148 sub-li' + ((obj.setupFormCode || self.chooseAll) ? ' fnCanChoose' : '') + ' fnCanChoose" businessTypeCode="' + obj.code + '" customerType="' + obj.customerType + '"><span class="fn-ml10 name">' + obj.name + '</span>' + '</li>';
					}
					return _html;
				},
				ulTpl: function(str, i) {
					return '<ul class="fn-clear fn-pl20">' + str + '</ul>';
				},
				callback: function() {
					self.$box.removeClass('fn-hide');
					self.$btn.removeClass('ing');
					if (self.callback) {
						self.callback(self);
					}
				}
			});
		},
		setQueryData: function(obj) {
			this.queryData = $.extend({}, this.queryData, obj);
			return this;
		}
	}

	var $body = $('body');

	var boxHtml = [
		'<div class="m-modal-box fn-hide">',
		'    <div class="m-modal-overlay"></div>',
		'    <div class="m-modal m-modal-default">',
		'        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title">业务品种</span></div>',
		// '        <div class="apply-org-hd">',
		// '            <span class="fn-usn fn-csp fn-right close">&times;</span>',
		// '            请选择类型',
		// '        </div>',
		'        <div class="m-modal-body"><div class="m-modal-body-box"><div class="m-modal-body-inner credit-types"></div></div></div>',
		// '        <div class="apply-org-body credit-types"></div>',
		'    </div>',
		'</div>'
	].join('');


	module.exports = getTypesOfCredit;

});