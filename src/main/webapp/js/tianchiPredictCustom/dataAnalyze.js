$(document).ready(function() {

    $.getJSON("/tianchiPredictCustomTest.do",function (data) {
        var dataarray=data.result;
        alert(dataarray[0].shop_id);
    });


    
    // var mainAnalyze = echarts.init(document.getElementById('mainAnalyze'));
    // mainAnalyze.setOption({
    //     title: {
    //         text: '商家客流柱状图'
    //     },
    //     tooltip: {},
    //     legend: {
    //         data: ['人数']
    //     }
    // });
    
    
    
    
});