package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoNormaCat.
 */
@Entity
@Table(name = "tipo_norma_cat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tiponormacat")
public class TipoNormaCat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToOne
    @JoinColumn(unique = true)
    private IdiomaCat idiomaCat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoNormaCat nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isActivo() {
        return activo;
    }

    public TipoNormaCat activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public IdiomaCat getIdiomaCat() {
        return idiomaCat;
    }

    public TipoNormaCat idiomaCat(IdiomaCat idiomaCat) {
        this.idiomaCat = idiomaCat;
        return this;
    }

    public void setIdiomaCat(IdiomaCat idiomaCat) {
        this.idiomaCat = idiomaCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoNormaCat tipoNormaCat = (TipoNormaCat) o;
        if (tipoNormaCat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoNormaCat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoNormaCat{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
