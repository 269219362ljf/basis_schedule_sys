$(document).on(
	'DOMContentLoaded',
    function()
    {
        $('div.finderDirectory, div.finderFile').on(
        	'click',
            function(event)
            {
                $('div.finderIconSelected')
                    .removeClass('finderIconSelected');
    
                $('span.finderDirectoryNameSelected')
                    .removeClass('finderDirectoryNameSelected');
    
                $(this).find('div.finderIcon')
                    .addClass('finderIconSelected');
    
                $(this).find('div.finderDirectoryName span')
                    .addClass('finderDirectoryNameSelected');
            }
        );
        
        $('div.finderDirectory, div.finderFile')
        	.filter(':first')
        	.trigger('click');
    }
);
