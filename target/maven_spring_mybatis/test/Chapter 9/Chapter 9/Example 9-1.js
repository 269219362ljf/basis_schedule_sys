//$.fn.extend()自身为一个固定的function，用于jQuery对象扩展，对jQuery对象添加方法
$.fn.extend({

    select : function()
    {
        // In a jQuery plugin; 'this' is already a jQuery ready object
        // Performing an operation like addClass() works on one 
        // or more items, depending on the selection.
        return this.addClass('movieSelected');
    },

    unselect : function()
    {
        return this.removeClass('movieSelected');
    }
});

var movies = {

    ready : function()
    {
        $('a#movieSelectAll').click(
            function(event)
            {
                event.preventDefault();

                $('ul.movieList li').select();
            }
        );

        $(document).on(
        	'click.movieList',
        	'ul.movieList li',
            function()
            {
                if ($(this).hasClass('movieSelected'))
                {
                    $(this).unselect();   
                }
                else
                {
                    $(this).select();
                }
            }
        );
    }
};

$(document).ready(
    function()
    {
        movies.ready();
    }
);