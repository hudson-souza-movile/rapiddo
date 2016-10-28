$(document).ready(function(){

	// MAIN-CONTENT MARGIN
	function upperHeight() {
		var upH = $('.upper-bar').outerHeight();
		$('.main-margin').css('margin-top',upH);
	}

	$(window).resize(upperHeight);
	upperHeight();


	// SIDEMODAL
	// use .click-sidemodal on element to magic happens! :)
	$('.click-sidemodal').click(function() {
		if ($('body').hasClass('new-class')) {
			$('body').css('overflow-y', 'hidden').addClass('uploading');
			$('.sidemodal__bg').fadeIn();
			$('.sidemodal__cancel').hide();
			$('#sidemodal').animate({right: 0}, 300);
			return false;

		} else {
			$('body').css('overflow-y', 'hidden');
			$('.sidemodal__bg').fadeIn();
			$('.sidemodal__cancel').hide();
			$('#sidemodal').animate({right: 0}, 300);
			return false;

		}

	});

	// close
	$('.back-btn, .sidemodal__bg').click(function() {
		if ($('body').hasClass('uploading')) {
			// upload
			$('.sidemodal__bg').css({
				'background-color': 'rgba(0,0,0,0.88)',
				'z-index': '70'
			});
			$('.sidemodal__cancel').fadeIn().css('z-index','70');

		} else {
			$('body').css('overflow-y', 'auto');
			$('#sidemodal').animate({right: '-56%'}, 300);
			$('.sidemodal__bg').fadeOut();
			return false;

		}
	});

	// cancel upload
	$('.sidemodal__cancel .disabled').click(function() {
		$('.sidemodal__bg').css({
			'background-color': 'rgba(0,0,0,0.6)',
			'z-index': '40'
		});
		$('.sidemodal__cancel').fadeOut();
		return false;

	});

});
