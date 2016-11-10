$(document).ready(
    function()
    {
        var animating = false;
        
        $('input#exampleAnimationShow').click(
            function(event)
            {
                event.preventDefault();
                
                if (!animating)
                {
                    animating = true;
                    
                    var easing = $('select#exampleAnimationEasing').val();
                    var duration = parseInt($('input#exampleAnimationDuration').val());
    
                    $('div#exampleDialog').slideDown(
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
        
        $('input#exampleAnimationHide').click(
            function(event)
            {
                event.preventDefault();
                
                if (!animating)
                {
                    animating = true;
                    
                    var easing = $('select#exampleAnimationEasing').val();
                    var duration = parseInt($('input#exampleAnimationDuration').val());

                    $('div#exampleDialog').slideUp(
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
        
        $('input#exampleAnimationToggle').click(
            function(event)
            {
                event.preventDefault();
                
                if (!animating)
                {
                    animating = true;

                    var easing = $('select#exampleAnimationEasing').val();
                    var duration = parseInt($('input#exampleAnimationDuration').val());
    
                    $('div#exampleDialog').slideToggle(
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
