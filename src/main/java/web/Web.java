package web;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.ui.*;
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
		String sql = "select * from member where " +
				"email = ? and password = sha2(?, 512)";
		try {
			Connection c = DriverManager.getConnection(
					server, user, this.password);
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, email);
			p.setString(2, password);
			ResultSet r = p.executeQuery();
			if (r.next()) {
				Member m = new Member();
				m.name = r.getString("name");
				m.email = r.getString("email");
				m.password = r.getString("password");
				session.setAttribute("member", m);
				correct = true;
			}
			r.close(); p.close(); c.close();
		} catch (Exception e) { }
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
	String showProfilePage(HttpSession session, Model model) {
		Member m = (Member)session.getAttribute("member");
		if (m == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("name", m.name);
			return "profile";
		}
	}
	@RequestMapping("/logout")
	String showLogOutPage(HttpSession session) {
		session.removeAttribute("member");
		return "logout";
	}
}

class Member {
	String name;
	String email;
	String password;
}