$(document).ready(
    function()
    {        
        $('input#mitchHedbergQuoteReveal1').click(
            function(event)
            {
                event.preventDefault();
                
                $(this).replaceWith(
                    "<p>\n" +
                    "    I would imagine that if you could understand \n" + 
                    "    Morse code, a tap dancer would drive you crazy.\n" +
                    "</p>\n"
                );
            }
        );

        $('input#mitchHedbergQuoteReveal2').click(
            function(event)
            {
                event.preventDefault();
                
                $(this).replaceWith(
                    "<p>\n" +
                    "  I&rsquo;d like to get four people who do cart wheels \n" +
                    "  very good, and make a cart.\n" +
                    "</p>\n"
                );
            }
        );
        
        $('input#mitchHedbergQuoteReveal3').click(
            function(event)
            {
                $('p#mitchHedbergQuote3').replaceAll(this);
            }
        );
        
        $('input#mitchHedbergQuoteReveal4').click(
            function(event)
            {
                $('p#mitchHedbergQuote4').replaceAll(this);
            }
        );
    }
);
