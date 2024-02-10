/**
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portalinternacional.middleware.services.validador.ValidadorEmision;
import com.indeval.portalinternacional.middleware.services.validador.ValidadorInstitucion;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.ValidacionesConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCambioBoveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.RSICCambioBoveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferDetalleVO;
import com.indeval.portalinternacional.middleware.servicios.vo.InstitucionesVo;
import com.indeval.portalinternacional.middleware.servicios.vo.ResultadoFTVO;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.FileTransferCambioBovedaDao;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;


/**
 * Implementaci&oacute;n de la interfaz de servicio FileTransferCambioBovedaService
 */
public class FileTransferCambioBovedaServiceImpl implements FileTransferCambioBovedaService {

    /** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FileTransferCambioBovedaServiceImpl.class);

    /**
     * <code>FileTransferCambioBovedaDao</code>
     */
    private FileTransferCambioBovedaDao fileTransferCambioBovedaDao;

    /**
     * <code>ParserAdaptadorService</code>
     */
    private ParserAdaptadorService parserAdaptadorService;

    /**
     * <code>ValidadorInstitucion</code>
     */
    private ValidadorInstitucion validadorInstitucion;

    /**
     * <code>ValidadorEmision</code>
     */
    private ValidadorEmision validadorEmision;

    /**
     * <code>EmisionesConsultasService</code>
     */
    private EmisionesConsultasService emisionesConsultasService;

    private static final String TIPO_MOVIMIENTO_ENTREGA = "E";

    private static final String TIPO_MOVIMIENTO_RECEPCION = "R";
    
    private final String CUENTA_NOMBRADA = "5001";

    /**
     * Secuencia para el Folio Control de las Operaciones SIC
     */
    private String seqFolioControl = null;

    /** 
     * Bean de acceso a los servicios de UtilService 
     */
    private UtilService utilService = null;

    /**
     * Cadena estatica para obtener la referencia del mensaje
     */
    static final String SEQ_REFERENCIA_MENSAJE = "SEQ_REFERENCIA_MENSAJE";

    /** 
     * Bean de acceso a los servicios de DateUtilService 
     */
    private DateUtilService dateUtilService = null;

    /** 
     * Dao que obtiene la Cuenta Nombrada 
     */
    private CuentaNombradaDao cuentaNombradaDao;

    /** 
     * Dao que obtiene la Sic Emision
     */
    private SicEmisionDao sicEmisionDao;

    /** 
     * Dao que obtiene la Sic Detalle
     */
    private SicDetalleDao sicDetalleDao;
    
    /** DAO para consulta de Catbic */
    private CatBicDao catBicDao;

    /**
     * Bean de acceso a los servicios de RSICCambioBovedaService
     */
    private RSICCambioBovedaService rSicCambioBovedaService;

    /** 
     * Dao para operaciones de bd de OperacionSic
     */
    private OperacionSicDao operacionSicDao;

    /** 
     * Dao para operaciones de bd de Bovedas
     */
    private BovedaService bovedaService;

    /** Indica el numero de columnas del layout */
    private final int NUM_COLUMNAS_LAYOUT = 11;
    
	/** Resolvedor de Mensajes */
    private MessageResolver messageResolver;

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCambioBovedaService#getFileTransfer(List, String, String)
     */
    public FileTransferCambioBoveda getFileTransfer(List<String> archivo, String idFolio, String usuario) {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.getFileTransfer()...");
        FileTransferCambioBoveda ft = new FileTransferCambioBoveda();
        ft.setIdFolio(idFolio);
        ft.setUsuarioRegistro(usuario);
        ft.setUsuarioModifico(usuario);
        
        
        List<FileTransferDetalleVO> operaciones = new ArrayList<FileTransferDetalleVO>();
        List<String> erroreslinea = new ArrayList<String>();
        for (String linea : archivo) {
            String[] lsp = linea.split("\\|");
            if (lsp.length < NUM_COLUMNAS_LAYOUT) {
                erroreslinea.add(linea + " ¡Faltan Campos!");
                ft.setErroresSintaxis(true);
                continue;
            }
            try {
                FileTransferDetalleVO ftdvo = new FileTransferDetalleVO();
                //Valida ISIN
                String isin = lsp[0];
        		if(StringUtils.isNotBlank(isin) &&
        				validarExpresionRegular(isin, ValidacionesConstantes.LONGITUD_12, ValidacionesConstantes.ER_ALFANUMERICOS)) {			
        			ftdvo.setIsin(isin);
        		}
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"ISIN"}));
                    ft.setErroresSintaxis(true);
                }
        		//Valida fecha liquidacion
                String fechaLiquidacion = lsp[1];
        		if (StringUtils.isNotBlank(fechaLiquidacion) &&
        				validarExpresionRegular(fechaLiquidacion, ValidacionesConstantes.LONGITUD_10, ValidacionesConstantes.ER_FORMATO_FECHA_DD_MM_YYYY)) {
                    ftdvo.setFechaLiquidacion(fechaLiquidacion);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Fecha Liquidaci\u00F3n"}));
                    ft.setErroresSintaxis(true);
                }
        		//Valida fecha de operacion
        		String fechaOperacion = lsp[2];
        		if (StringUtils.isNotBlank(fechaOperacion) &&
        				validarExpresionRegular(fechaOperacion, ValidacionesConstantes.LONGITUD_10, ValidacionesConstantes.ER_FORMATO_FECHA_DD_MM_YYYY)) {
                    ftdvo.setFechaOperacion(lsp[2]);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Fecha Operaci\u00F3n"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida el custidio
                String custodio = lsp[3];
                if(StringUtils.isNotBlank(custodio) &&
                		validarExpresionRegular(custodio, ValidacionesConstantes.LONGITUD_15, ValidacionesConstantes.ER_BOVEDA)) {
                	ftdvo.setCustodioDestino(custodio);
                }
                else {
                    ftdvo.setCustodioDestino(StringUtils.EMPTY);
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Custodio"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida la cuenta Indeval
                String cuentaIndeval = lsp[4];
                if(StringUtils.isNotBlank(cuentaIndeval) &&
                		validarExpresionRegular(cuentaIndeval, ValidacionesConstantes.LONGITUD_15, ValidacionesConstantes.ER_ALFANUMERICOS)) {
                	ftdvo.setCuentaIndeval(cuentaIndeval);
                }
                else {
                	ftdvo.setCuentaIndeval(StringUtils.EMPTY);
                	ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Cuenta Indeval"}));
                	ft.setErroresSintaxis(true);
                }
                //Valida la cuenta contraparte
                String cuentaContraparte = lsp[5];
                if (StringUtils.isNotBlank(cuentaContraparte) &&
                		validarExpresionRegular(cuentaContraparte, ValidacionesConstantes.LONGITUD_15, ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO)) {
                    ftdvo.setCuentaContraparte(cuentaContraparte);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Cuenta Contraparte"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida descripcion cuenta contraparte
                String descripcionCuentaContraparte = lsp[6];
                if (StringUtils.isNotBlank(descripcionCuentaContraparte) &&
                		validarExpresionRegular(descripcionCuentaContraparte, ValidacionesConstantes.LONGITUD_40, ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO)) {
                    ftdvo.setDescCuentaContraparte(descripcionCuentaContraparte);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Descripci\u00F3n de la Cuenta Contraparte"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida depositante/liquidador
                String depositanteLiquidador = lsp[7];
                if (StringUtils.isNotBlank(depositanteLiquidador) &&
                		validarExpresionRegular(depositanteLiquidador, ValidacionesConstantes.LONGITUD_40, ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO)) {
                    ftdvo.setDepositanteLiquidador(depositanteLiquidador);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Depositante/Liquidador"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida el nombre cuenta beneficiario
                String nombreCuentaBeneficiario = lsp[8];
                if (StringUtils.isNotBlank(nombreCuentaBeneficiario) &&
                		validarExpresionRegular(nombreCuentaBeneficiario, ValidacionesConstantes.LONGITUD_40, ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO)) {
                    ftdvo.setNomCuentaBeneficiario(nombreCuentaBeneficiario);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Nombre Cuenta Beneficiario"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida el numero cuenta beneficiario
                String numeroCuentaBeneficiario = lsp[9];
                if (StringUtils.isNotBlank(numeroCuentaBeneficiario) &&
                		validarExpresionRegular(numeroCuentaBeneficiario, ValidacionesConstantes.LONGITUD_15, ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO)) {
                    ftdvo.setNumCuentaBeneficiario(numeroCuentaBeneficiario);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Numero de Cuenta Beneficiario"}));
                    ft.setErroresSintaxis(true);
                }
                //Valida tipo de movimiento
                String tipoMovimiento = lsp[10];
                if (StringUtils.isNotBlank(tipoMovimiento) && 
                		(TIPO_MOVIMIENTO_ENTREGA.equals(tipoMovimiento) || TIPO_MOVIMIENTO_RECEPCION.equals(tipoMovimiento))) {
                    ftdvo.setTipoMovimiento(tipoMovimiento);
                }
                else {
                    ftdvo.setError(messageResolver.getMessage("J0106", new Object[]{"Tipo Movimiento"}));
                    ft.setErroresSintaxis(true);
                }
                ftdvo.setUsuario(usuario);
                ftdvo.setIdFolio(idFolio);
                operaciones.add(ftdvo);
            } catch (Exception ex) {
                erroreslinea.add(linea + "| ¡Error en Campos!");
                ft.setErroresSintaxis(true);
            }
        }
        ft.setLineasError(erroreslinea);
        ft.setOperaciones(operaciones);

        return ft;      
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCambioBovedaService#crearFileTransfer(FileTransferCambioBoveda)
     */
    public FileTransferCambioBoveda crearFileTransfer(FileTransferCambioBoveda fileTransfer) throws BusinessException {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.crearFileTransfer()...");
        //Listas auxiliares
        List<FileTransferDetalleVO> listadoDetalleNuevo = new ArrayList<FileTransferDetalleVO>();
        List<FileTransferDetalleVO> listadoOperacionesFT = new ArrayList<FileTransferDetalleVO>();
        //Total de operaciones
        int totalOperaciones = fileTransfer.getOperaciones().size();
        //Operaciones con error
        int operacionesError = 0;
        //Genera el XML con las operaciones que llegan a validacion de negocio
        try {
            this.setXmlOriginal(fileTransfer, fileTransfer.getOperaciones());
        }
        catch (Exception e) {
            throw new BusinessException("No se pudo generar el XML del archivo original!", e);
        }
        //Filtra solo las operaciones sin errores de formato o sintaxis
        for (FileTransferDetalleVO fileTransferDetalleVO : fileTransfer.getOperaciones()) {
			if(StringUtils.isNotBlank(fileTransferDetalleVO.getError())) {
				listadoDetalleNuevo.add(fileTransferDetalleVO);
				operacionesError++;
			}
			else {
				listadoOperacionesFT.add(fileTransferDetalleVO);
			}
		}
        // Ordenar las operaciones, primero Entregas y despues Recepciones.
        Map<String, List<FileTransferDetalleVO>> mapaER = this.separarEntregasYRecepciones(listadoOperacionesFT);
        List<FileTransferDetalleVO> listadoOperacionesTemp = new ArrayList<FileTransferDetalleVO>();
        List<FileTransferDetalleVO> listaEntregas = mapaER.get(TIPO_MOVIMIENTO_ENTREGA);
        listadoOperacionesTemp.addAll(listaEntregas);
        listadoOperacionesTemp.addAll(mapaER.get(TIPO_MOVIMIENTO_RECEPCION));

        listadoOperacionesFT = listadoOperacionesTemp;
        mapaER = null;
        listadoOperacionesTemp = null;

        //Validando las operaciones una por una.
        for (FileTransferDetalleVO operacionFT : listadoOperacionesFT) {
            FileTransferDetalleVO ftdvo = new FileTransferDetalleVO();
            this.setDatosFileTransferDetalleVO(ftdvo, operacionFT);
            ftdvo.setUsrCredencial(fileTransfer.getUsrCredencial());
            if (ftdvo.getError() == null) {
                InstitucionesVo institucion = null;
//                Cupon cupon = null;
//                BovedaDto boveda = null;
                
                SicEmision sicEmision = null;
                

                //Validacion de Institucion
                try {
                    institucion = this.validadorInstitucion.validarExistencia(ftdvo.getIdFolio());
                    ftdvo.setIdInstitucion(institucion.getId());
                    ftdvo.setCuenta(CUENTA_NOMBRADA);
                } catch (Exception e) {
                    ftdvo.addError(e.getMessage());
                    e.printStackTrace();
                }

                //Obtiene la emision
                Emisiones emisionValidar = emisionesConsultasService.obtenerEmisionLiberada(ftdvo.getIsin());
                if(emisionValidar != null) {
                	//Completa los datos de la emision
                	ftdvo.setIdEmision(emisionValidar.getIdEmision().intValue());
                    ftdvo.setTipoValor(emisionValidar.getInstrumento().getClaveTipoValor());
                    ftdvo.setEmisora(emisionValidar.getEmisora().getClavePizarra());
                    ftdvo.setSerie(emisionValidar.getSerie());
                    //Completa el cupon
                    for (Cupon cupon : emisionValidar.getCupones()) {
                        if (Constantes.ESTATUS_CUPON_VIGENTE == cupon.getEstadoCupon().getIdEstatusCupon()) {
                            ftdvo.setIdCuponVigente(cupon.getIdCupon().intValue());
                            ftdvo.setClaveCuponVigente(cupon.getClaveCupon());
                            continue;
                        }
                    }
                    //Completa la divisa
                    ftdvo.setIdDivisa(emisionValidar.getDivisa().getIdDivisa().intValue());
                    ftdvo.setClaveAlfabeticaDivisa(emisionValidar.getDivisa().getClaveAlfabetica());
                    
                    //Valida el custodio(boveda)
                    Bovedas bovedaValidar = this.bovedaService.obtenerBovedaByNombreCorto(ftdvo.getCustodioDestino());
                    CatBic catBic = null;
                    if(bovedaValidar != null) {
                    	if(TIPO_MOVIMIENTO_ENTREGA.equalsIgnoreCase(ftdvo.getTipoMovimiento()) &&
                    			!emisionValidar.getIdBoveda().equals(bovedaValidar.getIdBoveda())) {
                    		ftdvo.addError("El custodio es invalido.");
                    	}
                    	catBic = catBicDao.getCatBicByIdCtaNombradaCtaIndeval(
                    		bovedaValidar.getIdCuentaBoveda().longValue(), ftdvo.getCuentaIndeval());
                    	if(catBic != null) {
                    		ftdvo.setIdBoveda(bovedaValidar.getIdBoveda());
                    		ftdvo.setBoveda(catBic.getBicProd());
                    		ftdvo.setBovedaEfectivo(catBic.getBicProd());
                    		ftdvo.setCustodio(catBic.getBicProd());
                    		
                            //Valida que la emision sea valida en el SIC
                            if(TIPO_MOVIMIENTO_ENTREGA.equals(ftdvo.getTipoMovimiento())) {
                            	sicEmision = sicEmisionDao.findSicEmisionVigente(
                            			bovedaValidar.getIdCuentaBoveda().longValue(), 
                            			emisionValidar.getIdEmision().longValue(), 
                            			catBic.getIdCatbic().longValue());
                            }
                            else if(TIPO_MOVIMIENTO_RECEPCION.equals(ftdvo.getTipoMovimiento())) {
                            	sicEmision = 
                            		sicEmisionDao.findSicEmisionByIdEmision(emisionValidar.getIdEmision().longValue());
                            }
                        	if(sicEmision == null) {
                        		ftdvo.addError("La emision no es operable en SIC.");
                        	}
                            //Obtener el SicDetalle
                            Long[] idCatBics = new Long[]{catBic.getIdCatbic()}; 
                            SicDetalle sicDetalle = sicDetalleDao.findSicDetalle(idCatBics, operacionFT.getDepositanteLiquidador());
                            if (sicDetalle != null) {
                            	ftdvo.setLugarDeLiquidacion(sicDetalle.getBicDepLiq());
                            	ftdvo.setIdSicDetalle(sicDetalle.getIdSicDetalle());
                            }
                            else {
                            	ftdvo.addError("El Depositante/Liquidador es incorrecto.");
                            }
                    	}
                    	else {
                        	ftdvo.addError("El Custodio y/o la Cuenta Indeval es invalido.");
                        }
                    }
                    else {
                    	ftdvo.addError("El custodio es invalido.");
                    }
                    
                    // Validar la NO duplicidad de la operacion
                    try {
                        this.validarNoDuplicidadOperacion(ftdvo);
                    } catch (BusinessException e) {
                        ftdvo.addError(e.getMessage());
                        operacionesError++;
                        listadoDetalleNuevo.add(ftdvo);
                        e.printStackTrace();
                        continue;
                    }

                    // Validar que la operacion de Recepcion tenga su par de Entrega 
                    if (ftdvo.getTipoMovimiento().equals(TIPO_MOVIMIENTO_RECEPCION)) {
                        try {
                            if (!this.existeEntregaEnFT(listaEntregas, ftdvo)) {
                                List<OperacionSic> operaciones = this.operacionSicDao.obtenerOperacionDeEntrega(ftdvo.getIdEmision().longValue());
                                if (operaciones == null || operaciones.isEmpty() || operaciones.size() == 0) {
                                	ftdvo.addError("No existe la operaci\u00F3n de Entrega correspondiente.");
                                    operacionesError++;
                                    listadoDetalleNuevo.add(ftdvo);
                                    continue;
                                }
                                else {
                                	OperacionSic operacionSicEntrega = operaciones.get(0);
                                	if(catBic.getIdCatbic().equals(operacionSicEntrega.getCatBic().getIdCatbic())) {
                                    	ftdvo.addError("El custodio para la operaci\u00F3n de recepci\u00F3n es el mismo que para la entrega.");
                                        operacionesError++;
                                        listadoDetalleNuevo.add(ftdvo);
                                        continue;
                                	}
                                }
                            }
                        } catch (BusinessException e) {
                            ftdvo.addError(e.getMessage());
                            operacionesError++;
                            listadoDetalleNuevo.add(ftdvo);
                            e.printStackTrace();
                            continue;
                        }
                    }

                    //Validacion de la Posicion
                    if (ftdvo.getTipoMovimiento().equals(TIPO_MOVIMIENTO_ENTREGA)) {
                    	BigDecimal posicion = null;
                    	//Valida que la emision no tenga posicion cero (disponible + no disponible).
                    	posicion = getSaldoTotal(ftdvo.getIdEmision(), ftdvo.getIdBoveda(), ftdvo.getIdCuponVigente());
                    	if (posicion == null || BigDecimal.ZERO.equals(posicion)) {
                    		ftdvo.addError("La emisi\u00F3n con ISIN " + ftdvo.getIsin() + " no tiene posici\u00F3n.");
                    	}
                    	//Valida que no exista posicion no disponible de la emision
                    	else {                    		
                    		BigDecimal posicionNoDisponible = 
                    				getSaldoPosicionNoDisponible(ftdvo.getIdEmision(), ftdvo.getIdBoveda(), ftdvo.getIdCuponVigente());
                    		if(posicionNoDisponible != null && BigDecimal.ZERO.compareTo(posicionNoDisponible) != 0) {
                    			ftdvo.addError("La emisi\u00F3n con ISIN " + ftdvo.getIsin() + " tiene posici\u00F3n no disponible.");
                    		}
                    	}
                    	ftdvo.setPosicion(posicion);
                    }

                    //DATOS ADICIONALES
                    ftdvo.setCuentaBeneficiarioFinal(operacionFT.getNumCuentaBeneficiario());
                    ftdvo.setDescripcionCuentaContraparte(operacionFT.getDescCuentaContraparte());
                    ftdvo.setDescripcionBeneficiarioFinal(operacionFT.getNomCuentaBeneficiario());
                    ftdvo.setInstruccionesEspeciales(null);
                }
                else {
                	ftdvo.addError("La emisi\u00F3n es invalida.");
                }
            }

            //Si la operacion tuvo error se suma el contador de operaciones con error.
            if (ftdvo.getError() != null) {
                operacionesError++;
            }

            //Se enlista el detalle de la operacion de file transfer.
            listadoDetalleNuevo.add(ftdvo);
        }

        listaEntregas = null;

        //Valida emisiones repetidas
        validarEmisionesRepetidas(listadoDetalleNuevo);
        
        //Valida que entrega y recepcion no tenga errores.
        mapaER = this.separarEntregasYRecepciones(listadoDetalleNuevo);
        listaEntregas = mapaER.get(TIPO_MOVIMIENTO_ENTREGA);
        List<FileTransferDetalleVO> recepciones = mapaER.get(TIPO_MOVIMIENTO_RECEPCION);
        for (FileTransferDetalleVO recepcion : recepciones) {
            for (FileTransferDetalleVO entrega : listaEntregas) {
                if (StringUtils.isNotBlank(entrega.getIsin()) && StringUtils.isNotBlank(recepcion.getIsin())) {
                    if (recepcion.getIsin().trim().equals(entrega.getIsin().trim())) {
                    	
                    	//Asigna la posicion a la recepcion
                    	recepcion.setPosicion(entrega.getPosicion());
                    	
                    	//Valida que el custodio no sea el mismo
                    	if(StringUtils.equals(entrega.getCustodioDestino(), recepcion.getCustodioDestino())) {
                    		String error = "La operaci\u00F3n de entrega/recepci\u00F3n para la emisi\u00F3n " + entrega.getIsin() + " tiene el mismo custodio.";
                    		entrega.addError(error);
                    		recepcion.addError(error);
                    	}
                    	//Valida que ambas operaciones sean correctas
                    	if(StringUtils.isNotBlank(entrega.getError()) || StringUtils.isNotBlank(recepcion.getError())) {
                    		String error = "La operaci\u00F3n de entrega/recepci\u00F3n para la emisi\u00F3n " + entrega.getIsin() + " presenta errores. Verifique.";
                    		entrega.addError(error);
                    		recepcion.addError(error);
                    	}
                    }
                }
            }
		}
        
        mapaER = null;
        listaEntregas = null;
        recepciones = null;
        
        //Generando el XML del archivo con errores de validaciones.
        try {
            this.setXmlConValidaciones(fileTransfer, listadoDetalleNuevo);
        }
        catch (Exception e) {
            throw new BusinessException("No se pudo generar el XML de las operaciones validadas!");
        }

        //Guardar FileTransfer a BD.
        fileTransfer.setFechaRegistro(Calendar.getInstance().getTime());
        fileTransfer.setFechaModifico(Calendar.getInstance().getTime());
        fileTransfer.setEstado(Constantes.ESTATUS_CREADO);
        fileTransfer.setValidadosExito(totalOperaciones - operacionesError);
        fileTransfer.setValidadosError(operacionesError);
        Integer idFT = this.salvarFileTransferCambioBoveda(fileTransfer);
        fileTransfer.setIdFileTransfer(idFT);
        fileTransfer.setOperaciones(listadoDetalleNuevo);

        return fileTransfer;
    }
    
    
    /**
     * Valida que no existan operaciones con emisiones repetidas.
     * @param operacionesFileTransfer Operaciones a validar.
     */
    private void validarEmisionesRepetidas(List<FileTransferDetalleVO> operacionesFileTransfer) {
    	Map<String, List<FileTransferDetalleVO>> mapaER = this.separarEntregasYRecepciones(operacionesFileTransfer);    	
        List<FileTransferDetalleVO> entregas = mapaER.get(TIPO_MOVIMIENTO_ENTREGA);
        List<FileTransferDetalleVO> recepciones = mapaER.get(TIPO_MOVIMIENTO_RECEPCION);
        List<String> operacionesValidar = new ArrayList<String>();
    	for (FileTransferDetalleVO fileTransferDetalleVO : entregas) {
    		if(operacionesValidar.contains(fileTransferDetalleVO.getIsin())) {
    			String error = "La operaci\u00F3n de entrega para la emisi\u00F3n " + fileTransferDetalleVO.getIsin() + " esta duplicada.";
    			fileTransferDetalleVO.addError(error);
    		}
    		else {
    			operacionesValidar.add(fileTransferDetalleVO.getIsin());
    		}
		}
        operacionesValidar = new ArrayList<String>();
    	for (FileTransferDetalleVO fileTransferDetalleVO : recepciones) {
    		if(operacionesValidar.contains(fileTransferDetalleVO.getIsin())) {
    			String error = "La operaci\u00F3n de recepci\u00F3n para la emisi\u00F3n " + fileTransferDetalleVO.getIsin() + " esta duplicada.";
    			fileTransferDetalleVO.addError(error);
    		}
    		else {
    			operacionesValidar.add(fileTransferDetalleVO.getIsin());
    		}
		}
    	mapaER = null;
    	entregas = null;
    	recepciones = null;
    }
    
    

    /**
     * Valida si la operacion de Recepcion:'recepcion' encuentra su operacion par de Entrega.
     * @param entregas Listado de entregas
     * @param recepcion La Recepcion a buscar su par de Entrega
     * @return
     */
    private boolean existeEntregaEnFT(List<FileTransferDetalleVO> entregas, FileTransferDetalleVO recepcion) {
        boolean existe = false;
        for (FileTransferDetalleVO entrega : entregas) {
            if (StringUtils.isNotBlank(entrega.getIsin())) {
                if (recepcion.getIsin().trim().equals(entrega.getIsin().trim())) {
                    existe = true;
                    break;
                }
            }
        }

        return existe;
    }

    /**
     * Se realiza la validacion de que la operacion recibida como parametro no se haya capturado previamente.
     * @param opNow La operacion a validar
     * @throws BusinessException
     */
    private void validarNoDuplicidadOperacion(FileTransferDetalleVO opNow) throws BusinessException {
    	String tipoMensaje = TIPO_MOVIMIENTO_ENTREGA.equals(opNow.getTipoMovimiento()) ? Constantes.MT_542 : Constantes.MT_540;
    	List<OperacionSic> operaciones = 
        	operacionSicDao.findOperacionSicCambioBovedaEnTransito(opNow.getIdEmision().longValue(), tipoMensaje);
        if (operaciones != null && !operaciones.isEmpty()) {
			String msgErrorCambioBoveda = 
				"Existe una operacion de cambio de boveda en transito para la emision "	+ opNow.getIsin();
            throw new BusinessException(msgErrorCambioBoveda);
        }
    }

    /**
     * Guarda en BD un registro de File Transfer de Cambio de Boveda.
     * @param ft
     * @return
     */
    private Integer salvarFileTransferCambioBoveda(FileTransferCambioBoveda ft) {
        return this.fileTransferCambioBovedaDao.save(ft);
    }

    /**
     * Obtiene el saldo total de todas las cuentas de la emision.
     * @param idEmision El id de la emision.
     * @param idBoveda El id de la boveda.
     * @param idCuponVigente El id del cupon vigente.
     * @return El saldo total.
     * @throws BusinessException
     */
    private BigDecimal getSaldoTotal(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        BigDecimal saldo = null;
        try {
            saldo = this.emisionesConsultasService.getSaldoTotal(idEmision, idBoveda, idCuponVigente);
        } catch (Exception e) {
            throw new BusinessException("No se pudo consultar la Posici\u00F3n para la Emisi\u00F3n!");
        }
        return saldo;
    }

    /**
     * Obtiene la posicion NO disponible de las cuentas de una emision.
     * @param idEmision El id de la emision.
     * @param idBoveda El id de la boveda.
     * @param idCuponVigente El id del cupon vigente.
     * @return El saldo de la posicion no disponible.
     * @throws BusinessException
     */
    private BigDecimal getSaldoPosicionNoDisponible(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        BigDecimal saldo = null;
        try {
            saldo = this.emisionesConsultasService.getSaldoPosicionNoDisponible(idEmision, idBoveda, idCuponVigente);
        } catch (Exception e) {
            throw new BusinessException("No se pudo consultar la Posici\u00F3n NO Disponible para la Emisi\u00F3n!");
        }
        return saldo;
    }
//
//    /**
//     * Obtiene el saldo de la emision.
//     * @param idEmision El id de la emision.
//     * @param idCuenta El id de la cuenta.
//     * @param idBoveda El id de la boveda.
//     * @param idCuponVigente El id del cupon vigente.
//     * @return El saldo.
//     * @throws BusinessException
//     */
//    private BigDecimal getSaldo(Integer idEmision, Integer idCuenta, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
//        BigDecimal saldo = null;
//        try {
//            saldo = this.emisionesConsultasService.getSaldo(idEmision, idCuenta, idBoveda, idCuponVigente);
//        } catch (Exception e) {
//            throw new BusinessException("No se pudo consultar la posici\u00F3n para la Emisi\u00F3n!");
//        }
//        return saldo;
//    }

	/**
	 * Método que valida que una cadena cumpla con longitud y una expresión regular.
	 * @param cadena Cadena a validar
	 * @param longitud Longitud a validar
	 * @param expresionRegular Expresion regular que debe cumplir
	 * @return true si cumple todo; false en caso de fallar
	 */
	private boolean validarExpresionRegular(String cadena, int longitud, String expresionRegular) {
		return cadena.length() <= longitud && cadena.matches(expresionRegular);
	}
    
    /**
     * Asigna los datos del objeto OperacionFileTransferDto al objeto FileTransferDetalleVO, con unas leves diferencias 
     * de que en unas propuedades sen vez de una id o codigo se asigna una cadena mas descriptiva.
     * @param ftdvo El objeto FileTransferDetalleVO a setearse.
     * @param oftdto El objeto FileTransferDetalleVO original.
     */
    private void setDatosFileTransferDetalleVO(FileTransferDetalleVO ftdvo, FileTransferDetalleVO oftdto) {
        ftdvo.setIdFolio(oftdto.getIdFolio());
        ftdvo.setTipoValor(oftdto.getTipoValor());
        ftdvo.setEmisora(oftdto.getEmisora());
        ftdvo.setSerie(oftdto.getSerie());
        ftdvo.setIsin(oftdto.getIsin());
        ftdvo.setFechaLiquidacion(oftdto.getFechaLiquidacion());
        ftdvo.setFechaLiquidacionDate(this.getDateObject(oftdto.getFechaLiquidacion()));
        ftdvo.setFechaOperacion(oftdto.getFechaOperacion());
        ftdvo.setFechaOperacionDate(this.getDateObject(oftdto.getFechaOperacion()));
        ftdvo.setCustodioDestino(oftdto.getCustodioDestino());
        ftdvo.setCuentaIndeval(oftdto.getCuentaIndeval());
        ftdvo.setTipoMovimiento(oftdto.getTipoMovimiento());
        ftdvo.setCuentaContraparte(oftdto.getCuentaContraparte());
        ftdvo.setDescCuentaContraparte(oftdto.getDescCuentaContraparte());
        ftdvo.setDepositanteLiquidador(oftdto.getDepositanteLiquidador());
        ftdvo.setNomCuentaBeneficiario(oftdto.getNomCuentaBeneficiario());
        ftdvo.setNumCuentaBeneficiario(oftdto.getNumCuentaBeneficiario());
        ftdvo.setError(oftdto.getError());
        ftdvo.setUsuario(oftdto.getUsuario());
    }

    /**
     * Obtiene un objeto Date a partir de una cadena.
     * @param strFecha La fecha a convertir.
     * @return La fecha.
     * @throws BusinessException
     */
    private Date getDateObject(String strFecha) throws BusinessException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        
        if(strFecha != null) {        	
        	try {
        		date = formatter.parse(strFecha);
        	}
        	catch (ParseException e) {
        		date = null;
        	}
        }
        return date;
    }

    /**
     * Generacion de XML de las operaciones con validaciones.
     * @param fileTransfer El objeto a asignar el XML generado.
     * @param listadoDetalle El objeto a generar su XML.
     * @throws Exception
     */
    private void setXmlConValidaciones(FileTransferCambioBoveda fileTransfer, List<FileTransferDetalleVO> listadoDetalle) throws Exception {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.setXmlConValidaciones()...");
        fileTransfer.setXmlError(this.parserAdaptadorService.doConvertToXML(listadoDetalle));
    }

    /**
     * Generacion del XML del archivo original del file transfer.
     * @param fileTransfer El objeto a asignar el XML generado.
     * @param operaciones El objeto a generar su XML.
     * @throws Exception
     */
    private void setXmlOriginal(FileTransferCambioBoveda fileTransfer, List<FileTransferDetalleVO> operaciones) throws Exception {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.setXmlOriginal()...");
        fileTransfer.setXmlOriginal(this.parserAdaptadorService.doConvertToXML(operaciones));
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCambioBovedaService#procesarOperacionesFileTransfer(FileTransferCambioBoveda)
     */
    public void procesarOperacionesFileTransfer(FileTransferCambioBoveda fileTransfer) throws BusinessException {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.procesarOperacionesFileTransfer()...");
        //Obtener operaciones sin error
        List<FileTransferDetalleVO> opsSinError = this.obtenerOperacionesSinError(fileTransfer.getOperaciones());

        // Filtrar Entregas y Recepciones
        Map<String, List<FileTransferDetalleVO>> mapaER = this.separarEntregasYRecepciones(opsSinError); 

        //Procesar Entregas
        List<ResultadoFTVO> operacionesEntrega = this.procesarOperacionesCambioBoveda(mapaER.get(TIPO_MOVIMIENTO_ENTREGA));

        //Procesar Recepciones
        List<ResultadoFTVO> operacionesRecepcion = this.procesarOperacionesCambioBoveda(mapaER.get(TIPO_MOVIMIENTO_RECEPCION));

        //Convertir a XML las operaciones procesadas.
        try {
            this.setXmlOperacionesProcesadas(fileTransfer, operacionesEntrega, operacionesRecepcion);
        } catch (Exception e) {
            throw new BusinessException("No se pudo generar el XML de los ids de operaciones liberadas!");
        }

        //Actualizar FileTransfer
        fileTransfer.setFechaModifico(Calendar.getInstance().getTime());
        fileTransfer.setEstado(Constantes.ESTATUS_PROCESADO);
        fileTransfer.setProcesadosExito(operacionesEntrega.size() + operacionesRecepcion.size());
        fileTransfer.setProcesadosError(opsSinError.size() - (operacionesEntrega.size() + operacionesRecepcion.size()));
        this.fileTransferCambioBovedaDao.update(fileTransfer);
    }

    /**
     * Generacion de XML de las operaciones procesadas, tanto Entregas como Recepciones.
     * @param fileTransfer El objeto a asignar el XML generado.
     * @param entregas Las operaciones de Entregas.
     * @param recepciones Las operaciones de Recepciones.
     * @throws Exception
     */
    private void setXmlOperacionesProcesadas(FileTransferCambioBoveda fileTransfer, List<ResultadoFTVO> entregas, List<ResultadoFTVO> recepciones) throws Exception {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.setXmlOperacionesProcesadas()...");
        List<ResultadoFTVO> operacionesTodas = new ArrayList<ResultadoFTVO>();
        if (entregas != null && !entregas.isEmpty()) {
            for (ResultadoFTVO entrega : entregas) {
                operacionesTodas.add(entrega);
            }
        }
        if (recepciones != null && !recepciones.isEmpty()) {
            for (ResultadoFTVO recepcion : recepciones) {
                operacionesTodas.add(recepcion);
            }
        }
        fileTransfer.setXmlProcesadas(this.parserAdaptadorService.doConvertToXML(operacionesTodas));
    }

    /**
     * Procesamiento de Entregas y Recepciones.
     * @param operaciones El listado de operaciones.
     * @return Un listado de objetos ResultadoFTVO.
     */
    @SuppressWarnings("unchecked")
    private List<ResultadoFTVO> procesarOperacionesCambioBoveda(List<FileTransferDetalleVO> operacionesCB) {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.procesarOperacionesCambioBoveda()...");
        final String DIVISA_DEFAULT = "USD";
        final String USR_CREDENCIAL = "usrCredencial";
        List<ResultadoFTVO> operacionesProcesadas = new ArrayList<ResultadoFTVO>();
        ResultadoFTVO bitacoraOperacion = null;
        for (FileTransferDetalleVO operacionCB : operacionesCB) {
            LOG.info("####### Operacion=[" + operacionCB.toString() + "]");
            try {
                Date fechaActual = dateUtilService.getCurrentDate();

                CuentaNombrada ctaN = this.cuentaNombradaDao.findCuenta(this.crearAgenteVO(operacionCB.getIdFolio(), 
                                                                                           operacionCB.getCuenta()));
                //Consulta la emision 
                Emisiones emision = validadorEmision.validarEmisionLiberada(operacionCB.getIsin());
                
                Integer marcaCompra = Integer.valueOf(0);
                if (operacionCB.getTipoMovimiento().equals(TIPO_MOVIMIENTO_RECEPCION)) {
                    marcaCompra = Integer.valueOf(1);
                }

                // SE ARMA EL OBJETO BitacoraOperaciones
                BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
                bitacoraOperaciones.setCargoParticipante(Integer.valueOf(0));
                bitacoraOperaciones.setTasaFija(Integer.valueOf(0));
                bitacoraOperaciones.setMarcaCompra(marcaCompra);
                marcaCompra = null;
                bitacoraOperaciones.setIdTrasp(ctaN.getInstitucion().getTipoInstitucion() != null ? ctaN.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() : null);
                bitacoraOperaciones.setIdRecep(ctaN.getInstitucion().getTipoInstitucion() != null ? ctaN.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() : null);
                bitacoraOperaciones.setFolioTrasp(ctaN.getInstitucion().getFolioInstitucion());
                bitacoraOperaciones.setFolioRecep(ctaN.getInstitucion().getFolioInstitucion());
                bitacoraOperaciones.setCuentaRecep(ctaN.getCuenta());
                bitacoraOperaciones.setCuentaTrasp(ctaN.getCuenta());
                ctaN = null;
                //Setea TV, Emisora y Serie
                bitacoraOperaciones.setTv(emision.getInstrumento().getClaveTipoValor());
                bitacoraOperaciones.setEmisora(emision.getEmisora().getClavePizarra());
                bitacoraOperaciones.setSerie(emision.getSerie());
                emision = null;
                
                bitacoraOperaciones.setCupon(operacionCB.getClaveCuponVigente());
                bitacoraOperaciones.setDivisa(null);
                bitacoraOperaciones.setBoveda(operacionCB.getBoveda());
                bitacoraOperaciones.setBovedaEfectivo(operacionCB.getBovedaEfectivo());
                bitacoraOperaciones.setOperacionFirmada(null);
                bitacoraOperaciones.setFolioControl(this.utilService.getFolio(seqFolioControl));
                bitacoraOperaciones.setReferenciaMensaje(this.utilService.getFolio(SEQ_REFERENCIA_MENSAJE).toString().trim());
                bitacoraOperaciones.setReferenciaOperacion(null);
                if (bitacoraOperaciones.getFolioControl() != null) {
                    bitacoraOperaciones.setReferenciaOperacion(bitacoraOperaciones.getFolioControl().toString());
                }
                bitacoraOperaciones.setEstatusRegistro(Constantes.ESTATUS_NO_ENVIADA);
                bitacoraOperaciones.setFechaConcertacion(dateUtilService.getFechaHoraCero(fechaActual));
                bitacoraOperaciones.setFechaLiquidacion(dateUtilService.getFechaHoraCero(operacionCB.getFechaLiquidacionDate()));
                bitacoraOperaciones.setFechaVencimiento(dateUtilService.getFechaHoraCero(operacionCB.getFechaOperacionDate()));
                bitacoraOperaciones.setFechaHoraAlta(fechaActual);
                fechaActual = null;
                bitacoraOperaciones.setOrigenRegistro(Constantes.ID_ORIGEN_DIV_INTERNACIONAL);
                bitacoraOperaciones.setMonto(null);
                bitacoraOperaciones.setTipoInstruccion(Constantes.TIPO_OPER_TI);

                // Datos Adicionales
                @SuppressWarnings("rawtypes")
                HashMap datosAdicionales = new HashMap();
                datosAdicionales.put("lugarDeLiquidacion", operacionCB.getLugarDeLiquidacion());
                datosAdicionales.put("cuentaBeneficiarioFinal", operacionCB.getCuentaBeneficiarioFinal());            
                datosAdicionales.put("descripcionCuentaContraparte", operacionCB.getDescripcionCuentaContraparte());          
                datosAdicionales.put("descripcionBeneficiarioFinal", operacionCB.getDescripcionBeneficiarioFinal());
                if (StringUtils.isNotBlank(operacionCB.getInstruccionesEspeciales())) {
                    datosAdicionales.put("instruccionesEspeciales", operacionCB.getInstruccionesEspeciales());
                }
                datosAdicionales.put("custodio", operacionCB.getCustodio());          
                datosAdicionales.put("ISIN", operacionCB.getIsin());

                datosAdicionales.put(Constantes.CUENTA_CONTRAPARTE_DA, operacionCB.getCuentaContraparte());
                datosAdicionales.put(Constantes.DESC_CTA_CONTRAPARTE_DA, operacionCB.getDescripcionCuentaContraparte());
                datosAdicionales.put(Constantes.FECHA_OP_DA, bitacoraOperaciones.getFechaVencimiento());
                datosAdicionales.put(Constantes.FECHA_LIQ_DA, bitacoraOperaciones.getFechaLiquidacion());
                datosAdicionales.put(Constantes.FECHA_NOT_DA, bitacoraOperaciones.getFechaConcertacion());
                datosAdicionales.put(Constantes.NUM_CUNETA_BENEF_DA, operacionCB.getNumCuentaBeneficiario());
                datosAdicionales.put(Constantes.NOM_CUENTA_BENEF_DA, operacionCB.getNomCuentaBeneficiario());
                datosAdicionales.put(Constantes.DEPOSITANTE_DA, operacionCB.getIdSicDetalle());
                datosAdicionales.put(Constantes.INSTRUCCIONES_ESP_DA, "");
                datosAdicionales.put(Constantes.TIPO_MENSAJE_DA, Constantes.MT_542);
                if (operacionCB.getTipoMovimiento().equals(TIPO_MOVIMIENTO_RECEPCION)) {
                    datosAdicionales.put(Constantes.TIPO_MENSAJE_DA, Constantes.MT_540);
                }
                datosAdicionales.put(Constantes.ESTATUS_DA, Constantes.ST_OPER_RETENIDA);
                if (operacionCB.getTipoMovimiento().equals(TIPO_MOVIMIENTO_RECEPCION)) {
                    datosAdicionales.put(Constantes.ESTATUS_DA, Constantes.ST_OPER_NOTIFICADA);
                }
                datosAdicionales.put(Constantes.PRECIO_DA, new BigDecimal(0));
                datosAdicionales.put(Constantes.DIVISA_DA, DIVISA_DEFAULT);
                datosAdicionales.put(USR_CREDENCIAL, operacionCB.getUsrCredencial());

                //Set de datos adicionales
                bitacoraOperaciones.setDatosAdicionales(this.utilService.mapaToXml(datosAdicionales));

                //Guardando la bitacora de operacion
                Integer idBitacoraOperaciones = this.salvarCBEnBitacoraOperaciones(bitacoraOperaciones);
                bitacoraOperaciones.setIdBitacoraOperaciones(idBitacoraOperaciones.longValue());
                if (operacionCB.getPosicion() != null) {
                    bitacoraOperaciones.setCantidadTitulos(operacionCB.getPosicion().toBigInteger());
                }

                //Se genera un registro de la entidad RSICCambioBovedaDao de la bitacora registrada lineas arriba.
                RSICCambioBoveda reg = new RSICCambioBoveda(bitacoraOperaciones.getReferenciaOperacion());
                this.rSicCambioBovedaService.insertar(reg);

                bitacoraOperacion = new ResultadoFTVO();
                bitacoraOperacion.setIdOperacion(idBitacoraOperaciones);
                bitacoraOperacion.setTipoMovimiento(TIPO_MOVIMIENTO_ENTREGA);
                if (operacionCB.getTipoMovimiento().equals(TIPO_MOVIMIENTO_RECEPCION)) {
                    bitacoraOperacion.setTipoMovimiento(TIPO_MOVIMIENTO_RECEPCION);
                }
                bitacoraOperacion.setBitacoraOperaciones(bitacoraOperaciones);
                idBitacoraOperaciones = null;
                bitacoraOperaciones = null;
                operacionesProcesadas.add(bitacoraOperacion);
                bitacoraOperacion = null;
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(e.getMessage());
            }
        }

        return operacionesProcesadas;
    }

    private Integer salvarCBEnBitacoraOperaciones(BitacoraOperaciones bitacoraOperaciones) {
        return this.fileTransferCambioBovedaDao.salvarBitacoraOperaciones(bitacoraOperaciones);
    }

    /**
     * Crea un objeto AgenteVO a partir de el id-folio y el numero de cuenta
     * 
     * @param idFolio Id y Folio del agente
     * @param cuenta N&acute;mero de cuenta
     * @return AgenteVO creado
     */
    private AgenteVO crearAgenteVO(String idFolio, String cuenta) {
        AgenteVO agente = new AgenteVO();
        agente.setId(StringUtils.substring(idFolio, 0, 2));
        agente.setFolio(StringUtils.substring(idFolio, 2, 5));
        agente.setCuenta(cuenta);
        return agente;
    }

    /**
     * Separa en dos listas las entregas de las recepciones.
     * @param operaciones El listado de operaciones.
     * @return Un mapa con dos listas de operaciones.
     */
    private Map<String, List<FileTransferDetalleVO>> separarEntregasYRecepciones(List<FileTransferDetalleVO> operaciones) {
        List<FileTransferDetalleVO> entregas = new ArrayList<FileTransferDetalleVO>();
        List<FileTransferDetalleVO> recepciones = new ArrayList<FileTransferDetalleVO>();
        Map<String, List<FileTransferDetalleVO>> mapaER = new HashMap<String, List<FileTransferDetalleVO>>();
        for (FileTransferDetalleVO ftdvo : operaciones) {
            if (TIPO_MOVIMIENTO_ENTREGA.equals(ftdvo.getTipoMovimiento())) {
                entregas.add(ftdvo);
            }
            else if(TIPO_MOVIMIENTO_RECEPCION.equals(ftdvo.getTipoMovimiento())) {
                recepciones.add(ftdvo);
            }
            else {
            	continue;
            }
        }
        mapaER.put(TIPO_MOVIMIENTO_ENTREGA, entregas);
        mapaER.put(TIPO_MOVIMIENTO_RECEPCION, recepciones);

        return mapaER;
    }
    
    /**
     * Obtiene el subconjunto de todas las operaciones que no tengan error.  
     * @param operaciones Todas las operaciones del File Transfer.
     * @return El subconjunto sin errores.
     */
    private List<FileTransferDetalleVO> obtenerOperacionesSinError(List<FileTransferDetalleVO> operaciones) {
        List<FileTransferDetalleVO> opsSinError = new ArrayList<FileTransferDetalleVO>();
        for (FileTransferDetalleVO ftdvo : operaciones) {
            if (ftdvo.getError() == null) {
                opsSinError.add(ftdvo);
            }
        }

        return opsSinError;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCambioBovedaService#consultaFileTransferActivo(String)
     */
    @SuppressWarnings("unchecked")
    public FileTransferCambioBoveda consultaFileTransferActivo(String idFolio) throws BusinessException {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.consultaFileTransferActivo()...");
        FileTransferCambioBoveda ft = this.fileTransferCambioBovedaDao.getByIdFolioYEstadoCreado(idFolio);
        if (ft != null) {
             Object lista = this.parserAdaptadorService.doConvertFromXML(ft.getXmlError());
             if (lista != null) {
                 ft.setOperaciones((List<FileTransferDetalleVO>) lista);
             }
        }
        return ft;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCambioBovedaService#cancelarFileTransfer(FileTransferCambioBoveda)
     */
    public void cancelarFileTransfer(FileTransferCambioBoveda fileTransfer) throws BusinessException {
        LOG.info("####### Entrando a FileTransferCambioBovedaServiceImpl.cancelarFileTransfer()...");
        try {
            fileTransfer.setEstado(Constantes.ESTATUS_CANCELADO);
            this.fileTransferCambioBovedaDao.update(fileTransfer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error al cancelar el File Transfer:" + e.getMessage());
        }
    }

    public void setFileTransferCambioBovedaDao(FileTransferCambioBovedaDao fileTransferCambioBovedaDao) {
        this.fileTransferCambioBovedaDao = fileTransferCambioBovedaDao;
    }

    public void setParserAdaptadorService(ParserAdaptadorService parserAdaptadorService) {
        this.parserAdaptadorService = parserAdaptadorService;
    }

    public void setValidadorInstitucion(ValidadorInstitucion validadorInstitucion) {
        this.validadorInstitucion = validadorInstitucion;
    }

    public void setValidadorEmision(ValidadorEmision validadorEmision) {
        this.validadorEmision = validadorEmision;
    }

    public void setEmisionesConsultasService(EmisionesConsultasService emisionesConsultasService) {
        this.emisionesConsultasService = emisionesConsultasService;
    }

    public void setSeqFolioControl(String seqFolioControl) {
        this.seqFolioControl = seqFolioControl;
    }

    public void setUtilService(UtilService utilService) {
        this.utilService = utilService;
    }

    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
        this.cuentaNombradaDao = cuentaNombradaDao;
    }

    public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
        this.sicEmisionDao = sicEmisionDao;
    }

    public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
        this.sicDetalleDao = sicDetalleDao;
    }

    public void setrSicCambioBovedaService(RSICCambioBovedaService rSicCambioBovedaService) {
        this.rSicCambioBovedaService = rSicCambioBovedaService;
    }

    public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
        this.operacionSicDao = operacionSicDao;
    }

    public void setBovedaService(BovedaService bovedaService) {
        this.bovedaService = bovedaService;
    }

	/**
	 * Método para establecer el atributo catBicDao
	 * @param catBicDao El valor del atributo catBicDao a establecer.
	 */
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}

	/**
	 * Método para establecer el atributo messageResolver
	 * @param messageResolver El valor del atributo messageResolver a establecer.
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

}
