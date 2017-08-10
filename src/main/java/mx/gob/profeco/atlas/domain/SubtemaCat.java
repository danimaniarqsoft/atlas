package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SubtemaCat.
 */
@Entity
@Table(name = "subtema_cat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "subtemacat")
public class SubtemaCat implements Serializable {

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
    private IdiomaCat idioma;

    @OneToOne
    @JoinColumn(unique = true)
    private TemaCat temaCat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public SubtemaCat nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isActivo() {
        return activo;
    }

    public SubtemaCat activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public IdiomaCat getIdioma() {
        return idioma;
    }

    public SubtemaCat idioma(IdiomaCat idiomaCat) {
        this.idioma = idiomaCat;
        return this;
    }

    public void setIdioma(IdiomaCat idiomaCat) {
        this.idioma = idiomaCat;
    }

    public TemaCat getTemaCat() {
        return temaCat;
    }

    public SubtemaCat temaCat(TemaCat temaCat) {
        this.temaCat = temaCat;
        return this;
    }

    public void setTemaCat(TemaCat temaCat) {
        this.temaCat = temaCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubtemaCat subtemaCat = (SubtemaCat) o;
        if (subtemaCat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subtemaCat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubtemaCat{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
