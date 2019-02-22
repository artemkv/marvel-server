package net.artemkv.marvelconnector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains data returned by Marvel API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class CreatorData implements Creator {
    private static class ComicsData {
        @JsonProperty("available")
        private int available;
    }

    private static class SeriesData {
        @JsonProperty("available")
        private int available;
    }

    private static final DateFormat formatter;
    // This is the earliest date you can observe when using modifiedSince filter on Marvel API
    // So any date before this one will be considered null
    // This will allow to retrieve all the creators, even if the date is messed up
    private static final Date earliestDateCanFilterBy;
    static {
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            earliestDateCanFilterBy = formatter.parse("2007-01-01T00:00:00-0500");
        } catch (ParseException e) {
            throw new IllegalStateException("Impossible");
        }
    }

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String modified;

    @JsonProperty("comics")
    private ComicsData comics;
    @JsonProperty("series")
    private ComicsData series;

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("middleName")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getModified() {
        Date modifiedDate = null;

        if (modified != null) {
            try {
                modifiedDate = formatter.parse(modified);
                if (modifiedDate.before(earliestDateCanFilterBy)) {
                    modifiedDate = null;
                }
            } catch (ParseException e) {
                // Ignore
            }
        }
        return modifiedDate;
    }

    @JsonProperty("modified")
    public String getModifiedText() {
        return modified;
    }

    public void setModifiedText(String modifiedText) {
        this.modified = modifiedText;
    }

    public int getComicsTotal() {
        return comics.available;
    }

    public int getSeriesTotal() {
        return series.available;
    }
}
