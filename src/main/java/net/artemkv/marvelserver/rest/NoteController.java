package net.artemkv.marvelserver.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        if (noteService == null) {
            throw new IllegalArgumentException("noteService");
        }

        this.noteService = noteService;
    }

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/note/{noteId}",
        produces = "application/json")
    public NoteDto getNote(@PathVariable int noteId) {
        return noteService.getNote(noteId);
    }
}
