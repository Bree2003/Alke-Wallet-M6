package cl.bree2003.AlkeWalletM6.service.impl;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.UserRepository;
import cl.bree2003.AlkeWalletM6.service.IAuthService;
import cl.bree2003.AlkeWalletM6.service.IJWTUtilityService;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidation userValidation;

    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        try{
            HashMap<String, String> jwt = new HashMap<>();
            Optional<UserEntity> user = userRepository.findUserByEmail(login.getEmail());

            if(user.isEmpty()) {
                jwt.put("error", "User not registered!");
                return jwt;
            }

            //verificar la contraseña
            if(verifyPassword(login.getPassword(), user.get().getPass())) {
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
            } else {
                jwt.put("error", "Authentication failed!");
            }

            return jwt;
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @Override
    public ResponseDTO register(UserEntity user) throws Exception {
        try {
            ResponseDTO response = userValidation.validate(user);

            if(response.getNumOfErrors() > 0) {
                return response;
            }

            List<UserEntity> getAllUsers = userRepository.findAll();

            for (UserEntity existingUser : getAllUsers) {
                if (existingUser.getEmail().equals(user.getEmail())) {
                    response.setNumOfErrors(1);
                    response.setMessage("Email already exists!");
                    return response;
                }
            }
//            for(UserEntity repeatFields : getAllUsers) {
//                if(repeatFields != null) {
//                    response.setNumOfErrors(1);
//                    response.setMessage("User already exists!");
//                    return response;
//                }
//            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPass(encoder.encode(user.getPass()));

            userRepository.save(user);

            response.setMessage("User created successfully!");

            return response;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(enteredPassword, storedPassword);
    }
}
