$(document).ready(function () {
    $('select').selectpicker();
    $("#lang-picker-select").on("change", function () {
        $("#lang-picker-form").submit();
    });
    $('#complaints').on('keyup', function () {
        var textArea = document.getElementById('complaints');
        var invalidComplaintsMsg = document.getElementById('complaints-msg');
        var patternMatch = textArea.value.match("^[a-zA-Zа-яА-ЯёЁ ,.-]{0,4096}$");

        if (!patternMatch) {
            invalidComplaintsMsg.innerHTML = "Invalid or empty text. Only alphabetic, digits, -,. symbols";
        } else {
            invalidComplaintsMsg.innerHTML = null;
        }

        var applyDoctorButtons = document.getElementsByClassName('apply-to-doctor');
        for (var i = 0; i < applyDoctorButtons.length; i++) {
            applyDoctorButtons[i].disabled = !patternMatch;
        }
    });
    $('#therapy-description').on('keyup', function () {
        var textArea = document.getElementById('therapy-description');
        var invalidComplaintsMsg = document.getElementById('description-msg');
        var patternMatch = textArea.value.match("^[a-zA-Zа-яА-ЯёЁ0-9 ,.-]{0,4096}$");

        if (!patternMatch) {
            invalidComplaintsMsg.innerHTML = "Invalid or empty text. Only alphabetic, digits, -,. symbols";
        } else {
            invalidComplaintsMsg.innerHTML = null;
        }

        var submitTherapyButton = document.getElementById('submit-prescription-btn');
        submitTherapyButton.disabled = !patternMatch;
    });
    // todo remove code duplication
});
