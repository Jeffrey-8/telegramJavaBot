package application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication
@ComponentScan(basePackages = "bot")
@EnableJpaRepositories(basePackages = "repositories")
@EntityScan(basePackages = "models")
public class Application {
    public static void main(String[] args){

        ApiContextInitializer.init();
        SpringApplication.run(Application.class);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
