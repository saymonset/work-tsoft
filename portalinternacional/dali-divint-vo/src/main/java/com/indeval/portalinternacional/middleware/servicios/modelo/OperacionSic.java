/*
 * Copyright (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

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
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Emisora;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
@Entity
@Table(name = "T_OPERACIONES_SIC")
@SequenceGenerator(name = "foliador", sequenceName = "ID_OPERACIONES_SIC_SEQ", allocationSize = 1, initialValue = 1)
public class OperacionSic implements Serializable {

    /** Default Serial Version UID */
    private static final long serialVersionUID = 1L;

    private Long idOperacionesSic;

    private CuentaNombrada cuentaNombrada;

    private String cuentaRecep;

    private String tipoMensaje;

    private Emision emision;

    private Boveda boveda;

    private Date fechaNotificacion;

    private Date fechaLiquidacion;

    private Date fechaOperacion;

    private BigInteger cantidadTitulos;
    
	private BigInteger cantidadTitulosParcial;
	
	private BigInteger remanente;

    private CatBic catBic;

    private String cuentaContraparte;

    private String descContraparte;

    private SicDetalle sicDetalle;

    private String instruccEspeciales;

    private String nomCtaBenef;

    private String numCtaBenef;

    private BigDecimal importe;
    
    private BigDecimal importeParcial;
    
    private BigDecimal remanenteEfectivo;

    private String divisa;

    private ErrorSwift errorSwift;

    private Date fecha520;

    private Date fecha530;

    private Date fecha572;

    private Date fecha592;

    private Date fechaHora;

    private Date fechaNot592;

    private String mensajeEfectivo;

    private String refMsjPfi;

    private String stPfi548;

    private EstatusOperacion estatusOperacion;

    private String usuario;

    private Date fechaAlta;

    private Date fechaCambio;

    private String tipoMensajeConfirmacion;

    private Date fechaEfectivo103;

    private BigInteger folioControl;

    private Date fechaLiquidacionTlp;

    private Date fechaHoraHabilitada;

    private BigInteger origenPfi;

    private BigInteger cambioBoveda;

    /** Atributos transitorios - no persistentes */

    /* Almacena el tipo de traspaso - TLP (Traspaso libre de pago), TCP (Traspaso contra pago) */
    private String tipoTraspaso;

    /* Almacena el tipo de operacion - E (Entrega), R (Rececpion) */
    private String tipoOperacion;

    /* Obtiene el rango de fechas para la consulta de operaciones */
    private Date[] fechaConsulta;

    /* Indica si el usuario puede liberar la operacion */
    private boolean libera;

    /* Indica si el usuario puede confirmar la operacion */
    private boolean confirma;

    /* Indica si el usuario puede autorizar la operacion */
    private boolean autoriza;

    /* Indica si la operacion se actualiza con o sin mensaje */
    private boolean conMensaje;

    /* Indica si el usuario puede cancelar la operacion */
    private boolean cancela;

    /* Indica si se debe regresar el estado a enviada */
    private boolean regresa;

    /* Indica si el usuario autoriza el cambio de estado de la operacion */
    private boolean autorizada;

    /* Indica si el usuario puede realizar un regreso de posicion a la operacion, que debe ser de tipo 
     * Cambio de Boveda */
    private boolean regresaPosicion;

    /* Regresa la fecha para la consulta correspondiente al estatus de la operacion */
    private Date fechaActualizacion;

    /* Regresa una descripcion del tipo de mensaje al que pertenece la operacion */
    private String descTipoMensaje;

    /* Indica la peticion del usuario para regresar la posicion de la operacion de cambio de boveda */
    private boolean regresoPosicion;

    /* Indica la peticion del usuario para liberar la operacion */
    private boolean libero;

    /* Indica la peticion del usuario para confirmar la operacion */
    private boolean confirmo;

    /* Indica la peticion del usuario para autorizar la operacion */
    private boolean autorizo;

    /* Indica la peticion del usuario para cancelar la operacion */
    private boolean cancelo;

    /* Indica la peticion del usuario pararegresar el estado a enviada */
    private boolean regreso;

    /* Muestra el cupon vigente correspondiente a la emision del BO */
    private String claveCupon;

    /* indica si la peticion se habita */
    private boolean habilitar;

    private boolean habilito;

    private String referenciaMensaje;

    /* 05/09/12 campo agregado para la firma del iso */
    private String isoFirmado;

    private String referenciaOperacion;

    private Map<String, String> datosAdicionales;

    /** El estado nuevo para la funcionalidad de cambio de estado de las operaciones */
    private String estado;

    /**
     * Se agrego campo BitacoraOperacionesSic para pesistir los cambios de estado en modulo
     * "cambio estado SIC"
     *
     * @author Marco Edgar Valencia, KODE
     */
    private BitacoraOperacionesSic bitacoraOperacionesSic;

    private boolean actualizaCambio;

    private String depositanteLiquidador;
    
    private String liquidacionParcial;

    @Version
    @Column(name="VERSION")
    private Integer version;

    /** Default constructor */
    public OperacionSic() {}

    /** Constructor from the DB View Bean */
    public OperacionSic(final OperacionSicView operSicView) {

        this.setBoveda(new Boveda());
        this.setCatBic(new CatBic());
        this.setCuentaNombrada(new CuentaNombrada());
        this.getCuentaNombrada().setInstitucion(new Institucion());
        this.getCuentaNombrada().getInstitucion().setTipoInstitucion(new TipoInstitucion());
        this.setEmision(new Emision());
        this.getEmision().setEmisora(new Emisora());
        this.getEmision().setInstrumento(new Instrumento());
        this.setErrorSwift(new ErrorSwift());
        this.setEstatusOperacion(new EstatusOperacion());
        this.setSicDetalle(new SicDetalle());

        this.getCatBic().setIdCatbic(operSicView.getPkIdCatBic());
        this.getCuentaNombrada().setIdCuentaNombrada(operSicView.getPkIdCuentaNombrada());
        this.getEmision().setIdEmision(operSicView.getPkIdEmision());
        this.getEmision().getEmisora().setIdEmisora(operSicView.getPkIdEmisora());
        this.getErrorSwift().setIdErrorSwift(operSicView.getPkIdErrorSwift());
        this.getEstatusOperacion().setIdEstatusOperacion(operSicView.getPkIdEstatusOperacion());
        this.getCuentaNombrada().getInstitucion().setIdInstitucion(operSicView.getPkIdInstitucion());
        this.getEmision().getInstrumento().setIdInstrumento(operSicView.getPkIdInstrumento());
        this.getSicDetalle().setIdSicDetalle(operSicView.getPkIdSicDetalle());
        this.getCuentaNombrada().getInstitucion().getTipoInstitucion()
        .setIdTipoInstitucion(operSicView.getPkIdTipoInstitucion());
        this.setIdOperacionesSic(operSicView.getPkIdOperacionesSic());
        this.setFolioControl(operSicView.getFolioControl());
        this.setReferenciaOperacion(operSicView.getReferenciaOperacion());
        this.setFechaAlta(operSicView.getFechaHoraAlta());
        this.setFechaLiquidacionTlp(operSicView.getFechaHoraLiquidacionTlp());
        this.setFechaHoraHabilitada(operSicView.getFechaHoraHabilitada());
        this.getCuentaNombrada().getInstitucion().getTipoInstitucion().setClaveTipoInstitucion(operSicView.getId());
        this.getCuentaNombrada().getInstitucion().setFolioInstitucion(operSicView.getFolio());
        this.getCuentaNombrada().setCuenta(operSicView.getCuenta());
        this.getEmision().getInstrumento().setClaveTipoValor(operSicView.getTipoValor());
        this.getEmision().getEmisora().setClavePizarra(operSicView.getEmisora());
        this.getEmision().setSerie(operSicView.getSerie());
        this.setClaveCupon(operSicView.getCupon());
        this.getEmision().setIsin(operSicView.getIsin());
        this.setCantidadTitulos(operSicView.getCantidadOperada());
        this.setDescTipoMensaje(operSicView.getOperacion());
        this.setFechaOperacion(operSicView.getFechaOperacion());
        this.setFechaLiquidacion(operSicView.getFechaLiquidacion());
        this.setFechaActualizacion(operSicView.getFechaActualizacion());
        this.setDivisa(operSicView.getDivisa());
        this.setImporte(operSicView.getImporte());
        this.setImporteParcial(operSicView.getImporteParcial());
        this.getErrorSwift().setCodigoErrorSwift(operSicView.getError());
        this.getEstatusOperacion().setDescEstatusOperacion(operSicView.getEstatus());
        this.setStPfi548(operSicView.getEstatusPfi());
        this.setRefMsjPfi(operSicView.getDescEstatusPfi());
        this.getCatBic().setDetalleCustodio(operSicView.getDescripcionCustodio());
        this.getCatBic().setBicProd(operSicView.getDescripcionCuenta());
        this.setCuentaContraparte(operSicView.getCuentaContraparte());
        this.setDescContraparte(operSicView.getDescripcionContraparte());
        this.getSicDetalle().setDepLiq(operSicView.getDepositante());
        this.setInstruccEspeciales(operSicView.getInstruccionesEspeciales());
        this.setNomCtaBenef(operSicView.getNombreCuentaBeneficiario());
        this.setNumCtaBenef(operSicView.getNumeroCuentaBeneficiario());
        this.setFecha530(operSicView.getFechaAprobacion());
        this.setMensajeEfectivo(operSicView.getMensajeEfectivo());
        this.setTipoMensajeConfirmacion(operSicView.getMensajeConfirmacion());
        this.setOrigenPfi(operSicView.getOrigenPfi());
        this.getBoveda().setIdBoveda(operSicView.getIdBoveda());
        this.getSicDetalle().setBicDepLiq(operSicView.getBicDepositante());
        this.getErrorSwift().setDescErrorSwift(operSicView.getDescErrorSwift());

        if (null == this.getBoveda() || null == this.getBoveda().getIdBoveda()) {
            this.setBoveda(null);
        }
        if (null == this.getCatBic() || null == this.getCatBic().getIdCatbic()) {
            this.setCatBic(null);
        }
        if (null == this.getCuentaNombrada() || null == this.getCuentaNombrada().getIdCuentaNombrada()) {
            this.setCuentaNombrada(null);
        }
        if (null == this.getCuentaNombrada() || null == this.getCuentaNombrada().getInstitucion()
                || null == this.getCuentaNombrada().getInstitucion().getIdInstitucion()) {
            this.getCuentaNombrada().setInstitucion(null);
        }
        if (null == this.getCuentaNombrada() || null == this.getCuentaNombrada().getInstitucion()
                || null == this.getCuentaNombrada().getInstitucion().getTipoInstitucion()
                || null == this.getCuentaNombrada().getInstitucion().getTipoInstitucion().getIdTipoInstitucion()) {
            this.getCuentaNombrada().getInstitucion().setTipoInstitucion(null);
        }
        if (null == this.getEmision() || null == this.getEmision().getIdEmision()) {
            this.setEmision(null);
        }
        if (null == this.getEmision() || null == this.getEmision().getEmisora()
                || null == this.getEmision().getEmisora().getIdEmisora()) {
            this.getEmision().setEmisora(null);
        }
        if (null == this.getEmision() || null == this.getEmision().getInstrumento()
                || null == this.getEmision().getInstrumento().getIdInstrumento()) {
            this.getEmision().setInstrumento(null);
        }
        if (null == this.getErrorSwift() || null == this.getErrorSwift().getIdErrorSwift()) {
            this.setErrorSwift(null);
        }
        if (null == this.getEstatusOperacion() || null == this.getEstatusOperacion().getIdEstatusOperacion()) {
            this.setEstatusOperacion(null);
        }
        if (null == this.getSicDetalle() || null == this.getSicDetalle().getIdSicDetalle()) {
            this.setSicDetalle(null);
        }

        this.setCambioBoveda(operSicView.getCambioBoveda());
        this.setDepositanteLiquidador(operSicView.getDepositanteLiquidador());
        this.setTipoMensaje(operSicView.getTipoMensaje());
        this.setCantidadTitulosParcial(operSicView.getCantidadOperadaParcial());
        this.setRemanente(operSicView.getRemanente());
        this.setRemanenteEfectivo(operSicView.getRemanenteEfectivo());
        this.setLiquidacionParcial(operSicView.getLiquidacionParcial());
    }

    /**
     * @return Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "id_operaciones_sic", unique = true, nullable = false)
    public Long getIdOperacionesSic() {
        return this.idOperacionesSic;
    }

    /**
     * @return CuentaNombrada
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUENTA_NOMBRADA", nullable = false)
    public CuentaNombrada getCuentaNombrada() {
        return this.cuentaNombrada;
    }

    /**
     * @return String
     */
    @Column(name = "cuenta_recep", unique = false, nullable = false)
    public String getCuentaRecep() {
        return this.cuentaRecep;
    }

    /**
     * @return String
     */
    @Column(name = "tipo_mensaje", unique = false, nullable = false)
    public String getTipoMensaje() {
        return this.tipoMensaje;
    }

    /**
     * @return Emision
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_EMISION", nullable = false)
    public Emision getEmision() {
        return this.emision;
    }

    /**
     * @return Boveda
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA")
    public Boveda getBoveda() {
        return this.boveda;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_notificacion", unique = false, nullable = false)
    public Date getFechaNotificacion() {
        return this.fechaNotificacion;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_liquidacion", unique = false, nullable = false)
    public Date getFechaLiquidacion() {
        return this.fechaLiquidacion;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_operacion", unique = false, nullable = false)
    public Date getFechaOperacion() {
        return this.fechaOperacion;
    }

    /**
     * @return BigInteger
     */
    @Column(name = "cantidad_titulos", unique = false, nullable = false)
    public BigInteger getCantidadTitulos() {
        return this.cantidadTitulos;
    }

    /**
	 * @return the cantidadTitulosParcial
	 */
    @Column(name = "CANTIDAD_TITULOS_PARCIAL")
	public BigInteger getCantidadTitulosParcial() {
		return cantidadTitulosParcial;
	}

	/**
	 * @param cantidadTitulosParcial the cantidadTitulosParcial to set
	 */
	public void setCantidadTitulosParcial(BigInteger cantidadTitulosParcial) {
		this.cantidadTitulosParcial = cantidadTitulosParcial;
	}


	/**
	 * @return the remanente
	 */
	@Column(name = "REMANENTE")
	public BigInteger getRemanente() {
		return remanente;
	}

	/**
	 * @param remanente the remanente to set
	 */
	public void setRemanente(BigInteger remanente) {
		this.remanente = remanente;
	}

	/**
     * @return CatBic
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CATBIC", nullable = false)
    public CatBic getCatBic() {
        return this.catBic;
    }

    /**
     * @return String
     */
    @Column(name = "cuenta_contraparte", unique = false, nullable = false)
    public String getCuentaContraparte() {
        return this.cuentaContraparte;
    }

    /**
     * @return String
     */
    @Column(name = "desc_contraparte", unique = false, nullable = false)
    public String getDescContraparte() {
        return this.descContraparte;
    }

    /**
     * @return SicDetalle
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SIC_DETALLE", nullable = false)
    public SicDetalle getSicDetalle() {
        return this.sicDetalle;
    }

    /**
     * @return String
     */
    @Column(name = "instrucc_especiales", unique = false, nullable = true)
    public String getInstruccEspeciales() {
        return this.instruccEspeciales;
    }

    /**
     * @return String
     */
    @Column(name = "nom_cta_benef", unique = false, nullable = false)
    public String getNomCtaBenef() {
        return this.nomCtaBenef;
    }

    /**
     * @return String
     */
    @Column(name = "num_cta_benef", unique = false, nullable = false)
    public String getNumCtaBenef() {
        return this.numCtaBenef;
    }

    /**
     * @return BigDecimal
     */
    @Column(name = "importe", unique = false, nullable = true)
    public BigDecimal getImporte() {
        return this.importe;
    }
    
    

    /**
	 * @return the importeParcial
	 */
    @Column(name = "IMPORTE_PARCIAL")
	public BigDecimal getImporteParcial() {
		return importeParcial;
	}

	/**
	 * @param importeParcial the importeParcial to set
	 */
	public void setImporteParcial(BigDecimal importeParcial) {
		this.importeParcial = importeParcial;
	}

	/**
	 * @return the remanenteEfectivo
	 */
	@Column(name = "REMANENTE_EFECTIVO")
	public BigDecimal getRemanenteEfectivo() {
		return remanenteEfectivo;
	}

	/**
	 * @param remanenteEfectivo the remanenteEfectivo to set
	 */
	public void setRemanenteEfectivo(BigDecimal remanenteEfectivo) {
		this.remanenteEfectivo = remanenteEfectivo;
	}

	/**
     * @return String
     */
    @Column(name = "divisa", unique = false, nullable = true)
    public String getDivisa() {
        return this.divisa;
    }

    /**
     * @return the errorSwift
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ERROR_SWIFT", nullable = true)
    public ErrorSwift getErrorSwift() {
        return this.errorSwift;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_520", unique = false, nullable = true)
    public Date getFecha520() {
        return this.fecha520;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_530", unique = false, nullable = true)
    public Date getFecha530() {
        return this.fecha530;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_572", unique = false, nullable = true)
    public Date getFecha572() {
        return this.fecha572;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_592", unique = false, nullable = true)
    public Date getFecha592() {
        return this.fecha592;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_hora", unique = false, nullable = true)
    public Date getFechaHora() {
        return this.fechaHora;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_not_592", unique = false, nullable = true)
    public Date getFechaNot592() {
        return this.fechaNot592;
    }

    /**
     * @return String
     */
    @Column(name = "mensaje_efectivo", unique = false, nullable = true)
    public String getMensajeEfectivo() {
        return this.mensajeEfectivo;
    }

    /**
     * @return String
     */
    @Column(name = "ref_msj_pfi", unique = false, nullable = true)
    public String getRefMsjPfi() {
        return this.refMsjPfi;
    }

    /**
     * @return String
     */
    @Column(name = "st_pfi_548", unique = false, nullable = true)
    public String getStPfi548() {
        return this.stPfi548;
    }

    /**
     * @return the estatusOperacion
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTATUS_OPERACION", nullable = false)
    public EstatusOperacion getEstatusOperacion() {
        return this.estatusOperacion;
    }

    /**
     * @return String
     */
    @Column(name = "usuario", unique = false, nullable = true)
    public String getUsuario() {
        return this.usuario;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_alta", unique = false, nullable = false)
    public Date getFechaAlta() {
        return this.fechaAlta;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_cambio", unique = false, nullable = true)
    public Date getFechaCambio() {
        return this.fechaCambio;
    }

    /**
     * @return the tipoMensajeConfirmacion
     */
    // FECHA_EFECTIVO_103
    @Column(name = "tipo_mensaje_confirmacion", unique = false, nullable = true)
    public String getTipoMensajeConfirmacion() {
        return this.tipoMensajeConfirmacion;
    }

    /**
     * @return the fechaEfectivo103
     */
    @Column(name = "fecha_efectivo_103", unique = false, nullable = true)
    public Date getFechaEfectivo103() {
        return this.fechaEfectivo103;
    }

    /**
     * @return BigInteger
     */
    @Column(name = "folio_control", unique = false, nullable = false)
    public BigInteger getFolioControl() {
        return this.folioControl;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_liquidacion_tlp", unique = false, nullable = true)
    public Date getFechaLiquidacionTlp() {
        return this.fechaLiquidacionTlp;
    }

    /**
     * @return Date
     */
    @Column(name = "fecha_hora_habilitada", unique = false, nullable = true)
    public Date getFechaHoraHabilitada() {
        return this.fechaHoraHabilitada;
    }

    /**
     * @return BigInteger
     */
    @Column(name = "origen_pfi", unique = false, nullable = true)
    public BigInteger getOrigenPfi() {
        return this.origenPfi;
    }

    /**
     * @return BigInteger
     */
    @Column(name = "cambio_boveda", unique = false, nullable = true)
    public BigInteger getCambioBoveda() {
        return this.cambioBoveda;
    }
    
	/**
	 * @return the liquidacionParcial
	 */
    @Column(name = "LIQ_PARCIAL")
	public String getLiquidacionParcial() {
		return liquidacionParcial;
	}

    /**
     * @return String
     */
    @Transient
    public String getTipoTraspaso() {
        return this.tipoTraspaso;
    }

    /**
     * @return String
     */
    @Transient
    public String getTipoOperacion() {
        return this.tipoOperacion;
    }

    /**
     * @return Date[]
     */
    @Transient
    public Date[] getFechaConsulta() {
        return this.fechaConsulta;
    }

    /**
     * @return the libera
     */
    @Transient
    public boolean isLibera() {
        return this.libera;
    }

    /**
     * @return the autoriza
     */
    @Transient
    public boolean isAutoriza() {
        return this.autoriza;
    }

    /**
     * @return the conMensaje
     */
    @Transient
    public boolean isConMensaje() {
        return this.conMensaje;
    }

    /**
     * @return the cancela
     */
    @Transient
    public boolean isCancela() {
        return this.cancela;
    }

    /**
     * @return the regresaPosicion
     */
    @Transient
    public boolean isRegresaPosicion() {
        return this.regresaPosicion;
    }

    /**
     * @return the fechaActualizacion
     */
    @Transient
    public Date getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    /**
     * @return the descTipoMensaje
     */
    @Transient
    public String getDescTipoMensaje() {
        return this.descTipoMensaje;
    }

    /**
     * @return the regresoPosicion
     */
    @Transient
    public boolean isRegresoPosicion() {
        return this.regresoPosicion;
    }

    /**
     * @return the libero
     */
    @Transient
    public boolean isLibero() {
        return this.libero;
    }

    /**
     * @return the autorizo
     */
    @Transient
    public boolean isAutorizo() {
        return this.autorizo;
    }

    /**
     * @return the cancelo
     */
    @Transient
    public boolean isCancelo() {
        return this.cancelo;
    }

    @Transient
    public boolean isRegresa() {
        return this.regresa;
    }

    public void setRegresa(final boolean regresa) {
        this.regresa = regresa;
    }

    @Transient
    public boolean isRegreso() {
        return this.regreso;
    }

    public void setRegreso(final boolean regreso) {
        this.regreso = regreso;
    }

    @Transient
    public boolean isAutorizada() {
        return this.autorizada;
    }

    public void setAutorizada(final boolean autorizada) {
        this.autorizada = autorizada;
    }

    /**
     * @return the claveCupon
     */
    @Transient
    public String getClaveCupon() {
        return this.claveCupon;
    }

    @Transient
    public boolean tieneIdFolio() {
        return this.getCuentaNombrada() != null
                && this.getCuentaNombrada().getInstitucion() != null
                && this.getCuentaNombrada().getInstitucion().getTipoInstitucion() != null
                && StringUtils.isNotBlank(this.getCuentaNombrada().getInstitucion().getFolioInstitucion())
                && StringUtils.isNotBlank(this.getCuentaNombrada().getInstitucion().getTipoInstitucion()
                        .getClaveTipoInstitucion());
    }

    /**
     * @return the depositanteLiquidador
     */
    @Transient
    public String getDepositanteLiquidador() {
        return this.depositanteLiquidador;
    }

    /**
     * @param idOperacionesSic
     */
    public void setIdOperacionesSic(final Long idOperacionesSic) {
        this.idOperacionesSic = idOperacionesSic;
    }

    /**
     * @param cuentaNombrada
     */
    public void setCuentaNombrada(final CuentaNombrada cuentaNombrada) {
        this.cuentaNombrada = cuentaNombrada;
    }

    /**
     * @param cuentaRecep
     */
    public void setCuentaRecep(final String cuentaRecep) {
        this.cuentaRecep = cuentaRecep;
    }

    /**
     * @param tipoMensaje
     */
    public void setTipoMensaje(final String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @param emision
     */
    public void setEmision(final Emision emision) {
        this.emision = emision;
    }

    /**
     * @param boveda
     */
    public void setBoveda(final Boveda boveda) {
        this.boveda = boveda;
    }

    /**
     * @param fechaNotificacion
     */
    public void setFechaNotificacion(final Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    /**
     * @param fechaLiquidacion
     */
    public void setFechaLiquidacion(final Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @param fechaOperacion
     */
    public void setFechaOperacion(final Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @param cantidadTitulos
     */
    public void setCantidadTitulos(final BigInteger cantidadTitulos) {
        this.cantidadTitulos = cantidadTitulos;
    }

    /**
     * @param catBic
     */
    public void setCatBic(final CatBic catBic) {
        this.catBic = catBic;
    }

    /**
     * @param cuentaContraparte
     */
    public void setCuentaContraparte(final String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    /**
     * @param descContraparte
     */
    public void setDescContraparte(final String descContraparte) {
        this.descContraparte = descContraparte;
    }

    /**
     * @param sicDetalle
     */
    public void setSicDetalle(final SicDetalle sicDetalle) {
        this.sicDetalle = sicDetalle;
    }

    /**
     * @param instruccEspeciales
     */
    public void setInstruccEspeciales(final String instruccEspeciales) {
        this.instruccEspeciales = instruccEspeciales;
    }

    /**
     * @param nomCtaBenef
     */
    public void setNomCtaBenef(final String nomCtaBenef) {
        this.nomCtaBenef = nomCtaBenef;
    }

    /**
     * @param numCtaBenef
     */
    public void setNumCtaBenef(final String numCtaBenef) {
        this.numCtaBenef = numCtaBenef;
    }

    /**
     * @param importe
     */
    public void setImporte(final BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @param divisa
     */
    public void setDivisa(final String divisa) {
        this.divisa = divisa;
    }

    /**
     * @param errorSwift the errorSwift to set
     */
    public void setErrorSwift(final ErrorSwift errorSwift) {
        this.errorSwift = errorSwift;
    }

    /**
     * @param fecha520
     */
    public void setFecha520(final Date fecha520) {
        this.fecha520 = fecha520;
    }

    /**
     * @param fecha530
     */
    public void setFecha530(final Date fecha530) {
        this.fecha530 = fecha530;
    }

    /**
     * @param fecha572
     */
    public void setFecha572(final Date fecha572) {
        this.fecha572 = fecha572;
    }

    /**
     * @param fecha592
     */
    public void setFecha592(final Date fecha592) {
        this.fecha592 = fecha592;
    }

    /**
     * @param fechaHora
     */
    public void setFechaHora(final Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @param fechaNot592
     */
    public void setFechaNot592(final Date fechaNot592) {
        this.fechaNot592 = fechaNot592;
    }

    /**
     * @param mensajeEfectivo
     */
    public void setMensajeEfectivo(final String mensajeEfectivo) {
        this.mensajeEfectivo = mensajeEfectivo;
    }

    /**
     * @param refMsjPfi
     */
    public void setRefMsjPfi(final String refMsjPfi) {
        this.refMsjPfi = refMsjPfi;
    }

    /**
     * @param stPfi548
     */
    public void setStPfi548(final String stPfi548) {
        this.stPfi548 = stPfi548;
    }

    /**
     * @param estatusOperacion the estatusOperacion to set
     */
    public void setEstatusOperacion(final EstatusOperacion estatusOperacion) {
        this.estatusOperacion = estatusOperacion;
    }

    /**
     * @param usuario
     */
    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param fechaAlta
     */
    public void setFechaAlta(final Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    /**
     * @param fechaCambio
     */
    public void setFechaCambio(final Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    /**
     * @param tipoMensajeConfirmacion the tipoMensajeConfirmacion to set
     */
    public void setTipoMensajeConfirmacion(final String tipoMensajeConfirmacion) {
        this.tipoMensajeConfirmacion = tipoMensajeConfirmacion;
    }

    /**
     * @param fechaEfectivo103 the fechaEfectivo103 to set
     */
    public void setFechaEfectivo103(final Date fechaEfectivo103) {
        this.fechaEfectivo103 = fechaEfectivo103;
    }

    /**
     * @param folioControl
     */
    public void setFolioControl(final BigInteger folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @param
     */
    public void setFechaLiquidacionTlp(final Date fechaLiquidacionTlp) {
        this.fechaLiquidacionTlp = fechaLiquidacionTlp;
    }

    /**
     * @param
     */
    public void setFechaHoraHabilitada(final Date fechaHoraHabilitada) {
        this.fechaHoraHabilitada = fechaHoraHabilitada;
    }

    /**
     * @param
     */
    public void setOrigenPfi(final BigInteger origenPfi) {
        this.origenPfi = origenPfi;
    }

    public void setCambioBoveda(BigInteger cambioBoveda) {
        this.cambioBoveda = cambioBoveda;
    }

    /**
     * @param tipoTraspaso
     */
    public void setTipoTraspaso(final String tipoTraspaso) {
        this.tipoTraspaso = tipoTraspaso;
    }

    /**
     * @param tipoOperacion
     */
    public void setTipoOperacion(final String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @param fechaConsulta the fechaConsulta to set
     */
    public void setFechaConsulta(final Date[] fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    /**
     * @param libera the libera to set
     */
    public void setLibera(final boolean libera) {
        this.libera = libera;
    }

    /**
     * @param autoriza the autoriza to set
     */
    public void setAutoriza(final boolean autoriza) {
        this.autoriza = autoriza;
    }

    /**
     * @param conMensaje the conMensaje to set
     */
    public void setConMensaje(final boolean conMensaje) {
        this.conMensaje = conMensaje;
    }

    /**
     * @param cancela the cancela to set
     */
    public void setCancela(final boolean cancela) {
        this.cancela = cancela;
    }

    /**
     * @param regresaPosicion the regresaPosicion to set
     */
    public void setRegresaPosicion(final boolean regresaPosicion) {
        this.regresaPosicion = regresaPosicion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(final Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @param descTipoMensaje the descTipoMensaje to set
     */
    public void setDescTipoMensaje(final String descTipoMensaje) {
        this.descTipoMensaje = descTipoMensaje;
    }

    /**
     * @param regresoPosicion the regresoPosicion to set
     */
    public void setRegresoPosicion(final boolean regresoPosicion) {
        this.regresoPosicion = regresoPosicion;
    }

    /**
     * @param libero the libero to set
     */
    public void setLibero(final boolean libero) {
        this.libero = libero;
    }

    /**
     * @param autorizo the autorizo to set
     */
    public void setAutorizo(final boolean autorizo) {
        this.autorizo = autorizo;
    }

    /**
     * @param cancelo the cancelo to set
     */
    public void setCancelo(final boolean cancelo) {
        this.cancelo = cancelo;
    }

    /**
     * @param claveCupon the claveCupon to set
     */
    public void setClaveCupon(final String claveCupon) {
        this.claveCupon = claveCupon;
    }

    /**
     * @return the confirma
     */
    @Transient
    public boolean isConfirma() {
        return this.confirma;
    }

    /**
     * @param confirma the confirma to set
     */
    public void setConfirma(final boolean confirma) {
        this.confirma = confirma;
    }

    /**
     * @return the confirmo
     */
    @Transient
    public boolean isConfirmo() {
        return this.confirmo;
    }

    /**
     * @param confirmo the confirmo to set
     */
    public void setConfirmo(final boolean confirmo) {
        this.confirmo = confirmo;
    }

    @Transient
    public boolean isHabilitar() {
        return this.habilitar;
    }

    public void setHabilitar(final boolean habilitar) {
        this.habilitar = habilitar;
    }

    @Transient
    public boolean isHabilito() {
        return this.habilito;
    }

    public void setHabilito(final boolean habilito) {
        this.habilito = habilito;
    }

    @Transient
    public String getReferenciaMensaje() {
        return this.referenciaMensaje;
    }

    public void setReferenciaMensaje(final String referenciaMensaje) {
        this.referenciaMensaje = referenciaMensaje;
    }

    @Transient
    public String getIsoFirmado() {
        return this.isoFirmado;
    }

    public void setIsoFirmado(final String isoFirmado) {
        this.isoFirmado = isoFirmado;
    }

    @Transient
    public Map<String, String> getDatosAdicionales() {
        return this.datosAdicionales;
    }

    public void setDatosAdicionales(final Map<String, String> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }


    @Column(name = "REFERENCIA_OPERACION", unique = false, nullable = true)
    public String getReferenciaOperacion() {
        return this.referenciaOperacion;
    }

    public void setReferenciaOperacion(final String referenciaOperacion) {
        this.referenciaOperacion = referenciaOperacion;
    }

    @Transient
    public String getEstado() {
        return this.estado;
    }

    public void setEstado(final String estado) {
        this.estado = estado;
    }

    @Transient
    public BitacoraOperacionesSic getBitacoraOperacionesSic() {
        return this.bitacoraOperacionesSic;
    }

    public void setBitacoraOperacionesSic(final BitacoraOperacionesSic bitacoraOperacionesSic) {
        this.bitacoraOperacionesSic = bitacoraOperacionesSic;
    }

    /**
     * @return the actualizaCambio
     */
    @Transient
    public boolean isActualizaCambio() {
        return this.actualizaCambio;
    }

    /**
     * @param actualizaCambio the actualizaCambio to set
     */
    public void setActualizaCambio(final boolean actualizaCambio) {
        this.actualizaCambio = actualizaCambio;
    }

    public void setDepositanteLiquidador(String depositanteLiquidador) {
        this.depositanteLiquidador = depositanteLiquidador;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	/**
	 * @param liquidacionParcial the liquidacionParcial to set
	 */
	public void setLiquidacionParcial(String liquidacionParcial) {
		this.liquidacionParcial = liquidacionParcial;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OperacionSic [idOperacionesSic=" + idOperacionesSic + ", cuentaNombrada=" + cuentaNombrada
				+ ", cuentaRecep=" + cuentaRecep + ", tipoMensaje=" + tipoMensaje + ", emision=" + emision + ", boveda="
				+ boveda + ", fechaNotificacion=" + fechaNotificacion + ", fechaLiquidacion=" + fechaLiquidacion
				+ ", fechaOperacion=" + fechaOperacion + ", cantidadTitulos=" + cantidadTitulos
				+ ", cantidadTitulosParcial=" + cantidadTitulosParcial + ", remanente=" + remanente + ", catBic="
				+ catBic + ", cuentaContraparte=" + cuentaContraparte + ", descContraparte=" + descContraparte
				+ ", sicDetalle=" + sicDetalle + ", instruccEspeciales=" + instruccEspeciales + ", nomCtaBenef="
				+ nomCtaBenef + ", numCtaBenef=" + numCtaBenef + ", importe=" + importe + ", importeParcial="
				+ importeParcial + ", remanenteEfectivo=" + remanenteEfectivo + ", divisa=" + divisa + ", errorSwift="
				+ errorSwift + ", fecha520=" + fecha520 + ", fecha530=" + fecha530 + ", fecha572=" + fecha572
				+ ", fecha592=" + fecha592 + ", fechaHora=" + fechaHora + ", fechaNot592=" + fechaNot592
				+ ", mensajeEfectivo=" + mensajeEfectivo + ", refMsjPfi=" + refMsjPfi + ", stPfi548=" + stPfi548
				+ ", estatusOperacion=" + estatusOperacion + ", usuario=" + usuario + ", fechaAlta=" + fechaAlta
				+ ", fechaCambio=" + fechaCambio + ", tipoMensajeConfirmacion=" + tipoMensajeConfirmacion
				+ ", fechaEfectivo103=" + fechaEfectivo103 + ", folioControl=" + folioControl + ", fechaLiquidacionTlp="
				+ fechaLiquidacionTlp + ", fechaHoraHabilitada=" + fechaHoraHabilitada + ", origenPfi=" + origenPfi
				+ ", cambioBoveda=" + cambioBoveda + ", tipoTraspaso=" + tipoTraspaso + ", tipoOperacion="
				+ tipoOperacion + ", fechaConsulta=" + Arrays.toString(fechaConsulta) + ", libera=" + libera
				+ ", confirma=" + confirma + ", autoriza=" + autoriza + ", conMensaje=" + conMensaje + ", cancela="
				+ cancela + ", regresa=" + regresa + ", autorizada=" + autorizada + ", regresaPosicion="
				+ regresaPosicion + ", fechaActualizacion=" + fechaActualizacion + ", descTipoMensaje="
				+ descTipoMensaje + ", regresoPosicion=" + regresoPosicion + ", libero=" + libero + ", confirmo="
				+ confirmo + ", autorizo=" + autorizo + ", cancelo=" + cancelo + ", regreso=" + regreso
				+ ", claveCupon=" + claveCupon + ", habilitar=" + habilitar + ", habilito=" + habilito
				+ ", referenciaMensaje=" + referenciaMensaje + ", isoFirmado=" + isoFirmado + ", referenciaOperacion="
				+ referenciaOperacion + ", datosAdicionales=" + datosAdicionales + ", estado=" + estado
				+ ", bitacoraOperacionesSic=" + bitacoraOperacionesSic + ", actualizaCambio=" + actualizaCambio
				+ ", depositanteLiquidador=" + depositanteLiquidador + ", liquidacionParcial=" + liquidacionParcial
				+ ", version=" + version + "]";
	}

}
