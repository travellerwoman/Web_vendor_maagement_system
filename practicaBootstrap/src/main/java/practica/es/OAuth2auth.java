package practica.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2auth implements AuthenticationSuccessHandler {
	
	@Autowired
	public HttpSession session;
	@Autowired
	public authority authority;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_OAUTH");
		System.out.println("Hola");
		System.out.println(authentication.getPrincipal());
		System.out.println(authentication.getName());
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		
		if (user.getAuthorities().toString().contains("google")) {
			session.setAttribute("name", user.getAttribute("name"));
			session.setAttribute("email", user.getAttribute("email"));
			session.setAttribute("oauth", "yes");
		}else {
			session.setAttribute("name", user.getAttribute("login"));
			session.setAttribute("email", user.getAttribute("html_url"));
			session.setAttribute("oauth", "yes");
		}

		System.out.println(session.getAttribute("name"));
		System.out.println(session.getAttribute("email"));
		System.out.println(session.getAttribute("oauth"));
		System.out.println(session.getServletContext());
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");		
	}
}
