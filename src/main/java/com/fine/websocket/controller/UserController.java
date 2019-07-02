package com.fine.websocket.controller;

import com.fine.websocket.domain.User;
import com.fine.websocket.repository.UserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册登录控制
 * 前端传注册传用户名和密码，后端返回json：注册结果成功或失败
 */


@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserRepository repository;
    private LocalDateTime localDateTime = LocalDateTime.now();

    Gson gson = new Gson();
    //查找用户是否存在
    @PostMapping("/find")
    public User find(@RequestParam(value = "username") String username) {
        return repository.findByUsername(username);
    }

    //注册
    @PostMapping("/register")
    public Map register(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password) {
        Map<String, String> map = new HashMap<>();
        if (find(username) != null) {
            map.put("result","fail");
        } else {
            User user = new User();
            user.setCreate_data(localDateTime);
            user.setUsername(username);
            user.setPassword(password);
            repository.save(user);
            map.put("result", "success");
        }
        return map;
    }

    //登录
    @PostMapping("/login")
    public Map login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password) {
        Map<String, String> map = new HashMap<>();
        if (repository.findByUsernameAndPassword(username, password) != null) {
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        return map;
    }
}
