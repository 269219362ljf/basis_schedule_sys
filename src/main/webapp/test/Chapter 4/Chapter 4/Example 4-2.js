$(document).ready(
    function()
    {        
        $('button#documentAddClass').click(
            function(event)
            {
                event.preventDefault();
                
                $('table').addClass('johnLennonAlbums');
            }
        );

        $('button#documentHasClass').click(
            function(event)
            {
                event.preventDefault();

                if ($('table').hasClass('johnLennonAlbums'))
                {
                    alert('The <table> has the class johnLennonAlbums');
                }
                else
                {
                    alert('The <table> does not have the class johnLennonAlbums');
                }
            }
        );

        $('button#documentRemoveClass').click(
            function(event)
            {
                event.preventDefault();

                $('table').removeClass('johnLennonAlbums');
            }
        );

        $('button#documentToggleClass').click(
            function(event)
            {
                event.preventDefault();

                $('table').toggleClass('johnLennonAlbums');
            }
        );
    }
);
