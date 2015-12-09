package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation to support Spring Security logon based on custom
 * authentication of persisted databases
 * 
 * @author robert.hinds
 * 
 */
@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService, InitializingBean {



	public void afterPropertiesSet() throws Exception {
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = username.toLowerCase();
		try {
			User user = null;
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			
			auths.add(new SimpleGrantedAuthority("USER"));
			
			auths.add(new SimpleGrantedAuthority("ADMIN"));
			
			try {
				user = new User(username,"passw0rd", true, true, true, true, auths);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username + "not found", e);
		}
	}

}
