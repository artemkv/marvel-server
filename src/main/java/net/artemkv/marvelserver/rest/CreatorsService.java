package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface CreatorsService {
    GetCreatorsResponse getCreators(String fullName, Date modifiedSince, Pageable pageable);
}
