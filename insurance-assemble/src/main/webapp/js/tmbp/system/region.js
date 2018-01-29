define(function (require, exports, module) {

	require('Y-msg');

	require('input.limit');

	require('validate');

	var util = require('util');

	var $form = $('#form');

	var setting = {
		view: {
			selectedMulti: false
		},
		async: {
			enable: true,
			url: "/systemMg/region/tree.json",
			autoParam: ["code=parentCode"]
		},
		callback: {
			onClick: onClick,
			beforeRemove: beforeRemove
		},
		edit: {
			enable: true,
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: false,
			removeTitle: "删除"
		},
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		}
	};

	var tree = $.fn.zTree.init($("#regionTree"), setting);

	function onClick(event, treeId, treeNode) {
		// getParentNode
		$form.find("[name=id]").val(treeNode.id);
		$form.find("[name=code]").val(treeNode.code);
		$form.find("[name=name]").val(treeNode.name);
		$form.find("[name=parentCode]").attr("readonly", false).val(treeNode.parentCode);
		$form.find("[name=hasChildren][value=" + treeNode.hasChildren + "]").attr("checked", true);
		$form.find("[name=sortOrder]").val(treeNode.sortOrder);
	}

	function showRemoveBtn(treeId, treeNode) {
		return treeNode.hasChildren != "IS";
	}

	function addHoverDom(treeId, treeNode) {
		if (treeNode.hasChildren != "IS")
			return;
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增下级区域' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function () {
				$form.find("[name=id]").val(0);
				$form.find("[name=code]").val("");
				$form.find("[name=name]").val("");
				$form.find("[name=parentCode]").attr("readonly", true).val(treeNode.code);
				$form.find("[name=sortOrder]").val(1);
				return false;
			});
	}

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	}

	function beforeRemove(treeId, treeNode) {
		if (window.confirm("确定删除[ " + treeNode.name + " ]吗？")) {
			// 删除节点
			util.ajax({
				url: "/systemMg/region/delete.json",
				data: {
					id: treeNode.id
				},
				success: function (res) {
					if (res.success) {
						var pNode;
						if(treeNode && treeNode.getParentNode()){
							pNode = treeNode.getParentNode();
						}
						tree.reAsyncChildNodes(pNode, "refresh");
					} else {
						Y.alert('操作失败', res.message);
					}
				}
			});

			return true;
		} else {
			return false;
		}
	}

	var REQUIRERULES = { // 验证规则
		rules: {},
		messages: {}
	};

	// 必填
	$('.fnRequired').each(function (index, el) {

		util.ObjAddkey(REQUIRERULES.rules, el.name, {
			required: true
		});
		util.ObjAddkey(REQUIRERULES.messages, el.name, {
			required: '必填'
		});

	});

	$form.validate($.extend(true, REQUIRERULES, {
		errorClass: 'error-tip',
		errorElement: 'b',
		ignore: '.ignore',
		onkeyup: false,
		errorPlacement: function (error, element) {
			if (element.hasClass('fnErrorAfter')) {
				element.after(error);
			} else {
				element.parent().append(error);
			}
		},
		submitHandler: function () {
			util.ajax({
				url: "/systemMg/region/save.json",
				data: $form.serialize(),
				success: function (res) {
					if (res.success) {
						var selectedNode = tree.getSelectedNodes()[0];
						var pNode;
						if(selectedNode && selectedNode.getParentNode()){
							pNode = selectedNode.getParentNode();
						}
						tree.reAsyncChildNodes(pNode, "refresh");
					} else {
						Y.alert('操作失败', res.message);
					}
				}
			});
		}
	}));
});