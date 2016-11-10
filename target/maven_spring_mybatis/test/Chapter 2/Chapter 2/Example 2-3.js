$(document).ready(
    function()
    {
        var today = $('td.calendarToday');
        
        var setUpThisWeek = function()
        {
            //先去除class，后面再加上
            $('table.calendarMonth td').removeClass(
                'calendarYesterday ' +
                'calendarTomorrow ' +
                'calendarEarlierThisWeek ' + 
                'calendarLaterThisWeek ' +
                'calendarThisWeek'
            );

            var yesterday = today.prev('td');
            
            // If today occurs at the beginning of the week, look in the
            // preceding row for yesterday. 
            if (!yesterday.length)
            {
                var lastWeek = today.parent('tr').prev('tr');
                
                if (lastWeek.length)
                {
                    yesterday = lastWeek.children('td').eq(6);
                }
            }

            // If today occurs in the first cell of the first row of the 
            // calendar, yesterday won't be present in this month.
            if (yesterday.length)
            {
                yesterday.addClass('calendarYesterday');
            }
            
            var tomorrow = today.next('td');
            
            // If today occurs at the end of the week, look in the 
            // proceeding row for tommorrow.
            if (!tomorrow.length)
            {
                var nextWeek = today.parent('tr').next('tr');
            
                if (nextWeek.length)
                {
                    tomorrow = nextWeek.children('td').eq(0);
                }
            }
    
            // If today occurs in the last cell of the last row of 
            // the calendar, tomorrow won't be present in this month.
            if (tomorrow.length)
            {
                tomorrow.addClass('calendarTommorow');
            }
    
            var laterThisWeek = today.nextAll('td'); 
            
            if (laterThisWeek.length)
            {
                laterThisWeek.addClass('calendarLaterThisWeek');
            }

            var earlierThisWeek = today.prevAll('td');
    
            if (earlierThisWeek.length)
            {
                earlierThisWeek.addClass('calendarEarlierThisWeek');
            }

            today.siblings('td')
                 .addClass('calendarThisWeek');            
        };
            
        var selectedDay = null;
        //限定为本月的td，并为本月的td增加click（）
        $('table.calendarMonth td')
            .not('td.calendarLastMonth, td.calendarNextMonth')
            .click(
                function()
                {
                    //删除之前的选择项
                    if (selectedDay && selectedDay.length)
                    {
                        selectedDay
                            .removeClass('calendarDaySelected')
                            .parent('tr')
                                .removeClass('calendarWeekSelected');
                    }
                    //获取当前选择的td
                    var day = $(this);
                    //设置当前td
                    selectedDay = day;
                    //更改selectedday以及所在行的样式，class样式在css中定义
                    selectedDay
                        .addClass('calendarDaySelected')
                        .parent('tr')
                            .addClass('calendarWeekSelected');

                    day.parents('table.calendarMonth')
                       .find('span.calendarDay')
                       .text(day.text() + ', ');
                }
            )
            .dblclick(//双击事件，更改今天为选择日
                function()
                {
                    today.removeClass('calendarToday');
                    today = $(this);
                    today.addClass('calendarToday');
                    
                    setUpThisWeek();
                }
            );
        
        setUpThisWeek();
    }
);