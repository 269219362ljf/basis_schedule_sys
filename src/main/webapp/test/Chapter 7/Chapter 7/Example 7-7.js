$(document).ready(
    function()
    {
        //这几个方法只能挂靠在document身上
        $(document)
            .ajaxSend(
                function(event, request, options)
                {
                    if (decodeURI(options.url).indexOf('Example 7-7') != -1)
                    {
                        $('div#folderActivity').show();
                    }
                }
            )
            .ajaxSuccess(
                function(response, status, request)
                {
                    if (decodeURI(request.url).indexOf('Example 7-7') != -1)
                    {
                        $('div#folderActivity').hide();
                    }
                }
            )
            .ajaxError(
                function(request, status, error)
                {
                    if (decodeURI(request.url).indexOf('Example 7-7') != -1)
                    {
                        $('div#folderActivity').hide();
                    }
                }
            );
        
        $('img.folderTreeHasChildren').click(
            function()
            {
                var arrow = 'tree/down.png';
            
                if (!$(this).next().children('ul').length)
                {
                    $(this).next()
                        .load(
                            'Example%207-7/' +
                                $(this)
                                    .prev()
                                    .data('id') + '.html',
                            function()
                            {
                                $(this)
                                    .show()
                                    .prev()
                                    .attr('src', arrow);
                            }
                        );
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