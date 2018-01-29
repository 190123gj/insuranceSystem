define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('zyw/upAttachModify');
    require('tmbp/submit.common');
    require('tmbp/operate.common');

    require('zyw/chooseRegion');
    var chooseRegion =  require('tmbp/chooseRegionNew');
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    var getList = require('zyw/getList');
    var publicOPN = new(require('zyw/publicOPN'))(); //侧边栏

    var chooseRegionTemp = new chooseRegion();
    chooseRegionTemp.init();
    var $body = $('body');
    var template = new getList();
    var $GETLIST_TR = '';

    template.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json',
        btn: '.fnToChoose',// '.fnToChoosesss'
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{item.name}}</td>',
                // '        <td title="{{item.certType}}">{{item.certType}}</td>',
                '        <td title="{{item.certNo}}">{{item.certNo}}</td>',
                '        <td title="{{item.businessUserName}}">{{item.businessUserName}}</td>',
                '<td><a class="choose" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['姓名：',
                '<input class="ui-text fn-w100" type="text" name="customerName">',
                '<input class="ui-text fn-w100" type="hidden" name="customerType" value="INDIVIDUAL_CUSTOMER">',
                '证件号码：',
                '<input class="ui-text fn-w100" type="text" name="certNo">',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>姓名</th>',// '<th>客户名称</th>',
                // '<th>证件类型</th>',
                '<th>证件号码</th>',
                '<th>所属业务员</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            $GETLIST_TR.find(".parentNameClass").val($a.attr("name"));
            $GETLIST_TR.find(".parentIdClass").val($a.attr("userId"));
        }
    });
    $body.on('click', '.fnToChoose', function () {
        $GETLIST_TR = $(this).parents('tr');
        template.getDate(1);
        template.show();
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

// 添加选择后的回调，以及显示弹出层
    $body.on("click","#fnToChooseBusinessUserName",function(){
        var $names = $("#businessUserName"),
            $ids = $("#businessUserId");
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
    });
    // $body.on("click","#fnToClearBusinessUserName",function(){
    //     var $this = $(this);
    //     $this.siblings("input").val('');
    // });
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
    //----------------------

    var thisUserId = $('[name*="userId"]').val();
    if($('.publicOPNLink').length > 0){
        publicOPN.addOPN([{
            name: '客户基本情况',
            // url:'/insurance/customer/person/listPersonTrace.htm?userId='+thisUserId,
            alias: 'customerBasicInfo',
            event: function () {
                $('#customerInfo').click();
            }
        },{
            name: '客户联系情况',
            // url:'/insurance/customer/person/listPersonTrace.htm?userId='+thisUserId,
            alias: 'customerContactInfo',
            event: function () {
                $('#customerContactInfo').click();
            }
        },{
            name: '客户关联关系',
            // url:'/insurance/customer/person/listPersonTrace.htm?userId='+thisUserId,
            alias: 'customerRelateInfo',
            event: function () {
                $('#customerRelateInfo').click();
            }
        },{
            name: '历史保单信息',
           // url:'/insurance/businessBill/list.htm?userId='+thisUserId,
            alias: 'historyListInfo',
            event: function () {
                $('#historyListInfo').click();
            }
        }]);

        publicOPN.init().doRender(); //初始化侧边栏
    }else {
        publicOPN.addOPN([{
            name: '客户信息跟踪',
            url:'/insurance/customer/person/listPersonTrace.htm?userId='+thisUserId,
            alias: 'customerInfoTrace',
            event: function () {
                $('#customerInfoTrace').click();
            }
        },{
            name: '查看客户协议',
            url:'/insurance/customerProtocol/queryCustomerProtocol.htm?userId='+thisUserId,
            alias: 'viewCustomerPortocol',
            event: function () {
                $('#viewCustomerPortocol').click();
            }
        },{
            name: '查看保单信息',
            url:'/insurance/businessBill/list.htm?userId='+thisUserId,
            alias: 'viewWarrantyInfo',
            event: function () {
                $('#viewWarrantyInfo').click();
            }
        },{
            name: '查看询价记录',
            url:'/insurance/priceContactLetter/list.htm?userId='+thisUserId,
            alias: 'viewPriceRecord',
            event: function () {
                $('#viewPriceRecord').click();
            }
        },{
            name: '查看理赔记录',
            url:'/insurance/claimSettlement/list.htm?userId='+thisUserId,
            alias: 'viewClaimsRecord',
            event: function () {
                $('#viewClaimsRecord').click();
            }
        },{
            name: '申请立项',
            url:'/insurance/projectSetup/edit.htm?userId='+thisUserId,
            alias: 'applyAddProject',
            event: function () {
                $('#applyAddProject').click();
            }
        },{
            name: '制作询报价方案',
            url:'/insurance/priceContactLetter/add.htm？userId='+thisUserId,
            alias: 'addPricePlan',
            event: function () {
                console.log($('#addPricePlan').attr('href'))
                $('#addPricePlan').click();
            }
        },{
            name: '投保申请',
            url:'/insurance/insuranceContactLetter/edit.htm?userId='+thisUserId,
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