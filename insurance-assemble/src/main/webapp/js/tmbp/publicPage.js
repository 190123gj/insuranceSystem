define(function(require, exports, module) {

    require('input.limit');
    // 分页公共操作
    var util = require('util');

    var $body = $('body'),
        $fnListSearchForm = $('#fnListSearchForm').attr('method', 'GET'),
        $list = $('#list'),
        $fnCheckAll = $('.fnCheckAll'); // 全选中

    //删等操作
    util.listOPN({
        withdraw: {
            url: '/projectMg/form/cancel.htm',
            message: '已撤销',
            opn: '撤回'
        },
        del: {
            url: '/projectMg/form/delete.htm',
            message: '已删除',
            opn: '删除'
        },
        end: {
            url: '/projectMg/form/end.htm',
            message: '已作废',
            opn: '作废'
        }
    });


    // 新打开窗口
    $list.on('click', '.fnNewWindowOpen', function(e) {

        e.preventDefault();
        util.openDirect(this.getAttribute('mainurl'), this.href, this.getAttribute('navurl'));

    }).on('click', '.fnCurrentWindowOpne', function(e) {

        e.preventDefault();
        util.direct2param(this.href, this.getAttribute('navurl'));


    }).on('click', '.fnCheckAll', function(e) {

        // 列表中全选中的checkbox

        e ? e.stopPropagation() : window.event.cancelBubble = true;

        var _this = $(this);

        if (_this.prop('checked')) {
            $list.find('.fnCheck:not([disabled])').prop('checked', 'checked');
        } else {
            $list.find('.fnCheck:not([disabled])').removeProp('checked');
        }

    }).on('click', '.fnCheck', function(e) {

        // 列表中单个选中的checkbox

        e ? e.stopPropagation() : window.event.cancelBubble = true;

        var _this = $(this);

        if (!_this.prop('checked') && $fnCheckAll.prop('checked')) {
            $fnCheckAll.removeProp('checked');
        }

    }).on('click', '.fnDoAjax', function(e) {
        // 格式 <a href="javascript:void(0);" class="fnDoAjax" ajaxurl="/xx/x/.json?xxx=xxx&x=x">一些文字</a>

        e.preventDefault();

        var _url = this.getAttribute('ajaxurl').replace(/\s/g, '');

        if (!!!_url) {
            return;
        }

        Y.confirm('提示', '确定进行该操作？', function(opn) {

            if (opn == 'yes') {

                util.ajax({
                    url: _url,
                    success: function(res) {

                        Y.alert(res.success ? '操作成功' : '操作失败', res.message, function() {
                            window.location.reload(true);
                        });

                    }
                });

            }

        });

    })

    // 全选


    //全局方法
    window.toPage = function(totalPage, pageNo) {
        if (totalPage < pageNo) {
            return false;
        }
        document.getElementById('fnPageNumber').value = pageNo;
        document.getElementById('fnListSearchForm').submit();
    }

    //升降序
    var fnSortOrder = document.getElementById('fnSortOrder') || {},
        fnSortCol = document.getElementById('fnSortCol') || {},
        $fnListSearchTh = $('#fnListSearchTh');

    //还原表头的升降序状态
    if (fnSortCol.value) {
        var _$markTh = $fnListSearchTh.find('th[sortcol="' + fnSortCol.value + '"]');
        if (_$markTh.length) {
            _$markTh[0].innerHTML += '<span class="fn-fs16 fn-ml5 fn-fwb">' + ((fnSortOrder.value == 'ASC') ? '&uarr;' : '&darr;') + '</span>';
            _$markTh[0].className += (fnSortOrder.value == 'ASC') ? ' ASC' : ' DESC';
        }
    }
    $fnListSearchTh.on('click', 'th', function() {

        var _this = $(this),
            _key = _this.attr('sortcol');
        if (!!!_key) {
            return;
        }
        //键名
        fnSortCol.value = _key;
        //升降序
        if (_this.hasClass('ASC')) {
            fnSortOrder.value = 'DESC';
        } else {
            fnSortOrder.value = 'ASC';
        }
        document.getElementById('fnListSearchForm').submit();

    });

    //搜索按钮
    $('#fnListSearchBtn').on('click', function() {
        document.getElementById('fnPageNumber').value = '1';
        document.getElementById('fnListSearchForm').submit();
    });

    //翻页的go
    $('#fnListSearchGo').on('click', function() {

        var _input = +document.getElementById('fnListSearchInput').value,
            _max = +$(this).attr('maxitem');
        if (_input > _max || _input <= 0) {
            return;
        }
        document.getElementById('fnPageNumber').value = _input;
        document.getElementById('fnListSearchForm').submit();

    });

    //搜索框时间限制

    $body.on('blur', '.fnListSearchDateS', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateE');

        $input.attr('onclick', 'laydate({min: "' + this.value + '"})');

    }).on('blur', '.fnListSearchDateE', function() {

        var $p = $(this).parents('.fnListSearchDateItem'),
            $input = $p.find('.fnListSearchDateS');

        $input.attr('onclick', 'laydate({max: "' + this.value + '"})');

    }).on('blur', '.fnInputLimit', function() {

        // 限制输入
        var _max = this.getAttribute('max'),
            _min = this.getAttribute('min'),
            self = this;

        setTimeout(function() {
            if (_max && +_max < +self.value) {
                self.value = _max;
            }

            if (_min && +_min > +self.value) {
                self.value = _min;
            }
        }, 0);

    }).on('click').on('click', '.fnBatchOPN', function() {
        // 批量操作审核

        var $this = $(this),
            $form = $this.parents('form'),
            $checkbox = $form.find('tbody input:checked'),
            _idArr = [],
            DOC_NO_OBJ = {} //单据号、标题

        if (!!!$checkbox.length) {
            Y.alert('提示', '请选择需要操作的单据')
            return
        }

        $checkbox.each(function(index, el) {
            _idArr.push('processFormIds=' + el.value)
            DOC_NO_OBJ[el.value] = el.getAttribute('retitle')
        })

        // _SYSNAME  processFormIds  processServiceName processType=pass/back2start processContent

        var formArr = [
            _idArr.join('&'),
            'sysName=' + $this.attr('sysName'),
            'processType=' + $this.attr('processType'),
            'processServiceName=' + $('#processServiceName').val()
        ]

        util.ajax({
            url: '/projectMg/form/batchProcess.json',
            data: formArr.join('&'),
            success: function(res) {

                var _head = '<thead><tr><th>' + $this.attr('retitle') + '</th><th>审核状态</th><th>备注</th></tr></thead>',
                    _tbody = '',
                    _result = res.result

                $.each(_result.failureFormIdList, function(index, str) {

                    _tbody += '<tr><td>' + DOC_NO_OBJ[str] + '</td><td>失败</td><td>' + _result.failureMessageList[index] + '</td></tr>'

                })

                $.each(_result.nonSupportFormIdList, function(index, str) {

                    _tbody += '<tr><td>' + DOC_NO_OBJ[str] + '</td><td>失败</td><td>该单据不支持批量操作</td></tr>'

                })

                $.each(_result.successFormIdList, function(index, str) {

                    _tbody += '<tr><td>' + DOC_NO_OBJ[str] + '</td><td>成功</td><td>' + _result.successMessageList[index] + '</td></tr>'

                })

                var _html = '<table class="m-table m-table-list">' + _head + '<tbody>' + _tbody + '</tbody></table>'

                $('body').Y('Msg', {
                    type: 'alert',
                    width: '600px',
                    content: _html,
                    icon: '',
                    yesText: '确定',
                    callback: function(opn) {

                        window.location.reload(true)

                    }
                });


            }
        })


    })

    $body.find('.fnListSearchDateS,.fnListSearchDateE').blur();

    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数

    $('.fnListSearchClear').on('click', function() {

        $(this).parent().find('input').val('');

    });

    //------ 选择组织 start
    // 初始化弹出层
    var selectOrgDialog = new BPMiframe('', {
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
            _$parent = $this.parent(),
            _$id = _$parent.find('.fnListSearchOrgId'),
            _$name = _$parent.find('.fnListSearchOrgName');
        // _balance = $this.attr('gBalance') ? true : false;

        // 更新弹窗的单选、多选
        selectOrgDialog.resetSrc('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=' + (_isSingle ? 'true' : 'false'));

        //这里也可以更新已选择机构
        selectOrgDialog.obj.arrys = [{
            id: _$id.val(),
            name: _$name.val()
        }];

        selectOrgDialog.init(function(relObj) {

            _$id.val(relObj.orgId).trigger('change');
            _$name.val(relObj.orgName).trigger('change');

            // if (_balance) {
            //  getBalance($this);
            // }
        });

    });

    // function getBalance(orgc) {
    //  var budgetDeptId = orgc.parent().find('.fnListSearchOrgId').val();
    //  var $balance = orgc.parent().parent().parent().find('.balance');
    //  var $category = orgc.parent().parent().parent().find('.expenseType');
    //  var _categoryId = 1; //默认差旅费ID
    //  if (!!!$category.val()) {
    //      _categoryId = 1; //默认差旅费ID
    //  }else{
    //      _categoryId = $category.val(); //费用类型
    //  }
    //  // alert(_categoryId);
    //  util.ajax({
    //      url: '/baseDataLoad/deptBudgetBalance.json',
    //      data: {
    //          budgetDeptId: budgetDeptId,
    //          applicationTime: $('#applicationTime').val(),
    //          categoryId: _categoryId
    //      },
    //      success: function(ret) {
    //          $balance.val(ret.balance);
    //      }
    //  });
    // }

    //------ 选择组织 end

    //------ 选择人员 start
    // 初始化弹出层
    var selectUserDialog = new BPMiframe('', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'selectUsers': {
            selectUserIds: '', //已选id,多用户用,隔开
            selectUserNames: '' //已选Name,多用户用,隔开
        },
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });
    // 添加选择后的回调，以及显示弹出层
    $body.on('click', '.fnListSearchUserChoose', function() {

        var $this = $(this),
            _isSingle = $this.attr('single') ? true : false,
            _$parent = $this.parent(),
            _$id = _$parent.find('.fnListSearchUserId'),
            _$name = _$parent.find('.fnListSearchUserName'),
            _$account = _$parent.find('.fnListSearchUserAccount');

        // 更新弹窗的单选、多选
        selectUserDialog.resetSrc('/bornbpm/platform/system/sysUser/dialog.do?isSingle=' + (_isSingle ? 'true' : 'false'));

        //这里也可以更新已选择用户
        selectUserDialog.obj.selectUsers = {
            selectUserIds: _$id.val(), //已选id,多用户用,隔开
            selectUserNames: _$name.val(), //已选Name,多用户用,隔开
            selectUserAccounts: _$account.val() //已选accounte,多用户用,隔开
        }

        selectUserDialog.init(function(relObj) {

            _$id.val(relObj.userIds);
            _$name.val(relObj.fullnames);
            _$account.val(relObj.accounts);

        });

    });
    //------ 选择人员 end


});