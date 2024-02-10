package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.convertHSSFWorkbookToHSSFSheet;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.devuelveRangoDeFilas;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.validaFormatoExcelBeneficiarios;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.validaRangoMaximoCargaBeneficiarios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.util.ExcelUtils;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiarioService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCapturaBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistrosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoFormatoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.Registros;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenCargaFilebenefVO;
import com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import com.thoughtworks.xstream.io.xml.DomDriver;



@SuppressWarnings({"unchecked","unused","rawtypes"})
public abstract class FileTransferCapturaBenefServiceImpl<T extends FileTransferForma> implements FileTransferCapturaBenefService<T>{
	
	private Class<T> type;
	
	
	private T object;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferCapturaBenefServiceImpl.class);
    
    /** servicio de persistencia fileTransfer beneficiarios*/
	private FileTransferCapturaBenefDao fileTransferCapturaBenefDao;
	
	/** validador para formato W*/
	private ValidatorBeneficiarioService validatorFormatService;
	
	/** servicio para obtener la fecha actual*/
	private DateUtilService dateUtilService;
	
	/** Resolvedor de Mensajes */
    private MessageResolver errorResolver;
    
    /** servicio de control de beneficiarios*/
    private ControlBeneficiariosService controlBeneficiariosService;
    
     
	
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService#consultaCargaExistente(java.lang.String)
     */
	public ResumenCargaFilebenefVO consultaCargaExistente(String claveUsuario)throws BusinessException {
		
		FileTransferCapturaBeneficiario fileTransferCapturaBeneficiario = null;
		ResumenCargaFilebenefVO resumenCargaFilebenefVO = null;
		fileTransferCapturaBeneficiario = fileTransferCapturaBenefDao.findFileTransferCapturaBeneByIdFolioTipoReg(claveUsuario);
		if(fileTransferCapturaBeneficiario != null){
			resumenCargaFilebenefVO = new ResumenCargaFilebenefVO();
			resumenCargaFilebenefVO.setFechaCarga(fileTransferCapturaBeneficiario.getFechaRegistro());
			resumenCargaFilebenefVO.setRegistrosConError(fileTransferCapturaBeneficiario.getNumeroRegistrosError());
			resumenCargaFilebenefVO.setRegistrosACargar(fileTransferCapturaBeneficiario.getNumeroRegistrosCorrectos());
			resumenCargaFilebenefVO.setTotalRegistros(fileTransferCapturaBeneficiario.getTotalRegistros());
			resumenCargaFilebenefVO.setIdCapturaBeneficiario(fileTransferCapturaBeneficiario.getIdFileTransferCapturaBene());
			resumenCargaFilebenefVO.setTipoFormato(fileTransferCapturaBeneficiario.getTipoFormatoBene().getIdTipoFormatoBene());
			resumenCargaFilebenefVO.setUsuario(fileTransferCapturaBeneficiario.getUsuario());								
			resumenCargaFilebenefVO.setXmlRegistrosConError(fileTransferCapturaBeneficiario.getRegistrosError());
		}
		return resumenCargaFilebenefVO;
	}
    
	
	
	/**
	 * 
	 */
	public List<T> obtenerRegistrosError(String registrosXMLError)throws BusinessException{
		return getListaRegistros(registrosXMLError);
	}
	
	
	/**
	 * 
	 */
	public List<T> consultaRegistrosCorrectos(String claveUsuario)throws BusinessException{
		
		RegistrosBeneficiarios registrosBeneficiarios = null;
		List<T> listaRegistrosCorrectos = null;
		
		registrosBeneficiarios = fileTransferCapturaBenefDao.consultaRegistrosBeneByIdFileCarga(claveUsuario);
		if(registrosBeneficiarios != null){
			listaRegistrosCorrectos = getListaRegistros(registrosBeneficiarios.getRegistrosCorrectos());
		}				
		return listaRegistrosCorrectos;
	}
	
	
    /**
     * metodo para validar el objeto VO y validar el rango maximo y minimo de registros en un archivo
     * @param fileTransferCapturaBenefVO
     * @return
     * @throws BusinessException
     */
	protected HSSFSheet validaDatosVOBeneficiario(FileTransferCapturaBenefVO fileTransferCapturaBenefVO) throws BusinessException{
		
		validatorFormatService.validaVOFiletransferBeneficiario(fileTransferCapturaBenefVO);
		HSSFSheet sheet = null;
		try{
			sheet = convertHSSFWorkbookToHSSFSheet(fileTransferCapturaBenefVO.getArchivo());
			int[] rangoFilas = devuelveRangoDeFilas(sheet);
			validaFormatoExcelBeneficiarios(sheet,rangoFilas);
			validaRangoMaximoCargaBeneficiarios(rangoFilas);		
		}catch(IOException ioex){
			ioex.printStackTrace();
			throw new BusinessException(ioex.getMessage());	
		}
		return sheet;
	}
	
	
	
	
	
	
	
	public List<T> getListaRegistros(String registrosXML){
		
								
			XStream xstream = new XStream(new DomDriver());
			Registros registros = null;
				
			Annotations.configureAliases(xstream, type);								
			Annotations.configureAliases(xstream, Registros.class);
					
			registros = (Registros) xstream.fromXML(registrosXML);					
			return registros.getListaRegistros();
					
					
					
	}
	
	
	
	
	public String getRegistrosToXML(List<T> listaRegistros){		
		
			Registros registros = new Registros();
			XStream xstream = new XStream();
			
			Annotations.configureAliases(xstream, type);				
			
			registros.setListaRegistros(listaRegistros);
			Annotations.configureAliases(xstream, Registros.class);
			String registrosXml = xstream.toXML(registros);
			
			return registrosXml;
		
	
	}
	
	
	
	

	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService#eliminaCargaExistente(java.lang.String, java.lang.Boolean)
	 */
	public void eliminaCargaExistente(String claveUsuario,Boolean cuentaConRegistrosCorrectos)throws BusinessException{
		
		if(claveUsuario == null){
			throw new BusinessException(errorResolver.getMessage("J0105",(Object) "La clave del usuario"));			
		}
		if(cuentaConRegistrosCorrectos != null){
			//si tiene registros correctos, los eliminamos una vez guardados los beneficiarios
			if(cuentaConRegistrosCorrectos){
				if(fileTransferCapturaBenefDao.deleteArchivoTempRegistrosCorrectos(claveUsuario) == null){
					throw new BusinessException(errorResolver.getMessage("J0020",(Object) "RegistrosTemporales Correctos"));
				}
			}
			if(fileTransferCapturaBenefDao.deleteArchivoTempFileTransferBeneficiario(claveUsuario) == null){
				throw new BusinessException(errorResolver.getMessage("J0020",(Object) "Archivo Temporal FileTransfer Beneficiarios"));
			}			
		}else{
			//se ejecutan si ocurre una exception
			fileTransferCapturaBenefDao.deleteArchivoTempRegistrosCorrectos(claveUsuario);
			fileTransferCapturaBenefDao.deleteArchivoTempFileTransferBeneficiario(claveUsuario);
		}				
	}
	
	
	
	// se inyectan las dependencias
	public void setFileTransferCapturaBenefDao(
			FileTransferCapturaBenefDao fileTransferCapturaBenefDao) {
		this.fileTransferCapturaBenefDao = fileTransferCapturaBenefDao;
	}

	public void setValidatorFormatService(
			ValidatorBeneficiarioService validatorFormatService) {
		this.validatorFormatService = validatorFormatService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}


	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	protected FileTransferCapturaBenefDao getFileTransferCapturaBenefDao() {
		return fileTransferCapturaBenefDao;
	}


	protected ValidatorBeneficiarioService getValidatorFormatService() {
		return validatorFormatService;
	}


	protected DateUtilService getDateUtilService() {
		return dateUtilService;
	}


	protected MessageResolver getErrorResolver() {
		return errorResolver;
	}

	protected ControlBeneficiariosService getControlBeneficiariosService() {
		return controlBeneficiariosService;
	}

	

	

	
	protected void generaListas(HSSFSheet sheet,int[] rangoFilas,List<T> listaRegistrosCorrectosAux,List<T> listaRegistrosErrorAux,
			String idClaveInstitucion,boolean isIndeval ) throws BusinessException {
		log.debug("Metodo :: generaListas");
		HSSFRow row = null;
		T fileTransferForma = null;
		Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		getValidatorFormatService().init();
			for (int numeroRegistro = rangoFilas[0]+1; numeroRegistro <= rangoFilas[1]; numeroRegistro++) {
				row = sheet.getRow(numeroRegistro);
				if(row != null){
					fileTransferForma = convertRowToFileTransferForma(row);
					log.debug("Metodo :: generaListas :: fileTransferForma :: "+ fileTransferForma.getTipoFormato());
		        	fileTransferForma.setNumeroRegistro(numeroRegistro);
		        	if(getValidatorFormatService().esRegistroFileTransferValido(fileTransferForma, listaRegistrosErrorAux)){
		        		int llaveUnica = fileTransferForma.hashCode();
		        		if(mapa.containsKey(llaveUnica)){		        			
		        			if(listaRegistrosCorrectosAux.contains(fileTransferForma)){
		        				int indice = listaRegistrosCorrectosAux.indexOf(fileTransferForma);
		        				T fileTransferFormaAux = listaRegistrosCorrectosAux.get(indice);
		        				fileTransferFormaAux.setEstadoRegistro(Constantes.ESTADO_ERROR);
		        				fileTransferFormaAux.setDescripcionError(Constantes.BENEFICIARIO_REPETIDO);
	        					listaRegistrosErrorAux.add(fileTransferFormaAux);
	        					listaRegistrosCorrectosAux.remove(fileTransferForma);	        						        						        				
		        			}
		        			fileTransferForma.setEstadoRegistro(Constantes.ESTADO_ERROR);
		        			fileTransferForma.setDescripcionError(Constantes.BENEFICIARIO_REPETIDO);
		        			listaRegistrosErrorAux.add(fileTransferForma);	
		        		}
		        		else{
		        			if(getValidatorFormatService().validaReglasNegocioBeneficiarioFiletransfer(fileTransferForma, listaRegistrosErrorAux,idClaveInstitucion,isIndeval)){
		        				fileTransferForma.setEstadoRegistro(Constantes.ESTADO_CORRECTO);
			        			listaRegistrosCorrectosAux.add(fileTransferForma);				        		
			        		}		        			
		        		}		        				        	
		        		mapa.put(llaveUnica, Integer.valueOf(numeroRegistro));
		        	}			 	        	    				    				    			  
				}				
			}
			getValidatorFormatService().release();
			mapa.clear();
			mapa = null;			  		        		        		      																			

		
	}
	
	
	/**
	 * metodo para insertar en la tabla de registros temporales
	 * @param listaRegistrosCorrectos
	 * @param listaRegistrosError
	 * @param tipoFormato
	 * @param idUsuario
	 * @param folioUsuario
	 * @throws BusinessException
	 */
	private void insertaInformacionTemporal(List<T> listaRegistrosCorrectos,List<T> listaRegistrosError,Long tipoFormato,String idUsuario,String folioUsuario)throws BusinessException{
		log.debug("FileTransferCapturaBenefServiceImpl :: insertaInformacionTemporal");
		if(listaRegistrosCorrectos != null && listaRegistrosError != null){
			int registrosError = listaRegistrosError.size();
			int registrosCorrectos = listaRegistrosCorrectos.size();
			int totalRegistros = registrosError + registrosCorrectos; 
			
					
			TipoFormatoBeneficiario tipoFormatoBeneficiario = new TipoFormatoBeneficiario();
			tipoFormatoBeneficiario.setIdTipoFormatoBene(tipoFormato);
					
			FileTransferCapturaBeneficiario fileTransferCapturaBeneficiario = new FileTransferCapturaBeneficiario();
			fileTransferCapturaBeneficiario.setTipoFormatoBene(tipoFormatoBeneficiario);
			fileTransferCapturaBeneficiario.setFechaRegistro(getDateUtilService().getCurrentDate());
			fileTransferCapturaBeneficiario.setIdInstitucion(idUsuario);
			fileTransferCapturaBeneficiario.setFolioInstitucion(folioUsuario);
			fileTransferCapturaBeneficiario.setNumeroRegistrosCorrectos(registrosCorrectos);
			fileTransferCapturaBeneficiario.setNumeroRegistrosError(registrosError);
			fileTransferCapturaBeneficiario.setTotalRegistros(totalRegistros);
			fileTransferCapturaBeneficiario.setUsuario(idUsuario+folioUsuario);
			fileTransferCapturaBeneficiario.setRegistrosError(getRegistrosToXML(listaRegistrosError));
			
			
					
			log.debug("Se guarda el archivo FileTransferCapturaBeneficiario");
			Long idFileCapturaBene = (Long)getFileTransferCapturaBenefDao().save(fileTransferCapturaBeneficiario);
			if(idFileCapturaBene == null){
				throw new BusinessException(getErrorResolver().getMessage("J0020",(Object) "FileTransfer Captura Beneficiario"));
			}else if(registrosCorrectos > 0){				
				RegistrosBeneficiarios registrosBeneficiarios = new RegistrosBeneficiarios();
				registrosBeneficiarios.setUsuario(idUsuario+folioUsuario);
				registrosBeneficiarios.setTipoFormatoBene(tipoFormatoBeneficiario);
				registrosBeneficiarios.setRegistrosCorrectos(getRegistrosToXML(listaRegistrosCorrectos));
				
				log.debug("Se guarda el archivo RegistrosBeneficiarios");
				if(getFileTransferCapturaBenefDao().save(registrosBeneficiarios) == null){
					throw new BusinessException(getErrorResolver().getMessage("J0020",(Object) "Registros Beneficiarios"));
				}				
			}
			getFileTransferCapturaBenefDao().flush();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService#guardaCargaTemporal(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO, java.lang.String)
	 */
	public void guardaCargaTemporal(FileTransferCapturaBenefVO fileTransferCapturaBenefVO,String idClaveInstitucion) throws BusinessException{
		log.debug("FileTransferCapturaBenefServiceImpl :: guardaCargaTemporal");
		Long tiempoInicial=System.currentTimeMillis();	
		//se valida  el vo		
		HSSFSheet sheet = null;		 
		sheet = validaDatosVOBeneficiario(fileTransferCapturaBenefVO);		
		int[] rangoFilas = ExcelUtils.devuelveRangoDeFilas(sheet);	
		
		List<T> listaRegistrosCorrectosAux = new ArrayList<T>();
		List<T> listaRegistrosErrorAux = new ArrayList<T>();
		log.debug("FileTransferCapturaBenefServiceImpl :: comenzando a generaListas");
		generaListas(sheet,rangoFilas,listaRegistrosCorrectosAux,listaRegistrosErrorAux,idClaveInstitucion,fileTransferCapturaBenefVO.isUsuarioIndeval());									
		log.debug("FileTransferCapturaBenefServiceImpl :: fin generaListas");
		
		log.debug("FileTransferCapturaBenefServiceImpl :: comenzando insertaInformacionTemporal");
		insertaInformacionTemporal(listaRegistrosCorrectosAux,listaRegistrosErrorAux,fileTransferCapturaBenefVO.getTipoFormato(),fileTransferCapturaBenefVO.getId(),fileTransferCapturaBenefVO.getFolio());
		log.debug("FileTransferCapturaBenefServiceImpl :: fin insertaInformacionTemporal");
		
		Long tiempoFinal=System.currentTimeMillis();
		if(log.isDebugEnabled()){
			log.debug("Tiempo de Procesamiento del archivo : "+(tiempoFinal-tiempoInicial));
		}
	}
	
	
	
	
	public abstract T convertRowToFileTransferForma(HSSFRow row);

	
}
