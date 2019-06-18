layui.use(['table', 'jquery', 'laydate'], function () {
    var laydate = layui.laydate;
    laydate.render({
        elem: '#start'
    });
    laydate.render({
        elem: '#end'
    });
    var table = layui.table;
    var tableIns = table.render({
        elem: '#sysUserList'
        , method: 'post'
        , contentType: 'application/json'
        // , height: 'full-200'
        , url: '/sysUser/sysUserList' //数据接口
        , page: true //开启分页
        , limit: 10
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true}
            , {field: 'name', title: '职员名称', minWidth: 150, sort: true}
            , {field: 'userCode', title: '工号', minWidth: 150, sort: true}
            , {field: 'deptName', title: '部门', minWidth: 150, sort: true}
            , {field: 'postName', title: '岗位', minWidth: 150, sort: true}
            , {field: 'operation', title: '操作', minWidth: 150, toolbar: '#sysUserListOperation'}
        ]]
    });


    $('#reload').click(function () {
        tableIns.reload(
            {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    data: {
                        start: $('#start').val(),
                        end: $('#end').val(),
                        taskNo: $('#taskNo').val(),
                        bizKey: $('#bizKey').val(),
                        taskState: $('#taskState').val()
                    }

                }
            }
        );
        return false
    });

    //监听工具条
    table.on('tool(sysUserList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //流转记录
            xadmin.open('详情', '/workflow/detail?id=' + data.id + '&type=1&bizKey=' + data.bizKey, 1600, 800, false);
        } else if (layEvent === 'edit') {
            xadmin.open('编辑', '/workflow/detail?id=' + data.id + '&type=2', 1600, 800, true);
        } else if (layEvent === 'cancel') {
            layer.confirm('确定撤回申请？', function (index) {
                var reqData = {
                    bizKey: 'cancelProcess',
                    data: {
                        id: data.id
                    }
                };
                $.ajax({
                    url: '/workflow/cancelProcess',
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
            })
        } else if (layEvent === 'start') {
            layer.confirm('确定发起流程？', function (index) {
                var reqData = {
                    bizKey: 'startProcess',
                    oaTaskId: data.id
                };
                $.ajax({
                    url: '/workflow/startProcess',
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
            })
        } else if (layEvent === 'print') {
            xadmin.open('详情', '/workflow/detail?id=' + data.id + '&type=3&bizKey=' + data.bizKey, 1600, 800, true);
        }
    });
});