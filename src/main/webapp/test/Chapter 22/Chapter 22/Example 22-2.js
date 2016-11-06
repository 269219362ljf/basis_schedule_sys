$(document).ready(
    function()
    {
        $('button.toolbar-btn').click(
            function() 
            {
                var data = this && $(this).data && $(this).data();
                if (data && data.format && document.execCommand)
                {
                    document.execCommand(data.format, false, null);
                    $('div#container').focus();
                }
            }
        );
        $('select.toolbar-ddl').change(
            function()
            {
                var data = this && $(this).data && $(this).data();
                if (data && data.format && document.execCommand)
                {
                    document.execCommand(data.format, false, this[this.selectedIndex].value);
                    this.selectedIndex = 0;
                    $('div#container').focus();
                }
            }
        );
    }
);
