/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object que representa un registro en la Bitacora de 
 * los cambios de estado de las cuentas de retiro.
 * 
 * @author  Maria C. Buendia
 * @version 1.0
 */
public class BitacoraEdosCuentaRetiroDTO implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	private Long idBitacoraEdosCuenta;
	
	/** cuenta de retiro correspondiente a este detalle */
	private CuentaRetiroDTO cuentaRetiro;
	
	/** fecha creacion */
	private Date fechaCreacion;
	
	/** fecha autorizacion */
	private Date fechaAutorizacion;
	
	/** fecha liberacion */
	private Date fechaLiberacion;
	
	/** fecha aprobacion */
	private Date fechaAprobacion;
	
	/** fecha cancelacion */
	private Date fechaModificacion;
	
	/** fecha cancelacion */
	private Date fechaCancelacion;
	
	/** usuario Creacion */
	private String usuarioCreacion;
	
	/** serie Creacion */
	private String serieCreacion;
	
	/** usuario Autorizacion */
	private String usuarioAutorizacion;
	
	/** serie Autorizacion */
	private String serieAutorizacion;
	
	/** usuario Liberacion */
	private String usuarioLiberacion;
	
	/** serie Liberacion */
	private String serieLiberacion;
	
	/** usuario Aprobacion */
	private String usuarioAprobacion;
	
	/** serie Aprobacion */
	private String serieAprobacion;
	
	/** usuario Modificacion */
	private String usuarioModificacion;
	
	/** serie Modificacion */
	private String serieModificacion;
	
	/** usuario Cancelacion */
	private String usuarioCancelacion;
	
	/** serie Cancelacion */
	private String serieCancelacion;
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public Long getIdBitacoraEdosCuenta() {
		return idBitacoraEdosCuenta;
	}

	public void setIdBitacoraEdosCuenta(Long idBitacoraEdosCuenta) {
		this.idBitacoraEdosCuenta = idBitacoraEdosCuenta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public CuentaRetiroDTO getCuentaRetiro() {
		return cuentaRetiro;
	}

	public void setCuentaRetiro(CuentaRetiroDTO cuentaRetiro) {
		this.cuentaRetiro = cuentaRetiro;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getSerieCreacion() {
		return serieCreacion;
	}

	public void setSerieCreacion(String serieCreacion) {
		this.serieCreacion = serieCreacion;
	}

	public String getUsuarioAutorizacion() {
		return usuarioAutorizacion;
	}

	public void setUsuarioAutorizacion(String usuarioAutorizacion) {
		this.usuarioAutorizacion = usuarioAutorizacion;
	}

	public String getSerieAutorizacion() {
		return serieAutorizacion;
	}

	public void setSerieAutorizacion(String serieAutorizacion) {
		this.serieAutorizacion = serieAutorizacion;
	}

	public String getUsuarioLiberacion() {
		return usuarioLiberacion;
	}

	public void setUsuarioLiberacion(String usuarioLiberacion) {
		this.usuarioLiberacion = usuarioLiberacion;
	}

	public String getSerieLiberacion() {
		return serieLiberacion;
	}

	public void setSerieLiberacion(String serieLiberacion) {
		this.serieLiberacion = serieLiberacion;
	}

	public String getUsuarioAprobacion() {
		return usuarioAprobacion;
	}

	public void setUsuarioAprobacion(String usuarioAprobacion) {
		this.usuarioAprobacion = usuarioAprobacion;
	}

	public String getSerieAprobacion() {
		return serieAprobacion;
	}

	public void setSerieAprobacion(String serieAprobacion) {
		this.serieAprobacion = serieAprobacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public String getSerieModificacion() {
		return serieModificacion;
	}

	public void setSerieModificacion(String serieModificacion) {
		this.serieModificacion = serieModificacion;
	}

	public String getUsuarioCancelacion() {
		return usuarioCancelacion;
	}

	public void setUsuarioCancelacion(String usuarioCancelacion) {
		this.usuarioCancelacion = usuarioCancelacion;
	}

	public String getSerieCancelacion() {
		return serieCancelacion;
	}

	public void setSerieCancelacion(String serieCancelacion) {
		this.serieCancelacion = serieCancelacion;
	}

}
