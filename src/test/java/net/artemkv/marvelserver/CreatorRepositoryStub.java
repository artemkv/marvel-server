package net.artemkv.marvelserver;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.repositories.CreatorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CreatorRepositoryStub implements CreatorRepository {
    public List<CreatorModel> savedCreators = new ArrayList<>();

    @Override
    public Page<CreatorModel> findByFullNameIgnoreCase(String fullName, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CreatorModel> findByModifiedGreaterThan(Date modified, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CreatorModel> findByFullNameIgnoreCaseAndModifiedGreaterThan(String fullName, Date modified, Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<CreatorModel> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CreatorModel> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CreatorModel> S save(S s) {
        savedCreators.add(s);
        return s;
    }

    @Override
    public <S extends CreatorModel> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<CreatorModel> findById(Integer integer) {
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
