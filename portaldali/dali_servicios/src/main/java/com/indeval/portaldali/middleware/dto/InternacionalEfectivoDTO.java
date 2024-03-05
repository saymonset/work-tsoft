/**
 * Copyright (c) 2009-2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * DTO de InternacionalEfectivo.
 * EFECTIVO que llegan al MOI
 */


public class InternacionalEfectivoDTO implements Serializable {
    /**
     * serial no.
     */
    private static final long serialVersionUID = 1L;
    /**
     * PK internacional efectivo.
     */

    private Long idInternacionalEfectivo;
    
    /**
     * id de la institucion traspasante.
     */
    
    private InstitucionDTO idInstitucionTraspasante;
    
    /**
     * id de la institucion receptora.
     */
      
    private InstitucionDTO idInstitucionReceptora;
    
    /**
     * id cuenta traspasante.
     */
       
    private CuentaNombradaDTO idCuentaTraspasante;
    /**
     * id cuenta receptora.
     */
        
    private CuentaNombradaDTO idCuentaReceptora;
    
    /**
     * id error dali.
     */
       
    private ErrorDaliDTO idErrorDali;
    /**
     * id divisa.
     */
   
    private DivisaDTO idDivisa;
    /**
     * ID de la boveda.
     */
   
    private BovedaDTO idBoveda;
    /**
     * tipo de instruccion.
     */
   
    private TipoInstruccionDTO idTipoInstruccion;
    /**
     * tipo de mensaje.
     */
    
  
    private TipoMensajeCatDTO idTipoMensaje;

    /**
     * id estado instruccion.
     */
    
    private EstadoInstruccionCatDTO idEstadoInstruccion;
    /**
     * id institucion origen.
     */
   
    private InstitucionDTO idInstitucionOrigen;
    /**
     * id cuenta retiro.
     */
   
    private CuentaRetiroInternacionalDTO idCuentaRetiro;
    /**
     * folio control.
     */
 
    private Long folioControl;
    /**
     * folio del bloqueo.
     */
 
    private Long folioInstruccionBloqueo;
    /**
     * folio instruccion liquidacion.
     */
   
    private Long folioInstruccionLiquidacion;
    /**
     * referencia de la operacion.
     */
   
    private String referenciaOperacion;
    /**
     * referencia del participante.
     */
   
    private Long referenciaParticipante;
    /**
     * referencia numerica.
     */
    
    private Long referenciaNumerica;
    /**
     * concepto.
     */
    
    private String concepto;
    /**
     * monto.
     */
    
    private BigDecimal monto;
    /**
     * fecha de luidacion.
     */
    
    private Date fechaLiquidacion;
    /**
     * fecha registro.
     */
    
    private Date fechaRegistro;
    /**
     * descripcion del estado.
     */
    
    private String descripcionEstado;
    /**
     * prioridad.
     */
   
    private Boolean prioridad;
    /**
     * sobregiro.
     */
   
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
    public TipoMensajeCatDTO getIdTipoMensaje() {
        return idTipoMensaje;
    }

    /**
     * @param idTipoMensaje
     *            the idTipoMensaje to set
     */
    public void setIdTipoMensaje(TipoMensajeCatDTO idTipoMensaje) {
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
  

	public InstitucionDTO getIdInstitucionTraspasante() {
		return idInstitucionTraspasante;
	}

	public void setIdInstitucionTraspasante(InstitucionDTO idInstitucionTraspasante) {
		this.idInstitucionTraspasante = idInstitucionTraspasante;
	}

	public InstitucionDTO getIdInstitucionReceptora() {
		return idInstitucionReceptora;
	}

	public void setIdInstitucionReceptora(InstitucionDTO idInstitucionReceptora) {
		this.idInstitucionReceptora = idInstitucionReceptora;
	}

	public CuentaNombradaDTO getIdCuentaTraspasante() {
		return idCuentaTraspasante;
	}

	public void setIdCuentaTraspasante(CuentaNombradaDTO idCuentaTraspasante) {
		this.idCuentaTraspasante = idCuentaTraspasante;
	}

	public CuentaNombradaDTO getIdCuentaReceptora() {
		return idCuentaReceptora;
	}

	public void setIdCuentaReceptora(CuentaNombradaDTO idCuentaReceptora) {
		this.idCuentaReceptora = idCuentaReceptora;
	}

	public ErrorDaliDTO getIdErrorDali() {
		return idErrorDali;
	}

	public void setIdErrorDali(ErrorDaliDTO idErrorDali) {
		this.idErrorDali = idErrorDali;
	}

	public DivisaDTO getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(DivisaDTO idDivisa) {
		this.idDivisa = idDivisa;
	}

	public BovedaDTO getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(BovedaDTO idBoveda) {
		this.idBoveda = idBoveda;
	}

	public TipoInstruccionDTO getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	public void setIdTipoInstruccion(TipoInstruccionDTO idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	public EstadoInstruccionCatDTO getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	public void setIdEstadoInstruccion(EstadoInstruccionCatDTO idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

	public InstitucionDTO getIdInstitucionOrigen() {
		return idInstitucionOrigen;
	}

	public void setIdInstitucionOrigen(InstitucionDTO idInstitucionOrigen) {
		this.idInstitucionOrigen = idInstitucionOrigen;
	}

	public CuentaRetiroInternacionalDTO getIdCuentaRetiro() {
		return idCuentaRetiro;
	}

	public void setIdCuentaRetiro(CuentaRetiroInternacionalDTO idCuentaRetiro) {
		this.idCuentaRetiro = idCuentaRetiro;
	}
    
    

}
