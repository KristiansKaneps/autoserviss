package lv.kristianskaneps.autoserviss.model.traits;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public interface TimestampCreateAware {
    Instant getCreatedAt();
    void setCreatedAt(final @NotNull Instant createdAt);
}
