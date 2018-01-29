define(function (require, exports, module) {

    var util = require('util');

    var base64 = require('base64').Base64;

    //获取

    function getNav(arr) {
        var _arr = arr;
        var _html = '',
            _subHtml = '';
        var isActive = '';
        var _index = 0;
        for (var i = 0; i < _arr.length; i++) {
            if (_arr[i].subclass.length) {
                _subHtml = getNav(_arr[i].subclass);
            } else {
                _subHtml = '';
            }
            if (_arr[i].rank <= 1) {
                isActive = ' active';
            } else {
                isActive = '';
            }
            _index = _arr[i].rank;
            _html += '<li class="s-li"><p class="s-ul-hd' + (_arr[i].url ? '' : ' fnMore') + '"><a ' + (_arr[i].url ? ' target="main"' : '') + ' href="' + (_arr[i].url ? _arr[i].url : 'javascript: void(0);') + '">' + ((_index == 0) ? '<i class="icon icon-' + _arr[i].alias + '"></i>' : '') + _arr[i].mainName + '</a>' + (_arr[i].subclass.length ? '<span class="indicators fnMore">&gt;</span>' : '') + '</p>' + _subHtml + '</li>';
        }
        return '<ul class="s-ul' + isActive + ' s-ul-bg' + _index + '">' + _html + '</ul>';
    }

    function toggleMenu($SULHD, $this) {
        var _this = $SULHD;
        _this.next('.s-ul').toggleClass('active');
        //_this.toggleClass('open');
    }
    return function (key) {
        if (!!document.getElementById('fnSidebar').innerHTML) {
            return;
        }
        $.ajax({
            url: '/baseDataLoad/loadMenuData.json?currentUrl=' + key + '&_time=' + (new Date()).getTime(),
            dataType: 'json',
            success: function (res) {

                var $iframe = $('#fnIfame');

                if (res.success) {
                    $('#fnSidebar').html(getNav(res.data.list)).on('click', '.s-ul-hd .fnMore', function (e) {
                        e.stopPropagation();
                        var _this = $(this);
                        toggleMenu(_this.parent());
                    }).on('click', '.s-ul-hd.fnMore', function () {
                        var _this = $(this);
                        toggleMenu(_this);
                    }).on('click', '.s-ul-hd a', function () {
                        var _this = $(this);
                        if (_this.attr('href') !== 'javascript: void(0);') {
                            _this.parents('#fnSidebar').find('.s-ul-hd').removeClass('active');
                            _this.parent().addClass('active');
                        }
                    });

                    //------监听 url 中的哈希值变化 start
                    //给嵌套的页面预留回调函数，改变hash
                    window.hashDirect = function (hash) {
                        location.hash = hash;
                    }

                    if (location.hash.substr(1)) {
                        hash2page(location.hash.substr(1))
                    }

                    $(window).on('hashchange', function () {

                        var _hash = location.hash.substr(1);
                        // 仅仅是刷新页面
                        if (_hash === 'refresh') {
                            $iframe[0].contentWindow.location.reload(true);
                            location.hash = '';
                            return;
                        }


                        if (_hash) {

                            hash2page(_hash)

                        }

                    });

                    function hash2page(_hash) {

                        if (_hash.indexOf('direct=') >= 0) {

                            var _nav = util.deserialization(_hash, 'sidebarUrl');

                            _hash = util.deserialization(_hash, 'direct');

                            // direct 已经是base64字符串，转回来
                            _hash = base64.decode(_hash);

                            document.getElementById('fnIfame').src = _hash;

                            $('#fnSidebar .s-ul-hd').removeClass('active');

                            var $a = $('#fnSidebar a[href="' + _nav + '"]');
                            var _parent = $a.parent().addClass('active');

                            // 上级都要展开
                            $a.parents('.s-ul').each(function (index, el) {
                                if (!el.className.indexOf('active') >= 0) {
                                    el.className += ' active'
                                }
                            });

                            location.hash = '';

                            setTimeout(function () {
                                $('#fnSidebar').scrollTop(_parent.offset() ? (_parent.offset().top - 165) : 0);
                            }, 50)

                        }

                    }
                    //------监听 url 中的哈希值变化 end

                    //------带参数的url，跳转 start
                    window.direct2param = function (url, navUrl) {
                        $('#fnSidebar .s-ul-hd').removeClass('active');
                        $('#fnSidebar a[href="' + navUrl + '"]').parent().addClass('active');
                        document.getElementById('fnIfame').src = base64.decode(url);
                    };
                    //------带参数的url，跳转 end

                    // 进入某个主要菜单，然后点击第一个链接
                    // 某些跳转回直接在 `basesrc` 上添加一个值 例如工作台到审核
                    //
                    if (!$iframe.attr('basesrc')) {
                        $('#fnSidebar').find('a').each(function (index, el) {


                            if (!(/javascript/ig).test(el.href)) {

                                $iframe.attr('basesrc', el.href);

                                return false;
                            }

                        });
                    }

                    //------ 打开baseurl start
                    if ($iframe[0] && !!!$iframe[0].src) {
                        $iframe[0].src = $iframe.attr('basesrc')
                    }
                    //------ 打开baseurl start

                } else {
                    return;
                }
            }
        });
    }
});