$(document).ready(
function () {
    var testbutton=$('button#testbutton');
    testbutton.click(function (event) {
        alert("this test3");
        $(document).ajax(
            {
                beforeSend:function (event,request,options) {
                    
                },
                success:function (response,status,request) {
                    alert(request.getAttribute("test3status"));
                },
                error:function (request,status,error) {
                    alert("error");
                },
                url:'/test3'
            }
        );
    });
}



);
