$(document).ready(function() {
    var opt=createDataTableCommonParam("/Schedule/queryDepState.do");
    opt.dom='<"H" lfr  > t <"F" ip >';
    opt.aoColumns=[
        {"mDataProp":"task_id"},
        {"mDataProp":"parent_id"}
    ]

    if($.fn.dataTable.isDataTable( '#depStateTable' )){
        $('#depStateTable').destroy();
    }
    $('#depStateTable').DataTable(opt);
});
