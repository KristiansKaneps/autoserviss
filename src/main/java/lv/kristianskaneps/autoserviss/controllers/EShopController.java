package lv.kristianskaneps.autoserviss.controllers;

import io.quarkiverse.renarde.security.ControllerWithUser;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import lv.kristianskaneps.autoserviss.model.User;

@ApplicationScoped
public class EShopController extends ControllerWithUser<User> {
    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index();
    }

    @RunOnVirtualThread
    @Path("/e-shop")
    public TemplateInstance index() {
        return Templates.index();
    }
}
