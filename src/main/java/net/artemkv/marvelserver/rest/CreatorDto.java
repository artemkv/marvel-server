package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * The view model to be returned by the REST Api
 */
public class CreatorDto {
    private int id;
    private String fullName;
    private String modified;
    private int comicsTotal;
    private int seriesTotal;

    public CreatorDto(CreatorModel creator) {
        if (creator == null) {
            throw new IllegalArgumentException("creator");
        }

        this.id = creator.getId();
        this.fullName = creator.getFullName();
        DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.modified = formatter.format(creator.getModified());
        this.comicsTotal = creator.getComicsTotal();
        this.seriesTotal = creator.getSeriesTotal();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getComicsTotal() {
        return comicsTotal;
    }

    public void setComicsTotal(int comicsTotal) {
        this.comicsTotal = comicsTotal;
    }

    public int getSeriesTotal() {
        return seriesTotal;
    }

    public void setSeriesTotal(int seriesTotal) {
        this.seriesTotal = seriesTotal;
    }
}
