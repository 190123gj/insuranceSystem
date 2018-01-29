var _pl = myplayer = {
	connectId:null,
	containerKeys:new Array(),
	curActiveWndId:'',
	curTourId:1,	
	inited:false,
	get_content:function(){
		var html = '';
		html += '<div id="iv_layout" class="easyui-layout" tabPosition="left" fit="true" style="width:auto;height:auto">';
		html += '<div id="iv_region_north" data-options="region:\'west\',border:true" style="width:232px;padding:2px;" ></div>';
		html += '<div id="iv_region_center" data-options="region:\'center\',border:true"  style="width:auto;" ></div>';
		html += '</div>';
		return html;
	},
	init:function(connectId){
		_pl.connectId = connectId;
		if(_pl.inited){
			return;
		}
		_pl.inited = true;
	},
	pu_change:function(pu){
		_pl.pu = pu;
		_pl.init_frame();
	},
	init_frame:function(){
		var html = '';
		// 左边控制区域元素
		html += '<div id="iv_accordion" class="easyui-accordion" data-options="multiple:true" >';
		html += '    <div title="'+_lp.frame.player.cameraTitle+'" data-options="selected:true,tools:[{iconCls:\'icon-reload\',handler:function(){_pl.refresh_cameralist();}}]" style="overflow:auto;padding:1px;">';

	    	html += '<div id="camera_list_tb" style="padding:0px;height:auto">'; 
		    	html += '<table><tr><td width="160" style="text-align:left;text-indent:9px;" >'+_lp.frame.player.cameralist.streamType+':<select id="iv_streatype_cmb" data-options="editable:false" style="background:#000;" class="easyui-combobox" panelHeight="auto" style="width:65px;background:#000;">'; 
		    	html += '</select></td><td width="68" align=right ><a id="btn_playall" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-playall\'" title="'+_lp.frame.player.cameralist.btns.playall+'" ></a>&nbsp;<a id="btn_stopall" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-stopall\'" title="'+_lp.frame.player.cameralist.btns.stopall+'" ></a></td></tr></table>'; 
	    	html += '</div>';
			html += '<table id="camera_list" class="easyui-datagrid" cellpadding="0" cellspacing="0"  style="width:225px;" data-options="showHeader:false,toolbar:\'#camera_list_tb\'" >';
			html += '    <thead>';
			html += '        <tr>';
			html += '            <th data-options="field:\'blank\',width:1,align:\'left\',styler:function(){return {style:\'border-right:0\'};}"></th>';
			html += '            <th data-options="field:\'name\',width:160,align:\'left\'">'+_lp.frame.player.cameralist.col.name+'</th>';
			html += '            <th data-options="field:\'op\',width:60,align:\'center\',formatter:_pl.camera_formatter" >'+_lp.frame.player.cameralist.col.op+'</th>';
			html += '        </tr>';
			html += '    </thead>';
			html += '</table>';
	    
		html += '</div>';
		
		html += '<div title="'+_lp.frame.player.ptzTitle+'"  data-options="selected:true" style="padding:1px 0px 1px 0px">';
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_btn"><a id="iv_turn_leftup" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_leftup" onmousedown="_pl.ptz_control(\'turnup\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_turn_up" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_up" onmousedown="_pl.ptz_control(\'turnup\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_turn_rightup" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_up" onmousedown="_pl.ptz_control(\'turnup\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_zoom_in"  href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon_ptz_add" onmousedown="_pl.ptz_control(\'zoomin\');" onmouseup="_pl.ptz_control(\'stopzoom\');" ></a></div>';
		html += '<div class="ptz_btn"><a id="iv_zoom" class="easyui-linkbutton" iconCls="icon_ptz_zoom"  ></a></div>';
		html += '<div class="ptz_btn1"><a id="iv_zoom_out"  href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon_ptz_sub" onmousedown="_pl.ptz_control(\'zoomout\');" onmouseup="_pl.ptz_control(\'stopzoom\');" ></a></div>';
		html += '</div>';
		
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_btn"><a id="iv_turn_left" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_left" onmousedown="_pl.ptz_control(\'turnleft\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_turn_center" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_center" onmousedown="_pl.ptz_control(\'turncenter\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_turn_right" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_right" onmousedown="_pl.ptz_control(\'turnright\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_aperture_a" class="easyui-linkbutton" iconCls="icon_ptz_add"  onmousedown="_pl.ptz_control(\'aperturea\');" onmouseup="_pl.ptz_control(\'stopaperture\');"  ></a></div>';
		html += '<div class="ptz_btn"><a id="iv_aperture" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon_ptz_aperture"  ></a></div>';
		html += '<div class="ptz_btn1"><a id="iv_aperture_m" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon_ptz_sub" onmousedown="_pl.ptz_control(\'aperturem\');" onmouseup="_pl.ptz_control(\'stopaperture\');"></a></div>';
		html += '</div>';
		
		
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_btn"><a id="iv_turn_leftdown" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_leftdown" onmousedown="_pl.ptz_control(\'turndown\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_turn_down" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_down" onmousedown="_pl.ptz_control(\'turndown\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_turn_rightdown" href="javascript:void(0);"  class="easyui-linkbutton" iconCls="icon_ptz_rightdown" onmousedown="_pl.ptz_control(\'turndown\');" onmouseup="_pl.ptz_control(\'stopturn\');"></a></div>';
		html += '<div class="ptz_btn"><a id="iv_focus_far" class="easyui-linkbutton" iconCls="icon_ptz_add"  onmousedown="_pl.ptz_control(\'focusfar\');" onmouseup="_pl.ptz_control(\'stopfocus\');"  ></a></div>';
		html += '<div class="ptz_btn"><a id="iv_focus" class="easyui-linkbutton" iconCls="icon_ptz_focus"  ></a></div>';
		html += '<div class="ptz_btn1"><a id="iv_focus_near" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon_ptz_sub" onmousedown="_pl.ptz_control(\'focusnear\');" onmouseup="_pl.ptz_control(\'stopfocus\');"></a></div>';
		html += '</div>';
		
		// 云台速度
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_btn"><a id="iv_ptzspeed_sub" class="easyui-linkbutton" iconCls="icon_ptz_sub"  ></a></div>';
		html += '<div class="ptz_speedbar"><input id="iv_ptzspeed" class="easyui-slider" data-options="showTip:false" ></div>';
		html += '<div class="ptz_btn1"><a id="iv_ptzspeed_add" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon_ptz_add" ></a></div>';
		html += '</div>';
		
		// 预置位
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_combo">'+_lp.frame.player.prepostion.lbl+':　<input id="pre_position_combobox" class="easyui-combobox" name="preposition" ></div>';
		html += '<div class="ptz_btn2"><a id="pre_position_btn_go" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-go\'" onclick="_pl.ptz_control(\'movetopresetpos\');" title="'+_lp.frame.player.prepostion.go+'" ></a></div>';
		html += '<div class="ptz_btn2"><a id="pre_position_btn_set" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-setting\'" onclick="_pl.ptz_control(\'setpresetpos\');" title="'+_lp.frame.player.prepostion.set+'"></a></div>';
		html += '<div class="ptz_btn2"><a id="pre_position_btn_rename" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-rename\'" onclick="" title="'+_lp.frame.player.prepostion.rename+'"></a></div>';
		html += '</div>';
		
		// 辅助设备
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_combo">'+_lp.frame.player.adl.lbl+':<input id="adl_combobox" class="easyui-combobox" name="adl" ></div>';
		html += '<div class="ptz_btn2"><a id="adl_btn_open" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-open\'" onclick="_pl.ptz_control(\'startsecondarydev\');" title="'+_lp.frame.player.adl.open+'"></a></div>';
		html += '<div class="ptz_btn2"><a id="adl_btn_close" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-close\'" onclick="_pl.ptz_control(\'stopsecondarydev\');" title="'+_lp.frame.player.adl.close+'"></a></div>';
		html += '<div class="ptz_btn2"><a id="adl_btn_rename" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-rename\'" title="'+_lp.frame.player.adl.rename+'" "></a></div>';
		html += '</div>';
		
		// 巡航路线
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_combo">'+_lp.frame.player.tour.lbl+':<input id="tour_combobox" class="easyui-combobox" name="adl" ></div>';
		html += '<div class="ptz_btn2"><a id="tour_btn_start" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-open\'" onclick="_pl.ptz_control(\'runtour\');" title="'+_lp.frame.player.tour.start+'"></a></div>';
		html += '<div class="ptz_btn2"><a id="tour_btn_stop" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-close\'" onclick="_pl.ptz_control(\'stoptour\');" title="'+_lp.frame.player.tour.stop+'"></a></div>';
		html += '<div class="ptz_btn2"><a id="tour_btn_set" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-rename\'" title="'+_lp.frame.player.tour.set+'" ></a></div>';
		html += '</div>';
		
		// 自动扫描
		html += '<div class="ptz_bar">';
		html += '<div class="ptz_combo">'+_lp.frame.player.autoscan.lbl+':</div>';
		html += '<div class="ptz_btn3"><a id="tour_btn_start" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-start\'" title="'+_lp.frame.player.tour.start+'"  onclick="_pl.ptz_control(\'runtour\');"></a></div>';
		html += '<div class="ptz_btn3"><a id="autoscan_btn_stop" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-stop\'" onclick="_pl.ptz_control(\'stopautoscan\');" title="'+_lp.frame.player.autoscan.stop+'"></a></div>';
		html += '<div class="ptz_btn3"><a id="autoscan_btn_set_a" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-begin-point\'"  onclick="_pl.ptz_control(\'setbeginpos\');" title="'+_lp.frame.player.autoscan.seta+'" ></a></div>';
		html += '<div class="ptz_btn3"><a id="autoscan_btn_set_b" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-end-point\'"  onclick="_pl.ptz_control(\'setendpos\');" title="'+_lp.frame.player.autoscan.setb+'" ></a></div>';
		html += '</div>';
		
		html += '    </div>';
		html += '    <div title="'+_lp.frame.player.audioOutTitle+'" data-options="tools:[{iconCls:\'icon-reload\',handler:function(){_pl.refresh_audioout();}}]" style="overflow:auto;padding:1px 1px 1px 0px;">';
			html += '<table id="audioout_list" class="easyui-datagrid" style="width:226px;height:auto" data-options="showHeader:false" >';
			html += '    <thead>';
			html += '        <tr>';
			html += '            <th data-options="field:\'blank\',width:1,align:\'left\',styler:function(){return {style:\'border-right:0\'};}"></th>';
			html += '            <th data-options="field:\'name\',width:90,align:\'left\'">'+_lp.frame.player.audioout_list.col.name+'</th>';
			html += '            <th data-options="field:\'status\',width:60,formatter:_pl.format_audioout_status">'+_lp.frame.player.audioout_list.col.status+'</th>';
			html += '            <th data-options="field:\'op\',width:72,align:\'center\',formatter:_pl.format_audioout_op" >'+_lp.frame.player.audioout_list.col.op+'</th>';
			html += '        </tr>';
			html += '    </thead>';
			html += '</table>';
		html += '    </div>';
		
		
		html += '</div>';
		$('#iv_region_north').html(html);
		
		// 渲染各元素控制对象
		$('#iv_accordion').accordion({
			animate:true,border:false,
			width:226
		});
		
		$('#iv_accordion').accordion('select',0);
		$('#iv_accordion').accordion('select',1);
		//$('#iv_accordion').accordion('select',2);
		
//		$('#audioout_list').datagrid({
//			showHeader:false,border:false,
//			data:[],
//			singleSelect:true,
//			onClickRow:function(){
//			},
//			onLoadSuccess:function(data){
//			}
//		});
		
		var cameras = new Array(),audioouts = new Array(),alertins = new Array(),alertouts = new Array();
		/*
		var p = _pl.pu;
		for(var j = 0; j < p.childResource.length;j++){
			var c = p.childResource[j];
			if(c.type.toLowerCase() == "iv"){
				// 取出ptz,ia,oa
				var ptz = -1,ia = -1,oa = -1;
				for(var m = 0; m < p.childResource.length;m++){
					var r = p.childResource[m];
					if(r.type.toLowerCase() == "ptz" && r.idx == c.idx){
						ptz = r.idx;
					}else if(r.type.toLowerCase() == "ia" && r.idx == c.idx){
						ia = r.idx;							
					}else if(r.type.toLowerCase() == "oa" && r.idx == c.idx){
						oa = r.idx;
					}
				}
				cameras.push({pu:p,name:c.name,status:false,idx:c.idx,ptz:ptz,ia:ia,oa:oa});
			}else if(c.type.toLowerCase() == "oa"){
				audioouts.push({pu:p,name:c.name,status:"Free",idx:c.idx,op:0});
			}else if(c.type.toLowerCase() == "idl"){
				alertins.push({pu:p,name:c.name,status:false,idx:c.idx});
			}else if(c.type.toLowerCase() == "odl"){
				alertouts.push({pu:p,name:c.name,status:"",idx:c.idx,connect:"","break":"",pulse:""});
			}
		}*/

		var cameras = new Array(),audioouts = new Array(),alertins = new Array(),alertouts = new Array();
		for(var i = 0;i < _f.pulist.length;i++){
			var p = _f.pulist[i];
			for(var j = 0; j < p.childResource.length;j++){
				var c = p.childResource[j];
				if(c.type.toLowerCase() == "iv"){
					// 取出ptz,ia,oa
					var ptz = -1,ia = -1,oa = -1;
					for(var m = 0; m < p.childResource.length;m++){
						var r = p.childResource[m];
						if(r.type.toLowerCase() == "ptz" && r.idx == c.idx){
							ptz = r.idx;
						}else if(r.type.toLowerCase() == "ia" && r.idx == c.idx){
							ia = r.idx;							
						}else if(r.type.toLowerCase() == "oa" && r.idx == c.idx){
							oa = r.idx;
						}
					}
					cameras.push({pu:p,name:c.name,status:false,idx:c.idx,ptz:ptz,ia:ia,oa:oa});
				}
			}
		}

		$('#camera_list').datagrid({
			showHeader:false,border:false,
			data:cameras,
			singleSelect:true,
			onClickRow:function(){
			},
			onSelect:function(rowIndex,rowData){
		
				// 获取支持流数量
				var camera = $('#camera_list').datagrid('getSelected');
				if(camera && typeof camera.name != "undefined"){
					_pl.get_ptz_params();
					if(camera.pu && camera.pu.online ==1){
					//	_pl.set_ptzpad_status(false);
						
						var cameras = new Array(),audioouts = new Array(),alertins = new Array(),alertouts = new Array();
						
						var p = camera.pu;
						for(var j = 0; j < p.childResource.length;j++){
							var c = p.childResource[j];
							if(c.type.toLowerCase() == "iv"){
								// 取出ptz,ia,oa
								var ptz = -1,ia = -1,oa = -1;
								for(var m = 0; m < p.childResource.length;m++){
									var r = p.childResource[m];
									if(r.type.toLowerCase() == "ptz" && r.idx == c.idx){
										ptz = r.idx;
									}else if(r.type.toLowerCase() == "ia" && r.idx == c.idx){
										ia = r.idx;							
									}else if(r.type.toLowerCase() == "oa" && r.idx == c.idx){
										oa = r.idx;
									}
								}
								cameras.push({pu:p,name:c.name,status:false,idx:c.idx,ptz:ptz,ia:ia,oa:oa});
							}else if(c.type.toLowerCase() == "oa"){
								audioouts.push({pu:p,name:c.name,status:"Free",idx:c.idx,op:0});
							}else if(c.type.toLowerCase() == "idl"){
								alertins.push({pu:p,name:c.name,status:false,idx:c.idx});
							}else if(c.type.toLowerCase() == "odl"){
								alertouts.push({pu:p,name:c.name,status:"",idx:c.idx,connect:"","break":"",pulse:""});
							}
						}		
						//alert(audioouts.length)
						if(audioouts.length <= 0){
							var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.audioOutTitle);
							if(p){
								//var index = $('#iv_accordion').accordion('getPanelIndex', p);
								//alert(index)
								try{
									setTimeout(function(){
										$('#iv_accordion').accordion('remove',_lp.frame.player.audioOutTitle);
									},20);
								}catch(e){
									//console.log(e)
								}
							}
						}else{
							$('#audioout_list').datagrid({
								showHeader:false,border:false,
								data:audioouts,
								singleSelect:true,
								onClickRow:function(){
								},
								onLoadSuccess:function(data){
									// 获取支持流数量
									_pl.refresh_audioout();
								}
							});
									
						}
						
					}else{
						_pl.set_ptzpad_status(false);
					}
					
				}else{
					_pl.set_ptzpad_status(false);
				}
				if(rowData !=  undefined){
					var rv = _p.get_res_config(_pl.connectId,rowData.pu.puid,"IV",rowData.idx,"F_IV_SupportedStreamNum");
					
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
					
					$('#iv_streatype_cmb').combobox({
						width:85,
						valueField:'id',
						textField:'name',
						data:d
					});
				}
			},
			onLoadSuccess:function(){
				//if($('#camera_list').datagrid('getRows').length >0) $('#camera_list').datagrid("selectRow",0);
			}
		});

		if(_cf.type == "embed"){
			//$('#iv_accordion').accordion('remove',_lp.frame.player.alertInTitle);
			//$('#iv_accordion').accordion('remove',_lp.frame.player.alertOutTitle);
		}else{

			$('#btn_playall').hide();
			$('#btn_stopall').hide();
		}
		
		

		$('#audioout_list').datagrid();
		
		$('#iv_streatype_cmb').combobox();

		$('#iv_turn_leftup').linkbutton({disabled:true});
		$('#iv_turn_up').linkbutton();
		$('#iv_turn_rightup').linkbutton({disabled:true});
		$('#iv_turn_leftdown').linkbutton({disabled:true});
		$('#iv_turn_left').linkbutton();
		$('#iv_turn_center').linkbutton({disabled:true});
		$('#iv_turn_right').linkbutton();
		$('#iv_turn_leftdown').linkbutton({disabled:true});
		$('#iv_turn_down').linkbutton();
		$('#iv_turn_rightdown').linkbutton({disabled:true});

		$('#iv_zoom_in').linkbutton({disabled:true});
		$('#iv_zoom').linkbutton({disabled:true});
		$('#iv_zoom_out').linkbutton({disabled:true});

		$('#iv_aperture_a').linkbutton({disabled:true});
		$('#iv_aperture').linkbutton({disabled:true});
		$('#iv_aperture_m').linkbutton({disabled:true});

		$('#iv_focus_far').linkbutton({disabled:true});
		$('#iv_focus').linkbutton({disabled:true});
		$('#iv_focus_near').linkbutton({disabled:true});

		$('#pre_position_btn_go').linkbutton({disabled:true});
		$('#pre_position_btn_set').linkbutton({disabled:true});
		$('#pre_position_btn_rename').linkbutton({
			disabled:true,
			onClick:function(){
				_pl.open_pre_position_panel();
			}
		});
		
		$('#pre_position_combobox').combobox({
			disabled:true,
			valueField:'id',
			textField:'name',
			width:73
		});
		

		$('#adl_btn_open').linkbutton({disabled:true});
		$('#adl_btn_close').linkbutton({disabled:true});
		$('#adl_btn_rename').linkbutton({
			disabled:true,
			onClick:function(){
				_pl.open_adl_panel();
			}
		});
		$('#adl_combobox').combobox({
			disabled:true,
			valueField:'id',
			textField:'name',
			width:73
		});


		$('#tour_btn_start').linkbutton({disabled:true});
		$('#tour_btn_stop').linkbutton({disabled:true});
		$('#tour_btn_set').linkbutton({
			disabled:true,
			onClick:function(){
				_pl.open_tour_panel();
			}
		});
		$('#tour_combobox').combobox({
			disabled:true,
			valueField:'id',
			textField:'name',
			width:73
		});
		
		$('#autoscan_btn_start').linkbutton({disabled:true});
		$('#autoscan_btn_stop').linkbutton({disabled:true});
		$('#autoscan_btn_set_a').linkbutton({disabled:true});
		$('#autoscan_btn_set_b').linkbutton({disabled:true});
		

		$('#iv_ptzspeed_add').linkbutton({
			disabled:true,
			onClick:function(){
				var v = $('#iv_ptzspeed').slider('getValue');
				$('#iv_ptzspeed').slider('setValue',parseInt(v)+1);
			}
		});
		
		$('#iv_ptzspeed_sub').linkbutton({
			disabled:true,
			onClick:function(){
				var v = $('#iv_ptzspeed').slider('getValue');
				$('#iv_ptzspeed').slider('setValue',parseInt(v)-1);
			}
		});
		
		$('#iv_ptzspeed').slider({
			disabled:true,width:135,
			value:50,
			onChange:function(newValue,oldValue){
				//_pl.ptz_control('ptzspeed');
			}
		});

		// 初始化窗口
		html = '';
		html += '<div id="play_window_container" class="easyui-panel" style="border:0px solid red;overflow:hidden">';
		html += '</div>';
		
		$('#iv_region_center').html(html);

		var windownums = cameras.length;
		//alert(windownums)
		if(windownums<=1){
			windownums = 1;
		}else if(windownums<=4){
			windownums = 4;
		}else if(windownums<=9){
			windownums = 9;
		}else if(windownums<=16){
			windownums = 16;
		}else{
			if(_cf.type == "embed"){
				if(windownums<=25){
					windownums = 25;
				}else{
					windownums = 36;
				}	
			}else{
				windownums = 16;
			}
		}
		
		$('#play_window_container').panel({
			title:'',
			closable:false,
			border:true,
			fit:true,padding:0,
			content:_pl.load_windows(windownums),
			onResize:function(w,h){
				setTimeout(function(){
					_pl.set_window_size(windownums);	
				},0);
				
			}
		});
		
		$('#btn_playall').linkbutton({
			onClick:function(){
				var rows = $('#camera_list').datagrid("getRows");
				for(var i = 0;i < rows.length;i++){
					if(!rows[i].status){
						var n = i;
						var _f = function(rowIndex){
							setTimeout(function(){
								_pl.camera_onclick(rowIndex);
							},10);	
						}
						
						_f(n);
					}
					//_pl.camera_onclick(i);
				}
			}
		});
		
		$('#btn_stopall').linkbutton({
			onClick:function(){
				var rows = $('#camera_list').datagrid("getRows");
				for(var i = 0;i < rows.length;i++){
					if(rows[i].status)_pl.stop_video("windowbox"+rows[i].idx);
				}
			}
		})

		
		_pl.init_window_container();
		_pl.active_wnd($("#play_window_container .wnd")[0].id);

		_pl.set_ptzpad_status(false);
		var rows = $('#camera_list').datagrid("getRows");
		if(rows.length == 1)_pl.camera_onclick(0);
		//$('#camera_list').datagrid("selectRow",0);
		if($('#camera_list').datagrid('getRows').length >0) $('#camera_list').datagrid("selectRow",0);
	},
	refresh_cameralist:function(status,puid){
		//alert(status,puid)
		//console.log(status,puid);
		//console.log(_f.pulist);
		if(status == "0"){
			// 设备下线了，判断是否正在播放puid
				var rows = $('#camera_list').datagrid('getRows');    // get current page rows
				for(var i = 0;i <rows.length;i++){
					var row = rows[i];
					//console.log(row)
					if(row.pu.puid == puid){
						//console.log(row.op);
					
						if(row.op &&row.op.search('_pl.stop_video') >0){
							_pl.camera_onclick(i);
						}
						break;
					}
				}
		}
		var cameras = new Array(),audioouts = new Array(),alertins = new Array(),alertouts = new Array();
		for(var i = 0;i < _f.pulist.length;i++){
			var p = _f.pulist[i];
			for(var j = 0; j < p.childResource.length;j++){
				var c = p.childResource[j];
				if(c.type.toLowerCase() == "iv"){
					// 取出ptz,ia,oa
					var ptz = -1,ia = -1,oa = -1;
					for(var m = 0; m < p.childResource.length;m++){
						var r = p.childResource[m];
						if(r.type.toLowerCase() == "ptz" && r.idx == c.idx){
							ptz = r.idx;
						}else if(r.type.toLowerCase() == "ia" && r.idx == c.idx){
							ia = r.idx;							
						}else if(r.type.toLowerCase() == "oa" && r.idx == c.idx){
							oa = r.idx;
						}
					}
					cameras.push({pu:p,name:c.name,status:false,idx:c.idx,ptz:ptz,ia:ia,oa:oa});
				}
			}
		}
		$('#camera_list').datagrid('loadData',cameras);
		
		var rows = $('#camera_list').datagrid("getRows");
		for(var i = 0;i < rows.length;i++){
			if(idx == rows[i].idx && puid == rows[i].pu.puid){
				$('#camera_list').datagrid("selectRow",i);
				return;
			}
		}
	},
	refresh_audioout:function(){
		var rows = $('#audioout_list').datagrid("getRows");
		var configsets = new Array();
		var puid = "";
		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			puid = r.pu.puid;
			configsets.push({type:"OA",idx:r.idx,id:"F_OA_Status"});
		}
		
		var rv = _p.get_res_configs(_pl.connectId,puid,configsets);
		var res = $(rv).find("Res");
		for(var i = 0;i < res.length;i++){
			var r = $(res[i])
			var idx = r.attr("Idx");

			for(var j = 0;j < rows.length;j++){
				var row = rows[j];
				if(row.idx == idx){
					var p = $(r).find("Param");
					if(p.attr("Value") == "TeamTalking"){
						op = 2;
					}else if(p.attr("Value") == "Talking"){
						op = 3;
					}else if(p.attr("Value") == "Calling"){
						op = 1;
					}else{
						op = 0;
					}
					//console.log(op,p)
					$('#audioout_list').datagrid('updateRow',{
						index: j,
						row: {
							status: p.attr("Value"),
							op:op
						}
					});
					break;
				}
			}
				
		}
		try{
			
		//console.log(_pl.curActiveWndId);
		if(_pl.curActiveWndId != "") {
			setTimeout(function(){
				_p.update_wnd_menu(_pl.curActiveWndId);
			},100);
		}
		}catch(e){
			//alert(e.message)
		}
	},
	audioout_status_changed:function(){
		_pl.refresh_audioout();
	},
	format_audioout_status:function(value,row,index){
		if(_lp.frame.player.audioout_list.status_map[value]){
			return _lp.frame.player.audioout_list.status_map[value];
		}
		return value;
	},
	format_audioout_op:function(value,row,index){
		//'<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-closetalk\'" onclick="_pl.stop_talk(\''+row.pu.puid+'\',\''+index+'\',\''+row.idx+'\')" title="'+_lp.frame.player.cameralist.btns.closetalk+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-closetalk"></span></span></a>'
		if(value == 0){
			return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-talk\'" onclick="_pl.start_talk(\''+row.pu.puid+'\',\''+index+'\',\''+row.idx+'\')" title="'+_lp.frame.player.audioout_list.btns.talk+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-talk"></span></span></a>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-call\'" onclick="_pl.start_call(\''+row.pu.puid+'\',\''+index+'\',\''+row.idx+'\')" title="'+_lp.frame.player.audioout_list.btns.call+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-call"></span></span></a>';
		}else if(value == 1){
			return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-stop\'" onclick="_pl.stop_call(\''+row.pu.puid+'\',\''+index+'\',\''+row.idx+'\')" title="'+_lp.frame.player.audioout_list.btns.closecall+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-stop"></span></span></a>';
		}else if(value == 3){
			return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-stop\'" onclick="_pl.stop_talk(\''+row.pu.puid+'\',\''+index+'\',\''+row.idx+'\')" title="'+_lp.frame.player.audioout_list.btns.closetalk+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-stop"></span></span></a>';
		}
		return "";
	},
	start_call:function(puid,rowIndex,oaIdx){
		var rv = _p.start_call(_pl.connectId,puid,oaIdx);
		if(rv.rv == 0){
			$('#audioout_list').datagrid('updateRow',{
				index: rowIndex,
				row: {
					op: 1
				}
			});
		}else{
			_pl.refresh_audioout();
		}
	},
	stop_call:function(puid,rowIndex,oaIdx){
		var rv = _p.stop_call(_pl.connectId,puid,oaIdx);
		
		if(rv.rv == 0){
			$('#audioout_list').datagrid('updateRow',{
				index: rowIndex,
				row: {
					op: "0"
				}
			});
		}else{
			_pl.refresh_audioout();			
		}
	},
	start_talk:function(puid,rowIndex,oaIdx){
		var rv = _p.start_talk(_pl.connectId,puid,oaIdx);		
		if(rv.rv == 0){
			$('#audioout_list').datagrid('updateRow',{
				index: rowIndex,
				row: {
					op: 3
				}
			});				
		}else{
			_pl.refresh_audioout();
		}
	},
	stop_talk:function(puid,rowIndex,oaIdx){
		var rv = _p.stop_talk(_pl.connectId,puid,oaIdx);
		if(rv.rv == 0){
			$('#audioout_list').datagrid('updateRow',{
				index: rowIndex,
				row: {
					op: "0"
				}
			});	
		}else{
			_pl.refresh_audioout();
		}	
	},
	open_alertin_accordion:function(){

		var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.alertInTitle);
		if(p){
			$('#iv_accordion').accordion('remove',_lp.frame.player.alertInTitle);
		}

		var html = '';
		html += '    <div title="'+_lp.frame.player.alertInTitle+'" data-options="tools:[{iconCls:\'icon-reload\',handler:function(){}}]" style="overflow:auto;padding:3px;">';
			html += '<table id="alertin_list" class="easyui-datagrid" style="width:223px;height:auto" data-options="showHeader:false" >';
			html += '    <thead>';
			html += '        <tr>';
			html += '            <th data-options="field:\'name\',width:99,align:\'left\' ">'+_lp.frame.player.alertin_list.col.name+'</th>';
			html += '            <th data-options="field:\'workmode\',hidden:true"></th>';
			html += '            <th data-options="field:\'alertinStatus\',hidden:true"></th>';
			html += '            <th data-options="field:\'count\',hidden:true"></th>';
			html += '            <th data-options="field:\'levelValid\',hidden:true"></th>';
			html += '            <th data-options="field:\'status\',width:125,formatter:_pl.format_alertin_status">'+_lp.frame.player.alertin_list.col.status+'</th>';
			//html += '            <th data-options="field:\'op\',width:70,align:\'center\'" >'+_lp.frame.player.alertin_list.col.op+'</th>';
			
			html += '        </tr>';
			html += '    </thead>';
			html += '</table>';
		html += '    </div>';
		$('#iv_accordion').accordion('add',{
			title:_lp.frame.player.alertInTitle,
			collapsed:true,
			collapsible:true,
			content:html
		});
		
		return;
	},
	refresh_alertin:function(){

		var rows = $('#alertin_list').datagrid("getRows");
		var configsets = new Array();
		var puid = "";
		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			puid = r.pu.puid;
			configsets.push({type:"IDL",idx:r.idx,id:"F_IDL_WorkMode"});
			configsets.push({type:"IDL",idx:r.idx,id:"F_IDL_AlertInStatus"});
			configsets.push({type:"IDL",idx:r.idx,id:"F_IDL_Count"});
			configsets.push({type:"IDL",idx:r.idx,id:"F_IDL_LevelValid"});
		}

		var rv = _p.get_res_configs(_pl.connectId,puid,configsets);
		var res = $(rv).find("Res");
		var data = new Array();
		
		for(var i = 0;i < res.length;i++){
			var r = $(res[i]);
			var idx = r.attr("Idx");
			if(data[idx]){
			}else{
				data[idx] = {"workmode":"","alertinStatus":"","count":0,"levelValid":0};
			}

			var p = $(r).find("Param");
			if(r.attr('OptID') == "F_IDL_WorkMode"){
				data[idx].workmode = p.attr("Value");
				
			}else if(r.attr('OptID') == "F_IDL_AlertInStatus"){
				data[idx].alertinStatus = p.attr("Value");
				
			}else if(r.attr('OptID') == "F_IDL_Count"){
				data[idx].count = p.attr("Value");
				
			}else if(r.attr('OptID') == "F_IDL_LevelValid"){
				data[idx].levelValid = p.attr("Value");
				
			}
		}

		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			if(data[r.idx]){
				$('#alertin_list').datagrid('updateRow',{
					index: i,
					row: {
						"workmode":data[r.idx].workmode,
						"alertinStatus":data[r.idx].alertinStatus,
						"count":data[r.idx].count,
						"levelValid":data[r.idx].levelValid
					}
				});
			}
		}
	},
	format_alertin_status:function(value,row,index){
		if(row.workmode == "AlertIn"){
			return (row.alertinStatus == 1 ? _lp.frame.player.alertin_list.status_map.alert : _lp.frame.player.alertin_list.status_map.noalert);
		}else if(row.workmode == "Counter"){
			return row.count;
		} else if(row.workmode == "StatusCap"){
			return (row.levelValid == 1 ? _lp.frame.player.alertin_list.status_map.valid : _lp.frame.player.alertin_list.status_map.invalid);
		} 
	},

	open_alertout_accordion:function(){

		var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.alertOutTitle);
		if(p){
			$('#iv_accordion').accordion('remove',_lp.frame.player.alertOutTitle);
		}

		var html = '';
		html += '    <div title="'+_lp.frame.player.alertOutTitle+'" data-options="tools:[{iconCls:\'icon-reload\',handler:function(){_pl.refresh_alertout();}}]" style="overflow:auto;padding:3px;">';
		html += '<table id="alertout_list" class="easyui-datagrid" style="width:223px;height:auto" data-options="showHeader:false" >';
		html += '    <thead>';
		html += '        <tr>';
		html += '            <th data-options="field:\'name\',width:100"></th>';
		html += '            <th data-options="field:\'connect\',hidden:true"></th>';
		html += '            <th data-options="field:\'break\',hidden:true"></th>';
		html += '            <th data-options="field:\'pulse\',hidden:true"></th>';
		html += '            <th data-options="field:\'status\',width:125,formatter:_pl.format_alertout_op"></th>';
		html += '        </tr>';
		html += '    </thead>';
		html += '</table>';
	html += '    </div>';
		$('#iv_accordion').accordion('add',{
			title:_lp.frame.player.alertOutTitle,
			collapsed:true,
			collapsible:true,
			content:html
		});
		return;
	},
	
	refresh_alertout:function(){
		var rows = $('#alertout_list').datagrid("getRows");
		var configsets = new Array();
		var puid = "";
		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			puid = r.pu.puid;
			configsets.push({type:"ODL",idx:r.idx,id:"F_ODL_ConnectStatus"});
			configsets.push({type:"ODL",idx:r.idx,id:"F_ODL_AliasConnect"});
			configsets.push({type:"ODL",idx:r.idx,id:"F_ODL_AliasBreak"});
			configsets.push({type:"ODL",idx:r.idx,id:"F_ODL_AliasPulse"});
		}
		var rv = _p.get_res_configs(_pl.connectId,puid,configsets);
		
		var res = $(rv).find("Res");
		var data = new Array();
		
		for(var i = 0;i < res.length;i++){
			var r = $(res[i]);
			var idx = r.attr("Idx");
			if(data[idx]){
			}else{
				data[idx] = {"status":"","connect":"","break":"","pulse":""};
			}

			var p = $(r).find("Param");
			if(r.attr('OptID') == "F_ODL_ConnectStatus"){
				data[idx].status = p.attr("Value");
				
			}else if(r.attr('OptID') == "F_ODL_AliasConnect"){
				data[idx].connect = (p.attr("Value") != "" ? p.attr("Value") : _lp.frame.player.alertout_list.col.connect);
				
			}else if(r.attr('OptID') == "F_ODL_AliasBreak"){
				data[idx]["break"] = (p.attr("Value") != "" ? p.attr("Value") : _lp.frame.player.alertout_list.col["break"]);
				
			}else if(r.attr('OptID') == "F_ODL_AliasPulse"){
				data[idx].pulse = (p.attr("Value") != "" ? p.attr("Value") : _lp.frame.player.alertout_list.col.pulse);
				
			}
		}

		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			if(data[r.idx]){
				$('#alertout_list').datagrid('updateRow',{
					index: i,
					row: {
						"status":data[r.idx].status,
						"connect":data[r.idx].connect,
						"break":data[r.idx]["break"],
						"pulse":data[r.idx].pulse
					}
				});
			}
		}
		
	},
	format_alertout_op:function(value,row,index){
		var btns = "";
		//'<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small"  onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',1)" title="'+row.connect+'" ><span class="l-btn-left">'+row.connect+'</span></a>'
		//l-btn-selected

		btns += '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small '+(row.status == "Connect" ? " l-btn-selected" : "")+' "  onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',1)" title="'+row.connect+'" ><span class="l-btn-left">'+row.connect+'</span></a>';
		
		btns += '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small '+(row.status == "Break" ? " l-btn-selected" : "")+' "  onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',0)" title="'+row["break"]+'" ><span class="l-btn-left">'+row["break"]+'</span></a>';
		
		
		btns += '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small '+(row.status == "Flash" ? " l-btn-selected" : "")+' "  onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',2)" title="'+row.pulse+'" ><span class="l-btn-left">'+row.pulse+'</span></a>';
		
		return btns;
		var cls = "button def small";
		btns += '<a href="javascript:void(0);" class="'+(row.status == "Connect" ? "black_active small" : cls)+'" onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',1)" >'+row.connect+'</a>';
		btns += '<a href="javascript:void(0);" class="'+(row.status == "Break" ? "black_active small" : cls)+'" onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',0)" >'+row["break"]+'</a>';
		btns += '<a href="javascript:void(0);" class="'+(row.status == "Flash" ? "black_active small" : cls)+'" onclick="_pl.set_alertout_status(\''+row.pu.puid+'\',\''+row.idx+'\',2)" >'+row.pulse+'</a>';
		return btns;
	},
	set_alertout_status:function(puid,odlIdx,status){

		var rows = $('#alertout_list').datagrid("getRows");
		
		var rv = _p.set_control(_pl.connectId,puid,"ODL",odlIdx,"C_ODL_SetStatus"," Status=\""+status+"\" ");
		_pl.refresh_alertout();	
	},
	camera_formatter:function(v,r,i){
		if(r.pu.online == 0){
			return '离线';
		}
		
		return (r.op == undefined || r.op == "" ? '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-start\'" onclick="_pl.camera_onclick(\''+i+'\')" title="'+_lp.frame.player.cameralist.btns.play+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-start"></span></span></a>' : r.op);
		
		//return (r.op == undefined || r.op == "" ? '<a href="javascript:void(0);" class="button def small" onclick="_pl.camera_onclick(\''+i+'\')">'+_lp.frame.player.cameralist.btns.play+'</a>' : r.op);
	},
	camera_onclick:function(rowIndex){
		var rows = $('#camera_list').datagrid('getRows');    // get current page rows
		var row = rows[rowIndex];  
		if(row){
			
			
			// 先选择一个播放窗口
			if(_cf.type.search(/embed|encode/) > -1 ){
				// 内置网页，以摄像头的idx确定窗口
				_pl.active_wnd("windowbox"+row.idx);
				
			}else{
				// 已经选择窗口了
				if(_pl.curActiveWndId != ""){
					if (P_LY.WindowContainers.get(_pl.curActiveWndId)){
						// 当前选择的窗口存在，看看是否在播放，及播放的对象是谁
						var c = P_LY.WindowContainers.get(_pl.curActiveWndId)

						if(c.window && c.window.status.playvideoing){
		    				var p = c.window.params;
		    				if(p.puid == row.pu.puid && row.pu.idx == p.idx){
		    					// 同一路视频
		    		            $.messager.show({
		    		                title:_lp.frame.player.notes.noteTitle1,
		    		                msg:_lp.frame.player.notes.noteError3,
		    		                timeout:4000,
		    		                showType:'slide'
		    		            });
		    					return;
		    				}else{
		    					// 关闭原有视频
		    					_pl.stop_video(c.window.containerId);
		    				}
						}
					}
				}else{
					// 以选定的窗口播放视频，如果未选择，找一个未使用的窗口
					var flag = false;
					P_LY.WindowContainers.each(function(item){
						var node = item.value;
						//console.log(node)
						if(node.window == null ||!node.window.status.playvideoing){
							_pl.active_wnd(item.key);
							flag = true;
							return true;
						}
					});
				}

    			if(!flag){
    				// 没有找到空闲的窗口了
    				if(_pl.curActiveWndId == ""){
    		            $.messager.show({
    		                title:_lp.frame.player.notes.noteTitle1,
    		                msg:_lp.frame.player.notes.noteError2,
    		                timeout:4000,
    		                showType:'slide'
    		            });
    		            
    					if(_cf.debug) console.log(_lp.frame.player.notes.noteError2);
    					return;
    				}
    			}
			}
			
//			if (P_LY.WindowContainers.get(_pl.curActiveWndId)){
//				// 当前选择的窗口存在，看看是否在播放，及播放的对象是谁
//				var c = P_LY.WindowContainers.get(_pl.curActiveWndId);
//
//				if(c.window && c.window.status.playvideoing){
//    				var p = c.window.params;
//    				if(p.puid == row.pu.puid && row.pu.idx == p.idx){
//    					// 同一路视频
//    		            $.messager.show({
//    		                title:_lp.frame.player.notes.noteTitle1,
//    		                msg:_lp.frame.player.notes.noteError3,
//    		                timeout:4000,
//    		                showType:'slide'
//    		            });
//    					return;
//    				}else{
//    					// 关闭原有视频
//    					if(_cf.debug) console.log(c.window.containerId);
//    					_pl.stop_video(c.window.containerId);
//    				}
//				}
//			}
			
//  		'<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-stop\'" onclick="_pl.stop_video(null,\''+rowIndex+'\')" title="'+_lp.frame.player.cameralist.btns.stop+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon icon-stop">&nbsp;</span></span></a>'
			
			
			if(_pl.play_video(row) == true){
				_pl.set_ptzpad_status(true);
				row.op = '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-stop\'"  onclick="_pl.stop_video(null,\''+rowIndex+'\')" title="'+_lp.frame.player.cameralist.btns.stop+'" ><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-stop"></span><span class="l-btn-icon icon-stop"></span></span></a>';
				//row.op = '<a href="javascript:void(0);" class="button def small"  onclick="_pl.stop_video(null,\''+rowIndex+'\')">'+_lp.frame.player.cameralist.btns.stop+'</a>';				
				$('#camera_list').datagrid('updateRow',{index:rowIndex,row:{name:row.name,status:true,op:row.op}});
			}else{

	            $.messager.show({
	                title:_lp.frame.player.notes.noteTitle1,
	                msg:_lp.frame.player.notes.playfailed,
	                timeout:4000,
	                showType:'slide'
	            });
	            
			};	   	
		}
		return;
	},
	get_ptz_params:function(){
		var cameras = $('#camera_list').datagrid('getSelections');
		if(cameras && cameras.length > 0){
			var c = cameras[0];

			var d = new Array(),tourData = new Array(),presetData = new Array(),adlData = new Array();
			
			for(var i = 1;i <= 255;i++){
				d.push({id:i.toString(),name:i});
			}

			presetData = $.extend(true, [], d);
		//	tourData =  $.extend(true, [], d);
			adlData =  $.extend(true, [], d);
			
			// 获取预置们，辅助设备，巡航路线
			//var configsets = [{type:"PTZ",idx:c.idx,id:"F_PTZ_SecondaryDeviceSets"},{type:"PTZ",idx:c.idx,id:"F_PTZ_PresetPositionSets"},{type:"PTZ",idx:c.idx,id:"F_PTZ_TourSets"},{type:"PTZ",idx:c.idx,id:"F_PTZ_Speed"}];
			var configsets = [{type:"PTZ",idx:c.idx,id:"F_PTZ_SecondaryDeviceSets"},{type:"PTZ",idx:c.idx,id:"F_PTZ_PresetPositionSets"},{type:"PTZ",idx:c.idx,id:"F_PTZ_TourSets"}];


			var rv = _p.get_res_configs(_pl.connectId,c.pu.puid,configsets);
		
		//	console.log(rv)
			var res = $(rv).find("Res");
			for(var i = 0;i < res.length;i++){			
				var configId = $(res[i]).attr("OptID");
				if(configId == "F_PTZ_SecondaryDeviceSets"){
	    			var device  = $(res[i]).find("Device");
	    			for(var j = 0;j < device.length;j++){
	    				for(var k = 0;k < adlData.length;k++){
		    				if(adlData[k].id == $(device[j]).attr("idx")){
		    					adlData[k].name = $(device[j]).attr("name");
		    				}
	    				}
	    			}
				}else if(configId == "F_PTZ_PresetPositionSets"){
	    			var preset = $(res[i]).find("Preset");
	    			for(var j = 0;j < preset.length;j++){
	    				for(var k = 0;k < presetData.length;k++){
		    				if(presetData[k].id == $(preset[j]).attr("idx")){
		    					presetData[k].name = $(preset[j]).attr("name");
		    				}
	    				}
	    			}
				}
				else if(configId == "F_PTZ_TourSets"){
					
	    			var tour  = $(res[i]).find("Tour");
	    		//	console.log(tour)
	    			for(var j = 0;j < tour.length;j++){
	    				
	    				var tourPreset = $(tour[j]).find("Preset");
	    				var preset = new Array();
	    				for(var k = 0;k < tourPreset.length;k++){
	    					preset.push({idx:$(tourPreset[k]).attr("idx"),linger:$(tourPreset[k]).attr("linger")});
	    				}
	    				tourData.push({id:j+1,name:$(tour[j]).attr("name"),preset:preset});
	    			//	tourData.push({id:j+1,name:$(tour[j]).attr("name")});
	    				
	    				/*
	    				for(var k = 0;k < tourData.length;k++){
	    					
		    				if(tourData[k].id == $(tour[j]).attr("idx")){
		    					tourData[k].name = $(tour[j]).attr("name");
		    				}
	    				}*/
	    			}
				}
			}
			
			$('#pre_position_combobox').combobox('loadData',presetData);
			$('#adl_combobox').combobox('loadData',adlData);
			$('#tour_combobox').combobox('loadData',tourData);
			
			$('#pre_position_combobox').combobox('select',1);
			$('#adl_combobox').combobox('select',1);
			var tourData = $('#tour_combobox').combobox('getData');
			if(tourData.length > 0){
				$('#tour_combobox').combobox('select',1);
			}
		
		}
	},
	set_ptzpad_status:function(status){

		$('#iv_turn_up').linkbutton((status ? 'enable' : 'disable'));
		
		$('#iv_turn_left').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_turn_right').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_turn_down').linkbutton((status ? 'enable' : 'disable'));

		$('#iv_zoom_in').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_zoom_out').linkbutton((status ? 'enable' : 'disable'));

		$('#iv_aperture_a').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_aperture_m').linkbutton((status ? 'enable' : 'disable'));

		$('#iv_focus_far').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_focus_near').linkbutton((status ? 'enable' : 'disable'));
		$('#pre_position_btn_go').linkbutton((status ? 'enable' : 'disable'));
		$('#pre_position_btn_set').linkbutton((status ? 'enable' : 'disable'));
		$('#pre_position_btn_rename').linkbutton((status ? 'enable' : 'disable'));
		$('#pre_position_combobox').combobox((status ? 'enable' : 'disable'));

		$('#adl_btn_open').linkbutton((status ? 'enable' : 'disable'));
		$('#adl_btn_close').linkbutton((status ? 'enable' : 'disable'));
		$('#adl_btn_rename').linkbutton((status ? 'enable' : 'disable'));
		$('#adl_combobox').combobox((status ? 'enable' : 'disable'));
		


		$('#tour_btn_start').linkbutton((status ? 'enable' : 'disable'));
		$('#tour_btn_stop').linkbutton((status ? 'enable' : 'disable'));
		$('#tour_btn_set').linkbutton((status ? 'enable' : 'disable'));
		$('#tour_combobox').combobox((status ? 'enable' : 'disable'));


		$('#autoscan_btn_start').linkbutton((status ? 'enable' : 'disable'));
		$('#autoscan_btn_stop').linkbutton((status ? 'enable' : 'disable'));
		$('#autoscan_btn_set_a').linkbutton((status ? 'enable' : 'disable'));
		$('#autoscan_btn_set_b').linkbutton((status ? 'enable' : 'disable'));

		$('#iv_ptzspeed_add').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_ptzspeed_sub').linkbutton((status ? 'enable' : 'disable'));
		$('#iv_ptzspeed').slider((status ? 'enable' : 'disable'));
		
	},
	ptz_control:function(control){

		var cameras = $('#camera_list').datagrid('getSelections');
		if(cameras && cameras.length > 0){
			if(_pl.curActiveWndId != ""){
				if (P_LY.WindowContainers.get(_pl.curActiveWndId)){
					// 当前选择的窗口存在，看看是否在播放，及播放的对象是谁
					var c = P_LY.WindowContainers.get(_pl.curActiveWndId)
					if(c.window && c.window.status.playvideoing){
	    					var c = cameras[0];
							var attrs = new Array();
							if(control == "movetopresetpos" || control == "setpresetpos"){
								attrs.push({name:'PresetPos',val:$('#pre_position_combobox').combobox('getValue')});
							}else if(control == "startsecondarydev" || control == "stopsecondarydev"){
								attrs.push({name:'SecondaryDev',val:$('#adl_combobox').combobox('getValue')});
							}else if(control == "runtour" || control == "stoptour"){
								attrs.push({name:'Idx',val:$('#tour_combobox').combobox('getValue')});
							}else if(control == "ptzspeed"){
								attrs.push({name:'Value',val:$('#iv_ptzspeed').slider('getValue')});
							}
							
							attrs.push({name:'Speed',val:$('#iv_ptzspeed').slider('getValue')});
						
							var rv = _p.control(_pl.connectId, c.pu.puid, c.ptz, control,attrs);
					
	    			}
				}
			}
		}
	},
	//
	open_adl_panel:function(){
		if($('#iv_accordion').accordion('getPanel',_lp.frame.player.prepostion.renameTitle)) $('#iv_accordion').accordion('remove',_lp.frame.player.prepostion.renameTitle);
		if($('#iv_accordion').accordion('getPanel',_lp.frame.player.tour.setTitle)) $('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
		var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.adl.renameTitle);
		if(p){
			$('#iv_accordion').accordion('remove',_lp.frame.player.adl.renameTitle);
		}
		
		var adl_id = $('#adl_combobox').combobox('getValue');
		var html = '';
		html += '<form><input type="hidden" id="adl_id" value="'+adl_id+'" />';
		html += '<table>';
		html += '<tr>';
		html += '<td>'+_lp.frame.player.adl.name.lbl+':</td><td><input class="easyui-textbox" id="adl_name" data-options="required:true,validType:\'length[1,64]\'" /></td>';
		html += '</tr>';
		html += '<tr>';
		html += '<td></td><td><a></a></td>';
		html += '</tr>';
		html += '</table>';
		html += '</form>';
		html += '<div style="text-align:center;padding:5px">';
		html += '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_pl.submit_adl_name()">'+_lp.frame.player.btns.save+'</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		html += '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_pl.close_adl_panel()">'+_lp.frame.player.btns.cancel+'</a>';
		html += '</div>';
		$('#iv_accordion').accordion('add',{
			title:_lp.frame.player.adl.renameTitle,
			collapsed:false,
			collapsible:false,
			content:html
		});
	},
	//
	close_adl_panel:function(){
		$('#iv_accordion').accordion('remove',_lp.frame.player.adl.renameTitle);
	},
	//
	submit_adl_name:function(){
		var cameras = $('#camera_list').datagrid('getSelections');
		if(cameras && cameras.length > 0){
			var c = cameras[0];

			var adl_id = $('#adl_id').val();

			var adl_name = $('#adl_name').textbox('getValue');
				
			var params = new Array();
			var adlData = $('#adl_combobox').combobox('getData');
			adlData[parseInt(adl_id)-1].name = adl_name;
			
			params.push({
	        			name:'Device',
	        			attrs:[{name:'Idx',val:adl_id},{name:'Name',val:adl_name}]
	        		});
			
			for(var i = 0 ; i < adlData.length; i++){
				if(adlData[i].id != adlData[i].name){
					params.push({
	        			name:'Device',
	        			attrs:[{name:'Idx',val:adlData[i].id},{name:'Name',val:adlData[i].name}]
	        		});
				}
			}

	        rv = _p.set_res_configs(
	        	_pl.connectId,
	        	c.pu.puid,
	        	"PTZ",
	        	c.ptz,
	        	[{
	        		id:"F_PTZ_SecondaryDeviceSets",
	        		attrs:[],
	        		params:params
	        	}]
	        );
	       	$('#iv_accordion').accordion('remove',_lp.frame.player.adl.renameTitle);
			if(rv && rv.M && rv.M.C && rv.M.C.Res){
				if(rv.M.C.Res.Error == "0"){
					$('#adl_combobox').combobox('loadData',adlData);
		            $.messager.show({
		                title:_lp.frame.player.notes.noteTitle1,
		                msg:_lp.frame.player.notes.note1,
		                timeout:4000,
		                showType:'slide'
		            });
				}else{
		            $.messager.show({
		                title:_lp.frame.player.notes.noteTitle1,
		                msg:_lp.frame.player.notes.noteError1+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.player.notes.noteTitle1,
	                msg:_lp.frame.player.notes.noteError1+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
		}
	},
	

	//
	open_pre_position_panel:function(){

		if($('#iv_accordion').accordion('getPanel',_lp.frame.player.tour.setTitle)) $('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
		if($('#iv_accordion').accordion('getPanel',_lp.frame.player.adl.renameTitle)) $('#iv_accordion').accordion('remove',_lp.frame.player.adl.renameTitle);
		
		var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.prepostion.renameTitle);
		if(p){
			$('#iv_accordion').accordion('remove',_lp.frame.player.prepostion.renameTitle);
		}
		
		var preposition_id = $('#pre_position_combobox').combobox('getValue');
		var html = '';
		html += '<form><input type="hidden" id="preposition_id" value="'+preposition_id+'" />';
		html += '<table>';
		html += '<tr>';
		html += '<td>'+_lp.frame.player.prepostion.name.lbl+':</td><td><input class="easyui-textbox" id="preposition_name" data-options="required:true,validType:\'length[1,64]\'" /></td>';
		html += '</tr>';
		html += '<tr>';
		html += '<td></td><td><a></a></td>';
		html += '</tr>';
		html += '</table>';
		html += '</form>';
		html += '<div style="text-align:center;padding:5px">';
		html += '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_pl.submit_pre_position_name()">'+_lp.frame.player.btns.save+'</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		html += '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="_pl.close_pre_position_panel()">'+_lp.frame.player.btns.cancel+'</a>';
		html += '</div>';
		$('#iv_accordion').accordion('add',{
			title:_lp.frame.player.prepostion.renameTitle,
			collapsed:false,
			collapsible:false,
			content:html
		});
	},
	//
	close_pre_position_panel:function(){
		$('#iv_accordion').accordion('remove',_lp.frame.player.prepostion.renameTitle);
	},

	submit_pre_position_name:function(){
		var cameras = $('#camera_list').datagrid('getSelections');
		if(cameras && cameras.length > 0){
			var c = cameras[0];
		
			var preposition_id = $('#preposition_id').val();
	
			var preposition_name = $('#preposition_name').textbox('getValue');
			
			var prePositionData = $('#pre_position_combobox').combobox('getData');
			prePositionData[parseInt(preposition_id)-1].name = preposition_name;
			
			var params = new Array();
			params.push({
	        			name:'Preset',
	        			attrs:[{name:'Idx',val:preposition_id},{name:'Name',val:preposition_name}]
	        		});
			
			for(var i = 0 ; i < prePositionData.length; i++){
				if(prePositionData[i].id != prePositionData[i].name){
					params.push({
	        			name:'Preset',
	        			attrs:[{name:'Idx',val:prePositionData[i].id},{name:'Name',val:prePositionData[i].name}]
	        		});
				}
			}
		
	        rv = _p.set_res_configs(
	        	_pl.connectId,
	        	c.pu.puid,
	        	"PTZ",
	        	c.ptz,
	        	[{
	        		id:"F_PTZ_PresetPositionSets",
	        		attrs:[],
	        		params:params
	        	}]
	        );
			if(rv && rv.M && rv.M.C && rv.M.C.Res  ){
				$('#iv_accordion').accordion('remove',_lp.frame.player.prepostion.renameTitle);
				if(rv.M.C.Res.Error == "0"){
					$('#pre_position_combobox').combobox('loadData',prePositionData);
		            $.messager.show({
		                title:_lp.frame.player.notes.noteTitle1,
		                msg:_lp.frame.player.notes.note1,
		                timeout:4000,
		                showType:'slide'
		            });
				}else{
		            $.messager.show({
		                title:_lp.frame.player.notes.noteTitle1,
		                msg:_lp.frame.player.notes.noteError1+'（error:'+rv.M.C.Res.Error+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
				}
			}else{
	            $.messager.show({
	                title:_lp.frame.player.notes.noteTitle1,
	                msg:_lp.frame.player.notes.noteError1+'（response:'+rv+'）。',
	                timeout:4000,
	                showType:'slide'
	            });
	    	}
		}
	},

	open_tour_panels:function(){

		var html = '';
		
    	html += '<div id="iv_de_tabs" class="easyui-layout"  style="width:500px;height:400px">';
    		html += '<div id="iv_region_west" data-options="region:\'west\'" style="width:200px;height:400px;border-width: 1px 1px 1px 1px;"><div style="margin:5px;"><div id="tour_tabs"></div></div>';
    		html += '<div id="iv_region_center" data-options="region:\'center\',"  style="width:auto;" ><table id="ivsorces_dr"></table></div>';
        html += '</div>';
        
      	
        $('<div></div>').dialog({
         id:'tour_dlg',	
  		 title:_lp.frame.player.tour.setTitle,
         modal: true,
         width:850,
  	     height:470,
  	     content:html,
  	     closed: false,
		 cache: false,
		 closable: false,	 
  	     onOpen:function(){
  	   		/*
  	     	var htmlstr	=  ""
 	    	for(var i=0;i< _de.IVDatas.length;i++){
 	    	 	var d = _de.IVDatas[i];
 	    	 	var decname = d.name;
 	    	 	var puid = d.puid;
 	    		htmlstr += "<div id=\"iv"+i+"\" onclick=\"_de.changeIV('"+decname+"')\" style=\"border:1px solid #D3D3D3;margin-top:5px;height:25px;line-height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+decname+"</div>";
 	    	}
		   
 	    	$('#iv_tabs').html(htmlstr);
 	  	
			 
	      	_de.changeIV(_de.IVDatas[0].name);
       		*/
			
 	     },
         buttons: [{                    //设置下方按钮数组
               text: _lp.frame.decode.btns.confirm,
               iconCls: 'icon-ok',
    		   handler: function () {
				//	_de.addIVsources();
    		   }
    		   }, {
    		   text: _lp.frame.decode.btns.canael,
               iconCls: 'icon-cancel',
               handler: function () {
               
               	 $("#tour_dlg").dialog('destroy')
                 
               	 
                }
            }]
        }); 
        
	},
		
	open_tour_panel:function(){
		if($('#iv_accordion').accordion('getPanel',_lp.frame.player.prepostion.renameTitle)) $('#iv_accordion').accordion('remove',_lp.frame.player.prepostion.renameTitle);
		if($('#iv_accordion').accordion('getPanel',_lp.frame.player.adl.renameTitle)) $('#iv_accordion').accordion('remove',_lp.frame.player.adl.renameTitle);
		
		var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.tour.setTitle);
		if(p){
			$('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
		}
		var tourData = $('#tour_combobox').combobox('getData');
		
		var htmlstr = '<div id="tour_panel" style="width:220px;">';
	
		htmlstr += '<div id="tour_list" style="width:220px;height:auto;">';

		for(var i=0;i< tourData.length;i++){
    	 	var t = tourData[i];
    	 	var id = t.id;
    	 	var name = t.name;
    		htmlstr += "<div id=\"tour"+id+"\" onclick=\"_pl.changeTour('"+id+"')\" style=\"float:left;border:1px solid #D3D3D3;margin:5px 10px;width:150px;height:25px;line-height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+name+"</div>";
    		htmlstr += '<div style="float:left;margin:5px 0;margin-left:5px;height:25px;line-height:25px;"><a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-setting\'" onclick="_pl.edit_tour('+id+');" title='+_lp.frame.player.tour.set+'></a></div>';	
    	}
    		htmlstr += '</div>';
    		
    		htmlstr += '<div style="clear:both"></div>';

    		htmlstr += '<div style="width:220px;height:25px;">';
    		htmlstr += '<div style="float:left;margin:5px 0;margin-left:13px;"><a id="tour_up" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-up-point\'" onclick="_pl.tourUp();" title="'+_lp.frame.player.tour.up+'" ></a></div>';
			htmlstr += '<div style="float:left;margin:5px 0;margin-left:13px;"><a id="tour_down" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-down-point\'" onclick="_pl.tourDown();" title="'+_lp.frame.player.tour.down+'"></a></div>';
			htmlstr += '<div style="float:left;margin:5px 0;margin-left:13px;"><a id="tour_add" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon_ptz_add\'" onclick="_pl.tourAdd();"  title="'+_lp.frame.player.tour.add1+'"></a></div>';
				
			htmlstr += '<div style="float:left;margin:5px 0;margin-left:13px;"><a id="tour_sub" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon_ptz_sub\'" onclick="_pl.tourDel();"  title="'+_lp.frame.player.tour.sub+'"></a></div>';	

			htmlstr += '<div style="float:left;margin:5px 0;margin-left:13px;"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="_pl.close_tour_panel1()">'+_lp.frame.player.btns.cancel+'</a></div>';

    		htmlstr += '</div>';
    	
    		htmlstr += '<div id="add_tour"></div>';
    		htmlstr += '</div>';		
	
		/*
		var tour_id = $('#tour_combobox').combobox('getValue');
		var tour_name = $('#tour_combobox').combobox('getText');
		var html = '';
		html += '<form id="add-tours-form"><input type="hidden" id="tour_id" value="'+tour_id+'" />';

	//	html += '<table border=0 width="270" >';
	//	html += '<div style="margin:5px 1px"><span>'+_lp.frame.player.tour.name.lbl+':</span><input class="easyui-textbox" style="width:120px;margin-left:10px" id="tour_name" value="'+tour_name+'" data-options="required:true,validType:\'length[1,64]\'" /></div>';
	//	html += '</table></form>';
		html += '</form>';

		html += '<table id="edit_tour_dr" class="easyui-datagrid" style="width:220px;height:150px"';
		html += '        data-options="singleSelect:true,border:false">';
		html += '    <thead>';
		html += '        <tr>';
	//	html += '           <th data-options="field:\'id\',width:30">'+_lp.frame.player.tour.edit_list.col.id+'</th>';
		html += '            <th data-options="field:\'name\',width:70">'+_lp.frame.player.tour.edit_list.col.name+'</th>';
		html += '            <th data-options="field:\'linger\',width:60">'+_lp.frame.player.tour.edit_list.col.linger+'</th>';
		html += '            <th data-options="field:\'op\',width:90,align:\'right\'">'+_lp.frame.player.tour.edit_list.col.op+'</th>';
		html += '        </tr>';
		html += '    </thead>';
		html += '</table>';
		
		
	//	html += '<table border=0 width="280" >';
		html += '<div style="margin:5px 1px"><a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:10px" onclick="_pl.add_tour_row();">'+_lp.frame.player.tour.add+'</a><a href="javascript:void(0)" style="margin-left:30px" class="easyui-linkbutton" onclick="_pl.submit_tours();" >'+_lp.frame.player.btns.save+'</a><a href="javascript:void(0)" style="margin-left:30px" class="easyui-linkbutton" onclick="_pl.close_tour_panel();" >'+_lp.frame.player.btns.cancel+'</a></div>';
//		html += '</table>';
	    */
		$('#iv_accordion').accordion('add',{
			title:_lp.frame.player.tour.setTitle,
			collapsed:false,
			collapsible:false,
			content:htmlstr
		});
		/*
		if(tourData.length == 0){
			$('#tour_sub').linkbutton({disabled:true});
			$('#tour_up').linkbutton({disabled:true});
			$('#tour_down').linkbutton({disabled:true});
		}
		
		if(tourData.length == 1){
			$('#tour_sub').linkbutton({disabled:true});
			$('#tour_up').linkbutton({disabled:true});
			$('#tour_down').linkbutton({disabled:true});
		}*/		
		_pl.changeTour(tourData[0].id);		
	},
		
	changeTour:function(id){

		var tourData = $('#tour_combobox').combobox('getData');
		/*
		if(id == tourData[0].id){
			$('#tour_up').linkbutton({disabled:true});
			$('#tour_down').linkbutton({disabled:false});
		}
		if(id == tourData[tourData.length-1].id){
			$('#tour_down').linkbutton({disabled:true});
			$('#tour_up').linkbutton({disabled:false});
		}*/
		for(var i=0;i<tourData.length;i++){
			var t = tourData[i];
			if(t.id == id){
				_pl.curTourId = t.id;
				document.getElementById("tour"+t.id+"").style.fontWeight="bold";
			}
			else{
				if(document.getElementById("tour"+t.id+"")){
					document.getElementById("tour"+t.id+"").style.fontWeight="normal";	
				}	
			}
		}
	
	},
		
	tourUp:function(){
	
		var tourData = $('#tour_combobox').combobox('getData');
	
		if(_pl.curTourId == tourData[0].id)	return;
		
		for(var i=0;i < tourData.length;i++){
			var t = tourData[i];
			if(t.id == _pl.curTourId){
				var temp = 	tourData[i];
				var temp1 =	tourData[i-1];
				tourData[i] = temp1;
				tourData[i-1] = temp;
				break;			
			}
		}
		var htmlstr = "";
		for(var i=0;i< tourData.length;i++){
    	 	var t = tourData[i];
    	 
    	 	var id = t.id;
    	 	var name = t.name;
    		htmlstr += "<div id=\"tour"+id+"\" onclick=\"_pl.changeTour('"+id+"')\" style=\"float:left;border:1px solid #D3D3D3;margin:5px 10px;width:150px;height:25px;line-height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+name+"</div>";
    		htmlstr += '<div style="float:left;margin:5px 0;margin-left:5px;height:25px;line-height:25px;"><a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-setting\'" onclick="_pl.edit_tour('+id+');" title='+_lp.frame.player.tour.set+'></a></div>';	
    	}
    	$("#tour_list").empty(); 
		$("#tour_list").html(htmlstr);
		
		$.parser.parse('#tour_list')
		
		_pl.changeTour(_pl.curTourId);	
		$('#tour_combobox').combobox('loadData',tourData);
		$('#tour_combobox').combobox('select',tourData[0].id);
		
		/*
		if(tourData.length > 0){
			$('#tour_sub').linkbutton({disabled:false});	
		}
		if(tourData.length > 1){
			$('#tour_up').linkbutton({disabled:false});
			$('#tour_down').linkbutton({disabled:false});
		}
		if(_pl.curTourId == tourData[0].id){
			$('#tour_up').linkbutton({disabled:true});
		} */
		_pl.submit_tours(-1);
	},
				
	tourDown:function(){
	
		var tourData = $('#tour_combobox').combobox('getData');
		if(_pl.curTourId == tourData[tourData.length-1].id) return;
		
		for(var i=0;i < tourData.length;i++){
			var t = tourData[i];
			if(t.id == _pl.curTourId){
				var temp = 	tourData[i];
				var temp1 =	tourData[i+1];
				tourData[i] = temp1;
				tourData[i+1] = temp;
				break;		
			}
		}

		
		var htmlstr = "";
		for(var i=0;i< tourData.length;i++){
    	 	var t = tourData[i];
    	 	var id = t.id;
    	 	var name = t.name;
    		htmlstr += "<div id=\"tour"+id+"\" onclick=\"_pl.changeTour('"+id+"')\" style=\"float:left;border:1px solid #D3D3D3;margin:5px 10px;width:150px;height:25px;line-height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+name+"</div>";
    		htmlstr += '<div style="float:left;margin:5px 0;margin-left:5px;height:25px;line-height:25px;"><a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-setting\'" onclick="_pl.edit_tour('+id+');" title='+_lp.frame.player.tour.set+'></a></div>';	
    	}
    	$("#tour_list").empty(); 
		$("#tour_list").html(htmlstr);
		
		$.parser.parse('#tour_list')
		
		_pl.changeTour(_pl.curTourId);	
		$('#tour_combobox').combobox('loadData',tourData);
		$('#tour_combobox').combobox('select',tourData[0].id);
		/*	
		if(tourData.length > 0){
			$('#tour_sub').linkbutton({disabled:false});	
		}
		if(tourData.length > 1){
			$('#tour_up').linkbutton({disabled:false});
			$('#tour_down').linkbutton({disabled:false});
		}
		if(_pl.curTourId == tourData[tourData.length-1].id) 
		{
			$('#tour_down').linkbutton({disabled:true});
		}*/
		_pl.submit_tours(-1);
	},
		
	tourDel:function(){
	
		var tourData = $('#tour_combobox').combobox('getData');
	
		if(tourData == 0) return;
		
		for(var i=0;i < tourData.length;i++){
			var t = tourData[i];
			if(t.id == _pl.curTourId){
				tourData.splice(i,1);
				break;		
			}
		}
		
		var htmlstr = "";
		for(var i=0;i< tourData.length;i++){
    	 	var t = tourData[i];
    	 	var id = t.id;
    	 	var name = t.name;
    		htmlstr += "<div id=\"tour"+id+"\" onclick=\"_pl.changeTour('"+id+"')\" style=\"float:left;border:1px solid #D3D3D3;margin:5px 10px;width:150px;height:25px;line-height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+name+"</div>";
    		htmlstr += '<div style="float:left;margin:5px 0;margin-left:5px;height:25px;line-height:25px;"><a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-setting\'" onclick="_pl.edit_tour('+id+');" title='+_lp.frame.player.tour.set+'></a></div>';	
    	}
    	$("#tour_list").empty(); 
		$("#tour_list").html(htmlstr);
		
		$.parser.parse('#tour_list')
		
		if(tourData[0]){
			_pl.changeTour(tourData[0].id);	
			$('#tour_combobox').combobox('loadData',tourData);
			$('#tour_combobox').combobox('select',tourData[0].id);
		}else{
			$('#tour_combobox').combobox('clear');	
			$('#tour_combobox').combobox('loadData',tourData);	
		}
		/*
		if(tourData.length == 0){
			$('#tour_sub').linkbutton({disabled:true});
			$('#tour_up').linkbutton({disabled:true});
			$('#tour_down').linkbutton({disabled:true});	
		}
		if(tourData.length == 1){
			$('#tour_up').linkbutton({disabled:true});
			$('#tour_down').linkbutton({disabled:true});
		}*/
		_pl.submit_tours(-1);		
	},
		
	tourAdd:function(){
		var html = "";
		html += '<form id="add-tours-form"><div style="margin:15px 10px;width:200px;"><span>'+_lp.frame.player.tour.name.lbl+':</span><input class="easyui-textbox" style="width:120px;" id="tour_name"  data-options="required:true,validType:\'length[1,64]\'" /></div></form>';
		html += '<div style="margin:5px 1px"><a href="javascript:void(0)" style="margin-left:35px" class="easyui-linkbutton" onclick="_pl.add_tours();" >'+_lp.frame.player.btns.save+'</a><a href="javascript:void(0)" style="margin-left:30px" class="easyui-linkbutton" onclick="_pl.add_tour_cancel();" >'+_lp.frame.player.btns.cancel+'</a></div>';
		$("#add_tour").html(html);
		$.parser.parse('#add_tour');				
	},
		
	add_tours:function(){
		if ($('#add-tours-form').form("validate") == true) {
			var tour_name = $("#tour_name").val();
			var max = 0;
			var tourData = $('#tour_combobox').combobox('getData');
			for(var i=0;i < tourData.length;i++){
				var t = tourData[i];
				if(t.id > max) max = t.id;
			}
			tourData.push({id:max+1,name:tour_name,preset:[]});	
		}
		var htmlstr = "";
		for(var i=0;i< tourData.length;i++){
    	 	var t = tourData[i];
    	 	var id = t.id;
    	 	var name = t.name;
    		htmlstr += "<div id=\"tour"+id+"\" onclick=\"_pl.changeTour('"+id+"')\" style=\"float:left;border:1px solid #D3D3D3;margin:5px 10px;width:150px;height:25px;line-height:25px;text-align:center;cursor:pointer;overflow:hidden;text-overflow:ellipsis\" >"+name+"</div>";
    		htmlstr += '<div style="float:left;margin:5px 0;margin-left:5px;height:25px;line-height:25px;"><a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-setting\'" onclick="_pl.edit_tour('+id+');" title='+_lp.frame.player.tour.set+'></a></div>';	
    	}
    	$("#tour_list").empty(); 
		$("#tour_list").html(htmlstr);
		
		$.parser.parse('#tour_list')
		
		_pl.changeTour(_pl.curTourId);	
		$('#tour_combobox').combobox('loadData',tourData);
		$('#tour_combobox').combobox('select',tourData[0].id);
		$("#add_tour").html("");
		/*
		if(tourData.length > 0){
			$('#tour_sub').linkbutton({disabled:false});	
		}
		if(tourData.length == 1){
			$('#tour_up').linkbutton({disabled:true});
			$('#tour_down').linkbutton({disabled:true});
		}
		if(tourData.length > 1){
			$('#tour_up').linkbutton({disabled:false});
			$('#tour_down').linkbutton({disabled:false});
		}*/
		_pl.submit_tours(-1);
	},
			
	add_tour_cancel:function(){
		$("#add_tour").html("");
	},
	
	edit_tour:function(id){
	
		var p = $('#iv_accordion').accordion('getPanel',_lp.frame.player.tour.setTitle);
		if(p){
			$('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
		}
		
		var tourData = $('#tour_combobox').combobox('getData');
		var preset = new Array();
		for(var i=0;i< tourData.length;i++){
			var t = tourData[i];
			if(t.id == id){
				preset = t.preset;
			}
		}
		for(var m=0;m< preset.length;m++){
			preset[m].id = m;
		}
	
		var html = '<div style="width:225px;height:150px;overflow-x:hidden">';
		html += '<table id="edit_tour_dr" class="easyui-datagrid" >';
		html += '</table></div>';
		
		
		html += '<div style="margin:5px 1px"><a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:10px" onclick="_pl.add_tour_row();">'+_lp.frame.player.tour.add+'</a><a href="javascript:void(0)" style="margin-left:30px" class="easyui-linkbutton" onclick="_pl.submit_tours('+id+');" >'+_lp.frame.player.btns.save+'</a><a href="javascript:void(0)" style="margin-left:30px" class="easyui-linkbutton" onclick="_pl.close_tour_panel();" >'+_lp.frame.player.btns.cancel+'</a></div>';
	    
		$('#iv_accordion').accordion('add',{
			title:_lp.frame.player.tour.setTitle,
			collapsed:false,	
			collapsible:false,
			content:html
		});
	   	_pl.editTourDREndeditIndex = undefined;
		var presetData = new Array();
		var presetPosition = $('#pre_position_combobox').combobox("getData");

		$('#edit_tour_dr').datagrid({
			width:230,
			singleSelect:true,
			checkOnSelect:false,
			onClickRow:"onClickRow",
			columns:[[
				{field:'idx',title:_lp.frame.player.tour.edit_list.col.name,width:70,align:'center',
					formatter:function(value,rec,index){
						 for(var j=0 ; j<presetPosition.length;j++){
							if(presetPosition[j].id == value){
								return presetPosition[j].name;
							} 
						 }
					},   
					editor:{
                       type:'combobox',
                       options:{
                           valueField:'id',
                           textField:'name',
                           data:presetPosition,
                           required:true                        
                        }
                    }
	             },
				{field:'linger',title:_lp.frame.player.tour.edit_list.col.linger,width:60,align:'center',editor:'text'},
				{field:'op',title:_lp.frame.player.tour.edit_list.col.op,width:98,align:'center',
				formatter:function(value,rec,index){  
                    var btns = '<a href="javascript:void(0);" style="margin-left:10px"  onclick="_pl.add_tour_position(\''+index+'\');" >'+_lp.frame.player.btns.ok+'</a><a href="javascript:void(0);" style="margin-left:10px" onclick="_pl.remove_tour_position(\''+index+'\');" >'+_lp.frame.player.btns.del+'</a>';
			        return btns; 
                }}  	 
			]],
			onSelect:function(rowIndex, rowData){
				_de.RowDatas.push(rowData);
			},
			onUnselect:function(rowIndex, rowData){	
				for(var i = 0; i<_de.RowDatas.length;i++){
					var row = _de.RowDatas[i];
					if(row.index == rowData.index && row.puid == rowData.puid){
						_de.RowDatas.splice(i,1);
					}
				}
			},
		
			onAfterEdit: function (rowIndex, rowData, changes) {
            	_pl.editTourDREndeditIndex = undefined;
            	if (_pl.editTourDREndeditIndex != undefined) {
	                $("#edit_tour_dr").datagrid('endEdit', _pl.editTourDREndeditIndex);
	             }
        	},
        	onDblClickRow:function (rowIndex, rowData) {
            	if (_pl.editTourDREndeditIndex != undefined) {
                	$("#edit_tour_dr").datagrid('endEdit', _pl.editTourDREndeditIndex);
            	}
            	if (_pl.editTourDREndeditIndex == undefined) {
                	$("#edit_tour_dr").datagrid('beginEdit', rowIndex);
                	_pl.editTourDREndeditIndex = rowIndex;
            	}
       	 	},
       	 	onClickRow:function(rowIndex,rowData){
                 $(this).datagrid('unselectRow', rowIndex);
	           	 if (_pl.editTourDREndeditIndex != undefined) {
	                $("#edit_tour_dr").datagrid('endEdit', _pl.editTourDREndeditIndex);
	             }
	        }
		});	
		
		 $('#edit_tour_dr').datagrid('loadData',preset)		
	},
						
			
    add_tour_row:function(){
    	  var t = $('#edit_tour_dr').datagrid('getRows').length
     
            if (_pl.edit_tour_dr_endediting()){
            $('#edit_tour_dr').datagrid('appendRow',{id:t,idx:'1',linger:'10',op:'<a href="javascript:void(0);" style="margin-left:10px"  onclick="_pl.add_tour_position(\''+t+'\');" >'+_lp.frame.player.btns.ok+'</a>&nbsp;<a href="javascript:void(0);" style="margin-left:30px" onclick="_pl.remove_tour_position(\''+t+'\');" >'+_lp.frame.player.btns.del+'</a>'});
            _pl.editTourDREndeditIndex = $('#edit_tour_dr').datagrid('getRows').length-1;
            $('#edit_tour_dr').datagrid('selectRow', _pl.editTourDREndeditIndex)
                    .datagrid('beginEdit', _pl.editTourDREndeditIndex);
		  }
    },
    editTourDREndeditIndex:undefined,
    edit_tour_dr_endediting:function(){
    	
        if (_pl.editTourDREndeditIndex == undefined){return true}
        
        if ($('#edit_tour_dr').datagrid('validateRow', _pl.editTourDREndeditIndex)){
            var ed = $('#edit_tour_dr').datagrid('getEditor', {index:_pl.editTourDREndeditIndex,field:'idx'});
        
            var preset = $(ed.target).combobox('getText');
            var presetId = $(ed.target).combobox('getValue');
         	
            $('#edit_tour_dr').datagrid('getRows')[_pl.editTourDREndeditIndex]['idx'] =  presetId;
          //  $('#edit_tour_dr').datagrid('getRows')[_pl.editTourDREndeditIndex]['name'] =  preset;
            	
            $('#edit_tour_dr').datagrid('getRows')[_pl.editTourDREndeditIndex]['op'] = '<a href="javascript:void(0);" onclick="_pl.remove_tour_position(\''+$('#edit_tour_dr').datagrid('getRows')[_pl.editTourDREndeditIndex]['id']+'\');" >'+_lp.frame.player.btns.del+'</a>';
            $('#edit_tour_dr').datagrid('endEdit', _pl.editTourDREndeditIndex);
            
            _pl.editTourDREndeditIndex = undefined;
            
          
            return true;
        } else {
            return false;
        }
    },
    add_tour_position:function(t){
    	$("#edit_tour_dr").datagrid('endEdit',t);
    },
    remove_tour_position:function(t){
    
    	_pl.edit_tour_dr_endediting(t);
    	
    	var rows = $('#edit_tour_dr').datagrid('getRows');

    	for(var i = 0;i < rows.length;i++){
    		if(rows[i].id == t){
    			console.log(rows[i].id)
    			console.log(t)	
    			$('#edit_tour_dr').datagrid('deleteRow', i);
    			break;
    		}
    	}
    },
    	
	submit_tours:function(id){
			
			var cameras = $('#camera_list').datagrid('getSelections');
			var tourData = $('#tour_combobox').combobox('getData');
			if(cameras && cameras.length > 0){
				var c = cameras[0];
				if(id >= 0){
					var rows = $('#edit_tour_dr').datagrid('getRows');		
					for(var i = 0 ;i < tourData.length;i++){
						var t = tourData[i];
						if(t.id == id){
							t.preset = rows;
							break;
						}
					}	
				}	
		    	var params = new Array();
		    	for(var j = 0;j < tourData.length;j++){
		    		var t = tourData[j];
		    		var td =  tourData[j].preset;
		    		var presetXML = "";
		    		for(var k = 0;k < td.length;k++){
		    			presetXML += '<Preset Idx="'+td[k].idx+'" Linger="'+td[k].linger+'" />';
		    		}
		    	
		    		params.push({
	        			name:'Tour',
	        			attrs:[{name:"Name",val:t.name}],
	        			childXML:presetXML	
	        		});
		    	}
		   
		    	console.log(params);
		        rv = _p.set_res_configs(
		        	_pl.connectId,
		        	c.pu.puid,
		        	"PTZ",
		        	c.ptz,
		        	[{
		        		id:"F_PTZ_TourSets",
		        		attrs:[],
		        		params:params
		        	}]
		        );
		        			
				if(rv && rv.M && rv.M.C && rv.M.C.Res){
				
				//	$('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
					if(rv.M.C.Res.Error == "0"){
			            $.messager.show({
			                title:_lp.frame.player.notes.noteTitle1,
			                msg:_lp.frame.player.notes.note1,
			                timeout:4000,
			                showType:'slide'
			            });
					}else{
			            $.messager.show({
			                title:_lp.frame.player.notes.noteTitle1,
			                msg:_lp.frame.player.notes.noteError1+'（error:'+rv.M.C.Res.Error+'）。',
			                timeout:4000,
			                showType:'slide'
			            });
					}
				}else{
		            $.messager.show({
		                title:_lp.frame.player.notes.noteTitle1,
		                msg:_lp.frame.player.notes.noteError1+'（response:'+rv+'）。',
		                timeout:4000,
		                showType:'slide'
		            });
		    	}
		        
			}
    
	},
	close_tour_panel:function(){
		$('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
		_pl.open_tour_panel();
	},
	close_tour_panel1:function(){
		$('#iv_accordion').accordion('remove',_lp.frame.player.tour.setTitle);
	},	
	
	// 播放视频
    play_video: function (camera) {
        if (typeof camera == "object" && camera) {
        	var streamType = $('#iv_streatype_cmb').combobox('getValue');
            var puid = camera.pu.puid;
            if (!puid) return false;
            var idx = camera.idx || 0;
            if (!idx) return false;
            var enable = camera.pu.enable;
            if (!enable) return false;
            var connectId = _pl.connectId;
            if (!connectId) return false;
            var online = camera.pu.online;
            var wndId = _pl.curActiveWndId;
            //wndId = "windowbox"+camera.idx;
            _p.stop_video(null,wndId);
        	var rv = _p.play_video(
        		connectId, puid, idx, wndId, 
        		{
        			lbtn_click: true, 
        			select_rect: true, 
        			fsw_show: true, 
        			fsw_hide: true, 
        			fsw_hide: true, 
        			menu_command: {
        				status:true,
        				menu:[
  							{key:"stopvideo",text:_lp.frame.player.notes.stopvideo},
							{key:"snapshot",text:_lp.frame.player.notes.snapshot},
							{key:"record",text:_lp.frame.player.notes.record},
							{key:"playaudio",text:_lp.frame.player.notes.playaudio},
							{key:"playupaudio",text:_lp.frame.player.notes.playupaudio},
							{key:"playtalk",text:_lp.frame.player.notes.playtalk},
							//{key:"startGPS",text:"打开GPS"},
							{key:"-",text:"split"},
							{key:"fullscreen",text:_lp.frame.player.notes.fullscreen}
						],
						callback:function(key){
							switch(key){
								case "stopvideo":
									_pl.stop_video(wndId);
								break;
								case "snapshot":
									_pl.snapshot(wndId);
									break;
								case "record":
									_pl.record(wndId);
									break;
								case "playaudio":
									_pl.play_audio(wndId);
									break;
								case "playupaudio"	:
									_pl.play_upaudio(wndId);
									break;
								case "playtalk"	:
									_pl.play_talk(wndId);
									break;
								case "startGPS" :
									//_pl.StartGPS();
									break;	
								case "fullscreen":	
									_pl.full_screen(wndId);
									break;			
							}
						}
        			},
        			eventCallback: function (eventName, wndId) { 
        				_pl.event_callback(eventName, wndId); 
        			}
        		}
        		,streamType
        	);
        	//console.log(rv)
        	return rv;
        	/*
        	_p.get_video_status(function(args){
        		if(typeof args == "object"){
        			P_LY.WindowContainers.each(
        				function(item){
        					var node = item.value;
        					if(node.window){
	        					var html1 = "",html2 = "",htmlstr = "";
	        					if(args.errorCode == P_Error.SUCCESS){
	        						switch(Number(args.status)){//-1表示流已断开，0表示正在缓冲，1表示正在播放,2表示正在下载，3表示下载完成
	        						case -1:
	        							html1 = _lp.frame.player.notes.streambreak;//"流已断开"
	        							break;
	        						case 0:
	        							html1 =  _lp.frame.player.notes.buffering;//"正在缓冲"
	        							break;
	        						case 1:
	        							html1 = _lp.frame.player.notes.playing;//"正在播放"
	        							break;
	        						case 2:
	        							html1 = _lp.frame.player.notes.downing;//"正在下载"
	        							break;
	        						case 3:
	        							html1 = _lp.frame.player.notes.downdone;//"下载完成"
	        							break;
	        						}
	        						var _bite = function(bite){
	        							return (bite / 1000).toFixed(0) + "KB/s"; 
	        						}
	        						var bitrate = args.keyData.bit_rate;
	        						var bite = _bite(bitrate)
	        						var framerate = args.keyData.frame_rate;
	        						var frame = framerate;
	        						
	        						var title2 = '<a class="snapshot"  title="'+_lp.frame.player.notes.snapshot+'" onclick="_pl.snapshot(\''+node.window.containerId+'\')" ></a>&nbsp;';
	        						var recStr = "";
	        						if(node.window.status.recording){
	        							recStr = _lp.frame.player.notes.recording;//"正在录像"
	        							title2 += '<a class="record"  title="'+_lp.frame.player.notes.recording+'"  onclick="_pl.record(\''+node.window.containerId+'\')"  ></a>&nbsp;';
	        						}else{
	        							recStr = "";
	        							title2 += '<a class="record"  title="'+_lp.frame.player.notes.record+'"  onclick="_pl.record(\''+node.window.containerId+'\')"  ></a>&nbsp;';
	        						}
	        						var audStr = "";
	        						if(node.window.status.playaudioing){
	        							audStr = _lp.frame.player.notes.audioing;//"正在播放声音"
	        							title2 += '<a class="audio"  title="'+_lp.frame.player.notes.audioing+'" onclick="_pl.play_audio(\''+node.window.containerId+'\')"></a>&nbsp;';
	        						}else{
	        							audStr = "";
	        							title2 += '<a class="audio"  title="'+_lp.frame.player.notes.playaudio+'" onclick="_pl.play_audio(\''+node.window.containerId+'\')"></a>&nbsp;';
	        						}
	        						html2 = _lp.frame.player.notes.frame+"："+frame+","+_lp.frame.player.notes.bite+"："+bite;
	        						if(args._HANDLE == node.window.params.ivStreamHandle){
	        							var windowtitlekey = $("#"+node.window.containerId+"").next().attr("id");
	        							//$("#"+windowtitlekey+" .title1")[0].innerHTML =  html1 +","+ html2+","+ recStr+","+audStr+","+upaudStr+","+talkStr;
	        							$("#"+windowtitlekey+" .title1")[0].innerHTML =  html1 +","+ html2;
	        							if($("#"+windowtitlekey+" .title2")[0].innerHTML == ""){
		        							$("#"+windowtitlekey+" .title2")[0].innerHTML =  title2;//'<input type=button class="audio_onmouseover"  title="'+audStr+'"/>&nbsp;<input type=button class="snapshot_onmouseover" title="'+_lp.frame.player.notes.snapshot+'" />&nbsp;&nbsp;';	
	        							}else{
        									if($("#"+windowtitlekey+" .title2 .record").length > 0){
        										var t = (node.window.status.recording ? _lp.frame.player.notes.recording :_lp.frame.player.notes.record );
        										if($("#"+windowtitlekey+" .title2 .record").attr('title') != t){
        											
        											$("#"+windowtitlekey+" .title2 .record").attr('title',t);
        										}
        									}
        									if($("#"+windowtitlekey+" .title2 .audio").length > 0){
        										var t = (node.window.status.playaudioing ? _lp.frame.player.notes.audioing :_lp.frame.player.notes.playaudio );
        										if($("#"+windowtitlekey+" .title2 .audio").attr('title') != t){
        											
        											$("#"+windowtitlekey+" .title2 .audio").attr('title',t);
        										}
        									}
	        							}
	        						}
        						}
        						else{
        							html1 = _lp.frame.player.notes.playfailed;//"播放失败";
        						}
        					}	
        				});
        			}
        	});
        	*/
        	return true;
        }
    },
    notify_stream:function(args){

		if(typeof args == "object"){
			P_LY.WindowContainers.each(
			function(item){
				var node = item.value;
				if(node.window){
					var html1 = "",html2 = "",htmlstr = "";
					if(args.errorCode == P_Error.SUCCESS){
						switch(Number(args.status)){//-1表示流已断开，0表示正在缓冲，1表示正在播放,2表示正在下载，3表示下载完成
						case -1:
							html1 = _lp.frame.player.notes.streambreak;//"流已断开"
							break;
						case 0:
							html1 =  _lp.frame.player.notes.buffering;//"正在缓冲"
							break;
						case 1:
							html1 = _lp.frame.player.notes.playing;//"正在播放"
							break;
						case 2:
							html1 = _lp.frame.player.notes.downing;//"正在下载"
							break;
						case 3:
							html1 = _lp.frame.player.notes.downdone;//"下载完成"
							break;
						}
						var _bite = function(bite){
							return (bite / 1000).toFixed(0) + "KB/s"; 
						}
						var bitrate = args.keyData.bit_rate;
						var bite = _bite(bitrate)
						var framerate = args.keyData.frame_rate;
						var frame = framerate;
						
						var title2 = '<a class="snapshot"  title="'+_lp.frame.player.notes.snapshot+'" onclick="_pl.snapshot(\''+node.window.containerId+'\')" ></a>&nbsp;';
						var recStr = "";
						if(node.window.status.recording){
							recStr = _lp.frame.player.notes.recording;//"正在录像"
							title2 += '<a class="record"  title="'+_lp.frame.player.notes.recording+'"  onclick="_pl.record(\''+node.window.containerId+'\')"  ></a>&nbsp;';
						}else{
							recStr = "";
							title2 += '<a class="record"  title="'+_lp.frame.player.notes.record+'"  onclick="_pl.record(\''+node.window.containerId+'\')"  ></a>&nbsp;';
						}
						var audStr = "";
						if(node.window.status.playaudioing){
							audStr = _lp.frame.player.notes.audioing;//"正在播放声音"
							title2 += '<a class="audio"  title="'+_lp.frame.player.notes.audioing+'" onclick="_pl.play_audio(\''+node.window.containerId+'\')"></a>&nbsp;';
						}else{
							audStr = "";
							title2 += '<a class="audio"  title="'+_lp.frame.player.notes.playaudio+'" onclick="_pl.play_audio(\''+node.window.containerId+'\')"></a>&nbsp;';
						}
						html2 = _lp.frame.player.notes.frame+"："+frame+","+_lp.frame.player.notes.bite+"："+bite;
						if(args._HANDLE == node.window.params.ivStreamHandle){
							var windowtitlekey = $("#"+node.window.containerId+"").next().attr("id");
							//$("#"+windowtitlekey+" .title1")[0].innerHTML =  html1 +","+ html2+","+ recStr+","+audStr+","+upaudStr+","+talkStr;
							$("#"+windowtitlekey+" .title1")[0].innerHTML =  html1 +","+ html2;
							if($("#"+windowtitlekey+" .title2")[0].innerHTML == ""){
    							$("#"+windowtitlekey+" .title2")[0].innerHTML =  title2;//'<input type=button class="audio_onmouseover"  title="'+audStr+'"/>&nbsp;<input type=button class="snapshot_onmouseover" title="'+_lp.frame.player.notes.snapshot+'" />&nbsp;&nbsp;';	
							}else{
								if($("#"+windowtitlekey+" .title2 .record").length > 0){
									var t = (node.window.status.recording ? _lp.frame.player.notes.recording :_lp.frame.player.notes.record );
									if($("#"+windowtitlekey+" .title2 .record").attr('title') != t){
										
										$("#"+windowtitlekey+" .title2 .record").attr('title',t);
									}
								}
								if($("#"+windowtitlekey+" .title2 .audio").length > 0){
									var t = (node.window.status.playaudioing ? _lp.frame.player.notes.audioing :_lp.frame.player.notes.playaudio );
									if($("#"+windowtitlekey+" .title2 .audio").attr('title') != t){
										
										$("#"+windowtitlekey+" .title2 .audio").attr('title',t);
									}
								}
							}
						}
					}
					else{
						html1 = _lp.frame.player.notes.playfailed;//"播放失败";
					}
				}	
			});
		}
    },
    stop_video:function(wndId,cameraRowIndex){
    	
    	var rows = $('#camera_list').datagrid("getRows");
    	
		if(typeof wndId == "undefined" || wndId == null){
		   var wndId = _pl.curActiveWndId;
		   
		   if(typeof cameraRowIndex != "undefined" && cameraRowIndex != null){		    	
		    	if(rows[cameraRowIndex]){
		    		// 找到播放窗口

		    		var idx = rows[cameraRowIndex].idx;
		    		var puid = rows[cameraRowIndex].pu.puid;

		    		P_LY.WindowContainers.each(
		    			function(item){
							var node = item.value;
							if(node.window){
		    					wndId = node.window.containerId;
			    				var p = node.window.params;

			    				if(p.puid == puid && idx == p.idx){
			    					if($("#"+wndId+"")[0]){
			    						return true;
			    					}
			    				}
							}
		    			}
		    		);
		    		
		    	}
		    	
		   }
		}

		if($("#"+wndId+"")[0]){

			var node = P_LY.WindowContainers.get(wndId);

    		var idx = node.window.params.idx;
    		var puid = node.window.params.puid;
			if(_pl.curActiveWndId == wndId) _pl.set_ptzpad_status(false);
		
			_p.stop_video(wndId);
			$("#"+wndId.replace("windowbox","windowtitle")+" .title1")[0].innerHTML =  _lp.frame.player.notes.noVideo;
			$("#"+wndId.replace("windowbox","windowtitle")+" .title2")[0].innerHTML =  '';
			if(typeof cameraRowIndex != "undefined"){
				$('#camera_list').datagrid('updateRow',{index:cameraRowIndex,row:{op:'<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-start\'" title="'+_lp.frame.player.cameralist.btns.play+'" onclick="_pl.camera_onclick(\''+cameraRowIndex+'\')"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-start"></span></span></a>',status:false}});
			}else{
			    //var rows = $('#camera_list').datagrid("getRows");
				for(var i = 0;i < rows.length;i++){
					if(rows[i].idx == idx && rows[i].pu.puid == puid){
						$('#camera_list').datagrid('updateRow',{index:i,row:{op:'<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-start\'" title="'+_lp.frame.player.cameralist.btns.play+'" onclick="_pl.camera_onclick(\''+i+'\')"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text l-btn-empty"></span><span class="l-btn-icon icon-start"></span></span></a>',status:false}});
			    		break;
		    		}
		
		    	}
			}
		}
    	return;
    },
    stop_videos:function(){
		var rows = $('#camera_list').datagrid("getRows");
		P_LY.WindowContainers.each(
			function(item){
				var node = item.value;
				if(node.window && node.window.status.playvideoing){
					var wndId = node.window.containerId;
			    	var p = node.window.params;					
			    	for(var i = 0;i <rows.length;i++){
			    		var idx = rows[i].idx;
			    		var puid = rows[i].pu.puid;
		    		
	    				if(p.puid == puid && idx == p.idx){
	    					_p.stop_video(wndId,i);
	    					break;
	    				}
			    	}
					return true;
				}
			}
		);
    },
    play_audio:function(wndId){
		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
			_p.play_audio(wndId);
		}
    },
    snapshot:function(wndId){
		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
			_p.snapshot(wndId);
		}
    },
    record:function(wndId){
		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
			_p.record(wndId);
		}
    },
    play_audio:function(wndId){

		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
			_p.play_audio(wndId);
		}
    },
    play_talk:function(wndId){
		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
	//	console.log(wndId);
			_p.play_talk(wndId);
			setTimeout(function(){_pl.refresh_audioout();},100);	
		}
    },
    play_upaudio:function(wndId){
		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
			_p.play_upaudio(wndId);
			setTimeout(function(){_pl.refresh_audioout();},100);
		}
    },
    full_screen:function(wndId){
		if(typeof wndId == "undefined"){
		   var wndId = _pl.curActiveWndId;
		}
		if($("#"+wndId+"")[0]){
			_p.full_screen(wndId);
		}

    },
    // 事件回调
    event_callback: function (eventName, wndId) {
        if (typeof eventName != "undefiend" || eventName) {
            switch (eventName) {
                case "lbtn_click":
					_pl.active_wnd(wndId);
                    break;
                case "select_rect":
                case "ptz_control":
                case "fsw_show":
                case "fsw_hide":
                    break;
                case "menu_command":
                    break;
                case "onRestore":
                    break;
                case "onFullScreen":
                    break;
            }
        }
    },
	// 创建窗口元素
    load_windows:function(num){
		htmlArray = Array();
        switch (num) {
            case 1:
            case 4:
            case 9:
            case 16:
            case 25:
            case 36:
            case 64:
                for (var i = 0; i < num; i++) {
                    htmlArray.push(
                        "<div id=\"wnd" + i + "\" class=\"windowbox\">",
                        "<div id=\"windowbox" + i + "\" class=\"wnd\">",
                        "</div>",
                        "<div id=\"windowtitle" + i + "\" class=\"windowtitle\"><div id=\"title" + i + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div  class=\"title2\"></div></div>",
                        "</div>"
                    );
                }
                break;
            case 6:
                for (var i = 0, j = 1; i < 9; i++, j++) {
                    if ((i + 1) < 3 && ((i + 1) % 3) != 0) {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                        i += 3;
                    }
                    else {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                    }
                }
                break;
            case 8:
                for (var i = 0, j = 1; i < 16; i++, j++) {
                    if ((i + 1) < 4 && ((i + 1) % 4) != 0) {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                        i += 8;
                    }
                    else {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                    }
                }
                break;
            case 10:
                for (var i = 0, j = 1; i < 25; i++) {
                    if ((i + 1) < 5 && ((i + 1) % 5) != 0) {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                        i += 15;
                        j++;
                    }
                    else {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                        j++;
                    }
                }
                break;
            case 12:
                for (var i = 0, j = 1; i < 36; i++) {
                    if ((i + 1) < 6 && ((i + 1) % 6) != 0) {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                        i += 24;
                        j++;
                    }
                    else {
                        htmlArray.push(
                            "<div id=\"wnd" + j + "\" class=\"windowbox\">",
                            "<div id=\"windowbox" + j + "\" class=\"wnd\"></div>",
                            "<div id=\"windowtitle" + j + "\" class=\"windowtitle\"><div id=\"title" + j + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                            "</div>"
                        );
                        j++;
                    }
                }
                break;
            default:
                for (var i = 0; i < 4; i++) {
                    htmlArray.push(
                        "<div id=\"wnd" + i + "\" class=\"windowbox\">",
                        "<div id=\"windowbox" + i + "\" class=\"wnd\"></div>",
                        "<div id=\"windowtitle" + i + "\" class=\"windowtitle\"><div id=\"title" + i + "\" class=\"title1\" >" + _lp.frame.player.notes.noVideo + "</div><div class=\"title2\"></div></div>",
                        "</div>"
                    );
                }
                break;
        }
        
        return htmlArray.join("");
        //_pl.set_window_size(num);
	},
	// 设置窗口尺寸
	set_window_size:function(num)
	{
		var s = {width:$("#play_window_container").width(),height:$("#play_window_container").height()};
		//return;
		var offsetWidth = 0;
		//$('#play_window_container')[0].style.height = s.height-10+'px';
		switch(num)
		{
	        case 1:
	        case 2:
	        case 4:
	        case 9:
	        case 16:
	        case 25:
	        case 36:
	        	offsetWidth = 0;
                var windowMath = Math.sqrt(num);
                var w = parseInt((s.width-offsetWidth) /windowMath) - 2;
                var h = parseInt((s.height) / windowMath);
                for (var i = 0; i < $("#play_window_container .windowbox").length; i++)
                {
                    var wnd = $("#play_window_container .windowbox")[i];
                	if(wnd && wnd.id.search("wnd") > -1){
                		$("#"+wnd.id)[0].style.width=w+"px";
                		$("#"+wnd.id)[0].style.height=h+"px";
                		$("#"+wnd.id+ " .wnd")[0].style.height=(h-20)+"px";
                	}
                }
	        	break;
	        case 6:
	        	offsetWidth = 8;
                var w = parseInt((s.width-offsetWidth) /3) - 2;
                var h = parseInt((s.height) / 3) - 2;
                for (var i = 0; i < $("#play_window_container .windowbox").length; i++)
                {
                    var wnd = $("#play_window_container .windowbox")[i];
                	if(wnd && wnd.id.search("wnd") > -1){
                		if(i == 0){
	                		$("#"+wnd.id)[0].style.width=w*2+2+"px";
	                		$("#"+wnd.id)[0].style.height=h*2+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h*2-20)+"px";
                		}
                		else{
	                		$("#"+wnd.id)[0].style.width=w+"px";
	                		$("#"+wnd.id)[0].style.height=h+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h-20)+"px";
                		}
                	}
                }
	        	break;
	        case 8:
	        	offsetWidth = 4;
                var w = parseInt((s.width-offsetWidth) /4) - 2;
                var h = parseInt((s.height) / 4) - 2;
                for (var i = 0; i < $("#play_window_container .windowbox").length; i++)
                {
                    var wnd = $("#play_window_container .windowbox")[i];
                	if(wnd && wnd.id.search("wnd") > -1){
                		if(i == 0){
	                		$("#"+wnd.id)[0].style.width=w*3+2+"px";
	                		$("#"+wnd.id)[0].style.height=h*3+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h*3-20)+"px";
                		}
                		else{
	                		$("#"+wnd.id)[0].style.width=w+"px";
	                		$("#"+wnd.id)[0].style.height=h+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h-20)+"px";
                		}
                	}
                }
	        	break;
	        case 10:
                var w = parseInt((s.width-offsetWidth) /5) - 2;
                var h = parseInt((s.height) / 5) - 2;
                for (var i = 0; i < $("#play_window_container .windowbox").length; i++)
                {
                    var wnd = $("#play_window_container .windowbox")[i];
                	if(wnd && wnd.id.search("wnd") > -1){
                		if(i == 0){
	                		$("#"+wnd.id)[0].style.width=w*4+3+"px";
	                		$("#"+wnd.id)[0].style.height=h*4+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h*4-20)+"px";
                		}
                		else{
	                		$("#"+wnd.id)[0].style.width=w+"px";
	                		$("#"+wnd.id)[0].style.height=h+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h-20)+"px";
                		}
                	}
                }
	        	break;
	        case 12:
	        	offsetWidth = 8;
                var w = parseInt((s.width-336) /6) - 2;
                var h = parseInt((s.height) / 6) - 2;
                for (var i = 0; i < $("#play_window_container .windowbox").length; i++)
                {
                    var wnd = $("#play_window_container .windowbox")[i];
                	if(wnd && wnd.id.search("wnd") > -1){
                		if(i == 0){
	                		$("#"+wnd.id)[0].style.width=w*5+8+"px";
	                		$("#"+wnd.id)[0].style.height=h*5+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h*5-20)+"px";
                		}
                		else{
	                		$("#"+wnd.id)[0].style.width=w+"px";
	                		$("#"+wnd.id)[0].style.height=h+"px";
	                		$("#"+wnd.id+ " .wnd")[0].style.height=(h-20)+"px";
                		}
                	}
                }
	        	break;
		}
	},
	// 初始化窗口容器
    init_window_container: function () {
        if ($("#play_window_container .wnd").length > 0) {
            _pl.containerKeys = new Array();
            var wndBoxs = $("#play_window_container .wnd");
            for (var i = 0; i < wndBoxs.length; i++) {
                var wndBox = wndBoxs[i];
                
                
                var windowtitleId = wndBox.id.replace("windowbox", "windowtitle");
                if($('#'+windowtitleId).length > 0){
                	$('#'+windowtitleId).unbind().bind("click",function(){
                		var id = this.id.replace("windowtitle","windowbox");
                		_pl.active_wnd(id);
                	});
                }
                if (wndBox && wndBox.id.search("windowbox") != -1) {
                    wndBox.onclick = function () {
                        _pl.active_wnd(this.id);
                    }
                    _pl.containerKeys.push(wndBox.id);
                    _p.set_wnd_conatiner(wndBox.id, 'wnd');
                }
            }
        }
    },
    // 激活窗口
    active_wnd:function(wndId){
    //	console.log(wndId)
        var keys = _pl.containerKeys;
        var puid = "",idx = "";;
        for (var i = 0; i < keys.length; i++) {
            var k = keys[i];
            var node = _p.get_wnd_container(k, 'wnd');
            if (node && node.container) {
                if (node.container == wndId) {
                    node.active = true;
                    var windowtitle = k.replace("windowbox", "windowtitle");
                    if ($('#'+windowtitle)) {
                    	$('#'+windowtitle)[0].style.backgroundColor = "#6681E3";
                    }
                    _pl.curActiveWndId = k;
                    //_p.Video.IConStatusCallBack();

                    if(typeof node.window != "undefined" && node.window != null && typeof node.window.params != "undefined"){
                    	puid = node.window.params.puid;
                    	idx = node.window.params.idx;
                    	_pl.set_ptzpad_status(true)
                    }else{
                    }
    				
                }
                else {
                    node.active = false;
                    var windowtitle = node.container.replace("windowbox", "windowtitle");
                    if ($('#'+windowtitle)) {
                    	$('#'+windowtitle)[0].style.backgroundColor = "#9DBDD8";
                    }
                    $('#'+node.container)[0].style.borderColor = "#9CAABD"; //"#9bcaf3"
                    $('#'+node.container)[0].style.color = "#000000";
                }
            }
        }
        

        //setTimeout(function(){
    		var rows = $('#camera_list').datagrid("getRows");
    		for(var i = 0;i < rows.length;i++){
    			if(idx == rows[i].idx && puid == rows[i].pu.puid){
    				$('#camera_list').datagrid("selectRow",i);
    				return;
    			}
    		}
    		_pl.set_ptzpad_status(false);
        //},1);
		
    }
}