$.fn.extend({

    selectFile : function()
    {
        this.addClass('finderSelected');

        this.each(
            function()
            {
                if ($.inArray($(this), finder.selectedFiles) == -1)
                {
                    finder.selectedFiles.push($(this));
                }
            }
        );

        return this;
    },

    unselectFile : function()
    {
        this.removeClass('finderSelected');
        var files = this;

        if (finder.selectedFiles instanceof Array && finder.selectedFiles.length)
        {
            finder.selectedFiles = $.grep(
                finder.selectedFiles,
                function(file, index)
                {
                    return $.inArray(file, files) == -1;
                }
            );
        }

        return this;
    }
});

var finder = {

    selectingFiles : false,

    selectedFiles : [],

    unselectSelected : function()
    {
        if (this.selectedFiles instanceof Array && this.selectedFiles.length)
        {
            $(this.selectedFiles).each(
                function()
                {
                    $(this).unselectFile();
                }
            );
        }

        this.selectedFiles = [];
    },

    ready : function()
    {
        $('div.finderDirectory, div.finderFile')
            .mousedown(
                function()
                {
                    if (!finder.selectingFiles)
                    {
                        finder.unselectSelected();
                        $(this).selectFile();
                    }
                }
            )
            .draggable({
                helper : 'clone',
                opacity : 0.5
            });

        $('div.finderDirectory').droppable({
            accept : 'div.finderDirectory, div.finderFile',
            hoverClass : 'finderDirectoryDrop',
            drop : function(event, ui)
            {
                var path = ui.draggable.data('path');
                ui.draggable.remove();
            }
        });

        $('div#finderFiles').selectable({
            appendTo : 'div#finderFiles',
            filter : 'div.finderDirectory, div.finderFile',
            start : function(event, ui)
            {                
                finder.selectingFiles = true;
                finder.unselectSelected();
            },
            stop : function(event, ui)
            {
                finder.selectingFiles = false;
            },
            selecting : function(event, ui)
            {
                $(ui.selecting).selectFile();
            },
            unselecting : function(event, ui)
            {
                $(ui.unselecting).unselectFile();
            }
        });
    }
};

$(document).ready(
    function()
    {
        finder.ready();
    }
);
