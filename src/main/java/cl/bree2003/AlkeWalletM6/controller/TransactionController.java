package cl.bree2003.AlkeWalletM6.controller;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEnum;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.ITransactionService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.TransactionValidation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private TransactionValidation transactionValidation;

    @GetMapping("/deposit")
    public String showDepositTransactionForm(Model model, HttpSession session){
        Long id = (Long) session.getAttribute("userId");

        if(id == null){
            return "redirect:/api/users/login";
        }

        Optional<UserEntity> optionalUser = transactionService.findUserById(id);
        if (optionalUser.isEmpty()) {
            return "redirect:/api/users/login";
        }
        UserEntity user = optionalUser.get();

        model.addAttribute("user", user);
        model.addAttribute("transaction", new TransactionEntity());

        return "deposit";
    }

    @GetMapping("/withdraw")
    public String showWithdrawTransactionForm(Model model, HttpSession session){
        Long id = (Long) session.getAttribute("userId");

        if(id == null){
            return "redirect:/api/users/login";
        }

        Optional<UserEntity> optionalUser = transactionService.findUserById(id);
        if (optionalUser.isEmpty()) {
            return "redirect:/api/users/login";
        }
        UserEntity user = optionalUser.get();

        model.addAttribute("user", user);
        model.addAttribute("transaction", new TransactionEntity());

        return "withdraw";
    }

    @GetMapping("/transfer")
    public String showTransferTransactionForm(Model model, HttpSession session){
        Long id = (Long) session.getAttribute("userId");

        if(id == null){
            return "redirect:/api/users/login";
        }

        Optional<UserEntity> optionalUser = transactionService.findUserById(id);
        if (optionalUser.isEmpty()) {
            return "redirect:/api/users/login";
        }
        UserEntity user = optionalUser.get();

        model.addAttribute("user", user);
        model.addAttribute("transaction", new TransactionEntity());

        return "transfer";
    }

    @PostMapping("/deposit")
    public String createDepositTransaction(@ModelAttribute("transaction") TransactionEntity transaction, Model model, HttpSession session){

        Long id = (Long) session.getAttribute("userId");
        UserEntity user = transactionService.findUserById(id).get();
        transaction.setEmailSender(user.getEmail());
        transaction.setType(TransactionEnum.DEPOSIT);
        ResponseDTO validationResponse = transactionValidation.validate(transaction);

        if(validationResponse.getNumOfErrors() > 0){
            model.addAttribute("errors", validationResponse.getMessage());
            return "deposit";
        }

        transactionService.createTransaction(transaction);
        return "redirect:/api/transactions/all-your-transactions";
    }

    @PostMapping("/withdraw")
    public String createWithdrawTransaction(@ModelAttribute("transaction") TransactionEntity transaction, Model model, HttpSession session){

        Long id = (Long) session.getAttribute("userId");
        UserEntity user = transactionService.findUserById(id).get();
        transaction.setEmailSender(user.getEmail());
        transaction.setType(TransactionEnum.WITHDRAW);
        ResponseDTO validationResponse = transactionValidation.validate(transaction);

        if(validationResponse.getNumOfErrors() > 0){
            model.addAttribute("errors", validationResponse.getMessage());
            return "withdraw";
        }

        transactionService.createTransaction(transaction);
        return "redirect:/api/transactions/all-your-transactions";
    }

    @PostMapping("/transfer")
    public String createTransferTransaction(@ModelAttribute("transaction") TransactionEntity transaction, Model model, HttpSession session){

        Long id = (Long) session.getAttribute("userId");
        UserEntity user = transactionService.findUserById(id).get();
        transaction.setEmailSender(user.getEmail());
        transaction.setType(TransactionEnum.TRANSFER);

        ResponseDTO validationResponse = transactionValidation.validate(transaction);

        if(validationResponse.getNumOfErrors() > 0){
            model.addAttribute("user", user);
            model.addAttribute("errors", validationResponse.getMessage());
            return "transfer";
        }

        transactionService.createTransaction(transaction);
        return "redirect:/api/transactions/all-your-transactions";
    }

    @GetMapping("/all-your-transactions")
    public String showTransactionsByUser(Model model, HttpSession session){
        Long id = (Long) session.getAttribute("userId");

        if(id == null){
            return "redirect:/api/users/login";
        }

        Optional<UserEntity> optionalUser = transactionService.findUserById(id);
        if (optionalUser.isEmpty()) {
            return "redirect:/api/users/login";
        }

        List<TransactionEntity> transactionsByUser = transactionService.findAllTransactionsByUserId(id);

        model.addAttribute("transactions", transactionsByUser);
        return "all-transactions";
    }
}
