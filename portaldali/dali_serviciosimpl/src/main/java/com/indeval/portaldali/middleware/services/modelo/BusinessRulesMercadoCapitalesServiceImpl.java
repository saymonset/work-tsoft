/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.mercadocapitales.util.Constantes;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.ValorPK;

/**
 * Implementacion de los servicios de BusinessRulesMercadoCapitalesService
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BusinessRulesMercadoCapitalesServiceImpl implements
		BusinessRulesMercadoCapitalesService {

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(BusinessRulesMercadoCapitalesServiceImpl.class);

	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;

	/** Bean de acceso a UtilService */
	private UtilServices utilService;
    
    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /** Bean de acceso a instrumentoDaliDao */
    private InstrumentoDaliDao instrumentoDaliDao;

//    /** Bean de acceso a emisionesDao */
//    private EmisionesDao emisionesDao;

    /** Mapa que contiene las clave reporto validas para operar */
    private Map claveReporto;
    
//    /** Bean de acceso a valorDao */
//    private ValorDao valorDao;

	/*
	 * NOTAS DE DESARROLLO exec bdcaptal..UP_tranmcap @fecha @id1 @fol1 @cta1
	 * @t_cta1 @id2 @fol2 @cta2 @t_cta2 @tv @emis @serie @cupon @baja @cant
	 * @cve_rep @dias_plazo @fecha_reporto @fol_transm @m_dine @precio_titulo
	 * @tasa_premio @usuario @usuario_real @fecha_liq @liq @mercado @origen
	 * @origen_aplicac @tipo_lib @llave_fol_md @nom_us @nom_area @fol_cont
	 * @fol_desc @divisa
	 */
	/**
	 * @see com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoCapitalesService#traspasosDineroComprador(TraspasosDineroCompradorParams)
	 */
	public boolean traspasosDineroComprador(
			TraspasosDineroCompradorParams params) throws BusinessException {

        logger.info("Entrando a BusinessRulesMercadoCapitalesServiceImpl.traspasosDineroComprador()");

		// Validamos que el objeto que contiene los parametros no sea nulo
		utilService.validaDTONoNulo(params, "");

		// Validamos que cada uno de los parametros no sea nulo, en caso de
		// haber un nulo, lanzamos una BusinessException
		if (!params.validaParams()) {
			throw new BusinessException(errorResolver.getMessage("JBR-999"));
		}
		// la cantidad a operar debe ser mayor a 1
		if (params.getCant().longValue() < 1) {
			throw new BusinessException(errorResolver.getMessage("JBR-313"));
		}
        
		// La clave reporto para la operacion debe estar entre {'T','O','P','V','A'}
		if (!claveReporto.containsKey(params.getCveRep())) {
			throw new BusinessException(errorResolver.getMessage("JBR-310"));
		}
        
		// La fecha de liquidacion debe ser un multiplo de 24
		if ((params.getLiq().intValue() > 0 && params.getLiq().intValue() <= 144)
				&& (params.getLiq().intValue() % 24 != 0)) {
			throw new BusinessException(errorResolver.getMessage("JBR-325"));
		}

		// El precio del titulo no puede ser menor o igual a cero, cuando la clave reporto es T
		if (params.getPrecioTitulo().intValue() <= Constantes.CERO_INT
				&& !params.getCveRep().equals(Constantes.CLAVE_REPORTO_T)) {
			throw new BusinessException(errorResolver.getMessage("JBR-308"));
		}

//		Esta regla ya no aplica  con la introduccion de tlps fecha valor		
//		Date fechaHoy = dateUtilsDao.getDateFechaDB();
//		// if (@fecha_liq > @fecha_dia and @cve_rep = 'T' and @liq = 0 and @origen != '08')
//		if (params.getFechaLiq().compareTo(fechaHoy) > Constantes.CERO_INT
//				&& params.getCveRep().equals(Constantes.CLAVE_REPORTO_T)
//				&& params.getLiq().intValue() == Constantes.CERO_INT
//				&& !params.getOrigen().equals(Constantes.ORIGEN_08)) {
//			throw new BusinessException(errorResolver.getMessage("JBR-319"));
//		}

        //recuperamos el instrumento dada su llave primaria (tv).
        Instrumento instrumento = instrumentoDaliDao.getInstrumento(params.getTv());
        if(instrumento == null){
            throw new BusinessException(errorResolver.getMessage("JBR-369"));
        }

//        //recuperamos la emision asociada a este traspaso
//        EmisionPersistence emision = emisionesDao.getEmision(getInstanceEmisionPK(params));

//        //si la emision recuperada no existe o no esta vigente, lanzamos una excepcion.
//        if(emision == null){
//            throw new BusinessException(errorResolver.getMessage("JBR-106"));
//        }

//        //Regla de negocio para validar que no se generen cortos.
//        Valor valor = valorDao.getByPK(getInstanceValorPK(params));
        
//        if(valor == null){
//            throw new BusinessException(errorResolver.getMessage("JBR-695"));
//        }
        
//        //El saldo disponible es menor a la cantidad operada?
//        if(valor.getSaldoDisponible().compareTo(params.getCant()) < 1){
//            throw new BusinessException(errorResolver.getMessage("JBR-181"));
//        }

		return true;
	}
    
    /**
     * Construye una instancia de EmisionPK 
     * a partir de los datos recibidos en un TraspasosDineroCompradorParams
     * 
     * @param params
     * @return EmisionPK
     * @throws BusinessException 
     */
    private EmisionPK getInstanceEmisionPK(TraspasosDineroCompradorParams params) throws BusinessException{
        
        logger.info("Entrando a BusinessRulesMercadoCapitalesServiceImpl.getInstanceValorPK()");
        
        utilService.validaDTONoNulo(params, "");
        
        EmisionPK emisionPK =new EmisionPK();
        emisionPK.setTv(params.getTv());
        emisionPK.setEmisora(params.getEmis());
        emisionPK.setSerie(params.getSerie());
        emisionPK.setCupon(params.getCupon());
        
        return emisionPK;
        
    }
    
    /**
     * Construye una instancia de ValorPK 
     * a partir de los datos recibidos en un TraspasosDineroCompradorParams
     * 
     * @param params
     * @return ValorPK
     * @throws BusinessException 
     */
    private ValorPK getInstanceValorPK(TraspasosDineroCompradorParams params) throws BusinessException{
        
        logger.info("Entrando a BusinessRulesMercadoCapitalesServiceImpl.getInstanceValorPK()");
        
        utilService.validaDTONoNulo(params, "");
        
        ValorPK valorPK = new ValorPK();
        valorPK.setIdInst(params.getId1());
        valorPK.setFolioInst(params.getFol1());
        valorPK.setCuenta(params.getCta1());
        valorPK.setTv(params.getTv());
        valorPK.setEmisora(params.getEmis());
        valorPK.setSerie(params.getSerie());
        valorPK.setCupon(params.getCupon());
        
        return valorPK;
        
    }
    
    
	/*
	 * NOTAS DE DESARROLLO exec bdcaptal..UP_trasp_divint @id1 @fol1 @cta1 @id2
	 * @fol2 @cta2 @tv @emis @serie @cupon @cant @cve_rep @fol_transm @m_dine
	 * @usuario @usuario_val1 @usuario_val3 @fecha_liq @mercado @origen
	 * @origen_aplicac @nom_us @llave_folio @nom_area @fol_cont @tipo_mov @id3
	 * @fol3 @cta3 @folio_transm2 @emision_extr @pos_actd @pos_antd @divisa
	 */
	/**
	 * @see com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoCapitalesService#traspasosDivisionInternacionalEntreFideicomisos(TraspasosDivisionInternacionalEntreFideicomisosParams)
	 */
	public boolean traspasosDivisionInternacionalEntreFideicomisos(
			TraspasosDivisionInternacionalEntreFideicomisosParams params)
			throws BusinessException {
        
		logger.info("Entrando a BusinessRulesMercadoCapitalesServiceImpl." +
                "traspasosDivisionInternacionalEntreFideicomisos()");
        
        /* Se validan los parametros recibidos */
		utilService.validaDTONoNulo(params, "");
		if (!params.validaParams()) {
			throw new BusinessException(errorResolver.getMessage("JBR-999"));
		}

		if (params.getCant().compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) <= Constantes.CERO_INT) {
			throw new BusinessException(errorResolver.getMessage("JBR-996"));
		}

		return true;
	}

	/*
	 * NOTAS DE DESARROLLO exec bdcaptal..UP_trasp_divintsf @id1 @fol1 @cta1
	 * @id2 @fol2 @cta2 @tv @emis @serie @cupon @cant @cve_rep @fol_transm
	 * @m_dine @usuario @usuario_val1 @fecha_liq @mercado @origen
	 * @origen_aplicac @nom_us @llave_folio @nom_area @fol_cont
	 */
	/**
	 * @see com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoCapitalesService#traspasosDivisionInternacionalSinFideicomisos(TraspasosDivisionInternacionalSinFideicomisosParams)
	 */
	public boolean traspasosDivisionInternacionalSinFideicomisos(
			TraspasosDivisionInternacionalSinFideicomisosParams params)
			throws BusinessException {

		logger.info("Entrando a BusinessRulesMercadoCapitalesServiceImpl.traspasosDivisionInternacionalSinFideicomisos");

		utilService.validaDTONoNulo(params,
				"TraspasosDivisionInternacionalSinFideicomisosParams");

		// checamos que los parametros no sean nulos.
		if (!params.validaParams()) {

			throw new BusinessException(errorResolver.getMessage("JBR-999"),
					"JBR-999");

		}
		// checamos que la cantidad no se igual a cero.
		if (params.getCant() != null && Constantes.CERO_STRING
				.equalsIgnoreCase(params.getCant().toString())) {
			throw new BusinessException(errorResolver.getMessage("JBR-996"),
					"JBR-996");
		}
		return true;
	}

	/**
	 * Inyeccion del bean errorResolver
	 *
	 * @param errorResolver
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * Inyeccion del bean utilService utilService
	 *
	 * @param utilService
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

    /**
     * @param instrumentoDaliDao the instrumentoDaliDao to set
     */
    public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
        this.instrumentoDaliDao = instrumentoDaliDao;
    }

//    /**
//     * Inyeccion del bean emisionesDao
//     * 
//     * @param emisionesDao
//     */
//    public void setEmisionesDao(EmisionesDao emisionesDao) {
//        this.emisionesDao = emisionesDao;
//    }
//    
//    /**
//     * Inyeccion del bean valorDao
//     * 
//     * @param valorDao
//     */
//    public void setValorDao(ValorDao valorDao) {
//        this.valorDao = valorDao;
//    }

    /**
     * @return the claveReporto
     */
    public Map getClaveReporto() {
        return claveReporto;
    }

    /**
     * @param claveReporto the claveReporto to set
     */
    public void setClaveReporto(Map claveReporto) {
        this.claveReporto = claveReporto;
    }

    /**
     * @param dateUtilsDao the dateUtilsDao to set
     */
    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
        this.dateUtilsDao = dateUtilsDao;
    }

}
