var _de = mydecode = {
	pu:null,
	configIds:null,
	connectId:null,
	newConnectId:null,
	configSets:new Object(),
	puInfoArray:new Array(),
	OVDatas:new Array(),
	IVDatas:new Array(),
	RowDatas:new Array(),	
	sourcesdata:new Array(),
	checkModify:false,
	loginParam:{
		epid:'',
		ip:'',
		port:'',
		username:'',
		password:'',
		remember:true
	},
	fixedResource:{
		addr:'',
		puid:'',
		idx:'',
		stream:'',
		type:''
	},
	//configXML:"",
	//resConfigSets : new Object(),
	inited:false,
	//delayLoadControls:new Array(),
	submitAllCates:'Camera.Capture,Camera.Preprocessor,Camera.Day-Night',
	// 初始化
	init:function(connectId){	
		_de.connectId = connectId;
	},
	pu_change:function(pu){
		_de.pu = pu;
		_de.init_tabs();
	},
	init_tabs:function(){		
    	_de.resConfigSets = new Object();
    	_de.configSets = new Object();
    	var types = new Array();
    	//types.push("OV");
    	//types.push("DP");
    	for(var i = 0;i < _de.pu.childResource.length;i++){
    		var pu = _de.pu.childResource[i];
    		if(pu.type == P_LY.Enum.PuResourceType.DP || pu.type == P_LY.Enum.PuResourceType.VideoOut)
    		{
    			types.push(pu.type);
    		}
    	}
    	var params = "";
    	for(var i = 0;i < types.length;i++){
    		var type = types[i];
    		params += '<Res Type="'+(type||"")+'" Idx="0" OptID="F_RES_ConfigIDSets"  ></Res>';
    	}	
   
    	
    	var rv = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,params);

    	if(rv != ""){
            var res = $(rv).find("Res");
        	for(var i = 0;i < res.length;i++){
        		_de.configSets[$(res[i]).attr("Type")] = $(res[i]);
        	}
    	}else{
    		return;
    	}
		
		_de.init_left_center();
	},
	
	init_left_center:function(){
	
		var ovArray = new Array();
		var dpArray = new Array();
    	if(dpArray.length == 0){
    		// 找出OV类型，创建
    		for(var i = 0;i < _de.pu.childResource.length;i++){
    			
    			if(_de.pu.childResource[i].type == "OV"){
    				var o = new Array();
    				o.id = i;
    				o.text = _de.pu.childResource[i].name;
    				o.name = _de.pu.childResource[i].name;
    				o.idx = _de.pu.childResource[i].idx;
    				o.type = _de.pu.childResource[i].type;
    				o.iconCls = "icon_ov";
    				ovArray.push(o);
    			}
    			var ovlength = ovArray.length;
    			
    			// 找出DP类型，创建
    			if(_de.pu.childResource[i].type == "DP") {
    				var p = new Array();
    				p.id = _de.pu.childResource[i].idx;
    				p.text = _de.pu.childResource[i].name;
    				p.name = _de.pu.childResource[i].name;
    				p.idx = _de.pu.childResource[i].idx;
    				p.type = _de.pu.childResource[i].type;
    				p.ovlength = ovlength;
    				p.iconCls = "icon_dp";
    				if(p.idx == 0){	
    					p.state = "open";
    				}else{
    					p.state = "closed";
    				}   				
    				
    				//获取每个DP下最多分频窗口数
    				var params = '<Res Type="'+p.type+'" Idx="'+p.idx+'" OptID="F_DP_MaxSplitNum"  ></Res>';
        			var rv = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,params);	
        			var res = $(rv).find("Param");
        			var  maxnumber = $(res).attr("Value");
        			p.maxnumber = maxnumber;
        			
        			//子节点预留
        			p.children = new Array();
        			
    				dpArray.push(p);
    			}
    			
    		}
  		
  			
        	var currentnumber = ovArray.length;
        	for(var j = 0;j < dpArray.length;j++){
        		
        		var dp = dpArray[j];
        		var maxlength = dp.maxnumber;
        		
        		if(maxlength >= ovlength && j > 0) continue;
        		
        		var params = '<Res Type="'+dp.type+'" Idx="'+dp.idx+'" OptID="F_DP_SplitMode"  ></Res>';
        		var rv = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,params);
        		var res = $(rv).find("Param");
        		var mode = $(res).attr("Value");
        		var munber = 1;
        	
        		if(mode == "1x1"){
        			munber = 1;
        		}
        		else if(mode == "2x2"){
        			munber = 4;
        		}
        		else if(mode == "3x3"){
        			munber = 9;
        		}
        		else if(mode == "3x4"){
        			munber = 12;
        		}
        		else if(mode == "4x4"){
        			munber = 16;
        		}
        		else if(mode == "4x5"){
        			munber = 20;
        		}
        		else if(mode == "4x6"){
        			munber = 24;
        		}
        		else{
        			munber = 1;
        		}       		
        		       		
        		for(var k = 0;k < munber;k++){
        			if(currentnumber <= 0) continue;
        			if(maxlength < munber) continue;
        			
        			var c = ovArray[ovlength-currentnumber];
        			var data = {"text":c.name,"iconCls":c.iconCls,"type":c.type,"idx":c.idx};
        			dp.children.push(data)
        			--currentnumber ;
        		}
        	}
      		
      	   _de.OVDatas = dpArray;
 _lp.frame.decode.title
	       $('#de_region_west').tree({
				data:dpArray,
	    		onClick:function(node){
	    			if(_de.checkModify == true){
					   $.messager.confirm(_lp.frame.decode.title.noteTitle,_lp.frame.decode.title.note, function (r) {
					       if (r) {
					       		_de.init_region_center(node.type,node.idx)
					       }
					    });	
					}
					else{
						 _de.init_region_center(node.type,node.idx)
					}
	    		
	    		}
    		});
	    		
		 	var node = $('#de_region_west').tree('find',0);
			if(node && node.target){
				$('#de_region_west').tree("select", node.target)	
			}
		    
	    	_de.init_region_center("DP","0")
    	}	
	},
	// 初始化配置细项元素
	init_region_center:function(resType,idx){
		_de.checkModify = false;
		if(resType =="DP"){
		  if(_de.configSets["DP"]){
	        	_de.configIds = _de.configSets["DP"];
	            _de.fetch_category_configsets(_de.configIds,"DP",idx);
	        }
		}else{
			 if(_de.configSets["OV"]){
	        	_de.configIds = _de.configSets["OV"];
	        	_de.fetch_category_configsets(_de.configIds,"OV",idx);
	          
	        }
		}
      
	},

	fetch_category_configsets:function(cates,resType,idx){
        for(var i =0;i < cates.length;i++){
        	
        	_de.create_sub_tabpanel($(cates[i]).children(),resType,idx);
        }
	},
	
	create_sub_tabpanel:function(obj,resType,idx){
	
		var html = '';
		$(obj).each(function(k,n){
			if($(n)[0].localName.toLowerCase() == "category"){
				var name = $(n).attr("Name");
				var cateId = $(n).attr("ID");
				if(cateId.toLowerCase()=="source"){
					html += '<div title="'+name+'" id="'+cateId+'" idx="'+idx+'" data-options="closable:false" style="padding:5px;" fit="true" >';
						html += '<div style="padding:15px;height:20px;">';
								html += '<div style="height:20px;float:left">'+_lp.frame.decode.ov_list+'</div><div id="checkmodify" style="display:none;color:red;float:left">*</div>';
								html += '<div style="height:20px;float:right;padding-buttom:10px"><a href="javascript:void(0)" onclick="_de.addVideosource(\''+resType+'\',\''+idx+'\')"  class="easyui-linkbutton">'+_lp.frame.decode.btns.add+'</a></div>'; 
						html += '</div>';
						html += '<div>';
						html += '<table id="sources_dr"></table>';
						html += '</div>';
						html += '<div style="padding-top:20px">';
							html += '<div style="float:left"><input type="checkbox" id="switchmode" name="switchmode"  style="vertical-align:middle;margin-top:0;" onclick="_de.changeswitchmode(\''+resType+'\',\''+idx+'\')">'+_lp.frame.decode.notes.note1+'</div>';
							html += '<div style="margin-left:20px;float:right"><a href="javascript:void(0)" onclick="_de.set_ov_config(\''+resType+'\',\''+idx+'\')"  class="easyui-linkbutton">'+_lp.frame.decode.btns.ok+'</a>&nbsp';
							html += '<a href="javascript:void(0)" onclick="_de.refresh_ov_configsets(\''+resType+'\',\''+idx+'\')" class="easyui-linkbutton">'+_lp.frame.decode.btns.refresh+'</a></div>';	
						html += '</div>';
					html += '</div>';
				}else{
					html += '<div title="'+name+'" id="'+cateId+'" idx="'+idx+'" data-options="closable:false" style="padding:5px;" fit="true" >';
					html += '</div>';
				}
				
			}
		});
		
		html = '<div id="dp_de_tabs" resType="'+resType+'"  class="easyui-tabs" data-options="border:false" tabPosition="top" style="width:auto;height:auto;" fit="true" >'+html+'</div>';
		
        $('#de_region_center').html(html);
        
        $('#dp_de_tabs').tabs({
			selected:0,
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
		    		$('#wait_dlg').dialog('close');
			        _de.resource_tab_onselect(title,index,t.attr("resType"));
		    		
		    	},100);
		    }
		});
        $('#dp_de_tabs').tabs("select",0);
		$.parser.parse('#de_region_center');
	},
	
	resource_tab_onselect:function(title,index,resType){
		var t = $('#dp_de_tabs').tabs("getTab",index);
		var cateId = $(t).attr("id");
		var idx = $(t).attr("idx");
		//var cates = _cfs.configIds[0].children;
		var cates = _de.configIds;
		var contentHtml = "";
		
        for(var i =0;i < cates.length;i++){
        	var cate = $(cates[i]).children();
    		$(cate).each(function(k,n){
    			if($(n)[0].localName.toLowerCase() == "category"){
    				var name = $(n).attr("Name");
    				var id = $(n).attr("ID");
    				var subCategory = $(n).find("Category");
    				if(id == cateId){

    					_de.get_resource_configsets(cateId,resType,idx);
    					
    					if(subCategory.length > 0){
        					contentHtml = _de.create_sub_form_panel(n,resType,idx,cateId);
    					}else{
        					contentHtml = _de.create_form_panel(n,resType,idx,cateId);
    					}
    					return true;
    				}
    			}
    		});
        }
		$('#dp_de_tabs').tabs('update', {
			tab: t,
			options: {
				content:contentHtml
			}
		});

        setTimeout(function(){

	        //if($('#F_ST_IPChannelSets_grid')) $('#F_ST_IPChannelSets_grid').datagrid();
	        if($('#C_SG_GetDiskInfo_grid')){
	        	$('#C_SG_GetDiskInfo_grid').datagrid("reload");
	        }			        
        },1);
	},
	
	get_resource_configsets:function(cateId,resType,idx){
		var hasStream = false;
		var cates = _de.configIds;
        for(var i =0;i < cates.length;i++){
        	var cate = $(cates[i]).children();
    		$(cate).each(function(k,n){
    			if($(n)[0].localName.toLowerCase() == "category"){
    				
    				var name = $(n).attr("Name");
    				var id = $(n).attr("ID");
    				var subCategory = $(n).find("Category");
    				if(id == cateId){
    					var flagId = resType+"_"+idx;
    					if(_de.resConfigSets[flagId] == undefined)
    					_de.resConfigSets[flagId] = new Object();
    				
    					var configIds = $(n).find("ID"),configIdArr  = new Array();
    					for(var i =0;i < configIds.length;i++){
    						if(_de.resConfigSets[flagId][configId] != undefined){
    							continue;
    						}
    						var configId = $(configIds[i]).text();
    						if(resType == "DP"){
    							if(configId == "F_DP_Identifier" || configId == "F_DP_MaxSplitNum") continue;
    						}
    						configIdArr.push({type:resType,idx:idx,id:configId});
    					}
    					
    					var configXML = _p.get_res_configs(_de.connectId,_de.pu.puid,configIdArr);
					
    					var res = $(configXML).find("Res");
    					
    					for(var i = 0;i < res.length;i++){
    						var configId = $(res[i]).attr("OptID");
    						
    						_de.resConfigSets[flagId][configId] =res[i];
    					}
    					
    					return false;
    				}
    			}
    		});
        }
	},
	
	create_form_panel:function(n,resType,idx,cateId){
		var id = $(n).attr("id");
		
		if(id.toLowerCase() == "source"){
			
			var configId = "F_OV_VideoSources";
		
			var param = '<Res Type="'+resType+'" Idx="'+idx+'" OptID="'+configId+'" ></Res>';
	    	var res = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,param);
	    	
	    	var statusparam = '<Res Type="'+resType+'" Idx="'+idx+'" OptID="F_OV_VideoSourceStatusList" ></Res>';
	    	var statusres = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,statusparam);
	    	
	    	var statusArray = new Array();
			$(statusres).find("VideoSource").each(function(k,n){
				var s = $(n);
				statusArray.push(s.attr("status"))
			});
			
	    	var data = new Array();
	    	$(res).find("VideoSource").each(function(k,n){
	    		var d = $(n);
	    		var status = statusArray[k];
				data.push({index:k,bPlatform:d.attr("bPlatform"),Addr:d.attr("Addr"),EPID:d.attr("EPID"),AreaCode:d.attr("AreaCode"),UserID:d.attr("UserID"),PasswordHash:d.attr("PasswordHash"),PUID:d.attr("PUID"),Idx:parseInt(d.attr("Idx"))+1,Stream:parseInt(d.attr("Stream"))+1,DevName:d.attr("DevName"),Name:d.attr("Name"),Linger:d.attr("Linger"),AutoSwitch:d.attr("AutoSwitch"),Status:status});
			});			
	            _de.sourcesdata = data ;
	            
	            var switchMode = "";	            	
		    	var switchParam = '<Res Type="'+resType+'" Idx="'+idx+'" OptID="F_OV_SwitchMode" ></Res>';
		    	var switchRes = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,switchParam);
		    	var ress = $(switchRes).find("Param");
        		switchMode = $(ress).attr("Value");
		    	 
		        var fixVideo = new P_Utils.Hash();
		    	var fixVideoParam = '<Res Type="'+resType+'" Idx="'+idx+'" OptID="F_OV_FixedVideoSource" ></Res>';
		    	var fixVideoRes = _p.get_res_config_id_sets(_de.connectId,_de.pu.puid,fixVideoParam);
		
		    	var resss = $(fixVideoRes).find("Param");
		    	
        		fixVideo.addr = $(resss).attr("Addr");
        		fixVideo.puid = $(resss).attr("PUID");
        		fixVideo.idx = $(resss).attr("Idx");
        		fixVideo.Stream = $(resss).attr("Stream");
        		
	        	$('#sources_dr').datagrid({
					singleSelect:true,
					fitColumns: true,
					columns:[[
					  //{field:'ck',checkbox:true},
						{field:'Name',title:_lp.frame.decode.sources_list.name,width:200,align:'center',
							formatter:function(value,rec,index){
								var flag = false;
								if(switchMode == "Auto"){
									$('#switchmode').attr('checked',true); 
								}
								else{
									if(rec.Addr == fixVideo.addr && rec.PUID == fixVideo.puid && rec.Stream-1 == fixVideo.Stream && rec.PUID == fixVideo.puid  && rec.Idx-1 == fixVideo.idx && switchMode == "Fixed"){
										 flag = true;
									}
								}
								var btns = "";
								if(flag){
									  btns += '<div style="width:40px;float:left"><input type="radio" checked name="resourceradio" id="radio'+index+'" onclick="_de.fixresource(\''+index+'\',\''+resType+'\',\''+idx+'\')" /> </div>';
								}
								else{
									  btns += '<div style="width:40px;float:left"><input type="radio"  name="resourceradio" id="radio'+index+'" onclick="_de.fixresource(\''+index+'\',\''+resType+'\',\''+idx+'\')" /> </div>';
								}    
						      	btns += '<div style="float:left;padding-left:20px">'+value+'</div>';
						        return btns; 
		                    }  
						},
						{field:'DevName',title:_lp.frame.decode.sources_list.decname,width:200,align:'center'},
						{field:'Idx',title:_lp.frame.decode.sources_list.idx,width:150,align:'center'},
						{field:'Stream',title:_lp.frame.decode.sources_list.stream,width:150,align:'center',
							formatter:function(value,rec,index){
						        	return "流"+value;
						    }    
			            },
						{field:'Linger',title:_lp.frame.decode.sources_list.linger,width:150,align:'center'},			
						{field:'Addr',title:_lp.frame.decode.sources_list.addr,width:200,align:'center'},
						{field:'UserID',title:_lp.frame.decode.sources_list.userid,width:150,align:'center'},					
						{field:'AutoSwitch',title:_lp.frame.decode.sources_list.autoswitch,width:150,align:'center',
							formatter:function(value,rec,index){
								if(value == 0){
						        		return _lp.frame.decode.btns.no;
						        }
						        else{
						    		   return _lp.frame.decode.btns.yes;
						        }
						        
			                } 
						},
						{field:'Status',title:_lp.frame.decode.sources_list.status,width:200,align:'center'},	
						{field:'opt',title:_lp.frame.decode.sources_list.opt,width:200,align:'center',  
		                formatter:function(value,rec,index){  
	                        var btns = '<a href="javascript:void(0)" onclick="_de.modifyVideosource(\''+rec+'\',\''+index+'\',\''+rec.index+'\')" >'+_lp.frame.decode.btns.modify+'</a>&nbsp';
					        btns += '<a href="javascript:void(0)" onclick="_de.delVideosource(\''+rec+'\',\''+index+'\',\''+rec.index+'\')" >'+_lp.frame.decode.btns.del+'</a>&nbsp';
					        return btns; 
	                    }  
						}  
					]],
					onClickRow: function (rowIndex, rowData) {
                             $(this).datagrid('unselectRow', rowIndex);
                    },
	
				});	
	     	 $('#sources_dr').datagrid('loadData',_de.sourcesdata);
	     	 
	     	 
		}
		else{	
			var html = "";
		
			// 要判断cateId,有没有control元素要插入
			var controls = _cts.create_control_unit(resType,idx,cateId);
			// 没有子集,直接做form		
	    	html += '<div style="height:10px;width:100%;"></div>';
	
	    	if(controls[1] == "top"){
	    		html += controls[0];
	    	}
	    	
	    	html += '<div class="easyui-panel" data-options="width:800" title="'+$(n).attr("Name")+'" style="padding:10px 1px 0px 1px "><form id="'+resType+'_'+cateId.replace(".","_")+'_form">';
	    	if(controls[1] == "inlinetop"){
	    		html += controls[0];
	    	}
	    	html += '<table cellpadding="5">';
	
	    	html += _de.create_form_html(n,resType,idx,cateId);
	    	html += '</table></form>';
	    	if(controls[1] == "inlinebottom"){
	    		html += controls[0];
	    	}
	    	html += '</div>';
	    	
	    	return html;
			
			
		}
		
		 $(window).resize(function(){		 	
            var width = $(document.body).width()   
			$('#sources_dr').datagrid('resize',{
                 width : width-310
			});
		 });
	},
	
	addVideosource:function(resType,idx,cateId){
		if(_cf.type == "embed"){
			var html = '';
		    epid = _de.loginParam.epid || "system";
	    //	html += '<div id="res_de_tabs" class="easyui-layout" data-options="fit:true,border:false" style="width:auto;height:auto">';
	    //		html += '<div id="de_region_west" data-options="region:\'west\',border:false" style="width:149px;background-color:#3d3d3d;border-width: 1px 0 1px 1px;"></div>';
	    //		html += '<div id="de_region_center" data-options="region:\'center\',border:false"  style="width:auto;" ></div>';
	     //   html += '</div>';
	     
	     		html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.addr+':</label>';
					 html += '<input id="de_ip" name="ip" style="width:110px;" class="easyui-textbox" value='+_de.loginParam.ip+' >:<input id="de_port" name="port" style="width:55px;" class="easyui-textbox" value='+_de.loginParam.port+' >';
				html += '</div>';  
				
				html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.userid+':</label>';
					 html += '<input id="de_user" name="user" style="width:110px;" class="easyui-textbox" value='+_de.loginParam.username+' >:<input id="de_epid" name="epid" style="width:55px;" class="easyui-textbox" value='+epid+'>';
				html += '</div>';  
				
				html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.password+':</label>';
					 html += '<input id="de_psd" name="psd" type="password" style="width:110px;" class=\"easyui-textbox\" value='+_de.loginParam.password+' >';
					 html += '<input type="checkbox" id="remember" name="remember" checked onclick="_de.rememberParm()" style="vertical-align:middle;margin-top:0;" >';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.remumber+'</label>';	 				 
				html += '</div>';  
				   html += '<div id="wait_dlg"></div>';
			
	        
	         $("#decode1_dlg").dialog({
	  		 title:_lp.frame.decode.notes.note2,
	         modal: true,
	         width:350,
	  	     height:270,
	  	     content:html,
	         buttons: [{                    //设置下方按钮数组
	               text: _lp.frame.decode.btns.confirm,
	               iconCls: 'icon-ok',
	    		   handler: function () {
	    		   		$("#decode1_dlg").dialog('close');	
	    		   		var username = $("#de_user").val();
				   		var epId = $("#de_epid").val();
				   		var ip = $("#de_ip").val();
				   		var port = $("#de_port").val();
				   		var password = $("#de_psd").val();
				   		
				   		if($('#remember').attr('checked')) {
							_de.loginParam.username = $("#de_user").val();
							_de.loginParam.epid = $("#de_epid").val();
							_de.loginParam.ip = $("#de_ip").val();
							_de.loginParam.port = $("#de_port").val();
							_de.loginParam.password = $("#de_psd").val();
							_de.loginParam.remember = true;
				   		}
				   		else{
				   			_de.loginParam.username = "";
							_de.loginParam.epid = "";
							_de.loginParam.ip = "";
							_de.loginParam.port = "";
							_de.loginParam.password = "";
							_de.loginParam.remember = false;
				   		}			   		
				   		
						$('#wait_dlg').dialog({
							title:_lp.frame.notes.waiting_title1,
			    		    width:280,
			    		    height:100,
			    		    modal: true,
			    		    closable:false,
			    		    content:'<div style="width:100%;text-align:center;line-height:40px;">'+_lp.frame.notes.login_waiting+'</div>'
						});
						
						$('#wait_dlg').dialog('open');
						
						if(_de.newConnectId > 0 ){
							_p.disconnection(_de.newConnectId)
						}
						setTimeout(function(){
		            		_p.connect(ip,port,epId,username,password,_cf.bFix,_de.cb);
		            	},50);
				   		
	    		   }
	    		   }, {
	    		   text: _lp.frame.decode.btns.canael,
	               iconCls: 'icon-cancel',
	               handler: function () {
	               	   
	              	  $("#decode1_dlg").dialog('close');
	                }
	            }]
	        }); 
        }
	    if(_cf.type == "external"){
	    	if(_de.newConnectId > 0 ){
				_p.disconnection(_de.newConnectId)
			}	    	  		
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
            		_p.connect("","","","","",_cf.bFix,_de.cb);
            },50);		

		 }
	},
	
	cb:function(rv,connectId){
	
		if(rv != P_Error.SUCCESS){
			$('#wait_dlg').dialog('close');
            var rv = parseInt(rv);
            if(rv == 0x0302 || rv == 0x0301 || rv == 0x0304|| rv == 0x0305|| rv == 0x0203){
            	$.messager.alert(_lp.frame.notes.error_title2, _lp.frame.notes.error_msg1);
            }else if(rv == 0x030E){
            	$.messager.alert(_lp.frame.notes.error_title2, _lp.frame.notes.error_msg3);
            }else{
            	$.messager.alert(_lp.frame.notes.error_title2, _lp.frame.notes.error_msg2+"("+ rv+")");	
            }
            
			return;
		}		
		_de.newConnectId = connectId;
		
     	_de.fetch_resource(_de.newConnectId);
     	
     	
     	setTimeout(function(){
    		_de.refresh_pulist();
    	},100);
    
    	
		setTimeout(function(){
    		_de.addresource();
    	},500);
    	
    	_de.RowDatas.splice(0,_de.RowDatas.length)
	},
			
	fetch_resource:function(connectId){
		
		try
		{
			var puInfoArray = new Array();
			puInfoArray = _de.fetch_resource_bypage(connectId, "") || [];
			_de.puInfoArray = puInfoArray;
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
						if (puArrayPage.length <= count) {
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
        return puInfoArray || []; 
    },
    	
	refresh_pulist:function(){
	
		_de.IVDatas = new Array();
		
		for(var i = 0; i < _de.puInfoArray.length; i++){
			_de.IVDatas.push(_de.puInfoArray[i]);
		}	
		
		var domainRoads = P_LY.Connections.get(_de.newConnectId).domainRoad;
	    if(domainRoads && _p.isArray(domainRoads)){	
    		for(var j = 0; j < domainRoads.length; j++){
				var dr = domainRoads[j].domain_road;
				var cascadeResource = _p.cascadeResource.get(dr).puInfoArray;
				for(var k = 0; k < cascadeResource.length; k++){
					_de.IVDatas.push(cascadeResource[k]);
				}	
			}
	    }

	},

	rememberParm:function(){
		if($('#remember').attr('checked')) {
			$('#remember').attr('checked',false);
			_de.loginParam.username = "";
			_de.loginParam.epid = "";
			_de.loginParam.ip = "";
			_de.loginParam.port = "";
			_de.loginParam.password = "";
			_de.loginParam.remember = false;
		}
		else{
			$('#remember').attr('checked',true); 
			_de.loginParam.username = $("#user").val();
			_de.loginParam.epid = $("#epid").val();
			_de.loginParam.ip = $("#ip").val();
			_de.loginParam.port = $("#port").val();
			_de.loginParam.password = $("#psd").val();
			_de.loginParam.remember = true;
		}
	},

	addresource:function(){
		$('#wait_dlg').dialog('close');

		var html = '';
		
    	html += '<div id="iv_de_tabs" class="easyui-layout" data-options="fit:true,border:false" style="width:auto;height:auto">';
    		html += '<div id="iv_region_west" data-options="region:\'west\',border:false" style="width:200px;border-width: 1px 0 1px 1px;"><div style="margin:15px 5px 5px 15px;"><input  id="IVname" type="text" style="width:110px;border-width: 1px 1px 1px 1px;" > <a href="#" class="easyui-linkbutton" onclick="_de.search()" >'+_lp.frame.decode.btns.query+'</a></div><div id="iv_tabs"></div></div>';
    		html += '<div id="iv_region_center" data-options="region:\'center\',border:false"  style="width:auto;" ><table id="ivsorces_dr"></table></div>';
        html += '</div>';
        
      	
        $('<div></div>').dialog({
         id:'iv_dlg'+_de.pu.puid,	
  		 title:_lp.frame.decode.notes.note3,
         modal: true,
         width:850,
  	     height:470,
  	     content:html,
  	     closed: false,
		 cache: false,
		 closable: false,	 
  	     onOpen:function(){
  	   		var	data = new Array();
  	     	var htmlstr	=  ""
 	    	for(var i=0;i< _de.IVDatas.length;i++){
 	    	 	var d = _de.IVDatas[i];
 	    	 	if(d.modelType == "DEC" || d.modelType == "WDEC"  ) continue;
	 	    	if(d.online == 1 ){
	 	    	 	data.push(d);
	 	    	}
 	    	}
 	    
 	    	for(var j=0;j< _de.IVDatas.length;j++){
 	    	 	var d = _de.IVDatas[j];
 	    	 	if(d.modelType == "DEC" || d.modelType == "WDEC"  ) continue;
 	    	 	if(d.online == 0 ) data.push(d);
			}
			
			_de.IVDatas = data
			
			for(var k=0;k< _de.IVDatas.length;k++){
				var d = _de.IVDatas[k];
				var decname = d.name;
 	    	 	var puid = d.puid;
	 	    	if(d.online == 1 ){
	 	    		htmlstr += "<div id=\"pu_lists\" style=\"height:25px;line-height:25px;margin-top:5px;\" ><div class=\"icon-online\" ></div><div id=\"iv"+k+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div></div>";
	 	    	}
		 	    else{
		 	    	htmlstr += "<div id=\"pu_lists\" style=\"height:25px;line-height:25px;margin-top:5px;\" ><div class=\"icon-offline\" ></div><div id=\"iv"+k+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div></div>";
		 	    }
			}
 	    	$('#iv_tabs').html(htmlstr);
 	  	
			 
	      	_de.changeIV(_de.IVDatas[0].name);
       
			
 	     },
         buttons: [{                    //设置下方按钮数组
               text: _lp.frame.decode.btns.confirm,
               iconCls: 'icon-ok',
    		   handler: function () {
					_de.addIVsources();
    		   }
    		   }, {
    		   text: _lp.frame.decode.btns.canael,
               iconCls: 'icon-cancel',
               handler: function () {
               
               	 $("#iv_dlg"+_de.pu.puid).dialog('destroy')
                 
               	 
                }
            }]
        }); 
        
	},
		
	search:function(){
		var searchName = $("#IVname").val() || "";
		var htmlstr	=  ""
		if(searchName != ""){	
	    	for(var i=0;i< _de.IVDatas.length;i++){
	    	 	var d = _de.IVDatas[i];
	    	 	var decname = d.name;
	    	 	var puid = d.puid;	
	    	 	if(decname.indexOf(searchName) != -1 || decname == searchName){
	    			if(d.online == 1 ){
	 	    			htmlstr += "<div id=\"pu_lists\" style=\"height:25px;line-height:25px;margin-top:5px;\" ><div class=\"icon-online\" ></div><div id=\"iv"+i+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div></div>";
	 	    		}
	 	    		else{
		 	    		htmlstr += "<div id=\"pu_lists\" style=\"height:25px;line-height:25px;margin-top:5px;\" ><div class=\"icon-offline\" ></div><div id=\"iv"+i+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div></div>";
		 	    	}
				}
	    	}
		}else{
			for(var i=0;i< _de.IVDatas.length;i++){
	    	 	var d = _de.IVDatas[i];
	    	 	var decname = d.name;
	    	 	var puid = d.puid;
				if(d.online == 1 ){
	 	    		htmlstr += "<div id=\"pu_lists\" style=\"height:25px;line-height:25px;margin-top:5px;\" ><div class=\"icon-online\" ></div><div id=\"iv"+i+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div></div>";
	 	    	}
		 	    else{
		 	    	htmlstr += "<div id=\"pu_lists\" style=\"height:25px;line-height:25px;margin-top:5px;\" ><div class=\"icon-offline\" ></div><div id=\"iv"+i+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div></div>";
		 	    }
			}
		}	
    	$('#iv_tabs').html(htmlstr);
	},
		
	changeIV:function(name){
		
		var ivarr = new Array();
		var puid = "";
		for(var i=0;i<_de.IVDatas.length;i++){
			var d = _de.IVDatas[i];
			if(d.name == name){
				puid = d.puid;
				document.getElementById("iv"+i+"").style.fontWeight="bold";
			}
			else{
				if(document.getElementById("iv"+i+"")){
					document.getElementById("iv"+i+"").style.fontWeight="normal";	
				}
				
			}
		}
		
		var child = _p.query_puresource(_de.newConnectId, puid);
		
		for(var j=0;j<child.childResource.length;j++){
			var dt = child.childResource[j];
			if(dt.type == "IV"){
				ivarr.push({index:j,decname:name,puid:puid,idx:parseInt(dt.idx)+1,name:dt.name,stream:1,linger:10,autoswitch:1})
			}
		}
			
		/*
		for(var i=0;i<_de.IVDatas.length;i++){
			var d = _de.IVDatas[i];
			var puid = d.puid
			var pulist = new Array();
			if(d.childResource.length <=0){
				// 重新获取一下子资源
			
				var rv = _p.query_puresource(_de.newConnectId, d.puid);
		
				if(rv != null && typeof rv.childResource != "undefined" ){
					pulist = rv;
				}
			}
			
			if(d.name == title){
				for(var j=0;j<d.childResource.length;j++){
					var dt = d.childResource[j];
					if(dt.type == "IV"){
						ivarr.push({index:j,decname:d.name,puid:puid,idx:parseInt(dt.idx)+1,name:dt.name,stream:1,linger:10,autoswitch:1})
					}
				}
			}
		}*/
		var editRow = undefined;
		var lastIndex;
		var stream = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }];
		$('#ivsorces_dr').datagrid({
			singleSelect:false,
			checkOnSelect:false,
			onClickRow:"onClickRow",
			columns:[[
			    {field:'ck',checkbox:true},
			    {field:'decname',title:_lp.frame.decode.sources_list.decname,width:50,hidden:true},
				{field:'name',title:_lp.frame.decode.sources_list.name,width:190,align:'center'},
				{field:'stream',title:_lp.frame.decode.sources_list.stream,width:60,align:'center',
					formatter:function(value,rec,index){
						 return _lp.frame.decode.sources_list.stream1+value;
					},   
					editor:{
                       type:'combobox',
                       options:{
                           valueField:'value',
                           textField:'text',
                           data:stream,
                           required:true                        
                        }
                    }
				},
				{field:'linger',title:_lp.frame.decode.sources_list.linger,width:100,align:'center',editor:{ type: 'text',options:{ required: true}}},					
				{field:'autoswitch',title:_lp.frame.decode.sources_list.autoswitch,width:100,align:'center',editor:{type:'checkbox',options:{on:'1',off:'0'}},
					formatter:function(value,rec,index){
						if(value == 0){
				        		return _lp.frame.decode.btns.no;
				        }
				        else{
				    		   return _lp.frame.decode.btns.yes;
				        } 
	                } 
				},
				{field:'opt',title:_lp.frame.decode.sources_list.opt,width:100,align:'center',  
                formatter:function(value,rec,index){  
                    var btns = "<a href='javascript:_de.save(" + rec.index + ");'>"+_lp.frame.decode.btns.setmodify+"</a>&nbsp;";
			        return btns; 
                }  
				}  
			]],
			onSelect:function(rowIndex, rowData){
				if(_de.RowDatas.length > 0){
					var count = 0;
					for(var i = 0; i<_de.RowDatas.length;i++){
						var row = _de.RowDatas[i];
						if(row.index == rowData.index && row.puid == rowData.puid){
							count++;
						}
					}
					if(count == 0) _de.RowDatas.push(rowData);
				}else{
					_de.RowDatas.push(rowData);
				}
			},
			onSelectAll:function(rowData){
				for(var i = 0; i < rowData.length;i++){
					_de.RowDatas.push(rowData[i]);
				}
			
			},	
			onUnselectAll:function(rowData){
				for(var j = 0; j < rowData.length;j++){
					for(var i = 0; i < _de.RowDatas.length;i++){
						var row = _de.RowDatas[i];
						if(row == rowData[j]){
							_de.RowDatas.splice(i,1);
						}
					}	
				}

			},		
			onUnselect:function(rowIndex, rowData){	
				for(var i = 0; i<_de.RowDatas.length;i++){
					var row = _de.RowDatas[i];
					if(row.index == rowData.index && row.puid == rowData.puid){
						_de.RowDatas.splice(i,1);
					}
				}
			},
			onLoadSuccess:function(row){
				 var rowData = row.rows;				 
				 $.each(rowData,function(idx,val){
				 	for(var i = 0; i<_de.RowDatas.length;i++){
						var rowc = _de.RowDatas[i];
						if(rowc.index == val.index && rowc.puid == val.puid){
						//	console.log(idx)
						 	$("#ivsorces_dr").datagrid("checkRow",idx);
						 	break;
						}
					}	 
				});
			},		
			onAfterEdit: function (rowIndex, rowData, changes) {
            	editRow = undefined;
            	if (editRow != undefined) {
	                $("#ivsorces_dr").datagrid('endEdit', editRow);
	             }
        	},
        	onDblClickRow:function (rowIndex, rowData) {
            	if (editRow != undefined) {
                	$("#ivsorces_dr").datagrid('endEdit', editRow);
            	}
            	if (editRow == undefined) {
                	$("#ivsorces_dr").datagrid('beginEdit', rowIndex);
                	editRow = rowIndex;
            	}
       	 	},
       	 	onClickRow:function(rowIndex,rowData){
                 $(this).datagrid('unselectRow', rowIndex);
	           	 if (editRow != undefined) {
	                $("#ivsorces_dr").datagrid('endEdit', editRow);
	             }
	        }
		});	
		
	    $('#ivsorces_dr').datagrid('loadData',ivarr)
	    //
    
	},
	
	save:function(index){
	//	$("#ivsorces_dr").datagrid('unselectRow', index);
		$("#ivsorces_dr").datagrid('endEdit', index);
		$("#ivsorces_dr").datagrid('selectRow', index);
	},
		
	addIVsources:function(){
		var rows = _de.RowDatas;
		if(rows.length > 0)
    	{	
    		var t = P_LY.Connections.get(_de.newConnectId);
	    	if(_cf.type == "external"){
	    	
	    	}
    	//	alert(_cf.loginParam.ip)
    	//	alert(_cf.loginParam.epId)
    	//	alert(_cf.loginParam.port)			
    		var bplatform = 0;
	    	if(t.connType == "Device"){
	    		 bplatform = 0;
	    	}
	    	
		    if(t.connType == "Server"){
	    		 bplatform = 1;
	    	}
    		
    		var datalength = _de.sourcesdata.length;
    		var addr = _de.loginParam.ip+":"+_de.loginParam.port;
    		var epid = _de.loginParam.epid;
    		var username = _de.loginParam.username;
    		var password = "0x"+P_LY.Plug.nc.MD5Enc(_de.loginParam.password);
    		if(_cf.type == "external"){
	    		 addr = _cf.loginParam.ip+":"+_cf.loginParam.port;
    			 epid = _cf.loginParam.epId;
    			 username = _cf.loginParam.username;
    			 password = "0x"+P_LY.Plug.nc.MD5Enc(_cf.loginParam.password);
	    	}
	        for (var i = 0; i < rows.length; i++) {
	        	var row = rows[i];			_de.sourcesdata.push({index:i+datalength,bPlatform:bplatform,Addr:addr,EPID:epid,AreaCode:"0551",UserID:username,PasswordHash:password,PUID:row.puid,Idx:row.idx,Stream:row.stream,DevName:row.decname,Name:row.name,Linger:row.linger,AutoSwitch:row.autoswitch});
	    	}
	        	
	         	  $('#sources_dr').datagrid('loadData',_de.sourcesdata);
		          _de.checkModify = true;
		          $('#checkmodify').css("display","block"); 
		       	  $('#switchmode').attr('disabled',true);	
		       	  $("input:radio[name='resourceradio']").attr("disabled",true);
		       	  
		       	  $("#iv_dlg"+_de.pu.puid).dialog('destroy');
		}
    	else{
    		 $.messager.alert(_lp.frame.decode.notes.note4, _lp.frame.decode.notes.note5, 'error');
    	}
    	
	},
	
	modifyVideosource:function(rec,index,dataindex){
		var t = P_LY.Connections.get(_de.connectId);
		
		$('#sources_dr').datagrid('selectRow',index);	
		
		var row = $('#sources_dr').datagrid("getSelected");
	//	console.log(row)
		var str =  row.Addr.split(":");
		var ip = str[0];
		var port = str[1];
		var idx = row.Idx;
		var puid = row.PUID;
		var stream = row.Stream;
		
		
		var html = "";
		html += '<div style="margin-top:20px"><input id="bplatform" name="bplatform"  value='+row.bPlatform+' type="hidden"  >';
    	 	html += '<div class="fitem" id="classfitem" style="margin-top:40px;margin-left:20px">';
				 html += '<span style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.name+':</span>';
				 html += '<input id="name" name="name" style="width:155px;" class="easyui-textbox" value='+row.Name+'  >';
			html += '</div>';  
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.decname+':</label>';
				 html += '<input id="decname" name="decname" style="width:155px;" class="easyui-textbox" value='+row.DevName+'  >';
			html += '</div>'; 
			
			if(row.bPlatform == 1){
				html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">PUID:</label>';
					 html += '<input id="puid" name="puid" style="width:155px;" class="easyui-textbox" value='+row.PUID+' >';
				html += '</div>';  
			}
				html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.idx+':</label>';
					 html += '<input id="idx" name="idx" style="width:55px;" class="easyui-textbox" value='+idx+' >('+_lp.frame.decode.btns.msg+')';
				html += '</div>';  
			/*
			if(t.connType == "Device"){
				html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.idx+':</label>';
					 html += '<input id="idx" name="idx" style="width:55px;" class="easyui-textbox" value='+idx+' >('+_lp.frame.decode.btns.msg+')';
				html += '</div>';  
			}else{
				html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
					 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.idx+':</label>';
					 html += '<input id="idx" name="idx" style="width:55px;" class="easyui-combobox" >('+_lp.frame.decode.btns.msg+')';
				html += '</div>';  
			}
			*/
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.stream+':</label>';
				 html += '<input id="stream" name="stream" style="width:55px;" class="easyui-combobox"  >('+_lp.frame.decode.btns.msg+')';
			html += '</div>';  
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.linger1+':</label>';
				 html += '<input id="linger" name="linger" style="width:55px;" class="easyui-textbox" value='+row.Linger+' >(s)';
			html += '</div>';  
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.addr+':</label>';
				 html += '<input id="ip" name="ip" style="width:100px;" class="easyui-textbox" value='+ip+' >:<input id="port" name="port" style="width:55px;" class="easyui-textbox" value='+port+' >';
			html += '</div>';  
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.userid+':</label>';
				 html += '<input id="user" name="user" style="width:100px;" class="easyui-textbox" value='+row.UserID+' >:<input id="epid" name="epid" style="width:55px;" class="easyui-textbox" value='+row.EPID+' >';
			html += '</div>';  
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.password+':</label>';
				 html += '<input id="psd" name="psd" type="password" style="width:290px;"  maxlength="8" class=\"easyui-textbox\" value='+row.PasswordHash+' >';
			html += '</div>';  
			
			html += '<div class="fitem" id="classfitem" style="margin-top:20px;margin-left:20px">';
				 html += '<label style="display: inline-block; width: 80px;">'+_lp.frame.decode.sources_list.autoswitch+':</label>';
				 if(row.AutoSwitch == 1){
				 	 html += '<input type="checkbox" id="switch1" name="switch1" style="vertical-align:middle;margin-top:0;" checked="checked" onclick="_de.changeswitch()" >';
				 }
				 else{
				 	html += '<input type="checkbox" id="switch1" name="switch1"  style="vertical-align:middle;margin-top:0;" onclick="_de.changeswitch()">';
				 }
				
			html += '</div>';  
    	html += '</div>';    	
	
		
		if($("#modify_dlg"+_de.pu.puid)){
		 	$("#modify_dlg"+_de.pu.puid).dialog('destroy');
		}
         $('<div></div>').dialog({
	         id:'modify_dlg'+_de.pu.puid,	
	  		 title:_lp.frame.decode.notes.note6,
	         modal: true,
	         resizable:true,
	         width:450,
	  	     height:600,
	  	     content:html,
	  	     closed: false,
			 cache: false,
			 closable: false,
			 onBeforeOpen:function(){
			 	 /*
			 	 if(t.connType == "Server"){
			 	 	var iv = new Array();
			 	 	var rv = _p.query_puresource(_de.connectId,row.PUID);
			 	 
			 	 	var childResoure = rv.childResource;
			 	 	for(var i = 0 ; i < childResoure.length ;i++ ){
						var child = childResoure[i];
						if(child.type == "IV"){
							iv.push({text:parseInt(child.idx)+1,value:parseInt(child.idx)+1})
						}
					}
				
					$('#idx').combobox('loadData',iv);
					$('#idx').combobox('select',idx);
				 }*/
			 	var streamData = [{ "value": "1", "text": "1" }, { "value": "2", "text": "2" }];
			 	$('#stream').combobox('loadData',streamData);
			 	$('#stream').combobox('select',stream);
			 },	 	 
	         buttons: [{                    //设置下方按钮数组
	               text: _lp.frame.decode.btns.confirm,
	               iconCls: 'icon-ok',
	    		   handler: function () {
		   			   		var Name = $("#name").val();
		   			   		var DevName = $("#decname").val();
		   			   		var Idx = $("#idx").val();
		   			   		var Stream = $('#stream').combobox('getValue');
		   			   		var Linger = $("#linger").val();
		   			   		var Addr = $("#ip").val()+":"+$("#port").val();
		   			   		var UserID = $("#user").val();
		   			   		var EPID = $("#epid").val();
		   			   		var PasswordHash = $("#psd").val();
		   			   		var bPlatform = $("#bplatform").val();
		   			   		var AutoSwitch = 0;
		   			   		if($('#switch1').attr('checked')) {
		    				    AutoSwitch = 1;
							}	
							var PUID = "";
							if (bPlatform == 1) {
		    				    PUID = $("#puid").val();
							}	
							
							if($("#radio"+index).is(':checked')){
								_de.fixedResource.addr = Addr;
								_de.fixedResource.puid = PUID;
								_de.fixedResource.idx = Idx;
								_de.fixedResource.stream = Stream;
								_de.fixedResource.type = "modify";
							}
							
					   for(var i = 0;i < _de.sourcesdata.length;i++){
				         	var data = _de.sourcesdata[i]
				         	if(data.index == dataindex){
				         		_de.sourcesdata[i].Name = Name;
				         		_de.sourcesdata[i].DevName = DevName;
				         		_de.sourcesdata[i].PUID = PUID;
				         		_de.sourcesdata[i].Idx = Idx;
				         		_de.sourcesdata[i].Stream = Stream;
				         		_de.sourcesdata[i].Linger = Linger;
				         		_de.sourcesdata[i].Addr = Addr;
				         		_de.sourcesdata[i].UserID = UserID;
				         		_de.sourcesdata[i].EPID = EPID;
				         		_de.sourcesdata[i].PasswordHash = PasswordHash;
				         		_de.sourcesdata[i].bPlatform = bPlatform;
				         		_de.sourcesdata[i].AutoSwitch = AutoSwitch;
				         	}	
				         }
				        		
				          $('#sources_dr').datagrid('loadData',_de.sourcesdata)
				       	  $("#modify_dlg"+_de.pu.puid).dialog('destroy');
				       	  $('#checkmodify').css("display","block"); 
				       	  _de.checkModify = true;
				       	  $('#switchmode').attr('disabled',true);
				       	  $("input:radio[name='resourceradio']").attr("disabled",true);
		    		   }
	    		   }, {
	    		   text: _lp.frame.decode.btns.canael,
	               iconCls: 'icon-cancel',
	               handler: function () {
	              	  	  $("#modify_dlg"+_de.pu.puid).dialog('destroy');
	                }
	            }]
        });
     
	},
	
	fixresource:function(index,resType,idx){
	//	console.log(index)
		//<Res Type="OV" Idx="0" OptID="F_OV_SwitchMode"  ><Param Value="Auto/Fixed" ></Param></Res>
		
		$('#sources_dr').datagrid('selectRow',index);
		var row = $('#sources_dr').datagrid("getSelected");
		
		var addr =  row.Addr
		var resourceidx = parseInt(row.Idx)-1;
		var stream = parseInt(row.Stream)-1;
		var puid = row.PUID;
	
		var params ="";
		params += '<Res Type='+'"'+resType+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="F_OV_FixedVideoSource"  >';
    	params += '<Param ';
        params += 'Addr='+'"'+addr+'" ';
        params += 'PUID='+'"'+puid+'" ';
        params += 'Idx='+'"'+resourceidx+'" ';
        params += 'Stream='+'"'+stream+'" ';
		params += '/>';
    	params += '</Res>';
    	params += '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Fixed" ></Param></Res>';
		var rv = P_LY.Config.SetComplexEx(
			_de.connectId,
			{
				puid:_de.pu.puid,
				language:_cf.language,
				resType:resType,
				resIdx:idx,
				dstRes:params
			}
		 	).response;
		 	
	    	if(rv && rv.M && rv.M.C){
				if(_p.isArray(rv.M.C.Res)){
					var msg = "";
					for(var i = 0;i < rv.M.C.Res.length;i++){
						var res = rv.M.C.Res[i];
						if(res.Error == "0"){
					//		_de.set_ov_config(resType,idx);
						}else{
							msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
						}
					}
					if(msg == "") msg = _lp.frame.configsets.notes.note;
					
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:msg,
		                timeout:4000,
		                showType:'slide'
		            });
		          	$('#switchmode').attr('checked',false);  
		          	//_de.changeswitchmode(resType,idx)

				}else{
					if(rv.M.C.Res.Error == "0"){
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.note,
			                timeout:4000,
			                showType:'slide'
			            });
			            //_de.set_ov_config(resType,idx);	
			         	$('#switchmode').attr('checked',false); 
			         //	_de.changeswitchmode(resType,idx)
						
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
		
	},
	changeswitch:function(){
		if($('#switch1').attr('checked')) {
			$('#switch1').attr('checked',false);   
		}else{
			$('#switch1').attr('checked',true);  
		}
	},
	changeswitchmode:function(resType,idx){

		//<Res Type="OV" Idx="0" OptID="F_OV_SwitchMode"  ><Param Value="Auto/Fixed" ></Param></Res>
		if($('#switchmode').attr('checked')) {
		//	$('#switchmode').attr('checked',false);	
			var params = '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Fixed" ></Param></Res>';
			var rv = P_LY.Config.SetComplexEx(
			_de.connectId,
			{
				puid:_de.pu.puid,
				language:_cf.language,
				resType:resType,
				resIdx:idx,
				dstRes:params
			}
		 	).response;
		 	
	    	if(rv && rv.M && rv.M.C){
				if(_p.isArray(rv.M.C.Res)){
					var msg = "";
					for(var i = 0;i < rv.M.C.Res.length;i++){
						var res = rv.M.C.Res[i];
						if(res.Error == "0"){
							
						}else{
							msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
						}
					}
					if(msg == "") msg = _lp.frame.configsets.notes.note;
					
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:msg,
		                timeout:4000,
		                showType:'slide'
		            });
		          	$('#switchmode').attr('checked',false);
		            var row  =$('#sources_dr').datagrid('getRows');
			        if(row.length > 0 ){
		        		var addr =  row[0].Addr
						var resourceidx = parseInt(row[0].Idx)-1;
						var stream = parseInt(row[0].Stream)-1;
						var puid = row[0].PUID;
					
						var params ="";
						params += '<Res Type='+'"'+resType+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="F_OV_FixedVideoSource"  >';
				    	params += '<Param ';
				        params += 'Addr='+'"'+addr+'" ';
				        params += 'PUID='+'"'+puid+'" ';
				        params += 'Idx='+'"'+resourceidx+'" ';
				        params += 'Stream='+'"'+stream+'" ';
						params += '/>';
				    	params += '</Res>';
				    	params += '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Fixed" ></Param></Res>';
						var rv = P_LY.Config.SetComplexEx(
							_de.connectId,
							{
								puid:_de.pu.puid,
								language:_cf.language,
								resType:resType,
								resIdx:idx,
								dstRes:params
							}
						 	).response;
					//	$("input[name=resourceradio]:eq(0)").attr("checked","checked"); 
			        }  
					//$("input:radio[name='resourceradio']").attr("checked",false);
		            
				}else{
					if(rv.M.C.Res.Error == "0"){
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.note,
			                timeout:4000,
			                showType:'slide'
			            });
			         	$('#switchmode').attr('checked',false); 
			         	var row  =$('#sources_dr').datagrid('getRows');
				        if(row.length > 0 ){
				        	var addr =  row[0].Addr
							var resourceidx = parseInt(row[0].Idx)-1;
							var stream = parseInt(row[0].Stream)-1;
							var puid = row[0].PUID;
						
							var params ="";
							params += '<Res Type='+'"'+resType+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="F_OV_FixedVideoSource"  >';
					    	params += '<Param ';
					        params += 'Addr='+'"'+addr+'" ';
					        params += 'PUID='+'"'+puid+'" ';
					        params += 'Idx='+'"'+resourceidx+'" ';
					        params += 'Stream='+'"'+stream+'" ';
							params += '/>';
					    	params += '</Res>';
					    	params += '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Fixed" ></Param></Res>';
							var rv = P_LY.Config.SetComplexEx(
								_de.connectId,
								{
									puid:_de.pu.puid,
									language:_cf.language,
									resType:resType,
									resIdx:idx,
									dstRes:params
								}
							 	).response;
							 	
						
							 	$('#radio0').prop('checked',false); 
							 	$('#radio0').prop('checked',true);   
				        }   
				       
						//$("input:radio[name='resourceradio']").attr("checked",false);
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
			
		}else{			
			var params = '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Auto" ></Param></Res><Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_FixedVideoSource"  ><Param/></Res>';
			var rv = P_LY.Config.SetComplexEx(
			_de.connectId,
			{
				puid:_de.pu.puid,
				language:_cf.language,
				resType:resType,
				resIdx:idx,
				dstRes:params
			}
		 	).response;
		 	
	    	if(rv && rv.M && rv.M.C){
				if(_p.isArray(rv.M.C.Res)){
					var msg = "";
					for(var i = 0;i < rv.M.C.Res.length;i++){
						var res = rv.M.C.Res[i];
						if(res.Error == "0"){
							
						}else{
							msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
						}
					}
					if(msg == "") msg = _lp.frame.configsets.notes.note;
					
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:msg,
		                timeout:4000,
		                showType:'slide'
		            });
		          	$('#switchmode').attr('checked',true);  
					$("input:radio[name='resourceradio']").attr("checked",false);
		            
				}else{
					if(rv.M.C.Res.Error == "0"){
			            $.messager.show({
			                title:_lp.frame.configsets.notes.noteTitle,
			                msg:_lp.frame.configsets.notes.note,
			                timeout:4000,
			                showType:'slide'
			            });
			         	$('#switchmode').attr('checked',true);  
						$("input:radio[name='resourceradio']").attr("checked",false);
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
			
		}
	},
	delVideosource:function(rec,index,dataindex){
		
		$.messager.confirm(_lp.frame.decode.notes.note7,_lp.frame.decode.notes.note8, function (r) {
		      if (r) {
		      	$('#sources_dr').datagrid('selectRow',index);	
				var row = $('#sources_dr').datagrid("getSelected");
				if($("#radio"+index).is(':checked')){
					_de.fixedResource.addr = row.Addr;
					_de.fixedResource.puid = row.PUID;
					_de.fixedResource.idx = row.Idx;
					_de.fixedResource.stream = row.Stream;
					_de.fixedResource.type = "del";
				}
				
		      	 var newData = new Array(); 
		         for(var i = 0;i < _de.sourcesdata.length;i++){
		         	var data = _de.sourcesdata[i]
		         	if(data.index == dataindex) continue;
		         	newData.push(data);
		         }
		          _de.sourcesdata = newData;
		          $('#sources_dr').datagrid('loadData',_de.sourcesdata);
		          _de.checkModify = true;
		          $('#checkmodify').css("display","block"); 
		       	  $('#switchmode').attr('disabled',true);	
		       	  $("input:radio[name='resourceradio']").attr("disabled",true);
		      }
	  	  });
	},
	
	// 创建一个分类配置项表单
	create_sub_form_panel:function(obj,resType,idx){
		var html = "",cnt = 0,m= 0;		
		$(obj).find("Category").each(function(k,n){
        	var cateId = $(n).attr("ID");
        	html += _de.create_form_panel(n,resType,idx,cateId);
        }); 	
        return html;
	},
	
	create_form_html:function(n,resType,idx,cateId){
		var html = '',bSubmit = false,configIdArr = new Array(),configIds=$(n).children("ID");   //取对象

		//configXML = _de.configXML;
		var flagId = resType+"_"+idx;
		for(var i = 0;i < configIds.length;i++){
    		var configId = $(configIds[i]).text();
    		if(_de.resConfigSets[flagId] != undefined && _de.resConfigSets[flagId][configId] != undefined){
    			var res = _de.resConfigSets[flagId][configId];
    			
    			var param = $(res).children("Param");
    			
    			val = $(param).attr("Value");
    			attrs = $(res).children("Attr");
    			
    			if(attrs.attr('Setable')== '1'){
    				bSubmit = true;
    			}
                if(attrs == null) continue;
        		if(typeof attrs.attr("Type") == "undefined") continue;
       
        		if(attrs.attr("Type").toLowerCase() == "xml"){     		
        			html += _cfs.create_form_xml(resType,configId,attrs,res);    	      			      				
        		}else{
        			html += _cfs.create_form_input_unit(resType,configId,attrs,val,param,res);	
        		}
    		}
    	}
	
    	if(bSubmit){
    		html += '<tr><td></td><td >';
    		if(_de.submitAllCates.search(cateId) > -1){
        		html += '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_de.set_config_toallchannel(\''+cateId+'_form\',\''+resType+'\')">'+_lp.frame.decode.btns.okall+'</a>&nbsp;';
    		}
    		html += '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_de.set_config(\''+cateId+'_form\',\''+resType+'\',\''+idx+'\')">'+_lp.frame.configsets.btns.ok+'</a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="_de.refresh_resource_configsets(\''+cateId+'\',\''+resType+'\',\''+idx+'\')">'+_lp.frame.configsets.btns.refresh+'</a></td>'; 
    	}
    	// 判断此分类里是否有要处理的control命令    	
		return html;
	},
	
	set_ov_config:function(resType,idx){
		if(_de.fixedResource.type == "modify"){
			var addr = _de.fixedResource.addr ;
			var puid = _de.fixedResource.puid ;
			var resourceidx = parseInt(_de.fixedResource.idx)-1 ;
			var stream = parseInt(_de.fixedResource.stream)-1 ;
			
			var params ="";
			params += '<Res Type='+'"'+resType+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="F_OV_FixedVideoSource"  >';
	    	params += '<Param ';
	        params += 'Addr='+'"'+addr+'" ';
	        params += 'PUID='+'"'+puid+'" ';
	        params += 'Idx='+'"'+resourceidx+'" ';
	        params += 'Stream='+'"'+stream+'" ';
			params += '/>';
	    	params += '</Res>';
	    	params += '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Fixed" ></Param></Res>';
	    	//console.log(params)
	    	var rv = P_LY.Config.SetComplexEx(
			_de.connectId,
			{
				puid:_de.pu.puid,
				language:_cf.language,
				resType:resType,
				resIdx:idx,
				dstRes:params
			}
		 	).response; 	
		 	
		 	//console.log(rv)
		 	if(rv && rv.M && rv.M.C){
				if(_p.isArray(rv.M.C.Res)){
					var msg = "";
					for(var i = 0;i < rv.M.C.Res.length;i++){
						var res = rv.M.C.Res[i];
						if(res.Error == "0"){
							_de.fixedResource.addr = "";
							_de.fixedResource.puid = "";
							_de.fixedResource.idx = "";
							_de.fixedResource.stream = "";
							_de.fixedResource.type = "";
						}
					}	            
				}else{
					if(rv.M.C.Res.Error == "0"){
			           	_de.fixedResource.addr = "";
						_de.fixedResource.puid = "";
						_de.fixedResource.idx = "";
						_de.fixedResource.stream = "";
						_de.fixedResource.type = "";
					}
				}
			}
		}
		if(_de.fixedResource.type == "del"){
				var params = '<Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_SwitchMode"  ><Param Value="Auto" ></Param></Res><Res Type='+'"'+resType+'" Idx='+'"'+idx+'" OptID="F_OV_FixedVideoSource"  ><Param/></Res>';
			var rv = P_LY.Config.SetComplexEx(
			_de.connectId,
			{
				puid:_de.pu.puid,
				language:_cf.language,
				resType:resType,
				resIdx:idx,
				dstRes:params
			}
		 	).response;
		 	
		 	if(rv && rv.M && rv.M.C){
				if(_p.isArray(rv.M.C.Res)){
					var msg = "";
					for(var i = 0;i < rv.M.C.Res.length;i++){
						var res = rv.M.C.Res[i];
						if(res.Error == "0"){
							_de.fixedResource.addr = "";
							_de.fixedResource.puid = "";
							_de.fixedResource.idx = "";
							_de.fixedResource.stream = "";
							_de.fixedResource.type = "";
						}
					}	            
				}else{
					if(rv.M.C.Res.Error == "0"){
			           	_de.fixedResource.addr = "";
						_de.fixedResource.puid = "";
						_de.fixedResource.idx = "";
						_de.fixedResource.stream = "";
						_de.fixedResource.type = "";
					}
				}
			}
		}
		
		
		var values = new Array();
		var attrdata = new Array();
		var configID = "F_OV_VideoSources";
		var params = "";
		params += '<Res Type="'+(resType||"")+'" Idx="'+(typeof idx != "undefined" && !isNaN(idx) ? idx : "")+'" OptID="'+(configID||"")+'"  >';
    	params += '<Param>';
		 for(var i = 0;i < _de.sourcesdata.length;i++){
		 	var d = _de.sourcesdata[i]; 
		 	var Idx = parseInt(d.Idx)-1;
		 	var Stream = parseInt(d.Stream)-1;
		 	params += '<VideoSource ';
         	params += 'bPlatform='+'"'+d.bPlatform+'" ';
         	params += 'Addr='+'"'+d.Addr+'" ';
         	params += 'EPID='+'"'+d.EPID+'" ';
         	params += 'AreaCode='+'"'+d.AreaCode+'" ';
         	params += 'UserID='+'"'+d.UserID+'" ';
         	params += 'PasswordHash='+'"'+d.PasswordHash+'" ';
         	params += 'PUID='+'"'+d.PUID+'" ';
         	params += 'Idx='+'"'+Idx+'" ';
         	params += 'Stream='+'"'+Stream+'" ';
         	params += 'DevName='+'"'+d.DevName+'" ';
         	params += 'Name='+'"'+d.Name+'" ';
         	params += 'Linger='+'"'+d.Linger+'" ';
         	params += 'AutoSwitch='+'"'+d.AutoSwitch+'" ';
        	params += '/>';
         }
		 params += '</Param>';
    	 params += '</Res>';
    	 
		 var rv = P_LY.Config.SetComplexEx(
			_de.connectId,
			{
				puid:_de.pu.puid,
				language:_cf.language,
				resType:resType,
				resIdx:idx,
				dstRes:params
			}
		 ).response;
		
    	if(rv && rv.M && rv.M.C){
			if(_p.isArray(rv.M.C.Res)){
				var msg = "";
				for(var i = 0;i < rv.M.C.Res.length;i++){
					var res = rv.M.C.Res[i];
					if(res.Error == "0"){
						
					}else{
						msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
					}
				}
				if(msg == "") msg = _lp.frame.configsets.notes.note;
				
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:msg,
	                timeout:4000,
	                showType:'slide'
	            });
	            _de.init_region_center(resType,idx)
	            _de.checkModify = false;
	            $('#checkmodify').css("display","none");
	            $('#switchmode').attr('disabled',false);	
		       	$("input:radio[name='resourceradio']").attr("disabled",false);
	            
			}else{
				if(rv.M.C.Res.Error == "0"){
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.note,
		                timeout:4000,
		                showType:'slide'
		            });
		            _de.init_region_center(resType,idx)
		            _de.checkModify = false;
		            $('#checkmodify').css("display","none");
		            $('#switchmode').attr('disabled',false);	
		       	  	$("input:radio[name='resourceradio']").attr("disabled",false);
				}else{
					_de.checkModify = false;
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:_lp.frame.configsets.notes.noteError+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
				
			}
		}else{
			_de.checkModify = false;
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.configsets.notes.noteError+'（response:'+rv+'）。',
                timeout:4000,
                showType:'slide'
            });
    	}
	},
	
	refresh_ov_configsets:function(resType,idx){
		if(_de.checkModify == true){
			   $.messager.confirm(_lp.frame.decode.notes.note9, _lp.frame.decode.notes.note10, function (r) {
			       if (r) {
			       		_de.init_region_center(resType,idx)
			       }
	           });	
		}
		else{
			 _de.init_region_center(resType,idx)
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
		html += '<td width=200><select id="'+resType.toLowerCase()+'_channel_cmb" class="easyui-combobox" data-options="panelHeight:\'auto\',onSelect:function(rec){_de.resource_onchange(\''+resType+'\',rec)}"   style="width:195px;height:auto;" >';
		var idx = 0;
		if(resType.toLowerCase() != "st"){
			for(var i = 0;i < _de.pu.childResource.length;i++){
				if(_de.pu.childResource[i].type == resType){
					html += '<option value="'+_de.pu.childResource[i].idx+'">'+_de.pu.childResource[i].name+'</option>';
					idx = _de.pu.childResource[i].idx;
					if(idx == 0){
						desc.name = _de.pu.childResource[i].name;
						desc.description = _de.pu.childResource[i].description;
						desc.usable = _de.pu.childResource[i].usable;
						desc.enable = _de.pu.childResource[i].enable;
					}
				}
			}

			
		}else{
			html += '<option value=0 >'+_de.pu.name+'</option>';
			desc.name = _de.pu.name;
			desc.description = _de.pu.description;
			desc.usable = 1;
			desc.enable = 1;
		}
		
		html += '</select></td>';
		
		html += '<td width=120><a href="javascript:void(0)" class="easyui-linkbutton"  onclick="_de.open_resource_rename_dlg(\''+resType+'\')">'+_lp.frame.configsets.btns.rename+'</a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_de.open_resource_setdesc_dlg(\''+resType+'\')" >'+_lp.frame.decode.btns.setdesc+'</a></td>';
		
		html += '<td width=30>'+_lp.frame.configsets.st.idx.lbl+':</td>';
		html += '<td width=30><span id="'+resType.toLowerCase()+'_idx_lbl">'+idx+'</span></td>';

		if(resType.toLowerCase() != "st"){
			html += '<td width=60></td>';
			var checked = (desc.enable == 1 ? false:true);
			html += '<td width=120><input id="'+resType.toLowerCase()+'_disabled_chk" type=checkbox style="vertical-align：middle;" onclick="_de.resource_disabled(\''+resType+'\',this.checked)"  checked="'+checked+'" />&nbsp;<label>'+_lp.frame.configsets.btns.disabled+'</label></td>';
				
			html += '<td width=60>'+_lp.frame.configsets.st.bEnable.lbl+':</td>';
			html += '<td width=60><span id="'+resType.toLowerCase()+'_usable_span" >'+(desc.usable == 1 ? 'YES':'NO')+'</span></td>';
		}else{
			html += '<td width=60></td>';
			html += '<td width=120>&nbsp;</td>';
				
			html += '<td width=60> </td>';
			html += '<td width=60 >&nbsp;</td>';
		}
		
		html += '</tr>';

		html += '</table></form>';
		html += '</div>';
		
		
        $('#'+resType.toLowerCase()+'_de_region_north').html(html);
		
		$.parser.parse('#'+resType.toLowerCase()+'_de_region_north');

		setTimeout(function(){
			// 默认展开第一个资源
			if($('#'+resType.toLowerCase()+'_channel_cmb')){
				try{
					var v = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");	
					_de.resource_onchange(resType,{value:v});	
				}catch(e){
					//alert(e.message)
				}
				
			}
			
			
		},1);		
		return		
	},
	refresh_resource_desc:function(resType,idx){
		var rv = _p.query_puresource(_de.connectId,_de.pu.puid);
		
		if(_p.isArray(rv) && rv.length <= 0){
			
		}else{
			_de.pu = rv;
		}
		var d = new Array();
		if(resType.toLowerCase() == "st"){
			d.push({value:0,text:_de.pu.name});
			$('#'+resType.toLowerCase()+'_channel_cmb').combobox('loadData',d);
			_de.get_resource_value(resType,idx);
		}else{
			var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
			for(var i = 0;i < _de.pu.childResource.length;i++){
				if(_de.pu.childResource[i].type == resType){
					d.push({value:_de.pu.childResource[i].idx,text:_de.pu.childResource[i].name});
				}
			}
			
			$('#'+resType.toLowerCase()+'_channel_cmb').combobox('loadData',d);
			$('#'+resType.toLowerCase()+'_channel_cmb').combobox('select',idx);
		}
	},
	refresh_resource_configsets:function(cateId,resType,idx){
		var cates = _de.configIds;
		_de.get_resource_configsets(cateId,resType,idx);
        for(var i =0;i < cates.length;i++){
        	var cate = $(cates[i]).children();
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
    						if(resType == "DP"){
    							if(configId == "F_DP_Identifier" || configId == "F_DP_MaxSplitNum") continue;
    						}
    						configIdArr.push({type:resType,idx:idx,id:configId});
    					}

    					_de.refresh_resource_configsetsbyid(true,configIdArr,null);

    					if(subCategory.length > 0){
        					contentHtml = _de.create_sub_form_panel(n,resType,idx,cateId);
    					}else{
        					contentHtml = _de.create_form_panel(n,resType,idx,cateId);
    					}
    					

						var tabsId='#'+resType.toLowerCase()+'_de_tabs';
						
						var t = $(tabsId).tabs("getTab",name);
						
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
		return;
		//_de.configSets[resType] = $(res[i]);

		/*
        if(_de.configSets[resType]){
			var configIdArr = new Array();
	    	var configIds = $(_de.configSets[resType]).find("ID");
	        for(var i =0;i < configIds.length;i++){
	    		var configId = $(configIds[i]).text();
	    		configIdArr.push({type:resType,idx:idx,id:configId});
	        }
        }
        
		_de.configXML = _p.get_res_configs(_de.connectId,_de.pu.puid,configIdArr);
		//console.log(_de.configXML);
		var flagId = resType+"_"+idx;
		_de.resConfigSets[flagId] = new Object();
		var res = $(_de.configXML).find("Res");
		for(var i = 0;i < res.length;i++){
			var configId = $(res[i]).attr("OptID");
			_de.resConfigSets[flagId][configId] =res[i];
		}
        
		var tab = $('#'+resType.toLowerCase()+'_de_tabs').tabs('getSelected');
		var index = $('#'+resType.toLowerCase()+'_de_tabs').tabs('getTabIndex',tab);
		_de.resource_tab_onselect(null,index,resType);
		
		*/
		
/*
		var flagId = resType+"_"+idx;
		if(_de.resConfigSets[flagId] == undefined)
		_de.resConfigSets[flagId] = new Object();
		
		var configIds = $(n).find("ID"),configIdArr  = new Array();
		for(var i =0;i < configIds.length;i++){
			if(_de.resConfigSets[flagId][configId] != undefined){
				continue;
			}
			var configId = $(configIds[i]).text();
			configIdArr.push({type:resType,idx:idx,id:configId});
		}
		_de.configXML = _p.get_res_configs(_de.connectId,_de.pu.puid,configIdArr);

		var tab = $('#'+resType.toLowerCase()+'_de_tabs').tabs('getSelected');
		var index = $('#'+resType.toLowerCase()+'_de_tabs').tabs('getTabIndex',tab);
		_de.resource_tab_onselect(null,index,resType);
		*/
		var cates = _de.configIds;
		_de.get_resource_configsets(cateId,resType,idx);
        for(var i =0;i < cates.length;i++){
        	var cate = $(cates[i]).children();
    		$(cate).each(function(k,n){
    			if($(n)[0].localName.toLowerCase() == "category"){
    				var name = $(n).attr("Name");
    				var id = $(n).attr("ID");
    				var subCategory = $(n).find("Category");
    				cateId = cateId.split(".")[0];
    				//console.log(id,cateId);
    				if(id == cateId){
    					if(subCategory.length > 0){
        					contentHtml = _de.create_sub_form_panel(n,resType,idx,cateId);
    					}else{
        					contentHtml = _de.create_form_panel(n,resType,idx,cateId);
    					}
    					

						var tabsId='#'+resType.toLowerCase()+'_de_tabs';
						var t = $(tabsId).tabs("getTab",name);
						
						$(tabsId).tabs('update', {
							tab: t,
							options: {
								content:contentHtml
							}
						});
						
    					//console.log(contentHtml);
    					return false;
    				}
    			}
    		});
        }
	},
	refresh_resource_configsetsbyid:function(bForce,configIds,callback){

		var configIdArr  = new Array();
		for(var i =0;i < configIds.length;i++){
			var c = configIds[i];
			
			var flagId = c.type+"_"+c.idx;
			if(!bForce && _de.resConfigSets[flagId][c.id] != undefined){
				continue;
			}
			//var configId = $(configIds[i]).text();
			configIdArr.push(c);
		}
		var configXML = _p.get_res_configs(_de.connectId,_de.pu.puid,configIds);
		//console.log(configXML)
		
		var res = $(configXML).find("Res");
		for(var i = 0;i < res.length;i++){
			var r = $(res[i]);
			var configId = r.attr("OptID");
			var flagId = r.attr("Type")+"_"+r.attr("Idx");
			_de.resConfigSets[flagId][configId] =res[i];
		}
		if(typeof callback == "function"){
			callback();
		}
	},
	
	open_resource_rename_dlg:function(resType){

		var name = '';
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
		
		if(resType.toLowerCase() != "st"){
			for(var i = 0;i < _de.pu.childResource.length;i++){
				if(_de.pu.childResource[i].type == resType && _de.pu.childResource[i].idx == idx){
					name = _de.pu.childResource[i].name;
					break;
				}
			}
		}else{
			name = _de.pu.name;
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
			html += '</div>';	
			$(html).appendTo($('#'+resType.toLowerCase()+'_de_region_north'));

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
						_de.set_resource_name(resType)
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
	set_resource_name:function(resType,idx){
		var val = {};
		if(resType.toLowerCase() == "st"){
			val = {name:$('#'+resType.toLowerCase()+'_new_resname').textbox('getValue'),description:_de.pu.description,enable:'1',usable:'1'};
		}else{
			for(var i = 0;i < _de.pu.childResource.length;i++){
				if(_de.pu.childResource[i].type == resType && _de.pu.childResource[i].idx== idx){
					val = {name:$('#'+resType.toLowerCase()+'_new_resname').textbox('getValue'),description:_de.pu.childResource[i].description,enable:_de.pu.childResource[i].enable,usable:_de.pu.childResource[i].usable};
					break;
				}
			}
		}

		var rv = _p.set_res_name(_de.connectId,_de.pu.puid,resType,$('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue"),val.name);

		if(rv.rv == "0" ){
			_de.refresh_resource_desc(resType,idx);
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
	open_resource_setdesc_dlg:function(resType){
		var desc = '';
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");		
		if(resType.toLowerCase() != "st"){
			for(var i = 0;i < _de.pu.childResource.length;i++){
				if(_de.pu.childResource[i].type == resType && _de.pu.childResource[i].idx == idx){
					desc = _de.pu.childResource[i].description;
					break;
				}
			}
		}else{
			desc = _de.pu.description;
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
			
			$(html).appendTo($('#'+resType.toLowerCase()+'_de_region_north'));

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
						_de.set_resource_description(resType);
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
	set_resource_description:function(resType){
		var val = {};
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");

		if(resType.toLowerCase() == "st"){
			var description = $('#'+resType.toLowerCase()+'_new_setdesc').textbox("getValue");
			val = {name:_de.pu.name,description:description,enable:'1',usable:'1'};
		}else{
			for(var i = 0;i < _de.pu.childResource.length;i++){
				var pur =_de.pu.childResource[i]; 
				if(pur.type == resType && pur.idx== idx){
					var description = $('#'+resType.toLowerCase()+'_new_setdesc').textbox("getValue");
					val = {name:pur.name,description:description,enable:pur.enable,usable:pur.usable};
					break;
				}
			}
		}
		//console.log(_de.pu.puid,resType,idx,val.description)
		rv = _p.set_res_description(_de.connectId,_de.pu.puid,resType,idx,val.description);
		if(rv.rv == 0){
			_de.refresh_resource_desc(resType,idx);
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
	resource_disabled:function(resType,status){
		var val = {};
		var idx = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue");
		if(resType.toLowerCase() == "st"){
			return;
		}else{
			for(var i = 0;i < _de.pu.childResource.length;i++){
				var pur =_de.pu.childResource[i]; 
				if(pur.type == resType && pur.idx== idx){
					var status = $("#"+resType.toLowerCase()+"_disabled_chk").prop("checked");
					val = {name:pur.name,description:pur.description,enable:(status==true ? "0" : "1"),usable:pur.usable};
					break;
				}
			}
		}
		rv = _p.set_res_enable(_de.connectId,_de.pu.puid,resType,idx,val.enable);
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
	
	
	
	// 资源通道切换
	resource_onchange:function(resType,rec){
		_de.init_region_center(resType,rec.value);
		for(var i = 0;i < _de.pu.childResource.length;i++){
			if(_de.pu.childResource[i].type == resType && rec.value == _de.pu.childResource[i].idx){
				var r = _de.pu.childResource[i];
				$("#"+resType.toLowerCase()+"_idx_lbl").html(r.idx);
				//$("#"+resType.toLowerCase()+"_desc").textbox("setValue",r.description);
				$("#"+resType.toLowerCase()+"_disabled_chk").prop("checked",(r.enable ==1 ? false : true));
				$("#"+resType.toLowerCase()+"_usable_span").html((r.usable ? "YES" : "NO"));
				
				break;
			}
		}
	},
	get_resource_value:function(resType,idx){

		for(var i = 0;i < _de.pu.childResource.length;i++){
			//console.log(_de.pu.childResource[i].type ,resType , rec.value ,_de.pu.childResource[i].idx)
			if(_de.pu.childResource[i].type == resType && idx == _de.pu.childResource[i].idx){
				var r = _de.pu.childResource[i];
				$("#"+resType.toLowerCase()+"_idx_lbl").html(r.idx);
				//$("#"+resType.toLowerCase()+"_desc").textbox("setValue",r.description);
				if($("#"+resType.toLowerCase()+"_disabled_chk").length > 0) $("#"+resType.toLowerCase()+"_disabled_chk").prop("checked",(r.enable ==1 ? false : true));
				if($("#"+resType.toLowerCase()+"_usable_span").length > 0)$("#"+resType.toLowerCase()+"_usable_span").html((r.usable ? "YES" : "NO"));
				break;
			}
		}
	},
	
	
	// 提交资源配置
	set_config:function(cateId,resType,idx){	
		var formId = resType+'_'+cateId.replace(".","_")
		var values = new Array();
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
	        					attrs:[{name:'Value',val:($(inputs[i]).prop("checked")? 1:0)}]
	        				});
	        			}else if($(inputs[i]).attr("type") == "password"){
	        				var v = "";
	        				var configId = $(inputs[i]).attr("configid");
	        				if($("#"+configId+"_psw_chk") && $("#"+configId+"_psw_chk").prop("checked") == true){
	        					v = $("#"+configId+"").val();
	        				}else{
	        					v = $("#"+configId+"_psw").val();
	        				}
	        				values.push({
	        					id:configId,
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
	        					id:inputs[i].id,
	    		        		params:[{name:'Everyday',childXML:everydayXML,attrs:[]},{name:'Weekly',childXML:weeklyXML,attrs:[]}],
	        					attrs:[{name:'Mode',val:mode}]
	        					//val:$("#"+inputs[i].id).textbox("getValue")
	        				});
	        				
	        				
	        			}else{
	        				values.push({
	        					id:inputs[i].id,
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
					id:selects[i].id,
					attrs:[{name:'Value',val:$("#"+selects[i].id).combobox("getValue")}]
				});
        	}
        }
        rv = _p.set_res_configs(_de.connectId,_de.pu.puid,resType,idx,values);
       
		if(rv && rv.M && rv.M.C){
			if(_p.isArray(rv.M.C.Res)){
				var msg = "";
				for(var i = 0;i < rv.M.C.Res.length;i++){
					var res = rv.M.C.Res[i];
					if(res.Error == "0"){
						
					}else{
						msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
					}
		
					if(res.Error == "0" && res.OptID == "F_DP_SplitMode" ){
						
						_de.init_tabs();	
						var node = $('#de_region_west').tree('find',idx);
						if(node && node.target){
							$('#de_region_west').tree("select", node.target)	
						}
						
						//更新窗口信息
						/*
						for(var j = 0;j < values.length;j++){
							var munber = 0;
		            		if(values[j].id == "F_DP_SplitMode"){
		            			var mode =  values[j].attrs[0].val;
		            			if(mode == "1x1"){
		                			munber = 1;
		                		}
		                		else if(mode == "2x2"){
		                			munber = 4;
		                		}
		                		else if(mode == "3x3"){
		                			munber = 9;
		                		}
		                		else if(mode == "3x4"){
		                			munber = 12;
		                		}
		                		else if(mode == "4x4"){
		                			munber = 16;
		                		}
		                		else if(mode == "4x5"){
		                			munber = 20;
		                		}
		                		else if(mode == "4x6"){
		                			munber = 24;
		                		}
		                		else{
		                			munber = 1;
		                		}
		            		}
		            	}
						
						for(var k = 0;k < _de.OVDatas.length;k++){
							var data = _de.OVDatas[k];
							var ovlength = data.ovlength;
							var maxnumber = data.maxnumber;		
						}
		            	//console.log(resType,idx)		
            			var newChildren = new Array();
                		for(var m = 1;m <= munber;m++){
                			var data = {"text":"显示窗口 "+m+"","iconCls":"icon_ov","type":"ov","idx":""+idx+""};
                			newChildren.push(data)
                		}
                		
                		
            			var node = $('#de_region_west').tree('find',0);
						if(node && node.target){
							$('#de_region_west').tree("select", node.target)	
						}
							
                	    var node = $('#de_region_west').tree('getSelected');  
                	  
            			var children = $('#de_region_west').tree('getChildren',node.target);
            			for(var k = 0;k < children.length;k++){
            				$('#de_region_west').tree('remove', children[k].target);
            			}
            			$('#de_region_west').tree('append', {
            				parent: node.target,
            				data: newChildren
            			});	
		            	*/
					}
				}
				if(msg == "") msg = _lp.frame.configsets.notes.note;
				
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
		            if(rv.M.C.Res.OptID == "F_DP_SplitMode"){
		            	for(var j = 0;j < values.length;j++){
		            		//console.log(values[j])
		            	}
		            }
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
	set_config_toallchannel:function(cateId,resType){
		var formId = cateId.replace(".","_")
		var values = new Array();
		var rows = $('#'+resType.toLowerCase()+'_channel_cmb').combobox("getData");		
        if ($('#'+formId).form("validate") == true) {
        	var inputs =$('#'+formId+' input[configid]');	        	
        	for(var i =0;i < inputs.length;i++){
        		if($(inputs[i]).attr("configid") == "F_ST_OEMData"){
	        		if($(inputs[i]).attr("setable") == 1){
	        			for(var k = 0;k < rows.length;k++){
	        				values.push({
	        					type:resType,
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
		        			if($(inputs[i]).attr("type") == "checkbox"){
		        				values.push({
		        					type:resType,
		        					idx:rows[k].value,
		        					id:inputs[i].id,
		        					attrs:[{name:'Value',val:($(inputs[i]).prop("checked")? 1:0)}]
		        				});
		        			}else{
		        				values.push({
		        					type:resType,
		        					idx:rows[k].value,
		        					id:inputs[i].id,
		        					attrs:[{name:'Value',val:$("#"+inputs[i].id).textbox("getValue")}]
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
    					idx:rows[k].value,
						id:selects[i].id,
						attrs:[{name:'Value',val:$("#"+selects[i].id).combobox("getValue")}]
					});
        		}   
    			
        	}
        }
        //console.log(values);
        rv = _p.set_res_configsex(_de.connectId,_de.pu.puid,resType,$('#'+resType.toLowerCase()+'_channel_cmb').combobox("getValue"),values);

		if(rv && rv.M && rv.M.C){
			if(_p.isArray(rv.M.C.Res)){
				var msg = "";
				for(var i = 0;i < rv.M.C.Res.length;i++){
					var res = rv.M.C.Res[i];
					if(res.Error == "0"){
						
					}else{
						msg += _lp.frame.configsets.notes.noteError+'（id:'+res.OptID+',error:'+res.Error+'）;';
					}
				}
				if(msg == "") msg = _lp.frame.configsets.notes.note;
				
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
	create_form_input_unit:function(configId,attrs,val,params,res){
		
		if(typeof attrs.attr("Type") == "undefined") return;
		switch(attrs.attr("Type").toLowerCase()){
			case "string":
				return _de.create_form_textbox(configId,attrs,val);
			break;
			case "hashpsw":
				return _de.create_form_hashpsw(configId,attrs,val);
			break;
			case "psw":
				return _de.create_form_psw(configId,attrs,val);
			break;
			case "int":
				return _de.create_form_int(configId,attrs,val);
			break;
			case "bool":
				return _de.create_form_bool(configId,attrs,val);
			break;
			case "enum":
				return _de.create_form_enum(configId,attrs,val);
			break;
			case "utc":
				return _de.create_form_utc(configId,attrs,val);
			break;
			case "clock":
				return _de.create_form_clock(configId,attrs,val);
			break;
			case "list":
				return _de.create_form_list(configId,attrs,val,res);
			break;
			case "schedule":
				return _de.create_form_schedule(configId,attrs,val,params);
			break;
				default:
					break;
		}
		return "";
	},
	
	// 文本输入框
	create_form_textbox:function(configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log('<td>'+attrs.attr("Name"+_de.language)+':</td>')
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
		
		if(attrs.attr('Setable')== 0){
			dataOptions += ",editable:false";
		}
		
		if(typeof val == "undefined") val = "";
		
		html += '<td><input id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-textbox" type="text" value="'+val+'"  data-options="'+dataOptions+'"></input></td>';
		html += '</tr>';
		return html;
	},
	
	// 整形数字
	create_form_int:function(configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log('<td>'+attrs.attr("Name"+_de.language)+':</td>')
		var dataOptions='',validType = '',split = '',vLen='',min=0,max=0;
		
		// 看是否最小值
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
		
		// 如果是只读的，就用textbox
		if(attrs.attr('Setable')== 0){
			html += '<td><input id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'"  class="easyui-textbox" type="text" value="'+val+'" data-options="'+dataOptions+'"></input></td>';
			
		}else{
			html += '<td><input id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'"  class="easyui-numberspinner" type="text" value="'+val+'" data-options="'+dataOptions+'"></input></td>';
		}
		html += '</tr>';
		return html;
	},
	
	// bool型
	create_form_bool:function(configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
	
		if(typeof val == "undefined") val = "";
		
		//<input id="ck-rtl" type="checkbox" onclick="open2()">
		html += '<td><input id="'+configId+'" configid="'+configId+'" '+(attrs.attr('Setable')== 0 ? 'disabled' : '')+' setable="'+attrs.attr('Setable')+'" '+(val == 1 ? "checked":"")+' type="checkbox" ></input></td>';
		html += '</tr>';
		return html;
		
	},
	
	// combobox
	create_form_enum:function(configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log(attrs)
		var dataOptions='',validType = '',split = '',vLen='',min=0,max=0;
		
		// 看是否最小值
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
		
		dataOptions += ",panelHeight:'auto'";
		
		html += '<td>';
		html += '<select  id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-combobox" data-options="panelHeight:\'auto\'"  style="width:173px;height:auto;">';
		$(attrs).find("it").each(function(k,n){	
			html += '<option value="'+$(n).text()+'" '+(val == $(n).text() ? "selected" : "")+'>'+$(n).text()+'</option>';
		});
		html += '</select>';

		html += '</td></tr>';
		return html;
	},

	// 密码框
	create_form_psw:function(configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log('<td>'+attrs.attr("Name"+_de.language)+':</td>')
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
		html += '<td><input id="'+configId+'_psw" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" type="password" value="'+val+'" class="easyui-validatebox textbox" '+disabled+' data-options="'+dataOptions+'" style="height:21px;" ></input><input id="'+configId+'" type="text" class="easyui-validatebox textbox" '+disabled+' data-options="'+dataOptions+'" style="height:21px;display:none;"></input>&nbsp;&nbsp;<input type="checkbox" style="vertical-align:middle" id="'+configId+'_psw_chk" onclick="_de.psw_chk_onclick(\''+configId+'\')" ></input><label for="'+configId+'_psw_chk">显示密码<label></td>';
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
	// 创建hash密码元素
	create_form_hashpsw:function(configId,attrs,val){

		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log('<td>'+attrs.attr("Name"+_de.language)+':</td>')
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
		html += '<td><input id="'+configId+'_psw" type="password" class="easyui-validatebox textbox" '+disabled+' data-options="'+dataOptions+'" style="height:21px;" ></input></td>';
		html += '</tr>';
		return html;
	},
	// utc时间元素
	create_form_utc:function(configId,attrs,val){
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
		html += '<td><input  id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'"  class="easyui-datetimebox" data-options="required:true,showSeconds:true" value=""></td>';
		html += '</tr>';
		//console.log(html)
		return html;
	},
	
	// 时钟
	create_form_clock:function(configId,attrs,val){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log('<td>'+attrs.attr("Name"+_de.language)+':</td>')
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
		html += '<td><input  id="'+configId+'" configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-timespinner" data-options="required:true,showSeconds:true" value=""></td>';
		html += '</tr>';
		return html;
	},
	// list 元素
	create_form_list:function(configId,attrs,val,res){
		html = "";
		html += '<tr>';
		html += '<td>'+attrs.attr("Name")+':</td>';
		//console.log('<td>'+attrs.attr("Name"+_de.language)+':</td>')
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
		
		html += '<ul  id="'+configId+'"  configid="'+configId+'" setable="'+attrs.attr('Setable')+'" class="easyui-tree ovtree" style="background:#fff;border:1px solid #000;color:#000;width:200px;" data-options="lines:false" >';
			$(res).find("it").each(function(k,n){
				html += '<li>'+$(n).text()+'</li>';
			});
        html += '</ul>';
	
		html += '</td>';
		html += '</tr>';
		return html;
	}		
}

