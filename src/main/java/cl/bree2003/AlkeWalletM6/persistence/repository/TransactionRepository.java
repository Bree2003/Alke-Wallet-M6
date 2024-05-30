package cl.bree2003.AlkeWalletM6.persistence.repository;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t WHERE t.user = :user")
    List<TransactionEntity> findAllTransactionsByUser(UserEntity user);
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findUserByEmail(String email);
    @Query("SELECT t FROM TransactionEntity t WHERE t.user = :user AND t.type = 'WITHDRAW'")
    List<TransactionEntity> findAllWithdrawTransactionsByUser(UserEntity user);
    @Query("SELECT t FROM TransactionEntity t WHERE t.user = :user AND t.type = 'DEPOSIT'")
    List<TransactionEntity> findAllDepositTransactionsByUser(UserEntity user);
    @Query("SELECT t FROM TransactionEntity t WHERE t.user = :user AND t.type = 'TRANSFER'")
    List<TransactionEntity> findAllTransferTransactionsByUser(UserEntity user);
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findUserById(Long id);

}
