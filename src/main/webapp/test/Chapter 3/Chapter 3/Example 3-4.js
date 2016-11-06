$(document).on(
    'DOMContentLoaded',
    function()
    {
        var eventHandlerActive = false;
        
        function applyEventHandler()
        {
            if (eventHandlerActive)
            {
                return;
            }
            //click.finder 前面是事件，点后面是自定义的名字
            //click.finder 整个是一个事件处理器
            //on把事件处理器挂在对应div
            $('div#finderFiles').on(
                'click.finder',
                'div.finderDirectory, div.finderFile',
                function(event)
                {
                    $('div.finderIconSelected')
                        .removeClass('finderIconSelected');
        
                    $('span.finderDirectoryNameSelected')
                        .removeClass('finderDirectoryNameSelected');
                    
                    $('span.finderFileNameSelected')
                        .removeClass('finderFileNameSelected');
        
                    $(this).find('div.finderIcon')
                        .addClass('finderIconSelected');
        
                    $(this).find('div.finderDirectoryName span')
                        .addClass('finderDirectoryNameSelected');
    
                    $(this).find('div.finderFileName span')
                        .addClass('finderFileNameSelected');
                }
            );
            
            eventHandlerActive = true;
        }
        
        function removeEventHandler()
        {
            $('div#finderFiles').off('click.finder');
            
            eventHandlerActive = false;
        }

        $('div#finderFiles div.finderNode:first')
            .trigger('click');
        
        applyEventHandler();

        $('button#finderApplyEventHandler').click(
            function()
            {
                applyEventHandler();
            }
        );
        
        $('button#finderRemoveEventHandler').click(
            function()
            {
                removeEventHandler();
            }
        );
    }
);
