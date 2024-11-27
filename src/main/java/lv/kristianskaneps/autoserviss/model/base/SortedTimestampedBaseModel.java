package lv.kristianskaneps.autoserviss.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lv.kristianskaneps.autoserviss.database.hibernate.generator.SortOrder;
import lv.kristianskaneps.autoserviss.model.traits.Sortable;

@MappedSuperclass
public abstract class SortedTimestampedBaseModel extends TimestampedBaseModel implements Sortable {
    @SortOrder
    @Column(nullable = false, precision = 19, scale = 0, columnDefinition = "number(19)")
    protected long order;

    @Override
    public long getOrder() { return order; }
    @Override
    public void setOrder(final long order) { this.order = order; }
}
