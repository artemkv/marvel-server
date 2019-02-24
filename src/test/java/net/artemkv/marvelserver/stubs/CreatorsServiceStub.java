package net.artemkv.marvelserver.stubs;

import net.artemkv.marvelserver.rest.CreatorDto;
import net.artemkv.marvelserver.rest.CreatorsService;
import net.artemkv.marvelserver.rest.GetListResponse;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public class CreatorsServiceStub implements CreatorsService {
    @Override
    public CreatorDto getCreator(int id) {
        return null;
    }

    @Override
    public GetListResponse<CreatorDto> getCreators(String fullName, Date modifiedSince, Pageable pageable) {
        return null;
    }

    @Override
    public void updateCreatorNote(int creatorId, String text) {
    }

    @Override
    public void deleteCreatorNote(int creatorId) {
    }
}
