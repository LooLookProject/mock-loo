package io.github.shanepark.mockloo.loo.domain;

import io.github.shanepark.mockloo.util.RequestApi;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Loo {
    private final String id;
    private boolean isLocked = false;

    @Getter(AccessLevel.NONE)
    private final RequestApi requestApi;

    public void lock() {
        requestApi.sendLockRequest(id);
        this.isLocked = true;
    }

    public void unlock() {
        requestApi.sendUnlockRequest(id);
        this.isLocked = false;
    }

}
