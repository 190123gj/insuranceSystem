define(function(require, exports, module) {

    //输入字数检查
    $('body').on('keyup', 'textarea', function() {
        var _this = $(this),
            _val = _this.val().length,
            _next = _this.nextAll('span.hint'),
            _maxLength = _this.attr('maxlength'),
            _length = _next.length;
        if ($(this).hasClass('text500')) {
            _this.attr('maxlength', '500');
            _val ? (_length ?
                    _next.text('(' + _val + '/500)') :
                    _this.after('<span class="hint" style="position:absolute;bottom:-2px;right:10px">(' + _val + '/500)</span>')) :
                _next.remove('span.hint');
        } else if ($(this).hasClass('text10000')) {
            _this.attr('maxlength', '10000');
            _val ? (_length ?
                    _next.text('(' + _val + '/10000)') :
                    _this.after('<span class="hint fn-ml30">(' + _val + '/10000)</span>')) :
                _next.remove('span.hint');
        } else {

            var _l = _maxLength ? _maxLength : 1000;

            _val ? (_length ?
                    _next.text('(' + _val + '/' + _l + ')') :
                    _this.after('<span class="hint fn-ml30">(' + _val + '/' + _l + ')</span>')) :
                _next.remove('span.hint');
        }
    });
    //提示
    $('.tooltip').hover(function() {

        var $this, text;

        $this = $(this);
        text = $this.attr('hover');
        if ($this.attr('maxwidth')) {

            $this.append('<span class="hoverTooltip" style=" width: ' + $this.attr("maxwidth") + '">' + text + '</span>');

        } else {

            $this.append('<span class="hoverTooltip">' + text + '</span>');

        }


    }, function() {

        var $this, text;

        $this = $(this);

        $this.find('.hoverTooltip').remove();

    });

})