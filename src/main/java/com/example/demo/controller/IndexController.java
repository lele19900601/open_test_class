package com.example.demo.controller;

import com.example.demo.common.ResponseJson;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRoleEntity;
import com.example.demo.entity.dto.RoleMenuEntityDto;
import com.example.demo.entity.dto.loginDto;
import com.example.demo.service.RoleMenuService;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
* 首页
* */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 跳转用户登陆页面
     * @return
     */
    @GetMapping(value = "/")
    public String loginJump(){
        return "index";
    }

    /**
     * 跳转用户登陆页面错误页面
     * @return
     */
    @GetMapping(value = "/login")
    public String loginJumpError(){
        return "index";
    }

    /**
     * 用户登陆
     * @return
     */
    @PostMapping(value = "/login")
    public String login(HttpSession session,Model model, loginDto loginDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserAccount(loginDto.getUsername());
        userEntity.setUserPassport(loginDto.getPassword());
        ResponseJson result = userService.findByUserAccount(userEntity);
        if(result.getStatus() != 200) {
            model.addAttribute("error",result.getDesc());
            model.addAttribute("title","用户登陆");
            model.addAttribute("userInfo",userEntity);
            return "index";
        }
        UserEntity userEntityDo = (UserEntity)result.getData();
        session.setAttribute("userId",userEntityDo.getId());
        //根据用户Id查询对应的roleId
        UserRoleEntity userRoleEntity = userRoleService.getRoleIdByUserId(userEntityDo.getId());
        //根据角色roleId查询对应的菜单
        List<RoleMenuEntityDto> menuEntityList = roleMenuService.listMenus(userRoleEntity);
        model.addAttribute("menuList",menuEntityList);
        return "home/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if(!StringUtils.isEmpty(session.getAttribute("userId"))) {
            String userId = session.getAttribute("userId").toString();
            session.removeAttribute(userId);
        }
        return "index";
    }

    @GetMapping("/error403")
    public String error403() {
        return "403";
    }

    @GetMapping("/error500")
    public String error500() {
        return "500";
    }
}
