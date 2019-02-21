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
import org.springframework.stereotype.Component;

import java.util.Date;

// TODO: make singleton
@Component
public class LocalDbUpdater {
    // TODO: use status to indicate service readiness / health

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
                    // log
                    System.out.println(creator.getFullName()); // TODO: replace with log
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
                    System.out.println("has more starting from " + offset); // TODO: replace with log
                }
            }
        } catch (IntegrationException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExternalServiceUnavailableException e) {
            e.printStackTrace();
        }
    }
}
