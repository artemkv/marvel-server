package net.artemkv.marvelserver.stubs;

import net.artemkv.marvelconnector.Creator;

import java.util.Date;

public class CreatorStub implements Creator {
    private int id;
    private String fullName;
    private Date modified;
    private int comicsTotal;
    private int seriesTotal;

    public CreatorStub(int id, String fullName, Date modified, int comicsTotal, int seriesTotal) {
        this.id = id;
        this.fullName = fullName;
        this.modified = modified;
        this.comicsTotal = comicsTotal;
        this.seriesTotal = seriesTotal;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public Date getModified() {
        return modified;
    }

    @Override
    public int getComicsTotal() {
        return comicsTotal;
    }

    @Override
    public int getSeriesTotal() {
        return seriesTotal;
    }
}
