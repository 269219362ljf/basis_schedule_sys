$(document).ready(
    function()
    {
        $('a#examplePumpkin').click(
            function(event)
            {
                event.preventDefault();

                window.open(
                    'pumpkin.jpg',
                    'Pumpkin',
                    'scrollbars=no,width=300,height=280,resizable=yes'
                );
            }
        );
        
    }
);