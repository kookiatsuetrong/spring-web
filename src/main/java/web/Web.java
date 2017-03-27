package web;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
class Web {
	
	@RequestMapping("/")
	String showHome() {
		return "index";
	}
	
}
