package cl.bree2003.AlkeWalletM6.service.impl;


import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.UserRepository;
import cl.bree2003.AlkeWalletM6.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void updateUser(UserEntity user, Long id) {
        Optional<UserEntity> optionalUser = findUserById(id);
        if(optionalUser.isPresent()) {
            UserEntity updateUser = optionalUser.get();
            updateUser.setName(user.getName());
            updateUser.setUsername(user.getUsername());
            updateUser.setEmail(user.getEmail());
            updateUser.setPass(user.getPass());

            userRepository.save(updateUser);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void depositMoney(Double amount, Long id) {
        Optional<UserEntity> optionalUser = findUserById(id);
        if(optionalUser.isPresent()) {
            UserEntity updateUser = optionalUser.get();
            Double total = updateUser.getBalance() + amount;
            updateUser.setBalance(total);

            userRepository.save(updateUser);
        }
    }

    @Override
    public void withdrawMoney(Double amount, Long id) {
        Optional<UserEntity> optionalUser = findUserById(id);
        if(optionalUser.isPresent()) {
            UserEntity updateUser = optionalUser.get();

            if(amount <= updateUser.getBalance()){
                Double total = updateUser.getBalance() - amount;
                updateUser.setBalance(total);

                userRepository.save(updateUser);
            } else {
                System.out.println("The amount is greater than the balance");
            }
        }
    }

    @Override
    public void transferMoney(Double amount, Long userId, Long contactId) {

    }
}
