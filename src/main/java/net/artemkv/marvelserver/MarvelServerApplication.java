package net.artemkv.marvelserver;

import net.artemkv.marvelserver.MarvelConnector.Creator;
import net.artemkv.marvelserver.MarvelConnector.ExternalServiceUnavailableException;
import net.artemkv.marvelserver.MarvelConnector.GetCreatorsResult;
import net.artemkv.marvelserver.MarvelConnector.IntegrationException;
import net.artemkv.marvelserver.MarvelConnector.MarvelApiRepository;
import net.artemkv.marvelserver.MarvelConnector.TimeoutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class MarvelServerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MarvelServerApplication.class, args);

        MarvelApiRepository repo = context.getBean(MarvelApiRepository.class);
        try {
            GetCreatorsResult result = repo.getCreators(new Date(Long.MIN_VALUE), 0);
            for (Creator c : result.getCreators()) {
                System.out.println(c.getFullName());
            }
        } catch (IntegrationException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExternalServiceUnavailableException e) {
            e.printStackTrace();
        }
    }
}
