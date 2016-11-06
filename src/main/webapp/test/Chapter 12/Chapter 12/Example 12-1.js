$(document).ready(
    function()
    {
        $('div.finderDirectory')
            .mousedown(
                function()
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
            )
            .draggable();
    }
);
