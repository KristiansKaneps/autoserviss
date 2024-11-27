package lv.kristianskaneps.autoserviss.database.hibernate.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lv.kristianskaneps.autoserviss.types.PhoneNumber;
import org.jetbrains.annotations.Nullable;

@Converter(autoApply = true)
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {
    @Override
    public String convertToDatabaseColumn(final @Nullable PhoneNumber attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(final @Nullable String dbData) {
        return dbData == null ? null : new PhoneNumber(dbData);
    }
}
