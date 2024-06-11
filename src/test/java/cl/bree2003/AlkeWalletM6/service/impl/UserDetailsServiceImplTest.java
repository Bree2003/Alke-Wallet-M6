package cl.bree2003.AlkeWalletM6.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import jakarta.servlet.http.HttpSession;

class UserDetailsServiceImplTest {

	@Mock
	private IUserService userService;

	@Mock
	private HttpSession session;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoadUserByUsername_UserFound() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setUsername("johndoe");
		userEntity.setPassword("password");

		when(userService.findUserByUsername("johndoe")).thenReturn(Optional.of(userEntity));

		UserDetails userDetails = userDetailsService.loadUserByUsername("johndoe");

		verify(session, times(1)).setAttribute("user_session_id", userEntity.getId());
		assertEquals("johndoe", userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
	}

	@Test
	void testLoadUserByUsername_UserNotFound() {
		when(userService.findUserByUsername("johndoe")).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("johndoe");
		});
	}


}
