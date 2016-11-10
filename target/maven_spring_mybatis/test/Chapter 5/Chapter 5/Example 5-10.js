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
        
        console.log(
            'Love You To: ' + (
                $.inArray('Love You To', songs)
            )
        );
        
        console.log(
            'Strawberry Fields Forever: ' + (
                $.inArray('Strawberry Fields Forever', songs)
            )
        );
    }
);
