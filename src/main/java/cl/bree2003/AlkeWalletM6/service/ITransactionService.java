package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    void createTransaction(TransactionEntity transaction);
    void deleteTransaction(Long id);
    List<TransactionEntity> findAllTransactions();
    List<TransactionEntity> findAllTransactionsByUser(UserEntity user);
    List<TransactionEntity> findAllWithdrawTransactionsByUser(UserEntity user);
    List<TransactionEntity> findAllDepositTransactionsByUser(UserEntity user);
    List<TransactionEntity> findAllTransferTransactionsByUser(UserEntity user);
    Optional<UserEntity> findUserById(Long id);
    Optional<UserEntity> findUserByEmail(String email);
}
