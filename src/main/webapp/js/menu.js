$(document).ready(function() {
// Dropdown Menu
    var dropdown = document.querySelectorAll('.dropdown');
    var dropdownArray = Array.prototype.slice.call(dropdown, 0);
    var tabs = $('#tabs');


//this.className会列出this的类列表，然后用正则表达式检查是否存在参数指定类
//用prototype是原型，这样定义可以应用于所有的继承类，同时确定this范围
    Element.prototype.hasClass = function (className) {
        return this.className && new RegExp("(^|\\s)" + className + "(\\s|$)").test(this.className);
    };

//隐藏到展开
    function hide2show(menu, arrow) {
        menu.classList.add('show');
        menu.classList.remove('hide');
        arrow.classList.add('open');
        arrow.classList.remove('close');
    };

//展开到隐藏
    function show2hide(menu, arrow) {
        menu.classList.remove('show');
        menu.classList.add('hide');
        arrow.classList.remove('open');
        arrow.classList.add('close');
    };

//赋予每个dropdown点击方法
    dropdownArray.forEach(function (el) {
        var button = el.querySelector('a[data-toggle="dropdown"]'),
            menu = el.querySelector('.dropdown-menu'),
            arrow = button.querySelector('i.icon-arrow');

        button.onclick = function (event) {
            //将其他菜单收缩
            dropdownArray.forEach(function (el) {
                var othermenu = el.querySelector('.dropdown-menu'),
                    otherarrow = el.querySelector('a[data-toggle="dropdown"]').querySelector('i.icon-arrow');
                //不为选择项收缩
                if (menu != othermenu) {
                    show2hide(othermenu, otherarrow);
                }else{
                    //为选择项则改变状态
                    if (!menu.hasClass('show')) {
                        hide2show(menu, arrow);
                        event.preventDefault();
                    }
                    else {
                        show2hide(menu, arrow);
                        event.preventDefault();
                    }
                }
            });
        };
    });
    
    //设置菜单栏点击事件
    $('a#schedule').click(function (event) {
        event.preventDefault();
        $("#contentid").load("../html/schedule/schedule.html");
    });
    










});








