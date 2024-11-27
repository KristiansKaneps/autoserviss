package lv.kristianskaneps.autoserviss.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lv.kristianskaneps.autoserviss.database.hibernate.types.NumericBoolean;
import lv.kristianskaneps.autoserviss.database.hibernate.types.TimestampInstant;
import lv.kristianskaneps.autoserviss.model.base.BaseModel;
import lv.kristianskaneps.autoserviss.model.traits.TimestampCreateAware;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@Table(name = "contact_us_requests")
@Entity
public class ContactUs extends BaseModel implements TimestampCreateAware {
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String email;
    @Column
    protected String phone;
    @Column(nullable = false)
    protected String subject;
    @Column(nullable = false)
    protected String content;
    @Column(nullable = false)
    @Type(NumericBoolean.class)
    protected boolean replied;
    @Column(nullable = false)
    @Type(TimestampInstant.class)
    protected Instant createdAt;

    public ContactUs() {

    }

    public ContactUs(@NotNull String name, @NotNull String email, @Nullable String phone, @NotNull String subject, @NotNull String content, boolean replied) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.content = content;
        this.replied = replied;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(final @NotNull String name) {
        this.name = name;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(final @NotNull String email) {
        this.email = email;
    }

    public @Nullable String getPhone() {
        return phone;
    }

    public void setPhone(final @Nullable String phone) {
        this.phone = phone;
    }

    public @NotNull String getSubject() {
        return subject;
    }

    public void setSubject(final @NotNull String subject) {
        this.subject = subject;
    }

    public @NotNull String getContent() {
        return content;
    }

    public void setContent(final @Nullable String content) {
        this.content = content == null ? "" : content;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(final boolean replied) {
        this.replied = replied;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(final @NotNull Instant createdAt) {
        this.createdAt = createdAt;
    }
}
