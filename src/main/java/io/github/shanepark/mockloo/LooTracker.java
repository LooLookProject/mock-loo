package io.github.shanepark.mockloo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shanepark.mockloo.loo.domain.Loo;
import io.github.shanepark.mockloo.websocket.LooWebsocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LooTracker {
    private final LooWebsocketHandler websocketHandler;
    private final List<Loo> loos = Collections.synchronizedList(new ArrayList<>());
    private final ObjectMapper mapper = new ObjectMapper();

    public void addLoo(Loo loo) {
        this.loos.add(loo);
    }

    public void update() {
        try {
            String status = mapper.writeValueAsString(loos);
            websocketHandler.sendStatus(status);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
