package net.artemkv.marvelserver;

import io.reactivex.schedulers.Schedulers;
import net.artemkv.marvelconnector.MarvelApiRepository;
import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.domain.UpdateStatus;
import net.artemkv.marvelconnector.Creator;
import net.artemkv.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelconnector.IntegrationException;
import net.artemkv.marvelconnector.TimeoutException;
import net.artemkv.marvelserver.repositories.CreatorRepository;
import net.artemkv.marvelserver.repositories.UpdateStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Scope("singleton")
class LocalDbUpdater {
    private static class SyncDateContainer {
        public Date lastSyncDate;
        public Date newLastSyncDate;
    }

    private static final Logger logger = LoggerFactory.getLogger(LocalDbUpdater.class);

    LocalDatabaseState localDatabaseState = LocalDatabaseState.OUT_OF_DATE;

    private final MarvelApiRepository marvelApiRepository;
    private final CreatorRepository creatorRepository;
    private final UpdateStatusRepository updateStatusRepository;

    public LocalDbUpdater(
        MarvelApiRepository marvelApiRepository,
        CreatorRepository creatorRepository,
        UpdateStatusRepository updateStatusRepository) {
        if (marvelApiRepository == null) {
            throw new IllegalArgumentException("marvelApiRepository");
        }
        if (creatorRepository == null) {
            throw new IllegalArgumentException("creatorRepository");
        }
        if (updateStatusRepository == null) {
            throw new IllegalArgumentException("updateStatusRepository");
        }

        this.marvelApiRepository = marvelApiRepository;
        this.creatorRepository = creatorRepository;
        this.updateStatusRepository = updateStatusRepository;
    }

    public void update() {
        UpdateStatus updateStatus = updateStatusRepository.findById(1)
            .orElse(new UpdateStatus(1, null));

        logger.debug("Updating creators from Marvel...");
        try {
            SyncDateContainer syncDateContainer = new SyncDateContainer();
            syncDateContainer.lastSyncDate = updateStatus.getLastSyncDate();
            syncDateContainer.newLastSyncDate = updateStatus.getLastSyncDate();

            marvelApiRepository.getCreators(syncDateContainer.lastSyncDate)
                .subscribe(
                    creator -> {
                        logger.trace("Found creator: " + creator.getFullName());
                        // save into local db
                        CreatorModel creatorModel = new CreatorModel(creator);
                        creatorRepository.save(creatorModel);
                        // update last synced date
                        if (creator.getModified() != null &&
                            (syncDateContainer.newLastSyncDate == null ||
                                creator.getModified().after(syncDateContainer.newLastSyncDate))) {
                            // done with that date, save it
                            if (syncDateContainer.newLastSyncDate != null) {
                                updateStatus.setLastSyncDate(syncDateContainer.newLastSyncDate);
                                updateStatusRepository.save(updateStatus);
                            }
                            // use the newer value
                            syncDateContainer.newLastSyncDate = creator.getModified();
                        }
                    }
                    , e -> {
                        logger.error("Failed to update creators from Marvel", e);
                    }
                    , () -> {
                        logger.debug("Done updating creators from Marvel");

                        // save last sync date - final value
                        if (syncDateContainer.newLastSyncDate != null) {
                            updateStatus.setLastSyncDate(syncDateContainer.newLastSyncDate);
                            updateStatusRepository.save(updateStatus);
                        }

                        // done with sync
                        setLocalDatabaseState(LocalDatabaseState.UP_TO_DATE);
                    });

        } catch (IntegrationException e) {
            logger.error("Failed to update creators from Marvel", e);
            throw new IllegalStateException("Failed to update creators from Marvel");
        }
    }

    public synchronized LocalDatabaseState getLocalDatabaseState() {
        return localDatabaseState;
    }

    private synchronized void setLocalDatabaseState(LocalDatabaseState localDatabaseState) {
        this.localDatabaseState = localDatabaseState;
    }
}
