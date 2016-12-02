//将通用设置参数独立出来
function createDataTableCommonParam(ajaxUrl) {
    var opt={
        "bAutoWidth": false,//禁用自动适应列宽
        "bJQueryUI": true,//JQueryUI皮肤插件
        "bProcessing": true,//启用进度显示
        "bScrollInfinite": true,//开启内置滚动条，并且显示所有数据
        "bScrollCollapse": true,
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
        "ajax":{
            url: ajaxUrl,
            type: "get",
            dataType: "json",
            dataSrc:"data"
        }
    };
    return opt;
}
