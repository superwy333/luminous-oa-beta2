layui.use(['table', 'jquery'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var tableIns = table.render({
        elem: '#deployList'
        , method: 'post'
        , contentType: 'application/json'
        // , height: 'full-200'
        , url: '/deploy/deployList' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'name', title: '模型名称', minWidth: 150, sort: true}
            // , {field: 'description', title: '描述', minWidth: 150, sort: true}
            , {field: 'deploymentTime', title: '部署时间', minWidth: 150, sort: true}
            , {field: 'operation', title: '操作', minWidth: 150, toolbar: '#deployListOperation'}
        ]]
    });


    $('#reload').click(function () {
        tableIns.reload(
            {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    createBy: $('#createBy').val()
                }
            }
        );
    });

    //监听工具条
    table.on('tool(deployList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'diagram') {
            xadmin.open('流程记录','/deploy/deployDiagram?deployId=' + data.id,1200,800,false);
        } else if (layEvent === 'del') {
            var reqData = {
                bizKey: 'delDeploy',
                data: {
                    id:data.id
                }
            };
            $.ajax({
                url: '/deploy/deleteDeploy',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: JSON.stringify(reqData),
                success: function (data) {
                    if (data.code==0) {
                        layer.msg("操作成功");
                        window.location.reload();
                    } else {
                        layer.alert("操作失败，失败原因：" + data.msg);
                    }
                },
                error: function (data) {
                    layer.alert("网络超时，请联系管理员");
                }
            });
        }
    });
});