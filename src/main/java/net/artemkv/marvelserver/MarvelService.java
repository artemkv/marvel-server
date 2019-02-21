package net.artemkv.marvelserver;

import net.artemkv.marvelserver.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelserver.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelserver.marvelconnector.IntegrationException;
import net.artemkv.marvelserver.marvelconnector.TimeoutException;
import java.util.Date;

public interface MarvelService {
    GetCreatorsResult getCreators(Date modifiedSince, int offset)
        throws IntegrationException, ExternalServiceUnavailableException, TimeoutException;
}
