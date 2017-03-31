//自定义插件和通用设置
$(document).ready(function () {

    /* 模态框设置 */
    /*模态框事件绑定*/
    //对于有属性data-reveal-id的元素进行附加事件函数
    //属性data-reveal-id指向模态框的id
    $('a[data-reveal-id]').on('click',null, function(e) {
        e.preventDefault();
        var modalLocation = $(this).attr('data-reveal-id');
        //$(this).data()是元素中，以data-开头的属性键值对
        $('#'+modalLocation).reveal($(this).data());
    });

    //扩展模态框插件reveal
    $.fn.reveal=function(options){

        //默认设置
        var defaults = {
            animationspeed: 300, //how fast animtions are
            closeonbackgroundclick: true, //if you click background will modal close?
            dismissmodalclass: 'close-reveal-modal' //the class of a button or element that will close an open modal
        };

        //defaults与options合并，同样的属性后者会覆盖前者
        var options = $.extend({}, defaults, options);

        //对每一个模态框都进行处理,因为可能存在多个模态框
        return this.each(function() {
            //modal为模态框本体，topOffset判断模态框位置，locked为是否锁定模态框，modalBG为灰罩背景
            var modal = $(this),
                topMeasure  = parseInt(modal.css('top')),
                topOffset = modal.height() + topMeasure,
                locked = false,
                modalBG = $('.reveal-modal-bg');

            //背景不存在就新建并在模态框元素插入后面位置
            if(modalBG.length == 0) {
                modalBG = $('<div class="reveal-modal-bg" />').insertAfter(modal);
            }

            //模态框状态切换事件处理函数
            //模态框绑定打开事件，此处为定义，并不会马上执行function
            modal.one('reveal:open', function () {
                //背景解绑click.modalEvent事件，以确保modalBG不会有click.modalEvent事件的处理函数影响
                modalBG.unbind('click.modalEvent');
                //关闭按钮类解绑click.modalEvent事件，理由同上
                $('.' + options.dismissmodalclass).unbind('click.modalEvent');
                //如果模态框并没锁定，则执行锁定并进行入场动画
                if(!locked) {
                    lockModal();
                        modal.css({'opacity' : 0, 'visibility' : 'visible', 'top': $(document).scrollTop()+topMeasure});
                        modalBG.fadeIn(options.animationspeed/2);
                        modal.delay(options.animationspeed/2).animate({
                            "opacity" : 1
                        }, options.animationspeed,unlockModal());
                }
                // //当打开事件完成后，对打开事件进行解绑，以防止未知退出导致污染或者泄露
                // modal.unbind('reveal:open');
            });

            //模态框绑定关闭事件，此处为定义，并不会马上执行function
            modal.one('reveal:close', function () {
                if(!locked) {
                    lockModal();
                        modalBG.delay(options.animationspeed).fadeOut(options.animationspeed);
                        modal.animate({
                            "opacity" : 0
                        }, options.animationspeed, function() {
                            modal.css({'opacity' : 1, 'visibility' : 'hidden', 'top' : topMeasure});
                            unlockModal();
                        });
                }
                // //当关闭事件完成后，对打开事件进行解绑，以防止未知退出导致污染或者泄露
                // modal.unbind('reveal:close');
            });

            //完成事件绑定后进行触发reveal:open事件
            modal.trigger('reveal:open');

            //添加关闭按钮的绑定事件
            var closeButton = $('.' + options.dismissmodalclass).bind('click.modalEvent', function () {
                modal.trigger('reveal:close')
            });

            //如果closeonbackgroundclick设置为true，则点击背景会触发模态框退出
            if(options.closeonbackgroundclick) {
                //modalBG.css({"cursor":"pointer"});
                modalBG.bind('click.modalEvent', function () {
                    modal.trigger('reveal:close');
                });
            }

            //按下ESC也会造成模态框退出
            $('body').keyup(function(e) {
                if(e.which===27){ modal.trigger('reveal:close'); } // 27 is the keycode for the Escape key
            });

            //动画途中对模态框进行锁定
            function unlockModal() {
                locked = false;
            }
            //动画完结对模态框进行解锁
            function lockModal() {
                locked = true;
            }
        });
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
});

/* 模态框设置结束 */


//将通用设置参数独立出来

//dataTable通用和汉化设置
function createDataTableCommonParam(ajaxUrl) {
    var opt={
        "bRetrieve":true,
        //"serverSide": true,//启动服务器端分页
        "pageLength": 10,  // 每页10条记录
        "bAutoWidth": false,//禁用自动适应列宽
        "bJQueryUI": true,//JQueryUI皮肤插件
        "bProcessing": true,//启用进度显示
        "bScrollInfinite": true,//开启内置滚动条，并且显示所有数据
        "bScrollCollapse": true,
        "sScrollX" : 1200, //DataTables的宽
        "bInfo" : true, //是否显示页脚信息，DataTables插件左下角显示记录数
        "processing" : false, // 隐藏加载提示,自行处理
        "stripeClasses" : [ "odd", "even" ],// 为奇偶行加上样式，兼容不支持CSS伪类的场合
        "oLanguage": {                          //汉化
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "没有检索到数据",
            "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",
            "sInfoEmtpy": "没有数据",
            "sProcessing": "正在加载数据...",
            "sSearch": "模糊查询:  ",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前页",
                "sNext": "后页",
                "sLast": "尾页"
            },
            "sInfoFiltered":"(从总共 _MAX_ 条数据中)"
        },
        "ajax":{
            url: ajaxUrl,
            type: "get",
            dataType: "json",
            dataSrc:"data"
        }
    };
    return opt;
}



