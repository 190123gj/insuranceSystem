define	(function(require, exports, module) {

    require('Y-msg');
    require('input.limit');
    require('validate');
    require('zyw/publicPage');
    require('tmbp/submit.common');
    require('tmbp/upAttachModifyNew');

    var getList = require('zyw/getList');
    
    
	 (new getList()).init({
	        title: '保单选择',
	        ajaxUrl: '/baseDataLoad/queryBusinessBill.json',
	        btn: '.fnToChooseGuarantee',
	        tpl: {
	            tbody: ['{{each pageList as item i}}',
	                '    <tr class="fn-tac m-table-overflow">',
	                '        <td title="{{item.insuranceNo}}">{{item.insuranceNo}}</td>',
	                '        <td title="{{item.companyUserName}}">{{item.companyUserName}}</td>',
	                '        <td title="{{item.catalogName}}">{{item.catalogName}}</td>',
	                '        <td title="{{item.billCustomerName}}">{{item.billCustomerName}}</td>',
	                '<td><a class="choose" insuranceContactLetter="{{item.insuranceContactLetter}}" businessBillId="{{item.businessBillId}}" insuranceNo="{{item.insuranceNo}}"  href="javascript:void(0);">选择</a></td>',
	                '    </tr>',
	                '{{/each}}'
	            ].join(''),
	            form: ['保单号：',
	                '<input class="ui-text fn-w200" type="text" name="insuranceNo" />&nbsp;&nbsp;&nbsp;&nbsp;',
	                '<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="查询">'
	            ].join(''),
	            thead: ['<th>保单号</th>',
	                '<th>保险公司</th>',   
	                '<th>保险险种</th>',
	                '<th>投保人</th>',
	                '<th width="50">操作</th>'
	            ].join(''),
	            item: 5
	        },
	        callback: function($a) {
	        	$("#insuranceNo").val($a.attr("insuranceNo")).blur();
	            $("#businessBillId").val($a.attr("businessBillId")).blur();
	        }
	    });
});