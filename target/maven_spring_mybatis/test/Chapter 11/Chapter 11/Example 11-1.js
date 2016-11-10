$.fn.extend({
    outerHTML : function()
    {
        var temporary = $("<div/>").append($(this).clone());
        var html = temporary.html();

        temporary.remove();
        return html;
    },

    enableDragAndDrop : function()
    {
        return this.each(
            function()
            {       
                if (typeof this.style.WebkitUserDrag != 'undefined')
                {
                    this.style.WebkitUserDrag = 'element';
                }

                if (typeof this.draggable != 'undefined')
                {
                    this.draggable = true;   
                }

                if (typeof this.dragDrop == 'function')
                {
                    this.dragDrop();
                }
            }
        );
    }
});

$(document).ready(
    function()
    {
        $(document).on(
            'mousedown.finder',
            'div.finderDirectory, div.finderFile',
            function(event)
            {
                $(this).enableDragAndDrop();
                
                $('div.finderIconSelected')
                    .removeClass('finderIconSelected');
    
                $('span.finderDirectoryNameSelected')
                    .removeClass('finderDirectoryNameSelected');
    
                $(this).find('div.finderIcon')
                    .addClass('finderIconSelected');
    
                $(this).find('div.finderDirectoryName span')
                    .addClass('finderDirectoryNameSelected');
            }
        );

        $('div.finderDirectory, div.finderFile')
            .on(
                'dragstart.finder',
                function(event)
                {
                    event.stopPropagation();

                    var html = $(this).outerHTML();
                    
                    var dataTransfer = event.originalEvent.dataTransfer;

                    dataTransfer.effectAllowed = 'copyMove';

                    try
                    {
                        dataTransfer.setData('text/html', html);
                        dataTransfer.setData('text/plain', html);
                    }
                    catch (error)
                    {
                        dataTransfer.setData('Text', html);
                    }
                }
            )
            .on(
                'dragend.finder',
                function(event)
                {
                    if ($('div.finderDirectoryDrop').length)
                    {
                        $(this).removeClass('finderDirectoryDrop');
                        $(this).remove();
                    }
                }
            )
            .on(
                'dragenter.finder',
                function(event)
                {
                    event.preventDefault();
                    event.stopPropagation();
                }
            )
            .on(
                'dragover.finder',
                function(event)
                {
                    event.preventDefault();
                    event.stopPropagation();
                    
                    if ($(this).is('div.finderDirectory'))
                    {
                        $(this).addClass('finderDirectoryDrop');
                    }
                }
            )
            .on(
                'dragleave.finder',
                function(event)
                {
                    event.preventDefault();
                    event.stopPropagation();

                    $(this).removeClass('finderDirectoryDrop');
                }
            )
            .on(
                'drop.finder',
                function(event)
                {
                    event.preventDefault();
                    event.stopPropagation();
                    
                    var dataTransfer = event.originalEvent.dataTransfer;

                    try
                    {
                        var html = dataTransfer.getData('text/html');
                    }
                    catch (error)
                    {
                        var html = dataTransfer.getData('Text');
                    }

                    html = $(html);
                    var drop = $(this);
                    
                    var dontAcceptTheDrop = (
                        drop.data('path') == html.data('path') ||
                        drop.is('div.finderFile')
                    );

                    if (dontAcceptTheDrop)
                    {
                        // Prevent file from being dragged onto itself
                        drop.removeClass('finderDirectoryDrop');
                        return;
                    }

                    if (html.hasClass('finderDirectory finderFile'))
                    {
                        // Do something with the dropped file
                        console.log(html);
                    }
                }
            );
    }
);
