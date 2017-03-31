//未完成插件20170305
$(document).ready(function () {
    //扩展插件reveal
    $.fn.reveal=function () {
        //获取整体页面高宽
        var bh = $("body").height();
        var bw = $("body").width();
        //新建背景
        var bg = document.createElement("div");
        //定义id
        $(bg).addClass("fullbg");
        //样式设定覆盖全页面，锁定不可编辑
        $(bg).css({
            height:bh,
            width:bw,
            display:"block"
        });
        //插入当前节点之前
        this.before(bg);
        //显示模态框
        this.show();
    }
    //绑定点击
    $("#test").bind("click",function (e) {
        e.preventDefault();
        $("#testdialogid").reveal();
    })
});


//关闭灰色 jQuery 遮罩
function closeBg() {
    $(".fullbg,#testdialogid").hide();
    $(".fullbg").remove();

}
