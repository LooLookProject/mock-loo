package io.github.shanepark.mockloo.config;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.PostExchange;

public interface RequestApi {

    @PostExchange(url = "/lock/{id}")
    void sendLockRequest(@PathVariable("id") String id);

    @PostExchange(url = "/unlock/{id}")
    void sendUnlockRequest(@PathVariable("id") String id);

}
