$(document).ready(
    function()
    {
        var beatles = [
            'John Lennon',
            'Paul McCartney',
            'George Harrison',
            'Ringo Starr'
        ];
        
        var ul = $('ul#beatles');
        
        // each() called via jQuery
        $(beatles).each(
            function()
            {
                ul.append('<li>' + this + '</li>');
            }
        );  

        var albums = [
            'Please Please Me',
            'With the Beatles',
            'A Hard Day\'s Night',
            'Beatles for Sale',
            'Help!',
            'Rubber Soul',
            'Revolver',
            'Sgt. Pepper\'s Lonely Hearts Club Band',
            'Magical Mystery Tour',
            'The Beatles',
            'Yellow Submarine',
            'Abbey Road',
            'Let It Be'
        ];
        
        ul = $('ul#beatlesAlbums');

        // each() called directly.
        $.each(
            albums,
            function()
            {
                ul.append('<li>' + this + '</li>');
            }
        );
    }
);
