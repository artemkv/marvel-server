package net.artemkv.marvelserver.marvelconnector;

import java.util.Date;

/**
 * Represents the creator.
 */
public interface Creator {
    /**
     * Returns the creator id.
     *
     * @return id of the creator.
     */
    int getId();

    /**
     * Returns the creator's full name.
     *
     * @return full name of the creator.
     */
    String getFullName();

    /**
     * Returns the date the creator data was last modified.
     *
     * @return date the creator data was last modified.
     */
    Date getModified();

    /**
     * Returns number of comics created by the creator.
     *
     * @return number of comics created by the creator.
     */
    int getComicsTotal();

    /**
     * Returns number of series created by the creator.
     *
     * @return number of series created by the creator.
     */
    int getSeriesTotal();
}
