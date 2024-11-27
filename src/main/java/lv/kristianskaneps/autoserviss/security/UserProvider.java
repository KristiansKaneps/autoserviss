package lv.kristianskaneps.autoserviss.security;

import io.quarkiverse.renarde.security.RenardeSecurity;
import io.quarkiverse.renarde.security.RenardeUserProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import lv.kristianskaneps.autoserviss.model.User;
import lv.kristianskaneps.autoserviss.repository.UserRepository;

@ApplicationScoped
public class UserProvider implements RenardeUserProvider {
    @Inject
    @NotNull
    RenardeSecurity security;

    @Inject
    @NotNull
    UserRepository repository;

    @Override
    public User findUser(String tenantId, String id) {
        // https://github.com/quarkiverse/quarkus-renarde/issues/250
        return repository.findByEmail(id);
    }
}
