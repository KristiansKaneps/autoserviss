package lv.kristianskaneps.autoserviss.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

public abstract class BaseRepository<E> extends BaseRepositoryWithoutId<E> implements PanacheRepository<E> {

}
