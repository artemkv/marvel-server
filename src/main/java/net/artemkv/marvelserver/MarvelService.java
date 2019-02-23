package net.artemkv.marvelserver;

import net.artemkv.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelconnector.IntegrationException;
import net.artemkv.marvelconnector.TimeoutException;
import java.util.Date;

/**
 * Provides an access to the Marvel repository.
 */
public interface MarvelService {
    GetCreatorsResult getCreators(Date modifiedSince, int offset)
        throws IntegrationException, ExternalServiceUnavailableException, TimeoutException;
}
