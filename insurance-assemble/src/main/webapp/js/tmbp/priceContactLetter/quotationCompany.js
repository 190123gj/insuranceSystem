define(function(require, exports, module) {
    require('tmbp/priceContactLetter/add.common.template');

    var hintPopup = require('zyw/hintPopup');
    var popupWindow = require('zyw/popupWindow');
    var validateInit = require('tmbp/validate.common');

    var $body = $('body');

    //查看编辑报价
    $body.on('click','.seeEditQuotation', function () {
        var $self = $(this);
        var $box = $self.parents('[parentdiyname]');
        var templateId = $box.attr('targetmod');
        var fnUpAttachValHiddenData = $box.find('.fnUpAttachValHiddenData').html();
        var dataString = $self.parents('tr').find('.askCompanyOrdersData').val();
        var dataJson = !dataString ? {lists:[],others:{}} : JSON.parse(dataString);
        var targetIndex = $self.parents('table').find('tr').not(':first').index($self.parents('tr'));
        var targetMod = $self.parents('.priceCompanyConent [parentdiyname]').attr('targetmod');
        var $quotationCompany = $($('#TMP_QUOTATION').html());
        var $template='';
        var newArrOne = [];
        var newArrTow = [];
        var newArrJson = [];

        console.log(dataJson.lists);
        $.each(dataJson.lists,function(_key,_val){
            newArrOne.push(_val.productName);
        });
        newArrOne.forEach(function(val,i,arr){
            if(arr.indexOf(val) === i){
                newArrTow.push(val);
            }
        });

        for(var i = 0;i < newArrTow.length;i++){
            newArrJson[i] = [];
            for(var j = 0; j < dataJson.lists.length;j++){
                if(dataJson.lists[j].productName == newArrTow[i]){
                    newArrJson[i].push(dataJson.lists[j]);
                }
            }
        }
        // $.each(newArrJson,function (index,list) {
        //     // console.log(index,list);
        // });

        if(!templateId) {
            $template = $('.inquiryTemplate').eq(0).find('.pricePlanAreaBox table').clone(true);
        }else {
            $template = $('.inquiryTemplate.' + templateId).find('.pricePlanAreaBox table').clone(true);
        };
        $template.find('.icon-select-down').remove();
        $template.find('.selectFnBox1').width('auto');
        // $template.find('.addInsurance').remove();
        // $template.find('[targetindex] .intentionBrokerRate').eq(1).parents('td').remove();
        $template.find('[targetindex] .intentionBrokerRate').eq(0).parents('td').removeClass('fn-hide');
        // $template.find('tr.itemLine').not(':first').find('td.isIinquiryPlanBodyTd.rowspanTr').addClass('fn-hide');
        // var a = $template.find('tr.itemLine').find('td.isIinquiryPlanBodyTd.rowspanTr.notAllUpdateRowspan ');
        // console.log(a.length);

        var targetindexLength = $template.find("tr.itemLine[targetindex]").length;

        if(targetindexLength-2<0){
            $template.find('tr.itemLine').not(':first').find('td.isIinquiryPlanBodyTd.rowspanTr').addClass('fn-hide');
        }
        $template.find('tr.itemLine').find('td.rowspanTr.selectFnBoxTd ').attr("rowspan",4);
        $template.find('tr.itemLine').find('td.isIinquiryPlanBodyTd.rowspanTr.notAllUpdateRowspan ').attr("rowspan",1);
        $template.find('[name*=catalogId]').find('.selectedOption').attr('selected','selected');
        $template.find('[name*=nonDeductible]').find('.selectedOption').attr('selected','selected');


        var trLength = $template.find("tr").length;


        for(var i = 0;i<$template.find("tr").find("th").length;i++) {
            for(var m = 1;m<trLength;m++) {

                if($template.find("tr").find("th").eq(i).html()=='险种分类'||$template.find("tr").find("th").eq(i).html()=='险种'){
                    $template.find("tr").find("th").eq(i).addClass("fn-hide");
                    $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                }
                if ($template.find("tr").find("th").eq(i).html()=='产品'&&targetindexLength-2<0){
                    $template.find("tr").eq(m).find("td").eq(i+1).attr('rowspan',(trLength-1));
                }
                // if ($template.find("tr").find("td").hasClass("newTdToTd")) {
                //     if ($template.find("tr").find("th").eq(i).html() == '操作') {
                //         $template.find("tr").find("th").eq(i).addClass("fn-hide");
                //         $template.find("tr").eq(m).find("td").eq(i + 1).addClass("fn-hide");
                //     }
                // } else {
                //     if ($template.find("tr").find("th").eq(i).html() == '操作') {
                //         $template.find("tr").find("th").eq(i).addClass("fn-hide");
                //         $template.find("tr").eq(m).find("td.rowspanTr.fn-w80").addClass("fn-hide");
                //     }
                // }

                if ($template.find("tr").find("th").eq(i).html()=='责任范围'){
                    var _this = $template.find("tr");
                    console.log( $template.find("tr").length);
                    var del = '<a class="ui-btn delThis" style="" href="javascript:void(0);"><i class="icon icon-del"></i></a>';
                    if(_this.eq(1).nextAll().find("[name*='responsibilityScope']").parent().find("a").length<1){
                        _this.eq(1).nextAll().find("[name*='responsibilityScope']").after(del);
                    }
                    _this.eq(1).nextAll().find("td").eq(i).nextAll().addClass("fn-hide");
                    _this.find("td").eq(i+1).nextAll().attr('rowspan',(trLength-1));
                    //---------------附加险部分
                    var _index = $template.find(".isExtraList").length;
                    for (var j = 0;j<_index;j++) {
                        $template.find(".isExtraList").eq(j).find("td").eq(i-1).nextAll().addClass("fn-hide");
                        $template.find(".isExtraList").eq(j).find("td.notAllUpdateRowspan").not(".rowspanTr").addClass("fn-hide");
                    }
                }


                initTrLength = $template.find('tr').length;
                //预计保费费率，预计保费，意向经纪费干掉
                var a = $template.find("tr").find("td").hasClass("contentReplace");
                if($template.find("tr").find("td").hasClass("contentReplace")){
                    if($template.find("tr").find("th").eq(i).html()=='预计保费费率'){
                        $template.find("tr").find("th").eq(i).addClass("fn-hide");
                        $template.find("tr").eq(m).find("td").eq(i+1).addClass("fn-hide");
                    }
                    if($template.find("tr").find("th").eq(i).html()=='预计保费'){
                        $template.find("tr").find("th").eq(i).addClass("fn-hide");
                        $template.find("tr").eq(m).find("td").eq(i+1).addClass("fn-hide");
                    }
                    // if($template.find("tr").find("th").eq(i).html()=='意向经纪费比例(%)'|| $template.find("tr").find("th").eq(i).html()=='意向经纪费比例'){
                    //     $template.find("tr").find("th").eq(i).addClass("fn-hide");
                    //     $template.find("tr").eq(m).find("td").eq(i+1).addClass("fn-hide");
                    // }
                }else {
                    if($template.find("tr").find("th").eq(i).html()=='预计保费费率'){
                        $template.find("tr").find("th").eq(i).addClass("fn-hide");
                        $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                    }
                    if($template.find("tr").find("th").eq(i).html()=='预计保费'){
                        $template.find("tr").find("th").eq(i).addClass("fn-hide");
                        $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                    }
                    // if($template.find("tr").find("th").eq(i).html()=='意向经纪费比例(%)'|| $template.find("tr").find("th").eq(i).html()=='意向经纪费比例'){
                    //     $template.find("tr").find("th").eq(i).addClass("fn-hide");
                    //     $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                    // }
                }
            }
        }

        $.each(dataJson.lists,function (index,list) {
            // console.log(index,list);
            var $tr = $template.find('tr').eq(1 + parseInt(index));
            var $tr2 = $template.find('.itemLine');
            var $trList = $tr2.parents('table');
            if(index-$tr2.length<0) {
                $.each(list, function (_key, _val) {
                    if ('catalogId' == _key) {
                        $tr.find('[name*=' + _key + ']').find('.selectedOption').attr('selected', 'selected');
                        return;
                    }
                    if (!!_val) $tr.find('[name*=' + _key + ']').val(_val);
                });
            } else {
                var $tr3 = $template.find('tr').eq(index).clone(true);
                $.each(list,function(_key,_val) {
                    if('catalogId' == _key) {
                        $tr3.find('[name*=' + _key + ']').find('.selectedOption').attr('selected','selected');
                        return;
                    }
                    if(!!_val){
                        $tr3.find('[name*=' + _key + ']').val(_val);
                    }else {
                        $tr3.find('[name*=' + _key + ']').val(0);
                    };
                });
                $tr3.find('td.selectFnBoxTd.rowspanTr').addClass('fn-hide');
                // $tr3.find('td.rowspanTr').addClass('fn-hide');
                $template.find('tr').eq(index).after($tr3);
                $trList.find('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan',(index+1));
                // $trList.find('.rowspanTr').attr('rowspan',(index+1));
            }

        $quotationCompany.find('.quotationCompany').append($template).css("overflow-x","scroll");
        });
        var $templateTr = $template.find("tr.itemLine");
        var $templateTrTd = $templateTr.find("td.notAllUpdateRowspan");
        $.each(newArrTow,function(_key,_val){
            console.log(_key,_val) ;
            $.each($templateTrTd,function(_k,_v){
                var productInput = $templateTrTd.eq(_k).find("input.productName");
                var productName = productInput.val();
                if(productName == _val){
                    productInput.parents("td.notAllUpdateRowspan").addClass("productId_"+_key);
                    // productInput.parents("tr").eq(_k).nextAll().find("td.notAllUpdateRowspan").addClass("fn-hide");
                }
            });
            if($templateTrTd.hasClass("productId_"+_key)){
                var a = $templateTr.find(".productId_"+_key).length;

                $templateTr.find("td.productId_"+_key).eq(0).attr("rowspan",newArrJson[_key].length);//.not(":last")
                $templateTr.find("td.productId_"+_key).eq(0).parents("tr").find("input[name*='responsibilityScope']").parents("td").nextAll().attr("rowspan",newArrJson[_key].length);
                $templateTr.find("td.productId_"+_key).eq(0).parents("tr").find("input[name*='intentionBrokerRate']").parents("td").removeClass("fn-hide").attr("rowspan",newArrJson[_key].length).next().attr("rowspan",newArrJson[_key].length);
                $templateTr.find(".productId_"+_key).not(":first").addClass("fn-hide");
                $templateTr.find(".productId_"+_key).not(":first").parents("tr").find("input[name*='responsibilityScope']").parents("td").nextAll().addClass("fn-hide");
            }
        });


        $.each(dataJson.others,function(_key,_val) {
            var $input = $quotationCompany.find('[name*=' + _key + ']');
            if(!!_val && $input.length > 0) {
                if($input.hasClass('fnUpAttachVal') && !!fnUpAttachValHiddenData) {
                    $quotationCompany.find('.fnUpAttach').html(fnUpAttachValHiddenData);
                    return;
                };
                $input.val(_val);
            }

        });


        $body.Y('Window', {
            content: $quotationCompany,
            modal: true,
            key: 'mod',
            simple: true,
            closeEle: '.closeBtn'
        });
    });
    //编辑报价
    var initTrLength;
    $body.on('click','.editQuotation', function () {

        var $self = $(this);
        var customerUserId = $self.parents("tr").find("[name*='customerUserId']").val();
        var $box = $self.parents('[parentdiyname]');
        var templateId = $box.attr('targetmod');
        var fnUpAttachValHiddenData = $box.find('.fnUpAttachValHiddenData').html();
        var dataString = $self.parents('tr').find('.askCompanyOrdersData').val();
        var dataJson = !dataString ? {lists:[],others:{}} : JSON.parse(dataString);
        var targetIndex = $self.parents('table').find('tr').not(':first').index($self.parents('tr'));
        var targetMod = $self.parents('.priceCompanyConent [parentdiyname]').attr('targetmod');
        var $template = '';
        var $quotationCompany = $($('#TMP_QUOTATION').html());


        if($self.hasClass('notFirst')) {//非第一次编辑.parents("#formId")
            $template = $('.mod-render-template .mod_' + targetMod + '_' + targetIndex).clone(true);
            console.log('.mod-render-template .mod_' + targetMod + '_' + targetIndex);
            // $quotationCompany.find('.view-files-body').html($template);
            $quotationCompany.find('#formId').html($template);
        }else {//第一次编辑
            if(!templateId) {
                $template = $('.inquiryTemplate').eq(0).find('.pricePlanAreaBox table').clone(true);
            }else {
                $template = $('.inquiryTemplate.' + templateId).find('.pricePlanAreaBox table').clone(true);
            };
            $template.find('.icon-select-down').remove();
            $template.find('.selectFnBox1').width('auto');
            // $template.find('.addInsurance').remove();
            // $template.find('[targetindex] .intentionBrokerRate').eq(1).parents('td').remove();
            $template.find('[targetindex] .intentionBrokerRate').eq(0).parents('td').removeClass('fn-hide');

            var targetindex = $template.find("tr.itemLine[targetindex]");
            var targetindexLength = targetindex.length;
            if(targetindexLength-2<0){
                $template.find('tr.itemLine').not(':first').find('td.isIinquiryPlanBodyTd.rowspanTr').addClass('fn-hide');
            }
            $template.find('[name*=catalogId]').find('.selectedOption').attr('selected','selected');
            $template.find('[name*=nonDeductible]').find('.selectedOption').attr('selected','selected');


            //============
            $template.find(".productBtn").attr("flag",customerUserId);
//隐藏险种分类---------------


            var trLength = $template.find("tr").length;

                for(var i = 0;i<$template.find("tr").find("th").length;i++){
                    // console.log(trLength);
                    for(var m = 1;m<trLength;m++){
                        // console.log(trLength);
                    if($template.find("tr").find("th").eq(i).html()=='险种分类'||$template.find("tr").find("th").eq(i).html()=='险种'){
                        $template.find("tr").find("th").eq(i).addClass("fn-hide");
                        $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                    }
                    if ($template.find("tr").find("th").eq(i).html()=='产品'&&targetindexLength-2<0){
                        $template.find("tr").eq(m).find("td").eq(i+1).attr('rowspan',(trLength-1));
                    }
                    if ($template.find("tr").find("th").eq(i).html()=='责任范围'){
                        var _this = $template.find("tr");
                        var del = '<a class="ui-btn delThis" style="" href="javascript:void(0);"><i class="icon icon-del"></i></a>';
                        if(_this.eq(1).nextAll().find("[name*='responsibilityScope']").parent().find("a").length<1){
                            _this.eq(1).nextAll().find("[name*='responsibilityScope']").after(del);
                        }
                        _this.eq(1).nextAll().find("td").eq(i).nextAll().addClass("fn-hide");
                        _this.find("td").eq(i+1).nextAll().attr('rowspan',(trLength-1));
                        //---------------附加险部分
                        var _index = $template.find(".isExtraList").length;
                        for (var j = 0;j<_index;j++) {
                            $template.find(".isExtraList").eq(j).find("td").eq(i-1).nextAll().addClass("fn-hide");
                            $template.find(".isExtraList").eq(j).find("td.notAllUpdateRowspan").not(".rowspanTr").addClass("fn-hide");
                        }
                    }

                    initTrLength = $template.find('tr').length;
                    //预计保费费率，预计保费，意向经纪费干掉
                    var a = $template.find("tr").find("td").hasClass("contentReplace");
                    if($template.find("tr").find("td").hasClass("contentReplace")){
                        if($template.find("tr").find("th").eq(i).html()=='预计保费费率'){
                            $template.find("tr").find("th").eq(i).addClass("fn-hide");
                            $template.find("tr").eq(m).find("td").eq(i+1).addClass("fn-hide");
                        }
                        if($template.find("tr").find("th").eq(i).html()=='预计保费'){
                            $template.find("tr").find("th").eq(i).addClass("fn-hide");
                            $template.find("tr").eq(m).find("td").eq(i+1).addClass("fn-hide");
                        }
                        if($template.find("tr").find("th").eq(i).html()=='意向经纪费比例(%)'|| $template.find("tr").find("th").eq(i).html()=='意向经纪费比例'){
                            $template.find("tr").find("th").eq(i).addClass("fn-hide");
                            $template.find("tr").eq(m).find("td").eq(i+1).addClass("fn-hide");
                        }
                    }else {
                        if($template.find("tr").find("th").eq(i).html()=='预计保费费率'){
                            $template.find("tr").find("th").eq(i).addClass("fn-hide");
                            $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                        }
                        if($template.find("tr").find("th").eq(i).html()=='预计保费'){
                            $template.find("tr").find("th").eq(i).addClass("fn-hide");
                            $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                        }
                        if($template.find("tr").find("th").eq(i).html()=='意向经纪费比例(%)'|| $template.find("tr").find("th").eq(i).html()=='意向经纪费比例'){
                            $template.find("tr").find("th").eq(i).addClass("fn-hide");
                            $template.find("tr").eq(m).find("td").eq(i).addClass("fn-hide");
                        }
                    }
                }
            }
            //-----------------------

            $.each(dataJson.lists,function (index,list) {
                var $tr = $template.find('tr').eq(1 + parseInt(index));
                var $tr2 = $template.find('.itemLine');
                var $trList = $tr2.parents('table');
                if(index-$tr2.length<0){
                    $.each(list,function(_key,_val) {
                        if('catalogId' == _key) {
                            $tr.find('[name*=' + _key + ']').find('.selectedOption').attr('selected','selected');
                            return;
                        }
                        if(!!_val) $tr.find('[name*=' + _key + ']').val(_val);
                    });
                }else {
                    var $tr3 = $template.find('tr').eq(index).clone(true);
                    $.each(list,function(_key,_val) {
                        if('catalogId' == _key) {
                            $tr3.find('[name*=' + _key + ']').find('.selectedOption').attr('selected','selected');
                            return;
                        }
                        if(!!_val){
                            $tr3.find('[name*=' + _key + ']').val(_val);
                        }else {
                            $tr3.find('[name*=' + _key + ']').val('');
                        };
                    });
                    // $tr3.find('td.selectFnBoxTd.rowspanTr').addClass('fn-hide');
                    $tr3.find('td.rowspanTr').addClass('fn-hide');
                    $template.find('tr').eq(index).after($tr3);
                    $trList.find('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan',(index+1));
                    $trList.find('.rowspanTr').attr('rowspan',(index+1));
                    // $trList.find("tr").eq(1).find("td").find('[name*=liabilityNameId]').parent("td").nextAll().attr('rowspan',trLength);

                }

            });
            $.each(dataJson.others,function(_key,_val) {
                var $input = $quotationCompany.find('[name*=' + _key + ']');
                if(!!_val && $input.length > 0) {
                    if($input.hasClass('fnUpAttachVal') && !!fnUpAttachValHiddenData) {
                        $quotationCompany.find('.fnUpAttach').html(fnUpAttachValHiddenData);
                        return;
                    };
                    $input.val(_val);
                }

            });
            var $addLine = '<div class=""><a class="ui-btn ui-btn-fill ui-btn-green addProduct" templateid=""  href="javascript:void(0);" templatelist="tr">'
                +'<i class="icon icon-add"></i></a></div>'
            $quotationCompany.find('.sureQuotationCompany').attr({'targetindex':targetIndex,'targetmod':targetMod});
            $template.css("width","1300px");
            $quotationCompany.find('.quotationCompany').html($template).css("overflow-x","scroll");//.append($addLine);

            targetindexLength;
            for(var k =0;k<targetindexLength;k++){
                if(!$self.attr("flag")){
                    targetindex.parents("tbody").find('tr').eq(0).nextAll().find("td:last.rowspanTr").html($addLine);

                }
            }


            //添加标记
            $quotationCompany.find('.quotationCompany').parents('.form_template').addClass('mod_' + targetMod + '_' + targetIndex);
        };

        $body.Y('Window', {
            content: $quotationCompany,
            modal: true,
            key: 'mod',
            simple: true,
            closeEle: '.closeBtn'
        });


    });

    //新增产品------------
    var newLine;
    $body.on('click','.addProduct',function () {
        var $this = $(this);
        var box = $this.parents("tbody");
        var targetindex = box.find("tr.itemLine[targetindex]:last").attr("targetindex");
        // var targetindexLength = targetindex.length;

        if(targetindex-1<0){
            var newLine = box.find("tr").not(":first").clone(true);
        }else {
            var newLine = $this.parents("tr").clone(true);
        }
        var $tbody = "<tbody></tbody>";
        var $delNew = '<a class="ui-btn ui-btn-fill ui-btn-danger delNew" href="javascript:void(0);" style="color: #fff;">删除</a>';
        newLine.find("td:last.rowspanTr").html($delNew);
  
        box.parents(".quotationCompany").find('tbody').after($tbody);
        box.parents(".quotationCompany").find('tbody:last').append(newLine);
    });
    // $body.on('click','.addProduct',function () {
    //     var $this = $(this);
    //     var box = $this.parents(".quotationCompany");
    //     if( box.find('tr').length == initTrLength ){
    //         var copyHtml = box.find('tbody').clone(true);
    //         newLine = copyHtml.find('tr').eq(0).nextAll();
    //     }
    //     newLine = newLine.clone();
    //     var $tbody = "<tbody></tbody>";
    //     var $delNew = '<a class="ui-btn ui-btn-fill ui-btn-danger delNew" href="javascript:void(0);" style="color: #fff;">删除</a>';
    //     newLine.find("td:last.rowspanTr").html($delNew);
    //     // console.log(newLine.find("td:last.rowspanTr").html());
    //     // console.log(box.find('tbody'));
    //     box.find('tbody').after($tbody);
    //     box.find('tbody:last').append(newLine);
    // });

    $body.on("click",".delNew",function () {
        var $this = $(this);
        var targetindexLength = $this.parents("tbody").find("tr.itemLine[targetindex]:last").attr("targetindex");
        if(targetindexLength-1<0){
            $this.parents("tbody").remove();
        }else{
            $this.parents("tr").remove();

        }
    });

    //------------

    //报价确认
    $body.on('click','.sureQuotationCompany', function () {

        var $self = $(this);
        var $modBox = $self.parents('.m-modal');
        var $tableTr = $modBox.find('table tr').not(':first');
        var targetIndex = $self.attr('targetindex');
        var targetMod = $self.attr('targetmod');
        var $template = '';
        var data = {lists:[],others:{}};
        var $mod_tempalte = $modBox.find('.form_template').clone(true);
        // var $mod_tempalte = $modBox.find('#formId').clone(true);

        console.log($mod_tempalte);
        if(!targetMod) {
            $template = $('.priceCompanyConent [parentdiyname]').eq(0).find('table .itemList').eq(targetIndex);
        }else {
            $template = $('.priceCompanyConent .priceCompanyConent_' + targetMod).find('table .itemLine').eq(targetIndex);
        };

        $.each($tableTr,function (i,el) {
            var list = {};
            $.each($(el).find('td [name]'),function (j,input) {
                list[$(input).attr('name')] = input.value;
            });
            data.lists.push(list);
        });
        console.log(data);


        $.each($modBox.find('.quotationOthers [name]'),function (i,el) {
            data.others[$(el).attr('name')] = el.value;
            // if($(el).hasClass('fnUpAttachVal')) {
            //     var $fnUpAttachValHiddenData = $template.find('.fnUpAttachValHiddenData');
            //     if($fnUpAttachValHiddenData.length == 0){
            //         $template.append('<span class="fnUpAttachValHiddenData fn-hide">' + $(el).parents('.fnUpAttach').html() + '</span>');
            //     }else {
            //         $fnUpAttachValHiddenData.html($(el).parents('.fnUpAttach').html());
            //     }
            // }
        });
        var firstPeriod = data.lists[0].firstPeriod; //佣金参数
        console.log('.mod_' + targetMod + '_' + targetIndex);
        //把当前的表格缓存起来
        $('.mod-render-template').find('.mod_' + targetMod + '_' + targetIndex).remove();

        $('.mod-render-template').append($mod_tempalte);
        //把表格数据缓存起来
        console.log(data);
        $template.find('.askCompanyOrdersData').val(JSON.stringify(data)).end().find('.editQuotation').addClass('notFirst');
        //同步数据到报价表格
        $template.find('.createDate').val($modBox.find('.createDate').val());
        $template.find('.expiryDate').val($modBox.find('.expiryDate').val());
        $template.find('.premiumAmount').val($modBox.find('.target_premiumAmount').val());
        $template.find('.brokerAmount').val($modBox.find('.target_borkerAmount').val());
        $template.find('.borkerAmoutRate').val($modBox.find('.target_borkerAmountRate').val());
        $template.find('.expenseAmount').val($modBox.find('.target_premiumAmount').val()*firstPeriod);


        //----------------------
        // var validDiv = $self.parents("#formId");
        // validatForm(validDiv);
        // if (!validDiv.valid()) return;
        //---------------------------

        //关闭弹窗
        $self.siblings('.closeBtn').click();
    });
    //-----------------------
    function validatForm($thisForm) {//验证初始化
        new validateInit({
            formObj: $thisForm,//jq对象
            ignoreClass: '',//需要排除验证元素的class
            validateEle: 'fn-validate',//需要验证元素的obj
            validateRules: {
            }
        });
    };
    //-------------------------

    //根据产品选择
    $('body').on('click', '.productBtn', function(event) {
        var $thisProduct, kindsid, targetindex,$template_com,targetindex,customerUserId;
        $thisProduct = $(this);
        kindsid = $thisProduct.parents('tr').find('.selectedId').val();
        targetindex = $thisProduct.parents('tr').attr('targetindex');
        customerUserId = $thisProduct.attr("flag");
        //创建一个模板并初始化
        $template_com = $thisProduct.parents('tr').clone(true);
        $template_com.addClass('newListTr');
        $template_com.find('.isIinquiryPlanBodyTd').find('input,select,textarea').removeClass('fn-hide').removeAttr('disabled');
        $template_com.find('.isIinquiryPlanBodyTd .disabledInput').remove();

        if (!!!kindsid) {
            hintPopup('请选择保险分类');
            return;
        }

        var fundDitch = new popupWindow({

            YwindowObj: {
                content: 'selectProductPopup', //弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', //find关闭弹窗对象
                dragEle: '.newPopup dl dt' //find拖拽对象
            },

            ajaxObj: {
                url: '/baseDataLoad/product.json',
                type: 'post',
                dataType: 'json',
                data: {
                    catalogId: kindsid,
                    isQuota:'NO',
                    companyBaseUserId:customerUserId
                }
            },

            formAll: { //搜索
                submitBtn: '#PopupSubmit', //find搜索按钮
                formObj: '#PopupFrom', //find from
                callback: function($wnd) { //点击回调
                    // console.log($wnd)
                }
            },

            pageObj: { //翻页
                clickObj: '.pageBox a.btn', //find翻页对象
                attrObj: 'page', //翻页对象获取值得属性
                jsonName: 'pageNumber', //请求翻页页数的dataName
                callback: function($Y) {

                    //console.log($Y)

                }
            },

            callback: function($Y) {

                $Y.wnd.on('click', '.choose', function(event) {

                    var $this, productName, productId, companyUserName, arrHtml, $product,firstPeriod;
                    $this = $(this);
                    productName = $this.attr('productName');
                    productId = $this.attr('productId');
                    firstPeriod = $this.attr('firstPeriod');

                    console.log(firstPeriod);
                    //如果选择的同一种则终止操作
                    if(productId == $thisProduct.siblings('.productId').val()) {
                        $Y.close();
                        return;
                    }
                    var $trList = $thisProduct.parents('table').find('[targetchildindex=' + targetindex +'],[targetindex=' + targetindex +']');
                    var localtion = $thisProduct.parents('td').attr('rowspan') - 1;//多余保险责任开始插入的位置
                    // $thisProduct.siblings('.productName').val(productName);
                    // $thisProduct.siblings('.productId').val(productId);
                    // $thisProduct.parents('tbody').find('.productName').val(productName);
                    // $thisProduct.parents('tbody').find('.productId').val(productId);
                    //如果没有责任范围则去对比
                    if(!$thisProduct.parents('tr').hasClass('noResponsibilityScope')) {
                        $.ajax({
                            url:'/baseDataLoad/queryProductLiability.json?productId=' + productId,
                            success:function (res) {
                                if(res.success){
                                    var liabilityIds = res.data.liabilityIds;
                                    var liabilitis = res.data.liabilitis;
                                    // console.log(res.data)
                                    //清除上次数据
                                    $thisProduct.parents('table').find('.newListTr').remove();
                                    //对比保险责任
                                    $.each($trList,function (i,el) {
                                        var $liabilityId = $(el).find('[name*=liabilityNameId]');

                                        var liabilityId = $liabilityId.val();//此处值为string类型
                                        //判断只有在保险责任才执行
                                        if($liabilityId.length == 0 || !liabilityId || liabilityId == 0) return;
                                        // console.log('liabilityId: ' + liabilityId);
                                        // console.log('indexOf: ' + liabilityIds.indexOf(parseInt(liabilityId)));
                                        var liabilityIdIndex = liabilityIds.indexOf(parseInt(liabilityId));
                                        $(el).addClass('disabledTr').find('.productIdChecked .disabledInput').remove();
                                        if(liabilityIdIndex == -1){
                                           // $(el).find('.productIdChecked').append('<span class="disabledInput">\/</span>').find('input,select,textarea').addClass('fn-hide').attr('disabled','disabled').val('').blur();
                                        }else {
                                            $(el).find('.productIdChecked').find('input,select,textarea').removeClass('fn-hide').removeAttr('disabled');
                                            liabilityIds.splice(liabilityIdIndex,1);
                                        };

                                    });
                                    // console.log('liabilityIdsLength: ' + liabilityIds)
                                    //添加没有的保险责任
                                    $.each(liabilityIds,function (i,_val) {
                                        //拷贝

                                        var $template = $template_com.clone(true);
                                        $template.removeAttr('targetindex').attr('targetchildindex',targetindex);
                                        $template.find('.rowspanTr').addClass('fn-hide');
                                        $template.find('.oldList').not('.isIinquiryPlanBodyTd').find('input,select,textarea').val('').attr('readonly',true).addClass('ignore');
                                        $template.find('[targetlist=liabilityId]').val(_val);
                                        $template.find('[targetlist=liabilityName]').val(liabilitis[_val].liabilityName);
                                        //------------------
                                        var trLength = $thisProduct.parents("table").find("tr").length;
                                        var del = '<a class="ui-btn delThis" style="" href="javascript:void(0);"><i class="icon icon-del"></i></a>';
                                        $template.find('[name*=liabilityNameId]').parent("td").nextAll().addClass("fn-hide");
                                        $template.find('[name*=liabilityNameId]').after(del);
                                        $thisProduct.parents("table").find("tr").eq(1).find("td").find('[name*=liabilityNameId]').parent("td").nextAll().attr('rowspan',trLength);
                                        //-------------------------

                                        $trList.eq(localtion + i).after($template);
                                        //更新$trList
                                        $trList = $thisProduct.parents('table').find('[targetchildindex=' + targetindex +'],[targetindex=' + targetindex +']');
                                    });
                                    //更新rowspanTr的rowspan

                                    $trList.find('.rowspanTr').attr('rowspan',$thisProduct.parents('table').find('[targetchildindex=' + targetindex +'],[targetindex=' + targetindex +']').not('.isExtraList').length);
                                    // $trList.find('.rowspanTr').not('.notAllUpdateRowspan').attr('rowspan',$thisProduct.parents('table').find('[targetchildindex=' + targetindex +'],[targetindex=' + targetindex +']').length);
                                    $trList.find('.rowspanTr').attr('rowspan',$thisProduct.parents('table').find('[targetchildindex=' + targetindex +'],[targetindex=' + targetindex +']').length);


                                  //更新产品名字notAllUpdateRowspan
                                  //   $thisProduct.parents('tbody').find('.productName').val(productName);
                                  //   $thisProduct.parents('tbody').find('.productId').val(productId);
                                    var targetindexLength = $thisProduct.parents("table").find("tr.itemLine[targetindex]").length;
                                    console.log(targetindexLength);
                                    if(targetindexLength>1){
                                        $thisProduct.parents("tr").find('.productName').val(productName);
                                        $thisProduct.parents("tr").find('.productId').val(productId);
                                        $thisProduct.parents("tr").find('.firstPeriod').val(firstPeriod);
                                    }else{
                                        $thisProduct.parents("tbody").find('.productName').val(productName);
                                        $thisProduct.parents("tbody").find('.productId').val(productId);
                                        $thisProduct.parents("tbody").find('.firstPeriod').val(firstPeriod);
                                    }

                                }
                            }
                        });
                    }else {//没有保险责任 直接添加产品
                        var targetindexLength = $thisProduct.parents("table").find("tr.itemLine[targetindex]").length;
                        console.log(targetindexLength);
                        if(targetindexLength>1){
                            $thisProduct.parents("tr").find('.productName').val(productName);
                            $thisProduct.parents("tr").find('.productId').val(productId);
                            $thisProduct.parents("tr").find('.firstPeriod').val(firstPeriod);
                        }else{
                            $thisProduct.parents("tbody").find('.productName').val(productName);
                            $thisProduct.parents("tbody").find('.productId').val(productId);
                            $thisProduct.parents("tbody").find('.firstPeriod').val(firstPeriod);
                        }

                    };
                    $Y.close();

                });

            },

            console: true //打印返回数据

        });

    }).on('click', '.clearBtn', function(event) {
        $(this).siblings('[name]').val('');
    });

    $body.on('blur','.premiumAmount,.borkerAmount',function () {
        var $self = $(this);
        var allVal = 0;
        var isPremiumAmount = $self.hasClass('premiumAmount');
        var $allInput = isPremiumAmount ? $self.parents('table').find('.premiumAmount') : $self.parents('table').find('.borkerAmount');
        var $modBox = $self.parents('.m-modal-body');
        var $premiumAmount = $self.parents('.m-modal-body').find('.target_premiumAmount');
        var $borkerAmount = $self.parents('.m-modal-body').find('.target_borkerAmount');
        $.each($allInput,function (i,el) {
            var $input = $(el);
            var _thisVal = (!$input.val() || isNaN($input.val())) ? 0 : parseFloat($input.val());
            allVal += _thisVal;
        });
        // console.log('allVal: ' + allVal)
        allVal = parseInt(Math.round(allVal * 100)) / 100;
        if(isPremiumAmount) {
            $premiumAmount.val(allVal);
            var $tr = $self.parents('tr');
            var $borkerAmountRate = $tr.find('.borkerAmountRate');
            var $thisBorkerAmount = $tr.find('.borkerAmount');
            var borkerAmountRateVal = (!$borkerAmountRate.val() || isNaN($borkerAmountRate.val())) ? 0 : parseFloat($borkerAmountRate.val());
            var _val = (!$self.val() || isNaN($self.val())) ? 0 : parseFloat($self.val());
            var resltVal = Math.round(borkerAmountRateVal * _val) / 100;
            $thisBorkerAmount.val(resltVal).blur();
        }else {
            $borkerAmount.val(allVal)
        };
        var premiumAmountVal = (!$premiumAmount.val() || isNaN($premiumAmount.val())) ? 0 : parseFloat($premiumAmount.val());
        var borkerAmountVal = (!$borkerAmount.val() || isNaN($borkerAmount.val())) ? 0 : parseFloat($borkerAmount.val());
        if(premiumAmountVal == 0 || !premiumAmountVal) return;
        var reslt = parseInt((borkerAmountVal / premiumAmountVal) * 10000) / 100;
        $modBox.find('.target_borkerAmountRate').val(reslt > 0 ? reslt : 0);

    });

    $body.on('blur','.borkerAmountRate',function () {
        var $self = $(this);
        var $tr = $self.parents('tr');
        var $premiumAmount = $tr.find('.premiumAmount');
        var premiumAmountVal = (!$premiumAmount.val() || isNaN($premiumAmount.val())) ? 0 : parseFloat($premiumAmount.val());
        var _val = (!$self.val() || isNaN($self.val())) ? 0 : parseFloat($self.val());
        var resltVal  = Math.round(premiumAmountVal * _val) / 100;
        $tr.find('.borkerAmount').val(resltVal).blur();

    });
    $body.on("click",".delThis",function () {
        var $this = $(this);
        var trLength =  $this.parents("table").find("tr").length;
        $this.parent("td").remove();
        $this.parents("table").find(".delThis").parent("td").attr("rowspan",trLength-1)
    });


});