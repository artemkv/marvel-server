package net.artemkv.marvelserver;

import net.artemkv.marvelserver.domain.CreatorModel;
import net.artemkv.marvelserver.domain.UpdateStatus;
import net.artemkv.marvelserver.marvelconnector.Creator;
import net.artemkv.marvelserver.marvelconnector.ExternalServiceUnavailableException;
import net.artemkv.marvelserver.marvelconnector.GetCreatorsResult;
import net.artemkv.marvelserver.marvelconnector.IntegrationException;
import net.artemkv.marvelserver.marvelconnector.TimeoutException;
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
            boolean hasMore = true;
            int offset = 0;
            while (hasMore) {
                GetCreatorsResult result =
                    marvelApiService.getCreators(updateStatus.getLastSyncDate(), offset);
                Date newLastSyncDate = updateStatus.getLastSyncDate();
                for (Creator creator : result.getCreators()) {
                    // update last synced date
                    if (creator.getModified().after(newLastSyncDate)) {
                        newLastSyncDate = creator.getModified();
                    }
                    logger.trace("Found creator: " + creator.getFullName());
                    // save into local db
                    CreatorModel creatorModel = new CreatorModel(creator);
                    creatorRepository.save(creatorModel);
                }
                // save last sync date
                updateStatus.setLastSyncDate(newLastSyncDate);
                updateStatusRepository.save(updateStatus);

                hasMore = result.hasMore();
                offset = result.getNewOffset();

                if (hasMore) {
                    logger.debug("Marvel has more creators starting from " + offset);
                } else {
                    logger.debug("Done updating creators from Marvel");
                }
            }
        } catch (IntegrationException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExternalServiceUnavailableException e) {
            e.printStackTrace();
        }

        setLocalDatabaseState(LocalDatabaseState.UP_TO_DATE);
    }

    public synchronized LocalDatabaseState getLocalDatabaseState() {
        return localDatabaseState;
    }

    private synchronized void setLocalDatabaseState(LocalDatabaseState localDatabaseState) {
        this.localDatabaseState = localDatabaseState;
    }
}
