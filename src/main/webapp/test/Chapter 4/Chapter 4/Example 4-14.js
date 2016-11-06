$(document).ready(
    function()
    {        
        $('button#documentEmpty').click(
            function(event)
            {
                event.preventDefault();

                $('td').empty();
            }
        );
        
        $('button#documentRemove').click(
            function(event)
            {
                event.preventDefault();
                
                $('h4, table').remove();
            }
        );
    }
);
