package ch.heigvd.amt.ressources;

import ch.heigvd.amt.services.ProbeService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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
    Template statusPage;

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

    @POST
    @Path("/probes")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance registerProbes(@FormParam("url") String url) {
        probeService.getOrCreateProbe(url);
        return probesPage.data("probeList", probeService.listProb());

    }
    @GET
    @Path("probes/{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance probe(@PathParam("id") long id) {
        var probe = probeService.getProbeById(id);
        var statusList = probeService.getStatusList(probe);
        return statusPage.data("status", statusList.get(0));
    }   
}
