package ch.heigvd.amt.ressources;

import ch.heigvd.amt.services.ProbeService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class ProbeResource {

    @Inject
    Template indexPage;

    @Inject
    Template registerPage;

    @Inject
    Template probesPage;

    @Inject
    ProbeService probeService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        return indexPage.instance();
    }

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance register() {
        return registerPage.instance();
    }

    @GET
    @Path("/probes")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance probes() {
        var probeList = probeService.listProb();
        return probesPage.data("probeList", probeList);
    }

}
