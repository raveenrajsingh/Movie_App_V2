package org.springRaveen.com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**","/static/**","/img/**","/css/**","/js/**");
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	      throws Exception {
		 @SuppressWarnings("deprecation")
		UserBuilder users = User.withDefaultPasswordEncoder();
	        auth.inMemoryAuthentication().withUser(users.username("Raveen").password("test123").roles("USER"));
	       /* UserBuilder users = User.withDefaultPasswordEncoder();
			
			auth.inMemoryAuthentication()
				.withUser(users.username("john").password("test123").roles("EMPLOYEE"))*/
	        
	    }
	@Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/home");
    }
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests(a -> a
			.antMatchers("/","/error").permitAll()
			.anyRequest().authenticated()
					//.anyRequest().permitAll()
					)
			.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/authenticateTheUser")
			   .successHandler(new AuthenticationSuccessHandler() {
			         
			        @Override
			        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			                Authentication authentication) throws IOException, ServletException {
			            // run custom logics upon successful login
			             
			            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			            String username = userDetails.getUsername();
			             
			            System.out.println("The user " + username + " has logged in.");
			             
			            response.sendRedirect(request.getContextPath()+"/home");
			        }
			    })
			.and()
			.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			)
			.csrf(c -> c
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			)
			
			.logout(l -> l
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID") 
			)
			.oauth2Login().successHandler(successHandler());
		

	    
		// @formatter:on
    }
    
   
}