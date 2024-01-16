package io.github.shanepark.mockloo.loo.domain;

import io.github.shanepark.mockloo.LooTracker;
import io.github.shanepark.mockloo.config.RequestApi;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Getter
@Slf4j
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
        try {
            requestApi.sendLockRequest(id);
        } catch (WebClientRequestException e) {
            log.warn("Failed to send lock request: {} ", e.getMessage());
        }
    }

    public void unlock() {
        this.isLocked = false;
        looTracker.update();
        try {
            requestApi.sendUnlockRequest(id);
        } catch (WebClientRequestException e) {
            log.warn("Failed to send unlock request: {} ", e.getMessage());
        }
    }

}
