define(function (require, exports, module) {
    //我的工作台
    require('zyw/init');

    require('./workflowTemplate');

    var util = require('util')

    // ------ 项目情况 start
    function getId(id) {
        return document.getElementById(id);
    }
    $('.fnIsMyProject').on('click', function () {

        if (this.value == 'IS') {
            getId('isMyProjectIS').className = '';
            getId('isMyProjectNO').className = 'fn-hide';

            //getId('isMyProjectAIS').className = '';
            //getId('isMyProjectANO').className = 'fn-hide';
        } else {
            getId('isMyProjectIS').className = 'fn-hide';
            getId('isMyProjectNO').className = '';

           //getId('isMyProjectAIS').className = 'fn-hide';
           //getId('isMyProjectANO').className = '';
        }

    });
    // ------ 项目情况 end

    // ------ 待办、已办 start
    $('#stay2already').on('click', '.list-title li', function () {
        var $this = $(this);
        $this.addClass('active').siblings().removeClass('active');
        $('.stay2already').addClass('fn-hide').eq($this.index()).removeClass('fn-hide');
        if ($this.index() == 0) {
            $('#stay2already').find('.showback').show();
        } else {
            $('#stay2already').find('.showback').hide();
        }
    });

    // 被驳回
    $('#fnIsRejected').on('click', function () {
        if ($(this).prop('checked')) {
            $("#fnIsRejectedIS").show();
            $("#taskList").hide();
    		$("#groupTask").attr("checked",false);
    		$("#taskGroup").hide();
    		$("#taskGroupList").html("").hide();
        } else {
        	 $("#taskList").show();
        	 $("#fnIsRejectedIS").hide();
        }

    });
    // ------ 待办、已办 end

    //异步加载会议列表
    $("#councilList").load("/userHome/deskCouncilList.htm");
    //异步加载已办任务
    $("#doneTaskFrame").load("/userHome/doneTaskList.htm");
    
    $("#groupTask").click(function(){
    	if ($(this).prop('checked')) {
    		$("#taskGroup").show();
    		$("#taskGroupList").html("").hide();
    		$("#taskList").hide();
    		$("#fnIsRejectedIS").hide();
    		$("#fnIsRejected").attr("checked",false);
    	}else{
    		$("#taskGroup").hide();
    		$("#taskGroupList").html("").hide();
    		
            if ($('#fnIsRejected').prop('checked')) {
            	$("#fnIsRejectedIS").show();
            	$("#taskList").hide();
            } else {
            	$("#fnIsRejectedIS").hide();
            	$("#taskList").show();
            }
    	}
    });
    
    $("body").on("click","#backGroup",function(){
    	$("#taskGroup").show();
    	$("#taskGroupList").html("").hide();
    })

    // 可视化图形
    var echarts = require('echarts');
    /**
     * 转化为一个 echarts 饼图 需要的数据结构
     * @param  {[type]} arr   [服务器给出的数组]
     * @param  {[type]} name  [标示标题的key]
     * @param  {[type]} value [表述数据的key]
     * @return {[type]}       [title 用于legend，series用于series]
     */
    function getChartDate(arr, name, value, keyname) {

        var _o = {
            title: [],
            series: []
        };

        $.each(arr, function (index, el) {

            _o.title.push(el[name]);
            _o.series.push({
                name: el[name],
                value: el[value],
                jumpname: el[keyname]
            });

        });

        return _o;

    }

    //------ 项目分布 start

    if (document.getElementById('projectInfo')) {

        var projectInfoChart = echarts.init(document.getElementById('projectInfo')),
            projectInfoVal = getChartDate(eval(document.getElementById('fnCountJson').value), 'phasesMessage', 'count', 'phases');

        // 指定图表的配置项和数据
        var projectInfoOption = {
            title: {
                text: '项目分布图',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                left: 'left',
                data: projectInfoVal.title
            },
            series: [{
                name: '项目分布',
                type: 'pie',
                radius: '45%',
                center: ['50%', '60%'],
                data: projectInfoVal.series,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        projectInfoChart.setOption(projectInfoOption);

        projectInfoChart.on('click', function (params) {
            util.openDirect('/projectMg/index.htm', '/projectMg/list.htm?phases=' + params.data.jumpname)
        })

        // 假若没有数据
        if (!projectInfoVal.title.length) {
            document.getElementById('projectInfo').innerHTML = '项目分布图暂无数据';
        }

    }

    //------ 项目分布 end


    //------ 资金使用 start

    if (document.getElementById('moneyInfo')) {

        var moneyInfoChart = echarts.init(document.getElementById('moneyInfo'));

        // 指定图表的配置项和数据
        var moneyInfoOption = {
            title: {
                text: '资金使用情况',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['放用款', '放款', '授信金额']
            },
            series: [{
                name: '资金使用情况',
                type: 'pie',
                radius: '45%',
                center: ['50%', '50%'],
                data: [{
                    value: 10,
                    name: '放用款'
                }, {
                    value: 56,
                    name: '放款'
                }, {
                    value: 56,
                    name: '授信金额'
                }],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        moneyInfoChart.setOption(moneyInfoOption);

    }

    //------ 资金使用 end


    //------ 客户部门分布 start

    if (document.getElementById('departmentInfo')) {

        var departmentInfoChart = echarts.init(document.getElementById('departmentInfo')),
            departmentTitle = [],
            departmentData = [{
                value: 0,
                name: '暂无'
            }];

        var departmentInfoJson = $("#departmentInfoJson").html();
        if (departmentInfoJson) {
            departmentData = [];
            departmentInfoJson = eval('(' + departmentInfoJson + ')');
            for (key in departmentInfoJson) {
                departmentTitle.push(key)
                departmentData.push({
                    name: key,
                    value: departmentInfoJson[key]
                });
            }
        }

        // 指定图表的配置项和数据
        var departmentInfoOption = {
            title: {
                text: '客户部门分布',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                left: 'top',
                data: departmentTitle
            },
            series: [{
                name: '客户部门分布',
                type: 'pie',
                radius: '45%',
                center: ['50%', '60%'],
                data: departmentData,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        departmentInfoChart.setOption(departmentInfoOption);

    }

    //------ 客户部门分布 end

    //------ 地域分布 start

    if (document.getElementById('regionalInfo')) {

        var regionalInfoChart = echarts.init(document.getElementById('regionalInfo')),
            indicator = [{
                max: 100,
                name: '暂无'
            }],
            dataValue = [0];

        var regionalInfoJson = $("#regionalInfoJson").html();
        var max = 0;
        if (regionalInfoJson) {
            indicator = new Array();
            dataValue = new Array();
            regionalInfoJson = eval('(' + regionalInfoJson + ')');
            for (key in regionalInfoJson) {
                if (regionalInfoJson[key] > max) {
                    max = regionalInfoJson[key];
                }
            }
            for (key in regionalInfoJson) {
                indicator.push({
                    text: key,
                    max: max
                });
                dataValue.push(regionalInfoJson[key]);
            }
        }
        // console.log(indicator);
        // console.log(dataValue);

        // 指定图表的配置项和数据
        var regionalInfoOption = {
            title: {
                text: '客户地域分布图',
                x: 'center',
                y: 'bottom'
            },
            tooltip: {
                trigger: 'axis'
            },
            radar: [{
                indicator: indicator,
                center: ['50%', '50%'],
                radius: 80
            }],
            series: [{
                type: 'radar',
                tooltip: {
                    trigger: 'item'
                },
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                },
                data: [{
                    value: dataValue,
                    name: '客户地域分布'
                }]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        regionalInfoChart.setOption(regionalInfoOption);

    }

    //------ 地域分布 end

});