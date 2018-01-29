'use strict';
define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('input.limit');
	var util = require('util');
	require('zyw/upAttachModify');
	// require('validate');
	var hintPopup = require('zyw/hintPopup');
    var getList = require('zyw/getList');

	var $body = $('body'),
		BPMiframe = require('BPMiframe');

	//------ 选择组织 start
	// 初始化弹出层
	var selectOrgDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&selectTop=Y', {
		'title': '组织',
		'width': 850,
		'height': 460,
		'scope': '{type:\"system\",value:\"all\"}',
		'arrys': [], //[{id:'id',name:'name'}];
		'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
		'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
	});
	// 添加选择后的回调，以及显示弹出层
	$body.on('click', '.fnListSearchOrgChoose', function() {
		var $this = $(this),
			_isSingle = $this.attr('single') ? true : false,
			_$id = $("#contractingAgencyId"),
			_$name = $("#contractingAgencyName");

		// 更新弹窗的单选、多选
		//selectOrgDialog.resetSrc('/bornbpm/platform/system/sysOrg/dialog.do');

		//这里也可以更新已选择机构
		selectOrgDialog.obj.arrys = [{
			id: _$id.val(),
			name: _$name.val()
		}];

		selectOrgDialog.init(function(relObj) {

			_$id.val(relObj.orgId).trigger('change').trigger('blur');
			_$name.val(relObj.orgName).trigger('change').trigger('blur');

		});

	});
    $body.on('click', '.fnListSearchClear', function() {
    	var $this = $(this);
        $this.siblings('input').val('');
	});

	(new getList()).init({
		title: '协议',
		ajaxUrl: '/baseDataLoad/insuranceProtocol.json',
		btn: '#fnListMain',
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td title="{{item.name}}">{{item.name}}</td>',
				'        <td title="{{item.protocolUserName}}">{{item.protocolUserName}}</td>',
				'<td><a class="choose" signDate="{{item.signDate}}"  contractingAgencyId="{{item.contractingAgencyId}}" contractingAgencyName="{{item.contractingAgencyName}}"  id="{{item.id}}" name="{{item.name}}" protocolUserName="{{item.protocolUserName}}" protocolUserId="{{item.protocolUserId}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['名称：',
				'<input class="ui-text fn-w100" type="text" name="name1">&nbsp;&nbsp;&nbsp;&nbsp;',
				'合作机构:',
				'<input class="ui-text fn-w100" type="text" name="protocolUserName1" >',
				'&nbsp;&nbsp;&nbsp;&nbsp;<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>协议名称</th>',
				'<th>合作机构</th>',
				'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
		callback: function($a) {
			$("#contractingAgencyId").val($a.attr("contractingAgencyId"));
			$("#contractingAgencyName").val($a.attr("contractingAgencyName"));
			$("#protocolUserId").val($a.attr("protocolUserId"));
			$("#protocolUserName").val($a.attr("protocolUserName"));
			$("#signDate").val($a.attr("signDate"));
			$("#parentId").val($a.attr("id"));
			$("#parentName").val($a.attr("name"));
			//--------------------------------------
			$.ajax({
				url:'/baseDataLoad/insuranceProtocolById.json?id='+$a.attr("id"),
                dataType: "json",
                success: function(data){
                    $.each(data.data.protocolInfo.protocolInfoInfos,function (i,el) {
						$("[name*='catalogId']").val(el.catalogId);
                        $("[name*='catalogName']").val(el.catalogName);
                        $("[name*='catalogId']").siblings("label").html(el.catalogName);
                        $("[name*='productName']").val(el.productName);
                        $("[name*='productId']").val(el.productId);
                        $("[name*='chargeInfo']").val(el.chargeInfo);
                        var $val = el.chargeInfo;
                        var $data = $val ? JSON.parse($val) : {
                                data: [],
                                maxLength: 0,
                                newly: []
                            };
                        if($data.data.length != 0){
                            $("[name*='firstPeriod']").val($data.data[0].periods[0]);
                        };
                    });
				}
			});
		}
	});
	//----主协议，附协议
	$(function () {
		var $isMain = $(".isMain");
        if($isMain.eq(0).attr("checked")){
            $isMain.parents("form").find(".totalCompany").hide().find("#parentName").addClass("ignore");
        }else {
            $isMain.parents("form").find(".totalCompany").show().find("#parentName").removeClass("ignore");
        }
        $isMain.click(function () {
        	var $this = $(this);
        	if($this.val()=="YES"){
                $this.parents("form").find(".totalCompany").hide().find("#parentName").addClass("ignore");
			}else {
                $this.parents("form").find(".totalCompany").show().find("#parentName").removeClass("ignore");
			}
        });
    });



	//(new getList()).init({
	//	title: '客户信息',
	//	ajaxUrl: '/baseDataLoad/customer.json?customerType=INSURANCE_INSTITUTIONS',
	//	btn: '#fnListContractingAgency2',//
	//	tpl: {
	//		tbody: ['{{each pageList as item i}}',
	//			'    <tr class="fn-tac m-table-overflow">',
	//			'        <td title="{{item.name}}">{{item.name}}</td>',
	//			'        <td title="{{item.customerType}}">{{item.customerType}}</td>',
	//			'        <td title="{{item.certType}}">{{item.certType}}</td>',
	//			'        <td title="{{item.certNo}}">{{item.certNo}}</td>',
	//			'<td><a class="choose" certType="{{item.certType}}" certNo="{{item.certNo}}" userId="{{item.userId}}" name="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
	//			'    </tr>',
	//			'{{/each}}'
	//		].join(''),
	//		form: ['保险公司名称：',
	//			'<input class="ui-text fn-w100" type="text" name="customerName">&nbsp;&nbsp;&nbsp;&nbsp;',
	//			'公司性质:',
	//			'<input class="fn-validate" type="radio" name="kind" value="property" id="kind1">',
	//			'<label for="kind1">全选</label>',
	//			'<input class="fn-validate" type="radio" name="kind" value="property" id="kind1">',
	//			'<label for="kind1">总公司</label>',
	//			'<input class="fn-validate" type="radio" name="kind" value="property" id="kind1">',
	//			'<label for="kind1">分支机构</label>',
	//			'&nbsp;&nbsp;&nbsp;&nbsp;<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
	//		].join(''),
	//		thead: ['<th>客户名称</th>',
	//			'<th>客户类型</th>',
	//			'<th>公司性质</th>',
	//			'<th>公司编号</th>',
	//			'<th width="50">操作</th>'
	//		].join(''),
	//		item: 5
	//	},
	//	callback: function($a) {
	//		$("#protocolUserName").val($a.attr("name"));
	//		$("#protocolUserId").val($a.attr("userId"));
	//		$(".clearProduct").click();
    //
	//	}
	//});
// //--树形---------------------------
    var zTreeObj;
    var setting = {
        view: {
            dblClickExpand: false
        },
        async: {
            enable: true,
            url: "/insurance/insuranceCompany/tree.json",
            autoParam: ["id=parentId"]
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
                onClick: onClick
        }
	};

    $(document).ready(function(){
    	var $ztreeDemo= $("#ztreeDemo");
    	if($ztreeDemo.size()>0){
            zTreeObj = $.fn.zTree.init($("#ztreeDemo"), setting);
        }
    });
    $body.on("click","#fnListContractingAgency",function () {
        showMenu();
        return false;
    });


    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("ztreeDemo"),
            nodes = zTree.getSelectedNodes(),
            v = "";
		var    ids= "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            v += nodes[i].name + ",";
			ids += nodes[i].id + ",";
        }
        if (v.length > 0 ){
			v = v.substring(0, v.length-1);
			ids = ids.substring(0, ids.length-1);
		}


        var userNameObj = $("#protocolUserName");
        userNameObj.attr("value", v);

		var userIdObj = $("#protocolUserId");
		userIdObj.attr("value", ids);

	}

    function showMenu() {
        var cityObj = $("#protocolUserId");
        var cityOffset = $("#protocolUserId").offset();
        $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("normal");
        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#menuContent").fadeOut("normal");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "fnListContractingAgency" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
            hideMenu();
        }
    }
// 	//----------------------------------


	require('zyw/requiredRules');
	var template = require('arttemplate');
	var popupWindow = require('zyw/popupWindow');
	var selectType = require('tmbp/selectType');
	var $form = $("#form"),
		allWhetherNull = {
			stringObj: 'name,no,protocolUserName,contractingAgencyName,beginDate,endDate,catalogName',
			boll: false
		},
		requiredRules = $form.requiredRulesSharp(allWhetherNull['stringObj'], allWhetherNull['boll'], {}, function(rules, name) {
			rules[name] = {
				required: true,
				messages: {
					required: '必填'
				}
			};
		}),
		rulesAllBefore = {
			setingRatio: {
				required: true,
				messages: {
					required: '请设置费用比例'
				}
			}
		},
		rulesAll = $.extend(true, {}, requiredRules, rulesAllBefore); //所有格式验证的基

	var submitValidataCommonUp = require('zyw/submitValidataCommonUp');

	submitValidataCommonUp.submitValidataCommonUp({

		form: $form, //form
		allWhetherNull: allWhetherNull, //必填集合与是否反向判断
		rulesAll: rulesAll, //验证全集
		allEvent: {

			// replaceContentBtn: true, //默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
			// replaceBroadsideBtn: true,//默认false，false时外部配置合并内部默认配置，否则替换内部默认配置
			contentBtn: [{
				clickObj: '.submit',
				eventFun: function(jsonObj) {

					$.fn.whetherMust(jsonObj.objList['rulesAll'], true).allAddValidata(jsonObj.objList['rulesAll']); //是必填

					jsonObj.portInitVal['submitHandlerContent'] = {

						submitStatus: 'submit',
						fm5: jsonObj.setInitVal['fm5']

					}; //向validate文件里的submitHandler暴露数据接口

					jsonObj.getInitVal('zyw/submitValidataCommonUp'); //validate文件获取数据

					jsonObj.objList['form'].submit(); //提交

				}
			}], //内容区提交组

		},
		ValidataInit: {

			successBeforeFun: function(res) {

				var util = require('util'),
					loading = new util.loading();

				loading.open();
				$.fn.orderName();

				return true;

			},

			successFun: function(res) {

				if (res['success']) {

					$('.m-modal-box').remove();

					var url = "/insurance/insuranceProtocol/list.htm";
					if($("#type").val() == "INSURANCE_EXPENSE"){
						var url = "/insurance/insuranceExpense/list.htm";
					}

					hintPopup(res.message,url);

				} else {

					$('.m-modal-box').remove();
					hintPopup(res['message']);

				}

			}
		},

		callback: function(objList) { //加载时回调

			//验证方法集初始化
			$('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
				.initAllOrderValidata()
				.assignGroupAddValidataUpUp(objList['rulesAll'], {

					laterFun: function(last) {
                        newSelectType(last.find('.selectFnBox1'));
						return true
					}

				});

		}

	})

	$('body').on('click', '.productBtn', function(event) {

		var $thisProduct, kindsid,customerUserId;

		$thisProduct = $(this);
        var protocolName = $thisProduct.parents('form').find('[name*=protocolUserId]').siblings("label").html();
		kindsid = $thisProduct.parents('tr').find('.catalogId').val();
        customerUserId = $thisProduct.parents("form").find("[name*=protocolUserId]").val();

        if (!!!customerUserId) {
            hintPopup('请选择'+protocolName);
            return;
        }
		if ((!!!kindsid)|| kindsid=='0') {
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

                var thisProductTd = $thisProduct.parents(".productTD");

                //$("#selectFnBox1").addClass("selectFnBox1");
                //$(".selectCustom").removeClass("selectFnBox1");
                if($('.selectFnBox2').length > 0) newSelectType($('.selectFnBox2').addClass("newStyle"));

				$Y.wnd.on('click', '.choose', function(event) {
					var $this, productName, productId, companyUserName,lifeInsurance, arrHtml, $product;

					$this = $(this);
					productName = $this.attr('productName');
					productId = $this.attr('productId');
					companyUserName = $this.attr('companyUserName');
                    lifeInsurance = $this.attr('lifeInsurance');

					$thisProduct.siblings('.productName').val(productName);
					$thisProduct.siblings('.productId').val(productId);
                    $thisProduct.siblings('.lifeInsurance').val(lifeInsurance);


                    $Y.close();

				});
                $Y.wnd.on('click', '.chooseBtn', function(event) {
					var  productName='';
					var productId = '';
					var companyUserName = '';
					var lifeInsurance = '';

                    if($("input:checkbox[class='chooseBox']").is(":checked")){
                        thisProductTd.html("");
                        $("input:checkbox[class='chooseBox']:checked").each(function(){

                            productName = $(this).attr("productName");
                            productId = $(this).attr("productId");
                            lifeInsurance = $(this).attr("lifeInsurance");

                            companyUserName = $(this).attr("companyUserName");

                            var str = "";
                            str+="<div style='position: relative; padding:6px 0;'><input type='text' name='productName' class='text productName valid' readonly='' aria-invalid='false' value='"+productName+"'>"
                                +"<input type='hidden' name='productId' class='productId' value='"+productId+"'><input type='hidden' name='lifeInsurance' class='lifeInsurance' value='"+lifeInsurance+"'><div style='position: absolute;top: 0px;right: 0px'><a class='productBtn ui-btn ui-btn-blue ui-btn-fill' style=''>选择</a> "
                                +"<a class='proClearBtn ui-btn ui-btn-gray ui-btn-fill'style=''>清除</a></div></div>";
                            //$(".productTD").append(str);
                            thisProductTd.append(str);
                            $thisProduct.siblings('.productId').val(productId);
                        });
					}

                    $Y.close();
                    // $("#selectFnBox1").removeClass("selectFnBox1");
                    // $(".selectCustom").addClass("selectFnBox1");

				});
            },
			console: true //打印返回数据
		});

	}).on('click', '.proClearBtn', function(event) {
		$(this).parent().siblings('[name]').val('');
	});

	$('body').on('click', '.setingRatio', function(event) { //设置费用比例

		var $this, data, Html, val;


		$this = $(this);

        var lifeInsuranceVal = $this.parents("tr.fnNewLine").find("input.lifeInsurance").val();
        console.log(lifeInsuranceVal);


		val = $this.siblings('.setingRatioArr').val();
		data = val ? JSON.parse(val) : {
			data: [],
			maxLength: 0,
			newly: []
		};
		data['maxLength'] = new Array(parseInt(data['maxLength']));

		Html = template('setingRatioPopup', data);

		$('body').Y('Window', {
			content: Html,
			modal: true,
			key: 'modalwndSetingRatio',
			simple: true,
			closeEle: '.close'
		});

		var modalwnd, wndBox;

		modalwnd = Y.getCmp('modalwndSetingRatio');
		wndBox = modalwnd.wnd;
        if(lifeInsuranceVal == 'NO'){
            $("a.rowBtn").hide();
            $("a.colBtn").hide();
        }
		wndBox.on('click', 'a.colBtn', function(event) { //增加期数

			var $targetList, listLength, $theadTr;

			$targetList = wndBox.find('tbody tr');
			$theadTr = wndBox.find('thead tr');
			listLength = $theadTr.find('th').length == 1 ? '首' : $theadTr.find('th').length;

			$theadTr.append('<th class="fn-p-reb">第' + listLength + '期<span class="recordDelete rowDelete">━</span></th>')

			$targetList.each(function(index, el) {

				var $el;

				$el = $(el);

				$el.append('<td class="fn-text-c"><input class="text fnMakeMoney" type="text" name=""></td>')

			});

		});

		wndBox.on('click', 'a.rowBtn', function(event) { //增加缴费期限

			var rowHtml, $tbody, colLength;

			$tbody = wndBox.find('tbody');
			// rowLength = $tbody.find('tr').length + 1;
			colLength = wndBox.find('thead tr th').length;

			rowHtml = ['<tr class="new">',
				'<td class="fn-text-c fn-p-reb"><input type="text" class="text norepeat"><span class="recordDelete colDelete">━</span></td>',
				'</tr>'
			];

			for (var i = 1; i < colLength; i++) {

				rowHtml.splice(-1, 0, '<td class="fn-text-c"><input class="text fnMakeMoney" type="text" name=""></td>');

			};

			$tbody.append(rowHtml.join(''));

		});

		var verifyVal = function(val) {
            var inputVal = $(val);
            var eqVal = [];
            var a = true;
            $(".err").html("");
            inputVal.siblings("span").remove();
            for(var i = 0;i<inputVal.length;i++){
                var inputV = inputVal.eq(i).val();
                inputVal.eq(i).parent().append("<span class='err' style='color:red;'></span>");
                if(Number(inputV)<0 || Number(inputV)>100){
                    $(".err").eq(i).html("请输入0--100的数值").css("width","130px");
                }else {
                    $(".err").eq(i).html("");
				}
				var errVal = $(".err:eq("+i+")").html();
                eqVal[i] = errVal;
            }
            for(var j=0;j<eqVal.length;j++){
                if(eqVal[j].length>0){
                    a=false;
                    break;
                }
            }
            if(!a){
            	return false;
			}else {
            	return true;
			}
			
        };
		wndBox.on('click', 'a.okBtn', function(event) { //确定
			//{data: [{timeLimit: 1,periods: [123, 123, 123]}, {timeLimit: 3,periods: [321, 321]}],maxLength: 3}
			//var classArr = ['fnMakeMoney','stagVal'];
			var _this = $(this);
			var stagVal = $(".newPopup input").hasClass("stagVal");
			var fnMakeMoney = $(".newPopup input").hasClass("fnMakeMoney");

			if(stagVal){
                if (!verifyVal(".stagVal")){
                    return false;
                }
			}else{
                if (!verifyVal(".fnMakeMoney")){
                    return false;
                }
			}

            var hash={};
			var reg = /^[1-9]+[0-9]*]*$/;
            $('.norepeat').each(function(i,o){
                if(hash[$(o).val()]){
                    Y.alert('','缴费期限不能重复！');
                    hash['t'] = true;
                }
                if(isNaN($(o).val()) || !reg.test($(o).val())){
                    Y.alert('','缴费期限只能输入正整数！');
                    hash['t'] = true;
				}
                hash[$(o).val()] = true;
            });
			if(hash['t']){
                return
			}

			var datas = {
				data: [],
				maxLength: wndBox.find('thead tr th').length - 1
			};

			var newly = new Array();

			wndBox.find('tbody tr').each(function(index, el) {

				var timeLimitIndex = index;
				var $this = $(el);

				datas['data'][timeLimitIndex] = new Object();
				datas['data'][timeLimitIndex]['periods'] = new Array();

				if ($this.hasClass('new')) {

					newly[timeLimitIndex] = true;

				}

				$(el).find('td').each(function(index, el) {

					var $el = $(el);

					if (!index) {

						datas['data'][timeLimitIndex]['timeLimit'] = $el.find('input').val();

					} else {

						datas['data'][timeLimitIndex]['periods'].push($el.find('input').val());

					}

				});

			});

			datas['newly'] = newly;
			$this.siblings('.setingRatioArr').val(JSON.stringify(datas));
			//--------首期传值
            var thisTr = _this.parents(".fn-clear").siblings("table").find("tr");
            var trLength = thisTr.length;
            var sum=0;
            for(var i=0;i<trLength;i++){
            	if(i!=0){
                    var firstMoney = thisTr.eq(i).find(".fnMakeMoney").eq(0).val();
                    sum += parseInt(firstMoney);
				}
			}
			$this.parents("tr").find(".firstNo").removeAttr("readonly");
            var firstNo = $this.parents("tr").find(".firstNo");
            firstNo.addClass("fnMakeMoney").val(sum/(trLength-1)).blur();
            firstNo.attr("readonly",true);
			//-----------------------
			modalwnd.close();

		});

		wndBox.on('click', '.colDelete', function(event) { //删除该行

			var $this, $tr, $tbody;

			$this = $(this);
			$tr = $this.parents('tr');
			$tbody = $this.parents('tbody');


			$tr.remove();
			// $tbody.find('tr').each(function(index, el) {

			// 	var $el;

			// 	$el = $(el);

			// 	$el.find('td:eq(0)').html((index + 1) + '<span class="recordDelete colDelete">━</span>年');

			// });


		});

		wndBox.on('click', '.rowDelete', function(event) { //删除该行列

			var $this, $th, $table, $index, $nextAll;

			$this = $(this);
			$th = $this.parents('th');
			$table = $this.parents('table');
			$index = $th.index();
			$nextAll = $th.nextAll();

			$th.remove();
			$table.find('tbody tr').each(function(index, el) {

				var $el;

				$el = $(el);

				$el.find('td').eq($index).remove();

			});

			$nextAll.each(function(index, el) {

				var $el, oldNum;

				$el = $(el);
				oldNum = Number($el.text().match(/\d+/)[0])

				$el.html('第' + (oldNum - 1) + '期<span class="recordDelete rowDelete">━</span>')
			});

		});

	});

	//销售区域：
	var $salesAreasOutput = $('.salesAreasOutput');
	var $levelOutput = $salesAreasOutput.find('.levelOutput');
	var $salesAreasCheckbox = $('.salesAreasCheckbox');

	function salesAreasCollect() {

		var arr = new Array();

		$levelOutput.eq(0).find('dd').each(function(index, el) {

			var code, obj;

			code = $(el).attr('code');
			obj = new Object();
			obj[code] = [];

			arr.push(obj);

		});

		$levelOutput.eq(1).find('dl').each(function(index, el) {

			var $el, childrenArr, code, obj;

			$el = $(el);
			childrenArr = new Array();
			code = $el.attr('code');
			obj = new Object();

			$el.find('dd').each(function(index, el) {
				// console.log(index);
				childrenArr.push($(el).attr('code'))

			});

			obj[code] = childrenArr;

			arr.push(obj);

		});

		$('.salesAreasCollect').val(JSON.stringify(arr));

	}

	$('body').on('change', '.salesAreasCheckbox input[type="checkbox"]', function(event) { //checkbox change

		var $this, seat, status, text, code;

		$this = $(this);
		seat = $this.parents('.level').index(); //level
		status = $this.is(':checked'); //whether checked
		code = $this.val();
		text = $this.attr('toponymy');

		if (status) { //增加

			if ($levelOutput.find('dd[code="' + code + '"]').length) return false;

			if (seat) { //二级收集处

				var oneLevelCode, oneLevelText, $target;

				oneLevelCode = $this.parents('.level').attr('code');
				oneLevelText = $salesAreasCheckbox.find('[value="' + oneLevelCode + '"]').attr('toponymy');
				$target = $levelOutput.eq(seat).find('[code="' + oneLevelCode + '"]');

				if ($target.length) { //存在该大项

					$levelOutput.eq(seat).find('dl[code="' + oneLevelCode + '"]').append('<dd class="fn-left fn-mr20" code="' + code + '">' + text + '</dd>');

				} else {

					$levelOutput.eq(seat).append(
						[
							'<dl class="fn-clear" code="' + oneLevelCode + '">',
							'<dt class="fn-left">' + oneLevelText + '：</dt>',
							'<dd class="fn-left fn-mr20" code="' + code + '">' + text + '</dd>',
							'</dl>'
						].join('')
					);

				}


			} else { //一级收集处

				var $salesAreasCheckboxLevel1 = $salesAreasCheckbox.find('.level:eq(1)');

				if ($salesAreasCheckboxLevel1.attr('code') == code) {

					$salesAreasCheckboxLevel1.find('ul').html('');

				}

				$levelOutput.eq(1).find('[code="' + code + '"]').remove();
				$levelOutput.eq(seat).find('dl').append('<dd class="fn-left fn-mr20" code="' + code + '">' + text + '</dd>');

			}

		} else { //移除

			var $salesAreasOutputCode;

			$salesAreasOutputCode = $salesAreasOutput.find('dd[code="' + code + '"]');

			if (seat && $levelOutput.eq(seat).find('dd[code]').length == 1) {

				$salesAreasOutputCode.parents('[code]dl').remove();

			}

			$salesAreasOutputCode.remove();

		}

		salesAreasCollect();

	}).on('click', '.salesAreasCheckbox a', function(event) {

		var $this, $checkbox, junior, code, Html, arr, $twoLevelOutput, judge;

		$this = $(this);
		$checkbox = $this.siblings('input[type="checkbox"]');
		junior = $checkbox.attr('junior');
		code = $checkbox.val();
		Html = '';
		$twoLevelOutput = $salesAreasOutput.find('.levelOutput:eq(1) dl[code="' + code + '"]');

		if (!!!junior) return false;

		arr = junior.split(';');

		for (var i in arr) {

			judge = !$twoLevelOutput.length || ($twoLevelOutput.length && $twoLevelOutput.find('dd[code="' + arr[i].split(',')[1] + '"]').length);

			Html += '<li class="fn-left fn-mr30"><input class="fn-mr5" value="' + arr[i].split(',')[1] + '" toponymy="' + arr[i].split(',')[0] + '" type="checkbox" ' + (judge ? "checked" : "") + '>' + arr[i].split(',')[0] + '</li>'

		}

		$salesAreasCheckbox.find('.level:eq(1)').attr('code', code)
			.find('ul').html(Html)
			.find('input[type="checkbox"]').change();
		$checkbox.attr('checked', false).change();

	}).on('click', '.packBtn', function(event) { //收起
		var _this = $(this);
		_this.hasClass('reversal') ? _this.removeClass('reversal') : _this.addClass('reversal');
		_this.parent().prev().slideToggle("2",function () {
            var salesAreasCheckbox = _this.parent().siblings(".salesAreasCheckbox");
            if(!salesAreasCheckbox.is(":hidden")){
                _this.parents(".salesAreasBox").animate({"width":"90%"},200);
            }else {
                _this.parents(".salesAreasBox").animate({"width":"30%"},200);
            }
        });
	});
    if($('.selectFnBox1').length > 0) newSelectType($('.selectFnBox1'));
    function newSelectType($select) {

        new selectType({
            selectBoxObj: $select,
            isReadOnly: false, //缺省false。统一配置的只读属性，只读属性也可以通过 <div class="selectFnBox1" isreadonly="true">...</div>这种方式（设置属性）来单独设置，优先级：属性 > 统一配置 > 默认值（false）
            afterChoosedCallback: { //缺省false。完成选择之后的回调
                callbackTargetCommon: function($this) { //如果$('.selectFnBox1')上未定义属性callbacktarget，那么将会执行此回调函数，如果此函数未定义将执行插件内部的回调
                    var Box,kindsid,catalogName;

                    Box = $this.parents('.selectFnBox1');
                    kindsid = $this.attr('kindsid');
                    catalogName = $this.attr('valuedata');
                    // console.log(Box.find('.catalogId'))
                    Box.find('.catalogId').val(kindsid);
                    Box.find('.catalogName').val(catalogName);
                    console.log(Box.find('.catalogName').val(catalogName));

                }
            }
        });
    }

    //-----------------------
    $(function(){
    	$(".firstNo").attr("readonly",true)
	});
});