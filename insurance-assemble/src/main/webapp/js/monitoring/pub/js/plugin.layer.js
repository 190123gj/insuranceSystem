/*
--- 
fn: P_LY
desc: ICVS2 PluginSDK中间层
time: 2013.09.04 10:20:00
version: v2014.02.18.001 Beta.
...
*/
var P_LY = 
{
    publishType : "public", 
	
	version : "v2015.06.04.001 Beta", 
	
	agt : navigator.userAgent.toLowerCase(), 
	
    browserType : "IE", // - IE | Chrome | FireFox | Other
    Connections : new P_Utils.Hash(), // - 存储所有连接信息
    WindowContainers : new P_Utils.Hash(), // - 存储所有窗口信息	
	// - puid reg
	puidRex: /^[A-Za-z0-9]+$/i,
    // - 整数 reg 	
    intRex: /^-?\d+$/,
	
    /*
    *    fn    :    P_LY.PluginHtml
    *    Des    :    Create Plugin Object
    */
    // - 插件对象
    Plug : 
    {
	    type:'',
	  	ver:'2.0.16.408',	// webcu
	  	eVer:'2.0.16.408',// embed
        inited : false, nc : null, wnd : null, wa : null, // - 插件容器
        boxDomId : "icvs2_pluginsbox",
        getVer:function(){
        	if(P_LY.Plug.type == "e"){

        		return P_LY.Plug.eVer;
        	}else{
        		return P_LY.Plug.ver;
        	}
        },
        Html : 
        {
            nc : "<OBJECT id=\"@id\" name=\"@name\" type=\"application/x-icvsc2\" style=\"width:0px;height:0px;border:0px solid red;\"><param name=\"onload\" /></OBJECT>", 
            wnd : "<OBJECT id=\"@id\" name=\"@name\" type=\"application/x-icvsc2\" style=\"width:0px;height:0px;border:0px solid #FFFFFF;\" ></OBJECT>",
			wa : "<OBJECT id=\"@id\" name=\"@name\" type=\"application/x-icvsc2\" style=\"width:0px;height:0px;border:0px solid #FFFFFF;\"></OBJECT>",
            get : function (objName,id,type) 
            {
                switch (objName) 
                {
                    case "nc":
                        return "<OBJECT id=\""+id+"\" name=\""+id+"\" type=\"application/x-icvsc2"+P_LY.Plug.type.toLowerCase()+"\" style=\"width:0px;height:0px;border:0px solid red;\"><param name=\"onload\" /></OBJECT>";
                        break;
                    case "wnd":
                        return "<OBJECT id=\""+id+"\" name=\""+id+"\" type=\"application/x-icvsc2"+P_LY.Plug.type.toLowerCase()+"\" style=\"width:0px;height:0px;border:0px solid red;\"><param name=\"onload\" /></OBJECT>";
                        break;
                    case "wa":
                        return "<OBJECT id=\""+id+"\" name=\""+id+"\" type=\"application/x-icvsc2"+P_LY.Plug.type.toLowerCase()+"\" style=\"width:0px;height:0px;border:0px solid red;\"><param name=\"onload\" /></OBJECT>";
                        break;
                    default:
                        return "";
                        break;
                }
            },
            end : true 
        },
        // - 温馨提示
        Error : 
        {
            domid : "icvs2_warmtipbox", active : false, pluginFile : "", html : "",
            tip : function (html) 
            {
				try
				{
					var fn = "P_LY.Plug.Error.tip";
					
					if (this.active == false)
					{
						P_Utils.Log(fn, "warm tip active false~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
					}
					
					if (!document.getElementById(this.domid)) 
					{
						var warmbox = document.createElement("DIV");
						warmbox.setAttribute("id", this.domid);
						warmbox.style.zIndex = "99999";
						warmbox.style.position = "absolute";
						warmbox.style.width = "100%";
						warmbox.style.height = "100%";
						warmbox.style.top = "0";
						warmbox.style.left = "0";
						warmbox.style.filter = "Alpha(Opacity=85)";
						warmbox.style.filter.MozOpacity = "0.8";
						warmbox.style.filter.Opacity = "0.8";
						warmbox.style.backgroundColor = "#C3D2EF";
						warmbox.style.color = "#990011";
						warmbox.style.fontSize = "14px";
						warmbox.style.textAlign = "left";
						warmbox.style.lineHeight = "180%";
						document.getElementsByTagName("BODY").item(0).appendChild(warmbox);
					}
					if (document.getElementById(this.domid)) 
					{
						var html = this.html || html || P_LY.Enum["warmTip"][P_LY.language];
						html = html.replace(/(#\(0\))/, this.pluginFile);
						
						document.getElementById(this.domid).innerHTML = html;
						this.show();
					}
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				catch(e) {
					P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
           	 		return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_THREAD);	
				}
            },
            show : function () 
            {
                if (document.getElementById(this.domid)) {
                    document.getElementById(this.domid).style.display = "block";
                }
            },
            hide : function () 
            {
                if (document.getElementById(this.domid)) 
                {
                    document.getElementById(this.domid).style.display = "none";
                    document.getElementById(this.domid).parentNode.removeChild(document.getElementById(this.domid));
                }
            },
            end : true 
        },
        end : true 
    },
    /*
    ---
    fn: Init 
    desc: 初始化插件 
    returns: 
        - P_Error 
    params: 
        - initParam(P_LY.Struct.InitParamStruct) 初始化参数 
    ... 
    */
    Init : function (initParam)
    {	
        try 
        {
            var fn = "P_LY.Init";
			
			if (P_LY.Plug.inited != false)
			{
				
				P_Utils.Log(fn, "P_LY plugins had inited success~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
			}
			
            if (!initParam || !initParam instanceof P_LY.Struct.InitParamStruct) 
			{
                initParam = new P_LY.Struct.InitParamStruct();
            }
            // - 判断浏览器类型
            var _agt = P_LY.agt || navigator.userAgent.toLowerCase();
            if (_agt.search("msie") != - 1 || _agt.search("trident") != - 1) {
                P_LY.browserType = P_LY.Enum.BrowserType.IE;
            }
            else if (_agt.search("chrome") != - 1) {
                P_LY.browserType = P_LY.Enum.BrowserType.Chrome;
            }
            else if (_agt.search("firefox") != - 1) {
                P_LY.browserType = P_LY.Enum.BrowserType.FireFox;
            }
            else {
                P_LY.browserType = P_LY.Enum.BrowserType.Other;
            }
            // - 初始化调试对象
            P_LY.Debug.Init(initParam);
            // - 语言风格
            var _language = initParam.language || P_LY.language;
            if (!P_LY.Enum.LanguageType[_language]) {
                _language = P_LY.Enum.LanguageType.zh_CN;
            }
			P_Error.language = P_LY.language = _language;
			
            if(initParam.type == "external"){
    			P_LY.Plug.nc = (typeof window.external != "undefined"? window.external:window_external);
    			P_IF.escape = false;
                //console.log(P_LY.Plug.nc.MD5Enc(""));
            }else{
    			// - 温馨提示处理
    			P_LY.Plug.Error.active = initParam.warmTip.active || false;
    			P_LY.Plug.Error.pluginFile = "";
    			P_LY.Plug.Error.html = initParam.warmTip.html || "";
    			
    			if(initParam.type == "embed" || initParam.type == "decode") P_LY.Plug.type = "e";
    			
                // - 检测插件是否加载完成
                var nc = P_LY.Plug.Html.get("nc","P_LYNC");//.replace(/(@id)|(@name)/g, "P_LYNC");
                var wnd = P_LY.Plug.Html.get("wnd","P_LYWND");//.replace(/(@id)|(@name)/g, "P_LYWND");
    			var wa = P_LY.Plug.Html.get("wa","P_LYWAP");//.replace(/(@id)|(@name)/g, "P_LYWAP");
    			
                if (!document.getElementById(P_LY.Plug.boxDomId)) 
                {
                    var _objn3box = document.createElement("DIV");
                    _objn3box.setAttribute("id", P_LY.Plug.boxDomId);
                    _objn3box.setAttribute("attention", "For np sdk private using, be sure won't remove it manually...");
                    document.getElementsByTagName("body").item(0).appendChild(_objn3box);
                }
                else {
                    var _objn3box = document.getElementById(P_LY.Plug.boxDomId);
                }
                var plugins = [nc, wnd, wa];
                
                _objn3box.innerHTML = plugins.join("");
                if (typeof document.getElementById("P_LYNC").Initialize == "undefined") 
                {
                    //P_LY.Plug.Error.tip();
                    P_Utils.Log(fn, "P_LY load nc error~");
                    return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_LOADPLUG_NC);
                }

                if (typeof document.getElementById("P_LYNC").Initialize == "undefined") 
                {
                    //P_LY.Plug.Error.tip();
                    P_Utils.Log(fn, "P_LY load nc error~");
                    return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_LOADPLUG_NC);
                }

                if (typeof document.getElementById("P_LYWND").GetWindow == "undefined") 
                {
                    //P_LY.Plug.Error.tip();
                    P_Utils.Log(fn, "P_LY load wnd error~");
                    return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_LOADPLUG_WND);
                }
/*    			if (typeof document.getElementById("P_LYWAP").DebugSwitch == "undefined")
    			{
    				//P_LY.Plug.Error.tip();
    				P_Utils.Log(fn, "P_LY load wa error~");
    				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_LOADPLUG_WA);
    			}*/
                P_LY.Plug.nc = document.getElementById("P_LYNC");
                //console.log($('#P_LYNC').attr("get_version"));
                
                P_LY.Plug.wnd = document.getElementById("P_LYWND");
                P_LY.Plug.wa = document.getElementById("P_LYWAP");

                var rv = P_LY.CheckVer();
                if(rv != P_Error.SUCCESS) return new P_LY.Struct.ReturnValueStruct(rv);
            }
            var operator = P_IF.Initialize(P_LY.Plug.nc);
          
            //alert(operator.rv)
            if (operator.rv == P_Error.SUCCESS) 
            {
                P_LY.Plug.inited = true;
                P_Utils.Log(fn, "P_LY plugins inited success~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
            }
            else
            {
                P_Utils.Log(fn, "P_LY plugins inited failed~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_UNINITIALIZED);
            }
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
        }
        catch (e) 
        {
            P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_THREAD);
        }
    },
    /*
    ---
    fn: UnLoad 
    desc: 页面卸载 
    returns: 
        - P_Error 
    ... 
    */
    UnLoad : function () 
    {
        try 
        {
            var fn = "P_LY.UnLoad";
            if (P_LY.Plug.inited == true) 
            {
				if (typeof P_Utils.Timer != "undefined" && typeof P_Utils.Timer.Stop != "undefined")
				{
					P_Utils.Timer.Stop();
				}
				
                var _connKeys = [];
                if (P_LY.Connections && typeof P_LY.Connections.keys == "function") 
                {
                    _connKeys = P_LY.Connections.keys();
                }
                for (var i = 0; i < _connKeys.length; i++) {
                    // - 断开连接
                    P_Utils.Log(fn, _connKeys[i]);
                    P_LY.DisConnection(_connKeys[i]);
                }
				
                if (P_LY.Plug.nc) {
					P_LY.Folder.UnLoad();
					P_LY.NCNotifyManager.Clear();
                    P_IF.Terminate(P_LY.Plug.nc);
                    P_LY.Plug.nc = null;
                    P_LY.Plug.wnd = null;
					P_LY.Plug.wa = null;
                }

                P_Utils.Log(fn, "unload plugin ");
				if (document.getElementById(P_LY.Plug.boxDomId))
				{
					document.getElementById(P_LY.Plug.boxDomId).innerHTML = "";	
				}
                P_LY.Plug.inited = false;
            }
            else {
                P_Utils.Log(fn, "P_LY not inited~");
            }
            return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
        }
        catch (e) 
        {
            P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_UNLOAD_PLUG_FAILED);
        }
    },
    CheckVer:function(){
        var versions = P_LY.Plug.nc.version.split(".");
        var oVersions = (P_LY.Plug.type == "e" ? P_LY.Plug.eVer.split(".") : P_LY.Plug.ver.split("."));
       // console.log(versions,oVersions);
        var flag = true;
        for(var i =0;i < 4;i++){
        	if(versions[i]){
        		if(parseInt(versions[i]) > parseInt(oVersions[i])){
        			flag = true;
        			break;
        		}else if(parseInt(versions[i]) < parseInt(oVersions[i])){
        			flag = false;
        			break;
        		}
        	}
        }
        if(!flag){
			return P_Error.ERROR_PLUG_VER;
        }
        return P_Error.SUCCESS;
    },
    /*
    ---
    fn: Connect 
    desc: 新建一个连接对象，保存在Connections组里 
    returns: 
        - conn (P_LY.Struct.ConnectionStruct)
    params: 
        - connParam(P_LY.Struct.ConnParamStruct) 连接参数 
    ... 
    */
    Connect : function (connParam)
    {

        try
        {
            var fn = "P_LY.Connect";
            
            if (!P_LY.Plug.inited) 
            {
                var rv = P_LY.CheckVer();
			
                if(rv != P_Error.SUCCESS) return new P_LY.Struct.ReturnValueStruct(rv);
                
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
            }
            if (!connParam || !connParam instanceof P_LY.Struct.ConnParamStruct)
			{
                connParam = new P_LY.Struct.ConnParamStruct();
            }
			
            // - 检测某个连接地址是否已经存在
            P_LY.Connections = P_LY.Connections || new P_Utils.Hash();
            var _connKeys = P_LY.Connections.keys();
            for (var i = 0; i < _connKeys.length; i++) 
            {
				var _connId = _connKeys[i],
					_connStruct = P_LY.Connections.get(_connId);
					 
				if (_connStruct.connParam.ip == connParam.ip)
                {
					if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
					{
						P_Utils.Log(fn, "connect connecting~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING, _connId);
					}
					else if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
					{
						P_Utils.Log(fn, "connection has built~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTED, _connId);
					}
					else
					{
						P_Utils.Log(fn, "connect already~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTIONID_ALREADY, _connId);
					}
                }
            }
            // - 生成一个连接ID
            var doMore = false, connectId = "", $_Generate = function () 
            {
                connectId = new Date().getTime() + "" + parseInt(Math.random() * (9999 - 1000 + 1) + 1000);
            };
            do 
            {
                $_Generate();
                if (connectId && P_Utils.Array.indexOf(_connKeys, connectId) != - 1) 
				{
                    doMore = true;
                    setTimeout(function () 
                    {
                        doMore = false;
                    }, 100);
                }
                else {
                    doMore = false;
                }
            }
            while (doMore);
            if (!connectId) 
			{
                $_Generate();
            }
            var _bfix = connParam.bFix = !!(connParam.bFix == true) ? 1 : 0;
            var _connStruct = new P_LY.Struct.ConnectionStruct( connectId, connParam );
          
			_connStruct.nc = P_LY.Plug.nc;
			_connStruct.ncName = P_LY.Plug.nc.id || P_LY.Plug.nc.name || "";
           
            // - 正在连接
            _connStruct.status = P_LY.Enum.ConnectionStatus.Connecting;
			
            // - 记录连接信息
		
            P_LY.Connections.set(connectId, _connStruct);
            //console.log(P_LY.Connections.get(connectId));

			P_Utils.Log(fn, _connStruct.connParam);
            var operator = P_IF.Connect(_connStruct.nc, _connStruct.connParam);
			P_Utils.Log(fn, operator);
            if (operator.rv == P_Error.SUCCESS) 
            {
                _connStruct.status = P_LY.Enum.ConnectionStatus.Connected;
                _connStruct.session = operator.response;
				
				// - 获取平台类型
				var operator = P_LY.GetPlatformType(connectId);
				if (operator.rv == P_Error.SUCCESS)
				{
					_connStruct.connType = operator.response;
				}
				// - 获取系统名称
				var operator = P_LY.GetSystemName(connectId);
				if (operator.rv == P_Error.SUCCESS)
				{
					_connStruct.systemName = operator.response;
				}
				// - 获取用户优先级
				var operator = P_LY.GetUserPriority(connectId);
				if (operator.rv == P_Error.SUCCESS)
				{
					_connStruct.userPriority = operator.response;
				}
				// - 获取子域
				var operator = P_LY.GetDomainRoad(connectId); 
			
				if (operator.rv == P_Error.SUCCESS)
				{
					_connStruct.domainRoad = operator.response;
				}
				
                return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, connectId);
            }
            else
            {
                _connStruct.status = P_LY.Enum.ConnectionStatus.Failed;
                P_Utils.Log(fn, "connect failed");
				P_LY.DisConnection(connectId);
                return new P_LY.Struct.ReturnValueStruct(operator.rv);
            }
        }
        catch (e)
        {
            P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
        }
    },
	// - 获取登录的平台类型
	GetPlatformType : function (connectId)
	{
		var operator = P_LY.NPPSDKCommon.GetNCResponse(connectId, "GetPlatformType");
		if (operator.rv == P_Error.SUCCESS)
		{
			operator.response = (operator.response == 1 ? P_LY.Enum.ConnectionType.Server : P_LY.Enum.ConnectionType.Device);	
		}
		return operator;
	},
	// - 获取平台名称
	GetSystemName : function (connectId)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, "GetSystemName");
	},
	// - 获取用户优先级
    GetUserPriority : function (connectId) 
    {
        return P_LY.NPPSDKCommon.GetNCResponse(connectId, "GetPriority");
    },
	// - 获取子域
	GetDomainRoad : function (connectId)
	{
		var operator = P_LY.NPPSDKCommon.GetNCResponse(connectId, "GetSubDomain");
	
		if (operator.rv == P_Error.SUCCESS)
		{
			var subdm = [];
			for (var i = 0; i < operator.response.length; i++)
			{
				subdm.push(operator.response[i]);
			}
			operator.response = subdm;	
		}
		return operator;
	},
	
    /*
    ---
    fn: DisConnection 
    desc: 断开一个连接对象，从Connections组里清除
    returns: 
        - P_Error 
    params: 
        - connectId 连接ID 
    ... 
    */
    DisConnection : function (connectId)
    {
        try
        {
            var fn = "P_LY.DisConnection";
            if (!P_LY.Plug.inited) 
            {
                P_Utils.Log(fn, "P_LY.Plug.inited failed~");
            	return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
            }
            if (!connectId || !P_LY.Connections.get(connectId)) 
			{
                P_Utils.Log(fn, "connectId error~");
            	return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
            }
            else 
            {
                var _connStruct = P_LY.Connections.get(connectId);
                // P_Utils.Log(fn, _connStruct.session);
                if (_connStruct instanceof P_LY.Struct.ConnectionStruct) 
                {
                    if (_connStruct.status != P_LY.Enum.ConnectionStatus.Idle && _connStruct.session) 
                    {
						// - 清空喊话或对讲信息
						P_LY.CallTalkControl.Clear(connectId);
						
						// - 停止下载信息
						P_LY.Download.Clear(connectId);
						
						// - 停止视频
						if(P_LY.WindowContainers instanceof P_Utils.Hash) 
						{
							P_LY.WindowContainers.each
							(
							 	function(item) 
								{
									var winObj = item.value.window;
									if(winObj && winObj instanceof P_LY.Struct.WindowStruct) 
									{
										if (winObj.status.playvideoing)
										{
											P_LY.StopVideo(winObj);
										}
										else if (winObj.status.playvoding)
										{
											P_LY.StopVod(winObj);
										}
										P_LY.WindowContainers.unset(item.key);
									} 
								}
							);
						}

	                    P_Utils.Log(fn, "dis connect session "+_connStruct.session);
                        P_IF.DisConnection(_connStruct.nc, _connStruct.session);
                       	
                        _connStruct.session = null;
                        _connStruct.status = P_LY.Enum.ConnectionStatus.Idle;
                    }

                    P_Utils.Log(fn, "close connect "+connectId);
        			
                    P_LY.Connections.unset(connectId);
                }
            }
            P_Utils.Log(fn, "close connect done");
			
            return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
        }
        catch (e) { 
            P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
    },
	
	/*
	---
	fn: NCNotifyManager
	time: 2013.09.10
	remark:
		- 管理NC的绑定事件信息
	...
	*/
	NCNotifyManager : 
	{
		Store : new P_Utils.Hash(),
		
		/*
		---
		fn : Clear
		desc : 清空事件
		time : 2013.11.06
		author : 
			- 
		remark :
			- 在P_LY.UnLoad时调用一次即可
		...
		*/
		Clear : function ()
		{
			try
			{
				var fn = "P_LY.NCNotifyManager.Clear";
				
				if (!P_LY.Plug.inited) 
				{
					P_Utils.Log(fn, "P_LY.Plug.inited failed~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
				}
				
				if (P_LY.NCNotifyManager.Store && P_LY.NCNotifyManager.Store instanceof P_Utils.Hash)
				{
					P_LY.NCNotifyManager.Store.each
					(
					 	function(item, index)
						{
							if (P_LY.Plug.nc != null)
							{
								P_IF.DetachObjectEvent(P_LY.Plug.nc, item.value.name, item.value.callback);	
							}
						}
					);
					
					P_LY.NCNotifyManager.Store = new P_Utils.Hash();
				}
				
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		/*
		---
		params:	
			- name(P_LY.Enum.NCObjectNotify)
			- callbach(function name) 回调函数
		...
		*/
		Add : function (name, callback)
		{
			try
			{
            	var fn = "P_LY.NCNotifyManager.Add";
				
				if (!P_LY.Plug.inited) 
				{
					P_Utils.Log(fn, "P_LY.Plug.inited failed~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
				}
				
				if (!callback || typeof callback != "function")
				{
					P_Utils.Log(fn, "callback error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);		
				}
				
				var found = false;
				for (var key in P_LY.Enum.NCObjectNotify)
				{
					var node = P_LY.Enum.NCObjectNotify[key];
					if (node == name)
					{
						found = true;
						break;
					}
				}
				
				if (!name || !found)
				{
					P_Utils.Log(fn, "name undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				} 
				 
				if (!P_LY.NCNotifyManager.Store.get(name))
				{	
					var evtCallBack = function(args1, args2, args3, args4) 
					{
						try
						{
							var _ncnStruct;
							switch(name)
							{
								case P_LY.Enum.NCObjectNotify.event_notify :
								 	_ncnStruct = new P_LY.Struct.NCObjectNotifyStruct
									(
										name, 
										args1, 
										null, 
										args2,
										args3
										//(args3 ? eval("("+args3+")") : "")
									);
									break;
								case P_LY.Enum.NCObjectNotify.stream_status_notify :
									_ncnStruct = new P_LY.Struct.NCObjectNotifyStruct
									(
										name, 
										args1, 
										args2, 
										args3, 
										(args4 ? eval("("+args4+")") : "")
									);
									break;
								case P_LY.Enum.NCObjectNotify.gps_data_notify :
									_ncnStruct = new P_LY.Struct.NCObjectNotifyStruct
									(
										name, 
										args1, 
										null, 
										null, 
										(args2 ? eval("("+args2+")") : "")
									); 
									break;
							}
							if (typeof _ncnStruct != "undefined") {
								callback(_ncnStruct);	
							}
						}
						catch(ee) {
							P_Utils.Log("P_LY.NCNotifyManager->evtCallBack", "excp error = " + ee.message + "::" + ee.name);
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);	
						} 
					};
					//alert(name)
					var operator = P_IF.AttachObjectEvent(P_LY.Plug.nc, name, evtCallBack);
					//console.log(operator)
					
					if (operator.rv == P_Error.SUCCESS)
					{
						// - 把绑定的信息记录下来
						P_LY.NCNotifyManager.Store.set
						(
							name, 
							{
								key : name,
								callback : evtCallBack || function() {},
								active : true
							}
						);
					}
					else
					{
						P_Utils.Log(fn, "add error, name = " + name);
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);		
					}
				}
				
            	return new P_IF.Struct.ReturnValueStruct(P_Error.SUCCESS); 
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		// - 移除绑定事件
		Remove : function (name /*, callback */)
		{
			try
			{
            	var fn = "P_LY.NCNotifyManager.Remove";
				
				if (!P_LY.Plug.inited) 
				{
					P_Utils.Log(fn, "P_LY.Plug.inited failed~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
				}
				
				var found = false;
				for (var key in P_LY.Enum.NCObjectNotify)
				{
					var node = P_LY.Enum.NCObjectNotify[key];
					if (node == name)
					{
						found = true;
						break;
					}
				}
				
				if (!name || !found)
				{
					P_Utils.Log(fn, "name undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				} 
				
				if (P_LY.NCNotifyManager.Store.get(name))
				{		
					var _ncevtNode = P_LY.NCNotifyManager.Store.get(name);
					if (_ncevtNode.key == name && typeof _ncevtNode.callback == "function")
					{
						var operator = P_IF.DetachObjectEvent(P_LY.Plug.nc, name, _ncevtNode.callback);
					
						if (operator.rv == P_Error.SUCCESS)
						{
							// - 把绑定的信息移除
							P_LY.NCNotifyManager.Store.remove(name); 
						}
						else
						{
							P_Utils.Log(fn, "remove error, name = " + name);
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);		
						}
					} 
				}
				
            	return new P_IF.Struct.ReturnValueStruct(P_Error.SUCCESS); 
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		// - 更新绑定事件
		Update : function (name, callback)
		{
			try
			{
            	var fn = "P_LY.NCNotifyManager.Update";
				 
				var operator = P_LY.NCNotifyManager.Remove(name);
				if (operator.rv != P_Error.SUCCESS)
				{
					return operator;
				}
				
				return P_LY.NCNotifyManager.Add(name, callback);
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		end : true
	},
	
	/*
	---
	fn: ForkResource
	desc: 获取或构建设备（子）资源
	returns: 
		- succ <response: Array(...)>
		- error P_Error
	params: 
		- connectId(string) 连接ID
		- forkLevel(P_LY.Enum.ForkResourceLevel) 构建资源级别
		- offset(uint) 分页查询开始索引
		- count(unit) 分页查询每次最大个数
		- domainRoad(string) 子域名称（根平台使用""或null）
		- customParams(object) 自定义参数
	remark:
		- customParams => 
		{
			PUID(string) 设备PUID
			PUIDSets(string|array) 设备PUID列表
		}
		其中：customParams.PUID存在时，customParams.PUIDSets不可用
		- 直连设备时，可以只传值connectId即可；亦可以传入connectId, ..., customParams._HANDLE或PUID
		- offset, count在forkLevel=P_LY.Enum.ForkResourceLevel.nppForkPUInfo时有效，其他查询可以取0或null
	...
	*/
	ForkResource : function(connectId, forkLevel, offset, count, domainRoad, customParams) 
	{
		try 
		{
            var fn = "P_LY.ForkResource";
			
			if (!P_LY.Plug.inited)
            {
                P_Utils.Log(fn, "P_LY.Plug.inited failed~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
            }
            
			if (!connectId || !P_LY.Connections.get(connectId)) {
                P_Utils.Log(fn, "connectId error~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
            }
            else 
            {
				var _connStruct = P_LY.Connections.get(connectId);
				if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
				{ 
                	P_Utils.Log(fn, "login or session error~");
                	return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				}
				// - 判断是直连还是服务器平台
				if (_connStruct.connType == P_LY.Enum.ConnectionType.Device)
				{
					// - 直连设备时只能获取一个PU资源信息
					forkLevel = P_LY.Enum.ForkResourceLevel.nppForkOnePUInfo;	
				}

				var offset = offset || 0, 
					count = count || 0,
					domainRoad = domainRoad || "",
					customParams = customParams || {};
				switch (forkLevel) 
				{
					// - PU节点信息
					case P_LY.Enum.ForkResourceLevel.nppForkPUInfo:
						var operator = P_IF.ForkPUList(_connStruct.nc, _connStruct.session, domainRoad, offset, count);
						if (operator.rv == P_Error.SUCCESS && operator.response)
						{ 
							if (operator.response.constructor != Array)
							{
								operator.response = [operator.response];
							}
							var _punodeStructArr = new Array(); 

							for (var i = 0; i < operator.response.length; i++)
							{
								var _puHandle = operator.response[i];
								
								var _punodeStruct = P_LY.NPPSDKCommon.ForkInfoByPUHandle
								(
									connectId, 
									_puHandle
								);
								if (_punodeStruct && _punodeStruct instanceof P_LY.Struct.PUNodeStruct)
								{
									_punodeStructArr.push(_punodeStruct);
								}
							}
							return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, _punodeStructArr || new Array());
						} 
						break;
						
					// - PU子资源信息
					case P_LY.Enum.ForkResourceLevel.nppForkPUResourceInfo:  
						
						var _punodeStructArr = new Array(),
							_puInfoSets = new Array(),
							_isPUHandle = false;
						
						_puInfoSets = customParams._HANDLE || customParams._HANDLESets || new Array();
						
						if (_puInfoSets.constructor != Array)
						{
							_puInfoSets = [_puInfoSets];	
						}
						if (_puInfoSets.length > 0) 
						{
							_isPUHandle = true;	
						}
						else 
						{
							_puInfoSets = customParams.PUID || customParams.PUIDSets || new Array();
							if (_puInfoSets.constructor != Array)
							{
								_puInfoSets = [_puInfoSets];
							}
						}
						
						for (var i = 0; i < _puInfoSets.length; i++)
						{ 
							if (_isPUHandle)
							{
								var _puHandle = _puInfoSets[i];	
							}
							else
							{
								// - 获取对应的_HANDLE
								var operator = P_IF.ForkOnePU(_connStruct.nc, _connStruct.session, _puInfoSets[i]);
								if(operator.rv != P_Error.SUCCESS) 
								{
									P_Utils.Log(fn, "1:)get handle error -> " + P_Error.Detail(operator.rv, true) + ", obj -> " + _puInfoSets[i]);
									continue; 		
								}
								
								var _puHandle = operator.response;
							}
							
							var _punodeStruct = P_LY.NPPSDKCommon.ForkInfoByPUHandle
							(
								connectId, 
								_puHandle
							);
							// P_Utils.Log(fn, _punodeStruct); 
							//console.log(_punodeStruct);
							if (_punodeStruct && _punodeStruct instanceof P_LY.Struct.PUNodeStruct) 
							{
								// - 获取资源
								var operator = P_IF.ForkPUResource(_connStruct.nc, _puHandle);
								
								if (operator.rv != P_Error.SUCCESS) 
								{ 	
									P_Utils.Log(fn, "2:)get handle error -> " + P_Error.Detail(operator.rv, true) + ", obj -> " + _puHandle);
									return operator;
								}
								else 
								{
									_punodeStruct.childResource =  new Array();
									
									if (operator.response.constructor != Array)
									{
										operator.response = [operator.response];	
									}
									for (var k = 0; k < operator.response.length; k++)
									{
										var _resHandle = operator.response[k]; // alert(_resHandle);
										
										var _puresStruct = P_LY.NPPSDKCommon.ForkInfoByPUResHandle
										(
											connectId,
											_resHandle
										);
										if (_puresStruct && _puresStruct instanceof P_LY.Struct.PUResourceNodeStruct) 
										{
											_punodeStruct.childResource.push(_puresStruct);
										}
									}
								}
								
								_punodeStructArr.push(_punodeStruct);
							}
						} // end for
						 
						if (_puInfoSets.length == 1 && _punodeStructArr.length == 1 && customParams._returnMode !== "whole") 
						{
							return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, (_punodeStructArr[0] ? (_punodeStructArr[0].childResource || new Array()) : new Array()));
						}
						else 
						{
							return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, _punodeStructArr || new Array());
						}
						break;
						
					// - 一个PU节点资源信息
					case P_LY.Enum.ForkResourceLevel.nppForkOnePUInfo:

						var _puHandle = customParams._HANDLE || "",
							puid = customParams.PUID || "";
						
						// - 直连设备时，不传puid时，由于ForkOnePU的puid是空的，将调用配置命令自行获取puid
						if (_connStruct.connType == P_LY.Enum.ConnectionType.Device)
						{
							if (!puid)
							{
								//console.log("ST_GetPUID")
								var operator = P_LY.ST_GetPUID(connectId, puid);
								if (operator.rv == P_Error.SUCCESS)
								{
									puid = operator.response.M.C.Res.Param.Value || "";	
									_puHandle = "";
									//console.log(operator.response.M.C.Res.Param)
									//console.log(operator.response.M.C.Res.Param.Value)
								}
							}
						}
						
						if (!_puHandle) 
						{
							var operator = P_IF.ForkOnePU(_connStruct.nc, _connStruct.session, puid);
							if(operator.rv != P_Error.SUCCESS) 
							{
                				P_Utils.Log(fn, "3:)get handle error -> " + P_Error.Detail(operator.rv, true) + ", obj -> " + puid);
								return operator; 	
							}
							_puHandle = operator.response;
						}

						var _punodeStruct = P_LY.NPPSDKCommon.ForkInfoByPUHandle
						(
							connectId, 
							_puHandle
						);
						
						if (_punodeStruct && _punodeStruct instanceof P_LY.Struct.PUNodeStruct) 
						{
							// - 直连设备时，使能和在线置为1
							if (_connStruct.connType == P_LY.Enum.ConnectionType.Device)
							{
								_punodeStruct.enable = "1";
								_punodeStruct.online = "1";
							}
							// - 获取资源
							var operator = P_IF.ForkPUResource(_connStruct.nc, _puHandle);

							if (operator.rv != P_Error.SUCCESS) 
							{ 	
								P_Utils.Log(fn, "4:)get handle error -> " + P_Error.Detail(operator.rv, true) + ", obj -> " + _puHandle);
								return operator;
							}
							else 
							{
								_punodeStruct.childResource = _punodeStruct.childResource || new Array();
								
								if (operator.response.constructor != Array)
								{
									operator.response = [operator.response];	
								}

								for (var k = 0; k < operator.response.length; k++)
								{
									var _resHandle = operator.response[k]; // alert(_resHandle);
									 
									var _puresStruct = P_LY.NPPSDKCommon.ForkInfoByPUResHandle
									(
									 	connectId,
										_resHandle
									);
									
									//console.log(_puresStruct)
									
									if (_puresStruct && _puresStruct instanceof P_LY.Struct.PUResourceNodeStruct) 
									{
										_punodeStruct.childResource.push(_puresStruct);
									}
								}

							}
						}

						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, _punodeStruct || null);
						break;
						
					default:
                		P_Utils.Log(fn, "forkLevel unknown error~");
                		return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						break;
						
				} // - end switch 
				
				P_Utils.Log(fn, "fork resource error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
			}
		}
		catch(e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Logic Group Resource
    * ........................................................................................................
    **/
	/*
    ---
    fn: GetLogicGroups
    desc: 获取逻辑分组
    returns:
    	- succ <response: Array(P_LY.Struct.LogicGroupStruct)>
    params:
		- connectId(string) * 连接ID
    ... 
    */
	GetLogicGroups : function (connectId)
	{
		try
		{
			var fn = "P_LY.GetLogicGroups";
			
			if (!P_LY.Plug.inited)
            {
                P_Utils.Log(fn, "P_LY.Plug.inited failed~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
            }
			if (!connectId || !P_LY.Connections.get(connectId)) {
                P_Utils.Log(fn, "connectId error~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
            }
            else 
            {
				var _connStruct = P_LY.Connections.get(connectId);
				
				if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
				{ 
                	P_Utils.Log(fn, "login or session error~");
                	return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				}
				
				var requestXML = '<?xml version="1.0" encoding="utf-8"?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryLogicGroupInfo"/></M>';
				// - 发送平台命令请求响应
				var operator = P_IF.TransCustomMessage(_connStruct.nc, _connStruct.session, requestXML);
				if (operator.rv == P_Error.SUCCESS)
				{
					var _lgsArray = [];
								
					if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
					{
						var _response = (new XML.ObjTree()).parseXML(operator.response);
						if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && typeof _response.Msg.Cmd.NUErrorCode != "undefined" && _response.Msg.Cmd.NUErrorCode != "0")
						{
							// - 返回具体的NUErrorCode错误码
							operator.rv = _response.Msg.Cmd.NUErrorCode || -1;
						}
						else if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && _response.Msg.Cmd.LogicGroup) 
						{
							LogicGroup = _response.Msg.Cmd.LogicGroup || [];
							if (typeof LogicGroup == "object")
							{
								if (LogicGroup.constructor != Array)
								{
									LogicGroup = [LogicGroup];	
								}
								for (var i = 0; i < LogicGroup.length; i++)
								{
									_lgsArray.push
									(
									 	new P_LY.Struct.LogicGroupStruct
										(
										 	LogicGroup[i].Index, 
											LogicGroup[i].Name, 
											LogicGroup[i].LastRefreshTime, 
											LogicGroup[i].RefreshInterval
										)
									);	
								}
							}
						}
					}
					operator.response = _lgsArray; 
				}
				return operator;
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
	---
	fn: GetLogicGroupNodes
    desc: 获取逻辑分组子节点
	returns:
		- succ <response: Array(P_LY.Sruct.LogicGroupNodeStruct)>
	params:
		- connectId(string) * 连接ID
		- options(object) * 可选参数
		{
			logicGroupIndex(uint<P_LY.Struct.LogicGroupStruct:index>) * 逻辑分组索引
			logicGroupNodeIndex(uint) 逻辑分组节点索引，缺省为0
			offset(uint) 分页查询开始条数，缺省为0
			count(uint) 分页查询最大条数，缺省为4294967295（一个较大的数值）
			fetchMode(string) 获取模式，缺省为step（递归分部获取） | once（一次性获取全部节点）
		}
	remark:
		- 获取逻辑分组节点或节点下节点
    	- logicGroupNodeIndex表示当前节点（父节点），根节点的父节点索引为0，即缺省获取逻辑分组直接子节点
		- fetchMode = 'step'时，如果logicGroupNodeIndex属性存在，就获取这个节点下面的节点，不递归。如果不存在此属性，则获取所有节点
		- fetchMode = 'once'时，无论logicGroupNodeIndex存不存在，则获取logicGroupIndex下面的所有节点
		- count 默认为一个足够大的数，但是如果节点过大的话，建议配合offset进行分页获取
	...
	*/
	GetLogicGroupNodes : function (connectId, options)
	{
		try
		{
			var fn = "P_LY.GetLogicGroupNodes";
			if (!connectId || !P_LY.Connections.get(connectId)) {
                P_Utils.Log(fn, "connectId error~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
            }
            else 
            {
				var o = options || {};
				if (typeof o.logicGroupIndex == "undefined" || !P_LY.intRex.test(o.logicGroupIndex))
				{
					P_Utils.Log(fn, "o.logicGroupIndex error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR); 
				}
				
				var _connStruct = P_LY.Connections.get(connectId);
				
				var xmlParam = '<LogicGroup Index="'+(Number(o.logicGroupIndex)||0)+'" ';
				
				o.logicGroupNodeIndex = typeof o.logicGroupNodeIndex != "undefined" && !isNaN(o.logicGroupNodeIndex) ? Number(o.logicGroupNodeIndex) : 0;
				o.offset = typeof o.offset != "undefined" && !isNaN(o.offset) ? Number(o.offset) : 0;
				o.count = typeof o.count != "undefined" && !isNaN(o.count) ? Number(o.count) : 4294967295;
				
				if ((o.fetchMode = o.fetchMode || "step") == "step")
				{
					xmlParam += ' NodeIndex="'+o.logicGroupNodeIndex+'" ';
				}
				
				xmlParam += ' Offset="'+o.offset+'" Count="'+o.count+'" />';
				
				var requestXML = '<?xml version="1.0" encoding="utf-8"?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryLogicGroupNode">'+xmlParam+'</C></M>';
				
				// - 发送平台命令请求响应
				var operator = P_IF.TransCustomMessage(_connStruct.nc, _connStruct.session, requestXML);
				
				if (operator.rv == P_Error.SUCCESS)
				{
					var _lgnsArray = [];
								
					if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
					{
						var _response = (new XML.ObjTree()).parseXML(operator.response);
						if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && typeof _response.Msg.Cmd.NUErrorCode != "undefined" && _response.Msg.Cmd.NUErrorCode != "0")
						{
							// - 返回具体的NUErrorCode错误码
							operator.rv = _response.Msg.Cmd.NUErrorCode || -1;
						}
						else if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && _response.Msg.Cmd.Node) 
						{
							Node = _response.Msg.Cmd.Node || [];
							if (typeof Node == "object")
							{
								if (Node.constructor != Array)
								{
									Node = [Node];	
								}
								for (var i = 0; i < Node.length; i++)
								{
									_lgnsArray.push
									(
									 	new P_LY.Struct.LogicGroupNodeStruct
										(
										 	Node[i].Index, 
											Node[i].Name, 
											Node[i].ParentNode_Index
										)
									);	
								}
							}
						}
					}
					operator.response = _lgnsArray; 
				}
				
				return operator;
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
	---
	fn: GetLogicGroupResource
    desc: 获取逻辑分组节点下资源
	returns:
		- succ <response: Array(P_LY.Sruct.LogicGroupResourceStruct)>
	params:
		- connectId(string) * 连接ID
		- options(object) * 可选参数
		{
			logicGroupIndex(uint<P_LY.Struct.LogicGroupStruct:index>) * 逻辑分组索引
			logicGroupNodeIndex(uint) 逻辑分组节点索引，缺省为0
			offset(uint) 分页查询开始条数，缺省为0
			count(uint) 分页查询最大条数，缺省为4294967295（一个较大的数值）
			fetchMode(string) 获取模式，缺省为step（递归分部获取） | once（一次性获取全部节点）
		}
	remark:
		- 获取逻辑分组节点或节点下节点资源
   		- 约定节点下的资源只有摄像头，没有PU以及其他资源，但为了扩然依然保留Res.Type属性
    	- logicGroupNodeIndex表示当前节点（父节点），根节点的父节点索引为0，即缺省获取逻辑分组直接子节点资源
		- fetchMode = 'step'时，如果logicGroupNodeIndex属性存在，就获取这个节点下面的节点资源，不递归。如果不存在此属性，则获取所有节点资源
		- fetchMode = 'once'时，无论logicGroupNodeIndex存不存在，则获取logicGroupIndex下面的所有节点资源
		- count 默认为一个足够大的数，但是如果节点过大的话，建议配合offset进行分页获取
	...
	*/
	GetLogicGroupResource : function (connectId, options)
	{
		try
		{
			var fn = "P_LY.GetLogicGroupResource";
			if (!connectId || !P_LY.Connections.get(connectId)) {
                P_Utils.Log(fn, "connectId error~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
            }
            else 
            {
				var o = options || {};
				if (typeof o.logicGroupIndex == "undefined" || !P_LY.intRex.test(o.logicGroupIndex))
				{
					P_Utils.Log(fn, "o.logicGroupIndex error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR); 
				}
				
				var _connStruct = P_LY.Connections.get(connectId);
				
				var xmlParam = '<LogicGroup Index="'+(Number(o.logicGroupIndex)||0)+'" ';
				
				o.logicGroupNodeIndex = typeof o.logicGroupNodeIndex != "undefined" && !isNaN(o.logicGroupNodeIndex) ? Number(o.logicGroupNodeIndex) : 0;
				o.offset = typeof o.offset != "undefined" && !isNaN(o.offset) ? Number(o.offset) : 0;
				o.count = typeof o.count != "undefined" && !isNaN(o.count) ? Number(o.count) : 4294967295;
				
				if ((o.fetchMode = o.fetchMode || "step") == "step")
				{
					xmlParam += ' NodeIndex="'+o.logicGroupNodeIndex+'" ';
				}
				
				xmlParam += ' Offset="'+o.offset+'" Count="'+o.count+'" />';
				
				var requestXML = '<?xml version="1.0" encoding="utf-8"?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryLogicGroupResource">'+xmlParam+'</C></M>';
				
				// - 发送平台命令请求响应
				var operator = P_IF.TransCustomMessage(_connStruct.nc, _connStruct.session, requestXML);
				
				if (operator.rv == P_Error.SUCCESS)
				{
					var _lgrsArray = [];
								
					if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
					{
						var _response = (new XML.ObjTree()).parseXML(operator.response);
						if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && typeof _response.Msg.Cmd.NUErrorCode != "undefined" && _response.Msg.Cmd.NUErrorCode != "0")
						{
							// - 返回具体的NUErrorCode错误码
							operator.rv = _response.Msg.Cmd.NUErrorCode || -1;
						}
						else if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && _response.Msg.Cmd.Res) 
						{
							Res = _response.Msg.Cmd.Res || [];
							if (typeof Res == "object")
							{
								if (Res.constructor != Array)
								{
									Res = [Res];	
								}
								for (var i = 0; i < Res.length; i++)
								{
									_lgrsArray.push
									(
									 	new P_LY.Struct.LogicGroupResourceStruct
										(
										 	Res[i].PUID, 
											Res[i].Type, 
											Res[i].Idx, 
											Res[i].Name, 
											Res[i].Description, 
											Res[i].Enable, 
											Res[i].ParentNode_Index
										)
									);	
								}
							}
						}
					}
					operator.response = _lgrsArray; 
				}
				
				return operator;
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
            return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Logic Group Resource
    * ........................................................................................................
    **/
	/*
	---
	fn: PTZ
	desc: 云台操作对象
	time: 2013.09.09
	...
	*/
	PTZ : 
	{
		/*
		---
		fn: Control
		desc: 云台控制
		time: 2013.09.09
		params: 
			- connectId(string) 连接ID
			- puid(string) 设备PUID
			- ptzIndex(string) PTZ资源索引
			- direction(P_LY.Enum.PTZDirection)
			- options(object)
				{
					degree(unit) 转动角度，可不传，默认为0（选传）	
					secondaryDevNo(uint) 辅助设备编号（选传）
					presetPosNo(unit) 预置位编号（选传）
					presetPosName(string) 预置位名称（选传）
				}
		remark:
			- options属性取值说明
			=> 	degree(unit) 云台上下左右转动时选择传入
			=> 	secondaryDevNo(uint) 操作辅助设备时选择传入，不可为空
			=> 	presetPosNo(unit) 操作预置位时选择传入，不可为空
				presetPosName(string) 操作设置预置位时选择传入，不可为空
				
			- <SDK> options._HANDLE云台资源句柄
		...
		*/
		Control : function(connectId, puid, ptzIndex, direction, options) 
		{
			try
			{
				var fn = "P_LY.PTZ.Control";
			 
				if (!P_LY.Plug.inited) 
				{
					P_Utils.Log(fn, "P_LY.Plug.inited failed~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_PLUG_FAILED);
				}
				
				if (!connectId || !P_LY.Connections.get(connectId)) {
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				else 
				{
					if (!puid || !P_LY.puidRex.test(puid))
					{
						P_Utils.Log(fn, "puid error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
					}
					if (typeof ptzIndex == "undefined" || !P_LY.intRex.test(ptzIndex))
					{
						P_Utils.Log(fn, "ptzIndex error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR); 
					}
					if (!direction)
					{
						P_Utils.Log(fn, "direction error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR); 
					}
					
					var options = options || {};
					
					var _connStruct = P_LY.Connections.get(connectId);
					
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
					{ 
						P_Utils.Log(fn, "login or session error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					}
					
				
			    	var operator = P_LY.Control.CommonSet(
			    		connectId,
						{
							puid:puid,
							language:'zh_CN',
							resType:P_LY.Enum.PuResourceType.PTZ,
							resIdx:ptzIndex,
							controlID:"C_"+direction,
							param:'<Param />'
						}
					);
					if (operator.rv != P_Error.SUCCESS) 
					{
						return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
					} 
					
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
					// 下面暂时不用
					var operator, _ptzHandle;
					if (options._HANDLE) 
					{
						_ptzHandle = options._HANDLE;	
					}
					else 
					{
						// - 获取资源句柄
						var _operator = P_LY.NPPSDKCommon.GetHandle
						(
							connectId, 
							puid,
							P_LY.Enum.PuResourceType.PTZ,
							ptzIndex
						);
						if (_operator.rv == P_Error.SUCCESS)
						{
							_ptzHandle = _operator.response;	
						}
						else
						{
							P_Utils.Log(fn, "get handle error~");
							return _operator;	
						}
					}
					
					switch (direction)
					{ 
						case P_LY.Enum.PTZDirection.turnleft :
						case P_LY.Enum.PTZDirection.turnright :
						case P_LY.Enum.PTZDirection.turnup :
						case P_LY.Enum.PTZDirection.turndown :
							operator = P_IF.PTZControl(_connStruct.nc, _ptzHandle, direction, (options.degree || 0)); 
							break;
						case P_LY.Enum.PTZDirection.startsecondarydev :
						case P_LY.Enum.PTZDirection.stopsecondarydev :
							if (typeof options.secondaryDevNo == "undefined")
							{ 
								P_Utils.Log(fn, "options.secondaryDevNo error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
							}
							operator = P_IF.PTZControl(_connStruct.nc, _ptzHandle, direction, options.secondaryDevNo);
							break;  
						case P_LY.Enum.PTZDirection.movetopresetpos :
							if (typeof options.presetPosNo == "undefined")
							{ 
								P_Utils.Log(fn, "options.presetPosNo error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
							}
							operator = P_IF.PTZControl(_connStruct.nc, _ptzHandle, direction, options.presetPosNo);
							break;  
						case P_LY.Enum.PTZDirection.setpresetpos :
							if (typeof options.presetPosNo == "undefined" || typeof options.presetPosName == "undefined")
							{ 
								P_Utils.Log(fn, "options.presetPosNo error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
							}
							operator = P_IF.PTZControl(_connStruct.nc, _ptzHandle, direction, options.presetPosNo, options.presetPosName);
							break;
						default : 
							operator = P_IF.PTZControl(_connStruct.nc, _ptzHandle, direction); 
							break;
					}
					// P_Utils.Log(fn, operator);
					if (operator.rv != P_Error.SUCCESS) 
					{
						P_Utils.Log(fn, "ptz control error = " + P_Error.Detail(operator.rv, true));
						return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
					} 
					
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				} 
			}
			catch(e) {
				console.log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		
		end : true
	},  
	/*
	---
	fn: NPPSDKCommon
	desc: 内部公共对象
	...
	*/
	NPPSDKCommon : 
	{
		/*
		---
		fn: NPPSDKCommon
		desc: 获取（资源）句柄
		time: 2013.09.09
		author: 
			- 
		returns:
			- succ 设备或资源句柄
			- error P_Error
		params:
			- connectId(string) * 连接ID
			- puid(string) * 设备PUID
			- resType(P_LY.Enum.PuResourceType) 资源类型（可省略）
			- resIndex(uint) 资源索引（可省略）
		remark:
			- 传入puid时，获取设备句柄
			- 传入puid, resType, resIndex获取资源句柄
		...
		*/
		GetHandle : function(connectId, puid, resType, resIndex)
		{
			try 
			{
				var fn = "P_LY.NPPSDKCommon.GetHandle";
				
				if (!connectId || !P_LY.Connections.get(connectId)) {
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				else 
				{    
					if (!puid || !P_LY.puidRex.test(puid))
					{
						P_Utils.Log(fn, "puid error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
					}
					
					var _usPUHandle;
					if (!resType && (typeof resIndex == "undefined" || resIndex == null || isNaN(resIndex)))
					{
						_usPUHandle = true;	
					}
					else
					{
						_usPUHandle = false;
						 
						if (!P_LY.intRex.test(resIndex))
						{
							P_Utils.Log(fn, "resIndex error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR); 
						}	
					}
					
					var _connStruct = P_LY.Connections.get(connectId);
					
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
					{ 
						P_Utils.Log(fn, "login or session error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					
					var _operator = P_IF.ForkOnePU
					(
					 	_connStruct.nc, 
						_connStruct.session, 
						puid
					);
					//alert("ForkOnePU1,"+_operator.rv+","+_operator.response+","+_usPUHandle);
					if (_operator.rv == P_Error.SUCCESS)
					{
						_puHandle = _operator.response;
					}
					else
					{
						return _operator;
					} 

					//alert("ForkOnePU2,"+_operator.rv+","+_operator.response);
					if (_usPUHandle)
					{
						if (_operator.response === "00000000")
						{
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_HANDLE_NOTEXIST, _puHandle);	
						} 
						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, _puHandle);
					}
					else
					{
						var _operator = P_IF.ForkPUResource
						(
						 	_connStruct.nc, 
							_puHandle
						); 
						if (_operator.rv == P_Error.SUCCESS)
						{
							if (_operator.response === "00000000")
							{
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_HANDLE_NOTEXIST, _operator.response);	
							} 
							var _operator = P_IF.GetResourceHandleFromPUHandle
							(
							 	_connStruct.nc, 
								_puHandle, 
								resType, 
								resIndex
							);

							//alert("1775,"+JSON.stringify(_operator));
							
							if (_operator.rv == P_Error.SUCCESS)
							{
								if (_operator.response === "00000000")
								{
									return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_HANDLE_NOTEXIST, _operator.response);	
								}
								return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, _operator.response);
							}
							else
							{
								return _operator;
							} 
						}	
						else
						{ 
							return _operator;
						} 
					} 
				}
			}
			catch(e)
			{ 
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		
		// - 设备是否在线
		PUIsOnline : function(connectId, puid)
		{
			try
			{
				var fn = "P_LY.NPPSDKCommon.PUIsOnline";
				if (!connectId || !P_LY.Connections.get(connectId)) {
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				var operator = P_LY.NPPSDKCommon.GetHandle(connectId, puid);
				
				if (operator.rv == P_Error.SUCCESS)
				{
					var _puHandle = operator.response; 
					
					var _connStruct = P_LY.Connections.get(connectId);
					
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
					{ 
						P_Utils.Log(fn, "login or session error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					
					var _operator = P_IF.GetPUOnlineFlag(_connStruct.nc, _puHandle); 
				//console.log(_operator);
					if (_operator.rv == P_Error.SUCCESS)
					{
						if (_operator.response != 1) {
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PU_OFFLINE, _puHandle || null);
						}
					}
				}
				else
				{
					if (operator.rv == P_Error.ERROR_HANDLE_NOTEXIST)
					{
						return operator;	
					}
				}
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, (typeof _puHandle != "undefined" ? _puHandle : null));
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		// - 获取设备信息节点
		ForkInfoByPUHandle : function(connectId, _puHandle)
		{
			try
			{
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					return null;	
				}
				
				if(!_puHandle)
				{
					return null;
				}
				else 
				{
					var _infors = 
					[{
						_method: "GetResourcePUID", _property: "puid", _value: "" 		 
					}, {
						_method: "GetResourceName", _property: "name", _value: "" 		 
					}, {
						_method: "GetResourceDesc", _property: "description", _value: "" 		 
					}, {
						_method: "GetPUOnlineFlag", _property: "online", _value: ""
					}, {
						_method: "GetResourceEnable", _property: "enable", _value: "" 		 
					}, {
						_method: "GetResourceUsable", _property: "usable", _value: "" 		 
					}, {
						_method: "GetPUModelType", _property: "modelType", _value: "" 		 
					}, {
						_method: "GetResourceType", _property: "resourceType", _value: "" 		 
					}, {
						_method: "GetPULatitude", _property: "latitude", _value: "" 		 
					}, {
						_method: "GetPULongitude", _property: "longitude", _value: "" 		 
					},{
						_method: "GetResourceUsable", _property: "usable", _value: "" 		 
					}, {
						/*
						_method: "", _property: "resType", _value: P_LY.Enum.PuResourceType.SELF, _active: false 		 
					}, {
						_method: "", _property: "resIdx", _value: 0, _active: false 		 
					}, {
						_method: "", _property: "immitted", _value: 1, _active: false 		 
					}, {
						_method: "GetPUModel", _property: "modelName", _value: "" 		 
					}, {
						_method: "GetPUProducerID", _property: "manufactrueID", _value: "" 		 
					}, {
						_method: "GetPUHardwareVersion", _property: "hardwareVersion", _value: "" 		 
					}, {
						_method: "GetPUSoftwareVersion", _property: "softwareVersion", _value: "" 		 
					}, {
						_method: "GetPUDeviceID", _property: "deviceID", _value: "" 		 
					}, {*/
						_method: "", _property: "_HANDLE", _value: _puHandle, _active: false 		 
					}];
					
					var _connStruct = P_LY.Connections.get(connectId),
						_punodeStruct = new P_LY.Struct.PUNodeStruct(),
						_errorFlag = false;
					for(var i = 0; i < _infors.length; i++) 
					{ 	
						var _infor = _infors[i];
						 
						if(_infor._active !== false)
						{
							//console.log(_infor._method,typeof P_IF[_infor._method])
							if(typeof P_IF[_infor._method] != "undefined")
							{
								var _operator = P_IF[_infor._method](_connStruct.nc, _puHandle);
							
								//console.log(_operator,_infor._method);
								if(_operator.rv == P_Error.SUCCESS)
								{
									_infor._value = _operator.response;
								}
								else 
								{
									_errorFlag = true;
									break;
								}
							} 
						}

						if(typeof _punodeStruct[_infor._property] != "undefined") 
						{
							_punodeStruct[_infor._property] = _infor._value;
						} 
					}

					return _errorFlag == true ? null : _punodeStruct; 
				} 
			}
			catch(e) {
				return null;	
			}  
		},
		
		// - 获取设备资源信息节点
		ForkInfoByPUResHandle : function(connectId, _resHandle)
		{
			try
			{
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					return null;	
				}
				
				if(!_resHandle)
				{
					return null;
				}
				else 
				{ 
					var _infors = 
					[{
						_method: "GetResourceType", _property: "type", _value: "" 		 
					}, {
						_method: "GetResourceIndex", _property: "idx", _value: "" 		 
					}, {
						_method: "GetResourceName", _property: "name", _value: "" 		 
					}, {
						_method: "GetResourceDesc", _property: "description", _value: "" 		 
					}, {
						_method: "GetResourceEnable", _property: "enable", _value: "" 		 
					},{
						_method: "GetResourceUsable", _property: "usable", _value: "" 		 
					}, {
						_method: "", _property: "_HANDLE", _value: _resHandle, _active: false
					}];
					
					var _connStruct = P_LY.Connections.get(connectId),
						_puresStruct = new P_LY.Struct.PUResourceNodeStruct(),
						_errorFlag = false;
					 
					for(var i = 0; i < _infors.length; i++) 
					{ 	
						var _infor = _infors[i];
						 
						if(_infor._active !== false)
						{
							//alert(_infor._method+","+typeof P_IF[_infor._method])
							if(typeof P_IF[_infor._method] != "undefined")
							{
								var _operator = P_IF[_infor._method](_connStruct.nc, _resHandle);
								if(_operator.rv == P_Error.SUCCESS)
								{
									_infor._value = _operator.response;
								}
								else 
								{
									_errorFlag = true;
									break;
								}
							} 
						}
						if(typeof _puresStruct[_infor._property] != "undefined") 
						{
							_puresStruct[_infor._property] = _infor._value;
						} 
					}
					
					return _errorFlag == true ? null : _puresStruct;
				} 
			}
			catch(e) {
				return null;	
			} 
		},
		
		// - 获取NC请求响应
		GetNCResponse : function (connectId, _method /* , ?... */ )
		{
			try
			{
				var fn = "P_LY.NPPSDKCommon.GetNCResponse";
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId not exist~");	
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				if (!_method) {
					P_Utils.Log(fn, "_method error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}
				
				var _connStruct = P_LY.Connections.get(connectId);
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
				{
					var _argsArr = new Array(_method, _connStruct.nc);
					
					var _args = arguments || [], 
						_argslen = _args.length,
						_customParams = _args[_argslen - 1],
						_HANDLE = null;
						
					if (_argslen >= 3)
					{
						if (typeof _customParams == "object" && typeof _customParams._HANDLE != "undefined") {
							_HANDLE	= _customParams._HANDLE;
						}
						else
						{	
							// - 设备是否在线
							var isOnline_operator = P_LY.NPPSDKCommon.PUIsOnline
							(
								connectId, 
								_args[2]
							);
							if (isOnline_operator.rv == P_Error.ERROR_PU_OFFLINE)
							{
								P_Utils.Log(fn, "operator failed, pu not online~");
								return isOnline_operator;	
							}
							// - 获取对应的资源句柄
							var handle_operator = P_LY.NPPSDKCommon.GetHandle
							(
								connectId, 
								_args[2],
								(_argslen >= 5 ? (_args[3] || null) : null),
								(_argslen >= 5 && typeof _args[4] != "undefined" && !isNaN(_args[4]) ? _args[4] : null)
							);
							if (handle_operator.rv == P_Error.SUCCESS)
							{
								_HANDLE	= handle_operator.response;
							}
							else
							{
								P_Utils.Log(fn, "handle_operator error~");
								return handle_operator;
							}
						}
					}
					else
					{
						_HANDLE = _connStruct.session || null;		
					}
					if (_HANDLE != null)
					{
						_argsArr.push(_HANDLE);
					}
					  
					for (var i = 2; i < _argslen; i++)
					{
						if (typeof _args[i] != "object")
						{
							if (_argslen == 4 && i == 3)
							{
								_argsArr.push(_args[i]);	
							}
							else if (_argslen > 5 && i >= 5) {
								_argsArr.push(_args[i]);		
							}
						}
					}
				
					var operator = new P_LY.Struct.ReturnValueStruct();
					// - 如果P_IF中没有对应接口，那么使用公共接口
					if (typeof P_IF[_argsArr[0]] != "undefined")
					{    
						 operator =  P_IF[_argsArr[0]].apply
						(
							P_IF[_argsArr[0]],
							_argsArr.slice(1)
						);
					}
					else
					{
						// - 获取NC响应
						 operator = P_IF.Common.GetNCResponse.apply
						(
							P_IF.Common.GetNCResponse, 
							_argsArr
						);
					}
						
					if (operator.rv == P_Error.SUCCESS)
					{
						if (typeof _customParams == "object" && typeof _customParams.returnType != "undefined" && _customParams.returnType.toLowerCase() == "json")
						{
							if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
							{
								operator.response = (new XML.ObjTree()).parseXML(operator.response);
							}
						}
					}
					return operator;
				}
				else
				{
					P_Utils.Log(fn, "hav't connected~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);	
			}
		},
		
		end : true
	},
	
	/*
    ---
    fn: CreateWindow 
    desc: 创建窗口
    time: 2013.09.05 -> 2013.09.26
	mdftime: 2013.10.18
    returns: 
		- succ <response: Object(P_LY.Struct.WindowStruct)>
    params: 
		- connectId(string) 连接ID 
        - containerOrId(dom element | dom id) 窗口插件容器或ID
		- windowType(P_LY.Enum.WindowType) 窗口类型
		- windowEvent(P_LY.Struct.WindowEventStruct) 绑定窗口事件
		- customParams(object) 自定义参数
    ... 
    */
	CreateWindow : function(connectId, containerOrId, windowType, windowEvent, customParams)
	{
		try
		{
			var fn = "P_LY.CreateWindow";
			
			if (!connectId || !P_LY.Connections.get(connectId)) 
			{
                P_Utils.Log(fn, "connectId error~");
                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
            }
			var container = null;
			if (typeof containerOrId == "object" && typeof containerOrId.id != "undefined")
			{
				container = containerOrId;	
			}
			else
			{
				if (typeof containerOrId == "undefined" || containerOrId == null || !document.getElementById(containerOrId))
				{	
					P_Utils.Log(fn, "containerOrId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				}
				
				container = document.getElementById(containerOrId);
			}
			var t_wndname = container.id + "_wnd"; 
			
			container.innerHTML = P_LY.Plug.Html.get("wnd",t_wndname);//.replace(/(@id)|(@name)/g, t_wndname);
			
			if (t_wndname == "" || (!container.firstChild && container.firstChild.id != t_wndname && !document.getElementById(t_wndname)))
			{
				P_Utils.Log(fn, "include wnd plugin error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
			}
			
				var wnd = container.firstChild || document.getElementById(t_wndname);
				
				wnd.style.border = "0";
				wnd.style.width = "100%";
				wnd.style.height = "100%";
				//var wnd = document.getElementById(t_wndname);
				var options = {
					container : container,
					wnd : wnd,
					type : windowType || P_LY.Enum.WindowType.VIDEO,
					connectId : connectId,
					customParams : typeof customParams == "object" ? customParams : {}
				};
				var winObj = new P_LY.Struct.WindowStruct(options);
				

				// - 获取窗口插件句柄
				var operator = P_IF.GetWindowHandle(winObj.wnd);
				if (operator.rv == P_Error.SUCCESS)
				{
					winObj.wndHandle = operator.response;
				}
				
				if (!winObj || !winObj instanceof P_LY.Struct.WindowStruct) 
				{
	                P_Utils.Log(fn, "wnd create failed~");
	                return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INIT_WINDOW_FAILED);
	            }
				
				// - 初始化绑定窗口事件
				P_LY.WindowAttachEvent.Init(winObj, windowEvent);
				
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, winObj);
			
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	 
	/*
    ---
    fn: WindowAttachEvent
    desc: 绑定窗口事件
	*/
	WindowAttachEvent : 
	{
		/*
		---
		fn: Init
		desc: 初始化绑定窗口事件 
		returns: 
			- Object(P_LY.Struct.ReturnValueStruct)
		params: 
			- winObj(P_LY.Struct.WindowStruct) 窗口信息结构实例
			- windowEvent(P_LY.Struct.WindowEventStruct) 窗口绑定事件结构实例
		remark:
			- 对winObj重复覆盖绑定windowEvent，可以随时调用此函数
		*/
		Init: function(winObj, windowEvent)
		{
			try
			{
				var fn = "P_LY.WindowAttachEvent.Init";
				
				if (!winObj || !winObj instanceof P_LY.Struct.WindowStruct || typeof winObj.wnd == "undefined")
				{
					P_Utils.Log(fn, "winObj error~");
                	return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				}
				
				if (!windowEvent || !windowEvent instanceof P_LY.Struct.WindowEventStruct)
				{
					windowEvent = new P_LY.Struct.WindowEventStruct();	
				}
				
				var _SELF = P_LY.WindowAttachEvent;
						
				// - 注册绑定的事件
				var _DW_ATTACH_EVENT = function (_evtObjItem, _evt_cb)
				{
					if (_evtObjItem.status == true)
					{	
						var name = _evtObjItem.name;
						var callback = _evt_cb || _evtObjItem.callback;
						
						var _name = "_" + name.toUpperCase();
						var _callback = function ()
						{ 
							var _argsArr = new Array(winObj);
							for (var i = 0; i < arguments.length; i++)
							{
								_argsArr.push(arguments[i]);	
							}
							
							switch(name)
							{
								case "lbtn_click" :
									_SELF.Fire_lbtn_click.apply(_SELF, _argsArr);
									break; 
								case "select_rect" :
									_SELF.Fire_select_rect.apply(_SELF, _argsArr);
									break;
								
								// - 云台控制（拖拽、鼠标滚轮）事件
								case "drag_up" :
								case "drag_down" :
								case "drag_left" :
								case "drag_right" :
								case "drag_stop" :
								case "mouse_wheel_scroll_forward" :
								case "mouse_wheel_scroll_backward" : 
								case "mouse_wheel_scroll_stop" :
									var _action;
									if (name == "drag_up") {
										_action = "drag_up";
									}
									else if (name == "drag_down") {
										_action = "drag_down";
									}
									else if (name == "drag_left") {
										_action = "drag_left";
									}
									else if (name == "drag_right") {
										_action = "drag_right";
									}
									else if (name == "drag_stop") {
										_action = "drag_stop";
									}
									else if (name == "mouse_wheel_scroll_forward") {
										_action = "mws_forward";
									}
									else if (name == "mouse_wheel_scroll_backward") {
										_action = "mws_backward";
									}
									else {
										_action = "mws_stop";
									}
									_argsArr.splice(0, 0, _action);
									_SELF.Fire_PTZControl.apply(_SELF, _argsArr);
									break;
									
								case "full_screen_window_show" :
									_SELF.Fire_fsw_show.apply(_SELF, _argsArr);
									break;
								case "full_screen_window_hide" :
									_SELF.Fire_fsw_hide.apply(_SELF, _argsArr);
									break;
								case "redraw_window" :
									_SELF.Fire_redraw_window.apply(_SELF, _argsArr);
									break;
								case "menu_command" :
									_argsArr.splice(2, 0, callback);
									_SELF.Fire_menu_command.apply(_SELF, _argsArr);
									break;
							}
							
							if (name != "menu_command")
							{
								if (typeof callback == "function")
								{
									//callback.apply(callback, _argsArr.slice(1));	
    								//console.log(_argsArr,callback)
									callback.apply(callback, _argsArr);
								}
							}
						};
						
						if (typeof winObj.wnd[_name] != "undefined") {
							// - 移除已经注册的事件
							P_IF.DetachObjectEvent(winObj.wnd, name, winObj.wnd[_name]);
						} 
						// - 绑定事件
						P_IF.AttachObjectEvent(winObj.wnd, name, _callback);
						
						winObj.wnd[_name] = _callback;
					} 
				};
				
				for (var _key in windowEvent) 
				{
					// - 使用闭包
					(function(key) {
						if (typeof windowEvent[key] != "undefined")
						{
							// - 云台控制事件单独处理
							if (key == "ptz_control") {
								if (windowEvent[key].status == true)
								{
									for (var __key in windowEvent[key])
									{
										// - 使用闭包
										(function(k) {	  
											if (typeof windowEvent[key][k] == "object" && typeof windowEvent[key][k].status != "undefined")
											{
												_DW_ATTACH_EVENT(windowEvent[key][k], windowEvent[key].callback);
											}
										})(__key);	
									}
								}
							}
							else {
								_DW_ATTACH_EVENT(windowEvent[key]);
								
								// - 绑定自定义右键菜单项
								if (key == "menu_command" && typeof windowEvent[key] == "object" && windowEvent[key].status == true)
								{									
									winObj.wnd["NPP_b_MENU_COMMAND"] = 1;
									
									var _customMenus = windowEvent[key].menu;
									if (typeof winObj.wnd["NPP_customMenus"] != "undefined")
									{
										// -  清除所有菜单项
										P_IF.DeleteAllMenuItem(winObj.wnd);
									}
									winObj.wnd["NPP_customMenus"] = {}; // [keyCode] -> key, text
									
									// - 追加（添加）窗口右键菜单项
									P_LY.WindowAttachEvent.AppendMenuCommand(winObj, _customMenus);
								}
								else
								{
									winObj.wnd["NPP_b_MENU_COMMAND"] = 0;
								}
							} 
						}
					})(_key);
				}
				
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},

		/**
		* --------------------------------------------------------------------------------------------------------
		*    - s - remark: 自定义窗口右键菜单项控制（SDK内部使用）
		* ........................................................................................................ 
		**/
		__Sdk_MenuCommandCtl : function(_action, _winObj, _customMenus /*, bRedraw */)
		{
			try 
			{
				var fn = "P_LY.WindowAttachEvent.__Sdk_MenuCommandCtl -> " + _action;
				
				if (typeof _winObj == "undefined" || typeof _winObj.wnd == "undefined" 
					|| typeof _winObj.wnd["NPP_b_MENU_COMMAND"] == "undefined" || !_winObj.wnd["NPP_b_MENU_COMMAND"])
				{
					P_Utils.Log(fn, "_objWnd | wnd | NPP_b_MENU_COMMAND(disabled right click menu_command) error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
				}
				
				switch (_action)
				{
					case "append":
						if (typeof _customMenus != "object")
						{
							P_Utils.Log(fn, "_customMenus error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						if (_customMenus.constructor != Array) {
							_customMenus = [_customMenus];	
						}
						 
						_winObj.wnd["NPP_customMenus"] = _winObj.wnd["NPP_customMenus"] || {};
						
						for (var i = 0; i < _customMenus.length; i++) 
						{
							var item = _customMenus[i];
							if (typeof item.key == "undefined" || typeof item.text == "undefined")
								continue;
								
							var _keyExist = false,
								_keyCodeNew = 0;
								
							if (typeof _winObj.wnd["NPP_customMenus"] != "undefined")
							{
								for (var _keyCode in _winObj.wnd["NPP_customMenus"])
								{
									var _menuItem = _winObj.wnd["NPP_customMenus"][_keyCode];
									if (_menuItem.key == item.key && (P_Utils.Array.indexOf(["-", "separator", "split"], item.key) == -1))
									{
										_keyExist = true;
										break;
									}
									else
									 	_keyCodeNew++;
								}
							}
							// alert(_keyExist + "::" + _keyCodeNew + "::" + item.key + "::" + item.text);
							if (!_keyExist)
							{
								var mode = (P_Utils.Array.indexOf(["-", "separator", "split"], item.key) != -1) ? 0x800 : 0;
								var operator = P_IF.AppendMenuItem(_winObj.wnd, mode, _keyCodeNew, item.text);
								if (operator.rv == P_Error.SUCCESS)
								{
									if (typeof _winObj.wnd["NPP_customMenus"][_keyCodeNew] == "undefined")
									{ 
										_winObj.wnd["NPP_customMenus"][_keyCodeNew] = 
										{
											key : item.key,
											text : item.text
										}; 
									} 
								}
							} 
						}
						break;
					case "update":
						if (typeof _customMenus != "object")
						{
							P_Utils.Log(fn, "_customMenus error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						
						// - equals bRedraw
						if (typeof arguments[3] != "undefined" && arguments[3] == 1) 
						{
							// -  清除所有菜单项
							P_IF.DeleteAllMenuItem(_winObj.wnd);
							_winObj.wnd["NPP_customMenus"] = {}; // [keyCode] -> key, text
							
							// - 追加（添加）窗口右键菜单项
							P_LY.WindowAttachEvent.__Sdk_MenuCommandCtl("append", _winObj, _customMenus);
						}
						else
						{
							if (_customMenus.constructor != Array) {
								_customMenus = [_customMenus];	
							}
							
							_winObj.wnd["NPP_customMenus"] = _winObj.wnd["NPP_customMenus"] || {};
							
							for (var i = 0; i < _customMenus.length; i++) 
							{
								var item = _customMenus[i];
								if (typeof item.key == "undefined" || typeof item.text == "undefined")
									continue;
								
								var _keyExist = false,
									_keyCodeNew = 0;
								
								if (typeof _winObj.wnd["NPP_customMenus"] != "undefined")
								{
									for (var _keyCode in _winObj.wnd["NPP_customMenus"])
									{
										var _menuItem = _winObj.wnd["NPP_customMenus"][_keyCode];
										if (_menuItem.key == item.key && (P_Utils.Array.indexOf(["-", "separator", "split"], item.key) == -1))
										{
											// - 更新text
											if (item.text)
											{
												_menuItem.text = item.text;
											}
											_keyExist = true;
											break;
										}
										
										_keyCodeNew++;
									}
								}
								
								if (!_keyExist)
								{
									_winObj.wnd["NPP_customMenus"][_keyCodeNew] = 
									{
										key : item.key,
										text : item.text
									};
								} 
							}
														
							var _customMenusNewArrayCache = [];
							
							for (var _keyCode in _winObj.wnd["NPP_customMenus"])
							{
								var _menuItem = _winObj.wnd["NPP_customMenus"][_keyCode];
								
								_customMenusNewArrayCache.push
								({
									key : _menuItem.key, 
									text : _menuItem.text 
								});
							}
							
							P_Utils.Log(fn, "Update custom menus -> " + _customMenusNewArrayCache);
							
							// -  清除所有菜单项
							P_IF.DeleteAllMenuItem(_winObj.wnd);
							_winObj.wnd["NPP_customMenus"] = {}; // [keyCode] -> key, text
							
							// - 追加（添加）窗口右键菜单项
							P_LY.WindowAttachEvent.__Sdk_MenuCommandCtl("append", _winObj, _customMenusNewArrayCache);
							
						} 
						break;
					case "remove":
						var _isRemoveAll = false;
						
						var _customMenus = _customMenus || [];
						if (typeof _customMenus == "object" && _customMenus.constructor != Array)
						{
							_customMenus = [_customMenus];
						}
						if (_customMenus.length <= 0)
						{
							_isRemoveAll = true;
						}
						
						if (_isRemoveAll)
						{
							// -  清除所有菜单项
							P_IF.DeleteAllMenuItem(_winObj.wnd);
							_winObj.wnd["NPP_customMenus"] = {}; // [keyCode] -> key, text
						}
						else 
						{
							var _customMenusNewArrayCache = [];
							
							for (var _keyCode in _winObj.wnd["NPP_customMenus"])
							{
								var _menuItem = _winObj.wnd["NPP_customMenus"][_keyCode];
								
								var _keyDelExist = false;
								
								for (var i = 0; i < _customMenus.length; i++) 
								{
									var item = _customMenus[i];
									if (typeof item.key == "undefined")
										continue;
									
									if (_menuItem.key == item.key)
									{
										if (P_Utils.Array.indexOf(["-", "separator", "split"], item.key) == -1)
										{	 
											_keyDelExist = true;
											break;
										}
										else 
										{
											if (typeof item.text != "undefined" && _menuItem.text == item.text)
											{
												_keyDelExist = true;
												break;
											} 
										} 
									}
								}
								
								if (!_keyDelExist)
								{
									_customMenusNewArrayCache.push
									({
										key : _menuItem.key, 
										text : _menuItem.text 
									});	
								}
							} // end for _keyCode
							
							P_Utils.Log(fn, "Remove then update custom menus -> " + _customMenusNewArrayCache);
							
							// -  清除所有菜单项
							P_IF.DeleteAllMenuItem(_winObj.wnd);
							_winObj.wnd["NPP_customMenus"] = {}; // [keyCode] -> key, text
							
							// - 追加（添加）窗口右键菜单项
							P_LY.WindowAttachEvent.__Sdk_MenuCommandCtl("append", _winObj, _customMenusNewArrayCache);
						}
						break;
				}
				
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		/*
		---
		fn : AppendMenuCommand
		desc : 追加（添加）窗口右键菜单项
		...
		*/
		AppendMenuCommand : function(winObj, customMenus)
		{
			return this.__Sdk_MenuCommandCtl.call(this.__Sdk_MenuCommandCtl, "append", winObj, customMenus);
		},/*
		---
		fn : UpdateMenuCommand
		desc : 更新窗口右键菜单项
		remark :
			- bRedraw(bool::0) 是否重新绘制（1、true是，0、false否）
			- if bRedraw = 1, then removing all menus 1st, appending all menus 2nd 
		...
		*/
		UpdateMenuCommand : function(winObj, customMenus, bRedraw /* = 0 */)
		{
			return this.__Sdk_MenuCommandCtl.call(this.__Sdk_MenuCommandCtl, "update", winObj, customMenus, (bRedraw || 0));
		},/*
		---
		fn : RemoveMenuCommand
		desc : 剔除窗口右键菜单项
		remark :
			- 如果customMenus存在，那么匹配key进行删除
			- 对["-", "separator", "split"]之一，将根据匹配text进行删除，如{key: "-", text: "split_1"}，将删除split_1对应的分割线
		...
		*/
		RemoveMenuCommand : function(winObj, customMenus)
		{
			return this.__Sdk_MenuCommandCtl.call(this.__Sdk_MenuCommandCtl, "remove", winObj, customMenus);
		},
		/**
		* --------------------------------------------------------------------------------------------------------
		*    - e - remark: 自定义窗口右键菜单项控制
		* ........................................................................................................ 
		**/
		
		// - 左键单击
		Fire_lbtn_click : function (winObj, x, y)
		{
			P_Utils.Log("Fire_lbtn_click", "xPos ->" + x + ", yPos -> " + y);
		},
		// - 选择矩形框
		Fire_select_rect : function (winObj, x, y, m, n)
		{
			P_Utils.Log("Fire_select_rect", x + "," + y + "," + m + "," + n);
		},
		
		// - 响应云台控制（拖拽、鼠标滚轮）事件
		Fire_PTZControl : function(_action, winObj)
		{
			try
			{
				var fn = "P_LY.WindowAttachEvent.Fire_PTZControl";
				
				if (winObj.wnd && winObj.status && winObj.status.playvideoing)
				{	
					var _direction;
					switch (_action)
					{
						// - 向上拖拽
						case "drag_up" :
							_direction = P_LY.Enum.PTZDirection.turnup;
							break; 
						// - 向下拖拽
						case "drag_down" :
							_direction = P_LY.Enum.PTZDirection.turndown;
							break;
						// - 向左拖拽 
						case "drag_left" :
							_direction = P_LY.Enum.PTZDirection.turnleft;
							break;
						// - 向右拖拽 
						case "drag_right" :
							_direction = P_LY.Enum.PTZDirection.turnright;
							break;
						// - 停止拖拽 
						case "drag_stop" :
							_direction = P_LY.Enum.PTZDirection.stopturn;
							break; 
						// - 鼠标滚轮向前
						case "mws_forward" :
							_direction = P_LY.Enum.PTZDirection.zoomin;
							break; 
						// - 鼠标滚轮向后
						case "mws_backward" :
							_direction = P_LY.Enum.PTZDirection.zoomout;
							break; 
						// - 停止鼠标滚轮
						case "mws_stop" :
							_direction = P_LY.Enum.PTZDirection.stopzoom;
							break; 
					}	
				}
				// - 发送云台命令
				var operator = P_LY.PTZ.Control
				(
					winObj.connectId,
					winObj.params.puid,
					winObj.params.idx,
					_direction,	
					{}
				);
				
				if (P_LY.Debug.debug)
				{
					P_Utils.Log(fn, "_action -> " + _action + ", _direction - > " + _direction + ", operator ->" + P_Error.Detail(operator.rv, true));
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		// - 显示全屏
		Fire_fsw_show : function (winObj, _newWinHandle)
		{
			try
			{
				var fn = "P_LY.WindowAttachEvent.Fire_fsw_hide";
				
				if (winObj && winObj.wnd && (winObj.status.playvideoing || winObj.status.playvoding) && typeof _newWinHandle != "undefined") 
				{
					winObj.status.isfullscreening = true;
					winObj.wnd["NPP_tmpWinHandle"] = _newWinHandle;
					return P_IF.SetPlayWindow(P_LY.Plug.nc, winObj.params.ivStreamHandle, _newWinHandle);	
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		// - 停止全屏
		Fire_fsw_hide : function (winObj, _oldWinHandle)
		{
			try
			{
				var fn = "P_LY.WindowAttachEvent.Fire_fsw_hide";
				
				if (winObj && winObj.wnd && (winObj.status.playvideoing || winObj.status.playvoding) && typeof _oldWinHandle != "undefined") 
				{
					winObj.status.isfullscreening = false;
					winObj.wnd["NPP_tmpWinHandle"] = _oldWinHandle;
					return P_IF.SetPlayWindow(P_LY.Plug.nc, winObj.params.ivStreamHandle, _oldWinHandle);	
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		// - 重绘窗口
		Fire_redraw_window : function (winObj)
		{
			try
			{
				var fn = "P_LY.WindowAttachEvent.Fire_redraw_window";
				
				if (winObj && winObj.wnd) {
					if (winObj.status.playvideoing || winObj.status.playvoding)
					{
						P_IF.RedrawLastImage(P_LY.Plug.nc, winObj.params.ivStreamHandle);
					}
					else
					{
						// - 设置是否重绘背景标志
						P_IF.SetEraseBkgndFlag(winObj.wnd, 0);
					}
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		// - 自定义右键菜单
		Fire_menu_command : function (winObj, _keyCode, _callback)
		{
			try
			{
				var fn = "P_LY.WindowAttachEvent.Fire_menu_command";
				
				// P_Utils.Log(fn, "_keyCode -> " + _keyCode);
				 
				if (winObj && winObj.wnd && typeof winObj.wnd["NPP_customMenus"] != "undefined" /* && (winObj.status.playvideoing || winObj.status.playvoding) */)
				{ 
					if (typeof winObj.wnd["NPP_customMenus"][_keyCode] != "undefined")
					{
						var item = winObj.wnd["NPP_customMenus"][_keyCode];
						
						P_Utils.Log(fn, "_keyCode -> " + _keyCode + ", item.key -> " + item.key + ", item.text -> " + item.text);
						
						if (typeof _callback == "function")
						{
							_callback(item.key);	
						}
					}
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		end : true
	},
	
	/*
	---
	fn : WindowControlMode
	desc : 窗口控制模式对象
	...
	*/
	WindowControlMode :
	{
		get : function(objWnd)
		{
			return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, ((objWnd && objWnd.wnd) ? (objWnd.wnd["NPP_controlMode"] || 0) : 0));
		},
		/*
		---
		params :
			- objWnd(P_LY.Struct.WindowStruct) 窗口结构
			- value(uint) 控制模式信号量（0表示拖拽，1表示框选，框选模式时不会全屏）
		...
		*/
		set : function(objWnd, value)
		{
			try
			{
				var fn = "P_LY.WindowControlMode.set";
				
				if (objWnd && objWnd.wnd)
				{
					var value = (value || 0) != 1 ? 0 : 1;
					var operator = P_IF.SetControlMode(objWnd.wnd, value);
					if (operator.rv == P_Error.SUCCESS)
					{
						objWnd.wnd["NPP_controlMode"] = value;
					}
					return operator;
				}
				else
				{
					P_Utils.Log(fn, "objWnd wnd not exist~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
				}
			}
			catch(e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		}
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Mask Control 2014.01.20
    * ........................................................................................................
    **/
	// - 是否使能掩码，窗口控制模式也要同时设定
	EnableMask : function (objWnd, bValue)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"EnableMask", 
			{
				objWnd : (objWnd || null),
				bValue : ((bValue || 0) != 1 ? 0 : 1)
			}
		);
	},
	// - 点击掩码
	MaskClick : function (objWnd, x, y)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"MaskClick", 
			{
				objWnd : (objWnd || null),
				x : (x || 0),
				y : (y || 0)
			}
		);
	},
	// - 选择掩码区域
	MaskSelectRect : function (objWnd, bx, by, ex, ey)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"MaskSelectRect", 
			{
				objWnd : (objWnd || null),
				bx : (bx || 0),
				by : (by || 0),
				ex : (ex || 0),
				ey : (ey || 0)
			}
		);
	},
	// - 选择掩码区域
	ClearMask : function (objWnd)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"ClearMask", 
			{
				objWnd : (objWnd || null)
			}
		);
	},
	// - 选择掩码区域
	RemoveSelectRect : function (objWnd)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"RemoveSelectRect", 
			{
				objWnd : (objWnd || null)
			}
		);
	},
	/*
	---
	fn : GetMask
	desc : 获取掩码
	returns :
		- response(Array<198个十进制数值>)
	params : 
		- objWnd(P_LY.Struct.WindowStruct) 窗口信息结构
	...
	*/
	GetMask : function (objWnd)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"GetMask", 
			{
				objWnd : (objWnd || null)
			}
		);
	},
	/*
	---
	fn : SetMask
	desc : 设置掩码
	params : 
		- objWnd(P_LY.Struct.WindowStruct) 窗口信息结构
		- maskList(Array) 掩码数组，198个十进制数值
	...
	*/
	SetMask : function (objWnd, maskList)
	{
		return P_LY.__Sdk_MaskCtl
		(
		 	"SetMask", 
			{
				objWnd : (objWnd || null),
				maskList : (maskList || [])
			}
		);
	},
	__Sdk_MaskCtl : function (action, options)
	{
		try
		{
			var fn = "P_LY.__Sdk_MaskCtl";
			 
			var options = options || {};
			if (options.objWnd && options.objWnd.wnd)
			{
				if (!options.objWnd.connectId || !P_LY.Connections.get(options.objWnd.connectId))
				{
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR); 
				}
				var _connStruct = P_LY.Connections.get(options.objWnd.connectId);
				if (options.objWnd.wnd && (options.objWnd.status.playvideoing || options.objWnd.status.playvoding))
				{
					switch (action)
					{
						case "EnableMask" :
							return P_IF.EnableMask(_connStruct.nc, options.objWnd.params.ivStreamHandle, options.bValue);
							break;
						case "MaskClick" :
							return P_IF.MaskClick(_connStruct.nc, options.objWnd.params.ivStreamHandle, options.x, options.y);
							break;
						case "MaskSelectRect" :
							return P_IF.MaskSelectRect(_connStruct.nc, options.objWnd.params.ivStreamHandle, options.bx, options.by, options.ex, options.ey);
							break;
						case "RemoveSelectRect" :
							return P_IF.RemoveSelectRect(_connStruct.nc, options.objWnd.params.ivStreamHandle);
							break;
						case "ClearMask" :
							return P_IF.ClearMask(_connStruct.nc, options.objWnd.params.ivStreamHandle);
							break;
						case "GetMask" :
							return P_IF.GetMask(_connStruct.nc, options.objWnd.params.ivStreamHandle);
							break;
						case "SetMask" :
							return P_IF.SetMask(_connStruct.nc, options.objWnd.params.ivStreamHandle, options.maskList);
							break;
					} 
				}
				else
				{
					P_Utils.Log(fn, "window no playing~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY); 
				}
			}
			else
			{
				P_Utils.Log(fn, "objWnd wnd not exist~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}	
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Mask Control 2014.01.20
    * ........................................................................................................
    **/
	/*
    ---
    fn: PlayVideo 
    desc: 播放视频
    returns: 
		- P_Error
    params: 
		- connectId	(string) 连接ID
        - objWnd (P_LY.Struct.WindowStruct) 窗口对象
		- puid (string) 设备ID 
		- idx (string) 视频资源索引
		- streamType (string) 流类型
		- customParams (object) 自定义参数
    ... 
    */
	PlayVideo : function (connectId, objWnd, puid, idx, streamType, customParams) 
	{
		
		try
		{
			var fn = "P_LY.PlayVideo";
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId not exist~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}

			if (objWnd && objWnd.wnd)
			{
				var _connStruct = P_LY.Connections.get(connectId);
				
				if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				if (!puid || !P_LY.puidRex.test(puid)) 
				{
					P_Utils.Log(fn, "puid error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
				}
				if (isNaN(idx) || !P_LY.intRex.test(idx)) 
				{
					P_Utils.Log(fn, "idx error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR);
				}
				
				var streamType = streamType || P_LY.Enum.StreamType.REALTIME;
				//streamType = 0;
				// - 检测设备是否在线
//				var operator = P_LY.NPPSDKCommon.PUIsOnline(connectId, puid);
//				if (operator.rv == P_Error.ERROR_PU_OFFLINE) 
//				{
//					P_Utils.Log(fn, "pu not online error~");
//					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PU_OFFLINE);	
//				}
				
				var customParams = customParams || {};
				
				var _ivHandle;
				
				// - 视频对应的资源句柄
				if (customParams._HANDLE) {
					_ivHandle = customParams._HANDLE;
				}
				else {
					var operator = P_LY.NPPSDKCommon.GetHandle
					(
						connectId,
						puid,
						P_LY.Enum.PuResourceType.VideoIn,
						idx
					);
					if (operator.rv == P_Error.SUCCESS)
					{
						_ivHandle = operator.response;
					}
					else
					{
						P_Utils.Log(fn, "get iv handle error~");
						return operator;
					}
				}
				var operator = P_IF.StartStreamPlay(_connStruct.nc, _ivHandle, streamType, objWnd.wndHandle);
			
				if (operator.rv != P_Error.SUCCESS) 
				{
					return new P_LY.Struct.ReturnValueStruct(operator.rv);
				}
				
				objWnd.connectId = connectId;
				objWnd.params.puid = puid;
				objWnd.params.idx = idx;
				objWnd.params.streamType = streamType;
				
				objWnd.wnd["NPP_transcodeStreamPlaying"] = false;
 
				objWnd.params.ivStreamHandle = operator.response;
				objWnd.status.playvideoing = true;
				
				// - 设置是否重绘背景标志
				P_IF.SetEraseBkgndFlag(objWnd.wnd, 1);
				
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS); 
			}
			else {
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e)
		{
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: PlayTranscodeVideo 
    desc: 播放平台转码视频
    params: 
		- connectId	(string) 连接ID
        - objWnd (P_LY.Struct.WindowStruct) 窗口对象
		- puid (string) 设备ID 
		- idx (string) 视频资源索引 
    ... 
    */
	PlayTranscodeVideo : function (connectId, objWnd, puid, idx, alg, resolution, bitRate, frameRate)
	{
		try
		{
			var fn = "P_LY.PlayTranscodeVideo";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId not exist~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			
			if (objWnd && objWnd.wnd)
			{
				var _connStruct = P_LY.Connections.get(connectId);
				
				if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				
				if (!puid || !P_LY.puidRex.test(puid)) 
				{
					P_Utils.Log(fn, "puid error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
				}
				if (isNaN(idx) || !P_LY.intRex.test(idx)) 
				{
					P_Utils.Log(fn, "idx error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR);
				}
				if (typeof alg == "undefined" || alg == null || typeof resolution == "undefined" || resolution == null || typeof bitRate == "undefined" || bitRate == null || typeof frameRate == "undefined" || frameRate == null)
				{
					P_Utils.Log(fn, "alg | resolution | bitRate | frameRate error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}
				// - 检测设备是否在线
				var operator = P_LY.NPPSDKCommon.PUIsOnline(connectId, puid);
				if (operator.rv == P_Error.ERROR_PU_OFFLINE) 
				{
					P_Utils.Log(fn, "pu not online error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PU_OFFLINE);	
				}
				var handle_operator = P_LY.NPPSDKCommon.GetHandle
				(
					connectId,
					puid,
					P_LY.Enum.PuResourceType.VideoIn,
					idx
				);
				if (handle_operator.rv != P_Error.SUCCESS)
				{
					return handle_operator;
				}
				else
				{
					var play_operator = P_IF.StartTranscodeStreamPlay
					(
						_connStruct.nc,
						handle_operator.response,
						alg,
						resolution,
						bitRate,
						frameRate,
						objWnd.wndHandle
					);
					if (play_operator.rv == P_Error.SUCCESS)
					{
						objWnd.connectId = connectId;
						objWnd.params.puid = puid;
						objWnd.params.idx = idx;
						
						objWnd.params.alg = alg;
						objWnd.params.resolution = resolution;
						objWnd.params.bitRate = bitRate;
						objWnd.params.frameRate = frameRate;
						
						// - 流类型置为转码流
						objWnd.params.streamType = P_LY.Enum.StreamType.TRANSCODE; 
						// - 记录转码播放
						objWnd.wnd["NPP_transcodeStreamPlaying"] = true;
						
						objWnd.params.ivStreamHandle = play_operator.response;
						objWnd.status.playvideoing = true;
						
						// - 设置是否重绘背景标志
						P_IF.SetEraseBkgndFlag(objWnd.wnd, 1);	
					}
					return play_operator;
				}
			}
			else
			{
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.name + "::" + e.message);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: StopVideo 
    desc: 停止播放 
    returns: 
        - P_Error
    params: 
        - objWnd (P_LY.Struct.WindowStruct)        // - 窗口对象
    ... 
    */
	StopVideo : function (objWnd) 
	{
		try
		{
			var fn = "P_LY.StopVideo";
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (connectId && P_LY.Connections.get(connectId)) 
				{
					var _conn = P_LY.Connections.get(connectId);
					if (_conn.status != P_LY.Enum.ConnectionStatus.Connected) 
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					if (objWnd.status.playvideoing) 
					{
						var ivStreamHandle = objWnd.params.ivStreamHandle;
						var iaStreamHandle = objWnd.params.iaStreamHandle;
						
						if (objWnd.status.playaudioing)
						{	
							P_IF.StopStreamPlay(_conn.nc, iaStreamHandle);
						}
						if (objWnd.status.recording)
						{
							P_IF.StopRecord(_conn.nc, ivStreamHandle);
						}						
						
						if (objWnd.status.isfullscreening)
						{
							P_IF.ExitFullScreen(objWnd.wnd);	
						}
						
						P_IF.StopStreamPlay(_conn.nc, ivStreamHandle);
						
						objWnd.connectId = null;
						objWnd.status.playvideoing = false;
						objWnd.status.playaudioing = false;
						objWnd.status.recording = false;
						objWnd.params.puid = null;
						objWnd.params.idx = 0;
						objWnd.params.ivStreamHandle = "";
						objWnd.params.iaStreamHandle = "";
					}
					
					// - 设置是否重绘背景标志
					P_IF.SetEraseBkgndFlag(objWnd.wnd, 0);
						
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					console.log(fn, "ConnectId noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
			}
			else 
			{

				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e)
		{
			console.log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
	---
	fn : ResizeWindowDimension
	desc : 改变视频窗口插件宽高
	params : 
		- objWnd(P_LY.Struct.WindowStruct) 窗口信息结构
		- width(uint|string|percent) 宽度，数值或字符串或百分比如0 | 50px | 50em | 100%，缺省为0
		- height(uint|string|percent) 高度，数值或字符串或百分比如0 | 50px | 50em | 100%，缺省为0
	remark :
		- 实时视频与点播回放均支持
		- 传的是不带单位的数值，将会在后面增加px后缀设置
	...
	*/
	ResizeWindowDimension : function (objWnd, width, height)
	{
		try
		{
			var fn = "P_LY.ResizeWindowDimension";
			
			if (objWnd && objWnd.wnd) 
			{
				var width = String(width || 0).toLowerCase(),
					height = String(height || 0).toLowerCase();
				
				if (width.search("%") == -1 && width.search("px") == -1 && width.search("em") == -1) {
					width += "px";
				}
				if (height.search("%") == -1 && height.search("px") == -1 && height.search("em") == -1) {
					height += "px";
				}
				
				objWnd.wnd.style.width = width;
				objWnd.wnd.style.height = height;
				
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);	
			}
			else
			{
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);	
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	
	/*
    ---
    fn: PlayAudio 
    desc: 停止播放
    time: 2013.09.06
	mdftime: 2014.02.25
    returns: 
        - P_Error
    params: 
        - objWnd(P_LY.Struct.WindowStruct)        // - 窗口对象
		- customParams(object) 自定义参数
		{
			_HANDLE(HANDLE) 音频资源句柄	
		}
    ... 
    */
	PlayAudio : function (objWnd, customParams) 
	{
		try
		{
			var fn = "P_LY.PlayAudio";
			
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
					
				var _connStruct = P_LY.Connections.get(connectId),
					customParams = customParams || {},
					puid = objWnd.params.puid,
					streamType = objWnd.params.streamType || P_LY.Enum.StreamType.REALTIME,
					resIAIdx = objWnd.params.idx || customParams.idx || 0,
					resIAHandle = customParams._HANDLE || null;
				
				if (objWnd.wnd["NPP_transcodeStreamPlaying"] == true)
				{
					streamType = P_LY.Enum.StreamType.REALTIME;	
				}
				
				if (!puid || !P_LY.puidRex.test(puid)) 
				{
					P_Utils.Log(fn, "puid error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
				}
				if (isNaN(resIAIdx) || !P_LY.intRex.test(resIAIdx)) 
				{
					P_Utils.Log(fn, "idx error");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR);
				}
				
			
				if (objWnd.status && objWnd.status.playvideoing)
				{
					if (objWnd.status.playaudioing)
					{
						var iaStreamHandle = objWnd.params.iaStreamHandle;
						if (iaStreamHandle) {
							P_IF.StopStreamPlay(_connStruct.nc, iaStreamHandle);
						}
						objWnd.params.iaStreamHandle = "";
						objWnd.status.playaudioing = false;
					}
					else
					{
						// - 视频对应的资源句柄
						if (!resIAHandle) 
						{
							var operator = P_LY.NPPSDKCommon.GetHandle
							(
								connectId,
								puid,
								P_LY.Enum.PuResourceType.AudioIn,
								resIAIdx
							);
							
							if (operator.rv == P_Error.SUCCESS)
							{
								resIAHandle = operator.response;
							}
							else
							{
								P_Utils.Log(fn, "get ia handle error~");
								return operator;
							}
						}
						var operator = P_IF.StartStreamPlay
						(
							_connStruct.nc, 
							resIAHandle, 
							streamType, 
							(objWnd.wnd["NPP_tmpWinHandle"] || objWnd.wndHandle)
						);
						if (operator.rv != P_Error.SUCCESS) 
						{
							return new P_LY.Struct.ReturnValueStruct(operator.rv);
						}
						
						objWnd.params.iaStreamHandle = operator.response;
						objWnd.status.playaudioing = true;
					}
						
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else
				{
					P_Utils.Log(fn, "playvideoing false");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}
			}
			else 
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e)
		{
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: 录像回放点播操作
    * ........................................................................................................
    **/
	/*
    ---
    fn: PlayVod 
    desc: 录像回放点播
    returns: 
        - P_Error
    params: 
		- connectId(string) * // - 连接ID
        - objWnd(P_LY.Struct.WindowStruct) * // - 窗口对象（P_LY.Enum.WindowType.VOD类型）
		- options(object) 点播相关参数
		{
			type(P_LY.Enum.StorageFileType) * 存储文件类型
			csuPuid(string) * 中心存储器PUID
			csuIndex(uint) 中心存储单元资源索引，缺省为0
			puid(string) * 点播对象（设备）PUID
			sgIndex(uint) 前端点播时存储器资源索引
			ivIndex(uint) 点播对象（摄像头）资源索引，缺省为0
			fileFullPath(string) 点播的录像文件路径
			speed(uint) 播放速度，缺省0为正常速度
			direction(uint) 播放时间轴方向，1正向（缺省），0倒向
			relativeStartTime(UTC timestamp) 相对播放开始时间（缺省为0秒）
			beginTime(UTC timestamp) 录像开始时间
			endTime(UTC timestamp) 录像结束时间
		}
	remark:
	 	- 点播平台存储录像，type=P_LY.Enum.StorageFileType.Platform时，可选下列参数传入csuPuid、csuIndex、puid、ivIndex、fileFullPath、speed、direction、relativeStartTime
　　		- 点播前端CEFS录像，type=P_LY.Enum.StorageFileType.CEFS时，可选下列参数传入puid、ivIndex、speed、direction、beginTime、endTime
    ... 
	*/
	PlayVod : function(connectId, objWnd, options)
	{
	//	console.log(objWnd)
		try
		{
			var fn = "P_LY.PlayVod";
		
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
			}
			if (!objWnd || !objWnd instanceof P_LY.Struct.WindowStruct || !objWnd.wnd)
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
			var o = options = options || {};
			if (!o.type)
			{
				P_Utils.Log(fn, "type error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
			}
			if (!o.puid || !P_LY.puidRex.test(o.puid))
			{
				
				P_Utils.Log(fn, "puid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
			}

			o.ivIndex = typeof o.ivIndex != "undefined" || !isNaN(o.ivIndex) ? o.ivIndex : 0;
			o.sgIndex = typeof o.sgIndex != "undefined" || !isNaN(o.sgIndex) ? o.sgIndex : 0;
			
			var _connStruct = P_LY.Connections.get(connectId);
			
			o.speed = typeof o.speed != "undefined" || !isNaN(o.speed) ? o.speed : 0;
			o.direction = typeof o.direction != "undefined" || !isNaN(o.direction) ? o.direction : 1; 
			o.relativeStartTime = typeof o.relativeStartTime != "undefined" || !isNaN(o.relativeStartTime) ? o.relativeStartTime : 0;

			switch (o.type)
			{
				case P_LY.Enum.StorageFileType.Platform : 
					if (!o.csuPuid || !P_LY.puidRex.test(o.csuPuid))
					{
						P_Utils.Log(fn, "csuPuid error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);	
					}
					o.csuIndex = typeof o.csuIndex != "undefined" || !isNaN(o.csuIndex) ? o.csuIndex : 0; 
					
					var csuhandle_operator = P_LY.NPPSDKCommon.GetHandle
					(
						connectId,
						o.csuPuid,
						P_LY.Enum.PuResourceType.SC,
						o.csuIndex
					);
					if (csuhandle_operator.rv != P_Error.SUCCESS)
					{
						P_Utils.Log(fn, "get csu handle error~");
						if (csuhandle_operator.rv == P_Error.ERROR)
						{
							csuhandle_operator.rv = P_Error.ERROR_HANDLE_ERROR;	
						}
						return new P_LY.Struct.ReturnValueStruct(csuhandle_operator.rv);	
					}
					
					var isPlayVodByTime = (!o.fileFullPath ? true : false);
					
					if (isPlayVodByTime)
					{
						
						if (!o.beginTime || isNaN(o.beginTime) || !o.endTime || isNaN(o.endTime))
						{
							P_Utils.Log(fn, "fileFullPath | beginTime | endTime error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						
						var ivhandle_operator = P_LY.NPPSDKCommon.GetHandle
						(
							connectId,
							o.puid,
							P_LY.Enum.PuResourceType.VideoIn,
							o.ivIndex
						);
						if (ivhandle_operator.rv != P_Error.SUCCESS)
						{
							P_Utils.Log(fn, "play vod by time -> get iv handle error~");
							if (ivhandle_operator.rv == P_Error.ERROR)
							{
								ivhandle_operator.rv = P_Error.ERROR_HANDLE_ERROR;	
							}
							return new P_LY.Struct.ReturnValueStruct(ivhandle_operator.rv);	
						}
						var operator = P_IF.SC_VODTimePlay
						(
							_connStruct.nc,
							csuhandle_operator.response,
							ivhandle_operator.response,
							o.beginTime,
							o.endTime,
							o.speed,
							o.direction,
							objWnd.wndHandle 	 
						);
					}
					else
					{
						
						var operator = P_IF.SC_VODFilePlay
						(
							_connStruct.nc, 
							csuhandle_operator.response, 
							o.fileFullPath, 
							o.speed, 
							o.direction, 
							o.relativeStartTime, 
							objWnd.wndHandle 	 
						);
					}
					
					if (operator.rv != P_Error.SUCCESS)
					{
						P_Utils.Log(fn, "play vod failed~");
						return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
					}
					
					objWnd.params.csuPuid = o.csuPuid;
					objWnd.params.csuIndex = o.csuIndex;
					
					if (isPlayVodByTime)
					{
						objWnd.params.fileFullPath = o.fileFullPath;
						objWnd.params.relativeStartTime = o.relativeStartTime;
					}
					else
					{
						objWnd.params.beginTime = o.beginTime;
						objWnd.params.endTime = o.endTime;
					}
					break;

				case P_LY.Enum.StorageFileType.Cloud:

					if (!o.szId )
					{
						//console.log("options.szId error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
					}
					
					if (!o.filePath)
					{
						//console.log("options.filePath error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
					}
					if (!o.direction)
					{
						o.direction = 1;
					}
					

					//PlayCloudRecord:function(nc,handle,szId,filePath,direction,startTime,duration,wndHandle)
					
					var _connStruct = P_LY.Connections.get(connectId);
					
					if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
					{
						operator = P_IF.PlayCloudRecord(
							_connStruct.nc,
							_connStruct.session,
							o.szId,
							o.filePath,
							o.direction,
							o.beginTime,
							o.duration,
							objWnd.wndHandle
						);
						
						if (operator.rv != P_Error.SUCCESS)
						{
							P_Utils.Log(fn, "play cefs vod failed~");
							return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
						}
					}
					else
					{
						P_Utils.Log(fn, "hav't connected~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					}

					
					break;
				case P_LY.Enum.StorageFileType.CEFS: 
					
					if (isNaN(o.beginTime))
					{
						P_Utils.Log(fn, "fileFullPath | beginTime | endTime error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					}
					var sghandle_operator = P_LY.NPPSDKCommon.GetHandle
					(
						connectId,
						o.puid,
						P_LY.Enum.PuResourceType.Storager,
						o.sgIndex
					);
					//console.log(sghandle_operator)
				
					if (sghandle_operator.rv != P_Error.SUCCESS)
					{
						P_Utils.Log(fn, "play cefs vod -> get sg handle error~");
						if (sghandle_operator.rv == P_Error.ERROR)
						{
							sghandle_operator.rv = P_Error.ERROR_HANDLE_ERROR;	
						}
						return new P_LY.Struct.ReturnValueStruct(sghandle_operator.rv);	
					}
					
					var operator = P_IF.SG_CEFSVODFilePlay
					(
						_connStruct.nc, 
						sghandle_operator.response,
						o.filePath,
						o.direction,
						o.beginTime, 
						objWnd.wndHandle 	 
					);

					if (operator.rv != P_Error.SUCCESS)
					{
						P_Utils.Log(fn, "play cefs vod failed~");
						return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
					}
				
					objWnd.params.beginTime = o.beginTime;
					objWnd.params.endTime = o.endTime;
					break;
				case P_LY.Enum.StorageFileType.FAT32 : 
					
					break;	
			}
			
			objWnd.connectId = connectId;
			
			objWnd.status.playvoding = true;
			objWnd.status.playaudioing = true; // - 声音默认是开启的
			
			objWnd.params.ivStreamHandle = operator.response;
			objWnd.params.puid = o.puid;
			objWnd.params.idx = o.ivIndex;
			objWnd.params.speed = o.speed;
			objWnd.params.direction = o.direction;
			
			// - 设置是否重绘背景标志
			P_IF.SetEraseBkgndFlag(objWnd.wnd, 1);
			
			return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, operator.response);	
		}
		catch (e) {
			cosole.log(e)
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);	
		}
	},
	// - 停止点播
	PauseVod : function(objWnd)
	{
		try
		{
			
			var fn = "P_LY.PauseVod";
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (connectId && P_LY.Connections.get(connectId)) 
				{
					var _connStruct = P_LY.Connections.get(connectId);
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					if (objWnd.status.playvoding) 
					{
						var ivStreamHandle = objWnd.params.ivStreamHandle;
						P_IF.PauseStreamPlay(_connStruct.nc, ivStreamHandle);						
					}
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					P_Utils.Log(fn, "ConnectId noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
			}
			else 
			{
				P_Utils.Log(fn, "objWnd wnd error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);	
		}
	},	
	// - 停止点播
	ResumeVod : function(objWnd)
	{
		try
		{
			
			var fn = "P_LY.ResumeVod";
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (connectId && P_LY.Connections.get(connectId)) 
				{
					var _connStruct = P_LY.Connections.get(connectId);
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					var ivStreamHandle = objWnd.params.ivStreamHandle;
					
					P_IF.ResumeStreamPlay(_connStruct.nc, ivStreamHandle);
					
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					P_Utils.Log(fn, "ConnectId noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
			}
			else 
			{
				P_Utils.Log(fn, "objWnd wnd error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);	
		}
	},	
	// - 停止点播
	StopVod : function(objWnd)
	{
		try
		{
			
			var fn = "P_LY.StopVod";
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (connectId && P_LY.Connections.get(connectId)) 
				{
					var _connStruct = P_LY.Connections.get(connectId);
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					if (objWnd.status.playvoding) 
					{
						var ivStreamHandle = objWnd.params.ivStreamHandle;

						if (objWnd.status.recording)
						{
							P_IF.StopRecord(_connStruct.nc, ivStreamHandle);
						}						
						
						if (objWnd.status.isfullscreening)
						{
							P_IF.ExitFullScreen(objWnd.wnd);	
						}
						
						P_IF.StopStreamPlay(_connStruct.nc, ivStreamHandle);
						
						objWnd.connectId = null;
						objWnd.status.playvoding = false;
						objWnd.status.playaudioing = false;
						objWnd.status.recording = false;
						objWnd.params.puid = null;
						objWnd.params.idx = 0;
						objWnd.params.ivStreamHandle = "";
					}
					
					// - 设置是否重绘背景标志
					P_IF.SetEraseBkgndFlag(objWnd.wnd, 0);
						
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					P_Utils.Log(fn, "ConnectId noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
			}
			else 
			{
				P_Utils.Log(fn, "objWnd wnd error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);	
		}
	},	
	// - 播放或停止点播声音
	PlayVodAudio : function (objWnd)
	{
	//	console.log(11)
		try
		{
			var fn = "P_LY.PlayVodAudio";
			
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (connectId && P_LY.Connections.get(connectId)) 
				{
					var _connStruct = P_LY.Connections.get(connectId);
					if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
					{
						P_Utils.Log(fn, "connect havn't success~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					if (objWnd.status.playvoding) 
					{
						var operator;
						if (!objWnd.status.playaudioing)
						{
							operator = P_IF.OpenAudio(_connStruct.nc, objWnd.params.ivStreamHandle);
							if (operator.rv != P_Error.SUCCESS)
							{
								P_Utils.Log(fn, "open vod audio failed~");
								return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
							}
							objWnd.status.playaudioing = true;
						}
						else
						{
							operator = P_IF.CloseAudio(_connStruct.nc, objWnd.params.ivStreamHandle);
							
							if (operator.rv != P_Error.SUCCESS)
							{
								P_Utils.Log(fn, "close vod audio failed~");
								return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED));	
							}
							objWnd.status.playaudioing = false;
						}
					}
					
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					P_Utils.Log(fn, "ConnectId noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
			}
			else 
			{
				P_Utils.Log(fn, "objWnd wnd error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: 录像回放点播操作
    * ........................................................................................................
    **/	
	/*
    ---
    fn: LocalRecord 
    desc: 本地录像
    time: 2013.09.25
	mdftime: 2013.11.26
    returns: 
        - P_Error
    params: 
        - objWnd(P_LY.Struct.WindowStruct) * 窗口对象
		- savePath(String) 存储路径目录，缺省为C:/
		- fileName(String) 本地存储文件名，缺省格式为yyyyMMdd_HHmmss.avi
    ... 
    */
	LocalRecord : function (objWnd, savePath, fileName) 
	{
		try 
		{
			var fn = "P_LY.LocalRecord";
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				var _connStruct = P_LY.Connections.get(connectId);
				if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected)
				{
					P_Utils.Log(fn, "connection hav't build error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				
				var savePath = savePath || "C:/";
				var fileName = fileName || (P_Utils.DateFormat("yyyy#MM#dd#_#HH#mm#ss").replace(/#/g, "") + ".avi");
				var saveAsFilePath = savePath + "/" + fileName;
				
				if (objWnd.status.playvideoing || objWnd.status.playvoding) 
				{
					var ivStreamHandle = objWnd.params.ivStreamHandle;
					if (!objWnd.status.recording) 
					{
						var operator = P_IF.StartRecord(_connStruct.nc, ivStreamHandle, saveAsFilePath);
					
						if (operator.rv == P_Error.SUCCESS) 
						{
							objWnd.status.recording = true;
						}
						else 
						{
							objWnd.status.recording = false;
						}
						return operator;
					}
					else 
					{
						P_IF.StopRecord(_connStruct.nc, ivStreamHandle);
						objWnd.status.recording = false;
					}
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					P_Utils.Log(fn, "window no play error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}
			}
			else 
			{
				P_Utils.Log(fn, "window noexist error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: Snapshot 
    desc: 本地抓图
    time: 2013.09.25
	mdftime: 2013.11.26
    returns: 
        - P_Error
    params: 
        - objWnd(P_LY.Struct.WindowStruct) * 窗口对象
		- savePath(String) 存储路径目录，缺省为C:/
		- fileName(String) 本地存储文件名，缺省格式为yyyyMMdd_HHmmss_l.bmp
    ... 
    */
	Snapshot : function(objWnd, savePath, fileName)
	{
		try 
		{
			var fn = "P_LY.Snapshot";
			if (objWnd && objWnd.wnd) 
			{
				var connectId = objWnd.connectId;
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				var _connStruct = P_LY.Connections.get(connectId);
				if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected)
				{
					P_Utils.Log(fn, "connection hav't build error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				var savePath = savePath || "C:/";
				var fileName = fileName || (P_Utils.DateFormat("yyyy#MM#dd#_#HH#mm#ss#_#l").replace(/#/g, "")+ ".bmp");
				var saveAsFilePath = savePath + "/" + fileName;

				if (objWnd.status.playvideoing || objWnd.status.playvoding) 
				{
					var ivStreamHandle = objWnd.params.ivStreamHandle;
					return P_IF.Snapshot(_connStruct.nc, ivStreamHandle, saveAsFilePath);
				}
				else 
				{
					P_Utils.Log(fn, "window no play error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}
			}
			else 
			{
				P_Utils.Log(fn, "window noexist error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: FullScreen 
    desc: 全屏 
    returns: 
        - P_Error
    params: 
        - objWnd (P_LY.Struct.WindowStruct)        // - 窗口对象
    ... 
    */
	FullScreen : function(objWnd)
	{
		try
		{
			var fn = "P_LY.FullScreen";
			if (objWnd && objWnd.wnd) 
			{
				if (objWnd.status.playvideoing) 
				{
					P_IF.FullScreen(objWnd.wnd);
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}
			}
			else 
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch(e)
		{
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);

		}
	},
	/*
    ---
    fn: ExitFullScreen 
    desc: 退出全屏  
    time: 2013.11.05 
    returns: 
        - P_Error
    params: 
        - objWnd(P_LY.Struct.WindowStruct) // - 窗口对象
    ... 
    */
	ExitFullScreen : function(objWnd)
	{
		try
		{
			var fn = "P_LY.ExitFullScreen";
			if (objWnd && objWnd.wnd) 
			{
				if (objWnd.status.playvideoing) 
				{
					P_IF.ExitFullScreen(objWnd.wnd);
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}
			}
			else 
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);
			}
		}
		catch(e)
		{
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);

		}
	},
	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: DDraw Mode Control
    * ........................................................................................................
    **/
	/*
	---
	fn : __Sdk_DDraw
	time : 2013.10.11
	...
	*/
	__Sdk_DDraw :
	{
		get : function(objWnd)
		{
			try
			{
				var fn = "P_LY.__Sdk_DDraw.get";
				if (objWnd && objWnd.wnd)
				{
					if (objWnd.status && objWnd.status.playvideoing) 
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, !!(objWnd.status.isddrawing == true));
					}
					else 
					{
						P_Utils.Log(fn, "playvideoing false!");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
					}
				}
				else
				{
					P_Utils.Log(fn, "window noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);	
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		set : function(objWnd, bValue)
		{
			try
			{
				var fn = "P_LY.__Sdk_DDraw.set";
				
				if (objWnd && objWnd.wnd)
				{
					if (objWnd.status && (objWnd.status.playvideoing || objWnd.status.playvoding)) 
					{
						var connectId = objWnd.connectId;
						if (!connectId || !P_LY.Connections.get(connectId))
						{
							P_Utils.Log(fn, "connectId error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
						}
						var _connStruct = P_LY.Connections.get(connectId);
						
						var operator = P_IF.EnableDDraw(_connStruct.nc, objWnd.params.ivStreamHandle, (bValue != 0 ? 1 : 0));
						if (operator.rv == P_Error.SUCCESS)
						{
							objWnd.status.isddrawing = bValue != 0 ? true : false;
						}
						else
						{
							objWnd.status.isddrawing = false;	
						}
						return operator;
					}
					else 
					{
						P_Utils.Log(fn, "playvideoing false!");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
					}					
				}
				else
				{
					P_Utils.Log(fn, "window noexist!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);	
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		}
	},
	IsDDrawing : function(objWnd)
	{
		return P_LY.__Sdk_DDraw.get(objWnd);
	},
	EnableDDraw : function(objWnd, bValue)
	{
		return P_LY.__Sdk_DDraw.set(objWnd, bValue);
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: DDraw Mode Control
    * ........................................................................................................
    **/
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: 窗口叠加字符控制
    * ........................................................................................................
    **/
	/*
	---
	fn : EnableAddText
	desc : 获取或设置是否使能窗口字符叠加
	params :
		- objWnd(P_LY.Struct.WindowStruct) * 窗口结构
		- bValue(bool|uint) true|1使能，false|0不使能，省略bValue或bValue=null即获取是否使能叠加
	...
	*/
	EnableAddText : function(objWnd, bValue)
	{
		try
		{
			var fn = "P_LY.EnableAddText";
			
			if (objWnd && objWnd.wnd)
			{
				if (objWnd.status && objWnd.status.playvideoing) 
				{
					var connectId = objWnd.connectId;
					if (!connectId || !P_LY.Connections.get(connectId))
					{
						P_Utils.Log(fn, "connectId error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					var _connStruct = P_LY.Connections.get(connectId);
					
					if (typeof bValue == "undefined" || bValue == null)
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, objWnd.wnd["NPP_b_ENABLEADDTEXT"] || 0);
					}
					else
					{
						var bValue = (bValue != 0 ? 1 : 0);
						
						var operator = P_IF.EnableAddText
						(
						 	_connStruct.nc, 
							objWnd.params.ivStreamHandle, 
							bValue
						);
						if (operator.rv == P_Error.SUCCESS)
						{
							objWnd.wnd["NPP_b_ENABLEADDTEXT"] = bValue;
						}
						return operator;
					}
				}
				else 
				{
					P_Utils.Log(fn, "playvideoing false!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}					
			}
			else
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);	
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: SetTextAdd
    desc: 向视频窗口叠加字符
    params:
    	- objWnd(P_LY.Struct.WindowStruct) 窗口信息对象 
   	 	- textSets([object|array](P_LY.Struct.WindowTextAddStruct)) 叠加字符信息参数  
    remark: 
    	- 只当前观看PC受影响
    	- 当只一个设置节点时，不成功返回非0，多于一个节点时，任一个成功设置就返回0，全部不成功返回非0
    ...
    */
	SetTextAdd : function(objWnd, textSets)
	{
		try
		{
			var fn = "P_LY.SetTextAdd";
			
			if (objWnd && objWnd.wnd)
			{
				if (objWnd.status && objWnd.status.playvideoing) 
				{
					var connectId = objWnd.connectId;
					if (!connectId || !P_LY.Connections.get(connectId))
					{
						P_Utils.Log(fn, "connectId error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					var _connStruct = P_LY.Connections.get(connectId);
					
					var textSets = textSets || {},
						anySuccess = false;
					
					if (typeof textSets != "object")
					{
						P_Utils.Log(fn, "textSets typeof error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					}
					if (textSets.constructor != Array) {
						textSets = [textSets];
					}
					
					// - 把叠加的区块编号记录下来
					objWnd.wnd["NPP_winTextAddNoStore"] = objWnd.wnd["NPP_winTextAddNoStore"] || {};
								
					for (var i = 0; i < textSets.length; i++)
					{
						var textSet = textSets[i];
						if (textSet instanceof P_LY.Struct.WindowTextAddStruct)
						{
							var wndBlockNo = textSet.wndBlockNo || 0,
								left = textSet.left || 0,
								top = textSet.top || 0,
								width = textSet.width || 0,
								height = textSet.height || 0,
								color = textSet.color || 0,
								enableTilt = textSet.enableTilt || 0,
								enableUnderline = textSet.enableUnderline || 0,
								fontFamily = textSet.fontFamily || "",
								content = textSet.content || "";
	 						
							if (P_Utils.GetStringRealLength(fontFamily) > 64)
							{
								P_Utils.Log(fn, "error fontFamily index of " + i);
								continue;
							}
							if (P_Utils.GetStringRealLength(content) > 128)
							{
								P_Utils.Log(fn, "error content index of " + i);
								continue;
							}
	
							var operator = P_IF.SetTextAdd
							(
								_connStruct.nc,
								objWnd.params.ivStreamHandle,
								wndBlockNo, left, top, width, height, color, enableTilt, enableUnderline, fontFamily, content
							);
							 
							if (operator.rv != P_Error.SUCCESS)
							{
								objWnd.wnd["NPP_winTextAddNoStore"][wndBlockNo] = false;
								
								P_Utils.Log(fn, "error SetTextAdd failed index of " + i);
								continue;
							}
							else
							{
								objWnd.wnd["NPP_winTextAddNoStore"][wndBlockNo] = true;
								
								if (!anySuccess) anySuccess = true;
							}
						}
					}
					
					return new P_LY.Struct.ReturnValueStruct
					(
					 	( anySuccess ? P_Error.SUCCESS : P_Error.ERROR )
					);
				}
				else 
				{
					P_Utils.Log(fn, "playvideoing false!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}					
			}
			else
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);	
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
	---
	fn : DeleteTextAdd
	desc : 删除叠加的文字信息
	params :
		- objWnd(P_LY.Struct.WindowStruct) * 窗口结构
		- wndBlockNo(uint) 叠加的编号，省略时删除所有的信息
	...
	*/
	DeleteTextAdd : function (objWnd, wndBlockNo)
	{
		try
		{
			var fn = "P_LY.DeleteTextAdd";
			
			if (objWnd && objWnd.wnd)
			{
				if (objWnd.status && objWnd.status.playvideoing) 
				{
					var connectId = objWnd.connectId;
					if (!connectId || !P_LY.Connections.get(connectId))
					{
						P_Utils.Log(fn, "connectId error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
					}
					var _connStruct = P_LY.Connections.get(connectId);
					
					// - 把叠加的区块编号记录下来
					objWnd.wnd["NPP_winTextAddNoStore"] = objWnd.wnd["NPP_winTextAddNoStore"] || {};
					
					var _delAll = false;
					if (typeof wndBlockNo == "undefined" || wndBlockNo == null || isNaN(wndBlockNo))
					{
						_delAll = true;
					}
						
					for (var i in objWnd.wnd["NPP_winTextAddNoStore"])
					{
						if (wndBlockNo == i || _delAll == true)
						{
							P_IF.DeleteTextAdd(_connStruct.nc, objWnd.params.ivStreamHandle, i);
							objWnd.wnd["NPP_winTextAddNoStore"][i] = false;
						}
					}
					
					if (_delAll == true)
					{
						objWnd.wnd["NPP_winTextAddNoStore"] = {};
					}
					else
					{
						if (typeof objWnd.wnd["NPP_winTextAddNoStore"][wndBlockNo] != "undefined")
						{
							delete objWnd.wnd["NPP_winTextAddNoStore"][wndBlockNo];	
						}
					}
						
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				else 
				{
					P_Utils.Log(fn, "playvideoing false!");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOPLAY);
				}					
			}
			else
			{
				P_Utils.Log(fn, "window noexist!");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_WINDOW_NOTEXIST);	
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: 窗口叠加字符控制
    * ........................................................................................................
    **/
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: GPS Modeless
    * ........................................................................................................
    **/
	/*
	---
	remark:
		- customParams._HANDLE(string)GPS资源句柄存在，那么不会再根据puid、gpsIndex获取
	...
	*/
	__Sdk_GPSStreamCtl : function (action, connectId, puid, gpsIndex, customParams)
	{
		try
		{
			var fn = "P_LY.__Sdk_GPSStreamCtl -> " + action;
			
			var action = action != "Stop" ? "Start" : "Stop"; // - Start | Stop
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var customParams = customParams || {};
			var _gpsHandle = null;
			if (typeof customParams._HANDLE != "undefined")
			{
				_gpsHandle = customParams._HANDLE;
			}
			else 
			{
				if (!puid || !P_LY.puidRex.test(puid))
				{
					P_Utils.Log(fn, "puid error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
				}
				if (typeof gpsIndex == "undefined" || isNaN(gpsIndex) || !P_LY.intRex.test(gpsIndex))
				{
					P_Utils.Log(fn, "gpsIndex error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR);
				}
				var handle_operator = P_LY.NPPSDKCommon.GetHandle
				(
					connectId,
					puid,
					P_LY.Enum.PuResourceType.GPS,
					gpsIndex
				);
				if (handle_operator.rv != P_Error.SUCCESS)
				{
					P_Utils.Log(fn, "handle_operator error~");
					return handle_operator;	
				}
				_gpsHandle = handle_operator.response;
			}	
			var operator = P_IF[(action + "GPSStream")]
			(
				_connStruct.nc,
				_gpsHandle
			);
			
			if (operator.rv != P_Error.SUCCESS)
			{
				P_Utils.Log(fn, action + " gps failed~");
				return new P_LY.Struct.ReturnValueStruct((P_LY.Debug.debug ? operator.rv : P_Error.FAILED), (_gpsHandle || null));	
			}
			return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS, (_gpsHandle || null));
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	// - 开启（注册）GPS信号接收通道
	StartGPSStream : function(connectId, puid, gpsIndex, customParams)
	{
		return P_LY.__Sdk_GPSStreamCtl("Start", connectId, puid, gpsIndex, customParams);
	},
	// - 停止（卸载）GPS信号接收通道
	StopGPSStream : function(connectId, puid, gpsIndex, customParams)
	{
		return P_LY.__Sdk_GPSStreamCtl("Stop", connectId, puid, gpsIndex, customParams);
	},
	
	/*
	---
	desc: GPS数据
	time: 2013.11.28
	...
	*/
	GPSData :
	{
		// - GPS数据存储
		GetGPSResFromCSU : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("getgpsres", connectId, options);
		},
		AddGPSResToCSU : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("addgpsres", connectId, options);
		},
		RemoveGPSResFromCSU : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("removegpsres", connectId, options);
		},
		
		// - GPS数据查询
		QueryLastGPSDatas : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("querylast", connectId, options);
		},		
		QueryHistoryGPSDatas : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("queryhistory", connectId, options);
		},
		QueryGPSDates : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("dates", connectId, options);
		},
		QueryGPSDateFiles : function (connectId, options)
		{
			return this.__Sdk_GPSDataControl("datefiles", connectId, options);
		},
		
		// - SDK内部GPS数据控制接口
		__Sdk_GPSDataControl : function (action, connectId, options)
		{
			try
			{
				var fn = "P_LY.GPSData.__Sdk_GPSDataControl -> " + action;
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
            		return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				var _connStruct = P_LY.Connections.get(connectId);
               	
               	if (!_connStruct.session || _connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
				{
					P_Utils.Log(fn, "login or session error~");
					if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
					}
					else
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
					}		
				}
				
				var o = options = options || {};
				if (!o.csuPuid || !P_LY.puidRex.test(o.csuPuid))
				{
					P_Utils.Log(fn, "csuPuid error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);
				}
				o.csuIndex = typeof o.csuIndex != "undefined" || !isNaN(o.csuIndex) ? o.csuIndex : 0;
				o.offset = typeof o.offset != "undefined" || !isNaN(o.offset) ? o.offset : 0;
				o.count = typeof o.count != "undefined" || !isNaN(o.count) ? o.count : 200;
				o.domainRoad = o.domainRoad || ""; 
				
				var operator;
				
				switch (action.toLowerCase())
				{
					case "getgpsres":
						operator = P_LY.Control.CommonGet
						(
							connectId,
							{
								puid : o.csuPuid,
								resType : P_LY.Enum.PuResourceType.SC,
								resIdx : o.csuIndex,
								domainRoad : o.domainRoad,
								controlID : "C_SC_GetGPSRes",
								param : '<Param Offset="'+o.offset+'" Cnt="'+o.count+'" />'
							}
						);
						break;
					case "addgpsres":
					case "removegpsres":
						// - 当没有o.puid参数时，就会检测o.resourceSets是否存在，o.resourceSets存在，就可以一次添加或删除多条资源信息
						if (!o.puid || !P_LY.puidRex.test(o.puid))
						{
							if (o.resourceSets == null || typeof o.resourceSets != "object")
							{
								P_Utils.Log(fn, "puid & resourceSets error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
							}
							else
							{
								if (typeof o.resourceSets == "object" && o.resourceSets.constructor != Array)
								{
									o.resourceSets = [o.resourceSets];	
								}
							}
						}
						else
						{
							// - 当o.puid存在，那么每次最多只添加或删除一条资源信息 
							if (!o.resType)
							{
								P_Utils.Log(fn, "resType error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
							}
							o.resIdx = typeof o.resIdx != "undefined" || !isNaN(o.resIdx) ? o.resIdx : 0;
							
							o.resourceSets = [new P_LY.Struct.GPSDataStorageResourceStruct(o.puid, o.resType, o.resIdx, o.optID, o.value)];
						}
						
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var xmlObj = new XML.ObjTree();
							xmlObj.xmlDecl = "";
							xmlObj.attr_prefix = "-";
							
							var ResArr = [];
							
							for (var i = 0; i < o.resourceSets.length; i++)
							{
								var item = o.resourceSets[i]; 
								
								if (typeof item.puid != "undefined")
								{
									ResArr.push
									(
									 	{
											"-ObjType" : 151,
											"-ObjID" : item.puid,
											"-Type" : item.resType,
											"-Idx" : item.resIdx,
											"-OptID" : (item.optID || "C_SCHEDULER_StartStream"),
											Param :
											{
												"-Value" : (item.value || P_LY.Enum.StreamType.REALTIME)
											}
										} 
									); 
								}
							}
							
							var objSetsJSON =
							{
								ObjSets : 
								{
									Res : (ResArr || [])
								}
							};
							
							operator = P_LY.Control.CommonGet
							(
								connectId,
								{
									puid : o.csuPuid,
									resType : P_LY.Enum.PuResourceType.SC,
									resIdx : o.csuIndex,
									domainRoad : o.domainRoad,
									controlID : (action.toLowerCase() == "addgpsres" ? "C_SC_AddGPSRes" : "C_SC_RemoveGPSRes"),
									param : '<Param SerialNo="'+(o.serialNo||_connStruct.connParam.username)+'" SerialToken="'+MD5.Hex_MD5(o.serialToken||_connStruct.connParam.password)+'" />',
									xmlObjSets : xmlObj.writeXML(objSetsJSON)
								}
							); 
						}
						else
						{
							P_Utils.Log(fn, "XML.ObjTree error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						break;
					
					case "querylast":
					case "queryhistory":
						if (!o.puid || !P_LY.puidRex.test(o.puid))
						{
							if (o.resourceSets == null || typeof o.resourceSets != "object")
							{
								P_Utils.Log(fn, "puid & resourceSets error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
							}
							else
							{
								if (typeof o.resourceSets == "object" && o.resourceSets.constructor != Array)
								{
									o.resourceSets = [o.resourceSets];	
								}
							}
						}
						else
						{
							o.resourceSets = [new P_LY.Struct.GPSDataStorageResourceStruct(o.puid, o.resType, o.resIdx, null, null)];
						}
						
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var xmlObj = new XML.ObjTree();
							xmlObj.xmlDecl = "";
							xmlObj.attr_prefix = "-";
							
							var ResArr = [];
							
							for (var i = 0; i < o.resourceSets.length; i++)
							{
								var item = o.resourceSets[i]; 
								
								if (typeof item.puid != "undefined")
								{
									ResArr.push
									(
									 	{
											"-ObjType" : 151,
											"-ObjID" : item.puid,
											"-Type" : (item.resType || P_LY.Enum.PuResourceType.GPS),
											"-Idx" : item.resIdx
										} 
									); 
								}
							}
							
							var paramJSON = 
							{
								Param : ""	
							};
							if (action == "queryhistory")
							{
								paramJSON = 
								{
									Param :
									{
										"-Offset": o.offset,
										"-Cnt": o.count,
										"-BeginTime": o.beginTime || 0,
										"-EndTime": (o.endTime || Math.ceil(new Date().getTime()/1000)) 
									}
								};	
							}
							
							var objSetsJSON =
							{
								ObjSets : 
								{
									Res : (ResArr || [])
								}
							};
							
							operator = P_LY.Control.CommonGet
							(
								connectId,
								{
									puid : o.csuPuid,
									resType : P_LY.Enum.PuResourceType.SC,
									resIdx : o.csuIndex,
									domainRoad : o.domainRoad,
									controlID : (action.toLowerCase() == "queryhistory" ? "C_SC_QueryHistoryGPSData" : "C_SC_QueryLastGPSData"),
									param : xmlObj.writeXML(paramJSON),
									xmlObjSets : xmlObj.writeXML(objSetsJSON)
								}
							); 
						}
						else
						{
							P_Utils.Log(fn, "XML.ObjTree error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						break;
						
					case "dates":
					case "datefiles":
						if (!o.puid || !P_LY.puidRex.test(o.puid))
						{
							P_Utils.Log(fn, "puid error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
						}
						o.resType = o.resType || P_LY.Enum.PuResourceType.GPS;
						o.resIdx = typeof o.resIdx != "undefined" || !isNaN(o.resIdx) ? o.resIdx : 0;
						
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var xmlObj = new XML.ObjTree();
							xmlObj.xmlDecl = "";
							xmlObj.attr_prefix = "-";
							
							var ResArr = [];
							
							var paramJSON = 
							{
								Param :
								{
									"-Offset": o.offset,
									"-Cnt": o.count
								}
							};
							if (action == "datefiles")
							{
								paramJSON.Param["-Date"] = o.date || 0;
							}
							var objSetsJSON =
							{
								ObjSets : 

								{
									Res :
									{
										"-ObjType" : 151,
										"-ObjID" : o.puid,
										"-Type" : o.resType,
										"-Idx" : o.resIdx
									} 
								}
							};
							
							operator = P_LY.Control.CommonGet
							(
								connectId,
								{
									puid : o.csuPuid,
									resType : P_LY.Enum.PuResourceType.SC,
									resIdx : o.csuIndex,
									domainRoad : o.domainRoad,
									controlID : (action == "datefiles" ? "C_SC_QueryDateGPSData" : "C_SC_QueryGPSDate"),
									param : xmlObj.writeXML(paramJSON),
									xmlObjSets : xmlObj.writeXML(objSetsJSON)
								}
							); 
						}
						else
						{
							P_Utils.Log(fn, "XML.ObjTree error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						break;
				}
				
				if (operator.rv == P_Error.SUCCESS)
				{
					var _response = operator.response || {};
					
					if (_response != null && typeof _response != "undefined")
					{
						switch (action.toLowerCase())
						{
							case "getgpsres":
								var _resArr = [];
								if (typeof _response.Res != "undefined")
								{
									if (_response.Res.constructor != Array)
									{
										_response.Res = [_response.Res];	
									}
									for (var i = 0; i < _response.Res.length; i++)
									{
										_resArr.push
										(
											new P_LY.Struct.GPSDataStorageResourceStruct
											(
												_response.Res[i].ObjID,
												_response.Res[i].Type,
												_response.Res[i].Idx,
												_response.Res[i].OptID,
												(_response.Res[i].Param.Value || P_LY.Enum.StreamType.REALTIME)
											)
										);
									}
								}
								_response = _resArr;
								break;
							case "addgpsres":
							case "removegpsres":
								break;
								
							case "querylast":
							case "queryhistory":
							case "datefiles":
								var _resArr = [];
								if (typeof _response.Res != "undefined")
								{
									if (_response.Res.constructor != Array)
									{
										_response.Res = [_response.Res];	
									}
									for (var i = 0; i < _response.Res.length; i++)
									{
										var GPS = _response.Res[i].GPS,
											ObjID = _response.Res[i].ObjID,
											Type = _response.Res[i].Type,
											Idx = _response.Res[i].Idx;
										
										if (typeof GPS != "undefined")
										{
											if (typeof GPS == "object" && GPS.constructor != Array)
											{
												GPS = [GPS];	
											}
											
											for (var j = 0; j < GPS.length; j++)
											{
												_resArr.push
												(
													new P_LY.Struct.CSUGPSDataFileStruct
													(
														GPS[j].UTC,
														GPS[j].Latitude,
														GPS[j].Longitude,
														GPS[j].Bearing,
														GPS[j].Speed,
														GPS[j].Altitude,
														GPS[j].State,
														GPS[j].MaxSpeed,
														GPS[j].MinSpeed,
														GPS[j].Timestamp,
														ObjID,
														Type,
														Idx
													)
												);	
											}
										}
									}
								}
								_response = _resArr;
								break;
							
							case "dates":
								var _dateArr = [];
								if (typeof _response.Res != "undefined" && typeof _response.Res.Date != "undefined")
								{
									if (_response.Res.Date.constructor != Array)
									{
										_response.Res.Date = [_response.Res.Date];	
									}
									_dateArr = _response.Res.Date || [];
								}
								_response = _dateArr;
								break;
						}
					}
					
					operator.response = _response;
				}
				
				return operator;
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}	
		},
		
		end : true	
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: GPS Modeless
    * ........................................................................................................
    **/	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Call Talk Ctl
    * ........................................................................................................
    **/
	CallTalkControl : 
	{
		// - 内部存储喊话对讲信息
		__Sdk_OAStore : new P_Utils.Hash(),
		// - 内部喊话对讲公共函数
		__Sdk_CallTalkCtl : function (action, connectId, puid, oaIndex, customParams)
		{
			try
			{
				var fn = "P_LY.CallTalkControl.__Sdk_CallTalkCtl -> " + action;
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
            		return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				var _connStruct = P_LY.Connections.get(connectId);
               	
               	if (!_connStruct.session || _connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
				{
					P_Utils.Log(fn, "login or session error~");
					if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
					}
					else
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
					}		
				}
				var _oastore = P_LY.CallTalkControl.__Sdk_OAStore = P_LY.CallTalkControl.__Sdk_OAStore || new P_Utils.Hash();
				// - 清空喊话或对讲信息
				if (action == "clear")
				{ 
					if (_oastore.get(connectId))
					{
						var _oakey = puid + "_" + oaIndex;
						
						_oastore.get(connectId).each
						(
							function (item)
							{
								if (item.key == _oakey)
								{
									if ((item.value.call || item.value.talk) && item.value.oaStreamHandle)
									{
										P_IF.StopStreamPlay(_connStruct.nc, item.value.oaStreamHandle);
										item.value.oaStreamHandle = "";
										item.value.call = false;
										item.value.talk = false;
									}
									return true;
								}
							} 
						);
						
						_oastore.unset(connectId);
					}
						
					return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
				}
				
				if (!puid || !P_LY.puidRex.test(puid)) 
				{
					P_Utils.Log(fn, "puid error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
				}
				if (isNaN(oaIndex) || !P_LY.intRex.test(oaIndex)) 
				{
					P_Utils.Log(fn, "oaIndex error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_INDEX_ERROR);
				}
				
				var customParams = customParams || {};
				
				switch (action)
				{
					case "call":
					case "talk":						
						// - 检查喊话或对讲状态
						var _status_operator = P_LY.CallTalkControl.__Sdk_CallTalkCtl("status", connectId, puid, oaIndex);
					
						if (_status_operator.rv == P_Error.SUCCESS)
						{
							var _nodeOAItem = _status_operator.response;
							if (_nodeOAItem != null && typeof _nodeOAItem != "undefined" && typeof _nodeOAItem.puid != "undefined" && _nodeOAItem.puid == puid && _nodeOAItem.oaIndex == oaIndex)
							{
								var _isPrevent = _nodeOAItem[(action=="call"?"talk":"call")];
								if (_isPrevent)
								{
									// - 喊话或对讲需要互斥操作
									P_Utils.Log(fn, "call or talk should incompatible~");
									return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CALLTALK_INCOMPATIBLE);	
								}
								else
								{
									// - 已经有喊话或对讲了
									var _isExist = _nodeOAItem[(action=="call"?"call":"talk")];
									if (_isExist)
									{
										P_Utils.Log(fn, "call or talk has existed~");
										return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CALLTALK_EXISTED);	
									}
								}
							}
						}
						
						var _oaHandle = null;
						if (customParams._HANDLE) {
							_oaHandle = customParams._HANDLE;
						}
						else {
							var operator = P_LY.NPPSDKCommon.GetHandle
							(
								connectId,
								puid,
								P_LY.Enum.PuResourceType.AudioOut,
								oaIndex
							);
							
							if (operator.rv == P_Error.SUCCESS)
							{
								_oaHandle = operator.response;
							}
							else
							{
								P_Utils.Log(fn, "get oa handle error~");
								return operator;
							}
						}
						
						// - 检查一下是否支持喊话或对讲资源
						var _support_operator = P_LY.Config.GetSimple
						(
							 connectId, 
							 puid, 
							 P_LY.Enum.PuResourceType.AudioOut, 
							 oaIndex, 
							 "F_OA_Status"
						);
						
						if (_support_operator.rv == P_Error.SUCCESS)
						{
							//console.log(_support_operator.response.M.C.Res.Param);
							//console.log(_support_operator.response.M.C.Res.Param.Value);
							if (_support_operator.response.M && _support_operator.response.M.C && _support_operator.response.M.Res && _support_operator.response.M.C.Res.Param.Value != "Free")
							{
								P_Utils.Log(fn, "oa res has occupied~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CALLTALK_OCCUPIED);		
							}
						}
						else
						{
							if (_support_operator.rv == P_Error.ERROR_PU_OFFLINE)
							{
								return _support_operator;
							}	
						}
						
						// - 开启喊话或对讲 
						var operator = P_IF[(action=="call"?"StartCallPlay":"StartTalkPlay")](_connStruct.nc, _oaHandle);
					
						if (operator.rv != P_Error.SUCCESS)
						{
							P_Utils.Log(fn, "start call or talk failed~");
							return operator;	
						}
						
						if (!_oastore.get(connectId))
						{
							_oastore.set(connectId, new P_Utils.Hash());
						}						
						if (_oastore.get(connectId))
						{
							var _oakey = puid + "_" + oaIndex;
							
							if (!_oastore.get(connectId).get(_oakey))
							{
								_oastore.get(connectId).set
								(
								 	_oakey,
									{
										puid: puid,
										oaIndex: oaIndex,
										oaStreamHandle: "",
										call: false,
										talk: false
									}
								); 
							}
							if (_oastore.get(connectId).get(_oakey))
							{
								// - 记录喊话或对讲流
								_oastore.get(connectId).get(_oakey)[(action=="call"?"call":"talk")] = true;
								_oastore.get(connectId).get(_oakey).oaStreamHandle = operator.response;
							}
						}
						P_Utils.Log(fn, "start call or talk success~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
						break;
						
					case "status": // - 获取喊话或对讲状态
						var isExist = false;
						if (_oastore.get(connectId))
						{
							var _oakey = puid + "_" + oaIndex;
							
							if (_oastore.get(connectId).get(_oakey))
							{
								isExist = true;	
								return new P_LY.Struct.ReturnValueStruct
								(
								 	P_Error.SUCCESS, 
									_oastore.get(connectId).get(_oakey)
								);
							}
						}
						if (isExist == false)
						{
							P_Utils.Log(fn, "get call or talk status failed~");
							
							return new P_LY.Struct.ReturnValueStruct
							(
							 	P_Error.SUCCESS, 
								{
									puid: puid, 
									oaIndex: oaIndex, 
									call: false, 
									talk: false, 
									oaStreamHandle: ""
								}
							);
						}
						break;
						
					case "stop": // - 停止喊话或对讲
						if (_oastore.get(connectId))
						{
							var _oakey = puid + "_" + oaIndex;

							_oastore.get(connectId).each
							(
								function (item)
								{
									if (item.key == _oakey)
									{
										if ((item.value.call || item.value.talk) && item.value.oaStreamHandle)
										{
											
											P_IF.StopStreamPlay(_connStruct.nc, item.value.oaStreamHandle);
											item.value.oaStreamHandle = "";
											item.value.call = false;
											item.value.talk = false;
										}
										return true;
									}
								} 
							);
						}
						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
						break;
					default:
						P_Utils.Log(fn, "unknown error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.UNDEFINE);
						break;
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD); 
			}
		},
		// - 清除对讲信息
		Clear : function (connectId)
		{
			return this.__Sdk_CallTalkCtl("clear", connectId);
		},
		
		// - 开启喊话
		StartCall : function (connectId, puid, oaIndex, customParams)
		{
			return this.__Sdk_CallTalkCtl("call", connectId, puid, oaIndex, customParams);
		},
		// - 开启对讲
		StartTalk : function (connectId, puid, oaIndex, customParams)
		{
			return this.__Sdk_CallTalkCtl("talk", connectId, puid, oaIndex, customParams);
		},
		// - 获取设备的喊话或对讲状态
		GetStatus : function (connectId, puid, oaIndex)
		{
			return this.__Sdk_CallTalkCtl("status", connectId, puid, oaIndex);
		},
		// - 停止喊话或对讲
		Stop : function (connectId, puid, oaIndex, customParams)
		{
			return this.__Sdk_CallTalkCtl("stop", connectId, puid, oaIndex, customParams);
		},
		
		end : true
	},	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Call Talk Ctl
    * ........................................................................................................
    **/
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Planform Storage Plan Mgr
    * ........................................................................................................
    **/
	PlatformStorageControl :
	{
		// - 内部处理函数
		__Sdk_PlatfromStorageCtl : function (action, connectId, options)
		{
			try
			{
				var fn = "P_LY.PlatformStorageControl.__Sdk_PlatfromStorageCtl -> " + action;
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
            		return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				var _connStruct = P_LY.Connections.get(connectId);
               	
               	if (!_connStruct.session || _connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
				{
					P_Utils.Log(fn, "login or session error~");
					if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
					}
					else
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
					}		
				}
				
				var o = options = options || {};
				if (!o.csuPuid || !P_LY.puidRex.test(o.csuPuid))
				{
					P_Utils.Log(fn, "csuPuid error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);
				}
				o.csuIndex = typeof o.csuIndex != "undefined" || !isNaN(o.csuIndex) ? o.csuIndex : 0;
				o.domainRoad = o.domainRoad || ""; 
				
				var operator;
				
				switch (action.toLowerCase())
				{
					case "getplan":
					case "removeplan":
						if (!o.planName)
						{
							P_Utils.Log(fn, "planName error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						operator = P_LY.Control.CommonGet
						(
							connectId,
							{
								puid : o.csuPuid,
								resType : P_LY.Enum.PuResourceType.SC,
								resIdx : o.csuIndex,
								domainRoad : o.domainRoad,
								controlID : (action.toLowerCase() == "getplan" ? "C_SC_GetPlan" : "C_SC_DelPlan"),
								param : '<Param Name="'+o.planName+'" />'
							}
						);
						break;
					case "setplan":
						if (!o.planName)
						{
							P_Utils.Log(fn, "planName error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						o.guard = o.guard == 0 || o.guard == 1 ? o.guard : 1;
						o.cycle = o.cycle || P_LY.Enum.PlanCycleValue.Everyday;
						 
						if (true)
						{
							var is_error = false;
							if (o.cycleParam != null && typeof o.cycleParam != "undefined" && !isNaN(o.cycleParam))
							{
								if (o.cycle == P_LY.Enum.PlanCycleValue.Weekly && (Number(o.cycleParam) <= 0 || Number(o.cycleParam) >= 128))
								{
									is_error = true;
								}
							}
							else
							{
								if (typeof o.cycle != "undefined" && o.cycle != P_LY.Enum.PlanCycleValue.Everyday)
								{
									is_error = true;
								}
							}
							if (is_error == true)
							{
								P_Utils.Log(fn, "cycleParam error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);


							}
						}
						
						if (true)
						{
							var is_error = false;
							
							if (o.guardTimeMap && typeof o.guardTimeMap != "undefined")
							{
								var gtmap = o.guardTimeMap.toString().replace(P_Utils.Regexs.strip, "").replace(/(0x)/gm, "");
								var gtmaplen = gtmap.length;
								
								switch (o.cycle)
								{
									case P_LY.Enum.PlanCycleValue.Once:
									case P_LY.Enum.PlanCycleValue.Everyday:
										if (gtmaplen != 36 * 2)
										{
											is_error = true;
										}
										break;
									case P_LY.Enum.PlanCycleValue.Weekly:
										var cp = o.cycleParam,
											bincp = parseInt(cp).toString(2),
											countoneflag = 0;
										
										if (bincp.length < 0 || bincp.length > 7)
										{
											is_error = true;
											P_Utils.Log(fn , "plan cycleParam length beyond range error~");
										}
										else
										{
											for (var i = 0; i < bincp.length; i++)
											{
												if (binco.charAt(i) == "1")
												{
													countoneflag++;
												}
											}
			
											if (gtmaplen != countoneflag * 36 * 2)
											{
												is_error = true;
											}
										}
										break;
								}
								
								o.guardTimeMap = gtmap; // - 赋值过滤后的值
							}
							else
							{
								is_error = true;
							}
							
							if (is_error == true)
							{
								P_Utils.Log(fn, "guardTimeMap length error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
							}
						}
						
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var xmlObj = new XML.ObjTree();
							xmlObj.xmlDecl = "";
							xmlObj.attr_prefix = "-";
							
							var paramJSON = 
							{
								"Param" :
								{
									"-Name" : o.planName,
									"-Guard" : o.guard,
									"-Cycle" : o.cycle,
									"-CycleParam" : o.cycleParam,
									"-GuardTimeMap" : o.guardTimeMap,
									"-SerialNo" : o.serialNo || (_connStruct.connParam.username || ""),
									"-SerialToken" : MD5.Hex_MD5(o.serialToken || (_connStruct.connParam.password || ""))
								}
							};
							
							operator = P_LY.Control.CommonGet
							(
								connectId,
								{
									puid : o.csuPuid,
									resType : P_LY.Enum.PuResourceType.SC,
									resIdx : o.csuIndex,
									domainRoad : o.domainRoad,
									controlID : "C_SC_SetPlan",
									param : xmlObj.writeXML(paramJSON)
								}
							); 
						}
						else
						{
							P_Utils.Log(fn, "XML.ObjTree error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						break;
					case "addplanres":
					case "removeres":
						if (!o.planName)
						{
							P_Utils.Log(fn, "planName error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						
						// - 当没有o.puid参数时，就会检测o.resourceSets是否存在，o.resourceSets存在，就可以一次添加或删除多条资源信息
						if (!o.puid || !P_LY.puidRex.test(o.puid))
						{
							if (o.resourceSets == null || typeof o.resourceSets != "object")
							{
								P_Utils.Log(fn, "puid & resourceSets error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 
							}
							else
							{
								if (typeof o.resourceSets == "object" && o.resourceSets.constructor != Array)
								{
									o.resourceSets = [o.resourceSets];	
								}
							}
						}
						else
						{
							// - 当o.puid存在，那么每次最多只添加或删除一条资源信息
							
							if (!o.resType)
							{
								P_Utils.Log(fn, "resType error~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
							}
							o.resIdx = typeof o.resIdx != "undefined" || !isNaN(o.resIdx) ? o.resIdx : 0;
							
							o.resourceSets = [new P_LY.Struct.PlatformStoragePlanResourceStruct(o.planName, o.puid, o.resType, o.resIdx, o.optID, o.value)];
						}
						
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var xmlObj = new XML.ObjTree();
							xmlObj.xmlDecl = "";
							xmlObj.attr_prefix = "-";
							
							var ResArr = [];
							
							var IDArr = ["C_PTZ_MoveToPresetPos", "C_SCHEDULER_StartStream", "C_COMMONRES_StartStream"];
							
							for (var i = 0; i < o.resourceSets.length; i++)
							{
								var item = o.resourceSets[i]; 
								
								if (typeof item.puid != "undefined" && typeof item.optID != "undefined")
								{
									if (P_Utils.Array.indexOf(IDArr, item.optID) == -1)
									{
										item.optID = item.resType == P_LY.Enum.PuResourceType.PTZ ? IDArr[0] : IDArr[1];
									}
									else
									{
										item.optID = item.resType == P_LY.Enum.PuResourceType.PTZ ? IDArr[0] : (item.optID || IDArr[1]);
									}
									
									item.value = item.value || (item.resType == P_LY.Enum.PuResourceType.PTZ ? 0 : P_LY.Enum.StreamType.REALTIME);
									
									ResArr.push
									(
									 	{
											"-ObjType" : 151,
											"-ObjID" : item.puid,
											"-Type" : item.resType,
											"-Idx" : item.resIdx,
											"-OptID" : item.optID,
											"-Prio" : _connStruct.userPriority, // - 用户优先级
											"-EPID" : _connStruct.connParam.epId, // - 登录用户所属企业ID
											Param :
											{
												"-Value" : item.value
											}
										} 
									); 
								}
							}
							
							var objSetsJSON =
							{
								ObjSets : 
								{
									Res : (ResArr || [])
								}
							};
							
							operator = P_LY.Control.CommonGet
							(
								connectId,
								{
									puid : o.csuPuid,
									resType : P_LY.Enum.PuResourceType.SC,
									resIdx : o.csuIndex,
									domainRoad : o.domainRoad,
									controlID : (action.toLowerCase() == "addplanres" ? "C_SC_AddResToPlan" : "C_SC_RemoveResFromPlan"),
									param : '<Param Name="'+o.planName+'" />',
									xmlObjSets : xmlObj.writeXML(objSetsJSON)
								}
							); 
						}
						else
						{
							P_Utils.Log(fn, "XML.ObjTree error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						break;
					case "getplanres":
						if (!o.planName)
						{
							P_Utils.Log(fn, "planName error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						o.offset = typeof o.offset != "undefined" && !isNaN(o.offset) ? o.offset : 0;
						o.count = typeof o.count != "undefined" && !isNaN(o.count) ? o.count : 200;
						
						operator = P_LY.Control.CommonGet
						(
							connectId,
							{
								puid : o.csuPuid,
								resType : P_LY.Enum.PuResourceType.SC,
								resIdx : o.csuIndex,
								domainRoad : o.domainRoad,
								controlID : "C_SC_GetPlanRes",
								param : '<Param Name="'+o.planName+'" Offset="'+o.offset+'" Cnt="'+o.count+'" />'
							}
						);
						break;
					case "getallplannames":
						operator = P_LY.Control.CommonGet
						(
							connectId,
							{
								puid : o.csuPuid,
								resType : P_LY.Enum.PuResourceType.SC,
								resIdx : o.csuIndex,
								domainRoad : o.domainRoad,
								controlID : "C_SC_GetAllPlanNames"
							}
						);
						break;
				}
				
				if (operator.rv == P_Error.SUCCESS)
				{
					var _response = operator.response || {};
					
					if (_response != null && typeof _response != "undefined")
					{
						switch (action.toLowerCase())
						{
							case "getplan":
								if (typeof _response.Cycle != "undefined")
								{
									_response = new P_LY.Struct.PlatformStoragePlanStruct
									(
										o.planName,
										_response.Guard,
										_response.Cycle,
										_response.CycleParam,
										_response.GuardTimeMap
									);
								}
								break;
							case "setplan":
								break;
							case "removeplan":
								break;
							case "addplanres":
								break;
							case "removeres":
								break;
							case "getplanres":
								if (typeof _response.Res != "undefined")
								{
									var planResArr = [];
									
									var Res = _response.Res;
									if (Res.constructor != Array)
									{
										Res = [Res];	
									}
									for (var i = 0; i < Res.length; i++)
									{
										planResArr.push
										(
											new P_LY.Struct.PlatformStoragePlanResourceStruct
											(
												o.planName,
												Res[i].ObjID,
												Res[i].Type,
												Res[i].Idx,
												Res[i].OptID,
												(typeof Res[i].Param != "undefined" && typeof Res[i].Param.Value != "undefined" ? (Res[i].Param.Value || "") : "")
											)
										);	
									}
									
									_response = planResArr;
								}
								break;
							case "getallplannames":
								if (typeof _response.Plan != "undefined")
								{
									var planNames = []; 
									
									var Plan = _response.Plan;
									if (Plan.constructor != Array)
									{
										Plan = [Plan];	
									}
									for (var i = 0; i < Plan.length; i++)
									{
										planNames.push(Plan[i].Name);	
									}
									
									_response = planNames;
								}
								break;
						}
					}
					
					operator.response = _response;
				}
				
				return operator;
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		/*
		---
		fn: GetPlan
		desc: 获取存储计划
		returns:
			- succ <response:Object(P_LY.Struct.PlatformStoragePlanStruct)>
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				planName(string) * 平台存储计划名称
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		GetPlan : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("getplan", connectId, options);
		},
		/*
		---
		fn: SetPlan
		desc: 设置存储计划
		author: 
			- 
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				planName(string) * 平台存储计划名称
				guard(uint) 是否布防，缺省为1布防，0为撤防
				cycle(P_LY.Enum.PlanCycleValue) 存储计划类型，缺省为P_LY.Enum.PlanCycleValue.Everyday
				cycleParam(uint) 存储计划取值
				guardTimeMap(string) * 存储计划布防时间表
				serialNo(string) 登录平台获取流的帐号，缺省为建立连接使用的用户名
				serialToken(string) 登录平台获取流的口令，缺省为建立连接使用的密码
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		SetPlan : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("setplan", connectId, options);
		},
		/*
		---
		fn: RemovePlan
		desc: 删除存储计划
		author: 
			- 
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				planName(string) * 平台存储计划名称
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		RemovePlan : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("removeplan", connectId, options);
		},
		/*
		---
		fn: AddResourceToPlan
		desc: 向存储计划添加资源
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				planName(string) * 平台存储计划名称，要添加的资源都属于此计划
				resourceSets(Array|Object<P_LY.Struct.PlatformStoragePlanResourceStruct>) [1] 存储计划资源集合
				puid(string) [2] 设备PUId
				resType(P_LY.Enum.PuResourcType) [2] 资源类型
				resIdx(uint) [2] 资源索引，缺省为0
				optID(string) [2] 命令ID
				value(string|uint) [2] 预置位编号或流类型 
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		remark:
			- [1] 存在，那么可以进行批量添加资源，但是如果[2]中options.puid参数存在，批量添加不会生效
			- [2] 的参数存在，那么一次只能添加一条资源
			- options.optID取值范围是"C_PTZ_MoveToPresetPos", "C_SCHEDULER_StartStream", "C_COMMONRES_StartStream"

			- options.resType为P_LY.Enum.PuResourcType.PTZ，那么options.optID取值C_PTZ_MoveToPresetPos，options.value为预置位编号，默认为0
			- options.resType为P_LY.Enum.PuResourcType.VideoIn，options.value为流类型，默认REALTIME
		...
		*/
		AddResourceToPlan : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("addplanres", connectId, options);
		},
		/*
		---
		fn: GetPlanResource
		desc: 获取存储计划资源
		returns:
			- succ <response:Array(P_LY.Struct.PlatformStoragePlanResourceStruct)>
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				planName(string) * 平台存储计划名称
				offset(uint) 分页查询开始量，缺省为0
				count(uint) 分页查询条数，缺省为200
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		GetPlanResource : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("getplanres", connectId, options);
		},
		/*
		---
		fn: RemoveResourceFromPlan
		desc: 从存储计划删除资源
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				planName(string) * 平台存储计划名称，要删除的资源都属于此计划
				resourceSets(Array|Object<P_LY.Struct.PlatformStoragePlanResourceStruct>) [1] 存储计划资源集合
				puid(string) [2] 设备PUId
				resType(P_LY.Enum.PuResourcType) [2] 资源类型
				resIdx(uint) [2] 资源索引，缺省为0
				optID(string) [2] 命令ID
				value(string|uint) [2] 预置位编号或流类型 
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		remark:
			- [1] 存在，那么可以进行批量删除资源，但是如果[2]中options.puid参数存在，批量删除不会生效
			- [2] 的参数存在，那么一次只能删除一条资源
			- options.optID取值范围是"C_PTZ_MoveToPresetPos", "C_SCHEDULER_StartStream", "C_COMMONRES_StartStream"
			- options.resType为P_LY.Enum.PuResourcType.PTZ，那么options.optID取值C_PTZ_MoveToPresetPos，options.value为预置位编号，默认为0
			- options.resType为P_LY.Enum.PuResourcType.VideoIn，options.value为流类型，默认REALTIME
		...
		*/
		RemoveResourceFromPlan : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("removeres", connectId, options);
		},
		/*
		---
		fn: GetAllPlanNames
		desc: 获取所有存储计划名
		returns:
			- succ <response:[planName, ..., planName]>
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，一般可缺省为0
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		GetAllPlanNames : function (connectId, options)
		{
			return this.__Sdk_PlatfromStorageCtl("getallplannames", connectId, options);
		},
		
		end : true
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Planform Storage Plan Mgr
    * ........................................................................................................
    **/
	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Planform LinkAction Mgr 2013.01.23
    * ........................................................................................................
    **/
	PlatformLinkActionControl :
	{
		/*
		---
		fn: GetAllPlanNames
		desc: 获取所有联动计划名
		returns:
			- succ <response:[planName, ..., planName]>
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				nuType(string|uint) 联动调度网元类型，缺省为19，上层调用时建议省略（下同）
				nuid(string) 联动调度网元NUID，缺省为019000100000000001，上层调用时建议省略（下同）
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		GetAllPlanNames : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("GetAllPlanNames", connectId, options);
		},
		/*
		---
		fn: GetPlan
		desc: 获取联动计划
		author: 
			- 
		returns:
			- succ <response: {Guard: ?, Cycle: ?, CycleParam: ?, GuardTmMap: ?, CombineTm: ?, TriggerStatus: ?}>
			- 	Guard 
					“1”表示该计划布防，“0”表示撤防
				Cycle 
					P_LY.Enum.PlanCycleValue对象的值Weekly, Everyday, Once
				CycleParam 
					字符串表示的是4字节无符号整数，
					当Cycle为Everyday时，此值无意义。
					当Cycle为Weekly时，这个值的0-6比特对应周一至周日，1表示有布防计划，0表示没有布防计划
					当Cycle为Once时，是UTC时间，单位秒
				GuardTmMap
					布防生效时间图。在布防情况下，如果不是生效时间图覆盖的时间区间，也不产生联动，1个比特代表5分钟，用36字节代表1天。
					内容是字符串。长度必然是n*36*2。
					Cycle是Weekly时，凡是CycleParam中是1的比特，对应着这里连续的36*2个字节。
					Cycle是Everyday或Once时，长度是36*2个字节
				CombineTm
					触发联动的事件同时发生的判决门限。单位ms
				TriggerStatus
					内存只读属性，1”表示该联动处于触发状态，“0”表示非触发状态。
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				planName(string) * 联动计划名
				nuType(string|uint) 联动调度网元类型，缺省为19，上层调用时建议省略
				nuid(string) 联动调度网元NUID，缺省为019000100000000001，上层调用时建议省略
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		GetPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("GetPlan", connectId, options);
		},
		/*
		---
		fn: SetPlan
		desc: 设置联动计划
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				planName(string) * 联动计划名
				Guard(uint) 
					“1”表示该计划布防，“0”表示撤防
				Cycle(string) * 
					P_LY.Enum.PlanCycleValue对象的值Weekly, Everyday, Once
				CycleParam(string) *
					字符串表示的是4字节无符号整数，
					当Cycle为Everyday时，此值无意义。
					当Cycle为Weekly时，这个值的0-6比特对应周一至周日，1表示有布防计划，0表示没有布防计划
					当Cycle为Once时，是UTC时间，单位秒
				GuardTmMap(hex string) *
					布防生效时间图。在布防情况下，如果不是生效时间图覆盖的时间区间，也不产生联动，1个比特代表5分钟，用36字节代表1天。
					内容是十六进制字符串。长度必然是n*36*2。
					Cycle是Weekly时，凡是CycleParam中是1的比特，对应着这里连续的36*2个字节。
					Cycle是Everyday或Once时，长度是36*2个字节
				CombineTm(uint) *
					触发联动的事件同时发生的判决门限。单位ms
				TriggerStatus(uint) *
					内存只读属性，1”表示该联动处于触发状态，“0”表示非触发
				nuType(string|uint) 联动调度网元类型，缺省为19，上层调用时建议省略
				nuid(string) 联动调度网元NUID，缺省为019000100000000001，上层调用时建议省略
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		SetPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("SetPlan", connectId, options);
		},
		/*
		---
		fn: RemovePlan
		desc: 移除联动计划
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				planName(string) * 联动计划名
				nuType(string|uint) 联动调度网元类型，缺省为19，上层调用时建议省略
				nuid(string) 联动调度网元NUID，缺省为019000100000000001，上层调用时建议省略
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		RemovePlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("RemovePlan", connectId, options);
		},
		
		GetPlanEvent : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("GetPlanEvent", connectId, options);
		},
		AddEventToPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("AddEventToPlan", connectId, options);
		},
		RemoveEventFromPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("RemoveEventFromPlan", connectId, options);
		},
		
		GetPlanAction : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("GetPlanAction", connectId, options);
		},
		AddActionToPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("AddActionToPlan", connectId, options);
		},
		RemoveActionFromPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("RemoveActionFromPlan", connectId, options);
		},
		
		/*
		---
		fn: ManualTriggerPlan
		desc: 手动触发联动
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				planName(string) * 联动计划名
				nuType(string|uint) 联动调度网元类型，缺省为19，上层调用时建议省略
				nuid(string) 联动调度网元NUID，缺省为019000100000000001，上层调用时建议省略
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		ManualTriggerPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("ManualTriggerPlan", connectId, options);
		},
		/*
		---
		fn: ManualStopPlan
		desc: 手动停止触发联动
		params:
			- connectId(string) 连接ID
			- options(object) 可选参数
			{
				planName(string) * 联动计划名
				nuType(string|uint) 联动调度网元类型，缺省为19，上层调用时建议省略
				nuid(string) 联动调度网元NUID，缺省为019000100000000001，上层调用时建议省略
				domainRoad(string) 所属子域名，缺省为空（表示根平台）
			}
		...
		*/
		ManualStopPlan : function (connectId, options)
		{
			return this.__Sdk_PlatformLinkActionCtl("ManualStopPlan", connectId, options);
		},
		
		__Sdk_PlatformLinkActionCtl : function (action, connectId, options)
		{
			try
			{
				var fn = "P_LY.PlatformLinkActionControl.__Sdk_PlatformLinkActionCtl -> " + action;
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
            		return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
				}
				var _connStruct = P_LY.Connections.get(connectId);
               	
               	if (!_connStruct.session || _connStruct.status != P_LY.Enum.ConnectionStatus.Connected) 
				{
					P_Utils.Log(fn, "login or session error~");
					if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
					}
					else
					{
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
					}		
				}
				
				var o = options = options || {};
				o.nuType_las = o.nuType || 19; // - 联动调度网元类型
				o.nuid_las = o.nuid || o.NUID || "019000100000000001"; // - 联动调度网元NUID
				o.domainRoad = o.domainRoad || ""; 
				o.planName = o.planName || "";
				
				var requestXML;
				
				switch (action)
				{
					case "GetAllPlanNames" :
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="C_LA_GetAllPlanNames"><Param /></DstRes></C></M>';
						break;
					
					case "GetPlan" :
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="C_LA_GetPlan"><Param Name="'+o.planName+'" /></DstRes></C></M>';
						break;
					case "SetPlan" :
						o.Guard = typeof o.Guard != "undefined" ? (o.Guard || 0) : 1;
						o.Cycle = o.Cycle || P_LY.Enum.PlanCycleValue.Everyday;
						
						var is_error = false;
						if (o.CycleParam != null && typeof o.CycleParam != "undefined" && !isNaN(o.CycleParam))
						{
							if (o.Cycle == P_LY.Enum.PlanCycleValue.Weekly && (Number(o.CycleParam) <= 0 || Number(o.CycleParam) >= 128))
							{
								is_error = true;
							}
						}
						else
						{
							if (typeof o.Cycle != "undefined" && o.Cycle != P_LY.Enum.PlanCycleValue.Everyday)
							{
								is_error = true;
							}
						}
						if (is_error == true)
						{
							P_Utils.Log(fn, "CycleParam error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						
						var is_error = false;
						if (o.GuardTmMap && typeof o.GuardTmMap != "undefined")
						{
							var gtmap = o.GuardTmMap.toString().replace(P_Utils.Regexs.strip, "").replace(/(0x)/gm, "");
							var gtmaplen = gtmap.length;
							
							switch (o.Cycle)
							{
								case P_LY.Enum.PlanCycleValue.Once:
								case P_LY.Enum.PlanCycleValue.Everyday:
									if (gtmaplen != 36 * 2)
									{
										is_error = true;
									}
									break;
								case P_LY.Enum.PlanCycleValue.Weekly:
									var cp = o.CycleParam,
										bincp = parseInt(cp).toString(2),
										countoneflag = 0;
									
									if (bincp.length < 0 || bincp.length > 7)
									{
										is_error = true;
										P_Utils.Log(fn , "plan cycleParam length beyond range error~");
									}
									else
									{
										for (var i = 0; i < bincp.length; i++)
										{
											if (binco.charAt(i) == "1")
											{
												countoneflag++;
											}
										}
										if (gtmaplen != countoneflag * 36 * 2)
										{
											is_error = true;
										}
									}
									break;
							}
							
							o.GuardTmMap = gtmap; // - 赋值过滤后的值
						}
						else
						{
							is_error = true;
						}
						if (is_error == true)
						{
							P_Utils.Log(fn, "GuardTmMap length error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						
						o.CombineTm = typeof o.CombineTm != "undefined" ? o.CombineTm : 1000;
						
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="C_LA_SetPlan"><Param Name="'+o.planName+'" Guard="'+o.Guard+'" Cycle="'+o.Cycle+'" CycleParam="'+o.CycleParam+'" GuardTmMap="'+o.GuardTmMap+'" CombineTm="'+o.CombineTm+'" /></DstRes></C></M>';
						break;
					case "RemovePlan" :
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="C_LA_DelPlan"><Param Name="'+o.planName+'" /></DstRes></C></M>';
						break;
						
					case "GetPlanEvent" : // - 获取联动计划事件源
					case "GetPlanAction" : // - 获取联动计划动作
						o.offset = o.offset || 0;
						o.count = o.count || 200;
						
						var operationID = "C_LA_" + (action == "GetPlanEvent" ? "GetPlanEvent" : "GetPlanAction");
						
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="'+operatorID+'"><Param Name="'+o.planName+'" Offset="'+o.offset+'" Count="'+o.count+'" /></DstRes></C></M>';
						break;
					case "AddEventToPlan" :
					case "RemoveEventFromPlan" :
						o.srcSets = o.srcSets || [];
						if (o.srcSets && o.srcSets.constructor != Array)
						{
							o.srcSets = [o.srcSets];	
						}
						
						var Src = [];
						for (var i = 0; i < o.srcSets.length; i++)
						{
							var item = o.srcSets[i];
							Src.push
							(
								{
									"-ObjType" : item.ObjType || "",
									"-ObjID" : item.ObjID || "",
									"-ResType" : item.ResType || P_LY.Enum.PuResourceType.ST,
									"-ResIdx" : item.ResIdx || "",
									"-EventID" : item.EventID || "",
									Param : 
									{
										"-ResName" : item.ResName || ""
									}
								} 
							);	
						}
						var jsonSrcSets = 
						{
							SrcSets : 
							{
								Src : Src	
							}
						};
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var xmlObj = new XML.ObjTree();
							xmlObj.xmlDecl = "";
							xmlObj.attr_prefix = "-";
							
							var operationID = "C_LA_" + (action == "AddEventToPlan" ? "AddEventToPlan" : "RemoveEventFromPlan");
						
							requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="'+operationID+'"><Param Name="'+o.planName+'" /></DstRes></Cmd>'+(xmlObj.writeXML(jsonSrcSets)||"")+'</Msg>';
						} 
						break;
					case "AddActionToPlan" :
					case "RemoveActionFromPlan" :
						o.resSets = o.resSets || [];
						if (o.resSets && o.resSets.constructor != Array)
						{
							o.resSets = [o.resSets];	
						}
						
						var xmlRes = "";
						
						for (var i = 0; i < o.resSets.length; i++)
						{
							var item = o.resSets[i];
							
							xmlRes += '<Res ObjType="'+(item.ObjType||"")+'" ObjID="'+(item.ObjID||"")+'" Type="'+(item.Type||"")+'" Idx="'+(item.Idx||"0")+'" CmdType="'+(item.CmdType||"")+'" OptID="'+(item.OptID||"")+'" Prio="'+(item.Prio||"0")+'" ><Param DelayTm="'+(item.DelayTm||"")+'" CycleTm="'+(item.CycleTm||"")+'" CycleNum="'+(item.CycleNum||"")+'" ResName="'+(item.ResName||"")+'">'+item.xmlExtraActionParam+'</Param></Res>';
						}
						var xmlObjSets = '<ObjSets>'+xmlRes+'</ObjSets>';
						
						var operationID = "C_LA_" + (action == "AddActionToPlan" ? "AddActionToPlan" : "RemoveActionFromPlan");
						
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="'+operationID+'"><Param Name="'+o.planName+'" /></DstRes></Cmd>'+(xmlObjSets||"")+'</Msg>';
						break;
					
					case "ManualTriggerPlan" :
					case "ManualStopPlan" :
						var operationID = "C_LA_" + (action == "ManualTriggerPlan" ? "ManualTriggerPlan" : "ManualStopPlan");
						
						requestXML = '<?xml version="1.0" encoding="utf-8"?><Msg Name="CUCommonMsgReq" DomainRoad="'+o.domainRoad+'"><Cmd Type="CTL" Prio="'+_connStruct.userPriority+'" EPID="'+_connStruct.connParam.epId+'"><DstRes Type="SELF" Idx="0" OptID="'+operationID+'"><Param Name="'+o.planName+'" /></DstRes></C></M>';
						break;
				}
				var operator = P_IF.TransNUCommonMessage
				(
				 	_connStruct.nc,
					_connStruct.session,
					o.nuType_las,
					o.nuid_las,
					requestXML
				);
				if (operator.rv == P_Error.SUCCESS)
				{
					if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
					{
						var xmlObj = new XML.ObjTree();
						var jsonResponse = xmlObj.parseXML(operator.response);
						
						if (jsonResponse && typeof jsonResponse.Msg != "undefined" && jsonResponse.Msg.Cmd && typeof jsonResponse.Msg.Cmd.NUErrorCode != "undefined")
						{
							if (jsonResponse.Msg.Cmd.NUErrorCode != 0)
							{
								operator.rv = jsonResponse.Msg.Cmd.NUErrorCode;
							}
							else
							{
								if (jsonResponse.Msg.Cmd.DstRes)
								{
									if (jsonResponse.Msg.Cmd.DstRes.ErrorCode != 0)
									{
										operator.rv = jsonResponse.Msg.Cmd.DstRes.ErrorCode;
									}
									else
									{
										var DstRes = jsonResponse.Msg.Cmd.DstRes;
										
										// - 根据不同的请求返回有效的值
										switch (action)
										{
											case "GetAllPlanNames":
												if (typeof DstRes.Param == "object" && typeof DstRes.Param.PlanName != "undefined")
												{
													if (DstRes.Param.PlanName.constructor != Array) DstRes.Param.PlanName = [DstRes.Param.PlanName];
													operator.response = DstRes.Param.PlanName;
												}
												else
												{
													operator.response = [];
												}
												break;
												
											case "GetPlan" :
												operator.response = DstRes.Param || {};
												break;
											
											case "GetPlanEvent" :
											case "GetPlanAction" :
												if (typeof DstRes.Param == "object" && typeof DstRes.Param.Res != "undefined")
												{
													if (DstRes.Param.Res.constructor != Array) DstRes.Param.Res = [DstRes.Param.Res];
													operator.response = DstRes.Param.Res || [];
												}
												else
												{
													operator.response = [];
												}
												break;
											case "SetPlan" :
											case "RemovePlan" :
											case "AddEventToPlan" :
											case "RemoveEventFromPlan" :
											case "AddActionToPlan" :
											case "RemoveActionFromPlan" :
											case "ManualTriggerPlan" :
											case "ManualStopPlan" :
												operator.response = jsonResponse;
												break;
										}
									}
								}
							}
						}		
					}
				}
				return operator;
			}
			catch (e)
			{
				P_Utils.Log(fn, "excp error = " + e.name + "::" + e.message);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD, e);
			}
		},
		
		end : true	
	}, 
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Planform LinkAction Mgr 2013.01.23
    * ........................................................................................................
    **/
	_Download:{
		StartDownloadRecordFile:function(connectId, puid, sgHandle,remoteFile, localSaveAsFile)
		{
			var _dlkey = puid + "_" + sgIndex +"_" + endTime +"_" + localSaveAsFile;
			if (_dlhash.get(options.connectId))
			{
				/*
				var _dlCEFSVodFileStore = _dlhash.get(options.connectId).dlCEFSVodFileStore || new P_Utils.Hash();
				if (_dlCEFSVodFileStore.containsKey(_dlkey))
				{
					// - 判断是否存在同一下载通道
					if (typeof _dlCEFSVodFileStore.get(_dlkey).dlStreamHandle != "undefined" 
							&& _dlCEFSVodFileStore.get(_dlkey).dlStreamHandle != null)
					{
						P_Utils.Log(fn, "cefs vod dl existed, dlkey->" + _dlkey);
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_DOWNLOAD_EXISTED, _dlCEFSVodFileStore.get(_dlkey).dlStreamHandle);	
					}
				}
				*/
				var operator = P_LY.NPPSDKCommon.GetNCResponse
				(
					options.connectId, 
					"DownloadRecord",
					options.sgIndex, 
					remoteFile,
					localSaveAsFile
				);
			}
			
		}
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Download Control - 2013.11.25 
    * ........................................................................................................
    **/ 
	Download :
	{
		// - 存储下载信息
		__Sdk_Downloads : new P_Utils.Hash(),
		
		Clear : function (connectId)
		{
			try
			{
				var fn = "P_LY.Download.Clear";
				
				if (!connectId || !P_LY.Connections.get(connectId))
				{
					P_Utils.Log(fn, "connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				var _connStruct = P_LY.Connections.get(connectId);
				
				// - 存储下载信息
				var _dlhash = P_LY.Download.__Sdk_Downloads = P_LY.Download.__Sdk_Downloads || new P_Utils.Hash();
				
				_dlhash.each
				(
					function (item)
					{
						if (item.key == connectId)
						{
							var _Sdk_ResetHandle = function (_dlFileStore)
							{
								_dlFileStore.each
								(
									function (_item)
									{
										if (_item.value.dlStreamHandle != null)
										{
											P_IF.StopStreamPlay(_connStruct.nc, _item.value.dlStreamHandle);
											_item.value.dlStreamHandle = null;
										}
									}
								);
							};
							
							_Sdk_ResetHandle(item.value.dlCSUFileStore);
							_Sdk_ResetHandle(item.value.dlCEFSVodFileStore);
							_Sdk_ResetHandle(item.value.dlCEFSImageFileStore);
							
							_dlhash.unset(item.key);
							
							return true;
						} 
					} 
				); 
				
				return  new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		/*
		fn: StartCSUFileDownload
		desc: 下载平台存储录像或图片
		author:
			- 
		returns:
			- succ <response:dlStreamHandle(string) 下载通道（流）句柄
		params:
			- connectId(string) * 连接ID
			- csuPuid(string) * 中心存储器PUID
			- fileAllPath(string) * 中心存储文件全路径
			- localSaveAsFile(string) * 下载保存全路径，如C:/vod.avi，上层调用时确保正确的存储路径
			- csuIndex(uint) 中心存储单元资源索引，缺省为0
		reamrk:
			- 成功时，返回下载通道（流）句柄
		*/
		StartCSUFileDownload : function (connectId, csuPuid, fileAllPath, localSaveAsFile, csuIndex)
		{
			return P_LY.Download.__Sdk_DLControl
			(
			 	"dlcsu",
				{
					connectId: connectId || null,
					csuPuid: csuPuid || null,
					csuIndex: csuIndex || 0,
					fileAllPath: fileAllPath || null,
					localSaveAsFile: localSaveAsFile || null
				}
			); 
		},
		/*
		fn: StopCSUFileDownload
		desc: 停止下载平台存储录像或图片
		author:
			- 
		params:
			- connectId(string) * 连接ID
			- dlStreamHandle(string) * 下载通道（流）句柄
		*/
		StopCSUFileDownload : function (connectId, dlStreamHandle)
		{
			return P_LY.Download.__Sdk_DLControl
			(
			 	"stopdl",
				{
					connectId: connectId || null,
					dlStreamHandle: dlStreamHandle || null
				}
			);
		},

		StartCloudFileDownload : function (connectId, szId, fileAllPath, localSaveAsFile,offset)
		{
			return P_LY.Download.__Sdk_DLControl
			(
			 	"cloud",
				{
					connectId: connectId || null,
					szId: szId|| null,
					fileAllPath: fileAllPath || null,
					localSaveAsFile: localSaveAsFile || null,
					offset:offset	
				}
			); 
		},
		/*
		fn: StopCSUFileDownload
		desc: 停止下载平台存储录像或图片
		author:
			- 
		params:
			- connectId(string) * 连接ID
			- dlStreamHandle(string) * 下载通道（流）句柄
		*/
		StopCloudFileDownload : function (connectId, dlStreamHandle)
		{
			return P_LY.Download.__Sdk_DLControl
			(
			 	"stopdl",
				{
					connectId: connectId || null,
					dlStreamHandle: dlStreamHandle || null
				}
			);
		},
		
		/*
		fn: StartCEFSVodFileDownload
		desc: 下载前端录像
		author:
			- 
		returns:
			- succ <response:dlStreamHandle(string) 下载通道（流）句柄>
		params:
			- connectId(string) * 连接ID
			- puid(string) * 录像所属设备PUID
			- sgIndex(uint) * 前端存储器资源索引，默认为0
			- beginTime(uint) * 录像开始UTC时间，如1385049600
			- endTime(uint) * 录像结束UTC时间，如1385049900
			- ivIndex(uint) * 录像所属视频资源索引，默认为0
			- localSaveAsFile(string) * 下载保存全路径，如C:/vod.avi，上层调用时确保正确的存储路径
		*/
		StartCEFSVodFileDownload : function (connectId, puid, sgIndex,remoteFile,localSaveAsFile,offset)
		{
			return P_LY.Download.__Sdk_DLControl
			(
			 	"dlcefsvod",
				{
					connectId: connectId || null,
					puid: puid || null,
					sgIndex: sgIndex || 0,
					remoteFile: remoteFile || "",
					localSaveAsFile: localSaveAsFile || null,
					offset:0
				}
			); 
		},
		/*
		fn: StartCEFSImageFileDownload
		desc: 下载前端图片
		author:
			- 
		returns:
			- succ <response:dlStreamHandle(string) 下载通道（流）句柄>
		params:
			- connectId(string) * 连接ID
			- puid(string) * 抓拍所属设备PUID
			- sgIndex(uint) * 前端存储器资源索引，默认为0
			- snapshotTime(uint) * 抓拍UTC时间，如1385049600
			- noInSecond(uint) * 抓拍图片秒内编号，默认为0，同一秒内可以有多个抓图
			- ivIndex(uint) * 抓拍所属视频资源索引，默认为0
			- localSaveAsFile(string) * 下载保存全路径，如C:/vod.bmp，上层调用时确保正确的存储路径
		*/
		StartCEFSImageFileDownload : function (connectId, puid, sgIndex,noInSecond ,snapshotTime,remoteFile,localSaveAsFile)
		{
		
			return P_LY.Download.__Sdk_DLControl
			(
			 	"dlcefsimage",
				{
					connectId: connectId || null,
					puid: puid || null,
					sgIndex: sgIndex || 0,
					snapshotTime: snapshotTime || null,
					noInSecond: noInSecond || 0,
				//	ivIndex: ivIndex || 0,
					remoteFile: remoteFile || "",	
					localSaveAsFile: localSaveAsFile || null
				}
			); 
		},
		/*
		fn: StopCEFSFileDownload
		desc: 停止下载前端存储录像或图片
		author:
			- 
		params:
			- connectId(string) * 连接ID
			- dlStreamHandle(string) * 下载通道（流）句柄
		*/
		StopCEFSFileDownload : function (connectId, dlStreamHandle)
		{
			return P_LY.Download.__Sdk_DLControl
			(
			 	"stopdl",
				{
					connectId: connectId || null,
					dlStreamHandle: dlStreamHandle || null
				}
			);
		},
		
		// - 内部共用下载函数
		__Sdk_DLControl : function (action, options)
		{
			try
			{
				var fn = "P_LY.Download.__Sdk_DLControl -> " + action;
				
				var options = options || {};
				
				if (!options.connectId || !P_LY.Connections.get(options.connectId))
				{
					P_Utils.Log(fn, "options.connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				// - 存储下载信息
				var _dlhash = P_LY.Download.__Sdk_Downloads = P_LY.Download.__Sdk_Downloads || new P_Utils.Hash();
				
				if (!_dlhash.get(options.connectId))
				{
					_dlhash.set
					(
					 	options.connectId, 
						{
							connectId: options.connectId,
							dlCSUFileStore: new P_Utils.Hash(),
							dlCEFSVodFileStore: new P_Utils.Hash(),
							dlCEFSImageFileStore: new P_Utils.Hash()
						}
					);	
				}
							
				var operator;
				switch (action)
				{
					case "cloud": // - 下载平台录像或图片
						if (!options.szId || !P_LY.puidRex.test(options.szId))
						{
							options.szId = "";
						}
						
						if (!options.fileAllPath)
						{
							P_Utils.Log(fn, "options.fileAllPath error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (!options.localSaveAsFile)
						{
							P_Utils.Log(fn, "options.localSaveAsFile error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						
						var _dlkey = options.szId + 
										"_" + options.fileAllPath + 
										"_" + options.localSaveAsFile;
						
						if (_dlhash.get(options.connectId))
						{		
							var _dlCSUFileStore = _dlhash.get(options.connectId).dlCSUFileStore || new P_Utils.Hash();
							if (_dlCSUFileStore.containsKey(_dlkey))
							{
								// - 判断是否存在同一下载通道
								if (typeof _dlCSUFileStore.get(_dlkey).dlStreamHandle != "undefined" 
										&& _dlCSUFileStore.get(_dlkey).dlStreamHandle != null)
								{
									P_Utils.Log(fn, "csu dl existed, dlkey->" + _dlkey);
									return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_DOWNLOAD_EXISTED, _dlCSUFileStore.get(_dlkey).dlStreamHandle);	
								}
							}
							
						
							var _connStruct = P_LY.Connections.get(options.connectId);
							if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
							{
								operator = P_IF.DownloadCloudRecord(_connStruct.nc,_connStruct.session,options.szId,options.fileAllPath,options.localSaveAsFile,options.offset);
								//console.log(operator)
							}
							else
							{
								P_Utils.Log(fn, "hav't connected~");
								return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
							}
							/*
							operator = P_LY.NPPSDKCommon.GetNCResponse
							(
								options.connectId, 
								P_LY.Enum.NCObjectMethodList.DownloadCloudRecord, 
								options.szId, 
								options.fileAllPath, 
								options.localSaveAsFile
							);console.log(operator);
							*/

							if (operator.rv == P_Error.SUCCESS)
							{		
								_dlCSUFileStore.set
								(
									_dlkey,
									{
										dlkey: _dlkey,
										csuPuid: options.csuPuid,
										csuIndex: options.csuIndex,
										offset: options.offset,
										fileAllPath: options.fileAllPath,
										localSaveAsFile: options.localSaveAsFile,
										dlStreamHandle: operator.response
									}
								);
							}
						}
						break;
					case "dlcefsvod": // - 下载CEFS录像
						if (!options.puid || !P_LY.puidRex.test(options.puid))
						{
							P_Utils.Log(fn, "options.puid error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);	
						}
						options.sgIndex = typeof options.sgIndex != "undefined" || !isNaN(options.sgIndex) ? options.sgIndex : 0; 
						//options.ivIndex = typeof options.ivIndex != "undefined" || !isNaN(options.ivIndex) ? options.ivIndex : 0; 
						/*
						if (!options.beginTime)
						{
							P_Utils.Log(fn, "options.beginTime error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (!options.endTime)
						{
							P_Utils.Log(fn, "options.endTime error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}*/
						if (!options.localSaveAsFile)
						{
							P_Utils.Log(fn, "options.localSaveAsFile error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						
						var _dlkey = options.puid + 
										"_" + options.sgIndex + 
										"_" + options.remoteFile + 
										"_" + options.localSaveAsFile;
							
						if (_dlhash.get(options.connectId))
						{		
							var _dlCEFSVodFileStore = _dlhash.get(options.connectId).dlCEFSVodFileStore || new P_Utils.Hash();
							if (_dlCEFSVodFileStore.containsKey(_dlkey))
							{
								// - 判断是否存在同一下载通道
								if (typeof _dlCEFSVodFileStore.get(_dlkey).dlStreamHandle != "undefined" 
										&& _dlCEFSVodFileStore.get(_dlkey).dlStreamHandle != null)
								{
									P_Utils.Log(fn, "cefs vod dl existed, dlkey->" + _dlkey);
									return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_DOWNLOAD_EXISTED, _dlCEFSVodFileStore.get(_dlkey).dlStreamHandle);	
								}
							}
						//	console.log(options)
							operator = P_LY.NPPSDKCommon.GetNCResponse
							(
								options.connectId, 
								P_LY.Enum.NCObjectMethodList.SG_DownLoadFile, 
								options.puid, 
								P_LY.Enum.PuResourceType.Storager, 
								options.sgIndex,
								options.remoteFile,
								options.localSaveAsFile,
								options.offset
							);
							/*
							operator = P_LY.NPPSDKCommon.GetNCResponse
							(
								options.connectId, 
								P_LY.Enum.NCObjectMethodList.SG_CEFSDownLoadFile, 
								options.puid, 
								P_LY.Enum.PuResourceType.Storager, 
								options.sgIndex,
								options.beginTime, 
								options.endTime, 
								options.ivIndex,
								options.localSaveAsFile
							);*/

							if (operator.rv == P_Error.SUCCESS)
							{		
								_dlCEFSVodFileStore.set
								(
									_dlkey,
									{
										dlkey: _dlkey,
										puid: options.puid,
										sgIndex: options.sgIndex,
										beginTime: options.beginTime,
										endTime: options.endTime,
										ivIndex: options.ivIndex,
										offset:	options.offset,
										localSaveAsFile: options.localSaveAsFile,
										dlStreamHandle: operator.response
									}
								);
							}
						}
						break;
					case "dlcefsimage": // - 下载CEFS图片
						if (!options.puid || !P_LY.puidRex.test(options.puid))
						{
							P_Utils.Log(fn, "options.puid error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);	
						}
						options.sgIndex = typeof options.sgIndex != "undefined" || !isNaN(options.sgIndex) ? options.sgIndex : 0; 
						options.noInSecond = typeof options.noInSecond != "undefined" || !isNaN(options.noInSecond) ? options.noInSecond : 0; 
						options.ivIndex = typeof options.ivIndex != "undefined" || !isNaN(options.ivIndex) ? options.ivIndex : 0; 
						
						if (!options.snapshotTime)
						{
							P_Utils.Log(fn, "options.snapshotTime error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (!options.localSaveAsFile)
						{
							P_Utils.Log(fn, "options.localSaveAsFile error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						
						var _dlkey = options.puid + 
										"_" + options.sgIndex +
										"_" + options.remoteFile + 
										"_" + options.localSaveAsFile;				
						if (_dlhash.get(options.connectId))
						{	
					
							var _dlCEFSImageFileStore = _dlhash.get(options.connectId).dlCEFSImageFileStore || new P_Utils.Hash();
							if (_dlCEFSImageFileStore.containsKey(_dlkey))
							{
								// - 判断是否存在同一下载通道
								if (typeof _dlCEFSImageFileStore.get(_dlkey).dlStreamHandle != "undefined" 
										&& _dlCEFSImageFileStore.get(_dlkey).dlStreamHandle != null)
								{
									P_Utils.Log(fn, "cefs image dl existed, dlkey->" + _dlkey);
									return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_DOWNLOAD_EXISTED, _dlCEFSImageFileStore.get(_dlkey).dlStreamHandle);	
								}
							}
							operator = P_LY.NPPSDKCommon.GetNCResponse
							(
								options.connectId, 
								P_LY.Enum.NCObjectMethodList.SG_CEFSDownLoadSnapshot, 
								options.puid, 
								P_LY.Enum.PuResourceType.Storager, 
								options.sgIndex,
								options.remoteFile,
								options.localSaveAsFile
							);
						//	console.log(operator)
							if (operator.rv == P_Error.SUCCESS)
							{		
								_dlCEFSImageFileStore.set
								(
									_dlkey,
									{
										dlkey: _dlkey,
										puid: options.puid,
										sgIndex: options.sgIndex,
										snapshotTime: options.snapshotTime,
										noInSecond: options.noInSecond,
										ivIndex: options.ivIndex,
										localSaveAsFile: options.localSaveAsFile,
										dlStreamHandle: operator.response
									}
								);
							}
						}
						break;
						
					case "stopdl": // - 停止下载
						if (typeof options.dlStreamHandle != "undefined" && options.dlStreamHandle != null)
						{
						 	// - 先检测是否存在
							var _connStruct = P_LY.Connections.get(options.connectId);
							
							_dlhash.each
							(
								function (item)
								{
									if (item.key == options.connectId)
									{
										var found = false;
										var _Sdk_ResetHandle = function (_dlFileStore)
										{
											if (!found)
											{
												_dlFileStore.each
												(
													function (_item)
													{
														if (_item.value.dlStreamHandle == options.dlStreamHandle)
														{
															found = true;
															
															// - 然后再停止下载
															operator = P_IF.StopStreamPlay(_connStruct.nc, options.dlStreamHandle);

															if (operator.rv == P_Error.SUCCESS)
															{
																_item.value.dlStreamHandle = null;
																
																_dlFileStore.unset(_item.key);
															}
															return true;
														}
													}
												);	
											}
										};
										
										_Sdk_ResetHandle(item.value.dlCSUFileStore);
										_Sdk_ResetHandle(item.value.dlCEFSVodFileStore);
										_Sdk_ResetHandle(item.value.dlCEFSImageFileStore);
										
										if (found == false)
										{
											operator = new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);	
										} 
										return true;	
									}
								}
							);
						}
						else
						{
							P_Utils.Log(fn, "dlStreamHandle error~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_HANDLE_ERROR);	
						}
						break;
				}
				
				return operator;
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		end : true
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Download Control - 2013.11.25
    * ........................................................................................................
    **/
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: User Management - 2014.02.14
    * ........................................................................................................
    **/
	UserManagementControl : 
	{
		// - 获取当前用户索引
		QueryUserIndex : function (connectId, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"QueryUserIndex", 
				{
					connectId: connectId,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 获取用户信息
		QueryUserInformation : function (connectId, userIndex, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"QueryUserInformation", 
				{
					connectId: connectId,
					userIndex: userIndex,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 获取非管理员用户资源
		QueryUserResource : function (connectId, userIndex, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"QueryUserResource", 
				{
					connectId: connectId,
					userIndex: userIndex,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 获取一个用户的逻辑分组
		QueryUserLogicGroup : function (connectId, userIndex, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"QueryUserLogicGroup", 
				{
					connectId: connectId,
					userIndex: userIndex,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 获取一个用户的子用户
		QuerySubUsers : function (connectId, userIndex, recursive, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"QuerySubUsers", 
				{
					connectId: connectId,
					userIndex: userIndex,
					recursive: recursive,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 添加用户
		AddUserInformation : function (connectId, userInformation, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"AddUser", 
				{
					connectId: connectId,
					userInformation: userInformation,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			); 
		},
		// - 更新用户
		UpdateUserInformation : function (connectId, userInformation, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"UpdateUser", 
				{
					connectId: connectId,
					userInformation: userInformation,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			); 
		},
		// - 删除用户
		DeleteUserInformation : function (connectId, userIndex, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
			 	"DeleteUser", 
				{
					connectId: connectId,
					userIndex: userIndex,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			); 
		},
		// - 添加用户资源
		AddUserResource : function (connectId, userIndex, resSets, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
				"AddUserResource",
				{
					connectId: connectId,
					userIndex: userIndex,
					resSets: resSets,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 移除用户资源
		RemoveUserResource : function (connectId, userIndex, resSets, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
				"RemoveUserResource",
				{
					connectId: connectId,
					userIndex: userIndex,
					resSets: resSets,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 添加用户逻辑分组
		AddUserLogicGroup : function (connectId, userIndex, logicGroupIndexs, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
				"AddUserLogicGroup",
				{
					connectId: connectId,
					userIndex: userIndex,
					logicGroupIndexs: logicGroupIndexs,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 移除用户逻辑分组
		RemoveUserLogicGroup : function (connectId, userIndex, logicGroupIndexs, nuType_cui, nuid_cui)
		{
			return this.__Sdk_UMCtl
			(
				"RemoveUserLogicGroup",
				{
					connectId: connectId,
					userIndex: userIndex,
					logicGroupIndexs: logicGroupIndexs,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 获取在线用户
		QueryOnlineUsers : function (connectId)
		{
			return this.__Sdk_UMCtl
			(
				"QueryOnlineUsers",
				{
					connectId: connectId,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		// - 踢除在线用户
		KickOnlineUsers : function (connectId, CUID)
		{
			return this.__Sdk_UMCtl
			(
				"KickOnlineUsers",
				{
					connectId: connectId,
					CUID: CUID,
					nuType_cui: nuType_cui,
					nuid_cui: nuid_cui
				}
			);
		},
		
		__Sdk_UMCtl : function (action, options)
		{
			try 
			{
				var fn = "P_LY.UserManagementControl.__Sdk_UMCtl - > " + action;
				
				var options = options || {};
				
				if (!options.connectId || !P_LY.Connections.get(options.connectId))
				{
					P_Utils.Log(fn, "options.connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				var _connStruct = P_LY.Connections.get(options.connectId);
				
				options.nuType_cui = options.nuType_cui || 5;
				options.nuid_cui = options.nuid_cui || "005000100000000001";
				
				options.userIndex = typeof options.userIndex != "undefined" && !isNaN(options.userIndex) ? options.userIndex : 0;
				
				var xmlRequest;
				switch (action)
				{
					case "QueryUserIndex":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryUserIndex" /></M>';		
						break;
					case "QueryUserInformation":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryUserInfo"><User Index="'+options.userIndex+'" /></C></M>';	
						break;
					case "QueryUserResource":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryUserResource"><User Index="'+options.userIndex+'" /></C></M>';	
						break;
					case "QueryUserLogicGroup":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryUserLogicGroup"><User Index="'+options.userIndex+'" /></C></M>';	
						break;
					case "QuerySubUsers":
						options.recursive = typeof options.recursive != "undefined" && !isNaN(options.recursive) ? options.recursive : 0;
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QuerySubUser"><User Index="'+options.userIndex+'" Recursive="'+options.recursive+'" /></C></M>';
						break;
					case "AddUser":
						var ui = options.userInformation = options.userInformation || {};
						if (!ui || typeof ui != "object" || typeof ui.Index == "undefined" || typeof ui.Identity == "undefined" || typeof ui.Password == "undefined" || typeof ui.Active == "undefined" || typeof ui.Priority == "undefined" || typeof ui.MaxSessionNum == "undefined" || typeof ui.ActorType == "undefined" || typeof ui.Actor_Index == "undefined")
						{
							P_Utils.Log(fn, "options.userInformation error...");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
						}
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_AddUser"><User Index="'+(ui.Index||0)+'" Identity="'+(ui.Identity||"")+'" Password="'+MD5.Hex_MD5(ui.Password||"")+'" Active="'+(ui.Active||0)+'" Priority="'+(ui.Priority||0)+'" MaxSessionNum="'+(ui.MaxSessionNum||"")+'" ActorType="'+(ui.ActorType||0)+'" Actor_Index="'+(ui.Actor_Index||0)+'" Name="'+(ui.Name||"")+'" Phones="'+(ui.Phones||"")+'" Description="'+(ui.Description||"")+'" Remark="'+(ui.Remark||"")+'" EnableWhiteList="'+(ui.EnableWhiteList||0)+'" /></C></M>';
						break;
					case "UpdateUser":
						var ui = options.userInformation = options.userInformation || {};
						if (typeof ui.Index == "undefined")
						{
							P_Utils.Log(fn, "options.userInformation Index must exist ...");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						var xmlUser = '<User ';
						if (typeof ui.Index != "undefined")
						{
							xmlUser += 'Index="' + (ui.Index || 0) + '" ';
						}
						if (typeof ui.Password != "undefined")
						{
							xmlUser += 'Password="' + MD5.Hex_MD5(ui.Password || "") + '" ';
						}
						if (typeof ui.Active != "undefined")
						{
							xmlUser += 'Active="' + (ui.Active || 0) + '" ';
						}
						if (typeof ui.Priority != "undefined")
						{
							xmlUser += 'Priority="' + (ui.Priority || 0) + '" ';
						}
						if (typeof ui.MaxSessionNum != "undefined")
						{
							xmlUser += 'MaxSessionNum="' + (ui.MaxSessionNum || 0) + '" ';
						}
						if (typeof ui.Name != "undefined")
						{
							xmlUser += 'Name="' + (ui.Name || "") + '" ';
						}
						if (typeof ui.Phones != "undefined")
						{
							xmlUser += 'Phones="' + (ui.Phones || "") + '" ';
						}
						if (typeof ui.Description != "undefined")
						{
							xmlUser += 'Description="' + (ui.Description || "") + '" ';
						}
						if (typeof ui.Remark != "undefined")
						{
							xmlUser += 'Remark="' + (ui.Remark || "") + '" ';
						}
						if (typeof ui.EnableWhiteList != "undefined")
						{
							xmlUser += 'EnableWhiteList="' + (ui.EnableWhiteList || 0) + '" ';
						}
						xmlUser += '/>';
						
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_UpdateUser">' + xmlUser + '</C></M>';
						break;
					case "DeleteUser":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_DeleteUser"><User Index="'+options.userIndex+'" /></C></M>';	
						break;
					case "AddUserResource":
					case "RemoveUserResource":
						if (!options.resSets || typeof options.resSets != "object")
						{
							P_Utils.Log(fn, "options.resSets null ...");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (options.resSets.constructor != Array)
						{
							options.resSets = [options.resSets];	
						}
						var xmlRes = '';
						for (var i = 0; i < options.resSets.length; i++)
						{
							xmlRes += '<Res PUID="'+options.resSets[i].PUID+'" Type="'+options.resSets[i].Type+'" Idx="'+options.resSets[i].Idx+'" />';	
						}
						var operationID = (action == 'AddUserResource' ? 'C_CAS_AddUserResource' : 'C_CAS_RemoveUserResource');
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="'+operationID+'"><User Index="'+options.userIndex+'">'+xmlRes+'</User></C></M>';
						break;
					case "AddUserLogicGroup":
					case "RemoveUserLogicGroup":
						if (!options.logicGroupIndexs || typeof options.logicGroupIndexs != "object")
						{
							P_Utils.Log(fn, "options.logicGroupIndexs null ...");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (options.logicGroupIndexs.constructor != Array)
						{
							options.logicGroupIndexs = [options.logicGroupIndexs];	
						}
						var xmlRes = '';
						for (var i = 0; i < options.logicGroupIndexs.length; i++)
						{
							xmlRes += '<LogicGroup Index="'+options.logicGroupIndexs[i].Index+'" />';	
						}
						var operationID = (action == 'AddUserLogicGroup' ? 'C_CAS_AddUserLogicGroup' : 'C_CAS_RemoveUserLogicGroup');
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="'+operationID+'"><User Index="'+options.userIndex+'">'+xmlRes+'</User></C></M>';
						break;
					case "QueryOnlineUsers":
						var xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryOnlineUsers"></C></M>';
						break;
					case "KickOnlineUsers":
						var xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_KickOnlineUsers"><OnlineUser CUID="'+options.CUID+'" /></C></M>';
						break;
						
				}
				var operator = P_IF.TransNUCommonMessage
				(
				 	_connStruct.nc, 
					_connStruct.session, 
					options.nuType_cui, 
					options.nuid_cui, 
					xmlRequest
				);
				if (operator.rv == P_Error.SUCCESS)
				{
					if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
					{
						var jsonResponse = (new XML.ObjTree()).parseXML(operator.response);
						
						if (jsonResponse && jsonResponse.Msg && jsonResponse.Msg.Cmd && jsonResponse.Msg.Cmd.NUErrorCode == 0)
						{ 
							switch (action)
							{
								case "QueryUserIndex":
								case "AddUser":
									if (jsonResponse.Msg.Cmd.User)
									{
										operator.response = Number(jsonResponse.Msg.Cmd.User.Index || 0);
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "UpdateUser":
								case "DeleteUser":
								case "AddUserResource":
								case "RemoveUserResource":
								case "AddUserLogicGroup":
								case "RemoveUserLogicGroup":
								case "KickOnlineUsers":
									break;
								case "QueryUserInformation":
									if (jsonResponse.Msg.Cmd.User)
									{
										operator.response = jsonResponse.Msg.Cmd.User;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "QueryUserResource":
									var Res = jsonResponse.Msg.Cmd.Res;
									if (typeof Res == "object")
									{
										if (Res.constructor != Array) Res = [Res];
										operator.response = Res;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "QueryUserLogicGroup":
									var LogicGroup = jsonResponse.Msg.Cmd.LogicGroup;
									if (typeof LogicGroup == "object")
									{
										if (LogicGroup.constructor != Array) LogicGroup = [LogicGroup];
										operator.response = LogicGroup;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "QuerySubUsers":
									var User = jsonResponse.Msg.Cmd.User;
									if (typeof User == "object")
									{
										if (User.constructor != Array) User = [User];
										operator.response = User;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "QueryOnlineUsers":
									var OnlineUser = jsonResponse.Msg.Cmd.OnlineUser;
									if (typeof OnlineUser == "object")
									{
										if (OnlineUser.constructor != Array) OnlineUser = [OnlineUser];
										operator.response = OnlineUser;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
							}
						}
						else
						{
							P_Utils.Log(fn, "NUErrorCode -> " + jsonResponse.Msg.Cmd.NUErrorCode);
							operator.rv = jsonResponse.Msg.Cmd.NUErrorCode;
						}
					} 
				}
				return operator;
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		end : true
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: User Management - 2014.02.14
    * ........................................................................................................
    **/
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: User Actor Management - 2014.02.17
    * ........................................................................................................
    **/
	UserActorManagementControl :
	{
		// - 获取系统支持的操作信息
		QueryOperationInfo : function (connectId)
		{
			return this.__Sdk_UAMCtl
			(
				"QueryOperationInfo",
				{
					connectId: connectId	
				}
			);
		},
		// - 获取预定义角色列表
		QueryPredefinedActorSets : function (connectId)
		{
			return this.__Sdk_UAMCtl
			(
				"QueryPredefinedActorSets",
				{
					connectId: connectId	
				}
			);
		},
		// - 获取自定义角色列表
		QueryUserdefinedActorSets : function (connectId)
		{
			return this.__Sdk_UAMCtl
			(
				"QueryUserdefinedActorSets",
				{
					connectId: connectId	
				}
			);
		},
		// - 获取角色对应的操作 actorType of 0 - Userdefined 1 - Predefined
		QueryActorOperation : function (connectId, actorType, actorIndex)
		{
			return this.__Sdk_UAMCtl
			(
				"QueryActorOperation",
				{
					connectId: connectId,
					actorType: actorType,
					actorIndex: actorIndex 
				}
			);
		},
		// - 获取角色对应的用户列表
		QueryActorUserSets : function (connectId, actorType, actorIndex)
		{
			return this.__Sdk_UAMCtl
			(
				"QueryActorUserSets",
				{
					connectId: connectId,
					actorType: actorType,
					actorIndex: actorIndex 
				}
			);
		},
		// - 添加自定义角色 admin才能使用
		AddUserdefinedActor : function (connectId, actorName, actorDesc, actorOptIndexs)
		{
			return this.__Sdk_UAMCtl
			(
				"AddUserdefinedActor",
				{
					connectId: connectId,
					actorName: actorName,
					actorDesc: actorDesc,
					actorOptIndexs: actorOptIndexs
				}
			);
		},
		// - 更新自定义角色 admin才能使用
		UpdateUserdefinedActor : function (connectId, actorIndex, actorName, actorDesc, actorOptIndexs)
		{
			return this.__Sdk_UAMCtl
			(
				"UpdateUserdefinedActor",
				{
					connectId: connectId,
					actorIndex: actorIndex,
					actorName: actorName,
					actorDesc: actorDesc,
					actorOptIndexs: actorOptIndexs
				}
			);
		},
		// - 删除自定义角色 admin才能使用
		DeleteUserdefinedActor : function (connectId, actorIndex)
		{
			return this.__Sdk_UAMCtl
			(
				"DeleteUserdefinedActor",
				{
					connectId: connectId,
					actorIndex: actorIndex
				}
			);
		},
		
		__Sdk_UAMCtl : function (action, options)
		{
			try 
			{
				var fn = "P_LY.UserActorManagementControl.__Sdk_UAMCtl - > " + action;
				
				var options = options || {};
				
				if (!options.connectId || !P_LY.Connections.get(options.connectId))
				{
					P_Utils.Log(fn, "options.connectId error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
				}
				var _connStruct = P_LY.Connections.get(options.connectId);
				
				options.nuType_cui = options.nuType_cui || 5;
				options.nuid_cui = options.nuid_cui || "005000100000000001";
				
				var xmlRequest;
				switch (action)
				{
					case "QueryOperationInfo":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryOperationInfo" /></M>';
						break;
					case "QueryPredefinedActorSets":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryPredefinedActorSets" /></M>';
						break;
					case "QueryUserdefinedActorSets":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryPredefinedActorSets" /></M>';
						break;
					case "QueryActorOperation":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryActorOperation"><Actor Type="'+options.actorType+'" Index="'+options.actorIndex+'" /></C></M>';
						break;
					case "QueryActorUserSets":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_QueryActorUserSets"><Actor Type="'+options.actorType+'" Index="'+options.actorIndex+'" /></C></M>';
						break;
					case "AddUserdefinedActor":
						if (!options.actorOptIndexs || typeof options.actorOptIndexs != "object")
						{
							P_Utils.Log(fn, "options.actorOptIndexs null ...");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (options.actorOptIndexs.constructor != Array)
						{
							options.actorOptIndexs = [options.actorOptIndexs];	
						}
						var xmlOpt = '';
						for (var i = 0; i < options.actorOptIndexs.length; i++)
						{
							xmlOpt += '<Opt Index="'+options.actorOptIndexs[i].Index+'" />';	
						}
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_AddUserdefinedActor"><Actor Name="'+options.actorName+'" Description="'+options.actorDesc+'">'+xmlOpt+'</Actor></C></M>';
						break;
					case "UpdateUserdefinedActor":
						if (!options.actorOptIndexs || typeof options.actorOptIndexs != "object")
						{
							P_Utils.Log(fn, "options.actorOptIndexs null ...");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
						}
						if (options.actorOptIndexs.constructor != Array)
						{
							options.actorOptIndexs = [options.actorOptIndexs];	
						}
						var xmlOpt = '';
						for (var i = 0; i < options.actorOptIndexs.length; i++)
						{
							xmlOpt += '<Opt Index="'+options.actorOptIndexs[i].Index+'" />';	
						}
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_UpdateUserdefinedActor"><Actor Index="'+options.actorIndex+'" Name="'+options.actorName+'" Description="'+options.actorDesc+'">'+xmlOpt+'</Actor></C></M>';
						break;
					case "DeleteUserdefinedActor":
						xmlRequest = '<?xml version="1.0" encoding="utf-8" ?><M Type="ComReq"><C Type="C" OptID="C_CAS_DeleteUserdefinedActor"><Actor Index="'+options.actorIndex+'" /></C></M>';
						break;
				}
				var operator = P_IF.TransNUCommonMessage
				(
				 	_connStruct.nc, 
					_connStruct.session, 
					options.nuType_cui, 
					options.nuid_cui, 
					xmlRequest
				);
				if (operator.rv == P_Error.SUCCESS)
				{
					if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
					{
						var jsonResponse = (new XML.ObjTree()).parseXML(operator.response);
						
						if (jsonResponse && jsonResponse.Msg && jsonResponse.Msg.Cmd && jsonResponse.Msg.Cmd.NUErrorCode == 0)
						{ 
							switch (action)
							{
								case "QueryOperationInfo":
								case "QueryUserdefinedActorSets":
								case "QueryActorOperation":
									var Opt = jsonResponse.Msg.Cmd.Opt;
									if (typeof Opt == "object")
									{
										if (Opt.constructor != Array) Opt = [Opt];
										operator.response = Opt;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "QueryPredefinedActorSets":
								case "QueryUserdefinedActorSets":
									var Actor = jsonResponse.Msg.Cmd.Actor;
									if (typeof Actor == "object")
									{
										if (Actor.constructor != Array) Actor = [Actor];
										operator.response = Actor;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "QueryActorUserSets":
									var User = jsonResponse.Msg.Cmd.User;
									if (typeof User == "object")
									{
										if (User.constructor != Array) User = [User];
										operator.response = User;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								case "AddUserdefinedActor":
									var Actor = jsonResponse.Msg.Cmd.Actor;
									if (typeof Actor == "object")
									{
										operator.response = Actor.Index;
									}
									else
									{
										operator.rv = P_Error.FAILED;	
									}
									break;
								
								case "UpdateUserdefinedActor":
								case "DeleteUserdefinedActor":
									break;
							}
						}
						else
						{
							P_Utils.Log(fn, "NUErrorCode -> " + jsonResponse.Msg.Cmd.NUErrorCode);
							operator.rv = jsonResponse.Msg.Cmd.NUErrorCode;
						}
					} 
				}
				return operator;
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		
		end : true
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: User Actor Management - 2014.02.17
    * ........................................................................................................
    **/
	/*
	---
	fn : CommonRequest
	desc : （支持多命令）联合请求
	author :
		- 
	time : 2013.10.15
	params : 
		- connectId(string) 连接ID
		- options(object) 选择传入参数
	remark :
		- options => 
		{
			cmdType(NPPILF.Enum.CmdType) * 请求命令类型，所有请求项符合此类型
			puid(string) 请求对象设备PUID，所有请求项针对此设备；直连设备时比较随意，可省略 
			xmlDstRes(xml string) * 联合请求命令
			xmlObjSets(xml string) 其他可能对象参数（如查询中心存储文件时需要，含<ObjSets>...</ObjSets>）
			domainRoad(string) 级联子域名称，根平台默认为空字符串
			returnType(string) 响应字符串格式xml | json(default)
			returnFlag(uint) 解析信号量（0内部解析，1留给上层自行解析，当returnType='xml'时，视returnFlag===1处理）
		}
		- options.xmlDstRes => 
			<DstRes Type=\"?\" Idx=\"?\" OptID=\"?\"  StreamType=\"?\"... ><Param ... /></DstRes>...<DstRes ...>...</DstRes>
		- options.xmlObjSets =>
			<ObjSets><Res ObjType=\"?\" ObjID=\"?\" Type=\"?\" Idx=\"?\" ></Res>...</ObjSets>
	...
	*/
	CommonRequest : function(connectId, options)
	{
		try
		{	
			var fn = "P_LY.CommonRequest";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
			}
		
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
			{
				var options = options || {};
				if (!options.cmdType || typeof P_LY.Enum.CmdType[options.cmdType] == "undefined")
				{
					P_Utils.Log(fn, "options.cmdType error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}
				if (_connStruct.connType != P_LY.Enum.ConnectionType.Device)
				{
					if (!options.puid || !P_Utils.Regexs.puid.test(options.puid))
					{
						P_Utils.Log(fn, "options.puid error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
					}
					else
					{
						var operator = P_LY.NPPSDKCommon.PUIsOnline(connectId, options.puid);
						if (operator.rv != P_Error.SUCCESS)
						{
							P_Utils.Log(fn, "options.puid not online~");
							//return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PU_OFFLINE);	
						}
					}
				}
				if (!options.xmlDstRes)
				{
					P_Utils.Log(fn, "options.xmlDstRes error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}
				
				options.domainRoad = options.domainRoad || "";
				options.returnType = options.returnType || "json";
				options.returnFlag = options.returnFlag || 0;
				
				var prio_operator = P_IF.GetUserPriority(_connStruct.nc, _connStruct.session);
				var priority = prio_operator.response || 1;
				
				var requestXML = '<?xml version="1.0" encoding="UTF-8"?><M Type="ComReq" DomainRoad="' + options.domainRoad + '"><C Type="' + options.cmdType + '" Prio="' + priority + '" EPID="' + (_connStruct.connParam.epId || "system") + '">' + options.xmlDstRes + '</C>' + (options.xmlObjSets || "") + '</M>';
				//P_Utils.Log(fn, "requestXML -> " + requestXML);
				if(requestXML.search('C_PTZ_StartTurnLeft') > 0){
					//console.log(requestXML)
					//requestXML = '<?xml version="1.0" encoding="utf-8"?><M Type="ComReq" DomainRoad=""><C Type="C" Prio="1" EPID="system"><Res Type="ST" Idx="0" OptID="C_CSS_QueryStorageFiles"><Param Offset="0" Count="200" Begin="1414857600" End="1414943999" Type="0" /></Res></C><OSets><Res OType="201" OID="201115201224737167" Type="IV" Idx="0" /><Res OType="201" OID="201000000000000015" Type="IV" Idx="0" /></OSets></M>';
					//requestXML = '<?xml version="1.0" encoding="utf-8"?><M Type="ComReq" DomainRoad=""><C Type="C" Prio="1" EPID="system"><Res Type="ST" Idx="0" OptID="C_CSS_QueryStorageFiles"><Param Offset="0" Count="200" Begin="1414857600" End="1414943999" Type="0" /></Res></C></M>';
				}

				var operator = P_IF.TransCommonMessage(_connStruct.nc, _connStruct.session, (options.puid || ""), requestXML);
//				console.log(operator);
				if (operator.rv == P_Error.SUCCESS)
				{
					if (options.returnType.toLowerCase() == "json")
					{
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var _response = (new XML.ObjTree()).parseXML(operator.response);
							operator.response = _response;
						}
					}
				}
				return operator;
			}
			else
			{
				P_Utils.Log(fn, "connectId hav't built ~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},  
	CommonRequestEx : function(connectId, options)
	{
		try
		{	
			var fn = "P_LY.CommonRequestEx";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
			}
		
			var _connStruct = P_LY.Connections.get(connectId);
			
			if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
			{
				var options = options || {};
				if (!options.cmdType || typeof P_LY.Enum.CmdType[options.cmdType] == "undefined")
				{
					P_Utils.Log(fn, "options.cmdType error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}
				if (_connStruct.connType != P_LY.Enum.ConnectionType.Device)
				{
					if (!options.puid || !P_Utils.Regexs.puid.test(options.puid))
					{
						P_Utils.Log(fn, "options.puid error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
					}
					else
					{
						var operator = P_LY.NPPSDKCommon.PUIsOnline(connectId, options.puid);
						if (operator.rv != P_Error.SUCCESS)
						{
							P_Utils.Log(fn, "options.puid not online~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PU_OFFLINE);	
						}
					}
				}
				if (!options.xmlDstRes)
				{
					P_Utils.Log(fn, "options.xmlDstRes error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}
				
				options.domainRoad = options.domainRoad || "";
				options.returnType = options.returnType || "json";
				options.returnFlag = options.returnFlag || 0;
				options.language = options.language || "";
				var prio_operator = P_IF.GetUserPriority(_connStruct.nc, _connStruct.session);
				var priority = prio_operator.response || 1;
				
				var requestXML = '<?xml version="1.0" encoding="UTF-8"?><M Type="ComReq" DomainRoad="' + options.domainRoad + '"><C Type="' + options.cmdType + '" Prio="' + priority + '" EPID="' + (_connStruct.connParam.epId || "system") + '"  Lang="'+options.language+'">' + options.xmlDstRes + '</C>' + (options.xmlObjSets || "") + '</M>';
				
				//console.log(requestXML);
				P_Utils.Log(fn, "requestXML -> " + requestXML);
				
				var operator = P_IF.TransCommonMessage(_connStruct.nc, _connStruct.session, (options.puid || ""), requestXML);
				if (operator.rv == P_Error.SUCCESS)
				{
					if (options.returnType.toLowerCase() == "json")
					{
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var _response = (new XML.ObjTree()).parseXML(operator.response);
							
							if (options.returnFlag == "0")
							{
								if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && typeof _response.Msg.Cmd.NUErrorCode != "undefined" && _response.Msg.Cmd.NUErrorCode != "0")
								{
									// - 返回具体的NUErrorCode错误码
									operator.rv = _response.Msg.Cmd.NUErrorCode || -1;
								}
								else if (typeof _response == "object" && _response.Msg && _response.Msg.Cmd && _response.Msg.Cmd.DstRes) 
								{
									_response = _response.Msg.Cmd.DstRes || {};
									if (typeof _response == "object" && (_response.constructor != Array || _response.length == 1))
									{
										if (_response.constructor == Array)
										{
											_response = _response[0];	
										}
										if (typeof _response.ErrorCode != "undefined" && _response.ErrorCode != "0")
										{
											// - 返回具体的ErrorCode错误码
											operator.rv = _response.ErrorCode || -1;
										}
										// - 单个节点就解析单个的
										if (typeof _response.Param != "undefined")
										{
											_response = _response.Param;
										}
									}
								}
							}
							//operator.response = operator.response;
						}	
					}
				}
				return operator;
			}
			else
			{
				P_Utils.Log(fn, "connectId hav't built ~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},  
	CommonSPRequest : function(connectId, options)
	{
		try
		{	
			var fn = "P_LY.Request";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);	
			}
		
			var _connStruct = P_LY.Connections.get(connectId);
			
			if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connected)
			{
				var options = options || {};
				if (!options.cmdType || typeof P_LY.Enum.CmdType[options.cmdType] == "undefined")
				{
					P_Utils.Log(fn, "options.cmdType error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}

				if (!options.xmlDstRes)
				{
					P_Utils.Log(fn, "options.xmlDstRes error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
				}

				if (!options.dstType)
				{
					options.dstType ="";
				}

				if (!options.dstId)
				{
					options.dstId ="";
				}
				
				options.domainRoad = options.domainRoad || "";
				options.returnType = options.returnType || "json";
				options.returnFlag = options.returnFlag || 0;
				options.language = options.language || "";
				var prio_operator = P_IF.GetUserPriority(_connStruct.nc, _connStruct.session);
				var priority = prio_operator.response || 1;
				
				var requestXML = '<?xml version="1.0" encoding="UTF-8"?><M Type="ComReq" DomainRoad="' + options.domainRoad + '"><C Type="' + options.cmdType + '" Prio="' + priority + '" EPID="' + (_connStruct.connParam.epId || "system") + '"  Lang="'+options.language+'">' + options.xmlDstRes + '</C>' + (options.xmlObjSets || "") + '</M>';
				
				var operator = P_IF.TransSPCommonMessage(_connStruct.nc, _connStruct.session, options.dstType,options.dstId, requestXML);

				if (operator.rv == P_Error.SUCCESS)
				{
					if (options.returnType.toLowerCase() == "json")
					{
						if (typeof XML != "undefined" && typeof XML.ObjTree != "undefined")
						{
							var _response = (new XML.ObjTree()).parseXML(operator.response);
							operator.response = _response;
						}
					}
				}
				return operator;
			}
			else
			{
				P_Utils.Log(fn, "connectId hav't built ~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
			}
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},  
	// - 配置项
	Config :
	{
		SetResourceDescription:function(connectId,puid,type,idx,description){

			var handle_operator = P_LY.NPPSDKCommon.GetHandle
			(
				connectId,
				puid,
				type,
				idx
			);

			if (handle_operator.rv != P_Error.SUCCESS) 
			{
				return handle_operator;
			}else{
				var _connStruct = P_LY.Connections.get(connectId);
				var operator = P_IF.SetResourceDesc(_connStruct.nc, handle_operator.response,description);
				
				return operator;
				
			}
		},
		SetResourceName:function(connectId,puid,type,idx,name){
			var handle_operator = P_LY.NPPSDKCommon.GetHandle
			(
				connectId,
				puid,
				type,
				idx
			);
			

			if (handle_operator.rv != P_Error.SUCCESS) 
			{
				return handle_operator;
			}else{
				var _connStruct = P_LY.Connections.get(connectId);
				var operator = P_IF.SetResourceName(_connStruct.nc, handle_operator.response,name);
				
				return operator;
				
			}
		},
		SetResourceEnable:function(connectId,puid,type,idx,enable){
			var handle_operator = P_LY.NPPSDKCommon.GetHandle
			(
				connectId,
				puid,
				type,
				idx
			);

			if (handle_operator.rv != P_Error.SUCCESS) 
			{
				return handle_operator;
			}else{
				var _connStruct = P_LY.Connections.get(connectId);
				var operator = P_IF.SetResourceEnable(_connStruct.nc, handle_operator.response,enable);
				
				return operator;
				
			}
		},
		GetSimple : function(connectId, puid, resType, resIdx, configID, streamType, customParams)
		{
			var operator = P_LY.CommonRequest
			(
				connectId, 
				{
					cmdType : P_LY.Enum.CmdType.G,
					puid : puid || "",
					xmlDstRes : '<Res Type="'+(resType||"")+'" Idx="'+(typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : "")+'" OptID="'+(configID||"")+'" '+(streamType?'StreamType="'+streamType+'"' : '')+'></Res>',
					returnFlag : (typeof customParams == "object" ? (customParams.returnFlag || 0) : 0)
				}
			);
			
			if (operator.rv == P_Error.SUCCESS)
			{
				if (typeof operator.response.Value != "undefined")
				{
					// - 截取具体的值即可
					operator.response = operator.response.Value;
				}
			}
			return operator;
		},
		SetSimple : function(connectId, puid, resType, resIdx, configID, streamType, value, customParams)
		{
			return P_LY.CommonRequest
			(
				connectId, 
				{
					cmdType : P_LY.Enum.CmdType.S,
					puid : puid || "",
					xmlDstRes : '<Res Type="'+(resType||"")+'" Idx="'+(typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : "")+'" OptID="'+(configID||"")+'" '+(streamType?'StreamType="'+streamType+'"' : '')+'><Param Value="'+(typeof value != "undefined" && value != null ? value : '')+'" /></Res>',
					returnFlag : (typeof customParams == "object" ? (customParams.returnFlag || 0) : 0)
				}
			);	
		},
		/*
		---
		remark :
			- reqParams =>
			{
				puid(string) * 设备PUID
				resType(P_LY.Enum.PuResourceType) * 资源类型
				resIdx(uint) * 资源索引
				configID(string) * 配置命令
				streamType(P_LY.Enum.StreamType) 流类型
				returnType(string) xml | json(default)
				returnFlag(uint) 0(default)返回有用的节点 | 1自行解析，当returnType='xml'时，视returnFlag===1处理
				cmdType(P_LY.Enum.CmdType) 默认GET
				domainRoad(string) 级联子域名称，根平台默认为空字符串
			}
		...
		*/
		GetComplex : function(connectId, reqParams)
		{
			var reqParams = reqParams || {};
			return P_LY.CommonRequest
			(
				connectId,
				{
					cmdType : (reqParams.cmdType || P_LY.Enum.CmdType.G),
					puid : reqParams.puid || "",
					xmlDstRes : '<Res Type="'+(reqParams.resType||"")+'" Idx="'+(typeof reqParams.resIdx != "undefined" && !isNaN(reqParams.resIdx) ? reqParams.resIdx : "")+'" OptID="'+(reqParams.configID||"")+'" '+(reqParams.streamType?'StreamType="'+reqParams.streamType+'"' : '')+'></Res>',
					xmlObjSets : (reqParams.xmlObjSets || ""),
					domainRoad : (reqParams.domainRoad || ""),
					returnType : (reqParams.returnType || "json"),
					returnFlag : (reqParams.returnFlag || 0)	
				}
			);
		},

		GetComplexEx : function(connectId, reqParams)
		{	
			var reqParams = reqParams || {};
		
			return P_LY.CommonRequestEx
			(
				connectId,
				{
					cmdType : (reqParams.cmdType || P_LY.Enum.CmdType.G),
					puid : reqParams.puid || "",
					language:reqParams.language,
					xmlDstRes : reqParams.dstRes,//'<Res Type="'+(reqParams.resType||"")+'" Idx="'+(typeof reqParams.resIdx != "undefined" && !isNaN(reqParams.resIdx) ? reqParams.resIdx : "")+'" OptID="'+(reqParams.configID||"")+'" '+(reqParams.streamType?'StreamType="'+reqParams.streamType+'"' : '')+'></Res>',
					xmlObjSets : (reqParams.xmlObjSets || ""),
					domainRoad : (reqParams.domainRoad || ""),
					returnType : (reqParams.returnType || "json"),
					returnFlag : (reqParams.returnFlag || 0)	
				}
			);
		},
		
		/*
		---
		remark :
			- setParams =>
			{
				puid(string) * 设备PUID
				resType(P_LY.Enum.PuResourceType) * 资源类型
				resIdx(uint) * 资源索引
				configID(string) * 配置命令
				param(xml string) * 含<Param ...>...</Param> 
				streamType(P_LY.Enum.StreamType) 流类型
				returnType(string) xml | json(default)
				returnFlag(uint) 0(default)返回有用的节点 | 1自行解析，当returnType='xml'时，视returnFlag===1处理
				cmdType(P_LY.Enum.CmdType) 默认SET
				domainRoad(string) 级联子域名称，根平台默认为空字符串
			}
		...
		*/
		SetComplex : function(connectId, setParams)
		{
			var setParams = setParams || {};
			return P_LY.CommonRequest
			(
				connectId,
				{
					cmdType : (setParams.cmdType || P_LY.Enum.CmdType.S),
					puid : setParams.puid || "",
					xmlDstRes : '<Res Type="'+(setParams.resType||"")+'" Idx="'+(typeof setParams.resIdx != "undefined" && !isNaN(setParams.resIdx) ? setParams.resIdx : "")+'" OptID="'+(setParams.configID||"")+'" '+(setParams.streamType?'StreamType="'+setParams.streamType+'"' : '')+'>' + (setParams.param || '<Param />') + '</Res>',
					xmlObjSets : (setParams.xmlObjSets || ""),
					domainRoad : (setParams.domainRoad || ""),
					returnType : (setParams.returnType || "json"),
					returnFlag : (setParams.returnFlag || 0)	
				}
			);
		},
		SetComplexEx : function(connectId, setParams)
		{
		
			var setParams = setParams || {};
			return P_LY.CommonRequest
			(
				connectId,
				{
					cmdType : (setParams.cmdType || P_LY.Enum.CmdType.S),
					puid : setParams.puid || "",
					xmlDstRes : setParams.dstRes,//'<Res Type="'+(setParams.resType||"")+'" Idx="'+(typeof setParams.resIdx != "undefined" && !isNaN(setParams.resIdx) ? setParams.resIdx : "")+'" OptID="'+(setParams.configID||"")+'" '+(setParams.streamType?'StreamType="'+setParams.streamType+'"' : '')+'>' + (setParams.param || '<Param />') + '</Res>',
					xmlObjSets : (setParams.xmlObjSets || ""),
					domainRoad : (setParams.domainRoad || ""),
					returnType : (setParams.returnType || "json"),
					returnFlag : (setParams.returnFlag || 0)	
				}
			);
		}
	},
	// - 控制命令
	Control : 
	{
		/*
		---
		remark :
			- reqParams =>
			{
				puid(string) * 设备PUID
				resType(P_LY.Enum.PuResourceType) * 资源类型
				resIdx(uint) * 资源索引
				controlID(string) * 控制命令
				param(xml string) * 含<Param ...>...</Param> 
				streamType(P_LY.Enum.StreamType) 流类型
				returnType(string) xml | json(default)
				returnFlag(uint) 0(default)返回有用的节点 | 1自行解析，当returnType='xml'时，视returnFlag===1处理
				cmdType(P_LY.Enum.CmdType) 默认CTL
				xmlObjSets(xml string) 其他可能对象参数（如查询中心存储文件时需要，含<ObjSets>...</ObjSets>）
				domainRoad(string) 级联子域名称，根平台默认为空字符串
			}
		...
		*/
		CommonGet : function(connectId, reqParams)
		{
			var reqParams = reqParams || {};
			
			if(reqParams.controlID == "C_CSS_QueryStorageFiles"   ){
				return P_LY.CommonSPRequest
				(
					connectId,
					{
						cmdType : (reqParams.cmdType || P_LY.Enum.CmdType.C),
						puid : reqParams.puid || "",
						dstType:'14',
						dstId:'',
						xmlDstRes : '<Res Type="'+(reqParams.resType||"")+'" Idx="'+(typeof reqParams.resIdx != "undefined" && !isNaN(reqParams.resIdx) ? reqParams.resIdx : "")+'" OptID="'+(reqParams.controlID||"")+'" '+(reqParams.streamType?'StreamType="'+reqParams.streamType+'"' : '')+'>' + (reqParams.param || '<Param />') + '</Res>',
						xmlObjSets : (reqParams.xmlObjSets || ""),
						domainRoad : (reqParams.domainRoad || ""),
						returnType : (reqParams.returnType || "json"),
						returnFlag : (reqParams.returnFlag || 0)	
					}
				);
				
			}else if(reqParams.controlID == "C_GS_QueryHistoryGPSData"){

				return P_LY.CommonSPRequest
				(
					connectId,
					{
						cmdType : (reqParams.cmdType || P_LY.Enum.CmdType.C),
						puid : reqParams.puid || "",
						dstType:'33',
						dstId:'',
						xmlDstRes : '<Res Type="'+(reqParams.resType||"")+'" Idx="'+(typeof reqParams.resIdx != "undefined" && !isNaN(reqParams.resIdx) ? reqParams.resIdx : "")+'" OptID="'+(reqParams.controlID||"")+'" '+(reqParams.streamType?'StreamType="'+reqParams.streamType+'"' : '')+'>' + (reqParams.param || '<Param />') + '</Res>',
						xmlObjSets : (reqParams.xmlObjSets || ""),
						domainRoad : (reqParams.domainRoad || ""),
						returnType : (reqParams.returnType || "json"),
						returnFlag : (reqParams.returnFlag || 0)	
					}
				);
			}else{
				return P_LY.CommonRequest
				(
					connectId,
					{
						cmdType : (reqParams.cmdType || P_LY.Enum.CmdType.C),
						puid : reqParams.puid || "",
						xmlDstRes : '<Res Type="'+(reqParams.resType||"")+'" Idx="'+(typeof reqParams.resIdx != "undefined" && !isNaN(reqParams.resIdx) ? reqParams.resIdx : "")+'" OptID="'+(reqParams.controlID||"")+'" '+(reqParams.streamType?'StreamType="'+reqParams.streamType+'"' : '')+'>' + (reqParams.param || '<Param />') + '</Res>',
						xmlObjSets : (reqParams.xmlObjSets || ""),
						domainRoad : (reqParams.domainRoad || ""),
						returnType : (reqParams.returnType || "json"),
						returnFlag : (reqParams.returnFlag || 0)	
					}
				);
				
			}
		},
		/*
		---
		remark :
			- setParams =>
			{
				puid(string) * 设备PUID
				resType(P_LY.Enum.PuResourceType) * 资源类型
				resIdx(uint) * 资源索引
				controlID(string) * 控制命令
				param(xml string) * 含<Param ...>...</Param> 
				streamType(P_LY.Enum.StreamType) 流类型
				returnType(string) xml | json(default)
				returnFlag(uint) 0(default)返回有用的节点 | 1自行解析，当returnType='xml'时，视returnFlag===1处理
				cmdType(P_LY.Enum.CmdType) 默认CTL
				xmlObjSets(xml string) 其他可能对象参数（如查询中心存储文件时需要，含<ObjSets>...</ObjSets>）
				domainRoad(string) 级联子域名称，根平台默认为空字符串
			}
		...
		*/
		CommonSet : function(connectId, setParams)
		{
			var setParams = setParams || {};
			return P_LY.CommonRequest
			(
				connectId,
				{
					cmdType : (setParams.cmdType || P_LY.Enum.CmdType.C),
					puid : setParams.puid || "",
					xmlDstRes : '<Res Type="'+(setParams.resType||"")+'" Idx="'+(typeof setParams.resIdx != "undefined" && !isNaN(setParams.resIdx) ? setParams.resIdx : "")+'" OptID="'+(setParams.controlID||"")+'" '+(setParams.streamType?'StreamType="'+setParams.streamType+'"' : '')+'>' + (setParams.param || '<Param />') + '</Res>',
					xmlObjSets : (setParams.xmlObjSets || ""),
					domainRoad : (setParams.domainRoad || ""),
					returnType : (setParams.returnType || "json"),
					returnFlag : (setParams.returnFlag || 0)	
				}
			);	
		}
	},
	
	/* 平台命令 */
	// - 获取资源是否可用
	GetResourceUsable : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.GetResourceUsable, puid);
	},
	
	/* 设备命令 */
	// - 所有资源描述 
	ST_GetResDescSets : function (connectId, puid) 
	{
		return P_LY.Config.GetSimple(connectId, (puid || ""), P_LY.Enum.PuResourceType.ST, 0, "F_ST_ResDescSets");
	},
	// - 获取PUID
	ST_GetPUID : function (connectId, puid)
	{
		return P_LY.Config.GetSimple(connectId, (puid || ""), P_LY.Enum.PuResourceType.ST, 0, "F_ST_PUID");
	},
	// - 设置PUID
	ST_SetPUID : function (connectId, puid, newPuidValue)
	{
		return P_LY.Config.SetSimple(connectId, (puid || ""), P_LY.Enum.PuResourceType.ST, 0, "F_ST_PUID", "", newPuidValue);
	},
	// - 平台地址
	ST_GetPlatformAddr : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetPlatformAddr, puid);
	},
	ST_SetPlatformAddr : function(connectId, puid, platformAddr)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_SetPlatformAddr, puid, platformAddr);
	}, 
	// - 设备接入密码
	ST_GetRegPsw : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetRegPsw, puid);
	}, 
	ST_SetRegPsw : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_SetRegPsw, puid);
	}, 
	// - 设备型号
	ST_GetModel : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetModel, puid);
	},
	// - 软件版本号
	ST_GetSoftwareVersion : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetSoftwareVersion, puid);
	},
	// - 硬件型号
	ST_GetHardwareModel : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetHardwareModel, puid);
	},
	// - 硬件版本
	ST_GetHardwareVersion : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetHardwareVersion, puid);
	},
	// - 厂商ID
	ST_GetProducerID : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetProducerID, puid);
	},
	// - 设备ID
	ST_GetDeviceID : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetDeviceID, puid);
	},
	// - 设备时区
	ST_GetTZ : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetTZ, puid);
	},
	ST_SetTZ : function(connectId, puid, timeZone)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_SetTZ, puid, timeZone);
	},
	// - 设备与平台同步时间的间隔
	ST_GetTimeSyncInterval : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetTimeSyncInterval, puid);
	},
	ST_SetTimeSyncInterval : function(connectId, puid, nTimeSyncInterval)

	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_SetTimeSyncInterval, puid, nTimeSyncInterval);
	},
	// - OEM数据
	ST_GetOEMData : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetOEMData, puid);
	},
	ST_SetOEMData : function(connectId, puid, OEMData)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_SetOEMData, puid, OEMData);
	},
	// - 发送让视频服务器重启的命令
	ST_Reboot : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_Reboot, puid);
	},
	// - 时间
	ST_GetTime : function(connectId, puid)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_GetTime, puid);
	},
	ST_SetTime : function(connectId, puid, time)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ST_SetTime, puid, time);
	},
	
	// - s - 前端联动配置 @ 2014.01.02 ------------------------------------------------
	/*
	---
	desc: 获取支持的事件
	...
	*/
	ST_GetSupportedEventSets : function (connectId, puid)
	{
		try 
		{
			var fn = "P_LY.ST_GetSupportedEventSets";
			
			var operator = P_LY.Config.GetComplex
			(
				connectId, 
				{
					puid: puid,
					resType: P_LY.Enum.PuResourceType.ST,
					resIdx: 0,
					configID: "F_ST_SupportedEventSets"
				}
			);
			if (operator.rv == P_Error.SUCCESS)
			{
				var _response = operator.response;
				if (typeof _response != "undefined" && typeof _response.SrcRes != "undefined")
				{
					if (typeof _response.SrcRes == "object")
					{
						if (_response.SrcRes.constructor != Array)
						{
							_response.SrcRes = [_response.SrcRes];	
						}
						var map = {};
						for (var i = 0; i < _response.SrcRes.length; i++)
						{
							var item = _response.SrcRes[i];
							if (item.Type && item.Event)
							{
								if (typeof map[item.Type] == "undefined")
								{
									map[item.Type] = [];
								}
								if (item.Event.constructor != Array) 

								{
									item.Event = [item.Event];
								}
								for (var j = 0; j < item.Event.length; j++)
								{
									switch (item.Event[j])
									{
										case "alertIn":
											item.Event[j] = "EVT_IDL_AlertIn";
											break;
										case "signalLost":
											item.Event[j] = "EVT_IV_SignalLost";
											break;
										case "motDetect":
											item.Event[j] = "EVT_IV_MotionDetected";
											break;
										case "coverDetect":
											item.Event[j] = "EVT_IV_CoverDetected";
											break;
									}
								}
								map[item.Type] = map[item.Type].concat(item.Event);
							}
						}
						operator.response = [];
						for (var key in map)
						{
							if (typeof map[key] == "object" && map[key].constructor == Array && map[key].length > 0)
							{
								operator.response.push({"Type": key, "Event": map[key]});	
							}
						}
					}
				} 
			} 
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD, e);
		}
	},
	/*
	---
	desc: 获取支持的动作
	returns:
		- response::Array 如["alertOut", "record", ...]
		含义：alertOut 			报警输出
			sendEmail			发送电子邮件,并附抓图
			ftpUpload			抓拍上传到FTP服务器
			record				开始前端录像
			snapShot				开始前端抓拍
			moveToPresetPosition	前往预置位
			online				上线
			sendSMS				发送报警短信
			preTransmitVideo		预传视频
			preSnapshot			预抓图
			preTransmitAudio		预传音频
			preTransmitGPS		预传定位信息
			uploadAlert			上传报警信息
			uploadRecord		上传录像,实时上传视频和音频
			playTipVoice		播放提示声音
	...
	*/
	ST_GetSupportedActionSets : function (connectId, puid)
	{
		try 
		{
			var fn = "P_LY.ST_GetSupportedActionSets";
			
			var operator = P_LY.Config.GetComplex
			(
				connectId, 
				{
					puid: puid,
					resType: P_LY.Enum.PuResourceType.ST,
					resIdx: 0,
					configID: "F_ST_SupportedActionSets"
				}
			);
			if (operator.rv == P_Error.SUCCESS)
			{
				if (typeof operator.response != "undefined" && typeof operator.response.Action != "undefined")
				{
					if (operator.response.Action.constructor != Array)
					{
						operator.response.Action = [operator.response.Action];
					}
					operator.response = operator.response.Action;
				}
			}
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD, e);
		}
	},
	/*
	---
	desc: 获取前端联动配置
	...
	*/
	ST_GetDeviceLinkActions : function (connectId, puid, returnType)
	{
		try
		{
			var fn = "P_LY.ST_GetDeviceLinkActions";
			
			return P_LY.Config.GetComplex
			(
				connectId, 
				{
					puid: puid,
					resType: P_LY.Enum.PuResourceType.ST,
					resIdx: 0,
					configID: "F_ST_DeviceLinkActions",
					returnType: (returnType != "xml" ? "json" : "xml")
				}
			);
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD, e);
		}
	},
	/*
	---
	desc: 设置前端联动配置
	...
	*/
	ST_SetDeviceLinkActions : function (connectId, puid, xmlDevLinkActions)
	{
		try
		{
			var fn = "P_LY.ST_SetDeviceLinkActions";
			
			return P_LY.Config.SetComplex
			(
				connectId, 
				{
					puid: puid,
					resType: P_LY.Enum.PuResourceType.ST,
					resIdx: 0,
					configID: "F_ST_DeviceLinkActions",
					param: "<Param>" + xmlDevLinkActions + "</Param>"
				}
			);
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD, e);
		}
	},
	// - e - 前端联动配置 @ 2014.01.02 ------------------------------------------------
	
	/* 视频命令 */
	// - 摄像头状态
	IV_GetCameraStatus : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetCameraStatus, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	// - 亮度
	IV_GetBrightness : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetBrightness, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetBrightness : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetBrightness, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	IV_PreviewBrightness : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_PreviewBrightness, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	// - 对比度
	IV_GetContrast : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetContrast, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetContrast : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetContrast, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	IV_PreviewContrast : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_PreviewContrast, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	// - 色调
	IV_GetHue : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetHue, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetHue : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetHue, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	IV_PreviewHue : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_PreviewHue, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	// - 饱和度
	IV_GetSaturation : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetSaturation, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetSaturation : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetSaturation, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	IV_PreviewSaturation : function(connectId, puid, ivIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_PreviewSaturation, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, value);
	},
	// - 支持的编码算法 
	IV_GetSupportedEncoderSets : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetSupportedEncoderSets, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	// - 编码算法
	IV_GetEncoder : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetEncoder, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetEncoder : function(connectId, puid, ivIndex, encoder)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetEncoder, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, encoder);
	},
	// - 支持的流类型
	IV_GetSupportedStreamTypeSets : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetSupportedStreamTypeSets, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex); 
	},
	// - 支持的编码分辨率
	IV_GetSupportedResolutionSets : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetSupportedResolutionSets, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType); 
	},
	// - 编码分辨率
	IV_GetResolution : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetResolution, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetResolution : function(connectId, puid, ivIndex, streamType, resolution)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetResolution, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, resolution);
	},
	// - 支持的编码质量控制模式
	IV_GetSupportedQualityControlModeSets : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetSupportedQualityControlModeSets, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType); 
	},
	// - 编码质量控制模式
	IV_GetQualityControlMode : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetQualityControlMode, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetQualityControlMode : function(connectId, puid, ivIndex, streamType, mode)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetQualityControlMode, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, mode);
	},
	// - 目标码率
	IV_GetBitRate : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetBitRate, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetBitRate : function(connectId, puid, ivIndex, streamType, bitRate)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetBitRate, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, bitRate);
	},
	// - 目标帧率
	IV_GetFrameRate : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetFrameRate, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetFrameRate : function(connectId, puid, ivIndex, streamType, frameRate)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetFrameRate, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, frameRate);
	},
	// - 目标清晰度
	IV_GetImageDefinition : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetImageDefinition, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetImageDefinition : function(connectId, puid, ivIndex, streamType, definition)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetImageDefinition, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, definition);
	},
	// - 是否叠加时间
	IV_GetAddTime : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetAddTime, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetAddTime : function(connectId, puid, ivIndex, streamType, bValue)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetAddTime, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, bValue);
	},
	// - 是否叠加文字
	IV_GetAddText : function(connectId, puid, ivIndex, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetAddText, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType);
	},
	IV_SetAddText : function(connectId, puid, ivIndex, streamType, bValue)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetAddText, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, streamType, bValue);
	},
	// - 叠加的文字内容
	IV_GetTextAdd : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetTextAdd, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetTextAdd : function(connectId, puid, ivIndex, text)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetTextAdd, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, text);
	},
	// - 屏蔽区域
	IV_GetCoverRegions : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetCoverRegions, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetCoverRegions : function(connectId, puid, ivIndex, regions)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetCoverRegions, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, regions);
	},
	// - 定时录像时间表
	IV_GetRecordSchedule : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetRecordSchedule, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetRecordSchedule : function(connectId, puid, ivIndex, timeMap)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetRecordSchedule, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, timeMap);
	},
	// - 是否录制对应音频
	IV_GetRecordAudio : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_GetRecordAudio, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	IV_SetRecordAudio : function(connectId, puid, ivIndex, bValue)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_SetRecordAudio, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex, bValue);
	},
	// - 请求发送I帧
	IV_StartKeyFrame : function(connectId, puid, ivIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IV_StartKeyFrame, puid, P_LY.Enum.PuResourceType.VideoIn, ivIndex);
	},
	
	/* 输入音频 */
	// - 输入音量
	IA_GetVolume : function(connectId, puid, iaIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_GetVolume, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex);
	},
	IA_SetVolume : function(connectId, puid, iaIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_SetVolume, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex, value);
	},
	IA_PreviewVolume : function(connectId, puid, iaIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_PreviewVolume, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex, value);
	},
	// - 支持的编码算法
	IA_GetSupportedEncoderSets : function(connectId, puid, iaIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_GetSupportedEncoderSets, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex);
	},
	// - 编码算法
	IA_GetEncoder : function(connectId, puid, iaIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_GetEncoder, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex);
	},
	IA_SetEncoder : function(connectId, puid, iaIndex, encoder)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_SetEncoder, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex, encoder);
	},
	// - 支持的流类型
	IA_GetSupportedStreamTypeSets : function(connectId, puid, iaIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IA_GetSupportedStreamTypeSets, puid, P_LY.Enum.PuResourceType.AudioIn, iaIndex);
	},
	/* 串口命令 */
	// - 串口模式
	SP_GetMode : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetMode, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	// - 支持的波特率
	SP_GetSupportedBaudRateSets : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetSupportedBaudRateSets, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	// - 波特率
	SP_GetBaudRate : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetBaudRate, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	SP_SetBaudRate : function(connectId, puid, spIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_SetBaudRate, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex, value);
	},
	// - 支持的数据位
	SP_GetSupportedDataBitsSets : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetSupportedDataBitsSets, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	// - 数据位
	SP_GetDataBits : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetDataBits, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	SP_SetDataBits : function(connectId, puid, spIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_SetDataBits, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex, value);
	},
	// - 支持的校验位
	SP_GetSupportedParitySets : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetSupportedParitySets, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	// - 校验位
	SP_GetParity : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetParity, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	SP_SetParity : function(connectId, puid, spIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_SetParity, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex, value);
	},
	// - 支持的停止位
	SP_GetSupportedStopBitsSets : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetSupportedStopBitsSets, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	// - 停止位
	SP_GetStopBits : function(connectId, puid, spIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_GetStopBits, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex);
	},
	SP_SetStopBits : function(connectId, puid, spIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_SetStopBits, puid, P_LY.Enum.PuResourceType.SerialPort, spIndex, value);
	},
	// - 发送串口数据
	SP_WriteData : function(connectId, puid, spIndex, dataArr)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SP_WriteData, puid, P_LY.Enum.PuResourceType.SerialPort, dataArr);
	},
	
	/* 云台命令 */
	// - 所接串口编号
	PTZ_GetConnectedSerialPort : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetConnectedSerialPort, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	PTZ_SetConnectedSerialPort : function(connectId, puid, ptzIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetConnectedSerialPort, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, value);
	},
	// - 所有预置位名称
	PTZ_GetPresetPositionSets : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetPresetPositionSets, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	PTZ_SetPresetPositionSets : function(connectId, puid, ptzIndex, posMap)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetPresetPositionSets, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, posMap);
	},
	// - 所有辅助设备名称
	PTZ_GetSecondaryDeviceSets : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetSecondaryDeviceSets, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	PTZ_SetSecondaryDeviceSets : function(connectId, puid, ptzIndex, devMap)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetSecondaryDeviceSets, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, devMap);
	},
	// - 支持的云台协议
	PTZ_GetSupportedProtocolSets : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetSupportedProtocolSets, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 控制协议
	PTZ_GetProtocol : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetProtocol, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	PTZ_SetProtocol : function(connectId, puid, ptzIndex, protocol)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetProtocol, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, protocol);
	},
	// - 解码器地址
	PTZ_GetDecoderAddress : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetDecoderAddress, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	PTZ_SetDecoderAddress : function(connectId, puid, ptzIndex, decoderAddr)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetDecoderAddress, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, decoderAddr);
	},
	// - PTZ移动的速度
	PTZ_GetSpeed : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_GetSpeed, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	PTZ_SetSpeed : function(connectId, puid, ptzIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetSpeed, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, value);
	},
	// - 向左转（动）
	PTZ_StartTurnLeft : function(connectId, puid, ptzIndex, turnDegree)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StartTurnLeft, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (turnDegree || 0));
	},
	// - 向右转（动）
	PTZ_StartTurnRight : function(connectId, puid, ptzIndex, turnDegree)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StartTurnRight, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (turnDegree || 0));
	},
	// - 向上转（动）
	PTZ_StartTurnUp : function(connectId, puid, ptzIndex, turnDegree)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StartTurnUp, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (turnDegree || 0));
	},
	// - 向下转（动）
	PTZ_StartTurnDown : function(connectId, puid, ptzIndex, turnDegree)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StartTurnDown, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (turnDegree || 0));
	},
	// - 停止转动
	PTZ_StopTurn : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StopTurn, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 增大光圈
	PTZ_AugmentAperture : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_AugmentAperture, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 减小光圈
	PTZ_MinishAperture : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_MinishAperture, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 停止光圈缩放
	PTZ_StopApertureZoom : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StopApertureZoom, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 推远焦点
	PTZ_MakeFocusFar : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_MakeFocusFar, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 拉近焦点
	PTZ_MakeFocusNear : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_MakeFocusNear, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 停止焦点调整
	PTZ_StopFocusMove : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StopFocusMove, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 缩小图像
	PTZ_ZoomOutPicture : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_ZoomOutPicture, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 放大图像
	PTZ_ZoomInPicture : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_ZoomInPicture, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 停止图像缩放
	PTZ_StopPictureZoom : function(connectId, puid, ptzIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StopPictureZoom, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex);
	},
	// - 开启辅助设备
	PTZ_StartSecondaryDev : function(connectId, puid, ptzIndex, devNumber)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StartSecondaryDev, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (devNumber || 0));
	},
	// - 关闭辅助设备
	PTZ_StopSecondaryDev : function(connectId, puid, ptzIndex, devNumber)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_StopSecondaryDev, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (devNumber || 0));
	},

	// - 前往预置位
	PTZ_MoveToPresetPos : function(connectId, puid, ptzIndex, posNumber)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_MoveToPresetPos, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (posNumber || 0));
	},
	// - 设置预置位
	PTZ_SetPresetPos : function(connectId, puid, ptzIndex, posNumber, posName)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.PTZ_SetPresetPos, puid, P_LY.Enum.PuResourceType.PTZ, ptzIndex, (posNumber || 0), (posName || ""));
	},
	
	/* 输入报警命令 */
	// - 支持的报警触发模式
	IDL_GetSupportedAlertInModeSets : function(connectId, puid, idlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IDL_GetSupportedAlertInModeSets, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex);
	},
	// - 报警触发模式
	IDL_GetAlertInMode : function(connectId, puid, idlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IDL_GetAlertInMode, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex);
	},
	IDL_SetAlertInMode : function(connectId, puid, idlIndex, mode)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IDL_SetAlertInMode, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, mode);
	},
	// - 报警间隔
	IDL_GetAlertInDuration : function(connectId, puid, idlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IDL_GetAlertInDuration, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex);
	},
	IDL_SetAlertInDuration : function(connectId, puid, idlIndex, duration)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IDL_SetAlertInDuration, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, duration);
	},
	// - 报警输入状态
	IDL_GetAlertInStatus : function(connectId, puid, idlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.IDL_GetAlertInStatus, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex);
	},
	// - s - 其他报警输入配置 @ 2014.01.03 ------------------------------------------------
	/*
	---
	desc: 获取支持的工作模式
	returns:
		- response<Array(AlertIn|Counter|StatusCap)>
		含义：AlertIn	报警输入模式,该模式时,下面的报警参数生效
			Counter		计数模式,该模式时,下面的计数参数生效
			StatusCap	状态采集模式,该模式时,下面的"状态是否有效"生效 
	...
	*/
	IDL_GetSupportedWorkModeSets : function (connectId, puid, idlIndex) 
	{
		try
		{
			var fn = "P_LY.IDL_GetSupportedWorkModeSets";
			
			var operator = P_LY.Config.GetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_SupportedWorkModeSets");
			if (operator.rv == P_Error.SUCCESS)
			{
				if (operator.response != null && typeof operator.response.WorkMode == "object")
				{
					if (operator.response.WorkMode.constructor != Array)
					{
						operator.response.WorkMode = [operator.response.WorkMode];
					}
					operator.response = operator.response.WorkMode;
				}
				else
				{
					operator.response = [];	
				}
			}
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValue(P_Error.ERROR_THREAD, e);
		}
	},
	// - 当前的工作模式
	IDL_GetWorkMode : function (connectId, puid, idlIndex) 
	{
		return P_LY.Config.GetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_WorkMode");
	},
	IDL_SetWorkMode : function (connectId, puid, idlIndex, value) 
	{
		return P_LY.Config.SetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_WorkMode", "", value);
	},
	// - 获取开始计数时间，返回UTC时间秒
	IDL_GetCountBeginTime : function (connectId, puid, idlIndex) 
	{
		return P_LY.Config.SetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_CountBeginTime");
	},
	// - 计数总值
	IDL_GetCount : function (connectId, puid, idlIndex) 
	{
		return P_LY.Config.SetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_Count");
	},
	// - 状态是否有效
	IDL_GetLevelValid : function (connectId, puid, idlIndex) 
	{
		return P_LY.Config.SetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_LevelValid");
	},
	// - 读取报警布防区域信息摘要
	IDL_GetGuardRegionSetsSummary : function (connectId, puid, idlIndex)
	{
		try
		{
			var fn = "P_LY.IDL_GetGuardRegionSetsSummary";
			
			var operator = P_LY.Config.GetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_GuardRegionSetsSummary");
			if (operator.rv == P_Error.SUCCESS)
			{
				if (operator.response != null && typeof operator.response.Summary == "object")
				{
					operator.response = operator.response.Summary;
				}
				else
				{
					operator.response = {};	
				}
			}
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValue(P_Error.ERROR_THREAD, e);
		}
	},
	// - 读取报警布防时间表
	IDL_GetGuardMap : function (connectId, puid, idlIndex)
	{
		return P_LY.Config.GetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_GuardMap");
	},
	// - 设置报警布防时间表
	IDL_SetGuardMap : function (connectId, puid, idlIndex, mapValue)
	{
		return P_LY.Config.SetSimple(connectId, puid, P_LY.Enum.PuResourceType.AlertIn, idlIndex, "F_IDL_GuardMap", "", mapValue);
	},
	
	// - e - 其他报警输入配置 @ 2014.01.03 ------------------------------------------------
	
	/* 输出报警命令 */
	// - 报警输出默认状态
	ODL_GetDefaultConnectStatus : function(connectId, puid, odlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_GetDefaultConnectStatus, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex);
	},
	// - 报警输出当前状态
	ODL_GetConnectStatus : function(connectId, puid, odlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_GetConnectStatus, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex);
	},
	// - 接通动作别名
	ODL_GetAliasConnect : function(connectId, puid, odlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_GetAliasConnect, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex);
	},
	ODL_SetAliasConnect : function(connectId, puid, odlIndex, aliasName)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_SetAliasConnect, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex, aliasName);
	},
	// - 断开动作别名
	ODL_GetAliasBreak : function(connectId, puid, odlIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_GetAliasBreak, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex);
	},
	ODL_SetAliasBreak : function(connectId, puid, odlIndex, aliasName)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_SetAliasBreak, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex, aliasName);
	},
	// - 控制报警输出状态
	ODL_SetStatus : function(connectId, puid, odlIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.ODL_SetStatus, puid, P_LY.Enum.PuResourceType.AlertOut, odlIndex, value);
	},
	
	/* 前端存储配置 */
	// - 录像持续时间
	SG_GetRecordTimeSpan : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_GetRecordTimeSpan, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	SG_SetRecordTimeSpan : function(connectId, puid, sgIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_SetRecordTimeSpan, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, value);
	},
	// - 磁盘状态
	SG_GetDiskStatus : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_GetDiskStatus, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	// - 磁盘空间不足时是否覆盖旧文件
	SG_GetCoverOldRecordFile : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_GetCoverOldRecordFile, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	SG_SetCoverOldRecordFile : function(connectId, puid, sgIndex, bValue)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_SetCoverOldRecordFile, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, bValue);
	},
	// - 磁盘空间不足时的剩余空间门限
	SG_GetDiskInsufficientSpace : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_GetDiskInsufficientSpace, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	SG_SetDiskInsufficientSpace : function(connectId, puid, sgIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_SetDiskInsufficientSpace, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, value);
	},
	// - 录像文件保留天数
	SG_GetRecordFileReserveDays : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_GetRecordFileReserveDays, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	SG_SetRecordFileReserveDays : function(connectId, puid, sgIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_SetRecordFileReserveDays, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, value);
	},
	// - 文件系统类型
	SG_GetFileSystemType : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_GetFileSystemType, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	// - 获取磁盘信息
	SG_GetDiskInfo : function(connectId, puid, sgIndex)
	{
		var operator = P_LY.Control.CommonGet
		(
		 	connectId,
			{
				puid: puid,
				resType: P_LY.Enum.PuResourceType.Storager,
				resIdx: sgIndex,
				controlID: "C_SG_GetDiskInfo"
			} 
		);
		if (operator.rv == P_Error.SUCCESS)
		{
			if (operator.response != null && typeof operator.response.Disk == "object")
			{
				var Disk = operator.response.Disk;	
				if (Disk.constructor != Array)
				{
					Disk = [Disk];	
				}
				var dealWith = [];
				for (var i = 0; i < Disk.length; i++) 
				{
					var item = Disk[i];
					dealWith.push
					({
						status: item.Status,
						letter: item.Letter,
						volume_label: item.VolumeLabel,
						type: item.Type,
						file_system: item.FileSystem,
						capacity: item.Capacity,
						used_space: item.UsedSpace,
						usable_space: item.UsableSpace
					});
				}
				operator.response = dealWith;
			}
			else
			{
				operator.response = [];	
			}
		}
		return operator;
	},
	// - 开始初始化文件系统
	SG_StartInitFileSystem : function(connectId, puid, sgIndex, sgDiskLetter)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_StartInitFileSystem, puid, P_LY.Enum.PuResourceType.Storager, sgDiskLetter);
	},
	// - 查询初始化文件系统进度
	SG_QueryInitFileSystemProgress : function(connectId, puid, sgIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_QueryInitFileSystemProgress, puid, P_LY.Enum.PuResourceType.Storager, sgIndex);
	},
	// - 手动启动存储
	SG_ManualStart : function(connectId, puid, sgIndex, ivType, ivIndex, ivStreamType, duration)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_ManualStart, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, ivType, ivIndex, ivStreamType, duration);
	},
	// - 手动停止存储
	SG_ManualStop : function(connectId, puid, sgIndex, ivType, ivIndex, ivStreamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_ManualStop, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, ivType, ivIndex, ivStreamType);
	},
	// - 查询CEFS录像或图片
	SG_CEFSQueryFiles : function (connectId, puid, sgIndex, ivIndex, beginTime, endTime, streamType)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SG_CEFSQueryFiles, puid, P_LY.Enum.PuResourceType.Storager, sgIndex, ivIndex, beginTime, endTime, streamType);
	},
	
	/* 平台存储配置 */
	// - 录像时间
	SC_GetRecordTimeSpan : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetRecordTimeSpan, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	SC_SetRecordTimeSpan : function(connectId, puid, csuIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_SetRecordTimeSpan, puid, P_LY.Enum.PuResourceType.SC, csuIndex, value);
	},
	// - 磁盘状态
	SC_GetDiskStatus : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetDiskStatus, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	// - 磁盘满时是否覆盖旧文件
	SC_GetCoverOldRecordFile : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetCoverOldRecordFile, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	SC_SetCoverOldRecordFile : function(connectId, puid, csuIndex, bValue)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_SetCoverOldRecordFile, puid, P_LY.Enum.PuResourceType.SC, csuIndex, bValue);
	},
	// - 录像文件保存天数
	SC_GetRecordFileReserveDays : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetRecordFileReserveDays, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	SC_SetRecordFileReserveDays : function(connectId, puid, csuIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_SetRecordFileReserveDays, puid, P_LY.Enum.PuResourceType.SC, csuIndex, value);
	},
	// - GPS数据保存天数
	SC_GetGPSReserveDays : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetGPSReserveDays, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	SC_SetGPSReserveDays : function(connectId, puid, csuIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_SetGPSReserveDays, puid, P_LY.Enum.PuResourceType.SC, csuIndex, value);
	},
	// - 是否使能GPS存储
	SC_GetEnableGPSDataStorage : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetEnableGPSDataStorage, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	SC_SetEnableGPSDataStorage : function(connectId, puid, csuIndex, bValue)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_SetEnableGPSDataStorage, puid, P_LY.Enum.PuResourceType.SC, csuIndex, bValue);
	},
	// - 抓拍文件保存天数
	SC_GetSnapshotReserveDays : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetSnapshotReserveDays, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	SC_SetSnapshotReserveDays : function(connectId, puid, csuIndex, value)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_SetSnapshotReserveDays, puid, P_LY.Enum.PuResourceType.SC, csuIndex, value);

	},
	/* 平台存储控制 */
	// - 获取磁盘信息
	SC_GetDiskInfo : function(connectId, puid, csuIndex)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_GetDiskInfo, puid, P_LY.Enum.PuResourceType.SC, csuIndex);
	},
	// - 手动启动存储
	SC_ManualStart : function(connectId, puid, csuIndex, rec_puid, rec_ivIndex, rec_streamType, rec_reason, rec_duration)
	{
		if (!rec_puid || typeof rec_ivIndex == "undefined")
		{
			P_Utils.Log("P_LY.SC_ManualStart", "rec_puid or rec_ivIndex error~");
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
		}
		var rec_ivHandle_operator = P_LY.NPPSDKCommon.GetHandle
		(
			connectId,
			rec_puid,
			P_LY.Enum.PuResourceType.VideoIn,
			rec_ivIndex
		);
		if (rec_ivHandle_operator.rv != P_Error.SUCCESS) 
		{
			P_Utils.Log("P_LY.SC_ManualStart", "get rec_ivHandle error~");
			return rec_ivHandle_operator;
		}
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_ManualStart, puid, P_LY.Enum.PuResourceType.SC, csuIndex, rec_ivHandle_operator.response, rec_streamType, rec_reason, rec_duration);
	},
	// - 手动停止存储
	SC_ManualStop : function(connectId, puid, csuIndex, rec_puid, rec_ivIndex, rec_streamType)
	{
		if (!rec_puid || typeof rec_ivIndex == "undefined")
		{
			P_Utils.Log("P_LY.SC_ManualStop", "rec_puid or rec_ivIndex error~");
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
		}
		var rec_ivHandle_operator = P_LY.NPPSDKCommon.GetHandle
		(
			connectId,
			rec_puid,
			P_LY.Enum.PuResourceType.VideoIn,
			rec_ivIndex
		);
		if (rec_ivHandle_operator.rv != P_Error.SUCCESS) 
		{
			P_Utils.Log("P_LY.SC_ManualStop", "get rec_ivHandle error~");
			return rec_ivHandle_operator;
		}
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_ManualStop, puid, P_LY.Enum.PuResourceType.SC, csuIndex, rec_ivHandle_operator.response, rec_streamType);
	},
	// - 查询录像/图片文件
	SC_QueryFiles : function(connectId, puid, csuIndex, rec_puid, rec_ivIndex, rec_beginTime, rec_endTime, rec_streamType, byOffset, byCount)
	{
		if (!rec_puid || typeof rec_ivIndex == "undefined")
		{
			P_Utils.Log("P_LY.SC_QueryFiles", "rec_puid or rec_ivIndex error~");
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
		}
		var rec_ivHandle_operator = P_LY.NPPSDKCommon.GetHandle
		(
			connectId,
			rec_puid,
			P_LY.Enum.PuResourceType.VideoIn,
			rec_ivIndex
		);
		if (rec_ivHandle_operator.rv != P_Error.SUCCESS) 
		{
			P_Utils.Log("P_LY.SC_QueryFiles", "get rec_ivHandle error~");
			return rec_ivHandle_operator;
		}
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_QueryFiles, puid, P_LY.Enum.PuResourceType.SC, csuIndex, rec_ivHandle_operator.response, rec_beginTime, rec_endTime, rec_streamType, byOffset, byCount);
	},
	// - 删除录像/图片文件
	SC_DelFiles : function(connectId, puid, csuIndex, fileListArray)
	{
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_DelFiles, puid, P_LY.Enum.PuResourceType.SC, csuIndex, fileListArray);
	},
	// - 查询录像/图片文件
	SC_QueryGPSData : function(connectId, puid, csuIndex, rec_puid, rec_gpsIndex, rec_beginTime, rec_endTime, byOffset, byCount)
	{
		if (!rec_puid || typeof rec_gpsIndex == "undefined")
		{
			P_Utils.Log("P_LY.SC_QueryGPSData", "rec_puid or rec_ivIndex error~");
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
		}
		var rec_gpsHandle_operator = P_LY.NPPSDKCommon.GetHandle
		(
			connectId,
			rec_puid,
			P_LY.Enum.PuResourceType.GPS,
			rec_gpsIndex
		);
		if (rec_gpsHandle_operator.rv != P_Error.SUCCESS) 
		{
			P_Utils.Log("P_LY.SC_QueryGPSData", "get rec_gpsHandle error~");
			return rec_gpsHandle_operator;
		}
		return P_LY.NPPSDKCommon.GetNCResponse(connectId, P_LY.Enum.NCObjectMethodList.SC_QueryGPSData, puid, P_LY.Enum.PuResourceType.SC, csuIndex, rec_gpsHandle_operator.response, rec_beginTime, rec_endTime, byOffset, byCount);
	},
	
    /*
    ---
    fn: QueryCSUFiles 
    desc: 查询中心存储文件
    author:
        -   
    time: 2013.10.29
	returns:
		- succ <Response: Array(P_LY.Struct.SCIVDateFileStruct)>
	params:
		- connectId(string) * 连接ID
		- queryConditions(Object) * 查询条件
			=> {
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，缺省为0

				puid(string) * 查询对象（设备）PUID
				ivIndex(uint) 查询对象（摄像头）资源索引，缺省为0
				objSets(Object|Array) 若干查询对象列表，在queryConditions.puid存在情况下，此节点无效
				beginTime(UTC time) 查询开始时间（UTC秒，如1385380100），缺省为0
				endTime(UTC time) 查询结束时间（UTC秒，如1385380100），缺省为当前时间
				offset(uint) 查询开始索引，缺省从0开始
				count(uint) 查询条数，缺省为200
				streamType(P_LY.Enum.StreamType) 限定查询存储的流类型，多个流类型之间使用英文竖线（|）分割，如（REALTIME|STORAGE），查图片使用PICTURE，缺省为STORAGE查录像
				reason(string) 存储原因，多个原因之间使用英文竖线（|）分割，如（Manual|Plan），缺省为空字符串将查询所有符合条件录像
				logicMode(string) 查询逻辑（AND与，OR或），缺省OR
				domainRoad(string) 子域平台名（缺省空字符串，代表根平台）
			}
	remark:
		- queryConditions.objSets
			-> 单个查询对象 如 = {puid: "151...", ivIndex: "0"}	 
     		-> 多个查询数组 如 = [{puid: "151...", ivIndex: "0"}, {puid: "151...", ivIndex: "0"}, ...]	
    ... 
    */
	QueryCSUFiles : function (connectId, queryConditions)
	{
		try
		{
			var fn = "P_LY.QueryCSUFiles";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var querys = queryConditions || {};
			if (!querys || !querys.csuPuid)
			{
				P_Utils.Log(fn, "csuPuid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);
			}
			querys.csuIndex = typeof querys.csuIndex != "undefined" || !isNaN(querys.csuIndex) ? querys.csuIndex : 0;
			
			var xmlObjRes = '';
			if (typeof querys.puid != "undefined")
			{
				querys.ivIndex = typeof querys.ivIndex != "undefined" || !isNaN(querys.ivIndex) ? querys.ivIndex : 0;
				
				xmlObjRes = '<Res ObjType="151" ObjID="'+querys.puid+'" Type="'+P_LY.Enum.PuResourceType.VideoIn+'" Idx="'+querys.ivIndex+'"></Res>';
			}
			else
			{
				if (typeof querys.objSets == "object")
				{
					if (querys.objSets.constructor != Array)
					{
						querys.objSets = [querys.objSets];	
					}
					
					for (var i = 0; i < querys.objSets.length; i++)
					{
						var r = querys.objSets[i];
						if (r && r.puid)
						{
							xmlObjRes += '<Res ObjType="151" ObjID="'+r.puid+'" Type="'+P_LY.Enum.PuResourceType.VideoIn+'" Idx="'+r.ivIndex+'"></Res>';
						}
					}
				}
			}
			
			if (!xmlObjRes)
			{
               	P_Utils.Log(fn, "xmlObjRes error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
            }
			
			querys.beginTime = typeof querys.beginTime != "undefined" || !isNaN(querys.beginTime) ? querys.beginTime : 0;
			querys.endTime = typeof querys.endTime != "undefined" || !isNaN(querys.endTime) ? querys.endTime : Math.ceil(new Date().getTime() / 1000);
			querys.offset = typeof querys.offset != "undefined" || !isNaN(querys.offset) ? querys.offset : 0;
			querys.count = typeof querys.count != "undefined" || !isNaN(querys.count) ? querys.count : 200;
			querys.streamType = querys.streamType || P_LY.Enum.StreamType.STORAGE;
			querys.logicMode = querys.logicMode != "AND" ? "OR" : "AND";
			
			var xmlReason = "";
			querys.reason = typeof querys.reason != "undefined" ? querys.reason.split("|") : "";
			if (typeof querys.reason == "object")
			{
				if (querys.reason.constructor != Array)
				{
					querys.reason = [querys.reason];	
				}
				for (var j = 0; j < querys.reason.length; j++)
				{
					xmlReason += "<Reason>" + querys.reason[j] + "</Reason>";
				}
			}
			xmlReason = xmlReason || "<Reason></Reason>";
			
			var optID = "C_SC_QueryFiles";
			
			var operator = P_LY.CommonRequest
			(
				connectId, 
				{
					cmdType : P_LY.Enum.CmdType.C,
					puid : querys.csuPuid,
					domainRoad : querys.domainRoad || '',
					xmlDstRes : '<Res Type="'+P_LY.Enum.PuResourceType.SC+'" Idx="'+querys.csuIndex+'" OptID="'+optID+'"><Param Offset="'+querys.offset+'" Cnt="'+querys.count+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" StreamType="'+querys.streamType+'" LogicMode="'+querys.logicMode+'">'+xmlReason+'</Param></Res>',
					xmlObjSets : '<ObjSets>' + xmlObjRes + '</ObjSets>',
					returnType : "json"
				}
			);
			
			if (operator.rv == P_Error.SUCCESS)
			{
				var _fileListArray = new Array();
				
				var _response = operator.response || {};
				if (typeof _response == "object" && typeof _response.Res == "object")
				{
					if (_response.Res.constructor != Array)
					{
						_response.Res = [_response.Res];	
					}
					
					for (var k = 0; k < _response.Res.length; k++)
					{
						var _res = _response.Res[k],
							_puid = _res.ObjID,
							_type = _res.Type,
							_idx = _res.Idx;
						
						if (typeof _res.File == "object")
						{
							if (_res.File.constructor != Array)
							{
								_res.File = [_res.File]; 
							}
							for (var m = 0; m < _res.File.length; m++)
							{
								var _file = _res.File[m];
								
								_fileListArray.push
								(
								 	new P_LY.Struct.SCIVDateFileStruct
									(
										_file.Name,
										_file.Path,
										_file.Size,
										_file.BeginTime,
										_file.EndTime,
										_file.Reason,
										querys.csuPuid,
										querys.csuIndex,
										_puid,
										_type,
										_idx
									)
								);
							}
						}
					}
				}
				
				operator.response = _fileListArray || [];
			}
			
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
	---
	fn: RemoveCSUFiles
	desc: 删除存储文件
	author: 
		- 
	time: 2013.11.29
	params:
		- connectId(string) 连接ID
		- options(object) 可选参数
		{
			csuPuid(string) * 中心存储器PUID
			csuIndex(uint) 中心存储单元资源索引
			fileSets(object|array) * 平台录像或图片文件集合	
		}
	remark:
		- 单个文件可以options.fileSets = {fileName: ?, filePath: ?} 或 [{fileName: ?, filePath: ?}]
		- 多个文件集合options.fileSets = [{fileName: ?, filePath: ?}, {fileName: ?, filePath: ?}, ...]
		注意fileName和filePath为查询出来的文件名和文件所处目录
	...
	*/
	RemoveCSUFiles : function (connectId, options)
	{
		try
		{
			var fn = "P_LY.RemoveCSUFiles";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var o = options = options || {};
			if (!o.csuPuid || !P_LY.puidRex.test(o.csuPuid))
			{
				P_Utils.Log(fn, "csuPuid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);
			}
			o.csuIndex = typeof o.csuIndex != "undefined" || !isNaN(o.csuIndex) ? o.csuIndex : 0;
			
			o.fileSets = o.fileSets || [];
			
			if (o.fileSets != null && typeof o.fileSets == "object")
			{
				if (o.fileSets.constructor != Array)
				{
					o.fileSets = [o.fileSets];	
				}
				
				var xmlFiles = "";
				for (var i = 0; i < o.fileSets.length; i++)
				{
					var file = o.fileSets[i];
					if (typeof file.fileName != "undefined" && typeof file.filePath != "undefined")
					{
						xmlFiles += '<File Name="'+file.fileName+'" Path="'+file.filePath+'" />';
					}
				}
				
				var operator = P_LY.Control.CommonSet
				(
					connectId,
					{
						puid: o.csuPuid,
						resType: P_LY.Enum.PuResourcType.SC,
						resIdx: o.csuIndex,
						controlID: "C_SC_DelFile",
						param: '<Param>' + xmlFiles + '</Param>',
						domainRoad: o.domainRoad || ""
					}
				);
				
				return operator;
			}
			 
			return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	
	/*
    ---
    fn: FetchCSUIVDate 
    desc: 获取平台存储下有存储文件的日期
    author:
        -   
    time: 2013.10.29
	returns:
		- succ <Response: Array(UTC timestamp)>
	params:
		- connectId(string) * 连接ID
		- queryConditions(Object) * 查询条件
			=> {
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，缺省为0
				puid(string) * 查询对象（设备）PUID
				ivIndex(uint) 查询对象（摄像头）资源索引，缺省为0
				offset(uint) 查询开始索引，缺省从0开始
				count(uint) 查询条数，缺省为200
				streamType(P_LY.Enum.StreamType) 限定查询存储的流类型，多个流类型之间使用英文竖线（|）分割，如（REALTIME|STORAGE），查图片使用PICTURE，缺省为STORAGE查录像
				domainRoad(string) 子域平台名（缺省空字符串，代表根平台）
			}
    ... 
    */
	FetchCSUIVDate : function (connectId, queryConditions)
	{
		try
		{
			var fn = "P_LY.FetchCSUIVDate";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var querys = queryConditions || {};
			if (!querys || !querys.csuPuid)
			{
				P_Utils.Log(fn, "querys.csuPuid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);
			}
			querys.csuIndex = typeof querys.csuIndex != "undefined" || !isNaN(querys.csuIndex) ? querys.csuIndex : 0;
			
			var xmlObjRes = '';
			if (typeof querys.puid != "undefined")
			{
				querys.ivIndex = typeof querys.ivIndex != "undefined" || !isNaN(querys.ivIndex) ? querys.ivIndex : 0;
				
				xmlObjRes = '<Res ObjType="151" ObjID="'+querys.puid+'" Type="'+P_LY.Enum.PuResourceType.VideoIn+'" Idx="'+querys.ivIndex+'"></Res>';
			}
			else
			{
				P_Utils.Log(fn, "querys.puid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
			}
			
			if (!xmlObjRes)
			{
               	P_Utils.Log(fn, "xmlObjRes error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
            }
			
			querys.offset = typeof querys.offset != "undefined" || !isNaN(querys.offset) ? querys.offset : 0;
			querys.count = typeof querys.count != "undefined" || !isNaN(querys.count) ? querys.count : 200;
			querys.streamType = querys.streamType || P_LY.Enum.StreamType.STORAGE;
			
			var optID = "C_SC_QueryIVDate";
			
			var operator = P_LY.CommonRequest
			(
				connectId, 
				{
					cmdType : P_LY.Enum.CmdType.C,
					puid : querys.csuPuid,
					domainRoad : querys.domainRoad || '',
					xmlDstRes : '<Res Type="'+P_LY.Enum.PuResourceType.SC+'" Idx="'+querys.csuIndex+'" OptID="'+optID+'"><Param Offset="'+querys.offset+'" Cnt="'+querys.count+'" StreamType="'+querys.streamType+'"></Param></Res>',
					xmlObjSets : '<OSets>' + xmlObjRes + '</OSets>',
					returnType : "json"
				}
			);
			
			if (operator.rv == P_Error.SUCCESS)
			{
				var _dateArray = new Array();
				
				var _response = operator.response || {};
				if (typeof _response == "object" && typeof _response.Res == "object")
				{
					if (typeof _response.Res.Date == "object" && _response.Res.Date.constructor == Array)
					{
						for (var i = 0; i < _response.Res.Date.length; i++)
						{
							_dateArray.push(_response.Res.Date[i]);	
						}
 					}
					else
					{
						_dateArray.push(_response.Res.Date);		
					}
				}
				
				operator.response = _dateArray || [];
			}
			
			return operator;			
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: FetchCSUIVDateFiles 
    desc: 按日期获取视频的平台存储文件
    author:
        -   
    time: 2013.10.29
	returns:
		- succ <Response: Array(P_LY.Struct.SCIVDateFileStruct)>
	params:
		- connectId(string) * 连接ID
		- queryConditions(Object) * 查询条件
			=> {
				csuPuid(string) * 中心存储器PUID
				csuIndex(uint) 中心存储单元资源索引，缺省为0
				puid(string) * 查询对象（设备）PUID
				ivIndex(uint) 查询对象（摄像头）资源索引，缺省为0
				datetime(UTC timestamp) * 以UTC时间（如1385380100）查询，必选，应该为通过P_LY.FetchCSUIVDate获取到的UTC日期时间
				offset(uint) 查询开始索引，缺省从0开始
				count(uint) 查询条数，缺省为200
				streamType(P_LY.Enum.StreamType) 限定查询存储的流类型，多个流类型之间使用英文竖线（|）分割，如（REALTIME|STORAGE），查图片使用PICTURE，缺省为STORAGE查录像
				domainRoad(string) 子域平台名（缺省空字符串，代表根平台）
			}
    ... 
    */
	FetchCSUIVDateFiles : function (connectId, queryConditions)
	{
		try
		{
			var fn = "P_LY.FetchCSUIVDateFiles";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var querys = queryConditions || {};
			if (!querys || !querys.csuPuid)
			{
				P_Utils.Log(fn, "querys.csuPuid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CSU_PUID_ERROR);
			}
			querys.csuIndex = typeof querys.csuIndex != "undefined" || !isNaN(querys.csuIndex) ? querys.csuIndex : 0;
			
			var xmlObjRes = '';
			if (typeof querys.puid != "undefined")
			{
				querys.ivIndex = typeof querys.ivIndex != "undefined" || !isNaN(querys.ivIndex) ? querys.ivIndex : 0;
				
				xmlObjRes = '<Res ObjType="151" ObjID="'+querys.puid+'" Type="'+P_LY.Enum.PuResourceType.VideoIn+'" Idx="'+querys.ivIndex+'"></Res>';
			}
			else
			{
				P_Utils.Log(fn, "querys.puid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR);
			}
			
			if (!xmlObjRes)
			{
               	P_Utils.Log(fn, "xmlObjRes error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
            }
			if (typeof querys.datetime == "undefined" || isNaN(querys.datetime) || querys.datetime == null)
			{
				P_Utils.Log(fn, " querys.datetime error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);	
			}
			
			querys.offset = typeof querys.offset != "undefined" || !isNaN(querys.offset) ? querys.offset : 0;
			querys.count = typeof querys.count != "undefined" || !isNaN(querys.count) ? querys.count : 200;
			querys.streamType = querys.streamType || P_LY.Enum.StreamType.STORAGE;
			
			var optID = "C_SC_QueryIVDateFiles";
			
			var operator = P_LY.CommonRequest
			(
				connectId, 
				{
					cmdType : P_LY.Enum.CmdType.C,
					puid : querys.csuPuid,
					domainRoad : querys.domainRoad || '',
					xmlDstRes : '<Res Type="'+P_LY.Enum.PuResourceType.SC+'" Idx="'+querys.csuIndex+'" OptID="'+optID+'"><Param Offset="'+querys.offset+'" Cnt="'+querys.count+'" StreamType="'+querys.streamType+'" Date="'+querys.datetime+'"></Param></KRes>',
					xmlObjSets : '<OSets>' + xmlObjRes + '</OSets>',
					returnType : "json"
				}
			);
			if (operator.rv == P_Error.SUCCESS)
			{
				var _fileListArray = new Array();
				
				var _response = operator.response || {};
				if (typeof _response == "object" && typeof _response.Res == "object")
				{
					if (_response.Res.constructor != Array)
					{
						_response.Res = [_response.Res];	
					}
					
					for (var k = 0; k < _response.Res.length; k++)
					{
						var _res = _response.Res[k],
							_puid = _res.ObjID,
							_type = _res.Type,
							_idx = _res.Idx;
						
						if (typeof _res.File == "object")
						{
							if (_res.File.constructor != Array)
							{
								_res.File = [_res.File]; 
							}
							for (var m = 0; m < _res.File.length; m++)
							{
								var _file = _res.File[m];
								
								_fileListArray.push
								(
								 	new P_LY.Struct.SCIVDateFileStruct
									(
										_file.Name,
										_file.Path,
										_file.Size,
										_file.BeginTime,
										_file.EndTime,
										_file.Reason,
										querys.csuPuid,
										querys.csuIndex,
										_puid,
										_type,
										_idx
									)
								);
							}
						}
					}
				}
				
				operator.response = _fileListArray || [];
			}
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	
	/*
    ---
    fn: FetchCEFSDate 
    desc: CEFS查询有存储文件的日期
    author:
        -   
    time: 2013.11.07
	returns:
		- succ <Response: Array(UTC timstamp)>
	params:
		- connectId(string) * 连接ID
		- queryConditions(Object) * 查询条件
		{
			puid(string) * 查询对象设备PUID
			requestID(P_LY.Enum.CEFSRequestID) * 请求命令
			sgIndex(uint) 前端存储器资源索引，一般为0（缺省）
			diskLetter(string) 查询磁盘的盘符，缺省为空字符串
			channelSets(Uint|Array) 查询通道号，从0开始，依次表示第一路...视频，缺省为0，多个通道号使用数组，如new Array(0, 1, ..)
		}
	remark:
		- queryConditions.channelSets 单个数值，如0或[0]
			requestID = P_LY.Enum.CEFSRequestID.QueryRecordDate | QueryPictureDate
		- queryConditions.channelSets不传
			requestID = P_LY.Enum.CEFSRequestID.QueryUserLogDate  
		- queryConditions.channelSets 单个数值或数组，如0或[0, 1, 2, ...]
			requestID = P_LY.Enum.CEFSRequestID.QueryAlarmEventDate 
	...
	*/
	FetchCEFSDate : function (connectId, queryConditions)
	{
		try
		{
			var fn = "P_LY.FetchCEFSDate";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var querys = queryConditions || {};
			
			if (!querys.puid || !P_LY.puidRex.test(querys.puid))
			{
				P_Utils.Log(fn, "querys.puid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 	
			}
			
			querys.diskLetter = querys.diskLetter || "";
			querys.channelSets = typeof querys.channelSets != "undefined" ? querys.channelSets : [0];
			
			if (querys.channelSets.constructor != Array)
			{
				querys.channelSets = [querys.channelSets];	
			}
			
			var xmlRequest = "",
				sgType = P_LY.Enum.PuResourceType.Storager,
				sgIndex = querys.sgIndex = typeof querys.sgIndex != "undefined" && !isNaN(querys.sgIndex) ? querys.sgIndex : 0;
			
			switch (querys.requestID)
			{
				case P_LY.Enum.CEFSRequestID.QueryRecordDate :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" Channel="'+querys.channelSets[0]+'" />';
					break;
				case P_LY.Enum.CEFSRequestID.QueryPictureDate :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" Channel="'+querys.channelSets[0]+'" />';
					break;
				case P_LY.Enum.CEFSRequestID.QueryUserLogDate :
				case P_LY.Enum.CEFSRequestID.QueryGPSDataDate :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" />';
					break;
				case P_LY.Enum.CEFSRequestID.QueryAlarmEventDate :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'"><Channels>';
					for (var i = 0; i < querys.channelSets.length; i++)
					{
						if (typeof querys.channelSets[i] != "undefined" && !isNaN(querys.channelSets[i]))
						{
							xmlRequest += '<Channel>'+querys.channelSets[i]+'</Channel>';
						}	
					}
					xmlRequest += '</Channels></Request>';
					break;
				default:
					P_Utils.Log(fn, "querys.requestID undefined+ error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					break;
			}
			
			var operator = P_LY.Control.CommonGet
			(
				connectId,
				{
					puid: querys.puid,
					resType: P_LY.Enum.PuResourceType.Storager,
					resIdx: querys.sgIndex,
					controlID: "C_SG_TransparenceComXML",
					param: '<Param>' + xmlRequest + '</Param>',
					returnFlag: 0
				}
			);
			
			if (operator.rv == P_Error.SUCCESS)
			{
				var dateArr = [];
				
				if (operator.response && operator.response.Response && operator.response.Response.Result == "0")
				{
					if (operator.response.Response.ID == querys.requestID) 
					{
						if (typeof operator.response.Response.Date != "undefined" && typeof operator.response.Response.Date.UTC != "undefined")
						{
							dateArr = operator.response.Response.Date.UTC;
							if (dateArr.constructor != Array)
							{
								dateArr = [dateArr];	
							}
						}														
					}
				}
				operator.response = dateArr;
			}
			
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
    ---
    fn: FetchCEFSFiles 
    desc: 查询CEFS存储文件（适用快速查询或高级查询）
    author:
        -   
    time: 2013.11.08
	returns:
		- succ <Response: Array(UTC timstamp)>
	params:
		- connectId(string) * 连接ID
		- queryConditions(Object) * 查询条件
		{
			puid(string) * 查询对象设备PUID
			requestID(P_LY.Enum.CEFSRequestID) * 请求命令
			beginTime(UTC timstamp) * 起始查询时间，缺省为0，如1325376000
			endTime(UTC timstamp) * 结束查询时间，缺省为0，如1325386000
			sgIndex(uint) 前端存储器资源索引，一般为0（缺省）
			diskLetter(string) 查询磁盘的盘符，缺省为空字符串
			channelSets(Uint|Array) 查询通道号，从0开始，依次表示第一路...视频，缺省为0，多个通道号使用数组，如new Array(0, 1, ..)
			reasonSets(String|Array) 存储原因，多个原因逻辑或联合查询请传数组，缺省将查所有原因的
			typeSets(String|Array) 数据类型
		}
	remark:
		- requestID = P_LY.Enum.CEFSRequestID.QueryRecord 快速索引查询
			=> queryConditions.channelSets一个数值，如0或[0] 
			=> queryConditions.reasonSets传值也无效，将查所有原因的		
		- requestID = P_LY.Enum.CEFSRequestID.QueryLinkActionRecord 高级查询
			=> queryConditions.channelSets一个数值，如0或[0] 
			=> queryConditions.reasonSets<(String|Array)(P_LY.Enum.CEFSRecordReason)>，如Schedule或[Schedule, Manual]，缺省将查所有原因的
		
		- requestID = P_LY.Enum.CEFSRequestID.QueryPicture 快速索引查询
			=> queryConditions.channelSets一个数值，如0或[0] 
			=> queryConditions.reasonSets传值也无效，将查所有原因的		
		- requestID = P_LY.Enum.CEFSRequestID.QueryLinkActionPicture 高级查询
			=> queryConditions.channelSets单个数值或数组，如0或1或[0, 1, ...] 
			=> queryConditions.reasonSets<(String|Array)(P_LY.Enum.CEFSPictureReason)>，如Schedule或[Schedule, Manual]，缺省将查所有原因的
		
		- requestID = P_LY.Enum.CEFSRequestID.QueryUserLog
			=> 高级查询时: queryConditions.typeSets<(String|Array)(P_LY.Enum.CEFSUserLogType)>，如UserLogin或UserLogin, UserLogout]
			=> 快速索引查询时：queryConditions.typeSets不必传值，将查所有类型的
			
		- requestID = P_LY.Enum.CEFSRequestID.QueryAlarmEvent
			queryConditions.channelSets单个数值或数组，如0或1或[0, 1, ...] 
			=> 高级查询时: queryConditions.typeSets<(String|Array)(P_LY.Enum.CEFSAlarmEventType)>，如AlertIn或[AlertIn, MotionDetected]
			=> 快速索引查询时：queryConditions.typeSets不必传值，将查所有类型的
		- requestID = P_LY.Enum.CEFSRequestID.QueryGPSData
	...
	*/
	FetchCEFSFiles : function (connectId, queryConditions)
	{
		try
		{
			var fn = "P_LY.FetchCEFSFiles";
			
			if (!connectId || !P_LY.Connections.get(connectId))
			{
				P_Utils.Log(fn, "connectId error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTID_ERROR);
			}
			var _connStruct = P_LY.Connections.get(connectId);
			if (_connStruct.status != P_LY.Enum.ConnectionStatus.Connected || !_connStruct.session) 
			{ 
				P_Utils.Log(fn, "login or session error~");
				if (_connStruct.status == P_LY.Enum.ConnectionStatus.Connecting)
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECTING);
				}
				else
				{
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_CONNECT_FAILED);
				}
			}
			
			var querys = queryConditions || {};
			
			if (!querys.puid || !P_LY.puidRex.test(querys.puid))
			{
				P_Utils.Log(fn, "querys.puid error~");
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_PUID_ERROR); 	
			}
			
			querys.diskLetter = querys.diskLetter || "";
			querys.beginTime = typeof querys.beginTime != "undefined" && !isNaN(querys.beginTime) ? querys.beginTime : 0;
			querys.endTime = typeof querys.endTime != "undefined" && !isNaN(querys.endTime) ? querys.endTime : 0;
			querys.channelSets = typeof querys.channelSets != "undefined" ? querys.channelSets : [0];
			querys.reasonSets = querys.reasonSets || [];
			querys.typeSets = querys.typeSets || [];
			
			if (querys.channelSets.constructor != Array)
			{
				querys.channelSets = [querys.channelSets];	
			}
			
			var xmlRequest = "",
				sgType = P_LY.Enum.PuResourceType.Storager,
				sgIndex = querys.sgIndex = typeof querys.sgIndex != "undefined" && !isNaN(querys.sgIndex) ? querys.sgIndex : 0;
			
			switch (querys.requestID)
			{
				case P_LY.Enum.CEFSRequestID.QueryRecord :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" Channel="'+querys.channelSets[0]+'" />';
					break;
				case P_LY.Enum.CEFSRequestID.QueryLinkActionRecord :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" Channel="'+querys.channelSets[0]+'"><Reasons>';
					if (querys.reasonSets.constructor != Array)
					{
						querys.reasonSets = [querys.reasonSets];	
					}
					if (querys.reasonSets.length <= 0)
					{
						for (reason in P_LY.Enum.CEFSRecordReason)
						{
							xmlRequest += '<Reason>'+P_LY.Enum.CEFSRecordReason[reason]+'</Reason>';	
						}
					}
					else
					{
						for (var i = 0; i < querys.reasonSets.length; i++)
						{
							if (typeof querys.reasonSets[i] != "undefined" && querys.reasonSets[i])
							{
								xmlRequest += '<Reason>'+querys.reasonSets[i]+'</Reason>';
							}	
						}	
					}
					xmlRequest += '</Reasons></Request>';
					break;
				case P_LY.Enum.CEFSRequestID.QueryPicture :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" Channel="'+querys.channelSets[0]+'" />';
					break;
				case P_LY.Enum.CEFSRequestID.QueryLinkActionPicture :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" >';
						xmlRequest += '<Channels>';
						for (var i = 0; i < querys.channelSets.length; i++)
						{
							if (typeof querys.channelSets[i] != "undefined" && !isNaN(querys.channelSets[i]))
							{
								xmlRequest += '<Channel>'+querys.channelSets[i]+'</Channel>';
							}	
						}
						xmlRequest += '</Channels>';
						
						xmlRequest += '<Reasons>';
						if (querys.reasonSets.constructor != Array)
						{
							querys.reasonSets = [querys.reasonSets];	
						}
						if (querys.reasonSets.length <= 0)
						{
							for (reason in P_LY.Enum.CEFSPictureReason)
							{
								xmlRequest += '<Reason>'+P_LY.Enum.CEFSPictureReason[reason]+'</Reason>';	
							}
						}
						else
						{
							for (var i = 0; i < querys.reasonSets.length; i++)
							{
								if (typeof querys.reasonSets[i] != "undefined" && querys.reasonSets[i])
								{
									xmlRequest += '<Reason>'+querys.reasonSets[i]+'</Reason>';
								}	
							}	
						}
						xmlRequest += '</Reasons>';
					xmlRequest += '</Request>';
					break;
				case P_LY.Enum.CEFSRequestID.QueryUserLog :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" >';
						xmlRequest += '<Types>';
						if (querys.typeSets.constructor != Array)
						{
							querys.typeSets = [querys.typeSets];	
						}
						if (querys.typeSets.length <= 0)
						{
							for (type in P_LY.Enum.CEFSUserLogType)
							{
								xmlRequest += '<Type>'+P_LY.Enum.CEFSUserLogType[type]+'</Type>';	
							}
						}
						else
						{
							for (var i = 0; i < querys.typeSets.length; i++)
							{
								if (typeof querys.typeSets[i] != "undefined" && querys.typeSets[i])
								{
									xmlRequest += '<Type>'+querys.typeSets[i]+'</Type>';
								}	
							}
						}
						xmlRequest += '</Types>';
					xmlRequest += '</Request>';
					break;
				case P_LY.Enum.CEFSRequestID.QueryAlarmEvent :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'">';
						xmlRequest += '<Channels>';
						for (var i = 0; i < querys.channelSets.length; i++)
						{
							if (typeof querys.channelSets[i] != "undefined" && !isNaN(querys.channelSets[i]))
							{
								xmlRequest += '<Channel>'+querys.channelSets[i]+'</Channel>';
							}	
						}
						xmlRequest += '</Channels>';
						
						xmlRequest += '<Types>';
						if (querys.typeSets.length <= 0)
						{
							for (type in P_LY.Enum.CEFSAlarmEventType)
							{
								xmlRequest += '<Type>'+P_LY.Enum.CEFSAlarmEventType[type]+'</Type>';	
							}
						}
						else
						{
							for (var i = 0; i < querys.typeSets.length; i++)
							{
								if (typeof querys.typeSets[i] != "undefined" && querys.typeSets[i])
								{
									xmlRequest += '<Type>'+querys.typeSets[i]+'</Type>';
								}	
							}
						}
						xmlRequest += '</Types>';
					xmlRequest += '</Request>';
					break;
				case P_LY.Enum.CEFSRequestID.QueryGPSData :
					xmlRequest = '<Request ID="'+querys.requestID+'" DiskLetter="'+querys.diskLetter+'" BeginTime="'+querys.beginTime+'" EndTime="'+querys.endTime+'" />';
					break;
				default:
					P_Utils.Log(fn, "querys.requestID undefined+ error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					break;
			}
			
			var operator = P_LY.Control.CommonGet
			(
				connectId,
				{
					puid: querys.puid,
					resType: P_LY.Enum.PuResourceType.Storager,
					resIdx: querys.sgIndex,
					controlID: "C_SG_TransparenceComXML",
					param: '<Param>' + xmlRequest + '</Param>',
					returnFlag: 0
				}
			);
			
			if (operator.rv == P_Error.SUCCESS)
			{
				var filesArr = [];
				
				if (operator.response && operator.response.Response && operator.response.Response.Result == "0")
				{
					var nextBeginTime = operator.response.Response.NextBeginTime;
					var needNextQuery = nextBeginTime == 0 ? false : true;
							
					switch (querys.requestID)
					{
						case P_LY.Enum.CEFSRequestID.QueryRecord :
						case P_LY.Enum.CEFSRequestID.QueryLinkActionRecord :	
							if (typeof operator.response.Response.Record != "undefined")
							{
								var Record = operator.response.Response.Record;
								if (Record.constructor != Array)
								{
									Record = [Record];	
								}
								for (var i = 0; i < Record.length; i++)
								{
									filesArr.push(new P_LY.Struct.CEFSRecordStruct(false, Record[i].BeginTime, Record[i].EndTime, Record[i].BitRate, Record[i].Reason, querys.puid, querys.channelSets[0]));
								}
								if (needNextQuery == true)
								{
									filesArr.push(new P_LY.Struct.CEFSRecordStruct(true, nextBeginTime, 0, 0, 0, querys.puid, querys.channelSets[0]));
								}
							}
							break;
							
						case P_LY.Enum.CEFSRequestID.QueryPicture :
						case P_LY.Enum.CEFSRequestID.QueryLinkActionPicture :
							if (typeof operator.response.Response.Picture == "undefined")
							{
								if (typeof operator.response.Response.Record != "undefined")
								{
									operator.response.Response.Picture = operator.response.Response.Record;
								}
							}
							if (typeof operator.response.Response.Picture != "undefined")
							{
								var Picture = operator.response.Response.Picture;
								if (Picture.constructor != Array)
								{
									Picture = [Picture];	
								}
								for (var i = 0; i < Picture.length; i++)
								{
									filesArr.push(new P_LY.Struct.CEFSPictureStruct(false, Picture[i].Time, Picture[i].NoInSecond, Picture[i].Reason, querys.puid, (typeof Picture[i].Channel != "undefined" ? Picture[i].Channel : querys.channelSets[0])));
								}
								if (needNextQuery == true)
								{
									filesArr.push(new P_LY.Struct.CEFSPictureStruct(true, nextBeginTime, 0, 0, querys.puid, querys.channelSets));
								}
							}
							break;
							
						case P_LY.Enum.CEFSRequestID.QueryUserLog :
							if (typeof operator.response.Response.UserLog != "undefined")
							{
								var UserLog = operator.response.Response.UserLog;
								if (UserLog.constructor != Array)
								{
									UserLog = [UserLog];	
								}
								for (var i = 0; i < UserLog.length; i++)
								{
									filesArr.push(new P_LY.Struct.CEFSUserLogStruct(false, UserLog[i].Time, UserLog[i].Type, UserLog[i].Data, querys.puid));
								}
								if (needNextQuery == true)
								{
									filesArr.push(new P_LY.Struct.CEFSUserLogStruct(true, nextBeginTime, 0, 0, querys.puid));
								}
							}
							break;
							
						case P_LY.Enum.CEFSRequestID.QueryAlarmEvent :
							if (typeof operator.response.Response.AlarmEvent != "undefined")
							{
								var AlarmEvent = operator.response.Response.AlarmEvent;
								if (AlarmEvent.constructor != Array)
								{
									AlarmEvent = [AlarmEvent];	
								}
								for (var i = 0; i < AlarmEvent.length; i++)
								{
									filesArr.push(new P_LY.Struct.CEFSAlarmEventStruct(false, AlarmEvent[i].Time, AlarmEvent[i].Type, AlarmEvent[i].Data, querys.puid, AlarmEvent[i].Channel));
								}
								if (needNextQuery == true)
								{
									filesArr.push(new P_LY.Struct.CEFSAlarmEventStruct(true, nextBeginTime, 0, 0, querys.puid, querys.channelSets));
								}
							}
							break;
						case P_LY.Enum.CEFSRequestID.QueryGPSData :
							if (typeof operator.response.Response.GPSData != "undefined")
							{
								var GPSData = operator.response.Response.GPSData;
								if (GPSData.constructor != Array)
								{
									GPSData = [GPSData];	
								}
								for (var i = 0; i < GPSData.length; i++)
								{
									filesArr.push(new P_LY.Struct.CEFSGPSDataStruct(false, GPSData[i].Time, GPSData[i].Latitude, GPSData[i].Longitude, GPSData[i].Bearing, GPSData[i].Altitude, GPSData[i].OfflineFlag, GPSData[i].State, GPSData[i].Speed, GPSData[i].MaxSpeed, GPSData[i].MinSpeed, querys.puid));
								}
								if (needNextQuery == true)
								{
									filesArr.push(new P_LY.Struct.CEFSGPSDataStruct(true, nextBeginTime, 0, 0, 0, 0, 0, 0, 0, 0, 0, querys.puid));
								}
							}
							break;
					} // end switch
				}
				
				operator.response = filesArr;
			
			} // end operator.response
			
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/*
	---
	fn: CommonResConfig
	desc: 资源描述配置
	author: 
		- 
	time: 2013.11.14
	params: 
		- connectId(string) * 连接ID
		- puid(string) * 设备PUID
		- resType(string) * 资源类型
		- resIdx(uint) * 资源索引
		- action(string) 获取get或设置set资源描述，缺省为get
		- paramObj(P_LY.Struct.CommonResDescriptionStruct) 资源描述结构信息，action=set使用
	...
	*/
	CommonResConfig : function (connectId, puid, resType, resIdx, action, paramObj)
	{
		try
		{
			var fn = "P_LY.CommonResConfig";
			
			var operator,
				o = paramObj || {};
			switch ((typeof action == "string" ? action.toLowerCase() : action))
			{
				case "set":
					operator = P_LY.Config.SetComplex
					(
					 	connectId,
						{
							puid: puid,
							resType: resType,
							resIdx: resIdx,
							configID: "F_COMMONRES_Desc",
							streamType: "",
                    		param: '<Param><Res ResType="'+o.resType+'" ResIdx="'+o.resIdx+'" Name="'+o.name+'" Desc="'+o.description+'" Enable="'+o.enable+'" /></Param>',
							returnFlag: 0
						}
					);
					break;
				default:
					operator = P_LY.Config.GetComplex
					(
					 	connectId,
						{
							puid: puid,
							resType: resType,
							resIdx: resIdx,
							configID: "F_COMMONRES_Desc",
							returnFlag: 0
						}
					);
					if (operator.rv == P_Error.SUCCESS)
					{
						var o = null;
						if (operator.response && typeof operator.response.Res != "undefined")
						{
							if (typeof operator.response.Res.Name != "undefined")
							{
								o = new P_LY.Struct.CommonResDescriptionStruct
								(
									puid,
									operator.response.Res.ResType,
									operator.response.Res.ResIdx,
									operator.response.Res.Name,
									operator.response.Res.Desc,
									operator.response.Res.Enable
								);
							}
						}
						operator.response = o;
					}
					break;
			}; 
			return operator;
		}
		catch (e) {
			P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
			return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
		}
	},
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- s - remark: Folder Operation - 2013.12.14
    * ........................................................................................................
    **/
	// - 文件（夹）操作对象
	Folder :
	{
		/*
		---
		desc: 切换WA调试
		params:
			- bValue(boolean) 1/true开启，0/false关闭（缺省）
		...
		*/
		DebugSwitch : function (bValue)
		{
			return P_IF.Folder.DebugSwitch(P_LY.Plug.wa, (bValue || 0));
		},
		// - 获取操作系统根目录
		GetSystemRoot : function ()
		{
			return P_IF.Folder.GetSystemRoot(P_LY.Plug.wa);
		},
		/*
		---
		desc: 打开目录选择对话框
		params:
			- dialogTitle(string) 对话框标题，缺省为"" 
		...
		*/
		GetFileFolder : function (dialogTitle)
		{
			return P_IF.Folder.GetFileFolder(P_LY.Plug.wa, (dialogTitle || ""));
		},
		// - 打开目录
		OpenFolder : function (folderPath)
		{
			return P_IF.Folder.OpenFolder(P_LY.Plug.wa, folderPath);
		},
		/*
		---
		desc: 创建文件夹
		params:
			- folderPath(string) 需要创建的目录全路径
		...
		*/
		CreateDirectory : function (folderPath)
		{
			return P_IF.Folder.CreateDirectory(P_LY.Plug.wa, folderPath);
		},
		// - 删除文件夹
		DeleteDirectory : function (folderPath)
		{
			return P_IF.Folder.DeleteDirectory(P_LY.Plug.wa, folderPath);
		},
		/*
		---
		desc: 删除文件
		params:
			- fileName(string) 需要删除的文件全路径名称
		...
		*/
		DeleteFile : function (fileName)
		{
			return P_IF.Folder.DeleteFile(P_LY.Plug.wa, fileName);
		},
		/*
		---
		desc: 检测文件是否存在
		returns:
			- response = 1存在，其他值不存在
		params:
			- fileName(string) 需要删除的文件全路径名称
		remark:
			- 返回值节点rv = P_Error.SUCCESS，response = 1存在，0不存在 
			- rv为其他值时，表示调用不成功
		...
		*/
		FileExist : function (fileName)
		{
			try
			{
				var fn = "P_LY.Folder.FileExist";
				
				if (P_LY.Plug.wa)
				{
					var operator = P_IF.Folder.FileExist(P_LY.Plug.wa, (fileName || ""));
					if (operator.rv == P_Error.SUCCESS)
					{
						operator.response = (operator.response === true) ? 1 : 0;
					}
					return operator;
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		// - 读文件内容
		ReadFile : function (fileName)
		{
			return P_IF.Folder.ReadFile(P_LY.Plug.wa, (fileName || ""));
		},
		/*
		---
		desc: 写文件内容
		params:
			- fileName(string) 需要写文件的全路径名称，如C:/123.txt
			- content(string) 写的具体内容
			- contentlength(number) 内容真实长度
			- bValue(boolean) 1/true写追加，0/false写覆盖（缺省）
		remark:
			- 文件不存在可创建，前提是上级目录首先存在
		...
		*/
		WriteFile : function (fileName, content, contentLength, bValue)
		{
			return P_IF.Folder.WriteFile(P_LY.Plug.wa, (fileName || ""), content, contentLength, (bValue || 0));
		},
		/*
		---
		desc: 按类型获取目录下的文件列表
		params:
			- folderPath(string) 全路径文件夹名称
			- fileType(string) 为文件的类型，如"avi/AVI", "jpg", "doc"等等（不可省略）
			- bValue(uint) 是否深度递归获取，缺省为0
			- bReturnFullPath(uint) 是否返回全路径，缺省为0否
		remark:
			- 如果fileType为某个具体的类型时，理论上将返回此类型的全部文件
		...
		*/
		GetFolderFiles : function (folderPath, fileType, bValue, bReturnFullPath)
		{
			try
			{
				var fn = "P_LY.Folder.GetFolderFiles";
				
				if (P_LY.Plug.wa)
				{
					return P_IF.Folder.GetFolderFiles(P_LY.Plug.wa, folderPath, (fileType || ""), (bValue || 0), ( bReturnFullPath || 0));
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		// - 获取目录下的文件夹列表
		GetFolders : function (folderPath, bValue, bReturnFullPath)
		{
			try
			{
				var fn = "P_LY.Folder.GetFolders";
				
				if (P_LY.Plug.wa)
				{
					return P_IF.Folder.GetFolders(P_LY.Plug.wa, folderPath, (bValue || 0), (bReturnFullPath || 0));
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		/*
		---
		desc: 进行Base64编码
		params:
			- segment(string) 要编码的字符串
		...
		*/
		Base64Enc : function (segment)
		{
			return P_IF.Folder.Base64Enc(P_LY.Plug.wa, segment);
		},
		/*
		---
		desc: 进行Base64解码
		params:
			- base64EnStr(string) 要解码的字符串
			- 是否使用UTF8编码，缺省使用
		...
		*/
		Base64Dec : function (base64EnStr, bUsingUTF8)
		{
			return P_IF.Folder.Base64Dec(P_LY.Plug.wa, base64EnStr, bUsingUTF8);
		},
		// - Des编码
		DesEnc : function (base64Raw, base64Key)
		{
			return P_IF.Folder.DesEnc(P_LY.Plug.wa, base64Raw, base64Key);
		},
		// - Des解码
		DesDec : function (base64EnStr, base64KeyStr)
		{
			return P_IF.Folder.DesDec(P_LY.Plug.wa, base64EnStr, base64KeyStr);
		},
		GetRandTokenForUser : function (base64EnStr, keyStr)
		{
			return P_IF.Folder.GetRandTokenForUser(P_LY.Plug.wa, base64EnStr, keyStr);
		},
		SetRandTokenForUser : function (rawData, keyStr)
		{
			return P_IF.Folder.SetRandTokenForUser(P_LY.Plug.wa, rawData, keyStr);
		},
		/*
		---
		desc: 读取指定文件内容，经Base64编码后返回
		params:
			- fileName(string) 文件全路径，如图片C:/123.gif
		remark:
			- 请确保fileName合法存在
		...
		*/
		ReadFileEx : function (fileName)
		{
			return P_IF.Folder.ReadFileEx(P_LY.Plug.wa, (fileName || ""));
		},
		/*
		---
		desc: 将文件复制到指定目录下
		params:
			- srcFileName(string) 需要复制的文件全路径
			- dstFileName(string) 保存到目标目录下文件名，可以只是文件名，将放在srcFileName同一目录下
		remark:
			- 返回值节点rv = P_Error.SUCCESS，response = 0复制成功（对于某些不合法的目标文件名，实际复制是不成功的），-1源文件应传入绝对路径，-2源文件不存在 
			- rv为其他值时，表示调用不成功
		...
		*/
		CopyFile : function (srcFileName, dstFileName)
		{
			try
			{
				var fn = "P_LY.Folder.CopyFile";
				
				if (P_LY.Plug.wa)
				{
					var operator = P_IF.Folder.CopyFile(P_LY.Plug.wa, srcFileName, dstFileName);
					if (operator.rv == P_Error.SUCCESS)
					{
						operator.response = operator.response == "" ? 0 : (operator.response == -1 ? -1 : -2); 
					}
					return operator;
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		
		/*
		---
		fn: StartHttpDownload
		desc: 开始下载解码库插件
		params: 
			- url(net location string) 网络地址，如http://127.0.0.1/npsdk/Plugin_Setup.exe
			- saveDir(string) 本地下载保存目录，如C:/
			- autoRun(boolean) 下载完成后是否自动运行，1是0否
		remark:
			- WA -> NPP_httpDownloadServer{ active: ?, url: ?, saveDir: ?, autoRun: ?, ...}
		...
		*/
		StartHttpDownload : function (url, saveDir, autoRun) 
		{
			try
			{
				var fn = "P_LY.Folder.StartHttpDownload";
				if (P_LY.Plug.wa)
				{
					// - 检测一下是否存在下载
					if (typeof P_LY.Plug.wa["NPP_httpDownloadServer"] != "undefined")
					{
						if (P_LY.Plug.wa["NPP_httpDownloadServer"]["active"] == true)
						{
							P_Utils.Log(fn, "http download has existed~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_DOWNLOAD_EXISTED);
						}
					}
					if (!url || !saveDir)
					{
						P_Utils.Log(fn, "url or saveDir error~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
					}
					var autoRun = !!(autoRun == false) ? 0 : 1;
					
					var operator = P_IF.Folder.HttpDownload(P_LY.Plug.wa, url, saveDir, autoRun);
					if (operator.rv == P_Error.SUCCESS)
					{
						P_LY.Plug.wa["NPP_httpDownloadServer"] =
						{
							active: true, 
							url: url, 
							saveDir: saveDir, 
							autoRun: autoRun,
							status: null,
							desc: null,
							total_length: null,
							current_length: null,
							speed: null
						};
					}
					return operator;
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		CloseHttpDownload : function () 
		{
			try
			{
				var fn = "P_LY.Folder.CloseHttpDownload";
				if (P_LY.Plug.wa)
				{
					if (typeof P_LY.Plug.wa["NPP_httpDownloadServer"] != "undefined")
					{
						if (P_LY.Plug.wa["NPP_httpDownloadServer"]["active"] == false)
						{
							P_Utils.Log(fn, "http download closed~");
							return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
						}
					}
					else
					{
						P_Utils.Log(fn, "http download not exist~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);	
					}
					var operator = P_IF.Folder.CloseHttpDownload(P_LY.Plug.wa);
					if (operator.rv == P_Error.SUCCESS)
					{
						P_LY.Plug.wa["NPP_httpDownloadServer"]["active"] = false;
					}
					return operator;
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		/*
		---
		remark:
			- 开始下载后，上层需要定时侦测下载的状态，当检测到下载完成时应停止下载
		...
		*/
		GetStatus : function ()
		{
			try
			{
				var fn = "P_LY.Folder.GetStatus";
				if (P_LY.Plug.wa)
				{
					if (typeof P_LY.Plug.wa["NPP_httpDownloadServer"] != "undefined")
					{
						if (P_LY.Plug.wa["NPP_httpDownloadServer"]["active"] == false)
						{
							P_Utils.Log(fn, "http download not exist~");
							if (P_LY.Plug.wa["NPP_httpDownloadServer"]["status"] != null)
							{
								return new P_LY.Struct.ReturnValueStruct
								(
									P_Error.SUCCESS, 
									new P_LY.Struct.HttpDownloadStatusStruct
									(
										P_LY.Plug.wa["NPP_httpDownloadServer"]["status"],
										P_LY.Plug.wa["NPP_httpDownloadServer"]["desc"],
										P_LY.Plug.wa["NPP_httpDownloadServer"]["speed"],
										P_LY.Plug.wa["NPP_httpDownloadServer"]["total_length"],
										P_LY.Plug.wa["NPP_httpDownloadServer"]["current_length"]
									)
								);	
							}
							return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
						}
					}
					else
					{
						P_Utils.Log(fn, "http download not exist~");
						return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);	
					}
					
					var status_opt = P_IF.Folder.GetStatus(P_LY.Plug.wa);
					var speed_opt = P_IF.Folder.GetSpeed(P_LY.Plug.wa);
					var totalBytes_opt = P_IF.Folder.GetTotalLength(P_LY.Plug.wa);
					var curBytes_opt = P_IF.Folder.GetDownloadLength(P_LY.Plug.wa);
					
					P_Utils.Log(fn, status_opt.response + "::" + speed_opt.response + "::" + totalBytes_opt.response + "::" + curBytes_opt.response);
					
					var status = status_opt.response || 0,
						desc = P_LY.Enum.HttpDownloadStatusDesc[status][P_LY.language] || "",
						speed = speed_opt.response || 0,
						totalBytes = totalBytes_opt.response || 0;
						curBytes = curBytes_opt.response || 0;
					
					P_LY.Plug.wa["NPP_httpDownloadServer"]["status"] = status;
					P_LY.Plug.wa["NPP_httpDownloadServer"]["desc"] = desc;
					P_LY.Plug.wa["NPP_httpDownloadServer"]["speed"] = speed;
					P_LY.Plug.wa["NPP_httpDownloadServer"]["total_length"] = totalBytes;
					P_LY.Plug.wa["NPP_httpDownloadServer"]["current_length"] = curBytes;
					  
					return new P_LY.Struct.ReturnValueStruct
					(
					 	P_Error.SUCCESS, 
						new P_LY.Struct.HttpDownloadStatusStruct
						(
							status,
							desc,
							speed,
							totalBytes,
							curBytes
						)
					);
				}
				else
				{
					P_Utils.Log(fn, "wa undefined error~");
					return new P_LY.Struct.ReturnValueStruct(P_Error.FAILED);
				}
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		/*
		---
		remark:
			- 获取解码库插件的版本号，如果响应为空或者版本号不是最新的（上层应规定一个版本，每次可以验证一下），那么版本太低需执行下载更新
		...
		*/
		GetPluginVersion : function (pluginName) 
		{
			try
			{
				var fn = "P_LY.Folder.GetPluginVersion";
				return P_IF.Folder.GetPluginVersion(P_LY.Plug.wa, (pluginName || ""));
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			}
		},
		/*
		---
		desc: 退出清除
		...
		*/
		UnLoad : function ()
		{
			try
			{
				var fn = "P_LY.Folder.UnLoad";
				P_LY.Folder.CloseHttpDownload();
			}
			catch (e) {
				P_Utils.Log(fn, "excp error = " + e.message + "::" + e.name);
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR_THREAD);
			} 
		},
		end : true
	},
	
	/**
    * --------------------------------------------------------------------------------------------------------
    *	- e - remark: Folder Operation - 2013.12.14
    * ........................................................................................................
    **/
	
    /*
    ---
    fn: Debug 
    desc: 调试对象
    author:
        -   
    time: 2013.09.04 
    ... 
    */
    Debug : 
    {
        debug : false, // - 是否开启调试
        messages : new Array(), // - 调试信息数据
		maxCount : 1000, // - 最多保存条数
        callback : null, // - 回调函数
        Init : function (_ipStruct) 
        {
            try 
            {
                if (_ipStruct && _ipStruct instanceof P_LY.Struct.InitParamStruct) 
                {
                    P_LY.Debug.debug = _ipStruct.debug === true ? true : false;
                    if (typeof _ipStruct.callback == "function") 
                    {
                        P_LY.Debug.callback = _ipStruct.callback || function () {};
                    }
                }
                if (typeof NPPUtils != "undefined") {
                    P_Utils.Log = P_LY.Debug.Note;
                }
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
            }
            catch (e) {
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
            }
        },
        Note : function (fn, log, _time, _mode) 
        {
            try 
            {
				if (_mode === true)
				{
                	return P_Error.SUCCESS;
				}
                var _time = _time || P_Utils.DateFormat(), _fn = fn || "_P_LYSDKDebugger_", _log = log || "";
                var _message = new P_LY.Struct.DebugMessageStruct( _time, _fn, _log );
                if (_message) 
                {
                    P_LY.Debug.messages.push(_message);
					if (P_LY.Debug.messages.length > P_LY.Debug.maxCount)
					{
						P_LY.Debug.messages.splice(0, P_LY.Debug.maxCount);	
					}
                    if (P_LY.Debug.debug) 
                    {
                        if (typeof P_LY.Debug.callback == "function") 
                        {
                            P_LY.Debug.callback(_message);
                        }
                        else 
                        {
                            alert("[time] " + _message.time + "\r\n[fn]" + _message.fn + "\r\n[msg]" + (typeof _message.msg == "object" && typeof Object.toJSON != "undefined" && P_LY.browserType == P_LY.Enum.BrowserType.IE ? Object.toJSON(_message.msg) : _message.msg));
                        }
                    }
                }
				return new P_LY.Struct.ReturnValueStruct(P_Error.SUCCESS);
            }
            catch (e) {
				return new P_LY.Struct.ReturnValueStruct(P_Error.ERROR);
            }
        },
        end : true 
    },
    // - 结构体对象
    Struct : 
    { 
        /*
        ---
        fn: InitParamStruct
        desc: 初始化P_LY对象参数结构
        author: 
            - Lingsen
            - 
        time: 2010.11.26 -> 2013.01.12
        modifytime: 2013.09.03
        params:
            - debug(boolean) 是否开始调试状态
            - cb(function) 调试信息输出回调函数
            - extraParams(object) 其他参数
            => {
                language(P_LY.Enum.LanguageType) SDK语言对象
				warmTip(object):
				{
					active(boolean) 是否使能未加载插件时内部温馨提示
					pluginFile(string) 插件绝对地址或网络地址，默认"MediaPlugin7.exe"
					html(html code) 自定义网页提示代码
				}
            }
        remark:   
        ...
        */
        InitParamStruct : function (debug, cb, extraParams) 
        {
            this.debug = (typeof debug != "undefined" && debug === true ? true : false);
            this.callback = (typeof cb != "undefined" ? cb : null);
            var extraParams = extraParams || {};
            this.language = extraParams.language || "";
			
			var _wt = extraParams.warmTip = extraParams.warmTip || {};
			this.warmTip = {
				active : typeof _wt.active == "undefined" ? (P_LY.Plug.Error.active || false) : (_wt.active || false),
				pluginFile : _wt.pluginFile || P_LY.Plug.Error.pluginFile || "MediaPlugin7.exe",
				html : _wt.html || ""
			};
			this.type = extraParams.type = extraParams.type || "";
        },
        // - 调试信息结构
        DebugMessageStruct : function (time, fn, msg) 
        {
            this.time = time;
            this.fn = fn;
            this.msg = msg;
        },
		
		/*
		---
		desc: 返回值结构
		...
		*/
		ReturnValueStruct : P_IF.Struct.ReturnValueStruct,
		
        /*
        ---
        desc: 初始化连接服务器参数结构
        params:
            - path(string) IP:Port, 如127.0.0.1:8866
            - username(string) 用户名
            - epId(string) 企业ID
            - password(string) 用户密码
            - bFix(bool) 是否透过网闸（1/true是，0/false否）
        ...
        */
        ConnParamStruct : P_IF.Struct.ConnParamStruct,
		
		/*
        ---
        fn: PUNodeStruct
        desc: PU资源节点结构
        author:
                - 
        time: 2013.09.05
        params:
			- puid(string) 设备PUID
			- resType(string) 资源类型
			- resIdx(string) 资源索引
			- name(string) 资源名称
			- description(string) 资源描述
			- enable(string) 是否使能
			- online(string) 是否在线
			- immitted(string) 是否允许接入（0不允许，1允许）
			- modelName(string) 资源型号名称
			- modelType(string) 资源型号
			- manufactrueID(string) 厂商ID
			- hardwareVersion(string) 硬件版本
			- softwareVersion(string) 软件版本
			- deviceID(string) 设备ID
			- latitude(string) 固定点纬度
			- longitude(string) 固定点经度
			- _HANDLE(string) 设备资源句柄
			- childResource 子资源
		remark:
			- _HANDLE为保留属性，设备资源句柄，上层可以不用
        ...
        */
		PUNodeStruct : function (puid, resType, resIdx, name, description, enable, usable, online, immitted, modelName, modelType, manufactureID, hardwareVersion, softwareVersion, deviceID, latitude, longitude, _HANDLE, childResource) 
		{
            this.puid = (typeof puid != "undefined" ? puid : "");
            this.resType = (typeof resType != "undefined" ? resType : P_LY.Enum.PuResourceType.ST);
            this.resIdx = (resIdx != null && typeof resIdx != "undefined" ? resIdx : "0");
            this.name = (typeof name != "undefined" ? name : "");
            this.description = (typeof description != "undefined" ? description : "");
            this.enable = (parseInt(enable) === 0 ? enable : "1");
            this.usable = (parseInt(usable) === 0 ? usable : "1");
            this.online = (parseInt(online) === 0 ? online : "1");
            this.immitted = (parseInt(immitted) === 0 ? "0" : "1");
            this.modelName = (typeof modelName != "undefined" ? modelName : "");
            this.modelType = (typeof modelType != "undefined" ? modelType : "");

            this.manufactrueID = (typeof manufactrueID != "undefined" ? manufactrueID : "");
            this.hardwareVersion = (typeof hardwareVersion != "undefined" ? hardwareVersion : "");
            this.softwareVersion = (typeof softwareVersion != "undefined" ? softwareVersion : "");
            this.deviceID = (typeof deviceID != "undefined" ? deviceID : "");
            this.latitude = (latitude ? latitude : "");
            this.longitude = (longitude ? longitude : "");
			this._HANDLE = _HANDLE || "";
            this.childResource = (childResource || []);
        },
		/*
        ---
        fn: PUResourceNodeStruct
        desc: PU子资源节点结构
        author:
                - 
        time: 2013.09.05
        params:
			- type(string) 资源类型
			- idx(string) 资源索引
			- name(string) 资源名称
			- description(string) 资源描述
			- enable(string) 是否使能 
			- _HANDLE(string) 设备子资源句柄
		remark:
			- _HANDLE为保留属性，设备子资源句柄，上层可以不用
        */
		PUResourceNodeStruct: function (type, idx, name, description, enable, usable, _HANDLE) 
		{
            this.type = (typeof type != "undefined" ? type : "");
            this.idx = (idx != null && typeof idx != "undefined" ? idx : "");
            this.name = (typeof name != "undefined" ? name : "");
            this.description = (typeof description != "undefined" ? description : "");
            this.enable = (parseInt(enable) === 0 ? enable : "1");
            this.usable = (parseInt(usable) === 0 ? usable : "1");
			this._HANDLE = _HANDLE || "";
        },
		/*
        ---
        fn: LogicGroupStruct
        desc: ：逻辑分组信息结构
        author:
                - 
        time: 2013.11.15
        params:
			- index(string) 逻辑分组索引
			- name(string) 逻辑分组名称
			- lastRefreshTime(string) 最新刷新时间
			- refreshInterval(string) 刷新时间间隔
			- childResource(P_LY.Struct.LogicGroupNodeStruct) 逻辑分组节点数组对象 
        */
		LogicGroupStruct : function (index, name, lastRefreshTime, refreshInterval, childResource)
		{
			this.index = (index != null && typeof index != "undefined" ? index : "0");
            this.name = (name != null && typeof name != "undefined" ? name : "");
            this.lastRefreshTime = (lastRefreshTime != null && typeof lastRefreshTime != "undefined" ? lastRefreshTime : "");
            this.refreshInterval = (refreshInterval != null && typeof refreshInterval != "undefined" ? refreshInterval : "");
            this.childResource = (childResource != null && typeof childResource == "object" && childResource.constructor == Array ? childResource : new Array());
		},
		/*
        ---
        fn: LogicGroupNodeStruct
        desc: ：逻辑分组节点信息结构
        author:
                - 
        time: 2013.11.15
        params:
			- index(uint) 逻辑分组节点索引
			- name(string) 逻辑分组节点名称
			- parentNode_Index(uint) 逻辑分组节点上级索引，根节点上级为0
			- childResource(P_LY.Struct.LogicGroupNodeStruct|P_LY.Struct.LogicGroupResourceStruct) 数组对象 
        */
		LogicGroupNodeStruct : function (index, name, parentNode_Index, childResource)
		{
			this.index = (index != null && typeof index != "undefined" ? index : "0");
            this.name = (name != null && typeof name != "undefined" ? name : "");
            this.parentNode_Index = (parentNode_Index != null && typeof parentNode_Index != "undefined" ? parentNode_Index : "0");
            this.childResource = (childResource != null && typeof childResource == "object" && childResource.constructor == Array ? childResource : new Array());	
		},
		/*
        ---
        fn: LogicGroupResourceStruct
        desc: ：逻辑分组节点下资源信息结构
        author:
                - 
        time: 2013.11.15
        params:
			- puid(string) 设备PUID
			- type(string) （视频）资源类型，一般为P_LY.Enum.PuResourceType.VideoIn
			- idx(uint) （视频）资源索引
			- name(string) （视频）资源名称
			- description(string) （视频）资源描述
			- enable(uint) （视频）资源是否使能 
			- parentNode_Index(uint) 逻辑分组节点资源上级节点索引
        */
		LogicGroupResourceStruct: function (puid, type, idx, name, description, enable, parentNode_Index)
		{
            this.puid = (puid != null && typeof puid != "undefined" ? puid : "");
            this.type = (type != null && typeof type != "undefined" ? type : "");
            this.idx = (idx != null && typeof idx != "undefined" ? idx : "0");
            this.name = (name != null && typeof name != "undefined" ? name : "");
            this.description = (description != null && typeof description != "undefined" ? description : "");
            this.enable = (enable != null && typeof enable != "undefined" ? enable : "0");
            this.parentNode_Index = (parentNode_Index != null && typeof parentNode_Index != "undefined" ? parentNode_Index : "0");
        },
		/*
        ---
        fn: ConnectionStruct 
        desc: 连接对象信息结构
        author:
            -   
            -  
        time: 2013.09.03 
        returns: 
            - P_Error 
        params: 
            - connectId(string) 连接ID 
            - connParam(P_LY.Struct.ConnectParams) 连接参数 
        ... 
        */
        ConnectionStruct : function (connectId, connParam) 
        {
            var key = this.connectId = connectId || ""; 
            this.ncName = "";
            this.nc = null;
            // - 建立的连接类型 Server | Device
            this.connType = P_LY.Enum.ConnectionType.Server;
            // - 建立的连接状态 Idle | Connecting | Connected | Failed
            this.status = P_LY.Enum.ConnectionStatus.Idle;
            this.connParam = (connParam instanceof P_LY.Struct.ConnParamStruct ? connParam : new P_LY.Struct.ConnParamStruct());
            // - 建立的连接句柄，连接成功时有效
            this.session = null;
            this.systemName = "";
			this.userPriority = 1;
            this.domainRoad = [];
            // - 连接设备时可用
            this.resource = []; 
        },
		
		/*
        ---
        fn: WindowContainerStruct 
        desc: 窗口容器信息结构
        author:
            -   
            -  
        time: 2013.09.05 
        params: 
            - container(string) 窗口插件的容器Dom对象
            - type(P_LY.Enum.WindowType) 窗口类型对象
			- active(boolean) 窗口是否被激活
			- _window(P_LY.Struct.WindowStruct) 窗口信息结构
			- description(object) 正在播放的视频资源描述 
        ... 
        */
		WindowContainerStruct : function (container, type, active, _window, description) 
		{
            this.container = (container && typeof container != "undefined" ? container : "");
            this.type = (type != P_LY.Enum.WindowType.VOD ? P_LY.Enum.WindowType.VIDEO : type);
            this.active = !!(active == true);
            this.window = (_window && typeof _window != "undefined" ? _window : null);
            this.description = (typeof description != "undefined" ? description : {});
        },
		
		/*
        ---
        fn: WindowStruct
        desc: 窗口信息结构
        author: 
			- 
			- 
        mdftime: 2013.10.18
        params:
			- options(object) 可选参数
			{
				container(element) 窗口插件容器，优先判断container，其次containerId
				containerId(string) 窗口插件容器ID
				type(P_LY.Enum.WindowType) 窗口类型	
				wnd(element) 窗口插件实例，优先判断wnd，其次wndName
				wndName(string) 窗口插件实例ID
				connectId(string) 连接ID
				wndHandle(string) 窗口插件实例句柄
				status(object) 视频状态参数
				style(object) 窗口样式（保留）
				customParams(object) 自定义参数
			}
        ...
        */
		WindowStruct : function (options) 
		{
			var _SELF = this;
			
			var options = options || {};
			
			this.type = typeof options.type != "undefined" && options.type == P_LY.Enum.WindowType.VOD ? P_LY.Enum.WindowType.VOD : P_LY.Enum.WindowType.VIDEO;
			this.container = 
			(
				function() 
				{
					if (typeof options.container != "undefined" && typeof options.container.id != "undefined")
					{
						_SELF.containerId = options.container.id || document.getElementById(options.containerId || "");
						return options.container;
					}
					else
					{
						_SELF.containerId = options.containerId || "";
						return document.getElementById(_SELF.options.containerId);
					}
				}
			)();
			this.wnd = 
			(
				function() 
				{
					if (typeof options.wnd != "undefined" && typeof options.wnd.id != "undefined")
					{
						_SELF.wndName = options.wnd.id || document.getElementById(options.wndName || "");
						return options.wnd;
					}
					else
					{
						_SELF.wndName = options.wndName || "";
						return document.getElementById(_SELF.options.wndName);
					}
				}
			)();
			this.wndHandle = options.wndHandle || "";
			this.connectId = options.connectId || "";
			this.params = 
			{
				puid : "", // - 设备PUID
				idx : "", // - 资源索引 
				streamType : // - 默认播放流类型
				(
					function()
					{
						if (_SELF.type == P_LY.Enum.WindowType.VIDEO)
						{
							return P_LY.Enum.StreamType.REALTIME;
						}
						else
						{
							return P_LY.Enum.StreamType.STORAGE;	
						}
					}
				)(),
				ivStreamHandle : "" , // - 视频流句柄
				iaStreamHandle : "" , // - 音频流句柄
				alg : "", // - 平台转码流编码算法
				resolution : "", // - 平台转码流分辨率
				bitRate : "", // - 平台转码流码率
				frameRate : "", // - 平台转码流帧率
				speed : 0, // - 点播速度
				relativeStartTime : 0, // - 相对文件播放开始时间
				beginTime : 0, // - 点播开始时间
				endTime : 0, // - 点播结束时间
				fileFullPath : null, // - 点播文件全路径 
				fileTimeLength : 0 // - 点播文件时长
			};
			this.status = 
			{
				isddrawing : (options.status ? !!(options.status.isddrawing == true) : false), islittledecodelib : (options.status ? !!(options.status.islittledecodelib == true) : false), 
				playvideoing : (options.status ? !!(options.status.playvoding == true) : false), 
				isplaybyP2P : (options.status ? !!(options.status.isplaybyP2P == true) : false), playaudioing : (options.status ? !!(options.status.playaudioing == true) : false), 
				recording : (options.status ? !!(options.status.recording == true) : false), playvideoing : (options.status ? !!(options.status.playvideoing == true) : false),
				talking : (options.status ? !!(options.status.talking == true) : false), talking : (options.status ? !!(options.status.talking == true) : false),
				calling : (options.status ? !!(options.status.calling == true) : false), calling : (options.status ? !!(options.status.calling == true) : false),
				playvoding : (options.status ? !!(options.status.playvoding == true) : false), isfullscreening: (options.status ? !!(options.status.isfullscreening == true) : false)
			};
			this.customParams = options.customParams || {};
			this.style = 
			{
				enableFullScreen : (options.style ? !!(options.style.enableFullScreen == true) : false),
				enableMask : (options.style ? !!(options.style.enableMask == true) : false), 
				enableMainPopMenu : (options.style ? !!(options.style.enableMainPopMenu == true) : true) 
			};
			this.SetStyle = function (style) 
			{
				var v = style ? !!(style.enableFullScreen == true) : false;
				this.style.enableFullScreen = v;
				// this.wnd.enableFullScreen(v ? 1 : 0);
				var v = style ? !!(style.enableMask == true) : false;
				this.style.enableMask = v;
				// this.wnd.enableMask(v ? 1 : 0);
				var v = style ? !!(style.enableMainPopMenu == true) : false;
				this.style.enableMainPopMenu = v;
				// this.wnd.enableMainPopMenu(v ? 1 : 0);
			};
		},
        
		/*
        ---
        fn: NCObjectNotifyStruct 
        desc: NC事件回调信息结构
        author:
            -   
            -  
        time: 2013.09.16 
        params: 
            - eventName(string) 事件名称
            - _HANDLE(string) 句柄
			- status(number) 状态
			- errorCode(number) 错误码
			- keyData(object) 核心数据
        ... 
        */
		NCObjectNotifyStruct : function(eventName, _HANDLE, status, errorCode, keyData)
		{
			this.eventName = eventName || "";
			this._HANDLE = _HANDLE || "";
			if (typeof status != "undefined" && status != null && status !== "")
			{
				this.status = status || "0";
				this.statusDesc = "";
				if (P_LY.Enum.StreamStatusDesc[this.status])
				{
					this.statusDesc = P_LY.Enum.StreamStatusDesc[this.status][P_LY.language] || "";
				}
			}
			this.errorCode = typeof errorCode != "undefined" ? (errorCode || "0") : "0";
			this.keyData =  typeof keyData != "undefined" ? (keyData || {}) : {};
		},
		
		/*
        ---
        fn: WindowEventStruct 
        desc: 窗口事件信息结构
        author:
            -   
            -  
        time: 2013.09.26 
        params: 
			- lbtn_click(object) 鼠标左键点击
			- select_rect(object) 矩形框选择
			- ptz_control(object) 云台控制
			- fsw_show(object) 显示全屏
			- fsw_hide(object) 关闭全屏
			- redraw_window(object) 重绘窗口
			- menu_command(object) 自定义右键菜单项
        */
		WindowEventStruct : function(lbtn_click, select_rect, ptz_control, fsw_show, fsw_hide, redraw_window, menu_command)
		{
			// - 鼠标左键点击
			this.lbtn_click = 
			{
				name : "lbtn_click",
				status : (typeof lbtn_click != "undefined" && typeof lbtn_click.status != "undefined" ? !!(lbtn_click.status == true) : false),
				callback : (typeof lbtn_click != "undefined" && typeof lbtn_click.callback != "undefined" && lbtn_click.callback.constructor == Function ? lbtn_click.callback : function(){})
			};
			// - 矩形框选择
			this.select_rect = 
			{
				name: "select_rect",
				status : (typeof select_rect != "undefined" && typeof select_rect.status != "undefined" ? !!(select_rect.status == true) : false),
				callback : (typeof select_rect != "undefined" && typeof select_rect.callback != "undefined" && select_rect.callback.constructor == Function ? select_rect.callback : function(){})
			};
			
			// - 云台控制（拖拽、鼠标滚轮事件）
			this.ptz_control = 
			{
				name : "",
				status : (typeof ptz_control != "undefined" && typeof ptz_control.status != "undefined" ? !!(ptz_control.status == true) : false),
				callback : (typeof ptz_control != "undefined" && typeof ptz_control.callback != "undefined" && ptz_control.callback.constructor == Function ? ptz_control.callback : function(){}),
				
				// - 向上拖拽
				drag_up : {
					name : "drag_up",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.drag_up != "undefined" && typeof ptz_control.drag_up.status != "undefined" ? !!(ptz_control.drag_up.status == true) : true),
					callback : function() {}
				},
				// - 向下拖拽
				drag_down : 
				{
					name : "drag_down",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.drag_down != "undefined" && typeof ptz_control.drag_down.status != "undefined" ? !!(ptz_control.drag_down.status == true) : true),
					callback : function() {}
				},
				// - 向左拖拽
				drag_left : 
				{
					name : "drag_left",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.drag_left != "undefined" && typeof ptz_control.drag_left.status != "undefined" ? !!(ptz_control.drag_left.status == true) : true),
					callback : function() {}
				},
				// - 向右拖拽
				drag_right : 
				{
					name : "drag_right",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.drag_right != "undefined" && typeof ptz_control.drag_right.status != "undefined" ? !!(ptz_control.drag_right.status == true) : true),
					callback : function() {}
				},
				// - 停止拖拽
				drag_stop : 
				{
					name : "drag_stop",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.drag_stop != "undefined" && typeof ptz_control.drag_stop.status != "undefined" ? !!(ptz_control.drag_stop.status == true) : true),
					callback : function() {}
				},
				// - 鼠标滚轮向前
				mws_forward : 
				{
					name : "mouse_wheel_scroll_forward",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.mws_forward != "undefined" && typeof ptz_control.mws_forward.status != "undefined" ? !!(ptz_control.mws_forward.status == true) : true),
					callback : function() {}
				},
				// - 鼠标滚轮向后
				mws_backward : 
				{
					name : "mouse_wheel_scroll_backward",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.mws_backward != "undefined" && typeof ptz_control.mws_backward.status != "undefined" ? !!(ptz_control.mws_backward.status == true) : true),
					callback : function() {}
				},
				// - 停止鼠标滚轮
				mws_stop : 
				{
					name : "mouse_wheel_scroll_stop",
					status : (typeof ptz_control != "undefined" && typeof ptz_control.mws_stop != "undefined" && typeof ptz_control.mws_stop.status != "undefined" ? !!(ptz_control.mws_stop.status == true) : true),
					callback : function() {}
				}
			}; 
			
			// - 显示全屏
			this.fsw_show = 
			{
				name : "full_screen_window_show",
				status : (typeof fsw_show != "undefined" && typeof fsw_show.status != "undefined" ? !!(fsw_show.status == true) : true),
				callback : (typeof fsw_show != "undefined" && typeof fsw_show.callback != "undefined" && fsw_show.callback.constructor == Function ? fsw_show.callback : function(){})
			};
			// - 关闭全屏
			this.fsw_hide = 
			{
				name : "full_screen_window_hide",
				status : (typeof fsw_hide != "undefined" && typeof fsw_hide.status != "undefined" ? !!(fsw_hide.status == true) : true),
				callback : (typeof fsw_hide != "undefined" && typeof fsw_hide.callback != "undefined" && fsw_hide.callback.constructor == Function ? fsw_hide.callback : function(){})
			};
			// - 重绘窗口
			this.redraw_window =
			{
				name : "redraw_window",
				status : (typeof redraw_window != "undefined" && typeof redraw_window.status != "undefined" ? !!(redraw_window.status == true) : true),
				callback : (typeof redraw_window != "undefined" && typeof redraw_window.callback != "undefined" && redraw_window.callback.constructor == Function ? redraw_window.callback : function(){})
			},
			// - 自定义右键菜单项
			this.menu_command =
			{
				name : "menu_command",
				status : (typeof menu_command != "undefined" && typeof menu_command.status != "undefined" ? !!(menu_command.status == true) : false),
				callback : (typeof menu_command != "undefined" && typeof menu_command.callback != "undefined" && menu_command.callback.constructor == Function ? menu_command.callback : function(){}),
				menu : (function(_menuCommand) {
					if (typeof _menuCommand != "undefined" && typeof _menuCommand.menu == "object")
					{
						if (_menuCommand.menu.constructor != Array)
						{
							_menuCommand.menu = [_menuCommand.menu];
						}
						return _menuCommand.menu || [];
					}
					return [];
				})(menu_command)
			};
		},
		/*
        ---
        fn: WindowTextAddStruct
        desc: 视频窗口叠加文字信息结构
        time: 2013.10.15
        author: 
        	- 
        params:	- 10 -
			- wndBlockNo(number) 字符叠加的序号（默认为0）
			- left(number) 叠加字幕的横向坐标,取值范围不限
			- top(number) 叠加字幕的纵向坐标,取值范围不限
			- width(number) 每个字符的宽度,没有范围,视情况而定
			- height(number) 每个字符的高度,没有范围,视情况而定
			- color(string)  RGB(r,g,b)计算出来的值
			- enableTilt(number) 0表示不倾斜,1表示倾斜,默认不倾斜
			- enableUnderline(number) 0表示不带下划线,1表示带下划线,默认不带下划线
			- fontFamily(string) 字体的字样,宋体,华文行楷,隶书等等,此参数的最大长度为64
			- content(string) 叠加的字符,最大长度128　
        remark:
			- 在本地主机上对视频窗口进行叠加自定义文字
			- wndBlockNo
				= 同时支持16块叠加区域,可用序号0到15整型
				= 就是一个视频窗口最多可以叠加16个自定义的字符串
			- color RGB(r,g,b)计算得出的整数值
				= r(red), g(green), b(blue) -> value of 0~255
				= 公式为(65536 * b + 256 * g + r)
        ... 
        */
		WindowTextAddStruct : function (wndBlockNo, left, top, width, height, color, enableTilt, enableUnderline, fontFamily, content)
		{
            this.wndBlockNo = wndBlockNo || 0;
            this.left = left || 0;
            this.top = top || 0;
            this.width = width || 0;
            this.height = height || 0;
            this.color = isNaN(color) ? 0 : (color || 0);
            this.enableTilt = (isNaN(enableTilt) || P_Utils.Array.indexOf([0, 1], enableTilt) == -1) ? 0 : (enableTilt || 0);
            this.enableUnderline = (isNaN(enableUnderline) || P_Utils.Array.indexOf([0, 1], enableUnderline) == -1) ? 0 : (enableUnderline || 0);
            this.fontFamily = fontFamily || "";
            this.content = content || "";
		},
		/*
        ---
        fn: SCIVDateFileStruct
        desc: 中心存储文件信息结构
        time: 2013.10.30
        author: 
        	- 
        params:	- 10 -
			- fileName(string) 文件名称
			- filePath(string) 文件平台保存根目录
			- fileSize(uint) 文件大小，单位字节
			- beginTime(UTC timestamp) 文件开始时间
			- endTime(UTC timestamp) 文件结束时间
			- reason(String|Array) 文件原因（最终以英文竖线|连接多个原因字符串）
			- csuPuid(stirng) 中心存储器PUID
			- csuIndex(uint) 中心存储单元资源索引，缺省为0
			- puid(string) 所属设备PUID
			- type(string) 所属资源类型，一般固定为P_LY.Enum.PuResourceType.VideoIn
			- idx(uint) 所属资源索引，缺省为0
        ... 
        */
		SCIVDateFileStruct : function (fileName, filePath, fileSize, beginTime, endTime, reason, csuPuid, csuIndex, puid, type, idx)
		{
			this.fileName = fileName || "";
			this.filePath = filePath || "";
			this.fileSize = typeof fileSize != "undefined" && !isNaN(fileSize) ? fileSize : 0;
			this.beginTime = typeof beginTime != "undefined" && !isNaN(beginTime) ? beginTime : 0;
			this.endTime = typeof endTime != "undefined" && !isNaN(endTime) ? endTime : 0;
			if (typeof reason == "object" && reason.constructor == Array)
			{
				reason = reason.join("|");
			}
			this.reason = reason || "";
			this.csuPuid = typeof csuPuid != "undefined" ? csuPuid : "";
			this.csuIndex = typeof csuIndex != "undefined" && !isNaN(csuIndex) ? csuIndex : 0;
			this.puid = typeof puid != "undefined" ? puid : ""; 
			this.type = type || P_LY.Enum.PuResourceType.VideoIn;
			this.idx = typeof idx != "undefined" && !isNaN(idx) ? idx : 0;
		},
		/*
        ---
        fn: CEFSRecordStruct
        desc: 前端CEFS录像文件信息结构
        time: 2013.11.08
        author: 
        	- 
        params:
			- needNextQuery(boolean) 是否需要下次查询
			- beginTime(UTC timestamp) 文件开始时间
			- endTime(UTC timestamp) 文件结束时间
			- bitRate(uint) 这段录像的平均码率，单位kbps，可以估计出这段录像的大小
			- reason(P_LY.Enum.CEFSRecordReason) 文件原因
			- puid(string) 所属设备PUID
			- resIdx(uint) 所属资源索引，缺省为0
		remark:
			- nextQueryBeginTime(UTC timestamp) 下次查询时间（needNextQuery=true时有此节点）
			- resType(string) 所属资源类型，一般固定为P_LY.Enum.PuResourceType.VideoIn
        ... 
        */
		CEFSRecordStruct : function (needNextQuery, beginTime, endTime, bitRate, reason, puid, resIdx)
		{
			this.needNextQuery = typeof needNextQuery != "undefined" && needNextQuery == true ? true : false;
			if (this.needNextQuery == true)
			{
				this.nextQueryBeginTime = typeof beginTime != "undefined" && !isNaN(beginTime) ? beginTime : 0; 	
			}
			this.beginTime = typeof beginTime != "undefined" && !isNaN(beginTime) ? beginTime : 0; 
			this.endTime = typeof endTime != "undefined" && !isNaN(endTime) ? endTime : 0;
			this.bitRate = typeof bitRate != "undefined" && !isNaN(bitRate) ? bitRate : 0;
			this.reason = reason || "";
			this.puid = puid || "";
			this.resType = P_LY.Enum.PuResourceType.VideoIn;
			this.resIdx = typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : 0;
		},
		/*
        ---
        fn: CEFSPictureStruct
        desc: 前端CEFS图片文件信息结构
        time: 2013.11.08
        author: 
        	- 
        params:
			- needNextQuery(boolean) 是否需要下次查询
			- time(UTC timestamp) 文件开始时间
			- noInSecond(uint) 秒内编号，一秒内可能有多个图片
			- reason(P_LY.Enum.CEFSPictureReason) 文件原因
			- puid(string) 所属设备PUID
			- resIdx(uint) 所属资源索引，缺省为0
		remark:
			- nextQueryBeginTime(UTC timestamp) 下次查询时间（needNextQuery=true时有此节点）
			- resType(string) 所属资源类型，一般固定为P_LY.Enum.PuResourceType.VideoIn
        ...
        */
		CEFSPictureStruct : function (needNextQuery, time, noInSecond, reason, puid, resIdx)
		{
			this.needNextQuery = typeof needNextQuery != "undefined" && needNextQuery == true ? true : false;
			if (this.needNextQuery == true)
			{
				this.nextQueryBeginTime = typeof time != "undefined" && !isNaN(time) ? time : 0; 	
			}
			this.time = typeof time != "undefined" && !isNaN(time) ? time : 0;
			this.noInSecond = typeof noInSecond != "undefined" && !isNaN(noInSecond) ? noInSecond : 0;
			this.reason = reason || "";
			this.puid = puid || "";
			this.resType = P_LY.Enum.PuResourceType.VideoIn;
			this.resIdx = typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : 0;
		},
		/*
        ---
        fn: CEFSUserLogStruct
        desc: 前端CEFS用户日志信息结构
        time: 2013.11.08
        author: 
        	- 
        params:
			- needNextQuery(boolean) 是否需要下次查询
			- time(UTC timestamp) 文件开始时间
			- type(P_LY.Enum.CEFSUserLogType) 用户日志类型
			- data(string) 用户日志数据（Base64编码的）
			- puid(string) 所属设备PUID
		remark:
			- nextQueryBeginTime(UTC timestamp) 下次查询时间（needNextQuery=true时有此节点）
        ...
        */
		CEFSUserLogStruct : function (needNextQuery, time, type, data, puid)
		{
			this.needNextQuery = typeof needNextQuery != "undefined" && needNextQuery == true ? true : false;
			if (this.needNextQuery == true)
			{
				this.nextQueryBeginTime = typeof time != "undefined" && !isNaN(time) ? time : 0; 	
			}
			this.time = typeof time != "undefined" && !isNaN(time) ? time : 0;
			this.type = type || "";
			this.data = data || "";
			this.puid = puid || "";
		},
		/*
        ---
        fn: CEFSAlarmEventStruct
        desc: 前端CEFS报警事件信息结构
        time: 2013.11.08
        author: 
        	- 
        params:
			- needNextQuery(boolean) 是否需要下次查询
			- time(UTC timestamp) 文件开始时间
			- type(P_LY.Enum.CEFSAlarmEventType) 报警事件类型
			- data(string) 报警事件数据（Base64编码的）
			- puid(string) 所属设备PUID
			- resIdx(uint) 所属资源索引，缺省为
		remark:
			- nextQueryBeginTime(UTC timestamp) 下次查询时间（needNextQuery=true时有此节点）
			- resType(string) 所属资源类型，一般固定为P_LY.Enum.PuResourceType.AlertIn
        ...
        */
		CEFSAlarmEventStruct : function (needNextQuery, time, type, data, puid, resIdx)
		{
			this.needNextQuery = typeof needNextQuery != "undefined" && needNextQuery == true ? true : false;
			if (this.needNextQuery == true)
			{
				this.nextQueryBeginTime = typeof time != "undefined" && !isNaN(time) ? time : 0; 	
			}
			this.time = typeof time != "undefined" && !isNaN(time) ? time : 0;
			this.type = type || "";
			this.data = data || "";
			this.puid = puid || "";
			this.resType = P_LY.Enum.PuResourceType.AlertIn;
			this.resIdx = typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : 0;
		},
		/*
        ---
        fn: CEFSGPSDataStruct
        desc: 前端GPS数据信息结构
        time: 2013.11.08
        author: 
        	- 
        params:
			- needNextQuery(boolean) 是否需要下次查询
			- time(UTC timestamp) 文件开始时间
			- latitude(uint) 纬度,浮点型字符串,取值范围[-90,90],北纬为正,南纬为负
			- longitute(uint) 经度,浮点型字符串,取值范围[-180,180),东经为正,西经为负
			- bearing(uint) 方向,浮点型字符串,取值范围[0,360),正北为0,正东为90,依次类推.
			- speed(uint) 速度,浮点型字符串,单位km/h
			- altitude(uint)海拔,浮点型字符串,单位m,小于等于-50000表示无效.
			- offlineFlag(uint) 离线标识,1表示下线是存的数据,0表示实时数据.
			- state(uint) GPS状态,0表示正常,1表示无信号,2表示无模块.
			- maxSpeed(uint) 最高限速,整型字符串,单位km/h
			- minSpeed(uint) 最低限速,整型字符串,单位km/h
			- puid(string) 所属设备PUID
		remark:
			- nextQueryBeginTime(UTC timestamp) 下次查询时间（needNextQuery=true时有此节点）
        ...
        */
		CEFSGPSDataStruct : function (needNextQuery, time, latitude, longitute, bearing, speed, altitude, offlineFlag, state, maxSpeed, minSpeed, puid)
		{
			this.needNextQuery = typeof needNextQuery != "undefined" && needNextQuery == true ? true : false;
			if (this.needNextQuery == true)
			{
				this.nextQueryBeginTime = typeof time != "undefined" && !isNaN(time) ? time : 0; 	
			}
			this.time = typeof time != "undefined" && !isNaN(time) ? time : 0;
			this.latitude = typeof latitude != "undefined" && !isNaN(latitude) ? latitude : 0;
			this.longitute = typeof longitute != "undefined" && !isNaN(longitute) ? longitute : 0;
			this.bearing = typeof bearing != "undefined" && !isNaN(bearing) ? bearing : 0;
			this.speed = typeof speed != "undefined" && !isNaN(speed) ? speed : 0;
			this.altitude = typeof altitude != "undefined" && !isNaN(altitude) ? altitude : 0;
			this.offlineFlag = typeof offlineFlag != "undefined" && !isNaN(offlineFlag) ? offlineFlag : 0;
			this.state = typeof state != "undefined" && !isNaN(state) ? state : 0;
			this.maxSpeed = typeof maxSpeed != "undefined" && !isNaN(maxSpeed) ? maxSpeed : 0;
			this.minSpeed = typeof minSpeed != "undefined" && !isNaN(minSpeed) ? minSpeed : 0;
			this.puid = puid || "";
		},
		
		/*
		---
		fn: CommonResDescriptionStruct
		desc: 资源描述信息结构
		author:
			- 
		time: 2013.11.14
		...
		*/
		CommonResDescriptionStruct : function (puid, resType, resIdx, name, description, enable)
		{
			this.puid = (typeof puid != "undefined" ? puid : "");
            this.resType = (typeof resType != "undefined" ? resType : "");
            this.resIdx = (typeof resIdx != "undefined" ? resIdx : "0");
            this.name = (typeof name != "undefined" ? name : "");
            this.description = (typeof description != "undefined" ? description : "");
            this.enable = (typeof enable != "undefined" ? enable : "1");
		},
		// - ：平台存储计划信息结构
		PlatformStoragePlanStruct : function (name, guard, cycle, cycleParam, guardTimeMap, serialNo, serialToken)
		{
            this.name = (typeof name != "undefined" ? name : "");
            this.guard = (typeof guard != "undefined" ? guard : "1");
            this.cycle = (typeof cycle != "undefined" ? cycle : "");
            this.cycleParam = (typeof cycleParam != "undefined" ? cycleParam : "");
            this.guardTimeMap = (typeof guardTimeMap != "undefined" ? guardTimeMap : "");
            this.serialNo = (typeof serialNo != "undefined" ? serialNo : "");
            this.serialToken = (typeof serialToken != "undefined" ? serialToken : "");
        },
		// - 平台存储计划资源信息结构
		PlatformStoragePlanResourceStruct: function (name, puid, resType, resIdx, optID, value)
		{
         	this.name = (typeof name != "undefined" ? name : "");
            this.puid = (typeof puid != "undefined" ? puid : "");
            this.resType = (typeof resType != "undefined" ? resType : "");
            this.resIdx = (typeof resIdx != "undefined" ? resIdx : "");
            this.optID = (typeof optID != "undefined" ? optID : "");
            this.value = (value != null || typeof value != "undefined" ? value : "");
        },
		
		// - GPS数据存储资源结构
		GPSDataStorageResourceStruct : function (puid, resType, resIdx, optID, value)
		{
			this.puid = (typeof puid != "undefined" ? puid : "");
            this.resType = (typeof resType != "undefined" ? resType : "");
			this.resIdx = typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : 0;;
            this.optID = (typeof optID != "undefined" ? optID : "");
            this.value = (value != null || typeof value != "undefined" ? value : P_LY.Enum.StreamType.REALTIME);
		},
		// - 中心存储GPS数据文件信息结构
		CSUGPSDataFileStruct : function (time, latitude, longitute, bearing, speed, altitude, state, maxSpeed, minSpeed, timestamp, puid, resType, resIdx)
		{
			this.time = typeof time != "undefined" && !isNaN(time) ? time : 0;
			this.latitude = typeof latitude != "undefined" && !isNaN(latitude) ? latitude : 0;
			this.longitute = typeof longitute != "undefined" && !isNaN(longitute) ? longitute : 0;
			this.bearing = typeof bearing != "undefined" && !isNaN(bearing) ? bearing : 0;
			this.speed = typeof speed != "undefined" && !isNaN(speed) ? speed : 0;
			this.altitude = typeof altitude != "undefined" && !isNaN(altitude) ? altitude : 0;
			this.state = typeof state != "undefined" && !isNaN(state) ? state : 0;
			this.maxSpeed = typeof maxSpeed != "undefined" && !isNaN(maxSpeed) ? maxSpeed : 0;
			this.minSpeed = typeof minSpeed != "undefined" && !isNaN(minSpeed) ? minSpeed : 0;
			this.timestamp = typeof timestamp != "undefined" && !isNaN(timestamp) ? timestamp : 0;
			this.puid = puid || "";
			this.resType = resType || P_LY.Enum.PuResourceType.GPS;
			this.resIdx = typeof resIdx != "undefined" && !isNaN(resIdx) ? resIdx : 0;;
		},
		
		// - 网络文件下载状态结构
        HttpDownloadStatusStruct: function (flag, desc, speed, total, current)
		{
            this.flag = flag || 0;
            this.description = desc || "";
            this.speed = speed || 0;
            this.totalLength = total || 0;
            this.currentLength = current || 0;
        },
		
        end : true 
    },
    // - 枚举对象
    Enum : 
    { 
		// - 流类型
        StreamType : P_IF.Enum.StreamType,
		
		// - 云台控制命令
		PTZDirection : P_IF.Enum.PTZDirection,
		
		// - NC对象方法列表
		NCObjectMethodList : P_IF.Enum.NCObjectMethodList,
		
        // - 浏览器类型 
        BrowserType : 
		{
            "IE" : "IE", 
			"Chrome" : "Chrome",
			"FireFox" : "FireFox",
			"Other" : "Other" 
        },
        /*
        ---
        author:
                - 
        time: 2013.09.03
        remark:
            - P_LY SDK支持的语言
        ...
        */
        LanguageType : 
		{
            zh_CN : "zh_CN", /* 中文 */
            en : "en" /* 英文 */
        },
        // - 建立的连接类型
        ConnectionType : 
		{
            Server : "Server", // - 连接C7平台服务器
            Device : "Device" // - 直连设备
        },
        // - 建立的连接状态
        ConnectionStatus : 
        {
            Idle : "Idle", // - 初始状态
            Connecting : "Connecting", // - 正在建立连接
            Connected : "Connected", // - 连接已建立
            Failed : "Failed" // - 建立连接失败
        },
		// - 请求的命令类型
		CmdType :
		{
			"G" : "G", // - 获取命令
			"S" : "S", // - 设置命令
			"P" : "P", // - 预览命令
			"C" : "C" // - 控制命令
		},
		/*
        ---
        author:
            - 
        time: 2013.10.18
        remark:
            - 窗口信息结构类型
        ...
        */
		WindowType : 
		{
			VIDEO : "VIDEO", // - 实时视频
			VOD : "VOD" // - 录像回放
		},
		// - 存储文件类型
		StorageFileType :
		{
			Cloud : "Cloud", // - 中心存储
			Platform : "Platform", // - 中心存储
			CEFS : "CEFS", // - 前端CEFS存储
			FAT32 : "FAT32" // - 前端FAT32存储		
		},
		// - CEFS查询命令
		CEFSRequestID : 
		{
			QueryRecordDate : "QueryRecordDate", // - 查询录像日期命令
			QueryRecord : "QueryRecord", // - 查询录像命令
			QueryLinkActionRecord : "QueryLinkActionRecord", // - 查询联动录像命令
			QueryPictureDate : "QueryPictureDate", // - 查询图片日期命令
			QueryPicture : "QueryPicture", // - 查询图片命令
			QueryLinkActionPicture : "QueryLinkActionPicture", // - 查询联动抓图命令
			QueryPictureHalfHour : "QueryPictureHalfHour", // - 查询半小时图片命令
			QueryUserLog : "QueryUserLog", // - 查询用户日志命令
			QueryUserLogDate : "QueryUserLogDate", // - 查询用户日志日期命令
			QueryUserLogHalfHour : "QueryUserLogHalfHour", // - 查询半小时用户日志命令
			QueryAlarmEvent : "QueryAlarmEvent", // - 查询报警事件命令
			QueryAlarmEventDate : "QueryAlarmEventDate", // - 查询报警事件日期命令
			QueryAlarmEventHalfHour : "QueryAlarmEventHalfHour", // - 查询半小时报警事件命令
			QueryGPSData : "QueryGPSData", // - GPS数据查询命令
			QueryGPSDataDate : "QueryGPSDataDate" // - 查询GPS数据日期命令
		},
		// - CEFS录像原因
		CEFSRecordReason :
		{
			Schedule : "Schedule", // - 前端计划录像
			Manual : "Manual", // - 手动录像
			AlertIn : "LinkAction(AlertIn)", // - 联动录像（发生报警）
			MotionDetected : "LinkAction(MotionDetected)", // - 联动录像（侦测到移动）
			SignalLost : "LinkAction(SignalLost)" // - 联动录像（视频信号丢失）
		},
		// - CEFS录像原因描述
		CEFSRecordReasonDesc :
		{
			Schedule : {"zh_CN" : "前端计划录像", "en" : "Front plan video"},
			Manual : {"zh_CN" : "手动录像", "en" : "Menual video"},
			"LinkAction(AlertIn)" : {"zh_CN" : "联动录像（发生报警）", "en" : "LinkAction video(Alarm)"},
			"LinkAction(MotionDetected)" : {"zh_CN" : "联动录像（侦测到移动）", "en" : "LinkAction video(Motion Detected)"},
			"LinkAction(SignalLost)" : {"zh_CN" : "联动录像（视频信号丢失）", "en" : "LinkAction video(Signal Lost)"}
		},
		// - CEFS抓拍原因
		CEFSPictureReason :
		{
			Schedule : "Schedule", // - 前端抓拍录像
			Manual : "Manual", // - 手动抓拍
			AlertIn : "LinkAction(AlertIn)", // - 联动抓拍（发生报警）
			MotionDetected : "LinkAction(MotionDetected)", // - 联动抓拍（侦测到移动）
			SignalLost : "LinkAction(SignalLost)" // - 联动抓拍（视频信号丢失）
		},
		// - CEFS抓拍原因描述
		CEFSPictureReasonDesc :
		{
			Schedule : {"zh_CN" : "前端抓拍录像", "en" : "Front plan snapshot"},
			Manual : {"zh_CN" : "手动抓拍", "en" : "Menual snapshot"},
			"LinkAction(AlertIn)" : {"zh_CN" : "联动抓拍（发生报警）", "en" : "LinkAction snapshot(Alarm)"},
			"LinkAction(MotionDetected)" : {"zh_CN" : "联动抓拍（侦测到移动）", "en" : "LinkAction snapshot(Motion Detected)"},
			"LinkAction(SignalLost)" : {"zh_CN" : "联动抓拍（视频信号丢失）", "en" : "LinkAction snapshot(Signal Lost)"}
		},
		// CEFS用户日志类型
		CEFSUserLogType :
		{
			UserLogin : "UserLogin", // - 用户登录
			UserLogout : "UserLogout", // - 用户退出
			Booted : "Booted", // - 系统启动
			Reboot : "Reboot", // - 系统重启
			AdjustTime : "AdjustTime", // - 修改系统时间
			Formatted : "Formatted", // - 完成格式化
			OpenDoor : "OpenDoor", // - 打开挡板
			CloseDoor : "CloseDoor", // - 关闭挡板
			PowerOff : "PowerOff" // - 系统关机
		},
		// CEFS用户日志类型描述
		CEFSUserLogTypeDesc :
		{
			UserLogin : {"zh_CN" : "用户登录", "en" : "User Logon"},
			UserLogout : {"zh_CN" : "用户退出", "en" : "User Logoff"},
			Booted : {"zh_CN" : "系统启动", "en" : "System Start"},
			Reboot : {"zh_CN" : "系统重启", "en" : "System Reboot"},
			AdjustTime : {"zh_CN" : "修改系统时间", "en" : "Modify System Time"},
			Formatted : {"zh_CN" : "完成格式化", "en" : "Done Format"},
			OpenDoor : {"zh_CN" : "打开挡板", "en" : "Open Baffle plate"},
			CloseDoor : {"zh_CN" : "关闭挡板", "en" : "Close Baffle plate"},
			PowerOff : {"zh_CN" : "系统关机", "en" : "System Power Off"}
		},
		// - 报警事件类型
		CEFSAlarmEventType : 
		{
			AlertIn : "AlertIn", // - 报警输入发生报警
			MotionDetected : "MotionDetected", // - 视频侦测到移动
			SignalLost : "SignalLost" // - 视频信号丢失
		},
		// - 报警事件类型描述
		CEFSAlarmEventTypeDesc : 
		{
			AlertIn : {"zh_CN" : "报警输入发生报警", "en" : "Alarm"}, 
			MotionDetected : {"zh_CN" : "视频侦测到移动", "en" : "Motion Detected"}, 
			SignalLost : {"zh_CN" : "视频信号丢失", "en" : "Signal Lost"} 
		},
		/*
        ---
        author:
            - 
        time: 2013.09.05
        remark:
            - 获取或构建资源级别
        ...
        */
		ForkResourceLevel : 
		{
			nppForkPUInfo : "nppForkPUInfo", // - 获取PU节点信息
			nppForkPUResourceInfo : "nppForkPUResourceInfo", // - 获取PU节点资源信息
			nppForkOnePUInfo : "nppForkOnePUInfo" // - 获取一个PU节点信息
		},
		/*
        ---
        author:
                - 
        time: 2013.09.09
        remark:
            - 设备类型
        ...
        */
		PuModelType :
		{
			"ENC" : "ENC", /* 有线编码器 */
		    "WENC" : "WENC", /* 无线编码器 */
		    "DEC" : "DEC", /* 有线解码器 */
		    "WDEC" : "WDEC", /* 无线解码器 */
		    "CSU" : "CSU", /* 中心存储单元 */
		    "ESU" : "ESU" /* 企业自建存储单元 */
		},
		/*
        ---
        author:
                - 
        time: 2013.09.05
        remark:
            - 资源类型
        ...
        */
		PuResourceType : 
		{
			"GPS" : "GPS", /* GPS模块 */
		    "WIFI" : "WIFI", /* WIFI模块 */
		    "AudioIn" : "IA", /* 输入音频 */
		    "AudioOut" : "OA", /* 输出音频 */
		    "AlertIn" : "IDL", /* 输入报警 */
		    "AlertOut" : "ODL", /* 输出报警 */
		    //"IV" : "IV", /* 输入视频 */
		    "VideoIn" : "IV", /* 输入视频 */
		    "VideoOut" : "OV", /* 输出视频 */
		    "PTZ" : "PTZ", /* 云台 */
		    "ST" : "ST", /* 设备本身 */
		    "Storager" : "SG", /* 存储器(前端存储) */
		    "SerialPort" : "SP", /* 设备串口 */
		    "DP" : "DP", 
		    "Wireless" : "WM" /* 无线模块 */
		},
		

		// - 计划取值范围
		PlanCycleValue :
		{
			Weekly : "Weekly", // - 每周
			Everyday : "Everyday", // - 每天
			Once : "Once" // - 一次性
		},
		/*
        ---
        author:
                - 
        time: 2013.09.09
        remark:
            - nc事件对象
        ...
        */
		NCObjectNotify :
		{
			"event_notify" : "event_notify", /* 接收平台（设备）事件通知 */
		    "stream_status_notify" : "stream_status_notify", /* 接收流状态通知 */
		    "gps_data_notify" : "gps_data_notify" /* 接收GPS信息通知 */
		},		
		// - 流状态描述
		StreamStatusDesc : {
			"-1" : {"zh_CN" : "流已断开", "en" : "Stream Broken"}, // - 流已断开
			"0" : {"zh_CN" : "正在缓冲", "en" : "Buffering"}, // - 正在缓冲
			"1" : {"zh_CN" : "正在播放", "en" : "Playing"}, // - 正在播放
			"2" : {"zh_CN" : "正在下载", "en" : "Downloading"}, // - 正在下载
			"3" : {"zh_CN" : "下载完成", "en" : "Completed"} // - 下载完成
		},
        // - 温馨提示
        "warmTip" : 
        {
            "zh_CN" : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<b>温馨提示</b>]插件装载失败，请您检查一下是否成功安装插件！&nbsp;&nbsp;<a href=\"#(0)\">这里下载</a>&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"window.location.reload();\">刷新重试</a><br />", 
            "en" : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[<b>Warning</b>]Plug-in load failed, please check whether it was installed plug-in success or not!&nbsp;&nbsp;<a href=\"#(0)\">Download here</a>&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"window.location.reload();\">Refresh page</a><br />", 
            end : true 
        },
		
		// - 网络文件下载状态描述
		HttpDownloadStatusDesc : 
		{
			"-1" : {"zh_CN" : "下载失败", "en" : "Failed"},
			"0" : {"zh_CN" : "下载完成", "en" : "Completed"},
			"1" : {"zh_CN" : "正在下载", "en" : "Downloading"}
		},
		
        end : true 
    },
	
    end : true
};
