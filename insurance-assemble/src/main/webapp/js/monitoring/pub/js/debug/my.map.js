var _mp = mymap = {
	points:new Array(),
	markers:new Array(),
	historyLocus:new Array(),
	curHistoryLocus:null,
	
	historyMarkers:new Array(),
	contextMenu:null,
	curHistoryMarker:null,
	map :null,
	init:function(){
        try{

			if(_mp.map != null){
				if(typeof _mp.map.destroy == "function") _mp.map.destroy();
				_mp.map = null;
			}
			if(_mp.markers.length >0){
				_mp.markers = new Array();
			}
			//_mp.points = new Array();
			_mp.historyLocus = new Array();
			_mp.curHistoryLocus = null;			
			_mp.historyMarkers = new Array();
			_mp.contextMenu = null;
			_mp.curHistoryMarker = null;
        	
			$('#frame_tabs').tabs('select',_lp.frame.map.title);
			
			// 创建地图对象
			_mp.map = Map.create("mapcontainer",{center:null,zoom:10});
			// 启动gps流，开始接收gps数据
			_mp.run();		// 开始接收GPS;
			
			// 创建一个播放控制条
        	var c = Map.player_control(_mp.map.obj,"<table><tr><td>回放控制：<span id='playspan_speed'>原速</span><td><td><img id='playbtn_pause' src='themes/gray/bfzn_003.png'  style='display:none;' width=32 height=32  alt='暂停' title='暂停' onclick=_mp.player_onclick(\'pause\'); /><img  id='playbtn_play' src='themes/gray/bfzn_004.png' style='display:;'  width=32 height=32 alt='播放' title='播放'  onclick=_mp.player_onclick(\'play\'); />&nbsp;<img id='playbtn_stop' src='themes/gray/bfzn_006.png'  style='display:;' width=32 height=32  alt='停止' title='停止' onclick=_mp.player_onclick(\'stop\'); />&nbsp;<img id='playbtn_fast' src='themes/gray/bfzn_007.png'  width=32 height=32  alt='快速' title='快速' onclick=_mp.player_onclick(\'fast\'); /></td></tr></table>");
        	_mp.map.add_control(c);
        	$("#map_playerbar").hide();// 先隐藏控制条，到控制历史轨迹的时候再打开

        	// 创建地图右键菜单
			Map.event.add_listener(_mp.map.obj,"rightclick",function(e){				
	        	// 给地图加右键菜单
	        	var menus = new Array();
				if($("#map_playerbar").is(":hidden")){
					menus.push({text:'打开放控制条',cb:function(){
			        	$("#map_playerbar").show();
					}});
				}else{
					menus.push({text:'关闭回放控制条',cb:function(){
			        	$("#map_playerbar").hide();			        	
					}});
				}
				menus.push({text:'清除回放轨迹',cb:function(){
					_mp.clear_locus();
				}});
				
				if(_mp.contextMenu == null){
					_mp.contextMenu = new Map.contextmenu(_mp.map.obj,menus);	
				}else{
					_mp.contextMenu.reset(menus);
				}
	        	_mp.contextMenu.open(_mp.map.obj,e);
			});
			
        }catch(e){
        }
		return;
	},
	run:function(){
		_mp.points = new Array();
        var rv = P_LY.NCNotifyManager.Add(P_LY.Enum.NCObjectNotify.gps_data_notify, function(notify){
        	_mp.gps_notify(notify);
        });
        
		for(var i = 0;i < _f.pulist.length;i++){
			var pu = _f.pulist[i];
			if(pu.online != 1) continue;
			
			for(var j = 0;j < pu.childResource.length;j++){
				var res = pu.childResource[j];
				if(res.type == "GPS"){
					_mp.points.push({pu:pu,gps_handler:"",paths:new Array()});
				}
			}
		}
		
		_mp.starts();

		// 批量启动所有
//		for(var i = 0;i < _mp.points.length;i++){
//			var p = _mp.points[i];
//			var rv = P_LY.StartGPSStream(_f.connectId, p.pu.puid,0,"");
//			if(rv.rv === 0){						
//				_mp.points[i].gps_handler = rv.response;
//			}
//		}
		
		setInterval(function(){
			_mp.starts();

		},5000);
		
	},
	starts:function(){
		for(var i = 0;i < _mp.points.length;i++){
			var p = _mp.points[i];
			
			if(p.gps_handler != "") return;
			
			var rv = P_LY.StartGPSStream(_f.connectId, p.pu.puid,0,"");
			if(rv.rv === 0){
				_mp.points[i].gps_handler = rv.response;
			}
		}
	},
	start:function(puid){

		var bExists = false;
		for(var i = 0;i < _mp.points.length;i++){
			var p = _mp.points[i];
			if(p.pu.puid == puid){
				bExists = true;
				// 已经存在，并且流句柄存在直接返回
				if(_mp.points[i].gps_handler!= "") return;

				// 已经存在，流句柄为空，启动一下流句柄
				var rv = P_LY.StartGPSStream(_f.connectId, puid,0,"");
				if(rv.rv === 0){						
					_mp.points[i].gps_handler = rv.response;
				}
			}
		}
		
		if(!bExists){
			for(var i = 0;i < _f.pulist.length;i++){
				var pu = _f.pulist[i];
				if(pu.puid == puid){
					for(var j = 0;j < pu.childResource.length;j++){
						var res = pu.childResource[j];
						if(res.type == "GPS"){
							_mp.points.push({pu:pu,gps_handler:"",paths:new Array()});
							var index =_mp.points.length -1;

							var rv = P_LY.StartGPSStream(_f.connectId, puid,0,"");
							if(rv.rv === 0){						
								_mp.points[index].gps_handler = rv.response;
							}
						}
					}
				}
			}
		}
	},
	stop:function(puid){
		for(var i = 0;i < _mp.points.length;i++){
			var p = _mp.points[i];
			if(p.pu.puid == puid){
				P_LY.StopGPSStream(_f.connectId, puid,0,"");
				// 已经存在，并且流句柄存在直接返回
				_mp.points[i].gps_handler = "";

			}
		}
		
		return;
	},
	pustatus_change:function(status,puid){

		if(status == 1){
			_mp.start(puid);
		}else{
			_mp.stop(puid);
		}
		return;
	},
	notify_stream:function(notify){

		if(notify.eventName != "stream_status_notify" ){
			return;
		}
		try{
	
			for(var i = 0;i < _mp.points.length;i++){
				var p = _mp.points[i];
				if(p.gps_handler == notify._HANDLE){
					if(notify.status == -1 || notify.status == 4){
						//_p.stop_download_record_file(_v.connectId,r.type,notify._HANDLE);
						_mp.points[i].gps_handler = "";
					}
					break;
				}
			}
	
		}catch(e){
			
		}finally{
			return;
		}
	},
	gps_notify:function(notify){
		if(notify && typeof notify._HANDLE != "undefined"){
			for(var i = 0;i < _mp.points.length;i++){
				var p = _mp.points[i];
					
					if(typeof p.gps_handler != undefined && p.gps_handler == notify._HANDLE){

						var lng = parseFloat(notify.keyData.longitude);
						var lat = parseFloat(notify.keyData.latitude);
						if(lng <=0 || lat <= 0) continue;
						
						var bearing = parseInt(notify.keyData.bearing);
						var speed = parseInt(notify.keyData.speed);
						
						_mp.gps_delta(_cf.mapType,lng,lat,function(xlng,xlat){
							p.paths.push({lng:xlng,lat:xlat,bearing:bearing,speed:speed});
							_mp.map.query_addr(p.pu.puid,lng,lat,_mp.query_addr_callback);
							_mp.add_marker(xlng,xlat,bearing,speed,p);
						});
						return;
					}
				//}
			}
		}
	},
	query_addr_callback:function(address,id){
		for(var i = 0;i < _mp.markers.length;i++){
			var extData = _mp.markers[i].getExtData();
			if(extData.id == id){
				extData.address = address;
				try{
					//_mp.markers[i].getContent());
					
					// 更新marker的infowindow里的address内容
					var infowindow = extData.infowindow;
					if(infowindow != undefined){
		                var info = infowindow.getContent();
		                if ($(info).find("span.addr").length > 0) {
		                    var rv = $(info).find("span.addr").html(extData.address);                    
		                    //extData.infowindow.setContent(info);
		                }
					}
					_mp.markers[i].setExtData(extData);
				}catch(e){
					alert(e.message)
				}
				return;
			}
		}
		
		return;
	},
	trans_bearing_toicon:function(bearing){
		var idx = 1;
        if (bearing >= 360 - 22.5 || bearing < 22.5)
        {
            idx = 1;
        }
        else if (bearing >= 45 - 22.5 && bearing < 45 + 22.5)
        {
            idx = 2;
        }
        else if (bearing >= 90 - 22.5 && bearing < 90 + 22.5)
        {
            idx = 3;
        }
        else if (bearing >= 135 - 22.5 && bearing < 135 + 22.5)
        {
            idx = 4;
        }
        else if (bearing >= 180 - 22.5 && bearing < 180 + 22.5)
        {
            idx = 5;
        }
        else if (bearing >= 225 - 22.5 && bearing < 225 + 22.5)
        {
            idx = 6;
        }
        else if (bearing >= 270 - 22.5 && bearing < 270 + 22.5)
        {
            idx = 7;
        }
        else if (bearing >= 315 - 22.5 && bearing < 315 + 22.5)
        {
            idx = 8;
        }
        var isOnline = true;
        var icon = "arrow_"+(isOnline?"online":"offline")+"_"+idx+".png";
        return icon;
	},
	add_marker:function(lng,lat,bearing,speed,point){
		var icon = _mp.trans_bearing_toicon(bearing);
		//console.log(icon);
		for(var i = 0;i < _mp.markers.length;i++){
			var m = _mp.markers[i];
			var extData = m.getExtData();
			if(extData.id == point.pu.puid){
				//extData.infowindow.setContent(extData.infowindow.getContent());
				extData.point = point;
				_mp.markers[i].setExtData(extData);
				
				var content = "<table style='color:#000' align=center><tr><td ><img src='themes/icons/"+icon+"' width=32 height=32/></td></tr><tr><td>"+point.pu.name+"</td></tr></table>";
				
				_mp.markers[i].setContent(content);
				_mp.map.move_marker(m,{lng:lng,lat:lat,bearing:null,speed:speed});
				if(extData.bTrack == true){
					extData.track.setPath(Map.lnglats(extData.point.paths));
				}
				return;
			}
		}
		
		// mark显示的图标及名字
		var content = "<table style='color:#000' align=center><tr><td ><img src='themes/icons/"+icon+"' width=32 height=32/></td></tr><tr><td>"+point.pu.name+"</td></tr></table>";
		//var infowindow_content = _mp.create_infocontent(point.pu.puid,point.pu.name+'&nbsp;&nbsp;<span style="font-size:11px;color:#F00;"></span>',"<div><img src='http://tpc.googlesyndication.com/simgad/5843493769827749134' style='position:relative;float:left;margin:0 5px 5px 0;' />地址：<span class='addr' ></span></div><div><a class='hide_track_btn' style='display:none;' onclick=\"_mp.hide_track(\'"+point.pu.puid+"\')\" >关闭轨迹</a>&nbsp;<a style='display:;' class='show_track_btn' onclick=\"_mp.show_track(\'"+point.pu.puid+"\')\" >显示轨迹</a>&nbsp;<a calss='queryhistory_btn' onclick=\"_mp.query_hisotrypath(\'"+point.pu.puid+"\')\" >查询历史</a>&nbsp;<a  onclick=\"_mp.playerbar()\" >回放控制</a></div>");
		//var infowindow = Map.create_infowindow(infowindow_content);
		var infowindow = Map.create_infowindow({puid:point.pu.puid,title:point.pu.name,close:function(){_mp.map.obj.clearInfoWindow()},content:"<div>地址：<span class='addr' ></span></div><div><a class='hide_track_btn' style='display:none;' onclick=\"_mp.hide_track(\'"+point.pu.puid+"\')\" >关闭轨迹</a>&nbsp;<a style='display:;' class='show_track_btn' onclick=\"_mp.show_track(\'"+point.pu.puid+"\')\" >显示轨迹</a>&nbsp;<a calss='queryhistory_btn' onclick=\"_mp.query_hisotrypath(\'"+point.pu.puid+"\',\'"+point.pu.name+"\')\" >查询历史</a>&nbsp;<a  onclick=\"_mp.playerbar()\" >回放控制</a></div>"});
		var rv = _mp.map.add_marker({
			content:content,
			lng:lng,
			lat:lat,
			extData:{
				id:point.pu.puid,
				point:point,
				address:'',
				bTrack:false,
				track:null,
				infowindow:infowindow
			}
		});
		try{
				
			if(rv !== false){
				//绑定click事件
				Map.event.add_listener(rv,"click",function(e){
					var m = e.target;
					var extData = m.getExtData();
					
					try{
						extData.infowindow.open(_mp.map.obj,m.getPosition());
						
					}catch(e){
						alert(e.message)
					}
				});
	
				/*
				// 绑定右键菜单
				Map.event.add_listener(rv,"rightclick",function(e){
					var m = e.target;
					var extData = m.getExtData();
					//创建右键菜单
					if(extData.bTrack == true){
	
						_mp.contextMenu = Map.contextmenu();//new AMap.ContextMenu();
						_mp.contextMenu.addItem("关闭轨迹",function(){
							_mp.stop_track(point.pu.puid);
						},0);
					}else{
						_mp.contextMenu = Map.contextmenu();//new AMap.ContextMenu();
						_mp.contextMenu.addItem("显示轨迹",function(){
							_mp.start_track(point.pu.puid);
						},0);
					}
						_mp.contextMenu.open(_mp.map.obj,e.lnglat);
					
				})
				*/
				_mp.markers.push(rv);
			}
		}catch(e){
		}
	},
	create_infocontent:function(puid,title,content){
		var info = document.createElement("div");
		info.className = "info";
		//可以通过下面的方式修改自定义窗体的宽高
		//info.style.width = "400px";

		// 定义顶部标题
		var top = document.createElement("div");
		top.className = "info-top";
		  var titleD = document.createElement("div");
		  titleD.innerHTML = title;
		  var closeX = document.createElement("img");
		  closeX.src = "http://webapi.amap.com/images/close2.gif";
		  closeX.onclick = function(){
			  //mapObj.clearInfoWindow();
			  _mp.close_infowindow(puid);
			  _mp.map.obj.clearInfoWindow();
		  }
		  
		top.appendChild(titleD);
		top.appendChild(closeX);
		info.appendChild(top);		
	    
		// 定义中部内容
		var middle = document.createElement("div");
		middle.className = "info-middle";
		middle.style.backgroundColor='white';
		middle.innerHTML = content;
		info.appendChild(middle);
		
		// 定义底部内容
		var bottom = document.createElement("div");
		bottom.className = "info-bottom";
		bottom.style.position = 'relative';
		bottom.style.top = '0px';
		bottom.style.margin = '0 auto';
		
		var sharp = document.createElement("img");
		sharp.src = "http://webapi.amap.com/images/sharp.png";
		bottom.appendChild(sharp);	
		info.appendChild(bottom);
		
		return info;
	},
	close_infowindow:function(id){

		for(var i = 0;i < _mp.markers.length;i++){
			var extData = _mp.markers[i].getExtData();
			if(extData.id == id){
				// 更新marker的infowindow里的address内容
				var infowindow = extData.infowindow;
				if(infowindow != undefined){
					infowindow.close();
				}
				break;
			}
		}
		
		for(var i = 0;i < _mp.historyMarkers.length;i++){
			var extData = _mp.historyMarkers[i].getExtData();
			if(extData.id == id){
				// 更新marker的infowindow里的address内容
				var infowindow = extData.infowindow;
				if(infowindow != undefined){
					infowindow.close();
				}
				return;
			}
		}
	},
	show_track:function(id){
		for(var i = 0;i < _mp.markers.length;i++){
			var extData = _mp.markers[i].getExtData();
			if(extData.id == id){
				extData.bTrack = true;
				_mp.markers[i].setExtData(extData);
				if(extData.track == null){
					extData.track = Map.polyline({
						map:_mp.map.obj,
						path:Map.lnglats(extData.point.paths),
						strokeColor:"#3366FF", //线颜色
						strokeOpacity:1, //线透明度 
						strokeWeight:5, //线宽
						strokeStyle:"solid" //线样式
					});
				}else{
					extData.track.setPath(Map.lnglats(extData.point.paths));
					try{
						extData.track.show();
					}catch(e){
						
					}
				}
				var info = extData.infowindow.getContent();
				$(info).find('.hide_track_btn').show();
				$(info).find('.show_track_btn').hide();
				return;
			}
		}
	},
	hide_track:function(id){
		for(var i = 0;i < _mp.markers.length;i++){
			var extData = _mp.markers[i].getExtData();
			if(extData.id == id){
				extData.bTrack = false;
				_mp.markers[i].setExtData(extData);
				if(extData.track == null){
				}else{
					try{
						extData.track.hide();
					}catch(e){
						
					}
				}
				var info = extData.infowindow.getContent();
				$(info).find('.hide_track_btn').hide();
				$(info).find('.show_track_btn').show();
				return;
			}
		}
	},
	query_hisotrypath:function(id,name){
		try{

			var html = "";
			html += "<table>";
			html += "<tr><td>开始时间:</td><td><input id='gnns_query_begintime' class='easyui-datetimespinner' data-options='showSeconds:true' style='width:180px;' /></td></tr>";
			html += "<tr><td>结束时间:</td><td><input id='gnns_query_endtime' class='easyui-datetimespinner' data-options='showSeconds:true' style='width:180px;' /></td></tr>";
			//html += "<tr><td><input id='gnns_query_endtime' class='easyui-datetimespinner' data-options='showSeconds:true' style='width:180px;' /></td></tr>";
			html += "</table>";
			
			$("#query_historyqnss_dlg").dialog({
				width:320,
				height:160,
				content:html,
				buttons:[{
					text:'开始查询',
					handler:function(){
						var bt = $("#query_historyqnss_dlg #gnns_query_begintime").datetimespinner('getValue');
						var et = $("#query_historyqnss_dlg #gnns_query_endtime").datetimespinner('getValue');
				    	bt = parseInt(P_Utils.DTStrToTimestamp(bt).getTime()/1000);
				    	et = parseInt(P_Utils.DTStrToTimestamp(et)/1000);
				    	if(et-bt > 3600*24){
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:'查询时间段请不要超过24小时',
				                timeout:4000,
				                showType:'slide'
				            });
				    		return;
				    	}

			    		$('#wait_dlg').dialog({
			    			title:_lp.frame.notes.waiting_title1,
			    		    width:280,
			    		    height:100,
			    		    closable:false,
			    		    content:'<div style="width:100%;text-align:center;line-height:40px;">'+_lp.frame.notes.query_waiting+'</div>'
			    		});
			    		$('#wait_dlg').dialog('open');
			    		
				    	setTimeout(function(){

					    	var xmlOSets = "";
					    	xmlOSets += "<OSets>";
					    	xmlOSets += '<Res OType="151" OID="'+id+'" Type="GNNS" Idx="0" />';
					    	xmlOSets += "</OSets>";
					    	var gpsdata = new Array(), offset = 0, count = 1000;
					    	while(true){
								var rv = _p.get_control(_f.connectId,"","ST",0,'C_GS_QueryHistoryGPSData',' Offset="'+offset+'" Count="'+count+'" Begin="'+bt+'" End="'+et+'" ',xmlOSets);
								if(rv.M.C.Res && rv.M.C.Res.Error == "0"){
									var gpsPage = rv.M.C.Res.Param.Res.GPS;
									gpsdata = gpsdata.concat(gpsPage);
									offset = parseInt(offset + gpsPage.length);
									if(gpsPage.length < count){
										break;
									}
								}else{
									break;
								}
					    	}

				    		$('#wait_dlg').dialog('close');
					    	if(gpsdata.length > 0){
					    		_mp.start_locus(id,gpsdata,{name:name,beginTime:$("#query_historyqnss_dlg #gnns_query_begintime").datetimespinner('getValue'),endTime:$("#query_historyqnss_dlg #gnns_query_endtime").datetimespinner('getValue')});
					    		
					    	}
				    	},10);
				    	
					}
				},{
					text:'关闭',
					handler:function(){
						$("#query_historyqnss_dlg").dialog('close');
					}
				}]
			}).show();
		}catch(e){
		}
	},
	start_locus:function(id,gpsdata,params){

		var outGPSData = new Array();
		var speed = 40;
		
		for(var i = 0;i < gpsdata.length;i++){
			var g = gpsdata[i];
			var lg = parseFloat(g.Longitude);
			var lt = parseFloat(g.Latitude);
			var speed = parseFloat(g.Speed); 
			//speed += parseInt(g.Speed);
			
			_mp.gps_delta(_cf.mapType,lg,lt,function(lng,lat){
				var lnglat = Map.lnglat(lng,lat);
				outGPSData.push({p:lnglat,speed:speed});
				
				if(outGPSData.length == gpsdata.length){
					var locus = new Map.locus({
						id:id,
						name:params.name,
						beginTime:params.beginTime,
						endTime:params.endTime,
						mapObj:_mp.map.obj,
						paths:outGPSData
					});

					_mp.historyLocus.push(locus);
					_mp.curHistoryLocus = locus;
				}
			});
		}
	},
	clear_locus:function(){
		// 清除原来的
		_mp.player_onclick('stop');
		for(var i = 0;i < _mp.historyLocus.length;i++){
			var l = _mp.historyLocus[i];
			if(l){
				l.remove();
			}
		}
	},
	player_onclick:function(type){
		
		if(_mp.curHistoryLocus == null) return;
		
		if(type == 'play'){
			$('#playbtn_play').hide();
			$('#playbtn_pause').show();
	    	_mp.curHistoryLocus.start();		

		}else if(type == 'pause'){
	    	_mp.curHistoryLocus.pause();	
			try{
				$('#playbtn_pause').hide();
				$('#playbtn_play').show();
				
			}catch(e){
			}
		}else if(type == 'stop'){
			$('#playbtn_pause').hide();
			$('#playbtn_play').show();
	    	_mp.curHistoryLocus.stop();
			
		}else if(type == 'fast'){
	    	_mp.curHistoryLocus.set_speed('fast');
			
		}else if(type == 'slow'){
	    	_mp.curHistoryLocus.set_speed('slow');
		}
		$('#playspan_speed').html(_mp.curHistoryLocus.curPlaySpeed+"倍速");
	},
	playerbar:function(){
		$("#map_playerbar").toggle();
	},
	/*暂停 重写
	clear_locus:function(){
		// 清除原来的
		_mp.player_onclick('stop');
		for(var i = 0;i < _mp.historyMarkers.length;i++){
			var m = _mp.historyMarkers[i];
			if(m){
				var extData = m.getExtData();
				extData.polyline.setMap(null);
				m.setMap(null);
			}
		}
	},
	start_locus:function(id,gpsdata){
		_mp.clear_locus();
		
		if(gpsdata.length <= 0) {
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:'没有查询到历史数据',
                timeout:4000,
                showType:'slide'
            });
			return;
		}
		$("#query_historyqnss_dlg").dialog('close');
		
		
		var lineArr = new Array();
		var speed = 40;
		for(var i = 0;i < gpsdata.length;i++){
			var g = gpsdata[i];
			//lineArr.push(Map.lnglat(parseFloat(g.Longitude).toFixed(6),parseFloat(g.Latitude).toFixed(6)));
			var lg = parseFloat(g.Longitude).toFixed(6);
			var lt = parseFloat(g.Latitude).toFixed(6);

			var lnglat = Map.lnglat(lg,lt);
			lineArr.push(lnglat);
			speed += parseInt(g.Speed);
		}
		var beginPoint = lineArr[0];
		speed = parseInt(speed/gpsdata.length);

		//绘制轨迹
		var polyline = new Map.polyline({
			map:_mp.map.obj,
			path:lineArr,//gpsdata,
			strokeColor:"#00A",//线颜色
			strokeOpacity:1,//线透明度
			strokeWeight:3,//线宽
			strokeStyle:"solid"//线样式
		});
		
		//var infowindow_content = "<div><a style='display:;' onclick=\"_mp.stop_locus(\'"+id+"\')\" >关闭轨迹</a>&nbsp;</div>";
		var infowindow_content = _mp.create_infocontent(id,'<div>轨迹回放</div>',"<div>地址：<span class='addr' ></span></div><div><a class='hide_track_btn' style='display:;' onclick=\"_mp.hide_track(\'"+id+"\')\" >暂停</a>&nbsp;<a style='display:;' class='show_track_btn' onclick=\"_mp.show_track(\'"+id+"\')\" >播放</a></div>");
		
		var infowindow = Map.create_infowindow(infowindow_content);

		//var infowindow = Map.create_infowindow({puid:id,title:"轨迹回放",content:"<div>地址：<span class='addr' ></span></div><div><a class='hide_track_btn' style='display:;' onclick=\"_mp.hide_track(\'"+id+"\')\" >暂停</a>&nbsp;<a style='display:;' class='show_track_btn' onclick=\"_mp.show_track(\'"+id+"\')\" >播放</a></div>"});
		
		//marker = new AMap.Marker({
		marker = Map.marker({
			map:_mp.map.obj,
			//draggable:true, //是否可拖动
			position:beginPoint,//基点位置
			icon:"http://code.mapabc.com/images/car_03.png", //marker图标，直接传递地址url
			offset:Map.pixel(-26,-13),//new AMap.Pixel(-26,-13), //相对于基点的位置
			autoRotation:true,
			extData:{
				id:id,
				polyline:polyline,
				gpsdata:gpsdata,
				infowindow:infowindow
			}
		});
		

		//绑定click事件
//		Map.event.add_listener(marker,"click",function(e){
//			var m = e.target;
//			var extData = m.getExtData();_mp.map.obj.clearInfoWindow();
//			extData.infowindow.open(_mp.map.obj,m.getPosition());	
//		});

		Map.event.add_listener(marker,"moveend",function(e){
			var extData = _mp.curHistoryMarker.getExtData();
			if(_mp.curHistoryMarkerEndPointIndex>=extData.gpsdata.length){
				_mp.player_onclick('stop');
				return;
			}
			_mp.hisory_track_play(extData.gpsdata,_mp.curHistoryMarkerEndPointIndex);
		});
		

		//$('#playbtn_play').hide();
		//$('#playbtn_pause').show();
		
		_mp.historyMarkers.push(marker);
		_mp.curHistoryMarker = marker;

    	$("#map_playerbar").show();
		
    	// 单点播放，不能一起播放，否则无法暂停等
    	//_mp.hisory_track_play(gpsdata,0);
    	_mp.player_onclick('play');
    	
		//_mp.curHistoryMarker.moveAlong(lineArr,speed);
	},
	hisory_track_play:function(gpsdata,beginPointIndex){
		try{
			var endPointIndex = beginPointIndex+1;
			if(endPointIndex > gpsdata.length){
				// 结束
				_mp.player_onclick('stop');
				return;
			}
			if(!gpsdata[endPointIndex]){
				// 不存在
				_mp.player_onclick('stop');
				return;
			}
			//var a = gpsdata[beginPointIndex];
			var b = gpsdata[endPointIndex];

			var lg = parseFloat(b.Longitude).toFixed(6);
			var lt = parseFloat(b.Latitude).toFixed(6);
			var speed = parseInt(b.Speed) * _mp.curHistoryMarkerSpeed;
			var lnglat = Map.lnglat(lg,lt);
			_mp.curHistoryMarkerEndPointIndex = endPointIndex;
			$('#playspan_speed').html(_mp.curHistoryMarkerSpeed+"倍速");
			_mp.curHistoryMarker.moveTo(lnglat,speed);
			
		}catch(e){
		}
		return;
		
	},
	player_onclick:function(type){
		if(type == 'play'){
			$('#playbtn_play').hide();
			$('#playbtn_pause').show();
			var extData = _mp.curHistoryMarker.getExtData();
	    	_mp.hisory_track_play(extData.gpsdata,_mp.curHistoryMarkerEndPointIndex);		

		}else if(type == 'pause'){
			_mp.curHistoryMarker.stopMove();
			try{
				$('#playbtn_pause').hide();
				$('#playbtn_play').show();
				
			}catch(e){
			}
		}else if(type == 'stop'){
			$('#playbtn_pause').hide();
			$('#playbtn_play').show();
			if(_mp.curHistoryMarker && _mp.curHistoryMarker != null){
				_mp.curHistoryMarkerSpeed= 1;
				_mp.curHistoryMarkerEndPointIndex =0;
				_mp.curHistoryMarker.stopMove();	
			}
			
		}else if(type == 'fast'){
			_mp.curHistoryMarkerSpeed++;
			if(_mp.curHistoryMarkerSpeed > 16){
				_mp.curHistoryMarkerSpeed = 1;
			}
			
		}else if(type == 'slow'){
			_mp.curHistoryMarkerSpeed--;
			if(_mp.curHistoryMarkerSpeed < -8){
				_mp.curHistoryMarkerSpeed = -8;
			}
			
		}
	},
	stop_locus:function(id){
		_mp.curHistoryMarker.stopMove();
		_mp.curHistoryMarker = null;
	},
	*/
	gps_delta:function(type,lng,lat,callback){
		var d = {lon:0,lat:0};
		if(type == 'bmap'){
			d = GPS.wgs_bd_encrypt(lat,lng);
		}else{
			d = GPS.gcj_encrypt(lat,lng);
		}
		if(typeof callback == "function"){
			callback(d.lon,d.lat);
		}
		//return {lng:d.lon,lat:d.lat};
	},
	gps_deltas:function(type,points,callback){
		var outGPSData = new Array();
		
		for(var i = 0;i < points.length;i++){
			var g = points[i];
			var lg = parseFloat(g.Longitude);
			var lt = parseFloat(g.Latitude);
			
			_mp.gps_delta(_cf.mapType,lg,lt,function(lng,lat){
				var lnglat = Map.lnglat(lng,lat);
				outGPSData.push(lnglat);
				if(outGPSData.length == gpsdata.length){
					_mp.start_locus(id,outGPSData,speed);
				}
			});
		}
	}
}


