package net.artemkv.marvelserver;

import net.artemkv.marvelserver.marvelconnector.Creator;
import net.artemkv.marvelserver.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelserver.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelserver.marvelconnector.IntegrationException;
import net.artemkv.marvelserver.marvelconnector.TimeoutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class MarvelServerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MarvelServerApplication.class, args);

        MarvelService svc = context.getBean(MarvelService.class);
        try {
            GetCreatorsResult result = svc.getCreators(new Date(Long.MIN_VALUE), 0);
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
