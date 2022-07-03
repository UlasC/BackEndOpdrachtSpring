package ulas1.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CustomBeans {

    //Met de BCryptPasswordEncoder kan het wachtwoord van iemand die inlogt
    // worden vergeleken met het geëncodeerde wachtwoord in de database
    //De bean wordt ook gebruikt wanneer iemand zijn wachtwoord wijzigt
    @Bean
    public BCryptPasswordEncoder getBcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
