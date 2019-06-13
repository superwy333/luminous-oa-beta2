package cn.luminous.squab.controller.notice;


import cn.luminous.squab.entity.http.R;
import cn.luminous.squab.entity.http.Rq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/notice")
@Controller
@Slf4j
public class NoticeController {

    /**
     * 首页显示的通知公告列表
     *
     * @param rq
     * @return
     */
    @PostMapping("/noticeList")
    @ResponseBody
    public String noticeList(@RequestBody Rq rq) {
        Map<String, Object> result;
        List<Map<String, String>> data;
        try {
            result = new HashMap<>();
            result.put("code", 0);
            result.put("success", true);
            data = new ArrayList<>();
            for (int i = 0; i < 18; i++) {
                Map<String, String> dataItem = new HashMap<>();
                dataItem.put("title", "关于XXXXXX的通知");
                dataItem.put("auth", "管理员");
                dataItem.put("time", "2019-06-13");
                data.add(dataItem);
            }
            result.put("data", data);
        } catch (Exception e) {
            return R.nok(e.getMessage());
        }
        return R.ok(data, 18);
    }


}
