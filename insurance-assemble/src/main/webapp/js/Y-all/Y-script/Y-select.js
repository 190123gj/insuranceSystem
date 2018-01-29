
(function($){

define(function(require){
	require("./Y-base");
    $.extend(Y,{
	    Select: function(param){
		    var el = $(param);
		    if(el.length <= 0) {
			    return el;
			}
			$.extend(el,{
			    selected: function(val,type){
				    var item = el.getOption(val,type);
					if(item) {
					    item[0].selected = true;
					} 
					if(el.isTrans()) {
					    el.jqTrans();
					}
					return el;
				},
				getOption: function(val,type){
				    var result;
				    if(type === 'text') {
					    el.find('option').each(function(i,item){
						    if($(item).html() === val) {
							    result = $(item);
								return false;
							}
						});
					} else {
					    result = el.find("option[value="+val+"]");
					}
					return result;
				},
				isTrans: function(){
				    return el.hasClass('jqTransformHidden');    
				},
				jqTrans: function(){
				    if(!el.isTrans()) {
					    return;
					}
	                var i = el.parents('.jqTransformSelectWrapper').css('z-index');
					el.parent().find('div,ul').remove();
	                el.unwrap().removeClass('jqTransformHidden').jqTransSelect();
	                el.parent().css('zIndex', i);
					return el;
				},
				bindChange: function(handler){
				    el.change(handler);
				    if(el.isTrans()){
					    el.parent().find('ul li a').click(function(e){
						    var val = el.val(),lastVal = el.data('lastValue');
			                if(lastVal && lastVal == val) {
				                return;
				            }
				            el.data('lastValue',val);
				            el.each(function(i,item){
							    handler.call(item,e);
							});
						});
					};
				}
			});
			return el;
		}
	});
});

})($);