$(document).ready(function() {

    // $.ajax({
    //     url:"/queryTaskList.do",
    //     type:"get",
    //     dataType:"json",
    //     success:function (data) {
    //         alert("success "+data.toString());
    //     },
    //     error:function (data) {
    //         alert("fail "+data.toString());
    //     }
    // });

    // dom元素：
    // l - Length changing 每页显示多少条数据选项
    // f - Filtering input 搜索框
    // t - The Table 表格
    // i - Information 表格信息
    // p - Pagination 分页按钮
    // r - pRocessing 加载等待显示信息
    // < and > - div elements 一个div元素
    // <"#id" and > - div with an id 指定id的div元素
    // <"class" and > - div with a class 指定样式名的div元素
    // <"#id.class" and > - div with an id and class 指定id和样式的div元素
    //H - jQueryUI'header'classes,<H *>将jQueryUI皮肤（头部）应用到*
    //F - jQueryUI'footer'classes,<F *>将jQueryUI皮肤（脚部）应用到*
    $('#taskTable').DataTable({
        "dom": '<"H"<"toolbar"> Blfr  > t <"F"ip >',
        "bAutoWidth": false,//禁用自动适应列宽
        "bJQueryUI": true,//JQueryUI皮肤插件
        "bProcessing": true,//启用进度显示
        "bScrollInfinite": true,//开启内置滚动条，并且显示所有数据
        "bScrollCollapse": true,
        //"sScrollY" : 450, //DataTables的高
        "sScrollX" : 1200, //DataTables的宽
        "bInfo" : true, //是否显示页脚信息，DataTables插件左下角显示记录数
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
        "ajax": {
            url: "/queryTaskList.do",
            type: "get",
            dataType: "json",
            dataSrc:"data"
        }
        ,
        "aoColumns": [
            {"mDataProp":"task_id"},
            {"mDataProp":"name"},
            {"mDataProp":"des"},
            {"mDataProp":"taskclassname"},
            {"mDataProp":"type"},
            {"mDataProp":"para"},
            {"mDataProp":"prior"},
            {"mDataProp":"cost"},
            {"mDataProp":"avg_cost"}
        ],
        buttons:[
            {
                extend:"copy",
                text:'复制'
            },{
                extend:"print",
                text:'打印'
            }
        ]
    });

    $("div.toolbar").html('<b style="color:red">自定义文字、图片等等</b>');
});
