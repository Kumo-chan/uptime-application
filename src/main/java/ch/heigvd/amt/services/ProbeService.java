package ch.heigvd.amt.services;

import ch.heigvd.amt.entites.Probe;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ProbeService {

    @Inject
    EntityManager entityManager;

    public List<Probe> listProb() {
        return entityManager.createQuery("SELECT p FROM  Probe  p", Probe.class).getResultList();
    }
}
