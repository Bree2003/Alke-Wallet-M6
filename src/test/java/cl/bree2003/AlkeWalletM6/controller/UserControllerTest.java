package cl.bree2003.AlkeWalletM6.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private UserValidation userValidation;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRecordPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/users/register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testRegister() throws Exception {
        UserEntity user = new UserEntity();
        user.setName("Jane Doe");
        user.setUsername("janedoe");
        user.setEmail("janedoe@example.com");
        user.setPassword("password");

        ResponseDTO response = new ResponseDTO();
        when(userValidation.validate(any(UserEntity.class))).thenReturn(response);

        mockMvc.perform(post("/register")
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegisterWithErrors() throws Exception {
        UserEntity user = new UserEntity();
        user.setName("Jane Doe");
        user.setUsername("janedoe");
        user.setEmail("janedoe@example.com");
        user.setPassword("password");

        ResponseDTO response = new ResponseDTO();
        response.setNumOfErrors(1);
        response.setMessage("Some validation error");

        when(userValidation.validate(any(UserEntity.class))).thenReturn(response);

        mockMvc.perform(post("/register")
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("/users/register"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/users/login"))
                .andExpect(model().attributeExists("loginDTO"));
    }

    @Test
    public void testAccess() throws Exception {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("janedoe");

        when(userService.findUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/access").sessionAttr("user_session_id", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alke/home"));
    }


}
