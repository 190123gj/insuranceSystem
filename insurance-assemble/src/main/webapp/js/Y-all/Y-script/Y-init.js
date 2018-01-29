
(function($){

define(function(){
    if(Y.isCmpInit) return;
    var cmpInit = {

	}
	for(var cmp in cmpInit) {
	    if(Y[cmp]) {
		    cmpInit[cmp]();
			delete cmpInit[cmp];
		}
	}
	Y.cmpReady(function(name){
	    if(cmpInit[name]) cmpInit[name]();
	});
	Y.isCmpInit = true;
});

})($);