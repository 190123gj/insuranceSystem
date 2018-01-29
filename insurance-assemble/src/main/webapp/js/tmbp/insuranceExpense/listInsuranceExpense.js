define	(function(require, exports, module) {
	require('zyw/publicPage');
    require('Y-msg');
    var util = require('util');

    var getList = require('zyw/getList');

    var deleteObj = $(".deleteObj");


    $.each(deleteObj,function(i,v){
        var _cur = $(this);
        _cur.click(function(){
            Y.confirm('提示','确认删除参数:'+_cur.attr("paramName")+'吗？',function(opn){
                if (opn == 'yes') {
                    util.ajax({
                        url:'/insurance/insuranceLiability/deleteConfirm.json?',
                        data:{id:_cur.attr("id")},
                        success:function(reg){
                            if (reg.success) {
                                Y.alert("提示信息：",reg.message);
                                $("#fnListSearchForm").submit();
                            } else{
                                Y.alert("错误信息：",reg.message);
                            }
                        }
                    });
                }
            })
        });
    })

});