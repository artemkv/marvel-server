package net.artemkv.marvelserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"net.artemkv.marvelconnector", "net.artemkv.marvelserver"})
public class MarvelServerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MarvelServerApplication.class, args);
        LocalDbUpdater dbUpdater = context.getBean(LocalDbUpdater.class);
        dbUpdater.update();
    }
}
