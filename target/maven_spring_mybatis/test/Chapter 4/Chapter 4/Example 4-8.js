$(document).ready(
    function()
    {        
        $('table#seventiesAlbumsTemplate thead')
            .insertBefore('table.seventiesAlbums tbody');
        
        $('table#seventiesAlbumsTemplate tfoot')
            .insertAfter('table.seventiesAlbums tbody');
    }
);
