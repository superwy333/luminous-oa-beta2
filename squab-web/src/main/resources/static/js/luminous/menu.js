layui.use(['jquery'], function(){
    var $ = layui.jquery;
    var menuHtml = '';

    $.ajax({
        url: 'json/menu.json',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            // 菜单数据渲染
            var menuJson = data.result;
            $.each(menuJson, function (k,v) {
                // alert('k -> ' + k + ' v -> ' + v);
                menuHtml = menuHtml + '<li>\n' +
                    '                        <a href="javascript:;">\n' +
                    '                            <i class="iconfont left-nav-li" lay-tips="' + menuJson[k].name +
                    '">&#xe723;</i>\n' +
                    '                            <cite>' + menuJson[k].name + '</cite>\n' +
                    '                            <i class="iconfont nav_right">&#xe697;</i></a>';
                menuHtml = menuHtml + '<ul class="sub-menu">';
                var menuJsonChild = menuJson[k].child;
                $.each(menuJsonChild, function (k,v) {
                    // alert(v.url);
                    menuHtml = menuHtml +
                        '                            <li>\n' +
                        '                                <a onclick="xadmin.add_tab(\'' + menuJsonChild[k].name +
                        '\',\'' + menuJsonChild[k].url +
                        '\')">\n' +
                        '                                    <i class="iconfont">&#xe6a7;</i>\n' +
                        '                                    <cite>' + menuJsonChild[k].name +
                        '</cite></a>\n' +
                        '                            </li>';
                });
                menuHtml = menuHtml + '                        </ul>\n' +
                    '                    </li>';
            });
            // console.log(menuHtml)；
            $('#nav').append(menuHtml)
        }
    });
});