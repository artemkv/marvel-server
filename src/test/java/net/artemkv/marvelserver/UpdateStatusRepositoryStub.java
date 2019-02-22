package net.artemkv.marvelserver;

import net.artemkv.marvelserver.domain.UpdateStatus;
import net.artemkv.marvelserver.repositories.UpdateStatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpdateStatusRepositoryStub implements UpdateStatusRepository {
    public List<UpdateStatus> updateStatuses = new ArrayList<>();

    @Override
    public <S extends UpdateStatus> S save(S s) {
        updateStatuses.add(new UpdateStatus(1, s.getLastSyncDate()));
        return s;
    }

    @Override
    public <S extends UpdateStatus> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<UpdateStatus> findById(Integer integer) {
        if (updateStatuses.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(updateStatuses.get(updateStatuses.size() - 1));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<UpdateStatus> findAll() {
        return null;
    }

    @Override
    public Iterable<UpdateStatus> findAllById(Iterable<Integer> iterable) {
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
    public void delete(UpdateStatus updateStatus) {

    }

    @Override
    public void deleteAll(Iterable<? extends UpdateStatus> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
