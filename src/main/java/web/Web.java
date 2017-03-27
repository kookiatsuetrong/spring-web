package web;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
@Controller
class Web {
	@RequestMapping("/")
	String showHome() {
		return "index";
	}
	@RequestMapping("/login")
	String showLogInPage() {
		return "login";
	}
	@RequestMapping("/register")
	String showRegisterPage() {
		return "register";
	}
	@RequestMapping("/profile")
	String showProfilePage() {
		return "profile";
	}
	@RequestMapping("/logout")
	String showLogOutPage() {
		return "logout";
	}
}
