package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.CreatorsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public GetCreatorsResponse getCreators() {
        return new GetCreatorsResponse(1, 2, 3, 4, creatorsService.getCreators());
    }
}
