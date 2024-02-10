package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ActualizarHorarioController  extends ControllerBase{

	private static final String TO_HORARIOS = "adminHorarioDerecho";
	private Long idCustodio;
	private String porcentajeRet;
	private String dias;
	private String hora;
	private String minuto;
	private String tv;
	private HorarioBeneficiarioVO selectedHorario;
	private String emisora;
	private String serie;
	private String idFolioInstitucion;
	private Boolean esDespuesFechaCorte;
	private List<SelectItem> custodios;
	private ControlBeneficiariosService controlBeneficiariosService;
	private AdminCatalogosBenefService adminCatalogosBenefService;	
	
	public String getInit() {
		if(custodios == null || custodios.isEmpty()){
			inicializaCustodios();
		}
						
		if(selectedHorario == null){
			selectedHorario = (HorarioBeneficiarioVO)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(Constantes.KEY_HORARIO_SELECCIONADO);
			idCustodio = selectedHorario.getIdCuentaNombrada();
			if(selectedHorario.getPorcentajeRet() != null){
				porcentajeRet = selectedHorario.getPorcentajeRet().toString();
			}
			dias = selectedHorario.getDias().toString();
			hora = selectedHorario.getHora().toString();
			minuto = selectedHorario.getMinuto().toString().length() == 1?'0'+selectedHorario.getMinuto().toString():selectedHorario.getMinuto().toString();
			tv = selectedHorario.getTv();
			emisora = selectedHorario.getEmisora();
			serie = selectedHorario.getSerie();
			idFolioInstitucion = selectedHorario.getIdFolio();			
			esDespuesFechaCorte = selectedHorario.getEsDespuesFechaCorte();
		}
		
		return null;
	}
	
	private String validaCampos(){
		String error = null; 
		if(idCustodio == null){
			error = "El custodio es requerido";
		}
		if(StringUtils.isBlank(error) && StringUtils.isNotBlank(porcentajeRet)){
			try {
				if(Float.parseFloat(porcentajeRet) < 0 || Float.parseFloat(porcentajeRet) > Constantes.CIEN_PORCENTAJE){
					error = "El porcentaje de retencion debe estar entre 0 y 100";
				}
			} catch (NumberFormatException e) {
				error = "El porcentaje de retencion debe ser numerico";
			}		
		}
		
		if(StringUtils.isBlank(error) && StringUtils.isBlank(dias)){			
			error = "El campo de dias es requerido y debe ser entero";
		}else if(StringUtils.isBlank(error) && StringUtils.isNotBlank(dias)){
			if(!StringUtils.isNumeric(dias.trim())){
				error = "El campo de dias debe entero";
			}else if(Integer.parseInt(dias) < 0){
				error = "El campo de dias debe ser mayor a cero";
			}			
		}
		if(StringUtils.isBlank(error) && StringUtils.isBlank(hora)){
			error = "El campo de hora es requerido y debe ser entero";
		}else if(StringUtils.isBlank(error) && StringUtils.isNotBlank(hora)){
			if(!StringUtils.isNumeric(hora.trim())){
				error = "El campo de hora debe ser entero";
			}else if(Integer.parseInt(hora) < 0 || Integer.parseInt(hora) > 23){
				error = "El campo de hora debe estar entre cero y 23";
			}
		}			
		if(StringUtils.isBlank(error) && StringUtils.isBlank(minuto)){
			error = "El campo de minuto es requerido y debe ser entero";
		}else if(StringUtils.isBlank(error) && StringUtils.isNotBlank(minuto)){ 
			if(!StringUtils.isNumeric(minuto.trim())){
				error = "El campo de minuto es requerido y debe ser entero";
			}else if(Integer.parseInt(minuto) < 0 || Integer.parseInt(minuto) > 59){
				error = "El campo de minuto debe estar entre cero y 59";
			}
		}		
		if(StringUtils.isBlank(error) && StringUtils.isNotBlank(idFolioInstitucion) && idFolioInstitucion.length() != 5){
			error = "El campo de id folio institucion debe ser de longitud 5";
		}
		
		return error;
	}
	
	public String actualizarHorario() {
		HorarioBeneficiario horarioBeneficiario = null;
		String error = validaCampos();
		
		if(StringUtils.isBlank(error)){
			horarioBeneficiario = new HorarioBeneficiario();
			horarioBeneficiario.setHora(Integer.valueOf(hora));
			horarioBeneficiario.setDias(Integer.valueOf(dias));	
			horarioBeneficiario.setMinuto(Integer.valueOf(minuto));
			horarioBeneficiario.setEmisora(emisora);
			horarioBeneficiario.setEsDespuesFechaCorte(esDespuesFechaCorte);
			horarioBeneficiario.setIdHorario(selectedHorario.getIdHorario());
			if(StringUtils.isNotBlank(idFolioInstitucion) && idFolioInstitucion.length() == 5){
				horarioBeneficiario.setIdInstitucion(Integer.valueOf(idFolioInstitucion.substring(0,2)));
				horarioBeneficiario.setFolioInstitucion(idFolioInstitucion.substring(2));
			}								
			horarioBeneficiario.setIdCuentaNombrada(idCustodio);
			if(StringUtils.isNotBlank(porcentajeRet)){
				horarioBeneficiario.setPorcentajeRet(Float.valueOf(porcentajeRet));
			}			
			horarioBeneficiario.setSerie(serie);
			horarioBeneficiario.setTv(tv);			
			try {
				adminCatalogosBenefService.actualizarHorario(horarioBeneficiario);
				addMessage("El horario se actualizo correctamente");
			} catch (EJBException e) {				
				error = e.getCausedByException().getMessage();
			}
		}
		if(StringUtils.isNotBlank(error)){
			addErrorMessage(error);
		}
		return null;
	}
	
	public String regresar(){		
		return TO_HORARIOS;
	}
	
	private boolean validaEmisionHorario(){
		boolean validaEmision = false;

		if((StringUtils.isNotBlank(tv) && !tv.equalsIgnoreCase(selectedHorario.getTv())) 
				|| (StringUtils.isNotBlank(selectedHorario.getTv()) && !selectedHorario.getTv().equals(tv))){
			validaEmision = true;
		}
		
		if((StringUtils.isNotBlank(emisora) && !emisora.equalsIgnoreCase(selectedHorario.getEmisora())) 
				|| (StringUtils.isNotBlank(selectedHorario.getEmisora()) && !selectedHorario.getEmisora().equals(emisora))){
			validaEmision = true;
		}
		
		if((StringUtils.isNotBlank(serie) && !serie.equalsIgnoreCase(selectedHorario.getSerie())) 
				|| (StringUtils.isNotBlank(selectedHorario.getSerie()) && !selectedHorario.getSerie().equals(serie))){
			validaEmision = true;
		}
		
		if((StringUtils.isNotBlank(idFolioInstitucion) && !idFolioInstitucion.equalsIgnoreCase(selectedHorario.getIdFolio())) 
				|| (StringUtils.isNotBlank(selectedHorario.getIdFolio()) && !selectedHorario.getIdFolio().equals(idFolioInstitucion))){
			validaEmision = true;			
		}		
		return validaEmision;
	}
	
	private void inicializaCustodios() {
		custodios = new ArrayList<SelectItem>();
		
		List<Object[]> lista = controlBeneficiariosService.obtieneAllCatBic();
		
		for( Object[] bene : lista ) {
			custodios.add(new SelectItem((Long)bene[0], (String)bene[1]));
		}
	}

	public void setAdminCatalogosBenefService(
			AdminCatalogosBenefService adminCatalogosBenefService) {
		this.adminCatalogosBenefService = adminCatalogosBenefService;
	}

	public Long getIdCustodio() {
		return idCustodio;
	}

	public String getPorcentajeRet() {
		return porcentajeRet;
	}

	public String getDias() {
		return dias;
	}

	public String getHora() {
		return hora;
	}

	public String getMinuto() {
		return minuto;
	}

	public String getTv() {
		return tv;
	}

	public String getEmisora() {
		return emisora;
	}

	public String getSerie() {
		return serie;
	}

	public String getIdFolioInstitucion() {
		return idFolioInstitucion;
	}

	public Boolean getEsDespuesFechaCorte() {
		return esDespuesFechaCorte;
	}

	public List<SelectItem> getCustodios() {
		return custodios;
	}

	public void setIdCustodio(Long idCustodio) {
		this.idCustodio = idCustodio;
	}

	public void setPorcentajeRet(String porcentajeRet) {
		this.porcentajeRet = porcentajeRet;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setMinuto(String minuto) {
		this.minuto = minuto;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public void setIdFolioInstitucion(String idFolioInstitucion) {
		this.idFolioInstitucion = idFolioInstitucion;
	}

	public void setEsDespuesFechaCorte(Boolean esDespuesFechaCorte) {
		this.esDespuesFechaCorte = esDespuesFechaCorte;
	}

	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}
}
