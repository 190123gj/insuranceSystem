define	(function(require, exports, module) {
    require('Y-msg');
    require('tmbp/submit.common');

    var $body = $('body');
	var fnListSearchBtn = $("#fnListSearchBtn");
	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});
    $body.on('click','.chargesBalance', function () {
        var $this = $(this);
        $this.parents('tr').addClass('checkedItem');
        var content = $('#CHARGESBALANCE').html();
        $body.Y('Window', {
            content: content,
            modal: true,
            key: 'modalwnd',
            simple: true,
            closeEle: '.closeBtn',
            close: function () {
                $this.parents('tr').removeClass('checkedItem');
            }
        });
        var modalwnd = Y.getCmp('modalwnd');
        countPay($('table'),modalwnd.wnd,'checkedItem');
    });

    //统计
    function countPay($countBox,$resultBox,countClassTag) {

        var $countArea = $countBox.find('.countArea');
        var $countResultArea = $resultBox.find('.countResultArea');
        var $countItems = !countClassTag ? $countArea.find('tr') : $countArea.find('tr.' + countClassTag);
        var countResult = {};
        $.each($countItems,function (index,ele) {

            var $thisCountItems = $(this).find('[targetname]');
            // console.log($thisCountItems)
            $.each($thisCountItems,function () {
                var $this = $(this);
                var name = $this.attr('targetname');
                var type = $this.attr('type');
                var thisVal;
                if(type == 'text' || type == 'hidden'){
                    thisVal = $this.val().replace(/\s/g, '');
                }else {
                    thisVal = $this.html().replace(/\s/g, '');
                };
                countResult[name] = !countResult[name] ? parseFloat(thisVal) : parseFloat(countResult[name]) + parseFloat(thisVal);

                // console.log(countResult[name])
                if(index == $countItems.length - 1){
                    var $resultItem = $countResultArea.find('.' + name);
                    if($resultItem.length == 0) return;
                    var resultType = $resultItem.attr('type');
                    if(resultType == 'text' || resultType == 'hidden'){
                        $resultItem.val(countResult[name])
                    }else {
                        $resultItem.html(countResult[name])
                    };
                };

            });
        })

    }
});