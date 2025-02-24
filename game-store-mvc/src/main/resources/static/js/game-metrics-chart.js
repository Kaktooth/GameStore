var real_data = /*[[${chartData}]]*/'noValue';
$(document).ready(function () {
  google.charts.load('current', {
    packages: ['corechart', 'bar']
  });
  google.charts.setOnLoadCallback(drawColumnChart);
});

function drawColumnChart() {
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Date');
  data.addColumn('number', 'Interaction');
  Object.keys(real_data).forEach(function (key) {
    data.addRow([key, real_data[key]]);
  });
  var options = {
    chartArea:{left:0,top:0,width:"100%",height:"100%"},
    // bgColor: '#212529',
    // textStyle: {
    //   color: '#f0f8ff',
    // },
    width: 900,
    height: 600,
    isStacked: true,
    legend: {position: 'top', maxLines: 3},
    title: 'Selected interaction Chart',
    hAxis : {
      textStyle : {
        fontSize: 9
      }
    }
  };
  var chart = new google.visualization.ColumnChart(document
  .getElementById('chart_div'));
  chart.draw(data, options);
}
