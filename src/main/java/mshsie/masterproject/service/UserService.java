package mshsie.masterproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Ruan nje user te ri
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);

		user = userRepository.save(user);
	}

	// Update user ekzistues
	public void updateUser(User user) {
		user = userRepository.save(user);
	}

	// Kthen nje list me gjithe userat
	public List<User> getAllUsers() {

		List<User> userList = userRepository.findAll();

		if (userList.size() > 0 && !userList.isEmpty())
			return userList;
		else
			return new ArrayList<User>();
	}

	//Gjen user me ID
	public User findUserById(Long id) {
		User u = userRepository.findUserById(id);
		return u;
	}

	// Gjen perdoruesin me username
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user.getActive() == 0 || user == null)
			throw new UsernameNotFoundException("Perdoruesi nuk u gjet");

		return user;
	}

	// Gjen user me email
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	// Kontrollon nese username ekziston
	public Boolean usernameExists(String username) {
		Boolean exists;
		return exists = userRepository.existsByUsername(username);
	}

	// Kontrollon nese username ekziston
	public Boolean emailExists(String email) {
		Boolean exists;
		return exists = userRepository.existsByEmail(email);
	}

	// Fshin nje user
	public void deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username);

		if (user != null && user.getActive() == 1) {
			user.setActive(0);
		} else {
			throw new UsernameNotFoundException("No user record exists for given username");
		}

		userRepository.save(user);
	}
	
	//Gjen users me rol specifik
	public List<User> findUsersWithSpecificRole(String role) {
		List<User> users = userRepository.findBySpecificRole(role);
		if(!users.isEmpty() && users.size() > 0)
			return users;
		else
			return new ArrayList<User>();
	}
	
	public void unsubFromCourse(Long uid, Long cid) {
		userRepository.unsubFromCourse(uid, cid);
	}

}
