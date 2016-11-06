$(document).ready(
    function()
    {
        var name = 'The Beatles';
        
        var madeArray = $.makeArray(name);
        
        console.log('Transforming a string.');
        console.log('Type: ' + typeof(madeArray));
        console.log('Is Array? ' + (madeArray instanceof Array? 'yes' : 'no'));
        console.log(madeArray);
        
        var madeArray = {
            band1 : "The Beatles",
            band2 : "Electric Light Orchestra",
            band3 : "The Moody Blues",
            band4 : "Radiohead"
        };

        madeArray = $.makeArray(madeArray);
        
        console.log('Transforming an object.');
        console.log('Type: ' + typeof(madeArray));
        console.log('Is Array? ' + (madeArray instanceof Array? 'yes' : 'no'));
        console.log(madeArray);

        var madeArray = 1;
        
        madeArray = $.makeArray(madeArray);
        
        console.log('Transforming a number.');
        console.log('Type: ' + typeof(madeArray));
        console.log('Is Array? ' + (madeArray instanceof Array? 'yes' : 'no'));
        console.log(madeArray);
    }
);
