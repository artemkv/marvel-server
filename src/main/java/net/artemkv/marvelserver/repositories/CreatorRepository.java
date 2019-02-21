package net.artemkv.marvelserver.repositories;

import net.artemkv.marvelserver.domain.CreatorModel;
import org.springframework.data.repository.CrudRepository;

public interface CreatorRepository extends CrudRepository<CreatorModel, Integer> {
}
