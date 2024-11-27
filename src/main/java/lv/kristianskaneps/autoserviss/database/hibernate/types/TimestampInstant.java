package lv.kristianskaneps.autoserviss.database.hibernate.types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class TimestampInstant implements UserType<Instant> {
    @Override
    public int getSqlType() {
        return SqlTypes.TIMESTAMP_UTC;
    }

    @Override
    public Class<Instant> returnedClass() {
        return Instant.class;
    }

    @Override
    public boolean equals(Instant x, Instant y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Instant x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public @Nullable Instant nullSafeGet(
            ResultSet rs,
            int position,
            SharedSessionContractImplementor session,
            Object owner
    ) throws SQLException {
        final Timestamp timestamp = rs.getObject(position, Timestamp.class);
        return timestamp == null || rs.wasNull() ? null : timestamp.toInstant();
    }

    @Override
    public void nullSafeSet(
            PreparedStatement st,
            @Nullable Instant value,
            int index,
            SharedSessionContractImplementor session
    ) throws SQLException {
        if (value == null) {
            st.setNull(index, SqlTypes.TIMESTAMP_UTC);
        } else {
            st.setObject(index, Timestamp.from(value));
        }
    }

    @Override
    public Instant deepCopy(Instant value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Instant value) {
        return value;
    }

    @Override
    public Instant assemble(Serializable cached, Object owner) {
        return (Instant) cached;
    }

    @Override
    public Instant replace(Instant detached, Instant managed, Object owner) {
        return detached;
    }
}
