package net.artemkv.marvelserver.stubs;

import net.artemkv.marvelserver.rest.GetListResponse;
import net.artemkv.marvelserver.rest.NoteDto;
import net.artemkv.marvelserver.rest.NoteService;
import org.springframework.data.domain.Pageable;

public class NoteServiceStub implements NoteService {
    @Override
    public GetListResponse<NoteDto> getNotes(String text, Pageable pageable) {
        return null;
    }

    @Override
    public NoteDto getNote(int id) {
        return null;
    }
}
