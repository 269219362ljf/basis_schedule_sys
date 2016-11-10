$(document).ready(
    function()
    {
        $('body').append(
            $('<div/>')
                .addClass('selected')
                .attr({
                    id : 'body',
                    title : 'Welcome to jQuery'
                })
                .text(
                    "Hello, World!"
                )
        );
    }
);