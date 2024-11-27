package lv.kristianskaneps.autoserviss.model.base;

import jakarta.persistence.*;
import lv.kristianskaneps.autoserviss.database.hibernate.types.TimestampInstant;
import lv.kristianskaneps.autoserviss.model.traits.TimestampAware;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

@MappedSuperclass
public abstract class TimestampedBaseModel extends BaseModel implements TimestampAware {
    @Column(nullable = false)
    @Type(TimestampInstant.class)
    protected Instant updatedAt;
    @Column(nullable = false)
    @Type(TimestampInstant.class)
    protected Instant createdAt;

    @Override
    public Instant getUpdatedAt() { return updatedAt; }
    @Override
    public void setUpdatedAt(final @NotNull Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public Instant getCreatedAt() { return createdAt; }
    @Override
    public void setCreatedAt(final @NotNull Instant createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
