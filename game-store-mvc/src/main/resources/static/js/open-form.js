$(document).click(function (event) {
  var $target = $(event.target);
  if (!$target.closest('#drop').length &&
      $('#drop').is(":visible")) {
    $('#drop').hide();
    $('#searchString').text('');
    $('#searchForm').submit();
  }
  if (!$target.closest('#drop2').length &&
      $('#drop2').is(":visible")) {
    $('#drop2').hide();
    $('#searchString').text('');
    $('#searchForm').submit();
  }
});

function show(dropdown) {
  if (document.getElementById(dropdown).classList.toggle("show")) {
    document.getElementById(dropdown).classList.toggle("show");
  }
}