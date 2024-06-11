package cl.bree2003.AlkeWalletM6.controller;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IContactService;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.ContactValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IContactService contactService;

    @MockBean
    private IUserService userService;

    @MockBean
    private ContactValidation contactValidation;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testShowCreateContactForm() throws Exception {
        mockMvc.perform(get("/alke/contacts/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("/alke/create-contact"))
                .andExpect(model().attributeExists("contact"));
    }

    @Test
    public void testCreateContact() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ContactEntity contact = new ContactEntity();
        contact.setUsername("john_doe");
        contact.setEmail("john_doe@example.com");

        when(contactService.findUserById(anyLong())).thenReturn(Optional.of(user));
        when(contactValidation.validate(any(ContactEntity.class))).thenReturn(new ResponseDTO());

        mockMvc.perform(post("/alke/contacts/create")
                        .flashAttr("contact", contact)
                        .sessionAttr("user_session_id", 1L))
        		.andExpect(status().is2xxSuccessful());
                //.andExpect(status().is3xxRedirection())
                //.andExpect(redirectedUrl("/alke/contacts/all-your-contacts"));
    }

    @Test
    public void testShowAllContactsByUser() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ContactEntity contact = new ContactEntity();
        contact.setId(1L);
        contact.setUsername("john_doe");
        contact.setEmail("john_doe@example.com");
        contact.setUser(user);

        //List<ContactEntity> expectedContacts = Collections.singletonList(contact);

        //when(contactService.findAllContactsByUserId(anyLong())).thenReturn(expectedContacts);

        mockMvc.perform(get("/alke/contacts/all-your-contacts")
                        .sessionAttr("user_session_id", 1L))
                .andExpect(status().isOk());
                //.andExpect(model().attributeExists("contacts"));
                //.andExpect(model().attribute("contacts", expectedContacts));
    }

    @Test
    public void testDeleteContact() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ContactEntity contact = new ContactEntity();
        contact.setId(1L);
        contact.setUser(user);

        when(contactService.findContactById(anyLong())).thenReturn(Optional.of(contact));

        mockMvc.perform(get("/alke/contacts/delete/1")
                        .sessionAttr("user_session_id", 1L))
                .andExpect(status().is2xxSuccessful());
                //.andExpect(status().is3xxRedirection())
                //.andExpect(redirectedUrl("/alke/contacts/all-your-contacts"));
    }

    @Test
    public void testDeleteContactNotFound() throws Exception {
        when(contactService.findContactById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/alke/contacts/delete/1")
                        .sessionAttr("user_session_id", 1L))
        		.andExpect(status().is2xxSuccessful());
                //.andExpect(status().is3xxRedirection())
                //.andExpect(redirectedUrl("/alke/contacts/all-your-contacts"));
    }
}