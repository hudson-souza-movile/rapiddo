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
            $('body').addClass('hidden--ios');

        } else if (isMobile.iOS()) {
            $('body').addClass('hidden--android');

        } else {
            $('body').addClass('unrecognized');
        }
    }


    if ($(window).width() >= 960) {
        $('body').addClass('unrecognized');

    } else {
        mobileClass();
    }


    // Tabs
    $('#tabs .tab-col a.cupons').click(function() {
        if ($(this).hasClass('show')) {
            return false;

        } else {
            $('#tabs .pages-view.winners').hide();
            $('#tabs .tab-col a.winners').removeClass('show');
            $('#tabs .pages-view.cupons').show();
            $(this).addClass('show');

        }

        return false;
    });

    $('#tabs .tab-col a.winners').click(function() {
        if ($(this).hasClass('show')) {
            return false;

        } else {
            $('#tabs .pages-view.cupons').hide();
            $('#tabs .tab-col a.cupons').removeClass('show');
            $('#tabs .pages-view.winners').show();
            $(this).addClass('show');

        }

        return false;
    });

    // Modal
    $('ul.tab-row a').click(function() {
        if ($(this).hasClass('regulamento')) {
            $('.modal-overlay, #modal').fadeIn(400);
            $('#modal .content.regulamento').fadeIn(560);

        } else if ($(this).hasClass('termos')) {
            $('.modal-overlay, #modal').fadeIn(400);
            $('#modal .content.termos').fadeIn(560);

        } else if ($(this).hasClass('gift'))  {
            $('.modal-overlay, #modal').fadeIn(400);
            $('#modal .content.gift').fadeIn(560);

        }

        return false;

    });

    // Modal Payments
    $('#purchase ul.tab-col a').click(function() {
        if ($(this).hasClass('pay-credit')) {
            $('.modal-overlay, #modal').fadeIn(400);
            $('#modal .content.pay-credit-modal').fadeIn(560);

        } else if ($(this).hasClass('pay-debit')) {
            $('.modal-overlay, #modal').fadeIn(400);
            $('#modal .content.pay-debit-modal').fadeIn(560);

        } else if ($(this).hasClass('pay-gift'))  {
            $('.modal-overlay, #modal').fadeIn(400);
            $('#modal .content.pay-gift-modal').fadeIn(560);

        }

        return false;

    });

    $('#modal .title a').click(function() {
        $('#modal, #modal .content').fadeOut(320);
        $('.modal-overlay').fadeOut(400);
        return false;

    });

});
