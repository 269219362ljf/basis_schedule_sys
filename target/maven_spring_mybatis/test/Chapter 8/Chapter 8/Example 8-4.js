$(document).ready(
    function()
    {
        var animating = false;
        
        $('input#exampleAnimationGrow').click(
            function(event)
            {
                event.preventDefault();
                
                if (!animating)
                {
                    animating = true;
                    
                    var easing = $('select#exampleAnimationEasing').val();
                    var duration = parseInt($('input#exampleAnimationDuration').val());
    
                    $('div#exampleDialog').animate(
                        {
                            width : '400px',
                            height : '350px',
                            marginLeft : '-210px',
                            marginTop : '-185px'
                            
                        },
                        duration,
                        easing,
                        function()
                        {
                            animating = false;
                        }
                    );
                }
            }
        );
        
        $('input#exampleAnimationShrink').click(
            function(event)
            {
                event.preventDefault();
                
                if (!animating)
                {
                    animating = true;
                    
                    var easing = $('select#exampleAnimationEasing').val();
                    var duration = parseInt($('input#exampleAnimationDuration').val());

                    $('div#exampleDialog').animate(
                        {
                            width : '300px',
                            height : '200px',
                            marginLeft : '-160px',
                            marginTop : '-110px'
                        },
                        duration,
                        easing,
                        function()
                        {
                            animating = false;
                        }
                    );
                
                }
            }
        );
        
        $('input#exampleAnimationDuration').change(
            function()
            {
                $(this).attr('title', $(this).val());
            }
        );
    }
);
