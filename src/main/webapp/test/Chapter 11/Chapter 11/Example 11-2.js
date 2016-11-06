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

dragAndDrop = {

    path : null,
    
    files : [],
    
    openProgressDialogue : function(files, path)
    {
        this.path = path;

        $('div#finderDragAndDropDialogue')
            .fadeIn('fast');

        this.files = [];
    
        $(files).each(
            function(key, file)
            {
                dragAndDrop.addFileToQueue(file); 
            }         
        );

        if (this.files.length)
        {
            this.upload();
        }
        else
        {
            this.closeProgressDialogue();
        }
    },
    
    closeProgressDialogue : function()
    {
        // Uncomment this section to automatically close the 
        // dialogue after upload
        
        //$('div#finderDragAndDropDialogue')
        //    .fadeOut('fast');
        
        //$('div#finderDragAndDropDialogue tbody tr')
        //    .not('tr.finderDragAndDropDialogueTemplate')
        //    .remove();
    },

    addFileToQueue : function(file)
    {
        if (!file.name && file.fileName)
        {
            file.name = file.fileName;
        }
        
        if (!file.size && file.fileSize)
        {
            file.size = file.fileSize;
        }

        this.files.push(file);

        var tr = $('tr.finderDragAndDropDialogueTemplate').clone(true);

        tr.removeClass('finderDragAndDropDialogueTemplate');

        // Preview image uploads by showing a thumbnail of the image
        if (file.type.match(/^image\/.*$/) && FileReader)
        {
            var img = document.createElement('img');
            img.file = file;

            tr.find('td.finderDragAndDropDialogueFileIcon')
              .html(img);

            var reader = new FileReader();

            reader.onload = function(event)
            {
                img.src = event.target.result;
            };

            reader.readAsDataURL(file);
        }

        tr.find('td.finderDragAndDropDialogueFile')
          .text(file.name);
        
        tr.find('td.finderDragAndDropDialogueFileSize')
          .text(this.getFileSize(file.size));

        tr.attr('title', file.name);

        $('div#finderDragAndDropDialogueFiles tbody').append(tr);
    },
    
    http : null,

    upload : function()
    {
        this.http = new XMLHttpRequest();
    
        if (this.http.upload && this.http.upload.addEventListener)
        {
            this.http.upload.addEventListener(
                'progress',
                function(event)
                {
                    if (event.lengthComputable)
                    {
                        $('div#finderDragAndDropDialogueProgressMeter')
                            .show();
                        
                        $('div#finderDragAndDropDialogueProgressMeter div')
                            .show();
                        
                        var progress = Math.round(
                            (event.loaded * 100) / event.total
                        );

                        $('div#finderDragAndDropDialogueProgress span')
                            .text(progress);

                        $('div#finderDragAndDropDialogueProgressMeter div')
                            .css('width', progress + '%');
                    }
                },
                false
            );

            this.http.upload.addEventListener(
                'load',
                function(event)
                {
                    $('div#finderDragAndDropDialogueProgress span')
                        .text(100);

                    $('div#finderDragAndDropDialogueProgressMeter div')
                        .css('width', '100%');                    
                }
            );
        }

        this.http.addEventListener(
            'load',
            function(event)
            {
                // This event is fired when the upload completes and 
                // the server-side script /file/upload.json sends back 
                // a response.
                dragAndDrop.closeProgressDialogue();

                // If the server-side script sends back a JSON response, 
                // this is how you'd access it and do something with it.
                var json = $.parseJSON(dragAndDrop.http.responseText);
            },
            false
        );

        if (typeof FormData !== 'undefined')
        {
            var form = new FormData();

            // The form object invoked here is a built-in object, provided 
            // by the browser, it allows you to specify POST variables
            // in the request for the file upload.
            form.append('path', this.path);

            $(this.files).each(
                function(key, file)
                {
                    form.append('file[]', file);
                    form.append('name[]', file.name);
                    form.append('replaceFile[]', 1);
                }
            );

            // This sends a POST request to the server at the path 
            // /file/upload.php. This is the server-side file that will 
            // handle the file upload.
            this.http.open('POST', 'file/upload.json');
            this.http.send(form);
        }
        else
        {
            console.log(
                'This browser does not support HTML 5 ' + 
                'drag and drop file uploads.'
            );

            this.closeProgressDialogue();
        }
    },

    getFileSize : function(bytes)
    {
        switch (true)
        {
            case (bytes < Math.pow(2,10)):
            {
                return bytes + ' Bytes';
            }
            case (bytes >= Math.pow(2,10) && bytes < Math.pow(2,20)):
            {
                return Math.round(
                    bytes / Math.pow(2,10)
                ) +' KB';
            }
            case (bytes >= Math.pow(2,20) && bytes < Math.pow(2,30)):
            {
                return Math.round(
                    (bytes / Math.pow(2,20)) * 10
                ) / 10 + ' MB';
            }
            case (bytes > Math.pow(2,30)):
            {
                return Math.round(
                    (bytes / Math.pow(2,30)) * 100
                ) / 100 + ' GB';
            }
        }
    },

    applyEvents : function()
    {
        var context = null;

        if (arguments[0])
        {
            context = arguments[0];
        }
        else
        {
            context = $('div.finderDirectory, div.finderFile');
        }

        context
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
                    var drop = $(this);

                    if (drop.hasClass('finderDirectory'))
                    {
                        if (dataTransfer.files && dataTransfer.files.length)
                        {
                            // Files dropped from outside the browser
                            dragAndDrop.openProgressDialogue(
                                dataTransfer.files,
                                node.data('path')
                            );
                        }
                        else 
                        {
                            try
                            {
                                var html = dataTransfer.getData('text/html');
                            }
                            catch (error)
                            {
                                var html = dataTransfer.getData('Text');
                            }

                            html = $(html);

                            var dontAcceptTheDrop = (
                                html.data('path') == drop.data('path') ||
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
                    }
                }
            );
    }
};

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

        dragAndDrop.applyEvents();

        $('div#finderFiles')
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
                    
                    $(this).addClass('finderDirectoryDrop');
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
                    var drop = $(this);

                    if (dataTransfer.files && dataTransfer.files.length)
                    {
                        dragAndDrop.openProgressDialogue(
                            dataTransfer.files,
                            drop.data('path')
                        );
                    }
                    else
                    {
                        try
                        {
                            var html = dataTransfer.getData('text/html');
                        }
                        catch (error)
                        {
                            var html = dataTransfer.getData('Text');
                        }

                        html = $(html);

                        if (drop.data('path') == html.data('path'))
                        {
                            // Prevent file from being dragged onto itself
                            drop.removeClass('finderDirectoryDrop');
                            return;
                        }
                        
                        if (!html.hasClass('finderDirectory finderFile'))
                        {
                            return;
                        }

                        var fileExists = false;
                        
                        $('div.finderFile, div.finderDirectory').each(
                            function()
                            {
                                if ($(this).data('path') == html.data('path'))
                                {
                                    fileExists = true;
                                    return false;
                                }
                            }
                        );

                        if (!fileExists)
                        {
                            dragAndDrop.applyEvents(html);
                            drop.append(html);
                        }
                    }
                }
            );            
    }
);
