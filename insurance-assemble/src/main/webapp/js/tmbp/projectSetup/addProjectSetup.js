define(function(require, exports, module) {
    //客户协议
    require('Y-msg');
    require('input.limit');
    require('tmbp/upAttachModifyNew');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
    var selectType = require('tmbp/selectType');
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    var getList = require('zyw/getList');

    var $body = $('body');

    var proportionType = $("[name='proportionType']");



    var getList = require('zyw/getList');
    var customerChoose = new getList();
    customerChoose.init({
        title: '客户信息',
        ajaxUrl: '/baseDataLoad/customer.json',
        btn: '#fnToChooseCustomer',
        multiple: true,
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="itemLine">',
                '        <td class="fn-tac"title="{{item.name}}">{{item.name}}</td>',
                '        <td class="fn-tac"title="{{item.certType}}">{{item.certType}}</td>',
                '        <td class="fn-tac"title="{{item.certNo}}">{{item.certNo}}</td>',
                '        <td class="fn-tac fn-hide listData" certno="{{item.certNo}}" certtype="{{item.certType}}" userid="{{item.userId}}" username="{{item.name}}"><a href="/insurance/customer/company/info.htm?userId={{item.userId}}" target="_blank">[ 详情 ]</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="del">[ 删除 ]</a></td>',
                '        <td class="fn-tac"><input type="checkbox" id="choosedItems{{item.userId}}"></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['客户名称：',
                '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
                '证件类型：',
                '<select class="ui-select fn-w200 fn-validate" name="certType">'
                +'<option value="">请选择</option>'
                // +'<option value="IDENTITY_CARD">身份证</option>'
                // +'<option value="ARMY_IDENTITY_CARD">军官证</option>'
                // +'<option value="STUDENT_CARD">学生证</option>'
                // +'<option value="PASSPORT">护照</option>'
                +'<option value="UNIT_UNIFORM_CODE">统一社会代码证</option>'
                +'<option value="BUSINESS_LICENSE">营业执照</option>'
                +'<option value="TAX_REGISTRATION_CERTIFICATE">税务登记证</option>'
                +'<option value="ORGANIZATION_CODE_CERTIFICATE">组织机构代码证</option>'
                +'<option value="BUSINESS_PERMIT_CERTIFICATE">业务许可证</option>'
                +'<option value="OTHER">其他证件</option>'
                +'</select>',
                '&nbsp;&nbsp;',
                '证件号码：',
                '<input class="ui-text fn-w100" type="text" name="certNo">',
                '<input class="ui-text fn-w100" type="hidden" name="customerType" value="COMPANY_CUSTOMER">',
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
            var newObj = $a.$list.find('tr.hasCheckItem'),
                $checkList = newObj;
            getListSave($checkList);
            $checkList.find('td:last').remove().end()
                .find('td:last').removeClass('fn-hide');
            $('.chooseCustomerArea').html($checkList);
            //var a = $('.chooseCustomerArea').html();

        },
        renderCallBack: function (res,self) {//数据还原
            var choosedItems = $('.customerUserIds').val();
            if(!choosedItems) return;
            var choosedItemsArry = $('.customerUserIds').val().split(',');
            $.each(choosedItemsArry,function (index,val) {
                self.$box.find('#choosedItems' + val).trigger('click').parents('tr').addClass('hasCheckItem');
            })
        }
    });


    (new getList()).init({
        title: '选择渠道',
        ajaxUrl: '/baseDataLoad/customer.json',
        btn: '.fnToChooseChannels',
        tpl: {
            tbody: ['{{each pageList as item i}}',
                '    <tr class="fn-tac m-table-overflow">',
                '        <td title="{{item.name}}">{{item.name}}</td>',
                '        <td title="{{item.certType}}">{{item.certType}}</td>',
                '        <td title="{{item.certNo}}">{{item.certNo}}</td>',
                '<td><a class="choose" userid="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
                '    </tr>',
                '{{/each}}'
            ].join(''),
            form: ['第三方机构名称：',
                '<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
                '<input class="ui-text fn-w100" type="hidden" name="customerType" value="SYSTEM_ORGANIZATION">',
                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
            ].join(''),
            thead: ['<th>机构名称</th>',
                '<th>证件类型</th>',
                '<th>证件号码</th>',
                '<th width="50">操作</th>'
            ].join(''),
            item: 5
        },
        callback: function($a) {
            $("#channelsUserName").val($a.attr("name")).blur();
            $("#channelsUserId").val($a.attr("userid")).blur();

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

// 添加选择后的回调，以及显示弹出层
    $body.on("click",".fnToProjectPerson",function(){
        var $this = $(this),
            $thisParent = $this.parent();
        var $names = $thisParent.find("[name='setupUseName']"),
            $ids = $thisParent.find("[name='setupUseId']");
        singleSelectMemberNamesDialog.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        singleSelectMemberNamesDialog.init(function(relObj) {

            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    }).on('click','.itemLine .del', function () {//删除行

        var $this = $(this);
        var $thisTr = $this.parents('.itemLine');
        getListSave($thisTr.siblings('.hasCheckItem').not($thisTr),function () {
            $thisTr.remove();
        });
    }).on('click','.fnToClear', function () {//清除
        $(this).parent().find('input').val('').blur();
    }).on('blur','#startTime', function () {
        var $startTime = $('#startTime');
        var $endTime = $('#endTime');
        var satrTime = $startTime.val();
        var endTime = $endTime.val();
        var satrTimeSen = new Date(satrTime).getTime();
        var endTimeSen = 0;
        if(!!endTime) endTimeSen = new Date(endTime).getTime();
        $endTime.attr('onclick',"laydate({istime: true,format: 'YYYY-MM-DD',min:'" + satrTime + "'})");
        if(satrTimeSen > endTimeSen) $endTime.val('');
    }).on('click','.radio',function () {
        var $this = $(this);
        var $parent = $this.parents('.radioBox');
        var $otherParent = $parent.siblings('.radioBox');
        $parent.find('[type=text]').removeAttr('disabled');
        $otherParent.find('[type=text]').attr('disabled','disabled');

    }).on('blur','.chooseOneInThree', function () {
        var chooseOneInThreeVal = 0;
        $.each($('.chooseOneInThree'),function () {
            if(!!$(this).val().replace(/\s/g, '')) chooseOneInThreeVal++;
        });
        $('.chooseOneInThreeVal').val(chooseOneInThreeVal).blur();
    });
    $('.radio[checked]').click();


    function getListSave($items,cb) {
        var customerObj = [];
        var idArray = [];
        $.each($items,function () {
            var $this = $(this);
            var customer = {};
            idArray.push($this.find('td.listData').attr('userid'));
            customer.customerId = $this.find('td.listData').attr('userid');
            customer.customerName = $this.find('td.listData').attr('username');
            customer.certType = $this.find('td.listData').attr('certtype');
            customer.certNo = $this.find('td.listData').attr('certno');
            customerObj.push(customer);
        });
        $('.customerUserIds').val(idArray);
        $('.customerUserNames').val(customerObj.length > 0 ? JSON.stringify(customerObj).replace(/\"/g,"'") : '');
        if(!!cb && typeof cb == 'function') cb();
    }
    //-----------------------------------------------------
    var undefinedOrNull = function (element) {
        return (typeof element == "undefined" || element == null || element == "")?true:false;
    }
    $(".submitBtn").click(
        function () {
            var customerInfo  = $(".hasCheckItem").html();
            var insuranceInfo  = $("#selectedId").val();
            var channelInfo = $("#channelsUserName").val();
            if(undefinedOrNull(customerInfo) && undefinedOrNull(insuranceInfo) && undefinedOrNull(channelInfo)){
                $("#warning").addClass("oneInThree");
            }else {
                $("#warning").removeClass("oneInThree");
            }
        }
    );
});

