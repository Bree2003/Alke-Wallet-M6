package cl.bree2003.AlkeWalletM6.service;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    void createTransaction(TransactionEntity transaction);
    void deleteTransaction(Long id);
    List<TransactionEntity> findAllTransactions();
    List<TransactionEntity> findAllTransactionsByUserId(Long userId);
    List<TransactionEntity> findAllWithdrawTransactionsByUserId(Long userId);
    List<TransactionEntity> findAllDepositTransactionsByUserId(Long userId);
    List<TransactionEntity> findAllTransferTransactionsByUserId(Long userId);
    Optional<TransactionEntity> findTransactionById(Long id);
    Optional<UserEntity> findUserByEmail(String email);
}
