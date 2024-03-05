package com.indeval.portaldali.middleware.services.efectivo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.admonretiros.RetiroEfectivoDao;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.util.Constantes;

public class RetiroEfectivoServiceImpl implements RetiroEfectivoService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private RetiroEfectivoDao retiroEfectivoDao;
	
	@Transactional(readOnly=true)
	public RetiroEfectivoDTO findById(long idOperacionEfectivo) {
		if(idOperacionEfectivo <= 0) throw new IllegalArgumentException("id is required.");
		
		RetiroEfectivo retiro = retiroEfectivoDao.findById(idOperacionEfectivo);
		
		if(retiro == null ) return null;
		
		RetiroEfectivoDTO dto = DTOAssembler.crearRetiroEfectivoDTO(retiro);
		
		return dto;
	}
	
	@Transactional
	public void libera(long idOperacionEfectivo, Map<String, String> datosFirma) throws BusinessException {
		if(idOperacionEfectivo<=0)throw new IllegalArgumentException("id is required.");
		if(datosFirma == null || datosFirma.isEmpty())throw new IllegalArgumentException("datos firma es un dato requerido.");
		
		String usuario = datosFirma.get(Constantes.DATOS_FIRMA__USUARIO);
		String serie = datosFirma.get(Constantes.DATOS_FIRMA__SERIE);
		String firma = datosFirma.get(Constantes.DATOS_FIRMA__ISO_FIRMADO);
		
		RetiroEfectivo retiroEfectivo = retiroEfectivoDao.findById(idOperacionEfectivo);
		
		if(retiroEfectivo == null) throw new BusinessException(String.format("No se encontro la operacion con id [%d].", idOperacionEfectivo ));
		
		retiroEfectivoDao.libera(idOperacionEfectivo, usuario, serie, firma);
	}
	
	@Transactional
	public void autoriza(long idOperacionEfectivo, Map<String, String> datosFirma) throws BusinessException {
		if(idOperacionEfectivo<=0)throw new IllegalArgumentException("id is required.");
		if(datosFirma == null || datosFirma.isEmpty())throw new IllegalArgumentException("datos firma es un dato requerido.");
		
		String usuario = datosFirma.get(Constantes.DATOS_FIRMA__USUARIO);
		String serie = datosFirma.get(Constantes.DATOS_FIRMA__SERIE);
		String firma = datosFirma.get(Constantes.DATOS_FIRMA__ISO_FIRMADO);
		
		RetiroEfectivo retiroEfectivo = retiroEfectivoDao.findById(idOperacionEfectivo);
		
		if(retiroEfectivo == null) throw new BusinessException(String.format("No se encontro la operacion con id [%d].", idOperacionEfectivo ));
		
		retiroEfectivoDao.autoriza(idOperacionEfectivo, usuario, serie, firma);
	}
	
	@Transactional
	public void registra(RetiroEfectivoDTO dto) {
		if(dto == null)throw new IllegalArgumentException("operacionEfectivo no puede ser null.");
		
		RetiroEfectivo retiro  = DTOAssembler.crearRetiroEfectivo(dto); 
				
		logger.info("Salvando OPERACION DE EFECTIVO");
		logger.info(String.valueOf(retiro));
		
		retiroEfectivoDao.save(retiro);
	}

	public void setRetiroEfectivoDao(RetiroEfectivoDao retiroEfectivoDao) {
		this.retiroEfectivoDao = retiroEfectivoDao;
	}
}
