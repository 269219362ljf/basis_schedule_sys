$(document).ready(
    function()
    {
        var songs = [
             'Taxman',
             'Eleanor Rigby',
             'I\'m Only Sleeping',
             'Love You To',
             'Here, There and Everywhere',
             'Yellow Submarine',
             'She Said, She Said',
             'Good Day Sunshine',
             'And Your Bird Can Sing',
             'For No One',
             'Doctor Robert',
             'I Want to Tell You',
             'Got to Get You into My Life',
             'Tomorrow Never Knows'         
        ];
        
        var mappedSongs = $(songs).map(
            function(key)      
            {
                var track = key + 1;
                
                return (track < 10? '0' + track : track) + ' ' + this; 
            }
        );
        
        $(mappedSongs).each(
            function()
            {
                $('ul#revolver').append('<li>' + this + '</li>');
            }
        );        
    }
);
