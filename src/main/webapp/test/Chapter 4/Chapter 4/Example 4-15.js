$(document).ready(
    function()
    {        
        $('button#documentAddRow').click(
            function(event)
            {
                event.preventDefault();

                var tr = $('tr#johnLennonAlbumTemplate').clone(true);

                tr.removeAttr('id');

                $('table.johnLennonAlbums tbody').append(tr);

                tr.children('td:first').focus();
            }
        );
    }
);
