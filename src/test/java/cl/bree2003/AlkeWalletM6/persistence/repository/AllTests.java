package cl.bree2003.AlkeWalletM6.persistence.repository;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ContactRepositoryTest.class, TransactionRepositoryTest.class, UserRepositoryTest.class })
public class AllTests {

}
