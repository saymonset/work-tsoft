package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** DALI_ADMIN V_OPERACIONES_SIC View Bean. */
@Entity
@Table(name = "V_OPERACIONES_SIC")
public class OperacionSicView implements Serializable {

    /** Default Serial Version UID */
    private static final long serialVersionUID = 1L;

    /** C_CATBIC.ID_CATBIC as PK_ID_CATBIC */
    private Long pkIdCatBic;

    /** C_CUENTA_NOMBRADA.ID_CUENTA_NOMBRADA as PK_ID_CUENTA_NOMBRADA */
    private Long pkIdCuentaNombrada;

    /** C_CUPON.ID_CUPON as PK_ID_CUPON */
    private Long pkIdCupon;

    /** C_EMISION.ID_EMISION as PK_ID_EMISION */
    private Long pkIdEmision;

    /** C_EMISORA.ID_EMISORA as PK_ID_EMISORA */
    private Long pkIdEmisora;

    /** C_ERRORES_SWIFT.ID_ERROR_SWIFT as PK_ID_ERROR_SWIFT */
    private Long pkIdErrorSwift;

    /** C_ESTATUS_OPERACIONES.ID_ESTATUS_OPERACION as PK_ID_ESTATUS_OPERACION */
    private Long pkIdEstatusOperacion;

    /** C_INSTITUCION.ID_INSTITUCION as PK_ID_INSTITUCION */
    private Long pkIdInstitucion;

    /** C_INSTRUMENTO.ID_INSTRUMENTO as PK_ID_INSTRUMENTO */
    private Long pkIdInstrumento;

    /** C_SIC_DETALLE.ID_SIC_DETALLE as PK_ID_SIC_DETALLE */
    private Long pkIdSicDetalle;

    /** C_TIPO_INSTITUCION.ID_TIPO_INSTITUCION as PK_ID_TIPO_INSTITUCION */
    private Long pkIdTipoInstitucion;

    /** T_OPERACIONES_SIC.ID_OPERACIONES_SIC as PK_ID_OPERACIONES_SIC */
    private Long pkIdOperacionesSic;

    /** T_OPERACIONES_SIC.FOLIO_CONTROL as FOLIO_CONTROL */
    private BigInteger folioControl;

    /** T_OPERACIONES_SIC.REFERENCIA_OPERACION as REFERENCIA_OPERACION */
    private String referenciaOperacion;

    /** T_OPERACIONES_SIC.FECHA_ALTA as FECHA_HORA_ALTA */
    private Date fechaHoraAlta;

    /** T_OPERACIONES_SIC.FECHA_LIQUIDACION_TLP as FECHA_HORA_LIQUIDACION_TLP */
    private Date fechaHoraLiquidacionTlp;

    /** T_OPERACIONES_SIC.FECHA_HORA_HABILITADA as FECHA_HORA_HABILITADA */
    private Date fechaHoraHabilitada;

    /** C_TIPO_INSTITUCION.CLAVE_TIPO_INSTITUCION as ID */
    private String id;

    /** C_INSTITUCION.FOLIO_INSTITUCION as FOLIO */
    private String folio;

    /** C_CUENTA_NOMBRADA.CUENTA as CUENTA */
    private String cuenta;

    /** C_INSTRUMENTO.CLAVE_TIPO_VALOR as TIPO_VALOR */
    private String tipoValor;

    /** C_EMISORA.CLAVE_PIZARRA as EMISORA */
    private String emisora;

    /** C_EMISION.SERIE as SERIE */
    private String serie;

    /** C_CUPON.CLAVE_CUPON as CUPON */
    private String cupon;

    /** C_EMISION.ISIN as ISIN */
    private String isin;

    /** T_OPERACIONES_SIC.CANTIDAD_TITULOS as CANTIDAD_OPERADA */
    private BigInteger cantidadOperada;
    
    /** T_OPERACIONES_SIC.CANTIDAD_TITULOS as CANTIDAD_OPERADA_PARCIAL */
    private BigInteger cantidadOperadaParcial;
    
    /** T_OPERACIONES_SIC.CANTIDAD_TITULOS as REMANENTE */
    private BigInteger remanente;
    
    /**
     * OPERACION
     *
     * WHEN TOP.TIPO_MENSAJE = 543 THEN 'ENTREGA CONTRA PAGO'
     *
     * WHEN TOP.TIPO_MENSAJE = 542 THEN 'ENTREGA LIBRE PAGO'
     *
     * WHEN TOP.TIPO_MENSAJE = 541 THEN 'RECEPCION CONTRA PAGO'
     *
     * WHEN TOP.TIPO_MENSAJE = 540 THEN 'RECEPCION LIBRE PAGO'
     */
    private String operacion;
    
    /**
     * T_OPERACIONES_SIC.TIPO_MENSAJE as TIPO_MENSAJE
     */
    private String tipoMensaje;

    /** T_OPERACIONES_SIC.FECHA_OPERACION as FECHA_OPERACION */
    private Date fechaOperacion;

    /** T_OPERACIONES_SIC.FECHA_LIQUIDACION as FECHA_LIQUIDACION */
    private Date fechaLiquidacion;

    /**
     * FECHA_ACTUALIZACION
     *
     * WHEN TOP.ID_ESTATUS_OPERACION= 1 OR TOP.ID_ESTATUS_OPERACION = 2 OR TOP.ID_ESTATUS_OPERACION
     * = 3 THEN TOP.FECHA_NOTIFICACION
     *
     * WHEN TOP.ID_ESTATUS_OPERACION= 4 THEN TOP.FECHA_520
     *
     * WHEN TOP.ID_ESTATUS_OPERACION= 5 THEN TOP.FECHA_530
     *
     * WHEN TOP.ID_ESTATUS_OPERACION= 10 OR TOP.ID_ESTATUS_OPERACION = 11 OR
     * TOP.ID_ESTATUS_OPERACION = 14 THEN TOP.FECHA_592
     *
     * WHEN TOP.ID_ESTATUS_OPERACION= 6 OR TOP.ID_ESTATUS_OPERACION = 7 OR TOP.ID_ESTATUS_OPERACION
     * = 8 OR TOP.ID_ESTATUS_OPERACION = 9 THEN TOP.FECHA_HORA
     */
    private Date fechaActualizacion;

    /** T_OPERACIONES_SIC.DIVISA as DIVISA */
    private String divisa;

    /** T_OPERACIONES_SIC.IMPORTE as IMPORTE */
    private BigDecimal importe;
    
    /** T_OPERACIONES_SIC.IMPORTE as IMPORTE */
    private BigDecimal importeParcial;
    
    /** T_OPERACIONES_SIC.CANTIDAD_TITULOS as REMANENTE_EFECTIVO */
    private BigDecimal remanenteEfectivo;

    /** C_ERRORES_SWIFT.CODIGO_ERROR_SWIFT as ERROR */
    private String error;

    /** C_ESTATUS_OPERACIONES.DESC_ESTATUS_OPERACION as ESTATUS */
    private String estatus;

    /** T_OPERACIONES_SIC.ST_PFI_548 as ESTATUS_PFI */
    private String estatusPfi;

    /** T_OPERACIONES_SIC.REF_MSJ_PFI as DESC_ESTATUS_PFI */
    private String descEstatusPfi;

    /** C_CATBIC.DETALLE_CUSTODIO as DESCRIPCION_CUSTODIO */
    private String descripcionCustodio;

    /** C_CATBIC.BIC_PROD as DESCRIPCION_CUENTA */
    private String descripcionCuenta;

    /** T_OPERACIONES_SIC.CUENTA_CONTRAPARTE as CUENTA_CONTRAPARTE */
    private String cuentaContraparte;

    /** T_OPERACIONES_SIC.DESC_CONTRAPARTE as DESCRIPCION_CONTRAPARTE */
    private String descripcionContraparte;

    /** C_SIC_DETALLE.DEP_LIQ as DEPOSITANTE */
    private String depositante;

    /** T_OPERACIONES_SIC.INSTRUCC_ESPECIALES as INSTRUCCIONES_ESPECIALES */
    private String instruccionesEspeciales;

    /** T_OPERACIONES_SIC.NOM_CTA_BENEF as NOMBRE_CUENTA_BENEFICIARIO */
    private String nombreCuentaBeneficiario;

    /** T_OPERACIONES_SIC.NUM_CTA_BENEF as NUMERO_CUENTA_BENEFICIARIO */
    private String numeroCuentaBeneficiario;

    /** T_OPERACIONES_SIC.FECHA_530 as FECHA_APROBACION */
    private Date fechaAprobacion;

    /** T_OPERACIONES_SIC.MENSAJE_EFECTIVO as MENSAJE_EFECTIVO */
    private String mensajeEfectivo;

    /** T_OPERACIONES_SIC.TIPO_MENSAJE_CONFIRMACION as MENSAJE_CONFIRMACION */
    private String mensajeConfirmacion;

    /** T_OPERACIONES_SIC.ORIGEN_PFI as ORIGEN_PFI */
    private BigInteger origenPfi;

    /** T_OPERACIONES_SIC.ID_BOVEDA as ID_BOVEDA */
    private Long idBoveda;

    /** C_SIC_DETALLE.BIC_DEP_LIQ as BIC_DEPOSITANTE */
    private String bicDepositante;

    /** ERR.DESC_ERROR_SWIFT as DESC_ERROR_SWIFT */
    private String descErrorSwift;

    /** TOP.CAMBIO_BOVEDA as CAMBIO_BOVEDA */
    private BigInteger cambioBoveda;

    /** SDE.DEP_LIQ as DEPOSITANTE_LIQUIDADOR */
    private String depositanteLiquidador;
    
    /** LIQ_PARCIAL */
    private String liquidacionParcial;

    /** @return this.pkIdCatBic */
    @Column(name = "PK_ID_CATBIC")
    public Long getPkIdCatBic() {
        return this.pkIdCatBic;
    }

    /** @param pkIdCatBic to be set in this.pkIdCatBic */
    public void setPkIdCatBic(final Long pkIdCatBic) {

        if (null != pkIdCatBic) {
            this.pkIdCatBic = pkIdCatBic;
        }

    }

    /** @return this.pkIdCuentaNombrada */
    @Column(name = "PK_ID_CUENTA_NOMBRADA")
    public Long getPkIdCuentaNombrada() {
        return this.pkIdCuentaNombrada;
    }

    /** @param pkIdCuentaNombrada to be set in this.pkIdCuentaNombrada */
    public void setPkIdCuentaNombrada(final Long pkIdCuentaNombrada) {

        if (null != pkIdCuentaNombrada) {
            this.pkIdCuentaNombrada = pkIdCuentaNombrada;
        }

    }

    /** @return this.pkIdCupon */
    @Column(name = "PK_ID_CUPON")
    public Long getPkIdCupon() {
        return this.pkIdCupon;
    }

    /** @param pkIdCupon to be set in this.pkIdCupon */
    public void setPkIdCupon(final Long pkIdCupon) {

        if (null != pkIdCupon) {
            this.pkIdCupon = pkIdCupon;
        }

    }

    /** @return this.pkIdEmision */
    @Column(name = "PK_ID_EMISION")
    public Long getPkIdEmision() {
        return this.pkIdEmision;
    }

    /** @param pkIdEmision to be set in this.pkIdEmision */
    public void setPkIdEmision(final Long pkIdEmision) {

        if (null != pkIdEmision) {
            this.pkIdEmision = pkIdEmision;
        }

    }

    /** @return this.pkIdEmisora */
    @Column(name = "PK_ID_EMISORA")
    public Long getPkIdEmisora() {
        return this.pkIdEmisora;
    }

    /** @param pkIdEmisora to be set in this.pkIdEmisora */
    public void setPkIdEmisora(final Long pkIdEmisora) {

        if (null != pkIdEmisora) {
            this.pkIdEmisora = pkIdEmisora;
        }

    }

    /** @return this.pkIdErrorSwift */
    @Column(name = "PK_ID_ERROR_SWIFT")
    public Long getPkIdErrorSwift() {
        return this.pkIdErrorSwift;
    }

    /** @param pkIdErrorSwift to be set in this.pkIdErrorSwift */
    public void setPkIdErrorSwift(final Long pkIdErrorSwift) {

        if (null != pkIdErrorSwift) {
            this.pkIdErrorSwift = pkIdErrorSwift;
        }

    }

    /** @return this.pkIdEstatusOperacion */
    @Column(name = "PK_ID_ESTATUS_OPERACION")
    public Long getPkIdEstatusOperacion() {
        return this.pkIdEstatusOperacion;
    }

    /** @param pkIdEstatusOperacion to be set in this.pkIdEstatusOperacion */
    public void setPkIdEstatusOperacion(final Long pkIdEstatusOperacion) {

        if (null != pkIdEstatusOperacion) {
            this.pkIdEstatusOperacion = pkIdEstatusOperacion;
        }

    }

    /** @return this.pkIdInstitucion */
    @Column(name = "PK_ID_INSTITUCION")
    public Long getPkIdInstitucion() {
        return this.pkIdInstitucion;
    }

    /** @param pkIdInstitucion to be set in this.pkIdInstitucion */
    public void setPkIdInstitucion(final Long pkIdInstitucion) {

        if (null != pkIdInstitucion) {
            this.pkIdInstitucion = pkIdInstitucion;
        }

    }

    /** @return this.pkIdInstrumento */
    @Column(name = "PK_ID_INSTRUMENTO")
    public Long getPkIdInstrumento() {
        return this.pkIdInstrumento;
    }

    /** @param pkIdInstrumento to be set in this.pkIdInstrumento */
    public void setPkIdInstrumento(final Long pkIdInstrumento) {

        if (null != pkIdInstrumento) {
            this.pkIdInstrumento = pkIdInstrumento;
        }

    }

    /** @return this.pkIdSicDetalle */
    @Column(name = "PK_ID_SIC_DETALLE")
    public Long getPkIdSicDetalle() {
        return this.pkIdSicDetalle;
    }

    /** @param pkIdSicDetalle to be set in this.pkIdSicDetalle */
    public void setPkIdSicDetalle(final Long pkIdSicDetalle) {

        if (null != pkIdSicDetalle) {
            this.pkIdSicDetalle = pkIdSicDetalle;
        }

    }

    /** @return this.pkIdTipoInstitucion */
    @Column(name = "PK_ID_TIPO_INSTITUCION")
    public Long getPkIdTipoInstitucion() {
        return this.pkIdTipoInstitucion;
    }

    /** @param pkIdTipoInstitucion to be set in this.pkIdTipoInstitucion */
    public void setPkIdTipoInstitucion(final Long pkIdTipoInstitucion) {

        if (null != pkIdTipoInstitucion) {
            this.pkIdTipoInstitucion = pkIdTipoInstitucion;
        }

    }

    /** @return this.pkIdOperacionesSic */
    @Id
    @Column(name = "PK_ID_OPERACIONES_SIC")
    public Long getPkIdOperacionesSic() {
        return this.pkIdOperacionesSic;
    }

    /** @param pkIdOperacionesSic to be set in this.pkIdOperacionesSic */
    public void setPkIdOperacionesSic(final Long pkIdOperacionesSic) {

        if (null != pkIdOperacionesSic) {
            this.pkIdOperacionesSic = pkIdOperacionesSic;
        }

    }

    /** @return this.folioControl */
    @Column(name = "FOLIO_CONTROL")
    public BigInteger getFolioControl() {
        return this.folioControl;
    }

    /** @param folioControl to be set in this.folioControl */
    public void setFolioControl(final BigInteger folioControl) {

        if (null != folioControl) {
            this.folioControl = folioControl;
        }

    }

    /** @return this.referenciaOperacion */
    @Column(name = "REFERENCIA_OPERACION")
    public String getReferenciaOperacion() {
        return this.referenciaOperacion;
    }

    /** @param referenciaOperacion to be set in this.referenciaOperacion */
    public void setReferenciaOperacion(final String referenciaOperacion) {

        if (null != referenciaOperacion) {
            this.referenciaOperacion = referenciaOperacion;
        }

    }

    /** @return this.fechaHoraAlta */
    @Column(name = "FECHA_HORA_ALTA")
    public Date getFechaHoraAlta() {

        Date fechaHoraAlta = null;
        if (null != this.fechaHoraAlta) {
            fechaHoraAlta = new Date(this.fechaHoraAlta.getTime());
        }
        return fechaHoraAlta;

    }

    /** @param fechaHoraAlta to be set in this.fechaHoraAlta */
    public void setFechaHoraAlta(final Date fechaHoraAlta) {

        if (null != fechaHoraAlta) {
            this.fechaHoraAlta = new Date(fechaHoraAlta.getTime());
        }

    }

    /** @return this.fechaHoraLiquidacionTlp */
    @Column(name = "FECHA_HORA_LIQUIDACION_TLP")
    public Date getFechaHoraLiquidacionTlp() {

        Date fechaHoraLiquidacionTlp = null;
        if (null != this.fechaHoraLiquidacionTlp) {
            fechaHoraLiquidacionTlp = new Date(this.fechaHoraLiquidacionTlp.getTime());
        }
        return fechaHoraLiquidacionTlp;

    }

    /** @param fechaHoraLiquidacionTlp to be set in this.fechaHoraLiquidacionTlp */
    public void setFechaHoraLiquidacionTlp(final Date fechaHoraLiquidacionTlp) {

        if (null != fechaHoraLiquidacionTlp) {
            this.fechaHoraLiquidacionTlp = new Date(fechaHoraLiquidacionTlp.getTime());
        }

    }

    /** @return this.fechaHoraHabilitada */
    @Column(name = "FECHA_HORA_HABILITADA")
    public Date getFechaHoraHabilitada() {

        Date fechaHoraHabilitada = null;
        if (null != this.fechaHoraHabilitada) {
            fechaHoraHabilitada = new Date(this.fechaHoraHabilitada.getTime());
        }
        return fechaHoraHabilitada;

    }

    /** @param fechaHoraHabilitada to be set in this.fechaHoraHabilitada */
    public void setFechaHoraHabilitada(final Date fechaHoraHabilitada) {

        if (null != fechaHoraHabilitada) {
            this.fechaHoraHabilitada = new Date(fechaHoraHabilitada.getTime());
        }

    }

    /** @return this.id */
    @Column(name = "ID")
    public String getId() {
        return this.id;
    }

    /** @param id to be set in this.id */
    public void setId(final String id) {

        if (null != id) {
            this.id = id;
        }

    }

    /** @return this.folio */
    @Column(name = "FOLIO")
    public String getFolio() {
        return this.folio;
    }

    /** @param folio to be set in this.folio */
    public void setFolio(final String folio) {

        if (null != folio) {
            this.folio = folio;
        }

    }

    /** @return this.cuenta */
    @Column(name = "CUENTA")
    public String getCuenta() {
        return this.cuenta;
    }

    /** @param cuenta to be set in this.cuenta */
    public void setCuenta(final String cuenta) {

        if (null != cuenta) {
            this.cuenta = cuenta;
        }

    }

    /** @return this.tipoValor */
    @Column(name = "TIPO_VALOR")
    public String getTipoValor() {
        return this.tipoValor;
    }

    /** @param tipoValor to be set in this.tipoValor */
    public void setTipoValor(final String tipoValor) {

        if (null != tipoValor) {
            this.tipoValor = tipoValor;
        }

    }

    /** @return this.emisora */
    @Column(name = "EMISORA")
    public String getEmisora() {
        return this.emisora;
    }

    /** @param emisora to be set in this.emisora */
    public void setEmisora(final String emisora) {

        if (null != emisora) {
            this.emisora = emisora;
        }

    }

    /** @return this.serie */
    @Column(name = "SERIE")
    public String getSerie() {
        return this.serie;
    }

    /** @param serie to be set in this.serie */
    public void setSerie(final String serie) {

        if (null != serie) {
            this.serie = serie;
        }

    }

    /** @return this.cupon */
    @Column(name = "CUPON")
    public String getCupon() {
        return this.cupon;
    }

    /** @param cupon to be set in this.cupon */
    public void setCupon(final String cupon) {

        if (null != cupon) {
            this.cupon = cupon;
        }

    }

    /** @return this.isin */
    @Column(name = "ISIN")
    public String getIsin() {
        return this.isin;
    }

    /** @param isin to be set in this.isin */
    public void setIsin(final String isin) {

        if (null != isin) {
            this.isin = isin;
        }

    }

    /** @return this.cantidadOperada */
    @Column(name = "CANTIDAD_OPERADA")
    public BigInteger getCantidadOperada() {
        return this.cantidadOperada;
    }

    /** @param cantidadOperada to be set in this.cantidadOperada */
    public void setCantidadOperada(final BigInteger cantidadOperada) {

        if (null != cantidadOperada) {
            this.cantidadOperada = cantidadOperada;
        }

    }

    /**
	 * @return the cantidadOperadaParcial
	 */
    @Column(name = "CANTIDAD_OPERADA_PARCIAL")
	public BigInteger getCantidadOperadaParcial() {
		return cantidadOperadaParcial;
	}

	/**
	 * @param cantidadOperadaParcial the cantidadOperadaParcial to set
	 */
	public void setCantidadOperadaParcial(BigInteger cantidadOperadaParcial) {
		
        if (null != cantidadOperadaParcial) {
        	this.cantidadOperadaParcial = cantidadOperadaParcial;
        }
		
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
        if (null != remanente) {
        	this.remanente = remanente;
        }
	}

	/** @return this.operacion */
    @Column(name = "OPERACION")
    public String getOperacion() {
        return this.operacion;
    }

    /** @param operacion to be set in this.operacion */
    public void setOperacion(final String operacion) {

        if (null != operacion) {
            this.operacion = operacion;
        }

    }

    /**
	 * @return the tipoMensaje
	 */
    @Column(name = "TIPO_MENSAJE")
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
        if (null != tipoMensaje) {
            this.tipoMensaje = tipoMensaje;
        }
	}

	/** @return this.fechaOperacion */
    @Column(name = "FECHA_OPERACION")
    public Date getFechaOperacion() {

        Date fechaOperacion = null;
        if (null != this.fechaOperacion) {
            fechaOperacion = new Date(this.fechaOperacion.getTime());
        }
        return fechaOperacion;

    }

    /** @param fechaOperacion to be set in this.fechaOperacion */
    public void setFechaOperacion(final Date fechaOperacion) {

        if (null != fechaOperacion) {
            this.fechaOperacion = new Date(fechaOperacion.getTime());
        }

    }

    /** @return this.fechaLiquidacion */
    @Column(name = "FECHA_LIQUIDACION")
    public Date getFechaLiquidacion() {

        Date fechaLiquidacion = null;
        if (null != this.fechaLiquidacion) {
            fechaLiquidacion = new Date(this.fechaLiquidacion.getTime());
        }
        return fechaLiquidacion;

    }

    /** @param fechaLiquidacion to be set in this.fechaLiquidacion */
    public void setFechaLiquidacion(final Date fechaLiquidacion) {

        if (null != fechaLiquidacion) {
            this.fechaLiquidacion = new Date(fechaLiquidacion.getTime());
        }

    }

    /** @return this.fechaActualizacion */
    @Column(name = "FECHA_ACTUALIZACION")
    public Date getFechaActualizacion() {

        Date fechaActualizacion = null;
        if (null != this.fechaActualizacion) {
            fechaActualizacion = new Date(this.fechaActualizacion.getTime());
        }
        return fechaActualizacion;

    }

    /** @param fechaActualizacion to be set in this.fechaActualizacion */
    public void setFechaActualizacion(final Date fechaActualizacion) {

        if (null != fechaActualizacion) {
            this.fechaActualizacion = new Date(fechaActualizacion.getTime());
        }

    }

    /** @return this.divisa */
    @Column(name = "DIVISA")
    public String getDivisa() {
        return this.divisa;
    }

    /** @param divisa to be set in this.divisa */
    public void setDivisa(final String divisa) {

        if (null != divisa) {
            this.divisa = divisa;
        }

    }

    /** @return this.importe */
    @Column(name = "IMPORTE")
    public BigDecimal getImporte() {
        return this.importe;
    }

    /** @param importe to be set in this.importe */
    public void setImporte(final BigDecimal importe) {

        if (null != importe) {
            this.importe = importe;
        }

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
		if(importeParcial != null){
			this.importeParcial = importeParcial;	
		}
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
		if(remanenteEfectivo != null){
			this.remanenteEfectivo = remanenteEfectivo;			
		}
	}

	/** @return this.error */
    @Column(name = "ERROR")
    public String getError() {
        return this.error;
    }

    /** @param error to be set in this.error */
    public void setError(final String error) {

        if (null != error) {
            this.error = error;
        }

    }

    /** @return this.estatus */
    @Column(name = "ESTATUS")
    public String getEstatus() {
        return this.estatus;
    }

    /** @param estatus to be set in this.estatus */
    public void setEstatus(final String estatus) {

        if (null != estatus) {
            this.estatus = estatus;
        }

    }

    /** @return this.estatusPfi */
    @Column(name = "ESTATUS_PFI")
    public String getEstatusPfi() {
        return this.estatusPfi;
    }

    /** @param estatusPfi to be set in this.estatusPfi */
    public void setEstatusPfi(final String estatusPfi) {

        if (null != estatusPfi) {
            this.estatusPfi = estatusPfi;
        }

    }

    /** @return this.descEstatusPfi */
    @Column(name = "DESC_ESTATUS_PFI")
    public String getDescEstatusPfi() {
        return this.descEstatusPfi;
    }

    /** @param descEstatusPfi to be set in this.descEstatusPfi */
    public void setDescEstatusPfi(final String descEstatusPfi) {

        if (null != descEstatusPfi) {
            this.descEstatusPfi = descEstatusPfi;
        }

    }

    /** @return this.descripcionCustodio */
    @Column(name = "DESCRIPCION_CUSTODIO")
    public String getDescripcionCustodio() {
        return this.descripcionCustodio;
    }

    /** @param descripcionCustodio to be set in this.descripcionCustodio */
    public void setDescripcionCustodio(final String descripcionCustodio) {

        if (null != descripcionCustodio) {
            this.descripcionCustodio = descripcionCustodio;
        }

    }

    /** @return this.descripcionCuenta */
    @Column(name = "DESCRIPCION_CUENTA")
    public String getDescripcionCuenta() {
        return this.descripcionCuenta;
    }

    /** @param descripcionCuenta to be set in this.descripcionCuenta */
    public void setDescripcionCuenta(final String descripcionCuenta) {

        if (null != descripcionCuenta) {
            this.descripcionCuenta = descripcionCuenta;
        }

    }

    /** @return this.cuentaContraparte */
    @Column(name = "CUENTA_CONTRAPARTE")
    public String getCuentaContraparte() {
        return this.cuentaContraparte;
    }

    /** @param cuentaContraparte to be set in this.cuentaContraparte */
    public void setCuentaContraparte(final String cuentaContraparte) {

        if (null != cuentaContraparte) {
            this.cuentaContraparte = cuentaContraparte;
        }

    }

    /** @return this.descripcionContraparte */
    @Column(name = "DESCRIPCION_CONTRAPARTE")
    public String getDescripcionContraparte() {
        return this.descripcionContraparte;
    }

    /** @param descripcionContraparte to be set in this.descripcionContraparte */
    public void setDescripcionContraparte(final String descripcionContraparte) {

        if (null != descripcionContraparte) {
            this.descripcionContraparte = descripcionContraparte;
        }

    }

    /** @return this.depositante */
    @Column(name = "DEPOSITANTE")
    public String getDepositante() {
        return this.depositante;
    }

    /** @param depositante to be set in this.depositante */
    public void setDepositante(final String depositante) {

        if (null != depositante) {
            this.depositante = depositante;
        }

    }

    /** @return this.instruccionesEspeciales */
    @Column(name = "INSTRUCCIONES_ESPECIALES")
    public String getInstruccionesEspeciales() {
        return this.instruccionesEspeciales;
    }

    /** @param instruccionesEspeciales to be set in this.instruccionesEspeciales */
    public void setInstruccionesEspeciales(final String instruccionesEspeciales) {

        if (null != instruccionesEspeciales) {
            this.instruccionesEspeciales = instruccionesEspeciales;
        }

    }

    /** @return this.nombreCuentaBeneficiario */
    @Column(name = "NOMBRE_CUENTA_BENEFICIARIO")
    public String getNombreCuentaBeneficiario() {
        return this.nombreCuentaBeneficiario;
    }

    /** @param nombreCuentaBeneficiario to be set in this.nombreCuentaBeneficiario */
    public void setNombreCuentaBeneficiario(final String nombreCuentaBeneficiario) {

        if (null != nombreCuentaBeneficiario) {
            this.nombreCuentaBeneficiario = nombreCuentaBeneficiario;
        }

    }

    /** @return this.numeroCuentaBeneficiario */
    @Column(name = "NUMERO_CUENTA_BENEFICIARIO")
    public String getNumeroCuentaBeneficiario() {
        return this.numeroCuentaBeneficiario;
    }

    /** @param numeroCuentaBeneficiario to be set in this.numeroCuentaBeneficiario */
    public void setNumeroCuentaBeneficiario(final String numeroCuentaBeneficiario) {

        if (null != numeroCuentaBeneficiario) {
            this.numeroCuentaBeneficiario = numeroCuentaBeneficiario;
        }

    }

    /** @return this.fechaAprobacion */
    @Column(name = "FECHA_APROBACION")
    public Date getFechaAprobacion() {

        Date fechaAprobacion = null;
        if (null != this.fechaAprobacion) {
            fechaAprobacion = new Date(this.fechaAprobacion.getTime());
        }
        return fechaAprobacion;

    }

    /** @param fechaAprobacion to be set in this.fechaAprobacion */
    public void setFechaAprobacion(final Date fechaAprobacion) {

        if (null != fechaAprobacion) {
            this.fechaAprobacion = new Date(fechaAprobacion.getTime());
        }

    }

    /** @return this.mensajeEfectivo */
    @Column(name = "MENSAJE_EFECTIVO")
    public String getMensajeEfectivo() {
        return this.mensajeEfectivo;
    }

    /** @param mensajeEfectivo to be set in this.mensajeEfectivo */
    public void setMensajeEfectivo(final String mensajeEfectivo) {

        if (null != mensajeEfectivo) {
            this.mensajeEfectivo = mensajeEfectivo;
        }

    }

    /** @return this.mensajeConfirmacion */
    @Column(name = "MENSAJE_CONFIRMACION")
    public String getMensajeConfirmacion() {
        return this.mensajeConfirmacion;
    }

    /** @param mensajeConfirmacion to be set in this.mensajeConfirmacion */
    public void setMensajeConfirmacion(final String mensajeConfirmacion) {

        if (null != mensajeConfirmacion) {
            this.mensajeConfirmacion = mensajeConfirmacion;
        }

    }

    /** @return this.origenPfi */
    @Column(name = "ORIGEN_PFI")
    public BigInteger getOrigenPfi() {
        return this.origenPfi;
    }

    /** @param origenPfi to be set in this.origenPfi */
    public void setOrigenPfi(final BigInteger origenPfi) {

        if (null != origenPfi) {
            this.origenPfi = origenPfi;
        }

    }

    /** @return this.idBoveda */
    @Column(name = "ID_BOVEDA")
    public Long getIdBoveda() {
        return this.idBoveda;
    }

    /** @param idBoveda to be set in this.idBoveda */
    public void setIdBoveda(final Long idBoveda) {

        if (null != idBoveda) {
            this.idBoveda = idBoveda;
        }

    }

    /** @return this.bicDepositante */
    @Column(name = "BIC_DEPOSITANTE")
    public String getBicDepositante() {
        return this.bicDepositante;
    }

    /** @param bicDepositante to be set in this.bicDepositante */
    public void setBicDepositante(final String bicDepositante) {

        if (null != bicDepositante) {
            this.bicDepositante = bicDepositante;
        }

    }

    /** @return this.descErrorSwift */
    @Column(name = "DESC_ERROR_SWIFT")
    public String getDescErrorSwift() {
        return this.descErrorSwift;
    }

    /** @param descErrorSwift to set in this.descErrorSwift */
    public void setDescErrorSwift(final String descErrorSwift) {

        if (null != descErrorSwift) {
            this.descErrorSwift = descErrorSwift;
        }

    }

    /** @return this.cambioBoveda */
    @Column(name = "CAMBIO_BOVEDA")
    public BigInteger getCambioBoveda() {
        return cambioBoveda;
    }

    /** @param cambioBoveda to set in this.cambioBoveda */
    public void setCambioBoveda(BigInteger cambioBoveda) {
        this.cambioBoveda = cambioBoveda;
    }

    /** @return this.depositanteLiquidador */
    @Column(name = "DEPOSITANTE_LIQUIDADOR")
    public String getDepositanteLiquidador() {
        return this.depositanteLiquidador;
    }

    /** @param depositanteLiquidador to set in this.depositanteLiquidador */
    public void setDepositanteLiquidador(String depositanteLiquidador) {
        this.depositanteLiquidador = depositanteLiquidador;
    }

	/**
	 * @return the liquidacionParcial
	 */
    @Column(name = "LIQ_PARCIAL")
	public String getLiquidacionParcial() {
		return liquidacionParcial;
	}

	/**
	 * @param liquidacionParcial the liquidacionParcial to set
	 */
	public void setLiquidacionParcial(String liquidacionParcial) {
		this.liquidacionParcial = liquidacionParcial;
	}
    
    

}
