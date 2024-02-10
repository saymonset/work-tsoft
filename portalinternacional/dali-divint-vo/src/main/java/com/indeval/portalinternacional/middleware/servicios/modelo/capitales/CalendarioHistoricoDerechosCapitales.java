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
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;


/**
 * Entidad de la tabla T_HISTORICO_DERECHO_INT
 * @author javier.perez
 *
 */
@Entity
@Table(name = "T_HISTORICO_DERECHO_INT")
@SequenceGenerator(name = "foliador", sequenceName = "T_HISTORICO_DER_SEQ", allocationSize = 1, initialValue = 1)
public class CalendarioHistoricoDerechosCapitales implements Serializable {
	
	 private static final long serialVersionUID = -3518299119966693232L;
	 	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_HIST_DERECHO_INT")
	private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = true)
    private Custodio custodio;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_PAGO_CAEV", unique = false, nullable = true)
    private TipoPagoInt tipoPagoCAEV;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_PAGO_CAMV", unique = false, nullable = true)
    private TipoPagoInt tipoPagoCAMV;

    @Column(name = "ID_BITACORA_MENSAJE")
    private Long idBitacoraMensaje;
    
    @Column(name = "ID_CALENDARIO_INT")
    private Long idCalendario;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA", unique = false, nullable = true)
    private Boveda boveda;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTADO_MSJ_INT", unique = false, nullable = true)
    private EstadoMensajeIntSic estadoMensaje;		

    @Column(name = "CONSECUTIVO")
    private Long consecutivo;
    
    @Column(name = "NUMERO_CONFIRMADOS")
    private Long numeroConfirmados;
    
    @Column(name = "NUMERO_ENVIOS")
    private Long numeroEnvios;
    
    @Column(name = "MT_567_910")
    private String tipoMensaje;
    
    @Column(name = "ISIN")
    private String isin;
    
    @Column(name = "REFERENCIA")
    private String referencia;
    
    @Column(name = "SEME")
    private String seme;
    
    @Column(name = "FECHA_CORTE")
    private Date fechaCorte;
    
    @Column(name = "FECHA_PAGO")
    private Date fechaPago;
    
    @Column(name = "FECHA_XDATE")
    private Date fechaXDate;
    
    @Column(name = "FECHA_VENCIMIENTO")
    private Date fechaVencimiento;
    
    @Column(name = "IMPORTE_GRSS")
    private BigDecimal importeGRSS;
    
    @Column(name = "IMPORTE_PTSA")
    private BigDecimal importePTSA;
    
    @Column(name = "IMPORTE_NETT")
    private BigDecimal importeNETT;
    
    @Column(name = "IMPORTE_WITL")
    private BigDecimal importeWITL;
    
    @Column(name = "IMPORTE_FEE")
    private BigDecimal importeFEE;
    
    @Column(name = "CFI")
    private String cfi;
    
    @Column(name = "MONEDA")
    private String divisa;
    
    @Column(name = "TIPO_VALOR")
    private String tipoValor;
    
    @Column(name = "EMISORA")
    private String emisora;
    
    @Column(name = "SERIE")
    private String serie;
    
    @Column(name = "CUPON")
    private String cupon;
    
    @Column(name = "COAF")
    private String coaf;
    
    @Column(name = "FECHAS_ADICIONALES")
    private String fechasAdicionales;
    
    @Column(name = "LINK")
    private String links;
    
    @Column(name = "IS_566")
    private Integer is566;
    
    @Column(name = "VALOR_NOMINAL")
    private BigDecimal valorNominal;
    
    @Column(name = "POSICION_INDEVAL")
    private BigDecimal posicionIndeval;
    
    @Column(name = "FECHA_HORA_RECEPCION")
    private Date fechaHoraRecepcion;
    
    @Column(name = "FECHA_HORA_ENVIO")
    private Date fechaHoraEnvio;
    
    @Column(name = "FECHA_PREP")
    private Date fechaPrePago;
    
    @Column(name = "OPCIONES_MENSAJE")
    private String opcionesMensaje;
    
    @Column(name = "EDO_FUNCTION")
    private String functionM;
    
    @Column(name = "EDO_PROC")
    private String estadoProceso;
    
    @Column(name = "FECHA_EFECTIVA")
    private Date fechaEfectiva;
    
    @Column(name = "PRIORIDAD")
    private Long prioridad;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ISIN", referencedColumnName="ISIN", unique = false, nullable = true, insertable=false, updatable=false)
    private VEmisionesCalendario vEmisionesCalendario;
    
    @Transient
    private String destinatario;
    
    @Transient
    private BigDecimal importeBruto;
    
        
	public CalendarioHistoricoDerechosCapitales() {

    }

	public Long getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(Long consecutivo) {
		this.consecutivo = consecutivo;
	}
	public Long getNumeroConfirmados() {
		return numeroConfirmados;
	}
	public void setNumeroConfirmados(Long numeroConfirmados) {
		this.numeroConfirmados = numeroConfirmados;
	}
	public Long getNumeroEnvios() {
		return numeroEnvios;
	}
	public void setNumeroEnvios(Long numeroEnvios) {
		this.numeroEnvios = numeroEnvios;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public String getTipoMensaje() {
        return tipoMensaje;
    }
    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
    public String getTipoValor() {
        return tipoValor;
    }
    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }
    public String getEmisora() {
        return emisora;
    }
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public String getCupon() {
        return cupon;
    }
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }
    public String getIsin() {
        return isin;
    }
    public void setIsin(String isin) {
        this.isin = isin;
    }
    public Date getFechaCorte() {
        return fechaCorte;
    }
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }
    public Date getFechaPago() {
        return fechaPago;
    }
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    public String getDivisa() {
        return divisa;
    }
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }
    public Custodio getCustodio() {
        return custodio;
    }
    public void setCustodio(Custodio custodio) {
        this.custodio = custodio;
    }
    public TipoPagoInt getTipoPagoCAEV() {
        return tipoPagoCAEV;
    }
    public void setTipoPagoCAEV(TipoPagoInt tipoPagoCAEV) {
        this.tipoPagoCAEV = tipoPagoCAEV;
    }
    public TipoPagoInt getTipoPagoCAMV() {
        return tipoPagoCAMV;
    }
    public void setTipoPagoCAMV(TipoPagoInt tipoPagoCAMV) {
        this.tipoPagoCAMV = tipoPagoCAMV;
    }
    public BigDecimal getImportePTSA() {
        return importePTSA;
    }
    public void setImportePTSA(BigDecimal importePTSA) {
        this.importePTSA = importePTSA;
    }
    public BigDecimal getImporteGRSS() {
        return importeGRSS;
    }
    public void setImporteGRSS(BigDecimal importeGRSS) {
        this.importeGRSS = importeGRSS;
    }
	public Integer getIs566() {
		return is566;
	}
	public void setIs566(Integer is566) {
		this.is566 = is566;
	}
    public Date getFechaXDate() {
    	return fechaXDate;        
    }
    public void setFechaXDate(Date fechaXDate) {
    	this.fechaXDate = fechaXDate;       
    }
   
	public BigDecimal getImporteNETT() {
		return importeNETT;
	}
	public void setImporteNETT(BigDecimal importeNETT) {
		this.importeNETT = importeNETT;
	}
	public BigDecimal getImporteWITL() {
		return importeWITL;
	}
	public void setImporteWITL(BigDecimal importeWITL) {
		this.importeWITL = importeWITL;
	}
	public BigDecimal getImporteFEE() {
		return importeFEE;
	}
	public void setImporteFEE(BigDecimal importeFEE) {
		this.importeFEE = importeFEE;
	}
	public String getCfi() {
		return cfi;
	}
	public void setCfi(String cfi) {
		this.cfi = cfi;
	}
	public String getFechasAdicionales() {
		return fechasAdicionales;
	}
	public void setFechasAdicionales(String fechasAdicionales) {
		this.fechasAdicionales = fechasAdicionales;
	}
	public String getCoaf() {
		return coaf;
	}
	public void setCoaf(String coaf) {
		this.coaf = coaf;
	}
	public String getSeme() {
		return seme;
	}
	public void setSeme(String seme) {
		this.seme = seme;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public Boveda getBoveda() {
		return boveda;
	}
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}
	public BigDecimal getValorNominal() {
		return valorNominal;
	}
	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}
	public BigDecimal getPosicionIndeval() {
		return posicionIndeval;
	}
	public void setPosicionIndeval(BigDecimal posicionIndeval) {
		this.posicionIndeval = posicionIndeval;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Date getFechaHoraRecepcion() {
		return fechaHoraRecepcion;
	}
	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = fechaHoraRecepcion;
	}
	public Date getFechaHoraEnvio() {
		return fechaHoraEnvio;
	}
	public void setFechaHoraEnvio(Date fechaHoraEnvio) {
		this.fechaHoraEnvio = fechaHoraEnvio;
	}
	public Date getFechaPrePago() {
		return fechaPrePago;
	}
	public void setFechaPrePago(Date fechaPrePago) {
		this.fechaPrePago = fechaPrePago;
	}
	
	public String getOpcionesMensaje() {
		return opcionesMensaje;
	}
	public void setOpcionesMensaje(String opcionesMensaje) {
		this.opcionesMensaje = opcionesMensaje;
	}
	public String getFunctionM() {
		return functionM;
	}
	public void setFunctionM(String functionM) {
		this.functionM = functionM;
	}

	public EstadoMensajeIntSic getEstadoMensaje() {
		return estadoMensaje;
	}
	public void setEstadoMensaje(EstadoMensajeIntSic estadoMensaje) {
		this.estadoMensaje = estadoMensaje;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}
	
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}

	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Long getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}

	public Long getIdBitacoraMensaje() {
		return idBitacoraMensaje;
	}

	public void setIdBitacoraMensaje(Long idBitacoraMensaje) {
		this.idBitacoraMensaje = idBitacoraMensaje;
	}

	public Long getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	/**
	 * @return the importeBruto
	 */
	public BigDecimal getImporteBruto() {
		BigDecimal valorRetorno = null;
		if(this.importeGRSS != null && this.posicionIndeval != null){
			valorRetorno = importeGRSS.multiply(posicionIndeval).setScale(12, BigDecimal.ROUND_HALF_UP);
		}
		return valorRetorno;
	}

	/**
	 * @param importeBruto the importeBruto to set
	 */
	public void setImporteBruto(BigDecimal importeBruto) {	
		this.importeBruto = importeBruto;
	}
	
	public String getFechasAdicionlesFormatoReporte(){
		return this.fechasAdicionales != null ? this.fechasAdicionales.replaceAll("</br>", ", ") : null;
	}

	/**
	 * @return the vEmisionesCalendario
	 */
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
