package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        method = RequestMethod.GET, value = "/note/{noteId}", produces = "application/json")
    public NoteDto getNote(@PathVariable int noteId) {
        return noteService.getNote(noteId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notes", produces = "application/json")
    public GetListResponse<NoteDto> getNotes(
        @RequestParam(value = "text", defaultValue = "") String text, Pageable pageable) {
        // Validate paging
        if (pageable.getPageSize() < 1 || pageable.getPageSize() > Constants.MAX_PAGE_SIZE) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Size should be a number 1-%d", Constants.MAX_PAGE_SIZE));
        }

        // Validate search text
        if (text.length() > Constants.SEARCH_TEXT_MAX_LENGTH) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Text should be %d chars maximum.", Constants.SEARCH_TEXT_MAX_LENGTH));
        }

        return noteService.getNotes(text, pageable);
    }
}
