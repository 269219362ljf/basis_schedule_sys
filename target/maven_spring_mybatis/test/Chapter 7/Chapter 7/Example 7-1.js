$(document).ready(
    function()
    {
        $('select#addressCountry').click(
            function()
            {
                $.get(
                    'Example 7-1/' + this.value + '.xml',
                    function(xml)
                    {
                        // Make the XML query-able with jQuery
                        xml = $(xml);

                        // Get the ISO2 value, that's used for the 
                        // file name of the flag.
                        var iso2 = xml.find('iso2').text();

                        // Swap out the flag image
                        $('div#addressCountryWrapper img').attr({
                            alt : xml.find('name'),
                            src : 'flags/' + iso2.toLowerCase() + '.png'
                        });

                        // Remove all of the options
                        $('select#addressState').empty();

                        // Set the states... 
                        xml.find('state').each(
                            function()
                            {
                                $('select#addressState').append(
                                    $('<option/>')
                                        .attr('value', $(this).attr('id'))
                                        .text($(this).text())
                                );
                            }
                        );

                        // Change the label
                        $('label[for="addressState"]').text(
                            xml.find('label').text() + ':'
                        );
                    },
                    'xml'
                );
            }
        );
    }
);
