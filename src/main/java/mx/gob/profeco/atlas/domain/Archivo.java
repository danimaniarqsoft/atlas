package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Archivo.
 */
@Entity
@Table(name = "archivo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "archivo")
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "path")
    private String path;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "jhi_user_id")
    private Long jhiUserId;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @Column(name = "norma_idioma_norma_id")
    private Long normaIdiomaNormaId;

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

    public String getNombre() {
        return nombre;
    }

    public Archivo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public Archivo path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Archivo fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public Archivo fechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Archivo activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getJhiUserId() {
        return jhiUserId;
    }

    public Archivo jhiUserId(Long jhiUserId) {
        this.jhiUserId = jhiUserId;
        return this;
    }

    public void setJhiUserId(Long jhiUserId) {
        this.jhiUserId = jhiUserId;
    }

    public byte[] getFile() {
        return file;
    }

    public Archivo file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Archivo fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Long getNormaIdiomaNormaId() {
        return normaIdiomaNormaId;
    }

    public Archivo normaIdiomaNormaId(Long normaIdiomaNormaId) {
        this.normaIdiomaNormaId = normaIdiomaNormaId;
        return this;
    }

    public void setNormaIdiomaNormaId(Long normaIdiomaNormaId) {
        this.normaIdiomaNormaId = normaIdiomaNormaId;
    }

    public IdiomaCat getIdiomaCat() {
        return idiomaCat;
    }

    public Archivo idiomaCat(IdiomaCat idiomaCat) {
        this.idiomaCat = idiomaCat;
        return this;
    }

    public void setIdiomaCat(IdiomaCat idiomaCat) {
        this.idiomaCat = idiomaCat;
    }

    public Norma getNorma() {
        return norma;
    }

    public Archivo norma(Norma norma) {
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
        Archivo archivo = (Archivo) o;
        if (archivo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), archivo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Archivo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", path='" + getPath() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", activo='" + isActivo() + "'" +
            ", jhiUserId='" + getJhiUserId() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + fileContentType + "'" +
            ", normaIdiomaNormaId='" + getNormaIdiomaNormaId() + "'" +
            "}";
    }
}
