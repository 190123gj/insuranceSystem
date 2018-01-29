var _f={
	curPU:null,
	pulist:new Array(),
	bbol_pulist:new Array(),
	connectId:null,
	log:function(msg){
	},
	errors:new Array(),
	
	// 初始化页面框架
	init:function(){
		
		$.ajax({
		  	url: 'conf/ErrorExplain.xml',
		  	//dataType: 'xml',
		  	success: function(data){
			  	//var xmldoc = _f.load_xml(data);
			  	//console.log(data)
			 	$(data).find("ErrorCode").each(function(index, ele) {
			 		//console.log($(ele).attr("Number"));
			 		//console.log($(ele).find('zh-CN').text());
			 		_f.errors[$(ele).attr("Number")]  = $(ele).find('zh-CN').text();
			 		
				});
				//console.log(_f.errors);
			}
		});
//		$.ajax({
//		  	url: 'conf/ErrorExplain.xml',
//		  	//dataType: 'xml',
//		  	success: function(data){alert(data)
//			  	//var xmldoc = _f.load_xml(data);
//			  	console.log(data)
////			 	$(data).find("channel").find("item").each(function(index, ele) {
////					var titles = $(ele).find("title").text();
////					var links = $(ele).find("link").text();
////					console.log(titles+'-----');
////					$("#noticecon").find('ol').append('<li><a href="'+links+'">'+titles+'</a></li>');
////				});
//			}
//		});
		
//		var x = '<?xml version="1.0" encoding="UTF-8"?><Res Type="ST" Idx="0" OptID="F_ST_CurrentDNSSets" Stream="" Error="0"><Param><it>114.114.114.114</it><it>115.114.114.114</it></Param><Param><it>116.114.114.114</it><it>117.114.114.114</it></Param><Attr Setable="0" Refresh="1" Type="list" Name="当前使用的DNS列表" /></Res>';
//		
//		var res = $(x).find("Res");
//		console.log(res);
//		for(var i = 0;i < res.length;i++){
//			 console.log(res[i],$(res[i]));
//		}
		//$.base64.utf8encode = true;
		//console.log($.base64);
		//var a = $.base64.btoa("110088aABBCCDD国人人有在");
		//var b = $.base64.atob("5Zu95Lq65Lq65Lq65Lq66KaB",true);
		//console.log(a,$.base64.atob(a,true),b);
		
		document.title = (_cf.title.toString().length > 0 ? _cf.title : _lp.title);

		var params = location.search.substring(1);
	
		params = decodeURIComponent(params).split("&");
				
		for(var i = 0;i < params.length;i++){
			var p = params[i].split("=");
			if(p[0] == "epId"){
				_cf.autoLogin = true;
				_cf.loginParam.epId = p[1];
				
			}else if(p[0] == "ip"){
				_cf.loginParam.ip = p[1];
				
			}else if(p[0] == "port"){
				_cf.loginParam.port = p[1];
				
			}else if(p[0] == "username"){
				_cf.loginParam.username = p[1];
			}else if(p[0] == "password"){
				_cf.loginParam.password = p[1];
			}else if(p[0] == "bFix"){
				_cf.bFix = p[1];
			}else if(p[0] == "type"){
				_cf.type = p[1];
			}
		
		}
		
		if(_cf.type == "embed" || _cf.type == "decode"){

			_cf.loginParam.ip = window.location.hostname;
			_cf.loginParam.port = window.location.port;
			
			if(window.location.origin && window.location.origin.search("file") > -1){
				_cf.loginParam.ip = "";
				_cf.loginParam.port = "";
			}
							

//		    _cf.loginParam.ip = "172.25.8.19";
//		    _cf.loginParam.ip = "172.25.8.107";
//			_cf.loginParam.ip = "58.215.19.180";

//	   		 _cf.loginParam.ip = "58.241.160.250";
//		    _cf.loginParam.ip = "111.11.29.68";
			_cf.loginParam.ip = "172.16.2.3";

			_cf.loginParam.port = 9988;
     		_cf.loginParam.epId = "system";
     		_cf.loginParam.username = "test";
		}else if(_cf.type == "external"){
			_cf.defaultTabIndex = 2;
		}else{
			//_cf.defaultTabIndex = 2;
			_cf.loginParam.ip = "127.0.0.1";
			_cf.loginParam.port = 9988;
		}
		
		if(_cf.type != "external"){
			if($('#time_span')){
				setInterval(function(){
					var t = P_Utils.DateFormat("yyyy年MM月dd日HH时mm分ss秒 ")+_lp.week[P_Utils.DateFormat("ddd")];
					$('#time_span').html(t);
				},1000);
			}	
		}
		
		// 1. 初始化插件plugin对象
        if (typeof myplugin && typeof myplugin.init == "function") {
            if (!_p.init(_cf.type)) {
            	alert("init my plugin error")
    			_f.log({fn:"_f.init",msg:"init my plugin error"});
                //return;
            }
        }
        else {
			_f.log({fn:"_f.init",msg:"my plugin object no exists"});
            //return false;
        }
    		
        _f.init_frame_tabs();
		_f.init_frame_north();

		$("#frame_north_pulist").panel("close");
		$('#frame_north_menus').panel("close");
		
		// 打开登录框
		_f.open_login_dlg();
        if (!$.support.leadingWhitespace) {
            $.messager.show({
                title:_lp.frame.configsets.notes.noteTitle,
                msg:_lp.frame.notes.support_title,
                timeout:10000,
                showType:'slide'
            });
        }
		$('#about_btn').bind('mouseover',function(){
			$('#ver_span').html("|&nbsp;插件版本:"+P_LY.Plug.getVer()+"&nbsp;网页版本:"+_cf.webcu_ver);
			$('#ver_span').show();
		});
		$('#about_btn').bind('mouseout',function(){
			$('#ver_span').html("");
			$('#ver_span').hide();
		});
		
		$.extend($.fn.validatebox.defaults.rules, {
		    ipport: {
		        validator: function(value,param){
		        	var patn = /^([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\.]([0-9]|[0-9][0-9]|[1][0-9][0-9]|[2][0-5][0-5])[\:]([0-9]{1,5})$/ ;
		            return patn.test(value);
		        },
		        message: '输入的地址和端口格式不正确，请重新输入'
		    }
		});

	},
	unload: function () {
		// 页面关闭前,必须调用exit释放SDK插件的对象
        if (typeof myplugin == "object" && typeof myplugin.exit == "function") {
        	myplugin.exit();
        }
        return true;
    },
    exit:function(){
    	_f.unload();
    	window.location.reload();
    },
    view_ver:function(){
    	
    	return;
		//var h = "<div>插件版本:"+P_LY.Plug.getVer()+"</div><div>网页版本:"+_cf.webcu_ver+"</div>";
		var h = "<table style='text-align:center;width:100%'><tr><td rowspan='3'><img src='themes/icons/logo.png' /></td><td>名称:</td><td>WebCU</td></tr><tr><td>插件版本:</td><td>"+P_LY.Plug.getVer()+"</td></tr><tr><td>网页版本:</td><td>"+_cf.webcu_ver+"</td></tr></table>";
		//$('#ver_span').html(h);
		//var tab = $('#frame_tabs').tabs('getSelected');
		//var index = $('#frame_tabs').tabs('getTabIndex',tab);
		//$('#ver_span').html("插件版本:"+P_LY.Plug.getVer()+",网页版本:"+_cf.webcu_ver);
		//$('#ver_span').fadeIn(2000);
    	$('#version_dlg').dialog({
    		title:'关于',
    	    width:280,
    	    iconCls:'icon-help',
    	    height:150,
    	    modal:true,
    	    buttons:[{
				text:'关闭',
				handler:function(){
					$('#version_dlg').dialog('close');
				}
			}],
    		content:h
    	});
    },
    submit_login:function(){
    	$('#login_dlg_form_remember').focus();
    	$('#login_dlg_form_pwd').focus();

    	_f.login();
    },
	open_login_dlg:function(){
		var loginParam = $.cookie("loginParam");

    	var html = '';
    	html += '<form id="login_dlg_form" method="post" novalidate onkeydown="if(event.keyCode==13){_f.submit_login();return false;}" ><input type=hidden id="login_dlg_form_username" name="username" /><input type=hidden id="login_dlg_form_password" name="password" />';
    	html += '<div style="margin-bottom:10px">';
    	html += '<input name="uid" id="login_dlg_form_uid" tabindex=1 class="easyui-textbox" style="width:100%;height:28px;padding:12px" data-options="require:true,prompt:\''+_lp.frame.login.uid.fieldLabel+'\',iconCls:\'icon-man\',iconWidth:38">';
    	html += '</div>';
    	html += '<div style="margin-bottom:10px">';
        html += '<input name="pwd" id="login_dlg_form_pwd" tabindex=2  class="easyui-textbox" type="password" style="width:100%;height:28px;padding:12px" data-options="prompt:\'\',iconCls:\'icon-lock\',iconWidth:38">';
        html += '</div>';
        
        html += '<div style="margin-bottom:10px">';
        html += '<input id="login_dlg_form_remember" type="checkbox" style="vertical-align:middle;margin-top:0;">';
        html += '<span><label for="login_dlg_form_remember">'+_lp.frame.login.remember.boxLabel+'<label></span><span style="float:right;"><a href="plugin/iCVS2.0Plugin.msi" target="_blank" class="download_a" >'+_lp.frame.login.btn_download+'</a></span>';
        
        html += '</div>';
        
        html += '<div style="margin-bottom:0px">';
        html += '<a id="login_btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" style="padding:2px 0px;width:100%;" onclick="_f.login()">';
        html += '<span style="font-size:14px;">'+_lp.frame.login.btn_login+'</span>';
        html += '</a>';
        html += '</div>';
        
        html += '<div id="login_detail_box" style="display:none;">';
        
    	html += '<div style="margin-top:10px">';
    	html += '<input name="ip" id="login_dlg_form_ip" class="easyui-textbox"  data-options="prompt:\''+_lp.frame.login.ip.fieldLabel+'\'" style="width:60%;height:28px;padding:12px"  value=""><label>:</label>';    	
    	html += '<input name="port" id="login_dlg_form_port" class="easyui-textbox"  data-options="prompt:\''+_lp.frame.login.port.fieldLabel+'\'"  style="width:38%;height:28px;padding:12px" value="">';
        html += '</div>';

    	html += '<div style="margin-top:10px">';
    	html += '<input name="epId" id="login_dlg_form_epId"  class="easyui-textbox" style="width:60%;height:28px;padding:12px" data-options="prompt:\''+_lp.frame.login.epId.fieldLabel+'\',iconWidth:38"><label>&nbsp;</label><input type=checkbox id="login_dlg_form_bfix"  style="vertical-align:middle;"/><label for="login_dlg_form_bfix" >'+_lp.frame.login.bfix.fieldLabel+'</label>';
    	html += '</div>';    	
    	html += '</div>';        
        html += '<div id="expand_login_detail_btn_container" style="margin-top:4px;text-align:right;">';
        html += '<a id="expand_login_detail_btn" class="easyui-linkbutton" data-options="plain:true,iconCls:\'panel-tool-expand\'" onclick="_f.expand_login_detail()"  ></a>';
        html += '</div>';
        html += '</form>';
        html += '<div id="wait_dlg"></div>';
        
    	$('#login_dlg').dialog({
    		title:_lp.frame.login.title,
    	    width:300,
    	    iconCls:'icon-man',
    	    height:220,
    	    style:'padding:6px',
    	    modal:false,
    	    content:html
    	});
		if(_cf.type == ""){
			$('#login_dlg_form_host').hide();
		}

		if(_cf.type == "embed" || _cf.type == "decode"){
            $('#login_dlg').dialog("open");
            
            if(_cf.loginParam.ip !="")
            $('#expand_login_detail_btn_container').hide();
            
    		$("#login_dlg_form_ip").textbox("setValue",_cf.loginParam.ip);
    		$("#login_dlg_form_port").textbox("setValue",_cf.loginParam.port);
    		$("#login_dlg_form_epId").textbox("setValue",_cf.loginParam.epId);

	    	if(_cf.autoLogin == true){
	    		$("#login_dlg_form_uid").textbox("setValue",_cf.loginParam.username);
	    		$("#login_dlg_form_username").val(_cf.loginParam.username);
        		$("#login_dlg_form_pwd").textbox("setValue",_cf.loginParam.password);
        		$("#login_dlg_form_password").val(_cf.loginParam.password);
	    		_f.login();
	    		return;
	    	}
	    	if(typeof loginParam != "undefined"){
	    		loginParam = JSON.parse(loginParam);
	    		if(loginParam.ip ==_cf.loginParam.ip){
		    		$("#login_dlg_form_uid").textbox("setValue",loginParam.username);
		    		$("#login_dlg_form_username").val(loginParam.username);
		    		if(!loginParam.remember){
		        		$("#login_dlg_form_pwd").textbox("setValue",loginParam.password);
		        		$("#login_dlg_form_password").val(loginParam.password);
		    			$("#login_dlg_form_remember").prop("checked",true);	
		    		}else{
		    			$("#login_dlg_form_remember").prop("checked",false);	
		    		}
	    			
	    		}
	    	}
		}else if(_cf.type == "handeye"){
            $('#login_dlg').dialog("open");
            $('#expand_login_detail_btn_container').hide();

	    	if(typeof loginParam != "undefined"){
	    		loginParam = JSON.parse(loginParam);
	    		$("#login_dlg_form_uid").textbox("setValue",loginParam.uid);
	    		if(!loginParam.remember){
	        		$("#login_dlg_form_pwd").textbox("setValue",loginParam.pwd);
	    			$("#login_dlg_form_remember").prop("checked",true);	
	    		}else{
	    			$("#login_dlg_form_remember").prop("checked",false);	
	    		}    		
	    	}

		}else{
	    	if(typeof loginParam != "undefined"){
	    		loginParam = JSON.parse(loginParam);
	    		$("#login_dlg_form_ip").textbox("setValue",loginParam.ip);
	    		$("#login_dlg_form_port").textbox("setValue",loginParam.port);
	    		$("#login_dlg_form_epId").textbox("setValue",loginParam.epId);
	    		$("#login_dlg_form_uid").textbox("setValue",loginParam.username);
	    		$("#login_dlg_form_username").val(loginParam.username);
	    		
	    		if(!loginParam.remember){
	        		$("#login_dlg_form_pwd").textbox("setValue",loginParam.password);
	        		$("#login_dlg_form_password").val(loginParam.password);
	    			$("#login_dlg_form_remember").prop("checked",true);	
	    		}else{
	    			$("#login_dlg_form_remember").prop("checked",false);	
	    		}
	    		
	    		if(typeof loginParam.bFix == "undefined" || !loginParam.bFix || loginParam.bFix != "1"){
    				$('#login_dlg_form_bfix').prop("checked",false);
	    		}else{
    				$('#login_dlg_form_bfix').prop("checked",true);
	    		}
	    	}
	    	
	    	if(_cf.autoLogin == true){
	    		$("#login_dlg_form_ip").textbox("setValue",_cf.loginParam.ip);
	    		$("#login_dlg_form_port").textbox("setValue",_cf.loginParam.port);
	    		$("#login_dlg_form_epId").textbox("setValue",_cf.loginParam.epId);
	    		$("#login_dlg_form_uid").textbox("setValue",_cf.loginParam.username);
	    		$("#login_dlg_form_username").val(_cf.loginParam.username);
	    		$("#login_dlg_form_pwd").textbox("setValue",_cf.loginParam.password);
        		$("#login_dlg_form_password").val(_cf.loginParam.password);
        		$('#login_dlg_form_bfix').prop("checked",(_cf.bFix == "1" ? true :false));
	    		_f.login();
	    		return;
	    	}else{
	            $('#login_dlg').dialog("open");
	    	}
			
		}
        
	},
	expand_login_detail:function(btn){

		if($('#login_detail_box').is(":hidden") == false){

	    	$('#login_dlg').dialog('resize',{
	    	    height:220
	    	});
	    	
			$('#login_detail_box').hide();
			$('#expand_login_detail_btn').linkbutton({
				iconCls:"panel-tool-expand"
			});

		}else{
	    	$('#login_dlg').dialog('resize',{
	    	    height:300
	    	});
			$('#login_detail_box').show();
			$('#expand_login_detail_btn').linkbutton({
				iconCls:"panel-tool-collapse"
			});
		}

    	$('#login_dlg').dialog("open")
	},
	login:function(){

    	$('#login_dlg').dialog("close");
        if ($('#login_dlg_form').form("validate") == true) {

    		$('#wait_dlg').dialog({
    			title:_lp.frame.notes.waiting_title1,
    		    width:280,
    		    height:100,
    		    closable:false,
    		    content:'<div style="width:100%;text-align:center;line-height:40px;">'+_lp.frame.notes.login_waiting+'</div>'
    		});
    		$('#wait_dlg').dialog('open');
    		
        	$('#login_dlg_form_username').val($('#login_dlg_form_uid').textbox('getValue'));
        	$('#login_dlg_form_password').val($('#login_dlg_form_pwd').textbox('getValue'));
        	if(_cf.type == "handeye"){

        		var uid = $("#login_dlg_form_uid").textbox("getValue");
        		var pwd = $("#login_dlg_form_pwd").textbox("getValue");
                $.ajax(
                {
                    type: "post",
                    url: "auth.php?&t=" + new Date().getTime(),
                    data: "authtype="+_cf.authType+"&username=" + uid+"&password="+pwd,
                    success: function (rv) {
        				rv = jQuery.parseJSON(rv);
        				if(rv.response && rv.response.errorcode == "0x0000"){
        					
        					//判断宝宝在线登陆返回的摄像头信息
        					if(rv.response.content.cameras){
        						_f.bbol_pulist = rv.response.content.cameras;
        					}
        					
        		        	var ip = rv.response.content.icvs2_address;
        		        	var port = rv.response.content.icvs2_port;
        		        	var username = "admin";
        		        	var password = "";
        		        	var epId = rv.response.content.icvs2_ep;
        		        	
        		        	_cf.loginParam.ip = ip;
        		        	_cf.loginParam.port = port;
        		        	_cf.loginParam.epId = epId; 
        		        	_cf.loginParam.username =username;
        		        	_cf.loginParam.password = password;
        		        	
        		    		_p.connect(_cf.loginParam.ip,_cf.loginParam.port,_cf.loginParam.epId,_cf.loginParam.username,_cf.loginParam.password,_cf.bFix,_f.cb);
        		        	_p.connect("172.16.2.3","9988","system","admin","",_cf.bFix,_f.cb);
        		        	
        				}else{
        		            $('#login_dlg').dialog("open");
        		             var str = "用户认证失败";
        		             if(rv.response.errorcode == "0x0042"){
        		            	 str = "密码不正确";
        		             }else if(rv.response.errorcode == "0x0049"){
        		            	 str = "只能在您注册的移动设备上登录平台";
        		             }
        		             $.messager.alert("错误", str, 'error');
        				}
                    },
                    error: function (rv) {
                    }
                });
        	}else{
        		
	        	var ip = $('#login_dlg_form_ip').textbox('getValue');
	        	var port = $('#login_dlg_form_port').textbox('getValue');
	        	var epId = $('#login_dlg_form_epId').textbox('getValue');
	        	//$('#login_dlg_form_username').val($("#login_dlg_form_pwd").textbox("getValue"));
	        	var uid = $('#login_dlg_form_username').val();
        		//$('#login_dlg_form_password').val($("#login_dlg_form_pwd").textbox("getValue"));
	        	var pwd = $('#login_dlg_form_password').val();
        		var bFix = ($('#login_dlg_form_bfix').prop("checked") == true ? "1": "0");
        		
	        	//console.log(ip,port,epId,uid,pwd,_cf.bFix);
	            setTimeout(function(){
	            	_p.connect(ip,port,epId,uid,pwd,bFix,_f.cb);
	            },100);
        	}
        }
		return;
	},
	logout:function(){
		if (navigator.userAgent.indexOf("MSIE") > 0) {
			if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
				window.opener = null;
				window.close();
			} else {
				window.open('', '_top');
				window.top.close();
			}
		}
		else if (navigator.userAgent.indexOf("Firefox") > 0) {
			window.location.href = 'about:blank ';
		} else {
			window.opener = null;
			window.open('', '_self', '');
			window.close();
		}
	},
	// 登录设备成功,回调
	cb:function(rv,connectId){
		$('#wait_dlg').dialog('close');
		if(rv != P_Error.SUCCESS){
            $('#login_dlg').dialog("open");
            var rv = parseInt(rv);
            
            if(rv == 0x0302 || rv == 0x0301 || rv == 0x0304|| rv == 0x0305|| rv == 0x0203){
            	$.messager.alert(_lp.frame.notes.error_title2, _lp.frame.notes.error_msg1);
            }else if(rv == 0x030E){
            	$.messager.alert(_lp.frame.notes.error_title2, _lp.frame.notes.error_msg3);
            }else{
            	if(_f.errors[rv]){
            		$.messager.alert(_lp.frame.notes.error_title2, _f.errors[rv]);	
            	}else
            	$.messager.alert(_lp.frame.notes.error_title2, _lp.frame.notes.error_msg2+"("+ rv+")");	
            }
            
			return;
		}

		// 登录成功,保存参数到cookie
		_f.connectId = connectId;
		/*
		if(_p.get_platform_type(_f.connectId).toLowerCase() == "server"){
			if(_cf.type == "handeye"){
				$("#frame_north_pulist").panel("close");
				$('#frame_north_menus').panel("open");
				
			}else{
				$("#frame_north_pulist").panel("close");
				$('#frame_north_menus').panel("open");
			}
		}else{
			$('#frame_north_pulist').panel("close");
			$('#frame_north_menus').panel("open");
		}
			*/

		if(_cf.type == "external"){
			$('#frame_north_pulist').panel("close");
			$('#frame_north_menus').panel("close");
		}else if(_cf.type == "embed" || _cf.type == "decode"){
			$('#frame_north_pulist').panel("close");
			$('#frame_north_menus').panel("open");
		}else{
			$('#frame_north_pulist').panel("close");
			$('#frame_north_menus').panel("open");
			
			//$('#frame_north_pulist').panel("open");
			//$('#frame_north_menus').panel("open");
		}
		
        $('#login_dlg').window("close");
		var loginParam = _p.query_userinfo(_f.connectId);
		
		if($("#login_dlg_form_remember").val() == true){
			loginParam.remember = true;
		}else{
			loginParam.remember = false;
		}
		
		if(_cf.type == "handeye"){
			loginParam.uid =  $('#login_dlg_form_uid').textbox('getValue');
			loginParam.pwd = $('#login_dlg_form_pwd').textbox('getValue');
		}
		

		loginParam.selected = true;
    	$.cookie("loginParam",JSON.stringify(loginParam),{expires:365});
	
    	
    	if(_cf.type == "external"){
    		// 不要获取资源
	    	// 2. 初始化设备配置模块
	    	_cfs.init(_f.connectId);
	    	
    	}else{
    	//	_f.init_frame_north();
   
			// 1. 获取资源
	   		var rv = _p.fetch_resource(_f.connectId);
	    	// 2. 初始化设备配置模块
	    	_cfs.init(_f.connectId);
	    	_de.init(_f.connectId);
	    //	_pl.init(_f.connectId);
	
	    	// 3. 刷新资源列表
	    	_f.refresh_pulist();
    	}
    	// 4. 打开默认的tab 
      	
		$('#frame_tabs').tabs('select',_cf.defaultTabIndex);
			
	},
	reconnect:function(){
		_p.disconnection(_f.connectId);
	
		
		$('#reboot_wait_dlg').dialog({
			title:_lp.frame.notes.waiting_title1,
		    width:320,
		    height:150,
		    content:'<div style="padding:15px;line-height:50px;">'+_lp.frame.notes.reboot_waiting+'</div>'
		});
		
    	setTimeout(function(){
        	var ip = $('#login_dlg_form_ip').val();
        	var port = $('#login_dlg_form_port').val();
        	var uid = $('#login_dlg_form_uid').val();
        	var pwd = $('#login_dlg_form_pwd').val();
        	var epId = $('#login_dlg_form_epId').val();
			// 没有登录成功，过5秒后再登录
    		_p.connect(ip,port,epId,uid,pwd,_cf.bFix,_f.reconnect_callback);
    	},6000);
		
	},
	reconnect_callback:function(rv,connectId){
	
		if(rv != P_Error.SUCCESS){
			_f.reconnect();
			return;
		}
		$('#reboot_wait_dlg').dialog("close");
		$('#frame_north_menus .easyui-linkbutton').linkbutton('unselect');
		$('#menu_iv').linkbutton('select');
	
		
		_f.cb(rv,connectId);
	},

    init_frame_north:function(){

    	var html = '';     
    	html += '<div style="margin-bottom:0px;">';
    	
    	html += '<a id="menu_iv" href="#" class="easyui-linkbutton " data-options="iconCls:\'icon-player\',plain:false,selected:true,height:34">&nbsp;&nbsp;'+_lp.frame.player.title+'</a>';
    	html += '<a id="menu_vod" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:false,height:34">&nbsp;&nbsp;'+_lp.frame.vod.title+'</a>';
    	if(_cf.type.search(/embed|external/) > -1 ){
    		html += '<a id="menu_set" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-configset\',plain:false,height:34">&nbsp;&nbsp;'+_lp.frame.configsets.title+'</a>';
    	}else{
    		html += '<a id="menu_map" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-emap\',plain:false,height:34">&nbsp;&nbsp;'+_lp.frame.map.title+'</a>';
    	}		
    	
	
    	html += '</div>';     
//  		
//  	if(_cf.type == "decode"){
//  		html += '&nbsp;&nbsp;<a id="menu_de" href="#" class="easyui-linkbutton" data-options="plain:true",selected:true>'+_lp.frame.decode.title+'</a>';
//  		html += '&nbsp;&nbsp;<a id="menu_set" href="#" class="easyui-linkbutton" data-options="plain:true">'+_lp.frame.configsets.title+'</a>';
//  	}
//  	else{
//  		html += '&nbsp;&nbsp;<a id="menu_iv" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon_iv\',plain:true">'+_lp.frame.player.title+'</a>';
//      	
//      	html += '&nbsp;&nbsp;<a id="menu_vod" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:true">'+_lp.frame.vod.title+'</a>';
//      	
//      	if(_cf.type.search(/embed|external/) > -1)
//      		html += '&nbsp;&nbsp;<a id="menu_set" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-edit\',plain:true">'+_lp.frame.configsets.title+'</a>';
//      	else
//      		html += '&nbsp;&nbsp;<a id="menu_map" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon_map\',plain:true">'+_lp.frame.map.title+'</a>';
//      		
//      	html += '<a id="menu_vod" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-search\',plain:false,height:34">&nbsp;&nbsp;'+_lp.frame.vod.title+'</a><a class="easyui-linkbutton " data-options="plain:false,height:34,width:5">&nbsp;</a>';
//      	
//      	
//      	
//  	if(_cf.type.search(/embed|external/) > -1 ) 
//  		html += '<a id="menu_set" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-configset\',plain:false,height:34">&nbsp;&nbsp;'+_lp.frame.configsets.title+'</a>';
//  	else
//  		html += '<a id="menu_map" href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-emap\',plain:false,height:34">&nbsp;&nbsp;'+_lp.frame.map.title+'</a>';
//  		
//  		
//  	}   
        
        
    	$("#frame_north_menus").panel({
			content:html,
			border:false,
			width:540,
			closed:false
		});

		$.parser.parse('#frame_north_menus');
		
		$('#frame_north_menus a').unbind().bind('click',function(){
			$('#frame_north_menus .easyui-linkbutton').linkbutton('unselect');
			_f.frame_menu_click(this.id);
			$('#'+this.id).linkbutton('select');
		});
		if(_cf.type == "decode"){
			$('#menu_de').linkbutton('select');
		}
    	
    	html = '';     
    	html += '<div style="margin-bottom:2px;">';
    	html += '&nbsp;&nbsp;'+_lp.frame.pulist.title+':&nbsp;<input id="pulist_cmb" style="width:150px"></input>';
    	html += '</div>';        
        
        
    	$("#frame_north_pulist").panel({
			content:html,
			border:false,
			width:300,
			closed:false
		});
    	
        $('#pulist_cmb').combogrid({
            panelWidth:350,
            idField:'puid',
            textField:'name',
            mode:'local',
            fitColumns:true,
            columns:[[
                {field:'name',title:_lp.frame.pulist.gr_col.name,width:80},
                {field:'puid',title:'PUID',align:'left',width:160},
                {field:'online',title:'在线',align:'left',width:80,formatter:function(v){return (v == 1? "是" : "否" );}}
            ]],
            onChange:function(newValue,oldValue){
            },
            onSelect:function(index,rec){
            	_f.onselect_pu(rec);
            },
            onLoadSuccess:function(data){
            	if(data.rows.length > 0){
            		$('#pulist_cmb').combogrid("setValue",data.rows[0].puid);
            	}
            },
    		filter: function(q, row){
    			var opts = $(this).combogrid('options');
    			return (row[opts.textField].indexOf(q) == 0 || row[opts.idField].indexOf(q) == 0);
    		}
        });
    },
    add_tab_flag:false,
	init_frame_tabs:function(){		
		$('#frame_tabs').tabs({
		    border:false,
		    showHeader:false,
		    fit:true, 
		    onSelect:function(title,index){
		    	if(_cfs.add_tab_flag == true) return;
		    	
		        if(_f.connectId != null)
		        	_f.frame_tab_onclick(title,index);
		    }
		});
		
		_cfs.add_tab_flag = true;

		$('#frame_tabs').tabs('add',{
		    title:_lp.frame.player.title,
		    fit:true,
		    content:_pl.get_content(),
		    closable:false
		});
		
		$('#frame_tabs').tabs('add',{
		    title:_lp.frame.vod.title,fit:true,
		    content:_v.get_content(),
		    closable:false
		});
//
		$('#frame_tabs').tabs('add',{
		    title:_lp.frame.configsets.title,fit:true,
		    content:'<div id="res_cf_tabs" class="easyui-tabs" tabPosition="left" fit="true" style="width:auto;height:auto"></div><div id="edit_ipchannelset_dlg"><div id="devicelinkactions_dlg"></div>',
		    closable:false
		});

		$('#frame_tabs').tabs('add',{
		    title:_lp.frame.map.title,fit:true,
		    href:'template/map.html',
		    closable:false
		});
		

		$('#frame_tabs').tabs('add',{
		    title:_lp.frame.decode.title,fit:true,
		    content:'<div id="res_de_tabs" class="easyui-layout" data-options="fit:true,border:false" style="width:auto;height:auto"><div id="de_region_west" data-options="region:\'west\',border:false" style="width:149px;background-color:#3d3d3d;border-width: 1px 0 1px 1px;"></div><div id="de_region_center" data-options="region:\'center\',border:false"  style="width:auto;" ></div></div>',
		    closable:false
		});
	
		
		// 用于激活默认tab,
		$('#frame_tabs').tabs('add',{
		    title:'',fit:true,
		    content:'',
		    closable:false
		});
		_cfs.add_tab_flag = false;
		
	},

	frame_menu_click:function(id){
		var tabIndex = 0;
		switch(id){
		case "menu_iv":
			tabIndex = 0;
			break;
		case "menu_vod":
			tabIndex = 1;
			break;
		case "menu_set":
			tabIndex = 2;
			break;
		case "menu_map":
			tabIndex = 3;
			break;
		case "menu_de":
			tabIndex = 4;
			break;
		}
		var tab = $('#frame_tabs').tabs('getSelected');
		var index = $('#frame_tabs').tabs('getTabIndex',tab);
		
		if(index == 0){
			_pl.stop_videos();
		}
		$('#frame_tabs').tabs('select',tabIndex);
	},
	frame_tab_onclick:function(title,index){
		switch(index){
		case 0 :
			if(typeof _pl != 'undefined' && typeof _pl.init == "function"){
				if(_f.connectId != null){
					_pl.init(_f.connectId);
			    	var g = $('#pulist_cmb').combogrid("grid");
			    	if(g.datagrid("getRows").length > 0)
			    	g.datagrid("selectRow",0);
				}
			}
			break;
		case 1 :
			if(typeof _v != 'undefined' && typeof _v.init == "function"){
				if(_f.connectId != null){
					_v.init(_f.connectId);
			    	var g = $('#pulist_cmb').combogrid("grid");
			    	if(g.datagrid("getRows").length > 0) g.datagrid("selectRow",0);
				}
			}
			break;
		case 2 :
			if(typeof _cfs != 'undefined'  && typeof _cfs.init == "function"){
				if(_f.connectId != null){
					_cfs.init(_f.connectId);
			    	var g = $('#pulist_cmb').combogrid("grid");
			    	var rows = g.datagrid("getRows");
			    	for(var i = 0;i < rows.length;i++){
			    		if(rows[i].online == "1"){
				    		g.datagrid("selectRow",i);
				    		break;
			    		}
			    	}
				}
			}
			break;
		case 3 :
			if(typeof _mp != 'undefined' && typeof _mp.init == "function"){
				if(_f.connectId != null){
					_mp.init(_f.connectId);
				}
			}
			break;
		case 4 :
			if(typeof _de != 'undefined' && typeof _de.init == "function"){
				if(_f.connectId != null){
					_de.init(_f.connectId);
			    	var g = $('#pulist_cmb').combogrid("grid");
			    	var rows = g.datagrid("getRows");
			    	for(var i = 0;i < rows.length;i++){
			    		if(rows[i].online == "1"){
				    		g.datagrid("selectRow",i);
				    		break;
			    		}
			    	}
				}
			}
			break;

		}
	},
	onselect_pu:function(pu){
		
		if(pu.childResource.length <=0){
			// 重新获取一下子资源
			var rv = _p.query_puresource(_f.connectId, pu.puid);
			if(rv){
				_f.curPU = pu = rv;
			}
		}
    	var tab = $('#frame_tabs').tabs('getSelected');
    	var tabIndex = $('#frame_tabs').tabs('getTabIndex',tab);
    	if(tabIndex == 0){
    		_pl.pu_change(pu);
    	}else if(tabIndex == 1){
    		_v.pu_change(pu);
    	}else if(tabIndex == 2){
    		_cfs.pu_change(pu);
    	}else if(tabIndex == 4){	
    		_de.pu_change(pu);
    	}
	},
	refresh_pulist:function(puid){
    	// 1. 目前只能是在线设备
    	_f.pulist = new Array();
    	
    	var pulist = new Array();
		var isDec = false;
	
		for(var i = 0;i < _p.puInfoArray.length;i++){
    		//if(_p.puInfoArray[i].name != "1"){continue;}

		//	if(_p.puInfoArray[i].name == "lingsen_gps" && _p.puInfoArray[i].childResource.length <=0){
			if(_p.puInfoArray[i].childResource.length <=0){
				// 重新获取一下子资源
				var rv = _p.query_puresource(_f.connectId, _p.puInfoArray[i].puid);
							
				if(rv != null && typeof rv.childResource != "undefined" ){
					_p.puInfoArray[i] = rv;
				}
			}
			pulist.push(_p.puInfoArray[i]);
    	}
    	//获取子域平台下的资源
    	var domainRoads = P_LY.Connections.get(_f.connectId).domainRoad;
	    if(domainRoads && _p.isArray(domainRoads)){
			for(var j = 0; j < domainRoads.length; j++){
				var dr = domainRoads[j].domain_road;
				var cascadeResource = _p.cascadeResource.get(dr).puInfoArray;
				for(var k = 0;k < cascadeResource.length;k++){
	
					if(cascadeResource[k].childResource.length <=0){
						// 重新获取一下子资源
						var rv = _p.query_puresource(_f.connectId, cascadeResource[k].puid);
									
						if(rv != null && typeof rv.childResource != "undefined" ){
							cascadeResource[k] = rv;
						}
					}
					pulist.push(cascadeResource[k]);
		    	}
			}
	    }

    	if(_cf.authType == 2 && _cf.authType == "handeye"){
    		
    		var tmpPUList = new Array();
    		for(var i = 0;i < pulist.length;i++){
    			var pu = pulist[i];
    			for(var j = 0;j < _f.bbol_pulist.length;j++){
    				var bb_c = _f.bbol_pulist[j];

    				if(pu.puid == bb_c.puid){
    					tmpPUList.push(pu);
    					break;
    				}
    			}
    		}
			
    		for(var i = 0;i < tmpPUList.length;i++){
    			var pu = tmpPUList[i];
    			var childResource = new Array();
    			for(var j = 0;j < pu.childResource.length;j++){
    				var res = pu.childResource[j];

    				if(res.type == "IV"){
            			for(var k = 0;k < _f.bbol_pulist.length;k++){
            				
            				var bb_res = _f.bbol_pulist[k];
            		
            				if(bb_res.idx == res.idx && pu.puid == bb_res.puid ){          				
            					res.name = bb_res.name
            					childResource.push(res);
            					break;
            				}
            			}	
    				}else{
    					childResource.push(res);
    				}
    			}
    			tmpPUList[i].childResource = childResource;
    		}
    		
    		pulist = tmpPUList;
    	}
    	
    
    	_f.pulist = pulist;
    
		var t = P_LY.Connections.get(_f.connectId);
		var count = 0;
		if(t.connType == "Device"){
		
			for(var m = 0;m < pulist[0].childResource.length;m++){
				var rc = pulist[0].childResource[m];
				if(rc.type == "OV"){
					count++;
					break;
				}
			}
			if(count > 0){
				_cf.defaultTabIndex = 2;
				$("#menu_iv").hide();
    			$("#menu_vod").hide();
			}
			else{
				_cf.defaultTabIndex = 0;	
			}
		}else{
			 _cf.defaultTabIndex = 0;
		}
	    
	    	
    	if($('#pulist_cmb')){
        	var g = $('#pulist_cmb').combogrid("grid");
        	
        	g.datagrid('loadData',_f.pulist);    		
    	}
	},

	pu_offline:function(puid){
		if(_cf.type != "decode"){
			//console.log("offline",puid)
	    	var g = $('#pulist_cmb').combogrid("grid");
	    	var row = g.datagrid('getSelected');
	    	var reselected = false;
	    	if(row){
	    		// 当前选择的设备就是下线的设备
	    		if(row.puid == puid){
	    			reselected = true;
	    		}
	    	}
	    	_mp.pustatus_change(0,puid);
	    	var rows = g.datagrid('getRows');
	    	for(var i = 0;i < rows.length;i++){
	    		if(rows[i].puid == puid){
	    	    	g.datagrid('updateRow',{
	    	    		index: i,
	    	    		row: {
	    	    			name:rows[i].name,
	    	    			puid:puid,
	    	    			online:'0'
	    	    		}
	    	    	});
	    			break;
	    		}
	    	}
	    	_pl.refresh_cameralist(0,puid);
			if(reselected){
				var rows = g.datagrid('getRows');
				if(rows.length > 0){
					g.datagrid("selectRow",0);
				}
			}
			$.messager.show({
			    title:_lp.frame.notes.waiting_title1,
			    msg:"("+puid+")"+_lp.frame.notes.pu_offline,
				timeout:4000,
				showType:'slide'
			});
       } 
	},
	pu_online:function(puid){
		//_f.refresh_pulist(puid);
		if(_cf.type != "decode"){
			
	        $.messager.show({
	            title:_lp.frame.notes.waiting_title1,
	            msg:"("+puid+")"+_lp.frame.notes.pu_online,
	            timeout:4000,
	            showType:'slide'
	        });
			
	    	var g = $('#pulist_cmb').combogrid("grid");
	
	    	_mp.pustatus_change(1,puid);
	    	var rows = g.datagrid('getRows');
	    	for(var i = 0;i < rows.length;i++){
	    		if(rows[i].puid == puid){
	    	    	g.datagrid('updateRow',{
	    	    		index: i,
	    	    		row: {
	    	    			name:rows[i].name,
	    	    			puid:puid,
	    	    			online: '1'
	    	    		}
	    	    	});
	    			break;
	    		}
	    	}
	    	_pl.refresh_cameralist(1,puid);
	    //	return;
	    	var pu = null;
			for(var i = 0;i < _p.puInfoArray.length;i++){
				if(_p.puInfoArray[i].puid == puid){
					pu = _p.puInfoArray[i];
					if(pu.childResource.length <=0){
						// 重新获取一下子资源
						var rv = _p.query_puresource(_f.connectId, pu.puid);
						
						if(rv){
							_p.puInfoArray[i] = rv;
							pu = _p.puInfoArray[i];
						}
					}
				
			    //	g.datagrid('appendRow',pu);
			    	break;
				}
			}
		}
	},
	load_xml:function(xmlstr){
        if (!$.support.leadingWhitespace) {
			xmldoc=new ActiveXObject("Microsoft.XMLDOM");  
			xmldoc.async="false";
			xmldoc.loadXML(xmlstr);
        }else{
        	xmldoc = xmlstr;
        }
        return xmldoc;
	},
	end:true
}

function fire_selectpu(pu){	
	//alert(pu);
	var punode = eval("(" + pu + ")");
	if(_cf.type != "external") return;
	//alert(punode.puid)
	_f.onselect_pu(punode);
}

function event_notify(args1, args2, args3, args4){
	if(typeof _p == "object" && typeof _p.event_notify == "function"){
		_p.event_notify(_f.connectId,new P_LY.Struct.NCObjectNotifyStruct("event_notify",args1,null,args2,args3));
	}
}

function stream_status_notify(args1, args2, args3, args4){
	if(typeof _p == "object" && typeof _p.event_notify == "function"){
		_p.stream_status_notify(new P_LY.Struct.NCObjectNotifyStruct("stream_status_notify",args1,args2,args3,eval("("+args4+")")));
	}
}
function gps_data_notify(args1, args2, args3, args4){
	if(typeof _p == "object" && typeof _p.event_notify == "function"){
		_p.gps_data_notify(new P_LY.Struct.NCObjectNotifyStruct("gps_data_notify",args1,null,null,eval("("+args2+")")));
	}
}


$(function(){	
	_f.init();
});

window.onunload = function(){
	return _f.unload();
}
$(document).ready(function(){  
    //
});
