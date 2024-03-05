/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import com.indeval.portaldali.middleware.dto.filetransfer.CampoRegFileTransDto;
import com.indeval.portaldali.middleware.dto.filetransfer.LayoutFileTransferDto;

/**
 * Parser para los archivos de file transfer.
 * 
 * @author Pablo Balderas
 */
public class ParserFileTransfer {

	/** Mapa con los formatos de miscelanea fiscal. */
	private Map<String, LayoutFileTransferDto> layouts;

	/**
	 * Parsea un registro de file transfer y obtiene una lista con los campos que conforman el registro.
	 * @param registro Registro a parsear.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @return Lista con los campos que conforman el registro.
	 */
	public List<CampoRegFileTransDto> parsearRegistroFileTransfer(String registro, String tipoFileTransfer) {
		List<CampoRegFileTransDto> camposRegistro = null;
		//Obtenemos el layout asociado al tipo de file transfer.
		LayoutFileTransferDto layout = layouts.get(tipoFileTransfer);
		//Obtenemos la lista de los campos del registro
		camposRegistro = obtenerCamposRegistro(layout);
		//Obtenemos los valores de los campos del registro
		obtenerValoresCamposRegistro(registro, camposRegistro);
		//Retornamos la lista con los campos del registro
		return camposRegistro;
	}

    /**
     * Obtiene el objeto que representa el formato
     * @param valoresDeAtributos Mapa con los campos y los valores a setear.
     * @return Objeto con los valores seteados.
     */
    public Object obtenerObjectoDeCampos(String tipoFileTransfer, List<CampoRegFileTransDto> camposRegistro) {
		try {
			//Obtenemos el layout asociado al tipo de file transfer.
			LayoutFileTransferDto layout = layouts.get(tipoFileTransfer);
			Map<String,Object> valoresDeAtributos = obtenerValoresDeAtributosDeCadena(camposRegistro);
			Object object = layout.getBeanClass().newInstance();
	        DataBinder binder = new DataBinder(object);
	        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
	        mutablePropertyValues.addPropertyValues(valoresDeAtributos);
	        binder.bind(mutablePropertyValues);	        	   
	        return object;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    
    /**
     * Obtiene la lista de campos que conforman el layout.
     * @param layout Layout
     * @return Lista de campos
     */
    private List<CampoRegFileTransDto> obtenerCamposRegistro(LayoutFileTransferDto layout) {
    	List<CampoRegFileTransDto> camposRegistro = new ArrayList<CampoRegFileTransDto>();
    	for (CampoRegFileTransDto campoRegFileTrans : layout.getCamposLayout()) {
    		CampoRegFileTransDto campoRegFileTransDto = new CampoRegFileTransDto();
    		campoRegFileTransDto.setCampoCorrecto(campoRegFileTrans.isCampoCorrecto());
    		campoRegFileTransDto.setEtiquetaCampo(campoRegFileTrans.getEtiquetaCampo());
    		campoRegFileTransDto.setLongitudCampo(campoRegFileTrans.getLongitudCampo());
    		campoRegFileTransDto.setNombreCampo(campoRegFileTrans.getNombreCampo());
    		campoRegFileTransDto.setNumeroCampo(campoRegFileTrans.getNumeroCampo());
    		campoRegFileTransDto.setRequerido(campoRegFileTrans.isRequerido());
    		campoRegFileTransDto.setValorCampo(campoRegFileTrans.getValorCampo());
    		camposRegistro.add(campoRegFileTransDto);
		}
    	return camposRegistro;
    }
    
    
	/**
	 * Obtiene los valores de los campos de un registro.
	 * @param registro Registro del cual se obtendran los valores.
	 * @param camposRegistro Lista con los campos que conforman el registro.
	 */
	private void obtenerValoresCamposRegistro(String registro, List<CampoRegFileTransDto> camposRegistro) {
		int posicion = 0;
		for (CampoRegFileTransDto campoRegFileTransDto : camposRegistro) {
			String valorCampo = registro.substring(posicion, posicion + campoRegFileTransDto.getLongitudCampo());
			campoRegFileTransDto.setValorCampo(valorCampo.trim());
			posicion += campoRegFileTransDto.getLongitudCampo();
		}
	}
	

    private Map<String,Object> obtenerValoresDeAtributosDeCadena(List<CampoRegFileTransDto> camposRegistro){
    	Map<String,Object> valoresDeAtributos = new HashMap<String, Object>();
    	for (CampoRegFileTransDto campo : camposRegistro) {
			valoresDeAtributos.put(campo.getNombreCampo(), campo.getValorCampo());
		}
    	return valoresDeAtributos;
    }
	


	/**
	 * @return the layouts
	 */
	public Map<String, LayoutFileTransferDto> getLayouts() {
		return layouts;
	}

	/**
	 * @param layouts the layouts to set
	 */
	public void setLayouts(Map<String, LayoutFileTransferDto> layouts) {
		this.layouts = layouts;
	}


}
