$(document).ready(
    function()
    {
        $.getScript(
            '../jQueryUI.js',
            function()
            {
                $('table.calendarMonth td:not(td.calendarLastMonth, td.calendarNextMonth)').click(
                    function()
                    {
                        if ($(this).css('background-color') != 'rgb(200, 200, 200)')
                        {
                            $(this).animate({
                                    'background-color' : 'rgb(200, 200, 200)'
                                }, 
                                1000
                            );
                        }
                        else
                        {
                            //动画变化，渐变白
                            $(this).animate({
                                    'background-color' : 'rgb(255, 255, 255)'
                                }, 
                                1000
                            );
                        }
                    }
                );
            }
        );
    }
);