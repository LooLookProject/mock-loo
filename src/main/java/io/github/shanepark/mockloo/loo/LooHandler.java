package io.github.shanepark.mockloo.loo;

import io.github.shanepark.mockloo.Person.config.PeopleConfig;
import io.github.shanepark.mockloo.Person.domain.Person;
import io.github.shanepark.mockloo.loo.config.LooConfig;
import io.github.shanepark.mockloo.loo.domain.Loo;
import io.github.shanepark.mockloo.util.RequestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
public class LooHandler implements ApplicationListener<ContextRefreshedEvent> {

    private final PeopleConfig peopleConfig;
    private final LooConfig looConfig;
    private final RequestApi requestApi;

    private final ConcurrentLinkedQueue<Loo> looQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        start();
    }

    private void start() {
        makeLoo();

        int peopleCount = peopleConfig.count();
        for (int i = 0; i < peopleCount; i++) {
            Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("Person-" + i);
            final int finalI = i;
            builder.start(() -> {
                Person person = new Person(finalI, peopleConfig);
                try {
                    this.looJob(person, looQueue);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

    private void makeLoo() {
        looConfig.ids().forEach(id -> {
            Loo loo = new Loo(id, requestApi);
            looQueue.offer(loo);
        });
    }

    private void looJob(Person person, ConcurrentLinkedQueue<Loo> looQueue) throws InterruptedException {
        while (true) {
            if (person.checkTank()) {
                Loo loo = looQueue.poll();
                if (loo == null) {
                    Thread.sleep(2000);
                    continue;
                }
                person.useLoo(loo);
                looQueue.offer(loo);
            }
            Thread.sleep((long) (Math.random() * 1000));
        }
    }

}
