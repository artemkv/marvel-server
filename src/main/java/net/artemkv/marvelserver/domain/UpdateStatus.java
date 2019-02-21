package net.artemkv.marvelserver.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class UpdateStatus {
    @Id
    private int id;
    private Date lastSyncDate;

    public UpdateStatus() {
    }

    public UpdateStatus(int id, Date lastSyncDate) {
        this.id = id;
        this.setLastSyncDate(lastSyncDate);
    }

    public Date getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
}
