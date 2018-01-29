/**
 * initRiskQuery
 * 风险查询 信息、相似、失信
 *
 * initRiskQuery(id, nameId, certNoId, typeId, orgCodeId, isOneCertId)
 * id: 三个按钮容器的id，如果不需要显示在页面上 false
 * nameId: 用户名称input的id
 * certNoId: 证件号码input的id 身份证
 * typeId: 用户类型input的id
 * orgCodeId: 组织机构代码input的id
 * isOneCertId: 是否三证合一input\checkbox的id
 * licenseNoId: 证件号码input的id 三证合一
 * 
 * initCerNoNameMobile
 * 身份证、名称、电话号码核实
 *
 * initCheckBankCard
 * 开户人名称、开户账号核实
 * 
 */

define(function (require, exports, module) {

    require('Y-msg')
    var util = require('util')
        // var getList = require('zyw/getList')

    /**
     * 信息、相似、失信
     */
    // function initRiskQuery(id, nameId, certNoId, typeId, orgCodeId, isOneCertId, licenseNoId) {
    function initRiskQuery(id, nameId, certNoId, typeId, orgCodeId, oneCertId, licenseNoId) {

        var self = this

        self.DomName = document.getElementById(nameId) || {
            value: null
        }
        self.DomCertNo = document.getElementById(certNoId) || {
            value: null
        }
        self.DomType = document.getElementById(typeId) || {
            value: null
        }
        self.DomOrgCode = document.getElementById(orgCodeId) || {
            value: null
        }
        self.DomLicenseNo = document.getElementById(licenseNoId) || {
            value: null
        }

        self.$oneCert = !!oneCertId ? $('#' + oneCertId) : []

        // self.DomName = document.getElementById(nameId)
        // self.DomCertNo = document.getElementById(certNoId)
        // self.DomType = document.getElementById(typeId)

        // self.DomOrgCodeId = document.getElementById(orgCodeId) || {
        //  value: false
        // }

        // self.$isOneCertId = !!isOneCertId ? $('#' + isOneCertId) : []


        // if (!!!self.$isOneCertId.length || (!!self.$isOneCertId.length && self.$isOneCertId.prop('checked'))) {
        //  // 三证合一
        //  self.cerNo = self.DomCertNo
        // } else {
        //  // 组织机构代码
        //  self.cerNo = self.DomOrgCodeId
        // }

        // $('body').append('<div id="similarListBtn"></div>')

        $('body').on('click', '#fnRiskQueryAllXX', function () {

            openXX()

        })

        function openXX() {
            if (!!!self.DomName.value || !!!self.DomLicenseNo.value) {
                Y.alert('提示', '请输入客户名称及证件号码')
                return
            }
            util.risk2retrieve(self.DomName.value, self.DomLicenseNo.value || self.DomCertNo.value)
        }

        if (!!!id) {
            return
        }

        self.$btns = $('#' + id)

        if (!!!self.$btns.length) {
            return
        }

        // self.$btns.append([
        //  '<a href="javascript:void(0);" class="apply-btn" id="fnLookInfo">信息</a>',
        //  '<a href="javascript:void(0);" class="apply-btn xs" id="fnLookXS">相似</a>',
        //  '<a href="javascript:void(0);" class="apply-btn sx" id="fnLookSX">失信</a>'
        // ].join(''))

        self.$btns.append([
            '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-green fn-mr10" id="fnLookInfo">信息</a>',
            '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-green fn-mr10" id="fnLookXS">相似</a>',
            '<a href="javascript:void(0);" class="ui-btn ui-btn-fill ui-btn-danger fn-mr10" id="fnLookSX">失信</a>'
        ].join(''))

        function inInputed() {

            // var _tip = ''

            // if (!!!self.$isOneCertId.length || (!!self.$isOneCertId.length && self.$isOneCertId.prop('checked'))) {
            //  // 三证合一
            //  _tip = '请输入客户名称及证件号码'
            //  self.cerNo = self.DomCertNo
            // } else {
            //  // 组织机构代码
            //  _tip = '请输入客户名称及组织机构代码'
            //  self.cerNo = self.DomOrgCodeId
            // }

            // if (!!!self.DomName.value || !!!self.cerNo.value) {
            //  Y.alert('操作失败', _tip)
            //  return false
            // }

            if (!!!self.DomName.value) {
                Y.alert('提示', '请输入客户名称及证件号码')
                return false
            }

            return true

        }

        self.$btns.on('click', '#fnLookInfo', function () {

            // 信息

            openXX()

        }).on('click', '#fnLookSX', function () {
            // 失信

            if (!inInputed()) {
                return
            }

            self.showSX()

        }).on('click', '#fnLookXS', function (event) {
            // 相似
            if (!inInputed()) {
                return
            }
            if (!!!self.DomLicenseNo.value) {
                Y.alert('提示', '请输入客户证件号码')
                return
            }

            self.showXS()

        })

    }

    initRiskQuery.prototype.toggleXX = function (isShow) {

        if (isShow) {
            this.$btns.find('#fnLookInfo').removeClass('fn-hide')
        } else {
            this.$btns.find('#fnLookInfo').addClass('fn-hide')
        }

    }

    initRiskQuery.prototype.toggleXS = function (isShow) {

        if (isShow) {
            this.$btns.find('#fnLookXS').removeClass('fn-hide')
        } else {
            this.$btns.find('#fnLookXS').addClass('fn-hide')
        }

    }

    initRiskQuery.prototype.toggleSX = function (isShow) {

        if (isShow) {
            this.$btns.find('#fnLookSX').removeClass('fn-hide')
        } else {
            this.$btns.find('#fnLookSX').addClass('fn-hide')
        }

    }

    initRiskQuery.prototype.getSX = function (doQuery) {

        var self = this

        var _oneCert = '';

        if (self.$oneCert.attr('type') == 'input' || self.$oneCert.attr('type') == 'hidden') {
            _oneCert = self.$oneCert.val()
        }

        if (self.$oneCert.attr('type') == 'checkbox') {
            _oneCert = self.$oneCert.prop('checked') ? 'IS' : ''
        }

        var dtd = $.Deferred()

        if (!doQuery) {
            return dtd.resolve({
                success: false
            })
        }

        util.ajax({
            url: '/baseDataLoad/dishonestQuery.json',
            data: {
                name: self.DomName.value,
                type: self.DomType.value,
                oneCert: _oneCert,
                certNo: self.DomCertNo.value,
                licenseNo: self.DomLicenseNo.value,
                orgCode: self.DomOrgCode.value
            },
            success: function (res) {
                dtd.resolve(res)
            }
        })

        return dtd.promise()

    }

    initRiskQuery.prototype.showSX = function () {

        var self = this

        $.when(self.getSX(true))
            .then(function (res) {

                if (res.success) {

                    var _tbody = '',
                        _thead = '<thead><tr><th>案号</th><th>法律文书确定的义务</th><th>具体情形</th><th width="110px">发布时间</th><th width="100px">执行法院</th><th width="80px">履行情况</th></tr></thead>';

                    $.each(res.data, function (index, obj) {

                        _tbody += [
                            '<tr>',
                            '    <td>' + obj.code + '</td>',
                            '    <td title="' + obj.disruptType + '">' + (obj.disruptType || '').substr(0, 12) + '..</td>',
                            '    <td title="' + obj.duty + '">' + (obj.duty || '').substr(0, 12) + '..</td>',
                            '    <td>' + obj.pubTime + '</td>',
                            '    <td>' + obj.court + '</td>',
                            '    <td>' + obj.performance + '</td>',
                            '</tr>'
                        ].join('')

                    });

                    var _html = '<p class="fn-ml20 fn-mr20 fn-mb20 fn-fs16 fn-tal fn-f0">客户名称：' + self.DomName.value + '&emsp;证件号码：' + (self.DomCertNo.value || self.DomLicenseNo.value) + '&emsp;省份：' + (res.data[0] || {}).area + ((res.data[0] || {}).sex ? ('&emsp;性别：' + res.data[0].sex) : '') + '</p><div style="height: auto; max-height: 358px;" class="fn-ml20 fn-mr20"><table class="m-table m-table-list fn-tac">' + _thead + '<tbody>' + _tbody + '</tbody></table></div>';

                    if (!!!res.data.length) {
                        // _html = '<p class="fn-tac">暂无记录</p>'
                        Y.alert('失信黑名单查看', '暂无失信记录')
                        return
                    }

                    $('body').Y('Msg', {
                        type: 'alert',
                        title: '失信黑名单查看',
                        width: '90%',
                        content: _html,
                        icon: ''
                    });

                } else {
                    Y.alert('操作失败', res.message)
                }
            })

    }

    initRiskQuery.prototype.getXS = function (doQuery) {

        var self = this

        var dtd = $.Deferred()

        if (!doQuery) {
            return dtd.resolve({
                success: false
            })
        }

        util.ajax({
            url: '/baseDataLoad/querySimilarEnterprise.json',
            data: {
                customName: self.DomName.value,
                licenseNo: self.DomCertNo.value || self.DomLicenseNo.value
            },
            success: function (res) {

                return dtd.resolve(res)

            }
        })

        return dtd.promise()

    }

    initRiskQuery.prototype.showXS = function () {

        var self = this

        $.when(self.getXS(true))
            .then(function (res) {
                if (res.success) {

                    var _tbody = '',
                        _thead = '<thead><tr><th>企业名称</th><th>证件号码</th><th>操作</th></tr></thead>';

                    $.each(res.data, function (index, obj) {

                        _tbody += [
                            '<tr>',
                            '    <td>' + obj.custoName + '</td>',
                            '    <td>' + obj.licenseNo + '</td>',
                            '    <td><a href="' + obj.detailUrl + '" target="_blank">查看详情</a></td>',
                            '</tr>'
                        ].join('')

                    });

                    var _html = '<p class="fn-ml20 fn-mr20 fn-mb20 fn-fs16 fn-tal fn-f0">客户名称：' + self.DomName.value + '&emsp;证件号码：' + (self.DomCertNo.value || self.DomLicenseNo.value) + '</p><div style="height: auto; max-height: 358px;" class="fn-ml20 fn-mr20"><table class="m-table m-table-list fn-tac">' + _thead + '<tbody>' + _tbody + '</tbody></table></div>';

                    if (!!!res.data.length) {
                        Y.alert('相似企业信息查看', '暂无相似记录')
                        return
                    }

                    $('body').Y('Msg', {
                        type: 'alert',
                        title: '相似企业信息查看',
                        width: '600px',
                        content: _html,
                        icon: ''
                    });

                } else {
                    Y.alert('操作失败', res.message)
                }
            })

    }

    initRiskQuery.prototype.getXX = function (doQuery) {

        var self = this

        var dtd = $.Deferred()

        if (!doQuery) {
            return dtd.resolve({
                success: false
            })
        }

        util.ajax({
            url: '/baseDataLoad/verifyOrganizationInfo.json',
            data: {
                customName: self.DomName.value,
                licenseNo: self.DomCertNo.value || self.DomLicenseNo.value
            },
            success: function (res) {

                return dtd.resolve(res)

            }
        })

        return dtd.promise()

    }

    initRiskQuery.prototype.getAllInfo = function (xx, sx, xs, fn) {

        // var dtd = $.Deferred()

        var _html = '',
            self = this

        if (!xx && !sx && !xs) {
            return
        }

        if (!!!self.DomName.value || (!!!self.DomLicenseNo.value && !!!self.DomOrgCode.value)) {
            Y.alert('提示', '请输入客户名称及证件号码')
            return
        }

        // if (xx) {
        //     _html = '<div class="m-item"><label class="m-label">信息：</label><span class="fn-green fn-csp" id="fnRiskQueryAllXX">查看</span></div>'
        // }

        $.when(self.getXX(xx))
            .then(function (res) {

                var _s = ''

                if (res.success) {
                    _s = '<a class="fn-green" href="' + res.url + '" target="_blank">查看</a>'
                } else {
                    _s = res.message
                }

                if (xx) {
                    _html += '<div class="m-item"><label class="m-label">详细信息：</label>' + _s + '</div>'
                }

                return $.when(self.getSX(sx))

            })
            .then(function (res) {

                var _s = ''

                if (res.success) {

                    var _tbody = '',
                        _thead = '<thead><tr><th>案号</th><th>法律文书确定的义务</th><th>具体情形</th><th width="110px">发布时间</th><th width="100px">执行法院</th><th width="80px">履行情况</th></tr></thead>';

                    $.each(res.data, function (index, obj) {

                        _tbody += [
                            '<tr>',
                            '    <td>' + obj.code + '</td>',
                            '    <td>' + obj.disruptType + '</td>',
                            '    <td>' + obj.duty + '</td>',
                            '    <td>' + obj.pubTime + '</td>',
                            '    <td>' + obj.court + '</td>',
                            '    <td>' + obj.performance + '</td>',
                            '</tr>'
                        ].join('')

                    });

                    _s = '<p class="fn-mr20 fn-mb10 fn-tal">客户名称：' + self.DomName.value + '&emsp;证件号码：' + (self.DomCertNo.value || self.DomLicenseNo.value) + '&emsp;省份：' + (res.data[0] || {}).area + ((res.data[0] || {}).sex ? ('&emsp;性别：' + res.data[0].sex) : '') + '</p><div class="fn-wp90"><table class="m-table m-table-list fn-tac">' + _thead + '<tbody>' + _tbody + '</tbody></table></div>';

                    if (!!!res.data.length) {
                        _s = '暂无失信黑名单信息'
                    }

                } else {

                    if (res.message) {
                        if (res.message.indexOf('欠费') >= 0) {
                            _s = '查询失败，请联系管理员'
                        } else {
                            // _s = (self.DomType.value == 'ENTERPRISE') ? '无' : '暂无失信记录'
                            _s = '暂无失信记录'
                        }
                    }

                }

                if (sx) {

                    // var _title = (self.DomType.value == 'ENTERPRISE') ? '失信人名单' : '失信记录'
                    var _title = '失信记录'

                    _html += '<div class="m-item"><label class="m-label">' + _title + '：</label>' + _s + '</div>'
                }

                return $.when(self.getXS(xs))
            })
            .then(function (res) {

                var _s = '';

                if (res.success) {

                    var _tbody = '',
                        _thead = '<thead><tr><th>企业名称</th><th>证件号码</th><th width="80px">操作</th></tr></thead>';

                    $.each(res.data, function (index, obj) {

                        _tbody += [
                            '<tr>',
                            '    <td>' + obj.custoName + '</td>',
                            '    <td>' + obj.licenseNo + '</td>',
                            '    <td><a href="' + obj.detailUrl + '" target="_blank">查看详情</a></td>',
                            '</tr>'
                        ].join('')

                    });

                    _s = '<p class="fn-mr20 fn-mb10 fn-tal">客户名称：' + self.DomName.value + '&emsp;证件号码：' + (self.DomCertNo.value || self.DomLicenseNo.value) + '</p><div class="fn-mr20"><div class="fn-mr20 fn-wp90"><table class="m-table m-table-list fn-tac">' + _thead + '<tbody>' + _tbody + '</tbody></table></div>';

                    if (!!!res.data.length) {
                        _s = '暂无相似企业信息'
                    }

                } else {

                    if (res.message) {
                        _s = '无'
                    }

                }

                if (xs) {
                    _html += '<div class="m-item"><label class="m-label">相似企业信息：</label>' + _s + '</div>'
                }

                _html += '<input type="hidden" name="messageInfo" value="' + encodeURIComponent(_html) + '">'

                if (fn) {
                    fn(_html)
                }

            })


        // return dtd.promise()

    }


    // 身份证、姓名、电话号码 核实
    function initCerNoNameMobile(cerNoId, NameId, MobileId, btnId, tipId) {

        var self = this

        var DomCerNo = document.getElementById(cerNoId),
            DomName = document.getElementById(NameId),
            ajaxUrl,
            alertText,
            checkMobile = false;

        self.$btn = $('#' + btnId)
        self.DomTip = document.getElementById(tipId)

        if (!!MobileId) {
            var DomMobile = document.getElementById(MobileId)
            ajaxUrl = '/baseDataLoad/validateMobile.json'
            alertText = '请输入名称、证件号码、电话号码'
            checkMobile = true
        } else {
            var DomMobile = {
                value: true
            }
            ajaxUrl = '/baseDataLoad/validateIdCard.json'
            alertText = '请输入客户名称及证件号码'
        }

        var $p = self.$btn.parents('.m-item')

        // $p.on('click', '.fnLookSFXIMG', function() {})

        $p.on('click', '.fnLookSFXIMG', function () {

            var _url = this.getAttribute('url')
            if (!!!_url) {
                Y.alert('提示', '暂无图像信息')
            } else {
                var _img = new Image()
                _img.src = 'data:image/png;base64,' + _url
                _img.onload = function () {
                    var _div = document.createElement('div')
                    _div.appendChild(_img)
                    Y.alert('图像信息', _div);
                };
            }

        })

        self.$btn.on('click', function () {

            if (!!!DomCerNo.value || !!!DomName.value || !!!DomMobile.value) {
                Y.alert('提示', alertText)
                return
            }

            // 只能校验手机号码
            if (checkMobile) {

                // mobile reg from validate extend
                if (!(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/).test(DomMobile.value)) {
                    Y.alert('提示', '请输入手机号码')
                    return
                }

            }

            // 是否是身份证
            if (util.checkIdcard(DomCerNo.value) != '验证通过!') {
                Y.alert('提示', '请输入正确的身份证号码')
                return
            }

            util.ajax({
                url: ajaxUrl,
                data: {
                    certNo: DomCerNo.value,
                    name: DomName.value,
                    mobile: DomMobile.value
                },
                success: function (res) {

                    // 两个接口返回数据不一致
                    var _html,
                        _value,
                        _url;
                    if (!!MobileId) {
                        // 验证电话号码
                        if (res.success) {
                            _html = '<span class="fn-green">' + res.message + '</span>'
                        } else {
                            _html = '<span class="fn-f30">' + res.message + '</span>'
                        }

                        self.DomTip.innerHTML = _html + '<input type="hidden" name="messageInfo" value="' + encodeURIComponent(_html) + '">'

                    } else {

                        if (res.success) {
                            if (res.data.checkResult) {
                                _html = '<span class="fn-green">' + res.data.checkMessage + '</span>'
                                _url = res.data.idcardphoto
                            } else {
                                _html = '<span class="fn-f30">' + res.data.checkMessage + '</span>'
                            }
                        } else {
                            _html = '<span class="fn-f30">' + res.message + '</span>'
                        }

                        self.DomTip.innerHTML = _html + '<br><span class="fnLookSFXIMGs"></span><input type="hidden" name="messageInfo" value="' + encodeURIComponent(_html + '<br><span class="fnLookSFXIMGs">' + (_url ? ('<img src="data:image/png;base64,' + _url + '">') : '') + '</span>') + '">'

                        var $imgs = $p.find('.fnLookSFXIMGs').eq(0)

                        if (!!_url) {

                            var _img = new Image()
                            _img.src = 'data:image/png;base64,' + _url
                            _img.onload = function () {
                                // var _div = document.createElement('div')
                                // _div.appendChild(_img)
                                // $imgs.html(_div)
                                $imgs.html(_img)
                            };

                        } else {
                            $imgs.html('')
                        }

                        // var $img = $p.find('.fnLookSFXIMG')

                        // if (!!_url) {

                        //     if (!!$img.length) {
                        //         $img.attr('url', _url)
                        //     } else {
                        //         self.$btn.after('<a class="ui-btn ui-btn-fill ui-btn-green fn-ml10 fnLookSFXIMG" url="' + _url + '">图像</a>')
                        //     }

                        // } else {
                        //     $img.remove()
                        // }

                    }

                }
            })

        })

    }

    initCerNoNameMobile.prototype.toggleBtn = function (isShow) {
        if (isShow) {
            this.$btn.removeClass('fn-hide')
        } else {
            this.$btn.addClass('fn-hide')
            this.DomTip.innerHTML = '<input type="hidden" name="messageInfo" value="">'
        }
    }

    function initCheckBankCard(nameId, bankCardNoId, btnId, tipId) {

        var self = this

        self.DomName = document.getElementById(nameId) || {
            value: null
        }
        self.DomBankCardNo = document.getElementById(bankCardNoId) || {
            value: null
        }

        self.DomTip = document.getElementById(tipId)

        self.$btn = $('#' + btnId)


        self.$btn.on('click', function () {

            if (!!!self.DomName.value || !!!self.DomBankCardNo.value) {
                Y.alert('提示', '请输入开户人、开户账号')
                return
            }

            util.ajax({
                url: '/baseDataLoad/validateBankCard.json',
                data: {
                    name: self.DomName.value,
                    bankCardNo: self.DomBankCardNo.value
                },
                success: function (res) {

                    var _html,
                        _value;

                    if (res.success) {
                        if (res.data.checkResult) {
                            _html = '<span class="fn-green">' + res.data.checkMessage + '</span>'
                        } else {
                            _html = '<span class="fn-f30">' + res.data.checkMessage + '</span>'
                        }
                    } else {
                        _html = '<span class="fn-f30">' + res.message + '</span>'
                    }

                    self.DomTip.innerHTML = _html + '<input type="hidden" name="messageInfo" value="' + encodeURIComponent(_html) + '">'

                }
            })

        })

    }

    module.exports = {
        initRiskQuery: initRiskQuery,
        initCerNoNameMobile: initCerNoNameMobile,
        initCheckBankCard: initCheckBankCard
    }
})