package net.artemkv.marvelconnector;

import io.reactivex.Observable;

import java.util.Date;

public interface MarvelApiRepository {
    Observable<Creator> getCreators(Date modifiedSince) throws IntegrationException;
}
