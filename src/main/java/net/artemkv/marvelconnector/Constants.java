package net.artemkv.marvelconnector;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Constants {
    public static final String MARVEL_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final DateFormat MARVEL_API_DATE_FORMATTER =
        new SimpleDateFormat(Constants.MARVEL_API_DATE_FORMAT);

    // This is the earliest date you can observe when using modifiedSince filter on Marvel API
    // So any date before this one will be considered null
    // This will allow to retrieve all the creators, even if the date is messed up
    public static final Date MARVEL_API_EARLIEST_FILTERABLE_DATE;
    static {
        try {
            MARVEL_API_EARLIEST_FILTERABLE_DATE =
                MARVEL_API_DATE_FORMATTER.parse("2007-01-01T00:00:00-0500");
        } catch (ParseException e) {
            throw new IllegalStateException("Impossible");
        }
    }
}
