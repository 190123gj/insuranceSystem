define(function(require, exports, module) {

    //弹窗提示
    var hintPopup = require('zyw/hintPopup');

	function submited(_submited,noFulfil){

		if(!_submited) return false;
        //console.log(_submited)
        var _arr = _submited.split(''),
            _index = $('.apply-step .item.active').index()+1;

        for(var i=0; i<_arr.length; i++){
            if(_arr[i]=='0'&&i!=7){
                if($('.apply-step .item').eq(i-1).find('em.remind').length){
                    $('.apply-step .item').eq(i-1).find('em.remind').html('<b>请完整该页必填项</b>');
                }else{
                    $('.apply-step .item').eq(i-1).append('<em class="remind"><b>请完整该页必填项</b></em>');
                }
            }else if(_arr[i]=='1'){
                $('.apply-step .item').eq(i-1).find('em.remind').remove();
            }

            if(_arr[i]=='0'&&i==0&&!noFulfil){
                hintPopup('尽职调查报告申明必填项未填写完整');
            }
        }

        return _arr[_index]


	}

	module.exports = submited

})