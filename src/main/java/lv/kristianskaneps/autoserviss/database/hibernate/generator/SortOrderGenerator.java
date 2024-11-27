package lv.kristianskaneps.autoserviss.database.hibernate.generator;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Table;
import lv.kristianskaneps.autoserviss.model.traits.Sortable;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SharedSessionContract;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SortOrderGenerator implements BeforeExecutionGenerator {
    private static final Logger log = LoggerFactory.getLogger(SortOrderGenerator.class);

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        if (currentValue == null && owner instanceof Sortable model) {
            final long nextValue = generateNextOrderFor(model, session);
            model.setOrder(nextValue);
            return nextValue;
        }
        return currentValue;
    }

    @Override
    public boolean generatedOnExecution(Object entity, SharedSessionContractImplementor session) {
        return false;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EventTypeSets.INSERT_ONLY;
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }

    @Override
    public boolean generatesSometimes() {
        return true;
    }

    @Override
    public boolean generatesOnInsert() {
        return true;
    }

    @Override
    public boolean generatesOnUpdate() {
        return false;
    }

    private static final ConcurrentMap<Class<? extends Sortable>, String> modelTableNames = new ConcurrentHashMap<>();

    private long generateNextOrderFor(final Sortable model, final SharedSessionContractImplementor sessionImplementor) {
        final @NotNull String tableName = modelTableNames.computeIfAbsent(model.getClass(), modelClass -> {
            final @Nullable Table tableAnnotation = modelClass.getAnnotation(Table.class);
            if (tableAnnotation == null) {
                return toUnderscoreName(modelClass.getSimpleName()).toLowerCase(Locale.ROOT) + 's';
            } else {
                return modelClass.getAnnotation(Table.class).name();
            }
        });
        return generateNextVal(tableName, sessionImplementor);
    }

    private long generateNextVal(final @NotNull String tableName, final SharedSessionContractImplementor sessionImplementor) {
        long generatedOrder = 0;

        try {
            final SharedSessionContract session = sessionImplementor.isStatelessSession()
                    ? sessionImplementor.asStatelessSession()
                    : sessionImplementor.getSession();
            generatedOrder = session.createNativeQuery("SELECT MAX('order') FROM " + tableName, Long.class)
                    .getSingleResult();
        } catch (final NonUniqueResultException | NoResultException e) {
            log.error("Error occurred while generating next value for {}", tableName, e);
        }

        return generatedOrder;
    }

    private boolean isUnderscoreRequired(final char before, final char current, final char after) {
        return Character.isLowerCase(before) && Character.isUpperCase(current) && Character.isLowerCase(after);
    }

    private @NotNull String toUnderscoreName(final @NotNull String name) {
        final StringBuilder builder = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i < builder.length() - 1; i++) {
            if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
                builder.insert(i++, '_');
            }
        }
        return builder.toString();
    }
}
