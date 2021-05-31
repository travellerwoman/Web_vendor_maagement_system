package practica.es;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	
	@Autowired
	private authority authority;
	
	@Autowired
	private UserService userServices;	
	
	@GetMapping("/login")
	public String login(Model model) {
		return"login";
	}
	
	@GetMapping("/loginerror")
	public String loginerror() {
		return"register";
	}
	
	@GetMapping("/register")
	public String register() {
		return"register";
	}

	@RequestMapping ("/form/postUser")
	public String formPostItem(@RequestParam String name, @RequestParam String email ,@RequestParam String password) {
		User user = new User(name, email,  password);
		if(userServices.postUser(user)){
			return "success";	
		}else {
			return "error";
		}
	}
		
	@RequestMapping("/admin/indexUser")
	public String  showUsers(Model model) {
		return userServices.addToModel(model, userServices.showUsers());
	}		
		
	@RequestMapping("/user/profile")
	public String showUser(Model model) {
		return userServices.showUser(model);
	}
		
	@RequestMapping ("/admin/delete/{id}")     
	public String formAdminDeleteUser(@PathVariable Long id) {
		if(userServices.AdminDeleteUser(id)) {
			return "success";
		}else {
			return "error";
		}
	}
	
	@RequestMapping ("/user/delete")     
	public String formDeleteUser(HttpSession session) {
		if(userServices.deleteUser(session)) {
			return "success";
		}else {
			return "error";
		}
	}
		
	@RequestMapping ("/user/modifyUser")
	public String formUpdateUser(Model model) {
		model.addAttribute("user" , authority.getLoggedUser());
		return "userUpdate";
	}
		
	@RequestMapping ("/user/modifyUser2")    // the id is used to update
	public String formUpdateUser2(HttpSession session , @RequestParam String name, @RequestParam String email, @RequestParam String password){
		User newUser = new User(authority.getLoggedUser().getIdUser() ,name, email, password);
		if(userServices.updateUser(newUser)) {
			session.invalidate();
	   	   	return "success";
	    }else {
	   	   	return "error";
	    }
	}
	
	@RequestMapping ("/user/updateFav/{itemId}")
	public String updateFav(@PathVariable Long itemId){
		if(userServices.updateUserFav(itemId)) {
	   	   return "success";
	    }else {
	   	   return "error";
	    }
	}
}