package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.services.CreatorsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value="api")
public class CreatorsController {
    private CreatorsService creatorsService;

    public CreatorsController(CreatorsService creatorsService) {
        if (creatorsService == null) {
            throw new IllegalArgumentException("creatorsService");
        }

        this.creatorsService = creatorsService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/creators", produces = "application/json")
    public GetCreatorsResponse getCreators(
        @RequestParam(value="pageNumber", defaultValue="0") int pageNumber,
        @RequestParam(value="pageSize", defaultValue="100") int pageSize) {
        if (pageSize < 1 || pageSize > 1000) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "page size should be a number 1-1000");
        }

        List<CreatorModel> creators = creatorsService.getCreators(pageNumber, pageSize);
        return new GetCreatorsResponse(pageNumber, pageSize, 0, 0, creators);
    }
}
