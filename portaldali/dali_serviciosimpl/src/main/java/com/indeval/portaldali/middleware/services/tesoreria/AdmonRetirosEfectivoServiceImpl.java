/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.ClobImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.util.Constantes;
import com.indeval.portaldali.persistence.dao.admonretiros.RetiroEfectivoDao;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.dao.admoncuentas.CuentasRetiroEfectivoDao;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.EstadoInstruccionCat;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;
import com.indeval.portaldali.persistence.model.RetiroEfectivoInternacional;
import com.indeval.portaldali.persistence.model.CuentaRetiroInternacional;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.service.Efectivo;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;


/**
 * Implementacion del servicio para la administracion de retiro de efectivo
 * 
 * @author Maria C. Buendia
 */
public class AdmonRetirosEfectivoServiceImpl implements AdmonRetirosEfectivoService{
    
	/** Log de clase */
    private static final Logger logger = LoggerFactory.getLogger(AdmonRetirosEfectivoServiceImpl.class);
    
    /** primer estado de los retiros */
    private static final long PRIMER_ESTADO = 19;
    
    /** dao retiro */
    private RetiroEfectivoDao retiroDao;
    
    /** dao estados de cuenta */
    private EstadoInstruccionDaliDAO edosDao;
    
    /** dao cuenta retiro */
    private CuentasRetiroEfectivoDao cuentaRetiroDao;     
    
    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;
    
	/** Bean para acceso al servicio de operaciones de efectivo */
	private Efectivo efectivo;
    
    
	/**
	 * Guarda un nuevo retiro
	 * @param retiro objeto retiro
	 */
	public void crearRetiro(RetiroEfectivoInternacionalDTO retiroDTO){
		logger.debug("creando nuevo retiro internacional...");
		RetiroEfectivoInternacional retiro = new RetiroEfectivoInternacional();
		
		retiro.setBoveda(DTOAssembler.crearBoveda(retiroDTO.getBoveda()));
		retiro.setConceptoPago(retiroDTO.getConceptoPago());
		
		//obteniendo la cuenta de retiro
		//logger.debug("retiroDTO.getCuentaBeneficiarioDTO().getIdCuentaRetiroInt(): "+retiroDTO.getCuentaBeneficiarioDTO().getIdCuentaRetiroInt());
		retiro.setIdCuentaBeneficiario(retiroDTO.getIdCuentaBeneficiario());
		
		retiro.setDivisa(DTOAssembler.crearDivisa(retiroDTO.getDivisa()));
	
		//obteniendo el objeto Estado
		EstadoInstruccionDTO estado = edosDao.consultarEstadoInstruccionPorId(PRIMER_ESTADO); 
		EstadoInstruccionCat estadoCat = new EstadoInstruccionCat();
		estadoCat.setIdEstadoInstruccion(BigInteger.valueOf(estado.getIdEstadoInstruccion()));
		retiro.setEstado(estadoCat);
		
		retiro.setFechaCreacion(new Date());
		retiro.setFechaValor(retiroDTO.getFechaValor());
		retiro.setImporteTraspaso(retiroDTO.getImporteTraspaso());
		retiro.setUsuarioCreacion(StringUtils.isEmpty((String)retiroDTO.getDatosFirmas().get("creacion_usuario"))? 
						null
						:((String)retiroDTO.getDatosFirmas().get("creacion_usuario")));
		retiro.setSerieCreacion(StringUtils.isEmpty((String)retiroDTO.getDatosFirmas().get("creacion_serie"))? 
						null
						:((String)retiroDTO.getDatosFirmas().get("creacion_serie")));
		retiro.setCreacionFirmada(StringUtils.isEmpty((String)retiroDTO.getDatosFirmas().get("creacion_isoFirmado"))? 
						null
						:new ClobImpl(((String)retiroDTO.getDatosFirmas().get("creacion_isoFirmado"))));
		
		//obteniendo la institucion
		retiro.setInstitucion(DTOAssembler.crearInstitucion( retiroDTO.getInstitucion()));
		
		retiro.setReferenciaOperacion(retiroDTO.getReferenciaOperacion());
		retiro.setReferenciaMensaje(retiroDTO.getReferenciaMensaje());
		
		logger.debug("salvando retiro internacional...");
		retiroDao.save(retiro);
	}
	
	/**
	 * Guarda un nuevo retiro nacional
	 * @param retiro
	 */
	public void crearRetiro(RetiroEfectivoDTO retiroDTO){
		logger.debug("creando nuevo retiro nacional...");
		RetiroEfectivo retiro = new RetiroEfectivo();
		
		//obteniendo la boveda
		Boveda boveda = new Boveda();
		boveda.setIdBoveda(BigInteger.valueOf(retiroDTO.getBoveda().getId()));
		retiro.setBoveda(boveda);
		
		retiro.setReferencia(retiroDTO.getReferencia());
		retiro.setConceptoPago(retiroDTO.getConceptoPago());
		
		// Ahora se guardan los datos del beneficiario en RetiroEfectivo
		//retiro.setIdCuentaBeneficiario(0l);
		retiro.setCuentaBeneficiario( retiroDTO.getCuentaBeneficiario() );
		// retiro.setNombreBeneficiario( retiroDTO.getNombreBeneficiario() );
		
		//obteniendo el objeto Divisa
		Divisa divisa = new Divisa();
		divisa.setIdDivisa(BigInteger.valueOf(retiroDTO.getDivisa().getId()));
		retiro.setDivisa(divisa);
		
		//obteniendo el objeto Estado
		EstadoInstruccionDTO estado = edosDao.consultarEstadoInstruccionPorId(PRIMER_ESTADO); 
		EstadoInstruccionCat estadoCat = new EstadoInstruccionCat();
		estadoCat.setIdEstadoInstruccion(BigInteger.valueOf(estado.getIdEstadoInstruccion()));
		retiro.setEstado(estadoCat);
		
		retiro.setFechaCreacion(new Date());
		retiro.setImporteTraspaso(retiroDTO.getImporteTraspaso());
		retiro.setUsuarioCreacion(StringUtils.isEmpty((String)retiroDTO.getDatosFirmas().get("creacion_usuario"))? 
									null
									:((String)retiroDTO.getDatosFirmas().get("creacion_usuario")));
		retiro.setSerieCreacion(StringUtils.isEmpty((String)retiroDTO.getDatosFirmas().get("creacion_serie"))? 
									null
									:((String)retiroDTO.getDatosFirmas().get("creacion_serie")));
		retiro.setCreacionFirmada(StringUtils.isEmpty((String)retiroDTO.getDatosFirmas().get("creacion_isoFirmado"))? 
									null
									:new ClobImpl(((String)retiroDTO.getDatosFirmas().get("creacion_isoFirmado"))));
		
		//obteniendo la institucion
		Institucion institucion = new Institucion();
		institucion.setIdInstitucion(BigInteger.valueOf(retiroDTO.getInstitucion().getId()));
		retiro.setInstitucion(institucion);
		
		//obtieniendo institucion beneficiaria
		Institucion beneficiario = new Institucion();
		beneficiario.setIdInstitucion(BigInteger.valueOf(retiroDTO.getIdInstReceptor().getId()));
//		beneficiario.setIdInstitucion(BigInteger.valueOf(retiroDTO.getInstitucion().getId()));
		retiro.setIdInstReceptor(beneficiario);
		
		retiro.setReferenciaOperacion(retiroDTO.getReferenciaOperacion());
		retiro.setReferenciaMensaje(retiroDTO.getReferenciaMensaje());
		
		logger.debug("salvando retiro nacional...");
		retiroDao.save(retiro);
	}
	
	/**
	 * Cambia el estado de un retiro. 
	 * @param retiro objeto retiro
	 * @param edoNuevo id del nuevo estado al que cambiar el retiro
	 */
	public void cambiarEstadoRetiro(Object retiro, BigInteger edoNuevo){
		logger.debug("cambiando estado retiro internacional, edoNuevo="+edoNuevo);
		RetiroEfectivoInternacional retiroInt = (RetiroEfectivoInternacional)retiro;
		retiroDao.saveOrUpdate(retiroInt);
	}
	
	/**
	 * Cambia el estado de un conjunto de retiros.
	 * @param retiros lista de objetos retiro
	 * @param edoNuevo id del nuevo estado al que cambiarn los retiros
	 */
	public void cambiarEstadoRetiro(List<Object> retiros, BigInteger edoNuevo){
		logger.debug("cambiando estado retiros, edoNuevo="+edoNuevo);	
		for(int i=0; i<retiros.size(); i++){
			cambiarEstadoRetiro(retiros.get(i),edoNuevo);
		}
	}
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de retiro de efectivo
	 * 
	 * @param agente traspasante
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearRetiro( DivisaDTO divisa, BigDecimal importe) throws BusinessException{
		logger.debug("Entrando a businessRulesCrearRetiro()");
		
		//que no sean pesos mexicanos
		if(divisa.getId() == DaliConstants.ID_DIVISA_MEXICAN_PESO){
			throw new BusinessException(errorResolver.getMessage("J0210", new Object[] {"La divisa debe ser diferente a Pesos Mexicanos."}), "J0210");
		}
		
		//que el importe sea mayor a cero
        if(importe.compareTo(Constantes.CERO_BIG_DECIMAL) <= Constantes.CERO_INT){
        	throw new BusinessException(errorResolver.getMessage("J0210", new Object[] {"No se puede retirar fondos" }), "J0210");
        }
		
        /*
        Constantes - static final String SECUENCIA_FOLIO_CONTROL = "SEQ_SLV_FOLIADOR";
        BigInteger folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL);
    	log.debug("El folio control = [" + folioControl + "]");

		return folioControl; 
         */
		
		logger.debug("Saliendo de businessRulesCrearCuentaRetiro()");
		return true;
	}
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de retiro de efectivo banca comercial
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearRetiroBancaComercial(RetiroEfectivoDTO cre) throws BusinessException{
		logger.debug("Entrando a businessRulesCrearRetiroBancaComercial() v con BusinessException");
		
		//que sean pesos mexicanos
		if(cre.getDivisa().getId() != DaliConstants.ID_DIVISA_MEXICAN_PESO){
			throw new BusinessException(errorResolver.getMessage("J0138", new Object[] {"La divisa debe ser Pesos Mexicanos."}), "J0138");
		}		
		
		//que el importe sea mayor a cero 
        if(cre.getImporteTraspaso().compareTo(Constantes.CERO_BIG_DECIMAL) <= Constantes.CERO_INT){
        	throw new BusinessException(errorResolver.getMessage("J0010", new Object[] {"El importe est\u00e1 vac\u00edo o es menor que cero."}), "J0010");
        }
        
		//que el importe no sea mayor al limite por transaccion  (si aplica)
/*        if(cre.getCuentaRetiroEfectivo().getMontoMaximoXTran()!=null  
        		&& cre.getImporteTraspaso().doubleValue() > cre.getCuentaRetiroEfectivo().getMontoMaximoXTran().doubleValue()){
        	throw new BusinessException(errorResolver.getMessage("J0139", new Object[] {"El importe es mayor al l\u00edmite por transacci\u00f3n definido para la cuenta beneficiario."}), "J0139");
        }
*/		
        //que el importe sea de 15 digitos decimales (quedo en la pantalla en 13, 2 decimales x retiro 202)
		//que la referencia numerica sea de 7 digitos (quedo en la pantalla)
		//concepto de pago de 32-40 (quedo en la pantalla 35 mientras)
		//cuenta relacionada al receptor, vigente y activa (quedo en la pantalla, solo las que estan en estado liberado se muestran)
		
		/* 
		 * Que las siguientes no se validen aqui... solo en el mos
		 * que el importe no sea mayor al limite diario (si aplica)
		 * que el importe no sea mayor al limite mensual (si aplica)
		 * que el movimiento no sea mayor al numero permitido por mes (si aplica)
		 */
		
		logger.debug("Saliendo de businessRulesCrearRetiroBancaComercial()");
		return true;
	}

	
	/**
	 * Obtiene las cuentas beneficiario en base a la divisa
	 * @param idDivisa id de la divisa seleccionada 
	 */

    @SuppressWarnings("finally")
	public String generaIso(RetiroEfectivoInternacionalDTO retiroEfectivoInternacionalDTO) 
    {		
    	String strReturn="";
    		try {
    			strReturn = creaIsoRetiroEfectivo(retiroEfectivoInternacionalDTO);
				 
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocoloFinancieroException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				return strReturn;	
			}
			
    }
	
    
	private String creaIsoRetiroEfectivo(Object vo) throws ProtocoloFinancieroException, BusinessException {
		String iso = "";
		RetiroEfectivoVO reteVO = new RetiroEfectivoVO();
		try{
			if (vo instanceof RetiroEfectivoInternacionalDTO ){
				RetiroEfectivoInternacionalDTO reteIntlDTO = (RetiroEfectivoInternacionalDTO) vo;
				reteVO = DTOAssembler.creaRetiroEfectivoVOByRetiroEfectivoInternacionalDTO(reteIntlDTO);
				iso = efectivo.retiro103(reteVO);	
				//iso = getMessageAsString(generaMsgRetiroEfectivo103(reteVO));
										 
			}
			else if (vo instanceof RetiroEfectivoVO ){
				reteVO = (RetiroEfectivoVO) vo;
				reteVO.setBeneficiario(reteVO.getBeneficiario().trim());
				reteVO.setCuentaBeneficiaria(reteVO.getCuentaBeneficiaria().trim());
				reteVO.setReferenciaMensaje(reteVO.getReferenciaMensaje().trim());
				reteVO.setTipoInstruccion(reteVO.getTipoInstruccion().trim());
				reteVO.setReferenciaOperacion(reteVO.getReferenciaOperacion().trim());
				iso = efectivo.retiro(reteVO);
			}
		}catch (ProtocoloFinancieroException e){
			//System.out.println("+++++++++++++++++++ ProtocoloFinancieroException error = " + e.getMessage());
		}catch(Exception e){
			//System.out.println("+++++++++++++++++++ error = " + e.getMessage());
			//FVU poner el logger
		}
		return iso;
	}	
	
	public InstitucionDTO getInstitucionForClaveSpei(String claveSpei){
		return null;
	}
    
	/**
	 * Obtiene las cuentas beneficiario en base a la divisa
	 * @param idDivisa id de la divisa seleccionada 
	 */
	public List<CuentaRetiroInternacionalDTO> getCuentasInterXDivisa(BigInteger idDivisa){
		logger.debug("Entrando a getCuentasInterXDivisa, idDivisa="+idDivisa);
		List<CuentaRetiroInternacional> cuentas = cuentaRetiroDao.getCuentasInterXDivisa(idDivisa);
		List<CuentaRetiroInternacionalDTO> cuentasDTO = new ArrayList<CuentaRetiroInternacionalDTO>(0); 
		
		for(CuentaRetiroInternacional cuenta: cuentas){
			cuentasDTO.add(DTOAssembler.crearCuentaRetiroInternacionalDTO(cuenta));
		}
		
		return cuentasDTO;
	}
	
	/**
	 * Busca las cuentas por prefijo
	 * @param criterio datos para realizar la consulta 
	 * @param esNacional define si es nacional o no
	 * @return lista de objetos CuentaRetiroEfectivoDTO
	 */
	public List<CuentaRetiroEfectivoDTO> buscarCuentasPorPrefijo(CriterioCuentaEfectivoDTO criterio, boolean esNacional){
		logger.debug("Entrando a buscarCuentasPorPrefijo, prefijoAjustado="+criterio.getCuentaBeneficiario()+", esNacional:"+esNacional);
		List<CuentaRetiroEfectivoDTO> cuentasDTO = cuentaRetiroDao.getCuentas(criterio, esNacional, false,null);
		return cuentasDTO==null?new ArrayList<CuentaRetiroEfectivoDTO>(0):cuentasDTO;
	}
	
	/**
	 * Busca las cuenta clabe de la Institucion
	 * @param String cuentaBeneficiarioInsitucion
	 */
	public CuentaRetiroEfectivoDTO buscarCuentaBeneficiario(String cuentaBeneficiarioInsitucion, Long idInstitucion) {
		return cuentaRetiroDao.getCuentaBeneficiario(cuentaBeneficiarioInsitucion, idInstitucion);
	}
	
	/**
	 * Busca una cuenta por numero de cuenta
	 * @param numeroCuenta numero de cuenta
	 * @param esNacional define si es nacional o no
	 * @return objeto de tipo CuentaRetiroEfectivoDTO
	 */
	public CuentaRetiroEfectivoDTO buscarCuentaPorCriterio(CriterioCuentaEfectivoDTO criterio, boolean esNacional){
		logger.debug("Entrando a buscarCuentaPorCriterio, esNacional:"+esNacional);
		List<CuentaRetiroEfectivoDTO> cuentasDTO = cuentaRetiroDao.getCuentas(criterio, esNacional, true,null);
		return cuentasDTO != null && cuentasDTO.size() != 0 ? cuentasDTO.get(0): null;
	}

	/**
	 * Busca un retiro de efectivo por referencia operacion
	 * @param referenciaOperacion
	 * @param esNacional define si es nacional o no 
	 * @return RetiroEfectivoInternacionalDTO o RetiroEfectivoDTO
	 */
	public Object buscarRetiroConCuentaPorReferenciaOperacion(String referenciaOperacion, boolean esNacional){
		Object retiroDTO = retiroDao.getRetiro(referenciaOperacion, esNacional);
		
		return retiroDTO;
	}
	
	public RetiroEfectivoDao getRetiroDao() {
		return retiroDao;
	}

	public void setRetiroDao(RetiroEfectivoDao retiroDao) {
		this.retiroDao = retiroDao;
	}

	public EstadoInstruccionDaliDAO getEdosDao() {
		return edosDao;
	}

	public void setEdosDao(EstadoInstruccionDaliDAO edosDao) {
		this.edosDao = edosDao;
	}

	public MessageResolver getErrorResolver() {
		return errorResolver;
	}

	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	public CuentasRetiroEfectivoDao getCuentaRetiroDao() {
		return cuentaRetiroDao;
	}

	public void setCuentaRetiroDao(CuentasRetiroEfectivoDao cuentaRetiroDao) {
		this.cuentaRetiroDao = cuentaRetiroDao;
	}

	public Efectivo getEfectivo() {
		return efectivo;
	}

	public void setEfectivo(Efectivo efectivo) {
		this.efectivo = efectivo;
	}
	
}
