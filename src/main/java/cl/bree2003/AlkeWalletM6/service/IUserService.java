package cl.bree2003.AlkeWalletM6.service;



import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void createUser(UserEntity user);
    Optional<UserEntity> findUserById(Long id);
    Optional<UserEntity> findUserByUsername(String username);
    Optional<UserEntity> findUserByEmail(String email);
    void updateUser(UserEntity user, Long id);
    void deleteUserById(Long id);
    List<UserEntity> findAllUsers();
    void depositMoney(Double amount, Long id);
    void withdrawMoney(Double amount, Long id);
    void transferMoney(Double amount, Long userId, Long contactId);

}
