package ch.heigvd.amt.services;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProbeServiceTest {

    @Inject
    ProbeService probeService;

    @Test
    @TestTransaction
    void testProbeService() {
        var probeList = probeService.listProb();
        assertEquals(0, probeList.size());

        var probe = probeService.getOrCreateProbe("https://example.com");
        assertNotNull(probe);

        assertEquals("https://example.com", probe.getUrl());
        var probeList2 = probeService.listProb();
        assertEquals(1, probeList2.size());
    }
}