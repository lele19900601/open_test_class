package com.example.demo;

import com.example.demo.dao.*;
import com.example.demo.entity.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

@ComponentScan(value = {
		"com.example.demo.config",
		"com.example.demo.advice",
		"com.example.demo.common",
		"com.example.demo.controller",
		"com.example.demo.dao",
		"com.example.demo.entity",
		"com.example.demo.service"
})
@SpringBootApplication
public class DemoApplication {


	@Bean
	InitializingBean saveData(UserRepository repo, RoleRepository roleRepository, MenuRepository menuRepository, RoleMenuRepository roleMenuRepository, UserRoleRepository userRoleRepository){
		return ()->{
			Optional<UserEntity> adminUser = repo.findByUserAccount("Admin");
			UserEntity userEntity = new UserEntity();
			if(!adminUser.isPresent()) {
				//初始化用户Admin
				userEntity = repo.save(new UserEntity((long)1,null,"乐乐","Admin", "$2a$10$ATWImq90bcRDO8PZ4jjQTe4M/nkGBSzwoizErZIv/0PTXOopu8jmm",0));
			}
			//初始化角色Admin
			List<RoleEntity> roleEntityList = roleRepository.findAll();
			RoleMenuEntity roleMenuEntity1 = new RoleMenuEntity();
			RoleMenuEntity roleMenuEntity2 = new RoleMenuEntity();
			RoleMenuEntity roleMenuEntity3 = new RoleMenuEntity();
			if(roleEntityList==null || roleEntityList.size() == 0) {
				RoleEntity roleEntityAdmin = roleRepository.save(new RoleEntity((long)555,"ROLE_ADMIN","超级管理员"));
				roleMenuEntity1.setRoleId(roleEntityAdmin.getId());
				//初始化Admin用户对应的角色
				userRoleRepository.save(new UserRoleEntity((long)1,userEntity.getId(),roleEntityAdmin.getId()));
				//初始化Teacher角色
				RoleEntity roleEntityTeacher = roleRepository.save(new RoleEntity((long)2,"ROLE_TEACHER","教师"));
				roleMenuEntity2.setRoleId(roleEntityTeacher.getId());
				//初始化Student角色
				RoleEntity roleEntityStudent = roleRepository.save(new RoleEntity((long)3,"ROLE_STUDENT","学生"));
				roleMenuEntity3.setRoleId(roleEntityStudent.getId());
			}
//			//初始化菜单
			List<MenuEntity> menuEntityList = menuRepository.findAll();
			if(menuEntityList == null || menuEntityList.size() == 0) {
				MenuEntity menuEntityUser = menuRepository.save(new MenuEntity((long)1,null,null,"账户管理",null,"/user",null,null));
				roleMenuEntity1.setMenuId(menuEntityUser.getId());
				MenuEntity menuEntitySubject = menuRepository.save(new MenuEntity((long)2,null,null,"课程管理",null,"/subject",null,null));
				roleMenuEntity2.setMenuId(menuEntitySubject.getId());
				MenuEntity menuEntitySubscribe = menuRepository.save(new MenuEntity((long)3,null,null,"订阅管理",null,"/subscribe",null,null));
				roleMenuEntity3.setMenuId(menuEntitySubscribe.getId());
				//初始化角色对应的菜单
				roleMenuRepository.save(roleMenuEntity1);
				roleMenuRepository.save(roleMenuEntity2);
				roleMenuRepository.save(roleMenuEntity3);
			}

		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
