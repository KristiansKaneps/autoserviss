package lv.kristianskaneps.autoserviss.database.hibernate.types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RawString implements UserType<String> {
    @Override
    public int getSqlType() {
        return SqlTypes.VARBINARY;
    }

    @Override
    public Class<String> returnedClass() {
        return String.class;
    }

    @Override
    public boolean equals(String x, String y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(String x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public @Nullable String nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final byte @Nullable [] bytes = rs.getBytes(position);
        if (bytes == null) return null;
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, @Nullable String value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, SqlTypes.VARBINARY);
            return;
        }

        final byte @NotNull [] bytes = value.getBytes(StandardCharsets.UTF_8);
        st.setBytes(index, bytes);
    }

    @Override
    public String deepCopy(String value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(String value) {
        return value;
    }

    @Override
    public String assemble(Serializable cached, Object owner) {
        return (String) cached;
    }
}
