package com.Demo;
import com.Servlet.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class controller {
    @GetMapping("/show")
        public String show(User user){
        System.out.println(user);
            return "/index.jsp";
        }
    @PostMapping("/show1")
        public String show1(String name,int age){
        System.out.println(name + " " + age);
        return "/index.jsp";
    }
    @PostMapping("/show2")
        public String show2(@RequestBody JSONObject json) {
        System.out.println(json);
        User user = json.toJavaObject(User.class);
        return "/index.jsp";
    }
    @PostMapping("/show3")
    public String show3(@RequestBody User user){
        System.out.println(user);
        return "/index.jsp";
    }
    @GetMapping("/{id}/{name}")
    public String show4(@PathVariable("id") int id, @PathVariable("name") String name){
        System.out.println(id + " " + name);
        return "/index.jsp";
    }
    @PostMapping("temp")
    ResponseEntity<?> temp(@RequestParam Integer password){
        if(password != 123)
            return ResponseEntity.badRequest().body("密码错误");
        User user = new User();
        user.setAge(18);
        user.setName("temp");
        user.setGender("男");
        return ResponseEntity.ok(user);
    }
    @GetMapping("temp2")
    ResponseEntity<?> temp2( Integer password){
        if(password != 123)
            return ResponseEntity.badRequest().body("密码错误");
        User user = new User();
        user.setAge(18);
        user.setName("temp");
        user.setGender("男");
        return ResponseEntity.ok(user);
    }
}
