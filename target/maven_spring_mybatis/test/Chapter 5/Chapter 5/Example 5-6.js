$(document).ready(
    function()
    {
        var songs = [];
        
        $('ul#rubberSoul li').each(
            function()
            {
                songs.push(
                    $(this).text()
                );
            }
        );

        var filteredSongs = $.grep(
            songs,
            function(value, key)
            {
                return value.indexOf('You') != -1;
            }
        );
        
        var ul = $('ul#rubberSoulFiltered');
        
        $('ul#rubberSoul').hide();
        ul.show();
        
        $(filteredSongs).each(
            function()
            {
                ul.append('<li>' + this + '</li>');
            }
        );
    }
);
