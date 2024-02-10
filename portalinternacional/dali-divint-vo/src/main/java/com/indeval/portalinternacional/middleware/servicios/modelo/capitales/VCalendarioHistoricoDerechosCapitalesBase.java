package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class VCalendarioHistoricoDerechosCapitalesBase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer idCustodio;
	private String nombreCortoCus;	
	private String descripcionCus;	
	private Long idCatBicCus;
	private String codigoBancoCus;
	private Integer participanteCus;
	private Long idTipoPagoCaev;
	private String clavePagoCaev;
	private String descripcionCaev;
	private Integer caevCaev;
	private Integer participanteCaev;
	private Long idTipoPagoCamv;
	private String clavePagoCamv;
	private String descripcionCamv;
	private Integer caevCamv;
	private Integer participanteCamv;
    private Long idBitacoraMensaje;
    private Long idCalendario;
    private Long idBoveda;
    private Long idCuentaBoveda;
    private String descripcionBov;
    private String nombreCortoBov;
    private Long idTipoBoveda;
	private Long idEstadoMensajeIntSic;
	private String nombreEstado;
	private Date fechaCreacionEstado;
    private Long consecutivo;
    private Long numeroConfirmados;
    private Long numeroEnvios;
    private String tipoMensaje;
    private String isin;
    private String referencia;
    private String seme;
    private Date fechaCorte;
    private Date fechaPago;
    private Date fechaXDate;
    private Date fechaVencimiento;
    private BigDecimal importeGRSS;
    private BigDecimal importePTSA;
    private BigDecimal importeNETT;
    private BigDecimal importeWITL;
    private BigDecimal importeFEE;
    
    
    private String cfi;
    private String divisa;
    private String tipoValor;
    private String emisora;
    private String serie;
    private String cupon;
    private String coaf;
    protected String fechasAdicionales;
    private String links;
    private Integer is566;
    private BigDecimal valorNominal;
    private BigDecimal posicionIndeval;
    private Date fechaHoraRecepcion;
    private Date fechaPrimerEnvio;
    private Date fechaPrePago;
    private String opcionesMensaje;
    private String functionM;
    private String estadoProceso;
    private Date fechaEfectiva;
    private Long prioridad;
	protected String listada;
	private String destinatario;
	private Long idInstrucciones;
	protected Long liga;
	private String instrOpcionesMensaje;
	private String tagsEliminacion;
	private Long idTipoDerechoCaev;
	private String subtipoDerecho;
	protected String valor1;
    protected String valor2;
    private String propiedadesCalendario;
    protected Map<String,String> propiedadesMap;
    protected List<FechasAdicionalesDTO> listaFechasDTO;
    protected List<String> ratioAdicionalLst;
    protected String ratioAdicional;
    
   
	@Column(name = "CONSECUTIVO")
	public Long getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(Long consecutivo) {
		this.consecutivo = consecutivo;
	}
	
	@Column(name = "NUMERO_CONFIRMADOS")
	public Long getNumeroConfirmados() {
		return numeroConfirmados;
	}
	public void setNumeroConfirmados(Long numeroConfirmados) {
		this.numeroConfirmados = numeroConfirmados;
	}
	
	@Column(name = "NUMERO_ENVIOS")
	public Long getNumeroEnvios() {
		return numeroEnvios;
	}
	public void setNumeroEnvios(Long numeroEnvios) {
		this.numeroEnvios = numeroEnvios;
	}
	
	@Id	   
    @Column(name = "ID_HIST_DERECHO_INT")
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name = "REFERENCIA")
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    @Column(name = "MT_567_910")
    public String getTipoMensaje() {
        return tipoMensaje;
    }
    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
    
    @Column(name = "TIPO_VALOR")
    public String getTipoValor() {
        return tipoValor;
    }
    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }
    
    @Column(name = "EMISORA")
    public String getEmisora() {
        return emisora;
    }
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }
    
    @Column(name = "SERIE")
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    
    @Column(name = "CUPON")
    public String getCupon() {
        return cupon;
    }
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }
    
    @Column(name = "ISIN")
    public String getIsin() {
        return isin;
    }
    public void setIsin(String isin) {
        this.isin = isin;
    }
    
    @Column(name = "FECHA_CORTE")
    public Date getFechaCorte() {
        return fechaCorte;
    }
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }
    
    @Column(name = "FECHA_PAGO")
    public Date getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    @Column(name = "MONEDA")
    public String getDivisa() {
        return divisa;
    }
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }
    
    @Column(name = "IMPORTE_PTSA")
    public BigDecimal getImportePTSA() {
        return importePTSA;
    }
    public void setImportePTSA(BigDecimal importePTSA) {
        this.importePTSA = importePTSA;
    }
    
    @Column(name = "IMPORTE_GRSS")
    public BigDecimal getImporteGRSS() {
        return importeGRSS;
    }
    public void setImporteGRSS(BigDecimal importeGRSS) {
        this.importeGRSS = importeGRSS;
    }
    
    @Column(name = "IS_566")
	public Integer getIs566() {
		return is566;
	}
	public void setIs566(Integer is566) {
		this.is566 = is566;
	}
	
	@Column(name = "FECHA_XDATE")
    public Date getFechaXDate() {
    	return fechaXDate;        
    }
    public void setFechaXDate(Date fechaXDate) {
    	this.fechaXDate = fechaXDate;       
    }
   
    @Column(name = "IMPORTE_NETT")
	public BigDecimal getImporteNETT() {
		return importeNETT;
	}
	public void setImporteNETT(BigDecimal importeNETT) {
		this.importeNETT = importeNETT;
	}
	
	@Column(name = "IMPORTE_WITL")
	public BigDecimal getImporteWITL() {
		return importeWITL;
	}
	public void setImporteWITL(BigDecimal importeWITL) {
		this.importeWITL = importeWITL;
	}
	
	@Column(name = "IMPORTE_FEE")
	public BigDecimal getImporteFEE() {
		return importeFEE;
	}
	public void setImporteFEE(BigDecimal importeFEE) {
		this.importeFEE = importeFEE;
	}
	
	
	
	@Column(name = "CFI")
	public String getCfi() {
		return cfi;
	}
	public void setCfi(String cfi) {
		this.cfi = cfi;
	}
	
	@Column(name = "FECHAS_ADICIONALES")
	public String getFechasAdicionales() {
		return fechasAdicionales;
	}
	public void setFechasAdicionales(String fechasAdicionales) {
		this.fechasAdicionales = fechasAdicionales;
	}
	
	@Column(name = "COAF")
	public String getCoaf() {
		return coaf;
	}
	public void setCoaf(String coaf) {
		this.coaf = coaf;
	}
	
	@Column(name = "SEME")
	public String getSeme() {
		return seme;
	}
	public void setSeme(String seme) {
		this.seme = seme;
	}
	
	@Column(name = "LINK")
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	
	@Column(name = "VALOR_NOMINAL")
	public BigDecimal getValorNominal() {
		return valorNominal;
	}
	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}
	
	@Column(name = "POSICION_INDEVAL")
	public BigDecimal getPosicionIndeval() {
		return posicionIndeval;
	}
	public void setPosicionIndeval(BigDecimal posicionIndeval) {
		this.posicionIndeval = posicionIndeval;
	}
	
	@Column(name = "FECHA_VENCIMIENTO")
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	@Column(name = "FECHA_HORA_RECEPCION")
	public Date getFechaHoraRecepcion() {
		return fechaHoraRecepcion;
	}
	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = fechaHoraRecepcion;
	}
	
	@Column(name = "FECHA_PRIMER_ENVIO")
	public Date getFechaPrimerEnvio() {
		return fechaPrimerEnvio;
	}
	public void setFechaPrimerEnvio(Date fechaPrimerEnvio) {
		this.fechaPrimerEnvio = fechaPrimerEnvio;
	}
	
	@Column(name = "FECHA_PREP")
	public Date getFechaPrePago() {
		return fechaPrePago;
	}
	public void setFechaPrePago(Date fechaPrePago) {
		this.fechaPrePago = fechaPrePago;
	}
	
	@Column(name = "OPCIONES_MENSAJE")
	public String getOpcionesMensaje() {
		return opcionesMensaje;
	}
	public void setOpcionesMensaje(String opcionesMensaje) {
		this.opcionesMensaje = opcionesMensaje;
	}
	
	@Column(name = "EDO_FUNCTION")
	public String getFunctionM() {
		return functionM;
	}
	public void setFunctionM(String functionM) {
		this.functionM = functionM;
	}
	
	@Column(name = "EDO_PROC")
	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}
	
	@Column(name = "FECHA_EFECTIVA")
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}

	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}
	
	@Transient
	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	@Column(name = "PRIORIDAD")
	public Long getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}
	
	@Column(name = "ID_BITACORA_MENSAJE")
	public Long getIdBitacoraMensaje() {
		return idBitacoraMensaje;
	}

	public void setIdBitacoraMensaje(Long idBitacoraMensaje) {
		this.idBitacoraMensaje = idBitacoraMensaje;
	}
	
	@Column(name = "ID_CALENDARIO_INT")
	public Long getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	@Transient
	public BigDecimal getImporteBruto() {
		BigDecimal valorRetorno = null;
		if(this.importeGRSS != null && this.posicionIndeval != null){
			valorRetorno = importeGRSS.multiply(posicionIndeval).setScale(12, BigDecimal.ROUND_HALF_UP);
		}
		return valorRetorno;
	}

	public void setPermiteEnvio(boolean permiteEnvio) {
	}

	@Column(name = "CUS_ID_CUSTODIO")
	public Integer getIdCustodio() {
		return idCustodio;
	}

	public void setIdCustodio(Integer idCustodio) {
		this.idCustodio = idCustodio;
	}

	@Column(name = "CUS_NOMBRE_CORTO")
	public String getNombreCortoCus() {
		return nombreCortoCus;
	}

	public void setNombreCortoCus(String nombreCortoCus) {
		this.nombreCortoCus = nombreCortoCus;
	}

	@Column(name = "CUS_DESCRIPCION")
	public String getDescripcionCus() {
		return descripcionCus;
	}

	public void setDescripcionCus(String descripcionCus) {
		this.descripcionCus = descripcionCus;
	}

	@Column(name = "CUS_ID_CATBIC")
	public Long getIdCatBicCus() {
		return idCatBicCus;
	}

	public void setIdCatBicCus(Long idCatBicCus) {
		this.idCatBicCus = idCatBicCus;
	}

	@Column(name = "CUS_CODIGO_BANCO")
	public String getCodigoBancoCus() {
		return codigoBancoCus;
	}

	public void setCodigoBancoCus(String codigoBancoCus) {
		this.codigoBancoCus = codigoBancoCus;
	}

	@Column(name = "CUS_PARTICIPANTE")
	public Integer getParticipanteCus() {
		return participanteCus;
	}

	public void setParticipanteCus(Integer participanteCus) {
		this.participanteCus = participanteCus;
	}

	@Column(name = "TP_CAEV_ID_TIPO_PAGO")
	public Long getIdTipoPagoCaev() {
		return idTipoPagoCaev;
	}

	public void setIdTipoPagoCaev(Long idTipoPagoCaev) {
		this.idTipoPagoCaev = idTipoPagoCaev;
	}

	@Column(name = "TP_CAEV_CLAVE_PAGO")
	public String getClavePagoCaev() {
		return clavePagoCaev;
	}

	public void setClavePagoCaev(String clavePagoCaev) {
		this.clavePagoCaev = clavePagoCaev;
	}

	@Column(name = "TP_CAEV_DESCRIPCION")
	public String getDescripcionCaev() {
		return descripcionCaev;
	}

	public void setDescripcionCaev(String descripcionCaev) {
		this.descripcionCaev = descripcionCaev;
	}

	@Column(name = "TP_CAEV_CAEV")
	public Integer getCaevCaev() {
		return caevCaev;
	}

	public void setCaevCaev(Integer caevCaev) {
		this.caevCaev = caevCaev;
	}

	@Column(name = "TP_CAEV_PARTICIPANTE")
	public Integer getParticipanteCaev() {
		return participanteCaev;
	}

	public void setParticipanteCaev(Integer participanteCaev) {
		this.participanteCaev = participanteCaev;
	}

	@Column(name = "TP_CAMV_ID_TIPO_PAGO")
	public Long getIdTipoPagoCamv() {
		return idTipoPagoCamv;
	}

	public void setIdTipoPagoCamv(Long idTipoPagoCamv) {
		this.idTipoPagoCamv = idTipoPagoCamv;
	}

	@Column(name = "TP_CAMV_CLAVE_PAGO")
	public String getClavePagoCamv() {
		return clavePagoCamv;
	}

	public void setClavePagoCamv(String clavePagoCamv) {
		this.clavePagoCamv = clavePagoCamv;
	}

	@Column(name = "TP_CAMV_DESCRIPCION")
	public String getDescripcionCamv() {
		return descripcionCamv;
	}

	public void setDescripcionCamv(String descripcionCamv) {
		this.descripcionCamv = descripcionCamv;
	}

	@Column(name = "TP_CAMV_CAEV")
	public Integer getCaevCamv() {
		return caevCamv;
	}

	public void setCaevCamv(Integer caevCamv) {
		this.caevCamv = caevCamv;
	}

	@Column(name = "TP_CAMV_PARTICIPANTE")
	public Integer getParticipanteCamv() {
		return participanteCamv;
	}

	public void setParticipanteCamv(Integer participanteCamv) {
		this.participanteCamv = participanteCamv;
	}

	@Column(name = "BOV_ID_BOVEDA")
	public Long getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	@Column(name = "BOV_ID_CUENTA_BOVEDA")
	public Long getIdCuentaBoveda() {
		return idCuentaBoveda;
	}

	public void setIdCuentaBoveda(Long idCuentaBoveda) {
		this.idCuentaBoveda = idCuentaBoveda;
	}

	@Column(name = "BOV_DESCRIPCION")
	public String getDescripcionBov() {
		return descripcionBov;
	}

	public void setDescripcionBov(String descripcionBov) {
		this.descripcionBov = descripcionBov;
	}

	@Column(name = "BOV_NOMBRE_CORTO")
	public String getNombreCortoBov() {
		return nombreCortoBov;
	}

	public void setNombreCortoBov(String nombreCortoBov) {
		this.nombreCortoBov = nombreCortoBov;
	}

	@Column(name = "BOV_ID_TIPO_BOVEDA")
	public Long getIdTipoBoveda() {
		return idTipoBoveda;
	}

	public void setIdTipoBoveda(Long idTipoBoveda) {
		this.idTipoBoveda = idTipoBoveda;
	}

	@Column(name = "EDOM_ID_ESTADO_MSJ_INT")
	public Long getIdEstadoMensajeIntSic() {
		return idEstadoMensajeIntSic;
	}

	public void setIdEstadoMensajeIntSic(Long idEstadoMensajeIntSic) {
		this.idEstadoMensajeIntSic = idEstadoMensajeIntSic;
	}

	@Column(name = "EDOM_NOMBRE")
	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	@Column(name = "EDOM_FECHA_CREACION")
	public Date getFechaCreacionEstado() {
		return fechaCreacionEstado;
	}

	public void setFechaCreacionEstado(Date fechaCreacionEstado) {
		this.fechaCreacionEstado = fechaCreacionEstado;
	}

	@Column(name = "VEMI_LISTADA")
	public String getListada() {
		return listada;
	}

	public void setListada(String listada) {
		this.listada = listada;
	}

	@Column(name = "ID_INSTRUCCIONES")
	public Long getIdInstrucciones() {
		return idInstrucciones;
	}

	public void setIdInstrucciones(Long idInstrucciones) {
		this.idInstrucciones = idInstrucciones;
	}

	@Column(name = "LIGA")
	public Long getLiga() {
		return liga;
	}

	public void setLiga(Long liga) {
		this.liga = liga;
	}

	@Column(name = "INSTR_OPCIONES_MENSAJE")
	public String getInstrOpcionesMensaje() {
		return instrOpcionesMensaje;
	}

	public void setInstrOpcionesMensaje(String instrOpcionesMensaje) {
		this.instrOpcionesMensaje = instrOpcionesMensaje;
	}

	@Column(name = "TAGS_ELIMINACION")
	public String getTagsEliminacion() {
		return tagsEliminacion;
	}

	public void setTagsEliminacion(String tagsEliminacion) {
		this.tagsEliminacion = tagsEliminacion;
	}
	
	@Column(name = "ID_TIPO_DERECHO_CAEV")
	public Long getIdTipoDerechoCaev() {
		return idTipoDerechoCaev;
	}

	public void setIdTipoDerechoCaev(Long idTipoDerechoCaev) {
		this.idTipoDerechoCaev = idTipoDerechoCaev;
	}

	/**
	 * @return the subtipoDerecho
	 */
	@Column(name="SUBTIPO_DERECHO")
	public String getSubtipoDerecho() {
		return subtipoDerecho;
	}

	/**
	 * @return the valor1
	 */
	@Transient
	public String getValor1() {
		return valor1;
	}

	/**
	 * @param valor1 the valor1 to set
	 */
	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}

	/**
	 * @return the valor2
	 */
	@Transient
	public String getValor2() {
		return valor2;
	}

	/**
	 * @param valor2 the valor2 to set
	 */
	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}

	/**
	 * @return the propiedadesCalendario
	 */
	@Column(name = "PROPIEDADES_CALENDARIO")
	public String getPropiedadesCalendario() {
		return propiedadesCalendario;
	}

	/**
	 * @return the propiedadesMap
	 */
	@Transient
	public Map<String, String> getPropiedadesMap() {
		return propiedadesMap;
	}

	/**
	 * @param propiedadesMap the propiedadesMap to set
	 */
	public void setPropiedadesMap(Map<String, String> propiedadesMap) {
		this.propiedadesMap = propiedadesMap;
	}
	
	/**
	 * 
	 * @param propiedadesCalendario
	 */
	public void setPropiedadesCalendario(String propiedadesCalendario) {
		if(propiedadesCalendario != null){
			this.propiedadesMap = new HashMap<String, String>();
			String [] propiedadArray = propiedadesCalendario.split("\\|");
			if(propiedadArray != null && propiedadArray.length > 0){
				for(int i = 0;i < propiedadArray.length;i++){
					String valor = propiedadArray[i];
					propiedadesMap.put(valor,valor);
				}
			}
		}
		this.propiedadesCalendario = propiedadesCalendario;
	}
	
	/**
	 * 
	 * @param subtipoDerecho
	 */
	public void setSubtipoDerecho(String subtipoDerecho) {
		if (subtipoDerecho != null) {
			String[] sub = subtipoDerecho.split("//");
			if (sub != null && sub.length > 1) {
				valor1 = sub[0];
				valor2 = sub[1];
			}
		}
		this.subtipoDerecho = subtipoDerecho;
	}
	/**
	 * @return the ratioAdicional
	 */
	@Column(name = "RATIO_ADICIONAL")
	public String getRatioAdicional() {
		return ratioAdicional;
	}
	/**
	 * @param ratioAdicional the ratioAdicional to set
	 */
	public void setRatioAdicional(String ratioAdicional) {
		this.ratioAdicional = ratioAdicional;
	}	
	
}
