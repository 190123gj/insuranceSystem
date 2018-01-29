define(function(require, exports, module) {
    // $('#id').focus(function(event) {
    //        var yearsTimeFirst = new yearsTime({
    //            elem: '#id',
    //            callback: function(_this,_time){
    //                _this.val('报告期('+_time[0]+'年'+_time[1]+'月)');
    //                $('[name="productStructures[0].reportPeriod2"]').val('上一年（'+(_time[0]-1)+'年）');
    //                $('[name="productStructures[0].reportPeriod3"]').val('上二年（'+(_time[0]-2)+'年）');
    //            }
    //        });
    //    });

    function yearsTime(obj) {
        this.elem = obj.elem;
        this.min = obj.min;
        this.max = obj.max;
        this.format = obj.format;
        this.callback = obj.callback;
        this.init();
    }
    yearsTime.prototype = {
        init: function() {

            var _time = this.getPresentTime();
            //console.log(_time)
            this.setPopup(_time);
            this.objLocation();
            this.clickInput();
            this.clickTime(_time);
            this.clickyearsTimeNext();
            this.clickyearsTimePrev();
            this.clickyearsTimeYYLi();
            this.clickyearsTimeNextYY(_time);
            this.clickyearsTimePrevYY(_time);
            this.keyDownInput();
            this.blurInput();
            this.limitFun();

        },
        MaxFun: function() {
            if (this.format != 'YYYY') {
                return (typeof this.max == 'function') ? this.max(this.elem) : this.max;
            } else {
                return parseFloat((typeof this.max == 'function') ? this.max(this.elem) : this.max);
            }
        },
        MinFun: function() {
            if (this.format != 'YYYY') {
                return (typeof this.min == 'function') ? this.min(this.elem) : this.min;
            } else {
                return parseFloat((typeof this.min == 'function') ? this.min(this.elem) : this.min);
            }
        },
        limitFun: function(Ytime) {

            var Max = this.MaxFun();
            var Min = this.MinFun();

            if (this.format != 'YYYY') {

                return true;

            } else {

                return ((Min && Ytime < Min) || (Max && Ytime > Max)) ? false : true;

            }
        },
        limitFunUp: function(Ytime, Mtime) {

            var Max = this.MaxFun();
            var Min = this.MinFun();
            var YMax = Max ? parseFloat(Max.match(/\d{4}(?=[\D])/)[0]) : null;
            var YMin = Min ? parseFloat(Min.match(/\d{4}(?=[\D])/)[0]) : null;
            var MMax = Max ? parseFloat(Max.match(/[\D](\d{2})/)[1]) : null;
            var MMix = Min ? parseFloat(Min.match(/[\D](\d{2})/)[1]) : null;
            var DateMax = Max ? Date.parse(new Date(Max + '-01 00:00:00')) : null;
            var DateMin = Min ? Date.parse(new Date(Min + '-01 00:00:00')) : null;
            var Time = (Ytime && Mtime) ? Date.parse(new Date(Ytime + '-' + Mtime + '-01 00:00:00')) : null;

            if (this.format != 'YYYY') {

                if ((Max && Ytime && Ytime > YMax) || (Min && Ytime && Ytime < YMin)) {

                    return [false, false];

                } else if ((Max && Ytime && Ytime == YMax) || (Min && Ytime && Ytime == YMin)) {
                    console.log(Time, DateMin, Time < DateMin);
                    if ((Time && DateMax) && Time > DateMax || (Time && DateMin) && Time < DateMin) {

                        return [true, false];

                    } else {

                        return [true, true];

                    }

                } else {

                    return [true, true];

                }


            }

        },
        setPopup: function(_time) {

            var _getFullYear = _time['getFullYear'],
                _getMonth = _time['getMonth'] + 1,
                _html = ['<div class="yearsTime">',
                    '        <div id="yearsTimeYY">',
                    '            <i class="yearsTimePrev"></i>',
                    '            <input readonly>',
                    '            <i class="yearsTimeNext"></i>',
                    '            <div id="yearsTimeYYLi">',
                    '                <i class="yearsLiTimePrev"></i>',
                    '                <ul class="fn-clear">',
                    '                </ul>',
                    '                <i class="yearsLiTimeNext"></i>',
                    '            </div>',
                    '        </div>',
                    '        <div id="yearsTimeMM">',
                    '        </div>',
                    '    </div>'
                ].join(""),
                _yearsTimeYYLiContent = '',
                _yearsTimeMMContent = '';
            $('.yearsTime').remove();
            $('body').append($(_html));
            if (this.format != 'YYYY') {
                for (var i = 0; i < 10; i++) {
                    if (i == 0) {
                        _yearsTimeYYLiContent += '<li class="fn-left on ' + (this.limitFunUp(_getFullYear + i)[0] ? "" : "cannot") + '">' + (_getFullYear + i) + '年</li>'
                    } else {
                        _yearsTimeYYLiContent += '<li class="fn-left ' + (this.limitFunUp(_getFullYear + i)[0] ? "" : "cannot") + '">' + (_getFullYear + i) + '年</li>'
                    }
                }
                for (var x = 0; x < 2; x++) {
                    _yearsTimeMMContent += '<ul class="fn-clear">'
                    for (var y = 1; y < 7; y++) {
                        if (y + x * 6 == _getMonth) {
                            _yearsTimeMMContent += '<li class="fn-left on ' + (this.limitFunUp(_getFullYear, y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                        } else {
                            _yearsTimeMMContent += '<li class="fn-left ' + (this.limitFunUp(_getFullYear, y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                        }
                    }
                    _yearsTimeMMContent += '</ul>'
                }

            } else {

                for (var i = 0; i < 10; i++) {
                    if (i == 0) {
                        _yearsTimeYYLiContent += '<li class="fn-left on ' + (this.limitFun(_getFullYear + i) ? "" : "cannot") + '">' + (_getFullYear + i) + '年</li>'
                    } else {
                        _yearsTimeYYLiContent += '<li class="fn-left ' + (this.limitFun(_getFullYear + i) ? "" : "cannot") + '">' + (_getFullYear + i) + '年</li>'
                    }
                }
                for (var x = 0; x < 2; x++) {
                    _yearsTimeMMContent += '<ul class="fn-clear">'
                    for (var y = 1; y < 7; y++) {
                        if (y + x * 6 == _getMonth) {
                            _yearsTimeMMContent += '<li class="fn-left on">' + (y + x * 6) + '月</li>'
                        } else {
                            _yearsTimeMMContent += '<li class="fn-left">' + (y + x * 6) + '月</li>'
                        }
                    }
                    _yearsTimeMMContent += '</ul>'
                }

            }

            $('.yearsTime').find('#yearsTimeYYLi ul').html(_yearsTimeYYLiContent);

            if (this.format != 'YYYY') {

                $('.yearsTime').find('#yearsTimeMM').html(_yearsTimeMMContent);
                $('.yearsTime').find('input').val((this.limitFun(_getFullYear) ? _getFullYear : this.MinFun() ? this.MinFun() : this.MaxFun()) + '年');

            } else {

                $('.yearsTime').find('#yearsTimeMM').html('<a id="confirm" style="color: #fff;cursor: pointer;">确定</a>');
                $('.yearsTime').find('input').val((this.limitFun(_getFullYear) ? _getFullYear : this.MinFun() ? this.MinFun() : this.MaxFun()) + '年');

            }



        },
        keyDownInput: function() {

            var _val = $(this.elem).val(),
                _this = this;

            $(this.elem).on('keydown keyup', function(event) {

                if (event.keyCode != 8) {
                    $(this).val(_val)
                } else {
                    $(this).val('');
                    if (_this.callback) _this.callback($(_this.elem));
                }

            });

        },
        clickInput: function() {

            $('.yearsTime').find('input').click(function(event) {

                $('#yearsTimeYYLi').show();

            });

        },
        blurInput: function() {

            var _this = this;

            $('body').off('click.myNamespace').on('click.myNamespace', function(event) {

                var _target = $(event.target);
                if (_target.closest(".yearsTime").length == 0 && $(_this.elem)[0] != _target[0]) {
                    $(".yearsTime").remove();
                } else {
                    return false;
                }

            });

        },
        clickTime: function(_time) {

            var _this = this;

            if (this.format != 'YYYY') {

                $('.yearsTime').on('click', '#yearsTimeMM ul li:not(.cannot)', function(event) {

                    var _MM = $(this).text().match(/\d*/)[0],
                        _YY = $('.yearsTime').find('input').val().match(/\d*/)[0];

                    $(_this.elem).val(_YY + '-' + ((_MM.length == 1) ? '0' : '') + _MM).blur();
                    $('.yearsTime').remove();


                    if (_this.callback) _this.callback($(_this.elem), [_YY, _MM]);

                });

            } else {

                $('.yearsTime').on('click', '#yearsTimeYYLi ul li:not(".cannot")', function(event) {

                    var _YY = $(this).text().match(/\d*/)[0];

                    $(_this.elem).val(_YY).blur();
                    $('.yearsTime').remove();

                    if (_this.callback) _this.callback($(_this.elem), [_YY]);

                });

                $('.yearsTime').on('click', '#yearsTimeMM #confirm', function(event) {

                    var _YY = $(this).parent('#yearsTimeMM').prev('#yearsTimeYY').find('input').val().match(/\d*/)[0];

                    $(_this.elem).val(_YY).blur();
                    $('.yearsTime').remove();

                    if (_this.callback) _this.callback($(_this.elem), [_YY]);

                });

            }



        },
        clickyearsTimeNext: function() {

            var _this = this;
            $('.yearsTime').on('click', '#yearsTimeYY .yearsTimeNext', function(event) {
                var _val = _this.yearsTimeChange();
                if (!_this.limitFun(_val + 1)) return false;
                $('.yearsTime').find('input').val(_val + 1 + '年');
                if (_this.format != 'YYYY') {
                    var _yearsTimeMMContent = '',
                        _yearsTimeYYLiContent = '';
                    for (var x = 0; x < 2; x++) {
                        _yearsTimeMMContent += '<ul class="fn-clear">'
                        for (var y = 1; y < 7; y++) {
                            if (y + x * 6 == _this.getPresentTime()) {
                                _yearsTimeMMContent += '<li class="fn-left on ' + (_this.limitFunUp(_val + 1, y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                            } else {
                                _yearsTimeMMContent += '<li class="fn-left ' + (_this.limitFunUp(_val + 1, y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                            }
                        }
                        _yearsTimeMMContent += '</ul>'
                    }
                    $('.yearsTime').find('#yearsTimeMM').html(_yearsTimeMMContent);
                    for (var i = 0; i < 10; i++) {
                        if (i == 0) {
                            _yearsTimeYYLiContent += '<li class="fn-left on ' + (_this.limitFunUp(_val + 1 + i)[0] ? "" : "cannot") + '">' + (_val + 1 + i) + '年</li>'
                        } else {
                            _yearsTimeYYLiContent += '<li class="fn-left ' + (_this.limitFunUp(_val + 1 + i)[0] ? "" : "cannot") + '">' + (_val + 1 + i) + '年</li>'
                        }
                    }
                    $('.yearsTime').find('#yearsTimeYYLi ul').html(_yearsTimeYYLiContent);

                }
            })

        },
        clickyearsTimePrev: function() {
            var _this = this;
            $('.yearsTime').on('click', '#yearsTimeYY .yearsTimePrev', function(event) {
                var _val = _this.yearsTimeChange();
                if (!_this.limitFun(_val - 1)) return false;
                $('.yearsTime').find('input').val(_val - 1 + '年');
                if (_this.format != 'YYYY') {
                    var _yearsTimeMMContent = '',
                        _yearsTimeYYLiContent = '';
                    for (var x = 0; x < 2; x++) {
                        _yearsTimeMMContent += '<ul class="fn-clear">'
                        for (var y = 1; y < 7; y++) {
                            if (y + x * 6 == _this.getPresentTime()) {
                                _yearsTimeMMContent += '<li class="fn-left on ' + (_this.limitFunUp(_val - 1, y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                            } else {
                                _yearsTimeMMContent += '<li class="fn-left ' + (_this.limitFunUp(_val - 1, y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                            }
                        }
                        _yearsTimeMMContent += '</ul>'
                    }
                    $('.yearsTime').find('#yearsTimeMM').html(_yearsTimeMMContent);
                    for (var i = 0; i < 10; i++) {
                        if (i == 0) {
                            _yearsTimeYYLiContent += '<li class="fn-left on ' + (_this.limitFunUp(_val - 1 + i)[0] ? "" : "cannot") + '">' + (_val - 1 + i) + '年</li>'
                        } else {
                            _yearsTimeYYLiContent += '<li class="fn-left ' + (_this.limitFunUp(_val - 1 + i)[0] ? "" : "cannot") + '">' + (_val - 1 + i) + '年</li>'
                        }
                    }
                    $('.yearsTime').find('#yearsTimeYYLi ul').html(_yearsTimeYYLiContent);

                }
            })

        },
        yearsTimeChange: function() {

            return parseInt($('.yearsTime').find('input').val().match(/\d*/)[0])

        },
        clickyearsTimeYYLi: function() {
            var _this = this;
            $('.yearsTime').on('click', '#yearsTimeYYLi li:not(".cannot")', function(event) {
                var _val = $(this).text();
                $('.yearsTime').find('input').val(_val);
                $('#yearsTimeYYLi').hide();
                if (_this.format != 'YYYY') {
                    var _yearsTimeMMContent = '';
                    for (var x = 0; x < 2; x++) {
                        _yearsTimeMMContent += '<ul class="fn-clear">'
                        for (var y = 1; y < 7; y++) {
                            if (y + x * 6 == _this.getPresentTime()) {
                                _yearsTimeMMContent += '<li class="fn-left on ' + (_this.limitFunUp(_val.match(/\d*/)[0], y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                            } else {
                                _yearsTimeMMContent += '<li class="fn-left ' + (_this.limitFunUp(_val.match(/\d*/)[0], y + x * 6)[1] ? "" : "cannot") + '">' + (y + x * 6) + '月</li>'
                            }
                        }
                        _yearsTimeMMContent += '</ul>'
                    }
                    $('.yearsTime').find('#yearsTimeMM').html(_yearsTimeMMContent);

                }
            });

        },
        clickyearsTimeNextYY: function(_time) {

            var _this = this;
            $('.yearsTime').on('click', '#yearsTimeYYLi .yearsLiTimeNext', function(event) {
                _this.clickyearsTimeYY(function(_num) {
                    return _num + 10;
                }, _time)
            });

        },
        clickyearsTimePrevYY: function(_time) {

            var _this = this;
            $('.yearsTime').on('click', '#yearsTimeYYLi .yearsLiTimePrev', function(event) {
                _this.clickyearsTimeYY(function(_num) {
                    return _num - 10;
                }, _time)
            });

        },
        clickyearsTimeYY: function(_sum, _time) {

            var selfThis = this;

            $('#yearsTimeYYLi ul li').each(function(index, el) {
                var _this = $(el),
                    _num = parseInt(_this.text().match(/\d*/)[0]);
                _this.text(_sum(_num) + '年');
                (_time['getFullYear'] == _sum(_num)) ? _this.addClass('on'): _this.removeClass('on');
                if (selfThis.format != 'YYYY') {

                    selfThis.limitFunUp(_sum(_num))[0] ? _this.removeClass('cannot') : _this.addClass('cannot');

                } else {

                    selfThis.limitFun(_sum(_num)) ? _this.removeClass('cannot') : _this.addClass('cannot');

                }

            });

        },
        objLocation: function() {

            $('.yearsTime').css({
                top: $(this.elem).offset().top + $(this.elem).height(),
                left: $(this.elem).offset().left
            }).show();

        },
        getPresentTime: function() {

            var myDate = new Date();
            return {
                getFullYear: myDate.getFullYear(),
                getMonth: myDate.getMonth()
            };

        }
    }
    module.exports = yearsTime
})