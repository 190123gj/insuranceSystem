define(function(require, exports, module) {
	//项目管理>授信前管理> 立项申请
	require('Y-msg');
	require('zyw/upAttachModify');
	require('zyw/chooseRegion');
    require('tmbp/submit.common');
    require('tmbp/operate.common');

    var $body = $('body');

    $body.on('click','.chooseEndDate .radio',function () {
        var $this = $(this);
        if($this.hasClass('otherDays')) {
            $this.parents('.chooseEndDate').find('.otherDaysInput').removeAttr('readonly').removeClass('ignore').val('').blur()
        }else {
            $this.parents('.chooseEndDate').find('.otherDaysInput').attr('readonly','readonly').addClass('ignore').val('').blur()
        }
    }).on('blur','.sortOrder',function () {
        var $this = $(this);
        var val = $this.val();
        var notNum = isNaN(val);
        if((!val || notNum) && val != 0) return;//如果为空就中断
        if(val.indexOf('.') > 0) {//不为整数则清空
            Y.alert('提示','顺序必须为非负整数，值越小越优先执行哦！',function () {
                $this.val('');
            });
            return;
        };
        var $otherSortOrders = $this.parents('tr').siblings('tr').find('.sortOrder');
        $.each($otherSortOrders,function () {
            if(val == $(this).val()){
                Y.alert('提示','顺序不可重复，值越小越优先执行哦！',function () {
                    $this.val('');
                });
                return false;
            };
        })
    }).on('click','.remindMenbers .checkbox', function () {
        var $this = $(this);
        var $thisTd = $this.parents('td');
        var $thisTdCheckedBoxs = $thisTd.find('.checkbox');
        var name = $this.attr('name');
        var countMenbers = 0;

        $.each($thisTdCheckedBoxs,function (index,ele) {
            if($(this).prop('checked')) countMenbers++;
        });

        if($thisTdCheckedBoxs.length == countMenbers) {
            $thisTd.append('<input type="hidden" name="' + name + '" value="ALL" class="countMenbers">');
            $thisTdCheckedBoxs.removeAttr('name')
        }else {
            $thisTdCheckedBoxs.attr('name',$thisTd.find('.countMenbers').attr('name'));
            $thisTd.find('.countMenbers').remove();
        }


    })
});