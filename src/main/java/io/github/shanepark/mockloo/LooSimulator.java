package io.github.shanepark.mockloo;

import io.github.shanepark.mockloo.Person.config.PeopleConfig;
import io.github.shanepark.mockloo.Person.domain.Person;
import io.github.shanepark.mockloo.config.RequestApi;
import io.github.shanepark.mockloo.loo.config.LooConfig;
import io.github.shanepark.mockloo.loo.domain.Loo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class LooSimulator {

    private final BlockingQueue<Loo> looQueue = new LinkedBlockingQueue<>();
    private final PeopleConfig peopleConfig;
    private final LooConfig looConfig;
    private final RequestApi requestApi;
    private final LooTracker looTracker;

    protected void simulate() {
        initLoo();
        initPeople();
    }

    private void initPeople() {
        int peopleCount = peopleConfig.count();
        for (int i = 0; i < peopleCount; i++) {
            final int finalI = i;
            Thread.ofVirtual()
                    .name("Person-" + i)
                    .start(() -> {
                        Person person = new Person(finalI, peopleConfig);
                        try {
                            this.simulatePerson(person);
                        } catch (InterruptedException e) {
                            log.warn("Interrupted Person-{}", finalI);
                        }
                    });
        }
    }

    private void initLoo() {
        looConfig.ids().forEach(id -> {
            Loo loo = new Loo(id, requestApi, looTracker);
            looQueue.offer(loo);
        });
    }

    private void simulatePerson(Person person) throws InterruptedException {
        while (true) {
            if (person.checkTank()) {
                Loo loo = looQueue.take();
                person.useLoo(loo);
                looQueue.put(loo);
            }
            Thread.sleep((long) (Math.random() * 1000));
        }
    }

}
