package net.artemkv.marvelserver.marvelconnector;

import java.util.List;

/**
 * Contains the result of the creator retrieval operation.
 */
public interface GetCreatorsResult {
    /**
     * The list of creators.
     *
     * @return the list of creators.
     */
    List<Creator> getCreators();

    /**
     * Indicates whether there is more data to retrieve.
     *
     * @return value that indicates whether there is more data to retrieve.
     */
    boolean hasMore();

    /**
     * The new offset to be used to retrieve the next page.
     *
     * @return new offset to be used to retrieve the next page.
     */
    public int getNewOffset();
}
