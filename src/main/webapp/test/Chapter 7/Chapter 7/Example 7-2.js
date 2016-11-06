$(document).ready(
    function()
    {
        $('select#addressCountry').click(
            function()
            {
                $.getJSON(
                    'Example 7-2/' + this.value + '.js',
                    function(json)
                    {
                        // Swap out the flag image
                        $('div#addressCountryWrapper img').attr({
                            alt : json.name,
                            src : 'flags/' + json.iso2.toLowerCase() + '.png'
                        });

                        // Remove all of the options
                        $('select#addressState').empty();

                        // Set the states... 
                        $.each(
                            json.states,
                            function(id, state)
                            {
                                $('select#addressState').append(
                                    $('<option/>')
                                        .attr('value', id)
                                        .text(state)
                                );
                            }
                        );

                        // Change the label
                        $('label[for="addressState"]').text(
                            json.label + ':'
                        );
                    }
                );
            }
        );
    }
);