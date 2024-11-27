package lv.kristianskaneps.autoserviss.controllers;

import io.quarkiverse.renarde.security.ControllerWithUser;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lv.kristianskaneps.autoserviss.model.ContactUs;
import lv.kristianskaneps.autoserviss.model.User;
import lv.kristianskaneps.autoserviss.repository.ContactUsRepository;
import org.hibernate.validator.constraints.Length;
import org.jboss.resteasy.reactive.RestForm;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ApplicationScoped
public class ContactUsController extends ControllerWithUser<User> {
    @Inject
    @NotNull
    ContactUsRepository repository;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index();
        public static native TemplateInstance list(@NotNull List<ContactUs> entities);
    }

    @RunOnVirtualThread
    @Path("/contact-us")
    public TemplateInstance index() {
        return Templates.index();
    }

    @RunOnVirtualThread
    @Path("/contact-us/add")
    @POST
    public void add(
            @RestForm("name") @NotBlank @Length(max = 31)
            @NotNull String name,
            @RestForm("email") @NotBlank @Length(max = 320)
            @NotNull String email,
            @RestForm("phone") @Length(max = 23)
            @Nullable String phone,
            @RestForm("subject") @NotBlank @Length(max = 31)
            @NotNull String subject,
            @RestForm("content") @NotBlank @Length(max = 255)
            @NotNull String content
    ) {
        if (validationFailed()) {
            index();
        } else {
            ContactUs contactUs = new ContactUs(
                    name,
                    email,
                    phone,
                    subject,
                    content,
                    false
            );

            contactUs.persistAndFlush();

            index();
        }
    }

    @RunOnVirtualThread
    @Path("/contact-us/list")
    public TemplateInstance list() {
        var entities = repository.getClientsThatWantToContactUs();
        return Templates.list(entities);
    }
}
