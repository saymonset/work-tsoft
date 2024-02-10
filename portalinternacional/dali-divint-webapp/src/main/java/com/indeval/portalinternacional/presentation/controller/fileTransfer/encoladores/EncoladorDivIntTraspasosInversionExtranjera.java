package com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.CuponDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.common.util.IsoHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposDivIntVO;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;



public class EncoladorDivIntTraspasosInversionExtranjera implements IEncoladorProtocoloFinanciero{
	/** DAO para emisiones */
    private EmisionDao emisionDao = null;

    /** Clase de utileria para fechas */
    private DateUtilService dateUtilService = null;
    
    /** Clase de utileria para ISOs */
    private IsoHelper isoHelper = null;
    
    /** Servicio con metodos de utileria */
    private UtilService utilService = null;
    
    /**
     * CuponDao 
     */
    private CuponDao cuponDao = null;
  
    /**
     * Servicio para realizar la Inserci&oacute;n de la Operaci&oacute;n en la
     * Base de Datos
     */
    private DivisionInternacionalService divisionInternacionalService;
    
	/**
	 * @see com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores.IEncoladorProtocoloFinanciero#obtenerISOs(com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO)
	 */
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO) {
    	List<String> resultados = new ArrayList<String>();
    	List listaTLPs = new ArrayList();
    	Map<String, Object> mapa = new HashMap<String, Object>();
    	
    	PaginaVO pag = totProcVO.getPaginaVO();
        List listaRegistros = pag.getRegistros();
        RegistroProcesadoVO reg;

        for (int i = 0; i < listaRegistros.size(); i++) {
            reg = (RegistroProcesadoVO) listaRegistros.get(i);
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals(Constantes.ESTADO_ERROR)) {
                // Esta lista contiene cada uno de los campos que conforman el registro
                CamposDivIntVO camposDivIntVO = reg.getCamposDivIntVO();
                // 542 Envio Libre Pago
                if (camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_542)) {
                	TraspasoLibrePagoVO librePagoVO = obtenerTraspasoLibrePagoVO(camposDivIntVO);
                	resultados.add(isoHelper.creaISO(librePagoVO, false));
                	listaTLPs.add(librePagoVO);
                	//540 Enviando una compra
                }else if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_540)){
                	TraspasoLibrePagoVO librePagoVO = obtenerTraspasoLibrePagoVO(camposDivIntVO);
                	resultados.add(isoHelper.creaISO(librePagoVO, true));
                	listaTLPs.add(librePagoVO);
                }
            }
        }
        
        mapa.put(ID_LISTA_ISOS, resultados);
		mapa.put(ID_LISTA_OBJETOS, listaTLPs);

        return mapa;
    }

    /**
     * @see com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores.IEncoladorProtocoloFinanciero#firmayEncola(com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO, java.util.List, java.util.List)
     */
    public Map<BigInteger, Integer> firmayEncola(TotalesProcesoVO totProcVO, List listaTLPs, List<String> isosFirmados, String ticket) throws BusinessException, ProtocoloFinancieroException {
        LinkedHashMap mpRegistrosEncolados = new LinkedHashMap();

        PaginaVO pag = totProcVO.getPaginaVO();
        List listaRegistros = pag.getRegistros();
        RegistroProcesadoVO reg;
        int index = 0;

        for (int i = 0; i < listaRegistros.size(); i++) {
            reg = (RegistroProcesadoVO) listaRegistros.get(i);
            
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals(Constantes.ESTADO_ERROR)) {
            	
            	if(listaTLPs != null && !listaTLPs.isEmpty() &&  index < listaTLPs.size()) {
	                // Esta lista contiene cada uno de los campos que conforman el registro
	                CamposDivIntVO camposDivIntVO = reg.getCamposDivIntVO();	                
	                // 542 Envio Libre Pago
	                if (Constantes.TMSJ_542.equalsIgnoreCase(camposDivIntVO.getTipoMensaje()) || Constantes.TMSJ_540.equalsIgnoreCase(camposDivIntVO.getTipoMensaje())){
	                	TraspasoLibrePagoVO librePagoVO = (TraspasoLibrePagoVO)listaTLPs.get(index);
	                	try {
		                	HashMap<String, Object> valoresAdicionales = llenaValoresAdicionalesInversionExtranjera(camposDivIntVO);
		                	//se agrega el ticket al mapa
		                	valoresAdicionales.put(SeguridadConstants.USR_CREDENCIAL, ticket);
	                    	GrabaOperacionParams grabaOperacionParams = new GrabaOperacionParams();
	                    	grabaOperacionParams.setTraspasoLibrePagoVO(librePagoVO);
	                    	grabaOperacionParams.setDatosAdicionales(valoresAdicionales);
	                    	grabaOperacionParams.setOrigenRegistro(Constantes.ID_ORIGEN_PORTAL);
	                    	if(isosFirmados != null && !isosFirmados.isEmpty() &&  index < isosFirmados.size()) {
	                    		grabaOperacionParams.setIsoFirmado(isosFirmados.get(index));
	                    	}
	                    	if(Constantes.TMSJ_540.equalsIgnoreCase(camposDivIntVO.getTipoMensaje())){
	                    		grabaOperacionParams.setRecepcion(true);
	                    	}
	                    	divisionInternacionalService.grabaOperacion(grabaOperacionParams);	
	                        mpRegistrosEncolados.put(reg.getConsec(), new Integer(1));
	                    }
	                    catch (Exception e) {
	                        mpRegistrosEncolados.put(reg.getConsec(), new Integer(0));
	                        LoggerFactory.getLogger(this.getClass()).error("Error al grabar registro de operacion", e);
	                    }
	                    index++;
	                }	          
                }
            }
        }
        return mpRegistrosEncolados;
    }

    /**
     * Crea un objeto de tipo TraspasoLibrePagoVO con los datos necesarios
     * 
     * @param camposDivIntVO VO con los datos a obtener
     * @return Objeto de tipo TraspasoLibrePagoVO
     */
    private TraspasoLibrePagoVO obtenerTraspasoLibrePagoVO(CamposDivIntVO camposDivIntVO) {
    	TraspasoLibrePagoVO librePagoVO = new TraspasoLibrePagoVO();
        librePagoVO.setTipoInstruccion(Constantes.TIPO_OPER_TLP);
        librePagoVO.setIdFolioCtaTraspasante(camposDivIntVO.getIdInstTrasp().concat(
                camposDivIntVO.getFolioInstTrasp()).concat(
                camposDivIntVO.getCuentaTrasp()));
        librePagoVO.setIdFolioCtaReceptora(camposDivIntVO.getIdInstRecep().concat(
                camposDivIntVO.getFolioInstRecep()).concat(
                camposDivIntVO.getCuentaRecep()));
        librePagoVO.setTipoValor(camposDivIntVO.getTv().trim().toUpperCase());
        librePagoVO.setEmisora(camposDivIntVO.getEmisora().trim().toUpperCase());
        librePagoVO.setSerie(camposDivIntVO.getSerie().trim().toUpperCase());
        librePagoVO.setCupon(camposDivIntVO.getCupon().trim().toUpperCase());
        librePagoVO.setCantidadTitulos(camposDivIntVO.getCantidad().longValue());
        librePagoVO.setFechaLiquidacion(parseFechaAbsoluta(camposDivIntVO.getFechaLiquidacion()));
        librePagoVO.setFechaRegistro(getHoy());
        librePagoVO.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
        librePagoVO.setReferenciaOperacion(utilService.getFolio(Constantes.SEQ_FOLIO_CONTROL).toString());
        if (StringUtils.isBlank(librePagoVO.getTipoValor())
                || StringUtils.isBlank(librePagoVO.getEmisora())
                || StringUtils.isBlank(librePagoVO.getSerie())
                || StringUtils.isBlank(librePagoVO.getCupon())) {
            EmisionVO emisionVO =  new EmisionVO(camposDivIntVO.getIsin());
            Emision emision = emisionDao.findEmisionLiberada(new EmisionVO(camposDivIntVO.getIsin()));
            if(emision != null) {
	            emisionVO = ConvertBO2VO.crearEmisionVO(emision);
	            Cupon cupon = cuponDao.findCuponByIdEmision(emision.getIdEmision());
	            if(emisionVO != null) {
		            librePagoVO.setTipoValor(emisionVO.getTv());
		            librePagoVO.setEmisora(emisionVO.getEmisora());
		            librePagoVO.setSerie(emisionVO.getSerie());
		            librePagoVO.setCupon(cupon.getClaveCupon());
	            }
            }
        }
        return librePagoVO;
	}

	/**
     * Llena mapa con los valores de datos adicionales 
     * 
     * @param camposDivIntVO Objeto de donde se sacan los datos adicionales
     * @return Mapa con los datos adicionales
     * @throws BusinessException
     */
    private HashMap<String, Object> llenaValoresAdicionalesInversionExtranjera(CamposDivIntVO camposDivIntVO)
            throws BusinessException {
        
        HashMap datosAdicionales = new HashMap();
		
		datosAdicionales.put(Constantes.FECHA_OP_DA,parseFechaAbsoluta(getHoy()) );
		datosAdicionales.put(Constantes.FECHA_LIQ_DA, camposDivIntVO.getFechaLiquidacion());
		datosAdicionales.put(Constantes.FECHA_NOT_DA,parseFechaAbsoluta(getHoy()));	
		datosAdicionales.put(Constantes.TIPO_MENSAJE_DA, camposDivIntVO.getTipoMensaje());
		datosAdicionales.put(Constantes.ESTATUS_DA, new Long(2));
		datosAdicionales.put(Constantes.PRECIO_DA,camposDivIntVO.getImporte() != null ? camposDivIntVO.getImporte() : new BigDecimal("0"));
		
		return datosAdicionales;
    }
    /**
     * Obtiene la fecha actual
     * @return Returns the currentDate
     */
    public Date getHoy() {
        return dateUtilService.getCurrentDate();
    }

    /**
     * Establece a la fecha con el formato de dd/MM/yyyy absolutamente (Sin
     * horas, minutos, segundos).
     * 
     * @param fechaParam
     * @return
     */
    public Date parseFechaAbsoluta(Date fechaParam) {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaParam);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

	/**
	 * @return the emisionDao
	 */
	public EmisionDao getEmisionDao() {
		return emisionDao;
	}

	/**
	 * @param emisionDao the emisionDao to set
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @return the dateUtilService
	 */
	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	/**
	 * @param dateUtilService the dateUtilService to set
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * @return the isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * @param isoHelper the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @return the utilService
	 */
	public UtilService getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return CuponDao
	 */
	public CuponDao getCuponDao() {
		return cuponDao;
	}

	/**
	 * @param cuponDao
	 */
	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}
	
}
