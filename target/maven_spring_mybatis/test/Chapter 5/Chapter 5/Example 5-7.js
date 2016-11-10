$(document).ready(
    function()
    {
        var mappedSongs = $('ul#rubberSoul li').map(
            function(key)
            {
               if ($(this).hasClass('John'))
               {
                   return $(this).text() + ' <i>John Lennon</i>';
               }
               
               if ($(this).hasClass('Paul'))
               {
                   return $(this).text() + ' <i>Paul McCartney</i>';
               }
               
               if ($(this).hasClass('George'))
               {
                   return $(this).text() + ' <i>George Harrison</i>';
               }
            }
        );
        
        $('ul#rubberSoul').hide();
        
        var ul = $('ul#rubberSoulMapped');
        ul.show();
        
        $(mappedSongs).each(
            function()
            {
                ul.append('<li>' + this + '</li>');
            }
        );
    }
);
