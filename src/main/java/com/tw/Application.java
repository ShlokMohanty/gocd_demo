package com.tw;

import com.tw.application.entities.User;
import com.tw.application.repositories.UserRepository;
import java.util.stream.Stream;

import com.tw.ecommerce.model.Product;
import com.tw.ecommerce.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, ProductService productService) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel", "Ashle", "Kavin", "Hive").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);

            productService.save(new Product(1L, "TV Set", 300.00, "http://placehold.it/200x100"));
            productService.save(new Product(2L, "Game Console", 200.00, "http://placehold.it/200x100"));
            productService.save(new Product(3L, "Sofa", 100.00, "http://placehold.it/200x100"));
            productService.save(new Product(4L, "Icecream", 5.00, "http://placehold.it/200x100"));
            productService.save(new Product(5L, "Beer", 3.00, "http://placehold.it/200x100"));
            productService.save(new Product(6L, "Phone", 500.00, "http://placehold.it/200x100"));
            productService.save(new Product(7L, "Watch", 30.00, "http://placehold.it/200x100"));
        };
    }
}
