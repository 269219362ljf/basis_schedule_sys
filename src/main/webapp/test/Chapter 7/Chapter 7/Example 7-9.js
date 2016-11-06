$(document).ready(
    function()
    {
        $('select#addressCountry').click(
            function()
            {
                $.getJSON(
                    'Example 7-9/' + this.value + '.json',
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
        
        $('select#addressCountry').click();

        $('input#addressButton').click(
            function(event)
            {
                event.preventDefault();

                var data = {
                    country : $('select#addressCountry').val(),
                    street : $('textarea#addressStreet').val(),
                    city : $('input#addressCity').val(),
                    state : $('select#addressState').val(),
                    postalCode : $('input#addressPostalCode').val()
                };

                $.ajax({
                    url : 'Example%207-9/ADD.json',
                    contentType : "application/json; charset=utf-8",
                    type : 'ADD',
                    dataType : 'json',
                    data : JSON.stringify(data),
                    success : function(json, status, request)
                    {
                        if (parseInt(json) > 0)
                        {
                            alert('Data added successfully.');
                        }
                    },
                    error : function(request, status)
                    {
                        
                    }
                });
            }
        );
    }
);