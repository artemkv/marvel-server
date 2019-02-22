package net.artemkv.marvelserver;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
class UpToDateHealthCheck implements HealthIndicator {
    private LocalDbUpdater localDbUpdater;

    public UpToDateHealthCheck(LocalDbUpdater localDbUpdater) {
        if (localDbUpdater == null) {
            throw new IllegalArgumentException("localDbUpdater");
        }
        this.localDbUpdater = localDbUpdater;
    }

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down()
                .withDetail("Local database is out of date", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        if (localDbUpdater.getLocalDatabaseState() == LocalDatabaseState.UP_TO_DATE) {
            return 0;
        }
        return -1;
    }
}
