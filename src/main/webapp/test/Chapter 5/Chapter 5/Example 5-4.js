$(document).ready(
    function()
    {
        $('ul#rubberSoul li')
            .filter('li.George')
                .addClass('rubberSoulGeorge')
                .end()
            .filter('li.John')
                .addClass('rubberSoulJohn')
                .end()
            .filter('li.Paul')
                .addClass('rubberSoulPaul')
                .end();
    }
);
