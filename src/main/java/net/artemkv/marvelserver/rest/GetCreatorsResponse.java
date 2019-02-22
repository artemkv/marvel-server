package net.artemkv.marvelserver.rest;

import java.util.List;

/**
 * The view model to be returned by the REST Api
 */
public class GetCreatorsResponse {
    private int pageNumber;
    private int pageSize;
    private int total;
    private int count;
    private List<CreatorDto> results;

    public GetCreatorsResponse(
        int pageNumber, int pageSize, int total, int count, List<CreatorDto> results) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        this.count = count;
        this.results = results;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public List<CreatorDto> getResults() {
        return results;
    }
}
