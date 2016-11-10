var testForm={
    ready:function () {
        $('input#testForm').click(
            function (event) {
                var input=$(this);
                input.attr('disable',true);
                if(!testForm.validate()){
                    alert("Please input name");
                    input.removeAttr('disable');
                    event.preventDefault();
                }else{
                    $('form#testForm').submit();
                }
            }
        )
    },
    validate:function () {
        var hasRequiredValues=true;
        //查找testForm下所有input,select,textarea，对于每个查找到的都执行一次function()(存在required属性的都检查是否存在值)
        $('form#testForm').find('input,select,textarea').each(
            function () {
                var node=$(this);
                if(node.is('[required]')){
                    var value=node.val();
                    if(!value){
                        hasRequiredValues=false;
                        return false;
                    }
                }
            }
        );
        return hasRequiredValues;
    }
};

$(document).ready(
    function () {
        $('body').append(
            $('<div/>')
                .addClass('selected')
                .attr(
                    {
                        id:'body',
                        title:'Welcome to jQuery'
                    }
                )
                .text(
                    "hello,world"
                )
        );

        //使id为exampleFavoriteLinks的链接元素在新窗口打开
        $('a').click(
            function(event){
                var node=$(this);
                var target=node.attr('target');
                var href=node.attr('href');
                if(target === undefined && href !== undefined){
                    switch (true){
                        case href.indexOf('http://') !== -1 :
                        case href.indexOf('https://') !== -1:
                        case href.indexof('.pdf') !== -1:
                        {
                            node.attr('target','_blank')
                                .addClass('exampleFavoriteLinks');
                            break;
                        }
                    }
                }
            }
        );

        //form初始化
        testForm.ready();

    }
);




