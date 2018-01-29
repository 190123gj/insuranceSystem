
(function($){

define(function(require){
	require("./Y-base");
    $.extend(Y,{
	    Number: function(param){
		    if(typeof param == 'number') {
			    param = '' + param;
			} else if(typeof param == 'string') {
			    param = $.trim(param);
			}
		    var yNumber = new Number(param);
			if(isNaN(yNumber)) {
			    Y.handlerError("参数无法正确转换为数值");
			    return false;
			}
			$.extend(yNumber,{
			    digitUppercase:function(n){
				    var n = n || this;
			        var fraction = ['角', '分'];
			        var digit = ['零', '壹', '贰', '叁', '肆','伍', '陆', '柒', '捌', '玖'];
			        var unit = [['元', '万', '亿'],['', '拾', '佰', '仟']];
			        var head = n < 0? '欠': '';
			        n = Math.abs(n);
			        var s = '';
			        for (var i = 0; i < fraction.length; i++) {
						var arr = (''+n).split('.');
						var num;
						if(arr.length <= 1) num = digit[0];
						else num = digit[arr[1].charAt(i) || 0];
						s += (num + fraction[i]).replace(/零./, '');
			        }
			        s = s || '整';
			        n = Math.floor(n);
			        for (var i = 0; i < unit[0].length && n > 0; i++) {
				        var p = '';
				            for (var j = 0; j < unit[1].length && n > 0; j++) {
					        p = digit[n % 10] + unit[1][j] + p;
					        n = Math.floor(n / 10);
				        }
				        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')+ unit[0][i] + s;
			        }
			        return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
		        },
				round:function(param){
				    var n = Math.pow(10,parseInt(param));
					if(isNaN(n)) return param;
				    return Math.round(this*n)/n;
				},
				toMoneyString:function(isRound){
				    var s = '' + this;
					var arr = s.split('.');
					if(arr.length <=1 ) return s + '.00';
					var xlength = arr[1].split('').length;
					if(xlength == 1) return s + '0';
					if(xlength == 2) return s;
					if(xlength > 2) {
					    if(isRound) {
						    return '' + Y.Number(this.round(2)).toMoneyString();
						} else {
						    return arr[0] + '.' + arr[1].substring(0,2);
						}
					}
				},
				clone: function(){
				   return Y.Number(this);
				}
			});
			return yNumber;
		}
	});
});

})($);