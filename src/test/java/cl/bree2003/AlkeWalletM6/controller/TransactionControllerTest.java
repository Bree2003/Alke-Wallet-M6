package cl.bree2003.AlkeWalletM6.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEnum;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.service.ITransactionService;
import cl.bree2003.AlkeWalletM6.service.model.dto.ResponseDTO;
import cl.bree2003.AlkeWalletM6.service.model.validation.TransactionValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private ITransactionService transactionService;

    @Mock
    private TransactionValidation transactionValidation;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testShowDepositTransactionForm() {
        // Arrange
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user_session_id", 1L);
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(transactionService.findUserById(1L)).thenReturn(Optional.of(user));

        // Act
        String viewName = transactionController.showDepositTransactionForm(mock(Model.class), session);

        // Assert
        assertEquals("/alke/operations/deposit", viewName);
    }

   
    @Test
    public void testShowWithdrawTransactionForm() {
        // Arrange
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user_session_id", 1L);
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(transactionService.findUserById(1L)).thenReturn(Optional.of(user));

        // Act
        String viewName = transactionController.showWithdrawTransactionForm(mock(Model.class), session);

        // Assert
        assertEquals("/alke/operations/withdraw", viewName);
    }


    @Test
    public void testShowTransferTransactionForm() {
        // Arrange
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user_session_id", 1L);
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(transactionService.findUserById(1L)).thenReturn(Optional.of(user));

        // Act
        String viewName = transactionController.showTransferTransactionForm(mock(Model.class), session);

        // Assert
        assertEquals("/alke/operations/transfer", viewName);
    }



    @Test
    public void testShowTransactionsByUser() {
        // Arrange
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user_session_id", 1L);

        // Act
        String viewName = transactionController.showTransactionsByUser(mock(Model.class), session);

        // Assert
        assertEquals("/alke/all-transactions", viewName);
    }
}
