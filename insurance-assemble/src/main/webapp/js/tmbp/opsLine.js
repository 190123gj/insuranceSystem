define(function(require, exports, module) {

	//------表格、内容 增加一行、删除一行
	//HTML demo
	//<div class="fn-mt20">
	// 	<p><a class="ui-btn fnAddLine" tplID="t-test" cttID="test" href="javascript:void(0);">添加</a></p>
	// </div>
	// <table class="fn-mt20">
	// 	<tbody id="test">

	// 	</tbody>
	// </table>

	// <script type="text/html" id="t-test">
	// 	<tr class="fnNewLine">
	// 		<td>fsdfsd</td>
	// 		<td>fsd</td>
	// 		<td><a class="ui-btn fnDelLine" parentsClass="fnNewLine" href="javascript:void(0);">删</a></td>
	// 	</tr>
	// </script>
	//---增加
	$('body').on('click', '.fnAddLine', function() {
		var _this = $(this),
			_tplID = _this.attr('tplID'),
			_cttID = _this.attr('cttID');
		$('#' + _cttID).append($('#' + _tplID).html());
	});
	//---删除
	$('body').on('click', '.fnDelLine', function() {
		var _this = $(this);
		_this.parents('.' + _this.attr('parentsClass')).remove();
	});
});