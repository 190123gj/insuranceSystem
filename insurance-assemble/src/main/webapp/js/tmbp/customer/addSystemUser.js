define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('zyw/upAttachModify');
	// require('zyw/chooseRegion');
    require('tmbp/submit.common');
    require('tmbp/operate.common');

    var chooseRegion =  require('tmbp/chooseRegionNew');
    var BPMiframe = require('BPMiframe'); //isSingle=true 表示单选 尽量在url后面加上参数
    var getList = require('zyw/getList');

    var $body = $('body');

    var chooseRegionTemp = new chooseRegion();
    chooseRegionTemp.init();

    // 必备参数
    var scope = '{type:\"system\",value:\"all\"}';
    var selectUsers = {
        selectUserIds: '', //已选id,多用户用,隔开
        selectUserNames: '' //已选Name,多用户用,隔开
    }

    var singleSelectMemberNamesDialog = new BPMiframe('/bornbpm/platform/system/sysUser/dialog.do', {
        'title': '人员',
        'width': 850,
        'height': 460,
        'scope': scope,
        'selectUsers': [],
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });

// 添加选择后的回调，以及显示弹出层
//     $body.on("click","#fnToChooseBusinessUserName",function(){
//         var $names = $("#customerName"),
//             $ids = $("#ownerId");
//         singleSelectMemberNamesDialog.obj.selectUsers={
//             selectUserNames:$names.val(),
//             selectUserIds:$ids.val()
//         }
//         $names.val('');
//         $ids.val('');
//         singleSelectMemberNamesDialog.init(function(relObj) {
//
//             $names.val(relObj.fullnames).trigger('blur');
//             $ids.val(relObj.userIds).trigger('blur');
//         });
//     });
    $body.on("click","#fnToChooseBusinessUserName",function(){
        var $names = $("#customerName"),
            $ids = $("#ownerId");
        singleSelectMemberNamesDialog.init(function(relObj) {
            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    });

    $body.on("click","#fnToChooseRefereeName",function(){
        var $names = $("#refereeName"),
            $ids = $("#refereeId");
        singleSelectMemberNamesDialog.obj.selectUsers={
            selectUserNames:$names.val(),
            selectUserIds:$ids.val()
        }
        singleSelectMemberNamesDialog.init(function(relObj) {

            $names.val(relObj.fullnames).trigger('blur');
            $ids.val(relObj.userIds).trigger('blur');
        });
    }).on("click","#fnToClearRefereeName",function(){
        $("#refereeId").val('');
        $("#refereeName").val('');
    });

    $body.on("click","#addressNew",function () {
        var $this = $(this);
        var addressBox = $this.parents("table").find("tr:last")
        // var chooseRegion = new chooseRegion();
        chooseRegionTemp.init(
            addressBox
        );
    }).on("click",".addressDel",function () {
        var $this = $(this);
        $this.parents("tr").remove();
    });
});