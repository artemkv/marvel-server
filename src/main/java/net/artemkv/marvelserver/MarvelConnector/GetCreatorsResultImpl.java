package net.artemkv.marvelserver.MarvelConnector;

import java.util.List;

class GetCreatorsResultImpl implements GetCreatorsResult {
    private List<Creator> creators;
    private boolean hasMore;
    private int newOffset;

    public GetCreatorsResultImpl(List<Creator> creators, boolean hasMore, int newOffset) {
        if (creators == null) {
            throw new IllegalArgumentException("creators");
        }

        this.creators = creators;
        this.hasMore = hasMore;
        this.newOffset = newOffset;
    }

    public List<Creator> getCreators() {
        return creators;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public int getNewOffset() {
        return newOffset;
    }
}
