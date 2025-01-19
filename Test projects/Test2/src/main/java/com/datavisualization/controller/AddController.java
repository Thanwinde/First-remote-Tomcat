package com.datavisualization.controller;
import com.alibaba.fastjson.JSONObject;
import com.datavisualization.domain.TestUser;
import com.datavisualization.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@Transactional
public class AddController {
    @Autowired
    private MainMapper mainMapper;

    @GetMapping("/add")
    ResponseEntity<?> add(String name, int age) {
        TestUser user = new TestUser();
        user.setAge(age);
        user.setName(name);
        mainMapper.insert(user);
        JSONObject json = new JSONObject();
        json.put("users", user);
        return ResponseEntity.ok(json);
    }

}
