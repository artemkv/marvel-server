package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import java.util.List;

public class GetCreatorsResponse {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<CreatorModel> results;

    public GetCreatorsResponse(
        int offset, int limit, int total, int count, List<CreatorModel> results) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.count = count;
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public List<CreatorModel> getResults() {
        return results;
    }
}
