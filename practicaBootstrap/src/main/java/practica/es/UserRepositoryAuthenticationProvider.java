package practica.es;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private authority userComponent;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		User user = userRepository.findByName(auth.getName());
		String password = (String) auth.getCredentials();
		
		if ( user==null || !new BCryptPasswordEncoder().matches(password , userRepository.findByName(auth.getName()).getPassword())) {
			throw new BadCredentialsException("Wrong credentials");
		}else {
			userComponent.setLoggedUser(userRepository.findByName(auth.getName()));
			session.setAttribute("oauth", "no");
		}
		
		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : userRepository.findByName(auth.getName()).getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}
		return new UsernamePasswordAuthenticationToken(userRepository.findByName(auth.getName()).getName(), password, roles);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}