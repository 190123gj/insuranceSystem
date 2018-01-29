/**
 *
 * 初始化文件
 * 主要功能 获取左边菜单的Nav、顶部的时间、页面高度、侧边栏开关、监听hash改变iframe的src、获取未读消息
 *
 * 页面是iframe嵌套的，所有有很多局限性 默认开的url卸载iframe的basesrc中，然后当页面加载好后通过js改变iframe的src
 * 这样做避免跳转页面的时候，部分浏览器打开两次页面，默认和当前需要打开的页面
 * 关于 iframe 打开页面，每个用户的权限不是，所以打开的默认页面也不同，所以，把它移动到了 `getNav.js` 里面了
 * 在获取到后结束或获取第一个带链接的 `a` 标签
 *
 * 
 */

define(function (require, exports, module) {

    var util = require('util');

    //ie8兼容问题
    var isIE = $.browser.msie ? $.browser.version : '';

    if (isIE) {
        $('#fnCTop').after('<div class="fn-clears"></div>')
    }

    //获取nav
    require('zyw/getNav')(location.pathname);
    //设置顶部导航
    var $nav = $('#nav'),
        $navItem = $nav.find('li')
    $nav.find('a[href="' + location.pathname + '"]').addClass('active');
    // $navItem.css({
    //     'width': Math.floor($nav.width() / $navItem.length) - 10 + 'px',
    //     'margin-left': '10px'
    // })

    //------显示时间 start
    var nowTime, nowTimeStr = '';

    function getDBNum(num) {
        if (num < 10) {
            return '0' + num;
        } else {
            return num;
        }
    }
    setInterval(function () {
        nowTime = new Date();
        nowTimeStr = nowTime.getFullYear() + '年' + (nowTime.getMonth() + 1) + '月' + nowTime.getDate() + '日 ' + getDBNum(nowTime.getHours()) + ':' + getDBNum(nowTime.getMinutes()) + ':' + getDBNum(nowTime.getSeconds());
        document.getElementById('nowTime').innerHTML = nowTimeStr;
    }, 1000);
    //------显示时间 end
    //------页面高度 start
    var $win = $(window), //用于页面高度和哈希值监听
        $body = $('body'),
        $fnCTop = $('#fnCTop'),
        $fnCNav = $('#fnCNav'),
        $fnMainBox = $('#fnMainBox'),
        TOPNAVH = $fnCTop.outerHeight() + $fnCNav.outerHeight();

    function setMainBoxHeight() {
        $fnMainBox.outerHeight($win.height() - TOPNAVH).find('#fnSidebar').outerHeight($win.height() - TOPNAVH - 90);
    }
    setMainBoxHeight();
    $win.resize(function (event) {
        setMainBoxHeight();
    });
    //------页面高度 end
    //------全屏 start
    $('#fnFullScreen').on('click', function () {
        $(this).toggleClass('active');
        $body.toggleClass('fnFullScreen');
        var _isFullScreen;
        if ($body.hasClass('fnFullScreen')) {
            _isFullScreen = true;
        } else {
            _isFullScreen = false;
        }
        window.localStorage.isFullScreen = _isFullScreen;
    });

    //还原全屏
    (function (window, $) {

        var _isFullScreen = window.localStorage.isFullScreen;

        _isFullScreen = eval(_isFullScreen);

        if (_isFullScreen) {
            $body.addClass('fnFullScreen');
            $('#fnFullScreen').addClass('active');
        }

    })(window, jQuery);
    //------全屏 end
    //
    //
    //
    //------消息相关 start
    var timerTitle,
        isNewTitle = true,
        oldTitle = document.title,
        $msgBox = $('<div class="m-msg-box leave"></div>').html('<div class="title fn-usn back"><div class="fn-right fn-csp fn-f0 close">&times;</div><a href="/systemMg/message/user/list.htm?status=UNREAD">您有<span class="num"></span>条新短消息，请查收</a></div><a class="link" href="/systemMg/message/user/list.htm?status=UNREAD">点击查看</a>');

    // $msgBox.on('click', '.close', function () {
    //  $msgBox.addClass('leave').find('.title').addClass('back');
    // }).on('click', '.back', function () {
    //  $msgBox.removeClass('leave').find('.title').removeClass('back');
    // })
    window.checkUnReadMessage = function () {
        //获取未读消息数量
        $.post('/systemMg/message/user/unRead.json', {
            _time: (new Date()).getTime()
        }, function (res) {
            if (res.success) {
                if (res.count > 0) {
                    if (!!$body.find('.m-msg-box').length) {
                        $msgBox.find('.num').html(res.count)
                    } else {
                        $body.append($msgBox.find('.num').html(res.count).end());
                    }
                    $('#messageCount').html(res.count).addClass('num');
                    //                  timerTitle = setInterval(function() {
                    //                      if (isNewTitle) {
                    //                          window.top.document.title = '【新消息】' + oldTitle;
                    //                      } else {
                    //                          window.top.document.title = '【　　　】' + oldTitle;
                    //                      }
                    //                      isNewTitle = !isNewTitle;
                    //                  }, 500)
                } else {
                    $('#messageCount').html('').removeClass('num');
                    //                  clearInterval(timerTitle);
                    $body.find('.m-msg-box').remove()
                }
            } else {
                if (messageInterval) {
                    window.clearInterval(messageInterval);
                }
            }
        });
    }
    checkUnReadMessage();
    var messageInterval = window.setInterval(checkUnReadMessage, 3 * 60 * 1000);

    //------消息相关 end

    //------ 修改密码 start
    $('#fnChangePws').on('click', function () {

        util.openDirect('/insurance/index.htm', '/userHome/modifyPassword.htm')

    });
    //------ 修改密码 end

    //修复不支持placeholder属性 start
    var isSurportPlder = "placeholder" in document.createElement("input"); // 判断浏览器是否支持 placeholder
    if (!isSurportPlder) {
        $("[placeholder]").focus(function () {
            var _this = $(this);
            if (_this.val() == _this.attr("placeholder")) {
                _this.val('');
            }
        }).blur(function () {
            var _this = $(this);
            if (_this.val() == '' || _this.val() == _this.attr("placeholder")) {
                _this.val(_this.attr("placeholder"));
            }
        }).blur();
    };
    //修复不支持placeholder属性 end


});