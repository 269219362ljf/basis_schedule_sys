var contactNewsletterForm = {
     
    ready : function()
    {
        $('input#contactNewsletterFormSubmit').click(
            function(event)
            {
                var input = $(this);

                input.attr('disabled', true);

                if (!contactNewsletterForm.validate())
                {
                    alert("Please provide both your first and last name");

                    input.removeAttr('disabled');

                    event.preventDefault();
                }
                else
                {
                    $('form#contactNewsletterForm').submit();
                }
            }
        );
    },

    validate : function()
    {
        var hasRequiredValues = true;

        $('form#contactNewsletterForm').find('input, select, textarea').each(
            function()
            {
                var node = $(this);

                if (node.is('[required]'))
                {
                    var value = node.val();

                    if (!value)
                    {
                        hasRequiredValues = false;
                        return false;
                    }
                }
            }
        );

        return hasRequiredValues;
    }        
};

$(document).ready(
    function()
    {
        contactNewsletterForm.ready();
    }
);