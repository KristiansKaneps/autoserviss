package lv.kristianskaneps.autoserviss.controllers;

import io.quarkiverse.renarde.router.Router;
import io.quarkiverse.renarde.security.ControllerWithUser;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import lv.kristianskaneps.autoserviss.model.User;
import lv.kristianskaneps.autoserviss.repository.UserRepository;
import lv.kristianskaneps.autoserviss.types.PhoneNumber;
import org.hibernate.validator.constraints.Length;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestQuery;
import org.jetbrains.annotations.Nullable;

@ApplicationScoped
public class AuthController extends ControllerWithUser<User> {
    @Inject
    @NotNull
    UserRepository repository;

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance login();
        public static native TemplateInstance register(String email);
        public static native TemplateInstance logoutFirst();
        public static native TemplateInstance welcome();
    }

    public TemplateInstance index() {
        return this.login();
    }

    @RunOnVirtualThread
    @Path("/login")
    public TemplateInstance login() {
        return Templates.login();
    }

    @RunOnVirtualThread
    @Path("/register")
    public TemplateInstance register() {
        return Templates.register("");
    }

    @Path("/auth/login")
    @POST
    public Response loginAction(
            @RestForm("email") @NotBlank
            @NotNull String email,
            @RestForm("password") @NotBlank
            @NotNull String password
    ) {
        if (validationFailed()) {
            login();
        }
        User user = repository.findByEmail(email);
        if (user == null) {
            validation.addError("email", "Invalid e-mail address");
//            prepareForErrorRedirect();
            flash("email", email); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            login();
        } else if (!user.checkPassword(password)) {
            validation.addError("password", "Invalid password");
//            prepareForErrorRedirect();
            flash("email", email); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            login();
        }

        final NewCookie cookie = security.makeUserCookie(user);
        return Response.seeOther(Router.getURI(HomeController::index)).cookie(cookie).build();
    }

    @RunOnVirtualThread
    @Path("/auth/startRegistration")
    @POST
    public TemplateInstance startRegistrationAction(
            @RestForm @NotBlank @Length(min = 5, max = 320) String registerEmail
    ) {
        if (validationFailed()) {
            login();
        }
        return Templates.register(registerEmail);
    }

    @RunOnVirtualThread
    @Path("/logout")
    @GET
    public Response logout() {
        if (getUser() == null) {
            return Response.seeOther(Router.getURI(AuthController::login)).build();
        } else {
            return security.makeLogoutResponse(Router.getURI(AuthController::login));
        }
    }

    @Path("/confirm")
    public TemplateInstance confirm(@RestQuery @Length(min = 36, max = 36) String confirmationCode) {
        checkLogoutFirst();
        User newUser = checkConfirmationCode(confirmationCode);
        return login();
    }

    private void checkLogoutFirst() {
        if(getUser() != null) {
            logoutFirst();
        }
    }

    /**
     * Link to exclusive logout page
     */
    @RunOnVirtualThread
    @Path("/logout-first")
    public TemplateInstance logoutFirst() {
        return Templates.logoutFirst();
    }

    private User checkConfirmationCode(String confirmationCode) {
        if(confirmationCode.isBlank()) {
            flash("message", "Missing confirmation code");
            flash("messageType", "error");
            redirect(HomeController.class).index();
        }
        final @Nullable User user = repository.findForConfirmation(confirmationCode);
        if(user == null) {
            flash("message", "Invalid confirmation code");
            flash("messageType", "error");
            redirect(HomeController.class).index();
        }
        return user;
    }

    @Path("/auth/register")
    @POST
    public Response registerAction(
            @RestForm @NotBlank @Length(min = 5, max = 320) String email,
            @RestForm @NotBlank @Length(min = 1, max = 31) String name,
            @RestForm @NotBlank @Length(min = 1, max = 31) String surname,
            @RestForm @Length(max = 23) String phone,
            @RestForm @NotBlank @Length(min = 8) String password,
            @RestForm @NotBlank @Length(min = 8) String password2
    ) {
        checkLogoutFirst();
        validation.required("password", password);
        validation.required("password2", password2);
        validation.equals("password", password, password2);

        final boolean exists = repository.existsByEmail(email);

        if (exists) {
            validation.addError("email", "E-mail already taken");
        }

        if (validationFailed()) {
            flash("email", email);
            flash("name", name);
            flash("surname", surname);
            flash("phone", phone);
            return Response.seeOther(Router.getURI(AuthController::register)).build();
        }

        final User user = new User(
                email,
                name,
                surname,
                new PhoneNumber(phone)
        );

        user.setPassword(User.hashPassword(password));
        user.setStatus(User.Status.REGISTERED_UNCONFIRMED);

        repository.persist(user);

        final Response.ResponseBuilder responseBuilder = Response.seeOther(Router.getURI(AuthController::welcome));
        final NewCookie cookie = security.makeUserCookie(user);
        responseBuilder.cookie(cookie);
        return responseBuilder.build();
    }

    @Authenticated
    @Path("/welcome")
    public TemplateInstance welcome() {
        return Templates.welcome();
    }
}
