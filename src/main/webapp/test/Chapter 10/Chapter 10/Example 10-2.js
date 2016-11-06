$(document).ready(
    function()
    {
        $('div#container')
			.scroll(
				function()
				{
					$('span#vertical-scroll-value')
						.text($(this).scrollTop());
						
					$('span#horizontal-scroll-value')
						.text($(this).scrollLeft());
				}
			);
		$('button.block-button')
			.click(
				function()
				{
					$('div#container')
						.scrollTop($('div#' + $(this).data().block).offset().top - $('div#container').offset().top 
							+ $('div#container').scrollTop())
						.scrollLeft($('div#' + $(this).data().block).offset().left - $('div#container').offset().left 
							+ $('div#container').scrollLeft());
				}
			);
    }
);
