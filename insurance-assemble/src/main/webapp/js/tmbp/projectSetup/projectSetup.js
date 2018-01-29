define(function(require, exports, module) {
	var auditProject = require('tmbp/auditProject');
	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.init().doRender();
	 require('tmbp/upAttachModifyNew');
	 $("a.fnUpAttachBtn").remove();
});

