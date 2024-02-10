/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import com.indeval.portaldali.persistence.modelo.Emision;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.indeval.portaldali.persistence.modelo.Institucion;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Modelo para representar los datos de los statements para este 2010
 * 
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
@Entity
@Table(name = "T_STATEMENTS_DIVINT")
@SequenceGenerator(name = "foliador", sequenceName = "ID_STATEMENTS_DIVINT", allocationSize = 1, initialValue = 1)
public class StatementDivint implements Serializable {

    /** Constante de serializacion por default  */
    private static final long serialVersionUID = 1L;
    private Long idStatement;
    private Long idEmision;
    private Emision emision;
    private Long idInstitucion;
    private Institucion institucion;
    private Long idTipoBeneficiario;
    private TipoBeneficiario tipoBeneficiario;
    private Date fechaPago;
    private Date fechaCreacion;
    private Date fechaRegistro;
    private String adp;
    private String nombre;
    private String direccion;
    private String rfc;
    private String pais;
    private String taxPayerNumber;
    private String formato;
    private String statusOwner;
    private BigDecimal porcentajeRetencion;
    private Long numeroTitulos;
    private BigDecimal proporcion;
    private BigDecimal dividendo;
    private BigDecimal impuesto;
    private BigDecimal dividendoNeto;
    private String archivoOrigen;
    private String archivoZip;
	private Boolean cargoInstitucion;
	private String custodio;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_STATEMENTS_DIVINT", unique = true, nullable = false)
    public Long getIdStatement() {
        return idStatement;
    }

    @Column(name = "ID_EMISION", nullable = false)
    public Long getIdEmision() {
        return idEmision;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMISION", nullable = false, updatable = false, insertable = false)
    public Emision getEmision() {
        return emision;
    }

    @Column(name = "ID_INSTITUCION", nullable = false)
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSTITUCION", nullable = false, updatable = false, insertable = false)
    public Institucion getInstitucion() {
        return institucion;
    }

    @Column(name = "ID_TIPO_BENEFICIARIO", nullable = false)
    public Long getIdTipoBeneficiario() {
        return idTipoBeneficiario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_BENEFICIARIO", nullable = false, updatable = false, insertable = false)
    public TipoBeneficiario getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_PAGO", nullable = false)
    public Date getFechaPago() {
        return fechaPago;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION", nullable = false)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_REGISTRO", nullable = false)
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    @Column(name = "ADP", nullable = true, length = 10)
    public String getAdp() {
        return adp;
    }

    @Column(name = "NOMBRE", nullable = true, length = 1024)
    public String getNombre() {
        return nombre;
    }

    @Column(name = "DIRECCION", nullable = true, length = 1024)
    public String getDireccion() {
        return direccion;
    }

    @Column(name = "RFC", nullable = true, length = 20)
    public String getRfc() {
        return rfc;
    }

    @Column(name = "PAIS", nullable = true, length = 100)
    public String getPais() {
        return pais;
    }

    @Column(name = "TAX_PAYER_NUMBER", nullable = true, length = 15)
    public String getTaxPayerNumber() {
        return taxPayerNumber;
    }

    @Column(name = "FORMATO", nullable = false, length = 5)
    public String getFormato() {
        return formato;
    }

    @Column(name = "STATUS_OWNER", nullable = true, length = 50)
    public String getStatusOwner() {
        return statusOwner;
    }

    @Column(name = "PORCENTAJE_RETENCION", nullable = false, precision = 5, scale = 2)
    public BigDecimal getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    @Column(name = "NUMERO_TITULOS", nullable = false, precision = 12)
    public Long getNumeroTitulos() {
        return numeroTitulos;
    }

    @Column(name = "PROPORCION", nullable = false, precision = 20, scale = 8)
    public BigDecimal getProporcion() {
        return proporcion;
    }

    @Column(name = "DIVIDENDO", nullable = false, precision = 20, scale = 8)
    public BigDecimal getDividendo() {
        return dividendo;
    }

    @Column(name = "IMPUESTO", nullable = false, precision = 20, scale = 8)
    public BigDecimal getImpuesto() {
        return impuesto;
    }

    @Column(name = "DIVIDENDO_NETO", nullable = false, precision = 20, scale = 8)
    public BigDecimal getDividendoNeto() {
        return dividendoNeto;
    }

    @Column(name = "ARCHIVO_EXCEL", nullable = false, length = 200)
    public String getArchivoOrigen() {
        return archivoOrigen;
    }

    @Column(name = "ARCHIVO_ZIP", nullable = true, length = 200)
    public String getArchivoZip() {
        return archivoZip;
    }

	@Column(name = "CARGO_INSTITUCION", nullable = false)
	public Boolean getCargoInstitucion() {
		return cargoInstitucion;
	}

    public void setIdStatement(Long idStatement) {
        this.idStatement = idStatement;
    }

    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    public void setEmision(Emision emision) {
        this.emision = emision;
    }

    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
        this.idTipoBeneficiario = idTipoBeneficiario;
    }

    public void setTipoBeneficiario(TipoBeneficiario tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setAdp(String adp) {
        this.adp = adp;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setTaxPayerNumber(String taxPayerNumber) {
        this.taxPayerNumber = taxPayerNumber;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setStatusOwner(String statusOwner) {
        this.statusOwner = statusOwner;
    }

    public void setPorcentajeRetencion(BigDecimal porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public void setNumeroTitulos(Long numeroTitulos) {
        this.numeroTitulos = numeroTitulos;
    }

    public void setProporcion(BigDecimal proporcion) {
        this.proporcion = proporcion;
    }

    public void setDividendo(BigDecimal dividendo) {
        this.dividendo = dividendo;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public void setDividendoNeto(BigDecimal dividendoNeto) {
        this.dividendoNeto = dividendoNeto;
    }

    public void setArchivoOrigen(String archivoOrigen) {
        this.archivoOrigen = archivoOrigen;
    }

    public void setArchivoZip(String archivoZip) {
        this.archivoZip = archivoZip;
    }

	public void setCargoInstitucion(Boolean cargoInstitucion) {
		this.cargoInstitucion = cargoInstitucion;
	}
	
	
	@Column(name = "CUSTODIO", nullable = true, length = 250)
    public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23).append(idStatement).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof StatementDivint) {
            StatementDivint statement = (StatementDivint) obj;
            isEqual = new EqualsBuilder().append(idStatement, statement.idStatement).isEquals();
        }
        return isEqual;
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id Statement", idStatement)
				.append("Nombre", nombre)
				.append("Archivo Origen", archivoOrigen)
				.append("Tipo Formato", formato)
				.append("Tipo Beneficiario", idTipoBeneficiario)
				.append("Id Institucion", idInstitucion)
				.append("Fecha Registro", fechaRegistro);
        return toStringBuilder.toString();
    }
	
}
