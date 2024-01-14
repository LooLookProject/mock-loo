package io.github.shanepark.mockloo.Person.domain;

import io.github.shanepark.mockloo.Person.config.PeopleConfig;
import io.github.shanepark.mockloo.loo.domain.Loo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
public class Person {
    private final int index;
    private final PeopleConfig peopleConfig;

    Random random = new Random(LocalDateTime.now().getNano());
    private double peeGauge = random.nextDouble(0.6);
    private double pooGauge = random.nextDouble(0.6);

    public boolean checkTank() {
        if (peeGauge > 0.8 || pooGauge > 0.8) {
            return true;
        }
        this.peeGauge += random.nextDouble(0.01);
        this.pooGauge += random.nextDouble(0.01);
        return false;
    }

    public void useLoo(Loo loo) {
        try {
            log.info("[Loo-{}] Lock by Person-{}", loo.getId(), index);
            loo.lock();
            Thread.sleep(peopleConfig.useToiletSeconds() * 1000L);
            this.peeGauge = 0.0;
            this.pooGauge = 0.0;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            loo.unlock();
            log.info("[Loo-{}] Unlock by Person-{}", loo.getId(), index);
        }
    }
}
