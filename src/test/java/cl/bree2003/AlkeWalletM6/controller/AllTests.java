package cl.bree2003.AlkeWalletM6.controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ContactControllerTest.class, TransactionControllerTest.class, UserControllerTest.class })
public class AllTests {

}
