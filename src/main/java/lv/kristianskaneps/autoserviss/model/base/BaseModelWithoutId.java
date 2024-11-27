package lv.kristianskaneps.autoserviss.model.base;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModelWithoutId extends PanacheEntityBase implements BaseModelInterface {

}
