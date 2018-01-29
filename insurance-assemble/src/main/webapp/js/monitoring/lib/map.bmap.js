var Map = {
	geocoder: null,
	create: function(container, options) {

		BMap.playerControlDiv = function(map, content) {
			this.content = content;
		};
		BMap.playerControlDiv.prototype.initialize = {
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
			if (typeof infowindow != "undefined") {
				options.extData.infowindow = infowindow;
			}

			//var offset = new AMap.Pixel(-20,-30);
			//console.log(offset);
			var mOptions = {
				position: null,
				content: options.content,
				autoRotation: true,
				extData: options.extData
			};

			mOptions.position = Map.lnglat(options.lng, options.lat);

			var marker = Map.marker(mOptions);
			map.obj.addOverlay(marker); //增加点

			return marker;
		}


		map.move_marker = function(marker, options) {
			if (typeof options.lng == "undefined" || options.lng == null) {
				return;
			}
			if (typeof options.lat == "undefined" || options.lat == null) {
				return;
			}
			if (typeof options.bearing != "undefined" || options.bearing != null) {
				//var ang = Math.abs(360-options.bearing);
				//console.log(ang)
				//marker.setAngle(options.bearing);
			}
			if (typeof options.speed == "undefined" || options.speed == null) {
				options.speed = 40;
			}
			var position = Map.lnglat(options.lng, options.lat); // new  Map.lnglat(options.lng,options.lat);

			//marker.setPosition(position); //更新点标记位置
			var points = new Array();
			points.push(marker.getPosition());
			points.push(position);
			//console.log(position)
			marker.setPosition(position);
			//marker.setPosition(position)
			var extData = marker.getExtData();
			var self = this;
			if (typeof extData.infowindow != "undefined") {
				//console.log(extData.infowindow,marker.getPosition())
				//extData.infowindow.setPosition(marker.getPosition());
				if (extData.infowindow.isOpen())
					extData.infowindow.open(self.obj, position);
				//extData.infowindow.redraw();
			}
		}


		map.query_addr = function(id, lng, lat, callback) {
			try {
				var self = this;
				if (self.geocoder == null) {
					self.geocoder = new BMap.Geocoder();
				}

				var p = Map.lnglat(lng, lat);

				self.geocoder.getLocation(p, function(rs) {
					var addComp = rs.addressComponents;
					var address = addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;

					if (typeof callback == "function") {

						callback(rs.address, id);
					}
				});

			} catch (e) {
				console.log(e.message)
			}
		}

		map.remove_marker = function(marker) {
			this.removeOverlay(marker);
		}
		map.add_control = function(control) {
			this.obj.addControl(control);
		}


		map.set_center = function(lng, lat) {
			this.obj.setCenter(Map.lnglat(lng, lat));
		}

		map.set_zoom = function(level) {

			this.obj.setZoom(level);
		}


		var center = "合肥";
		if (typeof options != "undefined") {
			if (typeof options.center != "undefined" && options.center != null) {
				center = Map.lnglat(options.center.lng, options.center.lat); //创建中心点坐标
			}
		}
		map.obj = new BMap.Map(container); //创建地图实例
		//map.centerAndZoom(center,options.zoom);
		//new BMap.Point(116.404, 39.915)
		map.obj.centerAndZoom(center, 18); // 初始化地图,设置中心点坐标和地图级别
		map.obj.addControl(new BMap.MapTypeControl()); //添加地图类型控件
		map.obj.addControl(new BMap.ScaleControl());
		//map.obj.setCurrentCity("合肥");          // 设置地图显示的城市 此项是必须设置的
		map.obj.enableScrollWheelZoom(true); //开启鼠标滚轮缩放


		return map;

	},
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
					//gpsdata:gpsdata,
					//infowindow:infowindow
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
				//this.marker.moveTo(b.p,b.speed*this.curPlaySpeed);
				this.marker.setPosition(b.p)

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
			//extData.polyline.setMap(null);
			//this.marker.setMap(null);
			this.mapObj.removeOverlay(extData.polyline);
			this.mapObj.removeOverlay(this.marker);
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
	add_contextmenu: function(menus) {
		var contextMenu = Map.contextmenu(); //new AMap.ContextMenu();
		for (var i = 0; i < menus.length; i++) {
			var m = menus[i];
			contextMenu.addItem(new BMap.MenuItem(m.name, m.cb, 0));
		}
		contextMenu.open = function(map) {
			console.log(map, this);
			map.addContextMenu(this);
		};
		return contextMenu;
	},
	contextmenu: function(obj, menus) {
		//this.menus = menus;
		this.mapObj = obj;
		this.contextMenu = new BMap.ContextMenu();

		for (var i = 0; i < this.contextMenu._items.length; i++) {
			this.contextMenu.removeItem(this.contextMenu._items[i])
		}

		for (var i = 0; i < menus.length; i++) {
			var m = menus[i];
			var item = new BMap.MenuItem(m.text, m.cb, 0);
			this.contextMenu.addItem(new BMap.MenuItem(m.text, m.cb, 0));
		}

		this.open = function(map) {
			map.addContextMenu(this.contextMenu);
		};

		this.reset = function(menus) {
			this.mapObj.removeContextMenu(this.contextMenu);
			this.contextMenu = new BMap.ContextMenu();
			for (var i = 0; i < menus.length; i++) {
				var m = menus[i];
				var item = new BMap.MenuItem(m.text, m.cb, 0);
				this.contextMenu.addItem(new BMap.MenuItem(m.text, m.cb, 0));
			}

		}
		return;
	},
	event: {
		add_listener: function(instance, eventName, handler, context) {
			return instance.addEventListener(eventName, handler);
		},
		add_listeneronce: function(instance, eventName, handler, context) {
			return null; //instance.addEventListener(instance,eventName,handler,context);
		}
	},
	lnglat: function(lng, lat) {
		return new BMap.Point(lng, lat);
	},
	lnglats: function(lnglats) {
		var rvLngLats = new Array();
		for (var i = 0; i < lnglats.length; i++) {
			var ll = lnglats[i];
			rvLngLats.push(Map.lnglat(parseFloat(ll.lng), parseFloat(ll.lat)));
		}
		return rvLngLats;
	},
	polyline: function(opt) {
		var line = new BMap.Polyline(opt.path, opt);
		if (opt.map) {
			opt.map.addOverlay(line);
		}
		return line;
	},
	pixel: function(x, y) {
		return new BMap.Pixel(x, y);
	},
	marker: function(options) {

		//		var cabinIcon = new BMap.Icon("http://code.mapabc.com/images/car_03.png", new BMap.Size(64, 37));    
		//		var cabinMarkerOptions = {
		//			icon: cabinIcon,
		//			//enableDragging: true,
		//			//draggingCursor: "move",
		//			title: ""
		//		}
		//		
		var marker = new BMap.Marker(options.position);
		//console.log(marker,options.extData);
		//var marker = bMaker;
		marker.extData = options.extData;
		marker.getExtData = function() {
			return this.extData
		};
		marker.setExtData = function(extData) {
			this.extData = extData
		};
		marker.moveTo = function(point) {
			this.setPosition(point);
		};
		marker.stopMove = function(point) {
			//this.setPosition(point);
		};

		return marker;
	},
	player_control: function(map, content) {

		var playcontrol = function(map, content) {
			// 默认停靠位置和偏移量
			this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
			this.defaultOffset = new BMap.Size(10, 10);
			this.content = content;

		}
		playcontrol.prototype = new BMap.Control();
		playcontrol.prototype.initialize = function(map) {
			// 创建一个能承载控件的<div>容器                
			var controlUI = document.createElement("DIV");
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
			map.getContainer().appendChild(controlUI);
			return controlUI;
		}

		return new playcontrol(map, content);
	},
	create_infowindow: function(opts) {
		//		var content = opts.content || "";//"<h4 style='margin:0 0 5px 0;padding:0.2em 0'>天安门</h4>" + 
		//	"<img style='float:right;margin:4px' id='imgDemo' src='http://app.baidu.com/map/images/tiananmen.jpg' width='139' height='104' title='天安门'/>" + 
		//	"<p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>天安门坐落在中国北京市中心,故宫的南侧,与天安门广场隔长安街相望,是清朝皇城的大门...</p>";
		var content = Map.create_infocontent(opts);
		//Map.create_infocontent(opts);
		var title = opts.title || "";
		var width = opts.width || 250;
		var height = opts.height || 220;

		//		var opts = {
		//			width : 250,     // 信息窗口宽度
		//			height: 80     // 信息窗口高度
		//		};
		var infowindow = new BMap.InfoWindow(content, {
			title: title
		});
		infowindow.open = function(mapObj, position) {
			mapObj.closeInfoWindow();
			mapObj.openInfoWindow(this, position);
			//console.log(mapObj,position);
		}
		infowindow.close = function(mapObj) {

		}
		return infowindow;
	},
	create_infocontent: function(opts) {

		var info = document.createElement("div");
		//可以通过下面的方式修改自定义窗体的宽高
		info.style.lineHeight = "140%";
		info.style.textAlign = "left;";

		var content = opts.content || "";
		info.innerHTML = content;
		return info;
	}
}