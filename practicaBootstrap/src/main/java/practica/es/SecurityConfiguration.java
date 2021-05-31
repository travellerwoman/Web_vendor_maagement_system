package practica.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public AuthenticationSuccessHandler OAuth2auth;
	
	@Autowired
	public OAuth2authf OAuth2authf;
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Public pages
		http.authorizeRequests().antMatchers("/","/index").permitAll();
		http.authorizeRequests().antMatchers("/buttonPc").permitAll();
		http.authorizeRequests().antMatchers("/buttonMovil").permitAll();
		http.authorizeRequests().antMatchers("/buttonAccessory").permitAll();
		http.authorizeRequests().antMatchers("/advancedFilter").permitAll();
		http.authorizeRequests().antMatchers("/indexAjax").permitAll();
		http.authorizeRequests().antMatchers("/item/{num}").permitAll();	
		http.authorizeRequests().antMatchers("/item/**").permitAll();
		http.authorizeRequests().antMatchers("showItem").permitAll();
		http.authorizeRequests().antMatchers("/erroradfpiuvnw8anu").permitAll();
		http.authorizeRequests().antMatchers("/bootstrap.bundle.min.js").permitAll();
		
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/register").permitAll();
		http.authorizeRequests().antMatchers("/form/postUser").permitAll();
		http.authorizeRequests().antMatchers("/app.js").permitAll();
		http.authorizeRequests().antMatchers("/loginerror").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN") ;
		
		http.authorizeRequests().antMatchers("/user/delete").hasAnyRole("REGUSER","ADMIN");
		http.authorizeRequests().antMatchers("/user/modifyUser").hasAnyRole("REGUSER","ADMIN");
		http.authorizeRequests().antMatchers("/user/modifyUser2").hasAnyRole("REGUSER","ADMIN");
		http.authorizeRequests().antMatchers("/user/updateFav/**").hasAnyRole("REGUSER","ADMIN");
		http.authorizeRequests().antMatchers("/user/profile").hasAnyRole("USER","REGUSER","ADMIN");
		
		// Login form
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/loginerror");
		// Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");
		
		http.httpBasic().disable();
		
		http.oauth2Login().loginPage("/login");
		http.oauth2Login().permitAll().failureHandler(OAuth2authf);
		http.oauth2Login().permitAll().successHandler(OAuth2auth);
		http.oauth2Login();	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		// Database authentication provider
		auth.authenticationProvider(authenticationProvider);
	}
}