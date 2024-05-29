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
    public void createUser(UserEntity user){
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserEntity user, Long id) {
        Optional<UserEntity> optionalUser = findUserById(id);
        if(optionalUser.isPresent()){
            UserEntity existingUser = optionalUser.get();
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPass(user.getPass());

            userRepository.save(existingUser);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<UserEntity> optionalUser = findUserById(id);
        if(optionalUser.isPresent()){
            userRepository.deleteById(id);
        }
    }

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
