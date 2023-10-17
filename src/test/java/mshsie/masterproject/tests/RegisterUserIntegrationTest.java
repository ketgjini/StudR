package mshsie.masterproject.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RegisterUserIntegrationTest {

	class RegisterUseCaseTest {

		private UserRepository userRepository;

		private RegisterUseCase registerUseCase;

		@BeforeEach
		void initUseCase() {
			registerUseCase = new RegisterUseCase(userRepository);
		}
		
		@Test
		void savedUserHasUsername() {
			User user = new User("Jade", "Doe", "jane@gmail.com", "JaneDoe", "Jane1234-", 1, "student");
			User savedUser = registerUseCase.registerUser(user);
			assertEquals("JaneDoe", savedUser.getUsername());
		}

	}
}
