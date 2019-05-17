layui.use(['table', 'jquery'], function () {
    var table = layui.table;
    var tableIns = table.render({
        elem: '#myTask'
        , method: 'post'
        , contentType: 'application/json'
        // , height: 'full-200'
        , url: '/workflow/myTaskList' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'procInstId', title: 'procInstId', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'procDefId', title: 'procDefId', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'taskNo', title: '申请编号', minWidth: 150, sort: true}
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
            , {field: 'taskState', title: '任务状态', minWidth: 150, sort: true,
                templet: function (d) {
                    if (d.taskState == '0') {
                        return '流程中';
                    } else if (d.taskState == '1') {
                        return '通过';
                    } else if (d.taskState == '2') {
                        return '驳回';
                    }
                }
            }
            , {field: 'assignee', title: '当前指派人', minWidth: 150, sort: true}
            , {field: 'operation', title: '操作', minWidth: 150, toolbar: '#myTaskOperation'}
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
    table.on('tool(myTask)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //流转记录
            xadmin.open('详情','/workflow/detail?id=' + data.id + '&type=1',1500,800,false);
        } else if (layEvent === 'edit'){
            xadmin.open('编辑','/workflow/detail?id=' + data.id + '&type=2',1500,800,true);
        }
    });
});