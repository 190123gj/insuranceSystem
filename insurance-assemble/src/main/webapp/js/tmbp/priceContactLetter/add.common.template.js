define(function(require, exports, module) {
    require('tmbp/priceContactLetter/addPrice');
    var auditProject = require("tmbp/auditProject");

    var _auditProject = new auditProject();
    _auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();


    //客户协议
	require('Y-msg');
	// require('input.limit');
	//require('tmbp/upAttachModifyNew');
    require('tmbp/submit.common');
    var selectType = require('tmbp/selectType');
    var getList = require('zyw/getList');

    var $body = $('body');
    var $form = $('#form');

    //---START-----数据还原-----START---
    $.each($('.inquiryTemplate'),function (index,box) {

        // console.log('index=',index);
        // console.log(box);
        var $box = $(box);
        var templateName = $box.attr('templatename');
        // console.log(templateName);
        if(!templateName) return;
        var insurancePlanJosn = $box.find('.insurancePlanJosn').html();
        //TMP_INSURANCE_PLAN_RECOMED_CAICAN
        //TMP_INSURANCE_PLAN_RECOMED_CARINFO
        //TMP_MAIN_INSURANCET_CAICAN 主险
        //TMP_MAIN_INSURANCET_CARINFO 主险
        //TMP_EXTRA_INSURANCET_CAICAN //附加险
        //TMP_EXTRA_INSURANCET_CARINFO
        var TMP_INSURANCE_PLAN_RECOMED_CAICAN = $('#TMP_INSURANCE_PLAN_RECOMED_' + templateName).html();
        var TMP_MAIN_INSURANCET = $('#TMP_MAIN_INSURANCET_' + templateName).html();
        var TMP_EXTRA_INSURANCET_CAICAN = $('#TMP_EXTRA_INSURANCET_' + templateName).html();

        // var data = [
        //     {
        //         "catalogId": 0,
        //         "deductibleRate": 1,
        //         "expectPremiumAmount": 1,
        //         "id": 74,
        //         "insuranceAmount": 1,
        //         "insuranceWay": "1",
        //         "intentionBrokerRate": 1,
        //         "letterSchemeId": 31,
        //         "limitAmount": 0,
        //         "nonDeductible": 0,
        //         "parentCatalogId": 17,
        //         "parentCatalogName": "博恩财产",
        //         "premiumRate": 1,
        //         "propertyCategory": "1",
        //         "responsibilityScope": "保险责任2",
        //         "target": "1",
        //         "version": 0
        //     },
        //     {
        //         "catalogId": 0,
        //         "deductibleRate": 1,
        //         "expectPremiumAmount": 1,
        //         "id": 74,
        //         "insuranceAmount": 1,
        //         "insuranceWay": "1",
        //         "intentionBrokerRate": 1,
        //         "letterSchemeId": 31,
        //         "limitAmount": 0,
        //         "nonDeductible": 0,
        //         "parentCatalogId": 17,
        //         "parentCatalogName": "博恩财产",
        //         "premiumRate": 1,
        //         "propertyCategory": "1",
        //         "responsibilityScope": "保险责任2",
        //         "target": "1",
        //         "version": 0
        //     },
        //     {
        //         "catalogId": 0,
        //         "deductibleRate": 1,
        //         "expectPremiumAmount": 1,
        //         "id": 75,
        //         "insuranceAmount": 1,
        //         "insuranceWay": "1",
        //         "intentionBrokerRate": 1,
        //         "letterSchemeId": 31,
        //         "limitAmount": 0,
        //         "nonDeductible": 0,
        //         "parentCatalogId": 17,
        //         "parentCatalogName": "博恩财产",
        //         "premiumRate": 1,
        //         "propertyCategory": "1",
        //         "responsibilityScope": "保险责任1",
        //         "target": "1",
        //         "version": 0
        //     },
        //     {
        //         "catalogId": 0,
        //         "deductibleRate": 1,
        //         "expectPremiumAmount": 1,
        //         "id": 76,
        //         "insuranceAmount": 1,
        //         "insuranceWay": "1",
        //         "intentionBrokerRate": 1,
        //         "letterSchemeId": 31,
        //         "limitAmount": 0,
        //         "nonDeductible": 0,
        //         "parentCatalogId": 17,
        //         "parentCatalogName": "博恩财产",
        //         "premiumRate": 1,
        //         "propertyCategory": "1",
        //         "responsibilityScope": "保险责任5",
        //         "target": "1",
        //         "version": 0
        //     }
        // ];
        //  console.log(!!insurancePlanJosn);
        var lists = !!insurancePlanJosn ? JSON.parse(insurancePlanJosn) : [];
         // console.log(lists)
        //数据组装
        var datas = {
            options:!lists.extraKinds ? [] : lists.extraKinds,
            lists:!lists.detail ? [] : lists.detail
        };
        $.each(datas.lists,function (i,items) {
            //去掉默认的数据
            if(i == 0) $box.find('.insurancePlanBox').find('.itemLine').remove();
            //得到下拉选择
            var $template = '';
            var rowspanLen = 0;
            $.each(items,function (j,item) {
                var IS_EXTRA = (item.catalogId > 0 && !!item.catalogId);//判断是否是附加险
                if(!IS_EXTRA) rowspanLen ++;
                $template = IS_EXTRA ? $(TMP_EXTRA_INSURANCET_CAICAN) : $(TMP_MAIN_INSURANCET);
                var options = datas.options[item.parentCatalogId];
                //模板初始化
                if(j == 0){
                    var $template1 = $(TMP_INSURANCE_PLAN_RECOMED_CAICAN);
                    $template.remove('rowspanTr');
                    $template.find('td').not('.fn-hide').addClass('oldList');
                    // console.log($template,1111);
                    $template.find('.fn-hide').remove();
                    var $template2 = $($template.html());

                    // console.log($template2,22222);
                    $template1.find('.contentReplace').addClass('fn-hide').after($template2);
                    $template = $template1;
                    $template.find('.rowspanTr').attr('rowspan',items.length);
                    $template.attr('targetindex',i);
                    // $isIinquiryPlanBodyTr.eq(0).addClass('fn-hide');
                    // $isIinquiryPlanBodyTr.removeClass('fn-hide');
                }else {
                    $template.attr('targetchildindex',i).find('td').not('.fn-hide').addClass('oldList');
                };
                //初始化一个下拉选择
                if($template.find('.selectFnBox1').length > 0) creatSelectType($template.find('.selectFnBox1'));
                if(IS_EXTRA) $template.addClass('isExtraList');
                //用于生成附加险下拉选项的数据
                var selectOptions = [];
                if(options && options.length > 0) {
                    $.each(options,function (exIndex,exList) {
                        selectOptions.push('<option value="' + exList.catalogId + '">' + exList.catalogName + '</option>');
                    });
                    // console.log(selectOptions)
                    $template.find('.addInsurance').attr('selectoptions',selectOptions);
                    if(IS_EXTRA) $template.find('[name*=catalogId]').append(selectOptions);
                    // selectOptions.push(extraKindsLists);
                };

                //赋值
                $.each(item,function (key,val) {
                    var $input = $template.find('[name*=' + key + ']');

                    //如果不存在则创建一个
                    if($input.length == 0) {
                        $template.find('td').eq(0).append('<input type="hidden" name="' + key + '" value="' + val + '">');
                    }else {
                        var TAG_TYPE = $input[0].tagName;
                        var INPUT_TYPE = $input.attr('type');
                        // console.log(key + j + ': ' + $input.attr('name'));

                        $template.find('.key-' + key + '').html(val);

                        if(TAG_TYPE === 'INPUT'){
                            if(INPUT_TYPE == 'text' || INPUT_TYPE == 'hidden'){
                                $input.val(val);
                            }else if(INPUT_TYPE == 'radio' || INPUT_TYPE == 'hidden'){
                                $template.find('[name*=' + key + '][value=' + val + ']').prop('checked');
                            }else {
                                return;
                            }
                        }else if(TAG_TYPE === 'SELECT'){
                            $input.val(val);
                            $input.find('option:selected').addClass('selectedOption');
                        }else {
                            return;
                        }
                    }

                });
                $box.find('.insurancePlanBox').append($template);
            });
            $box.find('.insurancePlanBox .isIinquiryPlanBodyTd.rowspanTr').attr('rowspan', rowspanLen);

        });
    });
    //---END-----数据还原-----END---

    //新增一行数据
    $body.on('click','.addLine',function () {//新增一行

        var $self = $(this);
        var templateId = $self.attr('templateid');
        var templateList = $self.attr('templatelist');
        if(!templateId) return;
        var maxItems = $self.attr('maxitems') || false;
        var $itemBox = $self.parents('.itemBox').find('.itemsList');
        var itemBoxListLength = $itemBox.find('.itemLine').length;
        var toCopyTr = itemBoxListLength > 0 && templateList == 'tr';
        var $template = toCopyTr ? $itemBox.find('.itemLine').eq(0).clone(true) : $($('#' + templateId).html());

        if(toCopyTr) $template.find('[type=text],[type=hidden],select').not('.notEmptyVal').val('');

        $template.find('.fn-validate').removeClass('ignore');//移除验证

        if(itemBoxListLength - 1 >= maxItems && !!maxItems) {
            Y.alert('提示','不能超过' + maxItems + '条');
            return;
        };
        $template.attr('targetindex',itemBoxListLength);
        $template.find('.itemListIndex').val(itemBoxListLength + 1);
        $itemBox.append($template);
        if($itemBox.find('tr:last').find('.selectFnBox1').length > 0) {//如果有selectFnBox就初始化
            creatSelectType($itemBox.find('tr:last').find('.selectFnBox1'));
        };
        // diyName($self.parents('.diyNameBox'));
    });

    //新增保险公司
    $body.on('click','[templateid*="TMP_XUNJIA_INSURANCET_COMPANY"]',function () {
        var $this = $(this);
        var $thisTr = $this.parents(".itemBox").find("table").find("tr.itemLine");
        var trLength = $thisTr.length;
        var firstTrParentsErrorBoxValue = $thisTr.eq(0).find(".parentsErrorBox").html();
        for(var i=1;i<trLength;i++){
            $thisTr.eq(i).find(".parentsErrorBox").html(firstTrParentsErrorBoxValue);
        }
    });
    //删除一行
    $body.on('click','.deleteLine',function () {//删除一行

        var $self = $(this);
        var $selfItem = $self.parents('.itemLine');
        var $itemBox = $self.parents('.itemsList');
        var residueItems = (!$selfItem.attr('targetindex') && $selfItem.attr('targetindex') != 0) ? $itemBox.find('.itemLine').length : $itemBox.find('[targetindex]').length;
        var minLength = $self.attr('minlength') || 1;
        var targetIndex = $self.parents('.itemLine').attr('targetindex');
        // console.log(minLength);
        if(residueItems <= minLength) {
            Y.alert('提示','至少保留一条！');
            return;
        };
        $selfItem.remove();
        if(!!targetIndex) {
            $itemBox.find('[targetchildindex=' + targetIndex + ']').remove();
            $itemBox.find('[targetindex]').each(function (index,ele) {
                var thisTargetindex = $(this).attr('targetindex');
                $itemBox.find('[targetchildindex=' + thisTargetindex + ']').attr('targetchildindex',index);
                $(this).attr('targetindex',index);
            })
        };

        //更新下面'本次询价保险公司'的险种
        var catalogId = $self.parents('tr').find('.selectedId').val();
        var catalogName = $self.parents('tr').find('.selectedName').val();
        var templateCode = $self.parents('.inquiryTemplate').attr('templatecode');
        var $thisTemplate = $('.priceCompanyConent_' + templateCode).length == 0 ? $('.priceCompanyConent [parentdiyname]').eq(0) : $('.priceCompanyConent_' + templateCode);
        if(!!catalogId){
            //更新下面'本次询价保险公司'的险种
            var $catologIds_common = $thisTemplate.find('.catologIds'); //下面'本次询价保险公司'已选择的险种
            var $catologNames_common = $thisTemplate.find('.catalogNames'); //下面'本次询价保险公司'已选择的险种
            var catologIds_common = $catologIds_common.eq(0).val();
            var catologNames_common = $catologNames_common.eq(0).val();
            catologIds_common = catologIds_common.split(',');
            catologNames_common = catologNames_common.split(',');
            var index = catologIds_common.indexOf(catalogId);
            catologIds_common.splice(index,1);
            catologNames_common.splice(index,1);
            $catologIds_common.val(catologIds_common);
            $catologNames_common.val(catologNames_common);
            $thisTemplate.find('.mainInsurance_add .insuranceName_add_' + catalogId).remove();
        };
        $.each($thisTemplate.find('.askCompanyOrders [diyname]'),function (i,el) {
            getCompanyList($(el));
        });
        // diyName($self.parents('.diyNameBox'));
        // diyName($thisTemplate.find('.askCompanyOrders .diyNameBox'));
    });

    //删除一行数据
    $body.on('click','.delTrData',function () {
        var $self = $(this);
        var url = $self.attr('opthref');

        Y.alert('提示','是否确认删除？',function () {
            $.ajax({
                url:url,
                success:function (res) {
                    if(res.success){
                        Y.alert('提示',res.message,function () {
                            window.location.href = window.location.href;
                        });
                    }else {
                        Y.alert('提示',res.message);
                    }
                }
            })
        })
    });

    //新增附加险
    $body.on('click','.addInsurance',function () {

        var $self = $(this);
        var templateId = $self.attr('templateid');
        // var canChoose = $self.hasClass('canChoose');
        var selectedId = $self.siblings('.selectFnBox1').find('.selectedId').val()
        if(!selectedId) {
            Y.alert('提示','请先选择险种！');
            return;
        }
        if(!templateId) return;
        var $selfItem = $self.parents('.itemLine');
        var $itemBox = $self.parents('.itemsList');
        var targetIndex = $selfItem.attr('targetindex');
        var $template = $($('#' + templateId).html());
        var $template_xtabel = $('#TMP_EXTRA_INSURANCET_XTABLE').html();
        var $targetchild = $itemBox.find('[targetchildindex=' + targetIndex + ']');
        var targetchildLength = $itemBox.find('[targetchildindex=' + targetIndex + ']').length;
        var rowSpan = !$self.parents('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan') ? 2 : +$self.parents('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan') + 1;
        $template.find('[name*=catalogId]').append($self.attr('selectoptions'));
        $.each($selfItem.find('.syncsVal'),function () {//每次新增要同步数据
            var $this = $(this);
            if(!$this.val()) return;
            var name = $this.attr('name').split('.');
            name = name[name.length - 1];
            var $targetInput = $template.find('[name*=' + name + ']').length == 0 ? $template.find('.' + name + 'SyncsVal') : $template.find('[name*=' + name + ']');
            $targetInput.attr('value',$this.val()).blur();
        });
        $template.addClass('isExtraList').attr('targetchildindex',targetIndex).find('.fn-validate').removeClass('ignore');//移除验证
        if(targetchildLength == 0){
            // if($selfItem.find('.contentReplace').length > 0){
            //     $selfItem.find('.newTdToTd').after($template_xtabel);
            //     $template.find('td.fn-hide').remove();
            //     $selfItem.find('.contentReplace').after($template.html()).remove();
            //     rowSpan = 1;
            // }else{
                $template.append($template_xtabel)
                $selfItem.after($template);
            // }

        }else {
            $template.append($template_xtabel)
            $targetchild.eq(targetchildLength - 1).after($template)
        };
        $selfItem.find('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan',rowSpan);
        // diyName($self.parents('.diyNameBox'));
        // $selfItem.find('.syncsVal').blur();
    });

    //删除附加险
    $body.on('click','.deleteInsuranceLine',function () {
        var $self = $(this);
        var $selfItem = $self.parents('.itemLine');
        var $itemBox = $self.parents('.itemsList');
        var targetIndex = $selfItem.attr('targetchildindex');
        var $rowspanTr = $itemBox.find('[targetindex=' + targetIndex + '] td.rowspanTr').not('.notAllUpdateRowspan');
        var tdColspan = $self.attr('tdcolspan');
        if(targetIndex >= 0) {
            $selfItem.remove();
        }else {
            $selfItem.find('td').not('.rowspanTr').remove();
            $selfItem.find('td.selectFnBox1Td').after('<td style="padding: 0;" colspan="'+ tdColspan +'" class="contentReplace"></td>');
        };
        $rowspanTr.attr('rowspan',+$rowspanTr.eq(0).attr('rowspan') - 1);
        // diyName($self.parents('.diyNameBox'));
    });

    //把值放到各行的隐藏域
    $body.on('blur','.syncsVal',function () {
        var $this = $(this);
        var val = $this.val();
        var targetindex = $this.parents('tr').attr('targetindex');
        var name = $this.attr('name').split('.');
        name = name[name.length - 1];
        var $tr = $this.parents('table').find('tr[targetchildindex=' + targetindex + ']');
        var $targetInput = $tr.find('[name*=' + name + ']').length == 0 ? $tr.find('.' + name + 'SyncsVal') : $tr.find('[name*=' + name + ']');
        $targetInput.attr('value',val).blur();
        // console.log($this.val());
    });
    $body.on('click','.submitBtn',function () {
        var $this = $(this);

        $(".insurancePlanJosn").html("");
        var DRAFT = $this.hasClass('DRAFT');//暂存
        var m_companyName = $(".mainInsurance_add .insuranceName_add");
        var p_companyName = $(".protocolFees_add .insuranceName_add");

        var parkingNumVal = $(".parkingNum").val();
        var indoorVal = $(".indoor").val();
        var outdoorVal = $(".outdoor").val();
        var tempdoorVal = $(".tempdoor").val();
        var motodoorVal = $(".motodoor").val();

        var elevatorVal = $(".elevator").val();
        var straightVal = $(".straight").val();
        var escalatorVal = $(".escalator").val();
        var swimPoolVal = $(".swimPool").val();
        var straightPoolVal = $(".straightPool").val();
        var outSourcePoolVal = $(".outSourcePool").val();

        $(".parkingNum").parent().find("span").remove();
        if(!DRAFT && parkingNumVal != undefined && (parseInt(parkingNumVal) != parseInt(indoorVal)+parseInt(outdoorVal)+parseInt(tempdoorVal)+parseInt(motodoorVal))){
            $(".parkingNum").after("<span style='color: red'>车位数=室内+室外+临时+摩托车位</span>")
        }else {
            $(".parkingNum").parent().find("span").remove();
        };
        $(".elevator").parent().find("span").remove();
        if(!DRAFT && elevatorVal != undefined && (parseInt(elevatorVal) != parseInt(straightVal)+parseInt(escalatorVal))){
            $(".elevator").after("<span style='color: red'>电梯数=直行数+扶梯数</span>")
        }else {
            $(".elevator").parent().find("span").remove();
        };
        $(".swimPool").parent().find("span").remove();
        if(!DRAFT && swimPoolVal != undefined && (parseInt(swimPoolVal) != parseInt(straightPoolVal)+parseInt(outSourcePoolVal))){
            $(".swimPool").after("<span style='color: red'>泳池数=直管数+外包数</span>")
        }else {
            $(".swimPool").parent().find("span").remove();
        }

    });
    $body.on("blur","[name*='premiumRate']",function () {//[name*='personNum']
        var premiumRate = $(this);
        var expectPremiumAmount = premiumRate.parents("tr").find(".expectPremiumAmount");

        var personNum = $("[name*='personNum']");
        var _val = parseInt(premiumRate.val() * personNum.val());
        expectPremiumAmount.val(_val);
    });
    $body.on("blur",".lastTime",function () {
       var $this = $(this);
       var $firstTime = $this.siblings(".firstTime");
       var $allDays = $this.siblings(".allDays");
        var days = DateDiff($this.val(),$firstTime.val());
        if(days<=0){
            Y.alert("提示","结束日期必须晚于开始日期");
            $this.val("");
            $allDays.val("");
        }else {
            $allDays.val(days);
        }
    });
    $body.on("blur",".firstTime",function () {
        var $this = $(this);
        var $lastTime = $this.siblings(".lastTime");
        var $allDays = $this.siblings(".allDays");
        var days = DateDiff($lastTime.val(),$this.val());
        if(days<=0){
            Y.alert("提示","结束日期必须晚于开始日期");
            $this.val("");
            $allDays.val("");
        }else {
            $allDays.val(days);
        }

    });
    function DateDiff(d1,d2){
        var day = 24 * 60 * 60 *1000;
        try{
            var dateArr = d1.split("-");
            var checkDate = new Date();
            checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);
            var checkTime = checkDate.getTime();

            var dateArr2 = d2.split("-");
            var checkDate2 = new Date();
            checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);
            var checkTime2 = checkDate2.getTime();

            var cha = (checkTime - checkTime2)/day;
            return cha+1;
        }catch(e){
            return false;
        }
    }

    //合并'本次询价保险公司'中的意向经纪费比例（%）
    $body.on('blur','.singleInsuranceRate',function () {
        var $self = $(this);
        var $td = $self.parents('td');
        var _valArry = [];
        $.each($td.find('.singleInsuranceRate'),function (i,el) {
            var _thisVal = $(el).val();
            if(!!_thisVal) _valArry.push(_thisVal);
        });
        $td.find('.insuranceRates').val(_valArry);
    });

    //点击选择公司
    var $companyTr;
    $body.on('click','.fnToChooseCompanyName', function () {
        var $self = $(this);
        var arry = [];
        $companyTr = $self.parents('tr');
        var removeCompanys = $self.parents("table").find("[name*='companyUserId']");
        $.each(removeCompanys,function (i,el) {
            if(!!$(el).val()) arry.push($(el).val());
        });
        var param = (arry.length > 0) ? arry : '';

        chooseCompanys.ajaxUrl = '/baseDataLoad/customer.json?removeCompanys='+param;

        $self.siblings('.fnToChooseCompanyNameHiddenBtn').click();
    });

    //清除选择
    $body.on('click','.fnToClear', function () {
        var $chooseListBox = $(this).parents('.clearChooseListBox');
        $chooseListBox.find('.needToClearInput').val('').blur();
    });

    //初始化公司选择
    var chooseCompanys = (new getList()).init({
        title: '选择保险公司',
        ajaxUrl: '/baseDataLoad/customer.json',
        btn: '.fnToChooseCompanyNameHiddenBtn',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{item.name}}</td>',
                '        <td title="{{item.customerType}}">{{item.customerType}}</td>',
                '        <td title="{{item.certType}}">{{item.certType}}</td>',
                '        <td title="{{item.certNo}}">{{item.certNo}}</td>',
                '<td><a class="choose" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['客户名称：',
                '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
               '<input class="ui-text fn-w100" type="hidden" name="customerType" value="INSURANCE_INSTITUTIONS">&nbsp;&nbsp;&nbsp;&nbsp;',
                '<input class="ui-text fn-w100" type="hidden" name="companys" value="'+$("#companys").val()+'">&nbsp;&nbsp;&nbsp;&nbsp;',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>客户名称</th>',
                '<th>客户类型</th>',
                '<th>证件类型</th>',
                '<th>证件号码</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {

            var companyUserId = $a.attr("userId");
            var companyUserName = $a.attr("name");

            $companyTr.find(".companyUserName").val(companyUserName).blur();
            $companyTr.find(".companyUserId").val(companyUserId).blur();
            getCompanyList($companyTr,companyUserId);
            //-----------------------
            var intentionBrokerRate = $("[name*='intentionBrokerRate']");
            var singleInsuranceRate = $companyTr.find(".singleInsuranceRate");

            var str = [];
            for(var i = 0;i<intentionBrokerRate.length;i++){
                str.push(intentionBrokerRate.eq(i).val());
                singleInsuranceRate.eq(i).val(intentionBrokerRate.eq(i).val());
            }
            $(".insuranceRates").val(str.join());
            $(".insuranceRates").parents("table").find(".error-tip").hide();
        }
    });

    //添加询价模板
    $body.on('click','.addPriceTemplate',function () {

        var templateId = $(this).attr('templateid');
        if(!templateId) return;
        var _content = $('#' + templateId).html();
        $body.Y('Window', {
            content: _content,
            modal: true,
            key: 'mod',
            simple: true,
            closeEle: '.closeBtn'
        });
    });
    $body.on("change",".insurancePlanBox",function () {
        var deductibleRate = $("[name*='deductibleRate']");
        var premiumRate = $("[name*='premiumRate']");
        var intentionBrokerRate = $("[name*='intentionBrokerRate']");
        var singleInsuranceRate = $(".singleInsuranceRate");
        verifyVal(deductibleRate);
        verifyVal(premiumRate);
        verifyVal(intentionBrokerRate);
        // singleInsuranceRate.val(intentionBrokerRate.val());

    });
    var verifyVal = function(key) {
        var $this = key;
        for(var i = 0;i<$this.length;i++){
            var inputVal = $this.eq(i).val();
            $this.eq(i).parent().find("span").remove();
            if(isNaN(inputVal)||(Number(inputVal)<0 || Number(inputVal)>100)){
                $this.eq(i).parent().append("<span style='color: red;'>请输入0--100的数值</span>")
            }
        }
    };

    $body.on("blur",".infoDate",function () {
        var $this = $(this);
        if($this.val() != ''){
            $this.removeClass("laydate-icon");
        }else {
            $this.addClass("laydate-icon");
        }
    });
    //询价模板选择确认
    $body.on('click','.okBtn',function () {
        var $box = $(this).parents('.m-modal-body');
        var $closeBtn = $box.find('.closeBtn');
        var templateId = $box.find('select').val();
        var isRepeatId = $('.priceTemplateConent').find('.' + templateId).length > 0;
        var _templateHtml = $('#' + templateId).html();
        var $template = $(_templateHtml);
        var text = $box.find('select option:selected').html();
        //TMP_PRICECOMPANY
        if(!_templateHtml) {
            Y.alert('提示','询价模板不存在！');
            return;
        }
        if(isRepeatId) {
            Y.alert('提示','询价模板不能重复添加！');
            return;
        };

        var $priceCompany = $($('#TMP_PRICECOMPANY').html());
        $priceCompany.addClass('priceCompanyConent_' + templateId).find('.itemBox').before('<p style="font-weight: bold;">' + text + '</p>');
        $('.priceCompanyConent').append($priceCompany);

        $template.addClass('fn-p-reb').append('<a class="ui-btn ui-btn-fill ui-btn-danger fn-p-abs deletePriceTemplate" parentstemplate="' + templateId + '" href="javascript:void(0);" style="color: #fff;">删除当前询价模板</a>')
        creatSelectType($template.find('.selectFnBox1'));
        $('.priceTemplateConent').append($template);
        $closeBtn.click();
    });

    //询价模板删除
    $body.on('click','.deletePriceTemplate',function () {

        var templateId = $(this).attr('parentstemplate');
        $('.priceCompanyConent').find('.priceCompanyConent_' + templateId).remove();
        $(this).parents('.inquiryTemplate').remove();
    });

    //初始化下拉选择
    if($('.selectFnBox1').length > 0) {//如果有selectFnBox就初始化
        creatSelectType($('.selectFnBox1'));
    };

    //序列化name

    // function diyName($box) {
    //
    //     if(!$box) $box = $form;
    //     var $diyNameBox = $box.hasClass('diyNameBox') ? $box : $box.find('.diyNameBox');
    //     // var $parnetDiyName = $('[parentdiyname]');
    //     $.each($diyNameBox,function () {
    //         var $thisBox = $(this);
    //         var noParentDiyName = $thisBox.hasClass('noParentDiyName');
    //         var warpName = '';
    //         if(!noParentDiyName){
    //             var $thisParentDiyName = $thisBox.parents('[parentdiyname]');
    //             if($thisParentDiyName.length > 0) {
    //                 var _indexWarp = $('[parentdiyname]').index($thisParentDiyName);
    //                 warpName = $thisParentDiyName.attr('parentdiyname');
    //                 warpName = !warpName ? '' : warpName + '[' + _indexWarp + '].';
    //             }
    //         }
    //
    //         $.each($thisBox.find('[diyname]'),function(index,ele) {
    //             // console.log(index)
    //             var $thisDiyName= $(this);
    //             var diyName = $thisDiyName.attr('diyname');
    //
    //             $.each($thisDiyName.find('[name]'),function() {
    //
    //                 var _this = $(this),
    //                     name = _this.attr('name');
    //
    //                 if (name.indexOf('.') > 0) {
    //                     name = name.substring(name.lastIndexOf('.') + 1);
    //                 };
    //
    //                 name = warpName + diyName + '[' + index + '].' + name;
    //                 // console.log(name)
    //                 _this.attr('name', name);
    //                 if(index == $thisBox.find('[diyname]').length - 1){
    //                     addRules($thisBox.find('[diyname]'));
    //                 };
    //
    //             });
    //
    //         });
    //     })
    //
    // };

    //添加验证规则

    // function addRules ($addObj) {
    //     var $validateList;
    //     if(!$addObj || $addObj.length == 0){
    //         $validateList = $('#form').find('.fn-validate');
    //     }else {
    //         var isName = !!$addObj.attr('name');
    //         if(isName){
    //             $validateList = $addObj;
    //         }else {
    //             $validateList = $addObj.find('.fn-validate');
    //         }
    //     };
    //     $.each($validateList,function (i, e) {
    //         var _this = $(this);
    //         _this.rules("remove");
    //         _this.rules('add', {
    //             required: true,
    //             messages: {
    //                 required: '必填'
    //             }
    //         });
    //     })
    // };

    //new一个下拉
    function creatSelectType($box){
        new selectType({
            selectBoxObj: $box,
            afterChoosedCallback:{
                callbackTargetCommon: function ($op) {//选择完成之后赋值，加一个新增附加险的限制条件，并请求当前险种的对应数据
                    var $selectFnBox = $op.parents('.selectFnBox1');
                    var catalogName = $op.attr('valuedata');
                    var catalogId = $op.attr('parentcatalogid');
                    var _oldCatalogId = $selectFnBox.find('.selectedId').val();//获取上次选择的险种ID，用来比对是否选择相同的险种，相同则不进行后面的操作
                    //更新下面'本次询价保险公司'的险种
                    var templateCode = $op.parents('.inquiryTemplate').attr('templatecode');
                    var $thisTemplate = $('.priceCompanyConent_' + templateCode).length == 0 ? $('.priceCompanyConent [parentdiyname]').eq(0) : $('.priceCompanyConent_' + templateCode);
                    var $catologIds_common = $thisTemplate.find('.catologIds'); //下面'本次询价保险公司'已选择的险种
                    var $catologNames_common = $thisTemplate.find('.catalogNames'); //下面'本次询价保险公司'已选择的险种
                    var catologIds_common = $catologIds_common.eq(0).val();
                    var catologNames_common = $catologNames_common.eq(0).val();

                    //险种无变化则不进行任何操作
                    if(_oldCatalogId == catalogId) return;

                    //不能和其他险种相同
                    // if(catologIds_common && catologIds_common.indexOf(catalogId) >= 0){
                    if(catologIds_common && (catologIds_common==catalogId)){
                        // console.log(catologIds_common.indexOf(catalogId));
                        Y.alert('提示','险种不能重复！');
                        $selectFnBox.find('.selectFn label').html('');
                        $selectFnBox.find('.selectedName').val('');
                        $selectFnBox.find('.selectedId').val('');
                        return;
                    };

                    //赋值
                    $selectFnBox.find('.selectedName').val(catalogName).blur();
                    $selectFnBox.find('.selectedId').val(catalogId).blur();

                    //更新下面'本次询价保险公司'的险种
                    catologIds_common = !catologIds_common ? [] : catologIds_common.split(',');
                    catologNames_common = !catologNames_common ? [] : catologNames_common.split(',');
                    if(!!_oldCatalogId) {//先移除旧的
                        catologIds_common.splice(catologIds_common.indexOf(_oldCatalogId),1);
                        catologNames_common.splice(catologIds_common.indexOf(_oldCatalogId),1);
                        $thisTemplate.find('.mainInsurance_add .insuranceName_add_' + _oldCatalogId).remove();
                    }
                    catologIds_common.push(catalogId);
                    catologNames_common.push(catalogName);
                    $catologIds_common.val(catologIds_common);
                    $catologNames_common.val(catologNames_common);
                    $thisTemplate.find('.mainInsurance_add').append('<p class="fn-mb5 insuranceName_add_' + catalogId + '"><span class="insuranceName_add fn-mr10">' + catalogName + '</span><span class="insuranceRate_add fn-mr5">比例</span><input class="ui-text fn-w40 singleInsuranceRate ignore" type="text" value=""></p>');
                    $.each($thisTemplate.find('.askCompanyOrders [diyname]'),function (i,el) {
                        getCompanyList($(el));
                    });
                    // diyName($op.parents('.diyNameBox'));
                    //请求当前险种的对应数据
                    $.ajax({
                        url:'/baseDataLoad/queryLiabilityAndExtraKind.json?catalogId=' + catalogId,
                        type:'post',
                        success:function (res) {
                            if(res.success){
                                //更新表格
                                if(Object.prototype.toString.call(res.data) === '[object Object]'){
                                    var liabilitisId = 'TMP_MAIN_INSURANCET_' + $selectFnBox.parents('.inquiryTemplate').attr('templatename');//获取保险责任对应的模板ID
                                    var _liabilitisHtml = $('#' + liabilitisId).html();

                                    var liabilitisLists = res.data.liabilitis;
                                    var extraKindsLists = res.data.extraKinds;

                                    var listArry = [],_htmlTemplate = [], selectOptions = [];

                                    //用于循环的表格数据
                                    if(liabilitisLists && liabilitisLists.length > 0) {
                                        _htmlTemplate.push(_liabilitisHtml);
                                        listArry.push(liabilitisLists);
                                    };
                                    //用于生成附加险下拉选项的数据
                                    if(extraKindsLists && extraKindsLists.length > 0) {
                                        $.each(extraKindsLists,function (exIndex,exList) {
                                            selectOptions.push('<option value="' + exList.catalogId + '">' + exList.catalogName + '</option>');
                                        });
                                        $selectFnBox.siblings('.addInsurance').attr('selectoptions',selectOptions);
                                        // selectOptions.push(extraKindsLists);
                                    };

                                    //得到一些初始化标记
                                    var $tr = $selectFnBox.parents('tr');
                                    var targetIndex = $tr.attr('targetindex');
                                    var $childrenTr = $tr.parents('table').find('[targetchildindex=' + targetIndex + ']');

                                    //选择前清空以前的数据
                                    $childrenTr.remove();
                                    $tr.find('td.oldList').remove();
                                    $tr.find('.contentReplace').removeClass('fn-hide');
                                    $tr.find('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan',1);

                                    if(listArry.length > 0 && $op.parents('td').find('.addInsurance').length == 1){//有内容时和责任范围(或附加险)的才更新
                                        //更新列表
                                        updateList(0,function () {
                                            // diyName($op.parents('diyName'));//序列化
                                        });
                                    }
                                    //更新列表
                                    function updateList(_i,cb) {
                                        if(_i == listArry.length) {
                                            if(cb && typeof cb == 'function') cb(_i);
                                            return;
                                        };
                                        $.each(listArry[_i],function (_listIndex,_listObj) {
                                            var $list = $(_htmlTemplate[_i]);//获取对应模板内容

                                            //同步标的，险种等标记为syncsVal的值
                                            $.each($tr.find('.syncsVal'),function (syncsValIndex,syncsValEl) {
                                                var _NEW_VAL = $(syncsValEl).val();
                                                var nameArry = $(syncsValEl).attr('name').split('.');
                                                var name = nameArry[nameArry.length - 1];
                                                var $targetInput = $list.find('[name*=' + name + ']').length == 0 ? $list.find('.' + name + 'SyncsVal') : $list.find('[name*=' + name + ']');
                                                if(!!_NEW_VAL) $targetInput.val(_NEW_VAL);
                                            })

                                            //赋值
                                            $.each(_listObj,function (_key,_val) {
                                                $list.find('[targetlist*=' + _key + ']').attr('value',_val);
                                            });

                                            //更新一下
                                            $childrenTr = $tr.parents('table').find('[targetchildindex=' + targetIndex + ']');

                                            //没有其他子元素时则，把第一排tr的内容替换
                                            if(_listIndex == 0 && ($childrenTr.length == 0 || !$childrenTr)){

                                                $tr.find('td.oldList').remove();
                                                $list.find('td').addClass('oldList');
                                                $list.find('.rowspanTr').remove();
                                                $tr.find('.contentReplace').after($list.html());
                                                $tr.find('.contentReplace').addClass('fn-hide');
                                            }else {
                                                var $targetTr = ($childrenTr.length > 0) ? $childrenTr.eq(0) : $tr;
                                                $list.attr('targetchildindex',targetIndex).addClass('oldList');//加一个标记表示是以前有的
                                                $tr.find('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan',(1 + parseInt($tr.find('.rowspanTr').not('.notAllUpdateRowspan').eq(0).attr('rowspan'))));
                                                $targetTr.after($list);
                                            };
                                            var _LAST = (_listIndex == listArry[_i].length - 1);
                                            if(_LAST) {
                                                _i++;
                                                updateList(_i);
                                            }

                                        })
                                    };
                                };
                            }
                        }
                    })
                }
            }
        });
    };

    //选择公司请求
    function getCompanyList($tr,id) {
        var companyUserId = !!id ? id : $tr.find('[name*=companyUserId]').val();
        if(!companyUserId) return;
        $.ajax({
            url:'/baseDataLoad/queryCompanyContactInfo.json?userId=' + companyUserId + '&catalogIds=' + $tr.find('[name*=catalogIds]').val(),
            type:'post',
            success:function (res) {
                if(res.success){
                    if(!res.data) return;
                    $.each(res.data,function (key,val) {
                        if(key == 'charge'){
                            var chargeitems = JSON.parse(val)[0];
                            var _html = '';
                            var _protocolFees = [];
                            if(!chargeitems || !chargeitems.data) return;
                            $.each(chargeitems.data,function (i,item) {
                                var name = $tr.find('.insuranceName_add_' + item.catalogId + ' .insuranceName_add').html() || '';
                                _protocolFees.push(item.protocolFee);
                                _html += '<p class="fn-mb5 insuranceName_add_' + item.catalogId + '"><span class="insuranceName_add fn-mr10">' + name + '</span><span class="insuranceRate_add fn-mr5">比例</span>' + item.protocolFee + '%</p>'
                            });
                            $tr.find('.protocolFees_add').html(_html);
                            $tr.find('.protocolFees').val(_protocolFees);
                        }else {
                            $tr.find('[name*=' + key + ']').val(val);
                        }

                    })
                }
            }
        });
    }
});