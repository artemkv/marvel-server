package net.artemkv.marvelserver.rest;

import net.artemkv.marvelserver.stubs.NoteServiceStub;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

public class NoteControllerTests {
    @Test
    public void testPageSizeLimitOk() {
        NoteController controller = new NoteController(new NoteServiceStub());
        Pageable pageable = PageRequest.of(0, 1000);
        GetListResponse<NoteDto> response = controller.getNotes("", pageable);
    }

    @Test(expected = ResponseStatusException.class)
    public void testPageSizeLimitOver() {
        NoteController controller = new NoteController(new NoteServiceStub());
        Pageable pageable = PageRequest.of(0, 1001);
        GetListResponse<NoteDto> response = controller.getNotes("", pageable);
    }

    @Test
    public void testSearchTextLimitOk() {
        NoteController controller = new NoteController(new NoteServiceStub());
        Pageable pageable = PageRequest.of(0, 10);
        GetListResponse<NoteDto> response = controller.getNotes(
            "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ", pageable);
    }

    @Test(expected = ResponseStatusException.class)
    public void testSearchTextLimitOver() {
        NoteController controller = new NoteController(new NoteServiceStub());
        Pageable pageable = PageRequest.of(0, 10);
        GetListResponse<NoteDto> response = controller.getNotes(
            "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJx", pageable);
    }
}
