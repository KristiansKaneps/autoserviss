package lv.kristianskaneps.autoserviss.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lv.kristianskaneps.autoserviss.model.User;

import java.util.Objects;

@ApplicationScoped
public class UserRepository extends BaseRepository<User> {
    @Transactional(Transactional.TxType.SUPPORTS)
    public User findUnconfirmedByEmail(final String email) {
        return find("lower(email) = lower(?1) and status <= ?2", email, User.Status.REGISTERED_UNCONFIRMED).firstResult();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User findRegisteredByEmail(final String email) {
        return find("lower(email) = lower(?1) and status >= ?2", email, User.Status.REGISTERED_UNCONFIRMED).firstResult();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public boolean existsRegisteredByEmail(final String email) {
        return Objects.equals(getSession()
                .createQuery("select 1 from User where lower(email) = lower(?1) and status >= ?2", Integer.class)
                .setParameter(1, email)
                .setParameter(2, User.Status.REGISTERED_UNCONFIRMED)
                .getSingleResultOrNull(), 1);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public boolean existsByEmail(final String email) {
        return Objects.equals(getSession()
                .createQuery("select 1 from User where lower(email) = lower(?1)", Integer.class)
                .setParameter(1, email)
                .getSingleResultOrNull(), 1);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User findByEmail(final String email) {
        return find("lower(email) = lower(?1)", email).firstResult();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User findForConfirmation(final String confirmationCode) {
        return find("lower(confirmationCode) = lower(?1) and status <= ?2", confirmationCode, User.Status.REGISTERED_UNCONFIRMED).firstResult();
    }
}
