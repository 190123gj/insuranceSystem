/**
 * Created by eryue
 * Create in 2017-01-04 13:54
 * Description:
 */

'use strict';
define(function(require, exports, module) {
    //项目管理>授信前管理> 立项申请
    require('Y-msg');
    require('zyw/upAttachModify');
    require('zyw/chooseRegion');
    require('tmbp/submit.common');
    require('tmbp/operate.common');

    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数

    var $body = $('body');

    // 必备参数
    var scope = '{type:\"system\",value:\"all\"}';
    var selectUsers = {
        selectUserIds: '', //已选id,多用户用,隔开
        selectUserNames: '' //已选Name,多用户用,隔开
    };

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
    $body.on("click","#fnToChooseBusinessUserName",function(){
        var $names = $("#businessUserName"),
            $ids = $("#businessUserNameids");
        singleSelectMemberNamesDialog.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        singleSelectMemberNamesDialog.init(function(relObj) {

            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    });
});