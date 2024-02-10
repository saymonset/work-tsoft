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
import org.apache.commons.lang.StringUtils;
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
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileDocumentoUpload;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.controller.seguridad.MensajeInternacionalBean;


/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Alejandro Rodriguez
 * @version 1.0
 */
public class CapturaBeneficiariosMILAController extends ControllerBase {
	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(CapturaBeneficiariosMILAController.class);
	
	/** Servicio de Beneficiarios */
	private ControlBeneficiariosService controlBeneficiariosService;
	/**Servicio conciliaciones, usado para la consulta de paises**/
	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	
	/**Lista de paises**/
	private List<SelectItem> listaPaises;
	/** Lista de Custodios */
	private List<SelectItem> listaCustodios;
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
	/** Custodios adicionales que pueden ser seleccionados **/
	private Long[] custodios;
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
    /** Indica la navegacio despues de guardar */
    private String navegacion;
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
    /** Bandera que indica si se guardo con exito el registro*/
    private boolean exitoGuardar;
    /** Bandera que indica si el estado es combo ono**/
    private boolean comboEstado;
    /**Indicador si el pais es Mexico **/
    private boolean mexicano;
    /** Indica que documento ingresa en el caso de que seleccione otros**/
    private boolean mostrarOtros;
    /** Indica si permita que exista un select inicial en el tipo de documento**/
    private boolean mostrarSeleccionarTipoDocumento;
    
    private ArrayList<FileDocumentoUpload> files;
    private int uploadsAvailable = 2;
    private boolean autoUpload = false;
    private boolean useFlash = false;

    
	/**
	 * Constructor de Captura Operaciones
	 */
	public CapturaBeneficiariosMILAController() {
	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primerva vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit() {
		
		clearUploadData();
		
		Map<String,Object> mapa = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		custodio = (Long) mapa.get("custodio");
		tipoBeneficiario = (Long) mapa.get("tipoBeneficiario");
		formato = (String) mapa.get("formato");
		idFolioInstitucion = null;
		inicializaCustodios();
		comboEstado = false;
		mostrarOtros = false;
		mostrarSeleccionarTipoDocumento = true;
		boolean indeval = true;
		if( !isInstitucionIndeval() ) {
			AgenteVO agente = getAgenteFirmado();
			idFolioInstitucion = agente.getId() + agente.getFolio();
			nombreInstitucion = agente.getNombreCorto();
			indeval = false;
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
		
		forma = new FormaMILA(custodio, tipoBeneficiario, mapaTipoBeneficiario.get(tipoBeneficiario), idFolioInstitucion, indeval);
		
		//Se pone activo por default
		forma.setActivo("1");
		forma.setFormato(formato);
		
		return null;
	}
	
	private boolean guardaFormaMila(){
		boolean formaValida = true;
		FormaMILA formaMila = (FormaMILA)forma;
		formaMila.setPais(mapaPais.get(formaMila.getPaisNacionalidad()));
		if(comboEstado){
			formaMila.setStateProvinceResidencial(mapaEstadosEntidad.get(formaMila.getCodigoEstadoEntidad()));
		}
		formaMila.setPaisResidencial(mapaPais.get(formaMila.getPaisDireccion()));
		
		formaMila.setDocumentoUno(null);
		formaMila.setDocumentoDos(null);
		
		if(files.size()>0){
			formaMila.setDocumentoUno(files.get(0));
		}
		if(files.size()>1){
			formaMila.setDocumentoDos(files.get(1));
		}
		
		if(!formaMila.isCalculaNumeroIdentificacion() && StringUtils.isBlank(formaMila.getNumeroIdentificacion())){
			String message = "Error: El N\u00FAmero de identificaci\u00F3n MILA es NULL o vac\u00EDo.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			formaValida = false;
		}
		
		return formaValida;
	}
	
    public String guardar() {
        navegacion = null;
		try {
			Institucion institucion = null;
            exitoGuardar = false;
			if ( StringUtils.isNotBlank(idFolioInstitucion) ) {
				institucion = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
				if (institucion != null) {
						//Se checa si los valores la forma se pudieron obtener correctamente
						if(guardaFormaMila()){
							//Por todos los custodios que se seleccionaro se guarda al beneficiario
							for(Long idCustodio : custodios){
								forma.setCustodio(idCustodio);
								controlBeneficiariosService.insertaBeneficiario(forma.construyeBO(), institucion.getIdInstitucion());
							}
							clearUploadData();
	                        navegacion = "regresaCapturaBeneficiarios";
						}
						else{
							return null;
						}
				} else {
					String message = "La Institucion no existe";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
					return null;
				}
			} else {
				String message = "Institucion invalida";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
				return null;
			}
			String message = "Beneficiario guardado con exito";
           MensajeInternacionalBean mensajeInternacionalBean =
                   (MensajeInternacionalBean)getBean("mensajeInternacionalBean");
            if (mensajeInternacionalBean != null) {
                mensajeInternacionalBean.setMensajeUsuario(message);
            } else {
                throw new Exception("No se pudo obtener el bean elegirInstitucionBean");
            }
            exitoGuardar = true;
            return "capturaBeneficiariosRedirect";
		} catch( EJBException ejbException ) {
			log.error("ERROR", ejbException);
			trataExcepcion(ejbException);
		} catch( Throwable ex ) {
			log.error("ERROR", ex);
		}
		return null;
	}

    public boolean isDebeMostrarGaurdar3Formatos() {
    	if(listaCustodios==null){
    		getInit();
    	}
    	//SI solamente hay custodios que sean el mismo que se selecciono
    	//y no hay otros custodios que tengan el formato, no es necesario
    	//pintar la seccionar de copiar formato
    	if(listaCustodios.isEmpty() || listaCustodios.size()==1){
    		return false;
    	}
    	return true;
    }

    public String navegacionGuardar() {
        return navegacion;
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
	
	/**
	 * Navega al formato correspodiente
	 * 
	 * @return String
	 */
	public String navegaFormato() {
		if( StringUtils.isNotBlank(formato) ) {
			return "capturaBeneficiarios" + formato;
		} 
		return null;
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
		
		List<CustodioTipoBenef> listaTiposBeneficiarios = controlBeneficiariosService.getListaCustodioTipoBenef();
		
		listaCustodios = new ArrayList<SelectItem>();
		
		//Agregamos todos los custodios que tienen dado de alta el mismo formato
		for (CustodioTipoBenef tipos : listaTiposBeneficiarios) {
			if(formato.equals(tipos.getFormato()) && tipoBeneficiario.equals(tipos.getTipoBeneficiario().getIdTipoBeneficiario())){
				//Se agrega el mapa para sacar la descripción del tipo de beneficiario, solo es uno pero aprovachamos para para agregarlo aqui
				mapaTipoBeneficiario.put(tipos.getTipoBeneficiario().getIdTipoBeneficiario(), tipos.getTipoBeneficiario().getDescTipoBeneficiario().toUpperCase());
				//Se agregan los custodios que tienen el mismo formato, en caso de ser el mismo custodio que ya se selecciono se agrega como deshabilitado
				listaCustodios.add(new SelectItem(tipos.getIdCuentaNombrada(), mapaCustodios.get(tipos.getIdCuentaNombrada()), tipos.getFormato(), custodio.equals(tipos.getIdCuentaNombrada())));
			}
		}
		Collections.sort(listaCustodios,new SelectItemComparator(false));
		custodios = new Long[]{custodio};
	}
	
	/**
	 * @param controlBeneficiariosService the controlBeneficiariosService to set
	 */
	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	/**
	 * @return the listaCustodios
	 */
	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}

	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
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

    /**
     * Bandera que indica si se guardo con exito el registro
     * @return the exitoGuardar
     */
    public boolean isExitoGuardar() {
        return exitoGuardar;
    }

    /**
     * Bandera que indica si se guardo con exito el registro
     * @param exitoGuardar the exitoGuardar to set
     */
    public void setExitoGuardar(boolean exitoGuardar) {
        this.exitoGuardar = exitoGuardar;
    }

	/**
	 * @return
	 */
	public Long[] getCustodios() {
		return custodios;
	}

	/**
	 * @param custodios
	 */
	public void setCustodios(Long[] custodios) {
		this.custodios = custodios;
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
      
    public String clearUploadData() {
        files.clear();
        FormaMILA formaMila = (FormaMILA)forma;
        if(formaMila!=null){
        	  formaMila.setDocumentoDos(null);
              formaMila.setDocumentoUno(null);
        }
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
}