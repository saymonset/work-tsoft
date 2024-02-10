/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class ValidaNegocioVO extends AbstractBaseDTO implements Constantes {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;
    
	/* Atributos generales de la operacion */
	
	/** Agente firmado */
	private AgenteVO agenteFirmado;
	
	/** Tipo de Operacion */
	private String tipoOperacion;

	/** Es la cuenta receptora de la operacion */
	private String cuentaRecep;

	/** Map de indices */
	private Map<Object, Object> indice;

	/** Arreglo de campos */
	private String[] campos;
	
	/* Se utiliza para almacenar la fecha actual */
	private Date fechaActual;

	/** tamano */
	private int tam;
	
	/* Atributos con mecanismo de validacion controlada */
	
	/** IdBoveda */
	private Long idBoveda; /* Valor recibido de la boveda */
	
	/** Almacena las bovedas que ya han sido validadas */
	private List bovedasValidas;
	
	/** Bandera que indica que la boveda esta contenida en la lista de bovedasValidas */
	private boolean esBovedaValidada;
	
	/** Bandera que indica que la boveda es Valida */
	private boolean esBovedaValida = false;
	
	/** Almacena las cuentas traspasantes que ya han sido validados */
	private List cuentasTraspasanteValidas; /* id+folio+cuenta */
	
	/** Es el agente traspasante de la operacion */
	private AgenteVO agenteTraspasante;
	
	/** Bandera que indica que la cuenta traspasante esta contenida en la lista de cuentasTraspasanteValidas */
	private boolean esCuentaTraspasanteValidada;
	
	/** Bandera que indica que la cuenta traspasante es Valida */
	private boolean esCuentaTraspasanteValida = false;
	
	/** Es el agente traspasante de la operacion */
	private AgenteVO agenteSic;
	
	/** Arreglo de agentes SIC */
	private AgenteVO[] agentesSic;
	
	/** Almacena los SicEmision que ya han sido recuperadas */
	private Map sicEmision;
	
	/** Bandera que indica que el depositante esta contenido en la lista de depositantesValidos */
	private boolean esSicEmisionValidado;

	/** Bandera que indica que el depositante es Valido */
	private boolean esSicEmisionValido = false;
	
	/** Almacena los custodios que ya han sido validados */
	private Map custodiosValidos; /* <tv+emisora+serie+cupon+custodio, AgenteVO> */
	
	/** Descripcion del Custodio */
	private String custodio;
	
	/** Bandera que indica que el custodio esta contenido en la lista de custodiosValidos */
	private boolean esCustodioValidado;

	/** Bandera que indica que el custodio es Valido */
	private boolean esCustodioValido = false;
	
	/** Almacena los depositantes que ya han sido validados */
	private List depositantesValidos; /* tv+emisora+serie+cupon+depositante */
	
	/** Descripcion del Depositante*/
	private String depositante;
	
	/** Bandera que indica que el depositante esta contenido en la lista de depositantesValidos */
	private boolean esDepositanteValidado;

	/** Bandera que indica que el depositante es Valido */
	private boolean esDepositanteValido = false;
	
	/** Almacena las divisas que ya han sido validadas */
	private List divisasValidas; /* divisa */
	
	/** Divisa */
	private String divisa;
	
	/** Bandera que indica que la divisa esta contenida en la lista de divisasValidas */
	private boolean esDivisaValidada;

	/** Bandera que indica que la divisa es Valida */
	private boolean esDivisaValida = false;
	
	/** Almacena las emisiones que ya han sido validadas */
	private List emisionesValidas; /* tv+emisora+serie+cupon */
	
	/** EmisionVO */
	private EmisionVO emisionVO;
	
	/** Bandera que indica que la emisionVO esta contenida en la lista de emisionesValidas */
	private boolean esEmisionValidada;

	/** Bandera que indica que la emisionVO es Valida */
	private boolean esEmisionValida = false;
	
	/* Atributos para el manejo de error */
	
	/** Almacena los mensajes de error */
	private StringBuffer msgError;

	/** Almacena los codigos de los campos que han presentado algun error */
	private StringBuffer numCampoError;

	/** Bandera que indica que ocurrio de menos un error */
	private boolean noError;
	
	
	
	/**
	 * Constructor por defecto
	 */
	public ValidaNegocioVO() {
		
	}
	
	/**
	 * Constructor sobrecargado
	 */
	public ValidaNegocioVO(AgenteVO agenteFirmado, Map<Object, Object> indice, String[] campos, 
			StringBuffer msgError, StringBuffer numCampoError, int tam, List bovedasValidas,
			List cuentasTraspasanteValidas, Map custodiosValidos, Map sicEmisionValidos, List depositantesValidos, 
			List divisasValidas, List emisionesValidas, List cuentasValidas, Date fechaActualHrCero) {
		
		this.agenteFirmado =  agenteFirmado;
		this.indice = indice;
		this.campos = campos; 
		this.msgError = msgError; 
		this.numCampoError = numCampoError; 
		this.tam = tam;
		this.bovedasValidas = bovedasValidas;
		this.cuentasTraspasanteValidas = cuentasTraspasanteValidas;
		this.custodiosValidos = custodiosValidos;
		this.sicEmision = sicEmisionValidos;
		this.depositantesValidos = depositantesValidos;
		this.divisasValidas = divisasValidas;
		this.emisionesValidas = emisionesValidas;
		this.fechaActual = fechaActualHrCero;
		this.noError = true;
		this.extraeBoveda();
		this.extraeEmisionVO();
		this.setTipoOperacion(this.obtieneCampo(TIPO_OPER));
		this.extraeCustodio();
		this.extraeDepositante();
		this.extraeDivisa();
		this.extraeAgenteTraspasante();
		this.extraeCuentaTraspasante();
	}
	
	/**
	 * @return the agenteFirmado
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}

	/**
	 * @return the indice
	 */
	public Map<Object, Object> getIndice() {
		return indice;
	}

	/**
	 * @return the campos
	 */
	public String[] getCampos() {
		return campos;
	}

	/**
	 * @return the msgError
	 */
	public StringBuffer getMsgError() {
		return msgError;
	}

	/**
	 * @return the numCampoError
	 */
	public StringBuffer getNumCampoError() {
		return numCampoError;
	}

	/**
	 * @return the tam
	 */
	public int getTam() {
		return tam;
	}

	/**
	 * @return the emisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @return the cuentaRecep
	 */
	public String getCuentaRecep() {
		return cuentaRecep;
	}

	/**
	 * @param agenteFirmado the agenteFirmado to set
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @param indice the indice to set
	 */
	public void setIndice(Map<Object, Object> indice) {
		this.indice = indice;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(String[] campos) {
		this.campos = campos;
	}

	/**
	 * @param msgError the msgError to set
	 */
	public void setMsgError(StringBuffer msgError) {
		this.msgError = msgError;
	}

	/**
	 * @param numCampoError the numCampoError to set
	 */
	public void setNumCampoError(StringBuffer numCampoError) {
		this.numCampoError = numCampoError;
	}

	/**
	 * @param tam the tam to set
	 */
	public void setTam(int tam) {
		this.tam = tam;
	}

	/**
	 * @param emisionVO the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}
	
	/**
	 * Extrae del arreglo campos los datos tv, emisora, serie y cupon
	 * y los settea en la emisionVO.
	 */
	private void extraeEmisionVO(){
		
		if(emisionVO==null){
			emisionVO = new EmisionVO();
		}

		if (StringUtils.isNotBlank(obtieneCampo(TV)) && StringUtils.isNotBlank(obtieneCampo(EMISORA))
				&& StringUtils.isNotBlank(obtieneCampo(SERIE)) && StringUtils.isNotBlank(obtieneCampo(CUPON))) {

			this.getEmisionVO().setTv(obtieneCampo(TV));
			this.getEmisionVO().setEmisora(obtieneCampo(EMISORA));
			this.getEmisionVO().setSerie(obtieneCampo(SERIE));
			this.getEmisionVO().setCupon(obtieneCampo(CUPON));

			this.setEsEmisionValidada(this.getEmisionesValidas().contains(this.getEmisionVO().toResumeString()));

		}
		if (StringUtils.isNotBlank(obtieneCampo(ISIN))) {
			this.getEmisionVO().setIsin(obtieneCampo(ISIN));
		}
			
		
	}
	
	/**
	 * Encapsula la obtenci&oacute;n del valor del campo
	 * @param campo
	 * @return valor
	 */
	private String obtieneCampo(String campo){

		
		String valor = null;
		if(this.getCampos() != null && this.getIndice() != null) {
			Integer i = (Integer) this.getIndice().get(campo);	
			if(i!=null){
				valor = this.getCampos()[i.intValue()];
				valor = StringUtils.isNotBlank(valor) ? valor.trim() : valor;
			}
		}
		return valor;
		
	}
	
	/**
	 *  Extrae del arreglo campos el dato custodio, lo settea en el atributo
	 *  y settea la bandera segun el caso luego de verificar si el custodio ya fue validado.
	 */
	private void extraeBoveda(){
		
		/* No se utiliza el atributo del EmisionVO para garantizar que el atributo no se pierda */
			this.setIdBoveda(new Long(this.obtieneCampo(ID_BOVEDA)));
			this.setEsBovedaValidada(this.getBovedasValidas().contains(this.getIdBoveda()));
		
	}
	
	/**
	 *  Extrae del arreglo campos el dato custodio, lo settea en el atributo
	 *  y settea la bandera segun el caso luego de verificar si el custodio ya fue validado.
	 */
	private void extraeCustodio(){
		
		this.setCustodio(obtieneCampo(CUSTODIO));	
		this.setEsCustodioValidado(this.getCustodiosValidos().containsKey(
				this.getEmisionVO().toResumeString().concat(this.getCustodio())));
		
	}
    
	/**
	 * Extrae del arreglo campos el dato depositante, lo settea en el atributo
	 * y settea la bandera segun el caso luego de verificar si el depositante ya fue validado.
	 */
	private void extraeDepositante(){
		
		this.setDepositante(obtieneCampo(DEPOSITANTE));	
		this.setEsDepositanteValidado(this.getDepositantesValidos().contains(
				this.getEmisionVO().toResumeString().concat(this.getDepositante())));
		
	}
	
	/**
	 * Extrae del arreglo campos el dato divisa, lo settea en el atributo
	 * y settea la bandera segun el caso luego de verificar si la divisa ya fue validada.
	 */
	private void extraeDivisa(){
		
		this.setDivisa(obtieneCampo(DIVISA));	
		this.setEsDivisaValidada(this.getDivisasValidas().contains(this.getDivisa()));
		
	}
	
	/**
	 * Extrae del arreglo campos los datos necesarios para construir el agente traspasante,
	 * y lo settea en el atributo.
	 */
	private void extraeAgenteTraspasante(){
		
		this.setAgenteTraspasante(new AgenteVO(obtieneCampo(ID_INST_VEND), 
				obtieneCampo(FOLIO_INST_VEND), obtieneCampo(CUENTA_VEND)));
		
	}

	/**
	 * Extrae del arreglo campos el dato cuenta traspasante, lo settea en el atributo
	 * y settea la bandera segun el caso luego de verificar si la cuenta ya fue validada.
	 */
	private void extraeCuentaTraspasante(){
		
		this.extraeAgenteTraspasante();

		if(this.getAgenteTraspasante() != null && this.getAgenteTraspasante().tieneClave() 
				&& StringUtils.isNotBlank(this.getAgenteTraspasante().getCuenta())){
			this.setEsCuentaTraspasanteValidada(this.getCuentasTraspasanteValidas().contains(
					this.getAgenteTraspasante().toResumeString()));			
		}
		
	}
	
	/**
	 * @param cuentaRecep the cuentaRecep to set
	 */
	public void setCuentaRecep(String cuentaRecep) {
		this.cuentaRecep = cuentaRecep;
	}

	/**
	 * @return the noError
	 */
	public boolean isNoError() {
		return noError;
	}

	/**
	 * @param noError the noError to set
	 */
	public void setNoError(boolean noError) {
		this.noError = noError;
	}
	
	/**
	 * @return the custodiosValidos
	 */
	public Map getCustodiosValidos() {
		return custodiosValidos;
	}

	/**
	 * @param custodiosValidos the custodiosValidos to set
	 */
	public void setCustodiosValidos(Map custodiosValidos) {
		this.custodiosValidos = custodiosValidos;
	}

	/**
	 * @return the depositantesValidos
	 */
	public List getDepositantesValidos() {
		return depositantesValidos;
	}

	/**
	 * @param depositantesValidos the depositantesValidos to set
	 */
	public void setDepositantesValidos(List depositantesValidos) {
		this.depositantesValidos = depositantesValidos;
	}

	/**
	 * @return the divisasValidas
	 */
	public List getDivisasValidas() {
		return divisasValidas;
	}

	/**
	 * @param divisasValidas the divisasValidas to set
	 */
	public void setDivisasValidas(List divisasValidas) {
		this.divisasValidas = divisasValidas;
	}
	

	/**
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the depositante
	 */
	public String getDepositante() {
		return depositante;
	}

	/**
	 * @param depositante the depositante to set
	 */
	public void setDepositante(String depositante) {
		this.depositante = depositante;
	}

	/**
	 * @return the esCustodioValidado
	 */
	public boolean isEsCustodioValidado() {
		return esCustodioValidado;
	}

	/**
	 * @param esCustodioValidado the esCustodioValidado to set
	 */
	public void setEsCustodioValidado(boolean esCustodioValidado) {
		this.esCustodioValidado = esCustodioValidado;
	}

	/**
	 * @return the esDepositanteValidado
	 */
	public boolean isEsDepositanteValidado() {
		return esDepositanteValidado;
	}

	/**
	 * @param esDepositanteValidado the esDepositanteValidado to set
	 */
	public void setEsDepositanteValidado(boolean esDepositanteValidado) {
		this.esDepositanteValidado = esDepositanteValidado;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the esDivisaValidada
	 */
	public boolean isEsDivisaValidada() {
		return esDivisaValidada;
	}

	/**
	 * @param esDivisaValidada the esDivisaValidada to set
	 */
	public void setEsDivisaValidada(boolean esDivisaValidada) {
		this.esDivisaValidada = esDivisaValidada;
	}

	/**
	 * @return the emisionesValidas
	 */
	public List getEmisionesValidas() {
		return emisionesValidas;
	}

	/**
	 * @param emisionesValidas the emisionesValidas to set
	 */
	public void setEmisionesValidas(List emisionesValidas) {
		this.emisionesValidas = emisionesValidas;
	}
	
	/**
	 * @return the esEmisionValidada
	 */
	public boolean isEsEmisionValidada() {
		return esEmisionValidada;
	}

	/**
	 * @param esEmisionValidada the esEmisionValidada to set
	 */
	public void setEsEmisionValidada(boolean esEmisionValidada) {
		this.esEmisionValidada = esEmisionValidada;
	}
	
	/**
	 * @return boolean
	 */
	public boolean isTraspasoContra(){
		
		return TRASPASO_CONTRA.equalsIgnoreCase(obtieneCampo(TIPO_OPER));
		
	}
	
	/**
	 * @return the agenteTraspasante
	 */
	public AgenteVO getAgenteTraspasante() {
		return agenteTraspasante;
	}

	/**
	 * @param agenteTraspasante the agenteTraspasante to set
	 */
	public void setAgenteTraspasante(AgenteVO agenteTraspasante) {
		this.agenteTraspasante = agenteTraspasante;
	}

	/**
	 * @return the esCuentaTraspasanteValidada
	 */
	public boolean isEsCuentaTraspasanteValidada() {
		return esCuentaTraspasanteValidada;
	}

	/**
	 * @param esCuentaTraspasanteValidada the esCuentaTraspasanteValidada to set
	 */
	public void setEsCuentaTraspasanteValidada(boolean esCuentaTraspasanteValidada) {
		this.esCuentaTraspasanteValidada = esCuentaTraspasanteValidada;
	}

	/**
	 * @return the cuentasTraspasanteValidas
	 */
	public List getCuentasTraspasanteValidas() {
		return cuentasTraspasanteValidas;
	}

	/**
	 * @param cuentasTraspasanteValidas the cuentasTraspasanteValidas to set
	 */
	public void setCuentasTraspasanteValidas(List cuentasTraspasanteValidas) {
		this.cuentasTraspasanteValidas = cuentasTraspasanteValidas;
	}

	/**
	 * Arma el arreglo de string depositante, custodio 
	 * @return
	 */
	public String[] getDepositantecustodio(){
		
		String[] depositanteCustodio = {
				this.isEsDepositanteValidado() ? null : this.getDepositante(), 
						this.isEsCustodioValidado() ? null : this.getCustodio()};
		return depositanteCustodio;
	}
	
	/**
	 * @return boolean
	 */
	public boolean vendedorEsAgenteFirmado(){
		
		return this.getAgenteFirmado().getClave().trim().equalsIgnoreCase(
				(obtieneCampo(ID_INST_VEND) + obtieneCampo(FOLIO_INST_VEND)));
	}
	

	/**
	 * @return the idBoveda
	 */
	public Long getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda the idBoveda to set
	 */
	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @return the bovedasValidas
	 */
	public List getBovedasValidas() {
		return bovedasValidas;
	}

	/**
	 * @param bovedasValidas the bovedasValidas to set
	 */
	public void setBovedasValidas(List bovedasValidas) {
		this.bovedasValidas = bovedasValidas;
	}

	/**
	 * @return the esBovedaValidada
	 */
	public boolean isEsBovedaValidada() {
		return esBovedaValidada;
	}

	/**
	 * @param esBovedaValidada the esBovedaValidada to set
	 */
	public void setEsBovedaValidada(boolean esBovedaValidada) {
		this.esBovedaValidada = esBovedaValidada;
	}

	/**
	 * @return the sicEmision
	 */
	public Map getSicEmision() {
		return sicEmision;
	}

	/**
	 * @param sicEmision the sicEmision to set
	 */
	public void setSicEmision(Map sicEmision) {
		this.sicEmision = sicEmision;
	}

	/**
	 * @return the agenteSic
	 */
	public AgenteVO getAgenteSic() {
		return agenteSic;
	}

	/**
	 * @param agenteSic the agenteSic to set
	 */
	public void setAgenteSic(AgenteVO agenteSic) {
		this.agenteSic = agenteSic;
	}

	/**
	 * @return the esBovedaValida
	 */
	public boolean isEsBovedaValida() {
		return esBovedaValida;
	}

	/**
	 * @param esBovedaValida the esBovedaValida to set
	 */
	public void setEsBovedaValida(boolean esBovedaValida) {
		this.esBovedaValida = esBovedaValida;
	}

	/**
	 * @return the esCuentaTraspasanteValida
	 */
	public boolean isEsCuentaTraspasanteValida() {
		return esCuentaTraspasanteValida;
	}

	/**
	 * @param esCuentaTraspasanteValida the esCuentaTraspasanteValida to set
	 */
	public void setEsCuentaTraspasanteValida(boolean esCuentaTraspasanteValida) {
		this.esCuentaTraspasanteValida = esCuentaTraspasanteValida;
	}

	/**
	 * @return the esCustodioValido
	 */
	public boolean isEsCustodioValido() {
		return esCustodioValido;
	}

	/**
	 * @param esCustodioValido the esCustodioValido to set
	 */
	public void setEsCustodioValido(boolean esCustodioValido) {
		this.esCustodioValido = esCustodioValido;
	}

	/**
	 * @return the esDepositanteValido
	 */
	public boolean isEsDepositanteValido() {
		return esDepositanteValido;
	}

	/**
	 * @param esDepositanteValido the esDepositanteValido to set
	 */
	public void setEsDepositanteValido(boolean esDepositanteValido) {
		this.esDepositanteValido = esDepositanteValido;
	}

	/**
	 * @return the esDivisaValida
	 */
	public boolean isEsDivisaValida() {
		return esDivisaValida;
	}

	/**
	 * @param esDivisaValida the esDivisaValida to set
	 */
	public void setEsDivisaValida(boolean esDivisaValida) {
		this.esDivisaValida = esDivisaValida;
	}

	/**
	 * @return the esEmisionValida
	 */
	public boolean isEsEmisionValida() {
		return esEmisionValida;
	}

	/**
	 * @param esEmisionValida the esEmisionValida to set
	 */
	public void setEsEmisionValida(boolean esEmisionValida) {
		this.esEmisionValida = esEmisionValida;
	}

	/**
	 * @return the fechaActual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * @param fechaActual the fechaActual to set
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
	
	/**
	 * @return the esSicEmisionValidado
	 */
	public boolean isEsSicEmisionValidado() {
		return esSicEmisionValidado;
	}

	/**
	 * @param esSicEmisionValidado the esSicEmisionValidado to set
	 */
	public void setEsSicEmisionValidado(boolean esSicEmisionValidado) {
		this.esSicEmisionValidado = esSicEmisionValidado;
	}

	/**
	 * @return the esSicEmisionValido
	 */
	public boolean isEsSicEmisionValido() {
		return esSicEmisionValido;
	}

	/**
	 * @param esSicEmisionValido the esSicEmisionValido to set
	 */
	public void setEsSicEmisionValido(boolean esSicEmisionValido) {
		this.esSicEmisionValido = esSicEmisionValido;
	}

	/**
	 * @return the agentesSic
	 */
	public AgenteVO[] getAgentesSic() {
		return agentesSic;
	}

	/**
	 * @param agentesSic the agentesSic to set
	 */
	public void setAgentesSic(AgenteVO[] agentesSic) {
		this.agentesSic = agentesSic;
	}
	
	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {

	}

}
