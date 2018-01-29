'use strict'
define	(function(require, exports, module) {
    require('Y-msg');
    require('zyw/publicPage');
    var template = require('arttemplate');
    require('tmbp/submit.common');
    var echarts = require('echarts');
    var $body = $('body');
    var myChartBox = document.getElementById('personCommissionDtl');
    var myChart = echarts.init(myChartBox);

    var fnListSearchBtn = $("#fnListSearchBtn");
	
	fnListSearchBtn.on("click",function(){
		$("#fnListSearchForm").submit();
	});	
	//暂时用的假数据 STATR
/*	var xAxisData = [];
	var data1 = [];
	var data2 = [];
   
	var positiveData = $("#positiveData").val();
	var negativeData = $("#negativeData").val();
	var xAxisData = $("#xAxisData").val();
	if (!!xAxisData) {
		xAxisData = eval("("+xAxisData+")");
	}
	
	if (!!positiveData) {
		data1 = eval("("+positiveData+")");
	}
	
	if (!!negativeData) {
		data2 = eval("("+negativeData+")");
	}
	console.log(positiveData);
	console.log(negativeData);
	console.log(xAxisData);

    var itemStyle = {
        normal: {
        },
        emphasis: {
            barBorderWidth: 1,
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
            shadowColor: 'rgba(0,0,0,0.5)'
        }
    };

    var option = {
        backgroundColor: '#eee',
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            top: 40,
            bottom: 30
        },
        xAxis: {
            inverse: true,
            splitArea: {show: false}
        },
        yAxis: {
            data: xAxisData,
            name: '佣金结算明细',
            silent: false,
            axisLine: {onZero: true},
            splitLine: {show: false},
            splitArea: {show: false}
        },
        series: [
            {
                name: '佣金收入',
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    }
                },
                type: 'bar',
                stack: 'one',
                itemStyle: itemStyle,
                data: data1
            },
            {
                name: '佣金提取',
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    }
                },
                type: 'bar',
                stack: 'one',
                itemStyle: itemStyle,
                data: data2
            }
        ]
    };

    function renderBrushed(params) {
        var brushed = [];
        var brushComponent = params.batch[0];

        for (var sIdx = 0; sIdx < brushComponent.selected.length; sIdx++) {
            var rawIndices = brushComponent.selected[sIdx].dataIndex;
            brushed.push('[Series ' + sIdx + '] ' + rawIndices.join(', '));
        }
        myChart.setOption({
            title: {
                backgroundColor: '#333',
                text: 'SELECTED DATA INDICES: \n' + brushed.join('\n'),
                bottom: 0,
                right: 0,
                width: 100,
                textStyle: {
                    fontSize: 12,
                    color: '#fff'
                }
            }
        });
      
    };
    if($(myChartBox).length == 1){
        myChart.setOption(option);
        myChart.on('brushSelected', renderBrushed);
        myChart.on('click', function (param) {
        	console.log(param);
        	//业务员的id
           var businessUserId = $("input[name='businessUserId']").val();
           //日期
           var time = param.name;
           //弹框，显示当天的明细
           var seriesIndex = param.seriesIndex == 0 ? "INCOME" : "EXPENDITURE";
           $.ajax({
               url:'/insurance/personCommissionDetail/getPersonCommissionDetail.json',
               type:'post',
               data:{
            	   businessUserId:businessUserId,
            	   commissionTime:time,
                   commissionType:seriesIndex
               },
               success:function (res) {
                   if(res.success){
                	   var data = res.returnObject;
                	  var $incomeContent = $($("#PERSONCOMMISSIONDETAIL_INCOME").html());
                	  var $expenditureContent = $($("#PERSONCOMMISSIONDETAIL_EXPENDITURE").html());
                	  var $content = param.seriesIndex == 0  ? $incomeContent : $expenditureContent;
                	  if (data) {
                		  if (param.seriesIndex == 0) {
                			  var _html = '';
                			  $.each(data,function(i,v){
                				  _html +="<tr>" 
                					  _html +="<td>"+returnVal($(v).attr("insuranceNo"))+"</td>" 
                					  _html +="<td>"+$(v).attr("commissionAmount").amount+"</td>" 
                					  _html +="<td>"+$(v).attr("commissionTime")+"</td>" 
                			     _html +="</tr>" 
                			  });
                			  $incomeContent.find('tbody').html(_html);
                		  } else {
                			  var _incomHtml = '';
                			  $.each(data,function(i,v){
                				    _incomHtml +="<tr>" 
                				    	_incomHtml +="<td>"+$(v).attr("commissionAmount").amount+"</td>" 
                				    	_incomHtml +="<td>"+$(v).attr("commissionTime")+"</td>" 
                				   _incomHtml +="</tr>" 
                			  });
                			  $expenditureContent.find('tbody').html(_incomHtml);
                		  }
                	  }
                      $body.Y('Window', {
                          content: $content,
                          modal: true,
                          key: 'modalwnd',
                          simple: true,
                          closeEle: '.closeBtn',
                      });
                   }else {
                       Y.alert('提示',res.message);
                   }
               }
           });
        });
    };
    //暂时用的假数据 END

    function returnVal (val) {
    	return val == null || val == "" ? "" : val;
    }*/

    $body.on('click','.changeViewTable',function () {
        if($('.presonCommissionCanvas').hasClass('fn-hide')){
        	$("input[name='type']").val("presonCommissionCanvas");
            $('.presonCommissionCanvas').removeClass('fn-hide');
            $('.presonCommissionTable').addClass('fn-hide');
        }else {
        	$("input[name='type']").val("viewList");
            $('.presonCommissionCanvas').addClass('fn-hide');
            $('.presonCommissionTable').removeClass('fn-hide');
        }
    });
/*    if($(".presonCommissionCanvas").hasClass('canvas')) {
    	 $('.presonCommissionCanvas').removeClass('fn-hide');
         $('.presonCommissionTable').addClass('fn-hide');
    }
    if($(".presonCommissionTable").hasClass('viewList')) {
    	$('.presonCommissionCanvas').addClass('fn-hide');
        $('.presonCommissionTable').removeClass('fn-hide');
    }*/
  
    $body.on('click','.toChangeMonth',function () {
        var val = $(this).attr('changeval');
        console.log(val);
        $('#fastSearchTime').val(changeMonth(val));
    });
    function changeMonth(n) {
        var TRUE_TIME = new Date();
        var TRUE_YEAR = TRUE_TIME.getFullYear();
        var TRUE_MONTH = TRUE_TIME.getMonth() + 1;
        var TRUE_DAY = TRUE_TIME.getDate();
        var TEUE_date = TRUE_YEAR + '-' + TRUE_MONTH+'-'+TRUE_DAY;
        var begingDate = $("#beginDate").val();

        var beginTime = begingDate.split("-");
        var beginYear = parseInt(beginTime[0]);
        var beginMonth = parseInt(beginTime[1]);
        var newTime = TEUE_date.split("-");
        var year =  parseInt(newTime[0]);
        var month = parseInt(newTime[1]);
        var newYear,newMonth,newDay;

        if(n==0){
            year = TRUE_YEAR;
            newYear = TRUE_YEAR;
            month = TRUE_MONTH;
            newMonth = TRUE_MONTH;
        }else if(n==-2){
            year = TRUE_YEAR;
            newYear = TRUE_YEAR;
            month = TRUE_MONTH;
            newMonth = TRUE_MONTH-2;
        }else {
            year = beginYear;
            newYear = beginYear;
            month = beginMonth-1;
            newMonth = beginMonth-1;
        }
        if(newMonth > 12){
            newYear += 1;
            newMonth -= 12;
        }else if(newMonth < 1){

            newYear -= 1;
            newMonth += 12;
        }else if(newMonth > month && num < 0){

            newYear -= 1;
            newMonth += 12;
        }else {
            newYear = newYear;
            newMonth = newMonth;
        };
        if(month > 12){
            year += 1;
            month -= 12;
        }else if(month < 1){

            year -= 1;
            month += 12;
        }else if(newMonth > month && num < 0){

            year -= 1;
            month += 12;
        }else {
            year = year;
            month = month;
        };

        TRUE_TIME.setMonth(month);
        TRUE_TIME.setDate(0);
        var nowDay = new Date(TRUE_TIME).getDate();//本月最后一天为
        if(month>=1&&month<=9){
            month="0"+month;
        }
        if(newMonth>=1&&newMonth<=9){
            newMonth="0"+newMonth;
        }
        if(TRUE_DAY>=1&&TRUE_DAY<=9){
            TRUE_DAY="0"+TRUE_DAY;
        }
        if(n==0||n==-2){
            $("#endDate").val(year+'-'  + month+'-'+TRUE_DAY);
        } else {
            $("#endDate").val(year+'-'  + month+'-'+nowDay);
        }
        $("#beginDate").val(newYear+'-' + newMonth+'-'+"01");

        return newYear + '-' + newMonth;
    }


});