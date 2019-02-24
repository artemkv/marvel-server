package net.artemkv.marvelserver;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class ScheduledTasks {
    private static final int TWENTY_FOUR_HOURS = 86_400_000;

    private LocalDbUpdater dbUpdater;

    public ScheduledTasks(LocalDbUpdater dbUpdater) {
        if (dbUpdater == null) {
            throw new IllegalArgumentException("dbUpdater");
        }
        this.dbUpdater = dbUpdater;
    }

    @Scheduled(fixedRate = TWENTY_FOUR_HOURS)
    public void updateLocalDb() {
        dbUpdater.update();
    }
}
