package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NormaSubtema.
 */
@Entity
@Table(name = "norma_subtema")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "normasubtema")
public class NormaSubtema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subtema_cat_id")
    private Long subtemaCatId;

    @Column(name = "idioma_cat_id")
    private Long idiomaCatId;

    @Column(name = "norma_idioma_id")
    private Long normaIdiomaId;

    @Column(name = "norma_idioma_idioma_cat_id")
    private Long normaIdiomaIdiomaCatId;

    @Column(name = "norma_idioma_norma_id")
    private Long normaIdiomaNormaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubtemaCatId() {
        return subtemaCatId;
    }

    public NormaSubtema subtemaCatId(Long subtemaCatId) {
        this.subtemaCatId = subtemaCatId;
        return this;
    }

    public void setSubtemaCatId(Long subtemaCatId) {
        this.subtemaCatId = subtemaCatId;
    }

    public Long getIdiomaCatId() {
        return idiomaCatId;
    }

    public NormaSubtema idiomaCatId(Long idiomaCatId) {
        this.idiomaCatId = idiomaCatId;
        return this;
    }

    public void setIdiomaCatId(Long idiomaCatId) {
        this.idiomaCatId = idiomaCatId;
    }

    public Long getNormaIdiomaId() {
        return normaIdiomaId;
    }

    public NormaSubtema normaIdiomaId(Long normaIdiomaId) {
        this.normaIdiomaId = normaIdiomaId;
        return this;
    }

    public void setNormaIdiomaId(Long normaIdiomaId) {
        this.normaIdiomaId = normaIdiomaId;
    }

    public Long getNormaIdiomaIdiomaCatId() {
        return normaIdiomaIdiomaCatId;
    }

    public NormaSubtema normaIdiomaIdiomaCatId(Long normaIdiomaIdiomaCatId) {
        this.normaIdiomaIdiomaCatId = normaIdiomaIdiomaCatId;
        return this;
    }

    public void setNormaIdiomaIdiomaCatId(Long normaIdiomaIdiomaCatId) {
        this.normaIdiomaIdiomaCatId = normaIdiomaIdiomaCatId;
    }

    public Long getNormaIdiomaNormaId() {
        return normaIdiomaNormaId;
    }

    public NormaSubtema normaIdiomaNormaId(Long normaIdiomaNormaId) {
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
        NormaSubtema normaSubtema = (NormaSubtema) o;
        if (normaSubtema.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), normaSubtema.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NormaSubtema{" +
            "id=" + getId() +
            ", subtemaCatId='" + getSubtemaCatId() + "'" +
            ", idiomaCatId='" + getIdiomaCatId() + "'" +
            ", normaIdiomaId='" + getNormaIdiomaId() + "'" +
            ", normaIdiomaIdiomaCatId='" + getNormaIdiomaIdiomaCatId() + "'" +
            ", normaIdiomaNormaId='" + getNormaIdiomaNormaId() + "'" +
            "}";
    }
}
