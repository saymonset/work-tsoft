package com.indeval.portalinternacional.middleware.servicios.modelo;

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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portaldali.persistence.modelo.Boveda;
@Entity
@Table(name = "T_CALENDARIO_INT")
@SequenceGenerator(name = "foliador", sequenceName = "ID_CALENDARIO_INT_SEQ", allocationSize = 1, initialValue = 1)
public class CalendarioDerechos implements Serializable {

    private static final long serialVersionUID = -3518299119966693232L;
    private Long idCalendario;// ID_CALENDARIO_INT
    private String referencia;// REFERENCIA
    private String folio;// MT_567_910
    private EstadoDerechoInt estado;// ID_ESTADO_DERECHO_INT
    private String tipoValor;// TIPO_VALOR
    private String emisora;// EMISORA
    private String serie;// SERIE
    private String cupon;// CUPON
    private String isin;// ISIN
    private Date fechaCorte;// FECHA_CORTE
    private Date fechaPago;// FECHA_PAGO
    private Date fechaValor;// FECHA_VALOR
    private Date periodoInicio;// PERIODO
    private Integer diasMensaje;// DIAS_MENSAJE
    private BigDecimal factor;// FACTOR_MENSAJE
    private BigDecimal tasa;// TASA
    private Long posicionMensaje;// POSICION_MENSAJE
    private String divisa;// MONEDA
    private BigDecimal importe;// IMPORTE
    private BigDecimal importeIndeval;// IMPORTE_INDEVAL
    private BigDecimal factorVerificacion;// VERIFICACION_FACTOR
    private BigDecimal tasaVerificacion;// VERIFICACION_TASA
    private Integer diasVerificacion;// VERIFICACION_DIAS
    private String tipoTasa;// TIPO_TASA
    private BigDecimal posicionIndeval;// POSICION_INDEVAL
    private BigDecimal valorNominal;// VALOR_NOMINAL
    private Custodio custodio;// ID_CUSTODIO
    private TipoPagoInt tipoPagoCAEV;// ID_TIPO_PAGO
    private Boveda bovedaValores;// ID_BOVEDA
    private Boveda bovedaEfectivo;// ID_BOVEDA_DESTINO
    private TipoPagoInt tipoPagoCAMV;// ID_TIPO_PAGO
    private Date fechaPost; // fecha post
    private Long posicionVNIndeval;// posicion valor nominal
    private BigDecimal importePTSA;
    private BigDecimal importeGRSS;
    private Date periodoFin;
    private Integer diasSP;
    private BigDecimal factorCalculado;
    private Integer idEstadoMax;
    private Long prioridad;
    private Boolean has566;
    private Control control;
    private Date horaEnvio;
    private Date horaRecepcion;

    
    
    /**
     * Constructor por Default
     */
    public CalendarioDerechos() {

    }


    /**
     * @return the idCalendario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_CALENDARIO_INT")
    public Long getIdCalendario() {
        return idCalendario;
    }

    /**
     * @param idCalendario
     *            the idCalendario to set
     */
    public void setIdCalendario(Long idCalendario) {
        this.idCalendario = idCalendario;
    }

    /**
     * @return the referencia
     */
    @Column(name = "REFERENCIA")
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia
     *            the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the folio
     */
    @Column(name = "MT_567_910")
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio
     *            the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return the estado
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTADO_DERECHO_INT", unique = false, nullable = true)
    public EstadoDerechoInt getEstado() {
        return estado;
    }

    /**
     * @param estado
     *            the estado to set
     */
    public void setEstado(EstadoDerechoInt estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoValor
     */
    @Column(name = "TIPO_VALOR")
    public String getTipoValor() {
        return tipoValor;
    }

    /**
     * @param tipoValor
     *            the tipoValor to set
     */
    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    /**
     * @return the emisora
     */
    @Column(name = "EMISORA")
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora
     *            the emisora to set
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return the serie
     */
    @Column(name = "SERIE")
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie
     *            the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the cupon
     */
    @Column(name = "CUPON")
    public String getCupon() {
        return cupon;
    }

    /**
     * @param cupon
     *            the cupon to set
     */
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    /**
     * @return the isin
     */
    @Column(name = "ISIN")
    public String getIsin() {
        return isin;
    }

    /**
     * @param isin
     *            the isin to set
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * @return the fechaCorte
     */
    @Column(name = "FECHA_CORTE")
    public Date getFechaCorte() {
        return fechaCorte != null ? new Date(fechaCorte.getTime()) : null;
    }

    /**
     * @param fechaCorte
     *            the fechaCorte to set
     */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte != null ? new Date(fechaCorte.getTime()) : null;
    }

    /**
     * @return the fechaPago
     */
    @Column(name = "FECHA_PAGO")
    public Date getFechaPago() {
        return fechaPago != null ? new Date(fechaPago.getTime()) : null;
    }

    /**
     * @param fechaPago
     *            the fechaPago to set
     */
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago != null ? new Date(fechaPago.getTime()) : null;
    }

    /**
     * @return the fechaValor
     */
    @Column(name = "FECHA_VALOR")
    public Date getFechaValor() {
        return fechaValor != null ? new Date(fechaValor.getTime()) : null;
    }

    /**
     * @param fechaValor
     *            the fechaValor to set
     */
    public void setFechaValor(Date fechaValor) {
        this.fechaValor = fechaValor != null ? new Date(fechaValor.getTime()) : null;
    }

    /**
     * @return the periodo
     */
    @Column(name = "INICIO_PERIODO")
    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    /**
     * @param periodo
     *            the periodo to set
     */
    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    /**
     * @return the diasMensaje
     */
    @Column(name = "DIAS_MENSAJE")
    public Integer getDiasMensaje() {
        return diasMensaje;
    }

    /**
     * @param diasMensaje
     *            the diasMensaje to set
     */
    public void setDiasMensaje(Integer diasMensaje) {
        this.diasMensaje = diasMensaje;
    }

    /**
     * @return the factor
     */
    @Column(name = "FACTOR_MENSAJE")
    public BigDecimal getFactor() {
        return factor;
    }

    /**
     * @param factor
     *            the factor to set
     */
    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    /**
     * @return the tasa
     */
    @Column(name = "TASA")
    public BigDecimal getTasa() {
        return tasa;
    }

    /**
     * @param tasa
     *            the tasa to set
     */
    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    /**
     * @return the posicionMensaje
     */
    @Column(name = "POSICION_MENSAJE")
    public Long getPosicionMensaje() {
        return posicionMensaje;
    }

    /**
     * @param posicionMensaje
     *            the posicionMensaje to set
     */
    public void setPosicionMensaje(Long posicionMensaje) {
        this.posicionMensaje = posicionMensaje;
    }

    /**
     * @return the importe
     */
    @Column(name = "IMPORTE")
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe
     *            the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the divisa
     */
    @Column(name = "MONEDA")
    public String getDivisa() {
        return divisa;
    }

    /**
     * @param divisa
     *            the divisa to set
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    /**
     * @return the factorVerificacion
     */
    @Column(name = "VERIFICACION_FACTOR")
    public BigDecimal getFactorVerificacion() {
        return factorVerificacion;
    }

    /**
     * @param factorVerificacion
     *            the factorVerificacion to set
     */
    public void setFactorVerificacion(BigDecimal factorVerificacion) {
        this.factorVerificacion = factorVerificacion;
    }

    /**
     * @return the tasaVerificacion
     */
    @Column(name = "VERIFICACION_TASA")
    public BigDecimal getTasaVerificacion() {
        return tasaVerificacion;
    }

    /**
     * @param tasaVerificacion
     *            the tasaVerificacion to set
     */
    public void setTasaVerificacion(BigDecimal tasaVerificacion) {
        this.tasaVerificacion = tasaVerificacion;
    }

    /**
     * @return the diasVerificacion
     */
    @Column(name = "VERIFICACION_DIAS")
    public Integer getDiasVerificacion() {
        return diasVerificacion;
    }

    /**
     * @param diasVerificacion
     *            the diasVerificacion to set
     */
    public void setDiasVerificacion(Integer diasVerificacion) {
        this.diasVerificacion = diasVerificacion;
    }

    /**
     * @return the tipoTasa
     */
    @Column(name = "TIPO_TASA")
    public String getTipoTasa() {
        return tipoTasa;
    }

    /**
     * @param tipoTasa
     *            the tipoTasa to set
     */
    public void setTipoTasa(String tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    /**
     * @return the posicionIndeval
     */
    @Column(name = "POSICION_INDEVAL")
    public BigDecimal getPosicionIndeval() {
        return posicionIndeval;
    }

    /**
     * @param posicionIndeval
     *            the posicionIndeval to set
     */
    public void setPosicionIndeval(BigDecimal posicionIndeval) {
        this.posicionIndeval = posicionIndeval;
    }

    /**
     * @return the valorNominal
     */
    @Column(name = "VALOR_NOMINAL")
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * @param valorNominal
     *            the valorNominal to set
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    /**
     * @return the custodio
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = true)
    public Custodio getCustodio() {
        return custodio;
    }

    /**
     * @param custodio
     *            the custodio to set
     */
    public void setCustodio(Custodio custodio) {
        this.custodio = custodio;
    }

    /**
     * @return the tipoPagoMensaje
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_PAGO_CAEV", unique = false, nullable = true)
    public TipoPagoInt getTipoPagoCAEV() {
        return tipoPagoCAEV;
    }

    /**
     * @param tipoPagoMensaje
     *            the tipoPagoMensaje to set
     */

    public void setTipoPagoCAEV(TipoPagoInt tipoPagoCAEV) {
        this.tipoPagoCAEV = tipoPagoCAEV;
    }

    /**
     * @return the importeIndeval
     */
    @Column(name = "IMPORTE_INDEVAL")
    public BigDecimal getImporteIndeval() {
        return importeIndeval;
    }

    /**
     * @param importeIndeval
     *            the importeIndeval to set
     */
    public void setImporteIndeval(BigDecimal importeIndeval) {
        this.importeIndeval = importeIndeval;
    }

    /**
     * @return the idBoveda
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BOVEDA", unique = false, nullable = true)
    public Boveda getBovedaValores() {
        return bovedaValores;
    }

    /**
     * @param idBoveda
     *            the idBoveda to set
     */
    public void setBovedaValores(Boveda idBoveda) {
        this.bovedaValores = idBoveda;
    }

    /**
     * @return the idBovedaDestino
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BOVEDA_EFECTIVO", unique = false, nullable = true)
    public Boveda getBovedaEfectivo() {
        return bovedaEfectivo;
    }

    /**
     * @param idBovedaDestino
     *            the idBovedaDestino to set
     */
    public void setIdBovedaEfectivo(Boveda idBovedaEfectivo) {
        this.bovedaEfectivo = idBovedaEfectivo;
    }

    /*
     * Estados: RE REGISTRADO AU AUTORIZADO PV PREVIO CF CONFIRMADO PL
     * PRELIQUIDADO LI LIQUIDADO CA CANCELADO
     */
    private boolean validaEstado(String claveEstado) {
        if (estado != null && estado.getClaveEstado() != null && estado.getClaveEstado().equals(claveEstado)) {
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
        return validaEstado("RE");
    }

    /**
     * valida si se encuentra en estado AUTORIZADO
     */
    @Transient
    public boolean isAutorizado() {
        return validaEstado("AU");
    }

    /**
     * valida si se encuentra en estado PREVIO
     */
    @Transient
    public boolean isPrevio() {
        return validaEstado("PV");
    }

    /**
     * valida si se encuentra en estado CONFIRMADO
     */
    @Transient
    public boolean isConfirmado() {
        return validaEstado("CF");
    }

    /**
     * valida si se encuentra en estado PRELIQUIDADO
     */
    @Transient
    public boolean isPreliquidado() {
        return validaEstado("PL");
    }

    /**
     * valida si se encuentra en estado LIQUIDADO
     */
    @Transient
    public boolean isLiquidado() {
        return validaEstado("LI");
    }

    /**
     * valida si se encuentra en estado CANCELADO
     */
    @Transient
    public boolean isCancelado() {
        return validaEstado("CA");
    }

    /**
     * Mandatory/Voluntary Indicator<br>
     * Whether CA event is mandatory or voluntary and if instructions are
     * required from the account owner.<br>
     * <b>CHOS</b><br>
     * Mandatory CA event and an instruction is required<br>
     * Participation in CA event is mandatory; further instructions from the
     * account owner required.<br>
     * <br>
     * <b>MAND</b><br>
     * Mandatory CA event - no instruction required<br>
     * Participation in CA event is mandatory; further instructions from the
     * account owner not required.<br>
     * <br>
     * <b>VOLU</b><br>
     * Voluntary CA event and an instruction is required to participate<br>
     * Participation in CA event is voluntary; instructions from the account
     * owner required.
     * 
     * @return the tipoPagoCAMV
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_PAGO_CAMV", unique = false, nullable = true)
    public TipoPagoInt getTipoPagoCAMV() {
        return tipoPagoCAMV;
    }

    /**
     * Mandatory/Voluntary Indicator<br>
     * Whether CA event is mandatory or voluntary and if instructions are
     * required from the account owner.<br>
     * <b>CHOS</b><br>
     * Mandatory CA event and an instruction is required<br>
     * Participation in CA event is mandatory; further instructions from the
     * account owner required.<br>
     * <br>
     * <b>MAND</b><br>
     * Mandatory CA event - no instruction required<br>
     * Participation in CA event is mandatory; further instructions from the
     * account owner not required.<br>
     * <br>
     * <b>VOLU</b><br>
     * Voluntary CA event and an instruction is required to participate<br>
     * Participation in CA event is voluntary; instructions from the account
     * owner required.
     * 
     * @param tipoPagoCAMV
     *            the tipoPagoCAMV to set
     */
    public void setTipoPagoCAMV(TipoPagoInt tipoPagoCAMV) {
        this.tipoPagoCAMV = tipoPagoCAMV;
    }

    /**
     * @return the fechaPost
     */
    @Column(name = "FECHA_POST")
    public Date getFechaPost() {
        return fechaPost;
    }

    /**
     * @param fechaPost the fechaPost to set
     */
    public void setFechaPost(Date fechaPost) {
        this.fechaPost = fechaPost;
    }

    /**
     * @return the posicionNvIndeval
     */
    @Column(name = "POSICION_VN")
    public Long getPosicionVNIndeval() {
        return posicionVNIndeval;
    }

    /**
     * @param posicionNvIndeval the posicionNvIndeval to set
     */
    public void setPosicionVNIndeval(Long posicionNvIndeval) {
        this.posicionVNIndeval = posicionNvIndeval;
    }

    /**
     * @return the importePTSA
     */
    @Column(name = "IMPORTE_PTSA")
    public BigDecimal getImportePTSA() {
        return importePTSA;
    }

    /**
     * @param importePTSA the importePTSA to set
     */
    public void setImportePTSA(BigDecimal importePTSA) {
        this.importePTSA = importePTSA;
    }

    /**
     * @return the importeGRSS
     */
    @Column(name = "IMPORTE_GRSS")
    public BigDecimal getImporteGRSS() {
        return importeGRSS;
    }

    /**
     * @param importeGRSS the importeGRSS to set
     */
    public void setImporteGRSS(BigDecimal importeGRSS) {
        this.importeGRSS = importeGRSS;
    }

    /**
     * @return the periodoMT
     */
    @Column(name = "FIN_PERIODO")

    public Date getPeriodoFin() {
        return periodoFin;
    }

    /**
     * @param periodoMT the periodoMT to set
     */
    public void setPeriodoFin(Date periodoFin) {
        this.periodoFin = periodoFin;
    }

    /**
     * @return the diasSP
     */
    @Column(name = "DIAS_SP")
    public Integer getDiasSP() {
        return diasSP;
    }

    /**
     * @param diasSP the diasSP to set
     */
    public void setDiasSP(Integer diasSP) {
        this.diasSP = diasSP;
    }

    /**
     * @return the factorCalculado
     */
    @Column(name = "FACTOR_CALCULADO")
    public BigDecimal getFactorCalculado() {
        return factorCalculado;
    }

    /**
     * @param factorCalculado the factorCalculado to set
     */
    public void setFactorCalculado(BigDecimal factorCalculado) {
        this.factorCalculado = factorCalculado;
    }


	/**
	 * @return the idEstadoMax
	 */
    @Column(name = "ID_ESTADO_MAX")
	public Integer getIdEstadoMax() {
		return idEstadoMax;
	}


	/**
	 * @param idEstadoMax the idEstadoMax to set
	 */
	public void setIdEstadoMax(Integer idEstadoMax) {
		this.idEstadoMax = idEstadoMax;
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
	 * @param bovedaEfectivo the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(Boveda bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}
	/**
	 * Comprueba si el derecho es automatizado
	 * @return
	 */
	@Transient
	public boolean getEstaAutomatizado(){
		if(tipoValor != null && tipoValor.matches("(D1)|(D2)|(D3)|(D7)|(D8)|(JI)")){
			return true;
		}
		return false;
	}


	/**
	 * @return the has566
	 */
	@Column(name = "IS_566")	
	public Boolean getHas566() {
		return has566;
	}


	/**
	 * @param has566 the has566 to set
	 */	
	public void setHas566(Boolean has566) {
		this.has566 = has566;
	}
	/**
	 * @param has566 the has566 to set
	 */	
	public void setHas566(Integer has566) {
		if(has566 != null) {
			if(has566==0) {
				this.has566 = false;
			}else{
				this.has566 = true;
			}
		}else {
			this.has566 = false;
		}
	}


	/**
	 * @return the control
	 */
	 @ManyToOne(fetch = FetchType.EAGER)
	  @JoinColumns({
	    @JoinColumn(name="ID_ESTADO_DERECHO_INT", referencedColumnName="ID_ESTADO_DERECHO", insertable=false, updatable=false ),
	    @JoinColumn(name="CONTROL", referencedColumnName="ID_ESTADO_CONTROL", insertable=false, updatable=false)
	  }) 
	public Control getControl() {
		return control;
	}


	/**
	 * @param control the control to set
	 */
	public void setControl(Control control) {
		this.control = control;
	}
	
	@Column(name = "FECHA_HORA_ENVIO")	
	public Date getHoraEnvio() {
		return horaEnvio;
	}
	public void setHoraEnvio(Date horaEnvio) {
		this.horaEnvio = horaEnvio;
	}
	
	@Column(name = "FECHA_HORA_RECEPCION")	
	public Date getHoraRecepcion() {
		return horaRecepcion;
	}
	public void setHoraRecepcion(Date horaRecepcion) {
		this.horaRecepcion = horaRecepcion;
	}

}
