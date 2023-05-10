$(document).ready(function() {
  $("#minRange").on("input", function() {
    $("#minRangeResult").val($(this).val());
  });
  $("#maxRange").on("input", function() {
    $("#maxRangeResult").val($(this).val());
  });
});