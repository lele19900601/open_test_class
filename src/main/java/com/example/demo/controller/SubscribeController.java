package com.example.demo.controller;

import com.example.demo.entity.SubscribeEntity;
import com.example.demo.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/*
* 订阅controller
* */
@Controller
@RequestMapping(value = "/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    /**
     * 订阅列表
     * @return
     */
    @Secured("ROLE_STUDENT")
    @GetMapping(value = "")
    public String listSubscribe(HttpSession session, Model model){
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        if(StringUtils.isEmpty(session.getAttribute("userId")) ) {
            return "index";
        }
        String userId = String.valueOf(session.getAttribute("userId"));
        subscribeEntity.setUserId(userId);
        model.addAttribute("subscribeList",subscribeService.list(subscribeEntity));
        model.addAttribute("title","订阅管理");
        return "subscribe/list";
    }

    /**
     * 取消订阅
     * @return
     */
    @Secured("ROLE_STUDENT")
    @GetMapping(value = "/cancel/{id}")
    public String deleteSubscribe(@PathVariable("id") String id){
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        subscribeEntity.setId(Long.valueOf(id));
        subscribeService.delete(subscribeEntity);
        return "redirect:/subscribe";
    }

    /**
     * 订阅
     * @return
     */
    @Secured("ROLE_STUDENT")
    @GetMapping(value = "/order/{subjectId}")
    public String saveSubscribe(@PathVariable("subjectId") String subjectId, HttpSession session, SubscribeEntity subscribeEntity){
        if(StringUtils.isEmpty(session.getAttribute("userId")) ) {
            return "index";
        }
        String userId = String.valueOf(session.getAttribute("userId"));
        subscribeEntity.setUserId(userId);
        subscribeEntity.setSubjectId(subjectId);
        subscribeService.save(subscribeEntity);
        return "redirect:/subscribe";
    }

}
