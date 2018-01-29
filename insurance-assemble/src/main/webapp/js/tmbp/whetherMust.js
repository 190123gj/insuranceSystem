define(function(require, exports, module) {
	function whetherMust(rulesAll,bool){
		for( i in rulesAll){
			if(rulesAll[i].messages.required)rulesAll[i].required = bool;
	    }
	}
	module.exports = whetherMust
})