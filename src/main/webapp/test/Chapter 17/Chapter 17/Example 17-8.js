$(document).ready(
    function()
    {
        $('div#exampleDialog').dialog({
            title : 'Example Dialog',
            modal : true,
            autoOpen : false,
            resizable : true,
            draggable : true,
            show : 'explode',
            close : function(event, ui)
            {
                alert('Dialog Closed');
            },
            open : function(event, ui)
            {
                alert('Dialog Opened');
            }
        });
        
        $('input#exampleDialogOpen').click(
            function(event)
            {
                event.preventDefault();
                
                $('div#exampleDialog')
                    .dialog('open');
            }
        );
    }
);
