package lv.kristianskaneps.autoserviss.model.base;

import jakarta.persistence.*;
import lv.kristianskaneps.autoserviss.database.hibernate.generator.LongId;
import lv.kristianskaneps.autoserviss.model.traits.Identifiable;

@MappedSuperclass
public abstract class BaseModel extends BaseModelWithoutId implements Identifiable {
    @Id
    @LongId
    @Column(name = "id", unique = true, nullable = false, precision = 19, scale = 0, columnDefinition = "number(19)")
    protected Long id;

    @Override
    public Long getId() { return id; }
    @Override
    public void setId(final Long id) { this.id = id; }
}
