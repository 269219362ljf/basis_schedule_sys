$(document).ready(
    function()
    {        
        $('img.folderTreeHasChildren').click(
            function()
            {
                var arrow = 'tree/down.png';
            
                if (!$(this).next().children('ul').length)
                {
                    var tree = $(this);
                    
                    var file = (
                        $(this)
                            .prev()
                            .data('id') + '.html'
                    );
                    
                    $.ajax({
                        beforeSend : function(event, request, options)
                        {
                            $('div#folderActivity').show();
                        },
                        success : function(response, status, request)
                        {                            
                            $('div#folderActivity').hide();

                            tree.attr('src', arrow)
                                .next()
                                .html(response)
                                .show();
                        },
                        error : function(request, status, error)
                        {
                            $('div#folderActivity').hide();
                        },
                        url : 'Example%207-8/' + file,
                        dataType : 'html'
                    });
                }
                else
                {
                    $(this).next().toggle();
                    
                    if ($(this).attr('src').indexOf('down') != -1)
                    {
                        arrow = 'tree/right.png';
                    }

                    $(this).attr('src', arrow);
                }
            }
        );

    }
);