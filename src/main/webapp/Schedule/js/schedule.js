$(document).ready(function() {
    var tabs = $('#tabs');
    var path='/Schedule/html'
    var container=$("#maincontent");

//TAB点击方法
    function tabclick(event){
        event.preventDefault();
        //单击左键
        if(event.which=1){
            if ($(this).closest("li").attr("id") == "current"){ //detection for current tab
                return;
            }
            else{
                $("#tabs li").attr("id",""); //Reset id's
                $(this).parent().attr("id","current"); // Activate this
                $("#maincontent").load($(this).attr("href"));
            }
        }else
        if(event.which=3)//单击右键
        {
            alert("右键");
        }
    };

    //初始化tabs，仅为前期测试用
    $("#tabs li:first").attr("id", "current"); // Activate the first tab
    container.load(path+"/taskList.html");
    $('ul#tabs').on('click',
        'a',
        tabclick
    );
});
