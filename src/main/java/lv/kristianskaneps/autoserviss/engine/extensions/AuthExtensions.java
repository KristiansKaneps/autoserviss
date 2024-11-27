package lv.kristianskaneps.autoserviss.engine.extensions;

import io.quarkus.arc.Arc;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.security.identity.CurrentIdentityAssociation;
import io.quarkus.security.identity.SecurityIdentity;
import lv.kristianskaneps.autoserviss.model.User;
import lv.kristianskaneps.autoserviss.repository.UserRepository;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@TemplateExtension(namespace = "auth")
public class AuthExtensions {
    private static SecurityIdentity getIdentity() {
        try (var identityAssociationHandle = Arc.container().instance(CurrentIdentityAssociation.class)) {
            var identityAssociation = identityAssociationHandle.get();
            return identityAssociation.getIdentity();
        }
    }

    public static boolean isAuthenticated() {
        try (
                var identityAssociationHandle = Arc.container().instance(CurrentIdentityAssociation.class);
                var repositoryHandle = Arc.container().instance(UserRepository.class)) {
            var repository = repositoryHandle.get();
            return repository.existsRegisteredByEmail(identityAssociationHandle.get().getIdentity().getPrincipal().getName());
        }
    }

    public static boolean isNotAuthenticated() {
        return !isAuthenticated();
    }

    public static @Nullable User user() {
        try (
                var identityAssociationHandle = Arc.container().instance(CurrentIdentityAssociation.class);
                var repositoryHandle = Arc.container().instance(UserRepository.class)) {
            var repository = repositoryHandle.get();
            return repository.findRegisteredByEmail(identityAssociationHandle.get().getIdentity().getPrincipal().getName());
        }
    }

    public static String email() {
        return getIdentity().getPrincipal().getName();
    }

    public static Set<String> roles() {
        return getIdentity().getRoles();
    }
}
