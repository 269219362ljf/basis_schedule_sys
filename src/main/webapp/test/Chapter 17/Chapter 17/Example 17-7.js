$(document).ready(
    function()
    {
        $('div#exampleDialog').dialog({
            title : 'Example Dialog',
            modal : true,
            autoOpen : false,
            resizable : true,
            draggable : true,
            show : 'explode'//设置动画
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
