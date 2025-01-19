package com.datavisualization.controller;

import com.datavisualization.domain.TestUser;
import com.datavisualization.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Transactional
public class UpdateController {
    @Autowired
    private MainMapper mainMapper;
    @GetMapping("/update")
    ResponseEntity<?> update(String id, String name, int age) {
        TestUser user = new TestUser();
        user.setId(id);
        user.setAge(age);
        user.setName(name);
        int i =mainMapper.updateById(user);
        if(i == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok("成功");
    }

}
