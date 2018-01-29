define(function(require, exports, module) {
	//客户协议
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	// require('tmbp/upAttachModify');
    require('tmbp/upAttachModifyNew');
	require('tmbp/submit.common');//通用的提交(包含验证)

	var getList = require('zyw/getList');
    var $body = $('body');

	(new getList()).init({
		title: '客户信息',
		ajaxUrl: '/baseDataLoad/customer.json',
		btn: '#fnToChooseCustomer',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td title="{{item.name}}">{{item.name}}</td>',
				'        <td title="{{item.customerType}}">{{item.customerType}}</td>',
				// '        <td title="{{item.certType}}">{{item.certType}}</td>',
				'        <td title="{{item.certNo}}">{{item.certNo}}</td>',
				'<td><a class="choose" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['客户名称：',
				'<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
				'客户类型：',
				'<select class="ui-select fn-w200 fn-validate" name="customerType">'
	            +'<option value="">请选择</option>'
	            +'<option value="INDIVIDUAL_CUSTOMER">个人客户</option>'
	            +'<option value="COMPANY_CUSTOMER">团体客户</option>'
	            +'</select>',
				'&nbsp;&nbsp;',
				'<input type="hidden" value="companyCustomer" name="companyCustomer"><input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>客户名称</th>',
			     '<th>客户类型</th>',
				// '<th>证件类型</th>',
				'<th>证件号码</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#customerUserName").val($a.attr("name"));
			$("#customerUserId").val($a.attr("userId"));
			$("#certType").val($a.attr("certType"));
			$("#certNo").val($a.attr("certNo"));
            $("#customerUserName").siblings(".error-tip").hide();
		}
	});


	(new getList()).init({
		title: '关联协议',
		ajaxUrl: '/baseDataLoad/customerProtocol.json?id=' + $('#id').val(),
		btn: '#fnToChooseProtocol',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'		<td title="{{item.no}}">{{item.no}}</td>',
				'        <td title="{{item.name}}">{{item.name}}</td>',
				'<td><a class="choose" no="{{item.no}}" id="{{item.id}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['协议名称：',
				'<input class="ui-text fn-w100" type="text" name="name">',
				'&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>协议编号</th>','<th>协议名称</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#no").val($a.attr("no"));
			$("#protocolName").val($a.attr("name"));
			$("#relationProtocolId").val($a.attr("id"));
		}
	});

	var $form = $('#form');

    $body.on("click","#fnToClearCustomer",function(){
		$('#customerUserName').val("").trigger('blur');
		$('#customerUserId').val("").trigger('blur');
		$('#certType').val("").trigger('blur');
		$('#certNo').val("").trigger('blur');
	}).on("click","#fnToClearProtocol",function(){
		$("#protocolName").val("").trigger("blur");
		$("#no").val("").trigger("blur");
		$("#relationProtocolId").val("").trigger("blur");
	});

    $body.on('blur','#beginDate',function () {
        var _beginDate = $(this).val();
        var _endDate = $('#endDate').val();
        if(!_beginDate) return;
        $('#endDate').attr('onclick',"laydate({istime: true,format: 'YYYY-MM-DD',min:'" + _beginDate + "'})");
        if(!_endDate) return;
        if(_beginDate.split('-').join('') > _endDate.split('-').join('')) $('#endDate').val('');
    })
});

