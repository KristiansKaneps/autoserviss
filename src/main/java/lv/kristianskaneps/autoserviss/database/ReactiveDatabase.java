package lv.kristianskaneps.autoserviss.database;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.oracleclient.OraclePool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
public class ReactiveDatabase {
    private final @NotNull OraclePool pool;

    public ReactiveDatabase(
            @SuppressWarnings("CdiInjectionPointsInspection") final @NotNull OraclePool pool
    ) {
        this.pool = pool;
    }

    public @NotNull OraclePool client() {
        return this.pool;
    }

    protected void onStart(@Observes final @NotNull StartupEvent event) {

    }
}
