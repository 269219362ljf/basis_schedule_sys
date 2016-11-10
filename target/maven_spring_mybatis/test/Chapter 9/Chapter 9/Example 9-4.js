$.fn.extend({

    contextMenu : function()
    {
        //虽然本身为无参数函数，但若有输入参数，可以通过arguments获取输入参数
        var options = arguments[0] !== undefined ? arguments[0] : {};

        var contextMenuIsEnabled = true;

        var contextMenu = this;

        if (typeof options == 'string')
        {
            switch (options)
            {
                case 'disable':
                {
                    contextMenuIsEnabled = false;
                    break;
                }
            }
        }
        else if (typeof options == 'object')
        {
            // You can pass in an object containing options to
            // further customize your context menu.

        }

        function getViewportDimensions()
        {
            var x, y;

            if (self.innerHeight)
            {
                x = self.innerWidth;
                y = self.innerHeight;
            }
            else if (document.documentElement && document.documentElement.clientHeight)
            {
                x = document.documentElement.clientWidth;
                y = document.documentElement.clientHeight;
            }
            else if (document.body)
            {
                x = document.body.clientWidth;
                y = document.body.clientHeight;
            }

            return {
                x : x,
                y : y
            };
        }

        if (contextMenuIsEnabled)
        {
            // If this is attaching a context menu to multiple elements,
            // iterate over each of them.
            //对右键菜单初始化,绑定选定变色的class
            this.find('li')
                .not('li.contextMenuDisabled, li.contextMenuSeparator')
                .bind(
                    'mouseover.contextMenu',
                    function()
                    {
                        $(this).addClass('contextMenuHover');
                    }
                )
                .bind(
                    'mouseout.contextMenu',
                    function()
                    {
                        $(this).removeClass('contextMenuHover');
                    }
                );
            //查找当前元素是否已经挂靠了contextMenu数据，如果没有，增加一个contextMenu的数据
            //并添加类contextMenu和绑定两个方法
            //contextMenu数据为布尔值，用于追踪菜单是否处于激活状态
            if (!this.data('contextMenu'))
            {
                this.data('contextMenu', true)
                    .addClass('contextMenu')
                    .bind(
                        'mouseover.contextMenu',
                        function()
                        {
                            $(this).data('contextMenu', true);
                        }
                    )
                    .bind(
                        'mouseout.contextMenu',
                        function()
                        {
                            $(this).data('contextMenu', false);
                        }
                    );
                //选定整个页面容器，禁用上下文菜单，并启用自定义上下文菜单
                this.parents('.contextMenuContainer:first')
                    .bind(
                        'contextmenu.contextMenu',
                        function(event)
                        {
                            event.preventDefault();

                            var viewport = getViewportDimensions();

                            contextMenu.show();

                            contextMenu.css({
                                top : 'auto',
                                right : 'auto',
                                bottom : 'auto',
                                left : 'auto'
                            });

                            if (contextMenu.outerHeight() > (viewport.y - event.pageY))
                            {
                                contextMenu.css(
                                    'bottom',
                                    (viewport.y - event.pageY) + 'px'
                                );
                            }
                            else
                            {
                                contextMenu.css(
                                    'top',
                                    event.pageY + 'px'
                                );
                            }

                            if (contextMenu.outerWidth() > (viewport.x - event.pageX))
                            {
                                contextMenu.css(
                                    'right',
                                    (viewport.x - event.pageX) + 'px'
                                );
                            }
                            else
                            {
                                contextMenu.css(
                                    'left',
                                    event.pageX + 'px'
                                );
                            }
                        }
                    );
            }

            //如果body已存在contextMenu数据，即证明事件已挂钩,防止事件多次挂钩
            //不存在则新增数据并挂钩事件
            if (!$('body').data('contextMenu'))
            {
                $('body').data('contextMenu', true);
                //若任意div的contextMenu数据未为true则隐藏div
                $(document).bind(
                    'mousedown.contextMenu',
                    function()
                    {
                        $('div.contextMenu').each(
                            function()
                            {
                                if (!$(this).data('contextMenu'))
                                {
                                    $(this).hide();
                                }
                            }
                        );
                    }
                );
            }
        }//如果为禁用自定义菜单
        else
        {
            //事件解绑定恢复默认动作并去除contextMenu类，将contextMenu数值设为false
            this.find('li')
                .not('li.contextMenuDisabled, li.contextMenuSeparator')
                .unbind('mouseover.contextMenu')
                .unbind('mouseout.contextMenu');

            this.data('contextMenu', false)
                .removeClass('contextMenu')
                .unbind('mouseover.contextMenu')
                .unbind('mouseout.contextMenu');

            this.parents('.contextMenuContainer:first')
                .unbind('contextmenu.contextMenu');

            $('body').data('contextMenu', false);

            $(document).unbind('mousedown.contextMenu');
        }

        return this;
    }
});

$(document).ready(
    function()
    {
        $('span#applicationContextMenuDisable').click(
            function(event)
            {
                $('div#applicationContextMenu').contextMenu('disable');
                $('div#applicationContextMenu').hide();
            }
        );

        $('span#applicationContextMenuEnable').click(
            function()
            {
                $('div#applicationContextMenu').contextMenu();
            }
        );

        $('div#applicationContextMenu').contextMenu();
    }
);
