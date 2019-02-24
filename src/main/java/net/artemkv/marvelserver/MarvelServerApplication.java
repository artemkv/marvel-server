package net.artemkv.marvelserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"net.artemkv.marvelconnector", "net.artemkv.marvelserver"})
@EnableScheduling
public class MarvelServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarvelServerApplication.class, args);
    }
}
