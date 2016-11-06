$.fn.extend({
    outerHTML : function()
    {
        //设置一个临时div，然后将this克隆插入div
        var temporary = $("<div/>").append($(this).clone());
        //通过jquery的html()方法将对象变为html代码段，html（）内部调用的innerHTML是
        //所有浏览器都实现了的，确保可行
        var html = temporary.html();
        //删除临时div
        temporary.remove();
        return html;
    },

    enableDragAndDrop : function()
    {
        return this.each(
            function()
            {
                //以下3个都是启用拖放功能，只不过是不同浏览器的实现
                if (typeof this.style.WebkitUserDrag != 'undefined')
                {
                    this.style.WebkitUserDrag = 'element';
                }

                if (typeof this.draggable != 'undefined')
                {
                    this.draggable = true;   
                }

                if (typeof this.dragDrop == 'function')
                {
                    this.dragDrop();
                }
            }
        );
    }
});

$(document).ready(
    function()
    {
        //设置在div.finderDirectory, div.finderFile上mousedown事件处理器
        $(document).on(
            'mousedown.finder',
            'div.finderDirectory, div.finderFile',
            function(event)
            {
                //开启拖放属性
                $(this).enableDragAndDrop();
                
                $('div.finderIconSelected')
                    .removeClass('finderIconSelected');
    
                $('span.finderDirectoryNameSelected')
                    .removeClass('finderDirectoryNameSelected');
    
                $(this).find('div.finderIcon')
                    .addClass('finderIconSelected');
    
                $(this).find('div.finderDirectoryName span')
                    .addClass('finderDirectoryNameSelected');
            }
        );


        //拖动事件，dragstart->drag->dragend
        //投放事件，dragenter->dragover->drop(放下)/dragleave(离开)
        //顺序：dragstart->drag->dragenter->dragover->drop->dragend
        //此处设置拖放事件处理器
        $('div.finderDirectory, div.finderFile')
            .on(
                'dragstart.finder',
                function(event)
                {
                    //终止事件在传播过程的捕获、目标处理或起泡阶段进一步传播
                    //此处开始对象是拖动对象
                    event.stopPropagation();
                    //获取结点的html代码
                    var html = $(this).outerHTML();
                    //DataTransfer 对象用来保存被拖动的数据,保存到剪切板
                    var dataTransfer = event.originalEvent.dataTransfer;
                    //用来指定拖动时被允许的效果。
                    // copy: 复制到新的位置.
                    // move:移动到新的位置 .
                    // link:建立一个源位置到新位置的链接.
                    // copyLink: 允许复制或者链接.
                    // copyMove: 允许复制或者移动.
                    // linkMove: 允许链接或者移动.
                    // all: 允许所有的操作.
                    // none: 禁止所有操作.
                    // uninitialized: 缺省值（默认值）, 相当于 all.
                    dataTransfer.effectAllowed = 'copyMove';

                    try
                    {
                        //保存数据，前面是类型
                        dataTransfer.setData('text/html', html);
                        dataTransfer.setData('text/plain', html);
                    }
                    catch (error)
                    {
                        dataTransfer.setData('Text', html);
                    }
                }
            )
            .on(
                'dragend.finder',
                function(event)
                {
                    //检查是否已经放下，如果已经放下，证明拖动结束，删除finderDirectoryDrop和拖动结点
                    //此处removeClass并没有起作用，因为下一句删除结点必定会连class也删除
                    //所以removeClass给出一个实现，就是用class当做信号来传递结点状态
                    if ($('div.finderDirectoryDrop').length)
                    {
                        $(this).removeClass('finderDirectoryDrop');
                        $(this).remove();
                    }
                }
            )
            .on(
                'dragenter.finder',
                function(event)
                {
                    //投放事件开始，禁止默认行为和停止传播
                    // 从这里的方法开始对象是投放区域
                    //意思为有外来物体进入区域，触发
                    event.preventDefault();
                    event.stopPropagation();
                }
            )
            .on(
                'dragover.finder',
                function(event)
                {
                    //投放事件，禁止默认行为和停止传播
                    //加载类finderDirectoryDrop，将图标转为打开的文件夹
                    //意思有外来物体覆盖区域时，触发
                    event.preventDefault();
                    event.stopPropagation();

                    if ($(this).is('div.finderDirectory'))
                    {
                        $(this).addClass('finderDirectoryDrop');
                    }
                }
            )
            .on(
                'dragleave.finder',
                function(event)
                {
                    //意思为拖动经过
                    //投放结束
                    event.preventDefault();
                    event.stopPropagation();
                    //去除类finderDirectoryDrop，将图标恢复
                    $(this).removeClass('finderDirectoryDrop');
                }
            )
            .on(
                'drop.finder',
                function(event)
                {
                    //放下事件
                    event.preventDefault();
                    event.stopPropagation();


                    var dataTransfer = event.originalEvent.dataTransfer;

                    try
                    {
                        //获取剪切板数据
                        var html = dataTransfer.getData('text/html');
                    }
                    catch (error)
                    {
                        var html = dataTransfer.getData('Text');
                    }

                    html = $(html);
                    var drop = $(this);
                    //比较剪切板数据和当前释放区域的结点，若一致为true，防止自己放在自己身上
                    var dontAcceptTheDrop = (
                        drop.data('path') == html.data('path') ||
                        drop.is('div.finderFile')
                    );
                    //一致去除类finderDirectoryDrop，终止执行
                    if (dontAcceptTheDrop)
                    {
                        // Prevent file from being dragged onto itself
                        drop.removeClass('finderDirectoryDrop');
                        return;
                    }

                    if (html.hasClass('finderDirectory finderFile'))
                    {
                        // Do something with the dropped file
                        console.log(html);
                    }

                }
            );
    }
);
