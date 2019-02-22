package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.domain.NoteModel;
import net.artemkv.marvelserver.repositories.NoteRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NoteServiceImpl implements NoteService {
    private NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        if (noteRepository == null) {
            throw new IllegalArgumentException("noteRepository");
        }
        this.noteRepository = noteRepository;
    }

    @Override
    public GetListResponse<NoteDto> getNotes(Pageable pageable) {
        return null;
    }

    @Override
    public NoteDto getNote(int id) {
        NoteModel note = noteRepository.findById(id).orElse(null);
        if (note == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Note with id %d not found.", id));
        }
        CreatorModel creator = note.getCreator();
        return new NoteDto(note, creator.getId(), creator.getFullName());
    }
}
