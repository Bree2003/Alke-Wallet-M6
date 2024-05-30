package cl.bree2003.AlkeWalletM6.controller.rest;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/contacts")
public class ContactRestController {

    @Autowired
    private IContactService contactService;

    @PostMapping("/new")
    public ResponseEntity<ContactEntity> newContact(@RequestBody ContactEntity contact) {
        contactService.createContact(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ContactEntity> getContactById(@PathVariable Long id){
        Optional<ContactEntity> optionalContact = contactService.findContactById(id);
        return optionalContact.map(contact -> new ResponseEntity<>(contact, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ContactEntity> updateContact(@RequestBody ContactEntity contact, @PathVariable Long id){
        Optional<ContactEntity> optionalContact = contactService.findContactById(id);
        if(optionalContact.isPresent()){
            contactService.updateContact(contact, id);

            return new ResponseEntity<>(contact, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delecteContact(@PathVariable Long id){
        Optional<ContactEntity> optionalContact = contactService.findContactById(id);
        if(optionalContact.isPresent()){
            contactService.deleteContactById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContactEntity>> getContactsByUserId(@PathVariable Long userId){
        List<ContactEntity> contactsByUser = contactService.findAllContactsByUserId(userId);
        return new ResponseEntity<>(contactsByUser, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactEntity>> getAllContacts(){
        List<ContactEntity> contacts = contactService.findAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/checkUserExists")
    public ResponseEntity<Boolean> checkUserExists(@RequestParam String email){
        Optional<UserEntity> optionalUser = contactService.findUserByEmail(email);
        return new ResponseEntity<>(optionalUser.isPresent(), HttpStatus.OK);
    }

    @GetMapping("/checkDuplicateContact/{id}")
    public ResponseEntity<Boolean> checkDuplicateContact(@PathVariable Long id, @RequestParam String email){
        Optional<ContactEntity> optionalContact = contactService.findContactByEmailByUserId(email, id);
        return new ResponseEntity<>(optionalContact.isPresent(), HttpStatus.OK);
    }

}
