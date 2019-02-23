package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.domain.NoteModel;
import net.artemkv.marvelserver.repositories.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

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
    public GetListResponse<NoteDto> getNotes(String text, Pageable pageable) {
        Page<NoteModel> page = null;
        if (text != null && text.trim().length() > 0) {
            page = noteRepository.findByTextLikeIgnoreCase("%" + text + "%", pageable);
        } else {
            page = noteRepository.findAll(pageable);
        }
        ArrayList<NoteDto> notes = new ArrayList<>();
        page.forEach(x -> notes.add(new NoteDto(x, x.getCreator().getId(), x.getCreator().getFullName())));

        GetListResponse<NoteDto> response = new GetListResponse<NoteDto>(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            (int) page.getTotalElements(),
            notes.size(),
            notes);
        return response;
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
