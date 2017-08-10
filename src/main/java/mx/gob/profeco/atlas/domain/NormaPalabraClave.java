package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NormaPalabraClave.
 */
@Entity
@Table(name = "norma_palabra_clave")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "normapalabraclave")
public class NormaPalabraClave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "palabra_clave_id")
    private Long palabraClaveId;

    @Column(name = "idioma_cat_id")
    private Long idiomaCatId;

    @Column(name = "norma_idioma_id")
    private Long normaIdiomaId;

    @Column(name = "norma_idioma_norma_id")
    private Long normaIdiomaNormaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPalabraClaveId() {
        return palabraClaveId;
    }

    public NormaPalabraClave palabraClaveId(Long palabraClaveId) {
        this.palabraClaveId = palabraClaveId;
        return this;
    }

    public void setPalabraClaveId(Long palabraClaveId) {
        this.palabraClaveId = palabraClaveId;
    }

    public Long getIdiomaCatId() {
        return idiomaCatId;
    }

    public NormaPalabraClave idiomaCatId(Long idiomaCatId) {
        this.idiomaCatId = idiomaCatId;
        return this;
    }

    public void setIdiomaCatId(Long idiomaCatId) {
        this.idiomaCatId = idiomaCatId;
    }

    public Long getNormaIdiomaId() {
        return normaIdiomaId;
    }

    public NormaPalabraClave normaIdiomaId(Long normaIdiomaId) {
        this.normaIdiomaId = normaIdiomaId;
        return this;
    }

    public void setNormaIdiomaId(Long normaIdiomaId) {
        this.normaIdiomaId = normaIdiomaId;
    }

    public Long getNormaIdiomaNormaId() {
        return normaIdiomaNormaId;
    }

    public NormaPalabraClave normaIdiomaNormaId(Long normaIdiomaNormaId) {
        this.normaIdiomaNormaId = normaIdiomaNormaId;
        return this;
    }

    public void setNormaIdiomaNormaId(Long normaIdiomaNormaId) {
        this.normaIdiomaNormaId = normaIdiomaNormaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NormaPalabraClave normaPalabraClave = (NormaPalabraClave) o;
        if (normaPalabraClave.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), normaPalabraClave.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NormaPalabraClave{" +
            "id=" + getId() +
            ", palabraClaveId='" + getPalabraClaveId() + "'" +
            ", idiomaCatId='" + getIdiomaCatId() + "'" +
            ", normaIdiomaId='" + getNormaIdiomaId() + "'" +
            ", normaIdiomaNormaId='" + getNormaIdiomaNormaId() + "'" +
            "}";
    }
}
