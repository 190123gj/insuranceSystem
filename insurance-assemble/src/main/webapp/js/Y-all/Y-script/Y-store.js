/*
数据源组件
*/
(function($){

define(function(require, exports, module){
	require("./Y-base");
    Y.inherit('Store','component',{
	    init: function(cfg){
		    if(cfg.autoLoad && cfg.url) {
		    	this.load();
				
			} else if(cfg.records){
			    this.update(cfg.records);
			}
			if(!this.records) this.records = [];
			if(!this.initialData) this.initialData =[];
		    this.callBase('init','component',cfg);
		},
		load: function(callback, param){
		    var _this = this,cfg = this.cfg;
		    if(!cfg.url) return false;
			if(this.fire('beforeload') === false) return false;
			this.doLoad(function(res){
				var data;
				if(cfg.treatmentResponse) {
					data = cfg.treatmentResponse(res);
				} else {
					if(res.success) {
						data = res.data;
					} else {
						_this.fire('loadfail', res);
						return;
					}
				}
				_this.update(data);
				_this.isLoad = true;
				if(callback) callback(_this.getRecords());
				_this.fire('load',data);
			}, param);
		},
		doLoad: function(callback, param){
		    var _this = this,cfg = this.cfg;
			var data = {};
			if(cfg.data) {
			    if(typeof cfg.data == 'object') {
				    $.extend(data,cfg.data);
				} else if(typeof cfg.data == 'function') {
				    var d = cfg.data();
				    if(typeof d == 'object') data.extend(data,d);
				}
			}
		    $.ajax($.extend(true,{},cfg,{
				data: data,
				success: function(res){
					if(callback) callback(res);
				},
				error: function(e){
				    _this.fire('loadexception');
				}
			},param || {}));
		},
		update: function(data){
		    var cfg = this.cfg;
		    if(!data) return false;
			if(this.fire('beforeupdate',data) === false) return false;
			this.doUpdate(data);
			this.fire('update');
			this.fire('change');
		},
		doUpdate: function(data){
		    var cfg = this.cfg, reader = this.cfg.reader;
			this.records = this.read(data);
			this.initialData = data;
		},
		suspendReader: function(){
			this.isSuspendReader = true;
		},
		resumeReader: function(){
			this.isSuspendReader = false;
		},
		read: function(data){
			if(this.isSuspendReader) return data;
			var cfg = this.cfg, reader = this.cfg.reader;
		    var result = [];
		    if(!$.isArray(data)) data = [data];
		    if(typeof reader == 'object') {
		    	$.each(data, function(i, item){
		    		var record = {};
			    	for(var name in reader) {
			    		var startName = reader[name], value;
			    		if(typeof startName == 'function') {
			    			value = startName(item);
			    		} else {
			    			value = item[startName];
			    		}
			    		record[name] = value;
			    	}
			    	result.push(record);
		    	});
		    } else if(typeof reader == 'function') {
		    	result = reader(data);
		    } else {
		    	result = data;
		    }
			return result || [];
		},
		getRecords: function(){
			var cfg = this.cfg;
			if(!this.isLoad && cfg.url) {
				this.load(function(){}, {async: false, timeout: 5000});
			}
		    return this.records;
		},
		getAt: function(index){
		    return this.getRecords()[index];
		},
		find: function(name,value){
			var records = [];
			$.each(this.getRecords(),function(i,item){
			    if(typeof item[name] !== 'undefined' && item[name] === value) {
				    records.push(item);
				}
			});
			return records;
		},
		each: function(fn){
			$.each(this.getRecords(), fn);
		},
		add: function(data){
			if(!$.isArray(data)) data = [data];
	    	$.merge(this.initialData, this.data);
		    $.merge(this.records, this.read(data));
			this.fire('add');
			this.fire('change');
		},
		removeAt: function(index){
			var records = this.records;
			if(typeof index != 'number' || index > records.length) return;
		    records.splice(index,1);
		    this.fire('remove');
			this.fire('change');
		},
		clear: function(){
		    this.records.splice(0,this.records.length);
			this.fire('clear');
			this.fire('change');
		}
    });
	
    Y.Store.defaults = {
	    autoLoad: true,
		dataType: 'json',
		reader: undefined
	}
});

})($);
