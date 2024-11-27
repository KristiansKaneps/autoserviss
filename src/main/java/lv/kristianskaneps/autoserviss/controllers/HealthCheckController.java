package lv.kristianskaneps.autoserviss.controllers;

import io.quarkiverse.renarde.Controller;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class HealthCheckController extends Controller {
    public enum ServiceKey {
        OVERALL,
    }

    public enum HealthStatus {
        OK,
        ERROR
    }

    @RunOnVirtualThread
    @Path("/health-check")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Check overall status of API services",
        content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public @NotNull HealthStatus getHealthCheckResponse() {
        return HealthStatus.OK;
    }

    @RunOnVirtualThread
    @Path("/detailed-health-check")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            responseCode = "200",
            description = "Check detailed status of API services",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public @NotNull Map<ServiceKey, HealthStatus> getDetailedHealthCheckResponse() {
        final HealthStatus overallStatus = HealthStatus.OK;
        final HashMap<ServiceKey, HealthStatus> map = new HashMap<>();
        map.put(ServiceKey.OVERALL, overallStatus);
        return map;
    }
}
