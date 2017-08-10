package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IdiomaCat.
 */
@Entity
@Table(name = "idioma_cat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "idiomacat")
public class IdiomaCat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idioma_1")
    private String idioma1;

    @Column(name = "idioma_2")
    private String idioma2;

    @Column(name = "idioma_3")
    private String idioma3;

    @Column(name = "idioma_4")
    private String idioma4;

    @Column(name = "idioma_5")
    private String idioma5;

    @Column(name = "activo")
    private Boolean activo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdioma1() {
        return idioma1;
    }

    public IdiomaCat idioma1(String idioma1) {
        this.idioma1 = idioma1;
        return this;
    }

    public void setIdioma1(String idioma1) {
        this.idioma1 = idioma1;
    }

    public String getIdioma2() {
        return idioma2;
    }

    public IdiomaCat idioma2(String idioma2) {
        this.idioma2 = idioma2;
        return this;
    }

    public void setIdioma2(String idioma2) {
        this.idioma2 = idioma2;
    }

    public String getIdioma3() {
        return idioma3;
    }

    public IdiomaCat idioma3(String idioma3) {
        this.idioma3 = idioma3;
        return this;
    }

    public void setIdioma3(String idioma3) {
        this.idioma3 = idioma3;
    }

    public String getIdioma4() {
        return idioma4;
    }

    public IdiomaCat idioma4(String idioma4) {
        this.idioma4 = idioma4;
        return this;
    }

    public void setIdioma4(String idioma4) {
        this.idioma4 = idioma4;
    }

    public String getIdioma5() {
        return idioma5;
    }

    public IdiomaCat idioma5(String idioma5) {
        this.idioma5 = idioma5;
        return this;
    }

    public void setIdioma5(String idioma5) {
        this.idioma5 = idioma5;
    }

    public Boolean isActivo() {
        return activo;
    }

    public IdiomaCat activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdiomaCat idiomaCat = (IdiomaCat) o;
        if (idiomaCat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), idiomaCat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IdiomaCat{" +
            "id=" + getId() +
            ", idioma1='" + getIdioma1() + "'" +
            ", idioma2='" + getIdioma2() + "'" +
            ", idioma3='" + getIdioma3() + "'" +
            ", idioma4='" + getIdioma4() + "'" +
            ", idioma5='" + getIdioma5() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
