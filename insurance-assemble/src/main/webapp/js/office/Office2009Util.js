/**
 * 获取到显示word控件
 * @returns
 */
function getWebObject() {
	return document.getElementById("WebOffice");
}

/**
 * 远程加载文件[与后台发生交互]
 */
function loadWord() {
	getWebObject().WebOpen(true);
}

/**
 * 打开本地文件
 */
function loadLocalWord() {
	getWebObject().WebOpenLocal();
}

/**
 * 远程保存文件[与后台发生交互]
 */
function saveWord() {
	getWebObject().WebSave(true);
	var status = getWebObject().Status;
	if (status == "1") {
		alert("\u4FDD\u5B58\u6210\u529F!"); //保存成功
		// 2016.10.10 弹出新窗口 保存成功 被关闭窗口
		window.opener.top.location.hash = 'refresh';
		window.self.close();
	} else {
		alert("\u4FDD\u5B58\u5931\u8D25!"); //保存失败
	}
}

/**
 * 保存文件至本地
 */
function saveLocalWord() {
	getWebObject().WebSaveLocal();
}

/**
 * 显示|隐藏痕迹
 * @param mValue
 */
function showRevision(mValue) {
	getWebObject().WebShow(mValue);
}

/**
 * 打印文档
 */
function printWord() {
	getWebObject().WebOpenPrint();
}

/**
 * 设置初始化信息
 * @param userName   操作的用户名
 * @param webUrl	 请求URL
 * @param fileName   文件名称
 * @param fileSuffix 文件后缀
 * @param reayOnly	 是否只读
 */
function setBaseParam(userName, webUrl, fileName, fileSuffix, readOnly) {
	var webObject = getWebObject();
	webObject.UserName = userName;
	webObject.WebUrl = webUrl;
	webObject.FileName = fileName;
	webObject.FileType = fileSuffix;
	webObject.ShowToolBar = 0;
	//	webObject.WebToolsVisible("Reviewing", false);
	//	webObject.WebToolsVisible("Track Changes", false);
	if ("true" == readOnly) {
		webObject.EditType = "0,0";
	} else {
		webObject.EditType = "-1,0,1,1,0,0,0,0"; //"-1,0,1,1,0,0,1,0";
	}
	this.loadWord();
}