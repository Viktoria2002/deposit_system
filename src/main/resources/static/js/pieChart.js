window.onload = function (){
    $.ajax({
        url: '/banksCharts',
        success: (result) => {
            let series = [];
            let data = [];
            for (let i = 0; i < result.length; i++) {
                let object = {};
                object.name = result[i].bankName;
                object.y = result[i].depositAmount;
                data.push(object);
            }
            let seriesObject = {
                name: 'Банк:',
                colorByPoint: true,
                data: data
            }
            series.push(seriesObject);
            drawPieChart(series);
        }
    })

    function drawPieChart(series) {
        Highcharts.chart('container2', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: 'Диаграмма \"Доли банков в объеме привлеченных вкладов\"',
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.y}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.y} %'
                    }
                }
            },
            series: series
        });
    };
}