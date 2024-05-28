package cl.bree2003.AlkeWalletM6.service.impl;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.IAuthService;
import cl.bree2003.AlkeWalletM6.service.model.dto.LoginDTO;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements IAuthService {
    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        return null;
    }

    @Override
    public ResponseDTO register(UserEntity user) throws Exception {
        return null;
    }
}
