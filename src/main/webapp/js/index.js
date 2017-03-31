$(document).ready(function() {

    $('a').on('click',null,function(e){
        if($(this).attr('href')=="#"){
            return;
        }
        e.preventDefault();
        var href=$(this).attr('href');
        $("#contentid").load(href);
    });

    $("#contentid").load("/Schedule/html/schedule.html");

    //发送初始化命令
    $.ajax({
        url:"/Schedule/scheduleInit.do"
    });
 });
