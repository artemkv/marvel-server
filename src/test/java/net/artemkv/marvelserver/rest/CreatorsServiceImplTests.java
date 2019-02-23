package net.artemkv.marvelserver.rest;

import net.artemkv.marvelconnector.Creator;
import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.stubs.CreatorRepositoryStub;
import net.artemkv.marvelserver.stubs.CreatorStub;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class CreatorsServiceImplTests {
    @Test
    public void testGetCreatorsPage0() {
        CreatorsServiceImpl service = new CreatorsServiceImpl(getRepository());

        Pageable pageable = PageRequest.of(0, 2);
        GetListResponse<CreatorDto> response = service.getCreators("", null, pageable);

        assertEquals(0, response.getPageNumber());
        assertEquals(2, response.getPageSize());
        assertEquals(2, response.getCount());
        assertEquals(3, response.getTotal());

        assertEquals(2, response.getResults().size());
        assertEquals(111, response.getResults().get(0).getId());
        assertEquals(222, response.getResults().get(1).getId());
    }

    @Test
    public void testGetCreatorsPage1() {
        CreatorsServiceImpl service = new CreatorsServiceImpl(getRepository());

        Pageable pageable = PageRequest.of(1, 2);
        GetListResponse<CreatorDto> response = service.getCreators("", null, pageable);

        assertEquals(1, response.getPageNumber());
        assertEquals(2, response.getPageSize());
        assertEquals(1, response.getCount());
        assertEquals(3, response.getTotal());

        assertEquals(1, response.getResults().size());
        assertEquals(333, response.getResults().get(0).getId());
    }

    @Test
    public void testGetCreatorsByFullName() {
        CreatorsServiceImpl service = new CreatorsServiceImpl(getRepository());

        Pageable pageable = PageRequest.of(0, 5);
        GetListResponse<CreatorDto> response = service.getCreators("Creator 2", null, pageable);

        assertEquals(0, response.getPageNumber());
        assertEquals(5, response.getPageSize());
        assertEquals(1, response.getCount());
        assertEquals(1, response.getTotal());

        assertEquals(1, response.getResults().size());
        assertEquals(111, response.getResults().get(0).getId());
    }

    @Test
    public void testGetCreatorsByModifiedSince() {
        CreatorsServiceImpl service = new CreatorsServiceImpl(getRepository());

        Pageable pageable = PageRequest.of(0, 5);
        GetListResponse<CreatorDto> response = service.getCreators("", new Date(), pageable);

        assertEquals(0, response.getPageNumber());
        assertEquals(5, response.getPageSize());
        assertEquals(1, response.getCount());
        assertEquals(1, response.getTotal());

        assertEquals(1, response.getResults().size());
        assertEquals(333, response.getResults().get(0).getId());
    }

    @Test
    public void testGetCreatorsByFullNameAndModifiedSince() {
        CreatorsServiceImpl service = new CreatorsServiceImpl(getRepository());

        Pageable pageable = PageRequest.of(0, 5);
        GetListResponse<CreatorDto> response = service.getCreators("Creator 2", new Date(), pageable);

        assertEquals(0, response.getPageNumber());
        assertEquals(5, response.getPageSize());
        assertEquals(1, response.getCount());
        assertEquals(1, response.getTotal());

        assertEquals(1, response.getResults().size());
        assertEquals(222, response.getResults().get(0).getId());
    }

    @Test
    public void testAddNote() {
        CreatorRepositoryStub repository = getRepository();
        CreatorsServiceImpl service = new CreatorsServiceImpl(repository);

        CreatorModel creator = repository.savedCreators.get(0);
        service.updateCreatorNote(creator.getId(), "Test note");

        assertEquals("Test note", creator.getNote().getText());

        service.updateCreatorNote(creator.getId(), "Test note updated");

        assertEquals("Test note updated", creator.getNote().getText());
    }

    @Test
    public void testDeleteNote() {
        CreatorRepositoryStub repository = getRepository();
        CreatorsServiceImpl service = new CreatorsServiceImpl(repository);

        CreatorModel creator = repository.savedCreators.get(0);
        service.updateCreatorNote(creator.getId(), "Test note");

        assertEquals("Test note", creator.getNote().getText());

        service.deleteCreatorNote(creator.getId());

        assertEquals(null, creator.getNote());
    }

    private CreatorRepositoryStub getRepository() {
        CreatorRepositoryStub creatorRepository = new CreatorRepositoryStub();

        Calendar calendar = new GregorianCalendar();

        Date date1 = null;
        Creator c1 = new CreatorStub(111, "Creator 1", date1, 25, 60);
        CreatorModel creator1 = new CreatorModel(c1);

        calendar.set(2019, 01, 05, 0, 0, 0);
        Date date2 = calendar.getTime();
        Creator c2 = new CreatorStub(222, "Creator 2", date2, 15, 32);
        CreatorModel creator2 = new CreatorModel(c2);

        calendar.set(2019, 01, 10, 0, 0, 0);
        Date date3 = calendar.getTime();
        Creator c3 = new CreatorStub(333, "Creator 3", date3, 1, 888);
        CreatorModel creator3 = new CreatorModel(c3);

        creatorRepository.savedCreators.add(creator1);
        creatorRepository.savedCreators.add(creator2);
        creatorRepository.savedCreators.add(creator3);

        creatorRepository.matchingCreatorByFullName = creator1;
        creatorRepository.matchingCreatorByModifiedSince = creator3;
        creatorRepository.matchingCreatorByFullNameAndModifiedSince = creator2;

        return creatorRepository;
    }
}
