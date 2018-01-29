/**
 * This file includes the required ext-all js and css files based upon "theme" and "rtl"
 * url parameters.  It first searches for these parameters on the page url, and if they
 * are not found there, it looks for them on the script tag src query string.
 * For example, to include the neptune flavor of ext from an index page in a subdirectory
 * of extjs/examples/:
 * <script type="text/javascript" src="../../examples/shared/include-ext.js?theme=neptune"></script>
 */
(function() {    
	
	var params = location.search.substring(1);
	
	params = decodeURIComponent(params).split("&");
	for(var i = 0;i < params.length;i++){
		var p = params[i].split("=");
		if(p[0] == "theme"){
			_cf.theme = p[1];
		}else if(p[0] == "language"){
			_cf.language = p[1];
		}
	}
	// load css
	var jsfloder = (_cf.debug == true ?"debug" : "release");
	var theme = _cf.theme || 'black';
	var t = new Date().getTime();
	var easytheme = theme || 'black';
    var css_array = new Array();
    var path = _cf.path;
    css_array = css_array.concat([
	  path+"utility/mini-easyui/themes/"+theme+"/easyui.css",
	  path+"utility/mini-easyui/themes/icon.css",
	  path+"utility/mini-easyui/themes/color.css",
	  'themes/'+theme+"/btns.css",
	  'themes/'+theme+'/c.css',
	  'themes/'+theme+'/icons.css'
	]);

    for(var j = 0;j < css_array.length;j++)
    {
        document.write('<link rel="stylesheet" type="text/css" href="'+css_array[j]+'?t='+t+'" />');
    };
    var t = new Date().getTime();
    
    // 兼容一下全小写的zh_cn
    if(_cf.language.toLowerCase() == "zh_cn"){
    	_cf.language = "zh_CN";
    }
    if(_cf.language.toLowerCase() == "zh_EN"){
　　	_cf.language = "zh_EN";
　　}
	
    // load cu js
    var js_array = new Array();
    js_array = js_array.concat([
        path+'lib/console.js',
		'js/lang-' + _cf.language + '.js?t='+t,
		//'http://code.jquery.com/jquery-1.9.1.min.js',
		//path+'utility/json2.js',
		path+'utility/mini-easyui/jquery.min.js',
		path+'utility/mini-easyui/jquery.base64.js',
		path+'utility/mini-easyui/jquery.cookie.js',
		path+'utility/mini-easyui/jquery.easyui.min.js',
		path+'utility/mini-easyui/jquery.easyui.patch.js',
		path+'utility/mini-easyui/locale/easyui-lang-'+_cf.language+'.js',
		path+'lib/plugin.iface.js?t='+t,
		path+'lib/plugin.layer.js?t='+t,
		path+'lib/gps.js?t='+t]);
    
    js_array = js_array.concat([
		(_cf.mapType == "bmap" ? "http://api.map.baidu.com/api?v=1.3&ak=wrPv906yTCTlwS5q8xjnlmTS" : "http://webapi.amap.com/maps?v=1.3&key=c76303dc7cc434bd887c7f5823911bc7"),
		/*"http://api.map.baidu.com/api?v=1.3&ak=wrPv906yTCTlwS5q8xjnlmTS",*/
		path+'lib/map.'+(_cf.mapType == "bmap" ? 'bmap' : 'amap')+'.js?t='+t]);
    
    js_array = js_array.concat([
		'js/'+jsfloder+'/my.plugin.js?t='+t,
		'js/'+jsfloder+'/my.configsets.js?t='+t,
		'js/'+jsfloder+'/my.decode.js?t='+t,
		'js/'+jsfloder+'/my.player.js?t='+t,
		'js/'+jsfloder+'/my.vod.js?t='+t,
		'js/'+jsfloder+'/my.map.js?t='+t,
		'js/'+jsfloder+'/frame.js?t='+t
	]);
    
    for(var j = 0;j < js_array.length;j++)
    {
        document.write('<script type="text/javascript" src="'+js_array[j]+'"></script>');
    };
    


    
})();

/*
//这个需要改造成自己的，
(function(){        //闭包
function load_script(xyUrl, callback){
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = xyUrl;
    //借鉴了jQuery的script跨域方法
    script.onload = script.onreadystatechange = function(){
        if((!this.readyState || this.readyState === "loaded" || this.readyState === "complete")){
            callback && callback();
            // Handle memory leak in IE
            script.onload = script.onreadystatechange = null;
            if ( head && script.parentNode ) {
                head.removeChild( script );
            }
        }
    };
    // Use insertBefore instead of appendChild  to circumvent an IE6 bug.
    head.insertBefore( script, head.firstChild );
}
function translate(point,type,callback){
    var callbackName = 'cbk_' + Math.round(Math.random() * 10000);    //随机函数名
    var xyUrl = "http://api.map.baidu.com/ag/coord/convert?from="+ type + "&to=4&x=" + point.lng + "&y=" + point.lat + "&callback=BMap.Convertor." + callbackName;
    //动态创建script标签
    load_script(xyUrl);
    BMap.Convertor[callbackName] = function(xyResult){
        delete BMap.Convertor[callbackName];    //调用完需要删除改函数
        var point = new BMap.Point(xyResult.x, xyResult.y);
        callback && callback(point);
    }
}

window.BMap = window.BMap || {};
BMap.Convertor = {};
BMap.Convertor.translate = translate;
})();
*/
