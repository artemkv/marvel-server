package net.artemkv.marvelserver.rest;

import net.artemkv.marvelconnector.Creator;
import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.domain.NoteModel;
import net.artemkv.marvelserver.stubs.CreatorStub;
import net.artemkv.marvelserver.stubs.NoteRepositoryStub;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class NoteServiceImplTests {
    @Test
    public void testGetNotesPage0() {
        // Arrange
        NoteServiceImpl service = new NoteServiceImpl(getRepository());

        // Act
        Pageable pageable = PageRequest.of(0, 2);
        GetListResponse<NoteDto> response = service.getNotes("", pageable);

        // Verify paging props
        assertEquals(0, response.getPageNumber());
        assertEquals(2, response.getPageSize());
        assertEquals(2, response.getCount());
        assertEquals(3, response.getTotal());

        // Verify actual returned data
        assertEquals(2, response.getResults().size());
        assertEquals("Note 1", response.getResults().get(0).getText());
        assertEquals("Note 2", response.getResults().get(1).getText());
    }

    @Test
    public void testGetNotesByText() {
        // Arrange
        NoteServiceImpl service = new NoteServiceImpl(getRepository());

        // Act
        Pageable pageable = PageRequest.of(0, 5);
        GetListResponse<NoteDto> response = service.getNotes("Note 2", pageable);

        // Verify paging props
        assertEquals(0, response.getPageNumber());
        assertEquals(5, response.getPageSize());
        assertEquals(1, response.getCount());
        assertEquals(1, response.getTotal());

        // Verify actual returned data
        assertEquals(1, response.getResults().size());
        assertEquals("Note 2", response.getResults().get(0).getText());
    }

    @Test
    public void testGetNotesById() {
        // Arrange
        NoteServiceImpl service = new NoteServiceImpl(getRepository());

        // Act
        NoteDto note = service.getNote(1);

        // Verify the note returned
        assertEquals("Note 1", note.getText());
    }

    private NoteRepositoryStub getRepository() {
        NoteRepositoryStub noteRepository = new NoteRepositoryStub();

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

        NoteModel note1 = new NoteModel("Note 1");
        note1.setId(1);
        note1.setCreator(creator1);
        NoteModel note2 = new NoteModel("Note 2");
        note2.setId(2);
        note2.setCreator(creator2);
        NoteModel note3 = new NoteModel("Note 3");
        note3.setId(3);
        note3.setCreator(creator3);

        noteRepository.savedNotes.add(note1);
        noteRepository.savedNotes.add(note2);
        noteRepository.savedNotes.add(note3);

        noteRepository.matchingNoteByText = note2;

        return noteRepository;
    }
}
