package net.artemkv.marvelserver.stubs;

import net.artemkv.marvelserver.domain.NoteModel;
import net.artemkv.marvelserver.repositories.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class NoteRepositoryStub implements NoteRepository {
    public List<NoteModel> savedNotes = new ArrayList<>();
    public NoteModel matchingNoteByText;

    @Override
    public Page<NoteModel> findByTextLikeIgnoreCase(String fullName, Pageable pageable) {
        List<NoteModel> content = new ArrayList<>();
        content.add(matchingNoteByText);

        return new Page<NoteModel>() {
            @Override
            public Iterator<NoteModel> iterator() {
                return content.iterator();
            }

            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public int getNumber() {
                return pageable.getPageNumber();
            }

            @Override
            public int getSize() {
                return pageable.getPageSize();
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<NoteModel> getContent() {
                return content;
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super NoteModel, ? extends U> function) {
                return null;
            }
        };
    }

    @Override
    public Iterable<NoteModel> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<NoteModel> findAll(Pageable pageable) {
        int pagesTotalTmp = savedNotes.size() / pageable.getPageSize();
        if (savedNotes.size() % pageable.getPageSize() > 0) {
            pagesTotalTmp++;
        }
        int pagesTotal = pagesTotalTmp;

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        List<NoteModel> content = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (i >= 0 && i < savedNotes.size()) {
                content.add(savedNotes.get(i));
            }
        }

        return new Page<NoteModel>() {
            @Override
            public int getTotalPages() {
                return pagesTotal;
            }

            @Override
            public long getTotalElements() {
                return savedNotes.size();
            }

            @Override
            public <U> Page<U> map(Function<? super NoteModel, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return pageable.getPageNumber();
            }

            @Override
            public int getSize() {
                return pageable.getPageSize();
            }

            @Override
            public int getNumberOfElements() {
                return content.size();
            }

            @Override
            public List<NoteModel> getContent() {
                return content;
            }

            @Override
            public boolean hasContent() {
                return content.size() > 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<NoteModel> iterator() {
                return content.iterator();
            }
        };
    }

    @Override
    public <S extends NoteModel> S save(S s) {
        return null;
    }

    @Override
    public <S extends NoteModel> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<NoteModel> findById(Integer id) {
        for (int i = 0; i < savedNotes.size(); i++) {
            if (savedNotes.get(i).getId() == id) {
                return Optional.of(savedNotes.get(i));
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<NoteModel> findAll() {
        return null;
    }

    @Override
    public Iterable<NoteModel> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(NoteModel noteModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends NoteModel> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
