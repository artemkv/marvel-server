package net.artemkv.marvelserver.repositories;

import net.artemkv.marvelserver.domain.CreatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CreatorRepository
    extends JpaRepository<CreatorModel, Integer>, JpaSpecificationExecutor<CreatorModel> {
}
