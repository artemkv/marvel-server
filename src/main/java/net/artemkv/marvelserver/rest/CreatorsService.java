package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;
import java.util.Date;

public interface CreatorsService {
    CreatorDto getCreator(int id);

    GetListResponse<CreatorDto> getCreators(String fullName, Date modifiedSince, Pageable pageable);

    void updateCreatorNote(int creatorId, String text);

    void deleteCreatorNote(int creatorId);
}
