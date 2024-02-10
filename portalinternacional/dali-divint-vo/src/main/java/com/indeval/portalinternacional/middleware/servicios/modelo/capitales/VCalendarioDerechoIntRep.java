package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the T_CALENDARIO_INT database table.
 * 
 */
@Entity
@Table(name="V_CALENDARIO_DERECHO_INT")
public class VCalendarioDerechoIntRep extends VCalendarioDerechoIntBase implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<FechasAdicionalesDTO> listaFechasDTO = new ArrayList<FechasAdicionalesDTO>();
    private String narrativa;
	
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
	
	@Transient
	public String getFechasAdicionlesFormatoReporte(){		
		return this.fechasAdicionales != null ? this.fechasAdicionales.replaceAll("</br>", ", ").replace("|",": ") : null;
	}
	
	@Transient
	public String getRatioAdicionalFormatoReporte(){		
		return this.ratioAdicional != null ? this.ratioAdicional.replaceAll("</br>", " ") : null;
	}
	
	@Transient
	public boolean isPermiteEnvio() {
		return true;
	}


	/**
	 * @return the narrativa
	 */
	@Transient
	public String getNarrativa() {
		return narrativa;
	}


	/**
	 * @param narrativa the narrativa to set
	 */
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}
     
	
	
	
}
