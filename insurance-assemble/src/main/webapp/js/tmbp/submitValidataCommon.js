define(function(require, exports, module) {
	//验证方法集
	require('zyw/project/bfcg.itn.addValidataCommon');
	//弹窗提示
	var hintPopup = require('zyw/hintPopup'),
		submitedFun = require('zyw/submited');


	function submitValidataCommon(objList) {

		/*
			objList = {
				rulesAll:_rulesAll,
				form:_form,
				allWhetherNull:{
					stringObj:_stringObj,
					boll:_boll
				},
				ValidataInit:{
					form: _form,
					successFun: function(res) {

							响应成功操作
							响应成功操作

					}
				} //有特殊返回成功不同页面时可使用
			}
		*/

		var fillReviewTypeCommon = require('zyw/project/assistsys.smy.fillReviewTypeCommon'),
			ValidataInitObj = objList['ValidataInit'] || { //外部获取响应操作，objList['ValidataInit']为空则调用公用初始化validate

				form: objList['form'],

				successBeforeFun: function(successBeforeJson) {

					/*
						successBeforeJson:{
							fm5:'',
							checkStatus:'',
							urlParameter: {
								activeTabId: $('#step li.active').attr('tabId'),
								formId: $('#step').attr('formId')
							}
						}
					*/

					if (!successBeforeJson['fm5'] && successBeforeJson['urlParameter']['spId'] != 'all') { //数据未改变

						if (successBeforeJson['urlParameter']['spId'] == successBeforeJson['urlParameter']['activeTabId']) { //暂存

							hintPopup('数据未改变,保持原有存储');

						} else { //跳转

							window.location = '/projectMg/meetingMg/summary/edit.htm?formId=' + successBeforeJson['urlParameter']['formId'] + '&spId=' + successBeforeJson['urlParameter']['spId'] + '&submitedAll=' + $('#step').attr('submitedAll');

						}

						$.fn.orderName();

						return false;

					} else { //数据有改变

						var util = require('util'),
							loading = new util.loading();
						loading.open();
						//console.log(successBeforeJson['checkStatus']);
						$('[name="checkStatus"]').val(successBeforeJson['checkStatus']);
						$('[name="tabId"]').val(successBeforeJson['urlParameter']['spId']);
						$.fn.orderName();

						return true;

					}

				},

				successFun: function(res) { //公用响应成功操作

					if (res['success']) { //提交成功

						if (res['tabId'] == 'all') { //all提交

							submitedFun(1 + res['form']['checkStatus'], true);
							//console.log(res['form']['checkStatus']);
							$('#step').attr('submitedAll', res['form']['checkStatus']);

							if (/0/.test(res['form']['checkStatus'])) {

								$('body').animate({
									scrollTop: $('#step').offset().top - 10
								});
								$('.m-modal-box').remove();
								return false;

							}

							$.ajax({

								url: '/projectMg/form/submit.htm',
								type: 'post',
								dataType: 'json',
								data: {
									formId: res['form']['formId']
								},
								success: function(data) {

									hintPopup(data.message, function() {

										if (data.success) {
											window.location.href = '/projectMg/meetingMg/councilList.htm';
										}

										$('.m-modal-box').remove();

									});

								}

							});

						} else { //非all提交

							$('.m-modal-box').remove();

							if (res['tabId'] == $('#step li.active').attr('tabId')) { //暂存

								hintPopup(res['message'], window.location.href);

							} else { //切换

								window.location.href = '/projectMg/meetingMg/summary/edit.htm?formId=' + res['form']['formId'] + '&spId=' + res['tabId'] + '&submitedAll=' + res['form']['checkStatus'];

							}

						}

						return false;

					} else { //提交失败

						$('.m-modal-box').remove();
						hintPopup(res['message']);

					}

				}

			};

		fillReviewTypeCommon.ValidataInit(ValidataInitObj); //validate初始化


		//验证方法集初始化
		$('.fnAddLineNew').addValidataCommon(objList['rulesAll'], true)
			.initAllOrderValidata()
			.fillGroupAddValidata();
		$('.fnAddLine').addValidataCommon(objList['rulesAll'], true)
			.groupAddValidata();


		var md5 = require('md5'), //md5加密
			validateDataReturnGather = { //数据是否改变、否必填

				formSerializeMd5: function(_form) { //返回表单序列值

					var _formSerialize = _form.serialize();

					return md5(_formSerialize);

				},

				fm5WhetherChange: function() { //数据是否有改变

					var _newSerializeMd5 = validateDataReturnGather['formSerializeMd5'](objList['form']),
						fm5 = (_newSerializeMd5 != _initSerializeMd5) ? 1 : 0; //数据是否有改变

					return fm5;

				},

				rulesAllFalse: function() { //数据是否完整

					var checkStatus = objList['form'].allWhetherNull(objList['allWhetherNull']['stringObj'], objList['allWhetherNull']['boll']); //是否填写完整

					return checkStatus

				},

				urlParameter: { //响应成功URL参数
					activeTabId: $('#step li.active').attr('tabId'),
					formId: $('#step').attr('formId')
				},

			},

			collectJsonFun = function() {

				return {
					validateData: {
						//异步提交的data
					},
					urlParameter: validateDataReturnGather['urlParameter'],
					fm5: validateDataReturnGather['fm5WhetherChange'](), //页面是否有改动
					checkStatus: validateDataReturnGather['rulesAllFalse']() //当前页面是否填写完整
				}

			},

			_initSerializeMd5 = validateDataReturnGather['formSerializeMd5'](objList['form']); //初始页面数据

		//提交
		$('.submit').click(function(event) {

			var _this = $(this),
				_val = parseInt(validateDataReturnGather['urlParameter']['activeTabId']),
				_tabId;

			if (_this.attr('branch') == 'submitNext') {

				_tabId = _val + 1;
				$.fn.whetherMust(objList['rulesAll'], true).allAddValidata(objList['rulesAll']); //是必填

			} else if (_this.attr('branch') == 'submitPrev') {

				_tabId = _val - 1;
				$.fn.whetherMust(objList['rulesAll'], false).allAddValidata(objList['rulesAll']); //是必填

			} else if (_this.attr('branch') == 'submit') {

				_tabId = 'all';
				$.fn.whetherMust(objList['rulesAll'], true).allAddValidata(objList['rulesAll']); //是必填

			}

			exports.submitHandlerContent = $.extend(true, collectJsonFun(), { //对validate初始化文件提供提交时相应的接口
				urlParameter: {
					spId: _tabId
				}
			});

			fillReviewTypeCommon.submitHandlerContent('zyw/submitValidataCommon'); //执行validate初始化文件里submitHandlerContent类返回相应的json

			objList['form'].submit();

		});

		$('#step li').click(function(event) {

			var _tabId = $(this).attr('tabId');

			if (_tabId == validateDataReturnGather['urlParameter']['activeTabId']) return false; //点击原页页签不提交

			exports.submitHandlerContent = $.extend(true, collectJsonFun(), { //对validate初始化文件提供提交时相应的接口
				urlParameter: {
					spId: _tabId
				}
			});

			fillReviewTypeCommon.submitHandlerContent('zyw/submitValidataCommon'); //执行validate初始化文件里submitHandlerContent类返回相应的json

			$.fn.whetherMust(objList['rulesAll'], false).allAddValidata(objList['rulesAll']); //否必填

			objList['form'].submit();

		});

		if (!objList.notShowSidebar) {
			(new(require('zyw/publicOPN'))()).addOPN([{
				name: '暂存',
				alias: 'temporarStorage',
				event: function() {

					exports.submitHandlerContent = $.extend(true, collectJsonFun(), { //对validate初始化文件提供提交时相应的接口
						urlParameter: {
							spId: validateDataReturnGather['urlParameter']['activeTabId']
						}
					});

					fillReviewTypeCommon.submitHandlerContent('zyw/submitValidataCommon'); //执行validate初始化文件里submitHandlerContent类返回相应的json

					$.fn.whetherMust(objList['rulesAll'], false).allAddValidata(objList['rulesAll']); //否必填

					objList['form'].submit();
				}
			}, {
				name: '提交',
				alias: 'fulfilSubmit',
				event: function() {

					exports.submitHandlerContent = $.extend(true, collectJsonFun(), { //对validate初始化文件提供提交时相应的接口
						urlParameter: {
							spId: 'all' //提交
						}
					});

					fillReviewTypeCommon.submitHandlerContent('zyw/submitValidataCommon'); //执行validate初始化文件里submitHandlerContent类返回相应的json

					$.fn.whetherMust(objList['rulesAll'], true).allAddValidata(objList['rulesAll']); //是必填

					objList['form'].submit();

				}
			}]).init().doRender();
		}

	}


	exports.submitValidataCommon = submitValidataCommon;


});