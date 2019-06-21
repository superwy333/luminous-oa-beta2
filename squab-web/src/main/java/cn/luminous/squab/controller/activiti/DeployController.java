package cn.luminous.squab.controller.activiti;


import cn.luminous.squab.entity.BizMapping;
import cn.luminous.squab.entity.BizMappingAuth;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.service.ActivitiService;
import cn.luminous.squab.service.BizMappingAuthService;
import cn.luminous.squab.service.BizMappingService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private BizMappingService bizMappingService;
    @Autowired
    private BizMappingAuthService bizMappingAuthService;

    @RequestMapping("/deployList")
    public String toModelList() {
        return "deploy-list";
    }

    @RequestMapping("/deployDiagram")
    public String toDeployDiagram(@RequestParam(name = "deployId") String deployId,
                                  Model model) {
        String imgSrc = "/deploy/getDiagram?id=" + deployId;
        model.addAttribute("imgSrc", imgSrc);
//        model.addAttribute("deployId",deployId);
        return "deploy-diagram";
    }

    @RequestMapping(value = "/getDiagram", method = RequestMethod.GET)
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
        } catch (Exception e) {
            log.error("获取流程图失败，原因：" + e);
        } finally {
            try {
                if (imageStream != null) {
                    imageStream.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流失败，原因：" + e);
            }
        }
    }


    @PostMapping("/deployList")
    @ResponseBody
    public String deployList(@RequestBody Rq rq) {
        List<Deployment> deploymentList;
        Long count;
        try {
            log.debug("【查询部署开始】入参: " + rq.toString());
            // TODO 分页的处理
            Integer page = rq.getPage();
            Integer limit = rq.getLimit();
            deploymentList = repositoryService.createDeploymentQuery()
                    .listPage(limit * (page - 1), limit);
            count = repositoryService.createDeploymentQuery().count();
        } catch (Exception e) {
            log.error("【查询部署失败】入参: " + rq.toString(), e);
            return R.nok(e.getMessage());
        }
        return R.ok(deploymentList, count.intValue());
    }

    @PostMapping("/deleteDeploy")
    @ResponseBody
    public String deleteDeploy(@RequestBody Rq rq) {
        try {
            Map<String, String> data = (Map<String, String>) rq.getData();
            String deplotmentId = data.get("id");

            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .deploymentId(deplotmentId)
                    .singleResult();

            String processKey = processDefinition.getKey();

            List<ProcessDefinition> processDefinitionList = repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionKey(processKey)
                    .list();

            if (processDefinitionList.size() <= 1) { // 如果有相同的key的流程数量不止一个，则不删除bizMapping
                BizMapping bizMapping = new BizMapping();
                bizMapping.setProcessKey(processKey);
                bizMapping = bizMappingService.queryOne(bizMapping);
                if (bizMapping != null) {
                    bizMappingService.remove(bizMapping);
                    // 同步删除授权
                    BizMappingAuth bizMappingAuth = new BizMappingAuth();
                    bizMappingAuth.setBizMappingId(bizMapping.getId());
                    List<BizMappingAuth> bizMappingAuthList = bizMappingAuthService.query(bizMappingAuth);
                    bizMappingAuthList.stream().forEach(bizMappingAuth1 -> {
                        bizMappingAuthService.remove(bizMappingAuth1);
                    });
                }
            }
            repositoryService.deleteDeployment(deplotmentId);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok();

    }


}
