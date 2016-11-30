$(document).ready(function() {
    $("#contentid").load("../html/schedule/schedule.html");
    //发送初始化命令
    $.ajax({
        url:"/scheduleInit.do",
        type:"get",
        dataType:"json",
        success:function (data) {
            if(data.result != 0){
                alert("初始化错误 请检查任务列表");
            }
                },
        error:function (data) {
                    alert("fail");
                }
    });
 });
