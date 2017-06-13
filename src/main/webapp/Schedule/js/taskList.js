$(document).ready(function() {
    //selectArray需要初始化，否则下面直接使用indexOf会出错导致行初始化失败，显示无法找到数据
    //selectArray用于保存选定项
    var selectArray=[];
    var dtable;
    //Table设置
    //生成额外配置
    var extendopt={
        "destroy": true,
        dom:'<"H"<"toolbar"> Blfr  > t <"F"ip >',//表头dom元素
        columns :[
        {
            "sClass": "text-center",
            "mDataProp": "task_id",
            "render": function (data, type, full, meta) {
                return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
            },
            "sWidth" : "30px",
            "orderable" : false,
            "bSortable": false
        },//复选框
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
        "fnCreatedRow":function (nRow, aData, iDataIndex) {
            //显示已经选好的项
            if(selectArray.indexOf($('.checkchild',nRow).val())>-1){
                $('.checkchild',nRow).prop("checked", true);
            }
            //$('td:eq(1)',nRow).html(iDataIndex+1);//为第一个单元格增加序号
        },
        order:[[ 1, "asc" ]],//更改默认排序列
        buttons:[//定义按钮
            {
                text : '新增',
                action: function ( e, dt, node, config ) {
                    var modal=$("#add-modal").reveal();
                }
            },
            {
                text : '刷新',
                action: function ( e, dt, node, config ) {
                    dtable.ajax.reload();
                }
            }
        ]
    }
    //生成基本配置并与额外配置合并
    var opt=$.extend({}, createDataTableCommonParam("/Schedule/queryTaskList.do"), extendopt);
    //如果已经初始化就先删除再重建
    if($.fn.dataTable.isDataTable( '#taskTable' )){
        $('#taskTable').destroy();
    }
    dtable=$('#taskTable').DataTable(opt);


    $("div.toolbar").html('<b>任务列表</b>');
    //全选框勾选事件
    $(".checkall").on({
        click:function () {
            var check = $(this).prop("checked");
            $(".checkchild").prop("checked", check);
            $(".checkchild").each(function () {
                    if ($(this).prop("checked")) {
                        if (selectArray.indexOf($(this).val()) == -1) {
                            selectArray[selectArray.length] = $(this).val();
                        }
                    } else {
                        if (selectArray.indexOf($(this).val()) > -1) {
                            selectArray.splice(selectArray.indexOf($(this).val()), 1);
                        }
                    }
                }
            );
        }
    });
    //单选框勾选事件
    $('#taskTable tbody').on(
        {
            click:function () {
                //选择且不存在选定项数组则增加
                if($(this).prop("checked")&&selectArray.indexOf($(this).val())==-1){
                    selectArray[selectArray.length]=$(this).val();
                }
                //不选且存在选定项数组则删除
                if(!$(this).prop("checked")&&selectArray.indexOf($(this).val())>-1){
                    selectArray.splice(selectArray.indexOf($(this).val()),1);
                    $(".checkall").prop("checked",false);
                }
                //其余两种情况是不可能出现，故不考虑
            }
        },".checkchild"
     );
    //table设置完成

    //按钮绑定
    $('#add-modal-submit').bind('click',function () {
        $.ajax({
            url:"/Schedule/addTask.do",
            data:$('#add-modal-form').serialize(),
            type:"get",
            async:false,
            success:function (data,status,jqxhr) {
                alert("success");
                alert(data);
            },
            error:function (jqxhr,status,errorMsg) {
                alert(status);
                alert(errorMsg);
            },
            complete:function (jXHR,status) {
                $(".reveal-modal").trigger('reveal:close')
            }
        })
    });
    
    $('#close').bind('click',function () {
        $(".reveal-modal").trigger('reveal:close')
    });
    //按钮绑定完成

    //模态框的父id选择框填充
    //获取数据
    $.ajax({
        url:"/Schedule/queryTaskid.do",
        async:false,
        success:function (data) {
            parent_ids=$.parseJSON(data).data;
            //将数据添加到select框
            for(var i=0;i<parent_ids.length;i++)
                $('#parent_id').append("<option value='" + parent_ids[i] + "'>" + parent_ids[i] + "</option>");
        }
        }
    );
    //模态框的父id选择框填充完成
});
