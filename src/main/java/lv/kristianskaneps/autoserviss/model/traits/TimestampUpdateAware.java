package lv.kristianskaneps.autoserviss.model.traits;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public interface TimestampUpdateAware {
    Instant getUpdatedAt();
    void setUpdatedAt(final @NotNull Instant updatedAt);
}
