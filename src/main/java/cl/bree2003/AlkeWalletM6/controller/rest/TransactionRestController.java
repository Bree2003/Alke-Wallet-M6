package cl.bree2003.AlkeWalletM6.controller.rest;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<TransactionEntity>> getAllTransactions(){
        List<TransactionEntity> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TransactionEntity>> getAllTransactionsByUser(@PathVariable Long id){
        UserEntity user = transactionService.findUserById(id).get();
        List<TransactionEntity> transactions = transactionService.findAllTransactionsByUser(user);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/deposit")
    public ResponseEntity<List<TransactionEntity>> getAllDepositTransactionsByUser(@PathVariable Long id){
        UserEntity user = transactionService.findUserById(id).get();
        List<TransactionEntity> transactions = transactionService.findAllDepositTransactionsByUser(user);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/withdraw")
    public ResponseEntity<List<TransactionEntity>> getAllWithdrawTransactionsByUser(@PathVariable Long id){
        UserEntity user = transactionService.findUserById(id).get();
        List<TransactionEntity> transactions = transactionService.findAllWithdrawTransactionsByUser(user);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/transfer")
    public ResponseEntity<List<TransactionEntity>> getAllTransferTransactionsByUser(@PathVariable Long id){
        UserEntity user = transactionService.findUserById(id).get();
        List<TransactionEntity> transactions = transactionService.findAllTransferTransactionsByUser(user);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
