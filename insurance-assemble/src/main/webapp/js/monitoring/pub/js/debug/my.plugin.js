var _p = myplugin = {
    connections: new Array(),
    puInfoArray:new Array(),
    loginParams: null,
    logicGroupArray: new Array(),
    resource: new P_Utils.Hash(),
    vodResourece: new P_Utils.Hash(),
    WindowContainers: new P_Utils.Hash(),
    curChangWndFlage: false,
	mapType : "baidu",
	ConfigurationFilePath : null,	
	enableSort : true,
	isArray:$.isArray,
	ResOAChannels: new P_Utils.Hash(),
	resOAIndex:0,
	// 格式时间
	data_format: function (formate, time) {
        return P_Utils.DateFormat(formate, time);
    },
    
    // 初始化SDK的plugin对象
    init: function (type) {
		var initParam = 
		{
			language : _cf.language,
			type : type || "",
			warmTip : 
			{
				active : true,
				pluginFile : _cf.pluginFile
			}
		}

		var rv = P_LY.Init(new P_LY.Struct.InitParamStruct(
			_cf.debug, 
			function(msg)
			{
				if(_cf.debug) console.log(msg);
			},
			initParam
		));
	
		if(rv.rv != P_Error.SUCCESS)
		{
			return false;
		}
        return true;
    },
    
    // 卸载SDK的插件对象
    exit: function () 
	{
        P_LY.UnLoad();
    },

    // 退出
    logout: function (rv) 
	{
        if (rv == P_Error.SUCCESS) 
		{
            var connections = new Array();
            var logicGroupArray = new Array();
            var resource = new Hash();
        }
        else {
            return false;
        }
    },
	
    // 断开一个连接
    disconnection: function (connectId) 
	{
        return P_LY.DisConnection(connectId);
    },
    
	// 创建一个连接
    connect: function (ip,port, epId, username, password, bfix,callback) 
	{
			var loginParam = new P_IF.Struct.ConnParamStruct(ip,port, username, epId, password, bfix);
			var conn = P_LY.Connect(loginParam);
			
			if (conn.rv != P_Error.SUCCESS)
			{
				if(typeof callback == "function"){
					callback(conn.rv,"");		
				}
				return false;
			}
			else
			{
				var connectId = conn.response;
				//if(_p.connections.indexOf(connectId) < 0)
				
				if($.inArray(connectId,_p.connections) < 0)
				{
					
					// 保存connectId到myplugin对象里
					_p.connections.push(connectId);
					var t = P_LY.Connections.get(connectId);
					//_f.cb(P_Error.SUCCESS,connectId);
					if(typeof callback == "function"){
						callback(P_Error.SUCCESS,connectId);
					}

			        //var rv = P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.event_notify,function(notify){_p.event_notify(connectId,notify);});
			        //rv = P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.stream_status_notify,function(notify){_p.stream_status_notify(notify);});
			        		
					
					
			        var rv = P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.event_notify, function(notify){
			        	
			        	if(notify.errorCode <0){
		        			// 与服务器断开连接
		        			_f.exit();
			        	}
			        	var events = $(notify.keyData).find("E");
			        	for(var i = 0;i < events.length;i++){
			        		var e = events[i];
			        		var e_id = $(e).attr("ID");
			        		if(e_id.search("E_OA") > -1){
			        			_pl.audioout_status_changed(notify);
			        		}
			        		var e_src = $(e).find("Src");
			        		var e_src_type = $(e_src).attr("Type");
			        		var e_src_id = $(e_src).attr("ID");
			        		//<Src Type="201" ID="201000000000000015">
			        		switch(e_id){
			        		case "E_PU_Online":
			        		//case "E_DS_PUStreamConnectionOpened":
			        			_p.pu_online(connectId,e_src_id);
			        			break;
			        		//case "E_DS_PUStreamConnectionClosed":
			        		case "E_PU_Offline":
			        			// 通知页面，设备下线，刷新一下列表
			        			_p.pu_offline(e_src_id);
			        			break;
			        		case "E_CUSTOM_ConnectionBreakOff":
			        			// 与服务器断开连接
			        			_f.exit();
			        			break;
			        		}
			        		
			        	}
			        	
			        	//var keyData = arguments.keyData;
			        	// 分析事件xml
			        });
			        

			        P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.stream_status_notify,function(status){
			        	if(_v && typeof _v.notify_downloadfile == "function"){
			        		_v.notify_downloadfile(status);
			        	}

			        	if(_pl && typeof _pl.notify_stream == "function"){
			        		_pl.notify_stream(status);
			        	}
			        });
					
				}
				return true;
			}
    },
    event_notify:function(connectId,notify){
    	var events = $(notify.keyData).find("E");
    	for(var i = 0;i < events.length;i++){
    		var e = events[i];
    		var e_id = $(e).attr("ID");
    		var e_src = $(e).find("Src");
    		var e_src_type = $(e_src).attr("Type");
    		var e_src_id = $(e_src).attr("ID");
    		//console.log(notify)
    		//<Src Type="201" ID="201000000000000015">
    		switch(e_id){
    		case "E_PU_Online":
    		//case "E_DS_PUStreamConnectionOpened":
    			_p.pu_online(connectId,e_src_id);
    			break;
    		//case "E_DS_PUStreamConnectionClosed":
    		case "E_PU_Offline":
    			// 通知页面，设备下线，刷新一下列表
    			_p.pu_offline(e_src_id);
    			break;
    		case "E_CUSTOM_ConnectionBreakOff":
    			// 与服务器断开连接
    			_f.exit();
    			break;
    		}
    		
    	}
    },
    stream_status_notify:function(status){
    	
    	if(_v && typeof _v.notify_downloadfile == "function"){
    		console.log(status)
    		_v.notify_downloadfile(status);    		
    	}
    	if(_pl && typeof _pl.notify_stream == "function"){
    		_pl.notify_stream(status);
    	}

    	if(_mp && typeof _mp.notify_stream == "function"){
    		_pl.notify_stream(status);
    	}
    },
    gps_data_notify:function(notify){
    	
    },
    
    get_platform_type:function(connectId){
    	var conn = P_LY.Connections.get(connectId);
    	if(conn){
    		return conn.connType;
    	}else{
    		return "";
    	}
    },

	query_userinfo: function (connectId) 
	{
		try
		{
			if (P_LY.Connections.get(connectId)) 
			{
				return P_LY.Connections.get(connectId).connParam;
			}
		}
		catch(e)
		{
			if(_cf.debug) console.log(e);
		}
    },
    
    pu_online:function(connectId,puid){
    	var operator = P_LY.ForkResource(connectId, P_LY.Enum.ForkResourceLevel.nppForkOnePUInfo, 0, 0 , null, { PUID: puid });
    	if(operator.rv == P_Error.SUCCESS){
			var pu = operator.response;
			if (pu && typeof pu == "object" ) {
		    	//_p.puInfoArray.push(pu);
				var flag = true;
		    	for(var i = 0;i < _p.puInfoArray.length;i++){
		    		if(_p.puInfoArray[i].puid == pu.puid){
		    			_p.puInfoArray[i] = pu;
		    			flag = false;
		    		}
		    	}
		    	if(flag){
		    		_p.puInfoArray.push(pu);
		    	}
		    	_f.pu_online(puid);
			}
		}
    },
    
    pu_offline:function(puid){
    	for(var i = 0;i < _p.puInfoArray.length;i++){
    		if(_p.puInfoArray[i].puid == puid){
    			_p.puInfoArray[i].online = "0";
    			break;
    		}
    	}
    	_f.pu_offline(puid);
    },
    
    fetch_resource:function(connectId){
 
		try
		{
			var puInfoArray = new Array();
			var cascadePuInfoArray = new Array();
			var csuInfoArray = new Array();
			_p.resource.set(connectId, { status: "querying", puInfoArray: puInfoArray, csuInfoArray: csuInfoArray });
		
			puInfoArray = _p.fetch_resource_bypage(connectId, "") || [];
		
			var cascadeResource = new P_Utils.Hash();
			if(P_LY.Connections.get(connectId) && P_LY.Connections.get(connectId).domainRoad){
				var domainRoads = P_LY.Connections.get(connectId).domainRoad;
				if(domainRoads && _p.isArray(domainRoads)){
					for(var j = 0; j < domainRoads.length; j++){
						var dr = domainRoads[j].domain_road;
					    cascadePuInfoArray = _p.fetch_resource_bypage(connectId,dr) || [];
						cascadeResource.set(dr,{status: "done", domainRoadName:dr, puInfoArray:cascadePuInfoArray, csuInfoArray: new Array()});
					}
				} 
			}

			_p.cascadeResource = cascadeResource;
			_p.puInfoArray = puInfoArray;
		
			_p.csuInfoArray = csuInfoArray;
			_p.resource.set(
				connectId,{ 
					status: "done", 
					puInfoArray: puInfoArray, 
					csuInfoArray: csuInfoArray, 
					cascadeResource: cascadeResource
				}
			);
		}
		catch(e)
		{
			if(_cf.debug) console.log(e);
		}
    },
    
    fetch_resource_bypage:function(connectId,domainRoadName){
        
		if(!connectId || !P_LY.Connections.get(connectId)) return false;
        var domainRoadName = domainRoadName || "";
	       var puInfoArray = new Array();
	       
        if(P_LY.Connections.get(connectId).connType == P_LY.Enum.ConnectionType.Device){
        	// 直连设备
			var operator = P_LY.ForkResource(connectId, P_LY.Enum.ForkResourceLevel.nppForkPUInfo, 0, 100, domainRoadName);
			if(operator.rv == P_Error.SUCCESS){
				var pu = operator.response;
				if (pu && typeof pu == "object" ) {
					puInfoArray.push(pu);
				}
			}else{
				if(_cf.debug) console.log(operator);
			}
			
        }else{
	        var offset = 0, count = 200;
	        
	        while (true) {
				var operator = P_LY.ForkResource(connectId, P_LY.Enum.ForkResourceLevel.nppForkPUInfo, offset, count, domainRoadName);
				if(operator.rv == P_Error.SUCCESS){
					var puArrayPage = operator.response;
					if (puArrayPage && typeof puArrayPage == "object" && _p.isArray(puArrayPage) && puArrayPage.length > 0) {
						for (var i = 0; i < puArrayPage.length; i++) {
							puArrayPage[i].childResource = new Array(); 
						}
						puInfoArray = puInfoArray.concat(puArrayPage);
						offset = parseInt(offset + puArrayPage.length);
						if (puArrayPage.length < count) {
							break;
						}
					}
					else {
						break;
					}
				}else{
					if(_cf.debug) //console.log(operator);
					break;
				}
	        }
        }
        
        if(domainRoadName && _p.resource.get(connectId)){
            cascadeResource = _p.resource.get(connectId).cascadeResource;
            if(cascadeResource && (cascadeResource instanceof P_Utils.Hash ) ){	
                if(cascadeResource.get(domainRoadName) && cascadeResource.get(domainRoadName).status == "ready"){
                    cascadeResource.get(domainRoadName).puInfoArray = puInfoArray;
                    cascadeResource.get(domainRoadName).status = "done";
                }
            }
        }
		
		if (this.enableSort === true) {
            // 资源排序
            //puInfoArray = this.SortingPUResource(puInfoArray) || [];
        }
        return puInfoArray || []; 
    },
    

	// 获取pu的子资源
	query_puresource: function (connectId, puid) 
	{
		var handler = "";
		for(var i = 0;i < _p.puInfoArray.length;i++){
			var p = _p.puInfoArray[i];
			
			if(p.puid == puid){
				handler = p._HANDLE;
				break;
			}
		}
	
		var operator = P_LY.ForkResource(connectId, P_LY.Enum.ForkResourceLevel.nppForkOnePUInfo, 0, 0 , null, { PUID: puid,_HANDLE:handler});
		
		if(operator.rv == P_Error.SUCCESS)
		{
			// update _p.puInfoArray
			for(var i = 0;i < _p.puInfoArray.length;i++){
				if(_p.puInfoArray[i].puid == puid){
					_p.puInfoArray[i] = operator.response;
					break;
				}
			}
			return operator.response;
		}
		return null;
    },
    
	
    // 
    get_res_config_id_set:function (connectId,puid,type,idx){
    	
    	//_cf.language = _cf.language.replace("zh-CN","zh_CN");
    	// 暂时取出原始报文
    	var rv = P_LY.Config.GetComplexEx(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:type,
				resIdx:idx,
				dstRes:'<Res Type="'+(type||"")+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="F_RES_ConfigIDSets"  ></Res>'
			}
		);
    	return rv.response;
    },

    // 
    get_res_config_id_sets:function (connectId,puid,paramAttrs){
    	// 暂时取出原始报文
    	var rv = P_LY.Config.GetComplexEx(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:"",
				resIdx:"",
				dstRes:paramAttrs//'<Res Type="'+(type||"")+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="F_RES_ConfigIDSets"  ></Res>'
			}
		);
    	return rv.response;
    },    
    

    get_res_config:function (connectId,puid,type,idx,configId){

    	//_cf.language = _cf.language.replace("zh-CN","zh_CN");
    	// 暂时取出原始报文
    	var rv = P_LY.Config.GetComplexEx(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:type,
				resIdx:idx,
				//configID:configId,
				dstRes:'<Res Type="'+(type||"")+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="'+(configId||"")+'"  ></Res>'
			}
		);
    	return rv.response;
    },
    
    get_res_configs:function (connectId,puid,configsets){
	
    	// 暂时取出原始报文
    	var params = "";//<Param Value='"+values[i].val+"' />
    	for(var i = 0;i < configsets.length;i++){
    		var cf = configsets[i];
    		if(typeof cf.stream != "undefined"){
    			params += '<Res Type="'+(cf.type||"")+'" Idx="'+(typeof cf.idx != "undefined" && !isNaN(cf.idx) ? cf.idx : "")+'" OptID="'+(cf.id||"")+'" Stream="'+cf.stream+'" ></Res>';
    		}else{
    			params += '<Res Type="'+(cf.type||"")+'" Idx="'+(typeof cf.idx != "undefined" && !isNaN(cf.idx) ? cf.idx : "")+'" OptID="'+(cf.id||"")+'" ></Res>';
    		}
    		
    	}
    	var rv = P_LY.Config.GetComplexEx(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				dstRes:params
				//configID:configId
			}
		);
    	return rv.response;
    },
    
    set_res_configs:function (connectId,puid,type,idx,configSets){
    
    	var params = "";//<Param Value='"+values[i].val+"' />
    	
    	for(var i = 0;i < configSets.length;i++){
    		var c = configSets[i];
    		params += '<Res Type="'+(type||"")+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="'+(c.id||"")+'" Stream="'+(typeof c.stream != "undefined" ? c.stream :"" )+'"  >';
    		params += '<Param';
    		for(var j = 0; j < c.attrs.length;j++){
    			var at = c.attrs[j];
    			params += ' '+at.name+'="'+at.val+'" ';
    		}
    		params += '>';
    		if(c.params){
        		for(var k = 0;k < c.params.length;k++){
        			var p = c.params[k];
        			params += '<'+p.name+'';
        			for(var l = 0;l < p.attrs.length;l++){
        				var at = p.attrs[l]; 
        				params += ' '+at.name+'="'+at.val+'" ';
        			}
        			params += '>';
        			if(typeof p.childXML != "undefined"){
        				params += p.childXML;
        			}
        			params += '</'+p.name+'>';
        		}
    		}
    		params += '</Param>';
    		params += '</Res>';
    	}
    		console.log(params)
    	//return;
    	var rv = P_LY.Config.SetComplexEx(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:type,
				resIdx:idx || 0,
				dstRes:params
			}
		);
		//alert(rv.response)
    	return rv.response;
    },
    
    set_res_configsex:function (connectId,puid,type,idx,configSets){
  
    	var params = "";//<Param Value='"+values[i].val+"' />
    	
    	for(var i = 0;i < configSets.length;i++){
    		var c = configSets[i];
    		params += '<Res Type="'+(c.type||"")+'" Idx="'+(typeof c.idx != "undefined" && !isNaN(c.idx) ? c.idx : "")+'" OptID="'+(c.id||"")+'" Stream="'+(typeof c.stream != "undefined" ? c.stream :"" )+'" >';
    		params += '<Param';
    		for(var j = 0; j < c.attrs.length;j++){
    			var at = c.attrs[j];
    			params += ' '+at.name+'="'+at.val+'" ';
    		}
    		params += '>';
    		if(c.params){
        		for(var k = 0;k < c.params.length;k++){
        			var p = c.params[k];
        			params += '<'+p.name+'';
        			for(var l = 0;l < p.attrs.length;l++){
        				var at = p.attrs[l]; 
        				params += ' '+at.name+'="'+at.val+'" ';
        			}
        			params += '>';
        			if(typeof p.childXML != "undefined"){
        				params += p.childXML;
        			}
        			params += '</'+p.name+'>';
        		}
    		}
    		params += '</Param>';
    		params += '</Res>';
    	}
    
    	var rv = P_LY.Config.SetComplexEx(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:type,// no use
				resIdx:idx,// no use
				dstRes:params
			}
		);
    	return rv.response;
    },
    
    set_res_description:function(connectId,puid,type,idx,description){
    	var rv = P_LY.Config.SetResourceDescription(connectId,puid,type,idx,description);
    	return rv;
    },
    set_res_name:function(connectId,puid,type,idx,name){
    	var rv = P_LY.Config.SetResourceName(connectId,puid,type,idx,name);
    	return rv;
    },
    set_res_enable:function(connectId,puid,type,idx,enable){
    	var rv = P_LY.Config.SetResourceEnable(connectId,puid,type,idx,enable);
    	return rv;
    },
    
    set_control:function(connectId,puid,type,idx,controlId,paramAttrs){
    	if(typeof paramAttrs == "undefined" || paramAttrs == null) paramAttrs = '';
    	var cs =	{
				puid:puid,
				language:_cf.language,
				resType:type,
				resIdx:idx,
				controlID:controlId,
				param:'<Param '+paramAttrs+' />'
			} 
    
    	var rv = P_LY.Control.CommonSet(
    		connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:type,
				resIdx:idx,
				controlID:controlId,
				param:'<Param '+paramAttrs+' />'
			}
		);
    	return rv.response;
    },

    get_control:function(connectId,puid,type,idx,controlId,paramAttrs,xmlOSets){

    	if(typeof paramAttrs == "undefined" || paramAttrs == null) paramAttrs = '';
    	if(typeof xmlOSets == "undefined" || xmlOSets == null) xmlOSets = '';
    	var rv = P_LY.Control.CommonGet(
			connectId,
			{
				puid:puid,
				language:_cf.language,
				resType:type,
				resIdx:idx,
				controlID:controlId,
				xmlObjSets:xmlOSets,
				param:'<Param '+paramAttrs+' />'
			}
		);
    	return rv.response;
    },
    	
    query_gnns_offset:function(lng,lat){
    	var rv =  P_LY.Plug.nc.GetGNSSOffset(lng,lat);

		rv = eval("(" + rv + ")") || {};
		if(rv.rv == 0){
			return {lng:parseFloat(rv.response.longitude),lat:parseFloat(rv.response.latitude)};
		}else{
			return {lng:0,lat:0};
		}
    },

    PlatformStatusMap:function(status){
    	switch(status){
    	case "OK":
    		return _lp.des.status.success;
    		break;
    	case "Free":
    		return _lp.des.status.free;
    		break;
    	case "Registering":
    		return _lp.des.status.registering;
    		break;
    	case "Reporting":
    		return _lp.des.status.reporting;
    		break;
    	case "DNS Failed":
    		return _lp.des.status.dns_failed;
    		break;
    	case "Proxy Failed":
    		return _lp.des.status.proxy_failed;
    		break;
    	case "Route Failed":
    		return _lp.des.status.route_failed;
    		break;
    	case "Unreachable":
    		return _lp.des.status.unreachable;
    		break;
    	case "No Service":
    		return _lp.des.status.no_service;
    		break;
    	case "Invalid PUID":
    		return _lp.des.status.invalid_puid;
    		break;
    	case "Invalid Password":
    		return _lp.des.status.invalid_password;
    		break;
    	case "Forbidden PUID":
    		return _lp.des.status.forbidden_puid;
    		break;
    	case "Registered PUID":
    		return _lp.des.status.registered_puid;
    		break;
    	case "Registered DevID":
    		return _lp.des.status.registered_decid;
    		break;
    	case "PUID Full":
    		return _lp.des.status.puis_full;
    		break;
    	case "Unkown":
    		return _lp.des.status.unkown;
    		break;
    	}
    	return status;
    },	    
    //
	set_wnd_conatiner: function (wndKey, type) 
	{
		P_LY.WindowContainers.set(wndKey, new P_LY.Struct.WindowContainerStruct(wndKey, type));
    },
    remove_wnd_conatiner:function(wndKey){
		if(P_LY.WindowContainers.get(wndKey))
		{
			var window = P_LY.WindowContainers.get(wndKey).window;
			if(window != null){
				if (window.status.playvideoing){
					_p.stop_video(wndKey);
				}
			}
    		P_LY.WindowContainers.unset(wndKey);
    	}
    },
	
    //
	get_wnd_container: function (wndKey, type) 
	{
		if(P_LY.WindowContainers.get(wndKey))
		{
			return P_LY.WindowContainers.get(wndKey);
		}
		else
		{
			return null;
		}
    },
	
    //SetWindowSize 
	set_wnd_size:function(wndId, width, height)
	{
		if (P_LY.WindowContainers.get(wndId) && P_LY.WindowContainers.get(wndId).window != null) 
		{
            P_LY.WindowContainers.get(wndId).window.wnd.style.width = width+ "px";
            P_LY.WindowContainers.get(wndId).window.wnd.style.height = height + "px";
        }
	},
	
	//CreateWindow
	create_window: function(connectId, wndId, type,wndEvent)
	{
		try
		{
			if(type == null || type == undefined) type = P_LY.Enum.WindowType.VIDEO;
			
			if(connectId && P_LY.Connections.get(connectId))
			{
				var operator = P_LY.CreateWindow(connectId, wndId, type, wndEvent);
				
				if(operator.rv != P_Error.SUCCESS)
				{
					return false;
				}
				return operator.response;
			}
			else
			{
				if(_cf.debug) console.log("connectId error");
				return false;
			}
		}
		catch(e)
		{
			if(_cf.debug) console.log(e)
		}
	},

	//GetCoreInfosByConnectIdPuid
	query_coreinfo_bypuid: function(connectId, puid)
	{
        try{
            if(!connectId || !_p.resource.get(connectId)) return false;
            
            var puInfoArray = new Array();
            var domainRoadName = "";
            var found = false;
            
            var findPUArr = function(_puArr, _puid){
                var flag = false;
                if(_puArr && _p.isArray(_puArr)){
                    for(var i = 0; i < _puArr.length; i++){
                        var pu = _puArr[i];
                        if(pu.puid == _puid){
                            flag = true;
                            break;
                        } 
                    } 
                    puInfoArray = _puArr;
                } 
                return flag;
            }
            
            var puArr = _p.resource.get(connectId).puInfoArray;
            found = findPUArr(puArr, puid);
            if(found == false){
                var cascadeResource = _wc.resource.get(connectId).cascadeResource;
                if(cascadeResource){
                    cascadeResource.each(function(item){
                        if(found == true) return;
                        var dr = item.value; 
                        found = findPUArr(dr.puInfoArray, puid);  
                        if(found == true) 
                            domainRoadName = dr.domainRoadName;
                    });
                } 
            }
            
            return {
                puInfoArray: puInfoArray,
                domainRoadName: domainRoadName
            }
        }
        catch(e){
            if(_cf.debug) console.log(e);
            return false;
        }
    },
    
	play_video : function(connectId, puid, idx, wndId, wndEventParams,streamType)
	{
		try
		{
			if (typeof puid == "undefined" || typeof idx == "undefined") 
			{
				if(_cf.debug) console.log("puid or idx is undefined");
				return false;
			}
			if(!wndId && $('#'+wndId).length <=0)
			{
				if(_cf.debug) console.log("wndId is undefined");
				return false;
			}
			var node = P_LY.WindowContainers.get(wndId);
			//console.log(arguments,node)
			if (wndEventParams == "undefined" || wndEventParams == null) {
				wndEventParams = 
				{
					lbtn_click : false,
					select_rect : false,
					ptz_control : false,
					fsw_show : false,
					fsw_hide : false,
					menu_command : false
				}
			}
			else {
				wndEventParams = 
				{
					lbtn_click: (typeof wndEventParams.lbtn_click != "undefined" && wndEventParams.lbtn_click == true ? true : false),
					select_rect: (typeof wndEventParams.select_rect != "undefined" && wndEventParams.select_rect == true ? true : false),
					ptz_control: (typeof wndEventParams.ptz_control != "undefined" && wndEventParams.ptz_control == true ? true : false),
					fsw_show: (typeof wndEventParams.fsw_show != "undefined" && wndEventParams.fsw_show == true ? true : false),
					fsw_hide: (typeof wndEventParams.fsw_hide != "undefined" && wndEventParams.fsw_hide == true ? true : false),
					//menu_command: (typeof wndEventParams.menu_command != "undefined" && wndEventParams.menu_command == true ? true : false),
					menu_command: (typeof wndEventParams.menu_command != "undefined" ? wndEventParams.menu_command : false),
					eventCallback: (typeof wndEventParams.eventCallback == "function" ? wndEventParams.eventCallback : null)
				}
			}
			
			var play = true;
//			P_LY.WindowContainers.each
//			(
//				function (item) 
//				{
//					var temp = item.value;
//					if (temp.window && temp.window.status.playvideoing) 
//					{
//						var v_puid = temp.window.params.puid || "";
//						var v_idx = temp.window.params.idx || "";
//						if (v_puid == puid && v_idx == idx) 
//						{
//							play = false
//						}
//					}
//				}
//			);
			if (play) 
			{
				//if (node.active) 
				{
					var create = true;
					if (node.window != null) 
					{
						create = false;
						if (node.window.status.playvideoing) 
						{
							_p.stop_video(wndId);
						}

						//node.window = null;
						//create = true;
					}
					//if(_cf.debug) console.log(create);
					if (create) 
					{
						var windowEvent = new P_LY.Struct.WindowEventStruct();
						windowEvent.lbtn_click.status = wndEventParams.lbtn_click;
						windowEvent.select_rect.status = wndEventParams.select_rect;
						windowEvent.ptz_control.status = wndEventParams.ptz_control;
						windowEvent.fsw_show.status = wndEventParams.fsw_show;
						windowEvent.fsw_hide.status = wndEventParams.fsw_hide;
			
						windowEvent.menu_command.status = wndEventParams.menu_command.status;
						
//						windowEvent.lbtn_click.callback = function () { wndEventParams.eventCallback('lbtn_click', wndId); };
//						windowEvent.select_rect.callback = function () { wndEventParams.eventCallback('select_rect', wndId); };
//						windowEvent.fsw_show.callback = function () { wndEventParams.eventCallback('fsw_show', wndId); };
//						windowEvent.fsw_hide.callback = function () { wndEventParams.eventCallback('fsw_hide', wndId); };

						windowEvent.lbtn_click.callback = function () {
							var args = ['lbtn_click', wndId];
							//args = args.concat(arguments);
							Array.prototype.push.apply(args, arguments);
							
							wndEventParams.eventCallback.apply(wndEventParams.eventCallback, args);
						};
						windowEvent.select_rect.callback = function () {
							var args = ['select_rect', wndId];
							//args = args.concat(arguments);
							Array.prototype.push.apply(args, arguments);
							
							wndEventParams.eventCallback.apply(wndEventParams.eventCallback, args);
						};
						windowEvent.fsw_show.callback = function () { wndEventParams.eventCallback('fsw_show', wndId); };
						windowEvent.fsw_hide.callback = function () { wndEventParams.eventCallback('fsw_hide', wndId); };
						

						windowEvent.menu_command.menu = wndEventParams.menu_command.menu;
						windowEvent.menu_command.callback = wndEventParams.menu_command.callback;


						var window = _p.create_window(connectId, wndId,P_LY.Enum.WindowType.VIDEO,windowEvent);
						node.window = window;

						node.window.customParams.FullScreenValue = "fullscreen";
						node.window.customParams.upaudio = "start";
						node.window.customParams.talk = "start";
						node.window.customParams.gpsstatus = "start";;
						
						for(var j = 0; j < _p.puInfoArray.length;j++){
							var pu = _p.puInfoArray[j];
							if(pu.puid == puid){

								node.window.customParams.cameraName = (pu.childResource[idx] ? pu.childResource[idx].name : puid);
								break;
							}
						}
						
						P_LY.WindowContainers.get(wndId).window = node.window = window;
					}
					var operator = P_LY.PlayVideo(connectId, node.window, puid, idx,streamType);
					
					P_LY.ResizeWindowDimension(node.window,"100%","100%");
					var pu = _p.query_puresource(connectId, puid);
					for (var i = 0; i < pu.childResource.length; i++) {
						var puResourcesInfos = pu.childResource[i];
						if (puResourcesInfos.idx == idx && puResourcesInfos.type == P_LY.Enum.PuResourceType.VideoIn) {
							node.description = puResourcesInfos;
						}
					}
					if(operator.rv != P_Error.SUCCESS)
					{	
						P_LY.ResizeWindowDimension(node.window,0,0);
						if(_cf.debug) console.log("rv =",operator);
						return false;
					}
					return true;
				}
			}
			else
			{
				if(_cf.debug) console.log("该资源已在播放！请不要重复播放");
                return false;
			}
		}
		catch(e)
		{
			if(_cf.debug) console.log(e);
		}
	},
	
	//
	stop_video : function(wndId)
	{
		try
		{
			if(wndId && P_LY.WindowContainers.get(wndId) )
			{
				var node = P_LY.WindowContainers.get(wndId);
				if (node && node.window && node.window.status.playvideoing) {
					var rv = P_LY.StopVideo(node.window);
					//alert("stop video:"+rv.rv+","+rv.response);
					P_LY.ResizeWindowDimension(node.window,0,0);
					return true;
				}
				else {
					if(_cf.debug) console.log(wndId+",objWnd no exist");
					return false;
				}
			}
		}
		catch(e)
		{
			if(_cf.debug) console.log(e);
		}
	},
	//获取视频当前的状态
//	get_video_status: function (fnCallback) 
//	{
//        fnCallback = typeof fnCallback == "function" ? fnCallback : null;
//        var rv = P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.stream_status_notify, fnCallback);
//    },
	
    // 播放音频
	play_audio: function (wndId) 
	{
        if (wndId) {
            if (P_LY.WindowContainers.get(wndId)) {
                var playaudioed = false;
                P_LY.WindowContainers.each
				(
					function (item) {
					    if (item.value.window != null) {
					        if (item.value.window.status.playvideoing && item.value.window.status.playaudioing) {
					            if (item.key == wndId)	// 当前选择要播放的音频,已经在播放了
					            {
					                playaudioed = true;
					                //var rv = P_LY.PlayAudio(item.value.window); // 关闭音频 
					            }
					            else {
					                var rv = P_LY.PlayAudio(item.value.window); // 关闭非当前需要的其它所有播放音频 
					            }
					        }
					    }
					}
				);
                var node = P_LY.WindowContainers.get(wndId);
                if (node.window && node.window.status.playvideoing && node.window.status.talking) {
                    if (!confirm("播放声音前需要停止双向对讲，确定要停止双向对讲并开始播放声音吗？")) {
                        return false;
                    }
                    if (rv != true) {
                        return false;
                    }
                }
                var rv = P_LY.PlayAudio(node.window);
                
				if(node.window.status.playaudioing){					
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playaudio",text:"停止音频"}]);
				}else{
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playaudio",text:"播放音频"}]);
				}
            }
        }
    },
    play_upaudio:function(wndId){

		var node = P_LY.WindowContainers.get(wndId);
		
		if (node){
			if(node.window.customParams.upaudio == "start"){
			//var playupaudioed = false;
				P_LY.WindowContainers.each(
					function(item){
						if(item.value.window != null){
							var connectId =  item.value.window.connectId;
							if(item.value.window.status.playvideoing && item.value.window.status.talking){
								var rv = P_LY.CallTalkControl.Stop(connectId,item.value.window.params.puid,_p.resOAIndex);
								if(rv.rv == P_Error.SUCCESS){
									item.value.window.status.talking = false;
									item.value.window.customParams.talk = "start";
									P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playtalk",text:"开始对讲"}]);
								}
							}else if(item.value.window.status.playvideoing && item.value.window.status.calling){	
								if (item.key != wndId){	
									// 关闭非当前喊话
									var rv = P_LY.CallTalkControl.Stop(connectId,item.value.window.params.puid,_p.resOAIndex);
									if(rv.rv == P_Error.SUCCESS){
										item.value.window.status.calling = false;
										item.value.window.customParams.upaudio = "start";
									}
									if(item.value.window.status.calling){
										P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playupaudio",text:"停止喊话"}]);
									}else{
										P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playupaudio",text:"开始喊话"}]);
									}
								}
							}
						}
					});
				var puid = node.window.params.puid;
				connectId =  node.window.connectId;
				
				var rv_arr = _p.check_talkenable(connectId,puid) || [-1, -1];
				switch (rv_arr[0]) {
					case -1:
						if(_cf.debug) console.log("设备不支持喊话！");
						return false;
						break;
					case 0:
						if(_cf.debug) console.log("资源已被占用，无法开启喊话！");
						return false;
						break;
					default: // =>1
						break;
				}
				var resOAIdx = rv_arr[1];
				//var connectId = _p.connectId;
				var rv = P_LY.CallTalkControl.StartCall(connectId,puid,resOAIdx); // 开始喊话
				
				if(rv.rv == P_Error.SUCCESS){
					node.window.status.calling = true;
					node.window.customParams.upaudio = "end";
				}
				if(node.window.status.calling){
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playupaudio",text:"停止喊话"}]);
				}else{
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playupaudio",text:"开始喊话"}]);
				}
			}else{
				var connectId =  node.window.connectId;
				var rv = P_LY.CallTalkControl.Stop(connectId,node.window.params.puid,_p.resOAIndex);
				if(rv.rv == P_Error.SUCCESS){
					node.window.status.calling = false;
					node.window.customParams.upaudio = "start";
				}
				if(node.window.status.calling){
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playupaudio",text:"停止喊话"}]);
				}else{
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playupaudio",text:"开始喊话"}]);
				}
			}	
		}
    },
    start_call:function(connectId,puid,oaIdx){
		var rv = P_LY.CallTalkControl.StartCall(connectId,puid,oaIdx); // 开始喊话
		//var rv = P_LY.CallTalkControl.Stop(connectId,node.window.params.puid,_p.resOAIndex);
		// 因为跟视频窗口没有绑定，所以，要查找一下，如果有视频窗口正在播放此路视频要更新一下窗口里的各状态
		P_LY.WindowContainers.each(function(item){
			if(item.value.window != null){
				var connectId =  item.value.window.connectId;
				if(item.value.window.status.playvideoing ){
					if(item.value.window.params.puid == puid &&item.value.window.params.idx == oaIdx){
						
						item.value.window.status.calling = true;
						item.value.window.customParams.upaudio = "end";
						P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playupaudio",text:"停止喊话"}]);
						return;
					}
				}
			}
		});
		return rv;
    },
    stop_call:function(connectId,puid,oaIdx){
		var rv = P_LY.CallTalkControl.Stop(connectId,puid,oaIdx); // 停止喊话
		P_LY.WindowContainers.each(function(item){
			if(item.value.window != null){
				var connectId =  item.value.window.connectId;
				if(item.value.window.status.playvideoing ){
					if(item.value.window.params.puid == puid &&item.value.window.params.idx == oaIdx){
						
						item.value.window.status.talking = false;
						item.value.window.customParams.talk = "start";
						P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playtalk",text:"开始对讲"}]);
						
						item.value.window.status.calling = false;
						item.value.window.customParams.upaudio = "start";
						P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playupaudio",text:"开始喊话"}]);
						return;
					}
				}
			}
		});
		
		return rv;
    },
    start_talk:function(connectId,puid,oaIdx){
		var rv = P_LY.CallTalkControl.StartTalk(connectId,puid,oaIdx); // 开始喊话
		//var rv = P_LY.CallTalkControl.Stop(connectId,node.window.params.puid,_p.resOAIndex);
		
		// 因为跟视频窗口没有绑定，所以，要查找一下，如果有视频窗口正在播放此路视频要更新一下窗口里的各状态
		P_LY.WindowContainers.each(function(item){
			if(item.value.window != null){
				var connectId =  item.value.window.connectId;
				if(item.value.window.status.playvideoing ){
					if(item.value.window.params.puid == puid &&item.value.window.params.idx == oaIdx){
			
						item.value.window.status.talking = true;
						item.value.window.customParams.talk = "end";
						P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playtalk",text:"停止对讲"}]);						
						return;
					}
				}
			}
		});
		
		return rv;
    },
    stop_talk:function(connectId,puid,oaIdx){
		var rv = P_LY.CallTalkControl.Stop(connectId,puid,oaIdx); // 停止喊话
		
		// 因为跟视频窗口没有绑定，所以，要查找一下，如果有视频窗口正在播放此路视频要更新一下窗口里的各状态
		P_LY.WindowContainers.each(function(item){
			if(item.value.window != null){
				var connectId =  item.value.window.connectId;
				if(item.value.window.status.playvideoing ){
					if(item.value.window.params.puid == puid &&item.value.window.params.idx == oaIdx){
			
						item.value.window.status.talking = false;
						item.value.window.customParams.talk = "start";
						P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playtalk",text:"开始对讲"}]);
						
						
						item.value.window.status.calling = false;
						item.value.window.customParams.upaudio = "start";
						P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playupaudio",text:"开始喊话"}]);
						
						return;
					}
				}
			}
		});
		
		return rv;
    },
    check_talkenable:function(connectId,puid) {
		var rv = P_LY.ForkResource(connectId,P_LY.Enum.ForkResourceLevel.nppForkPUResourceInfo,0,200,null,{PUID:puid});
		
		var re = rv.response;
		
		for (var i = 0; i < re.length; i++) {
			var childRes = re[i];
			if(childRes.type == P_LY.Enum.PuResourceType.AudioOut){
				try{
					_p.ResOAChannels.set(puid + "_" + childRes.idx, { puid: puid, index: childRes.idx, windowKey: "", flag: false });	
				}catch(e){
					if(_cf.debug) console.log(e.message);
				}
			}
		}
		var found = false, activeResOAIdx = 0, flag = -1;
		_p.ResOAChannels.each
			(
				function (ee) {
					var e = ee.value;
					
					if (e.puid == puid) {
						flag = 0; // 存在OA资源
						if (found == false && e.flag != true) {
							activeResOAIdx = e.index;
							found = true;
						}
					}
				}
			);
		if (found == true){flag = 1}
		_p.resOAIndex = activeResOAIdx;
		return [flag, activeResOAIdx];
    },
	//对讲
	play_talk:function(wndId){
		var node = P_LY.WindowContainers.get(wndId);
		if(node){
			if(node.window.customParams.talk == "start"){
				P_LY.WindowContainers.each(
					function(item){
						if(item.value.window != null){
							var connectId =  item.value.window.connectId;
							if(item.value.window.status.playvideoing && item.value.window.status.calling){
								//关闭当前的喊话
								var rv = P_LY.CallTalkControl.Stop(connectId,item.value.window.params.puid,_p.resOAIndex);
								
								if(rv.rv == P_Error.SUCCESS){
									item.value.window.status.calling = false;
									item.value.window.customParams.upaudio = "start";
									P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playupaudio",text:"关闭喊话"}]);
								}
							}else if (item.key != wndId){
								if(item.value.window.status.playvideoing && item.value.window.status.talking){
									//关闭非当前的对讲
									var rv = P_LY.CallTalkControl.Stop(connectId,item.value.window.params.puid,_p.resOAIndex);
									if(rv.rv == P_Error.SUCCESS){
										item.value.window.status.talking = false;
										item.value.window.customParams.talk = "start";
										P_LY.WindowAttachEvent.UpdateMenuCommand(item.value.window, [{key:"playtalk",text:"开始对讲"}]);
									}
								}
							}
							return;
						}
					});
				var puid = node.window.params.puid;

				try{
				var connectId =  node.window.connectId;
					
				var rv_arr = _p.check_talkenable(connectId,puid) || [-1, -1];
				}catch(e){
				}
				switch (rv_arr[0]) {
					case -1:
						if(_cf.debug) console.log("设备不支持喊话！");
						return false;
						break;
					case 0:
						if(_cf.debug) console.log("资源已被占用，无法开启喊话！");
						return false;
						break;
					default: // =>1
						break;
				}
				var resOAIdx = rv_arr[1];
				var rv = P_LY.CallTalkControl.StartTalk(connectId,puid,resOAIdx); // 开始对讲
				if(rv.rv == P_Error.SUCCESS){
					node.window.status.talking = true;
					node.window.customParams.talk = "end";
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playtalk",text:"停止对讲"}]);
				}
			}else{
				var connectId =  node.window.connectId;
				var rv = P_LY.CallTalkControl.Stop(connectId,node.window.params.puid,_p.resOAIndex);
				if(rv.rv == P_Error.SUCCESS){
					node.window.status.talking = false;
					node.window.customParams.talk = "start";
					P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playtalk",text:"开始对讲"}]);
				}
			}	
		}
	},
    snapshot:function(wndId){
		var path = "C:/TSLFile/Snapshot/";
		if (P_LY.WindowContainers.get(wndId)){
			var node = P_LY.WindowContainers.get(wndId);
			if(typeof node.window != "object"){
			return false;
			}
			if(node.window.containerId == wndId){
			if(node.window.status.playvideoing){
				var name = node.window.customParams.cameraName+"_"+P_Utils.DateFormat("yyyy#MM#dd#HH#mm#ss#_#l").replace(/#/g, "")+".bmp";				
				var paStop = P_LY.Snapshot(node.window,path,name);
				
				}
			}
		}
    },

    record:function(wndId){
		if (P_LY.WindowContainers.get(wndId)){
			var node = P_LY.WindowContainers.get(wndId);
			if(typeof node.window != "object"){
			return false;
			}
			if(node.window.containerId == wndId){
				var name = node.window.customParams.cameraName+"_"+P_Utils.DateFormat("yyyy#MM#dd#HH#mm#ss").replace(/#/g, "")+".avi";
				if(node.window.status.playvideoing){
					var rv = P_LY.LocalRecord(node.window,"C:/TSLFile/Record/",name);
					if(node.window.status.recording){
						P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"record",text:"停止录像"}]);
					}
					else{
						P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"record",text:"本地录像"}]);
					}
				}
			}
		}
    },
    full_screen:function(wndId){
		if (P_LY.WindowContainers.get(wndId)){
			var node = P_LY.WindowContainers.get(wndId);
			if(typeof node.window != "object"){
				return false;
			}
			
			if(node.window.containerId == wndId){
				if(node.window.status.playvideoing){
					if(node.window.customParams.FullScreenValue == "fullscreen"){
						var paStop = P_LY.FullScreen(node.window);
						if(paStop.rv == 0){
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"fullscreen",text:"退出全屏"}]);
							node.window.customParams.FullScreenValue = "exitfullscreen";
						}
						else{
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"fullscreen",text:"退出全屏"}]);
						}
					}
					else{
						var paStop = P_LY.ExitFullScreen(node.window);
						if(paStop.rv == 0){
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"fullscreen",text:"全屏"}]);
							node.window.customParams.FullScreenValue = "fullscreen";
						}else{
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"fullscreen",text:"退出全屏"}]);
						}
					}
				}
			}
		}
    },
    update_wnd_menu:function(wndId){
    	return;
    	try{	
			if (P_LY.WindowContainers.get(wndId)){
				var node = P_LY.WindowContainers.get(wndId);
				
				if(node.window != null && typeof node.window != "object"){
					return false;
				}
	
				if(node.window.containerId == wndId){
					if(node.window.status.playvideoing){
						if(node.window.status.playaudioing){
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playaudio",text:"停止音频"}]);
						}else{
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playaudio",text:"播放音频"}]);
						}
						if(node.window.status.calling){
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playupaudio",text:"停止喊话"}]);
						}else{
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playupaudio",text:"开始喊话"}]);
						}
						if(node.window.status.talking){
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playtalk",text:"停止对讲"}]);
						}else{
							P_LY.WindowAttachEvent.UpdateMenuCommand(node.window, [{key:"playtalk",text:"开始对讲"}]);
						}
					}
				}
			}
	    	return;
    	}catch(e){
    	alert(e.message)	
    	}
    },
    control:function(connectId,puid,idx,control,attrs){
    
    	var ptzControl = "";
    	switch(control){

    	case "turnleft":
    		ptzControl = P_LY.Enum.PTZDirection.turnleft;
    		break;
    	case "turnright":
    		ptzControl = P_LY.Enum.PTZDirection.turnright;
    		break;
    	case "turnup":
    		ptzControl = P_LY.Enum.PTZDirection.turnup;
    		break;
    	case "turndown":
    		ptzControl = P_LY.Enum.PTZDirection.turndown;
    		break;
    	case "stopturn":
    		ptzControl = P_LY.Enum.PTZDirection.stopturn;
    		break;
    		
    	case "aperturea":
    		ptzControl = P_LY.Enum.PTZDirection.aperturea;
    		break;
    	case "aperturem":
    		ptzControl = P_LY.Enum.PTZDirection.aperturem;
    		break;
    	case "stopaperture":
    		ptzControl = P_LY.Enum.PTZDirection.stopaperture;
    		break;    		
    		
    	case "focusfar":
    		ptzControl = P_LY.Enum.PTZDirection.focusfar;
    		break;
    	case "focusnear":
    		ptzControl = P_LY.Enum.PTZDirection.focusnear;
    		break;
    	case "stopfocus":
    		ptzControl = P_LY.Enum.PTZDirection.stopfocus;
    		break;
    		
    		
    	case "zoomin":
    		ptzControl = P_LY.Enum.PTZDirection.zoomin;
    		break;
    	case "zoomout":
    		ptzControl = P_LY.Enum.PTZDirection.zoomout;
    		break;
    	case "stopzoom":
    		ptzControl = P_LY.Enum.PTZDirection.stopzoom;
    		break;

    	case "startsecondarydev":
    		ptzControl = P_LY.Enum.PTZDirection.startsecondarydev;
    		break;
    	case "stopsecondarydev":
    		ptzControl = P_LY.Enum.PTZDirection.stopsecondarydev;
    		break;

    	case "movetopresetpos":
    		ptzControl = P_LY.Enum.PTZDirection.movetopresetpos;
    		break;
    	case "setpresetpos":
    		ptzControl = P_LY.Enum.PTZDirection.setpresetpos;
    		break;

    	case "movetopresetpos":
    		ptzControl = P_LY.Enum.PTZDirection.movetopresetpos;
    		break;
    	case "setpresetpos":
    		ptzControl = P_LY.Enum.PTZDirection.setpresetpos;
    		break;

    	case "runtour":
    		ptzControl = P_LY.Enum.PTZDirection.runtour;
    		break;
    	case "stoptour":
    		ptzControl = P_LY.Enum.PTZDirection.stoptour;
    		break;
    		
    	case "startautoscan":
    		ptzControl = P_LY.Enum.PTZDirection.startautoscan;
    		break;
    	case "stopautoscan":
    		ptzControl = P_LY.Enum.PTZDirection.stopautoscan;
    		break;
    	case "setbeginpos":
    		ptzControl = P_LY.Enum.PTZDirection.setbeginpos;
    		break;
    	case "setendpos":
    		ptzControl = P_LY.Enum.PTZDirection.setendpos;
    		break;
    		
    	case "ptzspeed":			
    	    _p.set_res_configs(connectId,puid,"PTZ",idx,[{id:'F_PTZ_Speed',attrs:[{name:'Value',val:attrs[0].val}] }]);
    		return;
    		break;
    		default:
    			break;
    	}
    	var attrXML = '';
    	
    	for(var i = 0;i < attrs.length;i++){
    		var a = attrs[i];
    		attrXML += ' '+a.name+'="'+a.val+'"';
    	}
    	//P_LY.PTZ.Control(_pl.connectId, puid, idx, ptzControl);
    //	console.log(connectId,puid,"PTZ",idx,"C_"+ptzControl,attrXML);
    	
		var rv = _p.set_control(connectId,puid,"PTZ",idx,"C_"+ptzControl,attrXML);
		//console.log(rv)
    },
    download_record_file:function(connectId,type,szId,puid,remoteFile,fileName,idx){
		//var name = P_Utils.DateFormat("yyyy#MM#dd#HH#mm#ss").replace(/#/g, "")+".avi";
    	
    	for(var i = 0;i < _p.puInfoArray.length;i++){
			var p = _p.puInfoArray[i];
    		if(p.puid == puid){

    	    	var saveFilePath = "C:/TSLFile/Download/"+p.name+"_"+fileName.split(".")[0]+".avi";
    			if(typeof idx != "undefined"){
    				for(var j = 0;j < p.childResource.length;j++){
    					var r = p.childResource[j];
    					if(r.type == "IV" && r.idx == idx){
    						
    		    	    	saveFilePath = "C:/TSLFile/Download/"+r.name+"_"+fileName.split(".")[0]+".avi";
    		    	    	break;
    					}
    				}
    			}

    			if(type == "local"){ 
    				return P_LY.Download.StartCEFSVodFileDownload(connectId,puid,0,remoteFile,saveFilePath,0);
    			}else{
    				return P_LY.Download.StartCloudFileDownload(connectId,szId,remoteFile,saveFilePath,0);
    			
    			}
    				
    			break;
    		}
    	}
    	
    	//P_LY.Download.StartDownloadRecordFile(connectId, puid, sgIndex,remoteFile, localSaveAsFile);
    },
    	
    download_snapshot_file:function(connectId,type,szId,puid,snapshotTime,remoteFile,fileName,idx){
		//var name = P_Utils.DateFormat("yyyy#MM#dd#HH#mm#ss").replace(/#/g, "")+".avi";
    	
    	for(var i = 0;i < _p.puInfoArray.length;i++){
			var p = _p.puInfoArray[i];
    		if(p.puid == puid){

    	    	var saveFilePath = "C:/TSLFile/Download/"+p.name+"_"+fileName.split(".")[0]+".jpg";
    			if(typeof idx != "undefined"){
    				for(var j = 0;j < p.childResource.length;j++){
    					var r = p.childResource[j];
    					if(r.type == "IV" && r.idx == idx){
    						
    		    	    	saveFilePath = "C:/TSLFile/Download/"+r.name+"_"+fileName.split(".")[0]+".jpg";
    		    	    	break;
    					}
    				}
    			}

    			if(type == "local"){
    				return P_LY.Download.StartCEFSImageFileDownload(connectId,puid,0,0,snapshotTime,remoteFile,saveFilePath);
    			}else{
    				return P_LY.Download.StartCloudFileDownload(connectId,szId,remoteFile,saveFilePath);
    			}
    				
    			break;
    		}
    	}
    	
    	//P_LY.Download.StartDownloadRecordFile(connectId, puid, sgIndex,remoteFile, localSaveAsFile);
    },	
    stop_download_record_file:function(connectId,type,downloadHandler){
    	if(type == "local"){
        	return P_LY.Download.StopCEFSFileDownload(connectId,downloadHandler);	
    	}else{
        	return P_LY.Download.StopCloudFileDownload(connectId,downloadHandler);
    		
    	}
    },
    play_vod:function(connectId,wndId,options){

		if($("#"+wndId)[0]){
			var windowAttachEvent = new P_LY.Struct.WindowEventStruct();
			windowAttachEvent.lbtn_click.status = true;
			windowAttachEvent.lbtn_click.callback = function(){
				if(_cf.debug) console.log("鼠标左键单击响应回调函数")
				//$("#replayerwindowtitle0")[0].innerHTML = "鼠标左键单击响应回调函数,";
			}
			windowAttachEvent.select_rect.status = false;
			windowAttachEvent.select_rect.callback = function(){
				//$("#loginMsg")[0].innerHTML = "鼠标拉选响应回调函数,";
			}
			windowAttachEvent.ptz_control.status = true;
			windowAttachEvent.fsw_show.status = true;
			windowAttachEvent.fsw_show.callback = function(){
				if(_cf.debug) console.log("窗口全屏后的回调函数")
				//$("#replayerwindowtitle0")[0].innerHTML = "窗口全屏后的回调函数,";
			}
			windowAttachEvent.fsw_hide.status = true;
			windowAttachEvent.fsw_hide.callback = function(){
				if(_cf.debug) console.log("窗口全屏恢复后的回调函数")
				//$("#replayerwindowtitle0")[0].innerHTML = "窗口全屏恢复后的回调函数,";
			}
			windowAttachEvent.menu_command.status = false;
			windowAttachEvent.menu_command.menu = [
				{key:"stopvod",text:"停止"},
				{key:"-",text:"split"},
				{key:"playvodaudio",text:"静音"},
				
			]
			windowAttachEvent.menu_command.callback = function(key){
				switch(key){
					case "stopvod":
						//WebClient.StopVod();
						_v.fire_stop_vod(wndId);
						break;
					case "playvodaudio":
						_v.fire_stop_vod_audio(wndId);
						break;
				}
			}
			// - 创建窗口
			

			_p.set_wnd_conatiner(wndId,P_LY.Enum.WindowType.VOD);
			var window = _p.create_window(connectId, wndId, P_LY.Enum.WindowType.VOD,windowAttachEvent)
			
			//var window = P_LY.CreateWindow(connectId, wndId, "VOD", windowAttachEvent);
			//var wnd = window.response;
			
			P_LY.WindowContainers.get(wndId).window = window;
			
			//WebClient.wnd = wnd;
			// - 播放录像
			var PlayVodOptions = {type:(options.type == "local" ? P_LY.Enum.StorageFileType.CEFS :  P_LY.Enum.StorageFileType.Cloud),szId:options.szId,puid:options.puid,sgIndex:0,filePath:options.path+options.name,beginTime:options.beginTime,endTime:options.endTime,speed:options.speed,direction:options.direction,duration:options.duration,ivIndex:0}
			var operator = P_LY.PlayVod(connectId,window,PlayVodOptions);
			
					
			return operator;
		}
    },
    pause_vod:function(connectId,wndId){
    	if(!P_LY.WindowContainers.get(wndId) || !P_LY.WindowContainers.get(wndId).window){
    		return;
    	}
		var operator = P_LY.PauseVod(P_LY.WindowContainers.get(wndId).window);
    },
    resume_vod:function(connectId,wndId){
    	if(!P_LY.WindowContainers.get(wndId) || !P_LY.WindowContainers.get(wndId).window){
    		return;
    	}
		var operator = P_LY.ResumeVod(P_LY.WindowContainers.get(wndId).window);
    },
    stop_vod:function(connectId,wndId){
    	if(!P_LY.WindowContainers.get(wndId) || !P_LY.WindowContainers.get(wndId).window){
    		return false;
    	}
		var operator = P_LY.StopVod(P_LY.WindowContainers.get(wndId).window);
		return operator;
    },
    play_vod_audio:function(connectId,wndId){
    	if(!P_LY.WindowContainers.get(wndId) || !P_LY.WindowContainers.get(wndId).window){
    		return;
    	}
		var operator = P_LY.PlayVodAudio(P_LY.WindowContainers.get(wndId).window);
		//P_LY.WindowAttachEvent.UpdateMenuCommand(P_LY.WindowContainers.get(wndId).window, [{key:"playvodaudio",text:"静音"}]);
    },
    stop_vod_audio:function(connectId,wndId){
    	if(!P_LY.WindowContainers.get(wndId) || !P_LY.WindowContainers.get(wndId).window){
    		return;
    	}
		var operator = P_LY.PlayVodAudio(P_LY.WindowContainers.get(wndId).window);
		//P_LY.WindowAttachEvent.UpdateMenuCommand(P_LY.WindowContainers.get(wndId).window, [{key:"playvodaudio",text:"打开音频"}]);
    }
}