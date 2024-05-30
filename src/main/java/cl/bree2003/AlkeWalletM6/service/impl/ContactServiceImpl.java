package cl.bree2003.AlkeWalletM6.service.impl;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.ContactRepository;
import cl.bree2003.AlkeWalletM6.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public void createContact(ContactEntity contact) {
        contactRepository.save(contact);
    }

    @Override
    public void updateContact(ContactEntity contact, Long id) {
        Optional<ContactEntity> optionalContact = findContactById(id);
        if(optionalContact.isPresent()){
            ContactEntity existingContact = optionalContact.get();
            existingContact.setUsername(contact.getUsername());

            contactRepository.save(existingContact);
        }
    }

    @Override
    public Optional<ContactEntity> findContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return contactRepository.findUserById(id);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return contactRepository.findUserByEmail(email);
    }

    @Override
    public List<ContactEntity> findAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactEntity> findAllContactsByUserId(Long id) {
        UserEntity existingUser = findUserById(id).get();
        return contactRepository.findAllContactsByUser(existingUser);
    }

    @Override
    public Optional<ContactEntity> findContactByEmailByUser(String email, Long id) {
        Optional<UserEntity> optionalUser = findUserById(id);
        return contactRepository.findContactByEmailByUser(optionalUser.get(), email);
    }
}
