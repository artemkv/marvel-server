package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;

public interface CreatorsService {
    GetCreatorsResponse getCreators(Pageable pageable);
}
