package net.artemkv.marvelserver.repositories;

import net.artemkv.marvelserver.domain.NoteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<NoteModel, Integer> {
    Page<NoteModel> findByTextLikeIgnoreCase(String fullName, Pageable pageable);
}
