$(document).ready(
    function()
    {
        var getCheckbox = function()
        {
            var input = $('input[name="documentAttributeMarx"]:checked');

            if (input && input.length)
            {
                return input;
            }

            return $('input[name="documentAttributeMarx"]:first')
                .attr('checked', true);
        };
        
        $('button#documentSetAttribute').click(
            function(event)
            {
                event.preventDefault();
                
                var input = getCheckbox();

                input
                    .attr('disabled', true)
                    .parent('li')
                    .addClass('disabled');
            }
        );
        
        $('button#documentRetrieveAttribute').click(
            function(event)
            {
                event.preventDefault();
                
                var input = getCheckbox();

                alert('Disabled: ' + input.attr('disabled'));
            }
        );
        
        $('button#documentRemoveAttribute').click(
            function(event)
            {
                event.preventDefault();
                
                var input = getCheckbox();

                input
                    .removeAttr('disabled')
                    .parent('li')
                    .removeClass('disabled');
            }
        );
    }
);
