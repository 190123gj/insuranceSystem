var Map = {
	geocoder: null,
	create: function(container, options) {
		AMap.playerControlDiv = function(map, content) {
			this.content = content;
		};
		AMap.playerControlDiv.prototype = {
			addTo: function(map, dom) {
				dom.appendChild(this._getHtmlDom(map));
			},
			_getHtmlDom: function(map) {
				this.map = map;
				// 创建一个能承载控件的<div>容器                
				var controlUI = document.createElement("DIV");
				this.container = controlUI;
				controlUI.id = "map_playerbar";
				controlUI.style.width = '220px'; //设置控件容器的宽度
				controlUI.style.height = '35px'; //设置控件容器的高度                
				controlUI.style.backgroundColor = '#fefefe';
				controlUI.style.borderStyle = 'solid';
				controlUI.style.borderColor = '#cecece';
				controlUI.style.borderWidth = '1px';
				controlUI.style.cursor = 'pointer';
				controlUI.style.textAlign = 'center';
				//controlUI.style.lineheight='34px';                 

				// 设置控件的位置                 
				controlUI.style.position = 'absolute';
				controlUI.style.right = '5px'; //设置控件离地图的左边界的偏移量                
				controlUI.style.top = '2px'; //设置控件离地图上边界的偏移量                
				controlUI.style.zIndex = '300'; //设置控件在地图上显示                

				// 设置控件字体样式                
				controlUI.style.fontFamily = 'Arial,sens-serif';
				controlUI.style.fontSize = '12px';
				controlUI.style.paddingLeft = '2px';
				controlUI.style.paddingRight = '2px';
				controlUI.innerHTML = this.content;
				return controlUI;
			}
		}


		var map = {};
		map.obj = null;
		map.markers = new Array();
		map.query_addr_list = new Array();
		map.geocoder = null;

		// 添加一个叠加物
		map.add_marker = function(options) {

			if (typeof options.lng == "undefined" || options.lng == null) {
				options.lng = null;
			}
			if (typeof options.lat == "undefined" || options.lat == null) {
				options.lat = null;
			}
			if (typeof options.content == "undefined" || options.content == null) {
				options.content = "";
			}
			/*			if(typeof infowindow != "undefined"){
							options.extData.infowindow = infowindow;
						}*/

			var mOptions = {
				position: null,
				content: options.content,
				autoRotation: false,
				extData: options.extData
			};

			mOptions.position = Map.lnglat(options.lng, options.lat);

			var marker = new AMap.Marker(mOptions);
			var self = this;
			marker.remove = function() {
				self.marker.setMap(self.obj);
			}

			marker.set_content = function(opts) {
				//alert(opts.content);
				return;
				this.setContent(opts.content);

				var extData = this.getExtData();
				if (typeof extData.infowindow != "undefined") {
					extData.infowindow.set_content(opts.infowindowcontent);


				}
			}

			marker.setMap(this.obj);
			return marker;
		}

		// 移动叠加物
		map.move_marker = function(marker, options) {
			if (typeof options.lng == "undefined" || options.lng == null) {
				return;
			}
			if (typeof options.lat == "undefined" || options.lat == null) {
				return;
			}
			if (typeof options.bearing != "undefined" || options.bearing != null) {
				marker.setAngle(options.bearing);
			}
			if (typeof options.speed == "undefined" || options.speed == null || options.speed <= 0) {
				options.speed = 40;
			}
			var position = Map.lnglat(options.lng, options.lat); // new  Map.lnglat(options.lng,options.lat);

			//marker.setPosition(position); //更新点标记位置
			var points = new Array();
			points.push(marker.getPosition());
			points.push(position);
			marker.moveTo(position, options.speed * 10);


			var extData = marker.getExtData();
			if (typeof extData.infowindow != "undefined") {
				extData.infowindow.setPosition(position);
			}
			if (extData.bFocus) {
				var p = marker.getPosition();
				this.set_center(p.lng, p.lat);
			}
		}

		// 更新叠加物
		map.update_marker = function(id, options) {
			for (var i = 0; i < this.markers.length; i++) {
				var extData = this.markers[i].getExtData();
				if (extData.id == options.extData.id) {
					if (typeof options.address != "undefined" && options.address != "") {
						extData.address = options.address;
					}

					if (typeof options.lng != "undefined" && typeof options.lat != "undefined") {
						this.move_marker(options);
					}
					this.markers[i].setExtData(extData);
					return;
				}
			}
		}

		// 移除
		map.remove_marker = function(marker) {
			marker.setMap(null);
		}
		map.removeall_marker = function() {
			this.obj.clearMap();
		}

		// 添加一个地图控件
		map.add_control = function(control) {
			this.obj.addControl(control);
		}

		// 查询逆地址
		map.query_addr = function(id, lng, lat, callback) {
			try {
				var self = this;
				if (self.geocoder == null) {
					self.obj.plugin(["AMap.Geocoder"], function() {
						self.geocoder = new AMap.Geocoder({
							radius: 1000 //以已知坐标为中心点，radius为半径，返回范围内兴趣点和道路信息 
								//extensions: "all"//返回地址描述以及附近兴趣点和道路信息，默认"base" 
						});
					});
				}

				var p = Map.lnglat(lng, lat);

				AMap.event.addListenerOnce(self.geocoder, "complete", function(data) {
					if (typeof callback == "function") {
						//alert(this);
						callback(data.regeocode.formattedAddress, id);
					}
				}, id);
				self.geocoder.getAddress(p);
			} catch (e) {
				console.log(e.message)
			}
		}

		// 查询回调
		map.geocoder_callback = function(data, id) {
			for (var i = 0; i < this.markers.length; i++) {
				var m = this.markers[i];
				var extData = this.markers[i].getExtData();
				if (id == extData.id) {
					extData.address = data.regeocode.formattedAddress;
					//console.log(extData)
					m.setExtData(extData);

					if (typeof extData.infowindow != "undefined") {
						var content = extData.infowindow.getContent();
						$(content).find("span.addr").html(extData.address);
						try {
							//extData.infowindow.setContent(content);
						} catch (e) {
							console.log(e.message)
						}
					}
					break;
				}
			}
		}

		// 设置中心点
		map.set_center = function(lng, lat) {
			this.obj.setCenter(Map.lnglat(lng, lat));
		}

		// 设置地图级别
		map.set_zoom = function(level) {

			this.obj.setZoom(level);
		}


		var center = null;
		if (typeof options != "undefined") {
			if (typeof options.center != "undefined" && options.center != null) {
				center = Map.lnglat(options.center.lng, options.center.lat); //创建中心点坐标
			}
		}
		map.obj = new AMap.Map(container, {
			view: new AMap.View2D({
				center: center,
				zoom: options.zoom
			})
		}); //创建地图实例

		return map;
	},

	// 轨迹回放
	locus: function(opt) {
		if (typeof opt.id == "undefined") return false;
		if (typeof opt.mapObj == "undefined") return false;
		if (typeof opt.paths == "undefined" || opt.paths.length <= 0) return false;

		this.id = opt.id;
		this.mapObj = opt.mapObj;
		this.paths = opt.paths;
		this.curPointIndex = 0;
		this.curPlaySpeed = 1;
		this.name = (typeof opt.name == "undefined" ? "" : opt.name);
		this.beginTime = (typeof opt.beginTime == "undefined" ? "" : opt.beginTime);
		this.endTime = (typeof opt.endTime == "undefined" ? "" : opt.endTime);

		var point = this.paths[0];

		// 创建轨迹
		var lineArr = new Array();
		var speed = 40;
		for (var i = 0; i < this.paths.length; i++) {
			var p = this.paths[i];
			lineArr.push(p.p);
			speed += parseInt(p.speed);
		}
		var beginPoint = lineArr[0];
		speed = parseInt(speed / lineArr.length);

		//绘制轨迹
		var polyline = new Map.polyline({
			map: this.mapObj,
			path: lineArr, //gpsdata,
			strokeColor: "#00A", //线颜色
			strokeOpacity: 1, //线透明度
			strokeWeight: 3, //线宽
			strokeStyle: "solid" //线样式
		});

		// 创建起点
		var mOptions = {
			position: point,
			content: "测试",
			autoRotation: true,
			extData: ""
		};
		this.marker = Map.marker({
			map: this.mapObj,
			position: beginPoint, //基点位置
			icon: "http://code.mapabc.com/images/car_03.png", //marker图标，直接传递地址url
			offset: Map.pixel(-26, -13),
			title: "轨迹时间:" + this.beginTime + "-" + this.endTime,
			content: "<table style='color:#000'><tr><td><img src='http://code.mapabc.com/images/car_03.png'/></td></tr><tr><td>" + this.name + "的历史轨迹</td></tr></table>",
			autoRotation: true,
			extData: {
				id: "locus_" + this.id,
				polyline: polyline
			}
		});


		var self = this;
		Map.event.add_listener(self.marker, "moveend", function(e) {
			self.move();
		});

		this.start = function() {
			this.move();
		}

		this.move = function() {
			try {
				var nextIndex = this.curPointIndex + 1;
				console.log(nextIndex)
				if (nextIndex > this.paths.length) {
					// 结束
					console.log('end');
					this.stop();
					return;
				}
				if (!this.paths[nextIndex]) {
					// 不存在
					console.log('no exits')
					this.stop();
					return;
				}

				var b = this.paths[nextIndex];

				this.curPointIndex = nextIndex;
				//$('#playspan_speed').html(_mp.curHistoryMarkerSpeed+"倍速");
				this.marker.moveTo(b.p, b.speed * this.curPlaySpeed);

			} catch (e) {
				console.log(e.message)
			}
		}
		this.pause = function() {
			this.marker.stopMove();
		}

		this.stop = function() {
			this.marker.stopMove();
			this.curPointIndex = 0;
			this.curPlaySpeed = 1;
			var p = this.paths[0];
			this.marker.setPosition(p.p);
		}

		this.remove = function() {
			this.stop();
			var extData = this.marker.getExtData();
			extData.polyline.setMap(null);
			this.marker.setMap(null);
		}

		this.set_speed = function(type, callback) {
			if (type == "fast") {

				this.curPlaySpeed++;
				if (this.curPlaySpeed > 16) {
					_mp.curHistoryMarkerSpeed = 1;
				}
			} else {

				this.curPlaySpeed--;
				if (this.curPlaySpeed < -8) {
					_mp.curHistoryMarkerSpeed = 1;
				}
			}
		}
	},

	// 添加右键菜单
	add_contextmenu: function(menus) {
		var contextMenu = Map.contextmenu(); //new AMap.ContextMenu();
		for (var i = 0; i < menus.length; i++) {
			var m = menus[i];
			contextMenu.addItem(m.name, m.cb, 0);
		}
		return contextMenu;
	},

	// 添加事件
	event: {
		add_listener: function(instance, eventName, handler, context) {
			return AMap.event.addListener(instance, eventName, handler, context);
		},
		add_listeneronce: function(instance, eventName, handler, context) {
			return AMap.event.addListenerOnce(instance, eventName, handler, context);
		}
	},

	// 经纬度转换
	lnglat: function(lng, lat) {
		if (isNaN(lng) || isNaN(lat)) return;

		//console.log(parseFloat(lng).toFixed(6),parseFloat(lat).toFixed(6));
		return new AMap.LngLat(parseFloat(lng).toFixed(6), parseFloat(lat).toFixed(6));
	},
	lnglats: function(lnglats) {
		var rvLngLats = new Array();
		for (var i = 0; i < lnglats.length; i++) {
			var ll = lnglats[i];
			rvLngLats.push(Map.lnglat(parseFloat(ll.lng).toFixed(6), parseFloat(ll.lat).toFixed(6)));
		}
		return rvLngLats;
	},

	// 线
	polyline: function(opt) {
		return new AMap.Polyline(opt);
	},

	// 像素
	pixel: function(x, y) {
		return new AMap.Pixel(x, y);
	},

	// 叠加物
	marker: function(options) {
		return new AMap.Marker(options);
	},


	// 插件控件
	player_control: function(map, content) {
		return new AMap.playerControlDiv(map, content);
	},

	// 菜单对象
	contextmenu: function(obj, menus) {
		this.mapObj = obj;
		this.contextMenu = new AMap.ContextMenu();

		for (var i = 0; i < menus.length; i++) {
			var m = menus[i];
			this.contextMenu.addItem(m.text, m.cb, 0);
		}

		this.open = function(map, e) {
			this.contextMenu.open(_mp.map.obj, e.lnglat);
		};

		this.reset = function(menus) {
				this.contextMenu = new AMap.ContextMenu();
				for (var i = 0; i < menus.length; i++) {
					var m = menus[i];
					this.contextMenu.addItem(m.text, m.cb, 0);
				}

			}
			/*
			Map.event.add_listener(obj,"rightclick",function(e){
				//var m = e.target;
				console.log(e)
				_mp.contextMenu = Map.contextmenu();//new AMap.ContextMenu();
				if($("#map_playerbar").is(":hidden")){
					_mp.contextMenu.addItem("打开放控制条",function(){
			        	$("#map_playerbar").show();
					},0);
				}else{
					_mp.contextMenu.addItem("关闭回放控制条",function(){
			        	$("#map_playerbar").hide();
					},0);
				}
				
				_mp.contextMenu.addItem("清除回放轨迹",function(){
					_mp.clear_locus();
				},0);
				_mp.contextMenu.open(_mp.map.obj,e.lnglat);
			});
		
			return new AMap.ContextMenu();
			*/
	},

	// 创建tip对象
	create_infowindow: function(opts) {
		if (typeof opts == "undefined" || opts == null) return;
		var content = Map.create_infocontent(opts);

		var infowindow = new AMap.InfoWindow({
			isCustom: true, //使用自定义窗体
			content: content,
			offset: new AMap.Pixel(36, -35) //-113, -140
		});
		infowindow.set_content = function(content) {
			var html = this.getContent();
			//alert(html);
			$(html).find('.info-middle').html(content);
			//html = $(html).prop();
			//$(html).remove();
			//this.setContent(html);
		}
		return infowindow;
	},

	// 创建tip内容
	create_infocontent: function(opts) {

		var info = document.createElement("div");
		info.className = "info";
		//可以通过下面的方式修改自定义窗体的宽高
		//info.style.width = "400px";
		if (typeof opts.width != "undefined" && opts.width > 0) {
			info.style.width = opts.width + "px";
		}
		var title = opts.title || "";
		var content = opts.content || "";
		// 定义顶部标题
		var top = document.createElement("div");
		top.className = "info-top";
		var titleD = document.createElement("div");
		titleD.innerHTML = title;
		var closeX = document.createElement("img");
		closeX.src = "http://webapi.amap.com/images/close2.gif";
		closeX.onclick = function() {
			if (typeof opts.close == "function") {
				opts.close();
			}

			//_mp.close_infowindow(puid);
			//_mp.map.obj.clearInfoWindow();
		}

		top.appendChild(titleD);
		top.appendChild(closeX);
		info.appendChild(top);

		// 定义中部内容
		var middle = document.createElement("div");
		middle.className = "info-middle";
		middle.style.backgroundColor = 'white';
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
	}
}