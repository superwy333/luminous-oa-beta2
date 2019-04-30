layui.use(['table', 'jquery'], function () {
    var table = layui.table;
    var tableIns = table.render({
        elem: '#approveDetailTable'
        , method: 'get'
        // , contentType: 'application/json'
        // , height: 312
        , url: '/json/approveDetailTable.json' //数据接口
        // , page: true //开启分页
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'node', title: '节点名称', minWidth: 150, fixed: 'left'},
            {field: 'approver', title: '审批人', minWidth: 150, fixed: 'left'},
            {field: 'approveResult', title: '审批结果', minWidth: 150, fixed: 'left'},
            {field: 'approveDetail', title: '审批意见', minWidth: 150, fixed: 'left'},
            {field: 'approveTime', title: '审批时间', minWidth: 150, fixed: 'left'}
        ]]
    });
});