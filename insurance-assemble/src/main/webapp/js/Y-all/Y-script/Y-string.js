
(function($){

define(function(require){
	require("./Y-base");
    $.extend(Y,{
	    String: function(param){
		    if(typeof param == 'number') {
			    param = '' + param;
			}
		    var yString = new String(param);
			$.extend(yString,{
			    trim: function(){
				   return $.trim(this);
		        },
				replaceAll: function(oldStr,newStr){
				    var dictStr = "\\;*;^;$;+;-;|;[;];{;};(;)";
					var dict = dictStr.split(";");
					for(var  i =0;i<dict.length;i++) {
					    var reg = new RegExp("\\" + dict[i],"g");
						oldStr = oldStr.replace(reg,"\\"+dict[i]);
					}
				    var reg = new RegExp(oldStr,"g");
					return this.replace(reg,newStr);
				},
				isLike: function(str){
				    var dictStr = "\\;*;^;$;+;-;|;[;];{;};(;)";
					var dict = dictStr.split(";");
					for(var  i =0;i<dict.length;i++) {
					    var reg = new RegExp("\\" + dict[i],"g");
						str = str.replace(reg,"\\"+dict[i]);
					}
					var reg = new RegExp(str,"g");
					return reg.test(this);
				},
				clone: function(){
				   return Y.String(this);
				}
			});
			return yString;
		},
		StringBuilder: function(param){
		    var stringBuilder = {
			    append: function(str){
				    if(!this.members) this.members = [];
					this.members.push(str);
					return this;
				},
				toString: function(sign){
				    if(!this.members) return '';
					return this.members.join(typeof sign === 'string'?sign:'');
				}
			};
			if(!param) return stringBuilder;
		    if($.isArray(param)) {
			    for(var i=0;i<param;i++) {
				    stringBuilder.append(param[i]);
				}
			} else {
			    stringBuilder.append(param);
			}
			return stringBuilder;
		}
	});
});

})($);