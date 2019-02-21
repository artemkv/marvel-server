package net.artemkv.marvelserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:marvelservice.properties")
@ConfigurationProperties(prefix="marvelservice")
public class MarvelServiceProperties {
    private int retries = 3;

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
