package cn.luminous.squab.controller.activiti;


import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("/deploy")
@Controller
@Slf4j
public class DeployController {

    @Autowired
    RepositoryService repositoryService;

    @RequestMapping("/deployList")
    public String toModelList() {
        return "deploy-list";
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
