package blog.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.model.entity.UserEntity;
import blog.com.service.UserService;

@Controller
@RequestMapping("/user")
public class UserRegisterController {
	@Autowired
	private UserService userService; // 注入服务类

	// 处理用户登录请求
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam String username, @RequestParam String password) {
	    // 调用UserService的登录方法来处理用户登录逻辑
	    UserEntity loggedInUser = userService.checkLogin(username, password);
	    // 返回视图或重定向到其他页面
	    if (loggedInUser != null) {
	        // 登录成功，可以根据需求返回成功页面或重定向到其他页面
	        return "product_list.html"; // 返回登录成功页面
	    } else {
	        // 登录失败，可以返回登录失败页面或重定向到其他页面
	        return "register.html"; // 返回登录失败页面
	    }
	}}
