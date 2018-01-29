/**
 * @fileoverview 中文语言包文件
 * @author xiaoyangzheng
 * @modify 
 * @requires
 * @description   
 * @created  2014-12-12
 * @updated  
 */

define(function(){
    var name = "zh";
    Y.language.pact[name] = {};
    $.extend(Y.language.pact[name], {
    	Window: {
    		close: '关闭',
    		canDrag: '可拖动'
    	},
    	Msg: {
    		ok: '确定',
    		cancel: '取消'
    	},
    	ImagePlayer: {
    		next: '下一张',
	    	prev: '上一张',
	    	bigger: '放大',
	    	smaller: '缩小',
	    	rotaL: '逆时针旋转',
	    	rotaR: '顺时针旋转',
	    	full: '全屏',
	    	cancelFull: '还原',
	    	firstTip: '第一张了',
	    	lastTip: '最后一张了'
    	},
    	HtmlUploadify: {
            title: '文件上传',
    		typeInfo: '文件类型不符合',
    		type: '温馨提示，允许的文件类型为：',
    		success: '已上传',
    		fail: '上传未成功',
    		clear: '清空',
            add: '新增',
    		up: '上传',
    		cancel: '取消'
    	},
    	Combobox: {
    		open: '展开',
    		empty: '请选择',
    		canEnter: '可输入'
    	},
    	Countdown: {
    		message: '{0}秒后可重新发送短信'
    	},
    	TreeNode: {
    		del: '删除'
    	}
    	
    });

});
