package cn.luminous.squab.controller.auth;

import cn.luminous.squab.controller.BaseController;
import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import cn.luminous.squab.model.PostsModel;
import cn.luminous.squab.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/posts")
@Slf4j
public class PostController extends BaseController {

    @Autowired
    private PostsService postsService;

    @GetMapping("/postsList")
    public ModelAndView toPostsList() {
        ModelAndView m = new ModelAndView();
        m.setViewName("posts-list");
        return m;
    }


    @PostMapping("/postsList")
    @ResponseBody
    public String postsList(@RequestBody Rq rq) {
        List<PostsModel> postsModelList = null;
        Integer queryCount = null;
        try {
            Map<String, Object> condition = parseListQueryCondition(rq);
            postsModelList = postsService.queryPostsPage(condition);
            condition.put("page", null);
            condition.put("limit", null);
            queryCount = postsService.queryPostsPage(condition).size();
        } catch (Exception e) {
            log.error("岗位列表查询异常：", e);
            R.nok(e.getMessage());
        }
        return R.ok(postsModelList, queryCount);
    }

}
