package lv.kristianskaneps.autoserviss.engine;

import io.quarkus.qute.EngineBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class EngineConfigurer {
    void configureEngine(@Observes EngineBuilder builder) {

    }
}
