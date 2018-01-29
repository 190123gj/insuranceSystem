define(function(require, exports, module) {

	function orderName() {
        $("[orderName]").each(function() {
            var tr = $(this),
                orderName = tr.attr("orderName"),
                index = tr.index();
            tr.find("[name]").each(function() {
                var _this = $(this),
                    name = _this.attr("name");
                if (name.indexOf(".") > 0) {
                    name = name.substring(name.indexOf(".") + 1);
                }
                name = orderName + "[" + index + "]." + name;
                _this.attr("name", name);
            });
        });
    }
    module.exports = orderName;

});