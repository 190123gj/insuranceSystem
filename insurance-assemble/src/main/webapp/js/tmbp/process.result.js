define(function(require, exports, module) {

	var submitedFun = require('zyw/submited'),
		util = require('util'),
		loading = new util.loading();

	function processResult(res, hintPopup) {

		if (res.success) {
			var formId = $('#formId').val();
			if (isNaN(formId) || formId == '0') {
				formId = res.form.formId;
				$('#formId').val(formId);
			}
			var projectCode = $('#projectCode').val();
			var submited = $('#submited').val();
			var nextUrl = $('#nextUrl').val();
			if (res.url == '#') {
				hintPopup(res.message, window.location.href.replace(/\&scrollTop.+(?=\&{0,1})/g, '') + (/\?/.test(window.location.href) ? '&' : '?') + 'scrollTop=' + $('#years').attr('scrolltop'));
				$('.m-modal-box').remove();
			} else if (res.url == '##') {
				$('.m-modal-box').remove();
				var _checkStatus = res.form.checkStatus;

				submitedFun(_checkStatus);
				$('[name="submited"]').val(_checkStatus);
				if (res.token) {
					$('#token').val(res.token);
				}

				if (_checkStatus != '1111111111') {
					$('body,html').animate({
						scrollTop: 0
					});
					return false;
				}

				if (res.riskCoverRate) {
					$('[name="customerCoverRate"]').val(res.riskCoverRate);
				}
				var _customerCoverRate = parseFloat($('[name="customerCoverRate"]').val()),
					_riskCoverRate = parseFloat($('[name="riskCoverRate"]').val());
				$('body').Y('Window', {
					content: ['<div class="newPopup">',
						'    <span class="close">×</span>',
						'    <dl>',
						'        <dt><span>风险覆盖率试算结果</span></dt>',
						'        <dd class="fn-text-c">',
						'            <h1 class="fn-color-4a fn-mt10">该客户的风险覆盖率为<em class="remind">' + _customerCoverRate + '%</em></h1>',
						'        </dd>',
						'    </dl>',
						'</div>'
					].join(""),
					modal: true,
					key: 'modalwnd',
					simple: true,
					closeEle: '.close'
				});

				var modalwnd = Y.getCmp('modalwnd');
				var applyStepActive = $('.apply-step .item.active'),
					hasAssetVal = $('[name="hasAsset"]').val(),
					hasAsset;

				if (!applyStepActive.index()) {

					var test22 = $('#test22'),
						test = $('#test'),
						length1 = parseInt(test22.find('tr').length),
						length2 = parseInt(test.find('tr').length);

					hasAsset = (length1 + length2) ? true : false;

				} else {

					hasAsset = (hasAssetVal == 1) ? true : false;

				}

				modalwnd.wnd.find('h1').after(((_customerCoverRate < _riskCoverRate) ? '<p class="fn-mt25">低于公司设定的风险覆盖率阀值' + _riskCoverRate + '%</p>' : '') +
					(!hasAsset ? '<p class="fn-mb45 fn-mt5">没有相关抵质押物！</p>' : '') +
					'<span>' +
					'   <a class="fn-csp fn-left ui-btn ui-btn-submit ui-btn-next fn-ml5 fn-mr5 fnNext fn-size16 submit">提交</a>' +
					(((_customerCoverRate < _riskCoverRate) || !hasAsset) ? '   <a class="fn-csp fn-left ui-btn ui-btn-submit ui-btn-cancel fn-ml5 fn-mr5 fnNext fn-size16" href="/customerMg/evaluting/add.htm?userId=' + $('#customerId').val() + '" target="_blank">客户评级</a>' : '') +
					'   <a class="fn-csp fn-left ui-btn ui-btn-submit ui-btn-blue fn-ml5 fn-mr5 fnNext fn-size16 close">取消</a>' +
					'</span>')


				modalwnd.wnd.find('.close').click(function(event) {
					modalwnd.close();
				});

				modalwnd.wnd.find('.submit').click(function(event) {
					loading.open();
					$.ajax({
						url: '/projectMg/form/submit.htm',
						type: 'post',
						dataType: 'json',
						data: {
							formId: formId
						},
						success: function(data) {
							loading.close();
							hintPopup(data.message, function() {
								if (data.success) {
									window.location.href = '/projectMg/investigation/list.htm';
								}
							});
						}
					})
				});



			} else if (res.url != '') {
				var toUrl = res.url + '?formId=' + formId + '&toIndex=' + res.toIndex + '&submited=' + submited;
				window.location.href = toUrl;
			} else {
				var toUrl = '/projectMg/investigation/edit.htm' + '?formId=' + formId + '&toIndex=' + res.toIndex + '&submited=' + submited;
				window.location.href = toUrl;
			}
		} else {
			$('.m-modal-box').remove();
			hintPopup(res.message);
		}
	}
	module.exports = processResult
})