$(document).ready(function() {
    var dataTableparam=createDataTableCommonParam("/queryRunningState.do");
    dataTableparam.dom='<"H" lfr  > t <"F" ip >';
    dataTableparam.aoColumns=[
        {"mDataProp":"task_id"},
        {"mDataProp":"st"},
        {"mDataProp":"t_date"},
        {"mDataProp":"beg_time"},
        {"mDataProp":"end_time"}
    ];


    var dataTable=$('#runningTable').DataTable(dataTableparam);




    

});
