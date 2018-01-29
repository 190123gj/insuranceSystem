define(function(require) {
	$.fn.extend({
		requiredRulesSharp: function(exp, _reverse, obj, Fun) {
			var _rules = obj,
				_reverses = _reverse ? _reverse : false;
			if (exp && !$.isArray(exp)) exp = exp.split(',');
			this.find('input,textarea,select').each(function() {
				var name = $(this).attr('name');
				if (name != undefined && name.indexOf("].") > 0) {
					name = name.match(/.*\.(.*)/)[1];
				}
				var _WhetherToReverse = _reverse ? ($.inArray(name, exp) != -1) : !($.inArray(name, exp) != -1);
				if (exp) {
					if (_WhetherToReverse) return;
				};
				Fun(obj, name, $(this));
			});
			return _rules;
		}
	});

});