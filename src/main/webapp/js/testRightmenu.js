$(document).ready(
    function()
    {
        var contextMenuOn = false;
        //选择默认上下文菜单
        $(document).on(
            'contextmenu',
            function(event)
            {
                //禁止默认上下文菜单
                event.preventDefault();

                var rightmenu = $('div#rightmenu');
                //显示右键菜单
                rightmenu.show();

                // The following bit gets the dimensions of the viewport
                // Thanks to quirksmode.org
                //获取整个浏览器的宽高
                var vpx, vpy;

                if (self.innerHeight)
                {
                    // all except Explorer
                    vpx = self.innerWidth;
                    vpy = self.innerHeight;
                }
                else if (document.documentElement &&
                    document.documentElement.clientHeight)
                {
                    // Explorer 6 Strict Mode
                    vpx = document.documentElement.clientWidth;
                    vpy = document.documentElement.clientHeight;
                }
                else if (document.body)
                {
                    // other Explorers
                    vpx = document.body.clientWidth;
                    vpy = document.body.clientHeight;
                }

                // Reset offset values to their defaults
                //重置右键菜单的位置为默认值，以便设在新位置
                rightmenu.css({
                    top : 'auto',
                    right : 'auto',
                    bottom : 'auto',
                    left : 'auto'
                });


                // If the height or width of the context menu is greater than the amount
                // of pixels from the point of click to the right or bottom edge of the
                // viewport adjust the offset accordingly
                //计算弹出位置，判断是否超出浏览器高（宽）度，以及弹出的方向
                if (rightmenu.outerHeight() > (vpy - event.pageY))
                {
                    rightmenu.css('bottom', (vpy - event.pageY) + 'px');
                }
                else
                {
                    rightmenu.css('top', event.pageY + 'px');
                }

                if (rightmenu.outerWidth() > (vpx - event.pageX))
                {
                    rightmenu.css('right',  (vpx - event.pageX) + 'px');
                }
                else
                {
                    rightmenu.css('left', event.pageX + 'px');
                }
            }
        );

        //判断鼠标是否停留在右键菜单中，为后面单击判断提供依据
        $('div#rightmenu').hover(
            function()
            {
                contextMenuOn = true;
            },
            function()
            {
                contextMenuOn = false;
            }
        );

        //单击时，判断鼠标是否在右键菜单中，不在隐藏右键菜单
        $(document).mousedown(
            function()
            {
                if (!contextMenuOn)
                {
                    $('div#rightmenu').hide();
                }
            }
        );
    }
);
