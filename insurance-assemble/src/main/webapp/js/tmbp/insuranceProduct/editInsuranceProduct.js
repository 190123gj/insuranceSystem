'use strict'
define(function(require, exports, module) {
	//客户协议
	// require('Y-msg');
	require('input.limit');
    require('tmbp/operate.common');
    require('tmbp/upAttachModifyNew');
    require('tmbp/submit.common');
    
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();

	var util = require('util');
    var getList = require('zyw/getList');
	var selectType = require('tmbp/selectType');
    var template = require('arttemplate');

    var $GETLIST_TR = '';//各个getList，用来缓存当前是哪一个gelist
    var $form = $("#form");
    var $body = $('body');
    var searchStr = window.location.search;
    //从分类管理新增产品时会用到
    if(!!searchStr && searchStr.indexOf('catalogName') >=0 && searchStr.indexOf('catalogId') >=0 ) {
        var urlObj = getUrlVal(searchStr);
        $('.selectRslt').remove();
        $('.selectFn').parents('.selectFnBox1').attr('isreadonly',true);
        $('.selectFn').find('label').html(decodeURIComponent(urlObj.catalogName)).end()
            .find('i').remove();
        $('[name=catalogId]').val(urlObj.catalogId);
        $('[name=catalogName]').val(decodeURIComponent(urlObj.catalogName));
    };

    //获取url参数
    function getUrlVal(searchStr){
        if(!searchStr) return;
        var urlValStr = searchStr.replace('?','');
        var urlValArry = urlValStr.split('&');
        var urlValObject = {};
        for(var i in urlValArry){
            urlValObject[urlValArry[i].split('=')[0]] = urlValArry[i].split('=')[1];
        };
        return urlValObject;
    };

    // 下拉选择
    new selectType({
        selectBoxObj: $('.selectFnBox1'),
        afterChoosedCallback: {
            callbackTargetCommon: function ($this) {
                var ownerId = $this.attr('kindsid');
                var isLifeInsuranceText = $this.attr('isLifeInsuranceText');
                //是否为寿险
                var isLifeInsurance = $this.attr('isLifeInsurance');
                //寿险
                if (isLifeInsurance == "YES") {
                    $(".lifeInsuranceYes").removeClass("fn-hide").find("input").removeAttr("disabled");
                    $(".lifeInsuranceNo").addClass("fn-hide").find("input").attr("disabled", true);
                    $(".lifeInsurance_payType").removeClass("fn-hide").find("input").removeAttr("disabled");
                    //非寿险
                } else {
                    $(".lifeInsuranceYes").addClass("fn-hide").find("input").attr("disabled", true);
                    $(".lifeInsuranceNo").removeClass("fn-hide").find("input").removeAttr("disabled");
                    
                    //非寿险产品没有缴费类型 和 缴费年限
                    $(".lifeInsurance_payPeriod,.lifeInsurance_payType").addClass("fn-hide").find("input").attr("disabled", true);
                }
                var $thisBox = $this.parents('.selectFnBox1');
                $thisBox.find('#selectedId').val($this.attr('kindsid')).blur();
                $thisBox.find('#selectedName').val($this.attr('valuedata')).blur();
                $('#isLifeInsuranceText').html(isLifeInsuranceText);
                $('#isLifeInsurance').val(isLifeInsurance);
                $('#form').trigger('submit');
                $.ajax({
                    url: "/insurance/insuranceProduct/getInsuranceCatalogLiabilitys.json",
                    type: "POST",
                    dataType: "json",
                    data: {ownerId: ownerId},
                    success: function (res) {
                        // console.log(res)
                        if (res.success) {
                            var data = res.returnObject;
                            $(".insuranceCatalogLiability").removeClass("fn-hide");
                            var _table = $('<div></div>');
                            $.each(data, function (i, thisData) {
                                var $template = $($('#TMP_INSURANCE_CATALOG_LIABILITY').html());
                                $template.find('[name*=liabilityId]').attr('value',thisData.liabilityId).attr('name', 'insuranceCatalogLiabilityOrders[' + i + '].liabilityId');
                                $template.find('[name*=liabilityName]').attr('value',thisData.liabilityName).attr('name', 'insuranceCatalogLiabilityOrders[' + i + '].liabilityName');
                                _table.append($template);
                            });
                            $("#insuranceCatalogLiability").find('tbody').html(_table.html());
                        } else {
                            Y.alert('提示', res.message);
                        }
                    }
                });
            }
        }
    });

    //定额与非定额切换
    $body.on('change',"input:radio[name='isQuota']",function(){
    	//定额
    	if ($(this).val() == "YES") {
    		$(".lifeInsuranceLevel").removeClass("fn-hide").find("input").removeAttr("disabled");
    	//非定额
    	} else {
    		$(".lifeInsuranceLevel").addClass("fn-hide").find("input").val("").attr("disabled",true);
    	}
    });

    //更新当前GETLIST
    $body.on('click', '.fnToChooseBtn', function () {
        $GETLIST_TR = $(this).parents('tr');
        $GETLIST_TR.find('.fnToChoose').click();
    }).on('click','.fnToChoose',function(){
    	   $GETLIST_TR = $(this).parents('tr');
           var arry = [];
           $.each($(this).parents('table').find('[name*=liabilityId]'),function (i,el) {
               if(!!$(el).val()) arry.push($(el).val());
           });
           var param = (arry.length > 0) ? '?liabilitys=' + arry : '';
           insuranceLiability.ajaxUrl = '/baseDataLoad/insuranceLiability.json' + param;
    }).on('click', '.fnToClear', function () {
        $GETLIST_TR = $(this).parents('tr');
        $GETLIST_TR.find('[name*=liabilityId]').val("");
        $GETLIST_TR.find('[name*=liabilityName]').val("");
    });

    //缴费期限切换
    $body.on('click','[name="payType"]',function () {
        var $self = $(this);
        var payPeriod = $('#payPeriod');
        //一次性 和 年交的产品无缴费系数，按半年交、季交、月交的存在分期
        if ($self.val() == 19 || $self.val() == 18) {
        	$(".periodRate").addClass('fn-hide').find("input").attr("disabled",true);
        } else {
        	$(".periodRate"). removeClass('fn-hide').find("input").removeAttr("disabled");
        }

        $('#payPeriod .payTypeItems').addClass('fn-hide').find('[name]').attr('disabled','disabled');
        if($self.val() == 19){
            $('#payPeriod .isPayPeriod').removeClass('fn-hide').find('[type=checkbox][name]').removeAttr('disabled');
        }else {
            $('#payPeriod .notPayPeriod').removeClass('fn-hide').find('[type=checkbox][name]').removeAttr('disabled');
        }

    });

    //缴费年限切换
    $body.on('click','.notPayPeriod .checkbox',function () {
        var _self = $(this);
        var $parents = _self.parents('.notPayPeriod');
        var checked = $parents.find('.payPeriodOther').is(':checked');
        var $payPeriodOtherYear = $parents.find('.payPeriodOtherYear');

        if(checked){
            $payPeriodOtherYear.removeAttr('disabled');
        }else {
            $payPeriodOtherYear.attr('disabled','disabled');
            $payPeriodOtherYear.attr('value','');
        }

    });

    //年限不能重复
    $body.on('blur','.payPeriodOtherYear',function () {
        var _self = $(this);
        var _val = _self.val();
        if(!_val) return;
        _val = _val.replace(/\，/g,',').replace(/\s/g, '');
        _val = _val.split(',');

        var _oldVal = [];
        var _newVal = [];
        var $payPeriodCheckbox = _self.parents('.notPayPeriod').find('.payPeriodBox .checkbox');
        $.each($payPeriodCheckbox,function (i,el) {
            _oldVal.push($(el).attr('targetval').replace('年',''));
        });
        $.each(_val,function (i,val) {
            if(!val || val < 0) return;
            if(_oldVal.indexOf(val) == -1 && _newVal.indexOf(val) == -1 && !isNaN(val)) _newVal.push(val);
        });
        if(_newVal.length < _val.length){
            Y.alert('提示','请勿输入重复的年限值(年限值为正整数)，并且两逗号之间不能为空。',function () {
                _self.val(_newVal);
            })
        }else {
            _self.val(_val);
        }
    })

    //保险期限切换
    $body.on('change','input:radio[name="insurancePeriodType"]',function () {
        var _this = $(this).val();
        if (_this == "终身") {
            $(".year").attr("disabled",true);
            $(".age").attr("disabled",true);
        } else if (_this == "年"){
            $(".year").removeAttr("disabled");
            $(".age").val("");
            $(".age").attr("disabled",true);
        } else if (_this == "岁"){
        	$(".year").val("");
            $(".year").attr("disabled",true);
            $(".age").removeAttr("disabled");
        }
    });
    

   (new getList()).init({
         title: '选择保险公司',
         ajaxUrl: '/baseDataLoad/customer.json',
         btn: '#fnToChooseCompany',
         tpl: {
             tbody: ['{{each pageList as item i}}',
                 '    <tr class="fn-tac m-table-overflow">',
                 '        <td title="{{item.name}}">{{item.name}}</td>',
                 '        <td title="{{item.certType}}">{{item.certType}}</td>',
                 '        <td title="{{item.certNo}}">{{item.certNo}}</td>',
                 '<td><a class="choose" customerId="{{item.customerId}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
                 '    </tr>',
                 '{{/each}}'
             ].join(''),
             form: ['保险公司名称：',
                 '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
                 '<input class="ui-text fn-w100" type="hidden" name="customerType" value="INSURANCE_INSTITUTIONS">',
                 '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
             ].join(''),
             thead: ['<th>保险公司名称</th>',
                 '<th>证件类型</th>',
                 '<th>证件号码</th>',
                 '<th width="50">操作</th>'
             ].join(''),
             item: 5
         },
         callback: function($a) {
             $("#companyUserName").val($a.attr("name")).blur();
             $("#companyUserId").val($a.attr("customerId")).blur();
             $("#companyBaseUserId").val($a.attr("userId")).blur();
         }
     });

    //停售时间切换
    $body.on('click','.stopSaleDate',function () {
		if ($(this).prop("checked")) {
			$("#stopSaleDate").attr({
                disabled:'disabled',
                readonly:'readonly'
            }).val('').blur();
		} else {
			$("#stopSaleDate").removeAttr('disabled readonly').val('').blur();
		}
	}).on('click','.fnToClear',function(){
		var $this = $(this);
		$this.parent().find('input,select,[name]').val('');
	});

	(new getList()).init({
		title: '选择主险',
		ajaxUrl: '/baseDataLoad/product.json',
		btn: '#fnToChooseProduct',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td title="{{item.productName}}">{{item.productName}}</td>',
				'        <td title="{{item.catalogId}}">{{item.catalogId}}</td>',
				'<td><a class="choose" id="{{item.productId}}" name="{{item.productName}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['产品名称：',
				'<input class="ui-text fn-w100" type="text" name="productName">',
				'&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>名称</th>', '<th>所属险种</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#parentProduct").val($a.attr("name"));
			$("#parentProductId").val($a.attr("id"));
		}
	});


	var insuranceLiability = (new getList()).init({
		title: '保险责任',
		ajaxUrl: '/baseDataLoad/insuranceLiability.json',
		btn: '.fnToChoose',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td title="{{item.name}}">{{item.name}}</td>',
                '       <td><a class="choose" sid="{{item.id}}" sname="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['保险责任：',
				'<input class="ui-text fn-w100" type="text" name="name">',
				'&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>责任名称</th>',
			 	'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
        callback: function($a) {
            $GETLIST_TR.find('[name*=liabilityId]').val($a.attr("sid"));
            $GETLIST_TR.find('[name*=liabilityName]').val($a.attr("sname"));
        }
    });

    //地区选择 START
    var $salesAreasOutput = $('.salesAreasOutput');
    var $levelOutput = $salesAreasOutput.find('.levelOutput');
    var $salesAreasCheckbox = $('.salesAreasCheckbox');

    function salesAreasCollect() {

        var arr = new Array();

        $levelOutput.eq(0).find('dd').each(function(index, el) {

            var code, obj;

            code = $(el).attr('code');
            obj = new Object();
            obj[code] = [];

            arr.push(obj);

        });

        $levelOutput.eq(1).find('dl').each(function(index, el) {

            var $el, childrenArr, code, obj;

            $el = $(el);
            childrenArr = new Array();
            code = $el.attr('code');
            obj = new Object();

            $el.find('dd').each(function(index, el) {
                // console.log(index);
                childrenArr.push($(el).attr('code'))

            });

            obj[code] = childrenArr;

            arr.push(obj);

        });

        $('.salesAreasCollect').val(JSON.stringify(arr));

    }

    //checkbox change
    $body.on('change', '.salesAreasCheckbox input[type="checkbox"]', function(event) {

        var $this, seat, status, text, code;

        $this = $(this);
        seat = $this.parents('.level').index(); //level
        status = $this.is(':checked'); //whether checked
        code = $this.val();
        text = $this.attr('toponymy');

        if (status) { //增加

            if ($levelOutput.find('dd[code="' + code + '"]').length) return false;

            if (seat) { //二级收集处

                var oneLevelCode, oneLevelText, $target;

                oneLevelCode = $this.parents('.level').attr('code');
                oneLevelText = $salesAreasCheckbox.find('[value="' + oneLevelCode + '"]').attr('toponymy');
                $target = $levelOutput.eq(seat).find('[code="' + oneLevelCode + '"]');

                if ($target.length) { //存在该大项

                    $levelOutput.eq(seat).find('dl[code="' + oneLevelCode + '"]').append('<dd class="fn-left fn-mr20" code="' + code + '">' + text + '</dd>');

                } else {

                    $levelOutput.eq(seat).append(
                        [
                            '<dl class="fn-clear" code="' + oneLevelCode + '">',
                            '<dt class="fn-left">' + oneLevelText + '：</dt>',
                            '<dd class="fn-left fn-mr20" code="' + code + '">' + text + '</dd>',
                            '</dl>'
                        ].join('')
                    );

                }


            } else { //一级收集处

                var $salesAreasCheckboxLevel1 = $salesAreasCheckbox.find('.level:eq(1)');

                if ($salesAreasCheckboxLevel1.attr('code') == code) {

                    $salesAreasCheckboxLevel1.find('ul').html('');

                }

                $levelOutput.eq(1).find('[code="' + code + '"]').remove();
                $levelOutput.eq(seat).find('dl').append('<dd class="fn-left fn-mr20" code="' + code + '">' + text + '</dd>');

            }

        } else { //移除

            var $salesAreasOutputCode;

            $salesAreasOutputCode = $salesAreasOutput.find('dd[code="' + code + '"]');

            if (seat && $levelOutput.eq(seat).find('dd[code]').length == 1) {

                $salesAreasOutputCode.parents('[code]dl').remove();

            }

            $salesAreasOutputCode.remove();

        }

        salesAreasCollect();

    });

    $body.on('click', '.salesAreasCheckbox a', function(event) {

        var $this, $checkbox, junior, code, Html, arr, $twoLevelOutput, judge;

        $this = $(this);
        $checkbox = $this.siblings('input[type="checkbox"]');
        junior = $checkbox.attr('junior');
        code = $checkbox.val();
        Html = '';
        $twoLevelOutput = $salesAreasOutput.find('.levelOutput:eq(1) dl[code="' + code + '"]');

        if (!!!junior) return false;

        arr = junior.split(';');

        for (var i in arr) {

            judge = !$twoLevelOutput.length || ($twoLevelOutput.length && $twoLevelOutput.find('dd[code="' + arr[i].split(',')[1] + '"]').length);

            Html += '<li class="fn-left fn-mr30"><input class="fn-mr5" value="' + arr[i].split(',')[1] + '" toponymy="' + arr[i].split(',')[0] + '" type="checkbox" ' + (judge ? "checked" : "") + '>' + arr[i].split(',')[0] + '</li>'

        }

        $salesAreasCheckbox.find('.level:eq(1)').attr('code', code)
            .find('ul').html(Html)
            .find('input[type="checkbox"]').change();
        $checkbox.attr('checked', false).change();

    });

    $body.on('click', '.packBtn', function(event) { //收起
        var _this = $(this);
        _this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
        _this.parent().prev().slideToggle("2",function () {
            var salesAreasCheckbox = _this.parent().siblings(".salesAreasCheckbox");
            if(!salesAreasCheckbox.is(":hidden")){
                _this.parents(".salesAreasBox").animate({"width":"100%"},200);
            }else {
                _this.parents(".salesAreasBox").animate({"width":"30%"},200);
            }
        });
    });
    //地区选择 END

    //设置费率表
    $body.on('click', '.setingRatio', function(event) { //设置费用比例<option value="0">请选择</option><option value="0">请选择</option>
        var $this, data, Html, val;
        var manOptions = ['<td class="fn-p-reb trSexMan"><select class="ui-select fn-w100 paymentPeriodSelect">','</select><input type="text" class="fn-validate text paymentPeriodInput fnMakeNumber fn-hide" disabled="true"><span class="recordDelete rowDelete">━</span></td>'];
        var womanOptions = ['<td class="fn-p-reb trSexWoman"><select class="ui-select fn-w100 paymentPeriodSelect">','</select><input type="text" class="fn-validate text paymentPeriodInput fnMakeNumber fn-hide" disabled="true"><span class="recordDelete rowDelete">━</span></td>'];


        var $this = $(this);
        var val = $this.siblings('.setingRatioArr').val();
        var val2 = $this.siblings('.unitPrice').val();
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
            top:"100px",
            simple: true,
            closeEle: '.close'
        });
        $("#unitPrice").val(val2);
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

                domPostion.after('<td class="fn-text-c ' + sex + '"><input class="text fnMax minZero" type="text" name=""></td>')

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
                rowHtml.splice(-1, 0, '<td class="fn-text-c ' + tdSex + '"><input class="text fnMax minZero" type="text fnFloatNumber" name=""></td>');

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
            $this.siblings('.setingRatioArr').val(JSON.stringify(newItems));
            $this.siblings('.unitPrice').val($("#unitPrice").val()).blur().removeClass("error-tip");
            // $this.siblings('.setingRatioArr').val(JSON.stringify(newItems).replace(/\"/g,"'"));

            $this.siblings('#unitPrice-error').remove();
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

    //编辑
    if ($('[name="typeId"]').val() > 0) {

        //是否为寿险
        var isLifeInsurance = $('[name*=isLifeInsurance]').val();
        var insurancePeriodType = $('[name*=insurancePeriodType]').val();
        var isQuota = $('[name*=isQuota]:checked').val();

        //保险期限类型
        if (isLifeInsurance == "YES") {
              $(".lifeInsuranceYes").removeClass("fn-hide").find("input").removeAttr("disabled");
              $(".lifeInsuranceNo").addClass("fn-hide").find("input").attr("disabled",true);
              $(".lifeInsurance_payType").removeClass("fn-hide").find("input").removeAttr("disabled");
              insurancePeriodType = $("input:radio[name='insurancePeriodType']:checked").val();
              if (insurancePeriodType == "终身") {
                   $(".year").attr("disabled",true);
                   $(".age").attr("disabled",true);
              } else if (insurancePeriodType == "年"){
                   $(".year").removeAttr("disabled");
                   $(".age").val("");
                   $(".age").attr("disabled",true);
              } else if (insurancePeriodType == "岁"){
                   $(".year").val("");
                   $(".year").attr("disabled",true);
                   $(".age").removeAttr("disabled");
              }
              var payType = $('input[name="payType"]:checked').val();
              if (payType == 19 || payType == 18) {
              	$(".periodRate").addClass('fn-hide').find("input").attr("disabled",true);
              } else {
              	$(".periodRate"). removeClass('fn-hide').find("input").removeAttr("disabled");
              }
        } else {
              $(".lifeInsuranceYes").addClass("fn-hide").find("input").attr("disabled",true);
              //非寿险产品没有缴费类型 和 缴费年限
              $(".lifeInsurance_payPeriod,.lifeInsurance_payType").addClass("fn-hide").find("input").attr("disabled", true);
              $.each($('.lifeInsuranceNo'),function (i,el) {
                  !$(el).hasClass('isPaytype') && $(el).removeClass("fn-hide").find("input").removeAttr("disabled");
              })
              if (isQuota == "NO") {
                   $(".lifeInsuranceLevel").addClass("fn-hide").find("input").val("").attr("disabled",true);
              } else {
                   $(".lifeInsuranceLevel").removeClass("fn-hide").find("input").removeAttr("disabled");
              }
        }
        
   } else {
	  var isLifeInsurance = $("#isLifeInsurance").val();
	  if (!isLifeInsurance) return;
	    if (isLifeInsurance == "YES") {
            $(".lifeInsuranceYes").removeClass("fn-hide").find("input").removeAttr("disabled");
            $(".lifeInsuranceNo").addClass("fn-hide").find("input").attr("disabled",true);
        } else {
            $(".lifeInsuranceYes").addClass("fn-hide").find("input").attr("disabled",true);
            $.each($('.lifeInsuranceNo'),function (i,el) {
                !$(el).hasClass('isPaytype') && $(el).removeClass("fn-hide").find("input").removeAttr("disabled");
            })
        }
   }


});