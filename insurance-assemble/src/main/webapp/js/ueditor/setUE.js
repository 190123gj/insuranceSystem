/*
 * 创建一个 UE
 * 
 */

$_GLOBAL = typeof $_GLOBAL == 'object' ? $_GLOBAL : {};

$_GLOBAL.setUE = function ($div) {

    var $div = $div || $('body')

    try {

        $div.find('.fnMakeUE').each(function (index, el) {
            var $this = $(this);
            var maxL = $this.attr('maxlength');
            maxL = isNaN(maxL + 1) ? 500000 : maxL;
            $this.addClass('fn-input-hidden').attr('maxlength', maxL);

            $this.before('<script id="' + this.name + '" type="text/plain">' + $this.val() + '</script>');
            //仅有wordCount，则同时开启wordCountMsg/wordOverFlowMsg
            //wordCount为true时，wordCountMsg/wordOverFlowMsg为true或未定义则默认开启
            var isWordCount = !!$this.attr('wordCount') ? $this.attr('wordCount') : false;
            var wordCountMsg = isWordCount && (!!$this.attr('wordCountMsg') || typeof ($this.attr('wordCountMsg')) == undefined) ? '' : '当前已输入 {#count} 个字符，您还可以输入{#leave} 个字符';
            var wordOverFlowMsg = wordOverFlowMsg && (!!$this.attr('wordOverFlowMsg') || typeof ($this.attr('wordOverFlowMsg')) == undefined) ? '' : '<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>';
            var _ue = UE.getEditor(this.name, {
                maximumWords: maxL,
                elementPathEnabled: false,
                autoFloatEnabled: false, // 工具栏自动跟随
                wordCount: isWordCount,
                wordCountMsg: wordCountMsg, //当前已输入 {#count} 个字符，您还可以输入{#leave} 个字符
                wordOverFlowMsg: wordOverFlowMsg //<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>
            });

            _ue.ready(function () {
                _ue.setHeight(300);
            });

            _ue.addListener('contentChange', function () {
                $this.val(_ue.getContent());
                try {
                    $this.valid();
                } catch (err) {}
            });

        });

    } catch (err) {}


}

setTimeout(function () {

    $_GLOBAL.setUE()

}, 50)