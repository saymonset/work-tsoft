/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleParams;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams;
import com.indeval.portaldali.middleware.services.tesoreria.util.Constantes;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DepositanteValidoBanxicoDao;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.SaldoNombradaDaliDao;
import com.indeval.portaldali.persistence.model.SaldoNombrada;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosService;

/**
 * Implementacion de los servicios de Tesoreria.
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TesoreriaServiceImpl implements TesoreriaService, Constantes {
    
    /** Log de clase */
    private static final Logger logger = LoggerFactory.getLogger(TesoreriaServiceImpl.class);

    private DepositanteValidoBanxicoDao depositanteValidoBanxicoDao;

    /** Bean de acceso a datos de ProductoDineroDao */

//    /** Bean de acceso a datos de TraspasosEfectivoDao */
//    private TraspasosEfectivoDao traspasosEfectivoJDBCDao;
//
//    /** Bean de acceso a datos de SaldosEfectivoDao */
//    private SaldosEfectivoDao saldosEfectivoDao;
//    
    /** Bean de acceso a datos de TraspasosDao */
    private SaldoNombradaDaliDao saldoNombradaDaliDao; 
//
//    /** Bean de acceso a datos de EmpresaCatalogoDao */
//    private EmpresaCatalogoDao empresaCatalogoDao;
//
//    /** Bean de acceso a datos de TraspasosDao */
//    private TraspasosDao traspasosDao;
//
//    /** Bean de acceso a datos de StoredProceduresDao */
//    private StoredProceduresDao storedProceduresDao;

    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;

    /** Bean de acceso a datos de ProductoDineroDao */
//    private ProductoDineroDao productoDineroDao;
//
//    /** Bean de acceso a datos de ParcialidadesDao */
//    private ParcialidadesDao parcialidadesDao;
//
//    /** Bean de acceso a datos de AplicacionDao */
//    private AplicacionDao aplicacionDao;
//
//    /** Bean de acceso a datos de AreasTrabajoDao */
//    private AreasTrabajoDao areasTrabajoDao;
    
    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;
    
    /** DAO para la consulta del catálogo de instituciones del DALI */
    private InstitucionDaliDAO institucionDaliDAO = null;
    
	/** Bean de acceso al UtilsDao */
//    private DecretosFijaDao decretosFijaDao;

    /** Bean de acceso a UtilService. */
    private UtilServices utilService;

    /** Bean de acceso a datos de ContejpoDao */
//    private ContejpoDao contejpoDao;

    /** Bean de acceso al Map de fases abiertas */
    private Map fasesAbiertas;

    /** Bean de acceso al dao emisionesDao */
//    private EmisionesDao emisionesDao;

    /** Bean de acceso al ejb de ConsultaDecretosEJB */
    private LiquidacionDecretosService decretosEjercicioDerechos;

//    /** Bean de acceso a decretosVariableDao */
//    private DecretosVariableDao decretosVariableDao;
//
//    /** Bean de acceso a procesoDao */
//    private ProcesoDao procesoDao;
 
    /**
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getCuentaClabeEfectivoPorCuentaNombrada(com.indeval.portaldali.persistence.vo.AgentePK)
	 */
    public String getCuentaClabeEfectivoPorCuentaNombrada(AgenteVO agentePK) {
    	
    	String cuenta = null;
    	InstitucionDTO institucion = institucionDaliDAO.buscarInstitucionPorClaveYFolio(agentePK.getClave());
    	if(institucion != null) {
    		cuenta = institucion.getCuentaClabe();
    	}
    	return cuenta;
    }
       
    
    /**
	 * Obtiene el valor del atributo institucionDaliDAO
	 *
	 * @return el valor del atributo institucionDaliDAO
	 */
	public InstitucionDaliDAO getInstitucionDaliDAO() {
		return institucionDaliDAO;
	}


	/**
	 * Establece el valor del atributo institucionDaliDAO
	 *
	 * @param institucionDaliDAO el valor del atributo institucionDaliDAO a establecer
	 */
	public void setInstitucionDaliDAO(InstitucionDaliDAO institucionDaliDAO) {
		this.institucionDaliDAO = institucionDaliDAO;
	}


	/**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getEstadoCuentaLiquidacion(com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams)
     */
    public CuentaLiquidacionVO[] getEstadoCuentaLiquidacion(GetEdoCtaLiqParams params) 
    			throws BusinessException {
        
        logger.info("Entrando a TesoreriaServiceImpl.getEstadoCuentaLiquidacion()");
        
//        /* Valida parametros */
//        utilService.validaDTONoNulo(params, "");
//        utilService.validarClaveTipo(params.getTipoConsulta(), " tipo consulta ");
//        utilService.validaDTONoNulo(params.getFechaIni(), " fecha inicial ");
//        params.setFechaIni(dateUtilsDao.getFechaHoraCero(params.getFechaIni()));
//        utilService.validaDTONoNulo(params.getFechaFin(), " fecha final ");
//        params.setFechaFin(dateUtilsDao.getFechaHoraFinDia(params.getFechaFin()));
//        
//        if (!params.getTipoConsulta().equalsIgnoreCase(PAGOS)
//                && !params.getTipoConsulta().equalsIgnoreCase(COBROS)) {
//            throw new BusinessException("El tipo de consulta es incorrecto");
//        }
//        
//        if (params.getTipoMoneda() == null) {
//            if (params.getTipoConsulta().equalsIgnoreCase(PAGOS)) {
//                params.setTipoMoneda(PESOS);
//            }
//            else {
//                throw new BusinessException("Falta el tipo de moneda");
//            }
//        }
//
//        /* Copia el params dejando el tipo de ejercicio null para obtener los totales por intitucion */
//        GetEdoCtaLiqParams paramsTotales = new GetEdoCtaLiqParams();
//        paramsTotales.setAgente(params.getAgente());
//        paramsTotales.setEmision(params.getEmision());
//        paramsTotales.setFechaFin(params.getFechaFin());
//        paramsTotales.setFechaIni(params.getFechaIni());
//        paramsTotales.setTipoConsulta(params.getTipoConsulta());
//        paramsTotales.setTipoMoneda(params.getTipoMoneda());
//
//        List<LiquidacionDecretosVO> listaLiqDecretos = new ArrayList<LiquidacionDecretosVO>();
//        List<LiquidacionDecretosVO> listaLiqDecretosTotales = new ArrayList<LiquidacionDecretosVO>();
//
//        /* Obtiene informacion EJERDER */
//        if (StringUtils.isBlank(params.getTipoEjercicio()) ||
//                params.getTipoEjercicio().equalsIgnoreCase(TODOS)) {
//            params.setTipoEjercicio(null);
//        }
//        if (params.getTipoMoneda().equalsIgnoreCase(PESOS)) {
//            params.setTipoMoneda(MXN);
//            paramsTotales.setTipoMoneda(MXN);
//        }
//        else if (params.getTipoMoneda().equalsIgnoreCase(DOLARES)) {
//            params.setTipoMoneda(USD);
//            paramsTotales.setTipoMoneda(USD);
//        }
//        else if (params.getTipoMoneda().equalsIgnoreCase(UDIS)) {
//            params.setTipoMoneda(MXV);
//            paramsTotales.setTipoMoneda(MXV);
//        }
//        List listaLiqDecretosEjerDerechos = decretosEjercicioDerechos.getLiquidacionDecretos(params);
//        /* Obtiene informacion para totales por institucion EJERDER */
//        List listaLiqDecretosEjerDerechosTotales = 
//        	decretosEjercicioDerechos.getLiquidacionDecretos(paramsTotales);
//
//        /* Concatena las listas */
//        if (listaLiqDecretosEjerDerechos != null && !listaLiqDecretosEjerDerechos.isEmpty()) {
//            listaLiqDecretos.addAll(listaLiqDecretosEjerDerechos);
//        }
//        if (listaLiqDecretos == null || listaLiqDecretos.isEmpty()) {
//            throw new BusinessException(
//                    "No existe informacion para los criterios de busqueda seleccionados");
//        }
//
//        /* Concatena las listas para totales por institucion */
//        if (listaLiqDecretosEjerDerechosTotales != null && 
//        		!listaLiqDecretosEjerDerechosTotales.isEmpty()) {
//            listaLiqDecretosTotales.addAll(listaLiqDecretosEjerDerechosTotales);
//        }
//        if (listaLiqDecretosTotales == null || listaLiqDecretosTotales.isEmpty()) {
//            throw new BusinessException(
//                    "Ocurrio un error al calcular los totales");
//        }
//
//        /* Ordena el resultado */
//        Map<String, LiquidacionDecretosVO> mapaLiqDecretosOrdenado = 
//        	sortLiqDecretos(listaLiqDecretos);
//        if (mapaLiqDecretosOrdenado == null || mapaLiqDecretosOrdenado.isEmpty()) {
//            throw new BusinessException("Error al realizar el ordenamiento de la informacion");
//        }
//        listaLiqDecretos.clear();
//        listaLiqDecretos.addAll(mapaLiqDecretosOrdenado.values());
//
//        /* Ordena los totales */
//        Map mapaLiqDecretosTotalesOrdenado = sortLiqDecretos(listaLiqDecretosTotales);
//        if (mapaLiqDecretosTotalesOrdenado == null || mapaLiqDecretosTotalesOrdenado.isEmpty()) {
//            throw new BusinessException("Error al realizar el ordenamiento de la informacion");
//        }
//        listaLiqDecretosTotales.clear();
//        listaLiqDecretosTotales.addAll(mapaLiqDecretosTotalesOrdenado.values());
//
//        /* Define los mapas para los totales por institucion */
//        Map importePendienteXInst = new HashMap();
//        Map importeLiquidadoXInst = new HashMap();
//        Map importeRemanenteXInst = new HashMap();
//
//        /* Estructura la informacion para la pantalla */
//        Map mapCuentaLiqVO = new HashMap();
//        for (Iterator iter = listaLiqDecretos.iterator(); iter.hasNext();) {
//            com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO liqDecretosVO =
//            	(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO) iter.next();
//            RegCuentaLiqVO regCuentaLiqVO = new RegCuentaLiqVO();
//            regCuentaLiqVO.setCuenta(liqDecretosVO.getCuenta());
//            regCuentaLiqVO.setDivisaPago(liqDecretosVO.getDivisaPago().equalsIgnoreCase(PESOS) ? 
//            		MXN : liqDecretosVO.getDivisaPago());
//            regCuentaLiqVO.setDivisaPago(regCuentaLiqVO.getDivisaPago().equalsIgnoreCase(DOLARES) ? 
//            		USD : regCuentaLiqVO.getDivisaPago());
//            regCuentaLiqVO.setDivisaPago(regCuentaLiqVO.getDivisaPago().equalsIgnoreCase(UDIS) ? 
//            		MXV : regCuentaLiqVO.getDivisaPago());
//            regCuentaLiqVO.setEmision(new EmisionVO());
//            regCuentaLiqVO.getEmision().setIdTipoValor(liqDecretosVO.getTv());
//            regCuentaLiqVO.getEmision().setEmisora(liqDecretosVO.getEmisora());
//            regCuentaLiqVO.getEmision().setSerie(liqDecretosVO.getSerie());
//            regCuentaLiqVO.getEmision().setCupon(liqDecretosVO.getCupon());
//            regCuentaLiqVO.getEmision().setIsin(liqDecretosVO.getIsin());
//            regCuentaLiqVO.setFechaPago(liqDecretosVO.getFechaLiq());
//            regCuentaLiqVO.setFechaVencimiento(liqDecretosVO.getFechaVencimientoEmision());
//            regCuentaLiqVO.setFolioFija(
//                    liqDecretosVO.getFolioFija() != null ?
//                            liqDecretosVO.getFolioFija().toString() : UtilsDaliVO.BLANK);
//            regCuentaLiqVO.setFolioVariable(
//                    liqDecretosVO.getFolioVariable() != null ?
//                            liqDecretosVO.getFolioVariable().toString() : UtilsDaliVO.BLANK);
//            regCuentaLiqVO.setIdInst(liqDecretosVO.getIdInst());
//            regCuentaLiqVO.setFolioInst(liqDecretosVO.getFolioInst());
//            regCuentaLiqVO.setTipoEjercicio(liqDecretosVO.getTipoDerecho());
//            regCuentaLiqVO
//                    .setImporteLiquidado(liqDecretosVO.getImporteCobrado() != null ? liqDecretosVO
//                            .getImporteCobrado() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//            log.info("LIQ PENDIENTE= " + liqDecretosVO.getImporteACobrar());
//            regCuentaLiqVO
//                    .setImportePendiente(liqDecretosVO.getImporteACobrar() != null ? liqDecretosVO
//                            .getImporteACobrar() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//            regCuentaLiqVO.setIdDerecho(
//                    liqDecretosVO.getIdDerecho() != null ?
//                            liqDecretosVO.getIdDerecho() : UtilsDaliVO.INTEGER_ZERO);
//            regCuentaLiqVO.setIdTipoDerecho(
//                    liqDecretosVO.getIdTipoDerecho() != null ?
//                            liqDecretosVO.getIdTipoDerecho() : UtilsDaliVO.INTEGER_ZERO);
//            regCuentaLiqVO.setIdTipoEjercicio(
//                    liqDecretosVO.getIdTipoEjercicio() != null ?
//                            liqDecretosVO.getIdTipoEjercicio() : UtilsDaliVO.INTEGER_ZERO);
//
//            String institucion = regCuentaLiqVO.getIdInst() + regCuentaLiqVO.getFolioInst();
//            if (!mapCuentaLiqVO.containsKey(institucion)) {
//                mapCuentaLiqVO.put(institucion, new HashMap());
//            }
//            Map mapSubCuentaLiqVO = (Map) mapCuentaLiqVO.get(institucion);
//
//            if (!mapSubCuentaLiqVO.containsKey(regCuentaLiqVO.getTipoEjercicio())) {
//                mapSubCuentaLiqVO.put(regCuentaLiqVO.getTipoEjercicio(), new ArrayList());
//            }
//            List listRegCuentaLiqVO = (List) mapSubCuentaLiqVO.get(regCuentaLiqVO.getTipoEjercicio());
//            listRegCuentaLiqVO.add(regCuentaLiqVO);
//        }
//
//        /* Obtiene los totales por institucion */
//        for (Iterator iter = listaLiqDecretosTotales.iterator(); iter.hasNext();) {
//            com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO liqDecretosVO =
//            	(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO) iter.next();
//            String institucion = liqDecretosVO.getIdInst() + liqDecretosVO.getFolioInst();
//            if (!importePendienteXInst.containsKey(institucion)) {
//                importePendienteXInst.put(institucion, liqDecretosVO.getImporteACobrar() != null ? 
//                		liqDecretosVO.getImporteACobrar() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//            }
//            else {
//                importePendienteXInst.put(institucion, ((BigDecimal) importePendienteXInst.get(
//                		institucion)).add(liqDecretosVO.getImporteACobrar() != null ? 
//                				liqDecretosVO.getImporteACobrar() : UtilsDaliVO.BIG_DECIMAL_ZERO));
//            }
//            if (!importeLiquidadoXInst.containsKey(institucion)) {
//                importeLiquidadoXInst.put(institucion, liqDecretosVO.getImporteCobrado() != null ? 
//                		liqDecretosVO.getImporteCobrado() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//            }
//            else {
//                importeLiquidadoXInst.put(institucion, ((BigDecimal) importeLiquidadoXInst.get(
//                		institucion)).add(liqDecretosVO.getImporteCobrado() != null ? 
//                				liqDecretosVO.getImporteCobrado() : UtilsDaliVO.BIG_DECIMAL_ZERO));
//            }
//            importeRemanenteXInst.put(institucion, ((BigDecimal) importePendienteXInst.get(
//            		institucion)).subtract((BigDecimal) importeLiquidadoXInst.get(institucion)));
//
//        }
//
//        /* Almacena la informacion en los objetos usados en la pantalla */
//        List listaCuentaLiqVO = new ArrayList();
//        listaCuentaLiqVO.addAll(mapCuentaLiqVO.keySet());
//        Map mapCuentaLiqVOOrdenado = sortInstitucionesString(listaCuentaLiqVO);
//        CuentaLiquidacionVO[] cuentaLiqVO = new CuentaLiquidacionVO[mapCuentaLiqVO.keySet().size()];
//        int contCuenta = 0;
//        for (Iterator iter = mapCuentaLiqVOOrdenado.keySet().iterator(); iter.hasNext();) {
//            String institucion = (String) iter.next();
//            Map mapSubCuentaLiqVO = (Map) mapCuentaLiqVO.get(institucion);
//            SubCuentaLiqVO[] subCuentaLiqVO = new SubCuentaLiqVO[mapSubCuentaLiqVO.keySet().size()];
//            int contSubCuenta = 0;
//            for (Iterator iterator = mapSubCuentaLiqVO.keySet().iterator(); iterator.hasNext();) {
//                String tipoEjercicio = (String) iterator.next();
//                List listRegCuentaLiqVO = (List) mapSubCuentaLiqVO.get(tipoEjercicio);
//                RegCuentaLiqVO[] regCuentaLiqVO = new RegCuentaLiqVO[listRegCuentaLiqVO.size()];
//                listRegCuentaLiqVO.toArray(regCuentaLiqVO);
//                BigDecimal subImportePendiente = UtilsDaliVO.BIG_DECIMAL_ZERO;
//                BigDecimal subImporteLiquidado = UtilsDaliVO.BIG_DECIMAL_ZERO;
//                BigDecimal subImporteRemanente = UtilsDaliVO.BIG_DECIMAL_ZERO;
//                for (int i = 0; i < regCuentaLiqVO.length; i++) {
//                    subImportePendiente = subImportePendiente.add(
//                    		regCuentaLiqVO[i].getImportePendiente());
//                    log.info("PENDIENTE= " + regCuentaLiqVO[i].getImportePendiente());
//                    subImporteLiquidado = subImporteLiquidado.add(
//                    		regCuentaLiqVO[i].getImporteLiquidado());
//                    subImporteRemanente = subImportePendiente.subtract(subImporteLiquidado);
//                }
//                subCuentaLiqVO[contSubCuenta] = new SubCuentaLiqVO();
//                subCuentaLiqVO[contSubCuenta].setRegCuentaLiq(regCuentaLiqVO);
//                subCuentaLiqVO[contSubCuenta].setTotalImportePendiente(subImportePendiente);
//                subCuentaLiqVO[contSubCuenta].setTotalImporteLiquidado(subImporteLiquidado);
//                subCuentaLiqVO[contSubCuenta].setTotalRemanente(subImporteRemanente);
//                log.info("SUBTOTAL PENDIENTE= " + subImportePendiente);
//                log.info("SUBTOTAL LIQUIDADO= " + subImporteLiquidado);
//                log.info("SUBTOTAL REMANENTE= " + subImporteRemanente);
//                contSubCuenta++;
//            }
//            cuentaLiqVO[contCuenta] = new CuentaLiquidacionVO();
//            cuentaLiqVO[contCuenta].setSubCuentaLiq(subCuentaLiqVO);
//            cuentaLiqVO[contCuenta].setGranTotalImportePendiente(
//            		(BigDecimal) importePendienteXInst.get(institucion));
//            cuentaLiqVO[contCuenta].setGranTotalImporteLiquidado(
//            		(BigDecimal) importeLiquidadoXInst.get(institucion));
//            cuentaLiqVO[contCuenta].setGranTotalRemanente(
//            		(BigDecimal) importeRemanenteXInst.get(institucion));
//            contCuenta++;
//        }
//
//        return cuentaLiqVO;
        
        return null;
        
    }

//    /**
//     * Ordena los registros para la consulta de liquidacion por decretos
//     *
//     * @param listaLiqDecretos
//     * @return TreeMap Mapa ordenado de registros
//     * @throws BusinessException
//     */
//    private TreeMap<String, LiquidacionDecretosVO> sortLiqDecretos(List listaLiqDecretos) 
//    			throws BusinessException {
//        
//        log.info("Entrando a TesoreriaServiceImpl.sortLiqDecretos()");
//        
//        TreeMap<String, LiquidacionDecretosVO> map = new TreeMap<String, LiquidacionDecretosVO>();
//        long secuencia=0;
//        if (listaLiqDecretos != null) {
//            for (Iterator iter = listaLiqDecretos.iterator(); iter.hasNext();) {
//                LiquidacionDecretosVO liqDecretosVO =
//                	(LiquidacionDecretosVO) iter.next();
//                if (liqDecretosVO == null) {
//                    throw new BusinessException(errorResolver.getMessage("J0081"));
//                }
//                if (!liqDecretosVO.getTipoDerecho().equalsIgnoreCase(TIPO_EJERCICIO_COB)) {
//                	String key=liqDecretosVO.getIdInst() + liqDecretosVO.getFolioInst()
//                    + liqDecretosVO.getCuenta() + liqDecretosVO.getTv()
//                    + liqDecretosVO.getEmisora() + liqDecretosVO.getSerie()
//                    + liqDecretosVO.getCupon() + liqDecretosVO.getIsin()
//                    + liqDecretosVO.getFechaLiq() + liqDecretosVO.getTipoDerecho();
//                	if(map.containsKey(key)){
//                		key=key +(++secuencia);
//                	}
//                    map.put(key,liqDecretosVO);
//                }
//            }
//        }
//        return map;
//    }

//    /**
//     * Ordena una lista de agentes(instituciones) en base a su id+folio
//     * @param listaInstituciones
//     * @return TreeMap
//     */
//    private TreeMap sortInstituciones(List listaInstituciones) {
//        
//        log.info("Entrando a TesoreriaServiceImpl.sortInstituciones()");
//        
//        TreeMap map = new TreeMap();
//        if(listaInstituciones != null){
//        	for(int i=0;i<listaInstituciones.size();i++){
//        		if(listaInstituciones.get(i)  instanceof AgenteVO) {
//					AgenteVO institucionPortal = (AgenteVO) listaInstituciones.get(i);
//					map.put(institucionPortal.getId() + institucionPortal.getFolio(), 
//							institucionPortal);
//				}
//            	else{
//            		com.indeval.sidv.decretos.persistence.model.vo.AgenteVO
//            		institucionLiquidacion = (com.indeval.sidv.decretos.persistence.model.vo.
//            				AgenteVO) listaInstituciones.get(i);
//            		map.put(institucionLiquidacion.getId() + institucionLiquidacion.getFolio(), 
//            				institucionLiquidacion);
//            	}
//        	}
//        }
//        return map;
//    }

//    /**
//     * Ordena una lista de instituciones en base a su id
//     * @param listaInstituciones
//     * @return TreeMap
//     */
//    private TreeMap sortInstitucionesString(List listaInstituciones) {
//        
//        log.info("Entrando a TesoreriaServiceImpl.sortInstitucionesString()");
//        
//        TreeMap<String, String> map = new TreeMap<String, String>();
//        if (listaInstituciones != null) {
//            for (Iterator iter = listaInstituciones.iterator(); iter.hasNext();) {
//                String institucion = (String) iter.next();
//                map.put(institucion, institucion);
//            }
//        }
//        return map;
//    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleAmortizaciones(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleAmortizacionesParams)
     */
    public DetalleAmortizacionesVO[] getDetalleAmortizaciones(
            GetDetalleAmortizacionesParams params) throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.getDetalleAmortizaciones()");

//        utilService.validaDTONoNulo(params, "");
//        this.validaAgente(params.getAgente());
//        
//        DetalleAmortizacionesVO[] detalles = null;
//        DetalleAmortizacionesParams paramsDao = new DetalleAmortizacionesParams();
//
//        paramsDao.setIdInst(params.getAgente().getId());
//        paramsDao.setFolioInst(params.getAgente().getFolio());
//        paramsDao.setCuenta(params.getAgente().getCuenta());
//
//        List listaDetalleAmortizacionParcial =
//        	parcialidadesDao.findDetalleAmortizacionParcialidades(paramsDao);
//
//        if(listaDetalleAmortizacionParcial==null || listaDetalleAmortizacionParcial.size()==0){
//            throw new BusinessException("No hay registros coincidentes");
//        }
//        log.debug("La lista devuelta por el Dao tiene [" + listaDetalleAmortizacionParcial.size() +
//        		"] registros");
//        List<DetalleAmortizacionesVO> listaDetalleAmortizacionesVO = 
//        	new ArrayList<DetalleAmortizacionesVO>();
//        for (Iterator iter = listaDetalleAmortizacionParcial.iterator(); iter.hasNext();) {
//            Parcialidad parcialidad = (Parcialidad) iter.next();
//            DetalleAmortizacionesVO detalleAmortizacionesVO = new DetalleAmortizacionesVO();
//            detalleAmortizacionesVO.setFolioContraparte(parcialidad.getFolEmisor());
//            detalleAmortizacionesVO.setFolioLiquidacion(parcialidad.getFolio());
//            detalleAmortizacionesVO.setIdContraparte(parcialidad.getIdEmisor());
//            detalleAmortizacionesVO.setImporte(parcialidad.getImporte());
//            detalleAmortizacionesVO.setOperacion(parcialidad.getParcialidadPK().getParcialidad());
//            detalleAmortizacionesVO.setTitulos(parcialidad.getTitulos());
//            listaDetalleAmortizacionesVO.add(detalleAmortizacionesVO);
//        }
//        log.debug("La lista a retornar tiene [" + listaDetalleAmortizacionesVO.size() +
//        		"] registros");
//        detalles = new DetalleAmortizacionesVO[listaDetalleAmortizacionesVO.size()];
//        listaDetalleAmortizacionesVO.toArray(detalles);
//
//        return detalles;
        
        return null;
        
    }

    /**
     * @throws com.indeval.sidv.decretos.common.exception.BusinessException 
     * @throws com.indeval.sidv.decretos.common.exception.BusinessException 
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleAmort(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleAmortizacionesParams)
     */
    public DetalleAmortizacionesVO[] getDetalleAmort(GetDetalleAmortizacionesParams params) throws BusinessException
    		  {

        logger.info("Entrando a TesoreriaServiceImpl.getDetalleAmort()");
        
        utilService.validaDTONoNulo(params, "");
        utilService.validaAgente(params.getAgente());
//        this.validaAgente(params.getAgente());
       
        /* Se obtiene informacion del Portal */
		// List listaDetalleAmort =
		// this.getLiquidacionDecretosDetalleAmortizaciones(params);
		// if (listaDetalleAmort == null) {
        	List listaDetalleAmort = new ArrayList();
//        }

        /* Se obtiene informacion de Ejercicio de Derechos */
        params.setFolioVariable(null);
        params.setFolioFija(null);

        LiquidacionDecretosDetalleAmortizacionesParams paramsEjercicioDecechos =
        	this.detalleAmortizacionesParams2LiquidacionDecretosDetalleAmortizacionesParams(params);

        List listaDetalleAmortEjerDer;
		try {
			listaDetalleAmortEjerDer = decretosEjercicioDerechos.getLiquidacionDecretosDetalleAmortizaciones(
        			paramsEjercicioDecechos);
			if (listaDetalleAmortEjerDer != null && !listaDetalleAmortEjerDer.isEmpty()) {
	            listaDetalleAmort.addAll(listaDetalleAmortEjerDer);
	        }
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}

        

        if (listaDetalleAmort == null || listaDetalleAmort.isEmpty()) {
            throw new BusinessException("No existe detalle de parcialidades");
        }

        /* Obtiene el arreglo de retorno */
        DetalleAmortizacionesVO[] detalleAmortizacionesVO = 
        	new DetalleAmortizacionesVO[listaDetalleAmort.size()];
        for (int i = 0; i < detalleAmortizacionesVO.length; i++) {
        	com.indeval.sidv.decretos.persistence.model.vo.
        			LiquidacionDecretosDetalleAmortizacionesVO parcialidad = 
        				(com.indeval.sidv.decretos.persistence.model.vo.
        						LiquidacionDecretosDetalleAmortizacionesVO) listaDetalleAmort.get(i);
             detalleAmortizacionesVO[i] = new DetalleAmortizacionesVO();
             detalleAmortizacionesVO[i].setFolioInstitucion(parcialidad.getFolioInstitucion());
             detalleAmortizacionesVO[i].setIdInstitucion(parcialidad.getIdInstitucion());
             detalleAmortizacionesVO[i].setOperacion(parcialidad.getOperacion());
             detalleAmortizacionesVO[i].setIdContraparte(parcialidad.getIdContraparte());
             detalleAmortizacionesVO[i].setFolioContraparte(parcialidad.getFolioContraparte());
             detalleAmortizacionesVO[i].setFolioLiquidacion(parcialidad.getFolioLiquidacion());
             detalleAmortizacionesVO[i].setImporte(
                     parcialidad.getImporte() != null ? 
                    		 parcialidad.getImporte() : Constantes.BIG_DECIMAL_ZERO);
             detalleAmortizacionesVO[i].setTitulos(
                     parcialidad.getTitulos() != null ? 
                    		 parcialidad.getTitulos() : Constantes.BIG_INTEGER_ZERO);
             detalleAmortizacionesVO[i].setCodigoDivisa(Constantes.BLANK);
             detalleAmortizacionesVO[i].setDescEjercicio(Constantes.BLANK);
             detalleAmortizacionesVO[i].setIdDerecho(Constantes.INTEGER_ZERO);
        }

        return detalleAmortizacionesVO;
        
        //return null;
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleCuentaLiqVO(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleCuentaLiqParams)
     */
    public DetalleCuentaLiqVO getDetalleCuentaLiqVO(GetDetalleCuentaLiqParams params)
            throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.getDetalleCuentaLiqVO()");

//        utilService.validaDTONoNulo(params, "");
//        this.validaAgente(params.getAgente());
//        
//        ProductoDineroPK productoDineroPK = new ProductoDineroPK();
//        productoDineroPK.setIdInst(params.getAgente().getId());
//        productoDineroPK.setFolioInst(params.getAgente().getFolio());
//        productoDineroPK.setCuenta(params.getAgente().getCuenta());
//
//        try {
//            productoDineroPK.setFolioFija(new Integer(params.getFolioFija()));
//            productoDineroPK.setFolioVariable(new Integer(params.getFolioVariable()));
//        }
//        catch (NumberFormatException e) {
//            throw new BusinessException("error en formato de: folio_variable o folio_fija" , e);
//        }
//
//        ProductoDinero productoDinero =
//            productoDineroDao.getProductoEmisionByPk(productoDineroPK);
//
//        if (productoDinero == null) {
//            throw new BusinessException("No se encontro detalle");
//        }
//
//        DetalleCuentaLiqVO detalleCuentaLiqVO = new DetalleCuentaLiqVO();
//
//        EmisionVO emisionVO = new EmisionVO();
//        emisionVO.setEmisora(productoDinero.getEmision().getEmisionPk().getEmisora());
//        emisionVO.setSerie(productoDinero.getEmision().getEmisionPk().getSerie());
//        emisionVO.setCupon(productoDinero.getEmision().getEmisionPk().getCupon());
//        emisionVO.setIdTipoValor(productoDinero.getEmision().getEmisionPk().getTv());
//
//        detalleCuentaLiqVO.setEmision(emisionVO);
//        detalleCuentaLiqVO.setFechaLiquidacion(productoDinero.getFechaLiquidacion());
//        detalleCuentaLiqVO.setImporteDecreto(productoDinero.getImporteLiq() != null
//                ? productoDinero.getImporteLiq().subtract(productoDinero.getInteres() != null
//                        ? productoDinero.getInteres() :
//                            UtilsDaliVO.BIG_DECIMAL_ZERO) : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO.setImporteCobrar(productoDinero.getImporteLiq() != null
//                ? productoDinero.getImporteLiq() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO.setIntereses(productoDinero.getInteres() != null
//                ? productoDinero.getInteres() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO.setSaldoTitulos(productoDinero.getSaldoDisponible() != null
//                ? productoDinero.getSaldoDisponible() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO.setValorNominal(productoDinero.getEmision().getValorNominal());
//
//        return detalleCuentaLiqVO;
        
        return null;
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleCuentaLiquidacionVO(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleCuentaLiqParams)
     */
    public DetalleCuentaLiqVO getDetalleCuentaLiquidacionVO(GetDetalleCuentaLiqParams params) 
            throws BusinessException {
        
        logger.info("Entrando a TesoreriaServiceImpl.getDetalleCuentaLiquidacionVO()");
        
//        utilService.validaDTONoNulo(params, "");
//        this.validaAgente(params.getAgente());
//
//        /* Se obtiene informacion del Ejercicio de Derechos */
//        com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO
//        liqDecretosDetalleVO = decretosEjercicioDerechos.getLiquidacionDecretosDetalle(params);
//
//        /* Se obtiene informacion de Portal */
//        if (liqDecretosDetalleVO == null 
//        		|| StringUtils.isBlank(liqDecretosDetalleVO.getIdInstitucion())
//				|| StringUtils.isBlank(liqDecretosDetalleVO.getFolioInstitucion())
//				|| StringUtils.isBlank(liqDecretosDetalleVO.getCuenta())) {
//            liqDecretosDetalleVO = this.getLiquidacionDecretosDetalle(params);
//            if (liqDecretosDetalleVO == null) {
//                throw new BusinessException(errorResolver.getMessage("J0082"));
//            }
//        }
//
//        /* Se pasa la informacion al objeto de retorno */
//        DetalleCuentaLiqVO detalleCuentaLiqVO = new DetalleCuentaLiqVO();
//        detalleCuentaLiqVO.setEmision(
//        		this.emisionSidv2EmisionVOPortal(liqDecretosDetalleVO.getEmision()));
//        detalleCuentaLiqVO.setFechaLiquidacion(liqDecretosDetalleVO.getFechaLiquidacion());
//        detalleCuentaLiqVO.setImporteDecreto(liqDecretosDetalleVO.getImporteDecreto());
//        detalleCuentaLiqVO.setImporteCobrar(liqDecretosDetalleVO.getImporteCobrar());
//        detalleCuentaLiqVO.setIntereses(liqDecretosDetalleVO.getIntereses());
//        detalleCuentaLiqVO.setSaldoTitulos(liqDecretosDetalleVO.getSaldoTitulos());
//        detalleCuentaLiqVO.setValorNominal(liqDecretosDetalleVO.getValorNominal());
//        detalleCuentaLiqVO.setDivisa(liqDecretosDetalleVO.getDivisa() != null ?
//                        liqDecretosDetalleVO.getDivisa() : UtilsDaliVO.BLANK);
//        detalleCuentaLiqVO.setDivisa(detalleCuentaLiqVO.getDivisa().equalsIgnoreCase(PESOS) ? 
//        		MXN : detalleCuentaLiqVO.getDivisa());
//        detalleCuentaLiqVO.setDivisa(detalleCuentaLiqVO.getDivisa().equalsIgnoreCase(DOLARES) ? 
//        		USD : detalleCuentaLiqVO.getDivisa());
//        detalleCuentaLiqVO.setDivisa(detalleCuentaLiqVO.getDivisa().equalsIgnoreCase(UDIS) ? 
//        		MXV : detalleCuentaLiqVO.getDivisa());
//        detalleCuentaLiqVO.setTasaInteres(
//                liqDecretosDetalleVO.getTasaInteres() != null ?
//                        liqDecretosDetalleVO.getTasaInteres() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO.setTasaRendimientoDescto(
//                liqDecretosDetalleVO.getTasaRendimientoDescto() != null ?
//                        liqDecretosDetalleVO.getTasaRendimientoDescto() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO
//                .setTasaRendimiento(liqDecretosDetalleVO.getTasaRendimiento() != null ? 
//                		liqDecretosDetalleVO.getTasaRendimiento() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO
//                .setTasaDescto(liqDecretosDetalleVO.getTasaDescuento() != null ? 
//                		liqDecretosDetalleVO.getTasaDescuento() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//        detalleCuentaLiqVO.setPlazo(
//                liqDecretosDetalleVO.getPlazo() != null ?
//                        liqDecretosDetalleVO.getPlazo() : UtilsDaliVO.INTEGER_ZERO);
//
//        return detalleCuentaLiqVO;
        
        return null;
        
    }

    /*
     * NOTAS DE DESARROLLO
     *
     * select ?aplicacion = origen_aplicacion ?contraparte.id = id_inst_recep
     * ?contraparte.folio = folio_inst_recep ?cuenta = cta_destino (si es cobro)
     * ?cuenta = cta_origen (si es pago) ?folioOperacion = folio ?folioOriginal =
     * folio_origen ?hora = fecha_hora ?importePago = importe (si agente.id =
     * id_inst and agente.folio = folio_inst) ?importeCobro = importe (si
     * agente.id = id_inst_recep and agente.folio = folio_inst_recep) ?mercado =
     * mercado ?Movimiento = movimiento ?Origen = origen ?saldo = (diferencia
     * acumulada del importe, cobros se suman y pagos se restan) from
     * bdgubern..trspefec t (si es en linea) from bdgubern..cptrspefec c ( si es
     * historico) where ((id_inst = ?agente.id and folio_inst = ?agente.folio)
     * or (id_inst_recep = ?agente.id and folio_inst_recep = ?agente.folio)) and
     * id_inst_recep = ?contraparte.id and folio_inst_recep = ?contraparte.folio
     * and fecha_liq = ?fechaOperacion (si es historico) and origen_aplicacion =
     * ?idAplicacion and (cta_destino = ?idCuentasEfectivo or cta_origen =
     * ?idCuentasEfectivo) and mercado = ?idMercado and tipo_movimiento =
     * ?idMovimiento and origen = ?idOrigen
     *
     * si agrupacion = cronologica hacer order by llave_folio, id_inst,
     * folio_inst, folio si agrupacion = agrupada hacer order by mercado asc,
     * origen asc, tipo_movimiento asc, origen_aplicacion asc
     *
     * si fechaOperacion != null es historico sino es en linea.
     *
     * Nota: - Se considera pago cuando el agente coincide con id_inst y
     * folio_inst - Se considera cobro cuando el agente coincide con
     * id_inst_recep y folio_inst_recep
     *
     * Nota: si es pago y cobro entonces se muestran dos registros.
     *
     */
    /**
     * @throws BusinessException
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getEstadoCuentaSNE(com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaSNEParams)
     */
    public EstadoCuentaSNEVO getEstadoCuentaSNE(GetEdoCtaSNEParams params) throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.getEstadoCuentaSNE()");

//        /* Se validan los parametros */
//        utilService.validaDTONoNulo(params, "");
//        utilService.validaAgente(params.getAgente(), false);
//        params.agrupacionValida();
//        params.setPagina(UtilsDaliVO.getPaginaNotBlank(params.getPagina()));
//        
//        GetEdoCtaSNEParamsPersistence paramsPersistence = new GetEdoCtaSNEParamsPersistence();
//
//        AgentePK agentePK = new AgentePK();
//        agentePK.setIdInst(params.getAgente().getId());
//        agentePK.setFolioInst(params.getAgente().getFolio());
//        agentePK.setCuenta(params.getAgente().getClave());
//
//        paramsPersistence.setAgentePK(agentePK);
//        paramsPersistence.setAgrupacion(params.getAgrupacion());
//        paramsPersistence.setPagina(UtilsDaliVO.getPageVO(params.getPagina()));
//        paramsPersistence.setFechaOperacion(params.getFechaOperacion());
//        paramsPersistence.setIdAplicacion(params.getIdAplicacion());
//        paramsPersistence.setIdCuentasEfectivo(params.getIdCuentasEfectivo());
//        paramsPersistence.setIdMercado(params.getIdMercado());
//        paramsPersistence.setIdMovimiento(params.getIdMovimiento());
//        paramsPersistence.setIdOrigen(params.getIdOrigen());
//        paramsPersistence.setClaveAgenteFirmado(params.getAgente().getClave());
//
//        AgentePK contraParte = new AgentePK();
//
//        if(params.getContraparte() != null){
//        	contraParte.setIdInst(params.getContraparte().getId());
//        	contraParte.setFolioInst(params.getContraparte().getFolio());
//        	contraParte.setCuenta(params.getContraparte().getCuenta());
//        }
//        paramsPersistence.setContrapartePK(contraParte);
//
////         if (params.getEmision() != null) {
////         EmisionPK emisionPK = new EmisionPK();
////         if (StringUtils.isNotBlank(params.getEmision().getIdTipoValor())) {
////         emisionPK.setTv(params.getEmision().getIdTipoValor());
////         }
////         if (StringUtils.isNotBlank(params.getEmision().getEmisora())) {
////         emisionPK.setEmisora(params.getEmision().getEmisora());
////         }
////         if (StringUtils.isNotBlank(params.getEmision().getSerie())) {
////         emisionPK.setSerie(params.getEmision().getSerie());
////         }
////         if (StringUtils.isNotBlank(params.getEmision().getCupon())) {
////         emisionPK.setCupon(params.getEmision().getCupon());
////         }
////         paramsPersistence.setEmisionPK(emisionPK);
////         }
//
//        PageVO paginaPersitence = traspasosEfectivoJDBCDao.getRegEstadoCuentaSNE(paramsPersistence);
//
//        // Se recupera la lista de la pagina y se verifica
//        List listaTraspasoEfectivo = paginaPersitence.getRegistros();
//        if (listaTraspasoEfectivo == null || listaTraspasoEfectivo.isEmpty()) {
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//        log.debug("El DAO retorno [" + listaTraspasoEfectivo.size() + "] registros");
//        int cruces = 0; // variable de conteo para debugear los cruces
//
//        // Se crea la lista de RegEstadoCuentaSNEVO y las variables para los
//        // totales y saldo acumulado.
//        List listaRegEstadoCuentaSNEVO = new ArrayList();
//        BigDecimal totalCobros = UtilsDaliVO.BIG_DECIMAL_ZERO;
//        BigDecimal totalPagos = UtilsDaliVO.BIG_DECIMAL_ZERO;
//        BigDecimal saldoAcumulado = UtilsDaliVO.BIG_DECIMAL_ZERO;
//
//        boolean filtroClaveValor = false;
//
//        if (StringUtils.isNotBlank(params.getEmision().getIdTipoValor())
//                || StringUtils.isNotBlank(params.getEmision().getEmisora())
//                || StringUtils.isNotBlank(params.getEmision().getSerie())
//                || StringUtils.isNotBlank(params.getEmision().getCupon())) {
//            filtroClaveValor = true;
//        }
//
//        for (Iterator iteratorTraspasoEfectivo = 
//        	listaTraspasoEfectivo.iterator(); iteratorTraspasoEfectivo.hasNext();) {
//
//            log.debug("Recorriendo la lista de traspasoEfectivo");
//            TraspasoEfectivo elementoTraspasoEfectivo = (TraspasoEfectivo) iteratorTraspasoEfectivo
//                    .next();
//
//            if (elementoTraspasoEfectivo != null
//                    && elementoTraspasoEfectivo.getTraspasoEfectivoPk() != null
//                    && elementoTraspasoEfectivo.getInstitucionTraspasante() != null
//                    && elementoTraspasoEfectivo.getInstitucionTraspasante().getInstitucionPK() != null
//                    && elementoTraspasoEfectivo.getInstitucionReceptora() != null
//                    && elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK() != null) {
//
//                boolean isDecreto = isDecreto(elementoTraspasoEfectivo);
//
//                if (isDecreto) {
//                    log.debug("El traspaso es un Decreto, Intemis o Vencemis");
//                    elementoTraspasoEfectivo = recuperaClaveValorDecreto(elementoTraspasoEfectivo);
//                }
//
//                if (filtroClaveValor) {
//                    if (!validaClaveValor(elementoTraspasoEfectivo, params)) {
//                        continue;
//                    }
//                }
//
//                // Se construyen los objetos de registro y la variable de
//                // saldo
//                // acumulado temporal
//                RegEstadoCuentaSNEVO regEstadoCuentaSNEVO = new RegEstadoCuentaSNEVO();
//                regEstadoCuentaSNEVO.setValido(false);
//                RegEstadoCuentaSNEVO regEstadoCuentaSNEVOCruce = new RegEstadoCuentaSNEVO();
//                regEstadoCuentaSNEVOCruce.setValido(false);
//                BigDecimal saldoAcumuladoTemp = null;
//
//                int rol = determinaRol(elementoTraspasoEfectivo, params);
//                switch (rol) {
//                    case -1: // El agenteFirmado esta PAGANDO
//                        regEstadoCuentaSNEVO.setContraparte(new AgenteVO(elementoTraspasoEfectivo
//                                .getInstitucionReceptora().getInstitucionPK().getIdInst(),
//                                elementoTraspasoEfectivo.getInstitucionReceptora()
//                                        .getInstitucionPK().getFolioInst()));
//                        regEstadoCuentaSNEVO.getContraparte()
//                                .setNombreCorto(
//                                        elementoTraspasoEfectivo.getInstitucionReceptora()
//                                                .getNombreCorto());
//                        regEstadoCuentaSNEVO.setCuenta(elementoTraspasoEfectivo.getCtaOrigen()
//                                .trim()); // es la cuenta del agente
//                        // firmado
//                        regEstadoCuentaSNEVO.setValido(true);
//                        if (regEstadoCuentaSNEVO.isValido()) {
//                            regEstadoCuentaSNEVO.setImportePago(elementoTraspasoEfectivo
//                                    .getImporte());
//                            log.debug("Importe del pago : ["
//                                    + regEstadoCuentaSNEVO.getImportePago() + "]");
//                            totalPagos = totalPagos.add(elementoTraspasoEfectivo.getImporte());
//                            log.debug("Importe del total de pagos : [" + totalPagos + "]");
//                            saldoAcumulado = saldoAcumulado.subtract(regEstadoCuentaSNEVO
//                                    .getImportePago());
//                        }
//
//                        break;
//                    case 1: // El agenteFirmado esta COBRANDO
//
//                        regEstadoCuentaSNEVO.setContraparte(new AgenteVO(elementoTraspasoEfectivo
//                                .getInstitucionTraspasante().getInstitucionPK().getIdInst(),
//                                elementoTraspasoEfectivo.getInstitucionTraspasante()
//                                        .getInstitucionPK().getFolioInst()));
//                        regEstadoCuentaSNEVO.getContraparte().setNombreCorto(
//                                elementoTraspasoEfectivo.getInstitucionTraspasante()
//                                        .getNombreCorto());
//                        regEstadoCuentaSNEVO.setCuenta(
//                        		elementoTraspasoEfectivo.getCtaDestino()); // es la cuenta 
//                        												   // del agente firmado
//                        regEstadoCuentaSNEVO.setValido(true);
//                        if (regEstadoCuentaSNEVO.isValido()) {
//                            regEstadoCuentaSNEVO.setImporteCobro(elementoTraspasoEfectivo
//                                    .getImporte());
//                            log.debug("Importe del cobro : ["
//                                    + regEstadoCuentaSNEVO.getImporteCobro() + "]");
//                            totalCobros = totalCobros.add(elementoTraspasoEfectivo.getImporte());
//                            log.debug("Importe del total de cobros : [" + totalCobros + "]");
//                            saldoAcumulado = saldoAcumulado.add(regEstadoCuentaSNEVO
//                                    .getImporteCobro());
//                        }
//                        break;
//                    case 0: // El agenteFirmado esta PAGANDO y COBRANDO
//                        ++cruces;
//                        // registro de pago
//                        regEstadoCuentaSNEVO.setContraparte(new AgenteVO(elementoTraspasoEfectivo
//                                .getInstitucionReceptora().getInstitucionPK().getIdInst(),
//                                elementoTraspasoEfectivo.getInstitucionReceptora()
//                                        .getInstitucionPK().getFolioInst()));
//                        regEstadoCuentaSNEVO.getContraparte().setNombreCorto(
//                        		elementoTraspasoEfectivo.getInstitucionReceptora().getNombreCorto());
//                        regEstadoCuentaSNEVO.setValido(true);
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getCtaOrigen())) {
//                            regEstadoCuentaSNEVO.setCuenta(
//                            		elementoTraspasoEfectivo.getCtaOrigen().trim());
//                        }
//                        if (StringUtils.isNotBlank(params.getIdCuentasEfectivo())
//                                && !params.getIdCuentasEfectivo().equalsIgnoreCase(TODOS)
//                                && !regEstadoCuentaSNEVO.getCuenta().equalsIgnoreCase(
//                                        params.getIdCuentasEfectivo())) {
//                            regEstadoCuentaSNEVO.setValido(false);
//                        }
//                        log.debug("el registro es valido =  " + regEstadoCuentaSNEVO.isValido());
//                        if (regEstadoCuentaSNEVO.isValido()) {
//                            regEstadoCuentaSNEVO.setImportePago(elementoTraspasoEfectivo
//                                    .getImporte());
//                            log.debug("Importe del pago : ["
//                                    + regEstadoCuentaSNEVO.getImportePago() + "]");
//                            totalPagos = totalPagos.add(elementoTraspasoEfectivo.getImporte());
//                            log.debug("Importe del total de pagos : [" + totalPagos + "]");
//                            saldoAcumulado = saldoAcumulado.subtract(regEstadoCuentaSNEVO
//                                    .getImportePago());
//                        }
//
//                        // registro de cobro
//                        regEstadoCuentaSNEVOCruce.setContraparte(new AgenteVO(
//                                elementoTraspasoEfectivo.getInstitucionTraspasante()
//                                        .getInstitucionPK().getIdInst(), elementoTraspasoEfectivo
//                                        .getInstitucionTraspasante().getInstitucionPK()
//                                        .getFolioInst()));
//                        regEstadoCuentaSNEVOCruce.getContraparte().setNombreCorto(
//                                elementoTraspasoEfectivo.getInstitucionTraspasante()
//                                        .getNombreCorto());
//                        regEstadoCuentaSNEVOCruce.setValido(true);
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getCtaDestino())) {
//                            regEstadoCuentaSNEVOCruce.setCuenta(elementoTraspasoEfectivo
//                                    .getCtaDestino().trim());
//                        }
//                        if (StringUtils.isNotBlank(params.getIdCuentasEfectivo())
//                                && !params.getIdCuentasEfectivo().equalsIgnoreCase(TODOS)
//                                && !regEstadoCuentaSNEVOCruce.getCuenta().equalsIgnoreCase(
//                                        params.getIdCuentasEfectivo())) {
//                            regEstadoCuentaSNEVOCruce.setValido(false);
//                        }
//                        log.debug("el registro es valido =  "
//                                + regEstadoCuentaSNEVOCruce.isValido());
//                        if (regEstadoCuentaSNEVOCruce.isValido()) {
//                            regEstadoCuentaSNEVOCruce.setImporteCobro(elementoTraspasoEfectivo
//                                    .getImporte());
//                            log.debug("Importe del cobro : ["
//                                    + regEstadoCuentaSNEVOCruce.getImporteCobro() + "]");
//                            totalCobros = totalCobros.add(elementoTraspasoEfectivo.getImporte());
//                            log.debug("Importe del total de cobros : [" + totalCobros + "]");
//                            saldoAcumuladoTemp = saldoAcumulado;
//                            saldoAcumuladoTemp = saldoAcumuladoTemp.add(regEstadoCuentaSNEVOCruce
//                                    .getImporteCobro());
//                        }
//                        break;
//                }
//
//                regEstadoCuentaSNEVO.setSaldo(saldoAcumulado);
//                regEstadoCuentaSNEVOCruce.setSaldo(saldoAcumuladoTemp);
//                if (saldoAcumuladoTemp != null) {
//                    saldoAcumulado = saldoAcumuladoTemp;
//                }
//
//                regEstadoCuentaSNEVO.setFolioOperacion(elementoTraspasoEfectivo
//                        .getTraspasoEfectivoPk().getFolioOperacion());
//                regEstadoCuentaSNEVO.setMercado(elementoTraspasoEfectivo.getMercado());
//                regEstadoCuentaSNEVO.setMovimiento(elementoTraspasoEfectivo.getTipoMovimiento());
//                regEstadoCuentaSNEVO.setOrigen(elementoTraspasoEfectivo.getOrigen());
//                regEstadoCuentaSNEVO.setAplicacion(elementoTraspasoEfectivo.getOrigenAplicacion());
//                regEstadoCuentaSNEVO.setHora(elementoTraspasoEfectivo.getFechaHora());
//
//                regEstadoCuentaSNEVOCruce.setFolioOperacion(elementoTraspasoEfectivo
//                        .getTraspasoEfectivoPk().getFolioOperacion());
//                regEstadoCuentaSNEVOCruce.setMercado(elementoTraspasoEfectivo.getMercado());
//                regEstadoCuentaSNEVOCruce.setMovimiento(elementoTraspasoEfectivo
//                        .getTipoMovimiento());
//                regEstadoCuentaSNEVOCruce.setOrigen(elementoTraspasoEfectivo.getOrigen());
//                regEstadoCuentaSNEVOCruce.setAplicacion(elementoTraspasoEfectivo
//                        .getOrigenAplicacion());
//                regEstadoCuentaSNEVOCruce.setHora(elementoTraspasoEfectivo.getFechaHora());
//
//                EmisionVO emisionVO = new EmisionVO();
//                // BEGIN Mercado Secundario
//                if (elementoTraspasoEfectivo.getOrigenAplicacion().trim().startsWith(Constantes.MERCADO_SECUNDARIO)
//                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().startsWith(Constantes.RSP)
//                         || (elementoTraspasoEfectivo.getTipoMovimiento().trim().equalsIgnoreCase(Constantes.DECRETOS)
//                                 && (elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.INTEMIS)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.VENCEMIS)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.EJDPB)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.EJDPG)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.EJDMC)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.LIQEJDMC)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.VENCGAR)
//                                         || elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.MOVEFINT))
//                                         || (elementoTraspasoEfectivo.getTipoMovimiento().trim().equalsIgnoreCase(Constantes.TRJ)
//                                        		 && elementoTraspasoEfectivo.getOrigenAplicacion().trim().equalsIgnoreCase(Constantes.COLPRIM)))
//                        && elementoTraspasoEfectivo.getTraspaso() != null) {
//                    if (!isDecreto) {
//                        regEstadoCuentaSNEVO.setFolioOriginal(elementoTraspasoEfectivo
//                                .getFolioOrigen());
//                        regEstadoCuentaSNEVOCruce.setFolioOriginal(elementoTraspasoEfectivo
//                                .getFolioOrigen());
//                    }
//
//                    if (elementoTraspasoEfectivo.getTraspaso().getEmision() != null
//                            && elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk() != null) {
//
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getTraspaso()
//                                .getEmision().getEmisionPk().getTv())) {
//                            emisionVO.setIdTipoValor(elementoTraspasoEfectivo.getTraspaso()
//                                    .getEmision().getEmisionPk().getTv().trim());
//                        }
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getTraspaso()
//                                .getEmision().getEmisionPk().getEmisora())) {
//                            emisionVO.setEmisora(elementoTraspasoEfectivo.getTraspaso()
//                                    .getEmision().getEmisionPk().getEmisora().trim());
//                        }
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getTraspaso()
//                                .getEmision().getEmisionPk().getSerie())) {
//                            emisionVO.setSerie(elementoTraspasoEfectivo.getTraspaso().getEmision()
//                                    .getEmisionPk().getSerie().trim());
//                        }
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getTraspaso()
//                                .getEmision().getEmisionPk().getCupon())) {
//                            emisionVO.setCupon(elementoTraspasoEfectivo.getTraspaso().getEmision()
//                                    .getEmisionPk().getCupon());
//                        }
//                        if (elementoTraspasoEfectivo.getTraspaso().getEmision().getPrecioVector() != null) {
//                            emisionVO.setPrecioVector(elementoTraspasoEfectivo.getTraspaso()
//                                    .getEmision().getPrecioVector());
//                        }
//                        if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getTraspaso()
//                                .getEmision().getIsin())) {
//                            emisionVO.setIsin(elementoTraspasoEfectivo.getTraspaso().getEmision()
//                                    .getIsin().trim());
//                        }
//                        emisionVO.setFechaVencimiento(elementoTraspasoEfectivo.getTraspaso()
//                                .getEmision().getFechaVencimiento());
//
//                    }
//
//                    if (StringUtils.isNotBlank(elementoTraspasoEfectivo.getCtaValor())) {
//                        regEstadoCuentaSNEVO.setCuentaValor(elementoTraspasoEfectivo.getCtaValor());
//                        regEstadoCuentaSNEVOCruce.setCuentaValor(elementoTraspasoEfectivo
//                                .getCtaValor());
//                    }
//
//                    if (elementoTraspasoEfectivo.getTraspaso().getCantidadOperada() != null) {
//                        regEstadoCuentaSNEVO.setTitulos(elementoTraspasoEfectivo.getTraspaso()
//                                .getCantidadOperada().toBigInteger());
//                        regEstadoCuentaSNEVOCruce.setTitulos(elementoTraspasoEfectivo.getTraspaso()
//                                .getCantidadOperada().toBigInteger());
//                    }
//                    if (elementoTraspasoEfectivo.getTraspaso().getPrecioPorTitulo() != null) {
//                        regEstadoCuentaSNEVO.setPrecio(elementoTraspasoEfectivo.getTraspaso()
//                                .getPrecioPorTitulo());
//                        regEstadoCuentaSNEVOCruce.setPrecio(elementoTraspasoEfectivo.getTraspaso()
//                                .getPrecioPorTitulo());
//                    }
//                    if (regEstadoCuentaSNEVOCruce.getMovimiento().trim().equalsIgnoreCase(
//                            Constantes.TRR)) {
//
//                        if (elementoTraspasoEfectivo.getTraspaso().getDiasPlazo() != null) {
//                            regEstadoCuentaSNEVO.setPlazo(new BigInteger(elementoTraspasoEfectivo
//                                    .getTraspaso().getDiasPlazo().toString()));
//                            regEstadoCuentaSNEVOCruce.setPlazo(new BigInteger(
//                                    elementoTraspasoEfectivo.getTraspaso().getDiasPlazo()
//                                            .toString()));
//                        }
//                        if (elementoTraspasoEfectivo.getTraspaso().getTasaPremio() != null) {
//                            regEstadoCuentaSNEVO.setPorcentaje(elementoTraspasoEfectivo
//                                    .getTraspaso().getTasaPremio());
//                            regEstadoCuentaSNEVOCruce.setPorcentaje(elementoTraspasoEfectivo
//                                    .getTraspaso().getTasaPremio());
//                        }
//
//                    }
//
//                } // END Mercado Secundario
//
//                regEstadoCuentaSNEVO.setClaveDeValor(emisionVO);
//                regEstadoCuentaSNEVOCruce.setClaveDeValor(emisionVO);
//
//                if (regEstadoCuentaSNEVO.isValido()) {
//                    listaRegEstadoCuentaSNEVO.add(regEstadoCuentaSNEVO);
//                }
//                if (regEstadoCuentaSNEVOCruce.isValido()) {
//                    listaRegEstadoCuentaSNEVO.add(regEstadoCuentaSNEVOCruce);
//                }
//
//            }
//
//        }
//
//        // Se construye el objeto de retorno
//        EstadoCuentaSNEVO estadoCuentaSNE = new EstadoCuentaSNEVO();
//
//        // Se settea la pagina de registros
//        log.debug("La listaRegEstadoCuentaSNEVO tiene [" + listaRegEstadoCuentaSNEVO.size()
//                + "] registros");
//        log.debug("Hay [" + cruces + "] cruces");
//        PaginaVO laPagina = params.getPagina();
//        laPagina.extraerSublist(listaRegEstadoCuentaSNEVO);
//        estadoCuentaSNE.setPagina(laPagina);
//        log.debug("La pagina lleva [" + laPagina.getRegistros().size() + "] registros");
//
//        // Se settean los totales
//        log.debug("totalCobros [" + totalCobros + "]");
//        estadoCuentaSNE.setTotCobros(totalCobros);
//        log.debug("totalPagos [" + totalPagos + "]");
//        estadoCuentaSNE.setTotPagos(totalPagos);
//        log.debug("totalSaldos [" + totalCobros.subtract(totalPagos) + "]");
//        estadoCuentaSNE.setTotSaldos(totalCobros.subtract(totalPagos));
//
//        // Se imprime el objeto de retorno en el Log
//        log.debug("Imprimiendo el objeto estadoCuentaSNEVO en el servicio");
//        log.debug("[" + ToStringBuilder.reflectionToString(estadoCuentaSNE) + "]");
//
//        return estadoCuentaSNE;

        return null;
        
    }

//    /**
//     * @param elementoTraspasoEfectivo
//     * @return boolean
//     */
//    private boolean isDecreto(TraspasoEfectivo elementoTraspasoEfectivo) {
//        
//        log.info("Entrando a TesoreriaServiceImpl.isDecreto()");
//        
//        boolean isDecreto = StringUtils.isNotBlank(elementoTraspasoEfectivo.getTipoMovimiento())
//                && Constantes.DECRETOS.equalsIgnoreCase(
//                		elementoTraspasoEfectivo.getTipoMovimiento().trim())
//                && StringUtils.isNotBlank(elementoTraspasoEfectivo.getOrigenAplicacion())
//	           && (Constantes.INTEMIS.equalsIgnoreCase(
//	        		   elementoTraspasoEfectivo.getOrigenAplicacion().trim())
//                        || Constantes.VENCEMIS.equalsIgnoreCase(
//                        		elementoTraspasoEfectivo.getOrigenAplicacion().trim())
//                        || Constantes.EJDPB.equalsIgnoreCase(
//                        		elementoTraspasoEfectivo.getOrigenAplicacion().trim())
//                        || Constantes.EJDPG.equalsIgnoreCase(
//                        		elementoTraspasoEfectivo.getOrigenAplicacion().trim())
//                        || Constantes.EJDMC.equalsIgnoreCase(
//                        		elementoTraspasoEfectivo.getOrigenAplicacion().trim())
//                        || Constantes.LIQEJDMC.equalsIgnoreCase(
//                        		elementoTraspasoEfectivo.getOrigenAplicacion().trim())
//                );
//        log.debug("Tipo Movimiento = [" + elementoTraspasoEfectivo.getTipoMovimiento() + "]");
//        log.debug("Origen Aplicacion = [" + elementoTraspasoEfectivo.getOrigenAplicacion() + "]");
//        
//        return isDecreto;
//        
//    }

//    /**
//     * @param elementoTraspasoEfectivo
//     * @param params
//     * @return boolean
//     */
//    private boolean validaClaveValor(TraspasoEfectivo elementoTraspasoEfectivo,
//            GetEdoCtaSNEParams params) {
//    	
//        log.info("Entrando a TesoreriaServiceImpl.validaClaveValor()");
//        
//        EmisionVO emisionVO =
//        	new EmisionVO();
//        emisionVO.setIdTipoValor(elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk()
//                .getTv());
//        emisionVO.setEmisora(elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk()
//                .getEmisora());
//        emisionVO.setSerie(elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk()
//                .getSerie());
//        emisionVO.setCupon(elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk()
//                .getCupon());
//        return params.getEmision().equals(emisionVO);
//    }

//    /**
//     * @param elementoTraspasoEfectivo
//     * @return TraspasoEfectivo
//     * @throws BusinessException
//     */
//    private TraspasoEfectivo recuperaClaveValorDecreto(TraspasoEfectivo elementoTraspasoEfectivo)
//            throws BusinessException {
//    	
//        log.info("Entrando a TesoreriaServiceImpl.recuperaClaveValorDecreto()");
//        
//        try {
//        	String origen = elementoTraspasoEfectivo.getOrigen();
//        	BigInteger folio = new BigInteger(elementoTraspasoEfectivo.getFolioOrigen().trim());
//        	DecretosFija decreto  = 
//        		(DecretosFija) decretosFijaDao.getByPk(DecretosFija.class, folio);
//        	
//        	String mercado = elementoTraspasoEfectivo.getMercado();
//        	
//        	if(StringUtils.isNotBlank(mercado)){
//        		mercado = mercado.trim();
//        	}
//        	
//        	log.info("EL MERCADO ES: " + mercado);
//        	if((Constantes.ORIGEN_SIDV.equals(origen) || 
//        			Constantes.ORIGEN_EJERCICIO_DERECHOS.equals(origen)) && 
//        			(Constantes.PAPEL_BANCARIO.equals(mercado) || 
//        					Constantes.PAPEL_GUBERNAMENTAL.equals(mercado))){
//            	elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setTv(
//                        decreto.getTv());
//                elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setEmisora(
//                        decreto.getEmisora());
//                elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setSerie(
//                        decreto.getSerie());
//                elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setCupon(
//                        decreto.getCupon());
//        	}
//        	else{
//        		log.info("El decreto en decretoFija fue nulo");
//            	Integer folioVariable = new Integer(folio.intValue());
//                EmisionPersistence emisionDecretosVariable = decretosVariableDao.getEmisionSNE(folioVariable);
//
//                if(emisionDecretosVariable !=null && emisionDecretosVariable.getEmisionPk() !=null){
//                	elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setTv(
//                			emisionDecretosVariable.getEmisionPk().getTv());
//                	elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setEmisora(
//                			emisionDecretosVariable.getEmisionPk().getEmisora());
//                	elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setSerie(
//                			emisionDecretosVariable.getEmisionPk().getSerie());
//                	elementoTraspasoEfectivo.getTraspaso().getEmision().getEmisionPk().setCupon(
//                			emisionDecretosVariable.getEmisionPk().getCupon());
//                }
//        	}
//        }
//        catch (NumberFormatException numberFormatException) {
//            log.debug("El Folio Origen no era numero");
//        }
//        return elementoTraspasoEfectivo;
//    }

    /**
     * @throws BusinessException
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService
     *      #getSaldo3Cuentas(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
     */
    public Saldo3CuentasVO getSaldo3Cuentas(AgenteVO agenteVO) throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.getSaldo3Cuentas()");

//        utilService.validaAgente(agenteVO, false);
//
//        // Se obtiene la lista de SaldosEfectivo a traves del Dao
//        List listSaldoNombrada = 
//        	saldoNombradaDaliDao.getSaldoNombrada(agenteVO.getId(), agenteVO.getFolio());
//        
//        return UtilsVOTesoreria.getInstanceSaldo3CuentasVO(
//        		(listSaldoNombrada!=null && !listSaldoNombrada.isEmpty()) ? 
//        				(SaldoNombrada) listSaldoNombrada.get(0) : null);
        
        return null;

    }

    /**
     * @throws BusinessException
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService
     *      #getTraspFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     *      java.lang.String,
     *      com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getTraspFondos(AgenteVO agenteVO, String idTipoOperacion, PaginaVO pagina)
            throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.getTraspFondos()");

//        /* Se validan los parametros recibidos */
//        utilService.validaAgente(agenteVO, false);
//        
//        validaTipoOperacion(idTipoOperacion);
//
//        pagina = UtilsDaliVO.getPaginaNotBlank(pagina);
//
//        List listaTraspasosFondos = traspasosDao.getTraspFondos(UtilsDaliVO.getAgentePK(agenteVO),
//                idTipoOperacion);
//
//        if (listaTraspasosFondos == null || listaTraspasosFondos.isEmpty()) {
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//
//        pagina.extraerSublist(listaTraspasosFondos);
//        List elementosToVO = pagina.getRegistros();
//
//        List list = new ArrayList();
//        for (Iterator iter = elementosToVO.iterator(); iter.hasNext();) {
//            TraspasoVOPersistence element = (TraspasoVOPersistence) iter.next();
//            TraspasoVO traspasoVO = new TraspasoVO();
//            traspasoVO.setFechaCaptura(element.getFechaCaptura());
//            traspasoVO.setFolioCapt(element.getFolioCapt());
//            traspasoVO.setFolioConfirmacion(element.getFolioConfirmacion());
//            traspasoVO.setFolioLiq(element.getFolioLiq());
//            traspasoVO.setHoraConfirmacion(element.getHoraConfirmacion());
//            traspasoVO.setHoraInsercion(element.getHoraInsercion());
//            traspasoVO.setImporte(element.getImporte());
//            traspasoVO.setTipoOperacion(element.getTipoOperacion());
//
//            AgenteVO receptor = UtilsDaliVO.getAgenteVO(element.getReceptor());
//            AgenteVO traspasante = UtilsDaliVO.getAgenteVO(element.getTraspasante());
//            EstatusVO estatus = new EstatusVO();
//            estatus.setDescEstatus(element.getEstatus().getDescEstatus());
//            traspasoVO.setReceptor(receptor);
//            traspasoVO.setTraspasante(traspasante);
//            traspasoVO.setEstatus(estatus);
//
//            list.add(traspasoVO);
//
//        }
//
//        log.debug("La lista de traspaso contiene " + listaTraspasosFondos.size() + " elementos");
//        pagina.setRegistros(list);
//        log.debug("La pagina contiene " + pagina.getRegistros().size() + " elementos");
//        pagina = UtilsDaliVO.getPaginaNotBlank(pagina);
//
//        return pagina;

        return null;
        
    }

    /**
     * @throws BusinessException
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService
     *      #retirarFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     *      java.lang.String, java.math.BigDecimal)
     */
    public Integer retirarFondos(AgenteVO agenteVO, String idTipoRetiro, BigDecimal importe)
            throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.retirarFondos()");

//        /* Se validan la fase y los parametros recibidos */
//        utilService.validaFase(fasesAbiertas);
//        utilService.validaAgente(agenteVO, false);
//        utilService.validaDTONoNulo(importe, " importe ");
//        
//        if (StringUtils.isBlank(idTipoRetiro)) {
//            throw new BusinessException(errorResolver.getMessage("J0019", new Object[] {
//                "ID Tipo Retiro" }));
//        }
//
//        /* Se valida si CPLEX esta activo */
//        if (utilService.isCplexActive()) {
//            throw new BusinessException(errorResolver.getMessage("J0021", new Object[] {
//                "No se puede retirar fondos" }));
//        }
//
//        /* Se valida si el area de trabajo esta cerrada y el horario del SPEI */
//        this.permitirRetirosFondos(agenteVO);
//        this.validaRetiroEnHorarioSpei(idTipoRetiro);
//
//        Map resultado = storedProceduresDao.retirarFondos(UtilsDaliVO.getAgentePK(agenteVO),
//                idTipoRetiro, importe);
//
//        if (resultado == null) {
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//        // Se imprime en el log los valores del mapa retornado por el SP
//        // UtilsLog.logClaveValor(resultado);
//
//        // Se recupera el status
//        Integer status = (Integer) resultado.get(Constantes.STATUS);
//        log.debug("El status es: [" + status.intValue() + "]");
//
//        if (status.intValue() < 0) {
//            throw new BusinessDataException(errorResolver.getMessage(status.toString()), status
//                    .toString());
//        }
//
//        // Se recupera la lista de error
//        String error = null;
//        List listaRetiros = (List) resultado.get(Constantes.ERROR);
//        if (listaRetiros != null
//                && ((String) listaRetiros.get(0) != null && !(((String) listaRetiros.get(0)).trim()
//                        .equalsIgnoreCase(Constantes.REEFSPEI) || ((String) listaRetiros.get(0))
//                        .trim().equalsIgnoreCase(Constantes.REEFSIAC)))) {
//            error = ((String) listaRetiros.get(0)).trim();
//            throw new BusinessDataException(errorResolver.getMessage(error), error);
//        }
//
//        listaRetiros = (List) resultado.get("folioControl");
//        Integer folioControl = (Integer) listaRetiros.get(0);
//
//        // Se verifica el folio control
//        if (folioControl.intValue() < 0) {
//            throw new BusinessDataException(errorResolver.getMessage(folioControl.toString()),
//                    folioControl.toString());
//        }
//
//        return folioControl;

        return null;
        
    }

    /**
     * @throws BusinessException
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService
     *      #traspasarEntreCuentas(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     *      com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     *      java.math.BigDecimal)
     */
    public Integer traspasarEntreCuentas(AgenteVO traspasante, AgenteVO receptor, BigDecimal importe)
            throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.traspasarEntreCuentas()");

//        AgentePK traspasantePK = null;
//        AgentePK receptorPK = null;
//
//        /* Validaciones */
//        utilService.validaAgente(traspasante, " traspasante ", false);
//        traspasantePK = UtilsDaliVO.getAgentePK(traspasante);
//        utilService.validaAgente(receptor, " receptor ", false);
//        receptorPK = UtilsDaliVO.getAgentePK(receptor);
//        utilService.validaDTONoNulo(importe, " importe ");
//        
//        if (utilService.isCplexActive()) {
//
//            if (traspasante.equals(receptor)) {
//                // Solo se permite traspaso de la misma institucion de la cuenta
//                // concentradora a la de mercado de dinero.
//                if (!(traspasante.getCuenta().equals(Constantes.CUENTA_CONCENTRADORA) && receptor
//                        .getCuenta().equals(Constantes.LIQUIDADORA_DINERO))) {
//                    throw new BusinessException(
//                            errorResolver.getMessage("J0021", 
//                            		new Object[] {"Solo se permite traspaso de la Cuenta " +
//                            				"Concentradora (CC) a la Cuenta Liquidadora " +
//                            				"de Mercado de Dinero (MD) de la misma Institucion" }));
//                }
//            }
//            // No permitir traspasos a terceros.
//            else {
//                throw new BusinessException(errorResolver.getMessage("J0021", new Object[] {
//                    "No se permite traspaso a terceros" }), "J0021");
//            }
//        }
//
//        this.permitirRetirosFondos(traspasante);
//
//        log.debug("Llamando a executaTraspaso()");
//        TraspasoRespuestaVo resultado = storedProceduresDao.executaTraspaso(traspasantePK,
//                receptorPK, importe, traspasante.getNombreCorto());
//        log.debug("Regresando a executaTraspaso()");
//
//        if (resultado != null && resultado.getStatus().intValue() != Constantes.CERO_INT) {
//            log.debug("ERROR: " + resultado.getStatus().toString());
//            throw new BusinessDataException(errorResolver.getMessage(resultado.getStatus()
//                    .toString()), resultado.getStatus().toString());
//        }
//
//        return resultado.getFolio();
        
        return null;
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#permitirRetirosFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
     */
    public void permitirRetirosFondos(AgenteVO agente) throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.permitirRetirosFondos()");

//        boolean result = false;
//        AgentePK agentePK = UtilsDaliVO.getAgentePK(agente);
//
//        String permiso = this.getAreasTrabajoDao().getPermisoByAgenteAndNombreCorto(agentePK,
//                Constantes.TESOR, Constantes.TESORERIA);
//
//        if (permiso != null) {
//            if (permiso.equalsIgnoreCase(Constantes.ERROR_AREAS_TRABAJO)) {
//                throw new BusinessException(errorResolver.getMessage("J0000", new Object[] {
//                    "La institucion no esta dada de alta como area de trabajo" }), "J0000");
//            }
//
//            result = !"C".equals(permiso.trim().toUpperCase())
//                    && !"N".equals(permiso.trim().toUpperCase());
//
//            log.debug("permiso: [" + permiso + "]");
//        }
//
//        if (!result) {
//            throw new BusinessException(errorResolver.getMessage("J0000", new Object[] {
//                "El area de trabajo esta cerrada o en consulta" }), "J0000");
//        }
//
    }

//    /**
//     * @param idTipoRetiro
//     * @throws BusinessException
//     */
//    private void validaRetiroEnHorarioSpei(String idTipoRetiro) throws BusinessException {
//
//        log.info("Entrando a TesoreriaServiceImpl.validaRetiroEnHorarioSpei()");
//
//        // Si se esta realizando un tipo de retiro de SPEI, se debe validar el
//        // horario
//        if (idTipoRetiro.equals(Constantes.RETIROS_SPEI)) {
//            Date fechaBaseDatos = dateUtilsDao.getDateFechaDB();
//            Date fechaDeBaseDeDatosConHoraLimiteDeCierreSpei = 
//                this.getFechaDeBaseDeDatosConHoraDeCierreSpei(fechaBaseDatos);
//
//            if (fechaBaseDatos.after(fechaDeBaseDeDatosConHoraLimiteDeCierreSpei)) {
//                throw new BusinessException(
//                        errorResolver.getMessage("J0000",new Object[] {"No es posible realizar " +
//                                "retiros hacia Spei fuera del horario de operacion " +
//                                "de Spei" }), "J0000");
//            }
//        }
//    }

//    /**
//     * @param fechaBaseDatos
//     * @return Date
//     */
//    private Date getFechaDeBaseDeDatosConHoraDeCierreSpei(Date fechaBaseDatos) {
//
//        log.info("Entrando a TesoreriaServiceImpl.getFechaDeBaseDeDatosConHoraDeCierreSpei()");
//
//        String horaLimite = this.getContejpoDao().getHoraCierreDeSpei();
//
//        String hora = horaLimite.substring(Constantes.CERO_INT, Constantes.DOS_INT);
//        log.debug("[" + hora + "]");
//
//        String minutos = horaLimite.substring(Constantes.TRES_INT, Constantes.CINCO_INT);
//        log.debug("[" + minutos + "]");
//
//        String segundos = horaLimite.substring(Constantes.SEIS_INT, Constantes.OCHO_INT);
//        log.debug("[" + segundos + "]");
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(fechaBaseDatos);
//
//        int intHora = Integer.parseInt(hora);
//        c.set(Calendar.HOUR_OF_DAY, intHora);
//
//        int intMinutos = Integer.parseInt(minutos);
//        c.set(Calendar.MINUTE, intMinutos);
//
//        int intSegundos = Integer.parseInt(segundos);
//        c.set(Calendar.SECOND, intSegundos);
//
//        return c.getTime();
//    }

//    /**
//     * Valida que el tipo de operacion que recibe el metodo getTraspFondos se
//     * diferente de NULL, no este vacio y contenga un valor valido.
//     *
//     * @param idTipoOperacion
//     * @throws BusinessException
//     */
//    private void validaTipoOperacion(String idTipoOperacion) throws BusinessException {
//
//        log.info("Entrando a TesoreriaServiceImpl.validaTipoOperacion()");
//
//        if (StringUtils.isBlank(idTipoOperacion)) {
//            throw new BusinessException(errorResolver.getMessage("J0019", new Object[] {
//                "ID Tipo Operacion" }));
//        }
//        if (!idTipoOperacion.startsWith(Constantes.DOS_STRING)) {
//            throw new BusinessException(errorResolver.getMessage("J0006", new Object[] {
//                "ID Tipo Operacion" }));
//        }
//
//        if (!idTipoOperacion.equalsIgnoreCase(Constantes.RETIROS_SIAC)
//                && !idTipoOperacion.equalsIgnoreCase(Constantes.RETIROS_SPEI)
//                && !idTipoOperacion.equalsIgnoreCase(Constantes.RETIROS_AMBOS)
//                && !idTipoOperacion.equalsIgnoreCase(Constantes.DEPOSITOS_SIAC)
//                && !idTipoOperacion.equalsIgnoreCase(Constantes.DEPOSITOS_LIBERACION_DE_GARANTIAS)
//                && !idTipoOperacion.equalsIgnoreCase(Constantes.DEPOSITOS_SPEI)
//                && !idTipoOperacion.equalsIgnoreCase(DEPOSITOS_TODOS)) {
//            throw new BusinessException(errorResolver.getMessage("J0019", new Object[] {
//                "ID Tipo de Operacion" }));
//        }
//    }

//    /**
//     * Determina el rol del agente firmado en la operacion de traspaso entre las
//     * cuentas de efectivo Retorna -1 si el agente esta pagando, 1 si el agente
//     * esta cobrando o cero si la operacion es entre cuentas del mismo agente.
//     *
//     * @param elementoTraspasoEfectivo
//     * @param params
//     * @return int
//     * @throws BusinessException 
//     */
//    private int determinaRol(TraspasoEfectivo elementoTraspasoEfectivo, GetEdoCtaSNEParams params) 
//    		throws BusinessException {
//
//        log.info("Entrando a TesoreriaServiceImpl.determinaRol()");
//
//        int rol = 0;
//        int entrada = 0;
//        int salida = 0;
//
//        try {
//			Assert.notNull(params);
//			Assert.notNull(elementoTraspasoEfectivo);
//			Assert.notNull(elementoTraspasoEfectivo.getInstitucionTraspasante());
//			Assert.notNull(elementoTraspasoEfectivo.getInstitucionTraspasante());
//			Assert.notNull(elementoTraspasoEfectivo.getInstitucionReceptora());
//			Assert.notNull(elementoTraspasoEfectivo.getInstitucionReceptora());
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			throw new BusinessException(e.getMessage(), e);
//		}
//
//        boolean esValido = false;
//        if ((StringUtils.isNotBlank(elementoTraspasoEfectivo.getInstitucionTraspasante()
//                .getInstitucionPK().getIdInst()) && elementoTraspasoEfectivo
//                .getInstitucionTraspasante().getInstitucionPK().getIdInst().equalsIgnoreCase(
//                        params.getAgente().getId()))
//                && (StringUtils.isNotBlank(elementoTraspasoEfectivo.getInstitucionTraspasante()
//                        .getInstitucionPK().getFolioInst()) && elementoTraspasoEfectivo
//                        .getInstitucionTraspasante().getInstitucionPK().getFolioInst()
//                        .equalsIgnoreCase(params.getAgente().getFolio()))) {
//            log.debug("El agente esta pagando");
//            log.debug("la operacion se efectua entre el TRASPASANTE ["
//                    + elementoTraspasoEfectivo.getInstitucionTraspasante().getInstitucionPK()
//                            .getIdInst()
//                    + elementoTraspasoEfectivo.getInstitucionTraspasante().getInstitucionPK()
//                            .getFolioInst()
//                    + "] y el RECEPTOR ["
//                    + elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK()
//                            .getIdInst()
//                    + elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK()
//                            .getFolioInst() + "]");
//            salida = -1;
//            esValido = true;
//        }
//        if ((StringUtils.isNotBlank(elementoTraspasoEfectivo.getInstitucionReceptora()
//                .getInstitucionPK().getIdInst()) && elementoTraspasoEfectivo
//                .getInstitucionReceptora().getInstitucionPK().getIdInst().equalsIgnoreCase(
//                        params.getAgente().getId()))
//                && (StringUtils.isNotBlank(elementoTraspasoEfectivo.getInstitucionReceptora()
//                        .getInstitucionPK().getFolioInst()) && elementoTraspasoEfectivo
//                        .getInstitucionReceptora().getInstitucionPK().getFolioInst()
//                        .equalsIgnoreCase(params.getAgente().getFolio()))) {
//            log.debug("El agente esta cobrando");
//            log.debug("la operacion se efectua entre el TRASPASANTE ["
//                    + elementoTraspasoEfectivo.getInstitucionTraspasante().getInstitucionPK()
//                            .getIdInst()
//                    + elementoTraspasoEfectivo.getInstitucionTraspasante().getInstitucionPK()
//                            .getFolioInst()
//                    + "] y el RECEPTOR ["
//                    + elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK()
//                            .getIdInst()
//                    + elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK()
//                            .getFolioInst() + "]");
//            entrada = 1;
//            esValido = true;
//        }
//        if (!esValido) {
//            throw new InvalidDataAccessResourceUsageException(
//                    "Se ha accedido a un registro restringido,"
//                            + "la operacion se efectua entre el TRASPASANTE ["
//                            + elementoTraspasoEfectivo.getInstitucionTraspasante()
//                                    .getInstitucionPK().getIdInst()
//                            + elementoTraspasoEfectivo.getInstitucionTraspasante()
//                                    .getInstitucionPK().getFolioInst()
//                            + "] y el RECEPTOR ["
//                            + elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK()
//                                    .getIdInst()
//                            + elementoTraspasoEfectivo.getInstitucionReceptora().getInstitucionPK()
//                                    .getFolioInst() + "]");
//        }
//        rol = entrada + salida;
//        return rol;
//
//    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getInstituciones(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams)
     */
    @SuppressWarnings("unchecked")
	public List getInstituciones(LiquidacionDecretosParams params) throws BusinessException {
        
        logger.info("Entrando a TesoreriaServiceImpl.getInstituciones()");
        
//        /* Se verifican los parametros */
//        utilService.validaDTONoNulo(params, "");
//        utilService.validaDTONoNulo(params.getTipoConsulta(), " tipo de consulta ");
//        
//        if (params.getTipoConsulta().equalsIgnoreCase(COBROS)) {
//            try {
//				params.validaIdTipoMoneda();
//			} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
//				e.printStackTrace();
//			} //El Tipo de moneda es obligatorio para cobros
//        }
//        else if (!params.getTipoConsulta().equalsIgnoreCase(PAGOS)) {
//            throw new BusinessException("El objeto params tiene un valor invalido " +
//                    "en el tipo de consulta");
//        }
//
////        String tipoEjercicio = TODOS;  //Se verifica el tipo de ejercicio
////        if (StringUtils.isNotBlank(params.getTipoEjercicio())) {
////            tipoEjercicio = params.getTipoEjercicio();
////        }
//
//        /* Se crean los objetos Date y se verifican fechaIni y fechaFin */
//        Date desde = null;
//        if (params.getFechaIni() != null) {
//            desde = dateUtilsDao.getFechaHoraCero(params.getFechaIni());
//            params.setFechaIni(desde);
//        }
//        else {
//            throw new BusinessException("El objeto de parametro no tiene setteado " +
//                    "el parametro requerido fecha de inicio");
//        }
//        Date hasta = null;
//        if (params.getFechaFin() != null) {
//            hasta = dateUtilsDao.getFechaHoraFinDia(params.getFechaFin());
//            params.setFechaFin(hasta);
//        }
//        else {
//            throw new BusinessException("El objeto de parametro no tiene setteado " +
//            "el parametro requerido fecha fin");
//        }
//
//        if (params.getTipoMoneda() == null) {
//            if (params.getTipoConsulta().equalsIgnoreCase(PAGOS)) {
//                params.setTipoMoneda(PESOS);
//        }
//        else {
//                throw new BusinessException("Falta el tipo de moneda");
//            }
//        }
//
//        EmisionPersistence emisionExample = new EmisionPersistence();
//        EmisionPK emisionPKExample = new EmisionPK();
//        emisionExample.setEmisionPk(emisionPKExample);
//
//        emisionExample.setDivisa(params.getTipoMoneda()); //Si esPago siempre es PESOS
//
//        if (params.getEmision() != null) {
//
//            if (StringUtils.isNotBlank(params.getEmision().getIdTipoValor())) {
//                emisionExample.getEmisionPk().setTv(params.getEmision().getIdTipoValor());
//            }
//            if (StringUtils.isNotBlank(params.getEmision().getEmisora())) {
//                emisionExample.getEmisionPk().setEmisora(params.getEmision().getEmisora());
//            }
//            if (StringUtils.isNotBlank(params.getEmision().getSerie())) {
//                emisionExample.getEmisionPk().setSerie(params.getEmision().getSerie());
//            }
//            if (StringUtils.isNotBlank(params.getEmision().getCupon())) {
//                emisionExample.getEmisionPk().setCupon(params.getEmision().getCupon());
//            }
//
//        }
//
//        /* Se obtienen las instituciones */
//        Map mapaInstituciones = new HashMap();
//
//        if (params.getAgente() == null || StringUtils.isBlank(params.getAgente().getId())
//                || StringUtils.isBlank(params.getAgente().getFolio())) {
//            /* Obtiene las instituciones del portal */
//            //List listaInstitucionesPortal = new ArrayList();
//
//            /* Obtiene las instituciones de ejercicio de derechos */
//            if (StringUtils.isBlank(params.getTipoEjercicio()) ||
//                    params.getTipoEjercicio().equalsIgnoreCase(TODOS)) {
//                params.setTipoEjercicio(null);
//            }
//            if (params.getTipoMoneda().equalsIgnoreCase(PESOS)) {
//                params.setTipoMoneda(MXN);
//            }
//            else if (params.getTipoMoneda().equalsIgnoreCase(DOLARES)) {
//                params.setTipoMoneda(USD);
//            }
//            else if (params.getTipoMoneda().equalsIgnoreCase(UDIS)) {
//                params.setTipoMoneda(MXV);
//            }
//            List listaIntitucionesEjerDer = decretosEjercicioDerechos.getInstituciones(params);
//            if (listaIntitucionesEjerDer != null && !listaIntitucionesEjerDer.isEmpty()) {
//                for (Iterator iter = listaIntitucionesEjerDer.iterator(); iter.hasNext();) {
//                    com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agenteVO =
//                    	(com.indeval.sidv.decretos.persistence.model.vo.AgenteVO) iter.next();
//                    if (!mapaInstituciones.containsKey(agenteVO)) {
//                        mapaInstituciones.put(agenteVO, agenteVO);
//                    }
//                }
//            }
//        }
//        else {
//            mapaInstituciones.put(params.getAgente(), params.getAgente());
//        }
//
//        if (mapaInstituciones.isEmpty()) {
//            throw new BusinessException("No existen instituciones para los criterios de busqueda seleccionados");
//        }
//
//        List listaInstitucionesTemp = new ArrayList();
//        listaInstitucionesTemp.addAll(mapaInstituciones.values());
//        Map mapaInstitucionesOrdenado = sortInstituciones(listaInstitucionesTemp);
//        if (mapaInstitucionesOrdenado == null || mapaInstitucionesOrdenado.isEmpty()) {
//            throw new BusinessException("Error al realizar el ordenamiento de las instituciones");
//        }
//        List listaInstituciones = new ArrayList();
//        listaInstituciones.addAll(mapaInstitucionesOrdenado.values());
//
//        return this.institucionesSidv2InstitucionesPortal(listaInstituciones);
        
        return null;
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getTiposDerecho(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams)
     */
    public List getTiposDerecho(
            com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams params) 
                throws BusinessException {
//    	
//    	log.info("Entrando a TesoreriaServiceImpl.getTiposDerecho()");
//        
//        /* Se verifican los parametros */
//        utilService.validaDTONoNulo(params, "");
//        utilService.validaDTONoNulo(params.getTipoConsulta(), " tipo de consulta ");
//        
//        if (params.getTipoConsulta().equalsIgnoreCase(COBROS)) {
//            try {
//				params.validaIdTipoMoneda();
//			} 
//            catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
//				e.printStackTrace();
//			} //El Tipo de moneda es obligatorio para cobros
//        }
//        else if (!params.getTipoConsulta().equalsIgnoreCase(PAGOS)) {
//            throw new BusinessException("El objeto params tiene un valor invalido " +
//                    "en el tipo de consulta");
//        }
//
////        String tipoEjercicio = TODOS;  //Se verifica el tipo de ejercicio
////        if (StringUtils.isNotBlank(params.getTipoEjercicio())) {
////            tipoEjercicio = params.getTipoEjercicio();
////        }
//
//        /* Se crean los objetos Date y se verifican fechaIni y fechaFin */
//        Date desde = null;
//        if (params.getFechaIni() != null) {
//            desde = dateUtilsDao.getFechaHoraCero(params.getFechaIni());
//            params.setFechaIni(desde);
//        }
//        else {
//            throw new BusinessException("El objeto de parametro no tiene setteado " +
//                    "el parametro requerido fecha de inicio");
//        }
//        Date hasta = null;
//        if (params.getFechaFin() != null) {
//            hasta = dateUtilsDao.getFechaHoraFinDia(params.getFechaFin());
//            params.setFechaFin(hasta);
//        }
//        else {
//            throw new BusinessException("El objeto de parametro no tiene setteado " +
//            "el parametro requerido fecha fin");
//        }
//
//        if (params.getTipoMoneda() == null) {
//            if (params.getTipoConsulta().equalsIgnoreCase(PAGOS)) {
//                params.setTipoMoneda(PESOS);
//            }
//            else {
//                throw new BusinessException("Falta el tipo de moneda");
//            }
//        }
//
//        EmisionPersistence emisionExample = new EmisionPersistence();
//        EmisionPK emisionPKExample = new EmisionPK();
//        emisionExample.setEmisionPk(emisionPKExample);
//
//        emisionExample.setDivisa(params.getTipoMoneda()); //Si esPago siempre es PESOS
//
//        if (params.getEmision() != null) {
//
//            if (StringUtils.isNotBlank(params.getEmision().getIdTipoValor())) {
//                emisionExample.getEmisionPk().setTv(params.getEmision().getIdTipoValor());
//            }
//            if (StringUtils.isNotBlank(params.getEmision().getEmisora())) {
//                emisionExample.getEmisionPk().setEmisora(params.getEmision().getEmisora());
//            }
//            if (StringUtils.isNotBlank(params.getEmision().getSerie())) {
//                emisionExample.getEmisionPk().setSerie(params.getEmision().getSerie());
//            }
//            if (StringUtils.isNotBlank(params.getEmision().getCupon())) {
//                emisionExample.getEmisionPk().setCupon(params.getEmision().getCupon());
//            }
//
//        }
//
//        /* Se obtienen los tipos de derecho (Tipos de Ejercicio) */
//        Map mapaTiposDerecho = new HashMap();
//
//        if (StringUtils.isBlank(params.getTipoEjercicio())) {
//
//            /* Obtiene la lista de ejercicio de derechos */
//            if (StringUtils.isBlank(params.getTipoEjercicio()) ||
//                    params.getTipoEjercicio().equalsIgnoreCase(TODOS)) {
//                params.setTipoEjercicio(null);
//            }
//            if (params.getTipoMoneda().equalsIgnoreCase(PESOS)) {
//                params.setTipoMoneda(MXN);
//            }
//            else if (params.getTipoMoneda().equalsIgnoreCase(DOLARES)) {
//                params.setTipoMoneda(USD);
//            }
//            else if (params.getTipoMoneda().equalsIgnoreCase(UDIS)) {
//                params.setTipoMoneda(MXV);
//            }
//
//            List tiposDerechoEjerDer = decretosEjercicioDerechos.getTiposDerecho(params);
//            if (tiposDerechoEjerDer != null && !tiposDerechoEjerDer.isEmpty()) {
//                for (Iterator iter = tiposDerechoEjerDer.iterator(); iter.hasNext();) {
//                    String tipoEjerDer = (String) iter.next();
//                    if (!mapaTiposDerecho.containsKey(tipoEjerDer)) {
//                        mapaTiposDerecho.put(tipoEjerDer, tipoEjerDer);
//                    }
//                }
//            }
//        }
//        else {
//            mapaTiposDerecho.put(params.getTipoEjercicio(), params.getTipoEjercicio());
//        }
//
//        if (mapaTiposDerecho.isEmpty()) {
//            throw new BusinessException(
//                    "No existen tipos de ejercicio para los criterios de busqueda seleccionados");
//        }
//
//        List listaTiposDerecho = new ArrayList();
//        listaTiposDerecho.addAll(mapaTiposDerecho.values());
//
//        return listaTiposDerecho;
          return null;
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretos(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams)
     */
    public List getLiquidacionDecretos(LiquidacionDecretosParams params) 
                throws BusinessException {
    	
        logger.info("Entrando a TesoreriaServiceImpl.getLiquidacionDecretos()");
        
    	return null;
       
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretosDetalle(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleParams)
     */
    public com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO 
    		getLiquidacionDecretosDetalle(LiquidacionDecretosDetalleParams paramsDetalle) 
    			throws BusinessException {
    	
    	logger.info("Entrando a TesoreriaServiceImpl.getLiquidacionDecretosDetalle()");
    	
//    	utilService.validaDTONoNulo(paramsDetalle, "");
//    	this.validaAgente(paramsDetalle.getAgente());
//    	
//        ProductoDineroPK productoDineroPK = new ProductoDineroPK();
//        productoDineroPK.setIdInst(paramsDetalle.getAgente().getId());
//        productoDineroPK.setFolioInst(paramsDetalle.getAgente().getFolio());
//        productoDineroPK.setCuenta(paramsDetalle.getAgente().getCuenta());
//
//        ProductoDinero productoDinero =
//                productoDineroDao.getProductoEmisionByPk(productoDineroPK);
//
//        com.indeval.sidv.decretos.persistence.model.vo.
//        	LiquidacionDecretosDetalleVO liqDecrectosDetalleVO = 
//        		new com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO();
//
//        if (productoDinero != null) {
//            liqDecrectosDetalleVO.setFechaLiquidacion(productoDinero.getFechaLiquidacion());
//            liqDecrectosDetalleVO.setImporteCobrar(
//                    productoDinero.getImporteLiq() != null ? 
//                    		productoDinero.getImporteLiq() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//            liqDecrectosDetalleVO.setImporteDecreto( productoDinero.getImporteLiq() != null ? 
//            		productoDinero.getImporteLiq().subtract(productoDinero.getInteres() != null ? 
//            				productoDinero.getInteres() : UtilsDaliVO.BIG_DECIMAL_ZERO) :
//                                UtilsDaliVO.BIG_DECIMAL_ZERO);
//            liqDecrectosDetalleVO.setCuenta(productoDinero.getProductoDineroPk().getCuenta());
//            liqDecrectosDetalleVO.setDescTipoEjercicio(UtilsDaliVO.BLANK);
//            liqDecrectosDetalleVO.setFolioInstitucion(
//            		productoDinero.getProductoDineroPk().getFolioInst());
//            liqDecrectosDetalleVO.setIdInstitucion(productoDinero.getProductoDineroPk().getIdInst());
//            liqDecrectosDetalleVO.setIntereses(productoDinero.getInteres() != null ? 
//            		productoDinero.getInteres() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//            liqDecrectosDetalleVO.setSaldoTitulos(productoDinero.getSaldoDisponible());
//
//            com.indeval.sidv.decretos.persistence.model.vo.EmisionVO emisionVOSidv =
//            	new com.indeval.sidv.decretos.persistence.model.vo.EmisionVO();
//
//            //EmisionVO emisionVO = UtilsDaliVO.getEmisionVO(productoDinero.getEmision());
//
//            liqDecrectosDetalleVO.setEmision(emisionVOSidv);
//            if (productoDinero.getEmision() != null) {
//                liqDecrectosDetalleVO.setDivisa(productoDinero.getEmision().getDivisa());
//                liqDecrectosDetalleVO.setTasaInteres(
//                		productoDinero.getEmision().getTasaInteres() != null ?
//                        productoDinero.getEmision().getTasaInteres() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//                liqDecrectosDetalleVO.setValorNominal(
//                		productoDinero.getEmision().getValorNominal() != null ?
//                        productoDinero.getEmision().getValorNominal() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//				liqDecrectosDetalleVO.setPlazo(productoDinero.getEmision().getDiasNat() != null ?
//						productoDinero.getEmision().getDiasNat() : UtilsDaliVO.INTEGER_ZERO);
//				liqDecrectosDetalleVO.setTasaDescuento(
//						productoDinero.getEmision().getTasaDescuento() != null ?
//						productoDinero.getEmision().getTasaDescuento() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//	            liqDecrectosDetalleVO.setTasaRendimiento(
//	            		productoDinero.getEmision().getTasaRendimiento() != null ?
//						productoDinero.getEmision().getTasaRendimiento() : UtilsDaliVO.BIG_DECIMAL_ZERO);
//				if (liqDecrectosDetalleVO.getTasaDescuento().compareTo(
//						UtilsDaliVO.BIG_DECIMAL_ZERO) > CERO_INT) {
//					liqDecrectosDetalleVO.setTasaRendimientoDescto(
//							liqDecrectosDetalleVO.getTasaDescuento());
//				}
//				else if (liqDecrectosDetalleVO.getTasaRendimiento().compareTo(
//						UtilsDaliVO.BIG_DECIMAL_ZERO) > CERO_INT) {
//					liqDecrectosDetalleVO.setTasaRendimientoDescto(
//							liqDecrectosDetalleVO.getTasaRendimiento());
//				}
//				else {
//					liqDecrectosDetalleVO.setTasaRendimientoDescto(UtilsDaliVO.BIG_DECIMAL_ZERO);
//				}
//            }
//            else {
//                liqDecrectosDetalleVO.setDivisa(UtilsDaliVO.BLANK);
//                liqDecrectosDetalleVO.setTasaInteres(UtilsDaliVO.BIG_DECIMAL_ZERO);
//                liqDecrectosDetalleVO.setValorNominal(UtilsDaliVO.BIG_DECIMAL_ZERO);
//				liqDecrectosDetalleVO.setPlazo(UtilsDaliVO.INTEGER_ZERO);
//				liqDecrectosDetalleVO.setTasaDescuento(UtilsDaliVO.BIG_DECIMAL_ZERO);
//	            liqDecrectosDetalleVO.setTasaRendimiento(UtilsDaliVO.BIG_DECIMAL_ZERO);
//				liqDecrectosDetalleVO.setTasaRendimientoDescto(UtilsDaliVO.BIG_DECIMAL_ZERO);
//            }
//        }
//
//        return liqDecrectosDetalleVO;
    	
    	return null;
    	
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretosDetalleAmortizaciones(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams)
     */
    public List getLiquidacionDecretosDetalleAmortizaciones(
    		LiquidacionDecretosDetalleAmortizacionesParams paramsDetalleAmortizaciones) 
    			throws BusinessException {

        logger.info("Entrando a TesoreriaServiceImpl.getLiquidacionDecretosDetalleAmortizaciones()");
        
//        utilService.validaDTONoNulo(paramsDetalleAmortizaciones, "");
//        
//        this.validaAgente(paramsDetalleAmortizaciones.getAgente());
//
//        if(StringUtils.isBlank(paramsDetalleAmortizaciones.getFolioFija())){
//            paramsDetalleAmortizaciones.setFolioFija(CERO_STRING);
//        }
//        if(StringUtils.isBlank(paramsDetalleAmortizaciones.getFolioVariable())){
//            paramsDetalleAmortizaciones.setFolioVariable(CERO_STRING);
//        }
//
//        DetalleAmortizacionesParams paramsDao = new DetalleAmortizacionesParams();
//
//        paramsDao.setIdInst(paramsDetalleAmortizaciones.getAgente().getId());
//        paramsDao.setFolioInst(paramsDetalleAmortizaciones.getAgente().getFolio());
//        paramsDao.setCuenta(paramsDetalleAmortizaciones.getAgente().getCuenta());
//
//        try {
//            paramsDao.setFolioFija(new Integer(paramsDetalleAmortizaciones.getFolioFija()));
//            paramsDao.setFolioVariable(new Integer(paramsDetalleAmortizaciones.getFolioVariable()));
//        }
//        catch(NumberFormatException e) {
//            throw new BusinessException("Error en el formato de: folio_variable o folio_fija" , e);
//        }
//
//        List listaDetalleAmortizacionParcial =
//                parcialidadesDao.getDetalleAmortizacionParcialidades(paramsDao);
//
//        return listaDetalleAmortizacionParcial;
        
        return null;
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getListaTiposEjercicio()
     */
    public List getListaTiposEjercicio() throws BusinessException {
    	
    	logger.info("Entrando al metodo getListaTiposEjercicio");
    	
//    	return decretosEjercicioDerechos.getListaTiposEjercicio();
    	return null;
    }

    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getListaDivisa()
     */
    public List getListaDivisa() throws BusinessException {
    	
        logger.info("Entrando al metodo getListaDivisa()");
        
//    	return decretosEjercicioDerechos.getListaDivisa();
        return null;
        
    }

//    /**
//     * 
//     * @param emisionSidv
//     * @return EmisionVO
//     */
//    private EmisionVO emisionSidv2EmisionVOPortal(
//    		com.indeval.sidv.decretos.persistence.model.vo.EmisionVO emisionSidv){
//    	
//		log.info("Entrando a TesoreriaServiceImpl.emisionSidv2EmisionVOPortal()");
//		
//    	EmisionVO result = new EmisionVO();
//
//    	if(emisionSidv != null){
//    		result.setAlta(emisionSidv.getAlta());
//    		result.setCupon(emisionSidv.getCupon());
//    		result.setCuponCortado(emisionSidv.getCuponCortado());
//    		result.setDiasVigentes(emisionSidv.getDiasVigentes());
//    		result.setEmisora(emisionSidv.getEmisora());
//    		result.setFechaVencimiento(emisionSidv.getFechaVencimiento());
//    		result.setIdTipoValor(emisionSidv.getIdTipoValor());
//    		result.setIsin(emisionSidv.getIsin());
//    		result.setMercado(emisionSidv.getMercado());
//    		result.setPrecioVector(emisionSidv.getPrecioVector());
//    		result.setSaldoDisponible(emisionSidv.getSaldoDisponible());
//    		result.setSaldoInicialDia(emisionSidv.getSaldoInicialDia());
//    		result.setSerie(emisionSidv.getSerie());
//    		result.setUltimoHecho(emisionSidv.getUltimoHecho());
//    		result.setValorNominal(emisionSidv.getValorNominal());
//    	}
//    	return result;
//    }
    
    /**
     * @param agenteSidv
     * @return AgenteVO
     */
    public static AgenteVO agenteVO2AgenteSidv(
            com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agenteSidv){
    	
    	logger.info("Entrando a TesoreriaServiceImpl.agenteVO2AgenteSidv()");
    	
    	AgenteVO result = new AgenteVO();
    	if(agenteSidv != null){
    		result.setId(agenteSidv.getId());
    		result.setFolio(agenteSidv.getFolio());
    		result.setCuenta(agenteSidv.getCuenta());
    		result.setNombreCorto(agenteSidv.getNombreCorto());
    		result.setNombreCuenta(agenteSidv.getNombreCuenta());
    		result.setRazon(agenteSidv.getRazon());
    		result.setTenencia(agenteSidv.getTenencia());
    		result.setTipo(agenteSidv.getTipo());
    	}
    	return result;
    }


    /**
     * @param emisionSidv
     * @return EmisionVO
     */
    public static EmisionVO emisionVO2EmisionSidv(
    		com.indeval.sidv.decretos.persistence.model.vo.EmisionVO emisionSidv){
    	
    	logger.info("Entrando a TesoreriaServiceImpl.emisionVO2EmisionSidv()");
    	
    	EmisionVO result = new EmisionVO();

    	if(emisionSidv != null){
    		result.setAlta(emisionSidv.getAlta());
    		result.setCupon(emisionSidv.getCupon());
    		result.setCuponCortado(emisionSidv.getCuponCortado());
    		result.setDiasVigentes(emisionSidv.getDiasVigentes());
    		result.setEmisora(emisionSidv.getEmisora());
    		result.setFechaVencimiento(emisionSidv.getFechaVencimiento());
    		result.setIdTipoValor(emisionSidv.getIdTipoValor());
    		result.setIsin(emisionSidv.getIsin());
    		result.setMercado(emisionSidv.getMercado());
    		result.setPrecioVector(emisionSidv.getPrecioVector());
    		result.setSaldoDisponible(emisionSidv.getSaldoDisponible());
    		result.setSaldoInicialDia(emisionSidv.getSaldoInicialDia());
    		result.setSerie(emisionSidv.getSerie());
    		result.setUltimoHecho(emisionSidv.getUltimoHecho());
    		result.setValorNominal(emisionSidv.getValorNominal());
    	}
    	return result;
    }

    /**
     * Convierte un objeto GetDetalleAmortizacionesParams a un objeto LiquidacionDecretosDetalleAmortizacionesParams
     * @param params
     * @return LiquidacionDecretosDetalleAmortizacionesParams
     */
    private LiquidacionDecretosDetalleAmortizacionesParams 
    	detalleAmortizacionesParams2LiquidacionDecretosDetalleAmortizacionesParams(
			GetDetalleAmortizacionesParams params) {
    	
    	logger.info("Entrando a TesoreriaServiceImpl." +
    			"detalleAmortizacionesParams2LiquidacionDecretosDetalleAmortizacionesParams()");
    	
		LiquidacionDecretosDetalleAmortizacionesParams result = 
			new LiquidacionDecretosDetalleAmortizacionesParams();

		if(params != null){
			com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agente = new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO();

			if(params.getAgente() != null){
				agente.setId(params.getAgente().getId());
				agente.setFolio(params.getAgente().getFolio());
				agente.setCuenta(params.getAgente().getCuenta());
			}
			result.setAgente(agente);
			result.setFolioFija(params.getFolioFija());
			result.setFolioVariable(params.getFolioVariable());
			result.setIdDerecho(params.getIdDerecho());
			result.setIdTipoDerecho(params.getIdTipoDerecho());
			result.setIdTipoEjercicio(params.getIdTipoEjercicio());
		}
		return result;
	}

//    /**
//     * Recibe una lista de agentes tanto del portal como de ejercicio de derechos y regresa una 
//     * lista de agentes solamente del portal, convirtiendo los de ejercicio de derechos a agentes 
//     * del portal
//     * @param instituciones Lista de agenteVO
//     * @return List
//     */
//    private List institucionesSidv2InstitucionesPortal(List instituciones){
//    	
//    	log.info("Entrando a TesoreriaServiceImpl.institucionesSidv2InstitucionesPortal()");
//    	
//    	List result = new ArrayList();
//    	
//    	if(instituciones != null && !instituciones.isEmpty()){
//    		for (Iterator iterator = instituciones.iterator(); iterator.hasNext();) {
//    			Object agente = iterator.next();
//				if(agente instanceof AgenteVO){
//					result.add(agente);
//				}else{
//					AgenteVO agenteVOPortal = new AgenteVO();
//					com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agenteSidv = 
//						(com.indeval.sidv.decretos.persistence.model.vo.AgenteVO) agente;
//					agenteVOPortal.setId(agenteSidv.getId());
//					agenteVOPortal.setFolio(agenteSidv.getFolio());
//					agenteVOPortal.setCuenta(agenteSidv.getCuenta());
//					agenteVOPortal.setNombreCorto(agenteSidv.getNombreCorto());
//					agenteVOPortal.setNombreCuenta(agenteSidv.getNombreCuenta());
//					agenteVOPortal.setRazon(agenteSidv.getRazon());
//					agenteVOPortal.setTenencia(agenteSidv.getTenencia());
//					agenteVOPortal.setTipo(agenteSidv.getTipo());
//					result.add(agenteVOPortal);
//				}
//			}
//    	}
//    	return result;
//    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDivisaDefault()
     */
    public String getDivisaDefault() throws BusinessException{
    	
    	logger.info("Entrando a TesoreriaServiceImpl.getDivisaDefault()");

//    	List divisas = this.getListaDivisa();
//    	for (Iterator iterator = divisas.iterator(); iterator.hasNext();) {
//			DivisaVO divisa = (DivisaVO) iterator.next();
//			if(divisa.getEsDefault()){
//				return divisa.getCveDivisa();
//			}
//		}
    	
		return null;
		
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#businessRulesRetirarFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String, java.math.BigDecimal)
     */
    public BigInteger businessRulesRetirarFondos(AgenteVO agenteVO, String idTipoRetiro, BigInteger idBoveda, BigDecimal importe) throws BusinessException {
    	logger.debug("Entrando a TesoreriaServiceImpl.businessRulesRetirarFondos()");

    	utilService.validaAgente(agenteVO, false);
        utilService.validarClaveTipo(idTipoRetiro, " ID Tipo Retiro ");
        utilService.validaDTONoNulo(importe, " importe ");

        // Obtiene la institución traspasante.
        InstitucionDTO institucion = institucionDaliDAO.buscarInstitucionPorClaveYFolio(agenteVO.getId()+agenteVO.getFolio());
    	logger.debug("Id institucion del Agente = [" + institucion.getId() + "]");

    	//Valida que el tipo de operación sea SPEI o SIAC.
        if(!(Constantes.RETIROS_SIAC.equalsIgnoreCase(idTipoRetiro) || Constantes.RETIROS_SPEI.equalsIgnoreCase(idTipoRetiro))){
        	throw new BusinessException(errorResolver.getMessage("JBR-310"), "JBR-310");
        }

        /*
         * Se agregan validaciones para el requerimiento R-IND-2014-010 y se elimina
         * la validación de que la institución sea depositante valida banxico debido a
         * que se elimina la tabla R_INST_CON_INST_REFERENCIADA.
         * 02/10/2014 Pablo Balderas.
         */
        
        // 1) Si el tipo de retiro es SPEI, se valida que la institución tenga clave SPEI.
        if(Constantes.RETIROS_SPEI.equalsIgnoreCase(idTipoRetiro) && StringUtils.isEmpty(institucion.getClaveSpei())) {
        	throw new BusinessException(errorResolver.getMessage("JBR-010"), "JBR-010");
        }
        // 2) Si el tipo de retiro es SIAC, se valida que la institución operere SIAC 
        else if(Constantes.RETIROS_SIAC.equalsIgnoreCase(idTipoRetiro) && !institucion.isOperaSiac()) {
        	throw new BusinessException(errorResolver.getMessage("JBR-011"), "JBR-011");
        }
        
        //validacion de importe por retirar.
        if(importe.compareTo(Constantes.CERO_BIG_DECIMAL) <= Constantes.CERO_INT){
        	throw new BusinessException(errorResolver.getMessage("J0010", new Object[] {"No se puede retirar fondos" }), "J0010");
        }
        
        //validacion de importe diferente de cero o negativo.
        SaldoNombrada saldoNombrada = this.getSaldoNombrada(agenteVO.getId(), agenteVO.getFolio(), idBoveda);
        if(saldoNombrada.getSaldoDisponible() != null && Constantes.CERO_BIG_DECIMAL.compareTo(saldoNombrada.getSaldoDisponible()) > 0) {
        	throw new BusinessException(errorResolver.getMessage("JBR-013"), "JBR-013");
        }

        BigInteger folioControl = utilService.getFolio(SECUENCIA_FOLIO_CONTROL);
    	logger.debug("El folio control = [" + folioControl + "]");

		return folioControl;
	}
    
    /**
     * Recupera un SaldoNombrada para el agente y boveda recibido
     * @param id
     * @param folio
     * @param idBoveda
     * @return SaldoNombrada
     */
    @SuppressWarnings("unchecked")
	private SaldoNombrada getSaldoNombrada(String id, String folio, BigInteger idBoveda) {
        List<SaldoNombrada> listaSaldoNombrada = saldoNombradaDaliDao.getSaldoNombrada(id, folio, idBoveda);
        if (listaSaldoNombrada == null || listaSaldoNombrada.isEmpty()) {
            throw new BusinessDataException(errorResolver.getMessage("JBR-013"), "JBR-013");
        }
        SaldoNombrada saldoNombrada = listaSaldoNombrada.get(0);
        if(saldoNombrada == null){
            throw new BusinessException("No existe un registro en saldo_efectivo");
        }
        return saldoNombrada;
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#businessRulesTraspasarEntreCuentas(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.AgenteVO, BigInteger, java.math.BigDecimal)
     */
    public BigInteger businessRulesTraspasarEntreCuentas(AgenteVO traspasante,
			AgenteVO receptor, BigInteger idBoveda, BigDecimal importe) throws BusinessException {
    	
    	logger.info("Entrando a TesoreriaServiceImpl.businessRulesTraspasarEntreCuentas()");

    	utilService.validaAgente(traspasante, " traspasante", false);
    	utilService.validaAgente(receptor, "receptor", false);
    	
    	String claveTraspasante = traspasante.getClave();
    	String claveReceptor = receptor.getClave();
    	
    	InstitucionDTO institucionTraspasante = institucionDaliDAO.buscarInstitucionPorClaveYFolio(claveTraspasante);
    	InstitucionDTO institucionReceptora = institucionDaliDAO.buscarInstitucionPorClaveYFolio(claveReceptor);
    	
    	if(institucionTraspasante == null) {
    		throw new BusinessException(errorResolver.getMessage("JBR-K01", new Object[] { claveTraspasante }), "JBR-K01");
    	}else {
    		String cuentaClabe = StringUtils.trimToNull(institucionTraspasante.getCuentaClabe());
			// Validar que la institucion receptora tenga una cuenta clabe.
			if(StringUtils.isBlank(cuentaClabe)) {
				throw new BusinessException(errorResolver.getMessage("JBR-K02", new Object[] { claveTraspasante }), "JBR-K02");
			}else {// Validar que se pueda obtener la clave del participante a partir de la cuenta CLABE 
				if(cuentaClabe.length() != 18  || !claveTraspasante.equals(StringUtils.substring(cuentaClabe, 8, 13)) ) {
					throw new BusinessException(errorResolver.getMessage("JBR-K03", new Object[] { claveTraspasante }), "JBR-K03");
				}
			}
    	}
    	
    	if(institucionReceptora == null) {
    		throw new BusinessException(errorResolver.getMessage("JBR-K01", new Object[] { claveReceptor }), "JBR-K01");
    	}else {
    		String cuentaClabe = StringUtils.trimToNull(institucionReceptora.getCuentaClabe());
			// Validar que la institucion receptora tenga una cuenta clabe.
			if(StringUtils.isBlank(cuentaClabe)) {
				throw new BusinessException(errorResolver.getMessage("JBR-K02", new Object[] { claveReceptor }), "JBR-K02");
			}else {// Validar que se pueda obtener la clave del participante a partir de la cuenta CLABE 
				if(cuentaClabe.length() != 18  || !claveReceptor.equals(StringUtils.substring(cuentaClabe, 8, 13)) ) {
					throw new BusinessException(errorResolver.getMessage("JBR-K03", new Object[] { claveReceptor }), "JBR-K03");
				}
			}
    	}
    	
        if (importe == null || importe.compareTo(Constantes.CERO_BIG_DECIMAL) < Constantes.CERO_INT) {
            throw new BusinessException(errorResolver.getMessage("J0019", new Object[] {
                "Importe" }), "J0019");
        }   
        
//        SaldoNombrada saldoNombrada = this.getSaldoNombrada(
//                traspasante.getId(), traspasante.getFolio(), idBoveda);
//        
//        if(saldoNombrada == null){
//            throw new BusinessException(errorResolver.getMessage("-228", new Object[] {
//            "Error al obtener el saldo_traspasante." }), "-175");
//        }
    	
    	// Se retorna el folioControl
		return utilService.getFolio(SECUENCIA_FOLIO_CONTROL);
	}
    
    /**
     * XXX Deprecar y utilizar UtilService.validaAgente() cuando se elimine la dependencia
     * a objetos ajenos al modelo.
     * 
     * Encapsula las validaciones del AgenteVO
     * verifica que no sea nulo, que tenga id y folio, y que exista.
     * @param agenteVO
     * @throws BusinessException
     */
    public void validaAgente(com.indeval.sidv.decretos.persistence.model.vo.AgenteVO agenteVO) 
    		throws BusinessException {
    
		logger.info("Entrando a TesoreriaServiceImpl.validaAgente()");
		
		utilService.validaDTONoNulo(agenteVO, " agente ");
		
        /* XXX Este bloque Try-Catch es necesario 
         * ya que el metodo hace uso de un objeto ajeno al modelo */ 
        try {
        	agenteVO.tieneClaveValida();
		} catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
        if(StringUtils.isBlank(agenteVO.getCuenta())){
            throw new BusinessException(errorResolver.getMessage("J0008"));
        }
		
    }
    
    /* Setters y Getters */

    /**
     * Inyeccion del bean errorResolver
     *
     * @param errorResolver
     */
    public void setErrorResolver(MessageResolver errorResolver) {
        this.errorResolver = errorResolver;
    }

//    /**
//     * Inyeccion del bean traspasosDao
//     *
//     * @param traspasosDao
//     */
//    public void setTraspasosDao(TraspasosDao traspasosDao) {
//        this.traspasosDao = traspasosDao;
//    }
//
//    /**
//     * Inyeccion del bean saldosEfectivoDao
//     *
//     * @param saldosEfectivoDao
//     */
//    public void setSaldosEfectivoDao(SaldosEfectivoDao saldosEfectivoDao) {
//        this.saldosEfectivoDao = saldosEfectivoDao;
//    }
//
//    /**
//     * Inyeccion del bean empresaCatalogoDao
//     *
//     * @param empresaCatalogoDao
//     */
//    public void setEmpresaCatalogoDao(EmpresaCatalogoDao empresaCatalogoDao) {
//        this.empresaCatalogoDao = empresaCatalogoDao;
//    }
//
//    /**
//     * Inyeccion del bean parcialidadesDao
//     *
//     * @param parcialidadesDao
//     */
//    public void setParcialidadesDao(ParcialidadesDao parcialidadesDao) {
//        this.parcialidadesDao = parcialidadesDao;
//    }
//
//    /**
//     * Inyeccion del bean productoDineroDao
//     *
//     * @param productoDineroDao
//     */
//    public void setProductoDineroDao(ProductoDineroDao productoDineroDao) {
//        this.productoDineroDao = productoDineroDao;
//    }
//
//    /**
//     * Inyeccion del bean storedProceduresDao
//     *
//     * @param storedProceduresDao
//     */
//    public void setStoredProceduresDao(StoredProceduresDao storedProceduresDao) {
//        this.storedProceduresDao = storedProceduresDao;
//    }
//
//    /**
//     * Inyeccion del bean fasesAbiertas
//     *
//     * @param fasesAbiertas
//     */
//    public void setFasesAbiertas(Map fasesAbiertas) {
//        this.fasesAbiertas = fasesAbiertas;
//    }
//
//    /**
//     * Inyeccion del bean traspasosEfectivoJDBCDao
//     *
//     * @param traspasosEfectivoJDBCDao
//     */
//    public void setTraspasosEfectivoJDBCDao(TraspasosEfectivoDao traspasosEfectivoJDBCDao) {
//        this.traspasosEfectivoJDBCDao = traspasosEfectivoJDBCDao;
//    }
//
//    /**
//     * Obtiene el bean aplicacionDao
//     *
//     * @return AplicacionDao
//     */
//    public AplicacionDao getAplicacionDao() {
//        return aplicacionDao;
//    }
//
//    /**
//     * Inyeccion del bean aplicacionDao
//     *
//     * @param aplicacionDao
//     */
//    public void setAplicacionDao(AplicacionDao aplicacionDao) {
//        this.aplicacionDao = aplicacionDao;
//    }
//
//    /**
//     * Obtiene el bean areasTrabajoDao
//     *
//     * @return AreasTrabajoDao
//     */
//    public AreasTrabajoDao getAreasTrabajoDao() {
//        return areasTrabajoDao;
//    }
//
//    /**
//     * Inyeccion del bean areasTrabajoDao
//     *
//     * @param areasTrabajoDao
//     */
//    public void setAreasTrabajoDao(AreasTrabajoDao areasTrabajoDao) {
//        this.areasTrabajoDao = areasTrabajoDao;
//    }
//
//    /**
//     * Obtiene el bean contejpoDao
//     *
//     * @return ContejpoDao
//     */
//    public ContejpoDao getContejpoDao() {
//        return contejpoDao;
//    }
//
//    /**
//     * Inyeccion del bean contejpoDao
//     *
//     * @param contejpoDao
//     */
//    public void setContejpoDao(ContejpoDao contejpoDao) {
//        this.contejpoDao = contejpoDao;
//    }

    /**
     * Inyeccion del bean utilService
     *
     * @param utilService
     *            the utilService to set
     */
    public void setUtilService(UtilServices utilService) {
        this.utilService = utilService;
    }

//    /**
//     * @param emisionesDao
//     */
//    public void setEmisionesDao(EmisionesDao emisionesDao) {
//        this.emisionesDao = emisionesDao;
//    }
//
//    /**
//     * @return EmisionesDao
//     */
//    public EmisionesDao getEmisionesDao() {
//        return emisionesDao;
//    }
//
//	/**
//	 * @param decretosFijaDao
//	 */
//	public void setDecretosFijaDao(DecretosFijaDao decretosFijaDao) {
//        this.decretosFijaDao = decretosFijaDao;
//    }

    /**
     * @param decretosEjercicioDerechos
     */
    public void setDecretosEjercicioDerechos(LiquidacionDecretosService decretosEjercicioDerechos) {
        this.decretosEjercicioDerechos = decretosEjercicioDerechos;
    }

//    /**
//     * @param decretosVariableDao
//     */
//    public void setDecretosVariableDao(DecretosVariableDao decretosVariableDao) {
//		this.decretosVariableDao = decretosVariableDao;
//	}
//
//	/**
//	 * @param procesoDao
//	 */
//	public void setProcesoDao(ProcesoDao procesoDao) {
//		this.procesoDao = procesoDao;
//	}

    /**
     * @param dateUtilsDao the dateUtilsDao to set
     */
    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
        this.dateUtilsDao = dateUtilsDao;
    }

	/**
	 * @return the saldoNombradaDaliDao
	 */
	public SaldoNombradaDaliDao getSaldoNombradaDao() {
		return saldoNombradaDaliDao;
	}

	/**
	 * @param saldoNombradaDaliDao the saldoNombradaDaliDao to set
	 */
	public void setSaldoNombradaDao(SaldoNombradaDaliDao saldoNombradaDaliDao) {
		this.saldoNombradaDaliDao = saldoNombradaDaliDao;
	}

	/**
	 * @return the fasesAbiertas
	 */
	public Map getFasesAbiertas() {
		return fasesAbiertas;
	}

	/**
	 * @param fasesAbiertas the fasesAbiertas to set
	 */
	public void setFasesAbiertas(Map fasesAbiertas) {
		this.fasesAbiertas = fasesAbiertas;
	}

	/**
	 * @return the errorResolver
	 */
	public MessageResolver getErrorResolver() {
		return errorResolver;
	}

	/**
	 * @return the dateUtilsDao
	 */
	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @return the decretosEjercicioDerechos
	 */
	public LiquidacionDecretosService getDecretosEjercicioDerechos() {
		return decretosEjercicioDerechos;
	}


	public void setDepositanteValidoBanxicoDao(
			DepositanteValidoBanxicoDao depositanteValidoBanxicoDao) {
		this.depositanteValidoBanxicoDao = depositanteValidoBanxicoDao;
	}

}
