$(document).on(
    'DOMContentLoaded',
    function()
    {
        //增加自定义事件处理器appendFile.finder，处理appendFile
        $('div#finderFiles')
            .on(
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
            )
            .on(
                'appendFile.finder',
                'div.finderDirectory, div.finderFile',
                function(event, file)
                {
                    console.log(file.path);
                    console.log($(this));
                }
            );

        $('div#finderFiles div.finderNode:first')
            .trigger('click.finder');
        
        var addedAdditionalFiles = false;

        $('body').dblclick(
            function()
            {
                if (addedAdditionalFiles)
                {
                    return;
                }

                $('div#finderAdditionalFiles > div.finderFile').each(
                    function()
                    {
                        var file = $(this).clone();
                         
                        $('div#finderFiles').append(file);
                        //触发appendFile.finder的事件
                        //这里的.finder是不必要的，但.finder为appendFile指定了命名空间，所以
                        //appendFile.finder可以指定为特定命名空间中的特定事件，不会与其他同名
                        //事件冲突
                        file.trigger(
                            'appendFile.finder', {
                                path : file.data('path')
                            }
                        );
                    }
                );
                
                addedAdditionalFiles = true;
            }
        );
    }
);
