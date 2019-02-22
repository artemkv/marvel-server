package net.artemkv.marvelserver;

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
public class LocalDbUpdater {
    private static final Logger logger = LoggerFactory.getLogger(LocalDbUpdater.class);

    LocalDatabaseState localDatabaseState = LocalDatabaseState.OUT_OF_DATE;

    private MarvelService marvelApiService;
    private CreatorRepository creatorRepository;
    private UpdateStatusRepository updateStatusRepository;

    public LocalDbUpdater(
        MarvelService marvelApiService,
        CreatorRepository creatorRepository,
        UpdateStatusRepository updateStatusRepository) {
        if (marvelApiService == null) {
            throw new IllegalArgumentException("marvelApiService");
        }
        if (creatorRepository == null) {
            throw new IllegalArgumentException("creatorRepository");
        }
        if (updateStatusRepository == null) {
            throw new IllegalArgumentException("updateStatusRepository");
        }

        this.marvelApiService = marvelApiService;
        this.creatorRepository = creatorRepository;
        this.updateStatusRepository = updateStatusRepository;
    }

    public void update() {
        UpdateStatus updateStatus = updateStatusRepository.findById(1)
            .orElse(new UpdateStatus(1, new Date(Long.MIN_VALUE)));

        logger.debug("Updating creators from Marvel...");
        try {
            Date lastSyncDate = updateStatus.getLastSyncDate();
            Date newLastSyncDate = updateStatus.getLastSyncDate();
            boolean hasMore = true;
            int offset = 0;
            while (hasMore) {
                GetCreatorsResult result = marvelApiService.getCreators(lastSyncDate, offset);
                for (Creator creator : result.getCreators()) {
                    logger.trace("Found creator: " + creator.getFullName());
                    // save into local db
                    CreatorModel creatorModel = new CreatorModel(creator);
                    creatorRepository.save(creatorModel);
                    // update last synced date
                    if (creator.getModified().after(newLastSyncDate)) {
                        // done with that date, save it
                        updateStatus.setLastSyncDate(newLastSyncDate);
                        updateStatusRepository.save(updateStatus);
                        // use the newer value
                        newLastSyncDate = creator.getModified();
                    }
                }
                hasMore = result.hasMore();
                offset = result.getNewOffset();
                if (hasMore) {
                    logger.debug("Marvel has more creators starting from " + offset);
                } else {
                    logger.debug("Done updating creators from Marvel");
                }
            }
            // save last sync date - final value
            updateStatus.setLastSyncDate(newLastSyncDate);
            updateStatusRepository.save(updateStatus);

            setLocalDatabaseState(LocalDatabaseState.UP_TO_DATE);
        } catch (IntegrationException e) {
            logger.error("Failed to update creators from Marvel", e);
            throw new IllegalStateException("Failed to update creators from Marvel");
        } catch (TimeoutException e) {
            logger.error("Failed to update creators from Marvel", e);
            throw new IllegalStateException("Failed to update creators from Marvel");
        } catch (ExternalServiceUnavailableException e) {
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
