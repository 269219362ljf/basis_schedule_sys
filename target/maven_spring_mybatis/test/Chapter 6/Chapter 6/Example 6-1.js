$(document).ready(
    function()
    {
        var contextMenuOn = false;
        
        $(document).on(
            'contextmenu',
            function(event)
            {
                event.preventDefault();

                var contextMenu = $('div#contextMenu');
                
                contextMenu.show();
                
                // The following bit gets the dimensions of the viewport
                // Thanks to quirksmode.org
                var vpx, vpy;
                
                if (self.innerHeight)
                {
                    // all except Explorer
                    vpx = self.innerWidth;
                    vpy = self.innerHeight;
                }
                else if (document.documentElement && 
                            document.documentElement.clientHeight)
                {
                    // Explorer 6 Strict Mode
                    vpx = document.documentElement.clientWidth;
                    vpy = document.documentElement.clientHeight;
                }
                else if (document.body)
                {
                   // other Explorers
                   vpx = document.body.clientWidth;
                   vpy = document.body.clientHeight;
                }
                
                // Reset offset values to their defaults
                contextMenu.css({
                    top : 'auto',
                    right : 'auto',
                    bottom : 'auto',
                    left : 'auto'
                });
                
                 
                // If the height or width of the context menu is greater than the amount 
                // of pixels from the point of click to the right or bottom edge of the 
                // viewport adjust the offset accordingly
                if (contextMenu.outerHeight() > (vpy - event.pageY))
                {
                    contextMenu.css('bottom', (vpy - event.pageY) + 'px');
                }
                else
                {
                    contextMenu.css('top', event.pageY + 'px');
                }
                
                if (contextMenu.outerWidth() > (vpx - event.pageX))
                {
                    contextMenu.css('right',  (vpx - event.pageX) + 'px');
                }
                else
                {
                    contextMenu.css('left', event.pageX + 'px');
                }
            }    
        );

        $('div#contextMenu').hover(
            function()
            {
                contextMenuOn = true;
            },
            function()
            {
                contextMenuOn = false;
            }    
        );
              
        $(document).mousedown(
            function()
            {
                if (!contextMenuOn)
                {
                    $('div#contextMenu').hide();
                }
            }
        );
    }
);
