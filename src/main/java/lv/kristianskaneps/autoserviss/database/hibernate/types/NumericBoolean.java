package lv.kristianskaneps.autoserviss.database.hibernate.types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SqlTypes;
import org.hibernate.usertype.UserType;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumericBoolean implements UserType<Boolean> {
    @Override
    public int getSqlType() {
        return SqlTypes.NUMERIC;
    }

    @Override
    public Class<Boolean> returnedClass() {
        return Boolean.class;
    }

    @Override
    public boolean equals(Boolean x, Boolean y) {
        return x == y;
    }

    @Override
    public int hashCode(Boolean x) {
        return x == null ? 0 : x.hashCode();
    }

    @Override
    public @Nullable Boolean nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final byte b = rs.getByte(position);
        return rs.wasNull() ? null : (b == 1);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, @Nullable Boolean value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, SqlTypes.NUMERIC);
        } else {
            st.setByte(index, (byte) (value ? 1 : 0));
        }
    }

    @Override
    public Boolean deepCopy(Boolean value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Boolean value) {
        return value;
    }

    @Override
    public Boolean assemble(Serializable cached, Object owner) {
        return (Boolean) cached;
    }
}
