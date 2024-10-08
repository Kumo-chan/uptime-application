package ch.heigvd.amt.services;

import ch.heigvd.amt.entites.Probe;
import ch.heigvd.amt.entites.Status;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class ProbeService {

    @Inject
    EntityManager entityManager;

    public List<Probe> listProb() {
        return entityManager.createQuery("SELECT p FROM  Probe  p", Probe.class).getResultList();
    }

    @Transactional
    public Probe getOrCreateProbe(String url) {
        List<Probe> probeList = entityManager.createQuery("SELECT p FROM Probe p WHERE p.url =:url", Probe.class).setParameter("url", url).getResultList();
        if (probeList.isEmpty()) {
            Probe newProbe = new Probe();
            newProbe.setUrl(url);
            entityManager.persist(newProbe);
            return newProbe;
        }
        return probeList.get(0);
    }

    public void executeProbe(String url) {
        var start = Instant.now();
        try (var client = HttpClient
                .newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(1)).build()) {
            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("User-Agent", "Uptime/0.0.1")
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Expires", "0")
                    .build();

            var statusCode = client.send(request, HttpResponse.BodyHandlers.discarding()).statusCode();
            var end = Instant.now();
            var duration = Duration.between(start, end).toMillis();

            var probe = getOrCreateProbe(url);
            var status = new Status(probe, start, statusCode, (int) duration);
            entityManager.persist(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Probe getProbeById(long id) {
        return entityManager.createQuery("SELECT p FROM Probe  p WHERE id = :id", Probe.class).setParameter("id", id).getResultList().get(0);
    }

    public List<Status> getStatusList(Probe probe) {
        return entityManager.createQuery("SELECT s FROM Status s WHERE s.probe = :probe ORDER BY s.timestamp DESC LIMIT 100", Status.class)
                .setParameter("probe", probe)
                .getResultList();

    }
}
