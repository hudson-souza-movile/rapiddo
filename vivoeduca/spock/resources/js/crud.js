$(document).ready(function(){

	// SLICE NAME CRUMB-BAR
	function crumbSlice() {
		var title = $(".slice-title").text();
	    var titleLength = $(".slice-title").text().length;

		if (titleLength > 48) {
			var title = title.slice(0, 48);
	        var title = title + "...";
	        $(".slice-title").text(title);

	    }
	}

	crumbSlice();


	// FEEDBACK
	$('.feedback-msg.hide').delay(10000).animate({opacity: 0}, 400);
	$('.feedback-msg.hide').slideUp();


	// TAGS OVER
	$('.tags--container input').change(function(e){
		if ($('.tags--container input:checked').length > 3) {
			$(this).prop('checked', false);
			$('.tags-over').show().animate({opacity: 1}, 400);
			$('.tags-over').delay(4000).animate({opacity: 0}, 400);
			$('.tags-over').slideUp();
		}
	});


	// SKIP
	$('.skip-step').change(function(e) {
		$('#crumb-bar .right').removeClass('gray');
		$('#crumb-bar .right span').removeClass('icon-continue').addClass('icon-check').text('Criar curso');

	});


	// UPLOAD

});
