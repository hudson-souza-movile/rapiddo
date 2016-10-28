$(document).ready(function() {
    FillTitleField();
    $("input.use-title").keyup(FillTitleField);
    $("*").click(FillTitleField);
});

function FillTitleField() {
    var title = $("input.use-title").val();
    var titleLength = $("input.use-title").val().length;

    if (titleLength == 0) {
        $(".upper-bar h1 span").removeClass('active').text("preencher abaixo");

    } else if (titleLength < 40) {
        $(".upper-bar h1 span").addClass('active').text(title);

    } else {
        var title = title.slice(0, 40);
        var title = title + "...";
        $(".upper-bar h1 span").addClass('active').text(title);
    }
}
