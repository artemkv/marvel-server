package net.artemkv.marvelserver.repositories;

import net.artemkv.marvelserver.domain.CreatorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface CreatorRepository extends PagingAndSortingRepository<CreatorModel, Integer> {
    Page<CreatorModel> findByFullNameIgnoreCase(String fullName, Pageable pageable);
    Page<CreatorModel> findByModifiedGreaterThan(Date modified, Pageable pageable);
    Page<CreatorModel> findByFullNameIgnoreCaseAndModifiedGreaterThan(
        String fullName, Date modified, Pageable pageable);
}
