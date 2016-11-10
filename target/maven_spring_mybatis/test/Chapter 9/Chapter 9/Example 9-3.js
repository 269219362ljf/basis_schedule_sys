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

console.log($.fn);
