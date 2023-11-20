package blog.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.model.entity.UserEntity;
import blog.com.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserLoginController {
	@Autowired
	private UserService userService;
	
	//Sessionがつかえるように宣言
	@Autowired
	private HttpSession session;
	
	//ログイン画面の表示

	@GetMapping("/login")
    public String getLoginPage() {
		return "login.html";
	}
	
	//ログイン処理
	@PostMapping("/login/process")
	public String login(@RequestParam String adminEmail,@RequestParam String password) {
		//Serviceクラスのメソッドを使ってログインしている人の情報を獲得して変数に格納する
		UserEntity admin = userService.checkLogin(adminEmail, password);
		if(admin == null) {
			return "admin_login.html";
		}else {
			//sessionを使ってログインしている人の情報を保存する
			session.setAttribute("admin", admin);
			return "redirect:/product/list";
		}
		
	}
//logout
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login";
	}
	
}