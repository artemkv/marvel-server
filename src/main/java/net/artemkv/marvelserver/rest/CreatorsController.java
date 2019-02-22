package net.artemkv.marvelserver.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
        @RequestParam(value = "fullName", defaultValue = "") String fullName,
        @RequestParam(value = "modifiedSince", defaultValue = "") String modifiedSinceText,
        Pageable pageable) {
        // Validate paging
        if (pageable.getPageSize() < 1 || pageable.getPageSize() > Constants.MAX_PAGE_SIZE) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Size should be a number 1-%d", Constants.MAX_PAGE_SIZE));
        }
        if (pageable.getPageNumber() < 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Page should be >= 0");
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
                String.format("Invalid value for modifiedSince. Use format %s", Constants.DATE_FORMAT));
        }

        // Validate full name
        if (fullName.length() > Constants.FULL_NAME_MAX_LENGTH) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Full name should be %d chars maximum.", Constants.FULL_NAME_MAX_LENGTH));
        }

        return creatorsService.getCreators(fullName, modifiedSince, pageable);
    }

    @RequestMapping(
        method = RequestMethod.POST,
        value = "/creator/{creatorId}/note",
        consumes = "text/plain",
        produces = "application/json")
    public void postNote(
        @PathVariable int creatorId,
        @RequestBody String noteText) {
        // Validate note text
        if (noteText.length() > Constants.NOTE_TEXT_MAX_LENGTH) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.format("Note text should be %d chars maximum.", Constants.NOTE_TEXT_MAX_LENGTH));
        }

        creatorsService.updateCreatorNote(creatorId, noteText);
    }
}
