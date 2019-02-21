package net.artemkv.marvelconnector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class CreatorDataContainer {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<CreatorData> results;

    @JsonProperty("results")
    public List<CreatorData> getResults() {
        return results;
    }
    public void setResults(List<CreatorData> results) {
        this.results = results;
    }

    @JsonProperty("offset")
    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @JsonProperty("limit")
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @JsonProperty("total")
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    @JsonProperty("count")
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
