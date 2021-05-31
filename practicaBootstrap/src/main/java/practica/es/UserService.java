package practica.es;

import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {

	@Autowired
	private authority authority; 
	
	@Autowired
	private HttpSession session; 
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private StockService stockService;
	
	@PostConstruct
	public void init() {
		usersRepository.save(new User( "admin" , "a1@gmail.com" , "adminpass" , stockService.getItem((long) 1) , "ROLE_REGUSER","ROLE_ADMIN"));
		usersRepository.save(new User( "user1" , "a2@gmail.com" , "userpass1" , stockService.getItem((long) 1) , "ROLE_REGUSER"));
		usersRepository.save(new User( "user2" , "a3@gmail.com" , "userpass2" , stockService.getItem((long) 1) , "ROLE_REGUSER"));
		usersRepository.save(new User( "admin2" , "a4@gmail.com" , "adminpass" , stockService.getItem((long) 1) , "ROLE_REGUSER","ROLE_ADMIN"));
		
	}
	
	public boolean postUser(User user2) {
		User user=new User(user2.getName(),user2.getEmail(),user2.getPassword(), stockService.getItem((long) 1) , "ROLE_REGUSER");
		xssUser(user);
		if (user.getName().length()<= 30 && (user.getEmail().length()<= 30 && user.getEmail().contains("@") ) ) {
			usersRepository.save(user);
			return true;
		}else {
			return false;
		}
	}
	
	public Collection<User> showUsers(){
		return usersRepository.findAll(); 
	}
	
	public String showUser(Model model) {
		
		if(session.getAttribute("oauth").toString().equals("yes")) {
			model.addAttribute("oauth2Profile", 1);
			model.addAttribute("name", session.getAttribute("name"));
			model.addAttribute("identity", session.getAttribute("email"));
		}else{
			model.addAttribute("userProfile" , authority.getLoggedUser());
		}
		return "showUser";
	}
	
	public String addToModel(Model model , Collection<User> stockUsers) { //add Item collection to model to be seen in the Index
		model.addAttribute("users", stockUsers);
		model.addAttribute("admin", authority.getLoggedUser().getRoles().contains("ROLE_ADMIN"));
		return "indexUser";
	}
	
	public boolean deleteUser(HttpSession session) {
		if(usersRepository.getOne(authority.getLoggedUser().getIdUser()) == null) {
			return false;
		}
		usersRepository.deleteById(authority.getLoggedUser().getIdUser());
		session.invalidate();
		return true;
	}
	
	public boolean AdminDeleteUser(Long id) {
		if(usersRepository.getOne(id) == null) {
			return false;
		}
		usersRepository.deleteById(id);
		return true;
	}
	
	public User getUser() {
        User user = usersRepository.findById(authority.getLoggedUser().getIdUser()).get(); 
        return user;
    }
	
	public boolean updateUser(User newUser) {
		User oldUser = usersRepository.findById(authority.getLoggedUser().getIdUser()).get();
		xssUser(newUser);
		if (!oldUser.equals(null)  && newUser.getName().length()<= 30 && (newUser.getEmail().length()<= 30 && newUser.getEmail().contains("@"))) {
			oldUser.setName(newUser.getName());
			oldUser.setEmail(newUser.getEmail());
			oldUser.setPassword(newUser.getPassword());
            usersRepository.save(oldUser);
			return true;
		}else {
			return false;
		}
	}

	public boolean updateUserFav(Long id) {
		User user = usersRepository.findById(authority.getLoggedUser().getIdUser()).get();
		if (!user.equals(null)) {
			authority.getLoggedUser().setIdFavProduct(stockService.getItem(id));
            usersRepository.save(authority.getLoggedUser());
			return true;
		}else {
			return false;
		}
	}
	
	public void xssUser(User user) {
		String s=user.getEmail();
		s=s.replaceAll("<script>", "");
		s=s.replaceAll("</script>", "");
		s=s.replaceAll("<", "");
		s=s.replaceAll(">", "");
		s=s.replaceAll("'", "");
		s=s.replaceAll(";", "");
		user.setEmail(s);
		String n=user.getName();
		n=n.replaceAll("<script>", "");
		n=n.replaceAll("</script>", "");
		n=n.replaceAll("<", "");
		n=n.replaceAll(">", "");
		n=n.replaceAll("'", "");
		n=n.replaceAll(";", "");
		user.setName(n);
	}
	
}