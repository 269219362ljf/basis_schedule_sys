$.fn.extend({

    selectFile : function()
    {
        this.addClass('finderSelected');

        this.each(
            function()
            {
                if ($.inArray($(this), finder.selectedFiles) == -1)
                {
                    finder.selectedFiles.push($(this));
                }
            }
        );

        return this;
    },

    unselectFile : function()
    {
        this.removeClass('finderSelected');
        var files = this;

        if (finder.selectedFiles instanceof Array && finder.selectedFiles.length)
        {   //用grep从selectedFiles删除相应项，grep就是一种过滤，第一个参数为目标数组
            //第二个是判断（过滤）条件，如果为true才保留，否则删除
            finder.selectedFiles = $.grep(
                finder.selectedFiles,
                function(file, index)
                {
                    return $.inArray(file, files) == -1;
                }
            );
        }

        return this;
    }
});

var finder = {

    selectingFiles : false,//表状态，是否正在选择文件

    selectedFiles : [],//选中的文件列表

    unselectSelected : function()
    {
        //解除选择
        if (this.selectedFiles instanceof Array && this.selectedFiles.length)
        {
            $(this.selectedFiles).each(
                function()
                {
                    $(this).unselectFile();
                }
            );
        }

        this.selectedFiles = [];
    },
    //finder初始化
    ready : function()
    {
        $('div.finderDirectory, div.finderFile')
            .mousedown(
                function()
                {
                    if (!finder.selectingFiles)
                    {
                        finder.unselectSelected();
                        $(this).selectFile();
                    }
                }
            )
            .draggable({
                helper : 'clone',
                opacity : 0.5
            });

        $('div.finderDirectory').droppable({
            accept : 'div.finderDirectory, div.finderFile',
            hoverClass : 'finderDirectoryDrop',
            drop : function(event, ui)
            {
                var path = ui.draggable.data('path');
                ui.draggable.remove();
            }
        });
        //div#finderFiles开启选择框模式
        //append to 表示在何处放置表示选择框div元素
        //filter 使指定类变得可以选择
        $('div#finderFiles').selectable({
            appendTo : 'div#finderFiles',
            filter : 'div.finderDirectory, div.finderFile',
            start : function(event, ui)
            {
                //开始选择时，设置状态，解除之前的选择
                finder.selectingFiles = true;
                finder.unselectSelected();
            },
            stop : function(event, ui)
            {
                finder.selectingFiles = false;
            },
            selecting : function(event, ui)
            {
                //选择进行时，会持续触发selecting回调函数
                // 正在选择的对象会传给ui.selecting属性选择器中描述
                //调用自定义扩展函数selectFile就可以更改选择对象状态s
                $(ui.selecting).selectFile();
            },
            unselecting : function(event, ui)
            {
                //同selecting
                $(ui.unselecting).unselectFile();
            }
        });
    }
};

$(document).ready(
    function()
    {
        finder.ready();
    }
);
