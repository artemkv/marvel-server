package net.artemkv.marvelserver.marvelconnector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

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

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private Date modified;

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

    @JsonProperty("modified")
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }

    public int getComicsTotal() {
        return comics.available;
    }

    public int getSeriesTotal() {
        return series.available;
    }
}
