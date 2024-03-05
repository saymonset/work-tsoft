/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2018 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.constantes.ConstantesComunes;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoTenenciaConstants;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.dao.util.RInstitucionPerfilEmisionDao;
import com.indeval.portaldali.persistence.model.CuentaNombrada;

/**
 * Implementacion de la interfaz ValidadorCuentasEmision
 * 
 * @author Pablo Balderas
 *
 */
public class ValidadorCuentasEmisionImpl implements ValidadorCuentasEmision {

	/** Dao para la consulta de instituciones */
	private InstitucionDaliDAO institucionDaliDAO;

	/** Dao par la consulta de cuentas nombradas */
	private CuentaNombradaDaliDao cuentaNombradaDaliDao;

	/** Dao para consulta la emision */
	private EmisionDaliDAO emisionDaliDAO;
	
	/** Dao para la validacion de institucion-perfil / roles */
	private RInstitucionPerfilEmisionDao rInstitucionPerfilEmisionDao;
	
	/** Bean para el manejo de mensaje */
	private MessageResolver messageResolver;

	/** Objetos auxiliares para las validaciones */
	private InstitucionDTO institucionTraspasante;
	private InstitucionDTO institucionReceptor;
	private CuentaNombrada cuentaReceptor;
	private EmisionDTO emisionDto;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.util.ValidadorCuentasEmision#validarCuentasEmisionMercadoDinero(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
	 */
	public void validarCuentasEmisionMercadoDinero(AgenteVO traspasante, AgenteVO receptor, EmisionVO emision) throws BusinessException {
		//Valida que los argumentos no sean nulos
		if(traspasante != null && receptor != null && emision != null) {
			//Obtiene las instituciones traspasante y receptor
			institucionTraspasante = 
				institucionDaliDAO.buscarInstitucionPorClaveYFolio(traspasante.getId() + traspasante.getFolio());
			institucionReceptor = 
				institucionDaliDAO.buscarInstitucionPorClaveYFolio(receptor.getId() + receptor.getFolio());
			//Obtiene la cuenta receptor			
			cuentaReceptor = 
				cuentaNombradaDaliDao.obtenerCuentaNombradaPorIdInstitucionCuenta(
					institucionReceptor.getId(), receptor.getCuenta());
			//Obtiene la emision
			emisionDto = emisionDaliDAO.consultarEmisionLiberadaPorTVEmisoraSerie(
				emision.getIdTipoValor(), emision.getEmisora(), emision.getSerie());
			
			if(!(validarExcepcionesTraspasante() || validarExcepcionesReceptor())) {				
				//1) Si la cuenta traspasante es de circulacion y la cuenta receptora es de emision
				//2) El traspasante y el receptor es el mismo
				//3) La institucion no es el representante 
				if(validarCuentasTraspasanteReceptor()) {
					if(!(validarMismaInstitucionTraspasanteReceptor() && validarInstitucionAgenteColocadorEmision())) {					
						limpiarObjetosAuxiliares();
						throw new BusinessException(messageResolver.getMessage("errorControlCuentaEmision"));
					}
				}	
			}
		}
		limpiarObjetosAuxiliares();
	}

	/**
	 * Marca los objetos auxiliares a nulos para evitar problemas de memoria.
	 */
	private void limpiarObjetosAuxiliares() {
		institucionTraspasante = null;
		institucionReceptor = null;
		cuentaReceptor = null;
		emisionDto = null;		
	}
	
	/**
	 * Valida si la institucion traspasante esta dentro de las siguientes excepciones:
	 * 1) Indeval 12001
	 * 2) BANXICO 02033
	 * 3) BAXICOSP 02088
	 * @return true si cumple la condicion; false en caso contrario.
	 */
	private boolean validarExcepcionesTraspasante() {
		return (ConstantesComunes.ID_FOLIO_INDEVAL.equals(institucionTraspasante.getIdFolio())) || 
			(ConstantesComunes.ID_FOLIO_BANXICO.equals(institucionTraspasante.getIdFolio())) || 
			(ConstantesComunes.ID_FOLIO_BAXICOSP.equals(institucionTraspasante.getIdFolio()));
	}
	
	/**
	 * Valida si la institucion receptora esta dentro de las siguientes excepciones:
	 * 1) Indeval 12001
	 * 2) BANXICO 02033
	 * 3) BAXICOSP 02088
	 * @return true si cumple la condicion; false en caso contrario.
	 */
	private boolean validarExcepcionesReceptor() {
		return (ConstantesComunes.ID_FOLIO_INDEVAL.equals(institucionReceptor.getIdFolio())) || 
			(ConstantesComunes.ID_FOLIO_BANXICO.equals(institucionReceptor.getIdFolio())) || 
			(ConstantesComunes.ID_FOLIO_BAXICOSP.equals(institucionReceptor.getIdFolio()));
	}
	
	/**
	 * 
	 * @param cuentaTraspasante
	 * @param cuentaReceptor
	 * @return
	 */
	private boolean validarCuentasTraspasanteReceptor() {
		return TipoTenenciaConstants.EMISION.equals(cuentaReceptor.getTipoCuenta().getTipoTenencia());
	}
	
	/**
	 * 
	 * @param institucionTraspasante
	 * @param institucionReceptor
	 * @return
	 */
	private boolean validarMismaInstitucionTraspasanteReceptor() {
		return institucionTraspasante.getId() == institucionReceptor.getId(); 
	}
	
	/**
	 * 
	 * @param institucionTraspasante
	 * @param emisionDto
	 * @return
	 */
	private boolean validarInstitucionAgenteColocadorEmision() {
		return rInstitucionPerfilEmisionDao.validarRolAgenteColocadorPorIdInstitucionIdEmision(
			institucionReceptor.getId(), emisionDto.getId());
	}
	

	/**
	 * Metodo para establecer el atributo institucionDaliDAO
	 * @param institucionDaliDAO El valor del atributo institucionDaliDAO a establecer.
	 */
	public void setInstitucionDaliDAO(InstitucionDaliDAO institucionDaliDAO) {
		this.institucionDaliDAO = institucionDaliDAO;
	}


	/**
	 * Metodo para establecer el atributo cuentaNombradaDaliDao
	 * @param cuentaNombradaDaliDao El valor del atributo cuentaNombradaDaliDao a establecer.
	 */
	public void setCuentaNombradaDaliDao(CuentaNombradaDaliDao cuentaNombradaDaliDao) {
		this.cuentaNombradaDaliDao = cuentaNombradaDaliDao;
	}



	/**
	 * Metodo para establecer el atributo emisionDaliDAO
	 * @param emisionDaliDAO El valor del atributo emisionDaliDAO a establecer.
	 */
	public void setEmisionDaliDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}



	/**
	 * Metodo para establecer el atributo rInstitucionPerfilEmisionDao
	 * @param rInstitucionPerfilEmisionDao El valor del atributo rInstitucionPerfilEmisionDao a establecer.
	 */
	public void setrInstitucionPerfilEmisionDao(RInstitucionPerfilEmisionDao rInstitucionPerfilEmisionDao) {
		this.rInstitucionPerfilEmisionDao = rInstitucionPerfilEmisionDao;
	}

	/**
	 * Metodo para establecer el atributo messageResolver
	 * @param messageResolver El valor del atributo messageResolver a establecer.
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}


}
