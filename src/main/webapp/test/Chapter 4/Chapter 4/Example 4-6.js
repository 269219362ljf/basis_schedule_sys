$(document).ready(
    function()
    {        
        $('button#documentAppend').click(
            function(event)
            {
                event.preventDefault();
                
                if (!$('tr#johnLennonDoubleFantasy').length)
                {
                    $('table tbody').append(
                        "<tr id='johnLennonDoubleFantasy'>\n" + 
                            "<td>Double Fantasy</td>\n" +
                            "<td>1980</td>\n" + 
                        "</tr>\n"
                    );
                }
            }
        );

        $('button#documentPrepend').click(
            function(event)
            {
                event.preventDefault();

                if (!$('tr#johnLennonPlasticOnoBand').length)
                {
                    $('table tbody').prepend(
                        "<tr id='johnLennonPlasticOnoBand'>\n" + 
                            "<td>John Lennon/Plastic Ono Band</td>\n" +
                            "<td>1970</td>\n" +
                        "</tr>\n"
                    );
                }
            }
        );
    }
);
