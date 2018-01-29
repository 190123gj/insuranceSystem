/**
 * 输入限制的例子 class="xxx"
 *
 * 例如 金额 fnMakeMoney (99.00)
 *      数字 fnMakeNumber (333)
 *      电话号码 fnMakeTel (232323-2323232)
 *      小小宇1的n位小数 fnMakeLF fnMakeLV4 (0.1111)
 *      不小于1的n为小数 fnMakeLF fnMakeLV4 fnMakeLFS (2.1111)
 *      小于100的两位小数、百分比数 fnMakePercent100 (66.66)
 *      两位小数：fnFloatNumber
 *      两位小数和整数：fnInitNumber
 *      不能超过某个数值：在input上加上属性maxnumber="80"，表示不能超过80
 *
 *
 * 关于 fnMakeMoney 中，对于IE的bug
 * this.value == '333' 这样操作，似乎对于ie会再次触发blur事件，然后fnMakeMoney就会无限循环，直到溢出
 * 在 maxlength 的时候，针对IE多两位输入
 * 在 fnMakeMoney 的时候，针对IE多两位限制
 *
 */

define(function (require, exports, module) {

    var $body = $('body');

    var util = require('util');

    //输入限制
    function isMoney(e) {
        return 'number' != typeof e && 'string' != typeof e ? !1 : (e = '' + e,
            /^(\d|([1-9]\d+))(\.\d{0,2})?$/.test(e))
    }

    function getCurserIndex(e) {
        e = e[0] ? e[0] : e;
        var t, n;
        if (e.selectionStart || 0 === e.selectionStart)
            t = e.selectionStart,
            n = e.selectionEnd;
        else if (document.selection) {
            var a = document.selection.createRange(),
                r = a.text.length;
            a.moveStart('character', -1 * $(e).val().length),
                n = a.text.length,
                t = n - r
        }
        return {
            start: t,
            end: n
        }
    }

    function makeDB(_thisVal) {

        var _thisValArrs = _thisVal.split('.');

        if (_thisValArrs.length == 1 && _thisValArrs[0]) {
            _thisVal = +_thisValArrs[0] + '.00';
        }

        if (_thisValArrs.length == 2 && !!!_thisValArrs[1].replace(/\s/g, '')) {
            // ie中有个问题
            _thisVal = +_thisValArrs[0] + '.00';
        }

        if (_thisValArrs.length == 2 && _thisValArrs[1].length == 1) {
            _thisVal = _thisValArrs.join('.') + '0';
        }

        return _thisVal;

    }

    $body.on('focus', '.fnMakeMoney', function () {

        // 2016.11.03 某些地方做了自定义的maxlength 为了不影响，所以咯

        if (!!!this.getAttribute('limitinputmoney')) {

            var _old = this.getAttribute('maxlength') || '16'

            // 兼容以前 maxlength="15"
            if (_old === '15') {
                _old = '16'
            }

            this.setAttribute('limitinputmoney', _old)

            _old = +_old
            if (util.isIE()) {
                _old += 2
            }

            this.setAttribute('maxlength', _old)
        }

        // if (util.isIE()) {
        //     this.setAttribute('maxlength', '18')
        // } else {
        //     this.setAttribute('maxlength', '16')
        // }

    }).on('blur', '.fnMakeMoney', function (e) {

        var _thisVal = makeFloat(2, this.value);

        var _max = this.getAttribute('maxlength');
        if (util.isIE()) {
            _max -= 5;
        } else {
            _max -= 3;
        }
        // _max -= 3;
        var _thisValArr = _thisVal.split('.');

        _thisValArr[0] = (_thisValArr[0].length > _max) ? _thisValArr[0].substr(0, _max) : _thisValArr[0];

        _thisVal = _thisValArr.join('.');

        _thisVal = makeDB(_thisVal)

        if (_thisVal == this.value) {
            return;
        }
        this.value = _thisVal;

        $(this).focus();

    }).on('blur', '.fnMakeNumber', function (e) {

        this.value = this.value.replace(/[^\d]/gi, '');

    }).on('blur', '.fnMakeTel', function (e) {

        this.value = this.value.replace(/[^\d-]/gi, '');

    }).on('blur', '.fnMakeLF', function (e) {
        //class="fnMakeLF fnMakeLV4"
        var _thisVal = this.value,
            _classNameArr = this.className.split(' '),
            _classIndex = 0;
        //获取限制等级
        for (var i = _classNameArr.length - 1; i >= 0; i--) {
            if (_classNameArr[i].indexOf('fnMakeLV') >= 0) {
                _classIndex = _classNameArr[i].replace(/fnMakeLV/, '');
            }
        }
        _thisVal = makeFloat(parseInt(_classIndex), _thisVal);

        // 业务需要新增一个大于1的标志
        if (parseFloat(_thisVal) > 1 && !(this.className.indexOf('fnMakeLFS') >= 0)) {
            _thisVal = 0;
        }

        this.value = _thisVal;

    }).on('blur', '.fnMakePercent100', function (e) {

        var _thisVal = this.value;
        _thisVal = makeFloat(2, _thisVal);

        if (parseFloat(_thisVal) > 100) {
            _thisVal = '100';
        };
        if(parseFloat(_thisVal) < 0 && $(this).hasClass('minZero')){
            _thisVal = '0';
        }

        _thisVal = makeDB(_thisVal)

        this.value = _thisVal;

    }).on('blur', '.fnMax', function (e) {

        var _thisVal = this.value;
        _thisVal = makeFloat(2, _thisVal);

        if(parseFloat(_thisVal) < 0 && $(this).hasClass('minZero')){
            _thisVal = '0';
        }

        _thisVal = makeDB(_thisVal)

        this.value = _thisVal;

    }).on('blur', 'input:not([type="file"],.laydate-icon, .fnInputIgnore)', function (e) {

        // 注释，避免日期中间的空格被删了
        this.value = this.value.replace(/\s*|\s$/g, '');

    }).on('blur', '.fnMakePercent100', function () {

        // if (!!!this.value) {
        //  return;
        // }

        // this.value = +this.value;

    }).on('blur', '.fnMakePercent100,.fnMakeMoney,.fnMakeNumber', function () {

        if (this.value == '.') {
            this.value = '0'
        }

    }).on('click', 'input:not(.fnIgnoreInput),textarea:not(.fnIgnoreInput)', function () {

        // if (util.isIE()) {
        //  this.blur();
        //  this.focus();
        // }

    }).on('keyup', '.fnFloatNumber', function () { //正数：支持整数和小数

        var thisVal = $(this).val();
        thisVal = thisVal.indexOf(',') ? thisVal.replace(/,/g, '') : thisVal;
        var tempVal;

        if ((thisVal.indexOf('.') != thisVal.lastIndexOf('.')) && (thisVal.indexOf('.') > 0 || thisVal.lastIndexOf('.') > 0)) { //判断当前输入的是否为第二个小数点
            // if (thisVal.indexOf('.') > 0 && thisVal.replace('.', '').indexOf('.') > 0) { //判断当前输入的是否为第二个小数点

            tempVal = thisVal.substring(0, thisVal.lastIndexOf('.')); //去除第二个小数点及其后面的数字，保证只有一个小数点

        } else {

            tempVal = thisVal;

        };

        if (isNaN(tempVal)) tempVal = tempVal.replace(/[^\d]/gi, '');
        // if(!!tempVal) tempVal = tempVal.split('').reverse().join('').replace(/(\d{3}(?=\d)(?!\d+\.|$))/g, '$1,').split('').reverse().join('');//逗号分割
        $(this).val(tempVal);

    }).on('blur', '.fnFloatNumber', function () { //正数：支持整数和小数

        var _this = $(this);
        var thisVal = !!_this.val() ? _this.val() : '0';
        thisVal = thisVal.indexOf(',') ? thisVal.replace(/,/g, '') : thisVal;
        var nMaxLength = parseInt($(this).attr('nMaxlength'));
        var intNum = 0,
            floatNum = '00',
            reslt = intNum + '.' + floatNum;
        if (thisVal.indexOf('.') > 0) {
            var ttt = (parseInt(thisVal * 100) / 100).toFixed(2);
            intNum = ttt.split('.')[0];
            floatNum = ttt.split('.')[1];
        } else {
            intNum = thisVal || 0;
            floatNum = '00';
        };
        reslt = intNum + "." + floatNum;
        if (reslt.length > (nMaxLength + 1)) {
            reslt = '0.00';
            Y.alert('提示', '超出数值长度限制，包括小数位在内支持最大长度' + nMaxLength, function () {
                _this.val(reslt);
            });
            return;
        };
        if (parseFloat(reslt) == 0) reslt = _this.hasClass('toZero') ? '0.00' : '';
        // if(!!reslt) reslt = reslt.split('').reverse().join('').replace(/(\d{3}(?=\d)(?!\d+\.|$))/g, '$1,').split('').reverse().join('');//逗号分割
        _this.val(reslt);

    }).on('keyup', '.fnInitNumber', function () { //正数：支持整数和小数

        var _this = $(this);
        var thisVal = !!_this.val() ? _this.val() : '0';
        var notNum = isNaN(thisVal);
        if(notNum){
            _this.val(!_this.attr('tempvalnum') ? '' : _this.attr('tempvalnum'))
        }else {
            _this.attr('tempvalnum',thisVal);
        }


    }).on('focus', '.fnInitNumber', function () { //正数：支持整数和小数
        $(this).attr('tempvalnum',$(this).val().replace(/,/g,''));

    }).on('blur', '.fnInitNumber', function () { //正数：支持整数

        var _this = $(this);
        var thisVal = !!_this.val() ? _this.val() : '0';
        var notNum = isNaN(thisVal);
        if(notNum){
            _this.val('')
        };

    }).on('blur', '.fnMoneyNumber', function () { //金额

        var _this = $(this);
        var thisVal = !!_this.val() ? _this.val() : '0';
        var notNum = isNaN(thisVal);
        if(notNum){
            _this.val('')
        };

    }).on('blur', 'input[maxnumber]', function () { //不能超过某数值
        var $this = $(this);
        var thisVal = $this.val();
        var maxNum = parseInt($this.attr('maxnumber'));
        var tempVal;
        if(!thisVal || !maxNum || isNaN(maxNum)) return;
        if (thisVal > maxNum) {

            Y.alert('提醒', '不得超过当前阈值' + maxNum, function () {
                $this.val('').focus();
            });
            return;
        }

    }).on('blur', '.fnMakeMoney', function () {

        setInputK(this)

    }).on('focus', '.fnMakeMoney', function () {
        $(this).parent().find('.fnMakeMoneyTip').addClass('fn-hide')
    }).on('click', '.fnMakeMoneyTip', function () {
        $(this).addClass('fn-hide').parent().find('.fnMakeMoney').focus()
    }).find('.fnMakeMoney').each(function (index, el) {

        setInputK(this)

    });

    // 千分位，什么鬼
    function setInputK(el) {

        return;

        var $this = $(el)

        var $tip = $this.parent().find('.fnMakeMoneyTip')

        if (!!!$tip.length) {
            $tip = $('<span class="fnMakeMoneyTip"></span>')
            $this.after($tip)
        }

        setTimeout(function () {
            var _offset = $this.offset(),
                _left = _offset.left + 1 + +$this.css('padding-left').replace(/px$/g, '')

            $tip.html(util.num2k($this.val())).css({
                left: _left + 'px',
                top: _offset.top + 1 + 'px'
            }).removeClass('fn-hide')
        }, 20)

    }

    /**
     * 转换小数
     * @param  {[number]} limit [限制多少位小数]
     * @param  {[string]} str   [被转换的字符串]
     * @return {[string]}       [转换后的字符串]
     */
    function makeFloat(limit, str) {

        var _str = str;
        var IS_NEGATIVE = (_str.substr(0, 1) === '-') ? true : false;

        //除了数字和小数点都不要
        _str = _str.replace(/[^\d\.]/g, '');

        //去除多个小数点的可能性
        var _arr = _str.split('.');

        if (_arr.length > 2) {
            _str = _arr[0] + '.' + _arr[1];
        }

        //获取第一个小数点的位置
        var _dotIndex = _str.indexOf('.');

        if (_dotIndex < 0) {
            return (IS_NEGATIVE ? '-' : '') + _str;
        }

        return (IS_NEGATIVE ? '-' : '') + _str.substr(0, (_dotIndex + 1 + limit));

    }

    //字数限制 start
    $('input').each(function (index, el) {

        var _this = $(this);
        //隐藏
        if (this.type == 'hidden' || this.type == 'button' || this.type == 'submit' || this.type == 'checkbox' || this.type == 'radio') {
            return;
        }

        //只读
        if (_this.attr('readonly') || _this.attr('maxlength') || _this.hasClass('fn-input-hidden')) {
            return;
        }

        _this.attr('maxlength', '50');

        if (_this.hasClass('fnMakeMoney')) {
            _this.attr('maxlength', '16');
        }

    });

    $('textarea').each(function (index, el) {

        var _this = $(this);
        //是否有值
        if (_this.attr('maxlength')) {
            return;
        }
        _this.attr('maxlength', '1000');

    });

    if (util.isIE()) {
        $body.on('blur', 'input,textarea', function () {
            var _this = $(this),
                maxlens = +_this.attr('maxlength') - 2,
                inputstr = _this.val() || _this.text();
            if (inputstr.length > maxlens) {
                _this.val(inputstr.substr(0, maxlens)).trigger('blur');
            }
        });
    }

});