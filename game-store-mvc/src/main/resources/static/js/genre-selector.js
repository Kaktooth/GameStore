$(document).ready(function () {
  $('.js-example-basic-multiple').select2({
        width: 'resolve'
      }
  );
  $(".js-example-basic-multiple-limit-4").select2({
    maximumSelectionLength: 7
  });
});