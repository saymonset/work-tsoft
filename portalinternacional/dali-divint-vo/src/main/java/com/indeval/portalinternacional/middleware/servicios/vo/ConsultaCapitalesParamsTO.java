/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Aniela Loaiza
 * 
 */


public class ConsultaCapitalesParamsTO {
	private static final String INDEX_1 = "-1";
	
    private String referencia;
    private String tipoMensaje = INDEX_1;
    private String  tipoPagoCAEV = INDEX_1;
    private String  tipoPagoCAMV = INDEX_1;
    private String tipoValor;
    private String emisora;
    private String serie;
    private String isin;
    private Date fechaPagoInicio;
    private Date fechaPagoFin;
    private Date fechaCorteInicio;
    private Date fechaCorteFin;
    private Date fechaXdateInicio;
    private Date fechaXdateFin;
    private Date dueBillOfDateInicio;
    private Date dueBillOfDateFin;
    private String divisa = INDEX_1 ;
    private String custodio  = INDEX_1 ;
    private String estado = INDEX_1 ;
    private String refCustodio;
    private String estadoMensajeria;
    private String boveda = INDEX_1 ;
    private Date fechaPrimerEnvioInicio;
    private Date fechaPrimerEnvioFin;
    private Date fechaRecepcionInicio;
    private Date fechaRecepcionFin;
    private Date fechaEffdInicio;
    private Date fechaEffdFin;
 
    private String fechasAdicionales  = INDEX_1 ;
    private String fee  = INDEX_1 ;
    private String nett  = INDEX_1 ;
    
    private Integer custodioInt;
    private Long estadoInt;
    private Long tipoPagoCAEVInt;
    private Long tipoPagoCAMVInt;
    private Long bovedaInt;
    private Long estadoEmisionInt;
    private Long folioIndeval;
    private String corp;
    private String estadoEmision;
    private List<String> tipoValorPermitidos;
    private Long folioMensaje;
    private String subTipoDerecho;
    
    private Date fechaEnvioConsInicio;
    private Date fechaEnvioConsFin;
    private boolean isXLS;
    private String folioIndevalCadena;
    private String folioMensajeCadena;
    private String narrativa;
    private boolean conNarrativa;
    private List<Long> idsCaevs;
    

	private static SimpleDateFormat FORMATO_FECHA_PANTALLA = new SimpleDateFormat("dd/MM/yyyy"); 
	private static SimpleDateFormat FORMATO_FECHA_HH_MI_PANTALLA = new SimpleDateFormat("dd/MM/yyyy HH:mm");
   
	/**
     * @return the referencia
     */
    public String getReferencia() {
        return this.referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(final String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the tipoMensaje
     */
    public String getTipoMensaje() {
        return this.tipoMensaje;
    }

    /**
     * @param tipoMensaje the tipoMensaje to set
     */
    public void setTipoMensaje(final String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }



    /**
     * @return the tipoValor
     */
    public String getTipoValor() {
        return this.tipoValor != null ? this.tipoValor.toUpperCase():null;
    }

    /**
     * @param tipoValor the tipoValor to set
     */
    public void setTipoValor(final String tipoValor) {
        this.tipoValor = tipoValor;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
    	return this.emisora != null ? this.emisora.toUpperCase():null;
    }

    /**
     * @param emisora the emisora to set
     */
    public void setEmisora(final String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
    	return this.serie != null ? this.serie.toUpperCase():null;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(final String serie) {
        this.serie = serie;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
    	return this.isin != null ? this.isin.toUpperCase():null;
    }

    /**
     * @param isin the isin to set
     */
    public void setIsin(final String isin) {
        this.isin = isin;
    }

    /**
     * @return the fechaPagoInicio
     */
    public Date getFechaPagoInicio() {
        return this.fechaPagoInicio;
    }

    /**
     * @param fechaPagoInicio the fechaPagoInicio to set
     */
    public void setFechaPagoInicio(final Date fechaPagoInicio) {
        this.fechaPagoInicio = fechaPagoInicio;
    }

    /**
     * @return the fechaPagoFin
     */
    public Date getFechaPagoFin() {
        return this.fechaPagoFin;
    }

    /**
     * @param fechaPagoFin the fechaPagoFin to set
     */
    public void setFechaPagoFin(final Date fechaPagoFin) {
        this.fechaPagoFin = fechaPagoFin;
    }

    /**
     * @return the fechaCorteInicio
     */
    public Date getFechaCorteInicio() {
        return this.fechaCorteInicio;
    }

    /**
     * @param fechaCorteInicio the fechaCorteInicio to set
     */
    public void setFechaCorteInicio(final Date fechaCorteInicio) {
        this.fechaCorteInicio = fechaCorteInicio;
    }

    /**
     * @return the fechaCorteFin
     */
    public Date getFechaCorteFin() {
        return this.fechaCorteFin;
    }

    /**
     * @param fechaCorteFin the fechaCorteFin to set
     */
    public void setFechaCorteFin(final Date fechaCorteFin) {
        this.fechaCorteFin = fechaCorteFin;
    }

    /**
     * @return the fechaXdateInicio
     */
    public Date getFechaXdateInicio() {
        return this.fechaXdateInicio;
    }

    /**
     * @param fechaXdateInicio the fechaXdateInicio to set
     */
    public void setFechaXdateInicio(final Date fechaXdateInicio) {
        this.fechaXdateInicio = fechaXdateInicio;
    }

    /**
     * @return the fechaXdateFin
     */
    public Date getFechaXdateFin() {
        return this.fechaXdateFin;
    }

    /**
     * @param fechaXdateFin the fechaXdateFin to set
     */
    public void setFechaXdateFin(final Date fechaXdateFin) {
        this.fechaXdateFin = fechaXdateFin;
    }

  

  

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return this.divisa;
    }

    /**
     * @param divisa the divisa to set
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

	public String getTipoPagoCAEV() {
		return tipoPagoCAEV;
	}

	public void setTipoPagoCAEV(String tipoPagoCAEV) {
		this.tipoPagoCAEV = tipoPagoCAEV;
	}

	public String getTipoPagoCAMV() {
		return tipoPagoCAMV;
	}

	public void setTipoPagoCAMV(String tipoPagoCAMV) {
		this.tipoPagoCAMV = tipoPagoCAMV;
	}

	public String getCustodio() {
		return custodio;
	}

	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRefCustodio() {
		return this.refCustodio != null ? this.refCustodio.toUpperCase():null;
	}

	public void setRefCustodio(String refCustodio) {
		this.refCustodio = refCustodio;
	}

	public String getEstadoMensajeria() {
		return estadoMensajeria;
	}

	public void setEstadoMensajeria(String estadoMensajeria) {
		this.estadoMensajeria = estadoMensajeria;
	}

	public Integer getCustodioInt() {
		return custodioInt;
	}

	public void setCustodioInt(Integer custodioInt) {
		this.custodioInt = custodioInt;
	}

	public Long getEstadoInt() {
		return estadoInt;
	}

	public void setEstadoInt(Long estadoInt) {
		this.estadoInt = estadoInt;
	}

	public Long getTipoPagoCAEVInt() {
		return tipoPagoCAEVInt;
	}

	public void setTipoPagoCAEVInt(Long tipoPagoCAEVInt) {
		this.tipoPagoCAEVInt = tipoPagoCAEVInt;
	}

	public Long getTipoPagoCAMVInt() {
		return tipoPagoCAMVInt;
	}

	public void setTipoPagoCAMVInt(Long tipoPagoCAMVInt) {
		this.tipoPagoCAMVInt = tipoPagoCAMVInt;
	}

	public String getBoveda() {
		return boveda;
	}

	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	public Long getBovedaInt() {
		return bovedaInt;
	}

	public void setBovedaInt(Long bovedaInt) {
		this.bovedaInt = bovedaInt;
	}

	public Date getFechaPrimerEnvioInicio() {
		return fechaPrimerEnvioInicio;
	}

	public void setFechaPrimerEnvioInicio(Date fechaPrimerEnvioInicio) {
		this.fechaPrimerEnvioInicio = fechaPrimerEnvioInicio;
	}

	public Date getFechaPrimerEnvioFin() {
		return fechaPrimerEnvioFin;
	}

	public void setFechaPrimerEnvioFin(Date fechaPrimerEnvioFin) {
		this.fechaPrimerEnvioFin = fechaPrimerEnvioFin;
	}

	public Date getFechaRecepcionInicio() {
		return fechaRecepcionInicio;
	}

	public void setFechaRecepcionInicio(Date fechaRecepcionInicio) {
		this.fechaRecepcionInicio = fechaRecepcionInicio;
	}

	public Date getFechaRecepcionFin() {
		return fechaRecepcionFin;
	}

	public void setFechaRecepcionFin(Date fechaRecepcionFin) {
		this.fechaRecepcionFin = fechaRecepcionFin;
	}

	public Date getFechaEffdInicio() {
		return fechaEffdInicio;
	}

	public void setFechaEffdInicio(Date fechaEffdInicio) {
		this.fechaEffdInicio = fechaEffdInicio;
	}

	public Date getFechaEffdFin() {
		return fechaEffdFin;
	}

	public void setFechaEffdFin(Date fechaEffdFin) {
		this.fechaEffdFin = fechaEffdFin;
	}

	

	public String getFechasAdicionales() {
		return fechasAdicionales;
	}

	public void setFechasAdicionales(String fechasAdicionales) {
		this.fechasAdicionales = fechasAdicionales;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	
	
	public String getNett() {
		return nett;
	}

	public void setNett(String nett) {
		this.nett = nett;
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
	 * @return the corp
	 */
	public String getCorp() {
		return corp;
	}

	/**
	 * @param corp the corp to set
	 */
	public void setCorp(String corp) {
		this.corp = corp;
	}
	
	public String getFechaPagoInicioFormato(){
		return this.fechaPagoInicio != null ? FORMATO_FECHA_PANTALLA.format(this.fechaPagoInicio):"";					
	}
	
	public String getFechaPagoFinFormato(){
		return this.fechaPagoFin != null ? FORMATO_FECHA_PANTALLA.format(this.fechaPagoFin):"";					
	}
	
	public String getFechaCorteInicioFormato(){
		return this.fechaCorteInicio != null ? FORMATO_FECHA_PANTALLA.format(this.fechaCorteInicio):"";					
	}
	
	public String getFechaCorteFinFormato(){
		return this.fechaCorteFin != null ? FORMATO_FECHA_PANTALLA.format(this.fechaCorteFin):"";					
	}
	
	public String getFechaXdateInicioFormato(){
		return this.fechaXdateInicio != null ? FORMATO_FECHA_PANTALLA.format(this.fechaXdateInicio):"";					
	}
	
	public String getFechaXdateFinFormato(){
		return this.fechaXdateFin != null ? FORMATO_FECHA_PANTALLA.format(this.fechaXdateFin):"";					
	}
	
	public String getFechaPrimerEnvioInicioFormato(){
		return this.fechaPrimerEnvioInicio != null ? FORMATO_FECHA_PANTALLA.format(this.fechaPrimerEnvioInicio):"";					
	}
	
	public String getFechaPrimerEnvioFinFormato(){
		return this.fechaPrimerEnvioFin != null ? FORMATO_FECHA_PANTALLA.format(this.fechaPrimerEnvioFin):"";					
	}
	
	public String getFechaRecepcionInicioFormato(){
		return this.fechaRecepcionInicio != null ? FORMATO_FECHA_HH_MI_PANTALLA.format(this.fechaRecepcionInicio):"";					
	}
	
	public String getFechaRecepcionFinFormato(){
		return this.fechaRecepcionFin != null ? FORMATO_FECHA_HH_MI_PANTALLA.format(this.fechaRecepcionFin):"";					
	}
		
	public String getFechaEffdInicioFormato(){
		return this.fechaEffdInicio != null ? FORMATO_FECHA_PANTALLA.format(this.fechaEffdInicio):"";					
	}
	
	public String getFechaEffdFinFormato(){
		return this.fechaEffdFin != null ? FORMATO_FECHA_PANTALLA.format(this.fechaEffdFin):"";					
	}
	
	public String getDueBillOfDateInicioFormato(){
		return this.dueBillOfDateInicio != null ? FORMATO_FECHA_PANTALLA.format(this.dueBillOfDateInicio):"";					
	}
	
	public String getDueBillOfDateFinFormato(){
		return this.dueBillOfDateFin != null ? FORMATO_FECHA_PANTALLA.format(this.dueBillOfDateFin):"";					
	}

	/**
	 * @return the dueBillOfDateInicio
	 */
	public Date getDueBillOfDateInicio() {
		return dueBillOfDateInicio;
	}

	/**
	 * @param dueBillOfDateInicio the dueBillOfDateInicio to set
	 */
	public void setDueBillOfDateInicio(Date dueBillOfDateInicio) {
		this.dueBillOfDateInicio = dueBillOfDateInicio;
	}

	/**
	 * @return the dueBillOfDateFin
	 */
	public Date getDueBillOfDateFin() {
		return dueBillOfDateFin;
	}

	/**
	 * @param dueBillOfDateFin the dueBillOfDateFin to set
	 */
	public void setDueBillOfDateFin(Date dueBillOfDateFin) {
		this.dueBillOfDateFin = dueBillOfDateFin;
	}

	/**
	 * @return the estadoEmision
	 */
	public String getEstadoEmision() {
		return estadoEmision;
	}

	/**
	 * @param estadoEmision the estadoEmision to set
	 */
	public void setEstadoEmision(String estadoEmision) {
		this.estadoEmision = estadoEmision;
	}

	/**
	 * @return the tipoValorPermitidos
	 */
	public List<String> getTipoValorPermitidos() {
		return tipoValorPermitidos;
	}

	/**
	 * @param tipoValorPermitidos the tipoValorPermitidos to set
	 */
	public void setTipoValorPermitidos(List<String> tipoValorPermitidos) {
		this.tipoValorPermitidos = tipoValorPermitidos;
	}

	/**
	 * @return the estadoEmisionInt
	 */
	public Long getEstadoEmisionInt() {
		return estadoEmisionInt;
	}

	/**
	 * @param estadoEmisionInt the estadoEmisionInt to set
	 */
	public void setEstadoEmisionInt(Long estadoEmisionInt) {
		this.estadoEmisionInt = estadoEmisionInt;
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
	 * @return the subTipoDerecho
	 */
	public String getSubTipoDerecho() {
		return subTipoDerecho;
	}

	/**
	 * @param subTipoDerecho the subTipoDerecho to set
	 */
	public void setSubTipoDerecho(String subTipoDerecho) {
		this.subTipoDerecho = subTipoDerecho;
	}

	/**
	 * @return the fechaEnvioConsInicio
	 */
	public Date getFechaEnvioConsInicio() {
		return fechaEnvioConsInicio;
	}

	/**
	 * @param fechaEnvioConsInicio the fechaEnvioConsInicio to set
	 */
	public void setFechaEnvioConsInicio(Date fechaEnvioConsInicio) {
		this.fechaEnvioConsInicio = fechaEnvioConsInicio;
	}

	/**
	 * @return the fechaEnvioConsFin
	 */
	public Date getFechaEnvioConsFin() {
		return fechaEnvioConsFin;
	}

	/**
	 * @param fechaEnvioConsFin the fechaEnvioConsFin to set
	 */
	public void setFechaEnvioConsFin(Date fechaEnvioConsFin) {
		this.fechaEnvioConsFin = fechaEnvioConsFin;
	}
	
	/**
	 * @return the isXLS
	 */
	public boolean isXLS() {
		return isXLS;
	}

	/**
	 * @param isXLS the isXLS to set
	 */
	public void setXLS(boolean isXLS) {
		this.isXLS = isXLS;
	}

	/**
	 * @return the folioIndevalCadena
	 */
	public String getFolioIndevalCadena() {
		return folioIndevalCadena;
	}

	/**
	 * @param folioIndevalCadena the folioIndevalCadena to set
	 */
	public void setFolioIndevalCadena(String folioIndevalCadena) {
		this.folioIndevalCadena = folioIndevalCadena;
	}

	/**
	 * @return the folioMensajeCadena
	 */
	public String getFolioMensajeCadena() {
		return folioMensajeCadena;
	}

	/**
	 * @param folioMensajeCadena the folioMensajeCadena to set
	 */
	public void setFolioMensajeCadena(String folioMensajeCadena) {
		this.folioMensajeCadena = folioMensajeCadena;
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
	 * @return the conNarrativa
	 */
	public boolean isConNarrativa() {
		return conNarrativa;
	}

	/**
	 * @param conNarrativa the conNarrativa to set
	 */
	public void setConNarrativa(boolean conNarrativa) {
		this.conNarrativa = conNarrativa;
	}

	/**
	 * @return the idsCaevs
	 */
	public List<Long> getIdsCaevs() {
		return idsCaevs;
	}

	/**
	 * @param idsCaevs the idsCaevs to set
	 */
	public void setIdsCaevs(List<Long> idsCaevs) {
		this.idsCaevs = idsCaevs;
	}
	
	    
}
