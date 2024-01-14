package io.github.shanepark.mockloo.loolock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@Slf4j
public class LooLockMockController {

    @PostMapping("/lock/{id}")
    public void lock(@PathVariable String id) {
//        log.info("[Request Received] Lock : {}", id);
    }

    @PostMapping("/unlock/{id}")
    public void unlock(@PathVariable String id) {
//        log.info("[Request Received] Unlock : {}", id);
    }

}
