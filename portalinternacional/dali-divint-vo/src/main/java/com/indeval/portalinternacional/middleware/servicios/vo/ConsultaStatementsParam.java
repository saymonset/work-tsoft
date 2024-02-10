package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class ConsultaStatementsParam implements Serializable {

	private static final long serialVersionUID = -1L;
	/** Campo del nombre */
	private String nombre;
	/** Campo del tipo de formato */
	private String formato;
	/** Campo del tipo valor de la emision */
	private String tv;
	/** Campo de la emisora de la emision */
	private String emisora;
	/** Campo de la serie de la emision */
	private String serie;
	/** Campo del isin de la emision */
	private String isin;
	/** Campo del Id de la institucion */
	private Long idInstitucion;
	/** Campo de la fecha de pago inicial */
	private Date fechaPagoInicio;
	/** Campo de la fecha de pago final */
	private Date fechaPagoFin;
	/** Campo de la fecha de registro inicial */
	private Date fechaRegistroInicio;
	/** Campo de la fecha de registro final */
	private Date fechaRegistroFin;
	/** Campo del archivo del registro */
	private String archivo;
	/** Campo del ADP del beneficiario */
	private String adp;
	/** Campo del Id del tipo de beneficiario */
	private Long idTipoBeneficiario;
	
	/*Se agregan campos %retencion y custodio*/
	private Integer porcentaje;
	private String custodio;

	/** Constructor por omision */
	public ConsultaStatementsParam() {
		super();
	}
	
	public ConsultaStatementsParam(	 String nombre, 
	 String formato,
	 String tv,
	 String emisora,
	 String serie,
	 String isin,
	 Long idInstitucion,
	 Date fechaPagoInicio,
	 Date fechaPagoFin,
	 Date fechaRegistroInicio,
	 Date fechaRegistroFin,
	 String archivo,
	 String adp,
	 Long idTipoBeneficiario,
	 Integer porcentaje,
	 String custodio
			) {
		this.nombre = nombre;
		this.formato = formato;
		this.tv = tv;
		this.emisora = emisora;
		this.serie = serie;
		this.isin = isin;
		this.idInstitucion = idInstitucion;
		this.fechaPagoInicio = fechaPagoInicio;
		this.fechaPagoFin = fechaPagoFin;
		this.fechaRegistroInicio = fechaRegistroInicio;
		this.fechaRegistroFin = fechaRegistroFin;
		this.archivo = archivo;
		this.adp = adp;
		this.idTipoBeneficiario = idTipoBeneficiario;
		this.porcentaje = porcentaje;
		this.custodio = custodio;
		
		
	}

	/**
	 * Campo del nombre
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Campo del nombre
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Campo del tipo de formato
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * Campo del tipo de formato
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * Campo del tipo valor de la emision
	 * @return the tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * Campo del tipo valor de la emision
	 * @param tv the tv to set
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Campo de la emisora de la emision
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Campo de la emisora de la emision
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Campo de la serie de la emision
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Campo de la serie de la emision
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Campo del isin de la emision
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Campo del isin de la emision
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Campo del Id de la institucion
	 * @return the idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Campo del Id de la institucion
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Campo de la fecha de pago inicial
	 * @return the fechaPagoInicio
	 */
	public Date getFechaPagoInicio() {
		return fechaPagoInicio;
	}

	/**
	 * Campo de la fecha de pago inicial
	 * @param fechaPagoInicio the fechaPagoInicio to set
	 */
	public void setFechaPagoInicio(Date fechaPagoInicio) {
		this.fechaPagoInicio = fechaPagoInicio;
	}

	/**
	 * Campo de la fecha de pago final
	 * @return the fechaPagoFin
	 */
	public Date getFechaPagoFin() {
		return fechaPagoFin;
	}

	/**
	 * Campo de la fecha de pago final
	 * @param fechaPagoFin the fechaPagoFin to set
	 */
	public void setFechaPagoFin(Date fechaPagoFin) {
		this.fechaPagoFin = fechaPagoFin;
	}

	/**
	 * Campo de la fecha de registro inicial
	 * @return the fechaRegistroInicio
	 */
	public Date getFechaRegistroInicio() {
		return fechaRegistroInicio;
	}

	/**
	 * Campo de la fecha de registro inicial
	 * @param fechaRegistroInicio the fechaRegistroInicio to set
	 */
	public void setFechaRegistroInicio(Date fechaRegistroInicio) {
		this.fechaRegistroInicio = fechaRegistroInicio;
	}

	/**
	 * Campo de la fecha de registro final
	 * @return the fechaRegistroFin
	 */
	public Date getFechaRegistroFin() {
		return fechaRegistroFin;
	}

	/**
	 * Campo de la fecha de registro final
	 * @param fechaRegistroFin the fechaRegistroFin to set
	 */
	public void setFechaRegistroFin(Date fechaRegistroFin) {
		this.fechaRegistroFin = fechaRegistroFin;
	}

	/**
	 * Campo del archivo del registro
	 * @return the archivo
	 */
	public String getArchivo() {
		return archivo;
	}

	/**
	 * Campo del archivo del registro
	 * @param archivo the archivo to set
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	/**
	 * Campo del ADP del beneficiario
	 * @return the adp
	 */
	public String getAdp() {
		return adp;
	}

	/**
	 * Campo del ADP del beneficiario
	 * @param adp the adp to set
	 */
	public void setAdp(String adp) {
		this.adp = adp;
	}

	/**
	 * Campo del Id del tipo de beneficiario
	 * @return the idTipoBeneficiario
	 */
	public Long getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	/**
	 * Campo del Id del tipo de beneficiario
	 * @param idTipoBeneficiario the idTipoBeneficiario to set
	 */
	public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}
	
	
	
	public Integer getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Integer porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	@Override
	public String toString() {
		ToStringBuilder toStringBuilder =  new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("nombre",nombre)
				.append("formato", formato)
				.append("tv",tv)
				.append("emisora", emisora)
				.append("serie", serie)
				.append("isin", isin)
				.append("idInstitucion", idInstitucion)
				.append("fechaPagoInicio", fechaPagoInicio)
				.append("fechaPagoFin", fechaPagoFin)
				.append("fechaRegistroInicio", fechaRegistroInicio)
				.append("fechaRegistroFin", fechaRegistroFin)
				.append("archivo", archivo)
				.append("adp", adp)
				.append("idTipoBeneficiario", idTipoBeneficiario)
				.append("porcentaje", porcentaje)
				.append("custodio", custodio);
		return toStringBuilder.toString();
	}
	
}
