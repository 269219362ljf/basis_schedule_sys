$(document).ready(
    function()
    {
        $('video,audio').mediaelementplayer({
			clickToPlayPause: true,
			features: [ 'playpause', 'current', 'progress', 'volume', 'speed', 'fullscreen' ]
		});
    }
);
