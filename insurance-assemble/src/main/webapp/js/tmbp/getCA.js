define(function(require, exports, module) {
	var template = require('arttemplate');
	//合作机构相关操作
	var _htmlArr = ['<div class="m-modal-box fn-hide">',
		'    <div class="m-modal-overlay"></div>',
		'    <div class="m-modal apply-org">',
		'        <div class="apply-org-hd"><span class="fn-right fn-usn fn-csp apply-org-x" id="applyOrgX" href="javascript:void(0);">&times;</span>合作机构选择</div>',
		'        <div class="apply-org-body">',
		'            <p class="fn-mb10">',
		'                剩余可用授信额度&nbsp&nbsp;&nbsp;',
		'                <select class="ui-select" id="applyOrgSel">',
		'                    <option value="">&nbsp;</option>',
		'                    <option value="1">&ge;</option>',
		'                    <option value="2">&lt;</option>',
		'                </select>&nbsp;&nbsp;',
		'                <input class="ui-text fn-w90 fnMakeMoney" id="applyOrgMoney" type="text">&nbsp;&nbsp;',
		'                <input class="ui-btn ui-btn-fill ui-btn-green" id="applyOrgBtn" type="button" value="筛选">',
		'            </p>',
		'            <table class="m-table" border="1">',
		'                <thead>',
		'                    <tr>',
		'                        <th width="200">合作机构名称</th>',
		'                        <th width="170">机构授信额度（万元）</th>',
		'                        <th width="170">剩余可用额度（万元）</th>',
		'                        <th>操作</th>',
		'                    </tr>',
		'                </thead>',
		'                <tbody class="fn-tac fn-hide" id="applyOrgList"></tbody>',
		'                <tfoot class="fn-hide" id="applyOrgNo">',
		'                    <tr>',
		'                        <td colspan="4" class="fn-tac">没有对应数据..</td>',
		'                    </tr>',
		'                </tfoot>',
		'            </table>',
		'            <div class="m-loading fn-hide" id="applyOrgLoad"><span>加载中..</span></div>',
		'            <div class="m-pages" id="applyOrgPages"></div>',
		'        </div>',
		'    </div>',
		'</div>'
	].join('');
	var _trTpl = ['{{each list as item i}}',
		'    <tr class="fn-tac">',
		'        <td class="item" id="{{item.id}}">{{item.name}}</td>',
		'        <td>{{item.name}}</td>',
		'        <td>{{item.name}}</td>',
		'        <td><a class="choose" href="javascript:void(0);">选择</a></td>',
		'    </tr>',
		'{{/each}}'
	].join('');
	var $applyOrg = $(_htmlArr),
		$body = $('body').append($applyOrg),
		$coInstitutionId = $body.find('#coInstitutionId'),
		$coInstitutionName = $body.find('#coInstitutionName'),
		isLoad = false,
		ops, // 大于小于
		inputMoney, // 金额
		$load = $applyOrg.find('#applyOrgLoad'), //加载中
		$foot = $applyOrg.find('#applyOrgNo'), //没有数据
		$list = $applyOrg.find('#applyOrgList'), //内容区域
		$pages = $applyOrg.find('#applyOrgPages'), //分页
		_indexPage = 1; //当前第几页
	$body.on('click', '#openApplyOrg', function() {
		$applyOrg.removeClass('fn-hide');
		isLoad = false;
	});
	$applyOrg.on('change', '#applyOrgSel', function() {
		ops = this.value;
	}).on('change', '#applyOrgMoney', function() {
		inputMoney = this.value;
	}).on('click', '#applyOrgX', function() {
		$applyOrg.addClass('fn-hide');
	}).on('click', '#applyOrgBtn', function() {
		getDate(1);
	}).on('click', '#applyOrgPages a', function() {
		var _this = $(this);
		if (_this.hasClass('disabled')) {
			return;
		}
		getDate(parseInt(_this.attr('page'), 10));
	}).on('click', '.choose', function() {
		var _thisItem = $(this).parents('tr').find('.item');
		$coInstitutionId.val(_thisItem.attr('id'));
		$coInstitutionName.val(_thisItem.text());
		$applyOrg.addClass('fn-hide');
	});
	//初次获取数据
	getDate(1);
	//测试数据
	var res = {
		success: true,
		data: {
			pageSize: 6,
			totalItem: 50,
			totalPage: 5,
			list: [{
				"id": "1",
				"name": "测试数据啦~~~",
				"marketPrice": "666666",
				"price": "55555"
			}, {
				"id": "1",
				"name": "测试数据啦~~~",
				"marketPrice": "666666",
				"price": "55555"
			}, {
				"id": "1",
				"name": "测试数据啦~~~",
				"marketPrice": "666666",
				"price": "55555"
			}, {
				"id": "1",
				"name": "测试数据啦~~~",
				"marketPrice": "666666",
				"price": "55555"
			}, {
				"id": "1",
				"name": "测试数据啦~~~",
				"marketPrice": "666666",
				"price": "55555"
			}]
		}
	};
	//获取数据
	/**
	 * [getDate 获取并填充数据]
	 * @param  {[number]} index [第几页，必须为数字]
	 * @return {[type]}       [暂无]
	 */
	function getDate(index) {
		if (isLoad) {
			//正在加载退出
			return;
		}
		isLoad = true;
		$load.removeClass('fn-hide');
		$list.addClass('fn-hide');
		setTimeout(function() {
			isLoad = false;
			$load.addClass('fn-hide');
			if (res.success) {
				if (res.data.list.length) {
					$foot.addClass('fn-hide');
					$list.html(template.compile(_trTpl)(res.data)).removeClass('fn-hide');
					//page
					$pages.html(getPage(res.data, index));
				} else {
					$list.addClass('fn-hide');
					$foot.removeClass('fn-hide');
				}
			} else {
				$list.addClass('fn-hide');
				$foot.removeClass('fn-hide');
			}
		}, 1000);
		return;
		$.ajax({
			url: '/xx',
			data: {
				pageSize: 6,
				pageNumber: index,
				ops: ops,
				inputMoney: inputMoney
			},
			dataType: 'json',
			success: function(res) {
				isLoad = false;
				$load.addClass('fn-hide');
				if (res.success) {
					if (res.data.list.length) {
						$foot.addClass('fn-hide');
						$list.html(template.compile(_trTpl)(res.data)).removeClass('fn-hide');
						//page
						$pages.html(getPage(res.data, index));
					} else {
						$list.addClass('fn-hide');
						$foot.removeClass('fn-hide');
					}
				} else {
					$list.addClass('fn-hide');
					$foot.removeClass('fn-hide');
				}
			}
		});
	}
	//拼接page
	/**
	 * [getPage 拼接分页]
	 * @param  {[type]} data  [ajax请求返回值中的data]
	 * @param  {[type]} index [当前第几页]
	 * @return {[type]}       [完整的分页]
	 */
	function getPage(data, index) {
		//通过a标签上的page确定请求第几页数据
		var _txt = '第' + index + '页，共' + data.totalPage + '页，合计' + data.totalItem + '条&nbsp;|&nbsp;',
			_firstBtn, _preBtn, _nextBtn, _lastBtn;
		//第一页、上一页
		if (index == 1) {
			_firstBtn = '<a class="disabled" href="javascript:void(0);">首页</a>';
			_preBtn = '<a class="disabled" href="javascript:void(0);">上一页</a>';
		} else {
			_firstBtn = '<a page="1" href="javascript:void(0);">首页</a>';
			_preBtn = '<a page="' + (index - 1) + '" href="javascript:void(0);">上一页</a>';
		}
		//最后页、下一页
		if (index >= data.totalPage) {
			_nextBtn = '<a class="disabled" href="javascript:void(0);">下一页</a>';
			_lastBtn = '<a class="disabled" href="javascript:void(0);">尾页</a>';
		} else {
			_nextBtn = '<a page="' + (index + 1) + '" href="javascript:void(0);">下一页</a>';
			_lastBtn = '<a page="' + data.totalPage + '" href="javascript:void(0);">尾页</a>';
		}
		return _txt + _firstBtn + _preBtn + _nextBtn + _lastBtn;
	}
});