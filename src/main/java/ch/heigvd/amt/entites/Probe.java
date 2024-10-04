package ch.heigvd.amt.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

@Entity
public class Probe {
    @Id
    @NotNull
    @NotBlank
    @URL
    private String url;

    public Probe() {

    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
