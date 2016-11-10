$(document).ready(
    function()
    {
        var selectedFile;

        $('li.finderCategoryFile').mousedown(
            function()
            {
                if (selectedFile && selectedFile.length)
                {
                    selectedFile.removeClass('finderCategoryFileSelected');
                }

                selectedFile = $(this);
                selectedFile.addClass('finderCategoryFileSelected');
            }
        );

        $('ul#finderCategoryFiles').sortable({
            connectWith : 'ul#finderOtherCategoryFiles',
            placeholder : 'finderCategoryFilePlaceholder',
            opacity : 0.8,
            cursor : 'move',
            update : function(event, ui)
            {
                var data = $(this).sortable(
                    'serialize', {
                        attribute : 'data-path',
                        expression : /^(.*)$/,
                        key : 'categoryFiles[]'
                    }
                );
                
                data += '&categoryId=1';

                alert(data);

                // Here you could go on to make an AJAX request
                // to save the sorted data on the server, which
                // might look like this:
                //
                // $.get('/path/to/server/file.php', data);
            }
        });

        $('ul#finderOtherCategoryFiles').sortable({
            connectWith : 'ul#finderCategoryFiles',
            placeholder : 'finderCategoryFilePlaceholder',
            opacity : 0.8,
            cursor : 'move',
            update : function(event, ui)
            {
                var data = $(this).sortable(
                    'serialize', {
                        attribute : 'data-path',
                        expression : /^(.*)$/,
                        key : 'categoryFiles[]'
                    }
                );
                
                data += '&categoryId=2';

                alert(data);

                // Here you could go on to make an AJAX request
                // to save the sorted data on the server, which
                // might look like this:
                //
                // $.get(‘/path/to/server/file.php’, data);
            }
        });
    }
);
