$(document).ready(
    function()
    {
        $('input#exampleDate').datepicker({
            changeMonth : true,
            changeYear : true,
            minDate : new Date(1900, 1, 1),
            maxDate : new Date(2020, 12, 31),
            yearRange : "1900:2020"
        });
        
        $('div.exampleDate img').click(
            function()
            {
                $(this)
                    .prev('input')
                    .focus();
            }
        );
    }
);
