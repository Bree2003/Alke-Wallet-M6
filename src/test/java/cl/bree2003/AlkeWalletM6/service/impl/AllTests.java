package cl.bree2003.AlkeWalletM6.service.impl;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ContactServiceImplTest.class, TransactionServiceImplTest.class, UserDetailsServiceImplTest.class,
		UserServiceImplTest.class })
public class AllTests {

}
