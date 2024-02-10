package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.convertHSSFWorkbookToHSSFSheet;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.devuelveRangoDeFilas;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.getColumnAsString;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.transformaCadena;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.validaFormatoExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorMulticargaDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.MulticargaInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistroMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoOperacionMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.adp.ClaveAdp;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.RegistroAdp;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.RegistroMultiempresa;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.DetalleRegistroErrorAdpVO;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.DetalleRegistroErrorMultiempresaVO;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaResumenExcelVO;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;
import com.indeval.portalinternacional.persistence.dao.AdpCustodioPorcentajeDao;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;
import com.indeval.portalinternacional.persistence.dao.ClaveAdpDao;
import com.indeval.portalinternacional.persistence.dao.MulticargaInternacionalDao;


@SuppressWarnings("unchecked")
public class MulticargaInternacionalServiceImpl implements MulticargaInternacionalService, Constantes {
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(MulticargaInternacionalServiceImpl.class);
    
    /** servicio para actualizar los beneficiarios*/
    private ControlBeneficiariosService controlBeneficiariosService;
    
	/** servicio de validaci&oacute;n*/
	private ValidatorMulticargaDivIntService validatorMulticargaDivIntService;
	
	/** Dao multicarga internacional*/
	private MulticargaInternacionalDao multicargaInternacionalDao;
	
	/** Resolvedor de Mensajes */
    private MessageResolver errorResolver;
    
	/** Dao Beneficairios*/
	private BeneficiarioDao beneficiarioDao;
	
	/** Dao para las consultas de instituciones */
	private InstitucionDao institucionDao;
	
	/** Servicio utilitario para obtener las fechas */
	private DateUtilService dateUtilService;
	
	/** Dao para las consultas de Clave ADP */
	private ClaveAdpDao claveAdpDao;
	
	/** Dao para las consultas de la relación custodio adp */
	private AdpCustodioPorcentajeDao adpCustodioPorcentajeDao;
	
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#guardaRegistrosMulticarga(MulticargaVO)
     */		
	public MulticargaResumenExcelVO guardaRegistrosMulticargaAdp(MulticargaVO multicargaVO) throws BusinessException{
		log.info("Entrando a guardaRegistrosMulticargaAdp()");
		//se valida  el vo
		validatorMulticargaDivIntService.validaMulticargaVO(multicargaVO);		
				
		HSSFSheet sheet = null;
		List<RegistroAdp> listaRegistrosCorrectosAux = null;
		List<DetalleRegistroErrorAdpVO> listaRegistrosErrorAux = null;
		Set<RegistroAdp> listaSinRepetir = null;
		int totalRegistros = 0,registrosError = 0, registrosCargados = 0;
		try{			
			sheet = convertHSSFWorkbookToHSSFSheet(multicargaVO.getArchivo());
			int[] rangoFilas = devuelveRangoDeFilas(sheet);
			validaFormatoExcel(sheet,rangoFilas);
			HSSFRow row = null;
			RegistroAdp registrosAdp = null;
			listaRegistrosCorrectosAux = new ArrayList<RegistroAdp>();
			listaRegistrosErrorAux = new ArrayList<DetalleRegistroErrorAdpVO>();
			listaSinRepetir = new HashSet<RegistroAdp>();			
			// se iteran las filas para tomar los valores de las columnas
			for (int i = rangoFilas[0];i <= rangoFilas[1]; i++) {
				row = sheet.getRow(i);
				if(row != null){
					registrosAdp = convertRowToRegistroAdp(row); 
		        	totalRegistros++;	    
		        	boolean registroValido = true;
		        	// se valida que los datos no sean nulos
		        	if(validatorMulticargaDivIntService.esUoiNulo(registrosAdp.getUoi())){
		        		registroValido = false;
		        		DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		        		detalleRegistroErrorAdpVO.setUoi(VALOR_NULO);
		        		detalleRegistroErrorAdpVO.setDescripcionError(UOI_NULO);
		        		listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);		        				        	
		        	}else if(validatorMulticargaDivIntService.esAdpNulo(registrosAdp.getAdp())){
		        		registroValido = false;
		        		DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		        		detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		        		detalleRegistroErrorAdpVO.setAdp(VALOR_NULO);
		        		detalleRegistroErrorAdpVO.setDescripcionError(ADP_NULO);
		        		listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);		        				        		
		        	}
		        	// se valida que tengan el formato correcto
		        	if(registroValido){		        		
		        		if(validatorMulticargaDivIntService.esFormatoUoiIncorrecto(registrosAdp.getUoi())){
		        			registroValido = false;
		        			DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		        			detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		        			detalleRegistroErrorAdpVO.setAdp(registrosAdp.getAdp());
		        			detalleRegistroErrorAdpVO.setDescripcionError(FORMATO_UOI_INCORRECTO);
		        			listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);
		        		}else if(validatorMulticargaDivIntService.esFormatoAdpIncorrecto(registrosAdp.getAdp())){
		        			registroValido = false;
		        			DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		        			detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		        			detalleRegistroErrorAdpVO.setAdp(registrosAdp.getAdp());
		        			detalleRegistroErrorAdpVO.setDescripcionError(FORMATO_ADP_INCORRECTO);
		        			listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);
		        		}		        				        		
		        	}
		        	//se valida que no vengan registros repetidos
		        	if(registroValido){
		        		if(!listaSinRepetir.add(registrosAdp)){    			
		        			if(listaRegistrosCorrectosAux.contains(registrosAdp)){
		        				int indice = listaRegistrosCorrectosAux.indexOf(registrosAdp);
		        				RegistroAdp registroAdpAux = (RegistroAdp)listaRegistrosCorrectosAux.get(indice);
		        				DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVORepetidoGuardado = new DetalleRegistroErrorAdpVO();
		        				detalleRegistroErrorAdpVORepetidoGuardado.setUoi(registroAdpAux.getUoi());
		        				detalleRegistroErrorAdpVORepetidoGuardado.setAdp(registroAdpAux.getAdp());
		        				detalleRegistroErrorAdpVORepetidoGuardado.setDescripcionError(REGISTRO_REPETIDO);
		        				listaRegistrosErrorAux.add(detalleRegistroErrorAdpVORepetidoGuardado);
		        				listaRegistrosCorrectosAux.remove(registrosAdp);		        						        				
		        			}
		        			registroValido = false;
		        			DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		        			detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		    				detalleRegistroErrorAdpVO.setAdp(registrosAdp.getAdp());
		    				detalleRegistroErrorAdpVO.setDescripcionError(REGISTRO_REPETIDO);
		    				listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO); 				
		        		}
		        		// se valida que exista el uoi
		        		if(registroValido){ 
		        			log.info("Se ejecuta la consulta de Beneficiarios por UOI");
		        			Beneficiario beneAux = beneficiarioDao.consultaBeneficiarioByUoiForAdp(registrosAdp.getUoi());
		        			if(beneAux == null){		        				
		        				DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		        				detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		        				detalleRegistroErrorAdpVO.setAdp(registrosAdp.getAdp());
		        				detalleRegistroErrorAdpVO.setDescripcionError(UOI_SIN_BENEFICIARIO);
		        				listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);
		        			}else{
		        				//se valida que no tenga asignado un adp
		        				/*if(validatorMulticargaDivIntService.esBeneficiarioConAdpAsignado(beneAux)){		        					
		        					DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		            				detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		            				detalleRegistroErrorAdpVO.setAdp(beneAux.getAdp());
		            				detalleRegistroErrorAdpVO.setDescripcionError(UOI_CON_ADP);
		            				listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);
		            				// se valida que el beneficiario este autorizado
		        				}else*/ if(validatorMulticargaDivIntService.esBeneficiarioDiferenteAutorizado(beneAux) ){		        				
		        					DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
		            				detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
		            				detalleRegistroErrorAdpVO.setAdp(registrosAdp.getAdp());
		            				detalleRegistroErrorAdpVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
		            				detalleRegistroErrorAdpVO.setDescripcionError(UOI_NO_AUTORIZADO);
		            				listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO);     					
		        				}
		        				else {
		        					/*
		        					 * Se comenta esta seccion para que el ADP se cargue a todos los custodios
		        					 */
//		        					// se valida que el beneficiario pertenesca a DEUTSCHE
//		        					String nombreCustodio = validatorMulticargaDivIntService.nombreCustodioPorCuentaNombrada(beneAux);
//		        					if(StringUtils.isBlank(nombreCustodio) || validatorMulticargaDivIntService.esBeneficiarioDiferenteDeutsche(nombreCustodio.trim())){		        						
//		        						DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
//		                				detalleRegistroErrorAdpVO.setUoi(registrosAdp.getUoi());
//		                				detalleRegistroErrorAdpVO.setAdp(registrosAdp.getAdp());
//		                				detalleRegistroErrorAdpVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
//		                				detalleRegistroErrorAdpVO.setNombreCustodio(nombreCustodio);
//		                				detalleRegistroErrorAdpVO.setDescripcionError(CUSTODIO_INVALIDO);
//		                				listaRegistrosErrorAux.add(detalleRegistroErrorAdpVO); 		        						
//		        					}else{
//		        						// si cumple con todas las validaciones se guarda el registro en la lista de validos
//		        						listaRegistrosCorrectosAux.add(registrosAdp); 
//		        					}
		        					listaRegistrosCorrectosAux.add(registrosAdp);
		        				}		        						        				
		        			}		        		
		        		}		        		
		        	}		        		        			        			        			
				}				 	        	    				    				    			  
			}
			listaSinRepetir = null;
			if(listaRegistrosCorrectosAux.size() > 0){
				TipoOperacionMulticarga tipoOperacionMulticarga = new TipoOperacionMulticarga();
				tipoOperacionMulticarga.setIdTipoOperacionMulticarga(multicargaVO.getTipoOperacion());
				
				EstadoMulticarga estadoMulticarga = new EstadoMulticarga();
				estadoMulticarga.setIdEstadoMulticarga(multicargaVO.getEstadoRegistro());
				        		      		        		        		
        		MulticargaInternacional multicargaInternacional = new MulticargaInternacional();     			
        		multicargaInternacional.setFechaHoraCarga(dateUtilService.getCurrentDate());
        		multicargaInternacional.setNombreArchivo(multicargaVO.getNombreArchivo().toUpperCase());    			
        		multicargaInternacional.setNombreUsuarioCarga(multicargaVO.getUsuarioResponsable().toUpperCase());
        		multicargaInternacional.setNumeroRegistros(new Long(listaRegistrosCorrectosAux.size()));        		
        		multicargaInternacional.setTipoOperacionMulticarga(tipoOperacionMulticarga);
        		multicargaInternacional.setEstadoMulticarga(estadoMulticarga);        		        			
        		
        		// se guarda el archivo
        		log.debug("Se guarda el archivo multicarga Internacional");
        		Long idMulticargaInternacional = (Long)multicargaInternacionalDao.save(multicargaInternacional);
        		if(idMulticargaInternacional == null){
        			throw new BusinessException(errorResolver.getMessage("J0020",(Object) "Multicarga Internacional"));
        		}else{        			
        			RegistroMulticarga registroMulticarga = new RegistroMulticarga();
        			registroMulticarga.setTipoOperacionMulticarga(tipoOperacionMulticarga);
        			registroMulticarga.setListaRegistros(listaRegistrosCorrectosAux);
        			registroMulticarga.setIdMulticargaInternacional(idMulticargaInternacional);
        			// se guardan los registros que tiene el archivo
        			log.debug("Se guardan los registros Multicarga");
        			if(multicargaInternacionalDao.save(registroMulticarga) == null){
        				throw new BusinessException(errorResolver.getMessage("J0020",(Object) "Registro Multicarga"));
        			}
        		}	        		        		    	
        	}
			registrosError = listaRegistrosErrorAux.size();
			registrosCargados = listaRegistrosCorrectosAux.size();
			// se genera el VO a retornar con los datos del proceso
			MulticargaResumenExcelVO multicargaResumenExcelVO = new MulticargaResumenExcelVO();
			multicargaResumenExcelVO.setTotalRegistros(totalRegistros);
			multicargaResumenExcelVO.setTotalRegistrosCargados(registrosCargados);
			multicargaResumenExcelVO.setTotalRegistrosConError(registrosError);
			multicargaResumenExcelVO.setListaRegistrosError(listaRegistrosErrorAux);						
			return multicargaResumenExcelVO;			
		}catch(IOException ioex){
			ioex.printStackTrace();
			throw new BusinessException(ioex.getMessage());			
		} 						
	}
	
	/**
	 * m&eacute;todo para convertir un HSSFRow a un RegistroAdp
	 * @param  HSSFRow
	 * @return RegistroAdp	 
	 * */
	private RegistroAdp convertRowToRegistroAdp(HSSFRow row){
		RegistroAdp registroAdp = null;
		if(row != null){
			registroAdp = new RegistroAdp();
			String uoi= getColumnAsString(row.getCell(0));
			registroAdp.setUoi(transformaCadena(uoi));
			
			String adp= getColumnAsString(row.getCell(1));
			registroAdp.setAdp(transformaCadena(adp));			
		}
		return registroAdp;
	}
	
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#guardaRegistrosMulticargaMultiempresa(MulticargaVO)
     */	
	public MulticargaResumenExcelVO guardaRegistrosMulticargaMultiempresa(MulticargaVO multicargaVO) throws BusinessException{
		log.info("Entrando a guardaRegistrosMulticargaAdp()");
		//se valida  el vo
		validatorMulticargaDivIntService.validaMulticargaVO(multicargaVO);
			
		HSSFSheet sheet = null;
		List<RegistroMultiempresa> listaRegistrosCorrectosAux = null;
		List<DetalleRegistroErrorMultiempresaVO> listaRegistrosErrorAux = null;
		Set<RegistroMultiempresa> listaSinRepetir = null;
		int totalRegistros = 0,registrosError = 0, registrosCargados = 0;
		try{			
			sheet = convertHSSFWorkbookToHSSFSheet(multicargaVO.getArchivo());
			int[] rangoFilas = devuelveRangoDeFilas(sheet);
			validaFormatoExcel(sheet,rangoFilas);
			HSSFRow row = null;
			RegistroMultiempresa registroMultiempresa = null;
			listaRegistrosCorrectosAux = new ArrayList<RegistroMultiempresa>();
			listaRegistrosErrorAux = new ArrayList<DetalleRegistroErrorMultiempresaVO>();
			listaSinRepetir = new HashSet<RegistroMultiempresa>();
			String claveInstitucionDestino = multicargaVO.getClaveInstitucionDestino();
			Long idInstitucionDestino = multicargaVO.getIdInstitucionDestino();
			// se iteran las filas para tomar los valores de las columnas
			for (int i = rangoFilas[0];i <= rangoFilas[1]; i++) {
				row = sheet.getRow(i);
				if(row != null){
					registroMultiempresa = convertRowToRegistroMultiempresa(row);
		        	totalRegistros++;
		        	boolean registroValido = true;		        	    			        	
		        	//se valida que los datos no sean nulos
		        	if(validatorMulticargaDivIntService.esUoiNulo(registroMultiempresa.getUoi())){
		        		registroValido = false;
		        		DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		        		detalleRegistroErrorMultiempresaVO.setUoi(VALOR_NULO);
		        		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		        		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_NULO);    		
		        		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);
		        	}else if(validatorMulticargaDivIntService.esClaveInstitucionNula(registroMultiempresa.getInstitucionOrigen())){
		        		registroValido = false;
		        		DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		        		detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		        		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(VALOR_NULO);
		        		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		        		detalleRegistroErrorMultiempresaVO.setDescripcionError(INSTITUCION_ORIGEN_NULO);
		        		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);
		        	}		        	
		        	// se valida que los datos tengan el formato permitido
		        	if(registroValido){    		    		
		        		if(validatorMulticargaDivIntService.esFormatoUoiIncorrecto(registroMultiempresa.getUoi())){
		        			registroValido = false;
		            		DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		            		detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		            		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		            		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		            		detalleRegistroErrorMultiempresaVO.setDescripcionError(FORMATO_UOI_INCORRECTO);    		
		            		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);    			    		
		        		}else if(validatorMulticargaDivIntService.esFormatoClaveInstitucionIncorrecto(registroMultiempresa.getInstitucionOrigen())){
		        			registroValido = false;
		                	DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		                	detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		                	detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		                	detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                	detalleRegistroErrorMultiempresaVO.setDescripcionError(FORMATO_INSTITUCION_ORIGEN_INCORRECTO);    		
		                	listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);
		        		}
		        	}		        	
		        	//se valida que no vengan registros repetidos en el archivo, si lo trae elimina el registro repetido 
		        	//y lo mete en la lista de registros con error
		        	if(registroValido){      	
		        		if(!listaSinRepetir.add(registroMultiempresa)){    			
		        			if(listaRegistrosCorrectosAux.contains(registroMultiempresa)){    				
		        				int indice = listaRegistrosCorrectosAux.indexOf(registroMultiempresa);
		        				RegistroMultiempresa registroMultiempresaAux = (RegistroMultiempresa)listaRegistrosCorrectosAux.get(indice);
		        				DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		        				detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresaAux.getUoi());
		        				detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresaAux.getInstitucionOrigen());
		                		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                		detalleRegistroErrorMultiempresaVO.setDescripcionError(REGISTRO_REPETIDO);
		                		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);    				
		        				listaRegistrosCorrectosAux.remove(registroMultiempresa);
		        			}
		        			registroValido = false;
		        			DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		        			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		            		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		            		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		            		detalleRegistroErrorMultiempresaVO.setDescripcionError(REGISTRO_REPETIDO);
		            		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO); 				
		        		}
		        	
		        		// se valida que exista el beneficiario con  el uoi dado
		        		if(registroValido){ 
		        			log.info("Se ejecuta la consulta de Beneficiarios por UOI");
		        			Beneficiario beneAux = beneficiarioDao.consultaBeneficiarioByUoiForInstituciones(registroMultiempresa.getUoi());
		        			if(beneAux == null){		        				
		        				DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		            			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		                		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		                		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_SIN_BENEFICIARIO);
		                		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);    			
		        			}else{
		        				// se valida que se encuentre autorizado
		        				if(validatorMulticargaDivIntService.esBeneficiarioDiferenteAutorizado(beneAux) ){		        					
		        					DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		                			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		                    		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		                    		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                    		detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
		                    		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_NO_AUTORIZADO);
		                    		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);    					   				
		        				}else{
		        					// se valida que que exista la instituci&oacute;n origen, que el beneficiario tenga la instituci&oacute;n origen y no contenga la instituci&oacute;n destino
		        					Institucion institucion = institucionDao.findInstitucionByClaveFolio(registroMultiempresa.getInstitucionOrigen());
		        					if(institucion == null){		        						
		            					DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		                    			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		                        		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		                        		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                        		detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
		                        		detalleRegistroErrorMultiempresaVO.setDescripcionError(INSTITUCION_ORIGEN_NO_ENCONTRADA);
		                        		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);    						    					
		        					}else if(!validatorMulticargaDivIntService.validaInstitucionBeneficiario(beneAux,institucion.getIdInstitucion())){		        						
		            					DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		                    			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		                        		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		                        		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                        		detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
		                        		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_NO_EN_INSTITUCION);
		                        		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);    						    					
		        					}else if(validatorMulticargaDivIntService.validaInstitucionBeneficiario(beneAux,idInstitucionDestino)){		        						
		            					DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
		                    			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
		                        		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
		                        		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(claveInstitucionDestino);
		                        		detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
		                        		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_YA_CUENTA_CON_INSTITUCION);
		                        		listaRegistrosErrorAux.add(detalleRegistroErrorMultiempresaVO);     						
		        					}else{
		        						// si pasa las validaciones se guarda en la lista de registros correctos		        			        	
		        			        	registroMultiempresa.setInstitucionDestino(claveInstitucionDestino);
		        			        	listaRegistrosCorrectosAux.add(registroMultiempresa);    				        			        	
		        					}    				
		        				}    		    			    				    			    		
		        			}    		
		        		}    					        				        		
		        	}		     			
				}				 	        	    				    				    			  
			}
			listaSinRepetir = null;
			if(listaRegistrosCorrectosAux.size() > 0){
				TipoOperacionMulticarga tipoOperacionMulticarga = new TipoOperacionMulticarga();
				tipoOperacionMulticarga.setIdTipoOperacionMulticarga(multicargaVO.getTipoOperacion());
				
				EstadoMulticarga estadoMulticarga = new EstadoMulticarga();
				estadoMulticarga.setIdEstadoMulticarga(multicargaVO.getEstadoRegistro());
				        		      		        		        		
        		MulticargaInternacional multicargaInternacional = new MulticargaInternacional();     			
        		multicargaInternacional.setFechaHoraCarga(dateUtilService.getCurrentDate());
        		multicargaInternacional.setNombreArchivo(multicargaVO.getNombreArchivo().toUpperCase());    			
        		multicargaInternacional.setNombreUsuarioCarga(multicargaVO.getUsuarioResponsable().toUpperCase());
        		multicargaInternacional.setNumeroRegistros(new Long(listaRegistrosCorrectosAux.size()));        		
        		multicargaInternacional.setTipoOperacionMulticarga(tipoOperacionMulticarga);
        		multicargaInternacional.setEstadoMulticarga(estadoMulticarga);        		        			
        		
        		// se guarda el archivo
        		log.debug("Se guarda el archivo multicarga Internacional");
        		Long idMulticargaInternacional = (Long)multicargaInternacionalDao.save(multicargaInternacional);
        		if(idMulticargaInternacional == null){
        			throw new BusinessException(errorResolver.getMessage("J0020",(Object) "Multicarga Internacional"));
        		}else{        			
        			RegistroMulticarga registroMulticarga = new RegistroMulticarga();
        			registroMulticarga.setTipoOperacionMulticarga(tipoOperacionMulticarga);
        			registroMulticarga.setListaRegistros(listaRegistrosCorrectosAux);
        			registroMulticarga.setIdMulticargaInternacional(idMulticargaInternacional);
        			// se guardan los registros que tiene el archivo
        			log.debug("Se guardan los registros Multicarga");
        			if(multicargaInternacionalDao.save(registroMulticarga) == null){
        				throw new BusinessException(errorResolver.getMessage("J0020",(Object) "Registro Multicarga"));
        			}
        		}	        		        		    	
        	}
			registrosError = listaRegistrosErrorAux.size();
			registrosCargados = listaRegistrosCorrectosAux.size();
			// se genera el VO a retornar con los datos del proceso
			MulticargaResumenExcelVO multicargaResumenExcelVO = new MulticargaResumenExcelVO();
			multicargaResumenExcelVO.setTotalRegistros(totalRegistros);
			multicargaResumenExcelVO.setTotalRegistrosCargados(registrosCargados);
			multicargaResumenExcelVO.setTotalRegistrosConError(registrosError);
			multicargaResumenExcelVO.setListaRegistrosError(listaRegistrosErrorAux);						
			return multicargaResumenExcelVO;			
		}catch(IOException ioex){
			ioex.printStackTrace();
			throw new BusinessException(ioex.getMessage());			
		} 										
	}
	
	
	/**
	 * m&eacute;todo para convertir un HSSFRow a un RegistroMultiempresa
	 * @param  HSSFRow
	 * @return RegistroMultiempresa	 
	 * */
	private RegistroMultiempresa convertRowToRegistroMultiempresa(HSSFRow row){
		RegistroMultiempresa registroMultiempresa = null;
		if(row != null){
			registroMultiempresa = new RegistroMultiempresa();
			String uoi= getColumnAsString(row.getCell(0));
			registroMultiempresa.setUoi(transformaCadena(uoi));
			
			String institucionOrigen = getColumnAsString(row.getCell(1));
			registroMultiempresa.setInstitucionOrigen(transformaCadena(institucionOrigen));									
		}
		return registroMultiempresa;
	}
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#nombreArchivoValido(String)
     */	
	public boolean nombreArchivoValido(String nombreArchivo) throws BusinessException{
		log.info("Entrando a nombreArchivoValido()");
		
		if(nombreArchivo == null){
			throw new BusinessException(errorResolver.getMessage("J0098"));
		}
		return validatorMulticargaDivIntService.esNombreArchivoValido(nombreArchivo);
	}
	
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#obtieneCatalogoEstadoMulticarga()
     */	
	public List<EstadoMulticarga> obtieneCatalogoEstadoMulticarga() throws BusinessException {
		return multicargaInternacionalDao.getCatalogoEstadoMulticarga();
	}
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#obtieneCatalogoTipoOperacionMulticarga()
     */
	public List<TipoOperacionMulticarga> obtieneCatalogoTipoOperacionMulticarga()throws BusinessException {
		return multicargaInternacionalDao.getCatalogoTipoOperacionMulticarga();
	}
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#consultaOperacionesMulticarga(MulticargaVO,PaginaVO)
     */
	public PaginaVO consultaOperacionesMulticarga(MulticargaVO multicargaVO,PaginaVO paginaVO) throws BusinessException{
		log.info("Entrando a consultaOperacionesMulticarga()");
		if(multicargaVO == null){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "Los criterios de busqueda"));
		}
		return multicargaInternacionalDao.consultaOperacionesMulticarga(multicargaVO, paginaVO);
		
	}
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#consultaRegistrosByIdMulticarga(Long)
     */
	public RegistroMulticarga consultaRegistrosByIdMulticarga(Long id) throws BusinessException {
		log.info("Entrando a consultaRegistrosByIdMulticarga()");
		if(id == null || id <= 0){
			throw new BusinessException(errorResolver.getMessage("J0053",(Object) "El id de los Registros"));
		}
		return multicargaInternacionalDao.consultaRegistrosByIdMulticarga(id);
	}
		
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#borrarRegistroMulticarga(Long,String)
     */
	public void borrarRegistroMulticarga(Long idMulticarga, String nombreUsuario) throws BusinessException{	
		log.info("Entrando a borrarRegistroMulticarga()");
		// se obtiene el archivo multicarga con el idMulticarga
		MulticargaInternacional multicargaInternacional = multicargaInternacionalDao.consultaMulticargaByIdMulticarga(idMulticarga);
		if(multicargaInternacional == null || nombreUsuario == null){
			throw new BusinessException(errorResolver.getMessage("J0099"));					
		}else{			
			RegistroMulticarga registroMulticarga = multicargaInternacionalDao.consultaRegistrosByIdMulticarga(multicargaInternacional.getIdMulticargaInternacional()); 
			if(multicargaInternacional.getEstadoMulticarga().getIdEstadoMulticarga().longValue() == STATUS_MULTICARGA_PENDIENTE_DE_AUTORIZAR){
				// si su estado es pendiente, verifica que tenga registros el archivo y los elimina				
				if(registroMulticarga != null){
					multicargaInternacionalDao.delete(registroMulticarga);										
				}
				// se eliminan los datos del archivo cargado de manera permanente
				multicargaInternacionalDao.delete(multicargaInternacional);
			}else if(multicargaInternacional.getEstadoMulticarga().getIdEstadoMulticarga().longValue() == STATUS_MULTICARGA_AUTORIZADO ||
					multicargaInternacional.getEstadoMulticarga().getIdEstadoMulticarga().longValue() == STATUS_MULTICARGA_AUTORIZADO_CON_ERROR){									
					if(registroMulticarga != null){
						List listaRegistros = registroMulticarga.getListaRegistros();
						if(listaRegistros != null && !listaRegistros.isEmpty()){
							//dependiendo del tipo de operaci&oacute;n, se toma el m&eacute;todo correspondiente
							if(registroMulticarga.getTipoOperacionMulticarga().getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_ADP){
								asignaValorAdpNulo(listaRegistros);						
							}else if(registroMulticarga.getTipoOperacionMulticarga().getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_MULTIEMPRESA){
								asignaValorInstitucionNulo(listaRegistros);						
							}					
						}
					}
				// se pasa el archivo a estado eliminado, se guarda la fecha hora de actualizaci&oacute;n, 
				//se guarda el usuario que realizo la operaci&oacute;n y se actualiza el archivo
				EstadoMulticarga estadoMulticarga = new EstadoMulticarga();
				estadoMulticarga.setIdEstadoMulticarga(STATUS_MULTICARGA_ELIMINADO);				
				multicargaInternacional.setEstadoMulticarga(estadoMulticarga);
				multicargaInternacional.setFechaHoraActualizacion(dateUtilService.getCurrentDate());
				multicargaInternacional.setNombreUsuarioAutorizacion(nombreUsuario.toUpperCase());
				multicargaInternacionalDao.update(multicargaInternacional);
			}			
		}				
	}
	
	/**
     * @see com.indeval.portalinternacional.services.divisioninternacional.MulticargaInternacionalService#autorizarRegistroMulticarga(Long,String)
     */
	public List autorizarRegistroMulticarga(Long idMulticarga, String nombreUsuario) throws BusinessException{
		log.info("Entrando a autorizarRegistroMulticarga()");
		List registrosError = null;
		// se obtiene el archivo multicarga con el idMulticarga
		MulticargaInternacional multicargaInternacional = multicargaInternacionalDao.consultaMulticargaByIdMulticarga(idMulticarga);
		if(multicargaInternacional == null || nombreUsuario == null){
			throw new BusinessException(errorResolver.getMessage("J0099"));					
		}else{
			// se valida que el archivo tenga estado pendiente de autorizar
			if(multicargaInternacional.getEstadoMulticarga().getIdEstadoMulticarga().longValue() == STATUS_MULTICARGA_PENDIENTE_DE_AUTORIZAR){				
				RegistroMulticarga registroMulticarga = multicargaInternacionalDao.consultaRegistrosByIdMulticarga(multicargaInternacional.getIdMulticargaInternacional());
				if(registroMulticarga != null && registroMulticarga.getListaRegistros() != null && !registroMulticarga.getListaRegistros().isEmpty()){																						
					if(registroMulticarga.getTipoOperacionMulticarga().getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_ADP){
						registrosError = asignaValorAdp(registroMulticarga.getListaRegistros());						
					}else if(registroMulticarga.getTipoOperacionMulticarga().getIdTipoOperacionMulticarga().longValue() == OPERACION_MULTICARGA_MULTIEMPRESA){
						registrosError = asignaValorInstitucion(registroMulticarga.getListaRegistros());						
					}										
				}
				// se pasa el archivo a estado autorizado, se guarda la fecha hora de actualizaci&oacute;n, 
				//se guarda el usuario que realizo la operaci&oacute;n, se guarda la lista de registros con error y se actualiza el archivo
				EstadoMulticarga estadoMulticarga = new EstadoMulticarga();
				if(registrosError != null && !registrosError.isEmpty()){
					registroMulticarga.setListaRegistrosError(registrosError);
					estadoMulticarga.setIdEstadoMulticarga(STATUS_MULTICARGA_AUTORIZADO_CON_ERROR);
				}else{
					estadoMulticarga.setIdEstadoMulticarga(STATUS_MULTICARGA_AUTORIZADO);
				}								
				multicargaInternacional.setEstadoMulticarga(estadoMulticarga);
				multicargaInternacional.setFechaHoraActualizacion(dateUtilService.getCurrentDate());				
				multicargaInternacional.setNombreUsuarioAutorizacion(nombreUsuario.toUpperCase());									
				multicargaInternacionalDao.update(multicargaInternacional);
			}
		}
		return registrosError;
	}
	
	/**
	 * metodo para eliminar el Adp asignado a un Beneficiario al momento 
	 * de ser autorizado un archivo multicarga
	 * @param List lista con los registros previamente cargados y/o autorizados
	 * */
	private void asignaValorAdpNulo(List listaRegistros){
		for(Object obj:listaRegistros){
			RegistroAdp registroAdp = (RegistroAdp)obj;
			if(registroAdp != null && registroAdp.getUoi() != null){
				controlBeneficiariosService.asignaValorAdpNuloBeneficiario(registroAdp.getUoi());
			}			
		}				
	}
	
	/**
	 * metodo para asignar el ADP al beneficiario que contenga el Uoi proporcionado
	 * toma la lista de registros cargada previamente y los itera para ir tomando el valor del
	 * Uoi para consultar al Beneficiario y asignarle el ADP
	 * @param List lista con los registros previamente cargados
	 * @return List lista con registros con error al momento de ser autorizados
	 * */
	private List asignaValorAdp(List listaRegistros){
		List listaRegistrosError = new ArrayList();
		// se itera la lista con los registros y se validan las reglas de negocio
		for(Object obj:listaRegistros){			
			RegistroAdp registroAdp = (RegistroAdp)obj;
			Beneficiario beneAux = beneficiarioDao.consultaBeneficiarioByUoiForAdp(registroAdp.getUoi());	
			// se valida que exista el beneficiario con  el uoi dado
			if(beneAux == null){	
				DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
	    		detalleRegistroErrorAdpVO.setUoi(registroAdp.getUoi());
	    		detalleRegistroErrorAdpVO.setAdp(registroAdp.getAdp());
	    		detalleRegistroErrorAdpVO.setDescripcionError(UOI_SIN_BENEFICIARIO);
	    		listaRegistrosError.add(detalleRegistroErrorAdpVO);
			}else{				
				// se valida que se encuentre autorizado
				if(validatorMulticargaDivIntService.esBeneficiarioDiferenteAutorizado(beneAux)){					
					DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
					detalleRegistroErrorAdpVO.setUoi(registroAdp.getUoi());
		    		detalleRegistroErrorAdpVO.setAdp(registroAdp.getAdp());
    				detalleRegistroErrorAdpVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
    				detalleRegistroErrorAdpVO.setDescripcionError(UOI_NO_AUTORIZADO);
    				listaRegistrosError.add(detalleRegistroErrorAdpVO);
    				// se valida que no tenga ya un ADP asignado
				/*}else if(validatorMulticargaDivIntService.esBeneficiarioConAdpAsignado(beneAux)){							    			
    				DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
    				detalleRegistroErrorAdpVO.setUoi(registroAdp.getUoi());
			    	detalleRegistroErrorAdpVO.setAdp(beneAux.getAdp());
			    	detalleRegistroErrorAdpVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
        			detalleRegistroErrorAdpVO.setDescripcionError(UOI_CON_ADP);
        			listaRegistrosError.add(detalleRegistroErrorAdpVO);
        			*/    						    									
				}
				else{
					/*
					 * Se comenta esta seccion para que el ADP se cargue a todos los custodios
					 */
//					String nombreCustodio = validatorMulticargaDivIntService.nombreCustodioPorCuentaNombrada(beneAux);
//					//se valida que pertenesca a Deutsche
//					if(StringUtils.isBlank(nombreCustodio) || validatorMulticargaDivIntService.esBeneficiarioDiferenteDeutsche(nombreCustodio.trim())){						
//						DetalleRegistroErrorAdpVO detalleRegistroErrorAdpVO = new DetalleRegistroErrorAdpVO();
//						detalleRegistroErrorAdpVO.setUoi(registroAdp.getUoi());
//			    		detalleRegistroErrorAdpVO.setAdp(registroAdp.getAdp());
//        				detalleRegistroErrorAdpVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
//        				detalleRegistroErrorAdpVO.setNombreCustodio(nombreCustodio);
//        				detalleRegistroErrorAdpVO.setDescripcionError(CUSTODIO_INVALIDO);
//        				listaRegistrosError.add(detalleRegistroErrorAdpVO);         			
//					}else{
//						// si  pasa las validaciones se le asigna el ADP
//						beneAux.setAdp(registroAdp.getAdp());
//						beneficiarioDao.update(beneAux);						
//					}
					// Se actualiza el procentaje de retención con respecto al ADP y Cuenta nombrada
					ClaveAdp claveAdp = claveAdpDao.findClaveAdpByClaveAdp(registroAdp.getAdp());
					if(claveAdp != null) {
						beneAux.setAdp(claveAdp.getClaveAdp());
						Integer porcentaje = adpCustodioPorcentajeDao.getPorcentaje(claveAdp.getIdClaveAdp(), beneAux.getIdCuentaNombrada());
						if (porcentaje != null) {
							beneAux.setPorcentajeRetencion((double)porcentaje);
							beneficiarioDao.update(beneAux);
						}else {
							beneAux.setAdp(claveAdp.getClaveAdp());
							beneficiarioDao.update(beneAux);
						}
					}else {
						beneAux.setAdp(registroAdp.getAdp());
						beneficiarioDao.update(beneAux);
					}
				}																																																								
			}		
		}
		// se regresa la lista que puede contener registros que no pasaron alguna validaci&oacute;n
		return listaRegistrosError;
	}
	
	
	
	/**
	 * metodo para eliminar la instituci&oacute;n asignada a un Beneficiario al momento 
	 * de ser autorizado un archivo multicarga
	 * @param List lista con los registros previamente autorizados
	 * */
	private void asignaValorInstitucionNulo(List listaRegistros){
		for(Object obj:listaRegistros){
			RegistroMultiempresa registroMultiempresa  = (RegistroMultiempresa)obj;					
			if(registroMultiempresa != null && registroMultiempresa.getUoi() != null && registroMultiempresa.getInstitucionDestino() != null){
				Beneficiario beneAux = beneficiarioDao.consultaBeneficiarioByUoiForInstituciones(registroMultiempresa.getUoi());
				Institucion institucion = institucionDao.findInstitucionByClaveFolio(registroMultiempresa.getInstitucionDestino());
				if(beneAux != null && institucion != null){
					controlBeneficiariosService.eliminaInstitucionBeneficiarioMulticarga(beneAux.getIdBeneficiario(),institucion.getIdInstitucion());
				}
				
			}
		}				
	}
	
	/**
	 * metodo para asignar la instituci&oacute;n al beneficiario que contenga el Uoi proporcionado
	 * toma la lista de registros cargada previamente y los itera para ir tomando el valor del
	 * Uoi para consultar al Beneficiario y asignarle la instituci&oacute;n
	 * @param List lista con los registros previamente cargados
	 * @return List lista con registros con error al momento de ser autorizados
	 * */
	private List asignaValorInstitucion(List listaRegistros){
		List listaRegistrosError = new ArrayList();
		for(Object obj:listaRegistros){			
			Institucion institucionDestino = null;
			RegistroMultiempresa registroMultiempresa  = (RegistroMultiempresa)obj;
			Beneficiario beneAux = beneficiarioDao.consultaBeneficiarioByUoiForInstituciones(registroMultiempresa.getUoi());
			// se valida que exista el beneficiario con  el uoi dado
			if(beneAux == null){				
				DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
    			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
        		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
        		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(registroMultiempresa.getInstitucionDestino());
        		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_SIN_BENEFICIARIO);
				listaRegistrosError.add(detalleRegistroErrorMultiempresaVO);
			}else{				
				// se valida que se encuentre autorizado
				if(validatorMulticargaDivIntService.esBeneficiarioDiferenteAutorizado(beneAux) ){					
					DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
        			detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
            		detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
            		detalleRegistroErrorMultiempresaVO.setInstitucionDestino(registroMultiempresa.getInstitucionDestino());
            		detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
            		detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_NO_AUTORIZADO);
    				listaRegistrosError.add(detalleRegistroErrorMultiempresaVO);    					   				
				}else{
					// se valida que que exista la instituci&oacute;n origen, que el beneficiario tenga la instituci&oacute;n origen y no contenga la instituci&oacute;n destino
    				Institucion institucionOrigen = institucionDao.findInstitucionByClaveFolio(registroMultiempresa.getInstitucionOrigen());
    				if(institucionOrigen == null){    					
        				DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
                		detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
                    	detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
                    	detalleRegistroErrorMultiempresaVO.setInstitucionDestino(registroMultiempresa.getInstitucionDestino());
                    	detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
                    	detalleRegistroErrorMultiempresaVO.setDescripcionError(INSTITUCION_ORIGEN_NO_ENCONTRADA);
            			listaRegistrosError.add(detalleRegistroErrorMultiempresaVO);    						    					
    				}else if(!validatorMulticargaDivIntService.validaInstitucionBeneficiario(beneAux,institucionOrigen.getIdInstitucion())){    					
    					DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
    					detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
    					detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
    					detalleRegistroErrorMultiempresaVO.setInstitucionDestino(registroMultiempresa.getInstitucionDestino());
    					detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
    					detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_NO_EN_INSTITUCION);
    					listaRegistrosError.add(detalleRegistroErrorMultiempresaVO);    						    					
    				}else{ 
    					institucionDestino = institucionDao.findInstitucionByClaveFolio(registroMultiempresa.getInstitucionDestino());
    					if(validatorMulticargaDivIntService.validaInstitucionBeneficiario(beneAux,institucionDestino.getIdInstitucion())){    					
    						DetalleRegistroErrorMultiempresaVO detalleRegistroErrorMultiempresaVO = new DetalleRegistroErrorMultiempresaVO();
    						detalleRegistroErrorMultiempresaVO.setUoi(registroMultiempresa.getUoi());
    						detalleRegistroErrorMultiempresaVO.setInstitucionOrigen(registroMultiempresa.getInstitucionOrigen());
    						detalleRegistroErrorMultiempresaVO.setInstitucionDestino(registroMultiempresa.getInstitucionDestino());
    						detalleRegistroErrorMultiempresaVO.setEstadoBeneficiario(beneAux.getStatusBenef().getDescStatusBenef());
    						detalleRegistroErrorMultiempresaVO.setDescripcionError(UOI_YA_CUENTA_CON_INSTITUCION);
    						listaRegistrosError.add(detalleRegistroErrorMultiempresaVO);     						
    					}else{
    						// si  pasa las validaciones se le asigna la nueva Instituci&oacute;n
    						BeneficiarioInstitucion beneficiarioInstitucion = new BeneficiarioInstitucion();
    						beneficiarioInstitucion.setBeneficiario(beneAux.getIdBeneficiario());
    						beneficiarioInstitucion.setInstitucion(institucionDestino.getIdInstitucion());
    						beneficiarioDao.save(beneficiarioInstitucion);    					  					
    					}
    				}	
				}																					
			}		
		}
		// se regresa la lista que puede contener registros que no pasaron alguna validaci&oacute;n
		return listaRegistrosError;
	}
	
	
	/** se inyectan las dependencias*/
	
	public void setValidatorMulticargaDivIntService(
			ValidatorMulticargaDivIntService validatorMulticargaDivIntService) {
		this.validatorMulticargaDivIntService = validatorMulticargaDivIntService;
	}

	public void setMulticargaInternacionalDao(
			MulticargaInternacionalDao multicargaInternacionalDao) {
		this.multicargaInternacionalDao = multicargaInternacionalDao;
	}

	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}
	
	public void setBeneficiarioDao(BeneficiarioDao beneficiarioDao) {
		this.beneficiarioDao = beneficiarioDao;
	}
	
	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}
	
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}
	
	public void setClaveAdpDao (ClaveAdpDao claveAdpDao) {
		this.claveAdpDao = claveAdpDao;
	}

	public void setAdpCustodioPorcentajeDao(AdpCustodioPorcentajeDao adpCustodioPorcentajeDao) {
		this.adpCustodioPorcentajeDao = adpCustodioPorcentajeDao;
	}	
}