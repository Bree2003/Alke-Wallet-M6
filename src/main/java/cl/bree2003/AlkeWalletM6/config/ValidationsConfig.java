package cl.bree2003.AlkeWalletM6.config;

import cl.bree2003.AlkeWalletM6.service.model.validation.ContactValidation;
import cl.bree2003.AlkeWalletM6.service.model.validation.TransactionValidation;
import cl.bree2003.AlkeWalletM6.service.model.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationsConfig {

    @Bean
    public UserValidation userValidation() {
        return new UserValidation();
    }

    @Bean
    public ContactValidation contactValidation(){
        return new ContactValidation();
    }

    @Bean
    public TransactionValidation transactionValidation(){
        return new TransactionValidation();
    }
}
