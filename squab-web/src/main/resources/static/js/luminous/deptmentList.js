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
        elem: '#deptList'
        , method: 'post'
        , contentType: 'application/json'
        // , height: 'full-200'
        , url: '/department/deptList' //数据接口
        , page: true //开启分页
        , limit: 10
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'name', title: '部门名称', minWidth: 150, sort: true}
            , {field: 'parentDeptTxt', title: '上级部门', minWidth: 150, sort: true}
            , {field: 'leaderTxt', title: '主管领导', minWidth: 150, sort: true}
            , {field: 'leaderBranchTxt', title: '分管领导', minWidth: 150, sort: true}
            , {field: 'parentLeaderTxt', title: '上级部门领导', minWidth: 150, sort: true}
            , {field: 'operation', title: '操作', minWidth: 150, toolbar: '#deptListOperation'}
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
    table.on('tool(deptList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'edit') { //流转记录
            xadmin.open('编辑', '/department/edit?id=' + data.id, 480, 500, false);
        }
    });
});