package cl.bree2003.AlkeWalletM6.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {


	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateUser() {
		UserEntity user = new UserEntity();
		user.setName("John Doe");
		user.setUsername("johndoe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password");

		when(userRepository.save(any(UserEntity.class))).thenReturn(user);

		userService.createUser(user);

		verify(userRepository, times(1)).save(user);
		assertNotNull(user.getPassword());
		assertNotEquals("password", user.getPassword());
	}

	@Test
	void testUpdateUser() {
		UserEntity existingUser = new UserEntity();
		existingUser.setId(1L);
		existingUser.setName("John Doe");
		existingUser.setUsername("johndoe");
		existingUser.setEmail("johndoe@example.com");
		existingUser.setPassword("password");

		UserEntity updatedUser = new UserEntity();
		updatedUser.setId(1L);
		updatedUser.setName("Jane Doe");
		updatedUser.setUsername("janedoe");
		updatedUser.setEmail("janedoe@example.com");
		updatedUser.setPassword("newpassword");

		when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

		userService.updateUser(updatedUser, existingUser.getId());

		verify(userRepository, times(1)).findById(existingUser.getId());
		verify(userRepository, times(1)).save(updatedUser);
		assertEquals("Jane Doe", updatedUser.getName());
		assertEquals("janedoe", updatedUser.getUsername());
		assertEquals("janedoe@example.com", updatedUser.getEmail());
		assertNotEquals("password", updatedUser.getPassword());
		assertEquals("newpassword", updatedUser.getPassword());
	}

	@Test
	void testDeleteUserById() {
		Long userId = 1L;

		when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));

		userService.deleteUserById(userId);

		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	void testFindUserById() {
		Long userId = 1L;
		UserEntity user = new UserEntity();
		user.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Optional<UserEntity> found = userService.findUserById(userId);

		assertTrue(found.isPresent());
		assertEquals(userId, found.get().getId());
	}

	@Test
	void testFindAllUsers() {
		UserEntity user1 = new UserEntity();
		user1.setId(1L);
		UserEntity user2 = new UserEntity();
		user2.setId(2L);
		List<UserEntity> userList = Arrays.asList(user1, user2);

		when(userRepository.findAll()).thenReturn(userList);

		List<UserEntity> found = userService.findAllUsers();

		assertEquals(2, found.size());
	}

	@Test
	void testFindUserByUsername() {
		String username = "johndoe";
		UserEntity user = new UserEntity();
		user.setUsername(username);

		when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));

		Optional<UserEntity> found = userService.findUserByUsername(username);

		assertTrue(found.isPresent());
		assertEquals(username, found.get().getUsername());
	}

	@Test
	void testFindUserByEmail() {
		String email = "johndoe@example.com";
		UserEntity user = new UserEntity();
		user.setEmail(email);

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

		Optional<UserEntity> found = userService.findUserByEmail(email);

		assertTrue(found.isPresent());
		assertEquals(email, found.get().getEmail());
	}

}
