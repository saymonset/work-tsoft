package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * Entidad de la vista V_HISTORICO_DERECHO_INT
 * @author javier.perez
 *
 */
@Entity
@Table(name = "V_HISTORICO_DERECHO_INT")
public class VCalendarioHistoricoDerechosCapitales extends VCalendarioHistoricoDerechosCapitalesBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
				this.listaFechasDTO = new ArrayList<FechasAdicionalesDTO>();
				for (int i = 0; i < arregloLink.length; i++) {
					String cadena = arregloLink[i];
					if(cadena.contains("|")){
						FechasAdicionalesDTO dto = new FechasAdicionalesDTO();
						dto.setClave(StringUtils.substringBefore(cadena,"|").trim());							
						dto.setFecha(StringUtils.substringAfter(cadena,"|").trim());
						listaFechasDTO.add(dto);
					}
				}
			}		
		}	   
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
	}
	
	@Transient
    public boolean isPermiteEnvio() {
        boolean regreso = false;
        if(this.liga != null){
        	regreso = true;
        }else if(this.listada!=null){
            if("LISTADA".equals(this.listada)){
                regreso = true;
            }
        }
        return regreso;
    }

		
}
