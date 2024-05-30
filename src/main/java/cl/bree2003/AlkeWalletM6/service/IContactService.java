package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IContactService {

    void createContact(ContactEntity contact);
    void updateContact(ContactEntity contact, Long id);
    Optional<ContactEntity> findContactById(Long id);
    Optional<UserEntity> findUserByEmail(String email);
    List<ContactEntity> findAllContacts();
    Optional<UserEntity> findUserById(Long id);
    void deleteContactById(Long id);
    List<ContactEntity> findAllContactsByUserId(Long userId);
    Optional<ContactEntity> findContactByEmailByUserId(String email, Long userId);

}
