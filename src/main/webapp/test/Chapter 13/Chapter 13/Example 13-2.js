$(document).ready(
    function()
    {
        var selectedFile;

        $('li.finderCategoryFile').mousedown(
            function()
            {
                if (selectedFile && selectedFile.length)
                {
                    selectedFile.removeClass('finderCategoryFileSelected');
                }

                selectedFile = $(this);
                selectedFile.addClass('finderCategoryFileSelected');
            }
        );

        $('ul#finderCategoryFiles').sortable({
            connectWith : 'ul#finderOtherCategoryFiles',//关联列（单向）
            placeholder : 'finderCategoryFilePlaceholder',//预设位置样式
            opacity : 0.8,
            cursor : 'move'
        });

        $('ul#finderOtherCategoryFiles').sortable({
            connectWith : 'ul#finderCategoryFiles',
            placeholder : 'finderCategoryFilePlaceholder',
            opacity : 0.8,
            cursor : 'move'
        });
    }
);
