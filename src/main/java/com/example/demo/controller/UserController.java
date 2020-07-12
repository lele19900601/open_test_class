package com.example.demo.controller;

import com.example.demo.common.ResponseJson;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
* 用户controller
* */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "")
    public String listUser(Model model){
        model.addAttribute("userList",userService.list());
        model.addAttribute("title","用户管理");
        return "user/list";
    }

    /**
     * 删除用户
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/del/{id}")
    public String deleteUser(@PathVariable("id") String id){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(id));
        userService.delete(userEntity);
        return "redirect:/user";
    }

    /**
     * 创建表单
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/form")
    public String createForm(Model model){
        model.addAttribute("title","用户管理");
        model.addAttribute("user",new UserEntity());
        model.addAttribute("error",null);
        return "user/add";
    }

    /**
     * 添加用户
     * @return
     */
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "")
    public String saveUser(UserEntity userEntity,Model model){
        ResponseJson responseJson = userService.save(userEntity);
        if(responseJson.getStatus() == 500) {
            model.addAttribute("title","用户管理");
            model.addAttribute("error",responseJson.getDesc());
            model.addAttribute("user",userEntity);
            return "user/add";
        }
        return "redirect:/user";
    }

    /**
     * 修改用户
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/modify/{id}")
    public String modifySubject(@PathVariable("id") String id,Model model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(id));
        model.addAttribute("user",userService.getDetail(userEntity));
        model.addAttribute("title", "编辑用户");
        return "user/modify";
    }

    /**
     * 用户详情
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/one/{id}")
    public String getUser(@PathVariable("id") String id,Model model){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.valueOf(id));
        model.addAttribute("user",userService.getDetail(userEntity));
        model.addAttribute("title", "用户详情");
        return "user/view";
    }



    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hash = bCryptPasswordEncoder.encode("Admin");
        System.out.println(hash);
        System.out.println(bCryptPasswordEncoder.matches("123456",hash));
        //$2a$10$kdrwm2ZQD6b5Ihix3/426epBTVJikpJmoQjLBCslkNr9ZzDEK9yIy
    }

}
