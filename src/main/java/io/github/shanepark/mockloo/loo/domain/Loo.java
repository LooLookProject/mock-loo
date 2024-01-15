package io.github.shanepark.mockloo.loo.domain;

import io.github.shanepark.mockloo.LooTracker;
import io.github.shanepark.mockloo.config.RequestApi;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class Loo {
    private final String id;
    private boolean isLocked = false;

    @Getter(AccessLevel.NONE)
    private final RequestApi requestApi;
    @Getter(AccessLevel.NONE)
    private final LooTracker looTracker;

    public Loo(String id, RequestApi requestApi, LooTracker looTracker) {
        this.id = id;
        this.requestApi = requestApi;
        this.looTracker = looTracker;
        looTracker.addLoo(this);
    }

    public void lock() {
        this.isLocked = true;
        looTracker.update();
        requestApi.sendLockRequest(id);
    }

    public void unlock() {
        this.isLocked = false;
        looTracker.update();
        requestApi.sendUnlockRequest(id);
    }

}
