package lv.kristianskaneps.autoserviss.repository;

import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import lv.kristianskaneps.autoserviss.database.ReactiveDatabase;
import org.jetbrains.annotations.Nullable;

public abstract class BaseRepositoryWithoutId<E> {
    @Inject
    @NotNull
    ReactiveDatabase database;

    public static @NotNull Tuple tuple(final @Nullable Object... elements) { return Tuple.from(elements); }
}
