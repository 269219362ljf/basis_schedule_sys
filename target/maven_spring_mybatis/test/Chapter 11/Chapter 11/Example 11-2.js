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
        //设置上传路径
        this.path = path;
        //打开上传对话框
        $('div#finderDragAndDropDialogue')
            .fadeIn('fast');

        this.files = [];
    
        $(files).each(
            function(key, file)
            {
                //增加到上传文件队列
                dragAndDrop.addFileToQueue(file); 
            }         
        );

        if (this.files.length)
        {
            //files数组大于0，调用dragAndDrop的upload函数上传文件
            this.upload();
        }
        else
        {
            //不存在上传文件，关闭对话框
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
        //files数组增加文件
        this.files.push(file);
        //复制模板结点
        var tr = $('tr.finderDragAndDropDialogueTemplate').clone(true);

        tr.removeClass('finderDragAndDropDialogueTemplate');

        // Preview image uploads by showing a thumbnail of the image
        //如果是图像文件，则在左边列中提供预览
        if (file.type.match(/^image\/.*$/) && FileReader)
        {
            var img = document.createElement('img');
            img.file = file;

            tr.find('td.finderDragAndDropDialogueFileIcon')
              .html(img);

            //FileReader通过样式表将图像缩小为缩略图
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
        //检查this.http（XMLHttpRequest）是否存在upload对象和是否有addEventListener方法
        if (this.http.upload && this.http.upload.addEventListener)
        {
            //设置progress事件侦听器
            this.http.upload.addEventListener(
                'progress',
                function(event)
                {
                    //是否存在需要报告的进度
                    if (event.lengthComputable)
                    {
                        $('div#finderDragAndDropDialogueProgressMeter')
                            .show();
                        
                        $('div#finderDragAndDropDialogueProgressMeter div')
                            .show();

                        //计算上传进度
                        var progress = Math.round(
                            (event.loaded * 100) / event.total
                        );
                        //显示进度数
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
                    //覆盖到100%的情况
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
                //加载完成触发
                dragAndDrop.closeProgressDialogue();

                // If the server-side script sends back a JSON response, 
                // this is how you'd access it and do something with it.
                //如果服务器发来json响应，可以通过http.responseText读取解析
                var json = $.parseJSON(dragAndDrop.http.responseText);
            },
            false
        );
        //看浏览器是否支持新版本的拖放上传,支持则使用新版本
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
            //不支持则关闭对话框
            console.log(
                'This browser does not support HTML 5 ' + 
                'drag and drop file uploads.'
            );

            this.closeProgressDialogue();
        }
    },

    getFileSize : function(bytes)
    {
        //将字节数转换为KB,MB,GB等表示
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
        //应用事件
        var context = null;
        //若为外来文件或文件夹对象，则在该对象上应用事件
        //否则在页面文件或文件夹对象应用事件（相当于初始化）
        //事件类似于11-1，但修改了drop
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
                            //外来文件放入页面，启动对话框上传文件
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
