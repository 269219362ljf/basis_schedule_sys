$(document).ready(
    function()
    {
        $('li.finderCategoryFile').mousedown(
            function()
            {
                $('li.finderCategoryFile')
                    .not(this)
                    .removeClass('finderCategoryFileSelected');

                $(this).addClass('finderCategoryFileSelected');
            }
        );

        $('ul#finderCategoryFiles').sortable();
    }
);
