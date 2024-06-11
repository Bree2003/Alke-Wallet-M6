package cl.bree2003.AlkeWalletM6.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class ContactRepositoryTest {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UserRepository userRepository;

	private UserEntity user;
	private UserEntity user2;
	private ContactEntity contact;

	@BeforeEach
	void setUp(){
		user = new UserEntity();
		user.setName("John Doe");
		user.setUsername("johndoe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password");
		user.setBalance(100.00);
		userRepository.save(user);

		contact = new ContactEntity();
		contact.setUsername("contactuser");
		contact.setEmail("contactuser@example.com");
		contact.setUser(user);
		contactRepository.save(contact);
		
		user2 = new UserEntity();
		user.setName("Bre Ale");
		user.setUsername("breeale");
		user.setEmail("breale@example.com");
		user.setPassword("password");
		user.setBalance(100.00);
		userRepository.save(user2);
	}

	@Test
	void testFindAllContactsByUserId() {
		List<ContactEntity> foundContacts = contactRepository.findAllContactsByUserId(user.getId());

		assertFalse(foundContacts.isEmpty());
		assertEquals(1, foundContacts.size());
		assertEquals(contact.getUsername(), foundContacts.get(0).getUsername());
	}

	@Test
	void testFindContactByEmailByUserId() {
		Optional<ContactEntity> foundContact = contactRepository.findContactByEmailByUserId("contactuser@example.com", user.getId());
		assertTrue(foundContact.isPresent());
		assertEquals(contact.getEmail(), foundContact.get().getEmail());
	}

	@Test
	void testFindContactByUsernameByUserId() {
		Optional<ContactEntity> foundContact = contactRepository.findContactByUsernameByUserId("contactuser", user.getId());
		assertTrue(foundContact.isPresent());
		assertEquals(contact.getUsername(), foundContact.get().getUsername());
	}

	@Test
	void testNotFindAllContactsByUserId() {
		List<ContactEntity> foundContacts = contactRepository.findAllContactsByUserId(user2.getId());
		assertTrue(foundContacts.isEmpty());
		assertEquals(0, foundContacts.size());
	}

	@Test
	void testNotFindContactByEmailByUserId() {
		Optional<ContactEntity> foundContact = contactRepository.findContactByEmailByUserId("contactusera@example.com", user.getId());
		assertFalse(foundContact.isPresent());
	}

	@Test
	void testNotFindContactByUsernameByUserId() {
		Optional<ContactEntity> foundContact = contactRepository.findContactByUsernameByUserId("contactusera", user.getId());
		assertFalse(foundContact.isPresent());
	}

}
