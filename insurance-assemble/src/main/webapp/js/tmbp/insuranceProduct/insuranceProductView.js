define	(function(require, exports, module) {
	   require('tmbp/upAttachModifyNew');
	   $("a.fnUpAttachBtn").remove();
	    var template = require('arttemplate');

	    var $body = $('body');
	    
	   //设置费率表
	    $body.on('click', '.setingRatio', function(event) { //设置费用比例<option value="0">请选择</option><option value="0">请选择</option>
	        var $this, data, Html, val;
	        var manOptions = ['<td class="fn-p-reb trSexMan"><select class="ui-select fn-w100 paymentPeriodSelect">','</select><input type="text" class="fn-validate text paymentPeriodInput fnMakeNumber fn-hide" disabled="true"><span class="recordDelete rowDelete">━</span></td>'];
	        var womanOptions = ['<td class="fn-p-reb trSexWoman"><select class="ui-select fn-w100 paymentPeriodSelect">','</select><input type="text" class="fn-validate text paymentPeriodInput fnMakeNumber fn-hide" disabled="true"><span class="recordDelete rowDelete">━</span></td>'];


	        var $this = $(this);
	        var val = $this.siblings('.setingRatioArr').val();
	        var OPTION_CODE = !$('.setingRatioOptinCode').val() ? [] : $('.setingRatioOptinCode').val().split(',');
	        var OPTION_TEXT = !$('.setingRatioOptinText').val() ? [] : $('.setingRatioOptinText').val().split(',');

	      
	        //临时数据
	        var items =[{
	            year:10,
	            man:[
	                {period:1,charge:10},
	                {period:2,charge:200},
	                {period:3,charge:300},
	                {period:4,charge:400},
	                {period:5,charge:900}
	            ],
	            woman:[
	                {period:1,charge:500},
	                {period:2,charge:600},
	                {period:3,charge:700},
	            ]
	        },{
	            year:15,
	            man:[
	                {period:1,charge:101},
	                {period:2,charge:2001},
	                {period:3,charge:3001},
	                {period:4,charge:4001},
	                {period:5,charge:9001}
	            ],
	            woman:[
	                {period:1,charge:5001},
	                {period:2,charge:6001},
	                {period:3,charge:7001},
	            ]
	        }];

	        data = {
	            itemsLen: {
	                colspanMan:[],
	                colspanWoman:[],
	                colspanManOptions:{
	                    code:OPTION_CODE,
	                    text:OPTION_TEXT
	                },
	                colspanWomanOptions:{
	                    code:OPTION_CODE,
	                    text:OPTION_TEXT
	                }
	            },
	            items: !!val ? JSON.parse(val) : [],
	            newly: []
	        };

	        //从items中获取男，女有多少列，并取得每列的val，并且缓存哪些选择已被选择
	        if(data.items.length > 0){

	            // $.each(data.items[0].man,function (index1,item1) {
	            //     var period = (!item1.period && item1.period != 0) ? item1.period : '';
	            //     var text = (!item1.text && item1.text != 0) ? item1.text : '';
	            //     data.itemsLen.colspanMan.push({period:period,text:text});
	            //     // paymentPeriodSelectMan.push(item1.period.toString());//缓存已被选择选项,转换成字符串，以便调用indexOf方法比较
	            // });
	            //
	            // $.each(data.items[0].woman,function (index2,item2) {
	            //     data.itemsLen.colspanWoman.push({period:item2.period,text:item2.text});
	            //     // paymentPeriodSelectWoman.push(item2.period.toString());//缓存已被选择选项,转换成字符串，以便调用indexOf方法比较
	            // });

	            colspanSelectaa(data.items[0].man,data.itemsLen.colspanMan)
	            colspanSelectaa(data.items[0].woman,data.itemsLen.colspanWoman)

	        }else {

	            data.itemsLen.colspanMan = [{period:''}];
	            data.itemsLen.colspanWoman = [{period:''}];

	        };

	        function colspanSelectaa(_d,_arry) {
	            $.each(_d,function (_index,_item) {
	                var period = (!_item.period && _item.period != 0) ? '' : _item.period;
	                var text = period;
	                period = OPTION_CODE.indexOf(period) == -1 ? 'DEFINED' : period;
	                _arry.push({period:period,text:text});
	            });
	        }
	        //根据数据拼接下来选择的选项
	        if(data.itemsLen.colspanManOptions.code.length > 0){

	            $.each(data.itemsLen.colspanManOptions.code,function (index1,val1) {
	                manOptions.splice(index1+1,0,'<option value="'+ val1 +'">' + data.itemsLen.colspanManOptions.text[index1] + '</option>');
	            });

	            $.each(data.itemsLen.colspanWomanOptions.code,function (index2,val2) {
	                womanOptions.splice(index2+1,0,'<option value="'+ val2 +'">' + data.itemsLen.colspanWomanOptions.text[index2] + '</option>');
	            })

	        };

	        console.log(data)

	        Html = template('setingRatioPopup', data);

	        $('body').Y('Window', {
	            content: Html,
	            modal: true,
	            key: 'modalwndSetingRatio',
	            simple: true,
	            closeEle: '.close'
	        });

	        var modalwnd, wndBox;

	        modalwnd = Y.getCmp('modalwndSetingRatio');
	        wndBox = modalwnd.wnd;

	        //事件绑定

	        //增加列
	        wndBox.on('click', 'a.colBtn', function() {

	            var sex = $(this).attr('btnsex');
	            var $targetList = wndBox.find('tbody tr');
	            var $theadTr1 = wndBox.find('thead tr.thead1');
	            var $theadTr2 = wndBox.find('thead tr.thead2');
	            var colspanTd = +$theadTr1.find('th.' + sex).attr('colspan') + 1;
	            var maxItenLen = $(this).attr('maxitenlen');
	            var optionHtml = sex == 'trSexMan' ? manOptions.join('') : womanOptions.join('');
	            if(colspanTd > maxItenLen){
	                Y.alert('提示','不能超过缴费年限选项条数：' +maxItenLen + '条！');
	                return;
	            };
	            $theadTr1.find('th.' + sex).attr('colspan',colspanTd);
	            $theadTr2.find('td.' + sex + ':last').after(optionHtml);
	            $targetList.each(function(index, el) {

	                var $el = $(el);
	                var domPostion = $el.find('td.' + sex + ':last');

	                domPostion.after('<td class="fn-text-c ' + sex + '"><input class="text" type="text" name=""></td>')

	            });

	        });

	        //增加行
	        wndBox.on('click', 'a.rowBtn', function() {
	            var rowHtml, $tbody;

	            $tbody = wndBox.find('tbody');
	            var colspanMan = parseInt(wndBox.find('thead th.trSexMan').attr('colspan'));
	            var colspanWoman = parseInt(wndBox.find('thead th.trSexWoman').attr('colspan'));
	            var maxLength = $(this).attr('maxitemlength');
	            var colLength = colspanWoman + colspanMan;
	            var tdSex = '';
	            if(maxLength == wndBox.find('tbody tr').length) {
	                Y.alert('提示','最多只能添加' + maxLength + '条！');
	                return;
	            }
	            rowHtml = ['<tr class="new">',
	                '<td class="fn-text-c fn-p-reb"><input type="text" class="text fnInitNumber insureAge"><span class="recordDelete colDelete">━</span></td>',
	                '</tr>'
	            ];

	            for (var i = 1; i <= colLength; i++) {
	                if(i <= colspanMan) {
	                    tdSex = 'trSexMan';
	                }else {
	                    tdSex = 'trSexWoman';
	                }
	                rowHtml.splice(-1, 0, '<td class="fn-text-c ' + tdSex + '"><input class="text" type="text fnFloatNumber" name=""></td>');

	            };

	            $tbody.append(rowHtml.join(''));

	        });

	        //确定
	        wndBox.on('click', 'a.okBtn', function() {
	            var newItems = [];

	            wndBox.find('tbody tr').each(function(index, el) {

	                var $this = $(el);

	                newItems[index] = {
	                    year: $this.find('td:first input').val(),
	                    man:[],
	                    woman:[]
	                };
	                $.each(wndBox.find('thead tr').eq(1).find('td'),function (opIndex,ele) {
	                    var selectVal = $(this).find('select').val();
	                    var selectDis = $(this).find('select').is(':disabled');
	                    var text = selectDis ? $(this).find('input').val() : selectVal;
	                    if($(this).hasClass('trSexMan')) {
	                        newItems[index].man.push({period:text,charge:$this.find('td').eq(opIndex + 1).find('input').val()});
	                    }else {
	                        newItems[index].woman.push({period:text,charge:$this.find('td').eq(opIndex + 1).find('input').val()});
	                    }
	                })

	            });
	            console.log(newItems)
	            $this.siblings('.setingRatioArr').val(JSON.stringify(newItems));
	            // $this.siblings('.setingRatioArr').val(JSON.stringify(newItems).replace(/\"/g,"'"));

	            if(!verify(".insureAge")){
	                return false;
	            }
	            modalwnd.close();
	        });
	        var verify = function (val) {
	            var insureAge = $(val);
	            var insureAgeVal = insureAge.val();
	            var arr=[];
	            var a =true;
	            for(var i = 0;i<insureAge.length;i++){
	                insureAge.eq(i).parent().children("span").remove();
	                insureAge.eq(i).parent().append("<span class='err' style='color: red;'></span>");
	                if(insureAgeVal<0||insureAgeVal>150 || insureAgeVal==""){
	                    $(".err").html("年龄0-150");
	                }else {
	                    $(".err").html("");
	                }
	                var errVal = $(".err").html();
	                arr[i]=errVal;
	            }
	            for(var o = 0;o<arr.length;o++){
	                if(arr[o].length>0){
	                    a = false;
	                    break;
	                }
	            }
	            if(!a){
	                return false;
	            }else {
	                return true;
	            }
	        }

	        //删除该行
	        wndBox.on('click', '.colDelete', function() {

	            var $this, $tr;

	            $this = $(this);
	            $tr = $this.parents('tr');


	            $tr.remove();


	        });

	        //删除该行列
	        wndBox.on('click', '.rowDelete', function() {

	            var $this = $(this);
	            var $table = $this.parents('table');
	            var $thisTd = $(this).parents('td');
	            var sex = $thisTd.hasClass('trSexMan') ? 'trSexMan' : 'trSexWoman';
	            var $th = $table.find('th.' + sex);
	            var thColspan = $th.attr('colspan') || 2;
	            var $index = $thisTd.index();

	            $th.attr('colspan',thColspan - 1);
	            $table.find('tr').each(function(index, el) {
	                if(index == 0) return;
	                var $el;

	                $el = $(el);
	                if(index == 1) {
	                    $el.find('td').eq($index - 1).remove();
	                }else {
	                    $el.find('td').eq($index).remove();
	                }

	            });

	        });

	        //设置费用比例-投保年龄不能重复
	        wndBox.on('blur','.insureAge',function () {
	            var _this = $(this);
	            var _thisVal = _this.val();
	            var $allInsureAge = wndBox.find('tbody .insureAge').not(_this);
	            if(!_thisVal) return;
	            $.each($allInsureAge,function (inxe,ele) {
	                if($(ele).val() == _thisVal) {
	                    Y.alert('提示','投保年龄不能重复！',function () {
	                        _this.val('').focus();
	                    });
	                    return false;
	                }
	            })
	        });

	        //设置费用比例-缴费年限-select
	        wndBox.on('change','.paymentPeriodSelect',function () {
	            var _this = $(this);
	            var _thisVal = _this.val();
	            if(!_thisVal) return;
	            if(_thisVal === 'DEFINED') {
	                _this.addClass('fn-hide').attr('disabled','disabeld');
	                _this.siblings('.paymentPeriodInput').removeClass('fn-hide').removeAttr('disabled').val('');
	                return;
	            };
	            diffOptions(_this);

	        });

	        //设置费用比例-缴费年限-input
	        wndBox.on('blur','.paymentPeriodInput',function () {

	            var _this = $(this);
	            var _thisVal = _this.val();

	            if(!_thisVal) {
	                _this.addClass('fn-hide').attr('disabled','disabeld');
	                _this.siblings('.paymentPeriodSelect').removeClass('fn-hide').removeAttr('disabled').val('');
	                return;
	            };
	            if(_thisVal == 0 || isNaN(_thisVal)){//非空且不为数字，则清空
	                _this.val('').focus();
	                return;
	            };
	            diffOptions(_this);

	        });
	        //选项不能重复
	        function diffOptions(_this) {
	            var _val = _this.val();
	            var isMan = _this.parents('td').hasClass('trSexMan');
	            var $td = _this.parents('tr').find(isMan ? 'td.trSexMan' : 'td.trSexWoman').not(_this.parents('td'));
	            $.each($td,function (i,el) {
	                var $select = $(el).find('.paymentPeriodSelect,.paymentPeriodInput').not(':hidden');
	                if($select.length == 1 && $select.val() == _val && !!$select.val()){
	                    Y.alert('提示','相同性别缴费年限不能重复！',function () {
	                        _this.val('').focus();
	                    });
	                    return false;
	                }
	            })
	        }

	    });
});