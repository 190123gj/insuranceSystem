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
    // require('zyw/chooseRegion');
    require('tmbp/submit.common');
    require('tmbp/operate.common');
    require('tmbp/chooseIndustry');
    var chooseRegion =  require('tmbp/chooseRegionNew');

    var chooseRegionTemp = new chooseRegion();
    chooseRegionTemp.init();
    var BPMiframe = require('BPMiframe')
    var singleSelectOrgDialog = new BPMiframe('/bornbpm/platform/system/sysOrg/dialog.do?isSingle=true&selectTop=Y', {
        'title': '组织',
        'width': 850,
        'height': 460,
        'scope': '{type:\"system\",value:\"all\"}',
        'arrys': [], //[{id:'id',name:'name'}];
        'bpmIsloginUrl': '/bornbpm/bornsso/islogin.do?userName=' + $('#hddUserName').val(),
        'makeLoginUrl': '/JumpTrust/makeLoginUrl.htm'
    });

    $('#fnChooseOrg').on('click', function () {
        singleSelectOrgDialog.init(function (relObj) {
            $('#ownerId').val(relObj.orgId);
            $('#customerName').val(relObj.orgName).trigger('blur');
        });
    });
});