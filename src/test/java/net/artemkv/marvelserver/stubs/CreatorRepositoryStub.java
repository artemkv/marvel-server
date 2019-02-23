package net.artemkv.marvelserver.stubs;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.repositories.CreatorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CreatorRepositoryStub implements CreatorRepository {
    public List<CreatorModel> savedCreators = new ArrayList<>();
    public CreatorModel matchingCreatorByFullName;
    public CreatorModel matchingCreatorByModifiedSince;
    public CreatorModel matchingCreatorByFullNameAndModifiedSince;

    @Override
    public Page<CreatorModel> findByFullNameIgnoreCase(String fullName, Pageable pageable) {
        List<CreatorModel> content = new ArrayList<>();
        content.add(matchingCreatorByFullName);

        return new Page<CreatorModel>() {
            @Override
            public Iterator<CreatorModel> iterator() {
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
            public List<CreatorModel> getContent() {
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
            public <U> Page<U> map(Function<? super CreatorModel, ? extends U> function) {
                return null;
            }
        };
    }

    @Override
    public Page<CreatorModel> findByModifiedGreaterThan(Date modified, Pageable pageable) {
        List<CreatorModel> content = new ArrayList<>();
        content.add(matchingCreatorByModifiedSince);

        return new Page<CreatorModel>() {
            @Override
            public Iterator<CreatorModel> iterator() {
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
            public List<CreatorModel> getContent() {
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
            public <U> Page<U> map(Function<? super CreatorModel, ? extends U> function) {
                return null;
            }
        };
    }

    @Override
    public Page<CreatorModel> findByFullNameIgnoreCaseAndModifiedGreaterThan(String fullName, Date modified, Pageable pageable) {
        List<CreatorModel> content = new ArrayList<>();
        content.add(matchingCreatorByFullNameAndModifiedSince);

        return new Page<CreatorModel>() {
            @Override
            public Iterator<CreatorModel> iterator() {
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
            public List<CreatorModel> getContent() {
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
            public <U> Page<U> map(Function<? super CreatorModel, ? extends U> function) {
                return null;
            }
        };
    }

    @Override
    public Iterable<CreatorModel> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CreatorModel> findAll(Pageable pageable) {
        int pagesTotalTmp = savedCreators.size() / pageable.getPageSize();
        if (savedCreators.size() % pageable.getPageSize() > 0) {
            pagesTotalTmp++;
        }
        int pagesTotal = pagesTotalTmp;

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        List<CreatorModel> content = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (i >= 0 && i < savedCreators.size()) {
                content.add(savedCreators.get(i));
            }
        }

        return new Page<CreatorModel>() {
            @Override
            public int getTotalPages() {
                return pagesTotal;
            }

            @Override
            public long getTotalElements() {
                return savedCreators.size();
            }

            @Override
            public <U> Page<U> map(Function<? super CreatorModel, ? extends U> function) {
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
            public List<CreatorModel> getContent() {
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
            public Iterator<CreatorModel> iterator() {
                return content.iterator();
            }
        };
    }

    @Override
    public <S extends CreatorModel> S save(S s) {
        for (int i = 0; i < savedCreators.size(); i++) {
            if (savedCreators.get(i).getId() == s.getId()) {
                savedCreators.set(i, s);
                return s;
            }
        }

        savedCreators.add(s);
        return s;
    }

    @Override
    public <S extends CreatorModel> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<CreatorModel> findById(Integer id) {
        for (int i = 0; i < savedCreators.size(); i++) {
            if (savedCreators.get(i).getId() == id) {
                return Optional.of(savedCreators.get(i));
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<CreatorModel> findAll() {
        return null;
    }

    @Override
    public Iterable<CreatorModel> findAllById(Iterable<Integer> iterable) {
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
    public void delete(CreatorModel creatorModel) {

    }

    @Override
    public void deleteAll(Iterable<? extends CreatorModel> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
