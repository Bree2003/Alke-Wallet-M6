package cl.bree2003.AlkeWalletM6.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEntity;
import cl.bree2003.AlkeWalletM6.persistence.entity.TransactionEnum;
import cl.bree2003.AlkeWalletM6.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	private UserEntity user;
	private TransactionEntity transactionD;
	private TransactionEntity transactionW;
	private TransactionEntity transactionT;

	@BeforeEach
	void setUp() {
		user = new UserEntity();
		user.setName("John Doe");
		user.setUsername("johndoe");
		user.setEmail("johndoe@example.com");
		user.setPassword("password");
		user.setBalance(100.00);
		userRepository.save(user);

		transactionD = new TransactionEntity();
		transactionD.setMessage("Test Transaction");
		transactionD.setType(TransactionEnum.DEPOSIT);
		transactionD.setEmailSender("johndoe@example.com");
		transactionD.setEmailReceiver("receiver@example.com");
		transactionD.setTotal(50.00);
		transactionD.setUser(user);
		transactionRepository.save(transactionD);

		transactionW = new TransactionEntity();
		transactionW.setMessage("Test Transaction");
		transactionW.setType(TransactionEnum.WITHDRAW);
		transactionW.setEmailSender("johndoe@example.com");
		transactionW.setEmailReceiver("receiver@example.com");
		transactionW.setTotal(50.00);
		transactionW.setUser(user);
		transactionRepository.save(transactionW);

		transactionT = new TransactionEntity();
		transactionT.setMessage("Test Transaction");
		transactionT.setType(TransactionEnum.TRANSFER);
		transactionT.setEmailSender("johndoe@example.com");
		transactionT.setEmailReceiver("receiver@example.com");
		transactionT.setTotal(50.00);
		transactionT.setUser(user);
		transactionRepository.save(transactionT);
	}

	@Test
	void testFindAllTransactionsByUserId() {
		List<TransactionEntity> transactions = transactionRepository.findAllTransactionsByUserId(user.getId());

		assertFalse(transactions.isEmpty());
		assertEquals(3, transactions.size());
		assertEquals(transactionD.getMessage(), transactions.get(0).getMessage());
	}

	@Test
	void testFindAllWithdrawTransactionsByUserId() {
//		transaction.setType(TransactionEnum.WITHDRAW);
//		transactionRepository.save(transaction);

		List<TransactionEntity> transactions = transactionRepository.findAllWithdrawTransactionsByUserId(user.getId());

		assertFalse(transactions.isEmpty());
		assertEquals(1, transactions.size());
		assertEquals(TransactionEnum.WITHDRAW, transactions.get(0).getType());
	}

	@Test
	void testFindAllDepositTransactionsByUserId() {
		List<TransactionEntity> transactions = transactionRepository.findAllDepositTransactionsByUserId(user.getId());

		assertFalse(transactions.isEmpty());
		assertEquals(1, transactions.size());
		assertEquals(TransactionEnum.DEPOSIT, transactions.get(0).getType());
	}

	@Test
	void testFindAllTransferTransactionsByUserId() {
//		transaction.setType(TransactionEnum.TRANSFER);
//		transactionRepository.save(transaction);
		System.out.println(transactionT.getType());
		List<TransactionEntity> transactions = transactionRepository.findAllTransferTransactionsByUserId(user.getId());
		System.out.println(transactions);
		assertFalse(transactions.isEmpty());
		assertEquals(1, transactions.size());
		assertEquals(TransactionEnum.TRANSFER, transactions.get(0).getType());
	}

}
