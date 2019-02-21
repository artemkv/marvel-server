package net.artemkv.marvelserver;

import net.artemkv.marvelserver.MarvelConnector.ExternalServiceUnavailableException;
import net.artemkv.marvelserver.MarvelConnector.GetCreatorsResult;
import net.artemkv.marvelserver.MarvelConnector.IntegrationException;
import net.artemkv.marvelserver.MarvelConnector.TimeoutException;

import java.util.Date;

public interface MarvelService {
    GetCreatorsResult getCreators(Date modifiedSince, int offset)
        throws IntegrationException, ExternalServiceUnavailableException, TimeoutException;
}
