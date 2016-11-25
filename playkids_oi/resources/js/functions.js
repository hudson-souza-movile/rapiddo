// FUNCTIONS
$(document).ready(function() {

    // Mobile View
    var isMobile = {
        Android: function() {
            return /Android/i.test(navigator.userAgent);
        },

        iOS: function() {
            return /iPhone|iPad|iPod/i.test(navigator.userAgent);
        },

        any: function() {
            return (isMobile.Android() || isMobile.iOS());
        }
    };

    function mobileClass() {
        if (isMobile.Android()) {
            $('.help-ios').hide();

        } else if (isMobile.iOS()) {
            $('.help-android').hide();

        } else {
            $('.help-ios').hide();
        }
    }

    mobileClass();


    // HELP
    $('#help .next').click(function() {
        $('.help-nav .active').removeClass('active').next().addClass('active');
        $(this).parent().parent().hide().next().fadeIn();
        return false;

    });

    $('#help .back').click(function() {
        $('.help-nav .active').removeClass('active').prev().addClass('active');
        $(this).parent().parent().hide().prev().fadeIn();
        return false;

    });

});
