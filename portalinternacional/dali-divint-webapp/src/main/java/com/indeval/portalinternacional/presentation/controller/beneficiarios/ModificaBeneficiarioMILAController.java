/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 * 
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.io.IOUtils;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaMILA;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.common.util.SelectItemComparator;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
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
public class ModificaBeneficiarioMILAController extends ControllerBase {
	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ModificaBeneficiarioMILAController.class);
	
	/** Servicio de Beneficiarios */
	private ControlBeneficiariosService controlBeneficiariosService;
	/**Servicio conciliaciones, usado para la consulta de paises**/
	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	/**Lista de paises**/
	private List<SelectItem> listaPaises;
	/** Lista de Custodios */
	private List<SelectItem> listaTipoDocumentos;
	/** Lista de Custodios */
	private List<SelectItem> listaSectorEconomico;
	/** Lista de Custodios */
	private List<SelectItem> listaTipoEmpresa;
	/** Lista de Custodios */
	private List<SelectItem> listaEstados;
	/** Custodio Seleccionado */
	private Long custodio;
	/** TipoBeneficiario Seleccionado */
	private Long tipoBeneficiario;
	/** Formarto de acuerdo a custodio y a tipo beneficiario */
	private String formato;
	/** Forma MILA para la pantalla */
	private FormaGeneral forma;
	/** Institucion seleccionada */
	private String idFolioInstitucion;
	/** Institucion seleccionada */
	private String nombreInstitucion;
	/** Servicio para obtener la institucion */
	private ConsultaCatalogoService consultaCatService;
    /** Mapa que contiene la lista de custodios para la tabla */
	private Map<Long, String> mapaCustodios;
	/** Mapa que contiene la lista de custodios para la tabla */
	private Map<Long, String> mapaTipoBeneficiario;
	/** Mapa que contiene la lista de custodios para la tabla */
	private Map<Integer, String> mapaPais;
	/**Mapa con el mapa de tipos de documentos por tipo de persona y pais**/
	private Map<String, List<SelectItem>> mapaTipoDocumentos;
	/** Mapa que contiene de estados de la entidad */
	private Map<Long, String> mapaEstadosEntidad;
    /** Bandera que indica si el estado es combo ono**/
    private boolean comboEstado;
    /**Indicador si el pais es Mexico **/
    private boolean mexicano;
    /** Indica que documento ingresa en el caso de que seleccione otros**/
    private boolean mostrarOtros;
    /** Indica si permita que exista un select inicial en el tipo de documento**/
    private boolean mostrarSeleccionarTipoDocumento;
    /** Id del Beneficiario Seleccionado */
    private Long idBeneficiarioSelected;
    
    private ArrayList<FileDocumentoUpload> files;
    private int uploadsAvailable = 2;
    private boolean autoUpload = false;
    private boolean useFlash = false;

    
	/**
	 * Constructor de Captura Operaciones
	 */
	public ModificaBeneficiarioMILAController() {
	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primerva vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getIniciaModificacion() {
		
		 if (idBeneficiarioSelected != null && idBeneficiarioSelected > 0) {
            log.debug("Beneficiario Seleccionado: [" + idBeneficiarioSelected + "-"+formato+"]");
            Beneficiario beneficiario = controlBeneficiariosService.
                    consultaBeneficiarioById(idBeneficiarioSelected);
            if( beneficiario != null) {
            	custodio = beneficiario.getIdCuentaNombrada();
            	tipoBeneficiario = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();
                formato = beneficiario.getTipoFormato();
                if( formato.equals("MILA")) {
					forma = new FormaMILA();
					forma.setFormato(formato);
					forma.construyeBean(beneficiario, false);
				}
            } else {
                log.error("Beneficiario no encontrado");
            }
        } else {
            log.error("No se tiene el parametro del Id del beneficiairo");
            throw new IllegalArgumentException("No se pudo recibir el parametro del beneficiario");
        }
		idFolioInstitucion = null;
		inicializaCustodios();
		comboEstado = false;
		mostrarOtros = false;
		mostrarSeleccionarTipoDocumento = true;
		if( !isInstitucionIndeval() ) {
			AgenteVO agente = getAgenteFirmado();
			idFolioInstitucion = agente.getId() + agente.getFolio();
			nombreInstitucion = agente.getNombreCorto();
		}
		List<PaisInt> paises = conciliacionEfectivoIntService.consultaPaises();
		listaPaises = new ArrayList<SelectItem>(paises.size());
		mapaPais = new HashMap<Integer, String>(paises.size());
		SelectItem paisMexico = null;
		for (PaisInt pais : paises) {
			SelectItem itemPais = new SelectItem(pais.getIdPais().intValue(), pais.getNombrePais().toUpperCase());
			//Mexico no lo agregamos porque lo pones al inicio de la lista
			if(pais.getIdPais().intValue()!=PaisInt.ID_PAIS_MEXICO){
				listaPaises.add(itemPais);
			}
			else{
				paisMexico = itemPais;
			}
			mapaPais.put(pais.getIdPais().intValue(), pais.getNombrePais().toUpperCase());
		}
		//Ordenamos los paises sin mexico
		Collections.sort(listaPaises,new SelectItemComparator(false));
		//Agregamos al inicio de la lista mexico si existe
		if(paisMexico!=null){
			listaPaises.add(0, paisMexico);
		}
		
		List<MILATipoDocumento> tiposDocumentos = controlBeneficiariosService.consultaCatMilaTipoDocumento();
		listaTipoDocumentos = new ArrayList<SelectItem>(0);
		mapaTipoDocumentos = new HashMap<String, List<SelectItem>>(6);
		for (MILATipoDocumento documento : tiposDocumentos) {
			String keyMapa = documento.isPersonaFisica()+"|"+documento.getIdPais();
			List<SelectItem> tiposDocumentosSelect = mapaTipoDocumentos.get(keyMapa);
			if(tiposDocumentosSelect == null){
				tiposDocumentosSelect = new ArrayList<SelectItem>();
				mapaTipoDocumentos.put(keyMapa, tiposDocumentosSelect);
			}
			tiposDocumentosSelect.add(new SelectItem(documento.getIdTipoDocumento(), documento.getDescripcionIndeval()));
		}
		
		List<MILASectorEconomico> sectoresEconomicos = controlBeneficiariosService.consultaCatMilaSectorEconomico();
		listaSectorEconomico = new ArrayList<SelectItem>(sectoresEconomicos.size());
		for (MILASectorEconomico sector : sectoresEconomicos) {
			listaSectorEconomico.add(new SelectItem(sector.getIdSectorEconomico(), sector.getDescripcionMila()));
		}
		
		List<MILATipoEmpresa> tiposEmpresas = controlBeneficiariosService.consultaCatMilaTipoEmpresa();
		listaTipoEmpresa = new ArrayList<SelectItem>(tiposEmpresas.size());
		for (MILATipoEmpresa empresa : tiposEmpresas) {
			listaTipoEmpresa.add(new SelectItem(empresa.getIdTipoEmpresa(), empresa.getDescripcionMila()));
		}
		
		List<MILACodigoDepartamento> estadosEntidades = controlBeneficiariosService.consultaCatMilaEstados();
		listaEstados = new ArrayList<SelectItem>(estadosEntidades.size());
		mapaEstadosEntidad = new HashMap<Long, String>(estadosEntidades.size());
		for (MILACodigoDepartamento entidad : estadosEntidades) {
			listaEstados.add(new SelectItem(entidad.getIdCodigoEstado(), entidad.getNombre().toUpperCase()));
			mapaEstadosEntidad.put(entidad.getIdCodigoEstado(), entidad.getNombre().toUpperCase());
		}
		
		fillUploadData();
		
		inicializaCombos();
		
		return "modificaBeneficiario" + formato;
	}
	
	public void limpiar(ActionEvent event) {
        log.debug("Entrando a ModificaBeneficiarioController.limpiar");
		formato = null;
        forma = null;
		idFolioInstitucion = null;
		nombreInstitucion = null;
	}
	
	private void guardaFormaMila(){
		FormaMILA formaMila = (FormaMILA)forma;
		formaMila.setPais(mapaPais.get(formaMila.getPaisNacionalidad()));
		if(comboEstado){
			formaMila.setStateProvinceResidencial(mapaEstadosEntidad.get(formaMila.getCodigoEstadoEntidad()));
		}
		formaMila.setPaisResidencial(mapaPais.get(formaMila.getPaisDireccion()));
		
		if(files.size()>0){
			formaMila.setDocumentoUno(files.get(0));
		}
		if(files.size()>1){
			formaMila.setDocumentoDos(files.get(1));
		}
			
	}
	
    public String guardar() {
    	guardaFormaMila();
		
		log.debug("Entrando a ModificaBeneficiarioController.guardar");
		try {
			Beneficiario beneficiarioOriginal = controlBeneficiariosService.consultaBeneficiarioById(idBeneficiarioSelected);
            Beneficiario beneficiario = forma.construyeBO(beneficiarioOriginal);
            controlBeneficiariosService.actualizaBeneficiario(beneficiario);
            clearUploadData();
			String message = "Beneficiario guardado con exito";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
			limpiar(null);
            ConsultaBeneficiariosController consultaBeneficiariosBean = 
                    (ConsultaBeneficiariosController)getBean("consultaBeneficiariosBean");
            consultaBeneficiariosBean.buscar(null);
            log.debug("Bandera Inicio Consulta: [" + consultaBeneficiariosBean.isBanderaInicio() + "]");
            return "consultaBeneficiarios";
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
	
	public void cambiaTipoDocumento(ActionEvent event) {
		FormaMILA formaMila = (FormaMILA) forma;
		formaMila.setNombreDocumento(null);
		formaMila.setNumeroDocumento(null);
		cambiaMostrarOtros(formaMila);
	}
	
	private void cambiaMostrarOtros(FormaMILA formaMila){
		Long tipoDocumento = formaMila.getTipoDocumento();
		if(tipoDocumento.equals(41L) 
				||tipoDocumento.equals(43L)
				||tipoDocumento.equals(50L)
				||tipoDocumento.equals(60L)
				||tipoDocumento.equals(65L)
				||tipoDocumento.equals(67L)
				||tipoDocumento.equals(72L)
				||tipoDocumento.equals(74L)
				||tipoDocumento.equals(77L)
				||tipoDocumento.equals(79L)){
			mostrarOtros = true;
		}
		else{
			mostrarOtros = false;
		}
	}
	
	public void cambiaPaisNacionalidad(ActionEvent event) {
		FormaMILA formaMila = (FormaMILA) forma;
		String keyMapa = forma.isPersonaFisica()+"|"+formaMila.getPaisNacionalidad();
		listaTipoDocumentos = mapaTipoDocumentos.get(keyMapa);
		//Si no se encuentra el pais se toma el 
		if(listaTipoDocumentos==null){
			keyMapa = forma.isPersonaFisica()+"|0";
			listaTipoDocumentos = mapaTipoDocumentos.get(keyMapa);
		}
		if(-1==formaMila.getPaisNacionalidad()){
			listaTipoDocumentos = new ArrayList<SelectItem>();
		}
		formaMila.setNombreDocumento(null);
		formaMila.setNumeroDocumento(null);
		mostrarOtros = false;
		mostrarSeleccionarTipoDocumento = true;
		formaMila.setTipoDocumento(null);
		if(formaMila.getPaisNacionalidad() == PaisInt.ID_PAIS_MEXICO){
			mexicano = true;
			if(!forma.isPersonaFisica()){
				//Para las personas morales mexicanas se pone el RFC como valor por default
				formaMila.setTipoDocumento(70L);
				mostrarSeleccionarTipoDocumento = false;
			}
		}
		else{
			mexicano = false;
		}
	}
	
	public void cambiaPaisDireccion(ActionEvent event) {
		FormaMILA formaMila = (FormaMILA) forma;
		if(formaMila.getPaisDireccion() == PaisInt.ID_PAIS_MEXICO){
			comboEstado = true;
			formaMila.setCodigoEstadoEntidad(-1L);
			formaMila.setStateProvinceResidencial(null);
		}
		else{
			comboEstado = false;
		}
	}
	
	private void inicializaCombos(){
		FormaMILA formaMila = (FormaMILA) forma;
		
		String keyMapa = forma.isPersonaFisica()+"|"+formaMila.getPaisNacionalidad();
		listaTipoDocumentos = mapaTipoDocumentos.get(keyMapa);
		//Si no se encuentra el pais se toma el 
		if(listaTipoDocumentos==null){
			keyMapa = forma.isPersonaFisica()+"|0";
			listaTipoDocumentos = mapaTipoDocumentos.get(keyMapa);
		}
		if(-1==formaMila.getPaisNacionalidad()){
			listaTipoDocumentos = new ArrayList<SelectItem>();
		}
		
		if(formaMila.getPaisDireccion() == PaisInt.ID_PAIS_MEXICO){
			comboEstado = true;
		}
		else{
			comboEstado = false;
		}
		if(formaMila.getPaisNacionalidad() == PaisInt.ID_PAIS_MEXICO){
			mexicano = true;
			if(!forma.isPersonaFisica()){
				//Para las personas morales mexicanas se pone el RFC como valor por default
				mostrarSeleccionarTipoDocumento = false;
			}
		}
		else{
			mexicano = false;
		}
		cambiaMostrarOtros(formaMila);
	}
	
	/**
	 * Inicializa lista de custodios
	 */
	private void inicializaCustodios() {
		List<Object[]> lista = controlBeneficiariosService.obtieneCatBic();
		mapaCustodios = new HashMap<Long,String>();
		mapaTipoBeneficiario = new HashMap<Long, String>(1);

		for(Object[] custodioActual : lista) {
            mapaCustodios.put((Long)custodioActual[0], (String)custodioActual[1]);
		}
		
		List<Object[]> listaTiposBeneficiarios = controlBeneficiariosService.obtieneTiposBeneficiario(custodio);
		
		for( Object[] bene : listaTiposBeneficiarios ) {
			mapaTipoBeneficiario.put((Long)bene[0], ((String)bene[1]).toUpperCase());
		}
		
	}
	
	/**
	 * @param controlBeneficiariosService the controlBeneficiariosService to set
	 */
	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	/**
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the tipoBeneficiario
	 */
	public Long getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * @param tipoBeneficiario the tipoBeneficiario to set
	 */
	public void setTipoBeneficiario(Long tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @return the idFolioInstitucion
	 */
	public String getIdFolioInstitucion() {
		return idFolioInstitucion;
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
	 * @return the nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
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
    public Map<Long, String> getMapaCustodios() {
        return mapaCustodios;
    }

    /**
     * @param mapaCustodios the mapaCustodios to set
     */
    public void setMapaCustodios(Map<Long, String> mapaCustodios) {
        this.mapaCustodios = mapaCustodios;
    }

    /**
     * @return the mapaTipoBeneficiario
     */
    public Map<Long, String> getMapaTipoBeneficiario() {
        return mapaTipoBeneficiario;
    }

    /**
     * @param mapaTipoBeneficiario the mapaTipoBeneficiario to set
     */
    public void setMapaTipoBeneficiario(Map<Long, String> mapaTipoBeneficiario) {
        this.mapaTipoBeneficiario = mapaTipoBeneficiario;
    }


	public List<SelectItem> getListaPaises() {
		return listaPaises;
	}

	public void setListaPaises(List<SelectItem> listaPaises) {
		this.listaPaises = listaPaises;
	}

	public void setConciliacionEfectivoIntService(
			ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}

	/**
	 * @return the listaTipoDocumentos
	 */
	public List<SelectItem> getListaTipoDocumentos() {
		return listaTipoDocumentos;
	}

	/**
	 * @param listaTipoDocumentos the listaTipoDocumentos to set
	 */
	public void setListaTipoDocumentos(List<SelectItem> listaTipoDocumentos) {
		this.listaTipoDocumentos = listaTipoDocumentos;
	}

	/**
	 * @return the listaSectorEconomico
	 */
	public List<SelectItem> getListaSectorEconomico() {
		return listaSectorEconomico;
	}

	/**
	 * @param listaSectorEconomico the listaSectorEconomico to set
	 */
	public void setListaSectorEconomico(List<SelectItem> listaSectorEconomico) {
		this.listaSectorEconomico = listaSectorEconomico;
	}

	/**
	 * @return the listaTipoEmpresa
	 */
	public List<SelectItem> getListaTipoEmpresa() {
		return listaTipoEmpresa;
	}

	/**
	 * @param listaTipoEmpresa the listaTipoEmpresa to set
	 */
	public void setListaTipoEmpresa(List<SelectItem> listaTipoEmpresa) {
		this.listaTipoEmpresa = listaTipoEmpresa;
	}

	/**
	 * @return the listaEstados
	 */
	public List<SelectItem> getListaEstados() {
		return listaEstados;
	}

	/**
	 * @param listaEstados the listaEstados to set
	 */
	public void setListaEstados(List<SelectItem> listaEstados) {
		this.listaEstados = listaEstados;
	}
	
	public int getSize() {
        if (getFiles().size()>0){
            return getFiles().size();
        }else 
        {
            return 0;
        }
    }

    public void paint(OutputStream stream, Object object) throws Exception {
    	FileDocumentoUpload documento = getFiles().get((Integer)object);
    	byte[] data  = documento.getData();
        stream.write(data);
    }
    public void listener(UploadEvent event) throws Exception{
        UploadItem item = event.getUploadItem();
        FileDocumentoUpload archivo = new FileDocumentoUpload();
		File file = item.getFile();
		byte[] data = IOUtils.toByteArray(new FileInputStream(file));
		if(5.00d < ((double)data.length)/1000000.00d){
			throw new Exception("Archivo excede limite de tamano");
		}
		archivo.setLength(data.length);
		archivo.setName(item.getFileName());
		archivo.setData(data);
        files.add(archivo);
        uploadsAvailable--;
    } 
    
    public void fillUploadData(){
    	clearUploadData();
    	FormaMILA  formaMila = (FormaMILA) forma;
    	if(formaMila!=null){
    		if(formaMila.getDocumentoUno()!=null){
    			files.add(formaMila.getDocumentoUno());
    		}
    		if(formaMila.getDocumentoDos()!=null){
    			files.add(formaMila.getDocumentoDos());
    		}
    	}
    }
      
    public String clearUploadData() {
        files.clear();
        setUploadsAvailable(2);
        return null;
    }
    
    public long getTimeStamp(){
        return System.currentTimeMillis();
    }
    
    public ArrayList<FileDocumentoUpload> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FileDocumentoUpload> files) { 
        this.files = files;
    }

    public int getUploadsAvailable() {
        return uploadsAvailable;
    }

    public void setUploadsAvailable(int uploadsAvailable) {
        this.uploadsAvailable = uploadsAvailable;
    }

    public boolean isAutoUpload() {
        return autoUpload;
    }

    public void setAutoUpload(boolean autoUpload) {
        this.autoUpload = autoUpload;
    }

    public boolean isUseFlash() {
        return useFlash;
    }

    public void setUseFlash(boolean useFlash) {
        this.useFlash = useFlash;
    }

	public boolean isComboEstado() {
		return comboEstado;
	}

	public void setComboEstado(boolean comboEstado) {
		this.comboEstado = comboEstado;
	}

	/**
	 * @return the mexicano
	 */
	public boolean isMexicano() {
		return mexicano;
	}

	/**
	 * @param mexicano the mexicano to set
	 */
	public void setMexicano(boolean mexicano) {
		this.mexicano = mexicano;
	}

	/**
	 * @return the mostrarOtros
	 */
	public boolean isMostrarOtros() {
		return mostrarOtros;
	}

	/**
	 * @param mostrarOtros the mostrarOtros to set
	 */
	public void setMostrarOtros(boolean mostrarOtros) {
		this.mostrarOtros = mostrarOtros;
	}

	/**
	 * @return the mostrarSeleccionarTipoDocumento
	 */
	public boolean isMostrarSeleccionarTipoDocumento() {
		return mostrarSeleccionarTipoDocumento;
	}

	/**
	 * @param mostrarSeleccionarTipoDocumento the mostrarSeleccionarTipoDocumento to set
	 */
	public void setMostrarSeleccionarTipoDocumento(
			boolean mostrarSeleccionarTipoDocumento) {
		this.mostrarSeleccionarTipoDocumento = mostrarSeleccionarTipoDocumento;
	}

	/**
	 * @return the idBeneficiarioSelected
	 */
	public Long getIdBeneficiarioSelected() {
		return idBeneficiarioSelected;
	}

	/**
	 * @param idBeneficiarioSelected the idBeneficiarioSelected to set
	 */
	public void setIdBeneficiarioSelected(Long idBeneficiarioSelected) {
		this.idBeneficiarioSelected = idBeneficiarioSelected;
	}
	
	
	
	
}