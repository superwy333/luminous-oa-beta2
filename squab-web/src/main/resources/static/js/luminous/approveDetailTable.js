layui.use(['table', 'jquery'], function () {
    var $ = layui.jquery;
    var oaTaskId = $("#oaTaskId").val();
    var table = layui.table;
    var tableIns = table.render({
        elem: '#approveDetailTable'
        , method: 'POST'
        , contentType: 'application/json'
        , where: {
            data: {
                oaTaskId: oaTaskId
            }
        }
        , page: false
        , url: '/workflow/approveDetails' //数据接口
        , cols: [[ //表头
            {field: 'id', title: 'ID', minWidth: 150, sort: true, fixed: 'left', hide: true},
            {field: 'nodeName', title: '节点名称', minWidth: 150, fixed: 'left'},
            {field: 'approver', title: '审批人', minWidth: 150, fixed: 'left'},
            {field: 'approveResult', title: '审批结果', minWidth: 150, fixed: 'left',
                templet: function (d) {
                    if (d.approveResult == '0') {
                        return '同意';
                    } else if (d.approveResult == '1') {
                        return '拒绝';
                    }
                }
            },
            {field: 'approveContent', title: '审批意见', minWidth: 150, fixed: 'left'},
            {field: 'approveTime', title: '审批时间', minWidth: 150, fixed: 'left'}
        ]]
    });
});