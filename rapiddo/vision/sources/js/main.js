$(document).ready(function() {
    $('.campanha .play').click(function() {
        $('body').addClass('video-open');
        $('#video').attr('src', 'https://www.youtube.com/embed/86XrBf1nruE?rel=0&amp;controls=0&amp;showinfo=0&amp;autoplay=1');
        // videoFull();

        return false;
    })

    $('.modal .close').click(function(){
        $('#video').attr('src', 'https://www.youtube.com/embed/86XrBf1nruE?rel=0&amp;controls=0&amp;showinfo=0');
        $('body').removeClass('video-open');

        return false;
    });

    function videoFull() {
        var winW = $(window).width();
        var winH = $(window).height();

        $('#video').css({
            width: winW + 'px',
            height: winH + 'px',
        })
    }

    // $(window).resize(function(){
    //     videoFull();
    // });
});
