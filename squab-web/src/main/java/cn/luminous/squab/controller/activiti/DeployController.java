package cn.luminous.squab.controller.activiti;


import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.service.ActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RequestMapping("/deploy")
@Controller
@Slf4j
public class DeployController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ActivitiService activitiService;

    @RequestMapping("/deployList")
    public String toModelList() {
        return "deploy-list";
    }

    @RequestMapping("/deployDiagram")
    public String toDeployDiagram(@RequestParam(name = "deployId") String deployId,
                                  Model model) {
        String imgSrc = "/deploy/getDiagram?id=" + deployId;
        model.addAttribute("imgSrc",imgSrc);
//        model.addAttribute("deployId",deployId);
        return "deploy-diagram";
    }

    @RequestMapping(value = "/getDiagram",method = RequestMethod.GET)
    public void getDiagram(@RequestParam(name = "id") String id,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        InputStream imageStream = null;
        try {
            imageStream = activitiService.getDiagramOrgin(id);
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        }catch (Exception e) {
            log.error("获取流程图失败，原因："+ e);
        }finally {
            try {
                if (imageStream!=null) {
                    imageStream.close();
                }
            }catch (IOException e) {
                log.error("关闭输入流失败，原因："+ e);
            }
        }
    }


    @PostMapping("/deployList")
    @ResponseBody
    public String deployList(@RequestBody Rq rq) {
        List<Deployment> deploymentList;
        try {
            log.debug("【查询部署开始】入参: " + rq.toString());
            // TODO 分页的处理
            Integer page = 1;
            Integer limit = 10;
            deploymentList = repositoryService.createDeploymentQuery()
                    .listPage(limit * (page - 1), limit);
            long count = repositoryService.createDeploymentQuery().count();
        }catch (Exception e) {
            log.error("【查询部署失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(deploymentList);
    }

    @PostMapping("/deleteDeploy")
    @ResponseBody
    public String deleteDeploy(@RequestBody Rq rq) {
        try {
            Map<String,String> data = (Map<String,String>) rq.getData();
            repositoryService.deleteDeployment(data.get("id"));
        }catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();

    }




}
