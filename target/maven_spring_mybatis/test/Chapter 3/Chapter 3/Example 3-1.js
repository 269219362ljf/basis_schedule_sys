$(document).ready(
    function()
    {
        $('div.finderDirectory, div.finderFile').click(
            function(event)
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
        );
        //filter(':first')过滤获得集合中第一个对象，此处为获得第一个对象，并执行对象的click方法
        $('div.finderDirectory, div.finderFile')
        	.filter(':first')
        	.click();
    }
);
