$(document).on(
    'DOMContentLoaded',
    function()
    {
        $('div#finderFiles').on(
            'click',
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
        //trigger()是触发一个动作
        $('div#finderFiles div.finderNode:first')
            .trigger('click');
        
        var addedAdditionalFiles = false;

        $('body').dblclick(
            function()
            {
                if (addedAdditionalFiles)
                {
                    return;
                }
                //在id为finderAdditionalFiles旗下查找属性类为finderFile的结点，并每一个执行functon
                //此处的>为在id旗下的意思
                $('div#finderAdditionalFiles > div.finderFile').each(
                     function()
                     {
                         $('div#finderFiles').append(
                             $(this).clone()
                         );
                     }
                );
                
                addedAdditionalFiles = true;
            }
        );
    }
);
