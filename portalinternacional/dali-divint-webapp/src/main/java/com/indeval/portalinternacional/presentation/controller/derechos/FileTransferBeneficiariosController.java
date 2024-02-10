package com.indeval.portalinternacional.presentation.controller.derechos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Derecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.PosicionLiquidacion;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.derechos.util.FileTransferUtil;
import com.indeval.portalinternacional.presentation.derechos.util.ParserFile;

public class FileTransferBeneficiariosController  extends ControllerBase{
	
	/** Log de clase. */
	private static final Logger LOG = LoggerFactory.getLogger(FileTransferBeneficiariosController.class);
	private static final String ERROR_LONGITUD = "Longitud incorrecta del beneficiario (Deben de ser 107 posiciones en total)";
	
	private UploadedFile archivo;
	private ControlDerechosService controlDerechosService;
	private DateUtilService dateUtilService;
	private ControlBeneficiariosService beneficiariosService;
	private FileTransferBeneficiariosService fileTransferBeneficiariosService;
	private String error;
	private Date fechaCarga;
	private String horaCarga;
	private long totalCargados;
	private long totalError;
	private long totalRegistros;
	private boolean showErrors;
	private boolean banderaInicio = true;
	
	/** Lista para el detalle **/
	List<FileTransBeneficiarioVO> detalleCarga;
	
	/** VO con los datos del agente firmado */
	private AgenteVO agenteVO;
	
	/** idFolio de la instituci&oacute;n */
	private String idFolioInstitucion;
		
		
    public String getInit() {
    	if(banderaInicio) {
    		 paginaVO.setRegistrosXPag(20);
	         banderaInicio = false;
    	}
    	
    	this.agenteVO = this.getAgenteFirmado();
    	this.idFolioInstitucion = this.agenteVO.getId() + this.agenteVO.getFolio();
    	getResumen();    	
        return null;
    }
    
    private void getResumen(){    	
		totalError = fileTransferBeneficiariosService.countBeneficiariosConError(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio());
		totalCargados = fileTransferBeneficiariosService.countBeneficiariosSinError(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio());
		totalRegistros = totalCargados+totalError;
		
		if(totalRegistros > 0){
			showErrors = true;
			paginaVO.setOffset(0);
			ejecutarConsulta();
		}
    }
    
    public void uploadFile(ActionEvent e) {
    
    	if(archivo == null){
    		addErrorMessage("El archivo es requerido.");
    		return;
    	}
    	if(archivo.getSize() == 0){
    		addErrorMessage("El archivo esta vacio");
    		return;
    	}
    	if(!archivo.getContentType().equalsIgnoreCase("text/plain")){
    		error = "El archivo contiene caracteres invalidos";
    		return;
    	}
    	error = null;
    	
    	try {
    		Double sizeFT = new Double(archivo.getSize())/Constantes.TAMANIO_KB;
			if(sizeFT > Constantes.SIZE_FT_BENEFICIARIO){
				DecimalFormat df = new DecimalFormat("#.00");
				addErrorMessage("El contenido del archivo es: " + df.format(sizeFT) + " Kb, debe ser menor a: " + Constantes.SIZE_FT_BENEFICIARIO + " Kb");
	        	return;
			}
		} catch (Exception ex) {
			LOG.error("Error :: " ,ex);
			return;
		}
    	
    	processFile();

    }
    
    public void guardar(ActionEvent e) {
    	List<FileTransBeneficiarioVO> beneficiariosVo = null;
    	List<FileTransBeneficiario> lstTransBeneficiarios = null;
    	List<DerechoBeneficiario> derechoBeneficiarios  =null;
    	
    	lstTransBeneficiarios = fileTransferBeneficiariosService.consultaCargaExistenteSinError(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio());
    	
    	beneficiariosVo = creaListaBeneficiarioVo(lstTransBeneficiarios);
    	if(beneficiariosVo != null && !beneficiariosVo.isEmpty()){    		
    		validaCargaPrevia(beneficiariosVo);    		
    		derechoBeneficiarios = creaListaBeneficiario(beneficiariosVo);    		
    		controlDerechosService.agregaBeneficiariosDerecho(derechoBeneficiarios);    		
        	error = "Se agregaron los beneficiarios correctamente.";        	
    	}    	
    	fileTransferBeneficiariosService.eliminaCargaExistente(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio(), Boolean.TRUE);
    	getInit();		
//		showErrors = false;
		paginaVO.setOffset(0);
    }
    
    public void cancelar(ActionEvent e) {
		fileTransferBeneficiariosService.eliminaCargaExistente(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio(), Boolean.FALSE);
		getInit();	
		showErrors = false;
		paginaVO.setOffset(0);
    }
    
    private void processFile(){
    	List<FileTransBeneficiarioVO> registros = null;
    	FileTransBeneficiarioVO benefFileTransfer = null;
    	ParserFile parserFile = null;
    	String beneficiario = null;
    	List<String> rows = null;
    	String error = null;
    	Derecho derecho = null;
    	//idDerecho,horarioValido
    	Map<Long, Boolean> horarios = null;
    	//idDerecho,(idCuenta,totalTitulos)
    	Map<Long, Map<Long, Long>> totalesXDerecho = null;
    	//idDerecho,(idCuenta,totalTitulos), totales que faltan por asignar
    	Map<Long, Map<Long, Long>> totalesRestantes = null;
    	String prfixMEssage = "Error : ";
    	boolean requiereBeneficiarios = false;
    	EmisionVO emisionVO = null;
    	    	
    	try {
			rows = FileTransferUtil.readFile(archivo.getInputStream());
			if(rows != null && !rows.isEmpty()){
				parserFile = new ParserFile();
				registros = new ArrayList<FileTransBeneficiarioVO>();
				horarios = new HashMap<Long, Boolean>();
				totalesXDerecho = new HashMap<Long, Map<Long,Long>>();
				totalesRestantes = new HashMap<Long, Map<Long,Long>>();
				for(int i = 0;i<rows.size();i++){
					beneficiario = rows.get(i);
					benefFileTransfer = new FileTransBeneficiarioVO();
					error = parserFile.parseBeneficiario(beneficiario, benefFileTransfer);
					if(StringUtils.isNotBlank(error)){
						benefFileTransfer.setError(prfixMEssage+error);						
					}										
					benefFileTransfer.setLinea(beneficiario);
					
					if(StringUtils.isBlank(benefFileTransfer.getError())){
						derecho = getDerecho(benefFileTransfer);
						if(derecho == null){
							benefFileTransfer.setError(prfixMEssage+"El derecho no existe.");
                            LOG.debug("\n\n####### El derecho no existe=[" + benefFileTransfer.getLinea() + "]\n\n");
						}else{
							benefFileTransfer.setIdDerecho(derecho.getIdDerechoCapital());
						}
					} else {
						LOG.debug(" BENEFICIARIO CON ERROR");
					}

					LOG.debug(" BENEFICIARIO benefFileTransfer :: " + benefFileTransfer.toString());
					
					if(StringUtils.isBlank(benefFileTransfer.getError())){
						emisionVO = new EmisionVO();
						emisionVO.setTv(derecho.getCuponOrigen().getEmision().getInstrumento().getClaveTipoValor());
						emisionVO.setEmisora(derecho.getCuponOrigen().getEmision().getEmisora().getClavePizarra());
						emisionVO.setSerie(derecho.getCuponOrigen().getEmision().getSerie());
						emisionVO.setIsin(derecho.getCuponOrigen().getEmision().getIsin());
						
						requiereBeneficiarios = controlDerechosService.requireBeneficiariosDerecho(emisionVO, derecho.getCuponOrigen().getEmision().getIdEmision());
					} 
					if(StringUtils.isBlank(benefFileTransfer.getError()) && !requiereBeneficiarios){
					    benefFileTransfer.setError(prfixMEssage+" La emision del derecho no requiere beneficiarios.");
					}
					
					if(StringUtils.isBlank(benefFileTransfer.getError()) && !validaHorario(derecho, horarios)){
						benefFileTransfer.setError(prfixMEssage+" Horario invalido para agregar beneficiarios a este derecho");
					}
	
					if(StringUtils.isBlank(benefFileTransfer.getError())){
						error = validaBeneficiario(benefFileTransfer,derecho);
						if(StringUtils.isNotBlank(error)){
							benefFileTransfer.setError(prfixMEssage+error);
						}
					}
					
					if(StringUtils.isBlank(benefFileTransfer.getError())){
						error = validaTotal(derecho, benefFileTransfer, totalesXDerecho,totalesRestantes);
						if(StringUtils.isNotBlank(error)){
							benefFileTransfer.setError(prfixMEssage+error);
						}
					}
							
					LOG.debug(" BENEFICIARIO benefFileTransfer ADD!! :: " + benefFileTransfer.toString());
					registros.add(benefFileTransfer);
				}
				if(registros != null && !registros.isEmpty()){
					LOG.debug(" Prepare registros guardaCarga ");
					List<FileTransBeneficiario> listFileTransfer = creatListFileTransfer(registros);
					LOG.debug(" listFileTransfer AA :: " + listFileTransfer);
					if(listFileTransfer != null && !listFileTransfer.isEmpty() && listFileTransfer.size() > 0){
						LOG.debug(" Prepare registros guardaCarga size B :: " + listFileTransfer.size());
						
						for (FileTransBeneficiario fileTransBeneficiario : listFileTransfer) {
							LOG.debug(" fileTransBeneficiario for :: " + fileTransBeneficiario.toString());
						}
						try{
							fileTransferBeneficiariosService.guardaCarga(listFileTransfer);	
						} catch (Exception ex){
							LOG.error("Error al guardar los registros: ", ex);
							this.addWarnMessage("Error al guardar los registros");
						}
					} else {
						LOG.debug(" listFileTransfer VACIO!");	
					}
				} else {
					LOG.debug(" Prepare registros guardaCarga VACIO!");
				}
				getInit();
			} else {
				LOG.debug(" ROWS is Empty ");
			}			
		} catch (Exception e) {			
			LOG.error(e.getMessage());
			this.addErrorMessage(e.getMessage());
		}
    	
    }
    
    private List<FileTransBeneficiario> creatListFileTransfer(List<FileTransBeneficiarioVO> fileTransBeneficiarioVOs) {
        LOG.debug(" creatListFileTransfer");
        Map<String,FileTransBeneficiario> benefXDerXCuenta = null;
        FileTransBeneficiario fileTransBeneficiario = null;
        List<FileTransBeneficiario> beneficiarios = null;
        long cantidadTitulos = 0;
        String prfixMEssage = "Error : ";
        if (fileTransBeneficiarioVOs != null && !fileTransBeneficiarioVOs.isEmpty()) {
            LOG.debug(" fileTransBeneficiarioVOs.size :: " + fileTransBeneficiarioVOs.size());
            beneficiarios = new ArrayList<FileTransBeneficiario>();
            benefXDerXCuenta = new HashMap<String, FileTransBeneficiario>();
            Integer uoiCountTmp = 1;
            for (FileTransBeneficiarioVO beneficiarioVO : fileTransBeneficiarioVOs) {
                LOG.debug(" beneficiarioVO ::: " + beneficiarioVO.toString());
                if (!StringUtils.isBlank(beneficiarioVO.getError()) && 
                    beneficiarioVO.getError().equals(prfixMEssage + ERROR_LONGITUD)) {
                    LOG.debug(" ERROR_LONGITUD ::: " + ERROR_LONGITUD);
                    fileTransBeneficiario = new FileTransBeneficiario();
                    fileTransBeneficiario.setCantidadTitulos(beneficiarioVO.getCantidadTitulos());
                    fileTransBeneficiario.setConsecCampoError(0);
                    fileTransBeneficiario.setCuenta(beneficiarioVO.getCuenta());
                    fileTransBeneficiario.setCupon(beneficiarioVO.getCupon());
                    fileTransBeneficiario.setEmisora(beneficiarioVO.getEmisora());
                    fileTransBeneficiario.setError(beneficiarioVO.getError());
                    fileTransBeneficiario.setFechaCorte(beneficiarioVO.getFechaCorte());
                    fileTransBeneficiario.setFechaRegistro(dateUtilService.getCurrentDate());
                    fileTransBeneficiario.setFolioInstitucion(getAgenteFirmado().getFolio());
                    fileTransBeneficiario.setIdInstitucion(Integer.valueOf(getAgenteFirmado().getId()));
                    fileTransBeneficiario.setLinea(beneficiarioVO.getLinea());
                    fileTransBeneficiario.setSerie(beneficiarioVO.getSerie());
                    fileTransBeneficiario.setSubtipoDerecho(beneficiarioVO.getSubtipoDerecho());
                    fileTransBeneficiario.setTipoDerecho(beneficiarioVO.getTipoDerecho());
                    fileTransBeneficiario.setTv(beneficiarioVO.getTv());
                    fileTransBeneficiario.setUoi(beneficiarioVO.getUoi());
                    benefXDerXCuenta.put("ERROR" + uoiCountTmp + "" + "", fileTransBeneficiario);
                    uoiCountTmp++;
                }
                else {
                    fileTransBeneficiario = benefXDerXCuenta.get(beneficiarioVO.getTv() + beneficiarioVO.getEmisora() + 
                                                                 beneficiarioVO.getSerie() + beneficiarioVO.getCupon() + 
                                                                 beneficiarioVO.getTipoDerecho() + 
                                                                 (beneficiarioVO.getSubtipoDerecho()!=null ? 
                                                                     beneficiarioVO.getSubtipoDerecho() : "") + 
                                                                 beneficiarioVO.getUoi() + 
                                                                 beneficiarioVO.getFechaCorteStr() + 
                                                                 beneficiarioVO.getCuenta() 
                                                                 );
                    LOG.debug(" Flujo normal fileTransBeneficiario :: " + fileTransBeneficiario);
                    if (fileTransBeneficiario != null) {
                        cantidadTitulos = fileTransBeneficiario.getCantidadTitulos().longValue() + 
                                          beneficiarioVO.getCantidadTitulos().longValue();
                        fileTransBeneficiario.setCantidadTitulos(Long.valueOf(cantidadTitulos));
                    }
                    else {
                        fileTransBeneficiario = new FileTransBeneficiario();
                        fileTransBeneficiario.setCantidadTitulos(beneficiarioVO.getCantidadTitulos());
                        fileTransBeneficiario.setConsecCampoError(0);
                        fileTransBeneficiario.setCuenta(beneficiarioVO.getCuenta());
                        fileTransBeneficiario.setCupon(beneficiarioVO.getCupon());
                        fileTransBeneficiario.setEmisora(beneficiarioVO.getEmisora());
                        fileTransBeneficiario.setError(beneficiarioVO.getError());
                        fileTransBeneficiario.setFechaCorte(beneficiarioVO.getFechaCorte());
                        fileTransBeneficiario.setFechaRegistro(dateUtilService.getCurrentDate());
                        fileTransBeneficiario.setFolioInstitucion(getAgenteFirmado().getFolio());
                        fileTransBeneficiario.setIdInstitucion(Integer.valueOf(getAgenteFirmado().getId()));
                        fileTransBeneficiario.setLinea(beneficiarioVO.getLinea());
                        fileTransBeneficiario.setSerie(beneficiarioVO.getSerie());
                        fileTransBeneficiario.setSubtipoDerecho(beneficiarioVO.getSubtipoDerecho());
                        fileTransBeneficiario.setTipoDerecho(beneficiarioVO.getTipoDerecho());
                        fileTransBeneficiario.setTv(beneficiarioVO.getTv());
                        fileTransBeneficiario.setUoi(beneficiarioVO.getUoi());
                        benefXDerXCuenta.put(beneficiarioVO.getTv() + beneficiarioVO.getEmisora() +  
                                beneficiarioVO.getSerie() + beneficiarioVO.getCupon() + beneficiarioVO.getTipoDerecho() + 
                                (beneficiarioVO.getSubtipoDerecho()!=null ? beneficiarioVO.getSubtipoDerecho() : "") + 
                                beneficiarioVO.getUoi() + beneficiarioVO.getFechaCorteStr() + beneficiarioVO.getCuenta(), 
                                fileTransBeneficiario);
                    } 
                }
            }
            beneficiarios.addAll(benefXDerXCuenta.values());
            LOG.debug(" Flujo normal Terminado");
        } else {
            LOG.debug(" fileTransBeneficiarioVOs is NULL");
        }

        LOG.debug(" Flujo RETURN");
        return beneficiarios;
    }
    
    
    private boolean validaHorario(Derecho derecho,Map<Long, Boolean> horarios){
    	EmisionVO emisionVO = null;
    	HorarioBeneficiario beneficiario = null;
		HorarioBeneficiario horarioInst = null;
    	boolean isHorarioValido = false;
    	Long idEmision = null;
    	
    	emisionVO = new EmisionVO();
    	emisionVO.setTv(derecho.getCuponOrigen().getEmision().getInstrumento().getClaveTipoValor());
    	emisionVO.setEmisora(derecho.getCuponOrigen().getEmision().getEmisora().getClavePizarra());
    	emisionVO.setSerie(derecho.getCuponOrigen().getEmision().getSerie());
    	idEmision = derecho.getCuponOrigen().getEmision().getIdEmision();
    	
    	if(horarios.containsKey(derecho.getIdDerechoCapital())){
    		LOG.debug("Horario consultado para el dereccho ["+derecho.getIdDerechoCapital()+"]");
    		isHorarioValido = horarios.get(derecho.getIdDerechoCapital()).booleanValue();
    	}else{
	    	beneficiario = controlDerechosService.getHorarioBenefDerecho(emisionVO, idEmision);
			if(beneficiario != null && isHorarioValido(beneficiario,derecho.getFechaCorte(),derecho.getFechaPago())){			
				isHorarioValido = true;			
			}else{
				horarioInst = controlDerechosService.getHorarioInstitucion(emisionVO,idEmision,Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio());
				if(horarioInst != null && isHorarioValido(horarioInst,derecho.getFechaCorte(),derecho.getFechaPago())){
					isHorarioValido = true;
				}else{				
					isHorarioValido = false;
				}					
			}
			LOG.debug("Agregando horario al mapa de derechos...");
			horarios.put(derecho.getIdDerechoCapital(), Boolean.valueOf(isHorarioValido));
		}
    	
    	return isHorarioValido;    	
    }
    
    private String validaTotal(Derecho derecho,FileTransBeneficiarioVO fileTransfer,Map<Long, Map<Long, Long>> totales,Map<Long, Map<Long, Long>> totalesRestantes){
    	String error = null;
    	
    	fillTotales(derecho, totales, totalesRestantes);
    	if(!canSubstractPosicionAsignada(derecho, fileTransfer.getCantidadTitulos(), fileTransfer.getIdCuenta(), totalesRestantes)){
    		error = "No se puede asignar la cantidad de ["+fileTransfer.getCantidadTitulos()+"] titulos de la cuenta ["+fileTransfer.getCuenta()+"], se excedio el total de la cuenta.";
    	}
    	
    	return error;
    }
    
    private String validaBeneficiario(FileTransBeneficiarioVO benefFileTransfer,Derecho derecho){
    	Long cuentaNombradaCustodio = null;
		AgenteVO agenteVO = null;
    	String error = null;
    	Beneficiario beneficiario = null;
    	Long idCuentaNombrada = null;
    	CuentaNombrada cuentaNombrada = null;
    	String prefixMessage = "El beneficiario con UOI";
    	
    	agenteVO = getAgenteFirmado();
    	cuentaNombradaCustodio = controlDerechosService.getCuentaNombradaOfCustodio(derecho.getCuponOrigen().getEmision().getIdEmision());    	
    	beneficiario = beneficiariosService.consultaBeneficiarioUoiInst(benefFileTransfer.getUoi(),null,null,null);
    	
    	cuentaNombrada = controlDerechosService.getCuenta(benefFileTransfer.getCuenta(), Integer.valueOf(agenteVO.getId()), agenteVO.getFolio());
    	if(cuentaNombrada != null && cuentaNombrada.getIdCuentaNombrada() != null){
    		idCuentaNombrada = cuentaNombrada.getIdCuentaNombrada();
    		benefFileTransfer.setIdCuenta(idCuentaNombrada);
    	}
    	
    	if(idCuentaNombrada == null){
    		error = "No existe la cuenta ["+benefFileTransfer.getCuenta()+"]";
    	}else if(beneficiario == null){
			error = prefixMessage+" ["+benefFileTransfer.getUoi()+"] no existe";
		}else if(cuentaNombradaCustodio.longValue() != beneficiario.getIdCuentaNombrada()){					
			error = prefixMessage+" ["+benefFileTransfer.getUoi()+"] no corresponde con el custodio de la emision.";
		}else if(beneficiario.getStatusBenef().getIdStatusBenef().longValue() != 2){
			error = prefixMessage+" ["+benefFileTransfer.getUoi()+"] se encuentra "+beneficiario.getStatusBenef().getDescStatusBenef();
		}else if(controlDerechosService.existeBeneficiarioDerecho(beneficiario.getIdBeneficiario(), idCuentaNombrada,derecho.getIdDerechoCapital())){
			error = prefixMessage+" ["+benefFileTransfer.getUoi()+"] ya existe como beneficiario del derecho.";
		}else{
			beneficiario = controlDerechosService.getBeneficiarioCuentaByUoi(benefFileTransfer.getUoi(), idCuentaNombrada);					
			if(beneficiario == null){
				error = prefixMessage+" ["+benefFileTransfer.getUoi()+"] no tiene asignada la instituci\u00F3n de la cuenta.";
			}
		}
    	
    	if(StringUtils.isBlank(error)){
    		benefFileTransfer.setIdBeneficiario(beneficiario.getIdBeneficiario());
    	}
    	
    	return error;
    }
    
    private Derecho getDerecho(FileTransBeneficiarioVO benefFileTransfer){
    	ConsultaDerechosParam param = new ConsultaDerechosParam();
    	EmisionVO emisionVO = new EmisionVO();
    	Derecho derecho = null;
    	
    	emisionVO.setCupon(benefFileTransfer.getCupon());
    	emisionVO.setSerie(benefFileTransfer.getSerie());
    	emisionVO.setEmisora(benefFileTransfer.getEmisora());
    	emisionVO.setTv(benefFileTransfer.getTv());    	
    	param.setEmision(emisionVO);
    	param.setFechaCorte(benefFileTransfer.getFechaCorte());
    
    	if(benefFileTransfer.getTipoDerecho().intValue() == Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO){
    		derecho = controlDerechosService.getDerecho(param, benefFileTransfer.getTipoDerecho(),0);
    	}else{
    		derecho = controlDerechosService.getDerecho(param, benefFileTransfer.getTipoDerecho(),benefFileTransfer.getSubtipoDerecho().intValue());
    	}
    	
    	return derecho;
    }    
    
	private boolean isHorarioValido(HorarioBeneficiario beneficiario,Date fechaCorte,Date fechaPago){
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.setTime(dateUtilService.getCurrentDate());
		Calendar fechaLimite = Calendar.getInstance();
		if(beneficiario.getEsDespuesFechaCorte()){
			fechaLimite.setTime(dateUtilService.agregarDiasHabiles(fechaCorte, beneficiario.getDias().intValue()));
		}else{
			fechaLimite.setTime(dateUtilService.agregarDiasHabiles(fechaPago, (beneficiario.getDias().intValue())*(-1)));
		}
		fechaLimite.set(Calendar.HOUR_OF_DAY,beneficiario.getHora().intValue());
		fechaLimite.set(Calendar.MINUTE, beneficiario.getMinuto().intValue());		
		return fechaActual.before(fechaLimite);
	}
	
	private void fillTotales(Derecho derecho,Map<Long, Map<Long, Long>> totales,Map<Long, Map<Long, Long>> totalesRestantes){
		PaginaVO paginaVO = null;
		Map<Long, Long> cuentasTotales = null;
		Map<Long, Long> cuentasRestantes = null;
		List<PosicionLiquidacion> posiciones = null;
		
		if(!totalesRestantes.containsKey(derecho.getIdDerechoCapital())){		
			paginaVO = controlDerechosService.getCuentasDerecho(derecho.getIdDerechoCapital(), paginaVO);
			posiciones = (List<PosicionLiquidacion>)paginaVO.getRegistros();			
			if(posiciones != null && !posiciones.isEmpty()){				
				cuentasTotales = new HashMap<Long, Long>();
				cuentasRestantes  = new HashMap<Long, Long>();
				for(PosicionLiquidacion liquidacion : posiciones){
					cuentasTotales.put(liquidacion.getCuenta().getIdCuentaNombrada(), liquidacion.getSaldo());
					cuentasRestantes.put(liquidacion.getCuenta().getIdCuentaNombrada(), liquidacion.getSaldo());
				}
				totalesRestantes.put(derecho.getIdDerechoCapital(), cuentasRestantes);
				totales.put(derecho.getIdDerechoCapital(), cuentasTotales);
				substractPosicionAsignada(derecho, totalesRestantes);
			}
		}
		
	}
	
	private void substractPosicionAsignada(Derecho derecho,Map<Long, Map<Long, Long>> totalesRestantes){
		Map<Long,Long> cuentas = null;
		List<DerechoBeneficiario> beneficiarios = null;
		Long titulosTmp = null;
		
		if(totalesRestantes.containsKey(derecho.getIdDerechoCapital())){
			cuentas = totalesRestantes.get(derecho.getIdDerechoCapital());
			beneficiarios = controlDerechosService.getBenefDerechoXCuenta(derecho.getIdDerechoCapital(),cuentas.keySet().toArray(new Long[]{}));
			if(beneficiarios != null && !beneficiarios.isEmpty()){
				for(DerechoBeneficiario beneficiario : beneficiarios){
					titulosTmp = cuentas.get(beneficiario.getCuentaNombrada().getIdCuentaNombrada());
					titulosTmp = Long.valueOf(titulosTmp.longValue()-beneficiario.getAsignacion().longValue());
					cuentas.put(beneficiario.getCuentaNombrada().getIdCuentaNombrada(), titulosTmp);
				}
			}
		}
		
	}
	
	private boolean canSubstractPosicionAsignada(Derecho derecho,Long cantidad,Long idCuenta,Map<Long, Map<Long, Long>> totalesRestantes){
		boolean canSubstract = false;
		Long restente = null;
		Map<Long, Long> cuentas = null;
		
		if(totalesRestantes.containsKey(derecho.getIdDerechoCapital())){
			cuentas = totalesRestantes.get(derecho.getIdDerechoCapital());
			if(cuentas.containsKey(idCuenta)){
				restente = cuentas.get(idCuenta);
				if((restente.longValue() - cantidad.longValue()) >= 0){
					canSubstract = true;
					cuentas.put(idCuenta, Long.valueOf((restente.longValue() - cantidad.longValue())));
				}
			}
		}				
		return canSubstract;
	}
	
	private List<FileTransBeneficiarioVO> creaListaBeneficiarioVo(List<FileTransBeneficiario> transBeneficiarios){
		
		List<FileTransBeneficiarioVO> lstBeneficiarios = null;
		FileTransBeneficiarioVO beneficiarioVO = null;
		
        if(transBeneficiarios != null && !transBeneficiarios.isEmpty()){
        	lstBeneficiarios = new ArrayList<FileTransBeneficiarioVO>();
        	for(FileTransBeneficiario fileTransBeneficiario:transBeneficiarios){
        		beneficiarioVO = new FileTransBeneficiarioVO();
        		beneficiarioVO.transform(fileTransBeneficiario);
        		beneficiarioVO.setFolio(this.idFolioInstitucion);
        		lstBeneficiarios.add(beneficiarioVO);
        	}        	        	
        }
		
		return lstBeneficiarios;
	}	
	
	private List<DerechoBeneficiario> creaListaBeneficiario(List<FileTransBeneficiarioVO> beneficiarioVOs){
		
		List<DerechoBeneficiario> lstBeneficiarios = null;
		DerechoBeneficiario beneficiarioDerecho = null;
		CuentaNombrada cuentaNombrada = null;	
		EstatusDerecho estatusDerecho = null;
		Beneficiario beneficiario = null;
		DerechoBeneficiario tmp = null;
		
		if(beneficiarioVOs != null && !beneficiarioVOs.isEmpty()){
			lstBeneficiarios = new ArrayList<DerechoBeneficiario>();
			for(FileTransBeneficiarioVO fileTransfer:beneficiarioVOs){
				if(StringUtils.isBlank(fileTransfer.getError())){
					beneficiarioDerecho = new DerechoBeneficiario();
					cuentaNombrada = new CuentaNombrada();
					estatusDerecho = new EstatusDerecho();
					beneficiario = new Beneficiario();				
					cuentaNombrada.setIdCuentaNombrada(fileTransfer.getIdCuenta());
					if(controlDerechosService.existeDerechoNoCortado(fileTransfer.getIdDerecho())){
						tmp = controlDerechosService.getDerechoNoCortado(fileTransfer.getIdDerecho());
						if(tmp != null && tmp.getEstatusDerecho() != null){
							estatusDerecho.setIdEstatusDerecho(tmp.getEstatusDerecho().getIdEstatusDerecho());
						}else{
							estatusDerecho.setIdEstatusDerecho(Constantes.ID_ESTADUS_DERECHO_INT_CORTADO);
						}
					}else{
						estatusDerecho.setIdEstatusDerecho(Constantes.ID_ESTADUS_DERECHO_INT_CORTADO);					
					}
					beneficiario.setIdBeneficiario(fileTransfer.getIdBeneficiario());
					beneficiarioDerecho.setAsignacion(fileTransfer.getCantidadTitulos());
					beneficiarioDerecho.setIdDerechoCapital(fileTransfer.getIdDerecho());
					beneficiarioDerecho.setCuentaNombrada(cuentaNombrada);
					beneficiarioDerecho.setEstatusDerecho(estatusDerecho);
					beneficiarioDerecho.setBeneficiario(beneficiario);
					beneficiarioDerecho.setProcesado(Boolean.FALSE);
					lstBeneficiarios.add(beneficiarioDerecho);
				}
			}
		}
		
		return lstBeneficiarios;
	}	
	
	 public String ejecutarConsulta() {
		List<FileTransBeneficiarioVO> lstBeneficiarios = null;
				 
        if (paginaVO != null) {
        	if(paginaVO.getRegistros() != null){
        		paginaVO.getRegistros().clear();
        	}        	
        	paginaVO.setRegistros(null);        	
        }
		 
        setPaginaVO(fileTransferBeneficiariosService.consultaCargaExistente(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio(),paginaVO));

        if(paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()){
        	lstBeneficiarios = creaListaBeneficiarioVo(paginaVO.getRegistros());        	
        	paginaVO.getRegistros().clear();
        	paginaVO.setRegistros(lstBeneficiarios);
        }else{        	
        	paginaVO.setTotalRegistros(0);
        	paginaVO.setRegistros(new ArrayList<FileTransBeneficiario>());
        	this.detalleCarga = null;
        }        
        
		return null;
	}
	 
	private List<FileTransBeneficiarioVO> validaCargaPrevia(List<FileTransBeneficiarioVO> fileTransBeneficiarioVOs){
		List<FileTransBeneficiarioVO> transBeneficiarioVOs = null;
		Derecho derecho = null;
		String prfixMEssage = "Error : ";
		Map<Long, Boolean> horarios = null;
    	Map<Long, Map<Long, Long>> totalesXDerecho = null;
    	Map<Long, Map<Long, Long>> totalesRestantes = null;
		
		if(fileTransBeneficiarioVOs != null && !fileTransBeneficiarioVOs.isEmpty()){
			horarios = new HashMap<Long, Boolean>();
			totalesXDerecho = new HashMap<Long, Map<Long,Long>>();
			totalesRestantes = new HashMap<Long, Map<Long,Long>>();
			transBeneficiarioVOs = new ArrayList<FileTransBeneficiarioVO>();
			for(FileTransBeneficiarioVO benefFileTransfer: fileTransBeneficiarioVOs){
				if(StringUtils.isBlank(benefFileTransfer.getError())){
					derecho = getDerecho(benefFileTransfer);
					if(derecho == null){
						benefFileTransfer.setError(prfixMEssage+" El derecho no existe.");
					}else{
						benefFileTransfer.setIdDerecho(derecho.getIdDerechoCapital());							
					}
				}
				
				if(StringUtils.isBlank(benefFileTransfer.getError()) && !validaHorario(derecho, horarios)){
					benefFileTransfer.setError(prfixMEssage+" Horario invalido para agregar beneficiarios a este derecho");
				}
									
				if(StringUtils.isBlank(benefFileTransfer.getError())){
					error = validaBeneficiario(benefFileTransfer,derecho);
					if(StringUtils.isNotBlank(error)){
						benefFileTransfer.setError(prfixMEssage+error);
					}
				}
				
				if(StringUtils.isBlank(benefFileTransfer.getError())){
					error = validaTotal(derecho, benefFileTransfer, totalesXDerecho,totalesRestantes);
					if(StringUtils.isNotBlank(error)){
						benefFileTransfer.setError(prfixMEssage+error);
					}
				}
				if(StringUtils.isBlank(benefFileTransfer.getError())){					
					transBeneficiarioVOs.add(benefFileTransfer);
				}
			}
		}
		
		return transBeneficiarioVOs;
	}
	
	/**
	 * Regresa la lista que contiene los registros cargados, son los registros para el llenado del archivo excel
	 */
	public void detalleCargaDerechoBeneficiario(ActionEvent e) {
	
		/** Registros con error **/
        this.detalleCarga = new ArrayList<FileTransBeneficiarioVO>();
        List<FileTransBeneficiario> lstBeneficiariosConError = fileTransferBeneficiariosService.consultaBeneficiariosConError(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio());
    	if(lstBeneficiariosConError != null && lstBeneficiariosConError.size() > 0){
    		this.detalleCarga = creaListaBeneficiarioVo(lstBeneficiariosConError);
    	}
    	/** Registros sin error **/
    	List<FileTransBeneficiario> lstTransBeneficiarios = fileTransferBeneficiariosService.consultaCargaExistenteSinError(Integer.valueOf(getAgenteFirmado().getId()),getAgenteFirmado().getFolio());
    	if(lstTransBeneficiarios != null && lstTransBeneficiarios.size() > 0){
    		this.detalleCarga.addAll(creaListaBeneficiarioVo(lstTransBeneficiarios));
    	}
	}
	 
	public void obtenerOperaciones(ActionEvent event) {
		paginaVO.setOffset(0);
		ejecutarConsulta();
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public void setControlDerechosService(
			ControlDerechosService controlDerechosService) {
		this.controlDerechosService = controlDerechosService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public void setBeneficiariosService(
			ControlBeneficiariosService beneficiariosService) {
		this.beneficiariosService = beneficiariosService;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setFileTransferBeneficiariosService(
			FileTransferBeneficiariosService fileTransferBeneficiariosService) {
		this.fileTransferBeneficiariosService = fileTransferBeneficiariosService;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public long getTotalCargados() {
		return totalCargados;
	}

	public long getTotalError() {
		return totalError;
	}

	public long getTotalRegistros() {
		return totalRegistros;
	}

	public String getHoraCarga() {
		return horaCarga;
	}

	public boolean isShowErrors() {
		return showErrors;
	}

	/**
	 * @return the detalleCarga
	 */
	public List<FileTransBeneficiarioVO> getDetalleCarga() {
		return detalleCarga;
	}

	/**
	 * @param detalleCarga the detalleCarga to set
	 */
	public void setDetalleCarga(List<FileTransBeneficiarioVO> detalleCarga) {
		this.detalleCarga = detalleCarga;
	}

	/**
	 * @return the agenteVO
	 */
	public AgenteVO getAgenteVO() {
		return agenteVO;
	}

	/**
	 * @param agenteVO the agenteVO to set
	 */
	public void setAgenteVO(AgenteVO agenteVO) {
		this.agenteVO = agenteVO;
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
	 * @return the banderaInicio
	 */
	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	/**
	 * @param banderaInicio the banderaInicio to set
	 */
	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

}
