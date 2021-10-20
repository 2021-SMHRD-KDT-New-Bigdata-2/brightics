function nullCheck(data){
    if (!data){
        return true;
    }
    else {
        return false;
    }
}
var newsChartData
var newsStart;
var newsEnd;
$(document).ready(function () {
    newsChart()
    $("#newsCheck").click(function () {
        newsStart= document.getElementById("newsStart").value;
        newsEnd= document.getElementById("newsEnd").value;

        $("#news_error").text("")
        if(nullCheck(newsStart)||nullCheck(newsEnd) || Date.parse(newsStart)>Date.parse(newsEnd)){
            $("#news_error").html("<h4><i class=\"fas fa-exclamation-triangle\"></i>Please check the date</h4>")
            return;
        }

        $("#news_title").text(newsStart+"부터 "+newsEnd+ "까지의 뉴스 수");

        newsChart();
    })

    function newsChart() {
        $.ajax({
            url: location.pathname+"/getnewsgraph" ,
            data: {
                "start": newsStart,
                "end": newsEnd
            },
            type: 'get',
            success: function (data) {
                data.unshift(['기간', '빈도'])
                newsChartData = data
                google.charts.load('current', {'packages': ['corechart']});
                google.charts.setOnLoadCallback(drawNewsChart);
            },
            error: function (err) {
            }
        })
    }})
var stockChartData
var stockStart;
var stockEnd;
$(document).ready(function () {
    stockChart()
    $("#stockCheck").click(function () {
        stockStart= document.getElementById("stockStart").value;
        stockEnd= document.getElementById("stockEnd").value;
        $("#stock_error").text("")
        if(nullCheck(stockStart)||nullCheck(stockEnd) || Date.parse(stockStart)>Date.parse(stockEnd)){
            $("#stock_error").html("<h4><i class=\"fas fa-exclamation-triangle\"></i>Please check the date</h4>")
            return;
        }
        $("#stock_title").text(stockStart+"부터 "+stockEnd+ "까지의 주식 차트");
        stockChart()
    })
    function stockChart() {
        $.ajax({
            url: location.pathname+"/getstockgraph" ,
            data: {
                "start": stockStart,
                "end": stockEnd
            },
            type: 'get',
            success: function (data) {
                stockChartData = data
                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawStockChart);
            },
            error: function (err) {
            }
        })
    }})
function drawNewsChart() {
    var data = google.visualization.arrayToDataTable(newsChartData);
    var options = {
        title: "",
        hAxis: {title: '기간', titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0},
        backgroundColor: "transparent",

        hAxis: {
            textStyle: {
                color: 'white'
            },
            titleTextStyle: {
                color: 'white'
            }
        },
        vAxis: {
            textStyle: {
                color: 'white'
            },
            titleTextStyle: {
                color: 'white'
            }
        },
        legend:'none',


    };
    var chart = new google.visualization.AreaChart(document.getElementById('chart_div_news'));
    chart.draw(data, options);

}
function drawStockChart() {
    var data = google.visualization.arrayToDataTable(stockChartData, true);
    var options = {
        legend:'none',
        backgroundColor: "transparent",
        candlestick: {
            fallingColor: { fill: 'blue' },
            risingColor: {fill: 'red', stroke:'red', colors:'red'}

        },





        hAxis: {
            textStyle: {
                color: 'white'
            },
            titleTextStyle: {
                color: 'white'
            }
        },
        vAxis: {
            textStyle: {
                color: 'white'
            },
            titleTextStyle: {
                color: 'white'
            },

        },
    };
    var chart = new google.visualization.CandlestickChart(document.getElementById('chart_div_stock'));
    chart.draw(data, options);
}