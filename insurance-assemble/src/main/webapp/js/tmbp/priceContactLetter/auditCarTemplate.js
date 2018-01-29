define(function(require, exports, module) {
	//客户协议
   var auditProject = require("tmbp/auditProject");

	var _auditProject = new auditProject();
	_auditProject.initFlowChart().initSaveForm().initAssign().initAudit();

	var publicOPN = new(require('zyw/publicOPN'))();
	publicOPN.init().doRender();




});