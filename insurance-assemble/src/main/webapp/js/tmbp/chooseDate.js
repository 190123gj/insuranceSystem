/*
* @Author: erYue
* @Date:   2016-08-17 15:56:35
* @Last Modified by:   erYue
* @Last Modified time: 2016-08-17 16:14:01
*/

/*
    <div class="item chooseBox">
        <input class="ui-text fn-w90 chooseTime chooseYear" type="text" name="year" value="" placeholder="选择年" readOnly="true">
        <label class="fenge">-</label>
        <input class="ui-text fn-w90 chooseTime chooseMonth" type="text" name="month" value="" placeholder="选择月" readOnly="true">
        <a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-gray chooseTimeClear">清除</a>
    </div>

    chooseBox //盒子
    chooseTime //事件触发选择器
    chooseYear、chooseMonth //标记是年还是月
    支持单一选择：隐藏掉不需要的输入框即可
    限制选择年月范围(默认不限制):

        限制最小年月(min="date")：在chooseBox上添加属性minDate，
            不添加min属性值表示不限制最小,
            date值为：false 或 ""，表示不限制；
            date值为：true，表示限制最小年月为当前系统年月；
            date值为：y-m-prev， 表示当前系统时间向前y年m月，pre为向前标记，不能变，
                    若m小于0自动纠正为0，若m大于12，则y=y+parseInt(m / 12),m = m % 12,
                    例：date="1-2-pre",假设系统当前为2016年9月，则表示2015年7月
                       date="1-34-pre",假设系统当前为2016年9月，则表示2012年1月

            date值为：y-m-next，表示当前系统时间向后y年m月，next为向后标记，不能变，规则同pre；
            data值格式为'yyyy-mm'或者'yyyy-m'，表示限制最小时间为data，若m大于12则自动纠正为0；

        限制最大年月(max="date")：在chooseBox上添加属性max，方法与最小时间相同,

         如：<div class="item chooseBox" max="2019-06"> //不超过2019年6月
            <div class="item chooseBox" max="4-11-next"> //最大年限在当前系统时间的基础上向后移动4年11个月，月遇12则向年进1，如当前为2016-09==>2021-08
            <div class="item chooseBox" max="4-11-prev"> //最大年限在当前系统时间的基础上向前移动4年11个月，月不够则向年借1，如当前为2016-09==>2011-10
 */

define(function(require, exports, module) {

    var $body = $('body');
    var currentTime = new Date();
    var crnYear = currentTime.getFullYear();
    var crnMonth = currentTime.getMonth() + 1;
    var maxNum = 0,
        minNum = 0, //年阈值,用做缓存当前已选择到的最大值和最小值，如果不是最大或最小则置为0
        limitArry = []; //限制年月的数组:minYear = limitArry[0];minMonth= limitArry[1];maxYear = limitArry[2];maxMonth = limitArry[3],0表示不限制
    var t = 200; //定时时间
    var timer; //定时器

    $body.on('click', '.chooseTime', function() {//触发选择
        var _this = $(this);
        chooseTime(_this);

    }).on('mouseout', '.chooseTime', function() {//失焦消失
        var _this = $(this);
        timer = setTimeout(function () {
            _this.siblings('.timeContent').hide();
        }, t);

    }).on('mouseover', '.timeContent', function() {//清除定时器
        var _this = $(this);
        clearTimeout(timer);
    }).on('mouseout', '.timeContent', function() {//失焦消失
        var _this = $(this);
        timer = setTimeout(function () {
            _this.hide();
        }, t);
    }).on('click', '.changeTime', function() {//切换年
        clearTimeout(timer);
        var _this = $(this);
        var time = parseInt(_this.parents('.chooseBox').attr('startTime'));

        if (_this.hasClass('icon-left')) {
            if (time - 1 < limitArry[0] && limitArry[0]){//限制最小
                return;
            };
            chooseTime(_this,(time - 12));
        }else {
            if (time + 12 > limitArry[2] && limitArry[2]){//限制最大
                return;
            };
            chooseTime(_this,(time + 12));
        };
    }).on('click', '.timeBox .toChoose', function() {//确认选择
        clearTimeout(timer);
        var _this = $(this);
        var flag = _this.parents('.timeContent').hide().attr('id');

        _this.parents('.chooseBox').find('.' + flag).val(_this.html());
        if(flag == 'chooseYear'){//判断是否选择当前年
            var $monthIpt = $('input.chooseMonth');
            var thisYear = parseInt(_this.html());
            var giveVal = ($monthIpt || $monthIpt.val().length == 0) ? '' : parseInt($monthIpt.val());
            //当前选择的年为最大最小年(minYear 或者 maxYear)时，限制对应月的最大最小值月，存储限制状态;0 表示无限制
            if(thisYear == limitArry[0] && !!limitArry[0]){
                minNum = limitArry[0];
                if(limitArry[0] == limitArry[2]){
                    maxNum = limitArry[0];
                    giveVal = (giveVal < limitArry[1] && !!limitArry[1]) || (giveVal > limitArry[3] && !!limitArry[3]) ? '' : giveVal;
                }else {
                    maxNum = 0;
                    giveVal = giveVal< limitArry[1] && !!limitArry[1] ? '' : giveVal;
                }
                $monthIpt.val(giveVal);
            }else if(thisYear == limitArry[2] && !!limitArry[2]){
                maxNum = limitArry[2];
                if(limitArry[0] == limitArry[2]){
                    minNum = limitArry[2];
                    giveVal = (giveVal < limitArry[1] && !!limitArry[1]) || (giveVal > limitArry[3] && !!limitArry[3]) ? '' : giveVal;
                    $monthIpt.val(ttt);
                }else {
                    minNum = 0
                    giveVal = giveVal > limitArry[3] && !!limitArry[3] ? '' : giveVal;
                }
                $monthIpt.val(giveVal);
            }else{
                maxNum = 0;
                minNum = 0;
            }
        };
    }).on('click','.chooseTimeClear',function () {
        var $box = $(this).parents('.chooseBox');
        var $input = $box.find('.chooseTime');
        $input.each(function () {
            $(this).val('');
        })
    });

    function chooseTime($ele,time) {//渲染选择面板
        var $thisPs = $ele.parents('.chooseBox');
        var thisVal = $ele.val() || $thisPs.find('input.chooseYear').val();
        var startTime = !!time ? time : parseInt($thisPs.attr('startTime')) || false;
        var isYear = $ele.hasClass('chooseYear') || $ele.hasClass('changeTime');
        var isMonth = $ele.hasClass('chooseMonth');
        var $modBox =$thisPs.find('.timeContent');
        var $contents = ($modBox.length > 0) ? $modBox : $('<div class="timeContent">'+
                        '                <p class="chooseTtl">' +
                        '                    <i class="icon-left changeTime"><</i>切换' +
                        '                    <i class="icon-right changeTime">></i>' +
                        '                </p>'+
                        '               <div class="timeBox"></div>'+
                        '           </div>');
        var top = $thisPs.height() + 6;
        var left = $ele.offset().left - $thisPs.offset().left;
        var $timeBox = $contents.find('.timeBox').html('');
        splitDate($ele); //解析限制时间规则
        if ($ele.hasClass('chooseTime')) {//选择面板定位
            $contents.css({
                top: top,
                left: left
            });
        };

        if (isYear || time){ //渲染年设置开始和结束时间

            $contents.attr('id','chooseYear').find('.chooseTtl').show();

            if (!startTime) {
                if (thisVal.length == 0) { //没有当前选择值则默认为当前年
                    var tmpYear = crnYear;
                    if(crnYear < limitArry[0] && limitArry[0]){
                        tmpYear = limitArry[0];
                    }else if(crnYear > limitArry[2] && limitArry[2]){
                        tmpYear = limitArry[2];
                    }else{
                        tmpYear = thisVal || crnYear;
                    }

                    startTime = tmpYear - 5;
                    thisVal = tmpYear;

                }else {
                    startTime = thisVal - 5;
                }
            }else {

                thisVal = thisVal || crnYear;
            }
        }

        if (isMonth){ //渲染月设置开始和结束时间
            $contents.attr('id','chooseMonth').find('.chooseTtl').hide();
            startTime = 1;
            if ($ele.val().length == 0) { //没有当前选择值则默认为当前月
                thisVal = crnMonth;
                if (limitArry[0] == limitArry[2] && $thisPs.find('input.chooseYear').val().length != 0){
                    thisVal = limitArry[3];
                }
            };
        };

        for(var i = startTime; i < (startTime + 12); i++){
            var isSpecailFlag = limitArry[0] < limitArry[2] || (limitArry[0] == limitArry[2] && limitArry[1] <= limitArry[3]) || limitArry[0] == 0 || limitArry[2] == 0,
                isChoosedFlag = (i == thisVal) && (isMonth || (i <= limitArry[2] || limitArry[2] == 0) && (i >= limitArry[0] || limitArry[0] == 0)),
                minYearFlag = isYear && !!limitArry[0] && i < limitArry[0],
                maxYearFlag = isYear && !!limitArry[2] && i > limitArry[2],
                minMonthFlag = isMonth && !!limitArry[1] && minNum == limitArry[0] && i < limitArry[1],
                maxMonthFlag = isMonth && !!limitArry[3] && maxNum == limitArry[2] && i > limitArry[3];

            if(isSpecailFlag){
                if(minYearFlag || maxYearFlag || minMonthFlag || maxMonthFlag){
                    $timeBox.append('<span>' + i + '</span>');
                }else if (isChoosedFlag) {
                    $timeBox.append('<span class="toChoose choosed">' + i + '</span>');
                }else{
                    $timeBox.append('<span class="toChoose">' + i + '</span>');
                }
            }else {
                $timeBox.append('<span>' + i + '</span>');
            }

        };
        if (isYear || time) $thisPs.attr('startTime',startTime);
        $thisPs.append($contents.show());
    };

    function splitDate($ele) {//解析年月限制条件
        if(limitArry.length > 0) return;
        //读取年月限制
        var min =  $ele.parents('.chooseBox').attr('min') || false;
        var max =  $ele.parents('.chooseBox').attr('max') || false;

        var reg1 = /^(\d{4})-(\d{2})$/,
            reg2 = /^(\d{0,4})-(\d{0,2})-prev|next$/;
        var minArry = [], maxArry = [];
        var minStatus = min && (reg1.test(min) || reg2.test(min)),
            maxStatus = max && (reg1.test(max) || reg2.test(max));

        min = minStatus ? min : false;
        max = maxStatus ? max : false;

        if(min == 'true') min = crnYear + '-' + crnMonth;
        if(max == 'true') max = crnYear + '-' + crnMonth;

        min = countYM(min);
        max = countYM(max);

        var flag = min || max ? true : (new Date(min + '-01 00:00:00') < new Date(max + '-01 00:00:00'));

        if (min && flag){
            minArry = min.split('-');
        }else {
            minArry[0] = 0;
            minArry[1] = 0;
        };
        if (max && flag){
            maxArry = max.split('-');
        }else {
            maxArry[0] = 0;
            maxArry[1] = 0;
        };

        limitArry = [
            parseInt(minArry[0]),
            parseInt(minArry[1]),
            parseInt(maxArry[0]),
            parseInt(maxArry[1])
        ];
        
        function countYM(str) {
            if (!!!str) return str;

            var y = parseInt(str.split('-')[0]),
                m = parseInt(str.split('-')[1]);

            var tmp = y * 12 + m,
                x = 0;

            if(str.indexOf('prev') > 0){
                tmp = (crnYear - y) * 12 + crnMonth - m;
                x = 12;
            };
            if(str.indexOf('next') > 0){
                tmp = (crnYear + y) * 12 + crnMonth + m;
                x = 1;
            };

            var newY = parseInt(tmp / 12);
            var newM = parseInt(tmp % 12);

            newM = (newM == 0) ? x : newM;
            str = newY + '-' + newM;

            return str
        }
    }
})
