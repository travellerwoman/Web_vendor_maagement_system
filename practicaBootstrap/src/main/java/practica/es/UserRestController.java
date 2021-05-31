package practica.es;

import java.util.Collection;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
		
	@Autowired
	private UserService userServices;	
	
	@Autowired
	private authority authority;
		
	@GetMapping("/")
	public Collection<User> getUsers() {
		return userServices.showUsers();
	}
	
	@GetMapping("/profile")
	public User getUser() {
		return userServices.getUser();
	}
	
	@GetMapping("/whoami")
	public int whoami() {
		if (authority.isLoggedUser()) {
			if(authority.getLoggedUser().getRoles().contains("ROLE_ADMIN")){
				return 2;
			}else{
				return 1;
			}
		}else {
			return 0;
		}	
	}
	
		
	@PostMapping("/register")													
	public ResponseEntity<User> createUser(@RequestBody User user) {
		if(userServices.postUser(user)) {
			return new ResponseEntity<>(HttpStatus.CREATED);      //STATUS 201
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  //400 ERROR
		}
	}
	
	@PutMapping("/modify")
	public ResponseEntity<User> updateUser(@RequestBody User newUser, HttpSession session) {
		User User = new User(authority.getLoggedUser().getIdUser() ,newUser.getName(), newUser.getEmail(), newUser.getPassword());
		if(userServices.updateUser(User)) {
			session.invalidate();
			return new ResponseEntity<>(HttpStatus.CREATED);       //STATUS 201
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);   //400 ERROR
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<User> deleteUser(HttpSession session) {
		if (userServices.deleteUser(session)) {
			return new ResponseEntity<>(HttpStatus.OK);	
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
		
	@DeleteMapping("/admin/delete/{id}")
	public ResponseEntity<User> AdminDeleteUser(@PathVariable Long id) {
		if (userServices.AdminDeleteUser(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PatchMapping("/user/updateUserFav/{id}")
	public ResponseEntity<User> updateUserFav(@PathVariable Long id) {
		if (userServices.updateUserFav(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
	