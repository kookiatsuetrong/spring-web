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
	@RequestMapping(value="/login", method=RequestMethod.POST)
	String checkLogIn(String email, String password) {
		boolean correct = false;
		if (email.equals("mark@fb.com") && 
			password.equals("mark123")) {
			correct = true;
		}
		if (correct) {
			return "redirect:/profile";
		} else {
			return "redirect:/login";
		}
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
