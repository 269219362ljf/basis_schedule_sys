$(document).ready(
    function()
    {
        $('div.finderDirectory')
            .mousedown(
                function()
                {
                    $('div.finderIconSelected')
                        .removeClass('finderIconSelected');

                    $('span.finderDirectoryNameSelected')
                        .removeClass('finderDirectoryNameSelected');
    
                    $(this).find('div.finderIcon')
                        .addClass('finderIconSelected');
    
                    $(this).find('div.finderDirectoryName span')
                        .addClass('finderDirectoryNameSelected');
                }
            )
            .draggable({
                //clone为克隆体，opacity是透明度
                helper : 'clone',
                opacity : 0.5
            })
            .droppable({
                accept: 'div.finderDirectory',
                hoverClass: 'finderDirectoryDrop',
                drop: function(event, ui)
                {
                    var path = ui.draggable.data('path');
                    // Do something with the path
                    // For example, make an AJAX call to the server
                    // where the logic for actually moving the file or folder
                    // to the new folder would take place
    
                    // Remove the element that was dropped.
                    ui.draggable.remove();
                }
            });
    }
);


