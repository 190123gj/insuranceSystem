define(function(require, exports, module) {
    require('Y-msg');
    require('tmbp/upAttachModifyNew');
    require('tmbp/operate.common');
    require('tmbp/submit.common');
    var util = require('util');
    
    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();
    
    var hintPopup = require('zyw/hintPopup');
    var popupWindow = require('zyw/popupWindow');
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    var getList = require('zyw/getList');

    //编辑初始化
    $(function(){
    	if ($.trim($("#priceContactName").val()).length == 0) {
        	$(".showBtn").show();
        } else {
        	$(".showBtn").hide();
        }
    });
    
    
    var $body = $('body');
    var arry = [];
    var priceProducts = [];
    var $isCover = $("#isCover");
    var $certInfo = $("#certInfo");
    var $isInvoice = $("input:radio[name='isInvoice']");

    var URL_OBJ = getUrlVal();//获取url参数
    var selectedLifeInsurance = !URL_OBJ || !URL_OBJ.type ? selectedLifeInsuranceType() : URL_OBJ.type;

    if(!!selectedLifeInsurance){
        $('.lifeInsuranceMod').not('.' + selectedLifeInsurance).find('[name]').attr('disabled','disabled');
        $('.lifeInsuranceMod.' + selectedLifeInsurance).removeClass('fn-hide').find('[name]').removeAttr('disabled');
    }


    $isCover.on("change",function(){
        var _cover = $(this).prop("checked");
        $("input[name='isCover']").val(_cover);
    });


    window.checkThis = function (t,i) {
        if ($(t).prop("checked")) {
            $(".index"+i).val("1")
        } else {
            $(".index"+i).val("0")
        }
    }

    $isInvoice.on("change",function(){
        var isInvoicever = $(this).val();
        if (isInvoicever == "YES") {
            $('.invoice').removeClass('fn-hide').find("input").removeAttr("disabled");
        } else {
            $(".invoice").addClass("fn-hide").find("input").attr("disabled",true);
        }
    });

    if (infoJson != "") {
        var insuranceContactLetterDetail = eval("("+infoJson+")");
        var isInvoice = insuranceContactLetterDetail.isInvoice;
        if (isInvoice == "YES") {
            $('.invoice').removeClass('fn-hide').find("input").removeAttr("disabled");
        } else {
            $(".invoice").addClass("fn-hide").find("input").attr("disabled",true);
        }
    }


    (new getList()).init({
        title: '超权限审批单',
        ajaxUrl: '/baseDataLoad/projectSetup.json',
        btn: '#fnToChooseProjectSetupName',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.projectSetupName}}">{{item.projectSetupName}}</td>',
                '        <td title="{{item.beginTime}}">{{item.beginTime}}</td>',
                '        <td title="{{item.endTime}}">{{item.endTime}}</td>',
                '        <td title="{{item.status}}">{{item.status}}</td>',
                '<td><a class="choose" customers="{{item.customers}}" catalogIds="{{item.catalogIds}}" projectSetupId="{{item.projectSetupId}}" projectSetupName="{{item.projectSetupName}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['超权限审批单名称：',
                '<input class="ui-text fn-w100" type="text" name="projectSetupName" />&nbsp;&nbsp;&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>超权限审批单名称</th>',
                    '<th>生效日期</th>',
                    '<th>有效期至</th>',
                    '<th>状态</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            $("#projectSetupName").val($a.attr("projectSetupName")).blur();
            $("#project").val($a.attr("projectSetupName")).blur();
            $("#projectSetupId").val($a.attr("projectSetupId")).blur();
            //清空询价单信息
            arry = [];
            priceProducts = [];        
            
            //筛选 客户信息 和  投保内容险种信息
            $("select[name='customerUserType']").html("<option value='COMPANY_CUSTOMER' selected='selected'>团体客户</option>").blur();
            //团体客户无性别 和 出生日期
            $(".individual_customer").addClass("fn-hide").find("input").attr("disabled",true);
            var userIds = [];
            //超权限关联的客户
            var customerUserIds = $a.attr("customers");
            if (customerUserIds) {
            	userIds = eval("("+customerUserIds+")");
            }
            $('.fnToClearCustomer').click();
            cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=COMPANY_CUSTOMER&companys='+userIds;//更新客户选择请求地址
            //超权限管理的险种
            var catalog =  $a.attr("catalogIds");
            if (catalog) {
            	var catalogIds = eval("("+catalog+")");
            	$("#projectSetUpCatalogIds").val(catalogIds);
            }
        }
    });

    

    (new getList()).init({
        title: '询价方案',
        ajaxUrl: '/baseDataLoad/queryPriceScheme.json',
        btn: '#fnToChoosePriceTemplate',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.billNo}}">{{item.billNo}}</td>',
                '        <td title="{{item.name}}">{{item.name}}</td>',
                '<td><a class="choose chooseInquiry" billNo = "{{item.billNo}}" newName ="{{item.name}}"    projectSetUpId="{{item.projectSetUpId}}" projectSetUpName="{{item.projectSetName}}" customers="{{item.customers}}" catalogIds="{{item.catalogIds}}" unitAddress="{{item.unitAddress}}" sex="{{item.sex}}" birthday="{{item.birth}}" businessUserId="{{item.businessUserId}}" businessUserName="{{item.businessUserName}}" customerUserId="{{item.customerUserId}}" customerType="{{item.customerType}}" customerUserName="{{item.customerUserName}}" priceContactNo="{{item.billNo}}" priceContactId="{{item.id}}" priceContactName="{{item.name}}" company="{{item.company}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['编号：',
                '<input class="ui-text fn-w100" type="text" name="billNo" />&nbsp;&nbsp;&nbsp;&nbsp;',
                '名称：',
                '<input class="ui-text fn-w100" type="text" name="name" />&nbsp;&nbsp;&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>询价方案编号</th>',
                '<th>询价方案名称</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
        	//清空保险公司信息

            // $("#companyUserName").val("");
            // $("#companyUserId").val("");
            // $("#projectSetupId").val("");
            // $("#projectSetupName").val("").blur;
            //
            // //询价单赋值
            // $("#priceContactNo").val($a.attr("priceContactNo")).blur();
            // $("#priceContactId").val($a.attr("priceContactId")).blur();
            // $("#priceContactName").val($a.attr("priceContactName")).blur();
            //
            // //填充业务员的id和名称
            // $("#businessUserName").val($a.attr("businessUserName")).blur();
            // $("#businessUserId").val($a.attr("businessUserId")).blur();
            //
            // //带出询价单中的保险公司信息
            // var company = $a.attr("company");
            // arry=[];
            // if (company) {
           	//  var obj = eval("("+company+")");
           	//  $.each(obj,function(i,v){
           	// 	 arry.push($(v).attr("companyUserId"));
            //      arry.push($(v).attr("companyUserName"));
           	//  });
            //     initCompany.ajaxUrl = "/baseDataLoad/customer.json?companys="+arry[0];
            //     //将询价单中的保险公司的id 存起来，以防清除选择的保险公司的时候，查询了全部的保险公司id
            //    // $("#companyUserIds").val(arry);
            //
            // }
            // //---保险公司信息带入----
            // $("#companyUserName").val(arry[1]).blur();
            // $("#companyUserId").val(arry[0]).blur();
            // $newCode.companyUserName=arry[1];
            // $newCode.companyUserId=arry[0];
            // // console.log(initCompany.ajaxUrl);
            //
            // //-----------
            // //点击询价单编号，代入相应的客户信息
            // $("select[name='customerUserType']").val($a.attr("customerType")).blur();
            // $("input[name='customerUserId']").val($a.attr("customerUserId")).blur();
            // $("input[name='customerUserName']").val($a.attr("customerUserName")).blur();
            // $("#userName").val($a.attr("customerUserName"));
            // $("#projectSetupId").val($a.attr("projectSetupId")).blur();
            // $("#projectSetupName").val($a.attr("projectSetupName")).blur();
            // if ($.trim($a.attr("projectSetupName")).length > 0) {
            // 	//筛选 客户信息 和  投保内容险种信息
            //     $("select[name='customerUserType']").html("<option value='COMPANY_CUSTOMER' selected='selected'>团体客户</option>");
            //     var userIds = [];
            //     //超权限关联的客户
            //     var customerUserIds = $a.attr("customers");
            //     if (customerUserIds) {
            //     	userIds = eval("("+customerUserIds+")");
            //     }
            //     cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=COMPANY_CUSTOMER&companys='+userIds;//更新客户选择请求地址
            //     //超权限管理的险种
            //     var catalog =  $a.attr("catalogIds");
            //     if (catalog) {
            //     	var catalogIds = eval("("+catalog+")");
            //     	$("#projectSetUpCatalogIds").val(catalogIds);
            //     }
            // } else {
            // 	if ($a.attr("customerType") == "INDIVIDUAL_CUSTOMER") {
            // 		$("select[name='customerUserType']").html("<option value='INDIVIDUAL_CUSTOMER' selected='selected'>个人客户</option>");
            // 		cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=INDIVIDUAL_CUSTOMER';//更新客户选择请求地址
            //
            // 	} else {
            // 		$("select[name='customerUserType']").html("<option value='COMPANY_CUSTOMER' selected='selected'>团体客户</option>");
            // 		cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=COMPANY_CUSTOMER';//更新客户选择请求地址
            // 	}
            // }
            //
            // //个人客户，没有关联超权限(关联了超权限的，都是团体客户)
            // if ($a.attr("customerType") == "INDIVIDUAL_CUSTOMER" && $.trim($a.attr("projectSetupName")).length == 0) {
            // 	$(".individual_customer").removeClass("fn-hide").find("input").removeAttr("disabled");
            // 	$(".company_customer").addClass("fn-hide").find("input").attr("disabled",true);
            // 	//带出客户的性别、生日
            // 	var sexVal =  $("input:radio[name='customerUserSex']");
            //
            // 	$.each(sexVal,function(i,v){
            // 		var _this = $(this);
            // 		if (_this.val() == $a.attr("sex")) {
            // 			_this.attr("checked","checked");
            // 		}
            // 	});
            // 	$("input[name='customerUserBirth']").val($a.attr("birthday")).blur();
            // } else {
            // 	$("#customerUserAddress").val($a.attr("unitAddress")).blur();
            // 	$(".company_customer").removeClass("fn-hide").find("input").removeAttr("disabled");
            // 	$(".individual_customer").addClass("fn-hide").find("input").attr("disabled",true);
            // }
        }
    });
    //--------------------------
    var $newCode = {};
    $body.on("click",".chooseInquiry",function () {
        var $this = $(this);
        var priceContactLetterId = $this.attr("priceContactId");
        inquiryInfo.ajaxUrl ="/baseDataLoad/queryReportPriceInfo.json?priceContactLetterId="+priceContactLetterId;
        $newCode.billNo=$this.attr("billNo");
        $newCode.birth=$this.attr("birthday");
        $newCode.businessUserId=$this.attr("businessUserId");
        $newCode.businessUserName=$this.attr("businessUserName");
        $newCode.catalogIds=$this.attr("catalogIds");
        $newCode.company=$this.attr("company");
        // $newCode.companyUserName=$this.attr("companyUserName");
        // $newCode.companyUserId=$this.attr("companyUserId");
        $newCode.customerType=$this.attr("customerType");
        $newCode.customerUserId=$this.attr("customerUserId");
        $newCode.customerUserName=$this.attr("customerUserName");
        $newCode.customers=$this.attr("customers");
        $newCode.id=$this.attr("priceContactId");
        $newCode.name=$this.attr("priceContactName");
        $newCode.projectSetName=$this.attr("projectSetUpName");
        $newCode.projectSetUpId=$this.attr("projectSetUpId");
        $newCode.sex=$this.attr("sex");
        $newCode.unitAddress=$this.attr("unitAddress");



        console.log($newCode);
    });
    var inquiryInfo = new getList();
    inquiryInfo.init({
        title: '询价方案',
        ajaxUrl: '/baseDataLoad/queryReportPriceInfo.json?priceContactLetterId=',
        btn: '.chooseInquiry',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '<tr>',
                '<td class = "fn-tac" ><a href = "javascript:void(0)" class = " chooseProductName">{{item.customerUserName}}</a></td>' ,
                '<td class = "fn-tac" >{{item.premiumAmount.amount}}</td>' ,
                '<td class = "fn-tac" >{{item.brokerAmount.amount}}</td>',
                '<td class = "fn-tac" >{{item.brokerAmountRate}}</td>',
                '<td class = "fn-tac" >{{item.expenseAmount.amount}}</td>',
                '<td class = "fn-tac" >{{item.contactUserName}}</td>',
                '<td class = "fn-tac" >{{item.contactUserMobile}}</td>',
                '<td class = "fn-tac" >{{item.contactUserEmail}}</td>',
                '<td class = "fn-tac" >{{item.createDate}}</td>',
                '<td class = "fn-tac" >{{item.expiryDate}}</td>',
                '<td class = "fn-tac" ><a href = "javascript:void(0)" companyUserId="{{item.customerUserId}}"  companyUserName="{{item.customerUserName}}" detailInfos = "{{item.detailInfos}}" class = "choose chooseDetailInfos"> 选择 </a></td></tr> ',
                '{{/each}}'
            ].join(''),
            form: ['编号：',
                '<input class="ui-text fn-w100" type="text" name="billNo" />&nbsp;&nbsp;&nbsp;&nbsp;',
                '名称：',
                '<input class="ui-text fn-w100" type="text" name="name" />&nbsp;&nbsp;&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: [
            '<th class="fn-tac">保险公司名称</th>',
            '<th class="fn-tac">保险费(元)</th>',
            '<th class="fn-tac">经纪费（元）</th>',
            '<th class="fn-tac">经纪费比例</th>',
            '<th class="fn-tac">佣金</th>',
            '<th class="fn-tac">联系人</th>',
            '<th class="fn-tac">联系电话</th>',
            '<th class="fn-tac">邮箱地址</th>',
            '<th class="fn-tac">报价时间</th>',
            '<th class="fn-tac">报价有效期</th>',
            '<th class="fn-tac">操作</th>',
            ].join(''),
            item: 5
        },
        callback: function($a) {
            console.log($newCode);
            var $newCodeInfo = $newCode;
            //清空保险公司信息

            $("#companyUserName").val("");
            $("#companyUserId").val("");
            $("#projectSetupId").val("");
            $("#projectSetupName").val("").blur;

            //询价单赋值
            $("#priceContactNo").val($newCodeInfo.billNo).blur();
            $("#priceContactId").val($newCode.id).blur();
            $("#priceContactName").val($newCode.name).blur();

            //填充业务员的id和名称
            $("#businessUserName").val($newCode.businessUserName).blur();
            $("#businessUserId").val(!$newCode.businessUserId?0:$newCode.businessUserId).blur();

            //带出询价单中的保险公司信息
            var company = $newCode.company;
            arry=[];
            if (company) {
                var obj = eval("("+company+")");
                $.each(obj,function(i,v){
                    arry.push($(v).attr("companyUserId"));
                    arry.push($(v).attr("companyUserName"));
                });
                initCompany.ajaxUrl = "/baseDataLoad/customer.json?companys="+arry[0];
                //将询价单中的保险公司的id 存起来，以防清除选择的保险公司的时候，查询了全部的保险公司id
                // $("#companyUserIds").val(arry);

            }
            //---保险公司信息带入----
            $("#companyUserName").val($a.attr("companyUserName")).blur();
            $("#companyUserId").val($a.attr("companyUserId")).blur();
            // console.log(initCompany.ajaxUrl);

            //-----------
            //点击询价单编号，代入相应的客户信息
            $("select[name='customerUserType']").val($newCode.customerType).blur();
            $("input[name='customerUserId']").val($newCode.customerUserId).blur();
            $("input[name='customerUserName']").val($newCode.customerUserName).blur();
            $("#userName").val($newCode.customerUserName);
            $("#projectSetupId").val($newCode.projectSetUpId).blur();
            $("#projectSetupName").val($newCode.projectSetName).blur();
            if ($.trim($newCode.projectSetName).length > 0) {
                //筛选 客户信息 和  投保内容险种信息
                $("select[name='customerUserType']").html("<option value='COMPANY_CUSTOMER' selected='selected'>团体客户</option>");
                var userIds = [];
                //超权限关联的客户
                var customerUserIds = $newCode.customers;
                if (customerUserIds) {
                    userIds = eval("("+customerUserIds+")");
                }
                cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=COMPANY_CUSTOMER&companys='+userIds;//更新客户选择请求地址
                //超权限管理的险种
                var catalog =  $newCode.catalogIds;
                if (catalog) {
                    var catalogIds = eval("("+catalog+")");
                    $("#projectSetUpCatalogIds").val(catalogIds);
                }
            } else {
                if ($newCode.customerType == "INDIVIDUAL_CUSTOMER") {
                    $("select[name='customerUserType']").html("<option value='INDIVIDUAL_CUSTOMER' selected='selected'>个人客户</option>");
                    cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=INDIVIDUAL_CUSTOMER';//更新客户选择请求地址

                } else {
                    $("select[name='customerUserType']").html("<option value='COMPANY_CUSTOMER' selected='selected'>团体客户</option>");
                    cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=COMPANY_CUSTOMER';//更新客户选择请求地址
                }
            }

            //个人客户，没有关联超权限(关联了超权限的，都是团体客户)
            if ($newCode.customerType == "INDIVIDUAL_CUSTOMER" && $.trim($newCode.projectSetName).length == 0) {
                $(".individual_customer").removeClass("fn-hide").find("input").removeAttr("disabled");
                $(".company_customer").addClass("fn-hide").find("input").attr("disabled",true);
                //带出客户的性别、生日
                var sexVal =  $("input:radio[name='customerUserSex']");

                $.each(sexVal,function(i,v){
                    var _this = $(this);
                    if (_this.val() == $newCode.sex) {
                        _this.attr("checked","checked");
                    }
                });
                $("input[name='customerUserBirth']").val($newCode.birth).blur();
            } else {
                $("#customerUserAddress").val($newCode.unitAddress).blur();
                $(".company_customer").removeClass("fn-hide").find("input").removeAttr("disabled");
                $(".individual_customer").addClass("fn-hide").find("input").attr("disabled",true);
            }

            //保险项目-----
            $("#project").val($newCode.projectSetName).blur();
        }
    });
    $body.on("click",".chooseProductName",function () {
        var priceContactLetterId = $(this).parents("tr").find("a:last").attr("detailInfos");
        var newPriceContactLetterId;
        newPriceContactLetterId = JSON.parse(priceContactLetterId);
        // var list = newPriceContactLetterId.lists[0];
        var infoTable =  $body.find(".itemLineTable");
        var html = "";
        infoTable.html("");
        $.each(newPriceContactLetterId.lists,function (k,v) {
            html = '<tr><td class="fn-tac"><input type="text" name="productName" class="text productName" value="'+v.productName+'" readonly></td>'+
            '<td class="fn-tac"><input class="text insuranceAmount" type="text" name="insuranceAmount" value="'+v.limitAmount+'" readonly></td>'+
            '<td class="fn-tac"><input class="text premiumAmount" type="text" name="premiumAmount" value="'+v.premiumAmount+'" readonly></td>'+
            '<td class="fn-tac"><input class="text brokerAmount" type="text" name="brokerAmount" value="'+v.borkerAmount+'" readonly></td></tr>';
            infoTable.append(html);
        });



        var Html =$(".TMP_INFO3");
            var wnd = new Y.Window({
                renderTo:'body',
                content:Html,
                modal: true,
                title:'详情'
            });
            wnd.show();
    });
    $body.on("click",".chooseDetailInfos",function () {
        var priceContactLetterId = $(this).attr("detailInfos"),newPriceContactLetterId;
        newPriceContactLetterId = JSON.parse(priceContactLetterId);
        var list = newPriceContactLetterId.lists[0];
        var detailInfosTable =  $body.find("table.notLifeInsurance");
        // productName  productId insuranceAmount premiumAmount brokerAmount
        // detailInfosTable.find("input[name*='productName']").val(list.productName).blur();
        // detailInfosTable.find("input[name*='productId']").val(list.productId).blur();
        // detailInfosTable.find("input[name*='insuranceAmount']").val(list.insuranceAmount).blur();
        // detailInfosTable.find("input[name*='premiumAmount']").val(list.premiumAmount).blur();
        // detailInfosTable.find("input[name*='brokerAmount']").val(list.borkerAmount).blur();
        //
        var html = "";
        detailInfosTable.find("tr.itemLine").remove();
        detailInfosTable.append("");
        $.each(newPriceContactLetterId.lists,function (k,v) {
            html = '<tr trflag="delFlag" diyname="insuranceContactLetterDetailOrders" class="itemLine trflag "><td class="fn-tac"><input type="text" name="productName" class="text productName" value="'+v.productName+'" readonly><input type="hidden" name="productId" class="productId" value="'+v.productId+'"></td>'+
                '<td class="fn-tac"><input class="text insuranceAmount" type="text" name="insuranceAmount" value="'+v.limitAmount+'" readonly></td>'+
                '<td class="fn-tac"><input class="text premiumAmount" type="text" name="premiumAmount" value="'+v.premiumAmount+'" readonly></td>'+
                '<td class="fn-tac"><input class="text brokerAmount" type="text" name="brokerAmount" value="'+v.borkerAmount+'" readonly></td>'+
                '<td class="fn-tac">--</td></tr>';

            detailInfosTable.append(html);
        });

        $body.find(".showBtn").hide();

    });
    //--------------------------


    var cumstorInfo = new getList();
    cumstorInfo.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json?customerType=' + $('[name="customerUserType"]').val(),
        btn: '#fnToChooseCustomer',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{item.name}}</td>',
                '        <td title="{{item.customerType}}">{{item.customerType}}</td>',
                '        <td title="{{item.certType}}">{{item.certType}}</td>',
                '        <td title="{{item.certNo}}">{{item.certNo}}</td>',
                '<td><a class="choose" findCustomerCertList="{{item.findCustomerCertList}}" sex="{{item.sex}}" birthday="{{item.birthday}}" address="{{item.address}}" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}" unitAddress="{{item.unitAddress}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['客户名称：',
                '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
                '证件类型：',
                '<select class="ui-select fn-w200 fn-validate" name="certType">'
                +'<option value="">请选择</option>'
                +'<option value="IDENTITY_CARD">身份证</option>'
                +'<option value="ARMY_IDENTITY_CARD">军官证</option>'
                +'<option value="STUDENT_CARD">学生证</option>'
                +'<option value="PASSPORT">护照</option>'
                +'<option value="OTHER">其他证件</option>'
                +'<option value="BUSINESS_LICENSE">营业执照</option>'
                +'<option value="TAX_REGISTRATION_CERTIFICATE">税务登记证</option>'
                +'<option value="ORGANIZATION_CODE_CERTIFICATE">组织机构代码证</option>'
                +'<option value="BUSINESS_PERMIT_CERTIFICATE">业务许可证</option>'
                +'</select>',
                '&nbsp;&nbsp;',
                '证件号码：',
                '<input class="ui-text fn-w100" type="text" name="certNo">',
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
            //如果是寿险，获取客户的生日信息
            var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
            var isQuotaVal = $('[name=isQuota]:checked').val();//获取定额的值
            //投保人、被投保人默认客户
            $("#policyHolder").val($a.attr("name")).blur();
            $("#policyHolderId").val($a.attr("userId")).blur();
            $(".certType1").html($a.attr("certType")).blur();
            $(".certNo1").html($a.attr("certNo")).blur();
            $("#certType1").val($a.attr("certType")).blur();
            $("#certNo1").val($a.attr("certNo")).blur();
            
            //清空询价单信息
            $("#priceContactId").val(0);
            $("#priceContactNo").val("");
            $("#priceContactName").val("");
            
            $("#customerUserName").val($a.attr("name")).blur();
            $("#customerUserId").val($a.attr("userId")).blur();
            $("#certType").val($a.attr("certType")).blur();
            $("#certNo").val($a.attr("certNo")).blur();

            $("#letter_customer").val($a.attr("name")).blur();
            $("#userName").val($a.attr("name")).blur();
            $("#detailAddress").val($a.attr("address")).blur();
            $("#customerUserAddress").val($a.attr("unitAddress")).blur();
           var sexVal =  $("input:radio[name='customerUserSex']");
           $.each(sexVal,function(i,v){
        	  var _this = $(this);
        	  if (_this.val() == $a.attr("sex")) {
        		  _this.attr("checked","checked");
        	  }
           });
           var findCustomerCertList = $a.attr("findCustomerCertList");
           if (findCustomerCertList) {
        	   var obj = eval("("+findCustomerCertList+")");
        	   var _content = "<option value=''>请选择</option>";
        	   $.each(obj,function(i,v){
        		   _content +="<option value='"+$(v).attr("certType")+"' certNo='"+$(v).attr("certNo")+"'>"+$(v).attr("certTypeName")+"</option>";
        	   });
        	   $("#customerCertType").html(_content);
        	   $("#certNo").val("");
           }
            $("input[name='customerUserBirth']").val($a.attr("birthday")).blur();
            if(InsuranceType == 'noIsLifeInsurance' && isQuotaVal == 'NO'){//当为非寿险非定额才进行此操作
                $.ajax({
                    url: "/insurance/insuranceProduct/getCustomerCertInfo.json",
                    type:"POST",
                    dataType:"json",
                    data: {customerUesrId:$a.attr("userId")},
                    success: function(res) {
                        if (res.success) {
                            var _temp = "";
                            var data = res.returnObject;
                            if (data != '') {
                                $(".certInfo").removeClass("fn-hide")
                                $.each(data,function(i,v){
                                    _temp += "      <div class=\"itemLine\" diyname=\"certOrders\">"+
                                        "                        <table class=\"m-table\" style=\"margin: 0 0 20px 0;\">"+
                                        "                            <tr>"+
                                        "                                <td class=\"fn-w100\" align=\"right\"><span class=\"m-required\">*</span>证件类型：</td>"+
                                        "                                <td><input type=\"hidden\" value=\""+v.userId+"\" name=\"certOrders["+i+"].userId\"><input type=\"hidden\" value=\""+v.id+"\" name=\"certOrders["+i+"].certId\"> "+v.certTypeName+"</td>"+
                                        "                            </tr>"+
                                        "                            <tr>"+
                                        "                                <td class=\"fn-w100\" align=\"right\"><span class=\"m-required\">*</span>证件号码：</td>"+
                                        "                                <td>"+v.certNo+"</td>"+
                                        "                            </tr>"+
                                        "                            <tr>"+
                                        "                                <td class=\"fn-w100\" align=\"right\">证件有效期：</td>"+
                                        "                                <td>"+v.certExpDate+"</td>"+
                                        "                            </tr>"+
                                        "                            <tr>"+
                                        "                                <td class=\"fn-w100\" align=\"right\">上传证件影像：</td>"+
                                        "                                <td>"+
                                        "                                    <div class=\"fnUpAttach\">"+
                                        "                                        <a href=\"javascript:void(0);\" class=\"ui-btn ui-btn-fill ui-btn-blue fnUpAttachBtn\"><i class=\"icon icon-add\"></i>上传附件</a>"+
                                        "                                        <input class=\"fnUpAttachVal \" type=\"hidden\">"+
                                        "                                        <div class=\"fn-blank5\"></div>"+
                                        "                                        <div class=\"m-attach fnUpAttachUl\"></div>"+
                                        "                                    </div>"+
                                        "                                </td>"+
                                        "                            </tr>"+
                                        "                            <tr>"+
                                        "                             <td class=\"fn-w100\" align=\"right\">改证件是否提交保险公司：</td>"+
                                        "                                <td><input type=\"checkbox\" onclick=checkThis(this,"+i+") /> 是<input type=\"hidden\" value=0 name=\"certOrders["+i+"].submitVal\" class=index"+i+"></td>"+
                                        "                            </tr>"+
                                        "                        </table>"+
                                        "                    </div>";
                                })
                                $certInfo.html(_temp);
                            } else {
                                $(".certInfo").addClass("fn-hide")
                                $certInfo.html("");
                            }
                        } else {
                            Y.alert('提示', res.message);
                        }
                    }
                });
            }else {
                $certInfo.html('');
            }


        }
    });


    var customerChoose1 = new getList();
    var customerChoose2 = new getList();
    customerChoose1.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json',
        btn: '#fnToChooseCustomer1',
        //multiple: true,
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="itemLine">',
                '        <td class="fn-tac"title="{{item.name}}">{{item.name}}</td>',
                '        <td class="fn-tac"title="{{item.certType}}">{{item.certType}}</td>',
                '        <td class="fn-tac"title="{{item.certNo}}">{{item.certNo}}</td>',
                '        <td class="fn-tac fn-hide listData" userid="{{item.userId}}"><a href="/insurance/customer/{{item.customerTypeCode == "INDIVIDUAL_CUSTOMER" ? "person" : "company"}}/info.htm?customerId={{item.customerId}}" target="_blank">[ 详情 ]</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="del">[ 删除 ]</a></td>',
                '        <td class="fn-tac"><a class="choose" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['客户名称：',
                '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
                '证件类型：',
                '<select class="ui-select fn-w200 fn-validate" name="certType">'
                +'<option value="">请选择</option>'
                +'<option value="IDENTITY_CARD">身份证</option>'
                +'<option value="ARMY_IDENTITY_CARD">军官证</option>'
                +'<option value="STUDENT_CARD">学生证</option>'
                +'<option value="PASSPORT">护照</option>'
                +'<option value="OTHER">其他证件</option>'
                +'<option value="BUSINESS_LICENSE">营业执照</option>'
                +'<option value="TAX_REGISTRATION_CERTIFICATE">税务登记证</option>'
                +'<option value="ORGANIZATION_CODE_CERTIFICATE">组织机构代码证</option>'
                +'<option value="BUSINESS_PERMIT_CERTIFICATE">业务许可证</option>'
                +'</select>',
                '&nbsp;&nbsp;',
                '证件号码：',
                '<input class="ui-text fn-w100" type="text" name="certNo">',
                '<input class="ui-text fn-w100" type="hidden" name="customerType">',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>客户名称</th>',
                '<th>证件类型</th>',
                '<th>证件号码</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            $("#policyHolder").val($a.attr("name")).blur();
            $("#policyHolderId").val($a.attr("userId"));
            $(".certType1").html($a.attr("certType"));
            $(".certNo1").html($a.attr("certNo"));
            $("#certType1").val($a.attr("certType"));
            $("#certNo1").val($a.attr("certNo"));
        }
    });


    customerChoose2.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json',
        btn: '#fnToChooseCustomer2',
        //multiple: true,
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="itemLine">',
                '        <td class="fn-tac"title="{{item.name}}">{{item.name}}</td>',
                '        <td class="fn-tac"title="{{item.certType}}">{{item.certType}}</td>',
                '        <td class="fn-tac"title="{{item.certNo}}">{{item.certNo}}</td>',
                '        <td class="fn-tac fn-hide listData" userid="{{item.userId}}"><a href="/insurance/customer/{{item.customerTypeCode == "INDIVIDUAL_CUSTOMER" ? "person" : "company"}}/info.htm?customerId={{item.customerId}}" target="_blank">[ 详情 ]</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="del">[ 删除 ]</a></td>',
                '        <td class="fn-tac"><a class="choose" birth="{{item.birthday}}" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['客户名称：',
                '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
                '证件类型：',
                '<select class="ui-select fn-w200 fn-validate" name="certType">'
                +'<option value="">请选择</option>'
                +'<option value="IDENTITY_CARD">身份证</option>'
                +'<option value="ARMY_IDENTITY_CARD">军官证</option>'
                +'<option value="STUDENT_CARD">学生证</option>'
                +'<option value="PASSPORT">护照</option>'
                +'<option value="OTHER">其他证件</option>'
                +'<option value="BUSINESS_LICENSE">营业执照</option>'
                +'<option value="TAX_REGISTRATION_CERTIFICATE">税务登记证</option>'
                +'<option value="ORGANIZATION_CODE_CERTIFICATE">组织机构代码证</option>'
                +'<option value="BUSINESS_PERMIT_CERTIFICATE">业务许可证</option>'
                +'</select>',
                '&nbsp;&nbsp;',
                '证件号码：',
                '<input class="ui-text fn-w100" type="text" name="certNo">',
                '<input class="ui-text fn-w100" type="hidden" name="customerType" value="INDIVIDUAL_CUSTOMER">',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>客户名称</th>',
                '<th>证件类型</th>',
                '<th>证件号码</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            //如果是寿险，获取客户的生日信息
            var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
            var birth = $a.attr("birth");
            if(InsuranceType == 'isLifeInsurance'){
                $.ajax({
                    url: "/insurance/insuranceProduct/getCustomerCertInfo.json",
                    type:"POST",
                    dataType:"json",
                    data: {customerUesrId:$a.attr("userId")},
                    success: function(res) {
                        if (res.success) {
                            var data = res.returnObject;
                            if (data == "") {
                                Y.alert('提示','请先完善个人身份证信息');
                                return;
                            } else {
                                var flag = false;
                                var errorFlag = false;
                                var certNo = "";
                                $.each(data,function(i,v){
                                    var certType = $(v).attr("certType");
                                    if (certType == "IDENTITY_CARD") {
                                        certNo = $(v).attr("certNo");
                                        if (!/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(certNo)) {
                                            errorFlag = true;
                                        }
                                        flag = true;
                                        return;
                                    }
                                });
                                if (!flag) {
                                    Y.alert('提示','请先完善个人身份证信息');
                                    return;
                                }
                                if (errorFlag) {
                                    Y.alert('提示','客户证件身份证格式错误,请更改');
                                    return;
                                }
                                var sex = (certNo.substr(16,1))%2;
                                $("#idCard").val(certNo);
                                sex == 0 ? $("#sex").val("woman"):$("#sex").val("man");
                                $("#insuredPerson").val($a.attr("name")).blur();
                                $("#insuredPersonId").val($a.attr("userId"));
                                $(".certType2").html($a.attr("certType"));
                                $(".certNo2").html($a.attr("certNo"));
                                $("#certType2").val($a.attr("certType"));
                                $("#certNo2").val($a.attr("certNo"));
                            }
                        }
                    }
                });
            }
        }
    });
    
    function isEmptyObject(e) {  
        var t;  
        for (t in e)  
            return !1;  
        return !0  
    } 



   var initCompany  =  (new getList()).init({
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
            //清空保险产品
            $("#productId").val("");
            $("#productName").val("");
            $("#guaranteePeriod").val("");
            $("#productLevelId").html("<option value=\"\">请选择</option>");
            $("#premiumAmount").val("");
            //清空投保内容(判断类型)
            //如果是寿险，获取客户的生日信息
            var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
            var isQuotaVal = $('[name=isQuota]:checked').val();//获取定额的值
            
            //寿险
            if (InsuranceType == "isLifeInsurance") {
            	var $TR = $("div.lifeInsurance").find('tr[diyname="insuranceContactLetterDetailOrders"]');
            	$TR.find('input[name*="productName"]').val("");
            	$TR.find('.period').html('<select class="ui-select fn-w200 fn-validate"><option value=\"\">请选择</option></select>');
            	$TR.find('input[name*="insuranceAmount"]').val("");
            	$TR.find('input[name*="firstPremiumAmount"]').val("");
            } else {
            	//定额
            	if (isQuotaVal == "YES") {
                //非定额，如果关联了询价单信息，根据询价单的id 和 保险公司的id 查询对应的报价的产品
            	} else {
            		//ajax查询询价单中报价产品信息
            		//判断是否关联了询价单信息
            		if ($.trim($("#priceContactNo").val()).length > 0) {
            			$.ajax({
            				url: "/baseDataLoad/queryReportPrice.json",
            				type:"POST",
            				dataType:"json",
            				data: {customerUserId:$a.attr("userid"),priceContactLetterId:$("#priceContactId").val()},
            				success: function(res) {
            					if (res.success) {
            			    		var reportPrice = res.data.reportPrice;
            	            		if (reportPrice) {
            	            			var obj =  eval("("+reportPrice+")");
            	            			$.each(obj,function(index,value){
            	            				//产品的id
            	            				var productId = $(value).attr("productId");
            	            				priceProducts.push(productId);
            	            			});
            	            			console.log(priceProducts);
            	            		}
            					}
            				}
            			});
            		}
            	}
            }
           
        }
    });
    
    
// 必备参数
var scope = '{type:\"system\",value:\"all\"}';
var selectUsers = {
    selectUserIds: '', //已选id,多用户用,隔开
    selectUserNames: '' //已选Name,多用户用,隔开
}

var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
    'title': '人员',
    'width': 850,
    'height': 460,
    'scope': scope,
    'selectUsers': selectUsers,
    'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
    'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
});

$body.on("click","#fnToChooseCompany",function(){
     var param = (arry.length > 0) ? '?companys=' + arry : '';
     initCompany.ajaxUrl = '/baseDataLoad/customer.json' + param;
}).on("click",".fnToBusinessPerson",function(){
        var $this = $(this),
        $thisParent = $this.parent();
    var $names = $thisParent.find("input[name='businessUserName']"),
        $ids = $thisParent.find("input[name='businessUserId']");
    singleSelectMemberNamesDialog.obj.selectUsers={
        selectUserNames:$names.val(),
        selectUserIds:$ids.val()
    }
    singleSelectMemberNamesDialog.init(function(relObj) {
        $names.val(relObj.fullnames).trigger('blur');
        $ids.val(relObj.userIds).trigger('blur');
    });
}).on('change','.lifeInsuranceType', function () {
        var thisVal = $(this).val();
        $("input:radio[name='isQuota']").attr("checked",false);
        var isLifeInsurance = thisVal == 'isLifeInsurance';
        if(isLifeInsurance) {
            $('.isQuota').addClass('fn-hide').find('.fn-validate').addClass('ignore').attr('disabled','disabled');
            $('.lifeInsuranceMod').not('.lifeInsurance').addClass('fn-hide').find('[name]').attr('disabled','disabled');
            $('.lifeInsurance').removeClass('fn-hide').find('[name]').removeAttr('disabled');
        }else {
            $('.isQuota').removeClass('fn-hide').find('.fn-validate').removeClass('ignore').removeAttr('disabled');
            $('.lifeInsuranceMod').addClass('fn-hide').find('[name]').attr('disabled','disabled');
        }
        $("#customerUserName").val("");
        $("#customerUserId").val("");
        $("#letter_customer").val("");
        $("#certType").val("");
        $("#certNo").val("");
        $("#detailAddress").val("");
    }).on('click', '.productBtn', function(event) {
        var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
        var isQuotaVal = $('[name=isQuota]:checked').val();//获取定额的值
        var insuredPerson = $.trim($("#insuredPerson").val());
        if (!insuredPerson && InsuranceType == "isLifeInsurance") {
            Y.alert("提示信息","请填写被保险人的信息");
            return ;
        }
        if ($.trim($("#companyUserName").val()).length == 0) {
            Y.alert("提示信息","请选择保险公司相关信息");
            return ;
        }
        //寿险产品过滤，非寿险非定额产品过滤

        var $thisProduct = $(this);
        var productIds = [];
       
        $.each($thisProduct.parents('table').find('[name*=productId]'),function (i,e) {
            if(!!$(e).val()) productIds.push($(e).val());
        });
        var fundDitch = new popupWindow({

            YwindowObj: {
                content: 'selectProductPopup', //弹窗对象，支持拼接dom、template、template.compile
                closeEle: '.close', //find关闭弹窗对象
                dragEle: '.newPopup dl dt' //find拖拽对象
            },

            ajaxObj: {
                url: '/baseDataLoad/product.json?productIds=' + productIds.join(",")+"&priceProducts="+priceProducts.join(","),
                type: 'post',
                data:{"projectSetUpCatalogIds":$("#projectSetUpCatalogIds").val(),"isLifeInsurance":InsuranceType == "isLifeInsurance"?"YES":"NO","saleStatus":0,"isQuota":isQuotaVal,"companyUserId":($("#companyUserId").val() == null || $("#companyUserId").val() == "")?0:$("#companyUserId").val()},
                dataType: 'json',
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

                    var $this, productName, productId, companyUserName, arrHtml, $product;

                    $this = $(this);
                    productName = $this.attr('productName');
                    productId = $this.attr('productId');
                    companyUserName = $this.attr('companyUserName');

                    $thisProduct.siblings('.productName').val(productName).blur();
                    $thisProduct.siblings('.productId').val(productId);

                    //清空保额 和 保费 和 缴费期限
                    $thisProduct.parents('tr').find('[name*=insuranceAmount]').val('');
                    $thisProduct.parents('tr').find('[name*=firstPremiumAmount]').val('');

                    //查询该该投保人 在该产品上的缴费期限
                    var sex = $("#sex").val();
                    var insuranceType = $("select[name='insuranceType']").val();
                    var _url = "/insurance/insuranceProtocolCharge/getInsuranceProductCharge.json";
                    if (insuranceType == "isLifeInsurance") {
                    	$.ajax({
                    		url: _url,
                    		type:"POST",
                    		dataType:"json",
                    		data: {chargeType:sex,productId:productId},
                    		success: function(res) {
                    			if (res.success) {
                    				var target = $thisProduct.parents("td").siblings(".period");
                    				var _temp= '<select name="period" class="ui-select fn-w200 fn-validate getInsuranceCharge"><option value="">请选择</option>';
                    				var data = res.data;
                    				if (insuranceType == "isLifeInsurance" && isEmptyObject(data)) {
                    					Y.alert("提示信息",'请先设置该产品的费率信息');
                    					_temp += "</select>";
                    					target.html(_temp);
                    					return ;
                    				}
                    				
                    		
                    				var chargeInfo = data.insuranceProtocolChargeInfos;
                    				var insuranceProdcts = data.insuranceProducts;
                    				//缴费类型
                    				var $payTypeTr = $thisProduct.parents("tr").find("input[name*=payType]");
                    				//该产品是否属于主险
                    				var $catalogType = $thisProduct.parents("tr").find("input[name*=catalogType]");
                    				//该产品的基本保障金额
                    				var $unitPrice =  $thisProduct.parents("tr").find("input[name*=unitPrice]");
                    				//分期比例
                    				var $periodRate =  $thisProduct.parents("tr").find("input[name*=periodRate]");
                    				//投保内容选择的首个产品必须是主险产品
                    				var firstProductCatalogType = $thisProduct.parents("table").find("tr").eq(1).find("input[name*=catalogType]").val();
                    				if (!firstProductCatalogType) {
                    					if (insuranceProdcts.catalogType == "NO") {
                    						Y.alert('提示信息','必须选择主险产品');
                    						return ;
                    					}
                    				}else {
                    					//从第二行开始
                    					if (insuranceProdcts.catalogType == "YES" && $thisProduct.parents("table").find("tr").index($thisProduct.parents("tr")) >= 2) {
                    						Y.alert('提示信息','只能选择一个主险产品');
                    						return ;
                    					}
                    				}
                    				if (insuranceProdcts.payType == "一次性") {
                    					_temp += '<option value="'+0+'">趸交</option>';
                    				} else {
                    					if (chargeInfo != "" ) {
                    						$.each(chargeInfo,function(i,v){
                    							if (v.val == 1) {
                    								_temp += '<option value="' + v.val + '">趸交</option>';
                    								return ;
                    							}
                    							_temp += '<option value="' + v.val + '">' + v.val + '年</option>';
                    						});
                    					}
                    				}
                    				_temp += "</select>";
                    				target.html(_temp);
                    				$payTypeTr.val(insuranceProdcts.payType);
            						$catalogType.val(insuranceProdcts.catalogType);
            						$unitPrice.val(insuranceProdcts.unitPrice.amount);
            						$periodRate.val(insuranceProdcts.periodRate);
                    			}
                    		}
                    	});
                    }

                    $Y.close();

                });

            },

            console: true //打印返回数据

        });

    }).on('click', '.chooseInsuranceProduct', function(event) {
    	 if ($.trim($("#companyUserName").val()).length == 0) {
             Y.alert("提示信息","请选择保险公司相关信息");
             return ;
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
                data:{"projectSetUpCatalogIds":$("#projectSetUpCatalogIds").val(),"isLifeInsurance":"NO","saleStatus":0,"isQuota":"YES","companyUserId":($("#companyUserId").val() == null || $("#companyUserId").val() == "")?0:$("#companyUserId").val()},
                dataType: 'json',
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

                    var $this, productName,productId,insurancePeriod;
                    $this = $(this);
                    productName = $this.attr('productName');
                    productId = $this.attr('productId');
                    insurancePeriod = $this.attr('insurancePeriod');
                    $("#productName").val(productName).blur();
                    $("#productId").val(productId);
                    $("#premiumAmount").val("");
                    $("#guaranteePeriod").val(insurancePeriod).blur();
                    //查询产品的档次信息
                    $.ajax({
                        url: "/insurance/insuranceProduct/getInsuranceProductLevel.json",
                        type:"POST",
                        dataType:"json",
                        data: {productId:productId},
                        success: function(res) {
                            if (res.success) {
                                var data = res.returnObject;
                                var _temp = "<option value=\"\">请选择</option>";
                                $.each(data,function(index,value){
                                    _temp += "<option value="+value.levelId+" premiumamount="+value.premiumAmount.amount+">"+value.level+"</option>";
                                });
                                $("#productLevelId").html(_temp);
                            } else {
                                Y.alert('提示', res.message);
                            }
                        }
                    });
                    $Y.close();

                });

            },

            console: true //打印返回数据

        });

    }).on('click', '.clearInsuranceProduct', function(event) {
        $("#productId").val("");
        $("#productName").val("");
        $("#guaranteePeriod").val("");
        $("#premiumAmount").val("");
        $("#productLevelId").html("<option value=''>请选择</option>");
    }).on("change","#customerCertType",function(){
    	var _self = $(this);
    	var certNo = _self.find("option:selected").attr("certNo");
    	$("#certNo").val(certNo);
    }).on('click', '.clearBtn', function(event) {
        $(this).siblings('[name]').val('');
    }).on('click', '.fnToClearProjectSetupName', function(event) {
    	//清除超权限
        $("#projectSetupName").val("");
        $("#projectSetupId").val("");
        //清除询价单
        $("#priceContactNo").val("");
        $("#priceContactId").val("");
        $("#priceContactName").val("");
        //清除客户信息
        $("select[name='customerUserType']").html("<option value=''>请选择</option><option value='INDIVIDUAL_CUSTOMER'>个人客户</option><option value='COMPANY_CUSTOMER'>团体客户</option>");
        $('.fnToClearCustomer').click();
        $("#userName").val("");
        $("#businessUserName").val("");
        $("#businessUserId").val("");
        $("input[name='customerUserBirth']").val("");
        $("input:radio[name='customerUserSex']").attr("checked",false);
        cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json';//更新客户选择请求地址
        initCompany.ajaxUrl = '/baseDataLoad/customer.json' ; //更新保险公司请求地址
        $("#projectSetUpCatalogIds").val("");
    }).on('change', '.customerUserType', function(event) {
        var _thisVal = $(this).val();
        if (_thisVal == "INDIVIDUAL_CUSTOMER") {
        	$(".individual_customer").removeClass("fn-hide").find("input").removeAttr("disabled");
        	$(".company_customer").addClass("fn-hide").find("input").attr("disabled",true);
        } else {
        	$(".individual_customer").addClass("fn-hide").find("input").attr("disabled",true);
        	$(".company_customer").removeClass("fn-hide").find("input").removeAttr("disabled");
        }
        $('.fnToClearCustomer').click();
        cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=' + _thisVal;//更新客户选择请求地址
    }).on('change', 'input:radio[name=isCarSales]', function(event) {
        if ($(this).val() == "YES") {
            $(".plateNumber").removeClass("fn-hide").find("input").removeAttr("disabled");
            $("#plateNumber").addClass("fn-validate");
        } else {
            $(".plateNumber").addClass("fn-hide").find("input").attr("disabled",true);
            $("#plateNumber").removeClass("fn-validate");
        }
    }).on('change', 'select[name="productLevelId"]', function(event) {
        var premiumAmount = $(this).find('option:selected').attr("premiumamount");
        $("#premiumAmount").val(premiumAmount).blur();
    }).on('change','input:radio[name=isQuota]', function () {
        var isQuota = $(this).val();
        if(!isQuota) return;
        //定额
        if(isQuota  == 'YES') {
            $('.priceContactNo').addClass('fn-hide').find('.fn-validate').addClass('ignore').val('');
            $('.lifeInsuranceMod').not('.notLifeInsuranceQuota').addClass('fn-hide').find('[name]').attr('disabled','disabled');
            $('.notLifeInsuranceQuota').removeClass('fn-hide').find('[name]').removeAttr('disabled');
            //非定额
        }else {
            $('.priceContactNo').removeClass('fn-hide').find('.fn-validate').removeClass('ignore');
            $('.lifeInsuranceMod').not('.notLifeInsurance').addClass('fn-hide').find('[name]').attr('disabled','disabled');
            $('.notLifeInsurance').removeClass('fn-hide').find('[name]').removeAttr('disabled');
        }
    }).on('click','.benefitPlan',function () {
        var $this = $(this);
        var $benefitPlanBox = $('.benefitPlanBox');
        var $table =$benefitPlanBox.find('table');
        var radioTempalteId = $this.attr('templateid');
        var addTempalteId = $benefitPlanBox.find('.addLine').attr('templateid');
        if(!radioTempalteId || radioTempalteId == addTempalteId) return;
        // console.log(radioTempalteId)
        if(radioTempalteId == 'TMP_BENEFIT_PLAN_ORDER'){
            $table.find('tr:first th ').eq(1).addClass('fn-hide');
            $table.find('tr:first th').eq(0).removeClass('fn-hide');
            $benefitPlanBox.find('.addLine').attr('templateid','TMP_BENEFIT_PLAN_ORDER');
            $benefitPlanBox.find('.allScaleNum').addClass('ignore');
        }else {
            $table.find('tr:first th').eq(0).addClass('fn-hide')
            $table.find('tr:first th').eq(1).removeClass('fn-hide')
            $benefitPlanBox.find('.addLine').attr('templateid','TMP_BENEFIT_PLAN_SCALE');
            $benefitPlanBox.find('.allScaleNum').removeClass('ignore');
        }
        $table.find('tr').not(':first').remove();
      //通用清除选择
    }).on('click','.fnToClear',function () {
        var $this = $(this);
        var $thisP = $this.parents('.fnToClearBox').length == 1 ? $this.parents('.fnToClearBox') : $this.parent();
        var callBack = $this.attr('callback')
        $thisP.find('input,select,[name]').val('');
        if(!!callBack) eval(callBack + '()');
      //清除询价单关联
    }).on('click','.fnToClearCompany',function(){
    	var $this = $(this);
    	 $this.parent().find('input,select,[name]').val('');
    	 //清空保险产品
        $("#productId").val("");
        $("#productName").val("");
        $("#guaranteePeriod").val("");
        $("#productLevelId").html("<option value=\"\">请选择</option>");
        $("#premiumAmount").val("");
        priceProducts = [];        
        initCompany.ajaxUrl = "/baseDataLoad/customer.json?companys="+arry;
    }).on('click','.fnToClearPriceContact',function () {
        var $this = $(this);
        var $thisP = $this.parents('.priceContactNo');
        $thisP.find('input,select,[name]').val('');
        //清除询价中的保险公司
        arry = [];
        priceProducts = [];        
        $("#companyUserName").val("");
        $("#companyUserId").val("");
        initCompany.ajaxUrl = '/baseDataLoad/customer.json';
        cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json';
        //清除询价单中的客户
        $("select[name='customerUserType']").html("<option value=''>请选择</option><option value='INDIVIDUAL_CUSTOMER'>个人客户</option><option value='COMPANY_CUSTOMER'>团体客户</option>");
        $("#customerUserName").val("");
        $("#customerUserId").val("");
        $("#businessUserName").val("");
        $("#businessUserId").val("");
        $("#userName").val("");
        $("#customerUserAddress").val("");
        $("#projectSetupId").val("");
        $("#projectSetupName").val("");
        $("#projectSetUpCatalogIds").val("");
        $("input[name='customerUserBirth']").val("");
        $("input:radio[name='customerUserSex']").attr("checked",false);

        $body.find("input[name*='productName']").val("");
        $body.find("input[name*='productId']").val("");
        $body.find("input[name*='insuranceAmount']").val("");
        $body.find("input[name*='premiumAmount']").val("");
        $body.find("input[name*='brokerAmount']").val("");

        $body.find(".showBtn").show();
        $body.find("table.notLifeInsurance").find("tr.trflag").remove();
        $("[templateid*='TMP_INFO2']").click();
}).on('click','.fnToChooseCustomer',function () {//选择客户名称

        var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
        var isQuotaVal = $('[name=isQuota]:checked').val();//获取定额的值
        if(InsuranceType == 'isLifeInsurance'){
            $('#fnToChooseCustomer').click();
        }else if(InsuranceType == 'noIsLifeInsurance'){
            if (!isQuotaVal){
                Y.alert('提示','请先选择是否定额产品投保！')
            }else {
                $('#fnToChooseCustomer').click();
            }
        }else {
            Y.alert('提示','请先选择投保类型！')
        }
    }).on('change','.getInsuranceCharge',function () {

        var $this = $(this);
        var $tr = $this.parents('tr');
        var productId = $tr.find('[name*=productId]').val();
        var sex = $("#sex").val();//性别
        var idCard = $("#idCard").val();//身份证号码
        //缴费类型
        var payType =   $tr.find('[name*=payType]').val();
        //缴费期限
        var period = $this.val();
        if (!period) {
        	Y.alert('提示',"请选择缴费期限");
        	return;
        }
        //主险的缴费期限
        var mainPeriod = $this.parents('table').find('tr').eq(1).find("select").find("option:selected").val();
        period = parseInt(period) > parseInt(mainPeriod) ? parseInt(mainPeriod) : parseInt(period);
        //保额
        var insuranceAmount = $tr.find('[name*=insuranceAmount]').val();
       
        var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
        if (InsuranceType == "isLifeInsurance") {
        	//查询该产品该年限对应的费率值
        	$.ajax({
        		url: "/insurance/insuranceProtocolCharge/getInsuranceProductChargeRate.json",
        		type:"POST",
        		dataType:"json",
        		data: {chargeType:sex,certNo:idCard,productId:productId,period:period},
        		success: function(res) {
        			if (res.success) {
        				var data = res.data.insuranceProtocolChargeInfo;
        				if (data != "") {
        					//获取该差陪你对应的费率
        					var val = data.val;
        					if (!val) {
        						Y.alert('提示信息',"没有对应的费率信息");
        						$this.parents('tr').find('select').val("");
        						return;
        					}
        					$tr.find('.productRateVal').val(val);
        					//计算首期保费
        					calcPremiumAmount(insuranceAmount,$this);
        					  //计算保费
        			        calcTotalPremiumAmount(insuranceAmount,$this);
        				}
        			} else {
        				Y.alert('提示', res.message);
        			}
        		}
        	});
        }
    }).on("blur",".insuranceAmount",function(){
        var $this = $(this);
        var _val = $this.val();
        
        var productId =  $this.parents('tr').find('[name*=productId]').val();
		if (!productId) {
			 Y.alert('提示','请先选择对应的产品信息');
				return ;
		}
		
        //主险产品
        //应该默认第一个产品就是主险，附加险的选择应该随后，不能先选择主险
        //首期保费的计算
        calcPremiumAmount(_val,$this);
        //计算保费
        calcTotalPremiumAmount(_val,$this);
      
    });
    
	/**
	 * 计算首期保费
	 * insuranceAmount 保额
	 * $this 当前对象tr
	 */
	function calcPremiumAmount(insuranceAmount,$this){
		if($.trim(insuranceAmount).length == 0){
			$this.parents('tr').find('[name*=firstPremiumAmount]').val("");
			return ;
		}
		
		//该产品的基本保额
		var unitPrice =  $this.parents('tr').find('[name*=unitPrice]').val();
		if (!unitPrice || unitPrice == "0") {
			 Y.alert('提示','请先设置该产品的基本保额');
			return ;
		}
		
		var result =  "" ;
		//主险产品的缴费类型
		var payType =   $this.parents('table').find('tr').eq(1).find("[name*=payType]").val();
		var thisType = $this.parents('tr').find('[name*=payType]').val();
		var catalogType =  $this.parents('tr').find('[name*=catalogType]').val();
		
	   //该产品费率表中对应年龄取值
        var productRateVal =  $this.parents('tr').find('[name*=productRateVal]').val();
        //分期的比例
        var periodRate =  $this.parents('tr').find('[name*=periodRate]').val();
        
        //附加险 ，缴费类型与主险不一致，一次性缴费（不分期）
        if (thisType != payType && catalogType == "NO") {
        	result =  (parseFloat(insuranceAmount/unitPrice) * productRateVal).toFixed(2) ;
        } else {
        	if (!periodRate || periodRate == 0.0) {
        		result =  (parseFloat(insuranceAmount/unitPrice) * productRateVal).toFixed(2) ;
        	} else {
        		result =  (parseFloat(insuranceAmount/unitPrice) * productRateVal * periodRate).toFixed(2) ;
        	}
        }
        
        
	
		 $this.parents('tr').find('[name*=firstPremiumAmount]').val(result);
	}
	
	/**
	 * 计算产品的保费
	 * insuranceAmount 保额
	 * $this 当前对象tr
	 */
	var mainPayType = "";
	function calcTotalPremiumAmount(insuranceAmount,$this) {
		if($.trim(insuranceAmount).length == 0){
			$this.parents('tr').find('[name*=firstPremiumAmount]').val("");
			return ;
		}
		//当前产品的叫缴费类型
		var payType = $this.parents('tr').find('[name*=payType]').val();
		//主险的缴费类型
		//对应分期的比例
		var periodRate = $this.parents('tr').find('[name*=periodRate]').val();
		//该产品是主险 还是附加险
		var catalogType = $this.parents('tr').find('[name*=catalogType]').val();
		//分配缴费年限
		var period = $this.parents('tr').find("select").find('option:selected').val();
		
		//该产品的基本保额
		var unitPrice =  $this.parents('tr').find('[name*=unitPrice]').val();
		
		//该产品费率表中对应年龄取值
        var productRateVal =  $this.parents('tr').find('[name*=productRateVal]').val();
		
		var totalPremiumAmount = 0.0;
		//主险保费的计算
		if (catalogType == "YES") {
			mainPayType = payType;
			var result =  (parseFloat(insuranceAmount/unitPrice) * productRateVal).toFixed(2) ;
			//不分期
			if (payType == "一次性" || payType == "年交") {
				//年交保费 * 缴费期限
				totalPremiumAmount = period*result;
			} else {
				//年交保费*分期缴费系数*分期数
				
				//分期数的计算
				var n = 0;
				if (payType == "月交") {
					n = period * 1 * 12;
				} else if (payType == "半年交") {
					n = period * 1 * 2;
				} else if (payType == "季交") {
					n = period * 1 * 4;
				}
				
				totalPremiumAmount = n*periodRate*result; 
			}
			$this.parents('tr').find('[name*=premiumAmount]').val(totalPremiumAmount);
		//附加险保费的计算
		} else {
			var productId = $this.parents('tr').find('.productId').val();
		    var sex = $("#sex").val();//性别
		    var idCard = $("#idCard").val();//身份证号码
			//如果主险的缴费类型 和 附加险的j
		    var formData = {
		    		productId:productId, //产品id
		    		mainPayType:mainPayType,//主险产品缴费类型
		    		periodRate:periodRate,//附加险产品对应缴费的类型的比例系数
		    		payType:payType,//附加险产品缴费类型
		    		period:period,//缴费期限
		    		chargeType:sex,//性别
		    		certNo:idCard, //身份证
		    		unitPrice:unitPrice,//该产品的基本保额
		    		insuranceAmount:insuranceAmount //保额
		     }
	         $.ajax({
                 url: "/insurance/insuranceProduct/calcTotalPremiumAmount.json",
                 type:"POST",
                 dataType:"json",
                 data: formData,
                 success: function(res) {
                     if (res.success) {
                         var data = res.returnObject;
                         $this.parents('tr').find('[name*=premiumAmount]').val(data.amount);
                     } else {
                         Y.alert('提示', res.message);
                     }
                 }
             });
		}
		
	}
    


    function getUrlVal(url){//获取url参数
        var urlValStr = url || window.location.search.replace('?','');
        var urlValArry = urlValStr.split('&');
        var urlValObject = {};
        for(var i in urlValArry){
            urlValObject[urlValArry[i].split('=')[0]] = urlValArry[i].split('=')[1];
        };
        return urlValObject;
    };
    
    function selectedLifeInsuranceType() {//获取选择寿险的类型以及是否定额

        var InsuranceType = $('[name=insuranceType]').val();//获取非寿险的值
        var isQuotaVal = $('[name=isQuota]:checked').val();//获取定额的值
        var reslt = '';
        if(InsuranceType == 'isLifeInsurance'){
            reslt = 'lifeInsurance';
        }else if(InsuranceType == 'noIsLifeInsurance'){
            if (isQuotaVal == 'NO'){
                reslt = 'notLifeInsurance';
            }else if(isQuotaVal == 'YES'){
                reslt = 'notLifeInsuranceQuota';
            }else{
                reslt = '';
            }
        }else {
            reslt = ''
        }
        //reslt值
        //lifeInsurance寿险
        //notLifeInsurance非寿险非定额
        //notLifeInsuranceQuota非寿险定额
        return reslt;
    }
    
    function fnToClearCustomer() {//清除客户名称要做的事情
        $(".certInfo").addClass("fn-hide")
        $certInfo.html('');
        $("#letter_customer").val("");
        $("#userName").val("");
        $("#certType").val("");
        $("#certNo").val("");
        $("#detailAddress").val("");
        $("#customerUserBirth").val("");
        $("#customerUserAddress").val("")
    };
});

