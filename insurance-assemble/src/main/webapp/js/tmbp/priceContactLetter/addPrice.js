define(function(require, exports, module) {
	//客户协议
	require('Y-msg');
	// require('input.limit');
    require('tmbp/submit.common');
    require('zyw/upAttachModify');
	var util = require('util');
    var selectType = require('tmbp/selectType');
    var getList = require('zyw/getList');
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数

    var $body  = $('body');

    new selectType({
        selectBoxObj: $('.selectFnBox1'), //必须参数。外层JQ对象，多个会依次遍历-->初始化

        afterChoosedCallback: {//缺省false。完成选择之后的回调
            callbackTargetCommon: function ($this) {

                var $thisBox = $this.parents('.selectFnBox1')
                $thisBox.find('#selectedId').val($this.attr('kindsid')).blur();
                $thisBox.find('#selectedName').val($this.attr('valuedata')).blur();
                if($this.attr('itemtype') == 'kinds') {
                    $.ajax({//更新类型
                        url:'/baseDataLoad/catalogConditions.json?catalogId=' + $this.attr('kindsid'),
                        success:function (res) {
                            // console.log(res)
                            if(res.success){
                                var _html = '<option value="" certno="">请选择</option>';
                                if(!!res.data || res.data.conditions){
                                    var $conditionItems = $('.conditionItems');
                                    $.each(res.data.conditions,function (index,typeObj) {
                                        _html += '<option  value="'+ typeObj.conditionId + '">' + typeObj.businessConditions + '</option>'
                                    });
                                    $conditionItems.find('select').html(_html);

                                    $conditionItems.removeClass('fn-hide').find('[name]').removeClass('ignore').removeAttr('disabled').val('').blur();
                                }else{
                                    Y.alert('提示','无数据！',function () {
                                        $conditionItems.html(_html);

                                    });
                                }
                            }else {
                                Y.alert('提示',res.message);
                            }
                        }
                    })
                }else {
                    $('.conditionItems').addClass('fn-hide').find('[name]').addClass('ignore').attr('disabled');
                }
            }
        }
    });

    (new getList()).init({
        title: '关联超权限审批单名称',
        ajaxUrl: '/baseDataLoad/projectSetup.json',
        btn: '.fnToChooseProjectSetup',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.projectSetupName}}">{{item.projectSetupName}}</td>',
                '        <td title="{{item.beginTime}}">{{item.beginTime}}</td>',
                '        <td title="{{item.endTime}}">{{item.endTime}}</td>',
                '        <td title="{{item.rawAddTime}}">{{item.rawAddTime}}</td>',
                '<td><a class="choose" projectSetupName="{{item.projectSetupName}}" projectSetupId="{{item.projectSetupId}}"  certainIds="{{item.certainIds}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['客户名称：',
                '<input class="ui-text fn-w100" type="text" name="projectSetupName">&nbsp;&nbsp;&nbsp;&nbsp;',
                '&nbsp;&nbsp;',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>项目名称</th>',
                '<th>起日期</th>',
                '<th>止日期</th>',
                '<th>创建时间</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            $("#projectSetupId").val($a.attr("projectSetupId"));
            $("#projectSetupName").val($a.attr("projectSetupName"));
            $('.INDIVIDUAL_CUSTOMER').hide();
            $('.COMPANY_CUSTOMER').attr("selected",'selected');
            $(".userNameClear").click();
            $("#certainIds").val($a.attr("certainIds"));
            console.log($("#certainIds").val());
            if($('.selectFnBox1').length > 0) newSelectType($('.selectFnBox1'));

            cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=' + $('[name="customerType"]').val();
        }
    });




    var getList = require('zyw/getList');
    var cumstorInfo = new getList();
    cumstorInfo.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json?customerType=' + $('[name="customerType"]').val(),
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
            $("#customerUserName").val($a.attr("name"));
            $("#customerUserId").val($a.attr("userId"));
            
            $.ajax({//更新类型
                url:'/baseDataLoad/customerCert.json?userId=' + $a.attr('userId'),
                success:function (res) {
                    console.log(res);
                    if(res.success){
                        var _html = '<option value="" certno="">请选择</option>';
                        if(!!res.data || res.data.pageList){
                            var $customerUserCertType = $('.customerUserCertType');
                            $.each(res.data.pageList,function (index,typeObj) {
                                _html += '<option certno="' + typeObj.certNo + '" value="' + typeObj.certType + '">' + typeObj.certTypeText + '</option>'
                            });
                            $customerUserCertType.html(_html);
                        }else{
                            Y.alert('提示','无数据！',function () {
                                $customerUserCertType.html(_html)
                            });
                        }
                    }else {
                        Y.alert('提示',res.message);
                    }
                }
            })

        }
    });
    var customerUserId = $("#customerUserId").val();
    if(!!customerUserId){
        $.ajax({//更新类型
            url:'/baseDataLoad/customerCert.json?userId='+customerUserId,
            success:function (res) {
                console.log(res);
                if(res.success){
                    var _html = '<option value="" certno="">请选择</option>';
                    if(!!res.data || res.data.pageList){
                        var $customerUserCertType = $('.customerUserCertType');
                        $.each(res.data.pageList,function (index,typeObj) {
                            _html += '<option certno="' + typeObj.certNo + '" value="' + typeObj.certType + '">' + typeObj.certTypeText + '</option>'
                        });
                        $customerUserCertType.html(_html);
                    }else{
                        Y.alert('提示','无数据！',function () {
                            $customerUserCertType.html(_html)
                        });
                    }
                }else {
                    Y.alert('提示',res.message);
                }
            }
        })

    }

    // 必备参数
    var scope = '{type:\"system\",value:\"all\"}';
    var selectUsers = {
        selectUserIds: '', //已选id,多用户用,隔开
        selectUserNames: '' //已选Name,多用户用,隔开
    }

    var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do?isSingle=true', {
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
    });
    //询价模板选择
    $body.on('change','.selectEnquiryWayOptions',function () {
        var $this = $(this);
        var thisVal = $this.val();
        var $selectEnquiryWay = $('.selectEnquiryWay');
        $selectEnquiryWay.addClass('fn-hide').find('[name]').attr('disabled','disabled').addClass('ignore').val('').blur();
        $selectEnquiryWay.find('.productType').find('.selectFn label').html('请选择');
        !!thisVal && $('.selectEnquiryWay.' + thisVal).removeClass('fn-hide').find('[name]').removeAttr('disabled').removeClass('ignore');
        $('.conditionItems').addClass('fn-hide').find('[name]').addClass('ignore').attr('disabled');
    });
    //更新选择客户参数，以及选择客户输入框
    $body.on('change','[name="customerType"]',function () {
        var $this = $(this);
        var oldVal = $this.attr('oldval');
        var thisVal = $this.val();
        if(oldVal != thisVal) $('.customerUserParam').find('[name]').val('').blur();
        $this.attr('oldval',thisVal);
        cumstorInfo.ajaxUrl = '/baseDataLoad/customer.json?customerType=' + $(this).val();
    }).on('change','.customerUserCertType',function () {
        $('.customerUserCertNo').val($(this).find('option:selected').attr('certno')).blur();
    });
//---------------------------------------------------
//     (new getList()).init({
//         title: '业务员信息',
//         ajaxUrl: '',
//         btn: '#fnToChooseBusinessUserName',
//         tpl: {
//             tbody: ['{{each pageList as item i}}',
//                 '    <tr class="fn-tac m-table-overflow">',
//                 '        <td title=""></td>',
//                 '        <td title=""></td>',
//                 '        <td><a class="choose" certType="" certNo="" userId="" name=""  href="javascript:void(0);">选择</a></td>',
//                 '    </tr>',
//                 '{{/each}}'
//             ].join(''),
//             form: ['业务员姓名：',
//                 '<input class="ui-text fn-w100" type="text" name="">&nbsp;&nbsp;&nbsp;&nbsp;',
//                 '身份证号:',
//                 '<input class="ui-text fn-w100" type="text" name="">',
//                 '&nbsp;&nbsp;&nbsp;&nbsp;<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="查询">'
//             ].join(''),
//             thead: ['<th>姓名</th>',
//                 '<th>身份证</th>',
//                 '<th width="50">操作</th>'
//             ].join(''),
//             item: 5
//         },
//         callback: function($a) {S
//             $("#businessUserName").val($a.attr("name"));
//             $("#businessUserId").val($a.attr("userId"));
//         }
//     });
    //清除选择
    $body.on('click','.fnToClear', function () {
        var $chooseListBox = $(this).siblings('input');
        $chooseListBox.val('').blur();
    });
    $body.on('click','.fnToProjectSetupClear', function () {
        $('.INDIVIDUAL_CUSTOMER').show();
        $(".userNameClear").click();
    });
    $body.on('click','.userNameClear', function () {
        var cardNo = $(".customerUserCertNo");
        var customerUserCertType = $(".customerUserCertType");
        var _html = '<option value="" certno="">请选择</option>';
        cardNo.val("");
        customerUserCertType.val("");
        customerUserCertType.html(_html);
    });

//-----------------------------------------------
    function newSelectType($select) {
        new selectType({
            selectBoxObj: $select,
            isReadOnly: false//缺省false。统一配置的只读属性，只读属性也可以通过 <div class="selectFnBox1" isreadonly="true">...</div>这种方式（设置属性）来单独设置，优先级：属性 > 统一配置 > 默认值（false）
            // afterChoosedCallback: { //缺省false。完成选择之后的回调
            //     callbackTargetCommon: function($this) { //如果$('.selectFnBox1')上未定义属性callbacktarget，那么将会执行此回调函数，如果此函数未定义将执行插件内部的回调
            //         var Box,kindsid,catalogName;
            //         Box = $this.parents('.selectFnBox1');
            //         kindsid = $this.attr('kindsid');
            //         catalogName = $this.attr('valuedata');
            //         // console.log(Box.find('.catalogId'))
            //         Box.find('.catalogId').val(kindsid);
            //         Box.find('.catalogName').val(catalogName);
            //         console.log(Box.find('.catalogName').val(catalogName));
            //     }
            // }
        });
    }
});