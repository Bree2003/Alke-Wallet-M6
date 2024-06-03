package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.entity.UserEntity;

import java.util.Optional;

public interface IUserService {

    void createUser(UserEntity user);
    Optional<UserEntity> getUserById(Long id);
    Optional<UserEntity> getUserByUsername(String username);
}
