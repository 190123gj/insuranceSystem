(function($){
define(function(require, exports, module){
	require("./Y-combobox");
    Y.inherit('SelectBranch','component' , {
	    init : function(cfg){
			var provinceOpts , cityOpts , branchOpts;
			var comboOpts = cfg.comboOpts || {};
			var renderDiv = this.renderDiv = $(cfg.renderTo);
			if(!cfg.renderTo) return;
			provinceOpts = this.provinceOpts = $.extend({itemsHeight:180, renderTo: renderDiv, store: [], autoSize: true}, comboOpts.province || {});
			cityOpts = this.cityOpts = $.extend({itemsHeight: 180, renderTo: renderDiv, store: [], autoSize: true}, comboOpts.city || {});
			branchOpts = this.branchOpts = $.extend({itemsHeight: 180, renderTo: renderDiv, store: [], autoSize: true}, comboOpts.branch || {});
			var provinceList = this.provinceList = Y.create('Combobox', provinceOpts);
			var cityList = this.cityList = Y.create('Combobox', cityOpts);
			var branchList = this.branchList = Y.create('Combobox', branchOpts);
			provinceList.show();
			cityList.show();
			branchList.show();
			var _this = this;
			var bankSel = $(cfg.bankSelect || '<input>');
			if(!cfg.noInit){
   			    this.handleProvinceList();
			}
			//bind province change
			provinceList.change(function(value){
				if (value){
					_this.handleCityList(value);
				}
				else {
					_this.reset();
				}
			});
			//bind city change
			
			cityList.change(function(value){
				if (value){
					_this.handleBranchList(value , bankSel.getValue?bankSel.getValue():bankSel.val());
				}
				
			});
			if(cfg.provinceCityUrl){
				this.handleProvinceCityList();
			}
			this.callBase('init','component',cfg);
		},
		handleProvinceCityList:function(){
			var cfg = this.cfg;
			var _this = this;
			$.ajax({
				url:_this.cfg.provinceCityUrl,
				dataType:'jsonp',
                jsonp:'callback',
				success: function(res){
					_this.setAreaInfo(res);
				}
			});
		},
		handleCityList:function(code,callback){
			var cfg = this.cfg;
		    if(cfg.areaInfo) {
			    this.setCityList(cfg.areaInfo , code);
				if(callback) {
				    callback();
				}
				if(this.afterCity) {
					this.afterCity();
				}
				return;
			}
			if(!cfg.cityUrl) return;
			var _this = this;
			$.ajax({
				url: cfg.cityUrl + '?code=' + code,
				dataType:cfg.dataType,
				success: function(res){
					var fn = cfg.toCityItems || _this.toCityItems;
					var items = fn(res);
					_this.cityList.clear();
					_this.cityList.addItem(items);
					if(callback) {
					    callback();
					}
					if(_this.afterCity) {
					    _this.afterCity();
					}
				},
				error: function(){
				
				}
			});
		},
		handleProvinceList:function(callback){
			var cfg = this.cfg;
		    if(cfg.areaInfo) {
			    this.setProvinceList(cfg.areaInfo);
				if(callback) {
				    callback();
				}
				if(this.afterProvince) {
					this.afterProvince();
				}
				return;
			}
			if(!cfg.provinceUrl) return;
			var _this = this;
			$.ajax({
				url: cfg.provinceUrl,
				dataType:cfg.dataType,
				success: function(res){
				    var fn = cfg.toProvinceItems ||  _this.toProvinceItems
				    var items = fn(res);;
					_this.provinceList.clear();
					_this.provinceList.addItem(items);
					if(callback) {
						callback();
					}
					if(_this.afterProvince) {
					    _this.afterProvince();
					}
				},
				error: function(){
				}
			});
		},
		handleBranchList:function(code, bankId,callback){
			if(!bankId)return;
			var cfg = this.cfg;
			var _this = this;
			$.ajax({
				url: _this.cfg.branchUrl + (/\?/.test(_this.cfg.branchUrl)?"&":"?")+'districtNo='+ code +'&bankCode='+ bankId,
				dataType:'jsonp',
                jsonp:'callback',
				success: function(res){
					var fn = _this.cfg.toBranchItems || _this.toBranchItems;
					var items = fn(res);
					_this.branchList.clear();
					_this.branchList.addItem(items);
					if(callback) {
					    callback();
					}
					if(_this.afterBranch) {
					    _this.afterBranch();
					}
					
				},
				error: function(){
				
				}
			});
		},
		setProvinceList:function(areaInfo){
		    var fn = this.cfg.toProvinceItems || this.getProvinceStore;
		    var store = fn(areaInfo);
			this.provinceList.clear();
			this.provinceList.addItem(store);
		},
		reStart:function(){
			this.handleProvinceList();
		},
		reset:function(sts){
			if(sts)this.provinceList.clear();
			this.cityList.clear();
			this.branchList.clear();
		},
		getProvinceStore : function(areaInfo) {
		    var store = [];
			areaInfo = areaInfo.data || areaInfo;
		    $.each(areaInfo,function(i,item){
			    var aitem = item.provinceName;
				if(aitem)store.push(aitem);
			});
			return store;
		},
		setCityList:function(areaInfo,provinceName){
		    var fn = this.cfg.toCityItems || this.getCityStore;
		    var store = fn(areaInfo,provinceName);
			this.cityList.clear();
			this.cityList.addItem(store);
		},
		getCityStore:function(areaInfo,provinceName) {
		    var store = [];
			areaInfo = areaInfo.data || areaInfo;
		    $.each(areaInfo,function(i , item){
			    if(item.provinceName === provinceName) {
			        var citys = item.cityList;
				    $.each(citys , function(j , item){
					    var aitem = {value: item.branchDistrictNo , text: item.cityName};
						store.push(aitem);
					});
					return false;
				}
			});
			return store;
		},
		toProvinceItems:function(res){
		    var items = [];
		    if (res.length){
				for (var i = 0; i < res.length; i++){
					items.push({value: res[i].branchDistrictNo , text: res[i].branchDistrictName});
				}
			}
			return items;
		},
		toCityItems:function (res){
		    var items = [];
		    if (res.length){
				for (var i = 0; i < res.length; i++){
					items.push({value: res[i].branchDistrictNo , text: res[i].areaName});
				}
			}
			return items;
		},
		toBranchItems : function (res){
		    var items = [];
			res = res.data || res;
			for (var i = 0; i < res.length; i++){
				items.push({value: res[i].bankLasalle , text: res[i].branchName});
			}
			return items;
		},
		getValue : function(){
			return {province:this.provinceList.getValue(), city: this.city.getValue(), branch: this.branch.getValue()};
		},
		getText : function(){
			return {province: this.provinceList.getText(), city: this.city.getText(), branch: this.branch.getText()};
		},
		afterProvince : function(){
			if(this.provinceList.getItemList().length>0) {
				this.provinceList.setIndex(0);
			}
		},
		afterCity : function(){
			if(this.cityList.getItemList().length>0) {
				this.cityList.setIndex(0);
			}
		},
		afterBranch : function(){
			if(this.branchList.getItemList().length>0) {
				this.branchList.setIndex(0);
			}
		},
		setValue : function(info,type){
			type = type || 'value';
			if(typeof info != 'object') {
				return;
			}
			var _this = this;
			var fn1 = this.afterProvince, fn2 = this.afterCity,fn3 = this.afterBranch;
			this.afterProvince = function(){
				_this.provinceList.setSelect(info.province,type);
				_this.afterProvince = fn1;
			}
			this.afterCity = function(){
				_this.cityList.setSelect(info.city, type);
				_this.afterCity = fn2;
			}
			this.afterBranch = function(){
				_this.branchList.setSelect(info.branch, type);
				_this.afterBranch = fn3;
			}
			this.handleProvinceList();
			return this;
		},
		setAreaInfo: function(areaInfo, first, noInit){
			this.cfg.areaInfo = areaInfo;
			if(!first || typeof first != 'object') {
				if(!noInit) {
					this.handleProvinceList();
				}
				return;
			}
			if(first && first.values){
				this.province = first.values.province;
				this.city = first.values.city;
				this.branch = first.values.branch;
				this.type = "value";
			} 
			else if(first && first.texts){
				this.province = first.texts.province;
				this.city = first.texts.city;
				this.branch = first.texts.branch;
				this.type = "text";
			}
			var fn1 = this.afterProvince, fn2 = this.afterCity, fn3 = this.afterBranch;
			var _this = this;
			this.afterProvince = function(){
				if(_this.province) {
					_this.provinceList.setSelect(_this.province, _this.type);
				}
				_this.afterProvince = fn1;
			}
			this.afterCity = function(){
				if(_this.city) {
					_this.cityList.setSelect(_this.city, _this.type);
				}
				_this.afterCity = fn2;
			}
			this.afterBranch = function(){
				if(_this.branch) {
					_this.branchList.setSelect(_this.branch, _this.type);
				}
				_this.afterBranch = fn3;
			}
			this.handleProvinceList();
			return this;
		}
	}); 

	Y.SelectBranch.autoCreate = function(){
		$('.Y-SelectBranch,Y-selectbranch').each(function(index){
			var renderTo = $(this);
			if(renderTo.hasClass('Y-selectbranch-done')) return;
			var province = renderTo.find('input.province'),
				city = renderTo.find('input.city'),
				branch = renderTo.find('input.branch'),
				provinceText = renderTo.find('input.provinceText'),
				cityText = renderTo.find('input.cityText'),
				branchText = renderTo.find('input.branchText');
			
			var bank = $(renderTo.attr('bank'));
			var key = renderTo.attr('key') || renderTo.attr('id');

			var sel = Y.create('SelectBranch',{
				renderTo: renderTo,
				bankSelect: bank,
				key: key
			});
			sel.provinceList.on('change',function(value,txt){
				renderTo.find('input.province').val(value);
				renderTo.find('input.provinceText').val(txt);
			});
			sel.cityList.on('change',function(value,txt){
				renderTo.find('input.city').val(value);
				renderTo.find('input.cityText').val(txt);
			});
			sel.branchList.on('change',function(value,txt){
				renderTo.find('input.branch').val(value);
				renderTo.find('input.branchText').val(txt);
			});

	        if(branch.length && branch.val()) {
	        	sel.setValue({
	        		province: province.val(),
	        		city: city.val(),
	        		branch: branch.val()
	        	}, 'value');
	        }
	        if(branchText.length && branchText.val()) {
	        	sel.setValue({
	        		province: provinceText.val(),
	        		city: cityText.val(),
	        		branch: branchText.val()
	        	}, 'text');
	        }

	        renderTo.addClass('Y-selectbranch-done');

		});

	}

	Y.SelectBranch.defaults = $.extend({
		renderTo:'',
		bankSelect:'',
		provinceUrl:'',
		provinceCityUrl: 'https://www.yiji.com/anon/common/getAllDistrict.htm',
		cityUrl:'',
		branchUrl:'https://www.yiji.com/anon/common/getBranch.htm',
		areaInfo:'',
		comboOpts:'',
		dataType:'json',
		toProvinceItems:function(areaInfo){
            var data = areaInfo.data.childs[0].childs || {};
            var store = [];
            $.each(data,function(i,item){
                var aitem = item.areaName;
                if(aitem)store.push({value: item.branchDistrictNo , text: item.branchDistrictName});
            });
            return store;
        },
        toCityItems:function(areaInfo,provinceName){
            var store = [];
            var data = areaInfo.data.childs[0].childs || {};
            $.each(data,function(i , item){
                if(item.branchDistrictNo === provinceName) {
                    var citys = item.childs;
                    $.each(citys , function(j , item){
                        var aitem = {value: item.branchDistrictNo , text: item.branchDistrictName};
                        store.push(aitem);
                    });
                    return false;
                }
            });
            return store;
        },
        toBranchItems:function(res){
            var items = [];
            res = res.data || res;
            for (var i = 0; i < res.length; i++){
                items.push({value: res[i].bankLasalle , text: res[i].branchName});
            }
            return items;
        }
	});

	Y.SelectBranch.autoCreate();
	
});
})($);
