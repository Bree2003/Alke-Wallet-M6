package cl.bree2003.AlkeWalletM6.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.ContactRepository;

class ContactServiceImplTest {

	@Mock
	private ContactRepository contactRepository;

	@InjectMocks
	private ContactServiceImpl contactService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateContact() {
		ContactEntity contact = new ContactEntity();
		contact.setUsername("johndoe");
		contact.setEmail("johndoe@example.com");

		when(contactRepository.save(any(ContactEntity.class))).thenReturn(contact);

		contactService.createContact(contact);

		verify(contactRepository, times(1)).save(contact);
	}

	@Test
	void testUpdateContact() {
		ContactEntity existingContact = new ContactEntity();
		existingContact.setId(1L);
		existingContact.setUsername("johndoe");

		ContactEntity updatedContact = new ContactEntity();
		updatedContact.setUsername("janedoe");

		when(contactRepository.findById(existingContact.getId())).thenReturn(Optional.of(existingContact));
		when(contactRepository.save(any(ContactEntity.class))).thenReturn(updatedContact);

		contactService.updateContact(updatedContact, existingContact.getId());

		verify(contactRepository, times(1)).findById(existingContact.getId());
		verify(contactRepository, times(1)).save(existingContact);
		assertEquals("janedoe", existingContact.getUsername());
	}

	@Test
	void testFindContactById() {
		Long contactId = 1L;
		ContactEntity contact = new ContactEntity();
		contact.setId(contactId);

		when(contactRepository.findById(contactId)).thenReturn(Optional.of(contact));

		Optional<ContactEntity> found = contactService.findContactById(contactId);

		assertTrue(found.isPresent());
		assertEquals(contactId, found.get().getId());
	}

	@Test
	void testFindUserByEmail() {
		String email = "johndoe@example.com";
		UserEntity user = new UserEntity();
		user.setEmail(email);

		when(contactRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

		Optional<UserEntity> found = contactService.findUserByEmail(email);

		assertTrue(found.isPresent());
		assertEquals(email, found.get().getEmail());
	}

	@Test
	void testFindAllContacts() {
		ContactEntity contact1 = new ContactEntity();
		contact1.setId(1L);
		ContactEntity contact2 = new ContactEntity();
		contact2.setId(2L);
		List<ContactEntity> contactList = Arrays.asList(contact1, contact2);

		when(contactRepository.findAll()).thenReturn(contactList);

		List<ContactEntity> found = contactService.findAllContacts();

		assertEquals(2, found.size());
	}

	@Test
	void testFindUserById() {
		Long userId = 1L;
		UserEntity user = new UserEntity();
		user.setId(userId);

		when(contactRepository.findUserById(userId)).thenReturn(Optional.of(user));

		Optional<UserEntity> found = contactService.findUserById(userId);

		assertTrue(found.isPresent());
		assertEquals(userId, found.get().getId());
	}

	@Test
	void testDeleteContactById() {
		Long contactId = 1L;

		contactService.deleteContactById(contactId);

		verify(contactRepository, times(1)).deleteById(contactId);
	}

	@Test
	void testFindAllContactsByUserId() {
		Long userId = 1L;
		ContactEntity contact1 = new ContactEntity();
		contact1.setId(1L);
		ContactEntity contact2 = new ContactEntity();
		contact2.setId(2L);
		List<ContactEntity> contactList = Arrays.asList(contact1, contact2);

		when(contactRepository.findAllContactsByUserId(userId)).thenReturn(contactList);

		List<ContactEntity> found = contactService.findAllContactsByUserId(userId);

		assertEquals(2, found.size());
	}

	@Test
	void testFindContactByEmailByUserId() {
		Long userId = 1L;
		String email = "johndoe@example.com";
		ContactEntity contact = new ContactEntity();
		contact.setEmail(email);

		when(contactRepository.findContactByEmailByUserId(email, userId)).thenReturn(Optional.of(contact));

		Optional<ContactEntity> found = contactService.findContactByEmailByUserId(email, userId);

		assertTrue(found.isPresent());
		assertEquals(email, found.get().getEmail());
	}

	@Test
	void testFindContactByUsernameByUserId() {
		Long userId = 1L;
		String username = "johndoe";
		ContactEntity contact = new ContactEntity();
		contact.setUsername(username);

		when(contactRepository.findContactByUsernameByUserId(username, userId)).thenReturn(Optional.of(contact));

		Optional<ContactEntity> found = contactService.findContactByUsernameByUserId(username, userId);

		assertTrue(found.isPresent());
		assertEquals(username, found.get().getUsername());
	}
}
