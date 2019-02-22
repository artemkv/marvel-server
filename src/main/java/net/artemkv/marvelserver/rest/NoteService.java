package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;

public interface NoteService {
    GetListResponse<NoteDto> getNotes(String text, Pageable pageable);

    NoteDto getNote(int id);
}
