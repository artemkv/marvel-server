package net.artemkv.marvelserver.stubs;

import io.reactivex.Observable;
import net.artemkv.marvelconnector.Creator;
import net.artemkv.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelconnector.IntegrationException;
import net.artemkv.marvelconnector.MarvelApiRepository;
import net.artemkv.marvelconnector.TimeoutException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MarvelApiRepositoryStub implements MarvelApiRepository {
    @Override
    public Observable<Creator> getCreators(Date modifiedSince) throws IntegrationException {

        Calendar calendar = new GregorianCalendar();

        Date date1 = null;
        Creator c1 = new CreatorStub(111, "Creator 1", date1, 25, 60);

        calendar.set(2019, 01, 05, 0, 0, 0);
        Date date2 = calendar.getTime();
        Creator c2 = new CreatorStub(222, "Creator 2", date2, 15, 32);

        calendar.set(2019, 01, 10, 0, 0, 0);
        Date date3 = calendar.getTime();
        Creator c3 = new CreatorStub(333, "Creator 3", date3, 1, 888);

        return Observable.fromArray(new Creator[] { c1, c2, c3 });
    }
}
