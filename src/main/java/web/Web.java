package web;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
@Controller
class Web {
	Web() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) { }
	}
	
	String server = "jdbc:mysql://35.185.137.11/web";
	String user = "web";
	String password = "java";
	
	@RequestMapping("/test") @ResponseBody
	LinkedList showTest() {
		LinkedList list = new LinkedList();
		try {
			Connection c = DriverManager.getConnection(
				server, user, password);
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery(
				"select * from member");
			while (r.next()) {
				String email = r.getString("email");
				list.add(email);
			}
			r.close(); s.close(); c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
	
	@RequestMapping("/")
	String showHome() {
		return "index";
	}
	@RequestMapping("/login")
	String showLogInPage() {
		return "login";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	String checkLogIn(String email, String password,
			HttpSession session) {
		boolean correct = false;
		if (email.equals("mark@fb.com") && 
			password.equals("mark123")) {
			session.setAttribute("email", "mark@fb.com");
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
	@RequestMapping(value="/register", method=RequestMethod.POST)
	String createNewMember(String name,
			String email, String password) {
		String sql = "insert into member" +
				"(name, email,password) " +
				"values(?,?, sha2(?, 512) )";
		try {
			Connection c = DriverManager.getConnection(
				server, user, this.password);
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, name);
			p.setString(2, email);
			p.setString(3, password);
			p.execute();
			p.close(); c.close();
		} catch (Exception e) { }
		return "redirect:/login";
	}
	
	@RequestMapping("/profile")
	String showProfilePage(HttpSession session) {
		String e = (String)session.getAttribute("email");
		if (e == null) {
			return "redirect:/login";
		} else {
			return "profile";
		}
	}
	@RequestMapping("/logout")
	String showLogOutPage() {
		return "logout";
	}
}
