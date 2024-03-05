/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.filetransfer;

import java.util.List;

/**
 * Clase que representa el layout para los procesos de file transfer.
 * 
 * @author Pablo Balderas.
 */
public class LayoutFileTransferDto {

	/** Clase a la que se parsean los datos */
	private Class<?> beanClass;
	
	/** Lista con los campos del formato */
	private List<CampoRegFileTransDto> camposLayout;

	/**
	 * @return the camposLayout
	 */
	public List<CampoRegFileTransDto> getCamposLayout() {
		return camposLayout;
	}

	/**
	 * @param camposLayout the camposLayout to set
	 */
	public void setCamposLayout(List<CampoRegFileTransDto> camposLayout) {
		this.camposLayout = camposLayout;
	}

	/**
	 * @return the beanClass
	 */
	public Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * @param beanClass the beanClass to set
	 */
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	
}
