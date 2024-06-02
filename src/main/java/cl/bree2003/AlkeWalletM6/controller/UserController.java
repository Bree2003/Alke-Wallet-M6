package cl.bree2003.AlkeWalletM6.controller;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.UserValidation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidation userValidation;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserEntity user, Model model){
        user.setBalance(500.27);
        ResponseDTO validationResponse = userValidation.validate(user);
        if(validationResponse.getNumOfErrors() > 0) {
            model.addAttribute("errors", validationResponse.getMessage());
            return "register";
        }
        userService.createUser(user);
        return "redirect:/api/users/login";
    }

    @GetMapping({"/login", "/"})
    public String showLoginForm(Model model){
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDTO") LoginDTO loginDTO, Model model, HttpSession session){
        Optional<UserEntity> optionalUser = userService.findUserByUsername(loginDTO.getUsername());
        if(optionalUser.isEmpty()){
            model.addAttribute("error", "User not found");
            return "login";
        }

        if(!optionalUser.get().getPass().equals(loginDTO.getPass())) {
            model.addAttribute("error", "Invalid password");
            return "login";
        }

        session.setAttribute("userId", optionalUser.get().getId());
        return "redirect:/api/users/home";
    }

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");

        if(userId == null){
            return "redirect:/api/users/login";
        }

        Optional<UserEntity> optionalUser = userService.findUserById(userId);
        if (optionalUser.isEmpty()) {
            return "redirect:/api/users/login";
        }

        UserEntity user = optionalUser.get();
        int count = user.getContacts().size();
        model.addAttribute("user", user);
        model.addAttribute("contactCount", count);
        return "home";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session){
        session.invalidate();
        return "redirect:/api/users/login";
    }
}
