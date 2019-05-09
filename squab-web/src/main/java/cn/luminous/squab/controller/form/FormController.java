package cn.luminous.squab.controller.form;


import cn.hutool.core.bean.BeanUtil;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.form.entity.DynamicForm;
import cn.luminous.squab.form.service.DynamicFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/form")
@Slf4j
public class FormController {

    @Autowired
    private DynamicFormService dynamicFormService;


    @RequestMapping("/bxApply")
    public String toBxApply() {
        return "form/bx/bx_apply";
    }



    @RequestMapping("/edit")
    public String toFormEdit(@RequestParam(value = "formId", required = false) Long formId, Model model) {
        try {
            DynamicForm dynamicForm = dynamicFormService.queryById(formId);
            model.addAttribute("id",dynamicForm.getId());
            model.addAttribute("formName",dynamicForm.getFormName());
            model.addAttribute("formCode",dynamicForm.getFormCode());
            model.addAttribute("html",dynamicForm.getFormHtml());
        }catch (Exception e) {
            // TODO return 404
            log.error("【跳转表单编辑器失败】" + e);
        }
        return "form/form_edit2";
    }

    @PostMapping("/saveOrUpdateForm")
    @ResponseBody
    @CrossOrigin
    public String saveOrUpdateForm(@RequestBody Rq rq) {
        try {
            log.debug("【动态表单保存】入参: " + rq.toString());
            Map<String,Object> data = (Map<String,Object>) rq.getData();
            String idStr = (String)data.get("id");
            if (BeanUtil.isEmpty(idStr) || idStr.length()<=0) { // 新增
                DynamicForm dynamicForm = new DynamicForm();
                dynamicForm.setFormName((String) data.get("formName"));
                dynamicForm.setFormName((String) data.get("formCode"));
                dynamicForm.setFormHtml((String) data.get("template"));
                dynamicFormService.add(dynamicForm);
            }else { // 更新
                DynamicForm dynamicForm = dynamicFormService.queryById(Long.valueOf(idStr));
                dynamicForm.setFormName((String) data.get("formName"));
                dynamicForm.setFormName((String) data.get("formCode"));
                dynamicForm.setFormHtml((String) data.get("template"));
                dynamicFormService.updateByIdSelective(dynamicForm);
            }
        }catch (Exception e) {
            log.error("【动态表单保存失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok();
    }


    @RequestMapping("/previewEditForm")
    public String previewEditForm(String formeditor , Integer form_id, Model model) {
        long id = 12;
        DynamicForm dynamicForm = dynamicFormService.queryById(id);
        model.addAttribute("html", dynamicForm.getFormHtml());
        model.addAttribute("name","哈哈老王");

        return "form/form_preview";
    }




    /**
     * 通过流的方式上传文件
     * 测试文件上传功能
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    @CrossOrigin
    public String  fileUpload(@RequestParam("file") MultipartFile file) throws IOException {


        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());

        try {
            //获取输出流
            OutputStream os=new FileOutputStream("D:/"+new Date().getTime()+file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return R.nok();
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return R.ok();
    }








}
