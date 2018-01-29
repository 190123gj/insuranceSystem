
(function($){

define(function(require){
	require("./Y-base");
    $.extend(Y,{
	    Date: function(param){
		    if(typeof param == 'string') {
			    param = $.trim(param);
			    param = param.replace(/-/g,"\/").replace(/\./g,"\/").replace(/\s/g,"\/");
			}
		    var yDate = new Date(param);
			if(isNaN(yDate)) {
			    Y.handlerError("参数无法正确转换为日期");
			    return false;
			}
			$.extend(yDate,{
			    isLeapYear: function(){
				    return Y.Date.isLeapYear(this);
				},
				add: function(strInterval, number) {   
                    return Y.Date.add(this,strInterval,number);
                },
				diff: function(dtEnd, strInterval) {   
                    var dtStart = this;  
                    if (typeof dtEnd == 'string' ) 
                    {   
                        dtEnd = Y.Date(dtEnd);  
                    }  
                    return Y.Date.diff(dtStart,dtEnd,strInterval);
                },
				translate: function(type){
				    return Y.Date.translate(this,type);  
				},
				clone: function(){
				   return Y.Date(this.getTime());
				}
			});
			yDate.toString = function(type){   
                return Y.Date.toString(this,type);
            };
			return yDate;
		}         
	});  
    $.extend(Y.Date,{
	    isLeapYear: function(date){
		    return (0==date.getFullYear()%4&&((date.getFullYear()%100!=0)||(date.getFullYear()%400==0)));
		},
		translate: function(date,type){
			var myDate = date;  
			var myArray = Array();  
			myArray[0] = myDate.getFullYear();  
			myArray[1] = myDate.getMonth() + 1;  
			myArray[2] = myDate.getDate();  
			myArray[3] = myDate.getHours();  
			myArray[4] = myDate.getMinutes();  
			myArray[5] = myDate.getSeconds();
			if(type == 'array') {
				return myArray;
			} else {
				return {
					year: myArray[0], month: myArray[1], day: myArray[2],
					hour: myArray[3], minute: myArray[4], second: myArray[5]
				}
			}
		},
		add: function(date,strInterval,number){
			var dtTmp = date;  
			switch (strInterval) {   
				case 'second':
				case 's' :
					dtTmp.setTime(dtTmp.getTime() + (1000 * number)); 
					break;	
				case 'minute':
				case 'n' :
					dtTmp.setTime(dtTmp.getTime() + (60000 * number));  
					break;	
				case 'hour':
				case 'h' :
					dtTmp.setTime(dtTmp.getTime() + (3600000 * number)); 
					break;	
				case 'day':							
				case 'd' :
					dtTmp.setTime(dtTmp.getTime() + (86400000 * number)); 
					break;	
				case 'week':							
				case 'w' :
					dtTmp.setTime(dtTmp.getTime() + ((86400000 * 7) * number));
					break;	
				case 'month':
				case 'm' :
					dtTmp.setTime(new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()).getTime());  
					break;	
				case 'year':
				case 'y' :
					dtTmp.setTime(new Date((dtTmp.getFullYear() + number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()).getTime());  
					break;	
			} 
			return dtTmp;
		},
		diff:function(dtStart,dtEnd,strInterval){
			switch (strInterval) {   
				case 'second':
				case 's' :return parseInt((dtEnd - dtStart) / 1000);  
				case 'minute':
				case 'n' :return parseInt((dtEnd - dtStart) / 60000);  
				case 'hour':
				case 'h' :return parseInt((dtEnd - dtStart) / 3600000);  
				case 'day':							
				case 'd' :return parseInt((dtEnd - dtStart) / 86400000);  
				case 'week':							
				case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));  
				case 'month':
				case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);  
				case 'year':
				case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();  
			}  
		},
		toString: function(date,type){
			var obj = Y.Date.translate(date);
			var str = obj.year + '-' + obj.month + '-' + obj.day;
			if(type == 'time') {
				str += " " + obj.hour + ":" + obj.minute + ":" + obj.second;
			}
			return str;
		}			
	}); 	
});                  
                     
})($);               