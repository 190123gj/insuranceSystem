define(function (require, exports, module) {
    //登录
    //防止登录窗口被嵌套
    if (!(window.top === window.self)) {
        window.top.location.href = '/login/toLogin.htm';
    }

    // 全屏
    function setFullBody() {

        var $window = $(window)
        var FULLBODYHEIGHT = $window.height() - $('#fnLoginHeader').outerHeight() - $('#fnLoginFooter').outerHeight()
        $('#fnLoginBody').outerHeight(FULLBODYHEIGHT)
        $('#fnLoginBg').outerHeight(FULLBODYHEIGHT).outerWidth($window.width()).css('marginLeft', '-' + ($window.width() / 2) + 'px')

    }

    setFullBody()

    $(window).resize(function (event) {
        setFullBody()
    });
    
    var verify = $('#reg_verify_code');

	verify.click(function(e) {
		e.preventDefault();
		var seed = +new Date();
		$('#check_code_img').attr('src', '/login/getCaptchaImg4.htm?seed=' + seed);

	});
	setTimeout(function() {
		verify.trigger('click');
	}, 700)

	$('#check_code_img').click(function() {
		verify.trigger('click');

	});

});