package cl.bree2003.AlkeWalletM6.controller;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String shorwRegister(){
        return "/user/register";
    }

    @PostMapping("/register")
    public String registerUser(UserEntity user){
        user.setBalance(500.27);
        user.setPass(passwordEncoder.encode(user.getPass()));
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping(value = {"/login", "/"})
    public String showLogin(){
        return "/user/login";
    }

    @GetMapping("access")
    public String access(HttpSession session){
        Long id = (Long) session.getAttribute("userId");
        Optional<UserEntity> optionalUser = userService.findUserById(id);

        if(optionalUser.isPresent()){
            session.setAttribute("userId", id);
            return "redirect:/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/home")
    public String homePage(Model model){
        return "/user/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        return "redirect:/login";
    }
}
