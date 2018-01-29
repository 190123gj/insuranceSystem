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
    // require('zyw/chooseRegion');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
    require('tmbp/chooseIndustry');
    // require('zyw/chooseRegion');

    var chooseRegion =  require('tmbp/chooseRegionNew');
    var publicOPN = new(require('zyw/publicOPN'))(); //侧边栏
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数

    var chooseRegionTemp = new chooseRegion();
    chooseRegionTemp.init();

    var $body = $('body');

    // 必备参数
    var scope = '{type:\"system\",value:\"all\"}';
    var selectUsers = {
        selectUserIds: '', //已选id,多用户用,隔开
        selectUserNames: '' //已选Name,多用户用,隔开
    };

    var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': scope,
        'selectUsers': selectUsers,
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
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
            $(".fnToChooseCustomerContactClear").click();
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





// 添加选择后的回调，以及显示弹出层
    $body.on("click","#fnToChooseBusinessUserName",function(){
        var $names = $("#businessUserName"),
            $ids = $("#businessUserNameids");
        singleSelectMemberNamesDialog.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        singleSelectMemberNamesDialog.init(function(relObj) {

            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    }).on("click","#fnToClearBusinessUserName",function(){
        $("#businessUserId").val('');
        $("#businessUserName").val('');
    }).on("click",".fnToChooseCostomerNameClear",function(){
        $("#contactMan").val("");
        $("#contactManId").val("");
        $("#contactMobile").val("");
        $(".fnToChooseCustomerContactClear").click();
        customerContactInfo.ajaxUrl = '/baseDataLoad/queryCustomerMobile.json?userId='+$a.attr("userId");
    }).on("click",".fnToChooseCustomerContactClear",function(){
        $("#contactMobile").val("");
    });
    console.log($('isDetail').length)
    if($('.isDetail').length > 0){
        publicOPN.addOPN([{
            name: '客户信息跟踪',
            alias: 'customerInfoTrace',
            event: function () {
                $('#customerInfoTrace').click();
            }
        },{
            name: '查看客户协议',
            alias: 'viewCustomerPortocol',
            event: function () {
                $('#viewCustomerPortocol').click();
            }
        },{
            name: '查看保单信息',
            alias: 'viewWarrantyInfo',
            event: function () {
                $('#viewWarrantyInfo').click();
            }
        },{
            name: '查看询价记录',
            alias: 'viewPriceRecord',
            event: function () {
                $('#viewPriceRecord').click();
            }
        },{
            name: '查看理赔记录',
            alias: 'viewClaimsRecord',
            event: function () {
                $('#viewClaimsRecord').click();
            }
        },{
            name: '申请立项',
            alias: 'applyAddProject',
            event: function () {
                $('#applyAddProject').click();
            }
        },{
            name: '制作询报价方案',
            alias: 'addPricePlan',
            event: function () {
                console.log($('#addPricePlan').attr('href'))
                $('#addPricePlan').click();
            }
        },{
            name: '投保申请',
            alias: 'applyApplication',
            event: function () {
                $('#applyApplication').click();
            }
        }]);

        publicOPN.init().doRender(); //初始化侧边栏
    }
    $body.on('change','[name*="certType"]',function () {
        var $this = $(this);
        var certTypeClass = $(".certTypeClass");
        if($this.val() != 'OTHER'){
            $this.siblings(".certTypeClass").hide();
        }else {
            $this.siblings(".certTypeClass").show();
        }
    });


    //--------------------推荐人--------
    $body.on("click","#fnToChooseRefereeName",function(){
        var $names = $("#refereeName"),
            $ids = $("#refereeId");
        singleSelectMemberNamesDialog.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        singleSelectMemberNamesDialog.init(function(relObj) {

            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    }).on("click","#fnToClearRefereeName",function(){
        $("#refereeId").val('');
        $("#refereeName").val('');
    });

    $body.on("click","#addressNew",function () {
        var $this = $(this);
        var addressBox = $this.parents("table").find("tr:last")
        // var chooseRegion = new chooseRegion();
        chooseRegionTemp.init(
            addressBox
        );
    }).on("click",".addressDel",function () {
        var $this = $(this);
        $this.parents("tr").remove();
    });
});