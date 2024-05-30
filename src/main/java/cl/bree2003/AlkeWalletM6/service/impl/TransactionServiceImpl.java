package cl.bree2003.AlkeWalletM6.service.impl;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.TransactionRepository;
import cl.bree2003.AlkeWalletM6.service.ITransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void createTransaction(TransactionEntity transaction) {
        UserEntity sender = findUserByEmail(transaction.getEmailSender()).get();
        UserEntity receiver = findUserByEmail(transaction.getEmailReceiver()).get();
        switch (transaction.getType()){
            case DEPOSIT -> {
                sender.setBalance(sender.getBalance() + transaction.getTotal());
                transaction.setUser(sender);
                transactionRepository.save(transaction);
            }
            case WITHDRAW -> {
                sender.setBalance(sender.getBalance() - transaction.getTotal());
                transaction.setUser(sender);
                transactionRepository.save(transaction);
            }
            case TRANSFER -> {

                sender.setBalance(sender.getBalance() - transaction.getTotal());
                transaction.setUser(sender);
                transactionRepository.save(transaction);

                TransactionEntity receiverTransaction = new TransactionEntity();
                receiverTransaction.setMessage(transaction.getMessage());
                receiverTransaction.setType(transaction.getType());
                receiverTransaction.setEmailSender(transaction.getEmailSender());
                receiverTransaction.setEmailReceiver(transaction.getEmailReceiver());
                receiverTransaction.setTotal(transaction.getTotal());
                receiverTransaction.setUser(receiver);
                receiver.setBalance(receiver.getBalance() + transaction.getTotal());
                transactionRepository.save(receiverTransaction);
            }
        }
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionEntity> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionEntity> findAllTransactionsByUserId(Long userId) {
        return transactionRepository.findAllTransactionsByUserId(userId);
    }

    @Override
    public List<TransactionEntity> findAllWithdrawTransactionsByUserId(Long userId) {
        return transactionRepository.findAllWithdrawTransactionsByUserId(userId);
    }

    @Override
    public List<TransactionEntity> findAllDepositTransactionsByUserId(Long userId) {
        return transactionRepository.findAllDepositTransactionsByUserId(userId);
    }

    @Override
    public List<TransactionEntity> findAllTransferTransactionsByUserId(Long userId) {
        return transactionRepository.findAllTransferTransactionsByUserId(userId);
    }

    @Override
    public Optional<TransactionEntity> findTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return transactionRepository.findUserByEmail(email);
    }

}
