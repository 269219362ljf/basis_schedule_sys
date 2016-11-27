$(document).ready(function() {
    var dataSet;
    $.ajax({
        url:"/queryTaskList.do",
        type:"get",
        dataType:"json",
        success:function (data) {
            alert("success "+data.toString());
        },
        error:function (data) {
            alert("fail "+data.toString());
        }
    });



    $('#taskTable').DataTable({
        "bAutoWidth": false,//禁用自动适应列宽
        "bJQueryUI": true,//JQueryUI皮肤插件
        "bProcessing": true,//启用进度显示
        "bScrollInfinite": true,//开启内置滚动条，并且显示所有数据
        "bScrollCollapse": true,
        "bInfo":false,
        "oLanguage": {                          //汉化
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "没有检索到数据",
            "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",
            "sInfoEmtpy": "没有数据",
            "sProcessing": "正在加载数据...",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前页",
                "sNext": "后页",
                "sLast": "尾页"
            },
            "sInfoFiltered":"(从总共 _MAX_ 条数据中)"
        },
        ajax: {
            url: "/queryTaskList.do",
            type: "get",
            dataType: "json",
            dataSrc:"data"
        },
        aoColumns: [
            {"mDataProp":"task_id"},
            {"mDataProp":"name"},
            {"mDataProp":"des"},
            {"mDataProp":"taskclassname"},
            {"mDataProp":"type"},
            {"mDataProp":"st"},
            {"mDataProp":"para"},
            {"mDataProp":"prior"},
            {"mDataProp":"beg_time"},
            {"mDataProp":"end_time"},
            {"mDataProp":"cost"},
            {"mDataProp":"avg_cost"}
            ]

    });
});
