$(document).ready(
    function()
    {
        var beatles = {
            john : 'John Lennon',
            paul : 'Paul McCartney',
            george : 'George Harrison',
            ringo : 'Ringo Starr'
        };

        var ul = $('ul#beatles');
        
        // each() called via jQuery
        $(beatles).each(
            function()
            {
                ul.append('<li>' + this + '</li>');
            }
        );  

        var albums = {
            1 : 'Please Please Me',
            2 : 'With the Beatles',
            3 : 'A Hard Day\'s Night',
            4 : 'Beatles for Sale',
            5 : 'Help!',
            6 : 'Rubber Soul',
            7 : 'Revolver',
            8 : 'Sgt. Pepper\'s Lonely Hearts Club Band',
            9 : 'Magical Mystery Tour',
            10 : 'The Beatles',
            11 : 'Yellow Submarine',
            12 : 'Abbey Road',
            13 : 'Let It Be'
        };

        ul = $('ul#beatlesAlbums');
        
        if (albums instanceof Array)
        {
            console.log("Albums is an array.");
        }
        else
        {
            console.log("Albums is a plain object.");
        }

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
