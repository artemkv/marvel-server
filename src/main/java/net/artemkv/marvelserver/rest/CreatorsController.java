package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.Configuration;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        @RequestParam(value="modifiedSince", defaultValue="") String modifiedSinceText,
        Pageable pageable) {
        // Validate paging
        if (pageable.getPageSize() < 1 || pageable.getPageSize() > 1000) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "page size should be a number 1-1000");
        }

        // Validate modified since
        Date modifiedSince = null;
        try {
            if (modifiedSinceText != null && modifiedSinceText.trim().length() > 0) {
                DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
                modifiedSince = formatter.parse(modifiedSinceText);
            }
        } catch (ParseException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "invalid value for modifiedSince. Use the format " + Constants.DATE_FORMAT);
        }

        return creatorsService.getCreators(fullName, modifiedSince, pageable);
    }
}
