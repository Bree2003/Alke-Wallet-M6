package cl.bree2003.AlkeWalletM6.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	private UserEntity user;
	
	@BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setBalance(1000.0);

        userRepository.save(user);
    }

	@Test
	void testFindUserByUsername() {
		Optional<UserEntity> found = userRepository.findUserByUsername("johndoe");
		assertTrue(found.isPresent());
		assertEquals(user.getUsername(), found.get().getUsername());
	}

	@Test
	void testFindUserByEmail() {
		Optional<UserEntity> found = userRepository.findUserByEmail("john.doe@example.com");
		assertTrue(found.isPresent());
		assertEquals(user.getUsername(), found.get().getUsername());
	}
	
	@Test
	void testNotFindUserByUsername() {
		Optional<UserEntity> found = userRepository.findUserByUsername("joghndoe");
		assertFalse(found.isPresent());
	}

	@Test
	void testNotFindUserByEmail() {
		Optional<UserEntity> found = userRepository.findUserByEmail("joghn.doe@example.com");
		assertFalse(found.isPresent());
	}

}
