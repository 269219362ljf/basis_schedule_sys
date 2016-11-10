$(document).ready(
    function()
    {
        $('ul#rubberSoul li')
            .filter(
                function()
                {
                    return $(this).hasClass('John') || $(this).hasClass('Paul');
                }
            )
            .addClass('rubberSoulJohnAndPaul');
    }
);
