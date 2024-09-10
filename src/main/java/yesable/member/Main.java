package yesable.member;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world!");
        SpringApplication.run(Main.class,args);

        System.out.println("\n\n\n\n\n asdf");




    }
}