package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.domain.NoteModel;
import net.artemkv.marvelserver.repositories.CreatorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Date;

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
    public GetListResponse<CreatorDto> getCreators(String fullName, Date modifiedSince, Pageable pageable) {
        boolean filterByFullName = false;
        if (fullName != null && fullName.trim().length() > 0) {
            filterByFullName = true;
        }

        boolean filterByModified = false;
        if (modifiedSince != null && modifiedSince.after(new Date(Long.MIN_VALUE))) {
            filterByModified = true;
        }

        // TODO: to be revised according to https://blog.tratif.com/2017/11/23/effective-restful-search-api-in-spring/
        Page<CreatorModel> page = null;
        if (filterByFullName && filterByModified) {
            page = creatorRepository
                .findByFullNameIgnoreCaseAndModifiedGreaterThan(fullName, modifiedSince, pageable);
        } else if (filterByFullName) {
            page = creatorRepository.findByFullNameIgnoreCase(fullName, pageable);
        } else if (filterByModified) {
            page = creatorRepository.findByModifiedGreaterThan(modifiedSince, pageable);
        } else {
            page = creatorRepository.findAll(pageable);
        }

        ArrayList<CreatorDto> creators = new ArrayList<>();
        page.forEach(x -> creators.add(new CreatorDto(x)));

        GetListResponse<CreatorDto> response = new GetListResponse<CreatorDto>(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            (int) page.getTotalElements(),
            creators.size(),
            creators);
        return response;
    }

    public void updateCreatorNote(int creatorId, String text) {
        CreatorModel creator = getCreatorWithCheck(creatorId);
        if (creator.getNote() != null) {
            creator.getNote().setText(text);
        } else {
            NoteModel note = new NoteModel(text);
            creator.setNote(note);
        }
        creatorRepository.save(creator);
    }

    public void deleteCreatorNote(int creatorId) {
        CreatorModel creator = getCreatorWithCheck(creatorId);
        creator.setNote(null);
        creatorRepository.save(creator);
    }

    private CreatorModel getCreatorWithCheck(int creatorId) {
        CreatorModel creator = creatorRepository.findById(creatorId).orElse(null);
        if (creator == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Creator with id %d not found.", creatorId));
        }
        return creator;
    }
}
