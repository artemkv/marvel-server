package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.NoteModel;

/**
 * The view model to be returned by the REST Api
 */
public class NoteDto {
    private int id;
    private String text;
    private int creatorId;
    private String creatorFullName;

    public NoteDto(NoteModel note, int creatorId, String creatorFullName) {
        if (note == null) {
            throw new IllegalArgumentException("note");
        }

        this.id = note.getId();
        this.text = note.getText();
        this.creatorId = creatorId;
        this.creatorFullName = creatorFullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorFullName() {
        return creatorFullName;
    }

    public void setCreatorFullName(String creatorFullName) {
        this.creatorFullName = creatorFullName;
    }
}
