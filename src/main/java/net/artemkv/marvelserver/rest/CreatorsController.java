package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;

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
        @RequestParam(value="fullName", defaultValue="") String fullName,
        @RequestParam(value="modifiedSince", defaultValue=Constants.MIN_DATE)
        @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date modifiedSince,
        Pageable pageable) {
        if (pageable.getPageSize() < 1 || pageable.getPageSize() > 1000) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "page size should be a number 1-1000");
        }
        return creatorsService.getCreators(pageable);
    }
}
