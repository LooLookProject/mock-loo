package io.github.shanepark.mockloo.Person.domain;

import io.github.shanepark.mockloo.Person.config.PeopleConfig;
import io.github.shanepark.mockloo.loo.domain.Loo;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
public class Person {
    private final int index;
    private final PeopleConfig peopleConfig;
    private final double peeGaugeLimit;
    private final double pooGaugeLimit;
    private final Random random;
    private double peeGauge;
    private double pooGauge;

    public Person(int index, PeopleConfig peopleConfig) {
        this.index = index;
        this.peopleConfig = peopleConfig;
        random = new Random(LocalDateTime.now().getNano());
        peeGaugeLimit = random.nextDouble(0.5d, 1);
        pooGaugeLimit = random.nextDouble(0.5d, 1);
        peeGauge = random.nextDouble(peeGaugeLimit);
        pooGauge = random.nextDouble(pooGaugeLimit);
    }

    public boolean checkTank() {
        if (peeGauge >= peeGaugeLimit || pooGauge >= pooGaugeLimit) {
            return true;
        }
        this.peeGauge += random.nextDouble(0.01);
        this.pooGauge += random.nextDouble(0.005);
        return false;
    }

    public void useLoo(Loo loo) {
        try {
            log.info("[Loo-{}] Lock by Person-{}", loo.getId(), index);
            loo.lock();
            Thread.sleep(peopleConfig.useToiletSeconds() * 1000L);
            flushPoo();
            flushPee();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            loo.unlock();
            log.info("[Loo-{}] Unlock by Person-{}", loo.getId(), index);
        }
    }

    private void flushPee() {
        if (this.peeGauge >= peeGaugeLimit * 0.6) {
            this.peeGauge = 0.0;
        }
    }

    private void flushPoo() {
        if (this.pooGauge >= pooGaugeLimit * 0.9) {
            this.pooGauge = 0.0;
        }
    }
}
