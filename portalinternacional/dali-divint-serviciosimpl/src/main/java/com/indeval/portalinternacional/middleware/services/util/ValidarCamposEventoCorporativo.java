package com.indeval.portalinternacional.middleware.services.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesDTO;

public interface ValidarCamposEventoCorporativo {

	  
	public void validaCamposGenerales(EventoCorporativo params)throws BusinessException;
	
	public void validaCuerpoEvco(CuerpoEventoCorporativo params)throws BusinessException;
	
	public void validaNotasEvco(String params)throws BusinessException;
	
	public void validaOpcionesEvco(String params,Date fechaTiPa,Date fechaIn, Date fechaCli)throws BusinessException;
	
	public void validaTamanioListaOpcionesEvco(EventoCorporativo params,Map<String, OpcionesDTO>  mapOpciones)throws BusinessException;
	
	public void validaTamanioListaOpcionesEvco(EventoCorporativo params, List listaOpciones)throws BusinessException;
	
	public void validaNotificacionesEvco(String textNotificacion,String destinatario ,Date fechaIni, Date fechaFin)throws BusinessException;
	
	public void validarValidacionesEvco(String validacion ,String operador, String valor)throws BusinessException;
	
	public void validaOpcionesDefault(List<OpcionesDTO> listOpciones) throws BusinessException;
	public void validaAdjuntos(List<AdjuntosEventoCorporativo> listAdjuntos) throws BusinessException;
	
}
