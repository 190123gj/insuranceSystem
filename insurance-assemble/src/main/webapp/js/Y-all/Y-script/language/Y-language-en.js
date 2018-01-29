/**
 * @fileoverview 英文语言包文件
 * @author xiaoyangzheng
 * @modify 
 * @requires
 * @description   
 * @created  2014-12-18
 * @updated  
 */

define(function(){
    var name = "en";
    Y.language.pact[name] = {};
    $.extend(Y.language.pact[name], {
    	Window: {
    		close: 'close',
    		canDrag: 'drag'
    	},
    	Msg: {
    		ok: 'OK',
    		cancel: 'Cancel'
    	},
    	ImagePlayer: {
    		next: 'next',
	    	prev: 'prev',
	    	bigger: 'bigger',
	    	smaller: 'smaller',
	    	rotaL: 'rotaLeft',
	    	rotaR: 'rotaRight',
	    	full: 'full',
	    	cancelFull: 'cancelFull',
	    	firstTip: 'first',
	    	lastTip: 'last'
    	},
    	HtmlUploadify: {
    		title: 'file upload',
    		typeInfo: 'type of file is error',
    		type: 'Please，These types is allow:',
    		success: 'upload success',
    		fail: 'upload fail',
    		clear: 'clear',
    		add: 'add',
    		up: 'upload',
    		cancel: 'cancel'
    	},
    	Combobox: {
    		open: 'open',
    		empty: 'please choose',
    		canEnter: 'enter'
    	},
    	Countdown: {
    		message: 'resend message after {0} seconds'
    	},
    	TreeNode: {
    		del: 'delete'
    	}
    	
    });

});
