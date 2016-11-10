$(document).ready(
    function()
    {
        $('div#container').focus();
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
        $('button#btnCreateSelection').click(
            function()
            {
                var container = document.getElementById('container');
                container.innerHTML = 'Here is some sample text for selection';
                var range = document.createRange();
                range.setStart(container.firstChild, 5);
                range.setEnd(container.firstChild, 17);
                setSelectionRange(range);
            }
        );
        $('button#btnStoreSelection').click(
            function()
            {
                window.selectedRange = getSelectionRange();
            }
        );
        $('button#btnRestoreSelection').click(
            function()
            {
                if (window.selectedRange)
                {
                    setSelectionRange(window.selectedRange);
                }
            }
        );
    }
);

function getSelectionRange() 
{
    if (window.getSelection) 
    {
        var sel = window.getSelection();
        if (sel.getRangeAt && sel.rangeCount) 
        {
            return sel.getRangeAt(0);
        }
        else // Safari
        {
            var range = document.createRange();
            range.setStart(sel.anchorNode, sel.anchorOffset);
            range.setEnd(sel.focusNode, sel.focusOffset);
            return range;
        }
    } 
    return null;
}

function setSelectionRange(range) 
{
    if (range && window.getSelection) 
    {
        var sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(range);
    }
}
