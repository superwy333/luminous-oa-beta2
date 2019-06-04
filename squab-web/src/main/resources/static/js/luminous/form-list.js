layui.use(['table', 'jquery'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var tableIns = table.render({
        elem: '#formList'
        , method: 'post'
        , contentType: 'application/json'
        // , height: 'full-200'
        , url: '/dynamicForm/formList' //数据接口
        , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'formCode', title: '表单代码', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'formName', title: '表单名称', minWidth: 150, sort: true}
            , {field: 'createTime', title: '创建时间', minWidth: 150, sort: true}
            , {field: 'operation', title: '操作', minWidth: 150, toolbar: '#formListOperation'}
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
    table.on('tool(formList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'edit') {
            xadmin.open('新建表单','/dynamicForm/edit?formId=' + data.id,600,400,true)
        } else if (layEvent === 'del') {
            var reqData = {
                bizKey: 'delModel',
                data: {
                    id: data.id
                }
            };
            $.ajax({
                url: '/modeler/deleteModel',
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                data: JSON.stringify(reqData),
                success: function (data) {
                    if (data.code == 0) {
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