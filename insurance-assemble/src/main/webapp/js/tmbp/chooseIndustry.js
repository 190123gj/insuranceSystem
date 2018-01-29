define(function(require, exports, module) {
	// html 
	// <div class="m-item">
	//     <label class="m-label"><span class="m-required">*</span>所属行业：</label>
	//     &nbsp;
	//     <span id="industryBox"></span>
	//     <input type="hidden" id="industryName"> ## name
	//     <input type="hidden" id="industryCode"> ## code
	// </div>
	//选择所属行业
	//模板引擎
	var template = require('arttemplate');
	var $industryBox = $('#industryBox'),
		$industryName = $('#industryName'),
		$industryCode = $('#busiScope'),
		industryBoxTpl = ['<select class="ui-select">',
			'    <option value="">请选择</option>',
			'    {{each list as item i}}',
			'    <option value="{{item.code}}">{{item.name}}</option>',
			'    {{/each}}',
			'</select>'
		].join(''),
		industrySelTpl = ['<select class="ui-select">',
			'    <option value="">请选择</option>',
			'    {{each list as item i}}',
			'    <option value="{{item.code}}"{{(item.code==code)?\' selected="selected"\':\'\'}}>{{item.name}}</option>',
			'    {{/each}}',
			'</select>'
		].join(''),
		maxitem = +$industryBox.attr('maxitem');
	$industryBox.on('change', 'select', function() {
		var self = this;
		//当有做出选择的时候就清空数据
		$industryName.val('').trigger('blur');
		$industryCode.val('').trigger('blur');
		//每次选择，都去加载下一级数据
		//如果没有值，退出
		if (!!!self.value) {
			return;
		}
		var _thisSelectArr = [];
		$(self).find('option').each(function(index, el) {
			_thisSelectArr.push({
				code: el.value,
				name: el.text
			});
		});
		//是否超过限制
		setTimeout(function() {
			if ($('#industryBox select').length >= maxitem) {
				var _name;
				$(self).find('option').each(function(i, e) {
					if (e.value == self.value) {
						_name = e.innerHTML;
					}
				});
				$industryName.val(_name).trigger('blur');
				$industryCode.val(self.value).trigger('blur');
				return;
			}
			industryToSelect(self.value, _thisSelectArr);
		}, 0);
	}).on('change', 'select:not(:last)', function() {
		//当不是最后一个的时候，把当前后面的下级删了
		$(this).nextAll('select').remove();
	});

	if ($industryCode.val()) {
		//已有数据，还原
		industryRestore($industryCode.val());
	} else {
		//没有数据，加载第一条
		industryToSelect();
	}

	/**
	 * [industryToSelect description]
	 * @param  {[string]} key [select选中的value值]
	 * @param  {[array]}  industryLastList [当前select的option数组，code-name]
	 * @return {[type]}   [当有数据的时候，向容器加载html，当没有数据的时候，设置value]
	 */
	function industryToSelect(key, industryLastList) {
		$.getJSON('/baseDataLoad/industry.json', {
			parentCode: key
		}, function(res, textStatus) {
			if (res.success) {
				if (!!res.data.length) {
					$industryBox.append(template.compile(industryBoxTpl)({
						list: res.data
					}));
				} else {
					for (var i = industryLastList.length - 1; i >= 0; i--) {
						if (industryLastList[i].code == key) {
							$industryName.val(industryLastList[i].name).trigger('blur');
							$industryCode.val(key).trigger('blur');
							return;
						}
					}
				}
			} else {
				Y.alert('提示', res.message);
			}
		});
	}
	/**
	 * [industryRestore 还原所属行业]
	 * @param  {[type]} code [当前选中的code值]
	 * @return {[type]}      [description]
	 */
	function industryRestore(code) {
		var _codeArr = code.split('-'), //分割最终的code
			_index = 0, //索引
			_codeCasual = [], //临时当前节点
			_parentCode = [], //父级code数组
			_ajaxArr = []; //最终操作数组
		//组合父节点
		for (var i = 0; i < _codeArr.length; i++) {
			_codeCasual.push(_codeArr[i]);
			_parentCode.push(_codeCasual.join('-'));
		}
		//组合当前cod、父code
		for (var i = 0; i < _parentCode.length; i++) {
			_ajaxArr.push({
				code: _parentCode[i],
				parentCode: _parentCode[i - 1]
			});
		}

		getSelect(_ajaxArr[_index]);

		function getSelect(obj) {
			if (_index > _ajaxArr.length - 1) {
				showFullName();
				return;
			}
			$.getJSON('/baseDataLoad/industry.json', {
				parentCode: obj.parentCode
			}, function(res, textStatus) {
				if (res.success) {
					$industryBox.append(template.compile(industrySelTpl)({
						list: res.data,
						code: obj.code
					}));
					getSelect(_ajaxArr[++_index]);
				} else {
					Y.alert('提示', res.message);
				}
			});
		}
	}
	//---所属行业 end

	function showFullName() {
		if ($(".industryFullName").size() > 0) {
			var industry = '';
			$industryBox.find("select").each(function(i) {
				var _this = $(this),
					_sel = _this.find("option:selected");
				if (i > 0 && !!_sel.val()) {
					industry += ' > ' + _sel.text();
				} else if (!!_sel.val()) {
					industry += _sel.text();
				}
			});
			$(".industryFullName").html(industry);
		}
	}
});