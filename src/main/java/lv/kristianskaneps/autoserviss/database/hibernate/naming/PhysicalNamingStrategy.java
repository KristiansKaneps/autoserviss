package lv.kristianskaneps.autoserviss.database.hibernate.naming;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

@SuppressWarnings("unused")
public class PhysicalNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
    @Override
    public Identifier toPhysicalSequenceName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return super.toPhysicalSequenceName(logicalName, jdbcEnvironment);
    }
}
