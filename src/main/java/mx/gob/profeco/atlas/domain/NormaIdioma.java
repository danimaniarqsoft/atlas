package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A NormaIdioma.
 */
@Entity
@Table(name = "norma_idioma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "normaidioma")
public class NormaIdioma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Lob
    @Column(name = "texto")
    private String texto;

    @Column(name = "jhi_link")
    private String link;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @OneToOne
    @JoinColumn(unique = true)
    private IdiomaCat idiomaCat;

    @OneToOne
    @JoinColumn(unique = true)
    private Norma norma;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public NormaIdioma titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public NormaIdioma descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTexto() {
        return texto;
    }

    public NormaIdioma texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getLink() {
        return link;
    }

    public NormaIdioma link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public NormaIdioma fechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public IdiomaCat getIdiomaCat() {
        return idiomaCat;
    }

    public NormaIdioma idiomaCat(IdiomaCat idiomaCat) {
        this.idiomaCat = idiomaCat;
        return this;
    }

    public void setIdiomaCat(IdiomaCat idiomaCat) {
        this.idiomaCat = idiomaCat;
    }

    public Norma getNorma() {
        return norma;
    }

    public NormaIdioma norma(Norma norma) {
        this.norma = norma;
        return this;
    }

    public void setNorma(Norma norma) {
        this.norma = norma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NormaIdioma normaIdioma = (NormaIdioma) o;
        if (normaIdioma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), normaIdioma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NormaIdioma{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", texto='" + getTexto() + "'" +
            ", link='" + getLink() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
