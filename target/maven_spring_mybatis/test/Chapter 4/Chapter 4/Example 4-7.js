$(document).ready(
    function()
    {        
        $('p').before(
            '<h4>Quote</h4>'
        );
        
        $('p').after(
            "<p class='quoteAttribution'>\n" + 
            " - Groucho Marx\n" + 
            "</p>\n"
        );        
    }
);
