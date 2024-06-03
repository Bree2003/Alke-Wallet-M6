package cl.bree2003.AlkeWalletM6.controller;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.service.IContactService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.ContactValidation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/alke/contacts")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @Autowired
    private ContactValidation contactValidation;

    @GetMapping("/create")
    public String showCreateContactForm(Model model){
        model.addAttribute("contact", new ContactEntity());
        return "/alke/create-contact";
    }

    @PostMapping("/create")
    public String createContact(@ModelAttribute("contact") ContactEntity contact, Model model, HttpSession session){
        Long userId = (Long) session.getAttribute("user_session_id");
        contact.setUser(contactService.findUserById(userId).get());
        ResponseDTO validationResponse = contactValidation.validate(contact);
        if(validationResponse.getNumOfErrors() > 0){
            model.addAttribute("errors", validationResponse.getMessage());
            return "/alke/create-contact";
        }

        contactService.createContact(contact);
        return "redirect:/alke/contacts/all-your-contacts";
    }

    @GetMapping("/all-your-contacts")
    public String showAllContactsByUser(Model model, HttpSession session){
        Long userId = (Long) session.getAttribute("user_session_id");

        List<ContactEntity> contacts = contactService.findAllContactsByUserId(userId);
        model.addAttribute("contacts", contacts);

        return "/alke/all-contacts";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id, HttpSession session){
        Long userId = (Long) session.getAttribute("user_session_id");
        Optional<ContactEntity> optionalContact = contactService.findContactById(id);

        if(optionalContact.isPresent() & optionalContact.get().getUser().getId().equals(userId)){
            contactService.deleteContactById(id);
        }

        return "redirect:/alke/contacts/all-your-contacts";
    }
}
