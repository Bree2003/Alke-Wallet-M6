package cl.bree2003.AlkeWalletM6.service.model.validation;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEnum;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.ITransactionService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionValidation {

    @Autowired
    private ITransactionService transactionService;

    public ResponseDTO validate(TransactionEntity transaction){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setNumOfErrors(0);
        if(transaction.getType().equals(TransactionEnum.DEPOSIT) || transaction.getType().equals(TransactionEnum.WITHDRAW)){
            transaction.setEmailReceiver(transaction.getEmailSender());
        }
        System.out.println(transaction);
        if(transaction.getType().equals(TransactionEnum.TRANSFER) && transaction.getEmailReceiver().equals("")){
            System.out.println("en null "+transaction);
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("The contacts is not valid.");
        }

        if(transaction.getType().equals(TransactionEnum.TRANSFER) || transaction.getType().equals(TransactionEnum.WITHDRAW)){
            UserEntity user = transactionService.findUserByEmail(transaction.getEmailSender()).get();
            if(user.getBalance() < transaction.getTotal()){
                responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
                responseDTO.setMessage("The amount exceeds the balance.");
            }
        }

        return responseDTO;
    }
}
