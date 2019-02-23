package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.stubs.CreatorsServiceStub;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

public class CreatorsControllerTests {
    @Test
    public void testPageSizeLimitOk() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        Pageable pageable = PageRequest.of(0, 1000);
        GetListResponse<CreatorDto> response = controller.getCreators("", "", pageable);
    }

    @Test(expected = ResponseStatusException.class)
    public void testPageSizeLimitOver() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        Pageable pageable = PageRequest.of(0, 1001);
        GetListResponse<CreatorDto> response = controller.getCreators("", "", pageable);
    }

    @Test
    public void testFullNameLimitOk() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        Pageable pageable = PageRequest.of(0, 10);
        GetListResponse<CreatorDto> response = controller.getCreators(
            "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ", "", pageable);
    }

    @Test(expected = ResponseStatusException.class)
    public void testFullNameLimitOver() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        Pageable pageable = PageRequest.of(0, 10);
        GetListResponse<CreatorDto> response = controller.getCreators(
            "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJx", "", pageable);
    }

    @Test
    public void testModifiedSinceOk() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        Pageable pageable = PageRequest.of(0, 10);
        GetListResponse<CreatorDto> response = controller.getCreators(
            "", "1970-01-01T01:00:00.000+01", pageable);
    }

    @Test(expected = ResponseStatusException.class)
    public void testModifiedSinceInvalid() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        Pageable pageable = PageRequest.of(0, 10);
        GetListResponse<CreatorDto> response = controller.getCreators(
            "", "Some unparseable date", pageable);
    }

    @Test
    public void testNoteTextLimitOk() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        NoteInDto noteIn = new NoteInDto();
        noteIn.setText("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ");
        controller.postNote(1, noteIn);
    }

    @Test(expected = ResponseStatusException.class)
    public void testNoteTextLimitOver() {
        CreatorsController controller = new CreatorsController(new CreatorsServiceStub());
        NoteInDto noteIn = new NoteInDto();
        noteIn.setText("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJx");
        controller.postNote(1, noteIn);
    }
}
