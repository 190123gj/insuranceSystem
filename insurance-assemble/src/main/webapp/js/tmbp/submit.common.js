/**
 * Created by eryue
 * Create in 2016-12-28 17:15
 * Description: 若页面只有一个表单，form元素和提交按钮的位置可以是任意位置；如页面存在多个表单，那么提交按钮必须在form元素内
 */

'use strict';
define(function(require, exports, module) {

    var validateInit = require('tmbp/validate.common');
    var util = require('util');

    var $body = $('body');
    var $form = $('#form');

    //-----------------
    $(function () {
        $body.on("change",$form,function () {
            var $this = $(this);
            var validLength = $this.find(".fn-validate").length;
            for(var i = 0;i<validLength;i++){
                var validVal = $(".fn-validate").eq(i).val();
                if(validVal != ""){
                    $(".fn-validate").eq(i).removeClass("error-tip");
                }else {
                    $(".fn-validate").eq(i).addClass("error-tip");
                }
            }
        });

        $body.on("change",".selectFn",function () {
            alert($("#selectedId").val());
            var $this = $(this);
            $this.val();
        });
    });

    //-----------------

    if($form.length > 0) validatForm($form);
    //-------场所 停车位
    function carValid() {
        var m_companyName = $(".mainInsurance_add .insuranceName_add");
        var p_companyName = $(".protocolFees_add .insuranceName_add");
        var parkingNumVal = $(".parkingNum").val();
        var indoorVal = $(".indoor").val();
        var outdoorVal = $(".outdoor").val();
        var tempdoorVal = $(".tempdoor").val();
        var motodoorVal = $(".motodoor").val();
        var elevatorVal = $(".elevator").val();
        var straightVal = $(".straight").val();
        var escalatorVal = $(".escalator").val();
        var swimPoolVal = $(".swimPool").val();
        var straightPoolVal = $(".straightPool").val();
        var outSourcePoolVal = $(".outSourcePool").val();
        if(parkingNumVal != undefined && (parseInt(parkingNumVal) != parseInt(indoorVal)+parseInt(outdoorVal)+parseInt(tempdoorVal)+parseInt(motodoorVal))){
            Y.alert('提示','停车场车位数不准确！');
            return false;
        }
        if(elevatorVal != undefined && (parseInt(elevatorVal) != parseInt(straightVal)+parseInt(escalatorVal))){
            Y.alert('提示','停车场电梯数不准确！');
            return false;
        }
        if(swimPoolVal != undefined && (parseInt(swimPoolVal) != parseInt(straightPoolVal)+parseInt(outSourcePoolVal))){
            Y.alert('提示','停车场泳池数不准确！');
            return false;
        }
        for(var i = 0; i<m_companyName.length;i++){
            if(m_companyName.eq(i).text() != p_companyName.eq(i).text()){
                Y.alert('提示','协议经纪费与意向经纪费保险不匹配！');
                return false;
            }
        }
    }

    $body.on('click', '.submitBtn',function () {
        var $self = $(this);
        $form = $self.parents('form').length == 0 ? $form : $self.parents('form');
        //防重复提交
        if($form.hasClass('isSubmiting')) return;

        var url = $form.attr('action');
        var method = !!$form.attr('method') ? $form.attr('method') : 'POST';
        var isProcess = !!$form.attr('process') ? $form.attr('process') : false;
        var DRAFT = $self.hasClass('DRAFT');//暂存
        // console.log('DRAFT：' + DRAFT);
        var callbackHref = !!$form.attr('callbackhref') ? $form.attr('callbackhref') : window.location.href;


        //防止部分name没有diyname和add rules
        validatForm($form);

        // console.log($form);
        //暂存时不验证数据
        if (!DRAFT && !$form.valid()) {
            var carValidate = carValid();
            var errTop = $form.find(".fn-validate.error-tip").eq(0).offset().top;
            $('body,html').animate({ scrollTop: errTop }, "slow");
            return;
        }
        if(!DRAFT&&(carValidate != undefined) && !carValidate){
            return false;
        }

        if(!url) return;
        //validate验证通过之后添加一个防重复提交标记
        $form.addClass('isSubmiting');

        $.ajax({
            url: url,
            type: method,
            data: $form.serialize(),
            success: function(res) {
                // $form.removeClass('isSubmiting');
                if (!res.success || DRAFT) {//如果失败，或者是暂存
                    Y.alert('提醒', res.message,function () {
                        //如果是失败就值提示失败消息
                        if(!res.success) return;
                        //暂存的操作
                        var formIdVal = $form.find('[name="formId"]').val();
                        var _isFirstsave = (!!!formIdVal || formIdVal == 0) ? true : false;
                        if(DRAFT && _isFirstsave){//如果是新增页面的暂存操作，暂存后统一跳转到当条新增数据的编辑页面
                            var DRAFThref = window.location.pathname.split('/');
                            DRAFThref[DRAFThref.length - 1] = 'edit.htm?formId=' + res.form.formId;
                            DRAFThref = DRAFThref.join('/');
                            // console.log('暂存跳转到: ' + DRAFThref);
                            window.location.href = DRAFThref;
                        };
                    });

                } else {
                    if(isProcess) {//流程提交
                         console.log('进入流程提交');
                        util.postAudit(res,function(processRes) {
                            console.log('流程提交成功');
                            util.doBack();
                        },function(processRes) {//失败
                            console.log('流程提交失败');
                        });
                        return;
                    };
                     console.log('一般提交');
                    Y.alert('提醒', res.message, function() {
                        window.location.href = callbackHref;
                    });
                };

            },
            complete: function (res) {//完成后去掉防重复提交标记
                $form.removeClass('isSubmiting');
            }

        })
    });

    //创建一个validate实例
    var formValidateRule = '';
    window.formValidateRuleALL = '';
    function validatForm($thisForm) {
        // if(!formValidateRule){
            formValidateRule = new validateInit({
                submitBtnClass: 'submitBtn',
                formObj: $thisForm,//jq对象
                ignoreClass: '',//需要排除验证元素的class
                validateEle: 'fn-validate',//需要验证元素的obj
                validateRules: {}
            });
        // window.formValidateRuleALL = formValidateRule;
    //     }else {
    //     formValidateRule.diyName(formValidateRule.addRules());
    // }
    };
    $body.on('change','[name*="longTime"]',function () {
        var $this = $(this);
        if($this.is(':checked') == true){
            $this.parent().siblings("input").val("").attr("disabled",true);
        }else {
            $this.parent().siblings("input").removeAttr("disabled");
        }
    });
    $(function(){
        var $this = $('[name*="longTime"]');
        for(var i = 0; i<$this.length;i++){
            if($this.eq(i).is(':checked') == true){
                $this.eq(i).parent().siblings("input").val("").attr("disabled",true);
            }else {
                $this.eq(i).parent().siblings("input").removeAttr("disabled");
            }
        }
    });
})