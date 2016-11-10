document.addEventListener(
    'DOMContentLoaded',
    function()
    {
        var a = document.getElementById('movieSelectAll');

        for (var property in a)
        {
            console.log(property);
        }
    }
);
