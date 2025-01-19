package com.datavisualization.controller;
import com.alibaba.fastjson.JSONObject;
import com.datavisualization.domain.TestUser;
import com.datavisualization.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;
@Controller
@Transactional
public class SearchController {
    @Autowired
    private MainMapper mainMapper;
    @RequestMapping("/search")
    ResponseEntity<?> search(){
        System.out.println("search");
        List<TestUser> users = new ArrayList<>();
        JSONObject json = new JSONObject();
        users = mainMapper.selectList(null);
        System.out.println(users);
        json.put("users", users);
        return ResponseEntity.ok(json);
    }
}
