package net.artemkv.marvelserver;

import net.artemkv.marvelserver.MarvelConnector.MarvelApiRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class MarvelServerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MarvelServerApplication.class, args);

        MarvelApiRepository repo = context.getBean(MarvelApiRepository.class);
        repo.getCreators(new Date(Long.MIN_VALUE), 0);
    }
}
