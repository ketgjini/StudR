package mshsie.masterproject.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.UserRepository;
import mshsie.masterproject.validation.UserNotFoundException;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public UserPrincipalDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null || user.getActive() == 0)
			throw new UserNotFoundException("Perdoruesi nuk u gjet");
		
		UserPrincipal userPrincipal = new UserPrincipal(user);
		
		return userPrincipal;
	}
	

}
