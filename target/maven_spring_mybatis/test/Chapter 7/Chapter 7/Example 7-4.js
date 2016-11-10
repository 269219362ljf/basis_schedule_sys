$(document).ready(
    function()
    {
        $('img.folderTreeHasChildren').click(
            function()
            {
                var arrow = 'tree/down.png';
            
                if (!$(this).next().children('ul').length)
                {
                    $(this).next().load(
                        'Example%207-4/' +
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
                    //toggle事件是切换可见性show->hide,hide->show
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