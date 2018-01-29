/**
 * Created by eryue
 * Create in 2016-12-28 17:15
 * Description:
 *
 */

'use strict';
define(function(require, exports, module) {

    var selectType = require('tmbp/selectType');

    var $body = $('body');
    var $form = $('#form');

    $body.on('click','.addLine',function () {//新增一行
        var $self = $(this);
        var templateId = $self.attr('templateid');
        if(!templateId) return;
        var maxItems = $self.attr('maxitems') || false;
        var $itemBox = $self.parents('.itemBox').find('.itemsList');
        var $template = $($('#' + templateId).html());
        var itemBoxListLength = $itemBox.find('.itemLine').length;
        var cb = $self.attr('cbname');
        $template.find('.fn-validate').removeClass('ignore');//移除验证

        $template.addClass('newItemList');//标记是新增的
        $self.parents(".diyNameBox").find(".cardClass").show();
        if(itemBoxListLength >= maxItems && !!maxItems) {
            Y.alert('提示','不能超过' + maxItems + '条');
            return;
        };
        $template.find('.itemListIndex').val(itemBoxListLength + 1);
        $template.find('.itemListIndex').parents('tr').attr('targetlist','LIST_' + (itemBoxListLength + 1))
        if($itemBox.find('tr.resltTr').length == 1){
            $itemBox.find('tr.resltTr').before($template)
        }else {
            console.log($itemBox.find('tbody').length);
            // $itemBox.append($template);
            if($itemBox.find('tbody').length == 1 ) {//&& $itemBox.find(".newItemList").length<1
                $itemBox.find('tbody').append($template);
            }else {
                $itemBox.append($template);
                //---------------------
                // $self.attr('templateid',templateId+"_1");
                // $self.parent().siblings("div").removeClass("itemsList").find("table").addClass("itemsList");
                // $self.parent().siblings("div").find("div").removeAttr("diyname").removeClass("itemLine newItemList").find("tr").eq(0).nextAll("tr").addClass("itemLine newItemList").attr("diyname","certOrders");
            }
        };
        if($itemBox.find('tr:last').find('.selectFnBox').length > 0) {
            new selectType({
                selectBoxObj: $itemBox.find('tr:last').find('.selectFnBox')
            });
        };
        if(!!cb) window[cb]($self);
        //---------------------------
        var $customerName = $self.parents("form").find("[name*='customerName']").val();
        $self.parent().siblings("table").find("[name*='bankCardName']").val($customerName);
        //---------------------------
        diyName();
    }).on('click','.deleteLine',function () {//删除一行
        var $self = $(this);
        var $selfItem = $self.parents('.itemLine');
        var $itemBox = $self.parents('.itemsList');
        var residueItems = $itemBox.find('.itemLine').length;
        var minLength = $self.attr('minlength') || 1;
        var cb = $self.attr('cbname');
        var index = $self.parents('table').find('tr').index($self.parents('tr'));
        // console.log(minLength)
        if(residueItems <= minLength) {
            Y.alert('提示','至少保留一条！');
            return;
        };
        $selfItem.remove();
        if($itemBox.find('.itemListIndex').length > 0){
            $.each($itemBox.find('.itemLine'),function (i,el) {
                $(el).find('.itemListIndex').val(i+1);
                $(el).attr('targetlist','LIST_' + (i + 1))
            })
        };
        if(!!cb) window[cb](index);
        diyName();
    }).on('click','.delTrData',function () {//删除一行
        var $self = $(this);
        var url = $self.attr('opthref');

        Y.alert('提示','是否确认删除？',function () {
            $.ajax({
                url:url,
                success:function (res) {
                    if(res.success){
                        Y.alert('提示',res.message,function () {
                            window.location.href = window.location.href;
                        });
                    }else {
                        Y.alert('提示',res.message);
                    }
                }
            })
        })
    });

    //序列化name

    function diyName($box) {

        if(!$box) $box = $form;
        var $diyNameBox = $box.find('.diyNameBox');
        var $parnetDiyName = $('[parentdiyname]');
        // console.log($parnetDiyName)
        // $.each($parnetDiyName,function (_indexWarp,elWarp) {
        //
        //     var $thisBox = $(this);
        //     var parentDiyName = $thisBox.attr('parnetdiyname');
        //
        //     //找到普通[name]的元素
        //     $thisBox.find('[name]').each(function (_index1,el) {
        //         if($(el).parents('[diyname]').length > 0) return;
        //         var name = $(el).attr('name');
        //         if (name.indexOf('.') > 0) {
        //             name = name.substring(name.lastIndexOf('.') + 1);
        //         };
        //
        //         name = parentDiyName + '[' + _indexWarp + '].' + name;
        //         // console.log(name)
        //         $(el).attr('name', name);
        //         addRules($(el));
        //     });
        //
        //     //找到需要被多次[diyname]的元素
        //     $thisBox.find('[diyname]').each(function(index,ele) {
        //         // console.log(index)
        //         var $thisDiyName= $(this);
        //         var diyName = $thisDiyName.attr('diyname');
        //         console.log('111111111')
        //         var noParentDiyName = $thisBox.hasClass('noParentDiyName');
        //         parentDiyName = !parentDiyName ? '' : parentDiyName + '[' + _indexWarp + '].';
        //         console.log(parentDiyName)
        //
        //         $thisDiyName.find('[name]').each(function() {
        //             console.log(333333333333)
        //             var _this = $(this),
        //                 name = _this.attr('name');
        //
        //             if (name.indexOf('.') > 0) {
        //                 name = name.substring(name.lastIndexOf('.') + 1);
        //             };
        //
        //             name = parentDiyName + diyName + '[' + index + '].' + name;
        //             // console.log(name)
        //             _this.attr('name', name);
        //             if(index == $thisBox.find('[diyname]').length - 1){
        //                 addRules($thisBox.find('[diyname]'));
        //             };
        //
        //         });
        //
        //     });
        // })
        $.each($diyNameBox,function () {
            var $thisBox = $(this);
            var noParentDiyName = $thisBox.hasClass('noParentDiyName');
            var warpName = '';
            if(!noParentDiyName){
                var $thisParentDiyName = $thisBox.parents('[parentdiyname]');
                if($thisParentDiyName.length > 0) {
                    var _indexWarp = $parnetDiyName.index($thisParentDiyName);
                    warpName = $thisParentDiyName.attr('parentdiyname');
                    warpName = !warpName ? '' : warpName + '[' + _indexWarp + '].';
                }
            }

            $thisBox.find('[diyname]').each(function(index,ele) {
                // console.log(index)
                var $thisDiyName= $(this);
                var diyName = $thisDiyName.attr('diyname');

                $thisDiyName.find('[name]').each(function() {

                    var _this = $(this),
                        name = _this.attr('name');

                    if (name.indexOf('.') > 0) {
                        name = name.substring(name.lastIndexOf('.') + 1);
                    };

                    name = warpName + diyName + '[' + index + '].' + name;
                    // console.log(name)
                    _this.attr('name', name);
                    if(index == $thisBox.find('[diyname]').length - 1){
                        addRules($thisBox.find('[diyname]'));
                    };

                });

            });
        })

    };
    function addRules ($addObj) {
        var $validateList;
        if(!$addObj || $addObj.length == 0){
            $validateList = $addObj.find('.fn-validate');
        }else {
            var isName = !!$addObj.attr('name');
            if(isName){
                $validateList = $addObj;
            }else {
                $validateList = $addObj.find('.fn-validate');
            }
        };
        $validateList.each(function (i, e) {
            var _this = $(this);
            _this.rules("remove");
            _this.rules('add', {
                required: true,
                messages: {
                    required: '必填'
                }
            });
        })
    };

//-------------微信QQ添加
//     $body.on('click','.addSignal',function () {
//         var $this = $(this);
//         var thisName = $this.siblings('input').eq(0).attr('id');
//         var thisLabelName = $this.siblings('label').eq(0).html();
//         thisLabelName = thisLabelName.split('：');
//         var index =0 ;
//         var nameLength = $this.parents('form').find('[name="'+thisName+'"]').length;
//         index = index<nameLength?nameLength:++index;
//         console.log(index);
//         var addInput = '<div class="m-item '+thisName+'"><label class="m-label">'+thisLabelName[0]+index+'：</label><input class="ui-text fn-w200 fnInput " type="text" name="'+thisName+'" value="" />'
//             +'<a class="ui-btn ui-btn-fill ui-btn-danger removeSignal" href="javascript:void(0);"><i class="icon icon-del"></i></a></div>';
//         var thisClass = $this.parent().siblings('div').hasClass(thisName);
//         if(thisClass){
//             $this.parent().siblings("."+thisName).eq(index-2).after(addInput);
//         }else{
//             $this.parent().after(addInput);
//         }
//     });
//     $body.on('click','.removeSignal',function () {
//         var $this = $(this);
//         var thisName = $this.siblings('input').eq(0).attr('name');
//         var nameLength = $this.parents('form').find("."+thisName).length;
//         $this.parents('form').find("."+thisName).eq(nameLength-1).remove();
//     });
    //--------------------------
    $body.on('click','.addSignal',function () {

        var $this = $(this);
        var thisDiyName = $this.parent().siblings('div').find('div').attr('diyname');
        var nameLength = $this.parents('form').find('[name*="'+thisDiyName+'"]').length;
        var addInput = '<div diyname = "'+thisDiyName+'"><input class="ui-text fn-w200 fnInput "style="margin-bottom: 5px" type="text" name="way" value="" /></div>';
        $this.siblings('.removeSignal').show();
        $this.parent().siblings('div').find('div').eq(nameLength-1).after(addInput);
        var thisHeight = $this.parent().siblings('div').height();
        var thisTop = $this.parent().height();
        $this.parent().css('margin-top',thisHeight-thisTop-5);
        diyName();
    });
    $body.on('click','.removeSignal',function () {
        var $this = $(this);
        var thisDiyName = $this.parent().siblings('div').find('div').attr('diyname');
        var nameLength = $this.parents('form').find("[name*='"+thisDiyName+"']").length;
        if(nameLength<=1){
            Y.alert('提示','至少保留一条！');
            $this.hide();
        }else {
            $this.parents('form').find("[diyname*='"+thisDiyName+"']").eq(nameLength-1).remove();
            var thisHeight = $this.parent().siblings('div').height();
            var thisTop = $this.parent().height();
            $this.parent().css('margin-top',thisHeight-thisTop-5);
            if(nameLength==2){
                $this.hide();
            }

        }
    });
    $(function () {
        var $this = $(".addSignal");
        for(var i = 0; i<$this.length; i++){
            var thisHeight = $this.eq(i).parent().siblings('div').height();
            var thisTop = $this.eq(i).parent().height();
            $this.eq(i).parent().css('margin-top',thisHeight-thisTop-5);
            var inputLength = $this.eq(i).parent().siblings(".addInput").find("div").length;
            if(inputLength<=1){
                $this.eq(i).siblings(".removeSignal").hide();
            }
        }
    });
//    module.exports = selectType;
})