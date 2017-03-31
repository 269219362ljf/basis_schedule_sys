$(document).ready(function() {
    var opt=createDataTableCommonParam("/Schedule/queryRunningState.do");
    opt.dom='<"H" lfr  > t <"F" ip >';
    opt.aoColumns=[
        {"mDataProp":"task_id"},
        {"mDataProp":"st"},
        {"mDataProp":"t_date"},
        {"mDataProp":"beg_time"},
        {"mDataProp":"end_time"}
    ]

    if($.fn.dataTable.isDataTable( '#runningTable' )){
        $('#runningTable').destroy();
    }
    $('#runningTable').DataTable(opt);
});
