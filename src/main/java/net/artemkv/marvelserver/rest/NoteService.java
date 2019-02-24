package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;

public interface NoteService {
    NoteDto getNote(int id);

    GetListResponse<NoteDto> getNotes(String text, Pageable pageable);
}
