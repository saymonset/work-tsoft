package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class VCalendarioDerechoIntBase {

	private Long idCalendario;// folioIndeval
    private String referencia;// Corp 
    private String tipoMensaje;// MT_564-568_910
    private String tipoPagoCAEV;// ID_TIPO_PAGO ****
    private String tipoPagoCAEVDescripcion;
    private String tipoPagoCAMV;// ID_TIPO_PAGO
    private String tipoPagoCAMVDescripcion;
    private String tipoValor;// TIPO_VALOR
    private String emisora;// EMISORA
    private String serie;// SERIE
    private String isin;// ISIN 
    private Date fechaCorte;// FECHA_CORTE
    private Date fechaPago;// FECHA_PAGO
    private Date fechaPrep;// FECHA_PREP
    private Date fechaXDate;// FECHA_XDATE
	private String boveda;
	private BigDecimal posicionIndeval;// POSICION_INDEVAL
	private BigDecimal valorNominal;// VALOR_NOMINAL
	private BigDecimal importeIndeval;// IMPORTE_INDEVAL
	private BigDecimal importePTSA;// IMPORTE_PTSA
	private BigDecimal importeGRSS;// IMPORTE_GRSS
	private BigDecimal importeNETT;// IMPORTE_NETT
	private BigDecimal importeWITL;// IMPORTE_WITL
	private BigDecimal importeFEE;// IMPORTE FEE/Comision/Retension
	private String divisa;// MONEDA
	private String custodio;// ID_CUSTODIO
	private String estado;// ID_ESTADO_DERECHO_INT
	private Date fechaHoraRecepcion;
	private Date fechaPrimerEnvio;
	protected String fechasAdicionales;
	private String cfi; //CFI
	private String cupon; // CUPON
	private String seme; //No se visualiza en pantalla
    private Date fechaVencimiento;
    private String coaf; // COAF
    private String link;
    private String opcionesMensaje;
    private Integer is566; // IS_566
    private Long prioridad;
    private String estadoMensaje; 
    private Long estadoMensajeInt; 
    private Long idBoveda;
    private Integer idCustodio;
    private Long idTipoPagoCAEV;
    private Long idTipoPagoCAMV;
    private Date fechaEfectiva;
    private String edoFunc;
    private String edoProc;
 
    //falta en BD
	private Date fechaRegistro;
    //falta En BD
	private String estadoIndeval;
    //falta en BD
    private String listada;
     // LLena combo
    private String destinatario;
    //Duda
    private Long numeroEventoMensaje;
    //Listada   
    protected String vEmisionesCalendario;
    private String subtipoDerecho;
    protected String ratioAdicional;
    private String propiedades;
    protected String valor1;
    protected String valor2;
	private Map<String, Boolean> mapaPropiedades = new HashMap<String, Boolean>();
	private Date fechaEnvio;

    /**
     * 
     * @return the referencia
     */
    @Column(name = "REFERENCIA")
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
     * @return the idCalendario
     */

    @Id
    @Column(name = "ID_CALENDARIO")
    public Long getIdCalendario() {
        return this.idCalendario;
    }

    /**
     * @param idCalendario the idCalendario to set
     */
    public void setIdCalendario(final Long idCalendario) {
        this.idCalendario = idCalendario;
    }

    /**
     * @return the folio
     */
    @Column(name = "MT_567")
    public String getTipoMensaje() {
        return this.tipoMensaje;
    }

    /**
     * @param folio the folio to set
     */
    public void setTipoMensaje(final String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

  
    @Column(name = "ESTADO_DERECHO_DESCRIPCION")
    public String getEstado() {
        return this.estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(final String estado) {
        this.estado = estado;
    }
    
    /**
     * @return the tipoValor
     */
    @Column(name = "TIPO_VALOR")
    public String getTipoValor() {
        return this.tipoValor;
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
    @Column(name = "EMISORA")
    public String getEmisora() {
        return this.emisora;
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
    @Column(name = "SERIE")
    public String getSerie() {
        return this.serie;
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
    @Column(name = "ISIN")
    public String getIsin() {
        return this.isin;
    }

    /**
     * @param isin the isin to set
     */
    public void setIsin(final String isin) {
        this.isin = isin;
    }

    /**
     * @return the fechaCorte
     */
    @Column(name = "FECHA_CORTE")
    public Date getFechaCorte() {
        return this.fechaCorte;
    }

    /**
     * @param fechaCorte the fechaCorte to set
     */
    public void setFechaCorte(final Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /**
     * @return the fechaPago
     */
    @Column(name = "FECHA_PAGO")
    public Date getFechaPago() {
        return this.fechaPago;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(final Date fechaPago) {
        this.fechaPago = fechaPago;
    }

   
    /**
     * @return the fechaPrep
     */
    @Column(name = "FECHA_PREP")
    public Date getFechaPrep() {
        return this.fechaPrep;
    }

    /**
     * @param fechaPrep the fechaPrep to set
     */
    public void setFechaPrep(final Date fechaPrep) {
        this.fechaPrep = fechaPrep;
    }

    /**
     * @return the fechaXdate
     */
    @Column(name = "FECHA_XDATE")
    public Date getFechaXDate() {
        return this.fechaXDate;
    }

    /**
     * @param fechaXdate the fechaXdate to set
     */
    public void setFechaXDate(final Date fechaXDate) {
        this.fechaXDate = fechaXDate;
    }

   
    

    /**
     * @return the importeNETT
     */
    @Column(name = "IMPORTE_NETT")
    public BigDecimal getImporteNETT() {
        return this.importeNETT;
    }

    /**
     * @param importeNETT the importeNETT to set
     */
    public void setImporteNETT(final BigDecimal importeNETT) {
        this.importeNETT = importeNETT;
    }

     /**
     * @return the divisa
     */
    @Column(name = "MONEDA")
    public String getDivisa() {
        return this.divisa;
    }

    /**
     * @param divisa the divisa to set
     */
    public void setDivisa(final String divisa) {
        this.divisa = divisa;
    }

    /**
     * @return the posicionIndeval
     */
    @Column(name = "POSICION_INDEVAL")
    public BigDecimal getPosicionIndeval() {
        return this.posicionIndeval;
    }

    /**
     * @param posicionIndeval the posicionIndeval to set
     */
    public void setPosicionIndeval(final BigDecimal posicionIndeval) {
        this.posicionIndeval = posicionIndeval;
    }

    /**
     * @return the valorNominal
     */
    @Column(name = "VALOR_NOMINAL")
    public BigDecimal getValorNominal() {
        return this.valorNominal;
    }

    /**
     * @param valorNominal the valorNominal to set
     */
    public void setValorNominal(final BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    /**
     * @return the custodio
     */
    @Column(name = "C_NOMBRE_CORTO")
    public String getCustodio() {
        return this.custodio;
    }
    

    /**
     * @param custodio the custodio to set
     */
    public void setCustodio(final String custodio) {
        this.custodio = custodio;
    }

    /**
     * @return the tipoPagoMensaje
     */
    @Column(name = "TIPO_PAGO_CLAVE_PAGO")
    public String getTipoPagoCAEV() {
        return this.tipoPagoCAEV;
    }

    /**
     * @param tipoPagoMensaje the tipoPagoMensaje to set
     */

    public void setTipoPagoCAEV(final String tipoPagoCAEV) {
        this.tipoPagoCAEV = tipoPagoCAEV;
    }

    /**
     * @return the importeIndeval
     */
    @Column(name = "IMPORTE_INDEVAL")
    public BigDecimal getImporteIndeval() {
        return this.importeIndeval;
    }

    /**
     * @param importeIndeval the importeIndeval to set
     */
    public void setImporteIndeval(final BigDecimal importeIndeval) {
        this.importeIndeval = importeIndeval;
    }

    /*
     * Estados: RE REGISTRADO AU AUTORIZADO PV PREVIO CF CONFIRMADO PL PRELIQUIDADO LI LIQUIDADO CA
     * CANCELADO
     */
    private boolean validaEstado(final String claveEstado) {
        if (this.estado != null && this.estado != null
                && this.estado.equals(claveEstado)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * valida si se encuentra en estado REGISTRADO
     */
    @Transient
    public boolean isRegistrado() {
        return this.validaEstado("RE");
    }

    /**
     * valida si se encuentra en estado AUTORIZADO
     */
    @Transient
    public boolean isAutorizado() {
        return this.validaEstado("AU");
    }

    /**
     * valida si se encuentra en estado PREVIO
     */
    @Transient
    public boolean isPrevio() {
        return this.validaEstado("PV");
    }

    /**
     * valida si se encuentra en estado CONFIRMADO
     */
    @Transient
    public boolean isConfirmado() {
        return this.validaEstado("CF");
    }

    /**
     * valida si se encuentra en estado PRELIQUIDADO
     */
    @Transient
    public boolean isPreliquidado() {
        return this.validaEstado("PL");
    }

    /**
     * valida si se encuentra en estado LIQUIDADO
     */
    @Transient
    public boolean isLiquidado() {
        return this.validaEstado("LI");
    }

    /**
     * valida si se encuentra en estado CANCELADO
     */
    @Transient
    public boolean isCancelado() {
        return this.validaEstado("CA");
    }


    /**
     * Mandatory/Voluntary Indicator<br>
     * Whether CA event is mandatory or voluntary and if instructions are required from the account
     * owner.<br>
     * <b>CHOS</b><br>
     * Mandatory CA event and an instruction is required<br>
     * Participation in CA event is mandatory; further instructions from the account owner required.<br>
     * <br>
     * <b>MAND</b><br>
     * Mandatory CA event - no instruction required<br>
     * Participation in CA event is mandatory; further instructions from the account owner not
     * required.<br>
     * <br>
     * <b>VOLU</b><br>
     * Voluntary CA event and an instruction is required to participate<br>
     * Participation in CA event is voluntary; instructions from the account owner required.
     * 
     * @return the tipoPagoCAMV
     */

    @Column(name = "TIPO_PAGO_IN_CLAVE_PAGO")
    public String getTipoPagoCAMV() {
        return this.tipoPagoCAMV;
    }

    /**
     * Mandatory/Voluntary Indicator<br>
     * Whether CA event is mandatory or voluntary and if instructions are required from the account
     * owner.<br>
     * <b>CHOS</b><br>
     * Mandatory CA event and an instruction is required<br>
     * Participation in CA event is mandatory; further instructions from the account owner required.<br>
     * <br>
     * <b>MAND</b><br>
     * Mandatory CA event - no instruction required<br>
     * Participation in CA event is mandatory; further instructions from the account owner not
     * required.<br>
     * <br>
     * <b>VOLU</b><br>
     * Voluntary CA event and an instruction is required to participate<br>
     * Participation in CA event is voluntary; instructions from the account owner required.
     * 
     * @param tipoPagoCAMV the tipoPagoCAMV to set
     */

    public void setTipoPagoCAMV(final String tipoPagoCAMV) {
        this.tipoPagoCAMV = tipoPagoCAMV;
    }


    
    /**
     * @return the importePTSA
     */
    @Column(name = "IMPORTE_PTSA")
    public BigDecimal getImportePTSA() {
        return this.importePTSA;
    }

    /**
     * @param importePTSA the importePTSA to set
     */
    public void setImportePTSA(final BigDecimal importePTSA) {
        this.importePTSA = importePTSA;
    }

    /**
     * @return the importeGRSS
     */
    @Column(name = "IMPORTE_GRSS")
    public BigDecimal getImporteGRSS() {
        return this.importeGRSS;
    }

    /**
     * @param importeGRSS the importeGRSS to set
     */
    public void setImporteGRSS(final BigDecimal importeGRSS) {
        this.importeGRSS = importeGRSS;
    }


    /**
	 * @return the importeWITL
	 */
    @Column(name = "IMPORTE_WITL")
	public BigDecimal getImporteWITL() {
		return importeWITL;
	}


	/**
	 * @param importeWITL the importeWITL to set
	 */
	public void setImporteWITL(BigDecimal importeWITL) {
		this.importeWITL = importeWITL;
	}


	/**
	 * @return the importeFEE
	 */
	@Column(name = "IMPORTE_FEE")
	public BigDecimal getImporteFEE() {
		return importeFEE;
	}


	/**
	 * @param importeFEE the importeFEE to set
	 */
	public void setImporteFEE(BigDecimal importeFEE) {
		this.importeFEE = importeFEE;
	}


	/** @return this.boveda */
    @Column(name = "B_NOMBRE")
    public String getBoveda() {
        return this.boveda;
    }


    /** @param boveda to be set in this.boveda */
    public void setBoveda(final String boveda) {
        this.boveda = boveda;
    }


	/**
	 * @return the numeroEventoMensaje
	 */
	@Transient
	public Long getNumeroEventoMensaje() {
		return numeroEventoMensaje;
	}


	/**
	 * @param numeroEventoMensaje the numeroEventoMensaje to set
	 */
	public void setNumeroEventoMensaje(Long numeroEventoMensaje) {
		this.numeroEventoMensaje = numeroEventoMensaje;
	}


	/**
	 * @return the estadoMensaje
	 */
	@Column(name = "ESTADO_MENSAJE_NOMBRE")
	public String getEstadoMensaje() {
		return estadoMensaje;
	}


	/**
	 * @param estadoMensaje the estadoMensaje to set
	 */
	public void setEstadoMensaje(String estadoMensaje) {
		this.estadoMensaje = estadoMensaje;
	}


	/**
	 * @return the fechaRegistro
	 */
	@Transient
	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	/**
	 * @return the fechaEfectiva
	 */
	@Column(name = "FECHA_EFECTIVA")
	public Date getFechaEfectiva() {
		return this.fechaEfectiva;
	}


	/**
	 * @param fechaEfectiva the fechaEfectiva to set
	 */
	public void setFechaEfectiva(Date fechaEfectiva) {
		 this.fechaEfectiva = fechaEfectiva;
	}


	/**
	 * @return the fechasAdicionales
	 */
	@Column(name = "FECHAS_ADICIONALES")
	public String getFechasAdicionales() {
		return this.fechasAdicionales;
	}


	/**
	 * @param fechasAdicionales the fechasAdicionales to set
	 */
	public void setFechasAdicionales(String fechasAdicionales) {
		this.fechasAdicionales = fechasAdicionales;
		
     }
	
	/**
	 * @return the estadoIndeval
	 */
	@Transient
	public String getEstadoIndeval() {
		return estadoIndeval;
	}


	/**
	 * @param estadoIndeval the estadoIndeval to set
	 */
	public void setEstadoIndeval(String estadoIndeval) {
		this.estadoIndeval = estadoIndeval;
	}


	/**
	 * @return the listada
	 */
	@Transient
	public String getListada() {
		return listada;
	}


	/**
	 * @param listada the listada to set
	 */
	public void setListada(String listada) {
		this.listada = listada;
	}


	/**
	 * @return the destinatario
	 */
	@Transient
	public String getDestinatario() {
		return destinatario;
	}


	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the cfi
	 */
	@Column(name = "CFI")
	public String getCfi() {
		return cfi;
	}


	/**
	 * @param cfi the cfi to set
	 */
	public void setCfi(String cfi) {
		this.cfi = cfi;
	}


	/**
	 * @return the cupon
	 */
	@Column(name = "CUPON")
	public String getCupon() {
		return cupon;
	}


	/**
	 * @param cupon the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}


	/**
	 * @return the seme
	 */
	@Column(name = "SEME")
	public String getSeme() {
		return seme;
	}


	/**
	 * @param seme the seme to set
	 */
	public void setSeme(String seme) {
		this.seme = seme;
	}


	/**
	 * @return the fechaVencimiento
	 */
	@Column(name = "FECHA_VENCIMIENTO")
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}


	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}


	/**
	 * @return the coaf
	 */
	@Column(name = "COAF")
	public String getCoaf() {
		return coaf;
	}


	/**
	 * @param coaf the coaf to set
	 */
	public void setCoaf(String coaf) {
		this.coaf = coaf;
	}


	/**
	 * @return the link
	 */
	@Column(name = "LINK")
	public String getLink() {
		return link;
	}


	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}


	/**
	 * @return the opcionesMensaje
	 */
	@Column(name = "OPCIONES_MENSAJE")
	public String getOpcionesMensaje() {
		return opcionesMensaje;
	}


	/**
	 * @param opcionesMensaje the opcionesMensaje to set
	 */
	public void setOpcionesMensaje(String opcionesMensaje) {
		this.opcionesMensaje = opcionesMensaje;
	}


	/**
	 * @return the is566
	 */
	@Column(name = "IS_566")
	public Integer getIs566() {
		return is566;
	}


	/**
	 * @param is566 the is566 to set
	 */
	public void setIs566(Integer is566) {
		this.is566 = is566;
	}

	/**
	 * @return the fechaHoraRecepcion
	 */
	@Column(name = "FECHA_HORA_RECEPCION")
	public Date getFechaHoraRecepcion() {
		return this.fechaHoraRecepcion;
	}
	
	/**
	 * @param fechaHoraRecepcion the fechaHoraRecepcion to set
	 */
	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = fechaHoraRecepcion;
	}
	/**
	 * @return the fechaPrimerEnvio
	 */
	@Column(name = "FECHA_PRIMER_ENVIO")
	public Date getFechaPrimerEnvio() {
		return this.fechaPrimerEnvio;
	}
	
	/**
	 * @param fechaHoraRecepcion the fechaHoraEnvio to set
	 */
	public void setFechaPrimerEnvio(Date fechaPrimerEnvio) {
		this.fechaPrimerEnvio = fechaPrimerEnvio;
	}
	/**
	 * @return the prioridad
	 */
	@Column(name = "PRIORIDAD")
	public Long getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}


	/**
	 * @return the edoFunc
	 */
	@Column(name = "EDO_FUNCTION")
	public String getEdoFunc() {
		return edoFunc;
	}


	/**
	 * @param edoFunc the edoFunc to set
	 */
	public void setEdoFunc(String edoFunc) {
		this.edoFunc = edoFunc;
	}


	/**
	 * @return the edoProc
	 */
	@Column(name = "EDO_PROC")
	public String getEdoProc() {
		return edoProc;
	}


	/**
	 * @param edoProc the edoProc to set
	 */
	public void setEdoProc(String edoProc) {
		this.edoProc = edoProc;
	}
	
	
	/**
	 * @return 
	 */
	@Transient
	public BigDecimal getImporteBruto() {
		BigDecimal valorRetorno = null;
		if(this.importeGRSS != null && this.posicionIndeval != null){
			valorRetorno = importeGRSS.multiply(posicionIndeval).setScale(12, BigDecimal.ROUND_HALF_UP);
		}
		return valorRetorno;
	}
	
	
	
	/**
	 * @return the vEmisionesCalendario
	 */
    @Column(name = "V_LISTADA")
	public String getvEmisionesCalendario() {
		return vEmisionesCalendario;
	}


	/**
	 * @param vEmisionesCalendario the vEmisionesCalendario to set
	 */
	public void setvEmisionesCalendario(String vEmisionesCalendario) {
		this.vEmisionesCalendario = vEmisionesCalendario;
	}


	/**
	 * @return the estadoMensajeInt
	 */
	@Column(name="ID_ESTADO_MENSAJE")
	public Long getEstadoMensajeInt() {
		return estadoMensajeInt;
	}


	/**
	 * @param estadoMensajeInt the estadoMensajeInt to set
	 */
	public void setEstadoMensajeInt(Long estadoMensajeInt) {
		this.estadoMensajeInt = estadoMensajeInt;
	}


	/**
	 * @return the idBoveda
	 */
	@Column(name="B_ID_BOVEDA")
	public Long getIdBoveda() {
		return idBoveda;
	}


	/**
	 * @param idBoveda the idBoveda to set
	 */
	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}


	/**
	 * @return the idCustodio
	 */
	@Column(name="C_ID_CUSTODIO")
	public Integer getIdCustodio() {
		return idCustodio;
	}


	/**
	 * @param idCustodio the idCustodio to set
	 */
	public void setIdCustodio(Integer idCustodio) {
		this.idCustodio = idCustodio;
	}


	/**
	 * @return the idTipoPagoCAEV
	 */
	@Column(name="ID_TIPO_PAGO_CAEV")
	public Long getIdTipoPagoCAEV() {
		return idTipoPagoCAEV;
	}


	/**
	 * @param idTipoPagoCAEV the idTipoPagoCAEV to set
	 */
	public void setIdTipoPagoCAEV(Long idTipoPagoCAEV) {
		this.idTipoPagoCAEV = idTipoPagoCAEV;
	}


	/**
	 * @return the idTipoPagoCAMV
	 */
	@Column(name="ID_TIPO_PAGO_CAMV")
	public Long getIdTipoPagoCAMV() {
		return idTipoPagoCAMV;
	}


	/**
	 * @param idTipoPagoCAMV the idTipoPagoCAMV to set
	 */
	public void setIdTipoPagoCAMV(Long idTipoPagoCAMV) {
		this.idTipoPagoCAMV = idTipoPagoCAMV;
	}
	
	

	/**
	 * @return the tipoPagoCAEVDescripcion
	 */
	@Column(name="TIPO_PAGO_DESCRIPCION_CAEV")
	public String getTipoPagoCAEVDescripcion() {
		return tipoPagoCAEVDescripcion;
	}


	/**
	 * @param tipoPagoCAEVDescripcion the tipoPagoCAEVDescripcion to set
	 */
	public void setTipoPagoCAEVDescripcion(String tipoPagoCAEVDescripcion) {
		this.tipoPagoCAEVDescripcion = tipoPagoCAEVDescripcion;
	}


	/**
	 * @return the tipoPagoCAMVDescripcion
	 */
	@Column(name="TIPO_PAGO_CAMV_DESCRIPCION")
	public String getTipoPagoCAMVDescripcion() {
		return tipoPagoCAMVDescripcion;
	}


	/**
	 * @param tipoPagoCAMVDescripcion the tipoPagoCAMVDescripcion to set
	 */
	public void setTipoPagoCAMVDescripcion(String tipoPagoCAMVDescripcion) {
		this.tipoPagoCAMVDescripcion = tipoPagoCAMVDescripcion;
	}


	/**
	 * @return the subtipoDerecho
	 */
	@Column(name="SUBTIPO_DERECHO")
	public String getSubtipoDerecho() {
		return subtipoDerecho;
	}


	/**
	 * @param subtipoDerecho the subtipoDerecho to set
	 */
	public void setSubtipoDerecho(String subtipoDerecho) {
		this.subtipoDerecho = subtipoDerecho;
		if (subtipoDerecho != null) {
			String[] sub = subtipoDerecho.split("//");
			if (sub != null && sub.length > 1) {
				valor1 = sub[0];
				valor2 = sub[1];
			}
		}
	
	}	
    
	/**
	 * @return the ratioAdicional
	 */
	@Column(name="RATIO_ADICIONAL")
	public String getRatioAdicional() {
		return ratioAdicional;
	}

	/**
	 * @param ratioAdicional the ratioAdicional to set
	 */
	public void setRatioAdicional(String ratioAdicional) {
		this.ratioAdicional = ratioAdicional;
	}

	/**
	 * @return the propiedades
	 */
	@Column(name="PROPIEDADES_CALENDARIO")
	public String getPropiedades() {
		return propiedades;
	}


	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(String propiedades) {
		this.propiedades = propiedades;
		if (propiedades != null) {
			String[] prop = propiedades.split("\\|");
			if (prop.length > 1) {
				for (int i = 0; i < prop.length; i++) {
					mapaPropiedades.put(prop[i], Boolean.TRUE);
				}
			} else {
				mapaPropiedades.put(prop[0], Boolean.TRUE);
			}
		}
		
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
	 * @return the mapaPropiedades
	 */
	@Transient
	public Map<String, Boolean> getMapaPropiedades() {
		return mapaPropiedades;
	}


	/**
	 * @param mapaPropiedades the mapaPropiedades to set
	 */
	public void setMapaPropiedades(Map<String, Boolean> mapaPropiedades) {
		this.mapaPropiedades = mapaPropiedades;
	}

	/**
	 * @return the fechaEnvio
	 */
	@Column(name="FECHA_ENVIO")
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
}
