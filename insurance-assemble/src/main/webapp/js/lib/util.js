define(function (require, exports, module) {
    //Y对话框
    require('Y-msg');

    var base64 = require('base64').Base64;
    //通用方法集合
    //
    //
    //
    //
    //------ 获取url中的全部或某个参数值 start
    /**
     * [getParam 从url中获取全部或某个参数值]
     * @param  {[type]} e [参数键名]
     * @return {[type]}   [某个键名或整个参数对象]
     */
    function getParam(e) {
        var t = window.location.href,
            n = t.indexOf('?'),
            a = t.indexOf('#');
        if (-1 == n)
            return e ? '' : {};
        for (var r = t.substring(n + 1, -1 == a ? t.length : a), i = r.split('&'), o = {}, s = 0; s < i.length; s++) {
            var d = i[s].split('=');
            o[decodeURIComponent(d[0])] = decodeURIComponent(d[1])
        }
        return e ? o[e] : o
    }
    //------ 获取url中的全部或某个参数值 end
    //
    //
    //
    //------ 反序列化 start
    function deserialization(str, key) {
        var l = str.split('&'),
            o = {};
        for (var i = 0; i < l.length; i++) {
            var d = l[i].indexOf('=');
            o[decodeURIComponent(l[i].substring(0, d))] = decodeURIComponent(l[i].substring(d + 1))
        }
        return key ? o[key] : o
    }
    //------ 反序列化 end
    //
    //
    //
    //
    //------ 循环提交ajax start
    function loopAjax(arr, cb) {
        var _index = 0,
            _max = arr.length;
        _ajax(arr[0]);

        function _ajax(obj) {
            if (_index == _max) {
                cb();
                return;
            }
            $.ajax({
                url: obj.url,
                type: 'POST',
                dataType: 'json',
                data: obj.data,
                complete: function (res) {
                    _index++;
                    _ajax(arr[_index]);
                }
            });
        }
    }

    //------ 循环提交ajax end
    //
    //
    //
    //------ 获得某jQuery对象是否有该class，并返回参数的某个属性 start
    /**
     * [getJqHasClass 当前jQuery对象的class，是否有obj中的某个键名]
     * @param  {[type]} $el [需判断的jQuery对象]
     * @param  {[type]} obj [对比的object对象]
     * @return {[type]}     [当前键名(class)]
     */
    function getJqHasClass($el, obj) {
        var key;
        for (var k in obj) {
            if ($el.hasClass(k)) {
                key = k;
            }
        }
        return key;
    }
    //------ 获得某jQuery对象是否有该class，并返回参数的某个属性 end
    //
    //
    //
    //------ 对比两个对象是否相等 start
    function isObjectValueEqual(A, B) {
        //form http://www.cnblogs.com/lmh2072005/archive/2012/08/02/2619903.html
        if (A instanceof Array) {
            if (!(B instanceof Array)) {
                return false;
            }
            var aLen = A.length,
                bLen = B.length;
            if (aLen != bLen) {
                return false;
            }
            var isEqual = true,
                num = 0;
            for (var i = 0; i < aLen; i++) {
                if (A[i] != B[i] && typeof A[i] == 'object' && typeof B[i] == 'object') {
                    isEqual = arguments.callee.apply(null, [A[i], B[i]]);
                    //isEqual = compareobj(A[i],B[i]);
                } else {
                    isEqual = A[i] === B[i];
                }
                if (isEqual) {
                    num++;
                }
            }
            if (num != aLen) {
                return false;
            } else {
                return true;
            }
        } else if (A instanceof Date) {
            if (!(B instanceof Date)) {
                return false;
            } else {
                return A.getTime() == B.getTime();
            }
        } else if (A instanceof Object) {
            if ((B instanceof Array) || (B instanceof Date) || !(B instanceof Object)) {
                return false;
            } else {
                var aLen = bLen = 0;
                for (var i in A) {
                    aLen++;
                }
                for (var i in B) {
                    bLen++;
                }
                if (aLen != bLen) {
                    return false;
                }
                var isEqual = true,
                    num = 0;
                for (var i in A) {
                    if (typeof A[i] == 'object' && typeof B[i] == 'object' && A[i] != B[i]) {
                        isEqual = arguments.callee.apply(null, [A[i], B[i]]);
                        // isEqual = compareobj(A[i],B[i]);
                    } else {
                        isEqual = A[i] === B[i];
                    }
                    if (isEqual) {
                        num++;
                    }
                }
                if (num != aLen) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            if (B instanceof Object) {
                return false;
            } else {
                return A === B;
            }
        }
    }
    //------ 对比两个对象是否相等 end
    //
    //
    //
    //
    //------ 页面加载的loading start
    function loading() {
        this.$box = $('<div class="m-modal-box util-loading" style="z-index:99999"><div class="m-modal-overlay"></div><div class="m-modal m-ajax-loading"></div></div>');
    }
    loading.prototype = {
        constructor: loading,
        open: function () {
            this.$box.appendTo('body');
        },
        close: function () {
            this.$box.remove();
        }
    };
    //------ 页面加载的loading end
    //
    //
    //
    //------ 通用ajax方法 start
    function ajax(obj) {
        var isLoading = new loading();
        isLoading.open();

        $.ajax({
            url: obj.url + ((obj.url.indexOf('?') >= 0) ? '&' : '?') + '_time=' + (new Date()).getTime(),
            type: 'POST',
            async: obj.async,
            data: obj.data,
            dataType: 'json',
            success: function (res) {
                obj.success(res);
            },
            error: function (res) {
                Y.alert('接口报错', '服务器异常，刷新浏览器重试~');
            },
            complete: function () {
                isLoading.close();
            }
        });
    }
    //
    //
    //------ 通用ajax方法 end
    //
    //
    //
    //------ 验证身份证 start
    function checkIdcard(idcard) {
        //form validate.extend.js
        var Errors = new Array("验证通过!", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!", "身份证号码校验错误!", "身份证地区非法!");
        var area = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外"
        }
        var retflag = false;
        var idcard, Y, JYM;
        var S, M;
        var idcard_array = new Array();
        idcard_array = idcard.split("");
        //地区检验
        if (area[parseInt(idcard.substr(0, 2))] == null) return Errors[4];
        //身份号码位数及格式检验
        switch (idcard.length) {
            case 15:
                if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
                } else {
                    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
                }

                if (ereg.test(idcard)) {

                    return Errors[0];
                } else {
                    return Errors[2];
                }
                break;
            case 18:
                //18位身份号码检测
                //出生日期的合法性检查 
                //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
                //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
                if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
                } else {
                    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
                }
                if (ereg.test(idcard)) { //测试出生日期的合法性
                    //计算校验位
                    S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
                    Y = S % 11;

                    M = "F";

                    JYM = "10X98765432";
                    M = JYM.substr(Y, 1); //判断校验位
                    if (M == idcard_array[17]) return Errors[0]; //检测ID的校验位
                    else return Errors[3];
                } else return Errors[2];
                break;
            default:
                return Errors[1];
                break;
        }
    }
    //
    //------ 验证身份证 end
    //
    //
    //
    //------ 获取多级菜单 start
    function getMultilevel(obj) {
        // url data sublist insert liTpl ulTpl callback
        ajax({
            url: obj.url,
            data: obj.data,
            success: function (res) {
                if (res.success) {
                    //数据到底是res.data还是res.data.list傻傻分不清楚
                    obj.insert(getHtml(res.data.length ? res.data : res.data.list));
                    obj.callback(res);
                } else {
                    Y.alert('提示', res.message);
                }
            }
        });

        function getHtml(arr) {
            if (!!!arr || arr == '[]') {
                return '';
            }
            var _arr = arr;
            var _html = '',
                _subHtml = '';
            for (var i = 0; i < _arr.length; i++) {
                if (_arr[i][obj.sublist] && _arr[i][obj.sublist].length) {
                    _subHtml = getHtml(_arr[i][obj.sublist]);
                } else {
                    _subHtml = '';
                }
                _html += obj.liTpl(_arr[i], i, _subHtml, _arr);
            }
            return obj.ulTpl(_html, i);
        }
    }
    //------ 获取多级菜单 end
    //
    //
    //
    //------ 是否是图片 start
    /**
     * [isImg 给一个文件名，判断是否是图片]
     * @param  {[type]}  fileName [文件名]
     * @return {Boolean}          [description]
     */
    function isImg(fileName) {
        var _fileName = fileName || '',
            _fileNameArr = _fileName.split('.'),
            _type = _fileNameArr[(_fileNameArr.length - 1)].toLowerCase(), //文件名.后面最后为文件类型
            _imgType = ['jpg', 'jpeg', 'png', 'gif', 'tiff'],
            _isImg = false;
        for (var item in _imgType) {
            if (_type == _imgType[item]) {
                _isImg = true;
                break;
            }
        }
        return _isImg;
    }
    //------ 是否是图片 end
    //
    //
    //
    //
    //------ 建立一个年的选择 start
    function setYearSelect($el) {
        var thisYear = (new Date()).getFullYear();

        $el.each(function (index, el) {
            var _this = $(this),
                _thisVal = _this.attr('val'),
                _thisYearRange = _this.attr('yearRange'),
                _yearRange = [-10, 25],
                _html = '<option value="">请选择</option>';

            if (_thisYearRange) {
                _yearRange = eval(_thisYearRange);
            }

            for (var i = _yearRange[0]; i <= _yearRange[1]; i++) {
                _html += '<option value="' + (thisYear + i) + '"' + ((thisYear + i == _thisVal) ? ' selected="selected"' : '') + '>' + (thisYear + i) + '</option>';
            }

            _this.html(_html);
        });
    }
    //------ 建立一个年的选择 end
    //
    //
    //
    //------ 列表操作集合 start
    /**
     * [listOPN 列表的弹窗操作，例如删除、撤回等]
     * 列表id="list"，对应按钮/触发元素 class="fnOPN yourOPN"
     * @param  {[type]} ajaxObj [description]
     * @return {[type]}         [description]
     */
    function listOPN(ajaxObj, key) {
        /**
         * {
         *      withdraw: {
         *          url: '/projectMg/form/cancel.htm',
         *          message: '已撤销',
         *          opn: '撤回'
         *      },
         *      del: {
         *          url: '/projectMg/form/delete.htm',
         *          message: '已删除',
         *          opn: '删除'
         *      }
         *  };
         */

        var _k = key || 'formId';

        $('#list').on('click', '.fnOPN', function () {

            var _this = $(this);
            if (_this.hasClass('ing')) {
                return;
            }
            _this.addClass('ing');

            var _thisType = getJqHasClass(_this, ajaxObj);
            if (!!!_thisType) {
                _this.removeClass('ing');
                return;
            }

            Y.confirm('信息提醒', '您确定要' + ajaxObj[_thisType].opn + _this.parents('tr').attr('formname') + '？', function (opn) {
                if (opn == 'yes') {
                    var _data = {};
                    ajax({
                        url: ajaxObj[_thisType].url + '?' + _k + '=' + _this.parents('tr').attr(_k),
                        data: {
                            _SYSNAME: _this.attr('sysname') || '',
                        },
                        success: function (res) {
                            if (res.success) {
                                Y.alert('消息提醒', ajaxObj[_thisType].message);
                                window.location.reload(true);
                            } else {
                                _this.removeClass('ing');
                                Y.alert('消息提醒', res.message);
                            }
                        }
                    });
                } else {
                    _this.removeClass('ing');
                }
            });
        });
    }
    //------ 列表操作集合 end
    //
    //
    //
    //------ 重构name start
    /**
     * [重新序列化个别name属性]
     * @param  {[string]} diyname [指定序列化某些name]
     * @return {[type]}           [description]
     */
    function resetName(diyname) {

        var _tr;

        if (diyname) {
            _tr = $('[diyname=' + diyname + ']');
        } else {
            _tr = $('[diyname]');
        }

        _tr.each(function () {

            var tr = $(this),
                _diyname = tr.attr('diyname'),
                index = tr.index();

            tr.find('[name]').each(function () {

                var _this = $(this),
                    name = _this.attr('name');

                if (name.indexOf('.') > 0) {
                    name = name.substring(name.indexOf('.') + 1);
                }

                name = _diyname + '[' + index + '].' + name;

                _this.attr('name', name);

            });

        });

    }
    //------ 重构name end
    //
    //
    //
    //
    //------ 截取xxge字符 start
    function subStr(str, len) {

        if (!!!str) {
            return '';
        }

        if (str.length <= len) {
            return str;
        }
        return str.substr(0, len) + '..';
    }
    //------ 截取xxge字符 start
    //
    //
    //
    //
    //
    //------ 当前窗口 自定义跳转 start
    function direct(url, navUrl) {

        // 配合传多个参数，先将字符串转为base64
        var _url = base64.encode(url);

        var _hash = 'direct=' + _url + '&sidebarUrl=' + (navUrl || url),
            _old = window.top.hash;
        window.top.hashDirect((_old ? '&' : '') + _hash);
    }
    //------ 当前窗口 自定义跳转 end
    //
    //
    //
    //------ 新窗口 自定义跳转 start
    function openDirect(mainUrl, url, navUrl) {

        // 配合传多个参数，先将字符串转为base64
        var _url = base64.encode(url);

        var _newUrl = mainUrl + '#direct=' + _url + '&sidebarUrl=' + (navUrl || url);
        window.open(_newUrl);
    }
    //------ 新窗口 自定义跳转 end
    //
    //
    //
    //
    //------ 当前窗口 自定义跳转 带参数 star
    function direct2param(url, navUrl) {

        // 配合传多个参数，先将字符串转为base64
        var _url = base64.encode(url);

        window.top.direct2param(_url, navUrl);
    }
    //------ 当前窗口 自定义跳转 带参数 star
    //
    //
    //
    //
    //
    //------ 修复浮点数相加的问题 start
    /**
     ** http://www.cnblogs.com/sunshq/p/4425819.html 
     ** 加法函数，用来得到精确的加法结果
     ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
     ** 调用：accAdd(arg1,arg2)
     ** 返回值：arg1加上arg2的精确结果
     **/
    function accAdd(arg1, arg2) {
        var r1, r2, m, c;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        c = Math.abs(r1 - r2);
        m = Math.pow(10, Math.max(r1, r2));
        if (c > 0) {
            var cm = Math.pow(10, c);
            if (r1 > r2) {
                arg1 = Number(arg1.toString().replace(".", ""));
                arg2 = Number(arg2.toString().replace(".", "")) * cm;
            } else {
                arg1 = Number(arg1.toString().replace(".", "")) * cm;
                arg2 = Number(arg2.toString().replace(".", ""));
            }
        } else {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", ""));
        }
        return (arg1 + arg2) / m;
    }

    //------ 修复浮点数相加的问题 end
    //
    //
    //------ 修复浮点数相减的问题 start
    /**
     ** http://www.cnblogs.com/sunshq/p/4425819.html
     ** 减法函数，用来得到精确的减法结果
     ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
     ** 调用：accSub(arg1,arg2)
     ** 返回值：arg1加上arg2的精确结果
     **/
    function accSub(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        } catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        } catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }

    //------ 修复浮点数相减的问题 end


    //------ 放回两位整数 start
    function getTwoIntegers(str) {
        var _n = +str || 0;
        return (_n > 9) ? (_n + '') : ('0' + _n)
    }
    //------ 放回两位整数 end

    //------ 获取当前时间 start
    function getNowTime(ts) {

        var _d = new Date();

        if (ts) {
            _d = new Date(ts);
        }

        var _db = getTwoIntegers,
            _o = {
                YY: _d.getFullYear() + '',
                MM: _db(_d.getMonth() + 1),
                DD: _db(_d.getDate()),
                hh: _db(_d.getHours()),
                mm: _db(_d.getMinutes()),
                ss: _db(_d.getSeconds())
            };
        return _o;
    }
    //------ 获取当前时间 end

    //------ 获取某年某月有多少天 start
    function getDayOfMonth(year, month) {
        var _dayArr = {
                leap: [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
                normal: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
            },
            _y;
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
            _y = 'leap'
        } else {
            _y = 'normal'
        }

        return _dayArr[_y][month - 1]

    }
    //------ 获取某年某月有多少天 end


    //------ html清除空格 start
    function trimHtml(html) {
        return html.replace(/(^\s*)|(\s*$)/g, '').replace(/\>\s*\</g, '><').replace(/\>\s*(\S*)\s*\</g, '>$1<');
    }
    //------ html清除空格 end

    //------ JSON变为字符串 start
    function json2string(json) {
        return JSON.stringify(json)
    }
    //------ JSON变为字符串 end

    //------ 节点是否有对应的class start
    /**
     * http://www.cnblogs.com/chaojidan/p/4192529.html
     * [节点是否有对应的class]
     * @param  {[type]}  el       [jsDom对象]
     * @param  {[type]}  selector [class name]
     * @return {Boolean}          [description]
     */
    function hasClass(el, selector) {
        var rclass = /[\t\r\n\f]/g,
            className = " " + selector + " ";

        if (el.nodeType === 1 && (" " + el.className + " ").replace(rclass, " ").indexOf(className) > -1) {
            return true;
        }

        return false;

    }
    //------ 节点是否有对应的class end

    //------ 向未知对象添加一条属性 start
    function ObjAddkey(obj, key, objs) {

        if (obj[key]) {
            $.extend(obj[key], objs)
        } else {
            obj[key] = objs
        }

    }
    //------ 向未知对象添加一条属性 end


    //------ 打印页面 start
    function print(html) {
        document.getElementsByTagName('body')[0].innerHTML = html;
        setTimeout(function () {
            window.print();
            window.location.reload(true);
        }, 500);
    }
    //------ 打印页面 end

    //------ 判断是否 ie start
    function isIE() { //ie?  
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            return true;
        else
            return false;
    }
    //------ 判断是否 ie end


    //------ 从哪儿来，回哪儿去 start
    function doBack() {
        // 新开页面回到工作台
        // 有历史记录回到上一页
        // 通过请求页面是来自 /projectMg/index.htm? 判断是否返回，还是回到首页

        var isBack = ((/(\/insurance\/index\.htm)/g).test(document.referrer)) ? false : true;

        if (isBack) {
            window.location.href = document.referrer;
        } else {
            window.top.location.href = '/';
        }
    }
    //------ 从哪儿来，回哪儿去 end

    //------ 风险检索 start
    function risk2retrieve(userName, certNo) {

        ajax({
            url: '/baseDataLoad/verifyOrganizationInfo.json',
            data: {
                customName: userName,
                licenseNo: certNo
            },
            success: function (res) {
                if (res.success) {
                    window.open(res.url)
                } else {
                    Y.alert('提示', res.message)
                }
            }
        })

        // window.open('/riskMg/page/queryRiskInfo.htm?customName=' + encodeURIComponent(userName) + '&licenseNo=' + encodeURIComponent(certNo));
    }
    //------ 风险检索 end

    //------ 千位格式的数字 start
    function num2k(num) {

        var _num = num + '',
            _zum = _num.replace(/\-/g, ''); // 暂时不管正负数
        // 翻转、替换、翻转
        _zum = _zum.split('').reverse().join('').replace(/(\d{3})(?=[^$])/g, '$1,').split('').reverse().join('');

        if (+_num < 0) {
            _zum = '-' + _zum;
        }

        return _zum || '0.00';

    }
    //------ 千位格式的数字 end

    //------ validate 必填 start
    var validateDefault = {
        errorClass: 'error-tip',
        errorElement: 'b',
        ignore: '.ignore',
        onkeyup: false,
        errorPlacement: function (error, element) {

            if (element.hasClass('fnErrorAfter')) {

                element.after(error);

            } else {

                element.parent().append(error);

            }

        }
    };

    function setValidateRequired($fnInput, obj) {

        $fnInput.each(function (index, el) {

            obj.rules[el.name] = {
                required: true
            }

            obj.messages[el.name] = {
                required: el.getAttribute('errortext') || '必填'
            }

        });

    }
    //------ validate 必填 end


    //------ 构建通用图片 start
    function setUpAttachHtml(urls, isEdit) {

        var _html = '',
            urlArr = (urls || '').split(';'),
            _isEdit = isEdit || false

        $.each(urlArr, function (index, str) {

            var _arr = str.split(','),
                res = {
                    fileName: _arr[0],
                    serverPath: _arr[1],
                    fileUrl: _arr[2]
                },
                _isImg = isImg(res.fileName),
                _close = '',
                _file = ''

            if (isEdit) {
                _close = '<span class="attach-del fn-csp fn-usn fnUpAttachDel">&times;</span>'
            }

            if (_isImg) {
                _file = '<span class="fnAttachView fn-csp">' + res.fileName + '</span>'
            } else {
                _file = '<a title="点击下载" download target="_blank" href="/baseDataLoad/downLoad.htm?fileName=' + encodeURIComponent(res.fileName) + '&path=' + res.serverPath + '&id=0">' + res.fileName + '</a>'
            }

            _html += [
                '<span class="attach-item fnAttachItem" val="' + res.fileName + ',' + res.serverPath + ',' + res.fileUrl + '" title="' + res.fileName + '">',
                '<i class="icon' + (_isImg ? ' icon-img' : ' icon-file') + '"></i>',
                _file,
                _close,
                '</span>'
            ].join('')

        });

        return _html

    }
    //------ 构建通用图片 end

    //------ 通用提交审核流程 stat
    function postAudit(data, yesCB, noCB) {

        if (!!!data) {
            Y.alert('提交审核流程失败', '并没有找到表单编号')
            return
        }

        var _success = yesCB || function () {},
            _error = noCB || _success

        ajax({
            url: '/insurance/form/submit.json',
            data: {"formId":data.form.formId},
            success: function (res) {
                Y.alert('提示', res.message, function () {
                    res.success ? _success(res) : _error(res)
                })
            }
        })

    }
    //------ 通用提交审核流程 end

    //时间转换成格式 start
    // function formtDate(_ts,_type){
    //     var d = util.getNowTime(ts);
    //     var _y = _ts.getFullYear();
    //     var _m = _ts.getMonth();
    //     var _day = _ts.getDay();
    //     var _h = _ts.getHours();
    //     var _m = _ts.getMinutes();
    //     var _s = _ts.getSeconds();
    //
    //     return d.YY + '-' + d.MM + '-' + d.DD + ' ' + d.hh + ':' + d.mm + ':' + d.ss;
    // };
    /**
     * 对Date的扩展，将 Date 转化为指定格式的String
     * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     * eg:
     * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
     * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
     * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
     * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
     * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
     */
    function formtDate(_d,fmt) {
        var o = {
            "M+" : _d.getMonth()+1, //月份
            "d+" : _d.getDate(), //日
            "h+" : _d.getHours()%12 == 0 ? 12 : _d.getHours()%12, //小时
            "H+" : _d.getHours(), //小时
            "m+" : _d.getMinutes(), //分
            "s+" : _d.getSeconds(), //秒
            "q+" : Math.floor((_d.getMonth()+3)/3), //季度
            "S" : _d.getMilliseconds() //毫秒
        };
        var week = {
            "0" : "/u65e5",
            "1" : "/u4e00",
            "2" : "/u4e8c",
            "3" : "/u4e09",
            "4" : "/u56db",
            "5" : "/u4e94",
            "6" : "/u516d"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (_d.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[_d.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }

    //时间转换成格式 end

    //向外暴露方法
    module.exports = {
        fileType: {
            all: '*.jpg; *.jpeg; *.png; *.tiff; *.doc; *.xls; *.docx; *.xlsx; *.pdf; *.rar; *.zip; *.7z; *.tar; *.pcx; *.tga; *.exif; *.fpx; *.svg; *.psd; *.cdr; *.pcd; *.dxf; *.ufo; *.eps; *.ai; *.raw',
            img: '*.jpg; *.jpeg; *.png; *.tiff',
            doc: '*.doc; *.xls; *.docx; *.xlsx; *.pdf'
        },
        getParam: getParam,
        deserialization: deserialization,
        loopAjax: loopAjax,
        getJqHasClass: getJqHasClass,
        isObjectValueEqual: isObjectValueEqual,
        loading: loading,
        ajax: ajax,
        checkIdcard: checkIdcard,
        getMultilevel: getMultilevel,
        isImg: isImg,
        setYearSelect: setYearSelect,
        listOPN: listOPN,
        resetName: resetName,
        subStr: subStr,
        direct: direct,
        openDirect: openDirect,
        direct2param: direct2param,
        accAdd: accAdd,
        accSub: accSub,
        getTwoIntegers: getTwoIntegers,
        getNowTime: getNowTime,
        getDayOfMonth: getDayOfMonth,
        trimHtml: trimHtml,
        json2string: json2string,
        hasClass: hasClass,
        ObjAddkey: ObjAddkey,
        print: print,
        isIE: isIE,
        doBack: doBack,
        risk2retrieve: risk2retrieve,
        num2k: num2k,
        validateDefault: validateDefault,
        setValidateRequired: setValidateRequired,
        setUpAttachHtml: setUpAttachHtml,
        postAudit: postAudit,
        formtDate:formtDate
    }

});