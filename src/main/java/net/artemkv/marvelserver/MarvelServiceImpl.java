package net.artemkv.marvelserver;

import net.artemkv.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelconnector.IntegrationException;
import net.artemkv.marvelconnector.MarvelApiRepository;
import net.artemkv.marvelconnector.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class MarvelServiceImpl implements MarvelService {
    private static final Logger logger = LoggerFactory.getLogger(MarvelServiceImpl.class);
    private final MarvelApiRepository repository;
    private final MarvelServiceProperties properties;

    public MarvelServiceImpl(MarvelApiRepository repository, MarvelServiceProperties properties) {
        if (repository == null) {
            throw new IllegalArgumentException("repository");
        }
        if (properties == null) {
            throw new IllegalArgumentException("properties");
        }

        this.repository = repository;
        this.properties = properties;
    }

    @Override
    public GetCreatorsResult getCreators(Date modifiedSince, int offset)
        throws IntegrationException, ExternalServiceUnavailableException, TimeoutException {
        int retriesLeft = properties.getRetries();
        GetCreatorsResult result = null;

        while (retriesLeft > 0) {
            try {
                retriesLeft--;
                logger.debug(String.format(
                    "Requesting creators modified since %s, at offset %d",
                    modifiedSince != null ? modifiedSince.toString() : "beginning of time",
                    offset));
                return repository.getCreators(modifiedSince, offset);
            } catch (TimeoutException e) {
                logger.error("Requesting creators timed out", e);
                if (retriesLeft == 0) {
                    throw e;
                }
                logger.debug("Retrying, retries left: " + retriesLeft);
            } catch (ExternalServiceUnavailableException e) {
                logger.error("Requesting creators failed", e);
                if (retriesLeft == 0) {
                    throw e;
                }
                logger.debug("Retrying, retries left: " + retriesLeft);
            }
        }
        throw new IllegalStateException("Impossible state");
    }
}
