package cl.bree2003.AlkeWalletM6.persistence.repository;

import cl.bree2003.AlkeWalletM6.persistence.entity.ContactEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findUserByEmail(String email);
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findUserById(Long id);
    @Query("SELECT c FROM ContactEntity c WHERE c.user.id = :userId")
    List<ContactEntity> findAllContactsByUserId(Long userId);
    @Query("SELECT c FROM ContactEntity c WHERE c.email = :email AND c.user.id = :userId")
    Optional<ContactEntity> findContactByEmailByUserId(String email, Long userId);
    @Query("SELECT c FROM ContactEntity c WHERE c.username = :username AND c.user.id = :userId")
    Optional<ContactEntity> findContactByUsernameByUserId(String username, Long userId);
}
