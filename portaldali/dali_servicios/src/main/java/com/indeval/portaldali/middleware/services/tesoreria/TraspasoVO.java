/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EstatusVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspasoVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String folioCapt;

	private BigDecimal folioLiq;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private Date fechaCaptura; // dd/mm/yyyy hh24:mi:ss

	private Date horaInsercion; // hh24:mi:ss

	private Date horaConfirmacion; // hh24:mi:ss

	private BigDecimal importe;

	private EstatusVO estatus;

	private String tipoOperacion;

	private String folioConfirmacion;

	/**
	 * @return EstatusVO
	 */
	public EstatusVO getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus
	 */
	public void setEstatus(EstatusVO estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return Date
	 */
	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	/**
	 * @param fechaCaptura
	 */
	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = new Date(fechaCaptura.getTime());
	}

	/**
	 * @return String
	 */
	public String getFolioCapt() {
		return folioCapt;
	}

	/**
	 * @param folioCapt
	 */
	public void setFolioCapt(String folioCapt) {
		this.folioCapt = folioCapt;
	}

	/**
	 * @return String
	 */
	public String getFolioConfirmacion() {
		return folioConfirmacion;
	}

	/**
	 * @param folioConfirmacion
	 */
	public void setFolioConfirmacion(String folioConfirmacion) {
		this.folioConfirmacion = folioConfirmacion;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getFolioLiq() {
		return folioLiq;
	}

	/**
	 * @param folioLiq
	 */
	public void setFolioLiq(BigDecimal folioLiq) {
		this.folioLiq = folioLiq;
	}

	/**
	 * @return Date
	 */
	public Date getHoraConfirmacion() {
		return horaConfirmacion;
	}

	/**
	 * @param horaConfirmacion
	 */
	public void setHoraConfirmacion(Date horaConfirmacion) {
		this.horaConfirmacion = clona(horaConfirmacion);
	}

	/**
	 * @return Date
	 */
	public Date getHoraInsercion() {
		return horaInsercion;
	}

	/**
	 * @param horaInsercion
	 */
	public void setHoraInsercion(Date horaInsercion) {
		this.horaInsercion = clona(horaInsercion);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor
	 */
	public void setReceptor(AgenteVO receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return String
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getTraspasante() {
		return traspasante;
	}

	/**
	 * @param traspasante
	 */
	public void setTraspasante(AgenteVO traspasante) {
		this.traspasante = traspasante;
	}

	/**
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		if ((inObject == null) || (!(inObject instanceof TraspasoVO)))
			return false;

		return true;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
