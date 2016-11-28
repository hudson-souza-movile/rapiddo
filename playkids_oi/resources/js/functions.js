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
            $('.swiper-android').show();

        } else if (isMobile.iOS()) {
            $('.swiper-android').hide();
            $('.swiper-ios').show();

        } else {
            $('.swiper-ios').hide();
        }
    }

    mobileClass();


    // Swiper
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true
    });

});
