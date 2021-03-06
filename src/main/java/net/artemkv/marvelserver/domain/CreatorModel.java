package net.artemkv.marvelserver.domain;

import net.artemkv.marvelconnector.Creator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity(name="Creator")
public class CreatorModel {
    @Id
    private int id;
    private String fullName;
    private Date modified;
    private int comicsTotal;
    private int seriesTotal;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    private NoteModel note;

    public CreatorModel() {
    }

    public CreatorModel(Creator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("creator");
        }

        this.id = creator.getId();
        this.fullName = creator.getFullName();
        this.modified = creator.getModified() != null ? creator.getModified() : new Date(0);
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

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
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

    public NoteModel getNote() {
        return note;
    }

    public void setNote(NoteModel note) {
        this.note = note;
    }
}
