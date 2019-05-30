var leipiEditor = UE.getEditor('myFormDesign', {
    //allowDivTransToP: false,//阻止转换div 为p
    toolleipi: true,//是否显示，设计器的 toolbars
    textarea: 'design_content',
    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
    toolbars: [[
        'fullscreen', 'source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|', 'fontfamily', 'fontsize', '|', 'indent', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'link', 'unlink', '|', 'horizontal', 'spechars', 'wordimage', '|', 'inserttable', 'deletetable', 'mergecells', 'splittocells']],
    //focus时自动清空初始化时的内容
    //autoClearinitialContent:true,
    //关闭字数统计
    wordCount: false,
    //关闭elementPath
    elementPathEnabled: false,
    //默认的编辑区域高度
    initialFrameHeight: 300
    //,iframeCssUrl:"css/bootstrap/css/bootstrap.css" //引入自身 css使编辑器兼容你网站css
    //更多其他参数，请参考ueditor.config.js中的配置项
});

var leipiFormDesign = {
    /*执行控件*/
    exec: function (method) {
        leipiEditor.execCommand(method);
    }
};


$('#preview').click(function () {
    if (leipiEditor.queryCommandState('source'))
        leipiEditor.execCommand('source');/*切换到编辑模式才提交，否则部分浏览器有bug*/

    if (leipiEditor.hasContents()) {
        leipiEditor.sync();       /*同步内容*/

        //获取表单设计器里的内容
        formeditor = leipiEditor.getContent();

        $('#formeditor').val(formeditor);
        /*设计form的target 然后提交至一个新的窗口进行预览*/
        document.saveform.target = "mywin";
        window.open('', 'mywin', "menubar=0,toolbar=0,status=0,resizable=1,left=0,top=0,scrollbars=1,width=" + (screen.availWidth - 10) + ",height=" + (screen.availHeight - 50) + "\"");

        document.saveform.action = "/dynamicForm/previewEditForm";
        document.saveform.submit(); //提交表单
    } else {
        alert('表单内容不能为空！');
        return false;
    }

});

$('#save').click(function () {
    if (leipiEditor.queryCommandState('source'))
        leipiEditor.execCommand('source');//切换到编辑模式才提交，否则有bug

    if (leipiEditor.hasContents()) {
        leipiEditor.sync();/*同步内容*/
        //获取表单设计器里的内容
        formeditor = leipiEditor.getContent();

        var form_name = $("#form_name").val();
        if (!form_name) {
            alert('表单名称不能为空!');
            return false;
        }
        var form_code = $("#form_code").val();
        if (!form_code) {
            alert('表单代码不能为空!');
            return false;
        }
        var form_id = $("#form_id").val();

        //emplate , String form_name , Integer form_id

        // 组装数据
        var rqData = {
            bizKey: 'DynamicFormAddOrUpdate',
            data: {
                id: form_id,
                formName: form_name,
                formCode: form_code,
                template: formeditor
            }
        };

        //异步提交数据
        $.ajax({
            type: 'POST',
            url: '/dynamicForm/saveOrUpdateForm',
            data: JSON.stringify(rqData),
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            // data : {'template': formeditor , 'form_name' :form_name ,'form_id' : form_id  },
            success: function (data) {
                if (data.success) {
                    alert('操作成功');
                } else {
                    alert('保存失败！');
                    // if (data.status == 0) {
                    //     alert('保存成功');
                    //
                    //     //判断是否在iframe中 如果是 则找到上一级方法关闭dialog
                    //     if (parent) {
                    //         parent.closeEdit();
                    //     }
                    // } else {
                    //     alert('保存失败！');
                    // }
                }
            }
        });
    } else {
        alert('表单内容不能为空！')
        //$('#submitbtn').button('reset');
        return false;
    }

});
