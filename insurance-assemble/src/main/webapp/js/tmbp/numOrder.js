define(function(require, exports, module) {
	function numOrder(obj,_write){
		obj.each(function(index, el) {
            var _this = $(this);
                _index = _this.index()+1;
                _this.find(_write).text(_index);
        });
	}
	module.exports = numOrder
})