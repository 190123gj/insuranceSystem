define(function(require, exports, module) {

	module.exports = function() {
		// 富文本框 ？？ start
		// UE 的js必须在页面引入 ？？
		// try {

		// 	$('.fnMakeUE').each(function(index, el) {

		// 		var $this = $(this).addClass('fn-input-hidden');

		// 		$this.before('<script id="' + this.name + '" type="text/plain">' + $this.val() + '</script>');

		// 		var _ue = UE.getEditor(this.name, {
		// 			maximumWords: $this.attr('maxlength') || 1000,
		// 			elementPathEnabled: false,
		// 			wordCountMsg: '', //当前已输入 {#count} 个字符，您还可以输入{#leave} 个字符
		// 			wordOverFlowMsg: '' //<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>
		// 		});

		// 		_ue.ready(function() {
		// 			_ue.setHeight(300);
		// 		});

		// 		_ue.addListener('contentChange', function() {
		// 			$this.val(_ue.getContent());
		// 			try {
		// 				$this.valid();
		// 			} catch (err) {}
		// 		});

		// 	});

		// } catch (err) {}
		// 富文本框 ？？ end
	}

});