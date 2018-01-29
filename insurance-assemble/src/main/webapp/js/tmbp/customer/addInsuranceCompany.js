/**
 * Created by eryue
 * Create in 2017-01-04 13:54
 * Description:
 */

'use strict';
define(function(require, exports, module) {
    //项目管理>授信前管理> 立项申请
    require('Y-msg');
    require('zyw/upAttachModify');
    require('zyw/chooseRegion');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
    require('tmbp/chooseIndustry');


    var $body = $('body');
    var getList = require('zyw/getList');
    (new getList()).init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json?customerType=INSURANCE_INSTITUTIONS',
        btn: '#fnListContractingAgency',
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
            $("#parentName").val($a.attr("name"));
            $("#parentId").val($a.attr("userId"));
        }
    });
    $body.on('change','[name*="contactEmail"]',function () {
        var $this = $(this);
        var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        var emailVal = $this.val();
        $this.siblings('span').remove();
        if(!reg.test(emailVal)&& emailVal!=''){
                $this.parent().append("<span style='color: red;'>邮箱格式不对</span>");
        };

    });

    var getList = require('zyw/getList');
    var cumstorInfo = new getList();
    cumstorInfo.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json?customerType=INDIVIDUAL_CUSTOMER' ,
        btn: '.fnToChooseCostomerName',

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
            $("#contactMan").val($a.attr("name"));
            $("#contactManId").val($a.attr("userId"));
            customerContactInfo.ajaxUrl = '/baseDataLoad/queryCustomerMobile.json?userId='+$a.attr("userId");
        }
    });


    var customerContactInfo = new getList();
    customerContactInfo.init({
        title: '电话',
        ajaxUrl: '/baseDataLoad/queryCustomerMobile.json?userId=' +$("#contactManId").val() ,
        btn: '.fnToChooseCustomerContact',
        autoHide:true,
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.mobile}}">{{item.mobile}}</td>',
                '<td><a class="choose" mobile="{{item.mobile}}"   href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),

            thead: ['<th>电话</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            $("#contactMobile").val($a.attr("mobile"));
        }
    });


    $(function () {
        var branchOffice = $('.branchOffice');
        if(branchOffice.attr("checked")){
            $('.totalCompany').show().find("#parentName").removeClass("ignore");
        }else{
            $('.totalCompany').hide().find("#parentName").addClass("ignore");

        }
    });
    $body.on('change','[name*="companyNature"]',function () {
        var branchOffice = $('.branchOffice');
        if(branchOffice.attr("checked")){
            $('.totalCompany').show().find("#parentName").removeClass("ignore");
        }else{
            $('.totalCompany').hide().find("#parentName").addClass("ignore");

        }
    }).on("click",".fnToChooseCostomerNameClear",function(){
        $("#contactMan").val("");
        $("#contactManId").val("");
        $("#contactMobile").val("");
        customerContactInfo.ajaxUrl = '/baseDataLoad/queryCustomerMobile.json?userId='+$a.attr("userId");
    }).on("click",".fnToChooseCustomerContactClear",function(){
        $("#contactMobile").val("");
    });


});