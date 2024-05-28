package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {

    public HashMap<String, String> login(LoginDTO login) throws Exception;
    public ResponseDTO register(UserEntity user) throws Exception;

}
