package net.artemkv.marvelserver;

import net.artemkv.marvelserver.MarvelConnector.ExternalServiceUnavailableException;
import net.artemkv.marvelserver.MarvelConnector.GetCreatorsResult;
import net.artemkv.marvelserver.MarvelConnector.IntegrationException;
import net.artemkv.marvelserver.MarvelConnector.MarvelApiRepository;
import net.artemkv.marvelserver.MarvelConnector.TimeoutException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MarvelServiceImpl implements MarvelService {
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
                result = repository.getCreators(modifiedSince, offset);
            } catch (TimeoutException e) {
                // TODO: log
                if (retriesLeft == 0) {
                    throw e;
                }
                // TODO: log retrying
            } catch (ExternalServiceUnavailableException e) {
                // TODO: log
                if (retriesLeft == 0) {
                    throw e;
                }
                // TODO: log retrying
            }
        }
        return result;
    }
}
