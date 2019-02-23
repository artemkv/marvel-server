package net.artemkv.marvelserver.stubs;

import net.artemkv.marvelconnector.Creator;
import net.artemkv.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelconnector.IntegrationException;
import net.artemkv.marvelconnector.TimeoutException;
import net.artemkv.marvelserver.MarvelService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MarvelServiceStub implements MarvelService {
    @Override
    public GetCreatorsResult getCreators(Date modifiedSince, int offset)
        throws IntegrationException, ExternalServiceUnavailableException, TimeoutException {

        Calendar calendar = new GregorianCalendar();

        Date date1 = null;
        Creator c1 = new CreatorStub(111, "Creator 1", date1, 25, 60);

        calendar.set(2019, 01, 05, 0, 0, 0);
        Date date2 = calendar.getTime();
        Creator c2 = new CreatorStub(222, "Creator 2", date2, 15, 32);

        calendar.set(2019, 01, 10, 0, 0, 0);
        Date date3 = calendar.getTime();
        Creator c3 = new CreatorStub(333, "Creator 3", date3, 1, 888);

        GetCreatorsResult resultPage1 = new GetCreatorsResult() {
            @Override
            public List<Creator> getCreators() {
                List<Creator> creators = new ArrayList<>();
                creators.add(c1);
                creators.add(c2);
                return creators;
            }

            @Override
            public boolean hasMore() {
                return true;
            }

            @Override
            public int getNewOffset() {
                return 2;
            }
        };

        GetCreatorsResult resultPage2 = new GetCreatorsResult() {
            @Override
            public List<Creator> getCreators() {
                List<Creator> creators = new ArrayList<>();
                creators.add(c3);
                return creators;
            }

            @Override
            public boolean hasMore() {
                return false;
            }

            @Override
            public int getNewOffset() {
                return 3;
            }
        };

        GetCreatorsResult resultPage3 = new GetCreatorsResult() {
            @Override
            public List<Creator> getCreators() {
                List<Creator> creators = new ArrayList<>();
                return creators;
            }

            @Override
            public boolean hasMore() {
                return false;
            }

            @Override
            public int getNewOffset() {
                return offset;
            }
        };

        if (offset == 0) {
            return resultPage1;
        } else if (offset == 2) {
            return resultPage2;
        }
        return resultPage3;
    }
}
