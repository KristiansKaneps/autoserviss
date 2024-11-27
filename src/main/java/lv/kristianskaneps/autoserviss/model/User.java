package lv.kristianskaneps.autoserviss.model;

import io.quarkiverse.renarde.security.RenardeUser;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.persistence.*;
import lv.kristianskaneps.autoserviss.database.hibernate.types.RawString;
import lv.kristianskaneps.autoserviss.model.base.TimestampedBaseModel;
import lv.kristianskaneps.autoserviss.types.PhoneNumber;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Table(name = "users")
@Entity
public class User extends TimestampedBaseModel implements RenardeUser {
    @Column
    protected String email;
    @Column
    protected String name;
    @Column
    protected String surname;
    @Column
    protected PhoneNumber phone;
    @Column
    protected String roles = "user";
    @Column
    @Enumerated(EnumType.ORDINAL)
    protected Status status = Status.CONFIRMATION_REQUIRED;
    @Column
    @Type(RawString.class)
    protected String confirmationCode;
    @Column
    @Type(RawString.class)
    protected String password;

    public enum Status {
        CONFIRMATION_REQUIRED, // confirmation is mandatory
        REGISTERED_UNCONFIRMED, // confirmation is optional
        REGISTERED, // confirmation is done
    }

    public User() {

    }

    public User(@NotNull String email, @NotNull String name, @NotNull String surname, @Nullable PhoneNumber phone) {
        this();
        this.email = email.toLowerCase(Locale.ROOT);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public boolean checkPassword(final @NotNull String password) {
        return BcryptUtil.matches(password, this.password);
    }

    public static @NotNull String hashPassword(final @NotNull String password) {
        return BcryptUtil.bcryptHash(password, 16);
    }

    public @NotNull String getEmail() { return email; }
    public void setEmail(final @NotNull String email) { this.email = email.toLowerCase(Locale.ROOT); }

    public @NotNull String getName() { return name; }
    public void setName(final @NotNull String name) { this.name = name; }

    public @NotNull String getSurname() { return surname; }
    public void setSurname(final @NotNull String surname) { this.surname = surname; }

    public @NotNull PhoneNumber getPhone() { return phone; }
    public void setPhone(final @NotNull PhoneNumber phone) { this.phone = phone; }

    public @NotNull String getRoles() { return roles; }
    public void setRoles(final @NotNull String roles) { this.roles = roles; }

    public @NotNull Set<@NotNull String> getRolesSet() { return Set.of(roles.split(",")); }
    public void setRolesSet(final @NotNull Collection<@NotNull String> roles) {
        final Set<@NotNull String> set = roles instanceof Set
                ? (Set<@NotNull String>) roles
                : new HashSet<>(roles);
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> iterator = set.iterator();
        if (iterator.hasNext()) sb.append(iterator.next());
        while (iterator.hasNext()) {
            sb.append(',');
            sb.append(iterator.next());
        }
        this.roles = sb.toString();
    }

    public @NotNull Status getStatus() { return status; }
    public void setStatus(final @NotNull Status status) { this.status = status; }

    public @NotNull String getConfirmationCode() { return confirmationCode; }
    public void setConfirmationCode(final @NotNull String confirmationCode) { this.confirmationCode = confirmationCode; }

    public @NotNull String getPassword() { return password; }
    public void setPassword(final @NotNull String password) { this.password = password; }

    @Override
    public Set<String> roles() {
        return getRolesSet();
    }

    @Override
    public String userId() {
        return getEmail();
    }

    @Override
    public boolean registered() {
        return this.status == Status.REGISTERED || this.status == Status.REGISTERED_UNCONFIRMED;
    }
}
