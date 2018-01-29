
(function($){

define(function(require){

	var browser = {
		ie: $.browser.msie,
		ie6: $.browser.msie && !!window.ActiveXObject && !window.XMLHttpRequest && !!document.compatMode,
		ie7: $.browser.msie && window.XMLHttpRequest && !document.querySelector,
		ie8: ieV(8),
		ie9: ieV(9),
		ie10: ieV(10),
		firefox: !!navigator.userAgent.match(/firefox/i),
		opera: $.browser.opera,
		safari: $.browser.safari,
		webkit: 'WebkitAppearance' in document.documentElement.style
	}

	Y.browser = $.extend(browser,{
		'ie6-': browser.ie && !window.XMLHttpReques && !document.compatMode,
		'ie7-': browser.ie && !window.XMLHttpRequest,
		'ie8-': browser.ie && (document.documentMode < 8 || !document.documentMode),
		'ie9-': browser.ie && (document.documentMode < 9 || !document.documentMode),
		'ie6-8': browser.ie6 || browser.ie7 || browser.ie8,
		'ie6+': browser.ie6 || (browser.ie && !!window.XMLHttpRequest),
		'ie7+': browser.ie && !!window.XMLHttpRequest,
		'ie8+': browser.ie && document.documentMode >= 8,
		'ie9+': browser.ie && document.documentMode >= 9,
		'ff': browser.firefox
	});
	$.extend(Y.browser,{
		language: (function(){
			 var language = navigator.language || navigator.browserLanguage || navigator.userLanguage;
			 return language.substr(0,2).toLowerCase();
		})()
	});
	function ieV(v){
		return $.browser.msie && document.documentMode == v;
	}

});

})($);