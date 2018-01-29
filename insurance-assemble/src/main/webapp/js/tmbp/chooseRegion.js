define(function (require, exports, module) {
    // html
    // <div class="m-item Y-selectarea" id="selectAddress">
    //       <label class="m-label"><span class="m-required">*</span>所属区域：</label>
    //       &nbsp;
    //       <span id="addressBox"></span>
    //       <input type="text" class="fn-input-hidden" id="countryCode"> ## 国家
    //       <input type="hidden" id="countryName">
    //       <input type="hidden" id="provinceCode"> ## 省
    //       <input type="hidden" id="provinceName">
    //       <input type="hidden" id="cityCode"> ## 市
    //       <input type="hidden" id="cityName">
    //       <input type="hidden" id="countyCode"> ## 区
    //       <input type="hidden" id="countyName">
    //       <input type="text" class="fn-input-hidden" id="addressResult"> ##是否选到末尾
    //   </div>
    //选择地区
    //模板引擎
    var template = require('arttemplate');
    var $selectAddress = $('#selectAddress'),
        $addressBox = $selectAddress.find('#addressBox'),
        $addressResult = $selectAddress.find('#addressResult'),
        industryBoxTpl = ['<select class="ui-select fn-w148 fnInput">',
            '    <option value="">请选择</option>',
            '    {{each list as item i}}',
            '    <option value="{{item.code}}">{{item.name}}</option>',
            '    {{/each}}',
            '</select>'
        ].join(''),
        industrySelTpl = ['<select class="ui-select fn-w148 fnInput">',
            '    <option value="">请选择</option>',
            '    {{each list as item i}}',
            '    <option value="{{item.code}}"{{(item.code==code)?\' selected="selected"\':\'\'}}>{{item.name}}</option>',
            '    {{/each}}',
            '</select>'
        ].join(''),
        addressArr = [
        //     {
        //     code: $selectAddress.find('#countryCode'),
        //     name: $selectAddress.find('#countryName')
        // },
            {
            code: $selectAddress.find('#provinceCode'),
            name: $selectAddress.find('#provinceName')
        }, {
            code: $selectAddress.find('#cityCode'),
            name: $selectAddress.find('#cityName')
        }, {
            code: $selectAddress.find('#countyCode'),
            name: $selectAddress.find('#countyName')
        }];
    $addressBox.on('change', 'select', function () {
        debugger;
        var _this = $(this);
        $addressResult.val('').trigger('blur');
        //且清楚后面的值 并触发验证
        var _index = _this.index('#addressBox select');
        for (var i = _index; i < addressArr.length; i++) {
            addressArr[i].code.val('').trigger('blur');
            addressArr[i].name.val('');
        };
        // console.log(_this.val())
        // console.log(_this.find('option[value="' + this.value + '"]').text())
        addressArr[_index].code.val(_this.val()).trigger('blur');
        addressArr[_index].name.val(_this.find('option[value="' + this.value + '"]').text());
        //当有做出选择的时候就清空数据
        //每次选择，都去加载下一级数据
        if (!!!this.value) {
            //请选择 直接退出
            return;
        }
        addressToSelect(this.value);
    }).on('change', 'select:not(:last)', function () {
        var _this = $(this);
        //当不是最后一个的时候，把当前后面的下级删了
        _this.nextAll('select').remove();
    }).on('change', 'select:first', function () {
        $(this).nextAll('select').remove();
        for (var i = 1; i < addressArr.length; i++) {
            addressArr[i].code.val('').trigger('blur');
            addressArr[i].name.val('');
        }
    });
    //初始化，是绑定还是还原
    if (addressArr[0].code.val()) {
        addressRestore(addressArr);
    } else {
        addressToSelect("China");
    }

    // 选择后新增下拉框
    function addressToSelect(key) {
        $.getJSON('/baseDataLoad/region.json', {
            parentCode: key || 'China'
        }, function (res, textStatus) {
            if (res.success) {
                // console.log(res.data)
                if (res.data.length && res.data != '[]') {
                    $addressBox.append(template.compile(industryBoxTpl)({
                        list: res.data
                    }));
                } else {
                    $addressResult.val('IS');
                }
                if (!!key) {
                    $addressResult.trigger('blur');
                }
            } else {
                Y.alert('提示', res.message);
            }
        });
    }
    // 还原已选择
    function addressRestore(arr) {
        var _index = 0,
            _parentCode,
            _ajaxArr = [];
        for (var i = 0; i < arr.length; i++) {
            if (i == 0) {
                _ajaxArr.push({
                    code: arr[i].code.val(),
                    parentCode: ''
                });
            } else {
                if (arr[i].code.val()) {
                    _ajaxArr.push({
                        code: arr[i].code.val(),
                        parentCode: arr[i - 1].code.val()
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
                parentCode: obj.parentCode || 'China'
            }, function (res, textStatus) {
                if (res.success) {
                    if (res.data.length && res.data != '[]') {
                        $addressBox.append(template.compile(industrySelTpl)({
                            list: res.data,
                            code: obj.code
                        }));

                        for (var i = 0; i < res.data.length; i++) {
                            if (res.data[i].code == obj.code && res.data[i].hasChildren == 'NO') {
                                $addressResult.val('IS');
                                return;
                            }
                        }

                        $addressResult.trigger('blur');
                    }
                    getSelect(_ajaxArr[++_index]);
                } else {
                    Y.alert('提示', res.message);
                }
            });
        }
    }

});