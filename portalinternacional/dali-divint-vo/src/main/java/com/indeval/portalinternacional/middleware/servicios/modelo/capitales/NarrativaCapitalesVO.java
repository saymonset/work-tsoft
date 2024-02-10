package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;


public class NarrativaCapitalesVO implements Serializable,Comparable<NarrativaCapitalesVO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idNarrativa;
	
	private Long folioMensaje;
	
	private Long folioIndeval;
	
	private String origen;
	
	private String tipoMensaje;
	
	private String narrativa;
	
	private String tipoPagoCaev;
	
	private String tipoPagoCamv;
	
	private String fechaCorte;
	
	private String fuente;
	
	private String fechaMensaje;
	
	private String usuario;
	
	private String descripcionCaev;
	
	private String descripcionCamv;
	
	private Long orden;
	
	private Long secuencia;
	
	private enum ORDEN_ORIGEN{MENSAJE,ENVIADO,USUARIO}
	
	
	public NarrativaCapitalesVO(){}


	/**
	 * 
	 * @param idNarrativa
	 * @param folioMensaje
	 * @param folioIndeval
	 * @param origen
	 * @param tipoMensaje
	 * @param narrativa
	 * @param tipoPagoCaev
	 * @param tipoPagoCamv
	 * @param fechaCorte
	 * @param fuente
	 * @param fechaMensaje
	 * @param usuario
	 * @param descripcionCaev
	 * @param descripcionCamv
	 * @param orden
	 * @param secuencia
	 */
	public NarrativaCapitalesVO(Long idNarrativa, Long folioMensaje, Long folioIndeval, String origen,
			String tipoMensaje, String narrativa, String tipoPagoCaev, String tipoPagoCamv, String fechaCorte,
			String fuente, String fechaMensaje, String usuario, String descripcionCaev, String descripcionCamv,
			Long orden, Long secuencia) {
		super();
		this.idNarrativa = idNarrativa;
		this.folioMensaje = folioMensaje;
		this.folioIndeval = folioIndeval;
		this.origen = origen;
		this.tipoMensaje = tipoMensaje;
		this.narrativa = narrativa;
		this.tipoPagoCaev = tipoPagoCaev;
		this.tipoPagoCamv = tipoPagoCamv;
		this.fechaCorte = fechaCorte;
		this.fuente = fuente;
		this.fechaMensaje = fechaMensaje;
		this.usuario = usuario;
		this.descripcionCaev = descripcionCaev;
		this.descripcionCamv = descripcionCamv;
		this.orden = orden;
		this.secuencia = secuencia;
	}



	/**
	 * @return the idNarrativa
	 */
	public Long getIdNarrativa() {
		return idNarrativa;
	}

	/**
	 * @param idNarrativa the idNarrativa to set
	 */
	public void setIdNarrativa(Long idNarrativa) {
		this.idNarrativa = idNarrativa;
	}

	/**
	 * @return the folioMensaje
	 */
	public Long getFolioMensaje() {
		return folioMensaje;
	}

	/**
	 * @param folioMensaje the folioMensaje to set
	 */
	public void setFolioMensaje(Long folioMensaje) {
		this.folioMensaje = folioMensaje;
	}

	/**
	 * @return the folioIndeval
	 */
	public Long getFolioIndeval() {
		return folioIndeval;
	}

	/**
	 * @param folioIndeval the folioIndeval to set
	 */
	public void setFolioIndeval(Long folioIndeval) {
		this.folioIndeval = folioIndeval;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the tipoMensaje
	 */
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @return the narrativa
	 */
	public String getNarrativa() {
		return narrativa;
	}

	/**
	 * @param narrativa the narrativa to set
	 */
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}

	/**
	 * @return the tipoPagoCaev
	 */
	public String getTipoPagoCaev() {
		return tipoPagoCaev;
	}

	/**
	 * @param tipoPagoCaev the tipoPagoCaev to set
	 */
	public void setTipoPagoCaev(String tipoPagoCaev) {
		this.tipoPagoCaev = tipoPagoCaev;
	}

	/**
	 * @return the tipoPagoCamv
	 */
	public String getTipoPagoCamv() {
		return tipoPagoCamv;
	}

	/**
	 * @param tipoPagoCamv the tipoPagoCamv to set
	 */
	public void setTipoPagoCamv(String tipoPagoCamv) {
		this.tipoPagoCamv = tipoPagoCamv;
	}

	/**
	 * @return the fechaCorte
	 */
	public String getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	/**
	 * @return the fuente
	 */
	public String getFuente() {
		return fuente;
	}
	/**
	 * @param fuente the fuente to set
	 */
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	/**
	 * @return the fechaMensaje
	 */
	public String getFechaMensaje() {
		return fechaMensaje;
	}
	/**
	 * @param fechaMensaje the fechaMensaje to set
	 */
	public void setFechaMensaje(String fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the descripcionCaev
	 */
	public String getDescripcionCaev() {
		return descripcionCaev;
	}
	/**
	 * @param descripcionCaev the descripcionCaev to set
	 */
	public void setDescripcionCaev(String descripcionCaev) {
		this.descripcionCaev = descripcionCaev;
	}
	/**
	 * @return the descripcionCamv
	 */
	public String getDescripcionCamv() {
		return descripcionCamv;
	}
	/**
	 * @param descripcionCamv the descripcionCamv to set
	 */
	public void setDescripcionCamv(String descripcionCamv) {
		this.descripcionCamv = descripcionCamv;
	}
	
	/**
	 * @return the orden
	 */
	public Long getOrden() {
		return orden;
	}
	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Long orden) {
		this.orden = orden;
	}	
	/**
	 * @return the secuencia
	 */
	public Long getSecuencia() {
		return secuencia;
	}
	/**
	 * @param secuencia the secuencia to set
	 */
	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}
	/**
	 * M&eacute;todo para la comparación del objeto
	 */	
	public int compareTo(NarrativaCapitalesVO obj) {
		Integer regreso = null;
		NarrativaCapitalesVO narrativaCapitalesVO = obj;
		ORDEN_ORIGEN orden = ORDEN_ORIGEN.values()[narrativaCapitalesVO.getOrden().intValue()];		 
		switch(orden){ 
		case MENSAJE:
			if(this.orden == orden.ordinal()){
				regreso = this.idNarrativa.compareTo(narrativaCapitalesVO.getIdNarrativa());
			}
			break;
		case ENVIADO:
			if(this.orden == orden.ordinal()){
				regreso = this.secuencia.compareTo(narrativaCapitalesVO.getSecuencia());
			}
			break;
		case USUARIO:
			if(this.orden == orden.ordinal()){
				regreso = this.idNarrativa.compareTo(narrativaCapitalesVO.getIdNarrativa());
			}
			break;	
		default:
			break;
		}
		if(regreso == null){
			regreso = this.orden.compareTo(narrativaCapitalesVO.getOrden());
		}
		return regreso;
	}
}
