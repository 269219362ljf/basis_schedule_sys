$(document).ready(
    function()
    {
        $('ul#rubberSoul li').each(
            function(key)
            {
                if (key & 1)
                {
                    $(this).addClass('rubberSoulEven');
                }
            }
        );
        
    }
);
