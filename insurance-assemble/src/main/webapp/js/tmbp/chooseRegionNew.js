define(function(require, exports, module) {
    // html
    // <div class="selectAddress">
    //     <label class="m-label"><span class="m-required">*</span>所属区域：</label>
    //     <span class="addressBox"></span>
    //     <input type="hidden" class="adressCode" name=""> ##存储提交code,格式为China,110000,110100,110105
    // </div>
    //选择地区，支持一个页面多个地址
    //

    //初始化方法
    // var chooseRegion = require('tmbp/chooseRegionNew'); //引入
    // chooseRegion = new chooseRegion();
    // chooseRegion.init();
    //
    //
    //模板引擎
    var template = require('arttemplate');
    var $selectAddress = $('.selectAddress'),
        $addressBox = $('.addressBox'),
        industryBoxTpl = ['<select class="ui-select fn-w148">',
            '    <option value="NONE">请选择</option>',
            '    {{each list as item i}}',
            '    <option value="{{item.code}}">{{item.name}}</option>',
            '    {{/each}}',
            '</select>'
        ].join(''),
        industrySelTpl = ['<select class="ui-select fn-w100">',
            '    <option value="NONE">请选择</option>',
            '    {{each list as item i}}',
            '    <option value="{{item.code}}"{{(item.code==code)?\' selected="selected"\':\'\'}}>{{item.name}}</option>',
            '    {{/each}}',
            '</select>'
        ].join('');

    var addressArr = {};//缓存对应code

    function chooseRegion(){
    	// this.init();
    };
    chooseRegion.prototype = {
    	init: function (obj) {
    		this.obj = obj || $('body');
    		this.callback = obj || this.callback;
	    	this.obj.find('.selectAddress').each(function(index, el) {
		        $(this).attr('dataindex',index);//用于标记当前BOX，以便其他操作识别为哪一个BOX
		        addressArr[index] = getAddressArr($(this));

		        if (!!addressArr[index][0]) {
		            addressRestore($(this),addressArr[index]);
		        } else {
		            addressToSelect($(this),'');
		        };

		    });
		    $('body').bind('addressBox select','click');
    	},
    	callback:function () {
    		if(this.callback) this.callback();
    	}

    };
    //初始化，是绑定还是还原
    // $selectAddress.each(function(index, el) {
    //     $(this).attr('dataindex',index);//用于标记当前BOX，以便其他操作识别为哪一个BOX
    //     addressArr[index] = getAddressArr($(this));

    //     if (!!addressArr[index][0]) {
    //         addressRestore($(this),addressArr[index]);
    //     } else {
    //         addressToSelect($(this),'');
    //     }
    // });

    //绑定事件
    $('body').on('change', '.addressBox select', function() {
        var _this = $(this);
        var $thisSelectAddress = _this.parents('.selectAddress');
        var i = $thisSelectAddress.attr('dataindex');
        var index = _this.index();

        if (!!!_this.val()) {
			//请选择 直接退出
			return;
		};
        addressArr[i].length = index; //重新选择时，截断剩余code
        _this.nextAll('select').remove(); //重新选择时，移除后面下拉框
		addressArr[i][index] = _this.val();//缓存当前下拉框code

        if (index < 3 && addressArr[i].indexOf('China') >= 0) { //最后一个下拉框把code放到页面
        	$thisSelectAddress.find('.adressCode').val('');
        }else {
        	$thisSelectAddress.find('.adressCode').val(addressArr[i]);//从缓存拿code存入到对应的name
        }
        addressToSelect($thisSelectAddress,_this.val());
    })
     //    .on('change', '.addressBox select:not(:last)', function() {
     //    var _this = $(this);
     //    //当不是最后一个的时候，把当前后面的下级删了
     //    _this.nextAll('select').remove();
    // }).on('change', '.addressBox select:first', function() {
	// 	$(this).nextAll('select').remove();
	// });

    //获取当前下拉框的值,还原时候用
    function getAddressArr($ele) {
        var data = $ele.find('.adressCode').val().split(',');
        return data;
    };

    // 选择后新增下拉框
    function addressToSelect($container,key) {
        $.getJSON('/baseDataLoad/region.json', {
            parentCode: key||'China'
        }, function(res, textStatus) {
            if (res.success) {
                if (res.data.length && res.data != '[]') {
                    $container.find('.addressBox').append(template.compile(industryBoxTpl)({
                        list: res.data
                    }));
                } else {
                    // $container.find('.addressResult').val('IS');
                }
                // $container.find('.addressResult').trigger('blur');
            } else {
                Y.alert('提示', res.message);
            }
        });
    };
    // 还原已选择
    function addressRestore($container,arr) {
        var _index = 0,
            _parentCode,
            _ajaxArr = [];
        for (var i = 0; i < arr.length; i++) {
            if (i == 0) {
                _ajaxArr.push({
                    code: arr[i],
                    parentCode: ''
                });
            } else {
                if (arr[i]) {
                    _ajaxArr.push({
                        code: arr[i],
                        parentCode: arr[i - 1]
                    });
                }
            }
        }

        getSelect(_ajaxArr[_index]);

        function getSelect(obj) {
            if (_ajaxArr.length == _index) {
                return;
            }
            $.getJSON('/baseDataLoad/region.json', {
                parentCode: obj.parentCode
            }, function(res, textStatus) {
                if (res.success) {
                    if (res.data.length && res.data != '[]') {
                        $container.find('.addressBox').append(template.compile(industrySelTpl)({
                            list: res.data,
                            code: obj.code
                        }));

                        for (var i = 0; i < res.data.length; i++) {
                            if (res.data[i].code == obj.code && res.data[i].hasChildren == 'NO') {
                                // $container.find('.addressResult').val('IS');
                                return;
                            }
                        }

                        // $container.find('.addressResult').trigger('blur');
                    }
                    getSelect(_ajaxArr[++_index]);
                } else {
                    Y.alert('提示', res.message);
                }
            });
        }
    };

    module.exports = chooseRegion;

});