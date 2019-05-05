layui.use(['table', 'jquery'], function () {
    var table = layui.table;
    var tableIns = table.render({
        elem: '#modelList'
        , method: 'post'
        , contentType: 'application/json'
        // , height: 'full-200'
        , url: '/modeler/modelList' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'name', title: '模型名称', minWidth: 150, sort: true}
            , {field: 'createTime', title: '创建时间', minWidth: 150, sort: true}
            , {field: 'operation', title: '操作', minWidth: 150, toolbar: '#taskTodoOperation'}
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
    table.on('tool(modelList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //流转记录
            //do somehing
            xadmin.open('流程记录','/workflow/qjApprove',1500,800,false);
        } else if (layEvent === 'approve'){
            xadmin.open('流程记录','/workflow/qjApprove?taskId=' + data.taskId,1500,800,true);
        }

        // } else if (layEvent === 'del') { //删除
        //     layer.confirm('真的删除行么', function (index) {
        //         obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
        //         layer.close(index);
        //         //向服务端发送删除指令
        //     });
        // }
    });
});