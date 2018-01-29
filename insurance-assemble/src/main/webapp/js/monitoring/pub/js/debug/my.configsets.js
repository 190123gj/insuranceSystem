var _cfs = myconfigsets = {
	pu:null,
	configIds:null,
	configSets:new Object(),
	configXML:"",
	resConfigSets : new Object(),
	inited:false,
	type:new Array(),	
	delayLoadControls:new Array(),
	submitAllCates:'Camera.Capture,Camera.Preprocessor,Camera.Day-Night',
	
	// 初始化
	init:function(connectId){
		_cfs.connectId = connectId;
	},
	
	// 设备切换，主要是在客户端内使用
	pu_change:function(pu){
		try{
		_cfs.pu = pu;
		_cfs.init_tabs();	
		}catch(e){
			alert(e.message)
		}
	},
	// 初始化主tab
	init_tabs:function(){
		
        if (!$.support.leadingWhitespace) {
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.notes.support_title,
                timeout:10000,
                showType:'slide'
            });
        }
        
		var existTabs = new Array();
    	var tabs = $('#res_cf_tabs').tabs('tabs');
    	for(var i = 0;i < tabs.length;i++){
    	    var tb = tabs[i];
    	    var title = tb.panel('options').tab.text();
    	    existTabs.push(title);
    	}
    	
    	_cfs.resConfigSets = new Object();
    	_cfs.configSets = new Object();
		var types = new Array();
		_cfs.type = new Array();
		// 找出所有资源类型，创建
		types.push("ST");
		
		for(var i = 0;i < _cfs.pu.childResource.length;i++){
		//	console.log("获取子资源："+JSON.stringify(_cfs.pu.childResource[i]))
			if( _cfs.pu.childResource[i].type == P_LY.Enum.PuResourceType.VideoOut) continue;
			if(jQuery.inArray(_cfs.pu.childResource[i].type,types)<0){
				types.push(_cfs.pu.childResource[i].type);
			}
		}

		for(var i = 0;i < types.length;i++){
			var number = 0;
			for(var j = 0;j < _cfs.pu.childResource.length;j++){
				if(_cfs.pu.childResource[j].type == types[i]){
					number++;
				}
			}
			_cfs.type.push({type:types[i],number:number});
		}
	
    	var params = "";
    	for(var i = 0;i < types.length;i++){
    		var type = types[i];
    		params += '<Res Type="'+(type||"")+'" Idx="0" OptID="F_RES_ConfigIDSets"  ></Res>';
    	}
    
    	
    	var rv = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,params);
    
    	if(rv != ""){
    		try{
				var xml = _f.load_xml(rv);
		        var res = $(xml).find("Res");
	        	for(var i = 0;i < res.length;i++){
	        		_cfs.configSets[$(res[i]).attr("Type")] = $(res[i]);
	        	}
    		}catch(e){
    			alert(e.message)
    		}
    	}else{
    		return;
    	}
    	// 先创建一个空的，主要是为了能顺序打开下面的tab
    	$('#res_cf_tabs').tabs('add',{			
		    title:"    ",
		    content:"",
		    closable:false
		});
		for(var i = 0;i < types.length;i++){
			var tabhtml = '';
			if(types[i].toLowerCase() == "dp"){
				tabhtml += '<div id="res_de_tabs" class="easyui-layout" data-options="fit:true,border:false" style="width:auto;height:auto"><div id="de_region_west" data-options="region:\'west\',border:false" style="width:149px;border-width: 1px 1px 1px 1px;"></div><div id="de_region_center" data-options="region:\'center\',border:false"  style="width:auto;" ></div></div><div id="decode1_dlg"></div><div id="decode_dlg"></div><div id="iv_dlg"></div><div id="iv1_dlg"></div>';
			}
			else{
				tabhtml += '<div id="'+types[i].toLowerCase()+'_cf_layout" class="easyui-layout" data-options="fit:true,border:false" >';
				tabhtml += '<div id="'+types[i].toLowerCase()+'_cf_region_north" data-options="region:\'north\',border:false" style="height:45px;padding:2px;" ></div>';
				tabhtml += '<div id="'+types[i].toLowerCase()+'_cf_region_center" data-options="region:\'center\',border:true"  style="width:auto;" >';
				tabhtml += '</div>';
				tabhtml += '</div>';
			}
		
			
			var exists = $('#res_cf_tabs').tabs("exists",_lp.frame.navbar[types[i].toLowerCase()].title);
			if(!exists){
				$('#res_cf_tabs').tabs('add',{			
				    title:_lp.frame.navbar[types[i].toLowerCase()].title,
				    iconCls:'icon_'+types[i].toLowerCase(),
				    content:tabhtml,fit:true,
				    id:types[i].toLowerCase()+'_tabpanel',
				    closable:false
				});
			}else{
				var t = $('#res_cf_tabs').tabs('getTab',_lp.frame.navbar[types[i].toLowerCase()].title);
				$('#res_cf_tabs').tabs('update', {
					tab: t,
					options: {
					    content:tabhtml
					}
				});
			}
			
			var j = $.inArray(_lp.frame.navbar[types[i].toLowerCase()].title,existTabs);
			if(j > -1){
				existTabs[j] = "";
			}
		}
		
		$('#res_cf_tabs').tabs({
		    onSelect:function(title,index){
		    //	alert("onSelect,"+title)
		        _cfs.tab_onselect(title,index);
		    },
		    onUnselect:function(title,index){
		    //	alert("onUnselect,"+title)
		    	var t = $('#res_cf_tabs').tabs('getTab',index);
		    	// 正在取消选择的tab包括视频
		    	if($(t).attr('id')== "iv_tabpanel"){
		    		_cfs.draw.close_all();
		    	}
		    	
		    }
		});
		
		$('#res_cf_tabs').tabs('close', "    ");
		
		setTimeout(function(){

			for(var i = 0;i < existTabs.length;i++){
				if(existTabs[i] != ""){
					$('#res_cf_tabs').tabs('close', existTabs[i]);
				}
			}
			
		},100);
		
		$('#res_cf_tabs').tabs("select",_lp.frame.navbar["st"].title);
	},
	// 选择一个资源的tab
	tab_onselect:function(title,index){
		var t = $('#res_cf_tabs').tabs('getTab',index);
		switch(t[0].id.toLowerCase()){
		case "st_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.ST);
		break;
		case "iv_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.VideoIn);
		break;
		case "ia_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.AudioIn);
		break;
		case "ov_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.VideoOut);
		break;
		case "oa_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.AudioOut);
		break;
		case "idl_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.AlertIn);
		break;
		case "odl_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.AlertOut);
		break;
		case "ptz_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.PTZ);
		break;
		case "sp_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.SerialPort);
		break;
		case "wifi_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.WIFI);
		break;
		case "wm_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.Wireless);
		break;
		case "sg_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.Storager);
		break;
		case "gps_tabpanel":
			_cfs.init_region_north(P_LY.Enum.PuResourceType.GPS);
		break;
		case "dp_tabpanel":
				_de.init(_cfs.connectId);
				_de.pu_change(_cfs.pu);
			//_cfs.init_region_north(P_LY.Enum.PuResourceType.DP);
		break;
		}
	},
	
	// 初始化配置顶部元素,资源通道选择，名称，描述，使能设置
	init_region_north:function(resType){
		var html = '';
		var desc = {
			name:'',
			description:'',
			usable:'',
			enable:''
		}
		html += '<div style="padding:3px;">';
		html += '<form method="post"><table cellpadding="2">';
		
		html += '<tr>';
		html += '<td width=40 >'+_lp.frame.configsets.st.name.lbl+':</td>';
		html += '<td width=200><select id="'+resType.toLowerCase()+'_channel_cmb" class="easyui-combobox" data-options="editable:false,panelHeight:\'auto\',onSelect:function(rec){_cfs.resource_onchange(\''+resType+'\',rec)}"   style="width:195px;height:auto;" >';
		var idx = 0;
		if(resType.toLowerCase() != "st"){
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == resType){
					html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
					idx = _cfs.pu.childResource[i].idx;
					if(idx == 0){
						desc.name = _cfs.pu.childResource[i].name;
						desc.description = _cfs.pu.childResource[i].description;
						desc.usable = _cfs.pu.childResource[i].usable;
						desc.enable = _cfs.pu.childResource[i].enable;
					}
				}
			}

			
		}else{
			html += '<option value=0 >'+_cfs.pu.name+'</option>';
			desc.name = _cfs.pu.name;
			desc.description = _cfs.pu.description;
			desc.usable = 1;
			desc.enable = 1;
		}
		
		html += '</select></td>';
		
		html += '<td width=200><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="_cfs.open_resource_rename_dlg(\''+resType+'\')">'+_lp.frame.configsets.btns.rename+'</a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_cfs.open_resource_setdesc_dlg(\''+resType+'\')" >'+_lp.frame.configsets.setDescTitle+'</a></td>';
		
		html += '<td width=30>'+_lp.frame.configsets.st.idx.lbl+':</td>';
		html += '<td width=30><span id="'+resType.toLowerCase()+'_idx_lbl">'+idx+'</span></td>';

		if(resType.toLowerCase() != "st"){
		//	html += '<td width=60></td>';
			var checked = (desc.enable == 1 ? false:true);
			html += '<td width=120><input id="'+resType.toLowerCase()+'_disabled_chk" type=checkbox style="vertical-align：middle;" onclick="_cfs.resource_disabled(\''+resType+'\',this.checked)"  checked="'+checked+'" />&nbsp;<label>'+_lp.frame.configsets.btns.disabled+'</label></td>';
		//	html += '<td width=120></td>';
				
			html += '<td width=60>'+_lp.frame.configsets.st.bEnable.lbl+':</td>';
			html += '<td width=60><span id="'+resType.toLowerCase()+'_usable_span" >'+(desc.usable == 1 ? _lp.y:_lp.n)+'</span></td>';
		}else{
			html += '<td width=60></td>';
			//html += '<td width=120>&nbsp;</td>';
				
			html += '<td width=60> </td>';
			html += '<td width=60 >&nbsp;</td>';
		}
		
		html += '</tr>';
		/*
		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.st.desc.lbl+':</td>';
		html += '<td colspan=7><input id="'+resType.toLowerCase()+'_desc" class="easyui-textbox" data-options="multiline:true" style="width:100%;height:55px" value="'+desc.description+'"></input></td>';
		html += '<td><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="_cfs.set_resource_description(\''+resType+'\',\''+idx+'\')">'+_lp.frame.configsets.btns.set+'</a><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="_cfs.refresh_resource_desc(\''+resType+'\',\''+idx+'\')" style="margin-top:2px;">'+_lp.frame.configsets.btns.refresh+'</a></td>';
		html += '</tr>';
			*/
		html += '</table></form>';
		html += '</div>';

		
        $('#'+resType.toLowerCase()+'_cf_region_north').html(html);
		
		$.parser.parse('#'+resType.toLowerCase()+'_cf_region_north');

		setTimeout(function(){
			// 默认展开第一个资源
			if($('#'+resType.toLowerCase()+'_channel_cmb')){
				try{
					var v = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
					_cfs.resource_onchange(resType,{value:v});	
				}catch(e){
				}
				
			}
		},1);		
		return;
		
	},
	
	// 刷新描述
	refresh_resource_desc:function(resType,idx){
		var rv = _p.query_puresource(_cfs.connectId,_cfs.pu.puid);
		
		if(_p.isArray(rv) && rv.length <= 0){
			
		}else{
			_cfs.pu = rv;
		}
		var d = new Array();
		if(resType.toLowerCase() == "st"){
			d.push({value:0,text:_cfs.pu.name});
			$('#'+resType.toLowerCase()+'_channel_cmb').combobox('loadData',d);
			_cfs.get_resource_value(resType,idx);
		}else{
			var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == resType){
					d.push({value:_cfs.pu.childResource[i].idx,text:_cfs.pu.childResource[i].name});
				}
			}
			
			$('#'+resType.toLowerCase()+'_channel_cmb').combobox('loadData',d);
			$('#'+resType.toLowerCase()+'_channel_cmb').combobox('select',idx);
		}
	},
	
	// 根据cateid获取资源配置值
	get_resource_configsetsbycateid:function(cateId,resType,idx){
		var cates = _cfs.configIds;
        for(var i =0;i < cates.length;i++){
        	var cate = $(cates[i]).children();
        	
	        if (!$.support.leadingWhitespace) {
	        	var nodes = cate[0].childNodes;
	        	for(var i = 0;i < nodes.length;i++){
	        		var n = nodes[i];
	        		//alert(n.baseName);
	        		if(n.baseName.toLowerCase() == "category"){
	    				var name = $(n).attr("Name");
	    				var id = $(n).attr("ID");
	    				var subCategory = $(n).find("Category");
	    				if(id == cateId){
	    					var flagId = resType+"_"+idx;
	    					
	    					if(_cfs.resConfigSets[flagId] == undefined)
	    					_cfs.resConfigSets[flagId] = new Object();
	    					
	    					var configIds = $(n).find("ID"),configIdArr  = new Array();
	    					for(var i =0;i < configIds.length;i++){
	    						if(_cfs.resConfigSets[flagId][configId] != undefined){
	    							continue;
	    						}
	    						var configId = $(configIds[i]).text();
	    						var stream = undefined;
	    						if(configIds[i].parentNode){
	    							var parent = configIds[i].parentNode;
	    							hasStream = $(parent).attr("HasStream");
	    							if(hasStream == "1"){
	    								stream = ($('#'+cateId.replace(".","_")+'streamnum').length > 0 ? $('#'+cateId.replace(".","_")+'streamnum').combobox('getValue') : 0);
	    							}
	    						}
	    						configIdArr.push({type:resType,idx:idx,id:configId,stream:stream});
	    					}
	    			
	    					var configXML = _f.load_xml(_p.get_res_configs(_cfs.connectId,_cfs.pu.puid,configIdArr));
	    					var res = $(configXML).find("Res");
	    					for(var i = 0;i < res.length;i++){
	    						var configId = $(res[i]).attr("OptID");
	    						_cfs.resConfigSets[flagId][configId] =res[i];
	    					}
	    					break;
	    				}
	        		}
	        	}
	        }else{   	
	    		$(cate).each(function(k,n){
	    			if($(n)[0].localName.toLowerCase() == "category"){
	    				var name = $(n).attr("Name");
	    				var id = $(n).attr("ID");
	    				var subCategory = $(n).find("Category");
	    				if(id == cateId){
	    					var flagId = resType+"_"+idx;
	    					
	    					if(_cfs.resConfigSets[flagId] == undefined)
	    					_cfs.resConfigSets[flagId] = new Object();
	    					
	    					var configIds = $(n).find("ID"),configIdArr  = new Array();
	    					for(var i =0;i < configIds.length;i++){
	    						if(_cfs.resConfigSets[flagId][configId] != undefined){
	    							continue;
	    						}
	    						var configId = $(configIds[i]).text();
	    						var stream = undefined;
	    						if(configIds[i].parentNode){
	    							var parent = configIds[i].parentNode;
	    							hasStream = $(parent).attr("HasStream");
	    							if(hasStream == "1"){
	    								stream = ($('#'+cateId.replace(".","_")+'streamnum').length > 0 ? $('#'+cateId.replace(".","_")+'streamnum').combobox('getValue') : 0);
	    							}
	    						}
	    						configIdArr.push({type:resType,idx:idx,id:configId,stream:stream});
	    					}
	    					var configXML = _p.get_res_configs(_cfs.connectId,_cfs.pu.puid,configIdArr);
	    			
	    					var res = $(configXML).find("Res");
	    					for(var i = 0;i < res.length;i++){
	    						var configId = $(res[i]).attr("OptID");
	    						_cfs.resConfigSets[flagId][configId] =res[i];
	    					}
	    					return false;
	    				}
	    			}
	    		});
			}
				        	
				     
        }
	},
	
	// 根据cateId刷新资源的配置
	refresh_resource_configsetsbycateid:function(cateId,resType,idx){
		
		$('#wait_dlg').dialog({
			title:_lp.frame.notes.waiting_title1,
		    width:280,
		    height:100,
		    modal: true,
		    closable:false,
		    content:'<div style="width:100%;text-align:center;line-height:40px;">'+_lp.frame.notes.query_waiting+'</div>'
		});
		
		$('#wait_dlg').dialog('open');
		setTimeout(function(){
			
			if(cateId == "OSD.Basic" && resType.toLowerCase() == "iv"){
				_cfs.draw.close_all();
			}
			var cates = _cfs.configIds;
			_cfs.get_resource_configsetsbycateid(cateId,resType,idx);
	        for(var i =0;i < cates.length;i++){
	        	var cate = $(cates[i]).children();
		        if (!$.support.leadingWhitespace) {
		        	var nodes = cate[0].childNodes;
		        	for(var i = 0;i < nodes.length;i++){
		        		var n = nodes[i];
		        		//alert(n.baseName);
		        		if(n.baseName.toLowerCase() == "category"){
		        			
		    				var name = $(n).attr("Name");
		    				var id = $(n).attr("ID");
		    				var subCategory = $(n).find("Category");
		    				cateId = cateId.split(".")[0];
		    				if(id == cateId){
		    					var configIdArr = new Array();
		    					var configIds = $(n).find("ID"),configIdArr  = new Array();
		    					for(var i =0;i < configIds.length;i++){
		    						var configId = $(configIds[i]).text();
		
		    						var stream = undefined;
		    						if(configIds[i].parentNode){
		    							var parent = configIds[i].parentNode;    							
		    							hasStream = $(parent).attr("HasStream");
		    							if(hasStream == "1"){
		    								
		    								stream = ($('#'+cateId.replace(".","_")+'streamnum').length > 0 ? $('#'+cateId.replace(".","_")+'streamnum').combobox('getValue') : 0);
		    							}
		    						}
		    						
		    						configIdArr.push({type:resType,idx:idx,id:configId,stream:stream});
		    					}
		    					_cfs.refresh_resource_configsetsbyid(true,configIdArr,null);
		
		    					if(subCategory.length > 0){
		        					contentHtml = _cfs.create_sub_form_panel(n,resType,idx,cateId);
		    					}else{
		        					contentHtml = _cfs.create_form_panel(n,resType,idx,cateId);
		    					}
		    					
								var tabsId='#'+resType.toLowerCase()+'_cf_tabs';
								var t = $(tabsId).tabs("getTab",name);
								
								$(tabsId).tabs({
									'onUpdate':function(){
										$('#wait_dlg').dialog('close');
									}
								})
								
								$(tabsId).tabs('update', {
									tab: t,
									options: {
										content:contentHtml
									}
								});
		    					break;
		    				}
		        		}
		        	}
		        }else{ 
		    		$(cate).each(function(k,n){
		    			if($(n)[0].localName.toLowerCase() == "category"){
		    				var name = $(n).attr("Name");
		    				var id = $(n).attr("ID");
		    				var subCategory = $(n).find("Category");
		    				cateId = cateId.split(".")[0];
		    				if(id == cateId){
		    					var configIdArr = new Array();
		    					var configIds = $(n).find("ID"),configIdArr  = new Array();
		    					for(var i =0;i < configIds.length;i++){
		    						var configId = $(configIds[i]).text();
		
		    						var stream = undefined;
		    						if(configIds[i].parentNode){
		    							var parent = configIds[i].parentNode;    							
		    							hasStream = $(parent).attr("HasStream");
		    							if(hasStream == "1"){
		    								
		    								stream = ($('#'+cateId.replace(".","_")+'streamnum').length > 0 ? $('#'+cateId.replace(".","_")+'streamnum').combobox('getValue') : 0);
		    							}
		    						}
		    						
		    						configIdArr.push({type:resType,idx:idx,id:configId,stream:stream});
		    					}
		    					_cfs.refresh_resource_configsetsbyid(true,configIdArr,null);
		
		    					if(subCategory.length > 0){
		        					contentHtml = _cfs.create_sub_form_panel(n,resType,idx,cateId);
		    					}else{
		        					contentHtml = _cfs.create_form_panel(n,resType,idx,cateId);
		    					}
		    					
								var tabsId='#'+resType.toLowerCase()+'_cf_tabs';
								var t = $(tabsId).tabs("getTab",name);
								
//								$(tabsId).tabs({
//									'onUpdate':function(){
//										$('#wait_dlg').dialog('close');
//									}
//								})
								$(tabsId).tabs('update', {
									tab: t,
									options: {
										content:contentHtml
									}
								});
								
								
		    					return false;
		    				}
		    			}
		    		});
		        }
	        }
		},1);
		return;
	},
	// 刷新资源的配置，根据config列表
	refresh_resource_configsetsbyid:function(bForce,configIds,callback){
		var t = $('#res_cf_tabs').tabs('getSelected');
		//var t2 = $('#'+resType.toLowerCase()+'_cf_tabs').tabs('getSelected');
		
		if($(t).attr('id')== "iv_tabpanel"){
			_cfs.draw.close_all();
		}
//		var tabsId='#'+resType.toLowerCase()+'_cf_tabs';
//		var t = $(tabsId).tabs("getTab",index);
//		var cateId = $(t).attr("id");
//		//alert(cateId)
//      if(cateId == "OSD" || cateId == "Alarm"){
//      	//　如果有视频在播放要关闭，并且清除窗口
//      	_cfs.draw.close_all();
//      }
		        
		var configIdArr  = new Array();
		for(var i =0;i < configIds.length;i++){
			var c = configIds[i];
			
			var flagId = c.type+"_"+c.idx;
			if(!bForce && _cfs.resConfigSets[flagId][c.id] != undefined){
				continue;
			}
			configIdArr.push(c);
		}
		var configXML = _f.load_xml(_p.get_res_configs(_cfs.connectId,_cfs.pu.puid,configIds));
		var res = $(configXML).find("Res");
		for(var i = 0;i < res.length;i++){
			var r = $(res[i]);
			var configId = r.attr("OptID");
			if(r.attr("Error") != "0") {
//	            $.messager.show({
//	                title:_lp.frame.configsets.notes.noteTitle,
//	                //msg:configId+','+r.attr("Error"),
//	                msg:r.attr("Error"),
//	                timeout:4000,
//	                showType:'slide'
//	            });	            
				continue;
			}
			
			var flagId = r.attr("Type")+"_"+r.attr("Idx");
			_cfs.resConfigSets[flagId][configId] =res[i];
		}
		if(typeof callback == "function"){
			callback(configIds);
		}else{
			_cfs.refresh_config_callback(configIds);
		}
	},
	
	// 刷新资源回调
	refresh_config_callback:function(configIds){
		var configIdArr  = new Array();
		for(var i =0;i < configIds.length;i++){
			var c = configIds[i];
			
			var flagId = c.type+"_"+c.idx;
			
		}
	},
	
	refresh_resource_configset:function(resType,configIds){
		
		var configsets = new Array();
		var idx = 0;
		
		if($('#'+resType.toLowerCase()+'_channel_cmb')){
			idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
		}
			
		var flagId = resType+"_"+idx;
		for(var i = 0;i < configIds.length;i++){
			configsets.push({type:resType,idx:idx,id:configIds[i]});
		}
		_cfs.refresh_resource_configsetsbyid(true,configsets,function(configIds){
			for(var i = 0;i <configIds.length;i++){
				var configId = configIds[i].id;
				if(_cfs.resConfigSets[flagId] && _cfs.resConfigSets[flagId][configId]){
					
					var res =_cfs.resConfigSets[flagId][configId];
	    			var param = $(res).children("Param");
	    			var attr = $(res).children("Attr");
	   
    				if($(attr).attr('desc')){
						val = $(attr).attr('desc');
					}else{
						val = $(param).attr("Value");
					}	    			
	    			
	    			attrs = $(res).children("Attr");
					_cfs.update_form_input_unit(resType,configId,attrs,val,param);
				} 
			}
		});
	},
	
	// 打开设置资源名称编辑框
	open_resource_rename_dlg:function(resType){

		var name = '';
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
		
		if(resType.toLowerCase() != "st"){
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == resType && _cfs.pu.childResource[i].idx == idx){
					name = _cfs.pu.childResource[i].name;
					break;
				}
			}
		}else{
			name = _cfs.pu.name;
		}

		if($('#'+resType.toLowerCase()+'_resource_rename_dlg').length < 1){
			var html = '';
			html += '<div id="'+resType.toLowerCase()+'_resource_rename_dlg"  >';

			html += '<div style="padding:5px;"><form id="'+resType.toLowerCase()+'_resource_rename_form" method="post" novalidate>';
			html += '<div class="fitem">';
			html += '<label>'+_lp.frame.configsets.st.name.lbl+':</label>';
			html += '<input id="'+resType.toLowerCase()+'_new_resname" class="easyui-textbox" value="'+name+'" required="true" style="width:150px;">';
			html += '</div>';
			html += '</form></div>';
			
			html += '</div>';	
			$(html).appendTo($('#'+resType.toLowerCase()+'_cf_region_north'));

			$('#'+resType.toLowerCase()+'_resource_rename_dlg').dialog({
			    title: _lp.frame.configsets.renameTitle,
			    width: 300,
			    height: 130,
			    closed: false,
			    cache: false,
			    content:html,
			    buttons:[{
					text:_lp.frame.configsets.btns.ok,iconCls:"icon-ok",
					handler:function(){
						_cfs.set_resource_name(resType,idx)
					}
				},{
					text:_lp.frame.configsets.btns.close,iconCls:"icon-cancel",
					handler:function(){
						$('#'+resType.toLowerCase()+'_resource_rename_dlg').dialog("close");
					}
				}],
			    modal: true
			});
			
		}else{

			$('#'+resType.toLowerCase()+'_new_resname').textbox('setValue',name);
		}
		
//		$('#'+resType.toLowerCase()+'_resource_rename_dlg').show();
		$('#'+resType.toLowerCase()+'_resource_rename_dlg').dialog("open");
		//$.parser.parse('#'+resType.toLowerCase()+'_resource_rename_dlg');
	},
	
	// 设置资源名称
	set_resource_name:function(resType,idx){
		var val = {};
		if(resType.toLowerCase() == "st"){
			val = {name:$('#'+resType.toLowerCase()+'_new_resname').textbox('getValue'),description:_cfs.pu.description,enable:'1',usable:'1'};
		}else{
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == resType && _cfs.pu.childResource[i].idx== idx){
					//alert(_cfs.pu.childResource[i].usable)
					val = {name:$('#'+resType.toLowerCase()+'_new_resname').textbox('getValue'),description:_cfs.pu.childResource[i].description,enable:_cfs.pu.childResource[i].enable,usable:_cfs.pu.childResource[i].usable};
					break;
				}
			}
		}
	
			
		var rv = _p.set_res_name(_cfs.connectId,_cfs.pu.puid,resType,$('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue"),val.name);
		if(rv.rv == "0" ){
			_cfs.refresh_resource_desc(resType,idx);
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.note1,
                timeout:4000,
                showType:'slide'
            });
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError1+'（response:'+rv.response+'）。',
                timeout:4000,
                showType:'slide'
            });
    	}
		$('#'+resType.toLowerCase()+'_resource_rename_dlg').dialog("close");
        return rv;
        
	},
	
	// 打开设置资源描述编辑框
	open_resource_setdesc_dlg:function(resType){
		var desc = '';
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");		
		if(resType.toLowerCase() != "st"){
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == resType && _cfs.pu.childResource[i].idx == idx){
					desc = _cfs.pu.childResource[i].description;
					break;
				}
			}
		}else{
			desc = _cfs.pu.description;
		}
		

		if($('#'+resType.toLowerCase()+'_resource_setdesc_dlg').length < 1){
			var html = '';
			html += '<div id="'+resType.toLowerCase()+'_resource_setdesc_dlg" >';

			html += '<div style="padding:5px;"><form id="'+resType.toLowerCase()+'_resource_setdesc_form" method="post" novalidate>';
			html += '<div class="fitem">';
			html += '<label>'+_lp.frame.configsets.st.desc.lbl+':</label>';
			html += '<input id="'+resType.toLowerCase()+'_new_setdesc" class="easyui-textbox" value="'+desc+'" required="true" data-options="multiline:true" style="width:330px;height:140px" >';
			html += '</div>';
			html += '</form></div>';
			
			html += '</div>';
			
			$(html).appendTo($('#'+resType.toLowerCase()+'_cf_region_north'));

			$('#'+resType.toLowerCase()+'_resource_setdesc_dlg').dialog({
			    title: _lp.frame.configsets.setDescTitle,
			    width: 400,
			    height: 230,
			    closed: false,
			    cache: false,
			    content:html,
			    buttons:[{
					text:_lp.frame.configsets.btns.ok,iconCls:"icon-ok",
					handler:function(){
						_cfs.set_resource_description(resType);
					}
				},{
					text:_lp.frame.configsets.btns.close,iconCls:"icon-cancel",
					handler:function(){
						$('#'+resType.toLowerCase()+'_resource_setdesc_dlg').dialog("close");
					}
				}],
			    modal: true
			});
			
			
		}else{

			$('#'+resType.toLowerCase()+'_new_setdesc').textbox('setValue',desc);
		}

		$('#'+resType.toLowerCase()+'_resource_setdesc_dlg').dialog("open");
	},
	
	// 设置资源描述
	set_resource_description:function(resType){
		var val = {};
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");

		if(resType.toLowerCase() == "st"){
			var description = $('#'+resType.toLowerCase()+'_new_setdesc').textbox("getValue");
			val = {name:_cfs.pu.name,description:description,enable:'1',usable:'1'};
		}else{
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				var pur =_cfs.pu.childResource[i]; 
				if(pur.type == resType && pur.idx== idx){
					var description = $('#'+resType.toLowerCase()+'_new_setdesc').textbox("getValue");
					val = {name:pur.name,description:description,enable:pur.enable,usable:pur.usable};
					break;
				}
			}
		}
		rv = _p.set_res_description(_cfs.connectId,_cfs.pu.puid,resType,idx,val.description);
		if(rv.rv == 0){
			_cfs.refresh_resource_desc(resType,idx);
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.note2,
                timeout:4000,
                showType:'slide'
            });
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError2+'（response:'+rv.response+'）。',
                timeout:4000,
                showType:'slide'
            });
    	}
		$('#'+resType.toLowerCase()+'_resource_setdesc_dlg').dialog("close");
        return rv;
    	
        
	},
	
	// 禁用资源
	resource_disabled:function(resType,status){
		var val = {};
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
		if(resType.toLowerCase() == "st"){
			return;
		}else{
			for(var i = 0;i < _cfs.pu.childResource.length;i++){
				var pur =_cfs.pu.childResource[i]; 
				if(pur.type == resType && pur.idx== idx){
					var status = $("#"+resType.toLowerCase()+"_disabled_chk").prop("checked");
					val = {name:pur.name,description:pur.description,enable:(status==true ? "0" : "1"),usable:pur.usable};
					break;
				}
			}
		}
		rv = _p.set_res_enable(_cfs.connectId,_cfs.pu.puid,resType,idx,val.enable);
		if(rv.rv == 0){
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.note2,
                timeout:4000,
                showType:'slide'
            });
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError2+'（response:'+rv.response+'）。',
                timeout:4000,
                showType:'slide'
            });
    	}
	},
	
	// 初始化配置细项元素
	init_region_center:function(resType,idx){
		// 创建参数设置各模块
        if(_cfs.configSets[resType]){
        	_cfs.configIds = _cfs.configSets[resType];
            _cfs.fetch_category_configsets(_cfs.configIds,resType,idx);
        }
	},
	
	// 资源通道切换
	resource_onchange:function(resType,rec){
		if($('#'+resType.toLowerCase()+'_cf_tabs').length >0){
		var title = "";
		var t = $('#'+resType.toLowerCase()+'_cf_tabs').tabs('getSelected');
			title = t.panel('options').title;	
		}
		_cfs.init_region_center(resType,rec.value);
		for(var i = 0;i < _cfs.pu.childResource.length;i++){
			if(_cfs.pu.childResource[i].type == resType && rec.value == _cfs.pu.childResource[i].idx){
				var r = _cfs.pu.childResource[i];
				$("#"+resType.toLowerCase()+"_idx_lbl").html(r.idx);
				//$("#"+resType.toLowerCase()+"_desc").textbox("setValue",r.description);
				$("#"+resType.toLowerCase()+"_disabled_chk").prop("checked",(r.enable ==1 ? false : true));
				$("#"+resType.toLowerCase()+"_usable_span").html((r.usable ? _lp.y:_lp.n));
				
				break;
			}
		}
		
		if(title != ""){
			if($('#'+resType.toLowerCase()+'_cf_tabs').tabs('exists',title)){
				$('#'+resType.toLowerCase()+'_cf_tabs').tabs('select',title);
			}
		}
	},
	
	// 获取某资源的通通号，状态[使能，启用]
	get_resource_value:function(resType,idx){

		for(var i = 0;i < _cfs.pu.childResource.length;i++){
			if(_cfs.pu.childResource[i].type == resType && idx == _cfs.pu.childResource[i].idx){
				var r = _cfs.pu.childResource[i];
				$("#"+resType.toLowerCase()+"_idx_lbl").html(r.idx);
				//$("#"+resType.toLowerCase()+"_desc").textbox("setValue",r.description);
				if($("#"+resType.toLowerCase()+"_disabled_chk").length > 0) $("#"+resType.toLowerCase()+"_disabled_chk").prop("checked",(r.enable ==1 ? false : true));
				if($("#"+resType.toLowerCase()+"_usable_span").length > 0)$("#"+resType.toLowerCase()+"_usable_span").html((r.usable ? _lp.y:_lp.n));
				break;
			}
		}
	},
	
	// 遍历配置分类
	fetch_category_configsets:function(cates,resType,idx){
        for(var i =0;i < cates.length;i++){
        	_cfs.create_sub_tabpanel($(cates[i]).children(),resType,idx);
        }
	},
	
	// 创建配置大项tab
	create_sub_tabpanel:function(obj,resType,idx){
		var html = '';
        if (!$.support.leadingWhitespace) {
        	var nodes = obj[0].childNodes;
        	for(var i = 0;i < nodes.length;i++){
        		var n = nodes[i];
        		if(n.baseName.toLowerCase() == "category"){
					var name = $(n).attr("Name");
					var cateId = $(n).attr("ID");
					html += '<div title="'+name+'" id="'+cateId+'" idx="'+idx+'" data-options="closable:false" style="padding:5px;" fit="true" >';
					//var subCategory = $(n).find("Category");
					html += '</div>';
        			
        		}
        	}
        }
        else{
			$(obj).each(function(k,n){
				//alert( jQuery.toJSON(n));
				//alert($(n)[0]);
				if($(n)[0].localName.toLowerCase() == "category"){
					var name = $(n).attr("Name");
					var cateId = $(n).attr("ID");
					html += '<div title="'+name+'" id="'+cateId+'" idx="'+idx+'" data-options="closable:false" style="padding:5px;" fit="true" >';
					//var subCategory = $(n).find("Category");
					html += '</div>';
				}
			});
	        }
		html = '<div id="'+resType.toLowerCase()+'_cf_tabs" resType="'+resType+'"  class="easyui-tabs" data-options="border:false" tabPosition="top" style="width:auto;height:auto;" fit="true" >'+html+'</div>';
		
        $('#'+resType.toLowerCase()+'_cf_region_center').html(html);
        
		$('#'+resType.toLowerCase()+'_cf_tabs').tabs({
			//selected:1,
			onUnselect:function(title,index){
				
				if(resType == "IV" ){
					_cfs.draw.close_all();
//					var t = $(this).tabs('getTab',index);					
//					var opts = t.panel('options');
//					if(opts && opts.id == "OSD"){
//						// 关闭正在播放的视频
//					}
				}
			},
		    onSelect:function(title,index){
				
		    	var t = $(this);
	    		$('#wait_dlg').dialog({
	    			title:_lp.frame.notes.waiting_title1,
	    		    width:280,
	    		    height:100,
	    		    closable:false,
	    		    content:'<div style="width:100%;text-align:center;line-height:40px;">'+_lp.frame.notes.query_waiting+'</div>'
	    		});
	    		$('#wait_dlg').dialog('open');
		    	setTimeout(function(){
		    		try{
		    			var t1 = $('#res_cf_tabs').tabs('getSelected');
		    			var t2 = $('#'+resType.toLowerCase()+'_cf_tabs').tabs('getSelected');
		    			_cfs.resource_tab_onselect(title,index,t.attr("resType"));	
		    		}catch(e){
		    		}
			        
		    		$('#wait_dlg').dialog('close');
		    	},1);
		    },
			onUpdate:function(title,index){
				
				// 这里面都要特殊处理的内容
				$('#wait_dlg').dialog('close');
		        setTimeout(function(){
		       
			        var cmb = $('.easyui-combobox');
			        for(var i = 0;i < cmb.length;i++){
			        	if(cmb[i].id.search("_schedule")>0){
			        		var flagId = resType+"_"+$('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
			        		var configId = cmb[i].id.replace('_schedule','');
			        		if(_cfs.resConfigSets[flagId] && _cfs.resConfigSets[flagId][configId]){
				        		var res = _cfs.resConfigSets[flagId][configId];
				        		var mode = $(res).children("Param").attr("Mode");
				        		$("#"+cmb[i].id).combobox('setValue',"");
				        		$("#"+cmb[i].id).combobox('select',mode);
			        		}
			        		//_cfs.schedule_select(configId,$("#"+cmb[i].id).combobox('getValue'));
			        	}
			        }
			    if($('#F_ST_IPChannelSets_grid')) {
			    	 $('#F_ST_IPChannelSets_grid').datagrid();
			    	}
			       
			        if($('#C_SG_GetDiskInfo_grid')){
			        	$('#C_SG_GetDiskInfo_grid').datagrid("reload");
			        }
		        },1);
			}
		});
		$('#'+resType.toLowerCase()+'_cf_tabs').tabs("select",0);
		$.parser.parse('#'+resType.toLowerCase()+'_cf_region_center');
	},
	
	// 选择资源tab
	resource_tab_onselect:function(title,index,resType){
		
		var tabsId='#'+resType.toLowerCase()+'_cf_tabs';
		var t = $(tabsId).tabs("getTab",index);
		var cateId = $(t).attr("id");
		var idx = $(t).attr("idx");
		//var cates = _cfs.configIds[0].children;
		var cates = _cfs.configIds;
		var contentHtml = "";
		
		
        for(var i =0;i < cates.length;i++){
        	var cate = $(cates[i]).children();
        	
	        if (!$.support.leadingWhitespace) {
	        	var nodes = cate[0].childNodes;
	        	for(var i = 0;i < nodes.length;i++){
	        		var n = nodes[i];
	        		//alert(n.baseName);
	        		if(n.baseName.toLowerCase() == "category"){
	    				var name = $(n).attr("Name");
	    				var id = $(n).attr("ID");
	    				var subCategory = $(n).find("Category");
	    				if(id == cateId){
	
	    					_cfs.get_resource_configsetsbycateid(cateId,resType,idx);
	    					if(subCategory.length > 0){
	        					contentHtml = _cfs.create_sub_form_panel(n,resType,idx,cateId);
	    					}else{
	        					contentHtml = _cfs.create_form_panel(n,resType,idx,cateId);
	    					}
							continue;
	    				}
	        			
	        		}
	        	}
	        }else{
				        	
	    		$(cate).each(function(k,n){
	        
	    			if($(n)[0].localName.toLowerCase() == "category"){
	    				var name = $(n).attr("Name");
	    				var id = $(n).attr("ID");
	    				var subCategory = $(n).find("Category");
	    				if(id == cateId){
	
	    					_cfs.get_resource_configsetsbycateid(cateId,resType,idx);
	    					if(subCategory.length > 0){
	        					contentHtml = _cfs.create_sub_form_panel(n,resType,idx,cateId);
	    					}else{
	        					contentHtml = _cfs.create_form_panel(n,resType,idx,cateId);
	    					}
	    					return true;
	    				}
	    			}
	    		});
	        }
        }
       // console.log(contentHtml)
        //alert(title)
		$(tabsId).tabs('update', {
			tab: t,
			options: {
				content:contentHtml
			}
		});
		$.parser.parse("#"+tabsId);
		
        setTimeout(function(){

	        //if($('#F_ST_IPChannelSets_grid')) $('#F_ST_IPChannelSets_grid').datagrid();
	        if($('#C_SG_GetDiskInfo_grid')){
	        	$('#C_SG_GetDiskInfo_grid').datagrid("reload");
	        }			        
        },1);
	},
	
	// 切换流
	streamnum_onchage:function(nVal,oVal,idx,cateId){
	
		var formId = cateId.replace(".","_")+"_form";
		var configIds = new Array();

    	var inputs =$('#'+formId+' input[configid]');
    	
    	for(var i =0;i < inputs.length;i++){
    		var c = $(inputs[i]);
    		configIds.push({type:"IV",idx:idx,id:c.attr('configid'),stream:nVal});
    	}
    	

    	var selects =$('#'+formId+' select[configid]');
    	for(var i =0;i < selects.length;i++){
    		configIds.push({type:"IV",idx:idx,id:selects[i].id,stream:nVal});
    	}
    	setTimeout(function(){

    		_cfs.refresh_resource_configsetsbyid(true,configIds,function(){;
    		var flagId = "IV_"+idx;
    			
    			
    			for(var i = 0;i < configIds.length;i++){
    	    		var configId = configIds[i].id;

    				if(_cfs.resConfigSets[flagId] != undefined && _cfs.resConfigSets[flagId][configId] != undefined){
    					var res = _cfs.resConfigSets[flagId][configId];
    					
    					var param = $(res).children("Param");

    					val = $(param).attr("Value");
    					attrs = $(res).children("Attr");
    					
    					if(attrs.attr('Setable')== '1'){
    						bSubmit = true;
    					}
    		            if(attrs == null) continue;
    		            
    		           
						
						
						var min = "";
						var max = "";
						var note = "";
					
						if(attrs.attr('Unit')){
							note +='('+_lp.des.unit+':'+attrs.attr('Unit')+')';
						}
						
						// 看是否'+_lp.des.min+''+_lp.des.max+'
						if(attrs.attr('Min')){
							min=attrs.attr('Min');
						}

						if(attrs.attr('Max')){
							max=attrs.attr('Max');
						}
						
					
    		
    		    		if(typeof attrs.attr("Type") == "undefined") continue;
    		    		
    		    		switch(attrs.attr("Type").toLowerCase()){
    		    			case "string":
    		    			  if(attrs.attr('Setable')== 0){
    		    					$("#"+configId).textbox({  
										min:min,  	  
										max:max,
										editable:false,
										disabled:true		  
									});  
								}else{
									$("#"+configId).textbox({  
										min:min,  	  
										max:max
									}); 
										 
									if(min != '' && max != ''){
										note += '('+min+'～'+max+')';
									}else if(min != ''){
										note += '('+_lp.des.min+':'+min+')';
									}else{
										note += '('+_lp.des.max+':'+max+')';
									}
								}
    		    				$("#"+configId+"_node").html(note);
		    					$("#"+configId).textbox('setValue',val);
    		    			break;
    		    			case "int":
    		    			  if(attrs.attr('Setable')== 0){
    		    					$("#"+configId).numberspinner({  
										min:min,  	  
										max:max,
										editable:false,
										disabled:true		  
									});  
								}else{
									
									$("#"+configId).numberspinner({  
										min:min,  	  
										max:max
									}); 
										 
									if(min != '' && max != ''){
										note += '('+min+'～'+max+')';
									}else if(min != ''){
										note += '('+_lp.des.min+':'+min+')';
									}else{
										note += '('+_lp.des.max+':'+max+')';
									}
								}
    		    				$("#"+configId+"_node").html(note);
    		    				$("#"+configId).numberspinner('setValue',val);
    		    			break;
    		    			case "bool":
    		    				$("#"+configId).prop("checked",(val == 1 ? true:false));
    		    				$("#"+configId).attr("disabled",(attrs.attr('Setable') == 1 ? false:true));
    		    			break;
    		    			case "enum":
    		    				var enums = new Array();
    		    				$(attrs).find("it").each(function(k,n){
    		    						var text = $(n).text();
										if($(n).attr('Desc') && $(n).attr('Desc').length > 0){
											text = $(n).attr('Desc');
										}
			    		    			enums.push({value:$(n).text(),text:text,"selected":(val==$(n).text()?true:false)});
    		    				});
    		    				
    		    				$("#"+configId).combobox('loadData',enums);
    		    				
    		    			break;
    		    		}
    				}
    			}
    		});
    	},160);
		return;
	},
	
	// 创建某资源一个分类panel里的所有config所有dom元素
	create_form_html:function(n,resType,idx,cateId){
		var html = '',bSubmit = false,configIdArr = new Array(),configIds=$(n).children("ID");   //取对象
		
		//configXML = _cfs.configXML;
		var flagId = resType+"_"+idx;
		if($(n).attr("HasStream") && $(n).attr("HasStream") == "1"){
			
			_cfs.refresh_resource_configsetsbyid(true,[{type:resType,idx:idx,id:'F_IV_SupportedStreamNum'}]); //(_cfs.connectId,_cfs.pu.puid,resType,idx,'F_IV_SupportedStreamNum');
			
			var rv =_cfs.resConfigSets[flagId]['F_IV_SupportedStreamNum'];
			
			//var res = $(rv).find("Res");
			var d = new Array();
			if($(rv).attr("Error") == 0){
				var p = $(rv).find("Param");
				var num = $(p).attr("Value");
				for(var i = 0;i < num;i++){
					d.push({id:i,name:'Stream'+(i+1)});
				}
			}else{
				d.push({id:0,name:'Stream1'})
			}

			
			html += '<tr><td>'+_lp.frame.player.cameralist.streamType+':</td>';
			html += '<td align=rigth><select id="'+cateId.replace(".","_")+'_streamnum" class="easyui-combobox" panelHeight="auto" data-options="panelHeight:\'auto\',editable:false,onLoadSuccess:function(){_cfs.streamnum_onchage(0,0,\''+idx+'\',\''+cateId+'\')},onChange:function(nVal,oVal){_cfs.streamnum_onchage(nVal,oVal,\''+idx+'\',\''+cateId+'\');}"  style="width:173px;height:auto;">';
			for(var i = 0;i < d.length;i++){
				html += '<option value="'+d[i].id+'" >'+d[i].name+'</option>';
			}
			html += '</select></td></tr>';
		}
		for(var i = 0;i < configIds.length;i++){
    		var configId = $(configIds[i]).text();
    	
    		if(_cfs.resConfigSets[flagId] != undefined && _cfs.resConfigSets[flagId][configId] != undefined){
    			var res = _cfs.resConfigSets[flagId][configId];
    	
    			var param = $(res).children("Param");
    		
    			val = $(param).attr("Value");
    			attrs = $(res).children("Attr");
    			
    			if(attrs.attr('Setable')== '1'){
    				bSubmit = true;
    			}
                if(attrs == null) continue;
			
        		if(typeof attrs.attr("Type") == "undefined") continue;
        		if(attrs.attr("Type").toLowerCase() == "xml"){
        			html += _cfs.create_form_xml(resType,configId,attrs,res,resType);
        		}else{
        			html += _cfs.create_form_input_unit(resType,configId,attrs,val,param);
        		}
    		}
    	}
				
    	html += '<tr><td></td><td >';
    	if(bSubmit){
    		//if(_cfs.submitAllCates.search(cateId) > -1){
    		if(resType != "ST"){
	    		for(var i = 0;i < _cfs.type.length;i++){
	    			if(_cfs.type[i].type == resType )
		    		{
		    			var number = _cfs.type[i].number;
			    		if(number > 1){
			    			html += '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-save\'" onclick="_cfs.set_config_toallchannel(\''+cateId+'\',\''+resType+'\')">'+_lp.frame.configsets.btns.okall+'</a>&nbsp;';	
			    		}
			    	//	break;
		    		}
	    		}
    		}
    		html += '<a href="javascript:void(0)" data-options="iconCls:\'icon-save\'" class="easyui-linkbutton" onclick="_cfs.set_config(\''+cateId+'\',\''+resType+'\')">'+_lp.frame.configsets.btns.ok+'</a>&nbsp;';
    	}
    	html += '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\'" onclick="_cfs.refresh_resource_configsetsbycateid(\''+cateId+'\',\''+resType+'\',\''+idx+'\')">'+_lp.frame.configsets.btns.refresh+'</a>&nbsp;';
    	
    	html +='</td></tr>';
    	//data-options="iconCls:\'icon-reload\',plain:true" 
    	    	
    	// 判断此分类里是否有要处理的control命令    	
		return html;
	},
	
	// 创建某资源一个分类里panel的所有dom元素
	create_form_panel:function(n,resType,idx,cateId){
		var html = "";
		// 要判断cateId,有没有control元素要插入
		var controls = _cts.create_control_unit(resType,idx,cateId);
		
		// 没有子集,直接做form		
    	html += '<div style="height:10px;width:100%;"></div>';

    	if(controls[1] == "outtop"){
    		html += controls[0];
    	}
    
    	html += '<div class="easyui-panel" data-options="width:800" title="'+$(n).attr("Name")+'" style="padding:10px 1px 0px 1px "><form id="'+cateId.replace(".","_")+'_form">';
    	
    	if(controls[1] == "top"){
    		html += controls[0];
    	}
    	
    	html += '<table cellpadding="5">';
    	if(controls[1] == "inlinetop"){
    		html += controls[0];
    	}
    	html += _cfs.create_form_html(n,resType,idx,cateId);
    	
    	if(controls[1] == "inlinebottom"){
    		html += controls[0];
    	}
    	
    	html += '</table></form>';
    	
      	
    	if(controls[1] == "bottom"){
    		html += controls[0];
    	}
    	
    	html += '</div>';
    	return html;
	},
	
	// 创建一个分类配置项表单
	create_sub_form_panel:function(obj,resType,idx){
		
		var html = "",cnt = 0,m= 0;		
		$(obj).find("Category").each(function(k,n){
        	var cateId = $(n).attr("ID");
        	html += _cfs.create_form_panel(n,resType,idx,cateId);
        }); 
        return html;
	},
	
	// 提交资源配置
	set_config:function(cateId,resType){
		
		var stream = "";
		//alert($('#'+cateId.replace(".","_")+'_streamnum').length)
		if($('#'+cateId.replace(".","_")+'_streamnum').length >0){
			stream = $('#'+cateId.replace(".","_")+'_streamnum').combobox('getValue');
		}
		
		var formId = cateId.replace(".","_")+"_form";
		var values = new Array();
    	var inputs =$('#'+formId+' input[configid]');
    
        if ($('#'+formId).form("validate") == true) {
        	var inputs =$('#'+formId+' input[configid]');
        
        	for(var i =0;i < inputs.length;i++){ 		
        		if($(inputs[i]).attr("configid") == "F_ST_OEMData"){
	        		if($(inputs[i]).attr("setable") == 1){
        				values.push({
        					id:inputs[i].id,
    		        		params:[{name:'OEMData',childXML:$("#"+inputs[i].id).textbox("getValue"),attrs:[]}],
        					attrs:[]
        					//val:$("#"+inputs[i].id).textbox("getValue")
        				});
	        		}   
        		}else{  
	        		if($(inputs[i]).attr("setable") == 1){
	        			if($(inputs[i]).attr("type") == "checkbox"){
	        				values.push({
	        					id:inputs[i].id,
	        					stream:stream,
	        					attrs:[{name:'Value',val:($(inputs[i]).prop("checked")? 1:0)}]
	        				});
	        			}else if($(inputs[i]).attr("paramType") == "hashpsw"){
	        				var v = "";
	        				var configId = $(inputs[i]).attr("configid");
	    
	        				if($("#"+configId+"_psw_chk") && $("#"+configId+"_psw_chk").prop("checked") == true){
	        					var value = $("input",$("#"+configId+"_psw").next("span")).val();
	        				
	        					v = P_LY.Plug.nc.MD5Enc($("input",$("#"+configId+"_psw").next("span")).val());
	        				
	        				}else{
	        					v = $("#"+configId+"").val();
	        				}
	        			
	        				values.push({
	        					id:configId,stream:stream,
	        					attrs:[{name:'Value',val:v}]
	        				});
	        				
	        			}
	        			else if($(inputs[i]).attr("type") == "password"){
	        				var v = "";
	        				var configId = $(inputs[i]).attr("configid");
	        				if($("#"+configId+"_psw_chk") && $("#"+configId+"_psw_chk").prop("checked") == true){
	        					v = $("#"+configId+"").val();
	        				}else{
	        					v = $("#"+configId+"_psw").val();
	        				}
	        				values.push({
	        					id:configId,stream:stream,
	        					attrs:[{name:'Value',val:v}]
	        				});
	        			}else if($(inputs[i]).attr("paramType") == "schedule"){
	        				var weeklyXML = "",everydayXML = "";
	        				var mode = $('#'+inputs[i].id+"_schedule").combobox("getValue");
	        				var weekly = $('#'+inputs[i].id+"_schedule_weekly_dr").datagrid("getRows");
	        				var everyday = $('#'+inputs[i].id+"_schedule_everyday_dr").datagrid("getRows");
	        				for(var k = 0;k < weekly.length;k++){
	        					weeklyXML += '<Time Begin="'+weekly[k].beginTime+'" End="'+weekly[k].endTime+'" Weekday="'+weekly[k].weekday+'" />';
	        				}
	        				

	        				for(var k = 0;k < everyday.length;k++){
	        					everydayXML += '<Time Begin="'+everyday[k].beginTime+'" End="'+everyday[k].endTime+'" />';
	        				}
	        				values.push({
	        					id:inputs[i].id,stream:stream,
	    		        		params:[{name:'Everyday',childXML:everydayXML,attrs:[]},{name:'Weekly',childXML:weeklyXML,attrs:[]}],
	        					attrs:[{name:'Mode',val:mode}]
	        					//val:$("#"+inputs[i].id).textbox("getValue")
	        				});
	        				
	        				
	        			}else if($(inputs[i]).attr("paramType") == "utc"){
	        				var v = "";
	        				var configId = $(inputs[i]).attr("configid");
	        				v = $("#"+inputs[i].id).datetimebox("getValue");
	        				v = P_Utils.DTStrToTimestamp(v)/1000;
	        				values.push({
	        					id:inputs[i].id,stream:stream,
	        					attrs:[{name:'Value',val:v}]
	        					//val:$("#"+inputs[i].id).textbox("getValue")
	        				});
	        			}else if($(inputs[i]).attr("paramType") == "clock"){
	        				var v = "";
	        				var configId = $(inputs[i]).attr("configid");
	        				v = $("#"+inputs[i].id).timespinner("getValue");
	        				
	        				var t1 = P_Utils.DTStrToTimestamp("1970-01-01 "+v);
	        				var t2 = P_Utils.DTStrToTimestamp("1970-01-01 00:00:00");
	        				v = (t1.getTime()-t2.getTime())/1000;
	        				values.push({
	        					id:inputs[i].id,stream:stream,
	        					attrs:[{name:'Value',val:v}]
	        				});
	        			}else{
	        				
		        			if(inputs[i].id == "F_ST_ProxyAddr"){
		        				var val = $("#"+inputs[i].id).textbox("getValue");
			        			if(val.length > 0){
			        				$("#"+inputs[i].id).textbox({
										validType:'ipport'
									});
									var patn = /^([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\:]([0-9]{1,5})$/ ;
			           		 		var rv =  patn.test(val);
			           		 		if(rv != true) return false;	
			        			}
			        		
		        			}
		        		
	        				values.push({
	        					id:inputs[i].id,stream:stream,
	        					attrs:[{name:'Value',val:$("#"+inputs[i].id).textbox("getValue")}]
	        					//val:$("#"+inputs[i].id).textbox("getValue")
	        				});
	        			}
	        		}   
        			
        		}         	
        	}
        	var selects =$('#'+formId+' select[configid]');
      		
        	for(var i =0;i < selects.length;i++){
				values.push({
					id:selects[i].id,stream:stream,
					attrs:[{name:'Value',val:$("#"+selects[i].id).combobox("getValue")}]
				});
        	}
        }
	 	
	 		
		rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,resType,$('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue"),values);
		

		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return rv;
			}
			if(_p.isArray(rv.M.C.Res)){
				var msg = "";
				for(var i = 0;i < rv.M.C.Res.length;i++){
					var res = rv.M.C.Res[i];
					if(res.Error == "0"){
						
					}else{
						//msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
					}
				}
				if(msg == "") msg = _lp.frame.configsets.notes.note;
				
	            $.messager.show({
		            width:360,
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:msg,
	                timeout:4000,
	                showType:'slide'
	            });
	            
			}else{
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		            	width:360,
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note,
		                timeout:4000,
		                showType:'slide'
		            });
				}else{
		            $.messager.show({
		            	width:360,
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
				
			}
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError,
                timeout:4000,
                showType:'slide'
            });
    	}
        return rv;
        
	},
	
	// 提交资源的配置到所有通道
	set_config_toallchannel:function(cateId,resType){
		var formId = cateId.replace(".","_")+"_form";
		var values = new Array();
		var rows = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getData");
	
		var stream = "";

		if($('#'+cateId.replace(".","_")+'_streamnum').length >0){
			stream = $('#'+cateId.replace(".","_")+'_streamnum').combobox('getValue');
		}
        if ($('#'+formId).form("validate") == true) {
        	var inputs =$('#'+formId+' input[configid]');	        	
        	for(var i =0;i < inputs.length;i++){
        		if($(inputs[i]).attr("configid") == "F_ST_OEMData"){
	        		if($(inputs[i]).attr("setable") == 1){
	        			for(var k = 0;k < rows.length;k++){
	        				values.push({
	        					type:resType,
	        					stream:stream,	
	        					idx:rows[k].value,
	        					id:inputs[i].id,
	    		        		params:[{name:'OEMData',childXML:$("#"+inputs[i].id).textbox("getValue"),attrs:[]}],
	        					attrs:[]
	        				});	
	        			}
	        		}   
        		}else{ 
        		
	        		if($(inputs[i]).attr("setable") == 1){
	        			for(var k = 0;k < rows.length;k++){
	        				//console.log($(inputs[i]),$(inputs[i]).attr("type"))
		        			if($(inputs[i]).attr("type") == "checkbox"){
		        				console.log(1)
		        				values.push({
		        					type:resType,
		        					stream:stream,	
		        					idx:rows[k].value,
		        					id:inputs[i].id,
		        					attrs:[{name:'Value',val:($(inputs[i]).prop("checked")? 1:0)}]
		        				});
		        			}else if($(inputs[i]).attr("type") == "text"){
		        				values.push({
		        					type:resType,
		        					idx:rows[k].value,
		        					stream:stream,	
		        					id:inputs[i].id,
		        					attrs:[{name:'Value',val:$("#"+inputs[i].id).textbox("getValue")}]
		        				});
		        			}else if($(inputs[i]).attr("paramType") == "schedule"){
		        				var weeklyXML = "",everydayXML = "";
		        				var mode = $('#'+inputs[i].id+"_schedule").combobox("getValue");
		        				var weekly = $('#'+inputs[i].id+"_schedule_weekly_dr").datagrid("getRows");
		        				var everyday = $('#'+inputs[i].id+"_schedule_everyday_dr").datagrid("getRows");
		        				for(var j = 0;j < weekly.length;j++){
		        					weeklyXML += '<Time Begin="'+weekly[j].beginTime+'" End="'+weekly[j].endTime+'" Weekday="'+weekly[j].weekday+'" />';
		        				}
		        				
		        				for(var m = 0;m < everyday.length;m++){
		        					everydayXML += '<Time Begin="'+everyday[m].beginTime+'" End="'+everyday[m].endTime+'" />';
		        				}
		        				values.push({
		        					type:resType,
		        					stream:stream,	
		        					idx:rows[k].value,
		        					id:inputs[i].id,
		    		        		params:[{name:'Everyday',childXML:everydayXML,attrs:[]},{name:'Weekly',childXML:weeklyXML,attrs:[]}],
		        					attrs:[{name:'Mode',val:mode}]
		        				});
	        				}
		        		}   
	        		}	
        		}         	
        	}
        	var selects =$('#'+formId+' select[configid]');
        	for(var i =0;i < selects.length;i++){
    			for(var k = 0;k < rows.length;k++){
					values.push({
    					type:resType,
    					stream:stream,	
    					idx:rows[k].value,
						id:selects[i].id,
						attrs:[{name:'Value',val:$("#"+selects[i].id).combobox("getValue")}]
					});
        		}   
    			
        	}
        }
     
        rv = _p.set_res_configsex(_cfs.connectId,_cfs.pu.puid,resType,$('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue"),values);

		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return rv;
			}
			if(_p.isArray(rv.M.C.Res)){
				var msg = "";
				var refreshIds = Array();
				for(var i = 0;i < rv.M.C.Res.length;i++){
					var res = rv.M.C.Res[i];
					if(res.Error == "0"){
						// 同步一下刷新一下数据
						refreshIds.push(res.OptID);
					}else{
						//msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
					}
				}
				if(msg == "") msg = _lp.frame.configsets.notes.note;
				// 同步一下刷新一下数据
				_cfs.refresh_resource_configsetsbyid(true,refreshIds,null);
				
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:msg,
	                timeout:4000,
	                showType:'slide'
	            });
	            
			}else{
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note,
		                timeout:4000,
		                showType:'slide'
		            });
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
				
			}
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
                timeout:4000,
                showType:'slide'
            });
    	}
        return rv;
        
	},
	
	// 创建自解释类型表单元素
	create_form_input_unit:function(resType,configId,attrs,val,params){
		if(typeof attrs.attr("Type").toLowerCase() == "undefined") return;
		if(resType == "PTZ"){
			//	console.log(attrs)
		}
//		if(configId == "F_ST_SupportedActionSets"){
//			console.log(attrs,val,params)
//		}
		switch(attrs.attr("Type").toLowerCase()){
			case "string":
				return _cfs.create_form_textbox(resType,configId,attrs,val);
			break;
			case "hashpsw":
				return _cfs.create_form_hashpsw(resType,configId,attrs,val);
			break;
			case "psw":
				return _cfs.create_form_psw(resType,configId,attrs,val);
			break;
			case "int":
				return _cfs.create_form_int(resType,configId,attrs,val);
			break;
			case "bool":
				return _cfs.create_form_bool(resType,configId,attrs,val);
			break;
			case "enum":
				return _cfs.create_form_enum(resType,configId,attrs,val);
			break;
			case "utc":
				return _cfs.create_form_utc(resType,configId,attrs,val);
			break;
			case "clock":
				return _cfs.create_form_clock(resType,configId,attrs,val);
			break;
			case "list":
				return _cfs.create_form_list(resType,configId,attrs,val,params);
			break;
			case "schedule":
				return _cfs.create_form_schedule(resType,configId,attrs,val,params);
			break;
			case "region":
				return _cfs.create_form_region(resType,configId,attrs,val,params);
			break;
				default:
					break;
		}
		return "";
	},
	
	update_form_input_unit:function(resType,configId,attrs,val,params){
		if($('#'+configId)){
			if(typeof attrs.attr("Type") == "undefined") return;
			
			if(attrs.attr('Setable')== 0){
			}
			if(attrs.attr("Type").toLowerCase() == "list"){
				var res = $(params).parent();
				var v = "";
				var cnt = 0;
				$(res).find("it").each(function(k,n){
					var text = $(n).text();
					if($(n).attr('Desc') && $(n).attr('Desc').length > 0){
						text = $(n).attr('Desc');
					}
					v += text+'\r\n';
					cnt++;
				});
				$('#'+configId).textbox('setValue',v);		
			}else if(attrs.attr("Type").toLowerCase() == "string"){
				$('#'+configId).textbox('setValue',val);	
			}else if(attrs.attr("Type").toLowerCase() == "int"){
				if(attrs.attr('Setable')== 0){
					$('#'+configId).textbox('setValue',val);
				}else{
					$('#'+configId).numberspinner('setValue',val);
				}		
			}
		
		}
	},
	
	// 文本输入框
	create_form_textbox:function(resType,configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',minLen=0,maxLen=0,unit='',noteLen ='',note_remark='',note = "";
		
		if(attrs.attr('Unit')){
			note +='('+_lp.des.unit+':'+attrs.attr('Unit')+')';
		}
		
		
		// 看是否最小长度
		if(attrs.attr('MinLen')){
			minLen=attrs.attr('MinLen');
		}else{
			minLen = 0;
		}
		
		
		if(minLen <=0){
			dataOptions += 'required:false';// 没有,设置为可以为空
		}else{
			dataOptions += 'required:true';// 有,设置为必填项
		}
		
		// 取出最在长度,设置输入长度范围
		if(attrs.attr('MaxLen')){
			vLen = 'length['+minLen+','+attrs.attr('MaxLen')+']';
			maxLen = attrs.attr('MaxLen');
		}
		
		if(vLen){
			dataOptions += ",validType:'"+vLen+"'";
			noteLen = _lp.des.length+':'+minLen+'～'+maxLen+ _lp.des.character;
		}
		
		if(attrs.attr('Setable')== 0){
			dataOptions += ",disabled:true";
		}else{
			if(noteLen!= ""){
				note += '('+noteLen+')';
			}
		}

		if(typeof val == "undefined") val = "";
		if(attrs.attr('desc')){
			val = attrs.attr('desc');
		}
		
		html += '<td>';
		html += '<input id="'+configId+'" maxlength="'+maxLen+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-textbox" type="text" value="'+val+'"  data-options="'+dataOptions+'"></input>';
		if(attrs.attr("Refresh") == 1){
			html += '&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" onclick="_cfs.refresh_resource_configset(\''+resType+'\',[\''+configId+'\'])" style="width:30px" title="'+_lp.frame.configsets.btns.refresh+'"></a>';
		}
		
		if(attrs.attr('Remark')){
			note_remark = attrs.attr('Remark');
			//html += '&nbsp;&nbsp;('+attrs.attr('Remark')+')';
		}
		
		if(note_remark != ""){
			note += '('+note_remark+')';
		}
		
		if(note != ""){
			html += '<span id="'+configId+'_node">';
				html += "&nbsp;&nbsp;"+note;
			html += "</span>";
		}
		
		html += '</td>';
		//html += '<td>'+attrs.attr('Remark')+'</td>';
		
		html += '</tr>';
		return html;
	},
	
	// 整形数字
	create_form_int:function(resType,configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',min=0,max=0,unit='',noteLen ='',note_remark='',note = "";
		
		if(attrs.attr('Unit')){
			note +='('+_lp.des.unit+':'+attrs.attr('Unit')+')';
		}
		
		// 看是否'+_lp.des.min+'
		if(attrs.attr('Min')){
			min=attrs.attr('Min');
			dataOptions += split+"min:"+min;
			split = ","
		}

		if(attrs.attr('Max')){
			max=attrs.attr('Max');
			dataOptions += split+"max:"+max;
			split = ","
		}
		
		if(attrs.attr('Setable')== 0){
			dataOptions += split+"editable:false,disabled:true";
			split = ","
		}else{
			if(min != '' && max != ''){
				note += '('+min+'～'+max+')';
			}else if(min != ''){
				note += '('+_lp.des.min+':'+min+')';
			}else if(max != ''){
				note += '('+_lp.des.max+':'+max+')';
			}
		}

		if(typeof val == "undefined") val = "";
		// 如果是只读的，就用textbox
		if(attrs.attr('desc')){
			val = attrs.attr('desc');
		}
		html += '<td>';
		if(attrs.attr('Setable')== 0){
			html += '<input id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'"  class="easyui-textbox" type="text" value="'+val+'" data-options="'+dataOptions+'"></input>';
		}else{
			html += '<input id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'"  class="easyui-numberspinner" type="text" value="'+val+'" data-options="'+dataOptions+'"></input>';
		}
		
		if(attrs.attr("Refresh") == 1){
			html += ' <a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" onclick="_cfs.refresh_resource_configset(\''+resType+'\',[\''+configId+'\'])" style="width:30px" title="'+_lp.frame.configsets.btns.refresh+'"></a>';
		}
		
		if(attrs.attr('Remark')){
			note_remark = attrs.attr('Remark');
		}
		
		
		if(note_remark != ""){
			note += '('+note_remark+')';
		}
		
		if(note != ""){
			html += '&nbsp;&nbsp;'+note;
		}
		
		html += '</td>';
		
		html += '</tr>';
		return html;
		
	},
	
	// bool型
	create_form_bool:function(resType,configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
	
		if(typeof val == "undefined") val = "";
		if(attrs.attr('desc')){
			val = attrs.attr('desc');
		}
		//<input id="ck-rtl" type="checkbox" onclick="open2()">
		html += '<td>';
		html += '<input id="'+configId+'" configid="'+configId+'" '+(attrs.attr('Setable')== 0 ? 'disabled' : '')+' setable="'+attrs.attr('Setable')+'" '+(val == 1 ? "checked":"")+' type="checkbox" ></input>';
		
		if(attrs.attr('Remark')){
			html += '&nbsp;&nbsp;('+attrs.attr('Remark')+')';
		}
		html += '</td>';
		html += '</tr>';
		return html;
		
	},
	
	// combobox
	create_form_enum:function(resType,configId,attrs,val){
	
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',min=0,max=0;
		
		// 看是否'+_lp.des.min+'
		if(attrs.attr('Min')){
			min=attrs.attr('Min');
			dataOptions += split+"min:"+min;
			split = ","
		}

		if(attrs.attr('Max')){
			max=attrs.attr('Max');
			dataOptions += split+"max:"+max;
			split = ","
		}   
		if(attrs.attr('Setable')== 0){
			dataOptions += split+"editable:false";
			split = ","
		}
		
		if(typeof val == "undefined") val = "";
		if(attrs.attr('Desc')){
			val = attrs.attr('Desc');
		}
		dataOptions += ",panelHeight:'auto'";
	
		html += '<td>';
		html += '<select  id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-combobox" data-options="panelHeight:\'auto\',editable:false"  style="width:173px;height:auto;">';

		$(attrs).find("it").each(function(k,n){
			var text = $(n).text();
			if($(n).attr('Desc') && $(n).attr('Desc').length > 0){
				text = $(n).attr('Desc');
			}
			html += '<option value="'+$(n).text()+'" '+(val ==$(n).text() ? "selected" : "")+'>'+text+'</option>';
		
		});
		html += '</select>';

		if(attrs.attr('Remark')){
			html += '&nbsp;&nbsp;('+attrs.attr('Remark')+')';
		}
		html += '</td></tr>';
	
		return html;
	},
	
	// 密码框
	create_form_psw:function(resType,configId,attrs,val){
		
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',minLen=0,maxLen=0;
		
		// 看是否最小长度
		if(attrs.attr('MinLen')){
			minLen=attrs.attr('MinLen');
		}        			
		if(minLen <=0){
			dataOptions += 'required:false';// 没有,设置为可以为空
		}else{
			dataOptions += 'required:true';// 有,设置为必填项
		}
		
		// 取出最在长度,设置输入长度范围
		if(attrs.attr('MaxLen')){
			vLen = 'length['+minLen+','+attrs.attr('MaxLen')+']';
		}
		
		if(vLen){
			dataOptions += ",validType:'"+vLen+"'";
		}
		var disabled = "";
		if(attrs.attr('Setable')== 0){
			//dataOptions += ",editable:false";
			// password框禁用需要用disabled
			disabled = "disabled";
		}
		html += '<td>';
		html += '<input id="'+configId+'_psw" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" type="password" value="'+val+'" class="easyui-validatebox textbox" '+disabled+' data-options="'+dataOptions+'" style="height:21px;" ></input><input id="'+configId+'" type="text" class="easyui-validatebox textbox" '+disabled+' data-options="'+dataOptions+'" style="height:21px;display:none;"></input>&nbsp;&nbsp;<input type="checkbox" style="vertical-align:middle" id="'+configId+'_psw_chk" onclick="_cfs.psw_chk_onclick(\''+configId+'\')" ></input><label for="'+configId+'_psw_chk">'+_lp.des.password+'<label>';
		
		if(attrs.attr('Remark')){
			html += '&nbsp;&nbsp;('+attrs.attr('Remark')+')';
		}
		
		html += '</td>';
		html += '</tr>';
		return html;
	},
	
	// 密码元素处理
	psw_chk_onclick:function(id){
		if($("#"+id+"_psw_chk")){
			if($("#"+id+"_psw_chk").prop("checked") == true){
				$("#"+id+"").val($("#"+id+"_psw").val());
				$("#"+id+"").show();
				$("#"+id+"_psw").hide();
			}else{
				$("#"+id+"_psw").val($("#"+id+"").val());
				$("#"+id+"").hide();
				$("#"+id+"_psw").show();
			}
		}
	},
		
	hash_psw_chk_onclick:function(id){	
		if($("#"+id+"_psw_chk")){
			if($("#"+id+"_psw_chk").prop("checked") == true){
				$("input",$("#"+id+"_psw").next("span")).removeAttr("disabled");
			}else{
				$("input",$("#"+id+"_psw").next("span")).attr("disabled","disabled")
			}
		}
	},	
	
	// 创建hash密码元素
	create_form_hashpsw:function(resType,configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',minLen=0,maxLen=0;
		
		// 看是否最小长度
		if(attrs.attr('MinLen')){
			minLen=attrs.attr('MinLen');
		}        			
		if(minLen <=0){
			dataOptions += 'required:false';// 没有,设置为可以为空
		}else{
			dataOptions += 'required:true';// 有,设置为必填项
		}
		
		// 取出最在长度,设置输入长度范围
		if(attrs.attr('MaxLen')){
			vLen = 'length['+minLen+','+attrs.attr('MaxLen')+']';
		}
		
		if(vLen){
			dataOptions += ",validType:'"+vLen+"'";
		}
		var disabled = "";
		if(attrs.attr('Setable')== 0){
			//dataOptions += ",editable:false";
			// password框禁用需要用disabled
			disabled = "disabled";
		}
		html += '<td><input id="'+configId+'_psw" configid="'+configId+'" type="password" paramType="hashpsw"  class="easyui-textbox" disabled setable="'+attrs.attr('Setable')+'" data-options="'+dataOptions+'" style="height:21px;" ></input><input id="'+configId+'" type="text" value="'+val+'" class="easyui-validatebox textbox" '+disabled+' data-options="'+dataOptions+'" style="height:21px;display:none;"></input>&nbsp;&nbsp;<input type="checkbox" style="vertical-align:middle" id="'+configId+'_psw_chk" onclick="_cfs.hash_psw_chk_onclick(\''+configId+'\')" ></input><label for="'+configId+'_psw_chk">'+_lp.des.m_password+'<label></td>';
	
		html += '</tr>';
		return html;
	},
	
	// utc时间元素
	create_form_utc:function(resType,configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		
		var dataOptions='',unit='',note_remark='',note='';
		
		
		if(attrs.attr('Unit')){
			note +='('+_lp.des.unit+':'+attrs.attr('Unit')+')';
		}
		
		
		var t = P_Utils.DateFormat(null,new Date(val*1000));
		
		html += '<td>';
		if(attrs.attr('Setable')== 0){
			html += t;
		}else{
			html += '<input  id="'+configId+'" configid="'+configId+'" paramType="utc" setable="'+attrs.attr('Setable')+'"  class="easyui-datetimebox" data-options="required:true,showSeconds:true" value="'+t+'">';
		}
		
		if(attrs.attr('Remark')){
			note_remark = attrs.attr('Remark');
		}
		
		if(note_remark != ""){
			note += '('+note_remark+')';
		}
		
		if(note != ""){
			html += "&nbsp;&nbsp;"+note;
		}
		
		html += '</td>';
		html += '</tr>';
		return html;
	},
	
	// 时钟
	create_form_clock:function(resType,configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',unit='',note_remark='',note='';		
		var disabled = "";
		
		if(attrs.attr('Unit')){
			note +='('+_lp.des.unit+':'+attrs.attr('Unit')+')';
		}
		
		if(attrs.attr('Setable')== 0){
			//dataOptions += ",editable:false";
			// password框禁用需要用disabled
			disabled = "disabled";
		}
		var t = P_Utils.DTStrToTimestamp("1970-01-01 00:00:00");
		t.setTime(t.getTime()+val*1000);
		var v = P_Utils.DateFormat("HH:mm:ss",t)
		html += '<td>';
		html += '<input  id="'+configId+'" configid="'+configId+'" paramType="clock"  setable="'+attrs.attr('Setable')+'" class="easyui-timespinner" data-options="required:true,showSeconds:true" value="'+v+'">';
		
		if(attrs.attr('Remark')){
			note_remark = attrs.attr('Remark');
		}
		
		if(note_remark != ""){
			note += '('+note_remark+')';
		}
		
		if(note != ""){
			html += "&nbsp;&nbsp;"+note;
		}
		
		html += '</td>';
		html += '</tr>';
		return html;
	},
	
	// list 元素
	create_form_list:function(resType,configId,attrs,val,params){
	
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='';
		
		
		var disabled = "";
//		if(attrs.attr('Setable')== 0){
//			//dataOptions += ",editable:false";
//			// password框禁用需要用disabled
//			disabled = "disabled";
//	}
		html += '<td>';
		
		var res = $(params).parent();
	
	//	console.log($(res).find("it"))	
		var v = " ";
		var cnt = 0;
		$(res).find("it").each(function(k,n){
			var text = $(n).text();
			if($(n).attr('Desc') && $(n).attr('Desc').length > 0){
				text = $(n).attr('Desc');
			}
			v += text+'&#13 ';
			cnt++;
		});
	//	v += "</td>";
		//html += '<input  id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-textbox"  data-options="multiline:true,readonly:true" value="'+ v +'" '+(cnt <=0 ? 'style="height:23px;"' : '')+'>';
		html += '<textarea  id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-textbox"  data-options="multiline:true,disabled:true" '+(cnt <=0 ? 'style="height:23px;word-break:break-all;word-wrap: break-word"' : 'style="height:50px;word-break:break-all;word-wrap: break-word"' )+' >'+v+'</textarea>';
		
		if(attrs.attr("Refresh") == 1){
			html += ' <a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-reload\',plain:true" onclick="_cfs.refresh_resource_configset(\''+resType+'\',[\''+configId+'\'])" style="width:30px" title="'+_lp.frame.configsets.btns.refresh+'"></a>';
		}
		
        	
		if(attrs.attr('Remark')){
			html += '&nbsp;&nbsp;('+attrs.attr('Remark')+')';
		}

//		$(res).find("it").each(function(k,n){
//			html += '<li>'+$(n).text()+'</li>';
//		});
//      html += '</ul>';	
	
		html += '</td>';
		//html += '<td><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="_cfs.refresh_resource_configset(\''+resType+'\',[\''+configId+'\'])">'+_lp.frame.configsets.btns.refresh+'</a></td>'
		html += '</tr>';
		return html;
	},
	
	// 创建schedule
	create_form_schedule:function(resType,configId,attrs,val,params){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',minLen=0,maxLen=0;
		
		// 看是否最小长度
		if(attrs.attr('MinLen')){
			minLen=attrs.attr('MinLen');
		}
		
		if(minLen <=0){
			dataOptions += 'required:false';// 没有,设置为可以为空
		}else{
			dataOptions += 'required:true';// 有,设置为必填项
		}
		
		// 取出最在长度,设置输入长度范围
		if(attrs.attr('MaxLen')){
			vLen = 'length['+minLen+','+attrs.attr('MaxLen')+']';
		}
		
		if(vLen){
			dataOptions += ",validType:'"+vLen+"'";
		}
		var disabled = "";
		if(attrs.attr('Setable')== 0){
			//dataOptions += ",editable:false";
			// password框禁用需要用disabled
			disabled = "disabled";
		}
		var mode = params.attr('Mode');
		//var nodes = $(params).parent()[0].childNodes,weektime="",everydaytime="";
		
		var res = $(params).parent();
		
		var nodes = res[0].childNodes,weektime="",everydaytime="";
		if(nodes){
			if (!$.support.leadingWhitespace) {				
				nodes = $(nodes)[0].childNodes;
				for(var i = 0;i < nodes.length;i++){
					if(nodes[i].nodeName.toLowerCase() == "everyday"){
						//var times = $(nodes[i]).children("Time");
						
						$(nodes[i]).children("Time").each(function(kk,nn){
							everydaytime += '<tr><td>'+$(nn).attr("Begin")+'</td><td>'+$(nn).attr("End")+'</td><td></td></tr>';	
						});
						//everydaytime += '<tr><td>'+$(nn).attr("Begin")+'</td><td>'+$(nn).attr("End")+'</td><td></td></tr>';	
						
					}else if(nodes[i].nodeName.toLowerCase() == "weekly"){
						$(nodes[i]).children("Time").each(function(kk,nn){
							weektime += '<tr><td>'+$(nn).attr("Begin")+'</td><td>'+$(nn).attr("End")+'</td><td>'+$(nn).attr("Weekday")+'</td></tr>';	
						});	
						
					}
				}
			}
			else{
				
				$(nodes).each(function(k,n){
					if($(n)[0].nodeName.toLowerCase() == "everyday"){
						$(n).find("time").each(function(kk,nn){
							everydaytime += '<tr><td>'+$(nn).attr("Begin")+'</td><td>'+$(nn).attr("End")+'</td><td></td></tr>';	
						});
											
					}else if($(n)[0].nodeName.toLowerCase() == "weekly"){
						$(n).find("time").each(function(kk,nn){
							weektime += '<tr><td>'+$(nn).attr("Begin")+'</td><td>'+$(nn).attr("End")+'</td><td>'+$(nn).attr("Weekday")+'</td></tr>';	
						});
					}
					
				});
			}
		}
		
		html += '<td><input type=hidden id="'+configId+'" configid="'+configId+'" paramType="schedule" setable="'+attrs.attr('Setable')+'" /><select id="'+configId+'_schedule" class="easyui-combobox" data-options="panelHeight:\'auto\',editable:false,onSelect: function(rec){_cfs.schedule_select(\''+configId+'\',rec);}"  style="width:173px;height:auto;" >';
		html += '<option value="None" '+(mode == "None" ? "selected" : "")+'>'+_lp.des.none+'</option>';
		html += '<option value="Always" '+(mode == "Always" ?"selected":"")+'>'+_lp.des.always+'</option>';
		html += '<option value="Everyday" '+(mode == "Everyday" ?"selected":"")+'>'+_lp.des.everyday+'</option>';
		html += '<option value="Weekly" '+(mode == "Weekly" ? "selected":"")+'>'+_lp.des.weekly+'</option>';
		html += '</select>';
		html += '</td>';
		html += '</tr>';
		
		html += '<tr id="'+configId+'_schedule_weekly_tr" style="display:;">';
		html += '<td>&nbsp;&nbsp;</td>';
		
		html += '<td><table id="'+configId+'_schedule_weekly_dr" class="easyui-datagrid" data-options="toolbar:[{iconCls:\'icon-add\',handler: function(){_cfs.add_schedule(\''+configId+'\',\'weekly\');}},{iconCls:\'icon-remove\',handler: function(){_cfs.del_schedule(\''+configId+'\',\'weekly\');}}]" style="width:400px;">';
		html += '<thead><tr><th data-options="field:\'beginTime\',width:120">'+_lp.des.start+'</th><th data-options="field:\'endTime\',width:120">'+_lp.des.end+'</th><th data-options="field:\'weekday\',width:120">'+_lp.des.week+'</th></tr></thead>';
		html += weektime;
		
		html += '</table>';
		
		html += '</td>';
		html += '</tr>';	
		
		html += '<tr id="'+configId+'_schedule_everyday_tr" style="display:;">';
		html += '<td>&nbsp;&nbsp;</td>';
		
		html += '<td><table id="'+configId+'_schedule_everyday_dr" class="easyui-datagrid" data-options="toolbar:[{iconCls:\'icon-add\',handler: function(){_cfs.add_schedule(\''+configId+'\',\'everyday\');}},{iconCls:\'icon-remove\',handler: function(){_cfs.del_schedule(\''+configId+'\',\'everyday\');}}]"  style="width:400px;">';
		html += '<thead><tr><th data-options="field:\'beginTime\',width:120">'+_lp.des.start+'</th><th data-options="field:\'endTime\',width:120">'+_lp.des.end+'</th></tr></thead>';
		html += everydaytime;
		html += '</table>';
		
		html += '</td>';
		html += '</tr>';
			
//		html += '<table>';
//		html += '<tr><td></td></tr>';
//		html += '</table>';
	
		return html;
		
	},
	
	// schedule选择事件处理
	schedule_select:function(configId,rec){
		
		if(!$('#'+configId+'_schedule_everyday_dr').length > 0 || !$('#'+configId+'_schedule_weekly_dr').length > 0){
			return;
		}
		var mode = "";
		if(rec && typeof rec.value != "undefined"){
			mode = rec.value;
		}else{
			return;
		}
		
		if(mode == "None" || mode == "Always"){
			$('#'+configId+'_schedule_weekly_tr').hide();
			$('#'+configId+'_schedule_everyday_tr').hide();
		}else if(mode == "Weekly"){

			$('#'+configId+'_schedule_weekly_tr').show();
			$('#'+configId+'_schedule_everyday_tr').hide();
		}else if(mode == "Everyday"){

			$('#'+configId+'_schedule_weekly_tr').hide();
			$('#'+configId+'_schedule_everyday_tr').show();
		}
	},
	
	// 添加一个schedule项
	add_schedule:function(configId,type){
		var grid = '#'+configId;
		if(type == "weekly"){
			grid = grid + '_schedule_weekly_dr';
		}else if(type == "everyday"){
			grid = grid + '_schedule_everyday_dr';
		}
		var dlgId = configId+'_edit_schedule_dlg';
		var jDlgId = '#'+dlgId;
		if($(jDlgId).length > 0){
			$('#'+configId+'_edit_schedule_form_type').val(type);
			if(type == "weekly") $('#'+configId+'_edit_schedule_form_weekly_tr').show();
			else $('#'+configId+'_edit_schedule_form_weekly_tr').hide();
		}else{
			var weekday = new Date().getDay().toString();

			var weeks = ['Sun','Mon','Tue','Wed','Thr','Fri','Sat'];
			
			$("body").append("<div id='"+dlgId+"'></div>");
			var html = '';
			html += '<form id="'+configId+'_edit_schedule_form" ><input type="hidden" id="'+configId+'_edit_schedule_form_type" value="'+type+'" />';
			html += '<table>';
			html += '';
			html += '<tr><td>'+_lp.des.start+':</td><td><input id="'+configId+'_edit_schedule_form_beginTime" class="easyui-timespinner" required="required" value="00:00:00" data-options="min:\'00:00\',showSeconds:true" /></td></tr>';
			html += '<tr><td>'+_lp.des.end+':</td><td><input id="'+configId+'_edit_schedule_form_endTime" class="easyui-timespinner" required="required" value="23:59:00" data-options="max:\'23:59\',showSeconds:true" /></td></tr>';
			html += '<tr id="'+configId+'_edit_schedule_form_weekly_tr" style="display:'+(type == "weekly" ? '' : 'none')+';"><td>'+_lp.des.week+':</td><td><select id="'+configId+'_edit_schedule_form_weekday" class="easyui-combobox" style="width:173px;" >';
			for(var i = 0;i < 7;i++){
				html += '<option value='+i+' '+(i== weekday ? 'selected' : '')+' >'+_lp.week[weeks[i]]+'</option>'
			}
			html += '</select></td></tr>';
			html += '';
			html += '</table>';
			html += '</form>';
			$(jDlgId).dialog({
			    title: _lp.frame.configsets.setSchedule,
			    width: 290,
			    height: 180,
			    closed: false,
			    cache: false,
			    content:html,
			    buttons:[{
					text:_lp.frame.configsets.btns.ok,
					handler:function(){
						if($('#'+configId+'_edit_schedule_form').form("validate") == true){
							var type = $('#'+configId+'_edit_schedule_form_type').val();
							var grid = '#'+configId;
							if(type == "weekly"){
								grid = grid + '_schedule_weekly_dr';
							}else if(type == "everyday"){
								grid = grid + '_schedule_everyday_dr';
							}
							var data = $(grid).datagrid('getRows');
							var b = $('#'+configId+'_edit_schedule_form_beginTime').timespinner('getValue');
							var e = $('#'+configId+'_edit_schedule_form_endTime').timespinner('getValue');
							
							var i = (Date.parse("1970-01-01 "+e)-Date.parse("1970-01-01 "+b))/3600000;
							if(i <0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.des.start+_lp.des.note1+_lp.des.end,
					                timeout:4000,
					                showType:'slide'
					            });
								return;
							}
							data.push({beginTime:$('#'+configId+'_edit_schedule_form_beginTime').timespinner('getValue'),endTime:$('#'+configId+'_edit_schedule_form_endTime').timespinner('getValue'),weekday:$('#'+configId+'_edit_schedule_form_weekday').combobox('getValue')});
							
							$(grid).datagrid('loadData',data);
							$(jDlgId).dialog('close');
						}
					}
				},{
					text:_lp.frame.configsets.btns.close,
					handler:function(){
						$(jDlgId).dialog('close');
					}
				}],
			    modal: true
			});
		}
		$(jDlgId).dialog('open');
	},
	
	// 删除一个schedule项
	del_schedule:function(configId,type){
		var grid = '#'+configId;
		if(type == "weekly"){
			grid = grid + '_schedule_weekly_dr';
		}else if(type == "everyday"){
			grid = grid + '_schedule_everyday_dr';
		}
		
		if($(grid)){
			var allrows = $(grid).datagrid("getRows");
			var rows = $(grid).datagrid("getSelections");
			var delRowIds = "";
			for(var j = 0;j < rows.length;j++){
				var rowId = $(grid).datagrid('getRowIndex',rows[j]);
				delRowIds += rowId+",";
			}
			var newrows = new Array();
			for(var i = 0;i < allrows.length;i++){
				var rowId = $(grid).datagrid('getRowIndex',allrows[i]);
				if(delRowIds.search(rowId)<0){
					newrows.push(allrows[i]);
				}
			}
			$(grid).datagrid('loadData',newrows);
		}
	},
	
	create_form_region:function(resType,configId,attrs,val,params){
		html = "";
		if(_cf.type != "embed") return html;
		
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		var dataOptions='',validType = '',split = '',vLen='',minLen=0,maxLen=0;
		
		// 看是否最小长度
		if(attrs.attr('MinLen')){
			minLen=attrs.attr('MinLen');
		}
		
		if(minLen <=0){
			dataOptions += 'required:false';// 没有,设置为可以为空
		}else{
			dataOptions += 'required:true';// 有,设置为必填项
		}
		
		// 取出最在长度,设置输入长度范围
		if(attrs.attr('MaxLen')){
			vLen = 'length['+minLen+','+attrs.attr('MaxLen')+']';
		}
		
		if(vLen){
			dataOptions += ",validType:'"+vLen+"'";
		}
		var disabled = "";
		if(attrs.attr('Setable')== 0){
			//dataOptions += ",editable:false";
			// password框禁用需要用disabled
			disabled = "disabled";
		}
		
		html += '<td>';
		html += '<table><tr>';
		html += '<td><div id="'+configId+'" class=\"windowbox\" style="width:500px;height:280px;background-color:#000"></div></td>';
		html += '<td><table>';
		html += '<tr><td><a id="'+configId+'_start_btn" iconCls="icon-start" class="easyui-linkbutton" href="javascript:void(0)" onclick="_cfs.draw.start(\''+configId+'\',\''+resType+'\')">'+_lp.des.note2+'</a><a iconCls="icon-stop" id="'+configId+'_stop_btn" class="easyui-linkbutton" href="javascript:void(0)" onclick="_cfs.draw.stop(\''+configId+'\')" style="display:none;">'+_lp.des.note3+'</a></td></tr>';
		
		html += '<tr><td><a id="'+configId+'_remove_btn" class="easyui-linkbutton" iconCls="icon-remove" href="javascript:void(0)" onclick="_cfs.draw.del_rect(\''+configId+'\')"  style="display:none;">'+_lp.des.note4+'</a></td></tr>';
		html += '<tr><td><a id="'+configId+'_clear_btn" class="easyui-linkbutton" iconCls="icon-cut" href="javascript:void(0)" onclick="_cfs.draw.clear(\''+configId+'\')"  style="display:none;">'+_lp.des.note5+'</a></td></tr>';
		html += '<tr><td><a id="'+configId+'_submit_btn"class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-save" onclick="_cfs.draw.set_all_rects(\''+configId+'\',\''+resType+'\')"  style="display:none;">'+_lp.des.submit+'</a></td></tr>';
		
		html += '</table></td>';
		html += '</tr></table>';
		html += '</td>';
		html += '</tr>';
		html += '<tr><td></td><td></td></tr>';
		
//                          "<div id=\"wnd" + j + "\" >",
//                          "",
//                          "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
//                          "</div>"
                            
		return html;
		
		/* 原来的*/
		//<div id="test_drag" style="position:absolute;background-color:#cfcfcf;cursor:pointer;width:1px;height:1px;top:1px;left:1px;border:1px solid #c0c0c0;z-index:1000;"></div>
		html += '<td>';
		html += '<table><tr>';
		html += '<td><div id="'+configId+'" style="width:360px;height:300px;border:1px solid #cecece;position:relative;"></div></td>';
		html += '<td><a class="easyui-linkbutton" href="javascript:void(0)" onclick="_cfs.start_draw(\''+configId+'\')">'+_lp.des.note2+'</a><br/><a  class="easyui-linkbutton" href="javascript:void(0)" onclick="_cfs.stop_draw(\''+configId+'\')">'+_lp.des.note3+'</a><br/><a  class="easyui-linkbutton" href="javascript:void(0)" onclick="">'+_lp.des.note4+'</a></td>';
		html += '</tr></table>';
		html += '</td>';
		html += '</tr>';
		html += '<tr><td></td><td></td></tr>';
		
		var rv = _p.get_control(_cfs.connectId,_cfs.pu.puid,"IV",0,'C_IV_GetThumbnail','');
		
		return html;
	},
	draw:{
		status:false,
		draws:new Array(),
		start:function(configId,resType){
			
			var wndId = configId;
			$('#'+wndId+'_start_btn').hide();
			$('#'+wndId+'_stop_btn').show();
			$('#'+wndId+'_remove_btn').show();
			$('#'+wndId+'_clear_btn').show();
			$('#'+wndId+'_submit_btn').show();
			// 初始化一下窗口
			var wnd = _p.get_wnd_container(wndId,'wnd');
			
			if(!wnd || wnd == null){
        		_p.set_wnd_conatiner(configId, 'wnd');
        		wnd = _p.get_wnd_container(wndId,'wnd');
			}else{
				// 如果已经存在，关闭一下，重新开
				//_p.stop_video(wndId);
			}
			// 开始启动视频流
			var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox('getValue');
			
    		// 初始化一个视频窗口 F_IV_CoverRegions
    		var wnd_event = {
    			lbtn_click:true,
    			select_rect:true,
    			eventCallback:function(en,wndId){
    				
    				if(en == "select_rect"){
			        	var x1=0,y1=0,x2=0,y2=0;
			        	x1 = arguments[3];
			        	y1 = arguments[4];
			        	x2 = arguments[5];
			        	y2 = arguments[6];
			        	P_LY.MaskSelectRect(wnd.window,x1,y1,x2,y2);
    				}else if(en == "lbtn_click"){
    					var x=0,y=0;
			        	x = arguments[3];
			        	y = arguments[4];
	        			P_LY.MaskClick(wnd.window,x,y); 
    				}
    			}
    		};

    		var rv = _p.play_video(_cfs.connectId, _cfs.pu.puid, idx, wndId, wnd_event,0);   
    		try{
	        	P_LY.EnableMask(wnd.window,1);    		
	            P_LY.WindowControlMode.set(wnd.window,1);
    		}catch(e){
    			//alert(e.message)
    		}
    		_cfs.draw.draws.push(wndId);
    		
    		// 获取原有的叠加区域
			var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox('getValue');
    		rv = _f.load_xml(_p.get_res_config(_cfs.connectId,_cfs.pu.puid,resType,idx,configId));
			var regions = $(rv).find("Region");
			var rects = new Array();
			for(var i = 0;i < regions.length;i++){
				
				var r = $(regions[i]);
				rects.push({left:r.attr('left'),top:r.attr('top'),right:r.attr('right'),bottom:r.attr('bottom')});
				//P_LY.AddRect(_stream, l, t,r,b);
			}
        	P_LY.ClearMask(wnd.window);
			if(rects.length > 0){
				P_LY.SetMask(wnd.window, rects);	
			}
		},
		stop:function(configId){
			var wndId = configId;
			$('#'+wndId+'_start_btn').show();
			$('#'+wndId+'_stop_btn').hide();
			$('#'+wndId+'_remove_btn').hide();
			$('#'+wndId+'_clear_btn').hide();
			$('#'+wndId+'_submit_btn').hide();
			var wnd = _p.get_wnd_container(wndId,'wnd');
			if(wnd){
	            P_LY.WindowControlMode.set(wnd.window,0)
	        	P_LY.EnableMask(wnd.window,0);
				_p.stop_video(wndId);
			}
		},
		close_all:function(){
			for(var i = 0; i < _cfs.draw.draws.length;i++){
				_cfs.draw.stop(_cfs.draw.draws[i]);
				_p.remove_wnd_conatiner(_cfs.draw.draws[i]);
			}
			_cfs.draw.draws = new Array();
//			_cfs.draw.draws = $.grep(_cfs.draw.draws,function(n,i){
//				return n == wndId
//			},ture);
		},
//		clear_all:function(){
//			_cfs.draw.close_all();
//			// 清空window
//			_p.remove_wnd_conatiner();
//		},
		del_rect:function(configId){			
			var wndId = configId;
			var wnd = _p.get_wnd_container(wndId,'wnd');    		
            if(wnd){            	
        		P_LY.RemoveSelectRect(wnd.window);
            }
		},
		clear:function(configId){			
			var wndId = configId;
			var wnd = _p.get_wnd_container(wndId,'wnd');    		
            if(wnd){
        		P_LY.ClearMask(wnd.window);
            }			
		},
		set_all_rects:function(configId,resType){			
			var wndId = configId;
			var wnd = _p.get_wnd_container(wndId,'wnd');    		
            if(wnd){            	
        		var rv = P_LY.GetMask(wnd.window);
        		//var jsObj = v.response+")");
        		var recs = rv.response;
        		
        		
				var params = new Array();
				for(var i = 0;i < recs.length;i++){
					var r = recs[i];
					params.push({name:'Region',attrs:[{name:'Left',val:r.left},{name:'Top',val:r.top},{name:'Right',val:r.right},{name:'Bottom',val:r.bottom}]});
				}
				var values = new Array();
				values.push({
					id:configId,
	        		params:params,
					attrs:[]
				});
				
				var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox('getValue');
	
		        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"IV",idx,values);
		        
				if(rv && rv.M && rv.M.C){
					if(rv.M.C.SPError != 0){
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
			                timeout:4000,
			                showType:'slide'
			            });
			            return;
					}
					if(rv.M.C.Res){
						if(rv.M.C.Res.Error == "0"){
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.note,
				                timeout:4000,
				                showType:'slide'
				            });
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
				                timeout:4000,
				                showType:'slide'
				            });
						}
					}else{
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
			                timeout:4000,
			                showType:'slide'
			            });
			    	}
					
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError,
		                timeout:4000,
		                showType:'slide'
		            });
		    	}
				
            }
		}
	},
	//draw_flag:false,
	//draw_start_point:null,
	/* 遮挡 实现未完成，先这样
	draw :{
		status:false,	
		moveing:false,
		begin_point:null,
		offset:null,
		focus_region:null,
		top:0,
		left:0,
		regions:new Array()
	},
	move_region:null,
	start_draw:function(configId){
		$('#'+configId).bind('mousedown',function(e){
			//$('#test_drag').animate("")
			_cfs.draw.begin_point = e;
			
			if(!_cfs.draw.status){
				_cfs.draw.offset = $(this).offset();
				
				var x = _cfs.draw.begin_point.pageX;
				var y = _cfs.draw.begin_point.pageY;
				_cfs.draw.left = x-_cfs.draw.offset.left;
				_cfs.draw.top = y-_cfs.draw.offset.top;
				// 创建一个元素
				var id = _cfs.draw.regions.length;
				_cfs.focus_region = "darg_region_"+id;
				
  				$(this).append('<div id="'+_cfs.focus_region+'" style="background-color:#c0c0c0;cursor:point;width:1px;height:1px;top:1px;left:1px;border:0px solid #c0c0c0;z-index:1000;padding:1px;" ><div style="background-color:#1F637B;cursor:move;width:100%;height:100%;"></div></div>');
  				_cfs.draw.regions.push(_cfs.focus_region);
				
				$('#'+_cfs.focus_region).css({
					top:_cfs.draw.top,
					left:_cfs.draw.left,
					position:'absolute'
				});
				
				
				$('#'+_cfs.focus_region+" div").bind('mousedown',function(e){
					
					_cfs.draw.moveing = true;
                    var abs_x = e.pageX - $(this).parent().position().left;  
                    var abs_y = e.pageY - $(this).parent().position().top;
					$(this).bind('mousemove',_cfs.move_region = function(e){
						if(!_cfs.draw.moveing || _cfs.draw.status) return;
						
						var x = e.pageX - abs_x;//获得鼠标指针离DIV元素左边界的距离 
						var y = e.pageY - abs_y;//获得鼠标指针离DIV元素上边界的距离
						if(x<0) x = 0;
						if(y<0) y = 0;
						
						var max_x = 360-$(this).parent().width()-2; 
						var max_y = 300-$(this).parent().height()-2; 
						if(x >= max_x){x = max_x;}
						if(y >= max_y){y = max_y;}
						
						$(this).parent().animate({
							left:x,
							top:y
						},0)
					});
				});
				$('#'+_cfs.focus_region+" div").bind('mouseup',function(e){
					_cfs.draw.moveing = false;
					$(this).unbind('mousemove',_cfs.move_region);
				});
			}
			_cfs.draw.status = true;
		});
		

		$('#'+configId).bind('mouseup',function(){
			//$('#test_drag').animate("")
			_cfs.draw.status = false;
		})
		$('#'+configId).bind('mousemove',function(e){
			if(_cfs.draw.status && _cfs.draw.begin_point != null){
				
				var x = _cfs.draw.begin_point.pageX;
				var y = _cfs.draw.begin_point.pageY;
				var w = e.pageX-x;
				var h = e.pageY-y;
				
				if(e.pageX >=parseInt(_cfs.draw.offset.left)+360 ) return;
				if(e.pageY >=parseInt(_cfs.draw.offset.top)+300  ) return;
				
				$('#'+_cfs.focus_region).animate({
					//cursor:'pointer'
					width:w,
					height:h
				},0);
			}
		});
	},
	stop_draw:function(configId){
		_cfs.draw.status = false;
		_cfs.draw.begin_point = null;
		$('#'+configId).unbind();
	},
	*/
	/* 遮挡 实现未完成，先这样*/
	// xml 元素单独处理
	create_form_xml:function(resType,configId,attrs,param){
	//	console.log(attrs)
			if(resType == "PTZ"){
					console.log(configId)
				}
		var html = "";
		switch(configId){
			case "F_ST_PlatformAddrList":
				html += _cfs.create_from_platform_address_list(configId,attrs,param);
			break;
			case "F_ST_SessionList":
				html += _cfs.create_from_Session_list(configId,attrs,param);
				break;
			case "F_ST_StreamList":
				html += _cfs.create_from_Stream_list(configId,attrs,param);
				break; 
			case "F_ST_IPChannelSets":
				html += _cfs.create_from_IPChannelSets_list(configId,attrs,param);
				break;
			case "F_SG_RecordStatus":
				html += _cfs.create_from_RecordStatus_list(configId,attrs,param);
				break;
			case "F_ST_OnlineSMSets":
				html += _cfs.create_from_OnlineSMSets_list(configId,attrs,param);
				break;
			case "F_ST_OEMData":
				html += _cfs.create_from_OEMData(configId,attrs,param);
				break;
					
			case "F_ST_DeviceLinkActions":
				html += _cfs.create_from_deviceLinkActions(configId,attrs,param);
				break;	
			/*
			case "F_PTZ_TourSets":
				html += _cfs.create_from_TourSets_list(resType,configId,attrs,param);
				break;	
			case "F_PTZ_PresetPositionSets":
				html += _cfs.create_from_PresetPositionSets_list(resType,configId,attrs,param);
				break;
			case "F_PTZ_SecondaryDeviceSets":
				html += _cfs.create_from_SecondaryDeviceSets_list(resType,configId,attrs,param);
				break;	*/				
		}
		return html;
	},
	
	/*平台地址管理*/
	create_from_platform_address_list:function(configId,attrs,res){
	
	
		var data = new Array();
		// F_ST_RegPlatformStatusList
		var status = new Array();
		var configXML = _cfs.resConfigSets["ST_0"]["F_ST_RegPlatformStatusList"];
		console.log(configXML)
		$(configXML).find("Status").each(function(k,n){
			status.push($(n).text())
		});
		var i = 0;
		configXML = _cfs.resConfigSets["ST_0"]["F_ST_PlatformAddrList"];
	
		$(configXML).find("Addr").each(function(k,n){
			data.push({address:$(n).text(),status:status[i++]});
		});
		
		if($('#'+configId+'_add_dlg').length > 0){
			$('#'+configId+'_add_dlg').remove();
		}
		
		var html = '';
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		html += '<td>';
		
			html += '<table id="'+configId+'_grid" class="easyui-datagrid" width="448" data-options="toolbar:\'#'+configId+'_grid_tb\',onLoadSuccess:function(){_cfs.platform_address_grid_loaded(\''+configId+'\');}" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'address\',width:120,align:\'center\'">'+_lp.frame.configsets.address_list.col.address+'</th>';
			html += '<th data-options="field:\'status\',width:180,align:\'center\',formatter:function(v,rec,index){return _p.PlatformStatusMap(v);}">'+_lp.frame.configsets.address_list.col.status+'</th>';
			html += '<th data-options="field:\'operation\',width:140,align:\'center\',formatter:_cfs.platform_address_op_map" >'+_lp.frame.configsets.address_list.col.op+'</th>';
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			//html += '';
			for(var i = 0;i < data.length;i++){
				var d = data[i];
				//html += '<tr><td>'+d.address+'</td><td>'+_p.PlatformStatusMap(d.status)+'</td><td><a class="button black small" href="javascript:void(0)"  onclick="_cfs.modify_platform_address(\''+configId+'\',\''+i+'\',\''+d.address+'\')" >'+_lp.frame.configsets.btns.modify+'</a>&nbsp;<a class="button black small" href="javascript:void(0)" onclick="_cfs.del_platform_address(\''+configId+'\',\''+i+'\',\''+d.address+'\')" >'+_lp.frame.configsets.btns.del+'</a></td></tr>';
				html += '<tr><td>'+d.address+'</td><td>'+_p.PlatformStatusMap(d.status)+'</td><td></td></tr>';
				
			}
			html += '</tbody>';
			html += '</table>';

			html += '<div id="'+configId+'_grid_tb" style="height:auto">';
				html += '<div style="margin-bottom:1px;text-align:left;">';
				html += '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="_cfs.open_add_platform_address_dlg(\''+configId+'\');" >'+_lp.frame.configsets.btns.add+'</a>';
				html += '</div>';
			html += '</div>';
			html += '<div id="'+configId+'_add_dlg" style="display:none" >';
				html += '<table cellpadding="5">';
				html += '<tr>';
				html += '<td>'+_lp.frame.configsets.address.lbl+':</td>';
				html += '<td><input class="easyui-textbox" type="text" id="'+configId+'_add_form_platfromaddress" data-options="required:false,width:150"></input></td>';
				html += '</tr>';
				html += '</table>';
			html += '</div>';

		html += '</td></tr>';
		
		return html;
	},
	
	// 加载平台地址列表
	platform_address_reload:function(bForce){
		var data = new Array();
		var status = new Array();
		
		_cfs.refresh_resource_configsetsbyid(bForce,[{type:"ST",idx:0,id:"F_ST_PlatformAddrList"},{type:"ST",idx:0,id:"F_ST_RegPlatformStatusList"}])

		
		var data = new Array();
		// F_ST_RegPlatformStatusList
		var status = new Array();
		var configXML = _cfs.resConfigSets["ST_0"]["F_ST_RegPlatformStatusList"];
		console.log(configXML)
		$(configXML).find("Status").each(function(k,n){
			status.push($(n).text())
		});
		
		configXML = _cfs.resConfigSets["ST_0"]["F_ST_PlatformAddrList"];
		var i = 0;
		$(configXML).find("Addr").each(function(k,n){
			data.push({address:$(n).text(),status:status[i++]});
		});
		
	
		if($('#F_ST_PlatformAddrList_grid')){
			$('#F_ST_PlatformAddrList_grid').datagrid('loadData',data);
		}
	},
	
	// 平台地址加载完成
	platform_address_grid_loaded:function(configId){
		$('#'+configId+'_grid').parent().after($('#'+configId+'_grid_tb'))
	    
	},
	// 平台地址操作列
	platform_address_op_map:function(v,rec,index){
		var html = '';
		html += '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.modify_platform_address(\'F_ST_PlatformAddrList\',\''+index+'\',\''+rec.address+'\')"  ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.modify+'&nbsp;</span></a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.del_platform_address(\'F_ST_PlatformAddrList\',\''+index+'\',\''+rec.address+'\')"  ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.del+'&nbsp;</span></a>';
		
		//html += '<a class="button black small" href="javascript:void(0)"  onclick="_cfs.modify_platform_address(\'F_ST_PlatformAddrList\',\''+index+'\',\''+rec.address+'\')" >'+_lp.frame.configsets.btns.modify+'</a>&nbsp;<a class="button black small" href="javascript:void(0)" onclick="_cfs.del_platform_address(\'F_ST_PlatformAddrList\',\''+index+'\',\''+rec.address+'\')" >'+_lp.frame.configsets.btns.del+'</a>';
		return html;
	},
	
	// 打开添加平台地址编辑框
	open_add_platform_address_dlg:function(configId){
		var dlgId = configId+'_add_dlg';
		if($('#'+dlgId)){

			$('#'+dlgId).show();
			$('#'+dlgId).dialog({
			    title: _lp.frame.configsets.address.addTitle,
			    width: 300,
			    height: 120,
			    closed: false,
			    cache: false,
			    buttons:[{
					text:_lp.frame.configsets.btns.ok,
					handler:function(){
						
						var rows = $('#'+configId+'_grid').datagrid("getRows");
						var params = new Array();
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];
							params.push({name:'Addr',childXML:r.address,attrs:[]});
						}
						params.push({
		        			name:'Addr',
		        			childXML:$('#'+configId+'_add_form_platfromaddress').textbox('getValue'),
		        			attrs:[]
		        		});
						var values = new Array();
						values.push({
							id:configId,
			        		params:params,
							attrs:[]
						});
				        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
				        
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note,
						                timeout:4000,
						                showType:'slide'
						            });
						            $('#'+dlgId).dialog('close');
						            _cfs.platform_address_reload(true);
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				},{
					text:_lp.frame.configsets.btns.close,
					handler:function(){
						$('#'+dlgId).dialog('close');
					}
				}],
				onOpen:function(){					
					$('#'+configId+'_add_form_platfromaddress').textbox({
						required:true,
						validType:'ipport'
					});
				},
				onClose:function(){
								//var patn = /^([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\:]([0-9]{1,5})*$/ ;
				
					$('#'+configId+'_add_form_platfromaddress').textbox({
						required:false
					});
				},
			    modal: true
			});
			
			$('#'+configId+'_add_form_platfromaddress').textbox('setValue','');
		}
		
		//return "modify";
	},
	
	// 修改平台地址，如果要添加，也是调用此接口
	modify_platform_address:function(configId,index,v){
		var dlgId = configId+'_add_dlg';
		if($('#'+dlgId)){
			$('#'+dlgId).show();
			$('#'+dlgId).dialog({
			    title: _lp.frame.configsets.address.modifyTitle,
			    width: 300,
			    height: 120,
			    closed: false,
			    cache: false,
			    buttons:[{
					text:_lp.frame.configsets.btns.ok,
					handler:function(){

						var rows = $('#'+configId+'_grid').datagrid("getRows");
						var params = new Array();
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];
							if( i == index){
								params.push({name:'Addr',childXML:$('#'+configId+'_add_form_platfromaddress').textbox('getValue'),attrs:[]});
							}else{
								params.push({name:'Addr',childXML:r.address,attrs:[]});
							}
						}
						var values = new Array();
						values.push({
							id:configId,
			        		params:params,
							attrs:[]
						});
				        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);

						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note,
						                timeout:4000,
						                showType:'slide'
						            });

									$('#'+dlgId).dialog('close');
						            _cfs.platform_address_reload(true);
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				},{
					text:_lp.frame.configsets.btns.close,
					handler:function(){
						$('#'+dlgId).dialog('close');
					}
				}],
				onOpen:function(){					
					$('#'+configId+'_add_form_platfromaddress').textbox({
						required:true
					});
				},
				onClose:function(){
									
					$('#'+configId+'_add_form_platfromaddress').textbox({
						required:false
					});
				},
			    modal: true
			});
			
			$('#'+configId+'_add_form_platfromaddress').textbox("setValue",v);
		}
		return "modify";
	},
	
	// 删除平台地址
	del_platform_address:function(configId,index,v){
		var dlgId = configId+'_add_dlg';
		$.messager.confirm(_lp.frame.configsets.notes.noteTitle1,_lp.frame.configsets.notes.note4,function(r){
		    if (r){


				var rows = $('#'+configId+'_grid').datagrid("getRows");
				var params = new Array();
				for(var i = 0;i < rows.length;i++){
					var r = rows[i];
					if( i == index){
						//params.push({name:'Addr',childXML:$('#'+configId+'_add_form_platfromaddress').textbox('getValue'),attrs:[]});
					}else{
						params.push({name:'Addr',childXML:r.address,attrs:[]});
					}
				}
				var values = new Array();
				values.push({
					id:configId,
	        		params:params,
					attrs:[]
				});
		        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);

				if(rv && rv.M && rv.M.C){
					if(rv.M.C.SPError != 0){
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
			                timeout:4000,
			                showType:'slide'
			            });
			            return;
					}
					if(rv.M.C.Res){
						if(rv.M.C.Res.Error == "0"){
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.note5,
				                timeout:4000,
				                showType:'slide'
				            });
				            _cfs.platform_address_reload(true);
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
				                timeout:4000,
				                showType:'slide'
				            });
						}
					}else{
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
			                timeout:4000,
			                showType:'slide'
			            });
			    	}
					
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError,
		                timeout:4000,
		                showType:'slide'
		            });
		    	}
		    }
		});
		return;
	},
	
	// 创建session列表
	create_from_Session_list:function(configId,attrs,res){
		var data = new Array();
		var html = '';
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		html += '<td>';
			html += '<table class="easyui-datagrid" width="298" data-options="rownumbers:true,toolbar:\''+configId+'_grid_tb\'" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'userId\',width:120">'+_lp.frame.configsets.session_list.col.userId+'</th>';
			html += '<th data-options="field:\'clientType\',width:80">'+_lp.frame.configsets.session_list.col.clientType+'</th>';
			html += '<th data-options="field:\'loginTime\',width:80" >'+_lp.frame.configsets.session_list.col.loginTime+'</th>';
			html += '<th data-options="field:\'sourceAddr\',width:80" >'+_lp.frame.configsets.session_list.col.sourceAddr+'</th>';
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			//html += '';			
			var i = 0;
			$(res).children("Session").each(function(k,n){
				data.push({userId:$(n).attr("UserID"),clientType:$(n).attr("ClientType"),loginTime:$(n).attr("LoginTime"),sourceAddr:$(n).attr("SourceAddr")});
			});
			
			for(var i = 0;i < data.length;i++){
				var d = data[i];
				html += '<tr><td>'+d.userId+'</td><td>'+d.userId+'</td><td>'+d.loginTime+'</td><td>'+d.sourceAddr+'</td></tr>';
			}
			html += '</tbody>';
			html += '</table>';
			html += '<div id="'+configId+'_grid_tb" style="height:auto">';
				html += '<div style="margin-bottom:1px;text-align:left;">';
				html += '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="" >'+_lp.frame.configsets.btns.refresh+'</a>';
				html += '</div>';
			html += '</div>';
			
		html += '</td></tr>';		
		return html;
	},
	
	// 创建stream列表
	create_from_Stream_list:function(configId,attrs,res){

		var data = new Array();
		var html = '';
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		html += '<td>';
			html += '<table class="easyui-datagrid" width="570" data-options="rownumbers:true,toolbar:\''+configId+'_grid_tb\'" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'resType\',width:80">'+_lp.frame.configsets.stream_list.col.resType+'</th>';
			html += '<th data-options="field:\'stream\',width:80" >'+_lp.frame.configsets.stream_list.col.stream+'</th>';
			html += '<th data-options="field:\'resIdx\',width:80">'+_lp.frame.configsets.stream_list.col.resIdx+'</th>';
			html += '<th data-options="field:\'createTime\',width:80" >'+_lp.frame.configsets.stream_list.col.createTime+'</th>';
			html += '<th data-options="field:\'peerAddr\',width:80" >'+_lp.frame.configsets.session_list.col.peerAddr+'</th>';
			html += '<th data-options="field:\'totalMBytes\',width:80,formatter:function(v,rec,index){return rec.totalMBytes+\'.\'+rec.totalKBytes+\'M\';}" >'+_lp.frame.configsets.session_list.col.totalBytes+'</th>';
			//html += '<th data-options="field:\'totalKBytes\',width:80" >'+_lp.frame.configsets.session_list.col.totalKBytes+'</th>';
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			//html += '';

			var i = 0;
			$(res).children("Stream").each(function(k,n){
				data.push({resType:$(n).attr("ResType"),stream:$(n).attr("Stream"),resIdx:$(n).attr("ResIdx"),createTime:$(n).attr("CreateTime"),peerAddr:$(n).attr("PeerAddr"),totalMBytes:$(n).attr("TotalMBytes"),totalKBytes:$(n).attr("TotalKBytes")});
			});
			
			for(var i = 0;i < data.length;i++){
				var d = data[i];
				html += '<tr><td>'+d.userId+'</td><td>'+d.userId+'</td><td>'+d.loginTime+'</td><td>'+d.sourceAddr+'</td></tr>';
			}
			html += '</tbody>';
			html += '</table>';
			html += '<div id="'+configId+'_grid_tb" style="height:auto">';
			html += '<div style="margin-bottom:1px;text-align:left;">';
			html += '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="" >'+_lp.frame.configsets.btns.refresh+'</a>';
			html += '</div>';
		html += '</div>';
		html += '</td></tr>';
		return html;
	},
	
	// 创建IP通道列表
	create_from_IPChannelSets_list:function(configId,attrs,res){
		
		var ivNumber = 0
		for(var i = 0;i < _cfs.pu.childResource.length;i++){
			if(_cfs.pu.childResource[i].type == "IV") {
				ivNumber ++;
			}
		}	
	   var channelNum = 0
	   params = '<Res Type="ST" Idx="0" OptID="F_ST_AnalogChannelNum" ></Res>';
	   var rv = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,params);
	   var ress = $(rv).find("Res");
	   if($(ress).attr("Error") == 0){	
			var p = $(rv).find("Param");
			channelNum = $(p).attr("Value");
		}
		if(!channelNum > 0)	channelNum = 0;	

					   
		var data = new Array();
		var html = '';
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		html += '<td>';
			html += '<table id="'+configId+'_grid" class="easyui-datagrid" width="740" data-options="rownumbers:true,singleSelect:true,toolbar:\'#'+configId+'_grid_tb\'" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'producerId\',width:120,formatter:_cfs.ipchannelsets_formatter_producerId">'+_lp.frame.configsets.ipchannelsets_list.col.producerId+'</th>';
			html += '<th data-options="field:\'addr\',width:120" >'+_lp.frame.configsets.ipchannelsets_list.col.addr+'</th>';
			html += '<th data-options="field:\'userId\',width:80">'+_lp.frame.configsets.ipchannelsets_list.col.userId+'</th>';
			html += '<th data-options="field:\'password\',hidden:true"></th>';
			html += '<th data-options="field:\'devid\',hidden:true"></th>';
			html += '<th data-options="field:\'connectStatus\',width:100" >'+_lp.frame.configsets.ipchannelsets_list.col.connectStatus+'</th>';
			html += '<th data-options="field:\'usedCodeCapability\',width:80">'+_lp.frame.configsets.ipchannelsets_list.col.usedCodeCapability+'</th>';
			html += '<th data-options="field:\'usedBandwidth\',width:80">'+_lp.frame.configsets.ipchannelsets_list.col.usedBandwidth+'</th>';
			html += '<th data-options="field:\'op\',width:140,formatter:_cfs.ipchannelsets_formatter_op" >'+_lp.frame.configsets.ipchannelsets_list.col.op+'</th>';
				//
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			var i = 0;
			$(res).children("Channel").each(function(k,n){
				i++;
				var d = $(n);
				html += '<tr><td>'+d.attr("producerId")+'</td><td>'+d.attr("addr")+'</td><td>'+d.attr("userId")+'</td><td>'+d.attr("password")+'</td><td>'+d.attr("DevID")+'</td></tr>';
			});
			
			html += '</tbody>';
			html += '</table>';
		html += '<div id="'+configId+'_grid_tb" style="height:auto;">';
			html += '<div style="margin-bottom:1px;text-align:right;">';
			if(i >= ivNumber-channelNum){
				html += '<a href="javascript:void(0)" class="easyui-linkbutton" id="add-channel" iconCls="icon-add" plain="true" disabled onclick="_cfs.open_add_ipchannelset_dlg()" >'+_lp.frame.configsets.btns.add+'</a>';
			}else{
				html += '<a href="javascript:void(0)" class="easyui-linkbutton" id="add-channel" iconCls="icon-add" plain="true"  onclick="_cfs.open_add_ipchannelset_dlg()" >'+_lp.frame.configsets.btns.add+'</a>';
			}	 
		
			html += '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="_cfs.refresh_ipchannelsets();" >'+_lp.frame.configsets.btns.refresh+'</a>';
			html += '</div>';
		html += '</div>';
		
		html += '</td></tr>';
		//_cfs.delayLoadControls.push(_cfs.init_ipchannelsets);
		return html;
	},
	
	// 刷新IP通道
	refresh_ipchannelsets:function(){
		_cfs.refresh_resource_configsetsbyid(true,[{type:"ST",idx:0,id:"F_ST_IPChannelSets"},{type:"ST",idx:0,id:"F_ST_IPChannelStatusList"}]);
		var configXML = "";
		configXML = _cfs.resConfigSets["ST_0"]["F_ST_IPChannelStatusList"];
	
		//_p.get_res_configs(_cfs.connectId,_cfs.pu.puid,[{type:"ST",idx:0,id:"F_ST_IPChannelSets"}]);
		var status = new Array();
		$(configXML).find("Channel").each(function(k,n){
			var codeCapability = $(n).attr("CodeCapability") || 0;
			var bandwidth = $(n).attr("Bandwidth") || 0;
			status.push({status:$(n).attr("ErrorMsg"),codeCapability:codeCapability,bandwidth:bandwidth})
		});
		
		var channel = new Array();
		configXML = _cfs.resConfigSets["ST_0"]["F_ST_IPChannelSets"];
	
		var i = 0;
		$(configXML).children("Channel").each(function(k,n){
			var d = $(n);

			var s = (status[i] ? status[i]:{status:"",codeCapability:0,bandwidth:0});
			i++;
			channel.push({producerId:d.attr("producerId"),addr:d.attr("addr"),userId:d.attr("userId"),password:d.attr("password"),devid:d.attr("devid"),connectStatus:s.status,usedCodeCapability:s.codeCapability+"*D1",usedBandwidth:s.bandwidth+"Mbps"});
		});
		if($('#F_ST_IPChannelSets_grid')){
			$('#F_ST_IPChannelSets_grid').datagrid('loadData',channel);	
		}
		
		var ivNumber = 0
		for(var i = 0;i < _cfs.pu.childResource.length;i++){
			if(_cfs.pu.childResource[i].type == "IV") {
				ivNumber ++;
			}
		}	
	   var channelNum = 0
	   params = '<Res Type="ST" Idx="0" OptID="F_ST_AnalogChannelNum" ></Res>';
	   var rv = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,params);
	   var ress = $(rv).find("Res");
	   if($(ress).attr("Error") == 0){	
			var p = $(rv).find("Param");
			channelNum = $(p).attr("Value");
		}
		if(!channelNum > 0)	channelNum = 0	
		var datalength = $('#F_ST_IPChannelSets_grid').datagrid("getRows").length;
	
		if(datalength >= ivNumber-channelNum){
			$('#add-channel').linkbutton({  
				disabled: true  
			});  
		}	
	},
	
	// 格式化IP通道操作列
	ipchannelsets_formatter_op:function(v,r,i){
		//return '<a class="button black small" onclick="_cfs.open_edit_ipchannelset_dlg('+i+')">'+_lp.frame.configsets.btns.modify+'</a><a class="button black small" onclick="_cfs.del_ipchannelset('+i+')" >'+_lp.frame.configsets.btns.del+'</a>';
		
		return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.open_edit_ipchannelset_dlg('+i+')"  ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.modify+'&nbsp;</span></a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.del_ipchannelset('+i+')"  ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.clean+'&nbsp;</span></a>';
		
		
	},
	
	// 格式IP通道厂商列
	ipchannelsets_formatter_producerId:function(v,r,i){
		switch(v){
		case "00000":
			return _lp.frame.configsets.ipchannelsets_list.producers[2];
			break;	
		case "00001":
			return _lp.frame.configsets.ipchannelsets_list.producers[0];
			break;
		case "01000":
			return _lp.frame.configsets.ipchannelsets_list.producers[1];
			break;
		}
		return v;
	
	},
	
	// 打开添加IP通道对话框
	open_add_ipchannelset_dlg:function(){
		var html = '';
		html += '<form id="add_ipchannelset_dlg_form" method="post" novalidate><table style="padding:5px;">';
		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.ipchannelsets_list.col.producerId+':</td>';
		html += '<td><select id="add_ipchannelset_producerId" class="easyui-combobox"  data-options="editable:false,onSelect:function(r){_cfs.add_ipchannelset_producerId_change(r.value);}" style="width:200px;"><option value="00001" >iCAP</option><option value="01000">Onvif</option><option value="00101">'+_lp.des.note6+'</option></select></td>';
		html += '</tr>';

		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.ipchannelsets_list.col.addr+':</td>';
		html += '<td><input id="add_ipchannelset_addr" class="easyui-textbox" value="" required="true" style="width:200px;"><td>';
		html += '</tr>';

		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.ipchannelsets_list.col.userId+':</td>';
		html += '<td><input id="add_ipchannelset_userid" class="easyui-textbox" value="" required="true" style="width:200px;"><td>';
		html += '</div>';

		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.ipchannelsets_list.col.password+':</td>';
		html += '<td><input id="add_ipchannelset_password" class="easyui-textbox" value="" type="password" style="width:200px;">&nbsp;<span id="ipchannelset_show_pwd_span"><input id="ipchannelset_show_pwd_chk" style="vertical-align:middle;" type="checkbox" onclick="_cfs.ipchannelset_show_pwd(this.id)" /><label for="ipchannelset_show_pwd_chk">'+_lp.des.password+'</label></span><td>';
		html += '</tr>';
		
		html += '</table></form>';
		
		html += '</div>';	
		
			
		$('#edit_ipchannelset_dlg').show();
		$('#edit_ipchannelset_dlg').dialog({
		    title: _lp.frame.configsets.ipchannelsets_list.addTitle,
		    width: 400,
		    height: 220,
		    closed: false,
		    cache: false,
		    content:html,
		    buttons:[{
				text:_lp.frame.configsets.btns.ok,
				handler:function(){
					if($('#add_ipchannelset_dlg_form').form("validate") == true){
						var producerId = $('#add_ipchannelset_producerId').combobox("getValue");
						var addr = $('#add_ipchannelset_addr').textbox("getValue");
						var userId = $('#add_ipchannelset_userid').textbox("getValue");
						var password = (producerId == "00001" ? "0x"+P_LY.Plug.nc.MD5Enc($('#add_ipchannelset_password').textbox("getValue")) : $('#add_ipchannelset_password').textbox("getValue")) ;
						var index = $('#add_ipchannelset_index').val();
						
						var resIdx = 0;
						
						var params = new Array();		
						var rows = $('#F_ST_IPChannelSets_grid').datagrid("getRows");
						
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];								params.push({name:'Channel',childXML:"",attrs:[{name:"ProducerID",val:r.producerId},{name:"Addr",val:r.addr},{name:"UserID",val:r.userId},{name:"Password",val:r.password},{name:"DevID",val:r.devid},{name:"ResIdx",val:resIdx}]});
						}
					   					   																						params.push({name:'Channel',childXML:"",attrs:[{name:"ProducerID",val:producerId},{name:"Addr",val:addr},{name:"UserID",val:userId},{name:"Password",val:password},{name:"DevID",val:""},{name:"ResIdx",val:resIdx}]});
						var values = new Array();
						values.push({
							id:"F_ST_IPChannelSets",
			        		params:params,
							attrs:[]
						});
				        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
				        
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note11,
						                timeout:4000,
						                showType:'slide'
						            });
									$('#edit_ipchannelset_dlg').dialog('close');
									_cfs.refresh_ipchannelsets();
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				}
			},{
				text:_lp.frame.configsets.btns.close,
				handler:function(){
					$('#edit_ipchannelset_dlg').dialog('close');
				}
			}],
		    modal: true
		});
		
		_cfs.add_ipchannelset_producerId_change($("#add_ipchannelset_producerId").combobox('getValue'));
	},
	
	ipchannelset_show_pwd:function(chkId){
		//alert($('#add_ipchannelset_password').attr("type"));
		//$('#add_ipchannelset_password').attr("type","")
		if($("#"+chkId).prop("checked")){
			$('#add_ipchannelset_password').textbox({
				type:'text'
			});	
		}else{
			$('#add_ipchannelset_password').textbox({
				type:'password'
			});
		}
		//$('#add_ipchannelset_password').textbox('setValue','test');
	},
	add_ipchannelset_producerId_change:function(value){
		$('#add_ipchannelset_password').textbox("setValue","");
		if(value == "00001"){
			$('#ipchannelset_show_pwd_span').hide();
			return;
		}
		$('#ipchannelset_show_pwd_span').show();
	},
	// 打开编辑IP通道对话框
	open_edit_ipchannelset_dlg:function(rowIndex){

		var rows = $('#F_ST_IPChannelSets_grid').datagrid("getRows");
		var c = null;
		if(rows[rowIndex]){
			c = rows[rowIndex];
		}else{
			return;
		}

		var html = '';
		html += '<form id="edit_ipchannelset_dlg_form" method="post" novalidate><table style="padding:5px;"><input id="edit_ipchannelset_index" value="'+rowIndex+'" type=hidden />';
		html += '<tr>';
		html += '<td>'+_lp.des.p_type+':</td>';
		html += '<td><select id="edit_ipchannelset_producerId" class="easyui-combobox" data-options="editable:false" value="'+c.producerId+'" style="width:200px;"><option value="00000" >'+_lp.des.idel+'</option><option value="00001" selected >iCAP</option><option value="01000">Onvif</option><option value="00101">'+_lp.des.note6+'</option></select></td>';
		html += '</tr>';

		html += '<tr>';
		html += '<td>'+_lp.des.address+':</td>';
		html += '<td><input id="edit_ipchannelset_addr" class="easyui-textbox" value="'+c.addr+'" required="true" style="width:200px;"><td>';
		html += '</tr>';

		html += '<tr>';
		html += '<td>'+_lp.des.user+':</td>';
		html += '<td><input id="edit_ipchannelset_userid" class="easyui-textbox" value="'+c.userId+'" required="true" style="width:200px;"><td>';
		html += '</div>';

		html += '<tr>';
		html += '<td></td>';
		html += '<td><td>';
		html += '</tr>';
		
		html += '<tr>';
		html += '<td>'+_lp.des.password+':</td>';
		//alert(c.password)
		html += '<td><input type=hidden id="edit_ipchannelset_opassword" value="'+c.password+'" /><input id="edit_ipchannelset_password" class="easyui-textbox" type="password" value=""  style="width:200px;">&nbsp;<input type=checkbox id="edit_ipchannelset_password_chk"  style="vertical-align:middle;margin-top:0;" onclick="_cfs.edit_ipchannelset_password_chk_click();"><label for="edit_ipchannelset_password_chk">'+_lp.des.m_password+'</label><td>';
		html += '</tr>';
		
		html += '</table></form>';
			
			
		$('#edit_ipchannelset_dlg').show();
		$('#edit_ipchannelset_dlg').dialog({
		    title: _lp.frame.configsets.ipchannelsets_list.modifyTitle,
		    width: 400,
		    height: 220,
		    closed: false,
		    cache: false,
		    content:html,
		    buttons:[{
				text:_lp.frame.configsets.btns.ok,
				handler:function(){
					if($('#edit_ipchannelset_dlg_form').form("validate") == true){
						var producerId = $('#edit_ipchannelset_producerId').combobox("getValue");
						var addr = $('#edit_ipchannelset_addr').textbox("getValue");
						var userId = $('#edit_ipchannelset_userid').textbox("getValue");
						
						var password = $('#edit_ipchannelset_opassword').val();
						
						if($('#edit_ipchannelset_password_chk').prop("checked")){
							password = (producerId == "00001" ? "0x"+P_LY.Plug.nc.MD5Enc($('#edit_ipchannelset_password').textbox("getValue")) : $('#edit_ipchannelset_password').textbox("getValue")) ;
						}
						
						
						var index = $('#edit_ipchannelset_index').val();
						var resIdx = 0;

						var rows = $('#F_ST_IPChannelSets_grid').datagrid("getRows");
						var params = new Array();
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];
							if( i == index){
								params.push({name:'Channel',childXML:"",attrs:[{name:"ProducerID",val:producerId},{name:"Addr",val:addr},{name:"UserID",val:userId},{name:"Password",val:password},{name:"DevID",val:r.devid},{name:"ResIdx",val:resIdx}]});
							}else{
								params.push({name:'Channel',childXML:"",attrs:[{name:"ProducerID",val:r.producerId},{name:"Addr",val:r.addr},{name:"UserID",val:r.userId},{name:"Password",val:r.password},{name:"DevID",val:r.devid},{name:"ResIdx",val:resIdx}]});
							}
						}

						var values = new Array();
						values.push({
							id:"F_ST_IPChannelSets",
			        		params:params,
							attrs:[]
						});
				        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
						
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note12,
						                timeout:4000,
						                showType:'slide'
						            });
									$('#edit_ipchannelset_dlg').dialog('close');
									
						            setTimeout(function(){_cfs.refresh_ipchannelsets();},100)
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				}
			},{
				text:_lp.frame.configsets.btns.close,
				handler:function(){
					try{
							
						$('#edit_ipchannelset_dlg').hide();
						$('#edit_ipchannelset_dlg').dialog('open');
						$('#edit_ipchannelset_dlg').dialog('close');
					}catch(e){
					}
				}
			}],
		    modal: true
		});
		$('#edit_ipchannelset_producerId').combobox("setValue",c.producerId);
		_cfs.edit_ipchannelset_password_chk_click();
	},
	
	// 切换编辑IP通道密码
	edit_ipchannelset_password_chk_click:function(){		
		$('#edit_ipchannelset_password').textbox("setValue","");
		if($("#edit_ipchannelset_password_chk").prop('checked')){
			$('#edit_ipchannelset_password').textbox('enable');
		}else{
			$('#edit_ipchannelset_password').textbox('disable');
		}
	},
	// 删除IP通道
	del_ipchannelset:function(rowIndex){

		var rows = $('#F_ST_IPChannelSets_grid').datagrid("getRows");
		var resIdx = 0;
		var params = new Array();
		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			password = r.password;
			if( i == rowIndex){								params.push({name:'Channel',childXML:"",attrs:[{name:"ProducerID",val:"00000"},{name:"Addr",val:""},{name:"UserID",val:""},{name:"Password",val:""},{name:"DevID",val:""},{name:"ResIdx",val:resIdx}]});
				
			}
			else{							params.push({name:'Channel',childXML:"",attrs:[{name:"ProducerID",val:r.producerId},{name:"Addr",val:r.addr},{name:"UserID",val:r.userId},{name:"Password",val:password},{name:"DevID",val:r.devid},{name:"ResIdx",val:resIdx}]});
			}
		}
		console.log(params)
		var values = new Array();
		values.push({
			id:"F_ST_IPChannelSets",
    		params:params,
			attrs:[]
		});
		
        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
        
		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return;
			}
			if(rv.M.C.Res){
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note13,
		                timeout:4000,
		                showType:'slide'
		            });
					setTimeout(function(){
						_cfs.refresh_ipchannelsets();
					},100);
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
			
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError,
                timeout:4000,
                showType:'slide'
            });
    	}
	},
	// 创建前端联动列表
	create_from_deviceLinkActions:function(configId,attrs,res){
		var data = new Array();
		var html = '';
		html += '<tr>';
		html += '<td nowrap>'+attrs.attr("Name")+':</td>';
		html += '<td>';
			html += '<table id="'+configId+'_grid" class="easyui-datagrid" width="680" data-options="singleSelect:true,toolbar:\'#'+configId+'_grid_tb\'" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'eventtype\',width:150,align:\'center\'">'+_lp.frame.configsets.devicelinkactions_list.col.eventType+'</th>';
			html += '<th data-options="field:\'src\',width:150,align:\'center\'" >'+_lp.frame.configsets.devicelinkactions_list.col.src+'</th>';
			html += '<th data-options="field:\'model\',width:80,align:\'center\'">'+_lp.frame.configsets.devicelinkactions_list.col.model+'</th>';
			html += '<th data-options="field:\'action\',width:150,align:\'center\'">'+_lp.frame.configsets.devicelinkactions_list.col.action+'</th>';
			html += '<th data-options="field:\'srcrestype\',hidden:true"></th>';
			html += '<th data-options="field:\'eventsrcno\',hidden:true"></th>';
			html += '<th data-options="field:\'devicelinkaction\',hidden:true"></th>';
			html += '<th data-options="field:\'op\',width:140,align:\'center\',formatter:_cfs.devicelinkactions_formatter_op" >'+_lp.frame.configsets.devicelinkactions_list.col.op+'</th>';
				//
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
		
			$(res).find("devicelinkaction").each(function(k,n){
				var d = $(n);
				var eventType = _cfs.devicelinkactions_formatter_eventType(d.find("eventtype").text());
				var action = _cfs.devicelinkactions_formatter_action(d.find("action").text());
				var srcRestype = d.find("srcrestype").text();
				var eventSrcNo = d.find("eventsrcno").text();
				var src = _cfs.devicelinkactions_formatter_src(srcRestype,eventSrcNo);
				var p = d.find("param");
			
				var model = _cfs.devicelinkactions_formatter_model($(p).attr("mode"));
				var devicelinkaction = "";
				
			 	if (window.ActiveXObject){
		         	devicelinkaction = n.xml;
		     	}
		     	else{
		         	devicelinkaction = (new XMLSerializer()).serializeToString(n);
		     	}
				devicelinkaction = encodeURIComponent(devicelinkaction)
				
			
				html += '<tr><td>'+eventType+'</td><td>'+src+'</td><td>'+model+'</td><td>'+action+'</td><td>'+srcRestype+'</td><td>'+eventSrcNo+'</td><td>'+devicelinkaction+'</td></tr>';
			});
			html += '</tbody>';
		
			html += '</table>';
		html += '<div id="'+configId+'_grid_tb" style="height:auto;">';
			html += '<div style="margin-bottom:1px;text-align:right;">';
		
			html += '<a href="javascript:void(0)" class="easyui-linkbutton" id="add-channel" iconCls="icon-add" plain="true"  onclick="_cfs.open_add_devicelinkactions_dlg()" >'+_lp.frame.configsets.btns.add+'</a>';
				 
			html += '</div>';
		html += '</div>';
		
		html += '</td></tr>';
	//	console.log(html)
		return html;
	},
	// 格式化前端联动列表
	devicelinkactions_formatter_op:function(v,r,i){
		
		return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.open_edit_devicelinkactions_dlg('+i+')"  ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.modify+'&nbsp;</span></a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.del_devicelinkactions('+i+')"  ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.del+'&nbsp;</span></a>';
	},
		
	devicelinkactions_formatter_eventType:function(eventType){	
		switch(eventType){
			case "E_ST_EmergentAlert":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_EmergentAlert;
				break;
			case "E_ST_UnusableTimeAlert":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_UnusableTimeAlert;
				break;
			case "E_ST_OverSpeed":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_OverSpeed;
				break;
			case "E_ST_FatigueDriving":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_FatigueDriving;
				break;	
			case "E_ST_StationLinger":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_StationLinger;
				break;
			case "E_ST_RTCFault":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_RTCFault;
				break;
			case "E_ST_BatteryOver":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_BatteryOver;
				break;
			case "E_ST_WirelessAlarmIn":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_WirelessAlarmIn;
				break;
			case "E_ST_InfraredBodyAlarm":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_InfraredBodyAlarm;
				break;
			case "E_ST_VehicleBrake":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_VehicleBrake;
				break;
			case "E_ST_VehicleCollision":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_VehicleCollision;
				break;
			case "E_ST_VehicleRollover":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_ST_VehicleRollover;
				break;	
			case "E_WM_BytesAlarm":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_WM_BytesAlarm;
				break;
			case "E_IV_SignalLost":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_IV_SignalLost;
				break;
			case "E_IV_MotionDetected":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_IV_MotionDetected;
				break;
			case "E_IV_CoverDetected":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_IV_CoverDetected;
				break;	
			case "E_IDL_AlertIn":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_IDL_AlertIn;
				break;
			case "E_IDL_HostAlarm":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_IDL_HostAlarm;
				break;
			case "E_SG_DiskSpaceFull":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_SG_DiskSpaceFull;
				break;
			case "E_SG_StartFileSystemFailed":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_SG_StartFileSystemFailed;
				break;
			case "E_SG_DiskError":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_SG_DiskError;
				break;	
			case "E_GPS_LowSpeed":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_GPS_LowSpeed;
				break;
			case "E_GPS_InERailAlarm":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_GPS_InERailAlarm;
				break;
			case "E_GPS_OutERailAlarm":
				return _lp.frame.configsets.devicelinkactions_list.eventType.E_GPS_OutERailAlarm;
				break;
			default:
				return eventType;
				break;					
		}
	},			
	
	devicelinkactions_formatter_action:function(action){	
		switch(action){
			case "alertOut":
				return _lp.frame.configsets.devicelinkactions_list.action.alertOut;
				break;
			case "sendEmail":
				return _lp.frame.configsets.devicelinkactions_list.action.sendEmail;
				break;
			case "ftpUpload":
				return _lp.frame.configsets.devicelinkactions_list.action.ftpUpload;
				break;
			case "record":
				return _lp.frame.configsets.devicelinkactions_list.action.record;
				break;	
			case "snapShot":
				return _lp.frame.configsets.devicelinkactions_list.action.snapShot;
				break;
			case "moveToPresetPosition":
				return _lp.frame.configsets.devicelinkactions_list.action.moveToPresetPosition;
				break;
			case "online":
				return _lp.frame.configsets.devicelinkactions_list.action.online;
				break;	
			case "sendSMS":
				return _lp.frame.configsets.devicelinkactions_list.action.sendSMS;
				break;
			case "preTransmitVideo":
				return _lp.frame.configsets.devicelinkactions_list.action.preTransmitVideo;
				break;
			case "preSnapshot":
				return _lp.frame.configsets.devicelinkactions_list.action.preSnapshot;
				break;
			case "preTransmitAudio":
				return _lp.frame.configsets.devicelinkactions_list.action.preTransmitAudio;
				break;
			case "preTransmitGPS":
				return _lp.frame.configsets.devicelinkactions_list.action.preTransmitGPS;
				break;
			case "uploadAlert":
				return _lp.frame.configsets.devicelinkactions_list.action.uploadAlert;
				break;	
			case "uploadRecord":
				return _lp.frame.configsets.devicelinkactions_list.action.uploadRecord;
				break;
			case "playTipVoice":
				return _lp.frame.configsets.devicelinkactions_list.action.playTipVoice;
				break;
			default:
				return action;
				break;					
		}
	},
		
	devicelinkactions_formatter_src:function(srcrestype,eventsrcno){	
		switch(srcrestype.toLowerCase()){
			case "wifi":
				return "WiFi"+(parseInt(eventsrcno)+1);
				break;
			case "wm":
				return "Wireless"+(parseInt(eventsrcno)+1);
				break;
			case "iv":
				return "Camera"+(parseInt(eventsrcno)+1);
				break;
			case "ia":
				return "AudioIn"+(parseInt(eventsrcno)+1);
				break;	
			case "oa":
				return "AudioTalk"+(parseInt(eventsrcno)+1);
				break;	
			case "ptz":
				return "PTZ"+(parseInt(eventsrcno)+1);
				break;
			case "idl":
				return "AlertIn"+(parseInt(eventsrcno)+1);
				break;
			case "odl":
				return "AlertOut"+(parseInt(eventsrcno)+1);
				break;
			case "sg":
				return "SerialPort"+(parseInt(eventsrcno)+1);
				break;
			case "gps":
				return "GNSS"+(parseInt(eventsrcno)+1);
				break;
			default:
				return srcrestype+(parseInt(eventsrcno)+1);
				break;					
		}
	},
							
	devicelinkactions_formatter_model:function(model){
			switch(model){
			case "None":
				return _lp.frame.configsets.devicelinkactions_list.model.None;
				break;
			case "Always":
				return _lp.frame.configsets.devicelinkactions_list.model.Always;
				break;
			case "Everyday":
				return _lp.frame.configsets.devicelinkactions_list.model.Everyday;
				break;
			case "Weekly":
				return _lp.frame.configsets.devicelinkactions_list.model.Weekly;
				break;	
			default:	
				return model;
				break;					
		}
	},
	devicelinkactions_formatter_week:function(value){
			switch(value){
			case 0:
				return _lp.frame.configsets.devicelinkactions_list.week.Sun;
				break;
			case 1:
				return _lp.frame.configsets.devicelinkactions_list.week.Mon;
				break;
			case 2:
				return _lp.frame.configsets.devicelinkactions_list.week.Tues;
				break;
			case 3:
				return _lp.frame.configsets.devicelinkactions_list.week.Wed;
				break;
			case 4:
				return _lp.frame.configsets.devicelinkactions_list.week.Thur;
				break;
			case 5:
				return _lp.frame.configsets.devicelinkactions_list.week.Fri;
				break;
			case 6:
				return _lp.frame.configsets.devicelinkactions_list.week.Sat;
				break;			
			default:	
				break;					
		}
	},	
	
	open_add_devicelinkactions_dlg:function(){
		
		eventParams = '<Res Type="ST" Idx="0" OptID="F_ST_SupportedEventSets" ></Res>';
	    var events = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,eventParams);
	    
	    actionParams = '<Res Type="ST" Idx="0" OptID="F_ST_SupportedActionSets" ></Res>';
	    var actions = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,actionParams);
	   
		var html = '';
		html += '<form id="add_devicelinkactions_dlg_form" method="post" novalidate><table style="padding:25px 25px 0px 25px;">';
		html += '<tr height=30>';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.eventType+':</td>';
			html += '<td>';
			html += '<select id="add_devicelinkactions_eventType" class="easyui-combobox"  data-options="required:true,editable:false,onSelect:function(r){_cfs.add_devicelinkactions_eventType_change(r.value);}" style="width:200px;">';
			
			$(events).find("Event").each(function(k,n){	
				var d = $(n);
				var event = d.attr("ID");
				var type = d.attr("ResType");
				html += '<option value='+event+':'+type+' >'+_cfs.devicelinkactions_formatter_eventType(event)+'</option>';
			})
		
			html += '</select></td>';
			html += '<td style="padding-left:25px;">'+_lp.frame.configsets.devicelinkactions_list.col.src+':</td>';
			html += '<td id="add_devicelinkactions_eventsrc"></td>';
		html += '</tr>';

		html += '<tr height=30>';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.action+':</td>';
			html += '<td><select id="add_devicelinkactions_action" class="easyui-combobox"  data-options="required:true,editable:false,onSelect:function(r){_cfs.add_devicelinkactions_action_change(r.value);}" style="width:200px;">';
		
			$(actions).find("it").each(function(k,n){
				html += '<option value='+$(n).text()+' >'+_cfs.devicelinkactions_formatter_action($(n).text())+'</option>';
			})
				
			html += '</select></td>';
		
		html += '</tr>';

		html += '<tr height=30>';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.actionParams+':</td>';
		html += '</tr>';
		
		html += '<tr height=30><td colspan="3" style="padding-left:15px;"><table id="add_devicelinkactions_actionparams"> </table></td></tr>';
		
		html += '<tr height=30 >';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.modelTime+':</td>';
		html += '</tr>';
		
		html += '<tr height=30 >';
			html += '<td style="padding-left:18px;">'+_lp.frame.configsets.devicelinkactions_list.col.models+':</td>';
			html += '<td>';
			html += '<select id="add_devicelinkactions_model" class="easyui-combobox"  data-options="required:true,editable:false,onSelect:function(r){_cfs.add_devicelinkactions_model_change(r.value);}" style="width:200px;">';
				html += '<option value="None">'+_lp.frame.configsets.devicelinkactions_list.model.None+'</option>';
				html += '<option value="Always" selected>'+_lp.frame.configsets.devicelinkactions_list.model.Always+'</option>';
				html += '<option value="Everyday">'+_lp.frame.configsets.devicelinkactions_list.model.Everyday+'</option>';
				html += '<option value="Weekly">'+_lp.frame.configsets.devicelinkactions_list.model.Weekly+'</option>';
			html += '</select></td>';
		html += '</tr>';
		html += '<tr><td colspan="4"><table id="add_devicelinkactions_models" style="padding-left:15px;" ></table></td></tr>';
		
		html += '</table></form>';
		
		html += '</div>';	
		
			
		$('#devicelinkactions_dlg').show();
		$('#devicelinkactions_dlg').dialog({
		    title: _lp.frame.configsets.devicelinkactions_list.addTitle,
		    width: 650,
		    height: 800,
		    closed: false,
		    cache: false,
		    content:html,
		    buttons:[{
				text:_lp.frame.configsets.btns.ok,
				handler:function(){
					if($('#add_devicelinkactions_dlg_form').form("validate") == true){

						var params = "";
							
						params += '<Res Type="ST" Idx="0" OptID="F_ST_DeviceLinkActions"  Stream=""  >';
						params += '<Param>';
						params += '<deviceLinkActions>';	
						var rows = $('#F_ST_DeviceLinkActions_grid').datagrid("getRows");
						
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];
						
							var devicelinkaction = decodeURIComponent(r.devicelinkaction);
				
							devicelinkaction = devicelinkaction.replace(' xmlns="http://www.w3.org/1999/xhtml"',"");
							params += devicelinkaction;						
						}
						
						params += _cfs.add_devicelinkactions_params(); 
					
						params += '</deviceLinkActions>';
						params += '</Param>';
    					params += '</Res>';
    					
						params = params.replace(/devicelinkaction/g,"deviceLinkAction")
						params = params.replace(/eventtype/g,"eventType")
						params = params.replace(/srcrestype/g,"srcResType")
						params = params.replace(/eventsrcno/g,"eventSrcNo")
						params = params.replace(/guardmap/g,"guardMap")
						params = params.replace(/param/g,"Param")
						params = params.replace(/alertoutno/g,"alertOutNo")
						params = params.replace(/alertoutstatus/g,"alertOutStatus")
						params = params.replace(/inputvideono/g,"inputVideoNo")
						params = params.replace(/num/g,"Num")
						params = params.replace(/interval/g,"Interval")	
							
						params = params.replace(/mode/g,"Mode")	
						params = params.replace(/none/g,"None")
						params = params.replace(/always/g,"Always")
						params = params.replace(/everyday/g,"Everyday")
						params = params.replace(/weekly/g,"Weekly")
						params = params.replace(/time/g,"Time")	
							
						params = params.replace(/begin/g,"Begin")
						params = params.replace(/end/g,"End")
						params = params.replace(/weekday/g,"Weekday")					
												
				
						var rv = P_LY.Config.SetComplexEx(
							_cfs.connectId,
							{
								puid:_cfs.pu.puid,
								language:_cf.language,
								resType:"ST",
								resIdx:0,
								dstRes:params
							}
						).response;
									
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note19,
						                timeout:4000,
						                showType:'slide'
						            });
									$('#devicelinkactions_dlg').dialog('close');
									_cfs.refresh_resource_configsetsbycateid('Linkage','ST','0')
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				}
			},{
				text:_lp.frame.configsets.btns.close,
				handler:function(){
					$('#devicelinkactions_dlg').dialog('close');
				}
			}],
		    modal: true
		});
		
		_cfs.add_devicelinkactions_eventType_change($("#add_devicelinkactions_eventType").combobox('getValue'));
		_cfs.add_devicelinkactions_action_change($("#add_devicelinkactions_action").combobox('getValue'));
	},
		
	actionparam:new Object(),
		
	guardmap:new Array(),	
					
	open_edit_devicelinkactions_dlg:function(rowIndex){
		var rows = $('#F_ST_DeviceLinkActions_grid').datagrid("getRows");
		var c = null;
		if(rows[rowIndex]){
			c = rows[rowIndex];
		}else{
			return;
		}
	
		var d = $(decodeURIComponent(c.devicelinkaction));
	
		var eventType = d.find("eventtype").text();
		var action = d.find("action").text();
		var srcRestype = d.find("srcrestype").text();
		var eventSrcNo = d.find("eventsrcno").text();
		var src = _cfs.devicelinkactions_formatter_src(srcRestype,eventSrcNo);
		var p = d.find("param");
		var actionparams = d.find("actionparams");
	
		var model = $(p).attr("mode");
			
		var actionparam = {};
		actionparam.action = action;
	
		if(action == "alertOut"){
			actionparam.alertOutNo = $(actionparams).find("alertoutno").text();
			actionparam.alertOutStatus = $(actionparams).find("alertoutstatus").text();
		}else if(action == "record"){
			actionparam.inputVideoNo = $(actionparams).find("inputvideono").text();
			actionparam.duration = $(actionparams).find("duration").text();
		}else if(action == "snapShot"){
			actionparam.inputVideoNo = $(actionparams).find("inputvideono").text();
			actionparam.Num = $(actionparams).find("num").text();
			actionparam.Interval = $(actionparams).find("interval").text();
		}else if(action == "moveToPresetPosition"){
			actionparam.inputVideoNo = $(actionparams).find("inputvideono").text();
			actionparam.presetPositionNo = $(actionparams).find("presetpositionno").text();
		}
		_cfs.actionparam = actionparam;
		var guardmap = new Array();
		if(model == "Everyday"){
			d.find("time").each(function(k,n){
				var dt = $(n);
				guardmap.push({start:dt.attr("begin"),end:dt.attr("end")});
			})
		}
		else if(model == "Weekly"){
			for(var i = 0 ;i < 7 ;i++){
				var weektime = new Array();
				d.find("time").each(function(k,n){
					var dt = $(n);
					var weekday = dt.attr("weekday");
					if(weekday == i){
						weektime.push({start:dt.attr("begin"),end:dt.attr("end")})
					}
				})
				guardmap.push({weekday:i,weektime:weektime});		
			}
		}
		_cfs.guardmap = guardmap;	
		eventParams = '<Res Type="ST" Idx="0" OptID="F_ST_SupportedEventSets" ></Res>';
	    var events = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,eventParams);
	    
	    actionParams = '<Res Type="ST" Idx="0" OptID="F_ST_SupportedActionSets" ></Res>';
	    var actions = _p.get_res_config_id_sets(_cfs.connectId,_cfs.pu.puid,actionParams);
	   	
		var html = '';
		html += '<form id="edit_devicelinkactions_dlg_form" method="post" novalidate><table style="padding:25px 25px 0px 25px;">';
		html += '<tr height=30>';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.eventType+':</td>';
			html += '<td>';
			html += '<select id="edit_devicelinkactions_eventType" class="easyui-combobox" data-options="editable:false,onSelect:function(r){_cfs.edit_devicelinkactions_eventType_change(r.value,\''+srcRestype+'\','+eventSrcNo+');}" style="width:200px;">';
		
			$(events).find("Event").each(function(k,n){
				var d = $(n);
				var event = d.attr("ID");
				var type = d.attr("ResType");
				html += '<option value='+event+':'+type+' '+(event == eventType ? 'selected' : '')+' >'+_cfs.devicelinkactions_formatter_eventType(event)+'</option>';
			})
		
			html += '</select></td>';
			html += '<td style="padding-left:25px;">'+_lp.frame.configsets.devicelinkactions_list.col.src+':</td>';
			html += '<td id="edit_devicelinkactions_eventsrc"></td>';
		html += '</tr>';

		html += '<tr height=30>';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.action+':</td>';
			html += '<td><select id="edit_devicelinkactions_action" class="easyui-combobox"  data-options="required:true,editable:false,onSelect:function(r){_cfs.edit_devicelinkactions_action_change(r.value,\''+action+'\');}" style="width:200px;">';
		
			$(actions).find("it").each(function(k,n){
				html += '<option value='+$(n).text()+' '+($(n).text() == action ? 'selected' : '')+'  >'+_cfs.devicelinkactions_formatter_action($(n).text())+'</option>';
			})
				
			html += '</select></td>';
		
		html += '</tr>';

		html += '<tr height=30>';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.actionParams+':</td>';
		html += '</tr>';
		
		html += '<tr height=30><td colspan="3" style="padding-left:15px;"><table id="edit_devicelinkactions_actionparams"> </table></td></tr>';
		
		html += '<tr height=30 >';
			html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.modelTime+':</td>';
		html += '</tr>';
		
		html += '<tr height=30 >';
			html += '<td style="padding-left:18px;">'+_lp.frame.configsets.devicelinkactions_list.col.models+':</td>';
			html += '<td>';
			html += '<select id="edit_devicelinkactions_model" class="easyui-combobox"  data-options="required:true,editable:false,onSelect:function(r){_cfs.edit_devicelinkactions_model_change(r.value,\''+model+'\');}" style="width:200px;">';
				html += '<option value="None" '+( model == "None" ? "selected" : "")+'>'+_lp.frame.configsets.devicelinkactions_list.model.None+'</option>';
				html += '<option value="Always" '+( model == "Always" ? "selected" : "")+'>'+_lp.frame.configsets.devicelinkactions_list.model.Always+'</option>';
				html += '<option value="Everyday" '+( model == "Everyday" ? "selected" : "")+'>'+_lp.frame.configsets.devicelinkactions_list.model.Everyday+'</option>';
				html += '<option value="Weekly" '+( model == "Weekly" ? "selected" : "")+'>'+_lp.frame.configsets.devicelinkactions_list.model.Weekly+'</option>';
			html += '</select></td>';
		html += '</tr>';
		html += '<tr><td colspan="4"><table id="edit_devicelinkactions_models" style="padding-left:15px;" ></table></td></tr>';
		
		html += '</table></form>';
		
		html += '</div>';	
		
			
		$('#devicelinkactions_dlg').show();
		$('#devicelinkactions_dlg').dialog({
		    title: _lp.frame.configsets.devicelinkactions_list.addTitle,
		    width: 650,
		    height: 800,
		    closed: false,
		    cache: false,
		    content:html,
		    buttons:[{
				text:_lp.frame.configsets.btns.ok,
				handler:function(){
					if($('#edit_devicelinkactions_dlg_form').form("validate") == true){

						var params = "";
							
						params += '<Res Type="ST" Idx="0" OptID="F_ST_DeviceLinkActions"  Stream=""  >';
						params += '<Param>';
						params += '<devicelinkactions>';	
						var rows = $('#F_ST_DeviceLinkActions_grid').datagrid("getRows");
					
						for(var i = 0;i < rows.length;i++){
							if(rowIndex == i){
								params += _cfs.edit_devicelinkactions_params(); 
								continue;
							}
							var r = rows[i];
							var devicelinkaction = decodeURIComponent(r.devicelinkaction);
							devicelinkaction = devicelinkaction.replace(' xmlns="http://www.w3.org/1999/xhtml"',"");
							params += devicelinkaction;						
						}

						params += '</devicelinkactions>';
						params += '</Param>';
    					params += '</Res>';
    					
						params = params.replace(/devicelinkaction/g,"deviceLinkAction")
						params = params.replace(/eventtype/g,"eventType")
						params = params.replace(/srcrestype/g,"srcResType")
						params = params.replace(/eventsrcno/g,"eventSrcNo")
						params = params.replace(/guardmap/g,"guardMap")
						params = params.replace(/param/g,"Param")
						params = params.replace(/alertoutno/g,"alertOutNo")
						params = params.replace(/alertoutstatus/g,"alertOutStatus")
						params = params.replace(/inputvideono/g,"inputVideoNo")
						params = params.replace(/num/g,"Num")
						params = params.replace(/interval/g,"Interval")	
							
						params = params.replace(/mode/g,"Mode")	
						params = params.replace(/none/g,"None")
						params = params.replace(/always/g,"Always")
						params = params.replace(/everyday/g,"Everyday")
						params = params.replace(/weekly/g,"Weekly")
						params = params.replace(/time/g,"Time")	
							
						params = params.replace(/begin/g,"Begin")
						params = params.replace(/end/g,"End")
						params = params.replace(/weekday/g,"Weekday")
									
						var rv = P_LY.Config.SetComplexEx(
							_cfs.connectId,
							{
								puid:_cfs.pu.puid,
								language:_cf.language,
								resType:"ST",
								resIdx:0,
								dstRes:params
							}
						).response;
					
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note20,
						                timeout:4000,
						                showType:'slide'
						            });
									$('#devicelinkactions_dlg').dialog('close');
									_cfs.refresh_resource_configsetsbycateid('Linkage','ST','0')
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				}
			},{
				text:_lp.frame.configsets.btns.close,
				handler:function(){
					$('#devicelinkactions_dlg').dialog('close');
				}
			}],
		    modal: true
		});
		
		_cfs.edit_devicelinkactions_eventType_change($("#edit_devicelinkactions_eventType").combobox('getValue'),srcRestype,eventSrcNo);
		_cfs.edit_devicelinkactions_action_change($("#edit_devicelinkactions_action").combobox('getValue'),action);
		_cfs.edit_devicelinkactions_model_change($("#edit_devicelinkactions_model").combobox('getValue'),model);
	},	
	
	del_devicelinkactions:function(rowIndex){

		var rows = $('#F_ST_DeviceLinkActions_grid').datagrid("getRows");
		var resIdx = 0;
		
		var params = "";
							
		params += '<Res Type="ST" Idx="0" OptID="F_ST_DeviceLinkActions"  Stream=""  >';
		params += '<Param>';
		params += '<devicelinkactions>';	
		var rows = $('#F_ST_DeviceLinkActions_grid').datagrid("getRows");
	
		for(var i = 0;i < rows.length;i++){
			if(rowIndex == i) continue;
			var r = rows[i];
			var devicelinkaction = decodeURIComponent(r.devicelinkaction);
			devicelinkaction = devicelinkaction.replace(' xmlns="http://www.w3.org/1999/xhtml"',"");
			params += devicelinkaction;						
		}
			
		params += '</devicelinkactions>';
		params += '</Param>';
		params += '</Res>';
		
		params = params.replace(/devicelinkaction/g,"deviceLinkAction")
		params = params.replace(/eventtype/g,"eventType")
		params = params.replace(/srcrestype/g,"srcResType")
		params = params.replace(/eventsrcno/g,"eventSrcNo")
		params = params.replace(/guardmap/g,"guardMap")
		params = params.replace(/param/g,"Param")
		params = params.replace(/alertoutno/g,"alertOutNo")
		params = params.replace(/alertoutstatus/g,"alertOutStatus")
		params = params.replace(/inputvideono/g,"inputVideoNo")
		params = params.replace(/num/g,"Num")
		params = params.replace(/interval/g,"Interval")	
			
		params = params.replace(/mode/g,"Mode")	
		params = params.replace(/none/g,"None")
		params = params.replace(/always/g,"Always")
		params = params.replace(/everyday/g,"Everyday")
		params = params.replace(/weekly/g,"Weekly")
		params = params.replace(/time/g,"Time")	
			
		params = params.replace(/begin/g,"Begin")
		params = params.replace(/end/g,"End")
		params = params.replace(/weekday/g,"Weekday")		
		
    	var rv = P_LY.Config.SetComplexEx(
			_cfs.connectId,
			{
				puid:_cfs.pu.puid,
				language:_cf.language,
				resType:"ST",
				resIdx:0,
				dstRes:params
			}
		).response;
		
		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return;
			}
			if(rv.M.C.Res){
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note20,
		                timeout:4000,
		                showType:'slide'
		            });
					setTimeout(function(){
						_cfs.refresh_resource_configsetsbycateid('Linkage','ST','0')
					},100);
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
			
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError,
                timeout:4000,
                showType:'slide'
            });
    	}
	},	
			
	add_devicelinkactions_params:function(){
		var eventType = $("#add_devicelinkactions_eventType").combobox('getValue');
		var stream = $("#add_devicelinkactions_stream").combobox('getValue');
		var action = $("#add_devicelinkactions_action").combobox('getValue');
		var model = $("#add_devicelinkactions_model").combobox('getValue');
		
		var params = '<devicelinkaction>';
		var eventtype = eventType.split(":")[0];
		var restype = eventType.split(":")[1];

		params += '<eventType>'+eventtype+'</eventType>';
		params += '<srcResType>'+restype+'</srcResType>';
		params += '<eventSrcNo>'+stream+'</eventSrcNo>';
		params += '<guardmap><Param mode="'+model+'" />';
		
		if(model == "Everyday"){
			var s1_time = $("#everyday-start0").timespinner('getValue')+":00";
			var e1_time = $("#everyday-end0").timespinner('getValue')+":00";
			var s2_time = $("#everyday-start1").timespinner('getValue')+":00";
			var e2_time = $("#everyday-end1").timespinner('getValue')+":00";
			var s3_time = $("#everyday-start2").timespinner('getValue')+":00";
			var e3_time = $("#everyday-end2").timespinner('getValue')+":00";
		
			params += '<weekly></weekly><everyday><time begin="'+s1_time+'" end="'+e1_time+'"></time><time begin="'+s2_time+'" end="'+e2_time+'"></time><time begin="'+s3_time+'" end="'+e3_time+'"></time></everyday>';
		}
		else if(model == "Weekly"){
			params += '<weekly>';
			for(i = 0;i < 7;i++){
				var s1_time = $("#Weekly"+i+"-start0").timespinner('getValue')+":00";
				var e1_time = $("#Weekly"+i+"-end0").timespinner('getValue')+":00";
				var s2_time = $("#Weekly"+i+"-start1").timespinner('getValue')+":00";
				var e2_time = $("#Weekly"+i+"-end1").timespinner('getValue')+":00";
				var s3_time = $("#Weekly"+i+"-start2").timespinner('getValue')+":00";
				var e3_time = $("#Weekly"+i+"-end2").timespinner('getValue')+":00";
				params += '<time begin="'+s1_time+'" end="'+e1_time+'" Weekday="'+i+'"></time><time begin="'+s2_time+'" end="'+e2_time+'" Weekday="'+i+'"></time><time begin="'+s3_time+'" end="'+e3_time+'" Weekday="'+i+'"></time>'
			}
			params += '</weekly><everyday></everyday>';
		}
		
		params += '</guardmap>';
		params += '<action>'+action+'</action>';
		params += '<actionParams>';
	
		if(action == "alertOut"){
			var stream = $("#add_devicelinkactions_alertOut_stream").combobox('getValue');
			var action = $("#add_devicelinkactions_alertOut_action").combobox('getValue');
			params += '<alertOutNo>'+stream+'</alertOutNo>';
			params += '<alertOutStatus>'+action+'</alertOutStatus>';
		}
		else if(action == "record"){
			var stream = $("#add_devicelinkactions_record_stream").combobox('getValue');
			var recordTime = $("#add_devicelinkactions_record_time").val();
			params += '<inputVideoNo>'+stream+'</inputVideoNo>';
			params += '<duration>'+recordTime+'</duration>';
		}
		else if(action == "snapShot"){
			var stream = $("#add_devicelinkactions_snapShot_stream").combobox('getValue');
			var snapShotMumber = $("#add_devicelinkactions_snapShot_munber").val();
			var snapShotInterval = $("#add_devicelinkactions_snapShot_time").val();
			params += '<inputVideoNo>'+stream+'</inputVideoNo>';
			params += '<Num>'+snapShotMumber+'</Num>';
			params += '<Interval>'+snapShotInterval+'</Interval>';
		}
		else if(action == "moveToPresetPosition"){
			var stream = $("#add_devicelinkactions_moveToPresetPosition_stream").combobox('getValue');
			var presetPositionNo = $("#add_devicelinkactions_moveToPresetPosition_munber").combobox('getValue');
			params += '<inputVideoNo>'+stream+'</inputVideoNo>';
			params += '<presetPositionNo>'+presetPositionNo+'</presetPositionNo>';
		}
		params += '</actionParams>';
		params += '</devicelinkaction>';
		
		return params;
	},
		
	edit_devicelinkactions_params:function(){
		var eventType = $("#edit_devicelinkactions_eventType").combobox('getValue');
		var stream = $("#edit_devicelinkactions_stream").combobox('getValue');
		var action = $("#edit_devicelinkactions_action").combobox('getValue');
		var model = $("#edit_devicelinkactions_model").combobox('getValue');
		
		var params = '<devicelinkaction>';
		var eventtype = eventType.split(":")[0];
		var restype = eventType.split(":")[1];

		params += '<eventType>'+eventtype+'</eventType>';
		params += '<srcResType>'+restype+'</srcResType>';
		params += '<eventSrcNo>'+stream+'</eventSrcNo>';
		params += '<guardmap><param mode="'+model+'" />';
		
		if(model == "Everyday"){
			var s1_time = $("#edit-everyday-start0").timespinner('getValue')+":00";
			var e1_time = $("#edit-everyday-end0").timespinner('getValue')+":00";
			var s2_time = $("#edit-everyday-start1").timespinner('getValue')+":00";
			var e2_time = $("#edit-everyday-end1").timespinner('getValue')+":00";
			var s3_time = $("#edit-everyday-start2").timespinner('getValue')+":00";
			var e3_time = $("#edit-everyday-end2").timespinner('getValue')+":00";
		
			params += '<weekly></weekly><everyday><time begin="'+s1_time+'" end="'+e1_time+'"></time><time begin="'+s2_time+'" end="'+e2_time+'"></time><time begin="'+s3_time+'" end="'+e3_time+'"></time></everyday>';
		}
		else if(model == "Weekly"){
			params += '<weekly>';
			for(i = 0;i < 7;i++){
				var s1_time = $("#edit-Weekly"+i+"-start0").timespinner('getValue')+":00";
				var e1_time = $("#edit-Weekly"+i+"-end0").timespinner('getValue')+":00";
				var s2_time = $("#edit-Weekly"+i+"-start1").timespinner('getValue')+":00";
				var e2_time = $("#edit-Weekly"+i+"-end1").timespinner('getValue')+":00";
				var s3_time = $("#edit-Weekly"+i+"-start2").timespinner('getValue')+":00";
				var e3_time = $("#edit-Weekly"+i+"-end2").timespinner('getValue')+":00";
				params += '<time begin="'+s1_time+'" end="'+e1_time+'" Weekday="'+i+'"></time><time begin="'+s2_time+'" end="'+e2_time+'" Weekday="'+i+'"></time><time begin="'+s3_time+'" end="'+e3_time+'" Weekday="'+i+'"></time>'
			}
			params += '</weekly><everyday></everyday>';
		}
		
		params += '</guardmap>';
		params += '<action>'+action+'</action>';
		params += '<actionparams>';
	
		if(action == "alertOut"){
			var stream = $("#edit_devicelinkactions_alertOut_stream").combobox('getValue');
			var action = $("#edit_devicelinkactions_alertOut_action").combobox('getValue');
			params += '<alertOutNo>'+stream+'</alertOutNo>';
			params += '<alertOutStatus>'+action+'</alertOutStatus>';
		}
		else if(action == "record"){
			var stream = $("#edit_devicelinkactions_record_stream").combobox('getValue');
			var recordTime = $("#edit_devicelinkactions_record_time").val();
			params += '<inputVideoNo>'+stream+'</inputVideoNo>';
			params += '<duration>'+recordTime+'</duration>';
		}
		else if(action == "snapShot"){
			var stream = $("#edit_devicelinkactions_snapShot_stream").combobox('getValue');
			var snapShotMumber = $("#edit_devicelinkactions_snapShot_munber").val();
			var snapShotInterval = $("#edit_devicelinkactions_snapShot_time").val();
			params += '<inputVideoNo>'+stream+'</inputVideoNo>';
			params += '<Num>'+snapShotMumber+'</Num>';
			params += '<Interval>'+snapShotInterval+'</Interval>';
		}
		else if(action == "moveToPresetPosition"){
			var stream = $("#edit_devicelinkactions_moveToPresetPosition_stream").combobox('getValue');
			var presetPositionNo = $("#edit_devicelinkactions_moveToPresetPosition_munber").combobox('getValue');
			params += '<inputVideoNo>'+stream+'</inputVideoNo>';
			params += '<presetPositionNo>'+presetPositionNo+'</presetPositionNo>';
		}
		params += '</actionparams>';
		params += '</devicelinkaction>';
		
		return params;
	},	
		
			
	add_devicelinkactions_eventType_change:function(value){
		var resType = value.split(":")[1];
	
		html = '<select id="add_devicelinkactions_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
		for(var i = 0;i < _cfs.pu.childResource.length;i++){
			if(_cfs.pu.childResource[i].type == resType){
				html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
				idx = _cfs.pu.childResource[i].idx;
			}
		}
		
		html += '</select>';
	
		$("#add_devicelinkactions_eventsrc").html(html);
		$.parser.parse("#add_devicelinkactions_eventsrc");
	},
				
	edit_devicelinkactions_eventType_change:function(value,srcRestype,eventSrcNo){
	
		var event = value.split(":")[0];
		var resType = value.split(":")[1];
		
		html = '<select id="edit_devicelinkactions_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
		for(var i = 0;i < _cfs.pu.childResource.length;i++){
			if(_cfs.pu.childResource[i].type == resType){
				if(srcRestype == resType){
					html += '<option value="'+_cfs.pu.childResource[i].idx+'" '+( eventSrcNo == _cfs.pu.childResource[i].idx ? "selected" : "")+'>'+_cfs.pu.childResource[i].name+'</option>';	
				}else{
					html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
				}
			}
		}
		
		html += '</select>';
	
		$("#edit_devicelinkactions_eventsrc").html(html);
		$.parser.parse("#edit_devicelinkactions_eventsrc");
	},		
		
	add_devicelinkactions_action_change:function(value){
		var html = "";
		if(value == "alertOut"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.alertOutStream+':</td>';
				html += '<td><select id="add_devicelinkactions_alertOut_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == "IDL"){
					html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
					idx = _cfs.pu.childResource[i].idx;
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.alertOutAction+':</td>';
				html += '<td><select id="add_devicelinkactions_alertOut_action" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
					html += '<option value="connect">'+_lp.frame.configsets.devicelinkactions_list.alertOutStatus.connect+'</option>';
					html += '<option value="break">'+_lp.frame.configsets.devicelinkactions_list.alertOutStatus.breaks+'</option>';
					html += '<option value="pulse">'+_lp.frame.configsets.devicelinkactions_list.alertOutStatus.pulse+'</option>';	
				html += '</select></td>';
			html += '</tr>';
		}
		else if(value == "record"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.inputVideoNo+':</td>';
				html += '<td><select id="add_devicelinkactions_record_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == "IV"){
					html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
					idx = _cfs.pu.childResource[i].idx;
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.recordTime+':</td>';
				html += '<td><input id="add_devicelinkactions_record_time" class="easyui-textbox" value="5" required="true" style="width:160px;"><span>'+_lp.frame.configsets.devicelinkactions_list.col.recordUnit+'</span></td>';
			html += '</tr>';
		}
		else if(value == "snapShot"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.inputVideoNo+':</td>';
				html += '<td><select id="add_devicelinkactions_snapShot_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == "IV"){
						html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
						idx = _cfs.pu.childResource[i].idx;
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotMumber+':</td>';
				html += '<td><input id="add_devicelinkactions_snapShot_munber" class="easyui-textbox" value="1" required="true" style="width:160px;"><span>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotUnit+'</span></td>';
			html += '</tr>';
					
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotInterval+':</td>';
				html += '<td><input id="add_devicelinkactions_snapShot_time" class="easyui-textbox" value="500" required="true" style="width:160px;"><span>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotTime+'</span></td>';
			html += '</tr>';
		}
		else if(value == "moveToPresetPosition"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.inputVideoNo+':</td>';
				html += '<td><select id="add_devicelinkactions_moveToPresetPosition_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
				if(_cfs.pu.childResource[i].type == "IV"){
					html += '<option value="'+_cfs.pu.childResource[i].idx+'">'+_cfs.pu.childResource[i].name+'</option>';
					idx = _cfs.pu.childResource[i].idx;
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.presetPositionNo+':</td>';
				html += '<td><select id="add_devicelinkactions_moveToPresetPosition_munber" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
					for(var i = 1;i <= 255;i++){
						html += '<option value='+i.toString()+'>'+i+'</option>';
					}
				html += '</select></td>';
			html += '</tr>';
		}
		//<tr><td style="padding-left:25px;">111</td></tr><tr><td style="padding-left:25px;">222</td></tr>

		$("#add_devicelinkactions_actionparams").html(html);
		$.parser.parse("#add_devicelinkactions_actionparams");
	},	
		
	edit_devicelinkactions_action_change:function(value,action){
		var html = "";
		if(value == "alertOut"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.alertOutStream+':</td>';
				html += '<td><select id="edit_devicelinkactions_alertOut_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
					if(_cfs.pu.childResource[i].type == "IDL"){
						if(value == action){
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" '+(_cfs.pu.childResource[i].idx == _cfs.actionparam.alertOutNo ? 'selected' : '')+' >'+_cfs.pu.childResource[i].name+'</option>';
						}else{
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" >'+_cfs.pu.childResource[i].name+'</option>';
						}
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.alertOutAction+':</td>';
				html += '<td><select id="edit_devicelinkactions_alertOut_action" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
					html += '<option value="connect" '+(_cfs.actionparam.alertOutStatus == "connect" ? 'selected' : '')+' >'+_lp.frame.configsets.devicelinkactions_list.alertOutStatus.connect+'</option>';
					html += '<option value="break" '+(_cfs.actionparam.alertOutStatus == "break" ? 'selected' : '')+' >'+_lp.frame.configsets.devicelinkactions_list.alertOutStatus.breaks+'</option>';
					html += '<option value="pulse" '+(_cfs.actionparam.alertOutStatus == "pulse" ? 'selected' : '')+' >'+_lp.frame.configsets.devicelinkactions_list.alertOutStatus.pulse+'</option>';	
				html += '</select></td>';
			html += '</tr>';
		}
		else if(value == "record"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.inputVideoNo+':</td>';
				html += '<td><select id="edit_devicelinkactions_record_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
					if(_cfs.pu.childResource[i].type == "IV"){
						if(value == action){
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" '+(_cfs.actionparam.inputVideoNo == _cfs.pu.childResource[i].idx ? 'selected' : '')+' >'+_cfs.pu.childResource[i].name+'</option>';
						}else{
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" >'+_cfs.pu.childResource[i].name+'</option>';
						}
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.recordTime+':</td>';
				html += '<td><input id="edit_devicelinkactions_record_time" class="easyui-textbox" value='+(_cfs.actionparam.duration || 5)+' required="true" style="width:160px;"><span>'+_lp.frame.configsets.devicelinkactions_list.col.recordUnit+'</span></td>';
			html += '</tr>';
		}
		else if(value == "snapShot"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.inputVideoNo+':</td>';
				html += '<td><select id="edit_devicelinkactions_snapShot_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
					if(_cfs.pu.childResource[i].type == "IV"){
						if(value == action){
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" '+(_cfs.actionparam.inputVideoNo == _cfs.pu.childResource[i].idx ? 'selected' : '')+'>'+_cfs.pu.childResource[i].name+'</option>';
						}else{
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" >'+_cfs.pu.childResource[i].name+'</option>';
						}
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotMumber+':</td>';
				html += '<td><input id="edit_devicelinkactions_snapShot_munber" class="easyui-textbox" value='+(_cfs.actionparam.Num || 1)+' required="true" style="width:160px;"><span>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotUnit+'</span></td>';
			html += '</tr>';
					
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotInterval+':</td>';
				html += '<td><input id="edit_devicelinkactions_snapShot_time" class="easyui-textbox" value='+(_cfs.actionparam.Interval || 500) +' required="true" style="width:160px;"><span>'+_lp.frame.configsets.devicelinkactions_list.col.snapShotTime+'</span></td>';
			html += '</tr>';
		}
		else if(value == "moveToPresetPosition"){
			html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.inputVideoNo+':</td>';
				html += '<td><select id="edit_devicelinkactions_moveToPresetPosition_stream" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
			
				for(var i = 0;i < _cfs.pu.childResource.length;i++){
					if(_cfs.pu.childResource[i].type == "IV"){
						if(value == action){
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" '+(_cfs.actionparam.inputVideoNo == _cfs.pu.childResource[i].idx ? 'selected' : '')+'>'+_cfs.pu.childResource[i].name+'</option>';
						}else{
							html += '<option value="'+_cfs.pu.childResource[i].idx+'" >'+_cfs.pu.childResource[i].name+'</option>';
						}
					}
				}
				html += '</select></td>';
				
			html += '</tr>';
				html += '<tr height=30>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.col.presetPositionNo+':</td>';
				html += '<td><select id="edit_devicelinkactions_moveToPresetPosition_munber" class="easyui-combobox"  data-options="editable:false" style="width:200px;">';
					for(var i = 1;i <= 255;i++){
						html += '<option value='+i.toString()+' '+(_cfs.actionparam.presetPositionNo == i ? 'selected' : '')+'>'+i+'</option>';
					}
				html += '</select></td>';
			html += '</tr>';
		}	
		
		$("#edit_devicelinkactions_actionparams").html(html);
		$.parser.parse("#edit_devicelinkactions_actionparams");
	},		
		
	add_devicelinkactions_model_change:function(value){
		var html = "";
		if(value == "Everyday"){
			html += '<tr height=50>';
				html += '<td></td>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.time.time1+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time2+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time3+'：</td>';
			html += '</tr>';
			html += '<tr>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.model.Everyday+':</td>';
				html += "<td><input id=\"everyday-start0\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"08:00\"><span>-</span><input id=\"everyday-end0\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"10:00\"></td>";
				html += "<td width=150 style=\"padding-left:25px\"><input id=\"everyday-start1\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"12:00\"><span>-</span><input id=\"everyday-end1\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"14:00\"></td>";
				html += "<td width=150 style=\"padding-left:25px\"><input id=\"everyday-start2\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"15:00\"><span>-</span><input id=\"everyday-end2\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"18:00\"></td>";
			html += '</tr>';
		}
		else if(value == "Weekly"){
			html += '<tr height=50>';
				html += '<td></td>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.time.time1+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time2+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time3+'：</td>';
			html += '</tr>';
			for(i = 0;i < 7;i++){
				html += '<tr height=45>';
				html += '<td>'+_cfs.devicelinkactions_formatter_week(i)+':</td>';
				html += '<td><input id="Weekly'+i+'-start0" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="08:00"><span>-</span><input id="Weekly'+i+'-end0" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="10:00"></td>';
				html += '<td width=150 style="padding-left:25px"><input id="Weekly'+i+'-start1" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="12:00"><span>-</span><input id="Weekly'+i+'-end1" class="easyui-timespinner"  style="width:70px;" equired=\"required\" data-options="showSeconds:false" value="14:00"></td>';
				html += '<td width=150 style="padding-left:25px"><input id="Weekly'+i+'-start2" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="15:00"><span>-</span><input id="Weekly'+i+'-end2" class="easyui-timespinner"  style="width:70px;" equired=\"required\" data-options="showSeconds:false" value="18:00"></td>';
			html += '</tr>';
			}	
		}
		
		$("#add_devicelinkactions_models").html(html);
		$.parser.parse("#add_devicelinkactions_models");
	},
		
	edit_devicelinkactions_model_change:function(value,model){	
		var html = "";
		if(value == "Everyday"){
			html += '<tr height=50>';
				html += '<td></td>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.time.time1+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time2+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time3+'：</td>';
			html += '</tr>';
			html += '<tr>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.model.Everyday+':</td>';
				if(model == value){
					for(i = 0;i < _cfs.guardmap.length;i++){
						var g = _cfs.guardmap[i];
						html += '<td><input id="edit-everyday-start'+i+'" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="'+g.start+'"><span>-</span><input id="edit-everyday-end'+i+'" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="'+g.end+'"></td>';
					}
				}else{
					html += "<td><input id=\"edit-everyday-start0\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"08:00\"><span>-</span><input id=\"edit-everyday-end0\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"10:00\"></td>";
					html += "<td width=150 style=\"padding-left:25px\"><input id=\"edit-everyday-start1\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"12:00\"><span>-</span><input id=\"edit-everyday-end1\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"14:00\"></td>";
					html += "<td width=150 style=\"padding-left:25px\"><input id=\"edit-everyday-start2\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"15:00\"><span>-</span><input id=\"edit-everyday-end2\" class=\"easyui-timespinner\"  style=\"width:70px;\" equired=\"required\" data-options=\"showSeconds:false\" value=\"18:00\"></td>";
				}
			
			html += '</tr>';
		}
		else if(value == "Weekly"){
			html += '<tr height=50>';
				html += '<td></td>';
				html += '<td>'+_lp.frame.configsets.devicelinkactions_list.time.time1+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time2+'：</td>';
				html += '<td width=150 style="padding-left:25px">'+_lp.frame.configsets.devicelinkactions_list.time.time3+'：</td>';
			html += '</tr>';
			if(model == value){
				for(i = 0;i < _cfs.guardmap.length;i++){
					html += '<tr height=45>';
					html += '<td>'+_cfs.devicelinkactions_formatter_week(i)+':</td>';
					var g = _cfs.guardmap[i];
					if(i == g.weekday){
						var weektime = g.weektime;
						for(j = 0;j < weektime.length;j++){
							var time = weektime[j];
							html += '<td><input id="edit-Weekly'+i+'-start'+j+'" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="'+time.start+'"><span>-</span><input id="edit-Weekly'+i+'-end'+j+'" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="'+time.end+'"></td>';	
						}
					}
					html += '</tr>';
				}
				
			}else{
				for(i = 0;i < 7;i++){
				html += '<tr height=45>';
					html += '<td>'+_cfs.devicelinkactions_formatter_week(i)+':</td>';
					html += '<td><input id="edit-Weekly'+i+'-start0" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="08:00"><span>-</span><input id="edit-Weekly'+i+'-end0" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="10:00"></td>';
					html += '<td width=150 style="padding-left:25px"><input id="edit-Weekly'+i+'-start1" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="12:00"><span>-</span><input id="edit-Weekly'+i+'-end1" class="easyui-timespinner"  style="width:70px;" equired=\"required\" data-options="showSeconds:false" value="14:00"></td>';
					html += '<td width=150 style="padding-left:25px"><input id="edit-Weekly'+i+'-start2" class="easyui-timespinner"  style="width:70px;" equired="required" data-options="showSeconds:false" value="15:00"><span>-</span><input id="edit-Weekly'+i+'-end2" class="easyui-timespinner"  style="width:70px;" equired=\"required\" data-options="showSeconds:false" value="18:00"></td>';
				html += '</tr>';
				}	
			}
		}
		
		$("#edit_devicelinkactions_models").html(html);
		$.parser.parse("#edit_devicelinkactions_models");
	},		
				
	// 创建录像状态列表
	create_from_RecordStatus_list:function(configId,attrs,res){
		var data = new Array();
		var html = '';
		html += '<tr>';
		html += '<td></td>';
		html += '<td>';
		//通道	录像状态	录像原因	故障原因
			html += '<table id="'+configId+'_grid" class="easyui-datagrid" title="'+attrs.attr("Name")+'" width="650" data-options="rownumbers:false,toolbar:[{text:\''+_lp.frame.configsets.btns.refresh+'\',iconCls:\'icon-reload\',handler:function(){_cfs.refresh_recordtatus();}}]" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'idx\',width:100">'+_lp.frame.configsets.recordstatus_list.col.idx+'</th>';
			html += '<th data-options="field:\'status\',width:100" >'+_lp.frame.configsets.recordstatus_list.col.status+'</th>';
			html += '<th data-options="field:\'reason\',width:200">'+_lp.frame.configsets.recordstatus_list.col.reason+'</th>';
			html += '<th data-options="field:\'fault\',width:200" >'+_lp.frame.configsets.recordstatus_list.col.fault+'</th>';		
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			var i = 0;
			
			$(res).find("IV").each(function(k,n){
				var d = $(n);
				reason = d.attr("Reason");
				if(typeof reason == "undefined"){
					reason = "";	
				}
				fault = d.attr("Fault");
				if(typeof fault == "undefined"){
					fault = "";	
				}
				html += '<tr><td>'+d.attr("Idx")+'</td><td>'+d.attr("Status")+'</td><td>'+reason+'</td><td>'+fault+'</td></tr>';
			});
			html += '</tbody>';
			html += '</table>';
		
		html += '</td></tr>';
		return html;
	},
	
	// 刷新录像状态
	refresh_recordtatus:function(){
		var idx = $('#sg_channel_cmb').combobox("getValue");
		_cfs.refresh_resource_configsetsbyid(true,[{type:"SG",idx:idx,id:"F_SG_RecordStatus"}]);
		var configXML = "";
		configXML = _cfs.resConfigSets["SG_"+idx]["F_SG_RecordStatus"];
		
		var status = new Array();

		var i = 0;
		$(configXML).find("IV").each(function(k,n){
			var d = $(n);
			status.push({idx:d.attr("Idx"),status:d.attr("Status"),reason:d.attr("Reason"),fault:(d.attr("Fault") || "")});
		});
		if($('#F_SG_RecordStatus_grid')){
			$('#F_SG_RecordStatus_grid').datagrid('loadData',status);
		}

	},
	
	// 创建手机号码列表
	create_from_OnlineSMSets_list:function(configId,attrs,res){
		var data = new Array();
		var html = '';
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+'</td>';
		html += '<td>';
			html += '<table id="'+configId+'_grid" class="easyui-datagrid"  width="400" data-options="rownumbers:false,toolbar:[{text:\''+_lp.frame.configsets.btns.refresh+'\',iconCls:\'icon-reload\',handler:function(){_cfs.refresh_onlinesmsets();}},{text:\''+_lp.frame.configsets.btns.add+'\',iconCls:\'icon-add\',handler:function(){_cfs.open_add_onlinesm();}}]" >';
			html += '<thead>';
			html += '<tr>';
			html += '<th data-options="field:\'id\',width:100">'+_lp.frame.configsets.onlinesmsets_list.col.id+'</th>';
			html += '<th data-options="field:\'phone\',width:100" >'+_lp.frame.configsets.onlinesmsets_list.col.phone+'</th>';
			html += '<th data-options="field:\'op\',width:150,formatter:_cfs.onlinesmsets_formatter_op" >'+_lp.frame.configsets.onlinesmsets_list.col.op+'</th>';
			html += '</tr>';
			html += '</thead>';
			html += '<tbody>';
			html += '<div id="'+configId+'_add_dlg" class="easyui-dialog" data-options="closed:true" style="display:none" >';
			html += '</div>';
			var i = 0;
			$(res).find("it").each(function(k,n){
				var text = $(n).text();
				if($(n).attr('Desc') && $(n).attr('Desc').length > 0){
					text = $(n).attr('Desc');
				}
			
				html += '<tr><td>'+(i++)+'</td><td>'+text+'</td><td></td></tr>';
			});
			html += '</tbody>';
			html += '</table>';
		
		html += '</td></tr>';
		return html;
	},
	
	// 刷新手机号码
	refresh_onlinesmsets:function(){

		_cfs.refresh_resource_configsetsbyid(true,[{type:"ST",idx:0,id:"F_ST_OnlineSMSets"}]);
		var configXML = "";
		configXML = _cfs.resConfigSets["ST_0"]["F_ST_OnlineSMSets"];
		var phones = new Array();

		var i = 0;
		$(configXML).find("it").each(function(k,n){
			var text = $(n).text();
			if($(n).attr('Desc') && $(n).attr('Desc').length > 0){
				text = $(n).attr('Desc');
			}
		//	var d = $(n);
			phones.push({id:(i++),phone:text});
		});
		if($('#F_ST_OnlineSMSets_grid')){
			$('#F_ST_OnlineSMSets_grid').datagrid('loadData',phones);
		}

	},
	
	// 格式化手机号码操作列
	onlinesmsets_formatter_op:function(v,r,i){
		var btn = '<a class="button black small" style="color:#fff;" onclick="_cfs.open_edit_onlinesm('+i+')">'+_lp.frame.configsets.btns.modify+'</a><a class="button black small" style="color:#fff;"  onclick="_cfs.del_onlinesm('+i+')">'+_lp.frame.configsets.btns.del+'</a>';
		btn = '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" onclick="_cfs.open_edit_onlinesm('+i+')" ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.modify+'&nbsp;</span></a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small"  onclick="_cfs.del_onlinesm('+i+')" ><span class="l-btn-left l-btn-icon-left">&nbsp;'+_lp.frame.configsets.btns.del+'&nbsp;</span></a>';
		return btn;
	},
	
	// 打开添加一个手机号码编辑框
	open_add_onlinesm:function(){
		var html = '';
		html += '<form id="F_ST_OnlineSMSets_add_form" ><table cellpadding="5">';
		html += '<tr>';
		html += '<td>'+_lp.des.mobilephone+':</td>';
		html += '<td><input class="easyui-textbox" type="text" id="F_ST_OnlineSMSets_add_form_phone" data-options="required:true,width:150"></input></td>';
		html += '</tr>';
		html += '</table></form>';
		
		$('#F_ST_OnlineSMSets_add_dlg').show();
		$('#F_ST_OnlineSMSets_add_dlg').dialog({
		    title:_lp.des.add_m,
		    width: 350,
		    height: 160,
		    closed: false,
		    cache: false,
		    buttons:[{
				text:_lp.frame.configsets.btns.ok,
				handler:function(){

					if($('#F_ST_OnlineSMSets_add_form').form("validate") == true){
						var phone = $('#F_ST_OnlineSMSets_add_form_phone').textbox("getValue");
						
						var rows = $('#F_ST_OnlineSMSets_grid').datagrid("getRows");
						var params = new Array();
						params.push({name:'it',childXML:phone,attrs:[]});
						
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];
							params.push({name:'it',childXML:r.phone,attrs:[]});
						}

						var values = new Array();
						values.push({
							id:"F_ST_OnlineSMSets",
			        		params:params,
							attrs:[]
						});
						
				        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
						
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note14,
						                timeout:4000,
						                showType:'slide'
						            });
									$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
									_cfs.refresh_onlinesmsets();
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				}
			},{
				text:_lp.frame.configsets.btns.close,
				handler:function(){
					$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
				}
			}],
		    content:html
		});
	},
	
	// 打开编辑手机号码编辑框
	open_edit_onlinesm:function(rowIndex){

		var rows = $('#F_ST_OnlineSMSets_grid').datagrid("getRows");
		if(!rows[rowIndex]){
			return;
		}
		var html = '';
		html += '<form id="F_ST_OnlineSMSets_edit_form" ><table cellpadding="5"><input type=hidden id="F_ST_OnlineSMSets_edit_form_rowIndex" value="'+rowIndex+'" />';
		html += '<tr>';
		html += '<td>'+_lp.des.mobilephone+':</td>';
		html += '<td><input class="easyui-textbox" type="text" id="F_ST_OnlineSMSets_edit_form_phone" value="'+rows[rowIndex].phone+'" data-options="required:true,width:150"></input></td>';
		html += '</tr>';
		html += '</table></form>';
		
		$('#F_ST_OnlineSMSets_add_dlg').show();
		$('#F_ST_OnlineSMSets_add_dlg').dialog({
		    title:_lp.des.modify_m,
		    width: 350,
		    height: 160,
		    closed: false,
		    cache: false,
		    buttons:[{
				text:_lp.frame.configsets.btns.ok,
				handler:function(){

					if($('#F_ST_OnlineSMSets_add_form').form("validate") == true){
						var phone = $('#F_ST_OnlineSMSets_edit_form_phone').textbox("getValue");
						var rowIndex = $('#F_ST_OnlineSMSets_edit_form_rowIndex').val();
						var rows = $('#F_ST_OnlineSMSets_grid').datagrid("getRows");
						var params = new Array();
						for(var i = 0;i < rows.length;i++){
							var r = rows[i];
							if(i == rowIndex){
								params.push({name:'it',childXML:phone,attrs:[]});
							}else{
								params.push({name:'it',childXML:r.phone,attrs:[]});
							}
						}
						var values = new Array();
						values.push({
							id:"F_ST_OnlineSMSets",
			        		params:params,
							attrs:[]
						});
						
				        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
						
						if(rv && rv.M && rv.M.C){
							if(rv.M.C.SPError != 0){
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					            return;
							}
							if(rv.M.C.Res){
								if(rv.M.C.Res.Error == "0"){
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.note15,
						                timeout:4000,
						                showType:'slide'
						            });
									$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
									_cfs.refresh_onlinesmsets();
								}else{
						            $.messager.show({
						                title:_lp.frame.configsets.notes.noteTitle,
						                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
						                timeout:4000,
						                showType:'slide'
						            });
								}
							}else{
					            $.messager.show({
					                title:_lp.frame.configsets.notes.noteTitle,
					                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
					                timeout:4000,
					                showType:'slide'
					            });
					    	}
							
						}else{
				            $.messager.show({
				                title:_lp.frame.configsets.notes.noteTitle,
				                msg:_lp.frame.configsets.notes.noteError,
				                timeout:4000,
				                showType:'slide'
				            });
				    	}
					}
				}
			},{
				text:_lp.frame.configsets.btns.close,
				handler:function(){
					$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
				}
			}],
		    content:html
		});
	},
	
	// 删除手机号码
	del_onlinesm:function(rowIndex){
		
		$('#F_ST_OnlineSMSets_grid').datagrid("deleteRow",rowIndex);
		
		var rows = $('#F_ST_OnlineSMSets_grid').datagrid("getRows");
		var params = new Array();
		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			params.push({name:'it',childXML:r.phone,attrs:[]});
		}

		var values = new Array();
		values.push({
			id:"F_ST_OnlineSMSets",
    		params:params,
			attrs:[]
		});
		
        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"ST",0,values);
		
		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return;
			}
			if(rv.M.C.Res){
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note16,
		                timeout:4000,
		                showType:'slide'
		            });
					$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
					_cfs.refresh_onlinesmsets();
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
			
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError,
                timeout:4000,
                showType:'slide'
            });
    	}
	},
	
	// 创建OEMdata元素
	create_from_OEMData:function(configId,attrs,res){
		var data = new Array();
		
		var oemData = $(res).find("OEMData");
		if(oemData.length > 0){
			oemData = $.trim(oemData[0].innerHTML);
		}else{
			oemData = "";
		}
		
		var html = '';
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		html += '<td>';
		html += '<input class="easyui-textbox" id="F_ST_OEMData" configid="F_ST_OEMData" setable="'+attrs.attr('Setable')+'" data-options="multiline:true,editable:'+(attrs.attr('Setable')== 0 ? 'false': 'true')+'" value="'+oemData+'" style="width:500px;height:150px">';
		html += '</td></tr>';
		return html;
	}
	
}

// control控制对象
var _cts = controlset = {
	interval :null,
	create_control_unit:function(resType,idx,cateId){
		var html = "",position='inlinetop';
		if(resType.toLowerCase() == "st"){
			switch(cateId){
			case "Info":
				html = _cts.create_form_st_reboot_controls(resType,idx,cateId);
				position='bottom';
				break;
			case "Time":
				html = _cts.create_form_st_time_controls(resType,idx,cateId);
				break;
			case "Online":
				html = _cts.create_form_st_online_controls(resType,idx,cateId);
				break;
			}
		}else if(resType.toLowerCase() == "iv"){

			switch(cateId){
			case "Camera.Day-Night":
				html = _cts.create_form_iv_daynight_controls(resType,idx,cateId);
				break;
			case "Sensor.Other":
				html = _cts.create_form_iv_sensorother_controls(resType,idx,cateId);
				break;
			case "Encoder.Stream":
			//	html = _cts.create_form_iv_encodestream_controls(resType,idx,cateId);
				break;
			}
		}else if(resType.toLowerCase() == "idl"){

			switch(cateId){
			case "Basic":
				html = _cts.create_form_idl_resetcounter_controls(resType,idx,cateId);
				break;
			}
		}else if(resType.toLowerCase() == "wifi"){

			switch(cateId){
			case "Basic":
				html = _cts.create_form_wifi_basic_controls(resType,idx,cateId);
				break;
			}
		}else if(resType.toLowerCase() == "sp"){

			switch(cateId){
			case "Basic":
				html = _cts.create_form_sp_basic_controls(resType,idx,cateId);
				break;
			}
		}else if(resType.toLowerCase(resType,idx,cateId) == "idl"){

			switch(cateId){
			case "Basic":
				html = _cts.create_form_idl_basic_controls(resType,idx,cateId);
				break;
			}
		}else if(resType.toLowerCase() == "sg"){
			
			switch(cateId){
			case "Disk":
				html = _cts.create_form_sg_disc_controls(resType,idx,cateId);
				position='top';
				break;
			}
		}
		else if(resType.toLowerCase() == "wm"){
			
			switch(cateId){
			case "Statistics":
				html = _cts.create_form_vm_reset_controls(resType,idx,cateId);
				position='top';
				break;
			}	
		}
		return [html,position];
	},
	set_control:function(resType,idx,cateId,controlId){
		var rv = "",val = "";
		if(typeof controlId == "undefined" || controlId == null){
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.note6,
                timeout:4000,
                showType:'slide'
            });
			return;
		}
		// 根据controlId取出要设置的值
		if(controlId == "C_ST_SetTime"){
			var t= $("#C_ST_GetTime").datetimespinner('getValue');
			val = 'Time="'+P_Utils.GetDateTimeUTCSeconds(P_Utils.DTStrToTimestamp(t))+'"';
		}else if(controlId == "C_IV_ForceDayOrNightParam"){
			var v= (arguments[4] ? arguments[4] : 0 );
			val = 'DayOrNight="'+v+'"';
		}
        rv = _p.set_control(_cfs.connectId,_cfs.pu.puid,resType,idx,controlId,val);

		if(rv && rv.M && rv.M.C && rv.M.C.Res  ){
			if(rv.M.C.Res.Error == "0"){
				if(controlId == "C_WM_ResetSMOnlineTotalTime" || controlId == "C_WM_ResetSMBytes" ){
					_cfs.refresh_resource_configsetsbycateid(cateId,resType,idx);		
				}
				
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.note7+'（'+controlId+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
			}else{
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.note7+'（'+controlId+',error:'+rv.M.C.Res.Error+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
			}
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.note8+'（'+controlId+',response:'+rv+'）。',
                timeout:4000,
                showType:'slide'
            });
    	}
        return rv;
	},
	restore:function(){
		$.messager.confirm(_lp.frame.configsets.notes.noteTitle1,_lp.frame.configsets.notes.note10,function(r){
		    if (r){
		        rv = _p.set_control(_cfs.connectId,_cfs.pu.puid,"ST",0,"C_ST_Restore","");

				if(rv && rv.M && rv.M.C && rv.M.C.Res  ){
					if(rv.M.C.Res.Error == "0"){
						// 重启命令发送成功
						// 重新连接
						_f.reconnect();
						return;
					}
				}
		    }
		});
	},
	reboot:function(){
		
		$.messager.confirm(_lp.frame.configsets.notes.noteTitle1,_lp.frame.configsets.notes.note9,function(r){
		    if (r){
		        rv = _p.set_control(_cfs.connectId,_cfs.pu.puid,"ST",0,"C_ST_Reboot","");

				if(rv && rv.M && rv.M.C && rv.M.C.Res  ){
					if(rv.M.C.Res.Error == "0"){
						
				
						// 重启命令发送成功
						// 重新连接
						_f.reconnect();
						return;
					}
				}
		    }
		});
	},
	create_form_st_reboot_controls:function(resType,idx,cateId){
		var html = '';
		html += '<table style="width:100%;border-top:1px solid #e0e0e0;"><tr>';
		html += '<td>&nbsp;&nbsp;</td>';
		html += '<td style="text-align:right;"><a id="C_ST_Restore" href="#" class="easyui-linkbutton" onclick="_cts.restore(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);">'+_lp.frame.configsets.btns.restore+'</a>&nbsp;&nbsp;<a id="C_ST_Reboot" href="#" class="easyui-linkbutton"  onclick="_cts.reboot(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.reboot+'</a></td>';
		html += '</tr></table><div id="reboot_wait_dlg"  data-options="resizable:false,draggable:false,closable:false,modal:true" ></div>';
		
//		html += '<tr><div id="reboot_wait_dlg" data-options="resizable:false,draggable:false,closable:false,modal:true" ></div>';
//		html += '<td>&nbsp;&nbsp;</td>';
//		html += '<td style="text-align:right;"><a id="C_ST_Restore" href="#" class="easyui-linkbutton" onclick="_cts.restore(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);">'+_lp.frame.configsets.btns.restore+'</a>&nbsp;&nbsp;<a id="C_ST_Reboot" href="#" class="easyui-linkbutton"  onclick="_cts.reboot(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.reboot+'</a></td>';
//		html += '</tr>';
		
		return html;
	},
	create_form_st_time_controls:function(resType,idx,cateId){
		var html = '';
		t = new Date();
		var rv = _p.get_control(_cfs.connectId,_cfs.pu.puid,resType,idx,"C_ST_GetTime");
		
		if(typeof rv == "object" && rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param.Time){
			t = rv.M.C.Res.Param.Time;
			t = new Date(t*1000);
		}
		
//		html += '<table  cellpadding="5" ><tr>';
//		html += '<td>'+_lp.frame.configsets.st.dt.lbl+':</td>';
//		html += '<td ><input class="easyui-datetimebox" id="C_ST_GetTime" data-options="required:true,showSeconds:true" value="'+P_Utils.DateFormat(null,t)+'" >&nbsp;&nbsp;<a id="C_ST_SetTime" href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.set+'</a></td>';
//		html += '</tr></table>';

		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.st.dt.lbl+':</td>';
		html += '<td ><input class="easyui-datetimespinner" id="C_ST_GetTime" data-options="required:true,showSeconds:true" value="'+P_Utils.DateFormat(null,t)+'" >&nbsp;&nbsp;<a id="C_ST_SetTime" href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.set+'</a></td>';
		html += '</tr>';
		
		return html;
	},
	create_form_st_online_controls:function(resType,idx,cateId){
		var html = '';
		//html += '<table cellpadding="5" >';
		html += '<tr>';
		html += '<td>'+_lp.frame.configsets.st.control.lbl+':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
		html += '<td ><a id="C_ST_Online" href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.online+'</a>&nbsp;&nbsp;<a id="C_ST_Offline"  href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.offline+'</a></td>';
		html += '</tr>';
		//html += '</table>';
		return html;
	},

	create_form_wifi_basic_controls:function(resType,idx,cateId){
		var html = '';
		//html += '<table  cellpadding="5" >';
		html += '<tr><td></td>';
		html += '<td align=rigth><a href="#" class="easyui-linkbutton" onclick="_cts.scan_ap(\''+resType+'\',\''+idx+'\',\''+cateId+'\');"  >'+_lp.frame.configsets.btns.scanAP+'</a>&nbsp;&nbsp;<a href="#" class="easyui-linkbutton"  onclick="_cts.selected_ap();"  >'+_lp.frame.configsets.btns.selectedAP+'</a></td></tr>';

		html += '<tr><td width="55">&nbsp;</td>';
		html += '<td>';

		html += '<table id="WIFI_AP_gr" class="easyui-datagrid" width="388" data-options="singleSelect:true" height="160" >';
		html += '<thead>';
		html += '<tr>';
		html += '<th data-options="field:\'SSID\',width:120">'+_lp.frame.configsets.ap.gr_col.ssid+'</th>';
		html += '<th data-options="field:\'Auth\',width:100">'+_lp.frame.configsets.ap.gr_col.auth+'</th>';
		html += '<th data-options="field:\'Signal\',width:80" >'+_lp.frame.configsets.ap.gr_col.signal+'</th>';
		html += '<th data-options="field:\'InUse\',width:70,formatter:function(v){return (v==1 ? \''+_lp.frame.configsets.ap.gr_col.y+'\' : \''+_lp.frame.configsets.ap.gr_col.n+'\');}" >'+_lp.frame.configsets.ap.gr_col.isuse+'</th>';
		html += '</tr>';
		html += '</thead>';
		html += '<tbody>';
		//html += '';
		html += '</tbody>';
		html += '</table>';
		
		html += '</td></tr>';
		
		//html += '</table>';
		return html;
	},
	create_form_iv_sensorother_controls:function(resType,idx,cateId){

		var html = '';
		//html += '<table  cellpadding="5" >';
		html += '<tr><td></td>';
		html += '<td align=rigth><a id="C_IV_StartAperture" href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);" >'+_lp.frame.configsets.btns.aperture+'</a></td></tr>';
		
		//html += '</table>';
		return html;
	},
	create_form_iv_encodestream_controls:function(resType,idx,cateId){
	
		var rv = _p.get_res_config(_cfs.connectId,_cfs.pu.puid,resType,idx,'F_IV_SupportedStreamNum');
		
		var res = $(rv).find("Res");
		var d = new Array();
		if($(res).attr("Error") == 0){
			
			var p = $(rv).find("Param");
			var num = $(p).attr("Value");
			for(var i = 0;i < num;i++){
				d.push({id:i,name:'Stream'+(i+1)});
			}
		}else{
			d.push({id:0,name:'Stream1'})
		}
		
		var html = '';
		//html += '<table  cellpadding="5" >';
		html += '<tr><td>'+_lp.frame.player.cameralist.streamType+':</td>';
		html += '<td align=rigth><select id="streamnum" class="easyui-combobox" panelHeight="auto" data-options="panelHeight:\'auto\',onLoadSuccess:function(){_cts.streamnum_onchage(0,0,\''+idx+'\',\''+cateId+'\');},onChange:function(nVal,oVal){_cts.streamnum_onchage(nVal,oVal,\''+idx+'\',\''+cateId+'\');}"  style="width:173px;height:auto;">';
		for(var i = 0;i < d.length;i++){
			html += '<option value="'+d[i].id+'" >'+d[i].name+'</option>';
		}
		html += '</select></td></tr>';
		//html += '</table>';
		return html;
		
	},
	create_form_iv_daynight_controls:function(resType,idx,cateId){

		var html = '';
		//html += '<table  cellpadding="5" >';
		html += '<tr><td></td>';
		html += '<td align=rigth><a href="#"  class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_IV_ForceDayOrNightParam\',1);">'+_lp.frame.configsets.btns.forceDay+'</a>&nbsp;&nbsp;<a href="#" id="C_IV_ForceDayOrNightParam" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_IV_ForceDayOrNightParam\',2);" >'+_lp.frame.configsets.btns.forceNight+'</a>&nbsp;&nbsp;<a href="#"   class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_IV_ForceDayOrNightParam\',0);" >'+_lp.frame.configsets.btns.autoDayNight+'</a></td></tr>';		
		//html += '</table>';
		return html;
	},
	create_form_sp_basic_controls:function(resType,idx,cateId){
		var html = '';
		//html += '<table  cellpadding="5" >';
		//html += '<tr><td></td><td ><a href="#" class="easyui-linkbutton" ></a></td></tr>';
		//html += '<tr><td></td><td><a href="#" class="easyui-linkbutton" >'+_lp.frame.configsets.btns.inData+'</a></td></tr>';
		html += '<tr><td>'+_lp.frame.configsets.btns.sendSPData+':</td>';
		html += '<td ><input id="C_SP_WriteData" class="easyui-textbox" type="text" value=""  data-options="multiline:true" style="width:300px;height:60px;"></input>&nbsp;'+_lp.frame.configsets.btns.inData+'</td></tr>';
		html += '<tr><td></td><td align=left><a href="#" class="easyui-linkbutton" onclick="_cts.send_sp_data()">'+_lp.frame.configsets.btns.send+'</a></td></tr>';
		//html += '</table>';
		return html;
	},
	send_sp_data:function(){
		
		
	},
	create_form_idl_basic_controls:function(resType,idx,cateId){
		var html = '';
		//html += '<table  cellpadding="5" >';
		html += '<tr><td></td><td></td></tr>';
//		html += '<td align=rigth><a id="C_IDL_ResetCounter" href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',this.id);">'+_lp.frame.configsets.btns.emptyVal+'</a></td></tr>';
		//html += '</table>';
		return html;
	},
	create_form_sg_disc_controls:function(resType,idx,cateId){
		var html = '';
		html += '<table cellpadding="2" >';
		html += '<tr>';
		html += '<td colspan=2>';
		html += '<table id="C_SG_GetDiskInfo_grid" title="'+_lp.frame.configsets.sg.dk_gr_title+'" class="easyui-datagrid" width="700" data-options="toolbar:\'#C_SG_GetDiskInfo_grid_tb\',singleSelect:true,onSelect:function(rowIndex,rowData){_cts.disk_grid_rowclick(rowIndex,rowData);},onLoadSuccess:function(){_cts.disk_grid_loaded();}" >';
		html += '<thead>';
		html += '<tr>';
			
		html += '<th data-options="field:\'letter\',width:50">'+_lp.frame.configsets.sg.dk_gr_col.letter+'</th>';
		html += '<th data-options="field:\'backup\',width:50">'+_lp.frame.configsets.sg.dk_gr_col.backup+'</th>';
		html += '<th data-options="field:\'type\',width:80">'+_lp.frame.configsets.sg.dk_gr_col.type+'</th>';
		html += '<th data-options="field:\'volumeLabel\',width:140" >'+_lp.frame.configsets.sg.dk_gr_col.volumeLabel+'</th>';
		html += '<th data-options="field:\'fileSystem\',width:70" >'+_lp.frame.configsets.sg.dk_gr_col.fileSystem+'</th>';
		html += '<th data-options="field:\'capacity\',width:80" >'+_lp.frame.configsets.sg.dk_gr_col.capacity+'(MB)</th>';
		html += '<th data-options="field:\'usedSpace\',width:80" >'+_lp.frame.configsets.sg.dk_gr_col.usedSpace+'(MB)</th>';
		html += '<th data-options="field:\'usableSpace\',width:80" >'+_lp.frame.configsets.sg.dk_gr_col.usableSpace+'(MB)</th>';
		html += '<th data-options="field:\'status\',width:70" >'+_lp.frame.configsets.sg.dk_gr_col.status+'</th>';
		html += '</tr>';
		html += '</thead>';
		html += '<tbody>';
		//html += '';
		var data = _cts.refresh_diskinfo(resType,idx);
		console.log(data)
		//var data = new Array();
		for(var i = 0;i < data.length;i++){
			var d = data[i];
			html += '<tr><td>'+d.letter+'</td><td>'+d.backup+'</td><td>'+d.type+'</td><td>'+d.volumeLabel+'</td><td>'+d.fileSystem+'</td><td>'+d.capacity+'</td><td>'+d.usedSpace+'</td><td>'+d.usableSpace+'</td><td>'+d.status+'</td></tr>';
		}
		html += '</tbody>';
		html += '</table>';	
		
		html += '<div id="C_SG_GetDiskInfo_grid_tb" style="height:auto">';
			html += '<div style="margin-bottom:1px;text-align:left;">';
			html += '<a id="C_SG_GetDiskInfo_grid_tb_setbk" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="_cts.set_backup_disk();" >'+_lp.frame.configsets.btns.setBackup+'</a>';
			html += '<a id="C_SG_GetDiskInfo_grid_tb_cancelbk" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="_cts.cancel_backup_disk();" >'+_lp.frame.configsets.btns.cancelBackup+'</a>';
			html += '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-lock" plain="true" onclick="_cts.format_disk();" >'+_lp.frame.configsets.btns.formatSD+'</a>';
			html += '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="_cts.refresh_diskinfo(\''+resType+'\',\''+idx+'\')" >'+_lp.frame.configsets.btns.refresh+'</a>';
			html += '</div>';
		html += '</div>';

		html += '<div id="C_SG_GetDiskInfo_format_dlg" class="easyui-dialog" data-options="closed:true" style="display:none" ></div>';
		
		html += '</td></tr>';		
		html += '</table>';

		//
		return html;		
	},
	disk_grid_loaded:function(){
		$('#C_SG_GetDiskInfo_grid').parent().after($('#C_SG_GetDiskInfo_grid_tb'));
		var rows = $('#C_SG_GetDiskInfo_grid').datagrid("getRows")
		if(rows.length > 0){
			 $('#C_SG_GetDiskInfo_grid').datagrid('selectRow',0);
			//_cts.disk_grid_rowclick(0,rows[0]);
		}
		
	},
	refresh_diskinfo:function(resType,idx){
		_cfs.refresh_resource_configsetsbyid(true,[{type:resType,idx:idx,id:"F_SG_BackupDiskSets"}]);
		var configXML = "";
		configXML = _cfs.resConfigSets[resType+"_"+idx]["F_SG_BackupDiskSets"];
		
		var i = 0,backupDisks = new Array();
		$(configXML).children("Disk").each(function(k,n){
			var d = $(n);
			backupDisks.push({letter:d.attr("Letter"),backup:d.attr("Backup")});
		});
		var data = new Array();
		
		var rv = _p.get_control(_cfs.connectId,_cfs.pu.puid,resType,idx,'C_SG_GetDiskInfo');
		if(typeof rv == "object" && rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Error != 0){
			return data;
		}
		
		if(typeof rv == "object" && rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param.Disk){
			var disks = rv.M.C.Res.Param.Disk;
			
			if(!$.isArray(disks)){
				disks = new Array();
				disks.push(rv.M.C.Res.Param.Disk);
			}
			for(var i = 0;i < disks.length;i++){
				var d = disks[i],backup = 0;
				for(var j = 0;j < backupDisks.length;j++){
					if(backupDisks[j].letter == d.Letter){
						backup = backupDisks[j].backup;
						break;
					}
				}
				data.push({
					volumeLabel:d.VolumeLabel,
					letter:d.Letter,
					backup:backup,
					type:d.Type,
					fileSystem:d.FileSystem,
					usableSpace:d.UsableSpace,
					usedSpace:d.UsedSpace,
					capacity:d.Capacity,
					status:d.Status
				});
			}
			if($("#C_SG_GetDiskInfo_grid")) $("#C_SG_GetDiskInfo_grid").datagrid("loadData",data);
		}
		
		return data;
	},
	disk_grid_rowclick:function(rowIndex, rowData){
		if(typeof rowData.backup != "undefined" && rowData.backup == 0){
			$('#C_SG_GetDiskInfo_grid_tb_cancelbk').hide();
			$('#C_SG_GetDiskInfo_grid_tb_setbk').show();
		}else{

			$('#C_SG_GetDiskInfo_grid_tb_setbk').hide();
			$('#C_SG_GetDiskInfo_grid_tb_cancelbk').show();
		}
	},
	format_disk:function(){
		
		var idx = $('#sg_channel_cmb').combobox("getValue");
		var row = $("#C_SG_GetDiskInfo_grid").datagrid("getSelected");

		$.messager.confirm(_lp.frame.configsets.notes.noteTitle1,_lp.frame.configsets.notes.note17,function(r){
		    if (r){
				
		    	setTimeout(function(){
		    		
			        rv = _p.set_control(_cfs.connectId,_cfs.pu.puid,"SG",idx,'C_SG_StartInitFileSystem',' DiskLetter="'+row.letter+'" ');
	
					if(rv && rv.M && rv.M.C && rv.M.C.Res  ){
						if(rv.M.C.Res.Error == "0"){

							$('#C_SG_GetDiskInfo_format_dlg').show();
							$('#C_SG_GetDiskInfo_format_dlg').dialog({
							    title: '格式化磁盘',
							    width: 400,
							    height: 120,
							    closed: false,
							    cache: false,
							    modal: true,
								content:'<div id="format_progressbar" class="easyui-progressbar" data-options="value:0" style="width:360px;margin:15px auto;"></div>'
							});
							
							_cts.interval = setInterval(function(){
								var rv = _p.get_control(_cfs.connectId,_cfs.pu.puid,"SG",idx,'C_SG_QueryInitFileSystemProgress','');

								if(rv && rv.M && rv.M.C && rv.M.C.Res  ){
									if(rv.M.C.Res.Error == "0"){
										if(rv.M.C.Res.Param && rv.M.C.Res.Param.Progress){

											$('#format_progressbar').progressbar({
											    value: parseInt(rv.M.C.Res.Param.Progress)
											});
											
											if(rv.M.C.Res.Param.Progress == "100"){	
												clearInterval(_cts.interval);
												$.messager.show({
									                title:_lp.frame.configsets.notes.noteTitle,
									                msg:_lp.frame.configsets.notes.note18,
									                timeout:4000,
									                showType:'slide'
									            });
												$('#C_SG_GetDiskInfo_format_dlg').dialog("close");
											}
										}
									}
								}
							},1000);
							return;
						}
					}
					
		    	},100);
		    }
		});
		
		
		//var rv = _p.set_control(_cfs.connectId,_cfs.pu.puid,"SG",idx,'C_SG_StartInitFileSystem',' DiskLetter="'+row.letter+'" ');
		
/*		if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.APList){
			if(rv.M.C.Res.Param.APList.AP){
				$("#WIFI_AP_gr").datagrid("loadData",rv.M.C.Res.Param.APList.AP);
			}
		}*/
		
		
		
	},
	set_backup_disk:function(){
		var row = $("#C_SG_GetDiskInfo_grid").datagrid("getSelected");
		var params = new Array();
		params.push({name:'Disk',childXML:'',attrs:[{name:'Letter',val:row.letter},{name:'Backup',val:'1'}]});
		
		var values = new Array();
		values.push({
			id:"F_SG_BackupDiskSets",
    		params:params,
			attrs:[]
		});
		
		var idx = $('#sg_channel_cmb').combobox("getValue");
        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"SG",idx,values);
		
		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return;
			}
			if(rv.M.C.Res){
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note16,
		                timeout:4000,
		                showType:'slide'
		            });
					$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
					_cfs.refresh_onlinesmsets();
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
			
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError,
                timeout:4000,
                showType:'slide'
            });
    	}
	},
	cancel_backup_disk:function(){
		var row = $("#C_SG_GetDiskInfo_grid").datagrid("getSelected");
		var params = new Array();
		var values = new Array();
		values.push({
			id:"F_SG_BackupDiskSets",
    		params:params,
			attrs:[]
		});

		var idx = $('#sg_channel_cmb').combobox("getValue");
        rv = _p.set_res_configs(_cfs.connectId,_cfs.pu.puid,"SG",idx,values);
		
		if(rv && rv.M && rv.M.C){
			if(rv.M.C.SPError != 0){
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（sperror:'+rv.M.C.SPError+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	            return;
			}
			if(rv.M.C.Res){
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note16,
		                timeout:4000,
		                showType:'slide'
		            });
					$('#F_ST_OnlineSMSets_add_dlg').dialog('close');
					_cfs.refresh_onlinesmsets();
				}else{
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
			
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError,
                timeout:4000,
                showType:'slide'
            });
    	}
	},
	scan_ap:function(resType,idx,cateId){
		var rv = _p.get_control(_cfs.connectId,_cfs.pu.puid,resType,idx,'C_WIFI_ScanAP',' MaxNum="20" ');
		if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.APList){
			if(rv.M.C.Res.Param.APList.AP){
				$("#WIFI_AP_gr").datagrid("loadData",rv.M.C.Res.Param.APList.AP);
			}
		}
	},
	selected_ap:function(){
		var ap = $("#WIFI_AP_gr").datagrid("getSelected");
		if(ap){
			if($("#F_WIFI_SSID")) $("#F_WIFI_SSID").textbox("setValue",ap.SSID);
			if($("#F_WIFI_AuthMode"))$("#F_WIFI_AuthMode").combobox("setValue",ap.Auth);
		}
	},
	create_form_idl_resetcounter_controls:function(resType,idx,cateId){
		var html = '';
		//html += '<table  cellpadding="5" >';
		//html += '<tr><td></td><td align=right><a href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_IDL_ResetCounter\');" >'+_lp.frame.configsets.btns.resetCounter+'</a></td></tr>';
		html += '<tr><td></td><td><a href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_IDL_ResetCounter\');" >'+_lp.frame.configsets.btns.resetCounter+'</a></td></tr>';
		//html += '</table>';
		return html;
	},
	create_form_vm_reset_controls:function(resType,idx,cateId){
		var html = '';
		html += '<table  cellpadding="5" >';
		html += '<tr><td></td><td align=right><a href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_WM_ResetSMOnlineTotalTime\');" >'+_lp.frame.configsets.btns.resetOnlineTotalTime+'</a></td><td><a href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_WM_ResetSMBytes\');" >'+_lp.frame.configsets.btns.resetSMBytes+'</a></td></tr>';
	//	html += '<tr><td></td><td><a href="#" class="easyui-linkbutton" onclick="_cts.set_control(\''+resType+'\',\''+idx+'\',\''+cateId+'\',\'C_WM_ResetSMBytes\');" >'+_lp.frame.configsets.btns.resetSMBytes+'</a></td></tr>';
		html += '</table>';
		return html;
	},
	create_form_st_linkage_controls:function(resType,idx,cateId){
	
	}		
}