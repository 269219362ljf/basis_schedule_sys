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
                
                seletedFile = $(this);
                seletedFile.addClass('finderCategoryFileSelected');
            }
        );

        $('ul#finderCategoryFiles')
            .sortable();
    }
);
