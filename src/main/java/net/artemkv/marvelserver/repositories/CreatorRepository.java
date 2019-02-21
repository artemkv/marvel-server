package net.artemkv.marvelserver.repositories;

import net.artemkv.marvelserver.domain.CreatorModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CreatorRepository extends PagingAndSortingRepository<CreatorModel, Integer> {
}
