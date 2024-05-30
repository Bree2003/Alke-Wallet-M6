package cl.bree2003.AlkeWalletM6.service.model.validation;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IContactService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ContactValidation {

    @Autowired
    private IContactService contactService;

    public ResponseDTO validate(ContactEntity contact){
        ResponseDTO responseDTO = new ResponseDTO();

        Optional<UserEntity> existingUser = contactService.findUserByEmail(contact.getEmail());
        if(existingUser.isEmpty()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("User does not exist in the system, make sure you are entering the right email.");
        } else {
            // Compare emails only if existingUser is present
            if(existingUser.get().getEmail().equals(contact.getUser().getEmail())){
                responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
                responseDTO.setMessage("You cannot add yourself as a contact.");
            }

            // Check if the contact already exists
            Optional<ContactEntity> existingContact = contactService.findContactByEmailByUserId(contact.getEmail(), contact.getUser().getId());
            if(existingContact.isPresent()){
                responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
                responseDTO.setMessage("Contact already exists.");
            }
        }

        return responseDTO;
    }
}
