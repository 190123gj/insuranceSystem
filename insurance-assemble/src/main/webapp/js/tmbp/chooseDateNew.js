/*
* @Author: erYue
* @Date:   2016-08-17 15:56:35
* @Last Modified by:   erYue
* @Last Modified time: 2016-08-17 16:14:01
*/

/*
    <div class="item chooseBox">
        <input class="ui-text fn-w90 chooseYM" type="text" value="" placeholder="选择年月" readOnly="true">
        <input class="chooseYear" type="hidden" name="year" value="" placeholder="选择年" readOnly="true">
        <input class="chooseMonth" type="hidden" name="month" value="" placeholder="选择月" readOnly="true">
        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray chooseTimeClear">清除</a>
    </div>
    <div class="item chooseBox">
        <input class="ui-text fn-w90 chooseYM chooseYMS not" type="text" value="" placeholder="选择开始年月" readOnly="true" name="reportTime">
        <label class="fenge">-</label>
        <input class="ui-text fn-w90 chooseYM chooseYME not" type="text" value="" placeholder="选择结束年月" readOnly="true" name="reportTime">
        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray chooseTimeClear">清除</a>
    </div>

 */

define(function(require, exports, module) {

    var yearsTime = require('zyw/yearsTime');
    var $body = $('body');
    var yearsTimeFirst;
    $body.on('focus','.chooseYM',function(event) {
        var $this = $(this);
        var format = $this.attr('timeformat');
        var max = $this.attr('max');
        var min = $this.attr('min');
        // if($this.hasClass('chooseYME') && !min) {
        //     Y.alert('提示','请先选择开始年月');
        //     return;
        // };
        yearsTimeFirst = new yearsTime({
            elem: this,
            max:max,
            min:min,
            format: format,
            callback: function(_this, _time) {//_this返回当前elem的jq对象；_time返回一个年月数组(数组的每个成员为Number类型)，如[2016,8]或者[2016,10]
                var notsplit = _this.attr('notsplit');//是否分割储存
                var flag = _this.attr('timeflag');//获取当前的标记，分割储存是存储到对应标记的input
                var year = _time[0];
                var month = _time[1] < 10 ? '0' + _time[1] : _time[1];//min和max的格式必须为'YYYY-MM'格式
                var minMonth = ++_time[1];
                var minYear = minMonth > 12 ? ++_time[0] : _time[0];
                minMonth = minMonth < 10 ? '0' + minMonth : minMonth > 12 ? '01' : minMonth;
                var minYM = minYear + '-' + minMonth;
                if(_this.hasClass('chooseYMS')) {
                    var $otherInput = _this.siblings('.chooseYME');
                    var thisValSeconds = new Date(year + '-' + month + '-01 00:00:00').getTime();
                    var otherInputValSeconds = !!$otherInput.val() ? new Date($otherInput.val() + '-01 00:00:00').getTime() : thisValSeconds+1000;
                    if(thisValSeconds >= otherInputValSeconds) $otherInput.val('');
                    $otherInput.attr('min',minYM)
                }

                if(_this.hasClass('chooseYear')) _this.siblings('[name*=reportYear]').val(year);

                if(!!notsplit || format == 'YYYY') return;

                if(!flag){
                    var $box = _this.parents('.chooseBox');
                    $box.find('.chooseYear').val(year).end()
                        .find('.chooseMonth').val(month);
                }else {
                    $('.' + flag + 'Year').val(year)
                    $('.' + flag + 'Month').val(month)
                }

            }
        });
    }).on('focus','.chooseTimeClear',function() {
        var $chooseInput = $(this).siblings('.chooseYM,.chooseYear,.chooseMonth');
        $chooseInput.val('');
    }).on('click','#showFnListSearchBtn',function (e) {
        var $chooseYMS = $('.chooseYMS');
        var $chooseYME = $('.chooseYME');
        var chooseYMSVal = $chooseYMS.val().replace(/\s/g, '');
        var chooseYMEVal = $chooseYME.val().replace(/\s/g, '');
        if(($chooseYMS.length > 0 && $chooseYME.length > 0) && !chooseYMSVal && !!chooseYMEVal){
            Y.alert('提示','请完善搜索条件：选择报送开始年月');
        }else {
            $('#fnListSearchBtn').click();
        }
    })


})
