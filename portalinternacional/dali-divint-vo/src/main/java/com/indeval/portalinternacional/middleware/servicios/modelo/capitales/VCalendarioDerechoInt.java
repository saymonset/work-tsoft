package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;



/**
 * The persistent class for the T_CALENDARIO_INT database table.
 * 
 */
@Entity
@Table(name="V_CALENDARIO_DERECHO_INT")
public class VCalendarioDerechoInt extends VCalendarioDerechoIntBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<FechasAdicionalesDTO> listaFechasDTO = null;
	private List<String> ratioAdicionalLst = null;
	/**
	 * @return the listaFechasDTO
	 */
	@Transient
	public List<FechasAdicionalesDTO> getListaFechasDTO() {
		return listaFechasDTO;
	}


	/**
	 * @param listaFechasDTO the listaFechasDTO to set
	 */
	public void setListaFechasDTO(List<FechasAdicionalesDTO> listaFechasDTO) {
		this.listaFechasDTO = listaFechasDTO;
	}

	/**
	 * @return the ratioAdicionalLst
	 */
	@Transient
	public List<String> getRatioAdicionalLst() {
		return ratioAdicionalLst;
	}


	/**
	 * @param ratioAdicionalLst the ratioAdicionalLst to set
	 */
	public void setRatioAdicionalLst(List<String> ratioAdicionalLst) {
		this.ratioAdicionalLst = ratioAdicionalLst;
	}


	@Override
	public void setFechasAdicionales(String fechasAdicionales) {
		this.fechasAdicionales = fechasAdicionales;
		if (fechasAdicionales != null) {
			String[] arregloLink = fechasAdicionales.split("</br>");
			if (arregloLink != null && arregloLink.length > 0) {
				listaFechasDTO = new ArrayList<FechasAdicionalesDTO>();
				for (int i = 0; i < arregloLink.length; i++) {
					String cadena = arregloLink[i];
					if (cadena.contains("|")) {
						FechasAdicionalesDTO dto = new FechasAdicionalesDTO();
						dto.setClave(StringUtils.substringBefore(cadena, "|").trim());
						dto.setFecha(StringUtils.substringAfter(cadena, "|").trim());
						listaFechasDTO.add(dto);
					}

				}
			}
		}

		super.setFechasAdicionales(fechasAdicionales);
	}
	
	@Override
	public void setRatioAdicional(String ratioAdicionales) {
		this.ratioAdicional = ratioAdicionales;
		if (ratioAdicional != null) {
			String[] arregloLink = ratioAdicional.split("</br>");
			if (arregloLink != null && arregloLink.length > 0) {
				this.ratioAdicionalLst = new ArrayList<String>();
				this.ratioAdicionalLst = Arrays.asList(arregloLink);
			}
		}

		super.setRatioAdicional(ratioAdicional);
	}
	
	@Transient
	public boolean isPermiteEnvio() {
		boolean regreso = false;
		if (this.vEmisionesCalendario != null) {
			if ("LISTADA".equals(this.vEmisionesCalendario)) {
				regreso = true;
			}
		}
		return regreso;
	}

}
