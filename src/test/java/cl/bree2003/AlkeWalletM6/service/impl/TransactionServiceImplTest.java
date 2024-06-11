package cl.bree2003.AlkeWalletM6.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEnum;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import cl.bree2003.AlkeWalletM6.persistence.repository.TransactionRepository;

class TransactionServiceImplTest {
	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateTransaction_Deposit() {
		UserEntity sender = new UserEntity();
		sender.setEmail("sender@example.com");
		sender.setBalance(100.0);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setEmailSender("sender@example.com");
		transaction.setEmailReceiver("sender@example.com");
		transaction.setTotal(50.0);
		transaction.setType(TransactionEnum.DEPOSIT);

		when(transactionRepository.findUserByEmail("sender@example.com")).thenReturn(Optional.of(sender));
		when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transaction);

		transactionService.createTransaction(transaction);

		verify(transactionRepository, times(1)).save(transaction);
		assertEquals(150.0, sender.getBalance());
	}

	@Test
	void testCreateTransaction_Withdraw() {
		UserEntity sender = new UserEntity();
		sender.setEmail("sender@example.com");
		sender.setBalance(100.0);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setEmailSender("sender@example.com");
		transaction.setEmailReceiver("sender@example.com");
		transaction.setTotal(50.0);
		transaction.setType(TransactionEnum.WITHDRAW);

		when(transactionRepository.findUserByEmail("sender@example.com")).thenReturn(Optional.of(sender));
		when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transaction);

		transactionService.createTransaction(transaction);

		verify(transactionRepository, times(1)).save(transaction);
		assertEquals(50.0, sender.getBalance());
	}

	@Test
	void testCreateTransaction_Transfer() {
		UserEntity sender = new UserEntity();
		sender.setEmail("sender@example.com");
		sender.setBalance(100.0);

		UserEntity receiver = new UserEntity();
		receiver.setEmail("receiver@example.com");
		receiver.setBalance(50.0);

		TransactionEntity transaction = new TransactionEntity();
		transaction.setEmailSender("sender@example.com");
		transaction.setEmailReceiver("receiver@example.com");
		transaction.setTotal(50.0);
		transaction.setType(TransactionEnum.TRANSFER);

		when(transactionRepository.findUserByEmail("sender@example.com")).thenReturn(Optional.of(sender));
		when(transactionRepository.findUserByEmail("receiver@example.com")).thenReturn(Optional.of(receiver));
		when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transaction);

		transactionService.createTransaction(transaction);

		verify(transactionRepository, times(2)).save(any(TransactionEntity.class));
		assertEquals(50.0, sender.getBalance());
		assertEquals(100.0, receiver.getBalance());
	}

	@Test
	void testDeleteTransaction() {
		Long transactionId = 1L;
		doNothing().when(transactionRepository).deleteById(transactionId);

		transactionService.deleteTransaction(transactionId);

		verify(transactionRepository, times(1)).deleteById(transactionId);
	}

	@Test
	void testFindAllTransactions() {
		TransactionEntity transaction1 = new TransactionEntity();
		transaction1.setId(1L);
		TransactionEntity transaction2 = new TransactionEntity();
		transaction2.setId(2L);
		List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

		when(transactionRepository.findAll()).thenReturn(transactions);

		List<TransactionEntity> foundTransactions = transactionService.findAllTransactions();

		assertEquals(2, foundTransactions.size());
	}

	@Test
	void testFindAllTransactionsByUserId() {
		Long userId = 1L;
		TransactionEntity transaction1 = new TransactionEntity();
		transaction1.setId(1L);
		TransactionEntity transaction2 = new TransactionEntity();
		transaction2.setId(2L);
		List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

		when(transactionRepository.findAllTransactionsByUserId(userId)).thenReturn(transactions);

		List<TransactionEntity> foundTransactions = transactionService.findAllTransactionsByUserId(userId);

		assertEquals(2, foundTransactions.size());
	}

	@Test
	void testFindAllWithdrawTransactionsByUserId() {
		Long userId = 1L;
		TransactionEntity transaction1 = new TransactionEntity();
		transaction1.setId(1L);
		transaction1.setType(TransactionEnum.WITHDRAW);
		TransactionEntity transaction2 = new TransactionEntity();
		transaction2.setId(2L);
		transaction2.setType(TransactionEnum.WITHDRAW);
		List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

		when(transactionRepository.findAllWithdrawTransactionsByUserId(userId)).thenReturn(transactions);

		List<TransactionEntity> foundTransactions = transactionService.findAllWithdrawTransactionsByUserId(userId);

		assertEquals(2, foundTransactions.size());
	}

	@Test
	void testFindAllDepositTransactionsByUserId() {
		Long userId = 1L;
		TransactionEntity transaction1 = new TransactionEntity();
		transaction1.setId(1L);
		transaction1.setType(TransactionEnum.DEPOSIT);
		TransactionEntity transaction2 = new TransactionEntity();
		transaction2.setId(2L);
		transaction2.setType(TransactionEnum.DEPOSIT);
		List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

		when(transactionRepository.findAllDepositTransactionsByUserId(userId)).thenReturn(transactions);

		List<TransactionEntity> foundTransactions = transactionService.findAllDepositTransactionsByUserId(userId);

		assertEquals(2, foundTransactions.size());
	}

	@Test
	void testFindAllTransferTransactionsByUserId() {
		Long userId = 1L;
		TransactionEntity transaction1 = new TransactionEntity();
		transaction1.setId(1L);
		transaction1.setType(TransactionEnum.TRANSFER);
		TransactionEntity transaction2 = new TransactionEntity();
		transaction2.setId(2L);
		transaction2.setType(TransactionEnum.TRANSFER);
		List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

		when(transactionRepository.findAllTransferTransactionsByUserId(userId)).thenReturn(transactions);

		List<TransactionEntity> foundTransactions = transactionService.findAllTransferTransactionsByUserId(userId);

		assertEquals(2, foundTransactions.size());
	}

	@Test
	void testFindTransactionById() {
		Long transactionId = 1L;
		TransactionEntity transaction = new TransactionEntity();
		transaction.setId(transactionId);

		when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

		Optional<TransactionEntity> foundTransaction = transactionService.findTransactionById(transactionId);

		assertTrue(foundTransaction.isPresent());
		assertEquals(transactionId, foundTransaction.get().getId());
	}

	@Test
	void testFindUserByEmail() {
		String email = "user@example.com";
		UserEntity user = new UserEntity();
		user.setEmail(email);

		when(transactionRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

		Optional<UserEntity> foundUser = transactionService.findUserByEmail(email);

		assertTrue(foundUser.isPresent());
		assertEquals(email, foundUser.get().getEmail());
	}

	@Test
	void testFindUserById() {
		Long userId = 1L;
		UserEntity user = new UserEntity();
		user.setId(userId);

		when(transactionRepository.findUserById(userId)).thenReturn(Optional.of(user));

		Optional<UserEntity> foundUser = transactionService.findUserById(userId);

		assertTrue(foundUser.isPresent());
		assertEquals(userId, foundUser.get().getId());
	}
}
