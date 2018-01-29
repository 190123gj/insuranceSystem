define(function(require, exports, module) {
	  var getList = require('zyw/getList');
    require('tmbp/validate.common');
    require('Y-msg');
	require('input.limit');
	require('tmbp/upAttachModifyNew');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
	require('validate');

    var publicOPN = new(require('zyw/publicOPN'))();
    publicOPN.init().doRender();

    var util = require('util');
	var $body = $('body');
	var $form = $("#form");
	
	 (new getList()).init({
	        title: '保单选择',
	        ajaxUrl: '/baseDataLoad/queryBusinessBill.json',
	        btn: '#fnToChooseBusinessBill',
	        tpl: {
	            tbody: ['{{each pageList as item i}}',
	                '    <tr class="fn-tac m-table-overflow">',
	                '        <td title="{{item.insuranceNo}}">{{item.insuranceNo}}</td>',
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
	                '<th>保险险种</th>',
	                '<th>投保人</th>',
	                '<th width="50">操作</th>'
	            ].join(''),
	            item: 5
	        },
	        callback: function($a) {
	        	$("#insuranceNo").val($a.attr("insuranceNo")).blur();
	        	$("#businessBillId").val($a.attr("businessBillId")).blur();
	        	var insuranceContactLetter = $a.attr("insuranceContactLetter");
	        	if (!isEmptyObj(insuranceContactLetter)) {
	        		var info = eval("("+insuranceContactLetter+")");
	        		
	        		$("div.businessBillInfo").removeClass("fn-hide");
	        		if (info.insuranceType == "isLifeInsurance") {
	        			$("div.lifeInsurance").removeClass("fn-hide");
	        			$("div.noLifeInsuranceQuota").addClass("fn-hide");
	        			$("div.noLifeInsurance").addClass("fn-hide");
	        			//寿险数据赋值
	        		} else {
	        			if (info.isQuota == "YES") {
	        				$("div.noLifeInsuranceQuota").removeClass("fn-hide");
		        			$("div.lifeInsurance").addClass("fn-hide");
		        			$("div.noLifeInsurance").addClass("fn-hide");
	        			} else {
	        				$("div.noLifeInsurance").removeClass("fn-hide");
		        			$("div.noLifeInsuranceQuota").addClass("fn-hide");
		        			$("div.lifeInsurance").addClass("fn-hide");
	        			}
	        		}
	        		$("div.commonInfo").removeClass("fn-hide");
	        	}
	        }
	    });
	 
	    //判断一个对象是否为空
	    function isEmptyObj(obj) {
	        var res = true;
	        if(!obj || Object.prototype.toString.apply(obj) !== '[object Object]') return false;
	        $.each(obj,function (key,keyval) {
	            if(!keyval) {
	                res = false;
	                return false;
	            }
	        });
	        return res;
	    };
});