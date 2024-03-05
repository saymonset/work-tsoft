package com.indeval.portaldali.middleware.services.efectivo;

import java.util.Map;

import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;

public interface RetiroEfectivoService {
	
	public RetiroEfectivoDTO findById(long idOperacionEfectivo);
	
	public void libera(long idOperacionEfectivo, Map<String, String> datosFirma) throws BusinessException;
	
	public void autoriza(long idOperacionEfectivo, Map<String, String> datosFirma) throws BusinessException;
	
	public void registra(RetiroEfectivoDTO operacionEfectivo);
}
