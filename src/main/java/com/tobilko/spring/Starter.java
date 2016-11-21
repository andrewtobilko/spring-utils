package com.tobilko.spring;

import com.tobilko.spring.scope.MyScope;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.boot.SpringApplication.run;


/**
 *
 * Created by Andrew Tobilko on 23/09/16.
 *
 */
@SpringBootApplication
public class Starter {

    public static void main(String[] args) {
        run(Starter.class, args);
    }

    private @Autowired ConfigurableBeanFactory factory;

    public @Bean ApplicationRunner getRunner() {
        return (arguments) -> {

            factory.registerScope("super-scope", new MyScope());
            System.out.println("the scope is registered");


        };
    }

}

@Data
@Builder
@NoArgsConstructor(force = true)
class User {
    private String id;
    private String firstName;
    private String lastName;

    public User(String a, String b, String c, String bd, String d) {

    }

}