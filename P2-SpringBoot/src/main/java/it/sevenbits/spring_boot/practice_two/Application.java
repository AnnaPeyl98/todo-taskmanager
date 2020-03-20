package it.sevenbits.spring_boot.practice_two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for starting application
 */
@SpringBootApplication
public class Application {
    /**
     * Method for start
     *
     * @param args - console arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}