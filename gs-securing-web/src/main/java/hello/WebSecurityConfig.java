package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired private CustomTokenBasedRememberMeService tokenBasedRememberMeService;
	@Autowired private RememberMeAuthenticationProvider rememberMeAuthenticationProvider;

	@Override protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf()
        		.disable()
        		 .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll().and().rememberMe()
            .rememberMeServices(tokenBasedRememberMeService)
            .and().addFilterAfter(corsFilter(), BasicAuthenticationFilter.class);
        
        
//            
//            .authorizeRequests()
//                .antMatchers("/resources/**").permitAll()
//                .antMatchers("/sign-up").permitAll()
//                .antMatchers("/sign-in").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/")
//                .loginProcessingUrl("/loginprocess")
//                .failureUrl("/mobile/app/sign-in?loginFailure=true")
//                .permitAll().and()
//            .rememberMe().rememberMeServices(tokenBasedRememberMeService);
        
//        http
//        .authorizeRequests()
//            .antMatchers("/", "/home").permitAll()
//            .anyRequest().authenticated()
//            .and()
//        .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .and()
//        .logout()
//            .permitAll();
    }
	
	
	
	 
	 @Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		 auth
		 	.userDetailsService(userDetailsServiceImpl);
		 auth.authenticationProvider(rememberMeAuthenticationProvider);
	}


		@Bean public CorsFilter corsFilter() throws Exception{
			 return new CorsFilter();
		 }

	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		 auth
		 	.userDetailsService(userDetailsServiceImpl);
		 auth.authenticationProvider(rememberMeAuthenticationProvider);
	 }
	 
	 @Bean @Override public AuthenticationManager authenticationManagerBean() throws Exception {
		 return super.authenticationManagerBean();
	 }
	 
	 @Bean public BCryptPasswordEncoder bCryptPasswordEncoder(){
		 return new BCryptPasswordEncoder();
	 }
}