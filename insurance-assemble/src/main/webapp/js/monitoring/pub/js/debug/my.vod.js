var _v = vod = {
	connectId:null,
	inited:false,
	pu:null,
	vod_status:false,	
	vodList:Array(),
	get_content:function(){
		var html = '';
		html += '<div id="vod_layout" class="easyui-layout" tabPosition="left" fit="true" style="width:auto;height:auto">';
		html += '<div id="vod_region_west" data-options="region:\'west\',border:true" style="width:240px;padding:2px;" ></div>';
		html += '<div id="vod_region_center" data-options="region:\'center\',border:true"  style="width:auto;" >';
		html += '<div id="vod_tabs" class="easyui-tabs" tabPosition="top" fit="true" style="width:auto;height:auto"></div>'
		html += '</div>';
		html += '</div>';
		return html;
	},
	init:function(connectId){
		if(_v.inited){
			return;
		}
		_v.inited = true;
		_v.init_frame();
		_v.connectId = connectId;
	},
	pu_change:function(){
		_v.load();
	},
	init_frame:function(){
		html = '';
		html += '<div id="vod_west_tabs" class="easyui-tabs" tabPosition="top" data-options="" fit="true" style="width:240px">';
			html += '<div title="'+_lp.frame.vod.record.title+'">';
			
				if(_cf.type == "embed"){
					html += '<div id="vod_record_accordion" class="easyui-accordion" border=false  data-options="" >';
					html += '   <div title="'+_lp.frame.vod.record.fast_index+'"  data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:1px;">';
					html += '		<div id="vod_record_cameratree" class="easyui-tree" style="height:auto;" ></div>';
					html += '   </div>';
					// 暂时隐藏高级查询
//					html += '   <div title="'+_lp.frame.vod.record.adv_query+'"  data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:1px;">';
//				    html += '	<table>';
//				    html += '	<tr><td>开始时间:</td><td><input id="vod_record_begintime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 00:00:00")+'" style="width:148px;"></td></tr>';
//				    html += '	<tr><td>结束时间:</td><td><input id="vod_record_endtime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 23:59:59")+'" style="width:148px;"></td></tr>';
//				    html += '	<tr><td>摄像头:</td><td><div id="vod_record_cameracmbgrid" data-options="idField:\'id\',textField:\'text\'" class="easyui-combogrid" style="width:145px;" ></div></td></tr>';
//				    html += '	<tr><td>录像原因:</td><td id="vod_record_reason_container" >';
//				    html += '	<div><input type=checkbox reason="Schedule" /><label>定时录像</label></div>';
//				    html += '	<div><input type=checkbox reason="Manual" /><label>手动录像</label></div>';
//				    html += '	<div><input type=checkbox reason="E_IDL_AlertIn" /><label>联动录像（发生报警）</label></div>';
//				    html += '	<div><input type=checkbox reason="E_IV_MotionDetected" /><label>联动录像（侦测到移动）</label></div>';
//				    html += '	<div><input type=checkbox reason="E_IV_SignalLost" /><label>联动录像（视频信号丢失）</label></div>';
//				    html += '	</td></tr>';
//				    html += '	<tr><td></td><td><a class="easyui-linkbutton" style="width:100px;" onclick="_v.query_record_file(\'adv\')" >查询</a></td></tr>';
//				    html += '	</table>';			    
//					html += '    </div>';
					html += '</div>';
					
				}else{
					html += '<div class="easyui-layout" fit="true" style="width:100%;height:auto;" >';
					html += '<div data-options="region:\'north\',border:false" title="查询条件" style="padding:5px;height:150px;">';
				    html += '	<table>';
				    html += '	<tr><td>存储类型:</td><td><input type=radio name="storage_type" id="storage_type_cloud" checked  onclick="_v.storage_type_onchange(true);" /><label for="storage_type_cloud" >云存储</label>&nbsp;<input type=radio  name="storage_type" id="storage_type_local" onclick="_v.storage_type_onchange(false);" /></label for="storage_type_local" >前端存储<label></td></tr>';
				    html += '	<tr><td>开始时间:</td><td><input id="vod_record_begintime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 00:00:00")+'" style="width:148px;"></td></tr>';
				    html += '	<tr><td>结束时间:</td><td><input id="vod_record_endtime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 23:59:59")+'" style="width:148px;"></td></tr>';
				    html += '	<tr><td></td><td><a class="easyui-linkbutton" style="width:80px;" onclick="_v.open_record_file_tab(_lp.frame.vod.record.filelist_title)" >查询</a></td></tr>';
				    html += '	</table>';
				    
					html += '</div>';
					html += '<div data-options="region:\'center\',border:false" title="'+_lp.frame.vod.record.camera_title+'">';
					html += '		<div id="vod_record_cameratree"  fit="true" ></div>';
					html += '</div>';
					html += '</div>';
				}
				
			html += '</div>';
			html += '<div title="'+_lp.frame.vod.snapshot.title+'" >';
		

			html += '<div id="vod_snapshot_accordion" class="easyui-accordion" data-options="" >';
			html += '    <div title="'+_lp.frame.vod.snapshot.fast_index+'" data-options="collapsed:false,collapsible:false" style="overflow:auto;padding:1px;">';
			html += '		<div id="vod_snapshot_cameratree" class="easyui-tree" style="height:400px;" ></div>';
			html += '    </div>';
			
		//	html += '    <div title="'+_lp.frame.vod.snapshot.adv_query+'" data-options="" style="overflow:auto;padding:1px;">';
		//	html += '   </div>';
			html += '</div>';
			
			html += '</div>';
			html += '<div title="'+_lp.frame.vod.event.title+'" >';

		    html += '<table>';
		    html += '<tr><td>开始时间:</td><td><input id="vod_event_begintime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 00:00:00")+'" style="width:148px;"></td></tr>';
		    html += '<tr><td>结束时间:</td><td><input id="vod_event_endtime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 23:59:59")+'" style="width:148px;"></td></tr>';		    
		    html += '<tr><td>事件类型:</td><td id="event_type_container" >';
		    
		    html += '<ul class="easyui-tree">';
		    html += '<li data-options="state:\'closed\'">';
		    html += '<span>Photos</span>';
		    html += '<ul>';
		    html += '<li>';
		    html += '<span>Friend</span>';
		    html += '</li>';
		    html += '<li>';
		    html += '<span>Wife</span>';
		    html += '</li>';
		    html += '<li>';
		    html += '<span>Company</span>';
		    html += '</li>';
		    html += '</ul>';
		    html += '</li>';
		    html += '<li>index.html</li>';
		    html += '<li>about.html</li>';
		    html += '<li>welcome.html</li>';
		    html += '</ul>';
		
		    html += '</td></tr>';
		    html += '<tr><td></td><td><a class="easyui-linkbutton" style="width:100px;"  onclick="_v.open_event_tab(_lp.frame.vod.event.title)"  >查询</a></td></tr>';
		    html += '</table>';	
		    
			html += '</div>';
			html += '<div title="'+_lp.frame.vod.log.title+'" >';

		    html += '<table>';
		    html += '<tr><td>开始时间:</td><td><input id="vod_log_begintime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 00:00:00")+'" style="width:148px;"></td></tr>';
		    html += '<tr><td>结束时间:</td><td><input id="vod_log_endtime" class="easyui-datetimespinner" data-options="showSeconds:true" value="'+P_Utils.DateFormat("yyyy-MM-dd 23:59:59")+'" style="width:148px;"></td></tr>';		    
		    html += '<tr><td>日志类型:</td><td id="log_type_container" >';

		    html += '<div><input type=checkbox logtype="reboot" /><label>重启设备</label></div>';
		    html += '<div><input type=checkbox logtype="settime" /><label>修改时间</label></div>';
		    html += '</td></tr>';
		    html += '<tr><td></td><td><a class="easyui-linkbutton" style="width:100px;" onclick="_v.open_log_tab(_lp.frame.vod.log.title)" >查询</a></td></tr>';
		    html += '</table>';	
		    
			html += '</div>';
		html += '</div>';
		$('#vod_region_west').html(html);
		$('#vod_west_tabs').tabs({
			tabWidth:63,
			showHeader:true
		});
			
		$('#vod_west_tabs').tabs({
		    onSelect:function(title,index){
			    if(title == _lp.frame.vod.record.title){
			     	_v.record_accordion_select(_lp.frame.vod.record.fast_index,0);
			    }
			    if(title == _lp.frame.vod.snapshot.title){
			     	_v.snapshot_accordion_select(_lp.frame.vod.snapshot.fast_index,0);
			    }	
		    }
		});


		//if(_cf.type != "embed"){
		//	$('#vod_west_tabs').tabs('close',_lp.frame.vod.snapshot.title);
			$('#vod_west_tabs').tabs('close',_lp.frame.vod.event.title);
			$('#vod_west_tabs').tabs('close',_lp.frame.vod.log.title);
		//}
		
		$.parser.parse('#vod_west_tabs');
	
	},
	load:function(){

		if(_cf.type == "embed"){
		
			
			$('#vod_record_accordion').accordion({
				onSelect:function(t,i){
					_v.record_accordion_select(t,i);
				}
			});
			
			$('#vod_record_cameratree').tree({
				onDblClick:function(node){
					_v.record_cameratree_onclick(node);
				}
			});

			$('#vod_snapshot_accordion').accordion({
				onSelect:function(t,i){
					_v.snapshot_accordion_select(t,i);
				}
			});
			
			$('#vod_snapshot_cameratree').tree({
				onDblClick:function(node){
					_v.snapshot_cameratree_onclick(node);
				}
			});
			
			_v.record_accordion_select(_lp.frame.vod.record.fast_index,0);
		
		}else{
			_v.storage_type_onchange(true);
		}
		return;	
	},
	// 只有在登录平台的情况下才会有
	storage_type_onchange:function(type){

		var cameralist = new Array(),treenodeId = 0;
		for(var i = 0;i < _f.pulist.length;i++){
			var p = _f.pulist[i];
			if(!type && p.online != 1) continue;
			
			if(!type){
				var flag = false;
				for(var j = 0; j < p.childResource.length;j++){
					var c = p.childResource[j];
					if(c.type.toLowerCase() == "sg"){
						flag = true;
						break;
					}
				}
				if(!flag) continue;	
			}
			//console.log(type,p)
			for(var j = 0; j < p.childResource.length;j++){
				var c = p.childResource[j];
				if(c.type.toLowerCase() == "iv"){
					cameralist.push({id:treenodeId++,text:c.name,"iconCls":"icon_iv",type:"iv",children:[],attributes:{pu:p,name:c.name,status:false,idx:c.idx}});
				}
			}
		}

		$("#vod_record_cameratree").tree({
			checkbox:type,
			data:cameralist
		});
	},
	record_accordion_select:function(title,index){
		var cameralist = new Array(),treenodeId = 0;
		for(var i = 0;i < _f.pulist.length;i++){
			var p = _f.pulist[i];
			for(var j = 0; j < p.childResource.length;j++){
				var c = p.childResource[j];
				if(c.type.toLowerCase() == "iv"){
					cameralist.push({id:treenodeId++,text:c.name,"iconCls":"icon_iv",type:"iv",children:[],attributes:{pu:p,name:c.name,status:false,idx:c.idx}});
				}
			}
		}
		if(title == _lp.frame.vod.record.fast_index){
			
			$("#vod_record_cameratree").tree('loadData',cameralist);
			return;
		}else if(title == _lp.frame.vod.record.adv_query){

	        $('#vod_record_cameracmbgrid').combogrid({
	            //panelWidth:320,
	            //url: 'form5_getdata.php',
	            idField:'id',
	            textField:'text',
	            mode:'local',
	            data:cameralist,showHeader:false,
	            fitColumns:true,
	            columns:[[
	                {field:'id',hidden:true},
	                {field:'text',title:'name',align:'left',width:'100%'}
	            ]],
	            onSelect:function(index,rec){
	            }
	        });
	        
			//$("#vod_record_cameracmb").combobox('loadData',cameralist);
		}
	},
	record_cameratree_onclick:function(node){
		if(node.type == "date"){
			_v.open_record_file_tab(_lp.frame.vod.record.filelist_title,"record");
			return;
		}
		
		// 查询录像日期
		var rv = _p.get_control(_v.connectId,node.attributes.pu.puid,"SG",0,'C_SG_QueryRecordDate',' Idx="'+node.attributes.idx+'" Offset="0" Count="1000" Type="0" ');
		
		var childNodes = new Array();
		if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.Date){
			var date = rv.M.C.Res.Param.Date;
			if(typeof date == "string"){
				//childNodes.push({id:node.id+"_0",text:date});
				var t = new Date();
				t.setTime(parseInt(date)*1000);
				var dd = P_Utils.DateFormat("yyyy-MM-dd",t);
				childNodes.push({id:node.id+"_0",text:dd,type:'date',date:date,attributes:node.attributes});
			}else{
				for(var i = 0;i < date.length;i++){
					var d = date[i];
					var t = new Date();
					t.setTime(d*1000);
					var dd = P_Utils.DateFormat("yyyy-MM-dd",t);
					childNodes.push({id:node.id+"_"+i,text:dd,type:'date',date:d,attributes:node.attributes});
				}
			}
		}
		if(childNodes.length > 0){
			var children = $('#vod_record_cameratree').tree('getChildren',node.target);
			for(var i = 0;i < children.length;i++){
				$('#vod_record_cameratree').tree('remove', children[i].target);
			}
			$('#vod_record_cameratree').tree('append', {
				parent: node.target,
				data: childNodes
			});
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:'没有找到录像文件信息',
                timeout:4000,
                showType:'slide'
            });
		}
	},
		
	snapshot_cameratree_onclick:function(node){
		if(node.type == "date"){
			_v.open_record_file_tab(_lp.frame.vod.snapshot.filelist_title,"snapshot");
			return;
		}
		
		// 查询录像日期
		var rv = _p.get_control(_v.connectId,node.attributes.pu.puid,"SG",0,'C_SG_QueryRecordDate',' Idx="'+node.attributes.idx+'" Offset="0" Count="1000" Type="1" ');
	
		var childNodes = new Array();
		if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.Date){
			var date = rv.M.C.Res.Param.Date;
			if(typeof date == "string"){
				//childNodes.push({id:node.id+"_0",text:date});
				var t = new Date();
				t.setTime(parseInt(date)*1000);
				var dd = P_Utils.DateFormat("yyyy-MM-dd",t);
				childNodes.push({id:node.id+"_0",text:dd,type:'date',date:date,attributes:node.attributes});
			}else{
				for(var i = 0;i < date.length;i++){
					var d = date[i];
					var t = new Date();
					t.setTime(d*1000);
					var dd = P_Utils.DateFormat("yyyy-MM-dd",t);
					childNodes.push({id:node.id+"_"+i,text:dd,type:'date',date:d,attributes:node.attributes});
				}
			}
		}
	
		if(childNodes.length > 0){
			var children = $('#vod_snapshot_cameratree').tree('getChildren',node.target);
			for(var i = 0;i < children.length;i++){
				$('#vod_snapshot_cameratree').tree('remove', children[i].target);
			}
			$('#vod_snapshot_cameratree').tree('append', {
				parent: node.target,
				data: childNodes
			});
		}else{
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:'没有找到图片文件信息',
                timeout:4000,
                showType:'slide'
            });
		}
	},	
	open_record_file_tab:function(tabTitle,type){
		console.log(tabTitle,type)
		var exists = $('#vod_tabs').tabs("exists",tabTitle);
		if(!exists){
			$('#vod_tabs').tabs('add',{
			    title:tabTitle,
			    //content:content,
                href: "template/vod_"+type+"_file.html",
			    fit:true,
			    closable:false,
                onLoad: function (pp) {

	    			if(type == "record"){
	    				$('#'+type+'_file_dr').datagrid({
	            			singleSelect:true,
	            		    columns:[[
	            		        {field:'name',title:_lp.frame.vod.file_list.col.name,width:200},
	            		        {field:'size',title:_lp.frame.vod.file_list.col.size,width:100,formatter:_v.record_file_sizemap},
	            		        {field:'beginTime',title:_lp.frame.vod.file_list.col.beginTime,width:150,formatter:function(v){var dd = new Date();dd.setTime(parseInt(v*1000));return  P_Utils.DateFormat("yyyy-MM-dd HH:mm:ss",dd);}},
	            		        {field:'endTime',title:_lp.frame.vod.file_list.col.endTime,width:150,formatter:function(v){var dd = new Date();dd.setTime(parseInt(v*1000));return  P_Utils.DateFormat("yyyy-MM-dd HH:mm:ss",dd);}},
	            		        {field:'reason',title:_lp.frame.vod.file_list.col.reason,width:400},
	            		        {field:'path',title:_lp.frame.vod.file_list.col.path,width:300},
	            				{field:'op',title:_lp.frame.vod.file_list.col.op,width:200,formatter:_v.record_file_opmap}
	            		    ]]
	            		});
	            		
	            		$('#'+type+'_file_download_dr').datagrid({
	            			singleSelect:true,
	            		    columns:[[
	            		        {field:'handler',title:'handler',width:0,hidden:true},
	            		        {field:'puid',title:'',width:0,hidden:true},
	            		        {field:'path',title:_lp.frame.vod.file_list.col.path,width:0,hidden:true},
	            		        {field:'beginTime',width:0,hidden:true},
	            		        {field:'endTime',width:0,hidden:true},
	            		        {field:'downloadTime',width:0,hidden:true},
	            		        {field:'name',title:_lp.frame.vod.file_list.col.name,width:200},
	            		        {field:'size',title:_lp.frame.vod.file_list.col.size,width:100,formatter:_v.record_file_sizemap},
	            		        {field:'savePath',title:_lp.frame.vod.file_list.col.savePath,width:200},
	            		        {field:'status',title:_lp.frame.vod.file_list.col.status,width:150,formatter:_v.download_record_file_statusmap},
	            		        {field:'downloadLength',title:_lp.frame.vod.file_list.col.downloadLength,width:100,formatter:_v.record_file_sizemap},
	            		        {field:'process',title:_lp.frame.vod.file_list.col.process,width:100,formatter:_v.download_record_file_processmap},
	            		        {field:'op',title:_lp.frame.vod.file_list.col.op,width:400,formatter:_v.download_record_file_opmap}
	            		    ]]
	            		});
	    			}
	    			
	    			if(type == "snapshot"){
	    				$('#'+type+'_file_dr').datagrid({
	            			singleSelect:true,
	            		    columns:[[
	            		        {field:'name',title:_lp.frame.vod.file_list.col.name,width:200},
	            		        {field:'size',title:_lp.frame.vod.file_list.col.size,width:100,formatter:_v.record_file_sizemap},
	            		        {field:'beginTime',title:_lp.frame.vod.file_list.col.beginTime,width:150,formatter:function(v){var dd = new Date();dd.setTime(parseInt(v*1000));return  P_Utils.DateFormat("yyyy-MM-dd HH:mm:ss",dd);}},
	            		        {field:'endTime',title:_lp.frame.vod.file_list.col.endTime,width:150,formatter:function(v){var dd = new Date();dd.setTime(parseInt(v*1000));return  P_Utils.DateFormat("yyyy-MM-dd HH:mm:ss",dd);}},
	            		        {field:'reason',title:_lp.frame.vod.file_list.col.reason,width:400},
	            		        {field:'path',title:_lp.frame.vod.file_list.col.path,width:300},
	            				{field:'op',title:_lp.frame.vod.file_list.col.op,width:200,formatter:_v.snapshot_file_opmap}
	            		    ]]
	            		});
	            		
	            		$('#'+type+'_file_download_dr').datagrid({
	            			singleSelect:true,
	            		    columns:[[
	            		        {field:'handler',title:'handler',width:0,hidden:true},
	            		        {field:'puid',title:'',width:0,hidden:true},
	            		        {field:'path',title:_lp.frame.vod.file_list.col.path,width:0,hidden:true},
	            		        {field:'beginTime',width:0,hidden:true},
	            		        {field:'endTime',width:0,hidden:true},
	            		        {field:'downloadTime',width:0,hidden:true},
	            		        {field:'name',title:_lp.frame.vod.file_list.col.name,width:200},
	            		        {field:'size',title:_lp.frame.vod.file_list.col.size,width:100,formatter:_v.record_file_sizemap},
	            		        {field:'savePath',title:_lp.frame.vod.file_list.col.savePath,width:200},
	            		      //  {field:'status',title:_lp.frame.vod.file_list.col.status,width:150,formatter:_v.download_snapshot_file_statusmap},
	            		        //{field:'downloadLength',title:_lp.frame.vod.file_list.col.downloadLength,width:100,formatter:_v.record_file_sizemap},
	            		//    {field:'process',title:_lp.frame.vod.file_list.col.process,width:100,formatter:_v.download_snapshot_file_processmap}
  	            		  //    {field:'op',title:_lp.frame.vod.file_list.col.op,width:400,formatter:_v.download_snapshot_file_opmap}
	            		    ]]
	            		});
	    			}
	    	
            	
    	        	_v.query_record_file(type);
                }
			});
		}else{
			var tab = $('#vod_tabs').tabs('getSelected');
			var index = $('#vod_tabs').tabs('getTabIndex',tab);
			
			var t2 = $('#vod_tabs').tabs('getTab',tabTitle);
			var index2 = $('#vod_tabs').tabs('getTabIndex',t2);
			
			if(index == index2){
	    		$('#wait_dlg').dialog({
	    			title:_lp.frame.notes.waiting_title1,
	    		    width:280,
	    		    height:100,
	    		    closable:false,
	    		    content:'<div style="width:100%;text-align:center;line-height:40px;">正在查询文件......</div>'
	    		});
	    		
	        	setTimeout(function(){_v.query_record_file(type);},10);
				return;
			}			
			$('#vod_tabs').tabs('select',tabTitle);
		}
	},
	query_record_file:function(type){
		var beginTime = "",endTime="",puid="",idx="",reasons="";
		var files = new Array();
		if(_cf.type == "embed"){

			if(typeof type != "undefined" && type == "adv"){
				beginTime = $('#vod_'+type+'_begintime').datetimespinner('getValue');
				endTime = $('#vod_'+type+'_endtime').datetimespinner('getValue');
				
		    	var g = $('#vod_'+type+'_cameracmbgrid').combogrid("grid");
		    	var rows = g.datagrid("getSelected");
		    	if(row){
		    		puid = row.attributes.pu.puid;
		    		idx = row.attributes.idx;
		    		//cameraName = row.attributes.name 
		    	}
		    	
				var chks = $('#vod_'+type+'_reason_container input[type=checkbox]');
				reasons = "";
				for(var i = 0;i < chks.length;i++){
					reasons = $(chks[i]).attr('reason')+",";
				}
			}else{
				var node = $("#vod_"+type+"_cameratree").tree("getSelected");
				if(node.type == "date"){
		    		puid = node.attributes.pu.puid;
		    		idx = node.attributes.idx;
					beginTime = parseInt(node.date);
					endTime = (parseInt(node.date)+3600*24-1);
				}	
			}
			if(type == "record"){
				var rv = _p.get_control(_v.connectId,puid,"SG",0,'C_SG_QueryRecordFiles',' Idx="'+idx+'" Offset="0" Count="1000" Type="0" Begin="'+beginTime+'" End="'+endTime+'" LogicMode="AND"  ');
			}
			if(type == "snapshot"){
				var rv = _p.get_control(_v.connectId,puid,"SG",0,'C_SG_QueryRecordFiles',' Idx="'+idx+'" Offset="0" Count="1000" Type="1" Begin="'+beginTime+'" End="'+endTime+'" LogicMode="AND"  ');
			}
			
			
			if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.File){
				var f = rv.M.C.Res.Param.File;
				if($.isArray(f)){
					for(var i = 0;i <f.length;i++){
						files.push({type:'local',offset:0,szId:null,puid:puid,idx:idx,name:f[i].Name,size:f[i].Size,beginTime:f[i].Begin,endTime:f[i].End,reason:f[i].Reason,path:f[i].Path});
					}
				}else{
					files.push({type:'local',offset:0,szId:null,puid:puid,idx:idx,name:f.Name,size:f.Size,beginTime:f.Begin,endTime:f.End,reason:f.Reason,path:f.Path});
				}
			}
		}else{

			beginTime = $('#vod_'+type+'_begintime').datetimespinner('getValue');
			endTime = $('#vod_'+type+'_endtime').datetimespinner('getValue');
	    	beginTime = parseInt(P_Utils.DTStrToTimestamp(beginTime).getTime()/1000);
	    	endTime = parseInt(P_Utils.DTStrToTimestamp(endTime)/1000);
	    	if($('#storage_type_local').prop("checked") == true){

				var node = $("#vod_"+type+"_cameratree").tree("getSelected");
				if(node == null) return;
				
	    		puid = node.attributes.pu.puid;
	    		idx = node.attributes.idx;
				if(type == "record"){
					var rv = _p.get_control(_v.connectId,puid,"SG",0,'C_SG_QueryRecordFiles',' Idx="'+idx+'" Offset="0" Count="1000" Type="0" Begin="'+beginTime+'" End="'+endTime+'" LogicMode="AND"  ');
				}
				if(type == "snapshot"){
					var rv = _p.get_control(_v.connectId,puid,"SG",0,'C_SG_QueryRecordFiles',' Idx="'+idx+'" Offset="0" Count="1000" Type="1" Begin="'+beginTime+'" End="'+endTime+'" LogicMode="AND"  ');
				}
			
				if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.File){
					var f = rv.M.C.Res.Param.File;
					if($.isArray(f)){
						for(var i = 0;i <f.length;i++){
							console.log(f[i])
							files.push({type:'local',offset:0,szId:null,puid:puid,idx:idx,name:f[i].Name,size:f[i].Size,beginTime:f[i].Begin,endTime:f[i].End,reason:f[i].Reason,path:f[i].Path});
						}
					}else{
						files.push({type:'local',offset:0,szId:null,puid:puid,idx:idx,name:f.Name,size:f.Size,beginTime:f.Begin,endTime:f.End,reason:f.Reason,path:f.Path});
					}
				}
				
	    	}else{

		    	var rows = $('#vod_'+type+'_cameratree').tree("getChecked"),xmlOSets = "";
		    	xmlOSets += "<OSets>";
		    	for(var i = 0;i < rows.length;i++){
		    		xmlOSets += '<Res OType="201" OID="'+rows[i].attributes.pu.puid+'" Type="IV" Idx="'+rows[i].attributes.idx+'"  />';	    		
		    	}
		    	xmlOSets += "</OSets>";
		    	if(type == "record"){
					var rv = _p.get_control(_v.connectId,"","ST",0,'C_CSS_QueryStorageFiles',' Offset="0" Count="200" Begin="'+beginTime+'" End="'+endTime+'" Type="0" ',xmlOSets);
				}
				if(type == "snapshot"){
					var rv = _p.get_control(_v.connectId,"","ST",0,'C_CSS_QueryStorageFiles',' Offset="0" Count="200" Begin="'+beginTime+'" End="'+endTime+'" Type="1" ',xmlOSets);
				}
			

				if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.File){
					var f = rv.M.C.Res.Param.File;
					if($.isArray(f)){
						for(var i = 0;i <f.length;i++){
							files.push({type:'cloud',offset:0,szId:f[i].ID,name:f[i].Name,size:f[i].Size,beginTime:f[i].Begin,endTime:f[i].End,reason:f[i].Reason,path:f[i].Path,puid:f[i].PUID,idx:0});
						}
					}else{
						files.push({type:'cloud',offset:0,szId:f.ID,name:f.Name,size:f.Size,beginTime:f.Begin,endTime:f.End,reason:f.Reason,path:f.Path,puid:f.PUID,idx:0});
					}
				}
	    	}
		}
		$('#'+type+'_file_dr').datagrid("loadData",files);
		$('#wait_dlg').dialog('close');
		
	},
	record_file_sizemap:function(v,r,i){
		if(v > 0){
		
			if(v <1024){
		　　	return v+"(B)";
		　　}else if(v <(1024*1024)){
		　　	return (parseInt(v)/1024).toFixed(2)+"(KB)";
		　　}else{
		　　	return (parseInt(v)/(1024*1024)).toFixed(2)+"(MB)";
	　　	}
	 	}
	
		
	},
	record_file_opmap:function(v,r,i){
		var btns = '';
		btns += '<a href="javascript:void(0)" onclick="_v.download_record_file(\''+i+'\',\''+r.puid+'\')">下载</a>&nbsp;<a href="javascript:void(0)" onclick="_v.replayer_record_file(\''+i+'\')">回放</a>';
		//btns += '<a href="javascript:void(0)" onclick="_v.download_record_file(\''+i+'\',\''+r.puid+'\')">下载</a>';
		return btns;
	},
		
	snapshot_file_opmap:function(v,r,i){
		var btns = '';
		btns += '<a href="javascript:void(0)" onclick="_v.download_snapshot_file(\''+i+'\',\''+r.puid+'\')">下载</a>';
		return btns;
	},		
	
	vod_wnd_opened:false,
	replayer_record_file:function(rowIndex){
		
		var rows = $('#record_file_dr').datagrid('getRows');
		if(rows[rowIndex]){
			//$("#replayer_wnd").window();
			//$('#replayer_wnd').window('close');
		
			var windowId = "replayer_"+rowIndex;
			var windowTbarId = "replayer_tb_"+rowIndex;
			if(_v.vod_wnd_opened){
				$('#replayer_wnd').window('close');
			}
			$('#replayer_wnd').window({
				width:420,
				height:395,
				title:rows[rowIndex].name,
				href:'template/replayer_wnd.html',
				minimizable:false,
				maximizable:true,
				collapsible:false,
				onLoad:function(){
					if($('#replayerwnd').length > 0){
						$('#replayerwnd').attr("id",windowId);
						$('#repayerwnd_tb').attr("id",windowTbarId);
					}
					$(this).window('resize',{width:420,height:395});
					//$('#'+windowTbarId+" .easyui-slider").slider('setValue',200);					
					//return;
					_v.play_vod(rowIndex);
				},
				onOpen:function(){
					_v.vod_wnd_opened = true;
				},
				onClose:function(){
					_v.vod_wnd_opened = false;
					//alert(rowIndex+","+windowId)
					_v.stop_vod(rowIndex,windowId);
				},
				
				onResize:function(width,height){
					if(width<400 ){
						$(this).window('resize',{width:420,height:height});
						return;
					}
					if(height<395){
						$(this).window('resize',{width:width,height:395});
						return;
					}
					$(this).find(".windowbox").height(height-72);
					$(this).find(".windowbox").width(width-16);
					$(this).find(".windowtoolbar").width(width-15);
						
					$(this).find(".easyui-slider").slider({
						width:(width-170-40),
						showTip:false
					});
				}
			});
		}

	},
	play_vod:function(rowIndex){
		
		var rows = $('#record_file_dr').datagrid('getRows');
		if(rows[rowIndex]){
			var windowId = "replayer_"+rowIndex;
			var windowTbarId = "replayer_tb_"+rowIndex;
			
			var f = rows[rowIndex];
			
			if($("#"+windowId)){
				if(typeof f.replayerHandler != "undefined" && f.replayerHandler != null){
					_p.resume_vod(_v.connectId,windowId);
					$('#'+windowTbarId+" .player_icon_play").hide();
					$('#'+windowTbarId+" .player_icon_pause").show();
					return;
				}
					
				_v.stop_vod(rowIndex,windowId);
				$("#"+windowId+'_wnd').css("visibility","visible");
				var rv = _p.play_vod(_v.connectId,windowId,{type:($('#storage_type_cloud').prop("checked") == true? "cloud" : "local"),szId:f.szId,puid:f.puid,beginTime:f.offset,endTime:f.endTime,path:f.path,name:f.name,speed:0,direction:1,duration:0});
				console.log(rv)
				if(rv.rv !== 0){
					$('#'+windowTbarId+" .player_icon_play").show();
					$('#'+windowTbarId+" .player_icon_pause").hide();
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:'点播失败，errorcode:'+rv.rv,
		                timeout:4000,
		                showType:'slide'
		            });
				}else{
					$('#'+windowTbarId+" .easyui-slider").slider({disabled:false});
					_v.vod_status = true;
					f.replayerHandler = rv.response;
					//_p.stop_vod_audio(_v.connectId,windowId);
					//_p.play_vod_audio(_v.connectId,windowId);
					_v.vodList.push(f);
					var total = parseInt(f.endTime-f.beginTime);
					//
					var t = _v.format_secord(f.offset)+"/"+_v.format_secord(total);
					$("#"+windowTbarId+" .playtime").html(t);

					try{
		
						$('#'+windowTbarId+" .player_icon_stop").unbind().bind("click",function(){
							//console.log(rowIndex,windowId);
							_v.fire_stop_vod(windowId);return;
							
							_v.stop_vod(rowIndex,windowId);
							f.offset = 0;
							$('#'+windowTbarId+" .easyui-slider").slider("setValue",0);
							$('#'+windowTbarId+" .player_icon_play").show();
							$('#'+windowTbarId+" .player_icon_pause").hide();
						});

						$('#'+windowTbarId+" .player_icon_play").unbind().bind("click",function(){
							_v.play_vod(rowIndex);
						});
						
						$('#'+windowTbarId+" .player_icon_pause").unbind().bind("click",function(){
							_p.pause_vod(_v.connectId,windowId);
							//_v.stop_vod(windowId);
							//var offset = $('#'+windowTbarId+" .easyui-slider").slider("getValue");
							//f.offset = offset;
							$('#'+windowTbarId+" .player_icon_play").show();
							$('#'+windowTbarId+" .player_icon_pause").hide();
						});

						$('#'+windowTbarId+" .player_icon_play").hide();
						$('#'+windowTbarId+" .player_icon_pause").show();

					}
					catch(e){
						
					}
					$('#'+windowTbarId+" .easyui-slider").slider({
						"max":total,
						"min":0,
						"onComplete":function(newValue,oldValue){
							var rows = $('#record_file_dr').datagrid('getRows');
							if(!rows[rowIndex]) return;
							
							if(typeof f.replayerHandler != "undefined" && f.replayerHandler != null){
								_v.stop_vod(rowIndex,windowId);
								f.offset = newValue;
								$('#'+windowTbarId+" .easyui-slider").slider({disabled:true});
								
								var rv = _v.play_vod(rowIndex,windowId);
								return;
							}
							
						}
						/*,
						"onChange":function(newValue,oldValue){
							var t = _v.format_secord(newValue)+"/"+_v.format_secord(parseInt(total));
							$("#"+windowTbarId+" .playtime").html(t);
							
						},
						onSlideStart:function(value){
							//var opts = $(this).slider("options");
							//opts.showTip = true;
							$('#'+windowTbarId+" .player_icon_pause").trigger('click');
						},
						onSlideEnd:function(value){
						}*/
					});
				}
			}
		}
	},
		
	fire_stop_vod_audio:function(windowId){
		_p.stop_vod_audio(_v.connectId,windowId);
	},
	fire_stop_vod:function(windowId){
		
		var rowIndex = windowId.split('_')[1];
		
		var rows = $('#record_file_dr').datagrid('getRows');
		if(!rows[rowIndex]) return;
						
		_v.stop_vod(rowIndex,windowId);
		//var windowId = "replayer_"+rowIndex;
		var windowTbarId = "replayer_tb_"+rowIndex;
		$('#'+windowTbarId+" .easyui-slider").slider("setValue",0);
		$('#'+windowTbarId+" .player_icon_play").show();
		$('#'+windowTbarId+" .player_icon_pause").hide();
		rows[rowIndex].offset = 0;
		
	},
	stop_vod:function(rowIndex,windowId){
		var rows = $('#record_file_dr').datagrid('getRows');
		if(!rows[rowIndex]) return;
		
		var f = rows[rowIndex];
		_p.stop_vod(_v.connectId,windowId);
		f.replayerHandler = null;
		$("#"+windowId+'_wnd').css("visibility","hidden");
		$("#"+windowId.replace("replayer_","replayer_tb_")+" .playtime").html("00:00/00:00");
		_v.vodList.splice( $.inArray(f, _v.vodList), 1 );
	},
	replayer_update_status:function(status){
		var rows = $('#record_file_dr').datagrid('getRows');
		for(var i =0;i < rows.length;i++){
			var f = rows[i];
			if(f.replayerHandler == status._HANDLE){
				//console.log(status)
				var windowId = "replayer_"+i;
				var windowTbarId = "replayer_tb_"+i;
				if(status.keyData == undefined || status.keyData.play_time == undefined) status.keyData.play_time = "0";
				
				//var t = _v.format_secord(status.keyData.play_time)+"/"+_v.format_secord(parseInt(f.endTime-f.beginTime));
				var b = f.offset + parseInt(status.keyData.play_time);
				//var t = _v.format_secord(b)+"/"+_v.format_secord(parseInt(f.endTime-f.beginTime));
				//$("#"+windowTbarId+" .playtime").html(t);
				try{
					$('#'+windowTbarId+" .easyui-slider").slider("setValue",b);
				}catch(e){
					//console.log(e)
				}
				break;
			}
		}
	},
	download_record_file:function(rowIndex,puid){
		var rows = $('#record_file_dr').datagrid('getRows');
		// 插入到下载列表
		if(rows[rowIndex]){
			var f = rows[rowIndex];
			var rv = _p.download_record_file(_v.connectId,f.type,f.szId,f.puid,f.path+f.name,f.name,f.idx);
			if(rv.rv == 0){
				$('#record_file_download_dr').datagrid('appendRow',{
					path:f.path,
					name:f.name,
					puid:f.puid,
					idx:f.idx,
					type:f.type,
					size:f.size,
					szId:f.szId,
					offset:0,
					beginTime:f.beginTime,
					endTime:f.endTime,
					savePath:'C:/TSLFile/Download',
					handler:rv.response,
					status:'0',
					process:0
				});
			}else{
				// stop
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:'下载失败，errorcode:'+rv.rv,
	                timeout:4000,
	                showType:'slide'
	            });
			}
		}
	},
		
	download_snapshot_file:function(rowIndex,puid){
		var rows = $('#snapshot_file_dr').datagrid('getRows');
	//	
		// 插入到下载列表
		if(rows[rowIndex]){
			var f = rows[rowIndex];
		
			var rv = _p.download_snapshot_file(_v.connectId,f.type,f.szId,f.puid,f.beginTime,f.path+f.name,f.name,f.idx);
			console.log(rv)
			if(rv.rv == 0){
				$('#snapshot_file_download_dr').datagrid('appendRow',{
					path:f.path,
					name:f.name,
					puid:f.puid,
					idx:f.idx,
					type:f.type,
					size:f.size,
					szId:f.szId,
					offset:0,
					beginTime:f.beginTime,
					endTime:f.endTime,
					savePath:'C:/TSLFile/Download',
					handler:rv.response,
					status:'0',
					process:0
				});
			}else{
				// stop
	            $.messager.show({
	                title:_lp.frame.configsets.notes.noteTitle,
	                msg:'下载失败，errorcode:'+rv.rv,
	                timeout:4000,
	                showType:'slide'
	            });
			}
		}
	},	
	re_download_record_file:function(puid,name,path){
		var rows = $('#record_file_download_dr').datagrid('getRows');
		for(var i = 0;i < rows.length;i++){
			var r = rows[i];
			if(r.puid == puid && name == r.name && path == r.path){
				_v.stop_download_record_file(i);
				var rv = _p.download_record_file(_v.connectId,r.type,r.szId,puid,path+name,name,r.idx);
				if(rv.rv == 0){
					$('#record_file_download_dr').datagrid('updateRow',{
						index: i,
						row: {
							savePath:'C:/TSLFile/Download',
							handler:rv.response,
							status:'0',
							process:0
						}
					});
				}else{
					// stop
		            $.messager.show({
		                title:_lp.frame.configsets.notes.noteTitle,
		                msg:'下载失败，errorcode:'+rv.rv,
		                timeout:4000,
		                showType:'slide'
		            });
				}
				break;
			}
		}
	},
	stop_download_record_file:function(rowIndex){
		var rows = $('#record_file_download_dr').datagrid('getRows');
		// 插入到下载列表
		if(rows[rowIndex]){
			var f = rows[rowIndex];
			var rv = _p.stop_download_record_file(_v.connectId,f.type,f.handler);
			console.log(rv)
			if(rv.rv == "0"){
				// 停止下载
				
				$('#record_file_download_dr').datagrid('updateRow',{
					index: rowIndex,
					row: {
						status:-1,
						handler:-1
					}
				});
				
			}
				
		}	
	},
	download_snapshot_file_statusmap:function(v,r,i){
	　　if(v == 0){
	　　return '正在缓冲';
	　　}else if(v == 2){
	　　return '正在下载';
	　　}else if(v == 4){
	　　return '完成';
	　　}else if(v == -1){
	　　return '流断开';
	　　}else{
	　　return '';
	　　}
　　},	
		
	download_record_file_statusmap:function(v,r,i){
		if(v == 0){
			return '正在缓冲';
		}else if(v == 2){
			return '正在下载';
		}else if(v == 4){
			return '完成';
		}else if(v == -1){
			return '流断开';
		}else{
			return '';
		}
	},
	download_record_file_opmap:function(v,r,i){
		var btns = '';
		if(r.status == -1 || r.status == 4){
			btns += '<a href="javascript:void(0)" onclick="_v.del_download_record_row(\''+i+'\')">删除下载记录</a>&nbsp;';
			btns += '<a href="javascript:void(0)" onclick="_v.re_download_record_file(\''+r.puid+'\',\''+r.name+'\',\''+r.path+'\')">重新下载</a>&nbsp;';
		}
		
		if(r.status == 0 || r.status == 2 ){
			btns += '<a href="javascript:void(0)" onclick="_v.stop_download_record_file(\''+i+'\',\''+r.handler+'\')">停止</a>&nbsp;';
		}
		return btns;
	},
		
	download_snapshot_file_opmap:function(v,r,i){
		var btns = '';
		if(r.status == -1 || r.status == 4){
			btns += '<a href="javascript:void(0)" onclick="_v.del_download_record_row(\''+i+'\')">删除下载记录</a>&nbsp;';
			btns += '<a href="javascript:void(0)" onclick="_v.re_download_record_file(\''+r.puid+'\',\''+r.name+'\',\''+r.path+'\')">重新下载</a>&nbsp;';
		}
		
		if(r.status == 0 || r.status == 2 ){
			btns += '<a href="javascript:void(0)" onclick="_v.stop_download_record_file(\''+i+'\',\''+r.handler+'\')">停止</a>&nbsp;';
		}
		return btns;
	},	
	download_record_file_processmap:function(v,r,i){	
		if(r.status == 4){			
			return "100%";
		}else if(r.status == 2){
			var total = parseInt(r.endTime)-parseInt(r.beginTime);
			var p = parseInt(r.downloadTime/total*100);
			if(p >= 100) p = 100;
			return p+"%";	
		}else if(r.status == 0){
			return "0%";
		}else{
			return "";
		}
	},
	del_download_record_row:function(rowIndex){
		$('#record_file_download_dr').datagrid('deleteRow',rowIndex);
	},
	notify_downloadfile:function(notify){
		if(notify.eventName != "stream_status_notify" ){
			return;
		}
		try{

			var downloadLength = notify.keyData.download_length;
		//	console.log(downloadLength)
			var downloadTime = notify.keyData.download_time;
			var rows = $('#record_file_download_dr').datagrid('getRows');
			var type = "local";
			var flag = false;
			
			for(var i = 0;i < rows.length;i++){
				var r = rows[i];
				type = r.type;
				if(r.handler == notify._HANDLE){
					$('#record_file_download_dr').datagrid('updateRow',{
						index: i,
						row: {
							status:notify.status,
							downloadLength:downloadLength,	
							downloadTime:downloadTime,
							handler:(notify.status == 4? '':r.handler)
						}
					});
					
					flag = true;
	
					break;
				}
			}
	
			if(notify.status == -1 || notify.status == 4){
				
				_p.stop_download_record_file(_v.connectId,r.type,notify._HANDLE);
			}
			
			if(flag == false){
				
				for(var i = 0;i < _v.vodList.length;i++){
					var v = _v.vodList[i];
					
					if(typeof v.replayerHandler != "undefined" && v.replayerHandler == notify._HANDLE){
						
						if(notify.status == -1 || notify.status == 4){
							//console.log(v);
						}else{
							_v.replayer_update_status(notify);
						}
					}
				}
			}
		}catch(e){
			
		}finally{
			return;
		}
	},
	snapshot_accordion_select:function(title,index){
		if(title == _lp.frame.vod.record.fast_index){
			var cameralist = new Array(),treenodeId = 0;
			for(var i = 0;i < _f.pulist.length;i++){
				var p = _f.pulist[i];
				for(var j = 0; j < p.childResource.length;j++){
					var c = p.childResource[j];
					if(c.type.toLowerCase() == "iv"){
						cameralist.push({id:treenodeId++,text:c.name,"iconCls":"icon_iv",type:"iv",children:[],attributes:{pu:p,name:c.name,status:false,idx:c.idx}});
					}
				}
			}
			$("#vod_snapshot_cameratree").tree('loadData',cameralist);
			return;
		}
	},
	open_event_tab:function(tabTitle){
		var exists = $('#vod_tabs').tabs("exists",tabTitle);
		if(!exists){
			$('#vod_tabs').tabs('add',{
			    title:tabTitle,
			    //content:content,
                href: "template/vod_event.html",
			    fit:true,
			    closable:false,
                onLoad: function (pp) {
    	        	_v.query_events();
                }
			});
		}else{
			var tab = $('#vod_tabs').tabs('getSelected');
			var index = $('#vod_tabs').tabs('getTabIndex',tab);
			
			var t2 = $('#vod_tabs').tabs('getTab',tabTitle);
			var index2 = $('#vod_tabs').tabs('getTabIndex',t2);
			
			if(index == index2){
	    		$('#wait_dlg').dialog({
	    			title:_lp.frame.notes.waiting_title1,
	    		    width:280,
	    		    height:100,
	    		    closable:false,
	    		    content:'<div style="width:100%;text-align:center;line-height:40px;">正在查询事件信息......</div>'
	    		});
	    		
	        	setTimeout(function(){_v.query_events();},10);
				return;
			}			
			$('#vod_tabs').tabs('select',tabTitle);
		}
	},
	query_events:function(){
		
		var beginTime = "",endTime="";

		beginTime = $('#vod_log_begintime').datetimespinner('getValue');
		endTime = $('#vod_log_endtime').datetimespinner('getValue');

    	var g = $('#pulist_cmb').combogrid("grid");
    	var row = g.datagrid("getSelected");
		
		var rv = _p.get_control(_v.connectId,row.puid,"SG",0,'C_SG_QueryEvent',' Idx="0" Offset="0" Cnt="200" Type="0" BeginTime="'+beginTime+'" EndTime="'+endTime+'"  ');
		var files = new Array();
		if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.Event){
			var f = rv.M.C.Res.Param.Event;
			if($.isArray(f)){
				for(var i = 0;i <f.length;i++){
					files.push({id:f[i].ID,time:f[i].Time,level:f[i].Level});
				}
			}else{
				files.push({id:f[i].ID,time:f[i].Time,desc:f[i].Level});
			}
		}
		$('#event_dr').datagrid("loadData",files);
		$('#wait_dlg').dialog('close');
	},
	open_log_tab:function(tabTitle){
		var exists = $('#vod_tabs').tabs("exists",tabTitle);
		if(!exists){
			$('#vod_tabs').tabs('add',{
			    title:tabTitle,
			    //content:content,
                href: "template/vod_logs.html",
			    fit:true,
			    closable:false,
                onLoad: function (pp) {
    	        	_v.query_logs();
                }
			});
		}else{
			var tab = $('#vod_tabs').tabs('getSelected');
			var index = $('#vod_tabs').tabs('getTabIndex',tab);
			
			var t2 = $('#vod_tabs').tabs('getTab',tabTitle);
			var index2 = $('#vod_tabs').tabs('getTabIndex',t2);
			
			if(index == index2){
	    		$('#wait_dlg').dialog({
	    			title:_lp.frame.notes.waiting_title1,
	    		    width:280,
	    		    height:100,
	    		    closable:false,
	    		    content:'<div style="width:100%;text-align:center;line-height:40px;">正在查询日志信息......</div>'
	    		});
	    		
	        	setTimeout(function(){_v.query_logs();},10);
				return;
			}			
			$('#vod_tabs').tabs('select',tabTitle);
		}
	},
	query_logs:function(){
		
		var beginTime = "",endTime="";

		beginTime = $('#vod_log_begintime').datetimespinner('getValue');
		endTime = $('#vod_log_endtime').datetimespinner('getValue');

    	var g = $('#pulist_cmb').combogrid("grid");
    	var row = g.datagrid("getSelected");
    	beginTime = parseInt(P_Utils.DTStrToTimestamp(beginTime).getTime()/1000);
    	endTime = parseInt(P_Utils.DTStrToTimestamp(endTime)/1000);
		var rv = _p.get_control(_v.connectId,row.puid,"SG",0,'C_SG_QueryUserLog',' Idx="0" Offset="0" Cnt="200" Type="0" BeginTime="'+beginTime+'" EndTime="'+endTime+'"  ');
		var files = new Array();
		if(rv.M && rv.M.C && rv.M.C.Res && rv.M.C.Res.Param && rv.M.C.Res.Param.UserLog){
			var f = rv.M.C.Res.Param.UserLog;
			if($.isArray(f)){
				for(var i = 0;i <f.length;i++){
					files.push({id:f[i].ID,time:f[i].Time,desc:f[i].Desc});
				}
			}else{
				files.push({id:f.ID,time:f.Time,desc:f.Desc});
			}
		}
		$('#log_dr').datagrid("loadData",files);
		$('#wait_dlg').dialog('close');
	},
	format_secord:function(sec){
		
		if(parseInt(sec) > 0){
			if(sec <60){
				return (sec > 9 ? "00:"+sec : "00:0"+sec);
			}else if(sec < 3600){
				var m = parseInt(sec/60);
				var s = (sec%60);
				return (m > 9 ? m : "0"+m)+":"+(s > 9 ? s : "0"+s);
			}else{
				var h = parseInt(sec/3600);// 不可以转为小时的秒数
				var mm = (sec%3600);// 不可以转为小时的秒数
				var s = (mmm%60);
				var m = parseInt(mm/60);
				return h+":"+(m > 9 ? m : "0"+m)+":"+(s > 9 ? s : "0"+s);
			}
		}else{
			return "00:00";
		}
	}
}