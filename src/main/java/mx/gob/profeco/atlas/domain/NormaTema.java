package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NormaTema.
 */
@Entity
@Table(name = "norma_tema")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "normatema")
public class NormaTema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tema_cat_id")
    private Long temaCatId;

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

    public Long getTemaCatId() {
        return temaCatId;
    }

    public NormaTema temaCatId(Long temaCatId) {
        this.temaCatId = temaCatId;
        return this;
    }

    public void setTemaCatId(Long temaCatId) {
        this.temaCatId = temaCatId;
    }

    public Long getIdiomaCatId() {
        return idiomaCatId;
    }

    public NormaTema idiomaCatId(Long idiomaCatId) {
        this.idiomaCatId = idiomaCatId;
        return this;
    }

    public void setIdiomaCatId(Long idiomaCatId) {
        this.idiomaCatId = idiomaCatId;
    }

    public Long getNormaIdiomaId() {
        return normaIdiomaId;
    }

    public NormaTema normaIdiomaId(Long normaIdiomaId) {
        this.normaIdiomaId = normaIdiomaId;
        return this;
    }

    public void setNormaIdiomaId(Long normaIdiomaId) {
        this.normaIdiomaId = normaIdiomaId;
    }

    public Long getNormaIdiomaNormaId() {
        return normaIdiomaNormaId;
    }

    public NormaTema normaIdiomaNormaId(Long normaIdiomaNormaId) {
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
        NormaTema normaTema = (NormaTema) o;
        if (normaTema.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), normaTema.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NormaTema{" +
            "id=" + getId() +
            ", temaCatId='" + getTemaCatId() + "'" +
            ", idiomaCatId='" + getIdiomaCatId() + "'" +
            ", normaIdiomaId='" + getNormaIdiomaId() + "'" +
            ", normaIdiomaNormaId='" + getNormaIdiomaNormaId() + "'" +
            "}";
    }
}
