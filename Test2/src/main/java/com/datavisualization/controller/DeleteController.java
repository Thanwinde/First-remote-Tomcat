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
public class DeleteController {
    @Autowired
    private MainMapper mainMapper;

    @GetMapping("/del")
    ResponseEntity<?> add(Long id) {
        int i = mainMapper.deleteById(id);
        if(i == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok("成功");
    }
}
