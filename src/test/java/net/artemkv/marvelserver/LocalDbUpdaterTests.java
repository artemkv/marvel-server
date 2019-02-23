package net.artemkv.marvelserver;

import net.artemkv.marvelserver.stubs.CreatorRepositoryStub;
import net.artemkv.marvelserver.stubs.MarvelServiceStub;
import net.artemkv.marvelserver.stubs.UpdateStatusRepositoryStub;
import org.junit.Test;
import static org.junit.Assert.*;

public class LocalDbUpdaterTests {
    @Test
    public void testLocalDbUpdate() {
        // Arrange
        CreatorRepositoryStub creatorRepository = new CreatorRepositoryStub();
        UpdateStatusRepositoryStub updateStatusRepository = new UpdateStatusRepositoryStub();

        LocalDbUpdater updater = new LocalDbUpdater(
            new MarvelServiceStub(),
            creatorRepository,
            updateStatusRepository);

        // Act
        updater.update();

        // Check that we loaded all creators
        assertEquals(3, creatorRepository.savedCreators.size());

        // Check that we properly updated last sync date every time
        assertEquals(2, updateStatusRepository.updateStatuses.size());
        assertEquals(
            creatorRepository.savedCreators.get(1).getModified(),
            updateStatusRepository.updateStatuses.get(0).getLastSyncDate());
        assertEquals(
            creatorRepository.savedCreators.get(2).getModified(),
            updateStatusRepository.updateStatuses.get(1).getLastSyncDate());
    }
}
