package practica.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public  UserRepositoryAuthenticationProvider UserRepositoryAuthProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/api/**");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/items/").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/logIn").authenticated();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/register").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/items/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/items/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/items/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/users/modify").hasRole("REGUSER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/delete").hasRole("REGUSER");
		http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/users/user/updateUserFav/**").hasRole("REGUSER");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/").hasRole("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/profile").hasRole("REGUSER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/").hasRole("REGUSER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/users/admin/**").hasRole("ADMIN");
		
		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf().disable();
		
		// Use Http Basic Authentication
		http.httpBasic();

		// Do not redirect when logout
		http.logout().logoutSuccessHandler((rq, rs, a) -> {	});
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Database authentication provider
		auth.authenticationProvider(UserRepositoryAuthProvider);
	}
}