var real_data = /*[[${chartData}]]*/'noValue';
$(document).ready(function () {
  google.charts.load('current', {
    packages: ['corechart', 'bar']
  });
  google.charts.setOnLoadCallback(drawColumnChart);
  google.charts.setOnLoadCallback(drawPieChart);
});

function drawColumnChart() {
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Date');
  data.addColumn('number', 'Interaction');
  Object.keys(real_data).forEach(function (key) {
    data.addRow([key, real_data[key]]);
  });
  var options = {
    width: 870,
    height: 550,
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

function drawPieChart() {
  var data = new google.visualization.DataTable();
  data.addColumn('string', 'Date');
  data.addColumn('number', 'Interaction');
  Object.keys(real_data).forEach(function (key) {
    data.addRow([key, real_data[key]]);
  });
  var options = {
    width: 870,
    height: 400,
    title: 'Selected interaction during month'
  };
  var chart = new google.visualization.PieChart(document
  .getElementById('piechart'));
  chart.draw(data, options);
}