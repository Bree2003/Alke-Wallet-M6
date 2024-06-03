package cl.bree2003.AlkeWalletM6.controller;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidation userValidation;

    @GetMapping("/register")
    public String recordPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "/users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserEntity user, Model model) {
        user.setBalance(500.27);
        ResponseDTO validationResponse = userValidation.validate(user);
        if(validationResponse.getNumOfErrors() > 0) {
            model.addAttribute("errors", validationResponse.getMessage());
            return "/users/register";
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = {"/login", "/"})
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        model.addAttribute("loginDTO", new LoginDTO());
        return "/users/login";
    }

    @GetMapping("/access")
    public String access(HttpSession session) {
        Optional<UserEntity> optionalUser = userService.findUserById(Long.parseLong(session.getAttribute("user_session_id").toString()));

        if (optionalUser.isPresent()) {
            session.setAttribute("user_session_id", optionalUser.get().getId());
            return "redirect:/alke/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/alke/home")
    public String homePage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_session_id");
        UserEntity user = userService.findUserById(userId).get();
        int count = user.getContacts().size();
        model.addAttribute("contactCount", count);
        return "/alke/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}