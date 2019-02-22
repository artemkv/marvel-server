package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.repositories.CreatorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
class CreatorsServiceImpl implements CreatorsService {
    private CreatorRepository creatorRepository;

    public CreatorsServiceImpl(CreatorRepository creatorRepository) {
        if (creatorRepository == null) {
            throw new IllegalArgumentException("creatorRepository");
        }
        this.creatorRepository = creatorRepository;
    }

    @Override
    public GetCreatorsResponse getCreators(Pageable pageable) {
        Page<CreatorModel> page = creatorRepository.findAll(pageable);

        ArrayList<CreatorDto> creators = new ArrayList<>();
        page.forEach(x -> creators.add(new CreatorDto(x)));

        GetCreatorsResponse response = new GetCreatorsResponse(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            (int) page.getTotalElements(),
            page.getSize(),
            creators);
        return response;
    }
}
