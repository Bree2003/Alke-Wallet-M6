package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void createUser(UserEntity user);
    void updateUser(UserEntity user, Long id);
    void deleteUserById(Long id);
    Optional<UserEntity> findUserById(Long id);
    List<UserEntity> findAllUsers();
    Optional<UserEntity> findUserByUsername(String username);
    Optional<UserEntity> findUserByEmail(String email);

}
