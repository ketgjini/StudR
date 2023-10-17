package mshsie.masterproject.tests;

import org.springframework.stereotype.Service;

import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.UserRepository;

@Service
public class RegisterUseCase {
	
	private final UserRepository userRepository;

	public RegisterUseCase(UserRepository userRepository) {
	   this.userRepository = userRepository;
	}

	public User registerUser(User user) {
	   return userRepository.save(user);
	}
}
