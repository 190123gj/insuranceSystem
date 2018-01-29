
(function($){

define(function(require){
	require("./Y-base");
	Y.storage = {
		save: function(name, value, time){
			if(typeof name != 'string') return false;
			if(this.modern) this.remove(name);
			if(time) {
				this.saveLong(name, value, time);
			} else {
				this.saveShort(name, value);
			}
		},
		modern: !!(window.sessionStorage && window.localStorage),
		read: function(name){
			name = this.getName(name);
			var valueStr;
			if(this.modern) {
				valueStr = window.sessionStorage[name] || window.localStorage[name];
			} else {
				valueStr = this.getCookie(name);
			}
			if(!valueStr) return null;
			var valueObj = $.parseJSON(valueStr);
			if(valueObj.time < new Date().getTime()) {
				this.remove(name);
				return null;
			}
			return valueObj.data;
		},
		saveShort: function(name, value){
			var key = this.getName(name);
			var valueObj = {
				data: value
			}
			var valueStr = this.stringify(valueObj);
			if(this.modern) {
				window.sessionStorage[key] = valueStr;
				return;
			}
			this.setCookie(key, valueStr);
		},
		saveLong: function(name, value, time){
			var key = this.getName(name);
			if(typeof time == 'string') time = time.replace(/\-/g, '');
			var date = new Date(time);
			if(isNaN(data)) {
				date = new Date(2099, 1, 1);
			}
			var valueObj = {
				data: value,
				time: date.getTime()
			}
			var valueStr = this.stringify(valueObj);
			if(this.modern) {
				window.localStorage[key] = valueStr;
				return;
			}
			this.setCookie(key, valueStr, {
				expires: date
			});
		},
		remove: function(name){
			name = this.getName(name);
			if(this.modern) {
				window.sessionStorage[name] = '';
				window.localStorage[name] = '';
			} else {
				this.deleteCookie(name);
			}
		},
		setCookie: function(name, value, option){
			var cookie = "";
			cookie += name + "=" + escape(value);
			option = option || {};
			if(option.expires) cookie += "; expires=" + option.expires.toGMTString();
			if(option.path) cookie += "; path=" + option.path;
			if(option.domain) cookie += "; domain=" + option.domain;
			if(option.secure) cookie += "; secure";
			document.cookie = cookie;
		},
		getCookie: function(name){
			var all = document.cookie;
			var reg = new RegExp(name+"=.+;?");
			var match = reg.exec(all);
			if(!match || !match[0]) return null;
			return unescape(match[0].split('=')[1].replace(/;.+/, ''));
		},
		deleteCookie: function(name){
			var exp = new Date();
			exp.setTime (exp.getTime() - 1);
			var cval = 0;
			document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
		},
		stringify: function(obj){
			if(!obj) return '';
			if(window.JSON && window.JSON.stringify) {
				return window.JSON.stringify(obj);
			}
			var result = "";
			if(typeof obj == 'string' || obj instanceof String) {
				result = "\"" + obj + "\"";
			} else if($.isPlainObject(obj)) {
				result += "{";
				for(var name in obj) {
					var val = obj[name];
					if(result.length > 1) result += ",";
					result += "\"" + name +"\":" + arguments.callee(val);
				}
				result += "}";
			} else if($.isArray(obj)) {
				result += "[";
				for(var i=0;i<obj.length;i++) {
					var val = obj[i];
					if(i > 0) result += ",";
					result += arguments.callee(val);
				}
				result += "]";
			} else {
				result = obj.toString();
			}
			return result;
		},
		getName: function(name){
			return 'Y_s_' + name;
		}
	}

});


})($);