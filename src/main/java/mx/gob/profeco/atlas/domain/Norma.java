package mx.gob.profeco.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Norma.
 */
@Entity
@Table(name = "norma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "norma")
public class Norma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_firma")
    private LocalDate fechaFirma;

    @Column(name = "fecha_ratifica")
    private LocalDate fechaRatifica;

    @Column(name = "fecha_ini_vigor")
    private LocalDate fechaIniVigor;

    @Column(name = "fecha_fin_vigor")
    private LocalDate fechaFinVigor;

    @Lob
    @Column(name = "firmantes")
    private String firmantes;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Column(name = "jhi_user_id")
    private Long jhiUserId;

    @Column(name = "activo")
    private Boolean activo;

    @OneToOne
    @JoinColumn(unique = true)
    private EstatusCat estatusCat;

    @OneToOne
    @JoinColumn(unique = true)
    private PaisCat paisCat;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoNormaCat tipoNormaCat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaFirma() {
        return fechaFirma;
    }

    public Norma fechaFirma(LocalDate fechaFirma) {
        this.fechaFirma = fechaFirma;
        return this;
    }

    public void setFechaFirma(LocalDate fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public LocalDate getFechaRatifica() {
        return fechaRatifica;
    }

    public Norma fechaRatifica(LocalDate fechaRatifica) {
        this.fechaRatifica = fechaRatifica;
        return this;
    }

    public void setFechaRatifica(LocalDate fechaRatifica) {
        this.fechaRatifica = fechaRatifica;
    }

    public LocalDate getFechaIniVigor() {
        return fechaIniVigor;
    }

    public Norma fechaIniVigor(LocalDate fechaIniVigor) {
        this.fechaIniVigor = fechaIniVigor;
        return this;
    }

    public void setFechaIniVigor(LocalDate fechaIniVigor) {
        this.fechaIniVigor = fechaIniVigor;
    }

    public LocalDate getFechaFinVigor() {
        return fechaFinVigor;
    }

    public Norma fechaFinVigor(LocalDate fechaFinVigor) {
        this.fechaFinVigor = fechaFinVigor;
        return this;
    }

    public void setFechaFinVigor(LocalDate fechaFinVigor) {
        this.fechaFinVigor = fechaFinVigor;
    }

    public String getFirmantes() {
        return firmantes;
    }

    public Norma firmantes(String firmantes) {
        this.firmantes = firmantes;
        return this;
    }

    public void setFirmantes(String firmantes) {
        this.firmantes = firmantes;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Norma fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public Norma fechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Long getJhiUserId() {
        return jhiUserId;
    }

    public Norma jhiUserId(Long jhiUserId) {
        this.jhiUserId = jhiUserId;
        return this;
    }

    public void setJhiUserId(Long jhiUserId) {
        this.jhiUserId = jhiUserId;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Norma activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public EstatusCat getEstatusCat() {
        return estatusCat;
    }

    public Norma estatusCat(EstatusCat estatusCat) {
        this.estatusCat = estatusCat;
        return this;
    }

    public void setEstatusCat(EstatusCat estatusCat) {
        this.estatusCat = estatusCat;
    }

    public PaisCat getPaisCat() {
        return paisCat;
    }

    public Norma paisCat(PaisCat paisCat) {
        this.paisCat = paisCat;
        return this;
    }

    public void setPaisCat(PaisCat paisCat) {
        this.paisCat = paisCat;
    }

    public TipoNormaCat getTipoNormaCat() {
        return tipoNormaCat;
    }

    public Norma tipoNormaCat(TipoNormaCat tipoNormaCat) {
        this.tipoNormaCat = tipoNormaCat;
        return this;
    }

    public void setTipoNormaCat(TipoNormaCat tipoNormaCat) {
        this.tipoNormaCat = tipoNormaCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Norma norma = (Norma) o;
        if (norma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), norma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Norma{" +
            "id=" + getId() +
            ", fechaFirma='" + getFechaFirma() + "'" +
            ", fechaRatifica='" + getFechaRatifica() + "'" +
            ", fechaIniVigor='" + getFechaIniVigor() + "'" +
            ", fechaFinVigor='" + getFechaFinVigor() + "'" +
            ", firmantes='" + getFirmantes() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", jhiUserId='" + getJhiUserId() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
