package cl.bree2003.AlkeWalletM6.controller.rest;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/transactions")
public class TransactionRestController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionEntity transaction){
        try {
            transactionService.createTransaction(transaction);
            return new ResponseEntity<>("Transaction created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create transaction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        Optional<TransactionEntity> optionalTransaction = transactionService.findTransactionById(id);
        if(optionalTransaction.isPresent()){
            transactionService.deleteTransaction(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TransactionEntity> findTransactionById(@PathVariable Long id){
        Optional<TransactionEntity> optionalTransaction = transactionService.findTransactionById(id);
        return optionalTransaction.map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionEntity>> getAllTransactions(){
        List<TransactionEntity> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionEntity>> getAllTransactionsByUserId(@PathVariable Long userId){
        List<TransactionEntity> transactions = transactionService.findAllTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/deposit")
    public ResponseEntity<List<TransactionEntity>> getAllDepositTransactionsByUserId(@PathVariable Long userId){
        List<TransactionEntity> transactions = transactionService.findAllDepositTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/withdraw")
    public ResponseEntity<List<TransactionEntity>> getAllWithdrawTransactionsByUserId(@PathVariable Long userId){
        List<TransactionEntity> transactions = transactionService.findAllWithdrawTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/transfer")
    public ResponseEntity<List<TransactionEntity>> getAllTransferTransactionsByUserId(@PathVariable Long userId){
        List<TransactionEntity> transactions = transactionService.findAllTransferTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
