/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 * 
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaMILA;
import com.indeval.portaldali.middleware.formatosw.FormaW8BEN;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY;
import com.indeval.portaldali.middleware.formatosw.FormaW9;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileDocumentoUpload;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class MostrarBeneficiarioMILAActivoController extends ControllerBase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(MostrarBeneficiarioMILAActivoController.class);
	
	/** Servicio de Beneficiarios */
	private ControlBeneficiariosService controlBeneficiariosService;
	/** Mapa que contiene la lista de custodios para la tabla */
	private Map mapaCustodios;
	/** Mapa que contiene la lista de custodios para la tabla */
	private Map mapaTipoBeneficiario;
	/** Forma W8BEN para la pantalla */
	private FormaGeneral forma = null;
	/** Servicio para obtener la institucion */
	private ConsultaCatalogoService consultaCatService;
	/** Formato */
	private String formato;
	/** ID Beneficiacrio */
	private Long idBeneficiarioSelected;
	/** Institucion seleccionada */
	private String idFolioInstitucion;
	/** Institucion seleccionada */
	private String nombreInstitucion;
	/** Mostar Forma de Guardar */
	private boolean mostrarFormaGuardar = false;
	/** Mapa que contiene la lista de paises para la tabla */
	private Map<Integer, String> mapaPais;
	/** Mapa que contiene la lista de documentos para la tabla */
	private Map<Long, String> mapaTipoDocumentos;
	/** Mapa que contiene la lista de sectores economicos para la tabla */
	private Map<Long, String> mapaSectoresEconom;
	/** Mapa que contiene la lista de sectores economicos para la tabla */
	private Map<Long, String> mapaCaracterEntidades;
	/**Servicio conciliaciones, usado para la consulta de paises**/
	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	/**Se ocupa para mostrar los archivos de los documentos del beneficiario**/
	private ArrayList<FileDocumentoUpload> files;

	/**
	 * Constructor de Captura Operaciones
	 */
	public MostrarBeneficiarioMILAActivoController() {
		
	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primerva vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit() {
		
		if( !isInstitucionIndeval() ) {
			AgenteVO agente = getAgenteFirmado();
			idFolioInstitucion = agente.getId() + agente.getFolio();
			nombreInstitucion = agente.getNombreCorto();
		}
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		formato = facesContext.getExternalContext().getRequestParameterMap().get("formato");
		String idBenefCadena = facesContext.getExternalContext().getRequestParameterMap().get("idBeneficiario");
		String messageError = "Hubo un error al recibir los parametros";
		try {
			idBeneficiarioSelected = Long.valueOf(idBenefCadena);
		} catch (Exception e) {
			log.error(messageError,e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageError, messageError));
			return null;
		}
		
		if( StringUtils.isBlank(formato) ||
				idBeneficiarioSelected == null ||
				idBeneficiarioSelected.compareTo(1l) < 0 ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageError, messageError));
			return null;
		}
		
		String mostrarGuardarCadena = facesContext.getExternalContext().getRequestParameterMap().get("idMuestraConsulta");
		try {
			if( StringUtils.isNotBlank(mostrarGuardarCadena) ) {
				mostrarFormaGuardar = Boolean.valueOf(mostrarGuardarCadena);
			} else {
				mostrarFormaGuardar = false;
			}
		} catch (Exception e) {
			log.error("Error al convertir el Boolean: [" + mostrarFormaGuardar + "]", e);
		}
		
		Beneficiario beneficiario = controlBeneficiariosService.consultaBeneficiarioByIdEliminados(idBeneficiarioSelected);
		if( beneficiario != null ) {
			if( StringUtils.isNotBlank(beneficiario.getTipoFormato()) &&
					formato.equals(beneficiario.getTipoFormato()) ) {
				Long custodio = beneficiario.getIdCuentaNombrada();
				Long tipoBeneficiario = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();
				
				if( formato.equals("W8BEN") || formato.equals("W8BEN2014") ) {
					forma = new FormaW8BEN(custodio,tipoBeneficiario,null,controlBeneficiariosService.getField3W8BEN());
				} else if ( formato.equals("W8IMY") ) {
					forma = new FormaW8IMY(custodio,tipoBeneficiario,null, controlBeneficiariosService.getField3W8IMY());
				} else if ( formato.equals("W9") ) {
					forma = new FormaW9(custodio,tipoBeneficiario,null,controlBeneficiariosService.getField3W9());
				} else if ( formato.equals("MILA") ) {
					forma = new FormaMILA(custodio,tipoBeneficiario,beneficiario.getTipoBeneficiario().getDescTipoBeneficiario(),idFolioInstitucion,isInstitucionIndeval());
					inicializaMapaPaises();
					inicializaMapaTipoDocumentos();
					inicializaMapaSectorEconomico();
					inicializaMapaCaracterEntidades();
				} 
				forma.construyeBean(beneficiario, true);
				if(forma instanceof FormaMILA) {
					FormaMILA formaMila = (FormaMILA) forma;
					files.clear();
					if(formaMila.getDocumentoUno()!=null){
						files.add(formaMila.getDocumentoUno());
					}
					if(formaMila.getDocumentoDos()!=null){
						files.add(formaMila.getDocumentoDos());
					}
				}
				inicializaMapaCustodios();
				inicializaTiposBeneficiarios();
			}
		}
		return null;
	}

    public void limpiar(ActionEvent event) {
        idBeneficiarioSelected = null;
        forma = null;
        formato = null;
        idFolioInstitucion = null;
        nombreInstitucion = null;
        mostrarFormaGuardar = false;
    }
	
	public String guardar() {
		if( !isInstitucionIndeval() ) {
			AgenteVO agente = getAgenteFirmado();
			idFolioInstitucion = agente.getId() + agente.getFolio();
			nombreInstitucion = agente.getNombreCorto();
		}
		try {
			Institucion institucion = null;
			if ( StringUtils.isNotBlank(idFolioInstitucion) ) {
				institucion = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
				if (institucion != null) {
					forma.setIdInstitucion(institucion.getIdInstitucion());
					controlBeneficiariosService.agregaBeneficiarioInstitucion(idBeneficiarioSelected, institucion.getIdInstitucion());
				} else {
					String message = "La institucion no se ha encontrado";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
					return null;
				}
			} else {
				String message = "La institucion es invalida";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
				return null;
			}
			String message = "Beneficiario agregado con exito";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
            limpiar(null);
            return "regresaMostrarBenefExito";
		} catch( EJBException ejbException ) {
			log.error("ERROR", ejbException);
			trataExcepcion(ejbException);
		} catch( Throwable ex ) {
			log.error("ERROR", ex);
		}
        return null;
	}
	
	/**
	 * Obtiene los datos de la institucion relacionada
	 * 
	 * @param ActionEvent event
	 */
	public void obtenerDatosParticipante(ActionEvent event) {
		Institucion institucion = null;
		if (idFolioInstitucion != null) {
			institucion = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
			if (institucion != null) {
				nombreInstitucion = institucion.getNombreCorto();
			}
		}
	}
	
	/**
	 * Inicializa mapa de custodios
	 */
	private void inicializaMapaCustodios() {
		mapaCustodios = new HashMap();
		
		List<Object[]> lista = controlBeneficiariosService.obtieneCatBic();
		
		for( Object[] bene : lista ) {
			mapaCustodios.put((Long)bene[0], (String)bene[1]);
		}
	}
	
	/**
	 * Inicializa mapa de tipos de beneficiario
	 */
	private void inicializaTiposBeneficiarios() {
		mapaTipoBeneficiario = new HashMap();
		
		if( forma.getCustodio() != null && forma.getCustodio() > 0 ) {
			log.info("Custodio: [" + forma.getCustodio() + "]");
			List<Object[]> lista = controlBeneficiariosService.obtieneTiposBeneficiario(forma.getCustodio());
			
			for( Object[] bene : lista ) {
				mapaTipoBeneficiario.put((Long)bene[0], ((String)bene[1]).toUpperCase());
			}
		} 
		
	}
	
	/**
	 * Inicializa mapa de paises
	 */
	private void inicializaMapaPaises() {
		List<PaisInt> paises = conciliacionEfectivoIntService.consultaPaises();
		mapaPais = new HashMap<Integer, String>(paises.size());
		for (PaisInt pais : paises) {
			mapaPais.put(pais.getIdPais().intValue(), pais.getNombrePais().toUpperCase());
		}
		
	}
	
	/**
	 * Inicializa mapa de sectores economicos
	 */
	private void inicializaMapaSectorEconomico() {
		List<MILASectorEconomico> sectoresEconomicos = controlBeneficiariosService.consultaCatMilaSectorEconomico();
		mapaSectoresEconom = new HashMap<Long, String>(sectoresEconomicos.size());
		for (MILASectorEconomico sectorEconomico : sectoresEconomicos) {
			mapaSectoresEconom.put(sectorEconomico.getIdSectorEconomico(), sectorEconomico.getDescripcionMila());
		}
		
	}
	
	/**
	 * Inicializa mapa de sectores economicos
	 */
	private void inicializaMapaCaracterEntidades() {
		List<MILATipoEmpresa> caracterEntidades = controlBeneficiariosService.consultaCatMilaTipoEmpresa();
		mapaCaracterEntidades = new HashMap<Long, String>(caracterEntidades.size());
		for (MILATipoEmpresa caracterEntidad : caracterEntidades) {
			mapaCaracterEntidades.put(caracterEntidad.getIdTipoEmpresa(), caracterEntidad.getDescripcionMila());
		}
		
	}
	
	/**
	 * Inicializa mapa de tipo de documentos
	 */
	private void inicializaMapaTipoDocumentos() {
		List<MILATipoDocumento> tiposDocumentos = controlBeneficiariosService.consultaCatMilaTipoDocumento();
		mapaTipoDocumentos = new HashMap<Long, String>(tiposDocumentos.size());
		for (MILATipoDocumento tipoDocumento : tiposDocumentos) {
			mapaTipoDocumentos.put(tipoDocumento.getIdTipoDocumento().longValue(), 
					tipoDocumento.getDescripcionIndeval().toUpperCase());
			
		}
		
	}
	
	/**
	 * 
	 * @param stream
	 * @param object
	 * @throws Exception
	 */
	public void paint(OutputStream stream, Object object) throws Exception {
    	FileDocumentoUpload documento = getFiles().get((Integer)object);
    	byte[] data  = documento.getData();
        stream.write(data);
    }
	
	/**
	 * @param controlBeneficiariosService the controlBeneficiariosService to set
	 */
	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	/**
	 * @return the formaW8BEN
	 */
	public FormaGeneral getForma() {
		return forma;
	}

	/**
	 * @param formaW8BEN the formaW8BEN to set
	 */
	public void setForma(FormaGeneral forma) {
		this.forma = forma;
	}

	/**
	 * @param consultaCatService the consultaCatService to set
	 */
	public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
		this.consultaCatService = consultaCatService;
	}

	/**
	 * @return the mapaCustodios
	 */
	public Map getMapaCustodios() {
		return mapaCustodios;
	}

	/**
	 * @param mapaCustodios the mapaCustodios to set
	 */
	public void setMapaCustodios(Map mapaCustodios) {
		this.mapaCustodios = mapaCustodios;
	}

	/**
	 * @return the mapaTipoBeneficiario
	 */
	public Map getMapaTipoBeneficiario() {
		return mapaTipoBeneficiario;
	}

	/**
	 * @param mapaTipoBeneficiario the mapaTipoBeneficiario to set
	 */
	public void setMapaTipoBeneficiario(Map mapaTipoBeneficiario) {
		this.mapaTipoBeneficiario = mapaTipoBeneficiario;
	}

	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @return the idBeneficiarioSelected
	 */
	public Long getIdBeneficiarioSelected() {
		return idBeneficiarioSelected;
	}

	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @param idBeneficiarioSelected the idBeneficiarioSelected to set
	 */
	public void setIdBeneficiarioSelected(Long idBeneficiarioSelected) {
		this.idBeneficiarioSelected = idBeneficiarioSelected;
	}

	/**
	 * @return the idFolioInstitucion
	 */
	public String getIdFolioInstitucion() {
		return idFolioInstitucion;
	}

	/**
	 * @return the nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * @param idFolioInstitucion the idFolioInstitucion to set
	 */
	public void setIdFolioInstitucion(String idFolioInstitucion) {
		this.idFolioInstitucion = idFolioInstitucion;
	}

	/**
	 * @param nombreInstitucion the nombreInstitucion to set
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * @return the mostrarFormaGuardar
	 */
	public boolean isMostrarFormaGuardar() {
		return mostrarFormaGuardar;
	}

	/**
	 * @param mostrarFormaGuardar the mostrarFormaGuardar to set
	 */
	public void setMostrarFormaGuardar(boolean mostrarFormaGuardar) {
		this.mostrarFormaGuardar = mostrarFormaGuardar;
	}
	
	/**
	 * @param conciliacionEfectivoIntService
	 */
	public void setConciliacionEfectivoIntService(
			ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, String> getMapaPais() {
		return mapaPais;
	}

	/**
	 * 
	 * @param mapaPais
	 */
	public void setMapaPais(Map<Integer, String> mapaPais) {
		this.mapaPais = mapaPais;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Long, String> getMapaTipoDocumentos() {
		return mapaTipoDocumentos;
	}

	/**
	 * 
	 * @param mapaTipoDocumentos
	 */
	public void setMapaTipoDocumentos(Map<Long, String> mapaTipoDocumentos) {
		this.mapaTipoDocumentos = mapaTipoDocumentos;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<FileDocumentoUpload> getFiles() {
        return files;
    }

	/**
	 * 
	 * @param files
	 */
    public void setFiles(ArrayList<FileDocumentoUpload> files) { 
        this.files = files;
    }

    /**
     * 
     * @return
     */
	public Map<Long, String> getMapaSectoresEconom() {
		return mapaSectoresEconom;
	}

	/**
	 * 
	 * @param mapaSectoresEconom
	 */
	public void setMapaSectoresEconom(Map<Long, String> mapaSectoresEconom) {
		this.mapaSectoresEconom = mapaSectoresEconom;
	}

	/**
	 * 
	 * @return
	 */
	public Map<Long, String> getMapaCaracterEntidades() {
		return mapaCaracterEntidades;
	}

	/**
	 * 
	 * @param mapaCaracterEntidades
	 */
	public void setMapaCaracterEntidades(Map<Long, String> mapaCaracterEntidades) {
		this.mapaCaracterEntidades = mapaCaracterEntidades;
	}
}
