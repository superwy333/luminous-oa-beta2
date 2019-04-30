layui.use(['table', 'jquery'], function () {
    var table = layui.table;
    var tableIns = table.render({
        elem: '#taskTodo'
        , method: 'post'
        , contentType: 'application/json'
        , height: 'full-200'
        , url: '/workflow/taskToDo' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'taskId', title: 'taskId', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'procInstId', title: 'procInstId', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'procDefId', title: 'procDefId', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'applyName', title: '填报人', minWidth: 150, sort: true}
            , {
                field: 'bizKey', title: '任务类型', minWidth: 150, sort: true,
                templet: function (d) {
                    if (d.bizKey == 'qj') {
                        return '请假申请';
                    } else {
                        return '其他';
                    }
                }
            }
            , {field: 'applyTime', title: '填报时间', minWidth: 150, sort: true}
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
    table.on('tool(test)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //查看
            //do somehing
        } else if (layEvent === 'del') { //删除
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if (layEvent === 'edit') { //编辑
            //do something

            //同步更新缓存对应的值
            obj.update({
                username: '123'
                , title: 'xxx'
            });
        }
    });
})