$(document).ready(
    function()
    {
        var rubberSoul = [
            'Drive My Car',
            'Norwegian Wood (This Bird Has Flown)',
            'You Won\'t See Me',
            'Nowhere Man',
            'Think for Yourself',
            'The Word',
            'Michelle',
            'What Goes On',
            'Girl',
            'I\'m Looking Through You',
            'In My Life',
            'Wait',
            'If I Needed Someone',
            'Run for Your Life'
        ];
        
        var revolver = [
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
        
        var songs = $.merge(rubberSoul, revolver);
        
        console.log('Songs :', songs);     
    }
);