package ch.heigvd.amt.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class Status {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Probe probe;

    private Instant timestamp;

    private Integer status;

    private Integer duration;

    public Status() {

    }

    public Status(Probe probe, Instant timestamp, Integer status, Integer duration) {
        this.probe = probe;
        this.timestamp = timestamp;
        this.status = status;
        this.duration = duration;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Probe getProbe() {
        return probe;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
