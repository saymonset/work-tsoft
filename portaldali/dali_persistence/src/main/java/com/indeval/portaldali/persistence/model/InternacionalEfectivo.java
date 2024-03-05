/**
 * Copyright (c) 2009-2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

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

/**
 * Mapeo de la tabla T_INTERNACIONAL_EFECTIVO. Tabla para persistir las instrucciones de operaci&oacute;n de
 * EFECTIVO que llegan al MOI
 */

@Entity
@Table(name = "T_INTERNACIONAL_EFECTIVO")
@SequenceGenerator(name = "SEQ_InternacionalEfectivo", sequenceName = "SEQ_T_INTERNACIONAL_EFEC")
public class InternacionalEfectivo implements Serializable {
    /**
     * serial no.
     */
    private static final long serialVersionUID = 1L;
    /**
     * PK internacional efectivo.
     */
    @Id
    @Column(name = "id_internacional_efectivo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_InternacionalEfectivo")
    private Long idInternacionalEfectivo;
    
    /**
     * id de la institucion traspasante.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_institucion_traspasante", updatable = false, insertable = false)    
    private Institucion idInstitucionTraspasante;
    
    /**
     * id de la institucion receptora.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_institucion_receptora", updatable = false, insertable = false)    
    private Institucion idInstitucionReceptora;
    
    /**
     * id cuenta traspasante.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_traspasante", updatable = false, insertable = false)    
    private CuentaNombrada idCuentaTraspasante;
    /**
     * id cuenta receptora.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_receptora", updatable = false, insertable = false)    
    private CuentaNombrada idCuentaReceptora;
    
    /**
     * id error dali.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_error_dali", updatable = false, insertable = false)    
    private ErrorDali idErrorDali;
    /**
     * id divisa.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_divisa", updatable = false, insertable = false)
    private Divisa idDivisa;
    /**
     * ID de la boveda.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boveda", updatable = false, insertable = false)
    private Boveda idBoveda;
    /**
     * tipo de instruccion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_instruccion", updatable = false, insertable = false)
    private TipoInstruccion idTipoInstruccion;
    /**
     * tipo de mensaje.
     */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_mensaje", updatable = false, insertable = false)
    private TipoMensajeCat idTipoMensaje;

    /**
     * id estado instruccion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_instruccion", updatable = false, insertable = false)
    private EstadoInstruccionCat idEstadoInstruccion;
    /**
     * id institucion origen.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_institucion_origen", updatable = false, insertable = false)
    private Institucion idInstitucionOrigen;
    /**
     * id cuenta retiro.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_retiro", updatable = false, insertable = false)
    private CuentaRetiroInternacional idCuentaRetiro;
    /**
     * folio control.
     */
    @Column(name = "folio_control")
    private Long folioControl;
    /**
     * folio del bloqueo.
     */
    @Column(name = "folio_inst_bloqueo")
    private Long folioInstruccionBloqueo;
    /**
     * folio instruccion liquidacion.
     */
    @Column(name = "folio_inst")
    private Long folioInstruccionLiquidacion;
    /**
     * referencia de la operacion.
     */
    @Column(name = "referencia_operacion")
    private String referenciaOperacion;
    /**
     * referencia del participante.
     */
    @Column(name = "referencia_participante")
    private Long referenciaParticipante;
    /**
     * referencia numerica.
     */
    @Column(name = "referencia_numerica")
    private Long referenciaNumerica;
    /**
     * concepto.
     */
    @Column(name = "concepto")
    private String concepto;
    /**
     * monto.
     */
    @Column(name = "monto")
    private BigDecimal monto;
    /**
     * fecha de luidacion.
     */
    @Column(name = "fecha_liquidacion")
    private Date fechaLiquidacion;
    
    /**
     * fecha valor.
     */
    @Column(name = "fecha_valor")
    private Date fechaValor;
    
    /**
     * fecha registro.
     */
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
    /**
     * descripcion del estado.
     */
    @Column(name = "descripcion_estado")
    private String descripcionEstado;
    /**
     * prioridad.
     */
    @Column(name = "prioridad")
    private Boolean prioridad;
    /**
     * sobregiro.
     */
    @Column(name = "sobregiro")
    private Boolean sobregiro;


    /**
     * @return the idInternacionalEfectivo
     */
    public Long getIdInternacionalEfectivo() {
        return idInternacionalEfectivo;
    }

    /**
     * @param idInternacionalEfectivo
     *            the idInternacionalEfectivo to set
     */
    public void setIdInternacionalEfectivo(Long idInternacionalEfectivo) {
        this.idInternacionalEfectivo = idInternacionalEfectivo;
    }

    

   
  

 
    /**
     * @return the idTipoMensaje
     */
    public TipoMensajeCat getIdTipoMensaje() {
        return idTipoMensaje;
    }

    /**
     * @param idTipoMensaje
     *            the idTipoMensaje to set
     */
    public void setIdTipoMensaje(TipoMensajeCat idTipoMensaje) {
        this.idTipoMensaje = idTipoMensaje;
    }

    /**
     * @return the folioControl
     */
    public Long getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl
     *            the folioControl to set
     */
    public void setFolioControl(Long folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return the folioInstruccionBloqueo
     */
    public Long getFolioInstruccionBloqueo() {
        return folioInstruccionBloqueo;
    }

    /**
     * @param folioInstruccionBloqueo
     *            the folioInstruccionBloqueo to set
     */
    public void setFolioInstruccionBloqueo(Long folioInstruccionBloqueo) {
        this.folioInstruccionBloqueo = folioInstruccionBloqueo;
    }

    /**
     * @return the folioInstruccion
     */
    public Long getFolioInstruccionLiquidacion() {
        return folioInstruccionLiquidacion;
    }

    /**
     * @param folioInstruccionLiquidacion
     *            the folioInstruccionLiquidacion to set
     */
    public void setFolioInstruccionLiquidacion(Long folioInstruccionLiquidacion) {
        this.folioInstruccionLiquidacion = folioInstruccionLiquidacion;
    }

    /**
     * @return the referenciaOperacion
     */
    public String getReferenciaOperacion() {
        return referenciaOperacion;
    }

    /**
     * @param referenciaOperacion
     *            the referenciaOperacion to set
     */
    public void setReferenciaOperacion(String referenciaOperacion) {
        this.referenciaOperacion = referenciaOperacion;
    }

    /**
     * @return the referenciaParticipante
     */
    public Long getReferenciaParticipante() {
        return referenciaParticipante;
    }

    /**
     * @param referenciaParticipante
     *            the referenciaParticipante to set
     */
    public void setReferenciaParticipante(Long referenciaParticipante) {
        this.referenciaParticipante = referenciaParticipante;
    }

    /**
     * @return the referenciaNumerica
     */
    public Long getReferenciaNumerica() {
        return referenciaNumerica;
    }

    /**
     * @param referenciaNumerica
     *            the referenciaNumerica to set
     */
    public void setReferenciaNumerica(Long referenciaNumerica) {
        this.referenciaNumerica = referenciaNumerica;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto
     *            the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto
     *            the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *            the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro
     *            the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the descripcionEstado
     */
    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    /**
     * @param descripcionEstado
     *            the descripcionEstado to set
     */
    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    /**
     * @return the prioridad
     */
    public Boolean getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad
     *            the prioridad to set
     */
    public void setPrioridad(Boolean prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the sobregiro
     */
    public Boolean getSobregiro() {
        return sobregiro;
    }

    /**
     * @param sobregiro
     *            the sobregiro to set
     */
    public void setSobregiro(Boolean sobregiro) {
        this.sobregiro = sobregiro;
    }
  

	public Institucion getIdInstitucionTraspasante() {
		return idInstitucionTraspasante;
	}

	public void setIdInstitucionTraspasante(Institucion idInstitucionTraspasante) {
		this.idInstitucionTraspasante = idInstitucionTraspasante;
	}

	public Institucion getIdInstitucionReceptora() {
		return idInstitucionReceptora;
	}

	public void setIdInstitucionReceptora(Institucion idInstitucionReceptora) {
		this.idInstitucionReceptora = idInstitucionReceptora;
	}

	public CuentaNombrada getIdCuentaTraspasante() {
		return idCuentaTraspasante;
	}

	public void setIdCuentaTraspasante(CuentaNombrada idCuentaTraspasante) {
		this.idCuentaTraspasante = idCuentaTraspasante;
	}

	public CuentaNombrada getIdCuentaReceptora() {
		return idCuentaReceptora;
	}

	public void setIdCuentaReceptora(CuentaNombrada idCuentaReceptora) {
		this.idCuentaReceptora = idCuentaReceptora;
	}

	public ErrorDali getIdErrorDali() {
		return idErrorDali;
	}

	public void setIdErrorDali(ErrorDali idErrorDali) {
		this.idErrorDali = idErrorDali;
	}

	public Divisa getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(Divisa idDivisa) {
		this.idDivisa = idDivisa;
	}

	public Boveda getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(Boveda idBoveda) {
		this.idBoveda = idBoveda;
	}

	public TipoInstruccion getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	public void setIdTipoInstruccion(TipoInstruccion idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	public EstadoInstruccionCat getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	public void setIdEstadoInstruccion(EstadoInstruccionCat idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

	public Institucion getIdInstitucionOrigen() {
		return idInstitucionOrigen;
	}

	public void setIdInstitucionOrigen(Institucion idInstitucionOrigen) {
		this.idInstitucionOrigen = idInstitucionOrigen;
	}

	public CuentaRetiroInternacional getIdCuentaRetiro() {
		return idCuentaRetiro;
	}

	public void setIdCuentaRetiro(CuentaRetiroInternacional idCuentaRetiro) {
		this.idCuentaRetiro = idCuentaRetiro;
	}

	public Date getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}
    
    

}
