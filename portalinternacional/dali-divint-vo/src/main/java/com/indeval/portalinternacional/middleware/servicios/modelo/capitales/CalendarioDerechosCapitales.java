package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;


@Entity
@Table(name = "T_CALENDARIO_INT")
@SequenceGenerator(name = "foliador", sequenceName = "ID_CALENDARIO_INT_SEQ", allocationSize = 1,
        initialValue = 1)
public class CalendarioDerechosCapitales implements Serializable {

    private static final long serialVersionUID = -3518299119966693232L;
    private Long idCalendario;// folioIndeval
    private String referencia;// Corp 
    private String tipoMensaje;// MT_564-568_910
    private TipoPagoInt tipoPagoCAEV;// ID_TIPO_PAGO ****
    private TipoPagoInt tipoPagoCAMV;// ID_TIPO_PAGO
    private String tipoValor;// TIPO_VALOR
    private String emisora;// EMISORA
    private String serie;// SERIE
    private String isin;// ISIN 
    private Date fechaCorte;// FECHA_CORTE
    private Date fechaPago;// FECHA_PAGO
    private Date fechaPrep;// FECHA_PREP
    private Date fechaXDate;// FECHA_XDATE
	private Boveda boveda;
	private BigDecimal posicionIndeval;// POSICION_INDEVAL
	private BigDecimal valorNominal;// VALOR_NOMINAL
	private BigDecimal importeIndeval;// IMPORTE_INDEVAL
	private BigDecimal importePTSA;// IMPORTE_PTSA
	private BigDecimal importeGRSS;// IMPORTE_GRSS
	private BigDecimal importeNETT;// IMPORTE_NETT
	private BigDecimal importeWITL;// IMPORTE_WITL
	private BigDecimal importeFEE;// IMPORTE FEE/Comision/Retension
	private String divisa;// MONEDA
	private Custodio custodio;// ID_CUSTODIO
	private EstadoDerechoInt estado;// ID_ESTADO_DERECHO_INT
	private Date fechaHoraRecepcion;
	private Date fechaHoraEnvio;
	private String fechasAdicionales;
	private String cfi; //CFI
	private String cupon; // CUPON
	private String seme; //No se visualiza en pantalla
    private Date fechaVencimiento;
    private String coaf; // COAF
    private String link;
    private String opcionesMensaje;
    private Integer is566; // IS_566
    private Integer prioridad;
    private EstadoMensajeIntSic estadoMensaje; //Func - Proc
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
    private VEmisionesCalendario vEmisionesCalendario;

     /**
     * Importe calculado al momento de consultar
     */
    private BigDecimal importeBruto;
  
	/**
     * dm Constructor por Default
     */
    public CalendarioDerechosCapitales() {
    	
    }


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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_CALENDARIO_INT")
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
    @Column(name = "MT_567_910")
    public String getTipoMensaje() {
        return this.tipoMensaje;
    }

    /**
     * @param folio the folio to set
     */
    public void setTipoMensaje(final String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @return the estado
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTADO_DERECHO_INT", unique = false, nullable = true)
    public EstadoDerechoInt getEstado() {
        return this.estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(final EstadoDerechoInt estado) {
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
        return this.fechaCorte != null ? new Date(this.fechaCorte.getTime()) : null;
    }

    /**
     * @param fechaCorte the fechaCorte to set
     */
    public void setFechaCorte(final Date fechaCorte) {
        this.fechaCorte = fechaCorte != null ? new Date(fechaCorte.getTime()) : null;
    }

    /**
     * @return the fechaPago
     */
    @Column(name = "FECHA_PAGO")
    public Date getFechaPago() {
        return this.fechaPago != null ? new Date(this.fechaPago.getTime()) : null;
    }

    /**
     * @param fechaPago the fechaPago to set
     */
    public void setFechaPago(final Date fechaPago) {
        this.fechaPago = fechaPago != null ? new Date(fechaPago.getTime()) : null;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = true)
    public Custodio getCustodio() {
        return this.custodio;
    }
    

    /**
     * @param custodio the custodio to set
     */
    public void setCustodio(final Custodio custodio) {
        this.custodio = custodio;
    }

    /**
     * @return the tipoPagoMensaje
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_PAGO_CAEV", unique = false, nullable = true)
    public TipoPagoInt getTipoPagoCAEV() {
        return this.tipoPagoCAEV;
    }

    /**
     * @param tipoPagoMensaje the tipoPagoMensaje to set
     */

    public void setTipoPagoCAEV(final TipoPagoInt tipoPagoCAEV) {
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
        if (this.estado != null && this.estado.getClaveEstado() != null
                && this.estado.getClaveEstado().equals(claveEstado)) {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_PAGO_CAMV", unique = false, nullable = true)
    public TipoPagoInt getTipoPagoCAMV() {
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

    public void setTipoPagoCAMV(final TipoPagoInt tipoPagoCAMV) {
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA", unique = false, nullable = true)
    public Boveda getBoveda() {
        return this.boveda;
    }


    /** @param boveda to be set in this.boveda */
    public void setBoveda(final Boveda boveda) {
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTADO_MSJ_INT", unique = false, nullable = true)
	public EstadoMensajeIntSic getEstadoMensaje() {
		return estadoMensaje;
	}


	/**
	 * @param estadoMensaje the estadoMensaje to set
	 */
	public void setEstadoMensaje(EstadoMensajeIntSic estadoMensaje) {
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
		return this.fechaEfectiva != null ? new Date(this.fechaEfectiva.getTime()) : null;
	}


	/**
	 * @param fechaEfectiva the fechaEfectiva to set
	 */
	public void setFechaEfectiva(Date fechaEfectiva) {
		 this.fechaEfectiva = fechaEfectiva != null ? new Date(fechaEfectiva.getTime()) : null;
	}


	/**
	 * @return the fechasAdicionales
	 */
	@Column(name = "FECHAS_ADICIONALES")
	public String getFechasAdicionales() {
		return this.fechasAdicionales != null ? this.fechasAdicionales.replaceAll("</br>", ", ") : null;
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
	 * @return the fechaHoraEnvio
	 */
	@Column(name = "FECHA_HORA_ENVIO")
	public Date getFechaHoraEnvio() {
		return this.fechaHoraEnvio;
	}
	
	/**
	 * @param fechaHoraRecepcion the fechaHoraEnvio to set
	 */
	public void setFechaHoraEnvio(Date fechaHoraEnvio) {
		this.fechaHoraEnvio = fechaHoraEnvio;
	}
	/**
	 * @return the prioridad
	 */
	@Column(name = "PRIORIDAD", unique = false, nullable = false)
	public Integer getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(Integer prioridad) {
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
	 * @return the vEmisionesCalendario
	 */
	@Transient
	public BigDecimal getImporteBruto() {
		BigDecimal valorRetorno = null;
		if(this.importeGRSS != null && this.posicionIndeval != null){
			valorRetorno = importeGRSS.multiply(posicionIndeval).setScale(6, BigDecimal.ROUND_HALF_UP);
		}
		return valorRetorno;
	}


	/**
	 * @param importeBruto the importeBruto to set
	 */
	public void setImporteBruto(BigDecimal importeBruto) {	
		this.importeBruto = importeBruto;
	}
	@Transient
	public String getFechasAdicionlesFormatoReporte(){
		return this.fechasAdicionales != null ? this.fechasAdicionales.replaceAll("</br>", ", ") : null;
	}
	
	/**
	 * @return the vEmisionesCalendario
	 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ISIN", referencedColumnName="ISIN", unique = false, nullable = true, insertable=false, updatable=false)
	public VEmisionesCalendario getvEmisionesCalendario() {
		return vEmisionesCalendario;
	}


	/**
	 * @param vEmisionesCalendario the vEmisionesCalendario to set
	 */
	public void setvEmisionesCalendario(VEmisionesCalendario vEmisionesCalendario) {
		this.vEmisionesCalendario = vEmisionesCalendario;
	}
	 
}
