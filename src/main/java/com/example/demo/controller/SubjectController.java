package com.example.demo.controller;

import com.example.demo.entity.SubjectEntity;
import com.example.demo.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
* 科目controller
* */
@Controller
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 科目列表
     * @return
     */
    @Secured("ROLE_TEACHER")
    @GetMapping(value = "")
    public String listSubjectEntity(Model model){

        model.addAttribute("subjectList",subjectService.list());
        model.addAttribute("title","科目管理");
        return "subject/list";
    }

    /**
     * 删除科目
     * @return
     */
    @Secured("ROLE_TEACHER")
    @GetMapping(value = "/del/{id}")
    public String deleteSubeject(@PathVariable("id") String id){
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(Long.valueOf(id));
        subjectService.delete(subjectEntity);
        return "redirect:/subject";
    }

    /**
     * 创建表单
     * @return
     */
    @Secured("ROLE_TEACHER")
    @GetMapping(value = "/form")
    public String createForm(Model model){
        model.addAttribute("title","科目管理");
        model.addAttribute("subject",new SubjectEntity());
        return "subject/add";
    }

    /**
     * 添加科目
     * @return
     */
    @Secured("ROLE_TEACHER")
    @PostMapping(value = "")
    public String saveSubeject(Model model, SubjectEntity subjectEntity){
        if(StringUtils.isEmpty(subjectEntity.getSubjectName())) {
            model.addAttribute("title","科目管理");
            model.addAttribute("error","科目名称不能为空");
            model.addAttribute("subject",subjectEntity);
            return "subject/add";
        }
        subjectService.save(subjectEntity);
        return "redirect:/subject";
    }

    @Secured("ROLE_TEACHER")
    @GetMapping("/modify/{id}")
    public String modifySubject(@PathVariable("id") String id,Model model) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(Long.valueOf(id));
        model.addAttribute("subject",subjectService.getDetail(subjectEntity));
        model.addAttribute("title", "编辑科目");
        return "subject/modify";
    }

    /**
     * 科目详情
     * @return
     */
    @Secured("ROLE_TEACHER")
    @GetMapping(value = "/one/{id}")
    public String getSubeject(@PathVariable("id") String id,Model model){
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(Long.valueOf(id));
        model.addAttribute("subject",subjectService.getDetail(subjectEntity));
        model.addAttribute("title", "科目详情");
        return "subject/view";
    }

}
