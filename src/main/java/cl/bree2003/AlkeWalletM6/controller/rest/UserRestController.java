package cl.bree2003.AlkeWalletM6.controller.rest;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserValidation userValidation;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserEntity user){
        ResponseDTO validationResponse = userValidation.validate(user);
        if(validationResponse.getNumOfErrors() > 0){
            return new ResponseEntity<>(validationResponse, HttpStatus.BAD_REQUEST);
        }

        userService.createUser(user);

        return  new ResponseEntity<>(validationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO){
        Optional<UserEntity> optionalUser = userService.findUserByUsername(loginDTO.getUsername());
        if(optionalUser.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if(!optionalUser.get().getPass().equals(loginDTO.getPass())){
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

    @PostMapping("/new")
    private ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserEntity user, @PathVariable Long id){
        Optional<UserEntity> optionalUser = userService.findUserById(id);
        if(optionalUser.isPresent()){
        ResponseDTO validationResponse = userValidation.validate(user);
            if(validationResponse.getNumOfErrors() > 0){
                return new ResponseEntity<>(validationResponse, HttpStatus.BAD_REQUEST);
            }
            userService.updateUser(user, id);
            return new ResponseEntity<>(validationResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        Optional<UserEntity> optionalUser = userService.findUserById(id);
        if(optionalUser.isPresent()){
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    private ResponseEntity<List<UserEntity>> finAllUsers(){
        List<UserEntity> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserEntity> findUserById(@PathVariable Long id){
        Optional<UserEntity> optionalUser = userService.findUserById(id);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntity> findUserByUsername(@PathVariable String username){
        Optional<UserEntity> optionalUser = userService.findUserByUsername(username);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> findUserByEmail(@PathVariable String email){
        Optional<UserEntity> optionalUser = userService.findUserByEmail(email);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
