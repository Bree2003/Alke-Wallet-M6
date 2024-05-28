package cl.bree2003.AlkeWalletM6.controller.rest;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IAuthService;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    IAuthService authService;

    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> registerUser(@RequestBody UserEntity user) throws Exception {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<HashMap<String, String>> loginUser(@RequestBody LoginDTO loginDTO) throws Exception {
        HashMap<String, String> login = authService.login(loginDTO);

        if(login.containsKey("jwt")) {
            return new ResponseEntity<>(login, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }
    }

}
