define(function (require, exports, module) {

	require('Y-msg');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
    require('tmbp/selectType'); //下拉选择

	var util = require('util');

	var getList = require('zyw/getList');
    var $zTreeMenu = $("#regionTree");
    var $body = $('body');
    var template = new getList();
    var $GETLIST_TR = '';
    var zTree;
    var rMenu = $("#rMenu");//右键节点

    //弹窗选择类型
    var tree2; //用来缓存弹窗的
    var modalwnd,modalwnd1; //用来缓存弹窗
    var checkedData = {};//储存已经选择的数据

    var zTreeSelectNode = {};//储存已选择的节点（右键和点击都会更新此节点）
    template.init({
		title: '保险责任',
		ajaxUrl: '/baseDataLoad/insuranceLiability.json',
		btn: '.fnToChoosesss',
		// multiple:"radio",
		tpl: {
			tbody: ['{{each pageList as item i}}',
				'    <tr class="fn-tac m-table-overflow">',
				'        <td title="{{item.name}}">{{item.name}}</td>',
                '       <td><a class="choose" sid="{{item.id}}" sname="{{item.name}}"  href="javascript:void(0);">选择</a></td>',
				'    </tr>',
				'{{/each}}'
			].join(''),
			form: ['保险责任：',
				'<input class="ui-text fn-w100" type="text" name="name">',
				'&nbsp;&nbsp;',
				'<input class="ui-btn ui-btn-fill ui-btn-green" type="submit" value="筛选">'
			].join(''),
			thead: ['<th>责任名称</th>',
			 	'<th width="50">操作</th>'
			].join(''),
			item: 5
		},
        callback: function($a) {
            $GETLIST_TR.find('[name*=liabilityId]').val($a.attr("sid"));
            $GETLIST_TR.find('[name*=liabilityName]').val($a.attr("sname"));
        }
    });
//清楚按钮
    $body.on('click','#fnToClearLiabilityId',function () {
        var $this = $(this);
        $this.siblings('input').val('');
    });
    //ztree
	var setting = {
		view: {
			selectedMulti: false
		},
		async: {
			enable: true,
			url: "/insurance/insuranceCatalog/tree.json",
			autoParam: ["parentCatalogId"]
		},
		callback: {
			onClick: onClick,
			beforeRemove: beforeRemove,
            onRightClick:OnRightClick,
            onCollapse: collapseNode,
            onExpand: expandNode
        },
		edit: {
			enable: true,
			// showRemoveBtn: showRemoveBtn,
			showRenameBtn: false,
			removeTitle: "删除"
		},
		view: {
			// addHoverDom: addHoverDom,
			// removeHoverDom: removeHoverDom,
			selectedMulti: false
		}
	};
//-----------------------
    function collapseNode (event, treeId, treeNode) {
        // treeNode.open();
        // alert(treeNode.tId + ", " + treeNode.name);

        console.log(treeNode.open,treeId);
    };
    function expandNode(event, treeId, treeNode) {
        treeNode.open= true;
        console.log(treeNode.open,treeId,treeNode.name,treeNode);
    };
    //------------------

    var tree = $.fn.zTree.init($zTreeMenu, setting);
    zTree = $.fn.zTree.getZTreeObj("regionTree");
    console.log(zTree);


    function updateSelectNode(treeId,treeNode) {//更新，右键等操作更新当前节点
        console.log(treeNode);
        zTreeSelectNode = {};
        zTreeSelectNode = treeNode;
        $("#catalogId").val(treeNode.id);
        $("#type").val(treeNode.type);
        $('#regionTreeId').val(treeNode.tId);
        $('#gradeName').val(treeNode.name);
        $('#gradeDepth').val(treeNode.depth);
        $('#parentNodeId').val(!getParentNode(treeNode) ? 0 : getParentNode(treeNode).id);
        updateRightMenu(treeId,treeNode);
    };
    function updateRightMenu(treeId,treeNode) {//更新右键菜单
        if(treeNode.type == 'kinds'){
            rMenu.find('.newProduct').removeClass('fn-hide');
            rMenu.find('.opt').addClass('fn-hide');
        }else {
            rMenu.find('.newProduct').addClass('fn-hide');
            rMenu.find('.opt').removeClass('fn-hide');
        }
    };

    function getParentNode(treeNode) {//取得父级
        // if(treeNode)console.log(treeNode.getParentNode().getParentNode())
        return zTree.getSelectedNodes()[0].getParentNode();
    }

    function onClick(event, treeId, treeNode) {
        console.log(treeNode)
        updateSelectNode(treeId,treeNode);
        if(treeNode.type == 'kinds'){
            $.ajax({
                url:'/insurance/insuranceProduct/list.json',
                data:{
                    catalogId:treeNode.id
                },
                success:function (res) {
                    // console.log(res)
                    if(res.success && !!res.data && res.data.pageList.length > 0){
                        var lists = res.data.pageList;
                        var $tempConent;
                        $.each(lists,function (index,list) {
                            var $newTr = $($('#PRODUCT_LIST').html());
                            var thisHref = $newTr.find('.product_name a').attr('href');
                            $newTr.find('.product_num').html(index).end()
                                .find('.product_name a').attr('href',thisHref + list.productId).html(list.productName).end()
                                .find('.product_comanyUserName').html(list.comanyUserName).end()
                                .find('.product_saleStatus').html(list.saleStatus).end()
                                .find('.product_rawAddTime').html(list.rawAddTime).end()
                            if(index == 0){
                                $tempConent = $newTr;
                            }else {
                                $tempConent.after($newTr);
                            };
                            if(index == lists.length - 1) {
                                $('tbody').html($tempConent);
                                if(res.data.pageNumber > 1) {
                                    var showMore = '<tr class="fn-tac">'+
                                        '<td colspan="5"><a href="/insurance/insuranceProductst/list.htm?catalogId=' + treeNode.id + '" target="_blank">显示更多</a></td>'+
                                        '</tr>';
                                    $('tbody').append(showMore);
                                }
                            }
                        });
                    }else {
                        var noData = '<tr class="fn-tac">'+
                            '<td colspan="5">暂无数据</td>'+
                            '</tr>';
                        $('tbody').html(noData);
                    }
                }
            });
        }
        event.preventDefault();
	}


    function OnRightClick(event, treeId, treeNode) {//右键点击
        var rightClickTargetName = ['SPAN','A'];
        if(rightClickTargetName.indexOf(event.target.tagName) < 0) return;
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            zTree.cancelSelectedNode();
            showRMenu("root", event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
            zTree.selectNode(treeNode);
            showRMenu("node", event.clientX, event.clientY);
        };
        updateSelectNode(treeId,treeNode)
    }

    function showRMenu(type, x, y) {//显示右键菜单
        $("#rMenu ul").show();
        if (type=="root") {
            $("#m_del").hide();
            $("#m_check").hide();
            $("#m_unCheck").hide();
        } else {
            $("#m_del").show();
            $("#m_check").show();
            $("#m_unCheck").show();
        }
        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

        $body.bind("mousedown", onBodyMouseDown);
    }
    function hideRMenu() {//隐藏菜单
        if (rMenu) rMenu.css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
    }
    function onBodyMouseDown(event){
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
            rMenu.css({"visibility" : "hidden"});
        }
    }

	function beforeRemove(treeId, treeNode) {//删除
        hideRMenu();
        var result = false;
        Y.confirm('提示','确定删除[ ' + treeNode.name + ' ]吗？', function (opt) {
            if(opt == 'yes'){
                // 删除节点
                util.ajax({
                    url: "/insurance/insuranceCatalog/delete.json",
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
                            // tree.reAsyncChildNodes(pNode, "refresh");
                        }
                    }
                });
            };
            result = (opt == 'yes');
        });
        return result;
	};


    $body.on('click', '.fnToChoose', function () {
        $GETLIST_TR = $(this).parents('tr');
        template.getDate(1);
        template.show();
    }).on('click',function () {//隐藏菜单
        $zTreeMenu.find('.rightClickMenu').remove().end()
            .find('li').removeClass('fn-bg-gray');
    }).on('contextmenu','#regionTree',function(){//禁用右键菜单
        return false;
    }).on('click','.rightClickMenu ul .opt', function () {//新增险种，新增同级，子集菜单
        var $this = $(this);
        var templateId = $this.attr('tempalteid');
        if(!templateId) return;
        showNodeD(templateId,$this);
    }).on('click','.rightClickMenu ul .removeNode', function () {//移除节点
        var nodeId = $('#regionTreeId').val();
        $('#' + nodeId + '_remove').click();

    }).on('click','.rightClickMenu ul .editNode', function () {//编辑和查看节点
        var $this = $(this);
        var templateId = $('#type').val() == "catalog" ? 'NEW_GRADE_NODE' : $('#type').val() == "kinds" ? 'NEW_INSUREANCE_GRADE' : '';
        var isView = $(this).hasClass('ViewsNode');
        if(!templateId) return;
        showNodeD(templateId,$this,isView);
        $('.wnd-common').find('[name=parentCatalogId]').attr('name','catalogId').val($('#catalogId').val());

    }).on('click','.rightClickMenu ul .newProduct', function () {//新增产品
        var $this = $(this);
        var href = $this.find('a').attr('linkHref') + '?catalogId=' + $('#catalogId').val() + '&catalogName=' + encodeURIComponent($('#gradeName').val())+"&isLifeInsurance="+ zTreeSelectNode.isLifeInsurance;
        // console.log(href)
        $this.find('a').attr('href',href);
    }).on('click','[insurancetype]',function () {//选择保险类型
        var $this = $(this);
        var $thisParents = $this.parents('.wnd-common');
       var insuranceType = $(this).attr('insurancetype');
        var $mainInsurance = $thisParents.find('.mainInsurance');
        var $otherInsurance = $thisParents.find('.otherInsurance');
        if(insuranceType == 'otherInsurance'){

            $mainInsurance.addClass('fn-hide');
            $mainInsurance.find('[name]').attr('disabled','disabled');
            $mainInsurance.find('select[name],[type=text][name],[type=hidden][name]').val('').blur();

            $otherInsurance.removeClass('fn-hide');
            $otherInsurance.find('[name]').removeAttr('disabled');

        }else {

            $mainInsurance.removeClass('fn-hide');
            $mainInsurance.find('[name]').removeAttr('disabled');

            $otherInsurance.addClass('fn-hide');
            $otherInsurance.find('[name]').attr('disabled','disabled');
            $otherInsurance.find('select[name],[type=text][name],[type=hidden][name]').val('').blur();

        }
    }).on('click','.fnToChooseInsuranceName',function () {//触发类型选择

        var $this = $(this);
        var $ztreeObj = $('<div id="modRMenu" class="ztree"></div>');
        var setting2 = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: true
            },
            async: {
                enable: true,
                url: "/insurance/insuranceCatalog/tree.json",
                autoParam: ["parentCatalogId"]
            },
            callback: {
                // onClick: onClick,
                onCheck: zTreeOnCheck,
                onAsyncSuccess:function (event, treeId, treeNode, msg) {//控制是否选择复选框
                    // console.log(treeNode)
                    // console.log($.parseJSON(msg))
                    var $thisRadio = $this.parents("#markerrorForm").find("[name*='isMain']:checked");
                    var treeObjs = !treeNode ? $.parseJSON(msg) : treeNode.children;
                    $.each(treeObjs,function (key,treeObj) {
                        var treeIdCheck = !treeNode ? treeId + '_'+(key+1)+'_check' : treeObj.tId + '_check';
                        console.log(key,treeIdCheck);
                        //判断是否是主险，以及是否是当前险种
                        if($thisRadio.val()=="YES"){
                            if(treeObj.type !="kinds" ||(treeObj.isMain=="YES" && treeObj.type=="kinds")|| $('#catalogId').val() == treeObj.id) {
                                $('#' + treeIdCheck).remove();
                            }
                        }else {
                            if(!(treeObj.isMain=="YES")|| $('#catalogId').val() == treeObj.id) {
                                $('#' + treeIdCheck).remove();
                            }
                        }
                        // if(treeObj.hasChildren == 'YES' || $('#catalogId').val() == treeObj.id) $('#' + treeIdCheck).remove();
                    });
                }
            }
        };
        tree2 = $.fn.zTree.init($ztreeObj, setting2);
        var $content = $(
            '<div class="m-modal-box fn-hide">'+
            '    <div class="m-modal-overlay"></div>'+
            '    <div class="m-modal m-modal-default" id="modal" style="height: auto;">'+
            '        <div class="m-modal-title"><span class="m-modal-close close">&times;</span><span class="title" id="boxTitle">选择险种</span></div>'+
            '        <div class="m-modal-body" style="min-height: 200px;max-height: 500px;overflow-y:auto;">'+
                    // $('#CHOOSED_INSURANCE_TYPE').html(),
            '        </div>'+
            '        <div class="fn-tac fn-pt10">' +
            '           <span href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-green fn-fwb sureToChooseInsuranceType fn-ml10 fn-mr10 fn-csp">确定</span>' +
            '           <span href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-fill-big ui-btn-gray fn-fwb close fn-ml10 fn-mr10 fn-csp">取消</span>' +
            '        </div>'+
            '    </div>'+
            '</div>');
        $content.find('.m-modal-body').html($ztreeObj);
        $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd1',
            simple: true,
            closeEle: '.close',
            close: function () {//关闭回调
                checkedData = {};//如果取消则清空已选择的数据
            },
            show: function () {//显示回调
                hideRMenu();
                $('.m-modal-box').css('z-index','50000');
            }
        });
        modalwnd1 = Y.getCmp('modalwnd1');
        function zTreeOnCheck(event, treeId, treeNode) {
            // alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
            if(treeNode.checked){
                checkedData[treeNode.tId] = treeNode;
            }else {
                checkedData[treeNode.tId] = '';
            };
            // console.log(checkedData)
        };
    }).on('click','.sureToChooseInsuranceType',function () {//确认选择
        var $this = $(this);
        var $thisRadio = $(".fnToChooseInsuranceName").parents("#markerrorForm").find("[name*='isMain']:checked");
        var checkedObj = {
            ids:[],
            names:[]
        }
        $.each(checkedData,function (index,obj) {
            checkedObj.ids.push(obj.id)
            checkedObj.names.push(obj.name)
        });
        console.log($thisRadio.val());
        if($thisRadio.val()=='YES'){
            modalwnd.wnd.find('[name=childrenIds]').val(checkedObj.ids);
            modalwnd.wnd.find('[name=childrenNames]').val(checkedObj.names);
        }else{
            modalwnd.wnd.find('[name=parentIds]').val(checkedObj.ids);
            modalwnd.wnd.find('[name=parentNames]').val(checkedObj.names);
        }
        modalwnd1.close();
    });


    function showNodeD(templateId,$tab,isView) {//显示弹窗
        var $content = $($('#' + templateId).html());
        var title = $tab.attr('templatetitle');
        if(!!title) $content.find('.wnd-title').html(title);
        var isSameNode = $tab.hasClass('NEW_SAME_NODE');
        var isChildNode = $tab.hasClass('NEW_CHILD_NODE');
        var isInsureanceNode = $tab.hasClass('NEW_INSUREANCE_GRADE');
        var isEdit = $tab.attr('tempalteid') == 'EDIT_NODE';
        $body.Y('Window', {
            content: $content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn',
            close: function () {//关闭回调
                // $this.parents('tr').removeClass('checkedItem');
            },
            show: function () {//显示回调
                hideRMenu();
                $('.m-modal-box').css('z-index','50000');
                $('.wnd-common .wnd-body').css("cursor","auto")
            }
        });
        modalwnd = Y.getCmp('modalwnd');
        if(templateId != 'NEW_INSUREANCE_GRADE') modalwnd.wnd.find('[name=type]').val($('#type').val());
        //层级判断
        var gradeType = $('#gradeDepth').val() || 0;//如果没有曾经则默认为第一级
        var gradeName = [];

        gradeType = (isChildNode || isInsureanceNode) ? (+gradeType + 1)  : gradeType;//如果是子节点或者保险那么层级就+1

        function getParentNodes(thisNode) {//无限向上获取父级，返回一组数组，数组储存节点名字
            gradeName.push(thisNode.name);
            if(!!thisNode.getParentNode()){
                getParentNodes(thisNode.getParentNode());
            }else {
                // gradeName = gradeName.reverse().join('-');
                // console.log(gradeName)
            }
        };

        getParentNodes(zTreeSelectNode);
        (isSameNode || isEdit || isView) && gradeName.shift();//如果是同级，编辑，查看，去掉最后一个节点名字
        gradeName = gradeName.length == 0 ? '根' : gradeName.reverse().join(' - ');

        modalwnd.wnd.find('[insurancetype="mainInsurance"]').click();//新增的时候重置
        modalwnd.wnd.find('[name=isLifeInsurance][value=' + zTreeSelectNode.isLifeInsurance + ']').click();
        modalwnd.wnd.find('[name=isMain][value=' + zTreeSelectNode.isMain + ']').click();

        modalwnd.wnd.find('[name=parentCatalogId]').val($('#catalogId').val());
        modalwnd.wnd.find('.gradeName').html(gradeName);
        modalwnd.wnd.find('.gradeType').html(gradeType);
        modalwnd.wnd.find('[name=abbr1]').val(zTreeSelectNode.abbr1);
        modalwnd.wnd.find('[name=abbr2]').val(zTreeSelectNode.abbr2);
        modalwnd.wnd.find('[name=abbr3]').val(zTreeSelectNode.abbr3);
        modalwnd.wnd.find('[name=abbr3]').val(zTreeSelectNode.abbr3);
        modalwnd.wnd.find('[name=parentIds]').attr('value',zTreeSelectNode.parentIds);
        modalwnd.wnd.find('[name=parentNames]').attr('value',zTreeSelectNode.parentNames);
        modalwnd.wnd.find('[name=childrenIds]').attr('value',zTreeSelectNode.childrenIds);
        modalwnd.wnd.find('[name=childrenNames]').attr('value',zTreeSelectNode.childrenNames);
        modalwnd.wnd.find('[name=catalogCode]').val(zTreeSelectNode.catalogCode);
        modalwnd.wnd.find('[name=priceTemplate]').val(zTreeSelectNode.priceTemplate);
        if(isSameNode) $content.find('[name=parentCatalogId]').val($('#parentNodeId').val())

        if(isView) {
            modalwnd.wnd.find('input').attr('readonly','readonly').end()
                .find('select').attr('disabled','disabled').end()
                .find('.addLine').remove().end()
                .find('.choose-dailog a').remove().end()
                .find('.wnd-bottom').remove().end()
                .find('tbody tr').find('td:last').remove().end()
        }

        if(isEdit || isView) {
            var catalogId = $('#catalogId').val();
            $content.find('[name=parentCatalogId]').attr('name','catalogId').val(catalogId);
            $content.find('[name=catalogName]').val($('#gradeName').val());

            $.ajax({
                url:'/baseDataLoad/catalogKindsDetail.json?catalogId=' + catalogId,
                success:function (res) {
                    // console.log(res)
                    if(res.success){
                        $.each(res.data, function (Mkey,items) {
                            var isConditions = Mkey == 'conditions';
                            var $tempBox = isConditions ? $content.find('#CONDITIONS') : $content.find('#LIABILITIS');
                            var $allTemps = $('<tbody></tbody>');
                            $.each(items, function (index,item) {
                                var $temp = isConditions ? $($('#TMP_WORKTEMP').html()) : $($('#TMP_LIABILITYORDERS').html());
                                $.each(item, function(key,val){
                                    // console.log(val);
                                    var $rsltBox = $temp.find('[targetkey=' + key + ']');
                                    if($rsltBox.length == 0) return;
                                    // console.log('length>0')
                                    $.each($rsltBox,function () {
                                        var $thisRsltBox = $(this);
                                        var rsltBoxtype = $thisRsltBox[0].tagName;
                                        var valType = ['INPUT','SELECT'];
                                        if(valType.indexOf(rsltBoxtype) >= 0){
                                            $thisRsltBox.val(val);
                                        }else {
                                            $thisRsltBox.html(val);
                                        }
                                    })
                                });
                                $allTemps.append($temp);
                                if(index == items.length - 1){
                                    $tempBox.append($allTemps);
                                }

                            })
                        })
                    }else {
                        Y.alert('获取数据失败',res.message);
                    }
                }
            })
        }
    };
});