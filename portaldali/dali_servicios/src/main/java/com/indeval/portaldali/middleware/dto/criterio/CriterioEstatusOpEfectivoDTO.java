/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CriterioEstatusOpEfectivoDTO.java
 * 04/03/2008
 */
package com.indeval.portaldali.middleware.dto.criterio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;

/**
 * Representa los criterios que se utilizan en la pantalla de Estatus de
 * operaciones de efectivo.
 * 
 * @author Juan Carlos Huizar Moreno.
 * 
 */
public class CriterioEstatusOpEfectivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * 
	 * Id del registro
	 * */
	private String id = null;
	
	/** Criterio de consulta: fecha de liquidación. */
	private Date fechaLiquidacion = null;
	private Date fechaVencimiento = null;

	/** Criterios de consulta para retiro efectivo nal e internacional*/
	private Date fechaValor = null;
	private Date fechaEnvio = null;
	private Date fechaAutorizacion = null;
	private Date fechaLiberacion = null;
	private Date fechaRegistro = null;
	
	
	/** Criterio de consulta: rol. */
	private int rol;

	/** Criterio de consulta: cuenta propia. */
	private CuentaDTO cuentaParticipante = null;

	/** Criterio de consulta: cuenta contraparte. */
	private CuentaDTO cuentaContraparte = null;

	/** Criterio de consulta: folioControl. */
	private String referenciaOperacion = null;
	/** Criterio de consulta: importe. */
	private String monto = null;
	private  BigDecimal montoBd = null;
	private String montoVencimiento = null;
	private String tasa = null;
	/** Criterio de consulta: plazo. */
	private String plazo = null;
	
	

	/** El tipo de instrucción */
	private TipoInstruccionDTO tipoInstruccion = null;
	
	
	/** El tipo de liquidacion */
	private TipoLiquidacionDTO tipoLiquidacion = null;
	
	/** El tipo de liquidacion */
	private TipoLiquidacionDTO liquidacionVencimiento = null;
	
	private String referenciaPaquete;
	/**
	 * Institucion  del usuario loggeado
	 */
	private InstitucionDTO institucionParticipante = null;
	
	private InstitucionDTO institucionContraparte = null;

	/** El estado de la instrucción **/
	private EstadoInstruccionDTO estadoInstruccion = null;
	
	/** Las divisas **/
	private DivisaDTO divisa = null;
	
	/** Las bovedas*/
	private BovedaDTO boveda = null;
	
	
	/** Tipo de Retiro (SPEI/SIAC) **/
	private String tipoRetiro = null;
	
	/** Datos para completar el registro de banca comercial*/
	private Map<String, Object> mapBCOM;
	
	/**
	 * Obtiene el campo fechaLiquidacion
	 * 
	 * @return fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}


	/**
	 * Asigna el campo fechaLiquidacion
	 * 
	 * @param fechaLiquidacion
	 *            el valor de fechaLiquidacion a asignar
	 */

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * Obtiene el campo rol
	 * 
	 * @return rol
	 */
	public int getRol() {
		return rol;
	}

	/**
	 * Asigna el campo rol
	 * 
	 * @param rol
	 *            el valor de rol a asignar
	 */
	public void setRol(int rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el campo cuentaParticipante
	 * 
	 * @return cuentaParticipante
	 */
	public CuentaDTO getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * Asigna el campo cuentaParticipante
	 * 
	 * @param cuentaParticipante
	 *            el valor de cuentaParticipante a asignar
	 */
	public void setCuentaParticipante(CuentaDTO cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * Obtiene el campo cuentaContraparte
	 * 
	 * @return cuentaContraparte
	 */
	public CuentaDTO getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * Asigna el campo cuentaContraparte
	 * 
	 * @param cuentaContraparte
	 *            el valor de cuentaContraparte a asignar
	 */
	public void setCuentaContraparte(CuentaDTO cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * Obtiene el campo referenciaOperacion
	 * 
	 * @return referencia
	 */
	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}
        
	/**
	 * Asigna el campo referencia
         *
	 * @param referencia
	 *            el valor de referencia a asignar
	 */
	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String importe) {
		this.monto = importe;
	}

	public String getMontoVencimiento() {
		return montoVencimiento;
	}
	public void setMontoVencimiento(String montoVencimiento) {
		this.montoVencimiento = montoVencimiento;
	}
	
	
	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	/**
	 * Obtiene el campo tipoInstruccion
         *
	 * @return tipoInstruccion
	 */
	public TipoInstruccionDTO getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Asigna el campo tipoInstruccion
         *
	 * @param tipoInstruccion
	 *            el valor de tipoInstruccion a asignar
	 */
	public void setTipoInstruccion(TipoInstruccionDTO tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public TipoLiquidacionDTO getTipoLiquidacion() {
		return tipoLiquidacion;
	}

	public void setTipoLiquidacion(TipoLiquidacionDTO tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}
	
	
	
	public TipoLiquidacionDTO getLiquidacionVencimiento() {
		return liquidacionVencimiento;
	}
	public void setLiquidacionVencimiento(TipoLiquidacionDTO liquidacionVencimiento) {
		this.liquidacionVencimiento = liquidacionVencimiento;
	}
	/**
	 * Obtiene la cadena con la descripción del rol
	 * 
	 * @return
	 */
	public String getDescripcionRol() {
		return RolConstants.DESCRIPCION_ROLES[rol];
	}

	/**
	 * Obtiene el campo institucionParticipante
	 * @return  institucionParticipante
	 */
	public InstitucionDTO getInstitucionParticipante() {
		return institucionParticipante;
	}

	/**
	 * Asigna el campo institucionParticipante
	 * @param institucionParticipante el valor de institucionParticipante a asignar
	 */
	public void setInstitucionParticipante(InstitucionDTO institucionParticipante) {
		this.institucionParticipante = institucionParticipante;
	}

	/**
	 * @param estadoInstruccion the estadoInstruccion to set
	 */
	public void setEstadoInstruccion(EstadoInstruccionDTO estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	
	public DivisaDTO getDivisa() {
		return divisa;
	}


	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}


	public InstitucionDTO getInstitucionContraparte() {
		return institucionContraparte;
	}

	public void setInstitucionContraparte(InstitucionDTO institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}

	/**
	 * @return the estadoInstruccion
	 */
	public EstadoInstruccionDTO getEstadoInstruccion() {
		return estadoInstruccion;
	}

	public String getTipoRetiro() {
		return tipoRetiro;
	}

	public void setTipoRetiro(String tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getFechaValor() {
		return fechaValor;
	}


	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}


	public Date getFechaEnvio() {
		return fechaEnvio;
	}


	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
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


	public BovedaDTO getBoveda() {
		return boveda;
	}


	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}


	public Map<String, Object> getMapBCOM() {
		return mapBCOM;
	}


	public void setMapBCOM(Map<String, Object> mapBCOM) {
		this.mapBCOM = mapBCOM;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}


	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}


	public BigDecimal getMontoBd() {
		return montoBd;
	}


	public void setMontoBd(BigDecimal montoBD) {
		this.montoBd = montoBD;
	}
	
	
	
	
}
