package net.artemkv.marvelserver.services;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.repositories.CreatorRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreatorsServiceImpl implements CreatorsService {
    private CreatorRepository creatorRepository;

    public CreatorsServiceImpl(CreatorRepository creatorRepository) {
        if (creatorRepository == null) {
            throw new IllegalArgumentException("creatorRepository");
        }
        this.creatorRepository = creatorRepository;
    }

    @Override
    public List<CreatorModel> getCreators() {
        ArrayList<CreatorModel> creators = new ArrayList<>();
        creatorRepository.findAll().forEach(creators::add);
        return creators;
    }
}
