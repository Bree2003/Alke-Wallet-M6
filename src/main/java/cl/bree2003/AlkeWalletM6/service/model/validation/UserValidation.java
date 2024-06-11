package cl.bree2003.AlkeWalletM6.service.model.validation;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserValidation {

    @Autowired
    private IUserService userService;

    public ResponseDTO validate(UserEntity user) {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setNumOfErrors(0);

        if(user.getName().length() < 3){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("Name must contain more than 3 characters.");
        }

        if(user.getUsername().length() < 3){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("Username must contain more than 3 characters.");
        }

        Optional<UserEntity> existingUsername = userService.findUserByUsername(user.getUsername());
        if(existingUsername.isPresent()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("Username already exists.");
        }

        Optional<UserEntity> existingEmail = userService.findUserByEmail(user.getEmail());
        if(existingEmail.isPresent()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("Email already exists.");
        }

        if(user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("Email is not valid.");
        }

        if(user.getPassword() == null || !user.getPassword() .matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("Password must contain between 8 and 16 characters, at least a number, a lower case and a capital letter.");
        }

        return responseDTO;
    }

}
