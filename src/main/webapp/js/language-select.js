$(document).ready(function () {
    $('select').selectpicker();
    $("#lang-picker-select").on("change", function () {
        $("#lang-picker-form").submit();
    })
});
