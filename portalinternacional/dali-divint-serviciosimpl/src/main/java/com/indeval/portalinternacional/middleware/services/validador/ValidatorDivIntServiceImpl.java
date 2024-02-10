/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.ibm.icu.util.Calendar;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.ValidatorService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatementDivint;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author javiles
 *
 */
public class ValidatorDivIntServiceImpl implements ValidatorDivIntService, Constantes {

    /** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorDivIntServiceImpl.class);
    
    /** Bean de acceso a catBicDao */
    private CatBicDao catBicDao;
    
    /** Bean de acceso a detalleCustodio */
    private List<String> detalleCustodio;
    
    /** Bean de acceso a EmisionDao */
    private EmisionDao emisionDao;   
    
    /** Bean de acceso a institucionDao */
    private InstitucionDao institucionDao;
    
    /** Resolvedor de Mensajes */
    private MessageResolver errorResolver;
    
    /** Bean de acceso a validatorService */
    private ValidatorService validatorService;
    
    private SicDetalleDao sicDetalleDao;
    
    private CuentaNombradaDao cuentaNombradaDao;
    
    private BovedaDao bovedaDao;
    
    private DateUtilService dateUtilService;
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService#validaInstitucion(java.lang.Long)
     */
    public void validaInstitucion(Long idInstitucion) throws BusinessException {
    	
    	if (((Institucion) institucionDao.getByPk(Institucion.class, idInstitucion)) == null) {
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la Institucion"));
		}
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorService#esCuentaValida(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
    public boolean esCuentaValida(AgenteVO agenteVO) throws BusinessException {

        log.info("Entrando a ValidatorDivIntServiceImpl.esCuentaValida()");

        return ((agenteVO != null && agenteVO.tieneClave() && StringUtils.isNotBlank(agenteVO.getCuenta())) 
        		&& !(agenteVO.esTipoTenenciaTESO() || agenteVO.esCuenta0030() 
        				|| agenteVO.esCuenta0031() || agenteVO.esCuenta0050to0057() || agenteVO.esCuenta0070() 
        				|| agenteVO.esCuenta0080() || agenteVO.esCuenta0081()));
        
    }
    
    /**
     * metodo para validar si es el mismo participante y la misma cuenta en el Traspaso
     * @param AgenteVO traspasante y AgenteVO receptor
     * @return false si son diferentes ya sea el participante o la cuenta, true si son iguales tanto el participante como la cuenta
     */ 
    private boolean esMismaCuenta(AgenteVO traspasante, AgenteVO receptor){
    	
    	log.info("Entrando a ValidatorDivIntServiceImpl.esMismaCuenta");
    	
    	return ( (traspasante != null && traspasante.tieneClave() && StringUtils.isNotBlank(traspasante.getCuenta())) && 
    			(receptor != null && receptor.tieneClave() && StringUtils.isNotBlank(receptor.getCuenta()) ) &&
    			(traspasante.getClave().equals(receptor.getClave()) && traspasante.getCuenta().equals(receptor.getCuenta()))); 
    }
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorService#esTraspasoValido(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.StringBuilder)
     */
    public boolean esTraspasoValido(AgenteVO traspasante, AgenteVO receptor,
            EmisionVO emisionVO, StringBuilder mensaje) throws BusinessException {
        
        log.info("Entrando a ValidatorDivIntServiceImpl.esTraspasoValido()");
        
        if (!this.esCuentaValida(traspasante) || !this.esCuentaValida(receptor)) {
            mensaje.append("No esta permitido hacer un traspaso de cuenta: [");
            mensaje.append(traspasante.getCuenta());
            mensaje.append("] hacia una cuenta: [");
            mensaje.append(receptor.getCuenta());
            mensaje.append("]");
            return false;
        }
        /**se valida que no sea el mismo participante y la misma cuenta el traspaso*/
        if(esMismaCuenta(traspasante,receptor)){
        	mensaje.append("No se pueden realizar traspasos a la misma cuenta");
        	return false;
        }        
        /* Obtiene emision */
        Emision emision = emisionDao.findEmision(emisionVO);
        if (emision == null) {
            mensaje.append(errorResolver.getMessage("J0001"));
            return false;
        }
        emisionVO = ConvertBO2VO.crearEmisionVO(emision);
        
        try {
            
           // Valida si la emision esta dentro del programa de ADR's 
            //TODO Verificar como se obtiene ahora el programa de ADR's
            
             //Para cuentas con terminacion 99 o comienzo 54 
            if (receptor.esTerminacionCuenta98() || receptor.esTerminacionCuenta94()
                    || (receptor.esTerminacionCuenta97() && emisionVO.esLIBR())) {
                if (emisionVO.esLIBR() || emisionVO.esADR() || (emisionVO.esVIVI() 
                        && traspasante.esTerminacionCuenta99() && receptor.esTerminacionCuenta94())) {
                    throw new BusinessException("");
                }
            }
            if (traspasante.esTerminacionCuenta98() || traspasante.esTerminacionCuenta94()
                    || (traspasante.esTerminacionCuenta97() && emisionVO.esLIBR())) {
                if (receptor.esTerminacionCuenta99() || receptor.esComienzoCuenta54()) {
                    if (emisionVO.esLIBR() || emisionVO.esADR() || (emisionVO.esVIVI() 
                            && traspasante.esTerminacionCuenta94() && receptor.esTerminacionCuenta99())) {
                        throw new BusinessException("");
                    }
                }
            }            
           //  Para cuentas con terminacion 98 o comienzo 54 
            if (receptor.esTerminacionCuenta99() || receptor.esTerminacionCuenta94()
                    || (receptor.esTerminacionCuenta97() && emisionVO.esCPOS())) {
                if (emisionVO.esCPOS() || emisionVO.esADCP() || emisionVO.esBADC() || emisionVO.esIADC() 
                        || emisionVO.esGADC() || (emisionVO.esVIVI() && receptor.esTerminacionCuenta99())) {
                    throw new BusinessException("");
                }
            }
            if (traspasante.esTerminacionCuenta99() || traspasante.esTerminacionCuenta94()
                    || (traspasante.esTerminacionCuenta97() && emisionVO.esCPOS())) {
                if (receptor.esTerminacionCuenta98() || receptor.esComienzoCuenta54()) {
                    if (emisionVO.esCPOS() || emisionVO.esADCP() || emisionVO.esBADC() || emisionVO.esIADC() 
                            || emisionVO.esGADC() || (emisionVO.esVIVI() 
                                    && traspasante.esTerminacionCuenta99())) {
                        throw new BusinessException("");
                    }
                }
            }            
        }
        catch (BusinessException e) {
            mensaje.append("No esta permitido hacer un traspaso ");
            mensaje.append("de cuenta: [");
            mensaje.append(traspasante.getCuenta());
            mensaje.append("] con clave de alta: [");
            mensaje.append(emisionVO.getEmisionExtra());
            mensaje.append("] hacia una cuenta: [");
            mensaje.append(receptor.getCuenta());
            mensaje.append("]");
            return false;
        }       
        return true;
    }   
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService#esCustodioValido(java.lang.String, String)
     */
    public boolean esCustodioValido(String tipoMensaje, String descCustodio) throws BusinessException {
        
        log.info("Entrando a ValidatorDivIntServiceImpl.esCustodioValido()");
        
        if (StringUtils.isBlank(tipoMensaje) || StringUtils.isBlank(descCustodio)) {
            return false;
        }
        if ((TCP_ENTREGA.equalsIgnoreCase(tipoMensaje.trim()) 
                || TCP_RECEPCION.equalsIgnoreCase(tipoMensaje.trim()))
              && detalleCustodio.contains(descCustodio.trim())) {
          return false;
        }
        return true;
        
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService#validaBeneficiarioConsulta(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
     */
    public void validaBeneficiarioConsulta(Beneficiario beneficiario) throws BusinessException  {
    	
    	log.info("Entrando a ValidatorDivIntServiceImpl.validaBeneficiarioConsulta()");
    	
    	/* BLOQUE DE VALIDACIONES */
		validatorService.validaDTONoNulo(beneficiario, "Beneficiario");
		validatorService.validaDTONoNulo(beneficiario.getIdCuentaNombrada(), " Custodio");
		validatorService.validaDTONoNulo(beneficiario.getInstitucion(), "Institucion");
		
		if(beneficiario.getIdCuentaNombrada()==null)
		{
			throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el ID del custodio"));
		}
		
    	
    }
        

	
   /**
     * VALIDACIONES DE FORMATO
     * @param indice
     * @param campos
     * @param msgError
     * @param numCampoError
     * @param tam
     * @param agenteVO
     * @param emisionVO
     * @return
     */
    public boolean validaFormato(Map<Object, Object> indice, String[] campos,
            StringBuffer msgError, StringBuffer numCampoError, int tam) {
    	
        log.info("Entrando a ValidatorDivIntServiceImpl.validaFormato()");

        boolean noError = true;

        /* ID VENDEDOR */
        int pos = ((Integer) indice.get(ID_INST_VEND)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 2
                || !campos[pos].matches(PATRON_NUMERICO)) {

            agregaErrorValidacion(msgError, " del id del vendedor ", numCampoError, pos);
            noError = false;
        }

        /* FOLIO VENDEDOR */
        pos = ((Integer) indice.get(FOLIO_INST_VEND)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 3
                || !campos[pos].matches(PATRON_NUMERICO)) {
            agregaErrorValidacion(msgError, " del folio del vendedor ", numCampoError, pos);
            noError = false;
        }

        /* CUENTA VENDEDOR */
        pos = ((Integer) indice.get(CUENTA_VEND)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() < 3
                || campos[pos].trim().length() > 4 || !campos[pos].matches(PATRON_NUMERICO)) {
            agregaErrorValidacion(msgError, " de la cuenta del vendedor ", numCampoError, pos);
            noError = false;
        }

        /* TV */
        pos = ((Integer) indice.get(TV)).intValue();

        if (campos[pos].length() > 4 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del tv ", numCampoError, pos);
            noError = false;
        }

        /* EMISORA */
        pos = ((Integer) indice.get(EMISORA)).intValue();

        if (campos[pos].length() > 7) {
            agregaErrorValidacion(msgError, " de la emisora ", numCampoError, pos);
            noError = false;
        }

        /* SERIE */
        pos = ((Integer) indice.get(SERIE)).intValue();

        if (campos[pos].length() > 6) {
            agregaErrorValidacion(msgError, " de la serie ", numCampoError, pos);
            noError = false;
        }

        /* CUPON */
        pos = ((Integer) indice.get(CUPON)).intValue();

        if (campos[pos].length() > 4 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del cupon ", numCampoError, pos);
            noError = false;
        }

        /* ISIN */
        pos = ((Integer) indice.get(ISIN)).intValue();

        if (campos[pos].length() != 12 || !campos[pos].matches(PATRON_ALFANUMERICO)) {
            agregaErrorValidacion(msgError, " del isin ", numCampoError, pos);
            noError = false;
        }
        
        /* ID_BOVEDA */
        pos = ((Integer) indice.get(ID_BOVEDA)).intValue();

        if(StringUtils.isBlank(campos[pos])){
        	campos[pos] = FactoryDivInt.BOVEDA_PREDETERMINADA.toString();
        }
        else{
            if (campos[pos].length() > 2 
            		|| !campos[pos].matches(PATRON_NUMERICO)) {
                agregaErrorValidacion(msgError, " de la boveda ", numCampoError, pos);
                noError = false;
            }        	
        }

        /* CANTIDAD */
        pos = ((Integer) indice.get(CANTIDAD)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() > 20
                || !campos[pos].matches(PATRON_NUMERICO)) {
            agregaErrorValidacion(msgError, " de la cantidad ", numCampoError, pos);
            noError = false;
        }

        /* TIPO OPER */
        pos = ((Integer) indice.get(TIPO_OPER)).intValue();

        if (StringUtils.isBlank(campos[pos])
                || (!campos[pos].equalsIgnoreCase(TRASPASO_LIBRE) && !campos[pos]
                        .equalsIgnoreCase(TRASPASO_CONTRA))) {
            agregaErrorValidacion(msgError, " del tipo de operacion ", numCampoError, pos);
            noError = false;
        }

        /* TIPO MOV */
        pos = ((Integer) indice.get(TIPO_MOV)).intValue();

        if (StringUtils.isBlank(campos[pos])
                || (!campos[pos].equalsIgnoreCase(TIPO_MOVTO_E) && !campos[pos]
                        .equalsIgnoreCase(TIPO_MOVTO_R))) {
            agregaErrorValidacion(msgError, " del tipo de movimiento ", numCampoError, pos);
            noError = false;
        }

        /* FECHA NOTIFICACION */
        pos = ((Integer) indice.get(FECHA_NOT)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11
                || !fechaValida(campos[pos])) {
            agregaErrorValidacion(msgError, " de la fecha de Notificacion ", numCampoError, pos);
            noError = false;
        }

        /* FECHA LIQUIDACION */
        pos = ((Integer) indice.get(FECHA_LIQ)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11
                || !fechaValida(campos[pos])) {
            agregaErrorValidacion(msgError, " de la fecha de Liquidacion ", numCampoError, pos);
            noError = false;
        }

        /* FECHA OPERACION */
        pos = ((Integer) indice.get(FECHA_OPER)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 11
                || !fechaValida(campos[pos])) {
            agregaErrorValidacion(msgError, " de la fecha de Operacion ", numCampoError, pos);
            noError = false;
        }

        /* DESC. CUSTODIO */
        pos = ((Integer) indice.get(CUSTODIO)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 35) {
            agregaErrorValidacion(msgError, " de la descripcion del custodio ", numCampoError, pos);
            noError = false;
        }

        /* CUENTA CONTRAPARTE */
        pos = ((Integer) indice.get(CUENTA_CONTRA)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 15) {
            agregaErrorValidacion(msgError, " de la cuenta contraparte ", numCampoError, pos);
            noError = false;
        }

        /* DESC. CONTRAPRTE */
        pos = ((Integer) indice.get(CONTRAPARTE)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 40) {
            agregaErrorValidacion(msgError, " de la descripcion de la contraparte ", numCampoError,
                    pos);
            noError = false;
        }

        /* NOMBRE DEPOSITANTE */
        pos = ((Integer) indice.get(DEPOSITANTE)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 35) {
            agregaErrorValidacion(msgError, " del nombre del depositante ", numCampoError, pos);
            noError = false;
        }

        /* NOMBRE CUENTA BENEFICIARIO */
        pos = ((Integer) indice.get(NOMBRE_CUENTA_BEN)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 40) {
            agregaErrorValidacion(msgError, " del nombre de la cuenta del beneficiario ",
                    numCampoError, pos);
            noError = false;
        }

        /* NUMERO CUENTA BENEFICIARIO */
        pos = ((Integer) indice.get(NUMERO_CUENTA_BEN)).intValue();

        if (StringUtils.isBlank(campos[pos]) || campos[pos].length() != 15) {
            agregaErrorValidacion(msgError, " del numero de la cuenta del beneficiario ",
                    numCampoError, pos);
            noError = false;
        }

        if (tam > LONGITUD_TRASP_DIV_INT_MIN) {

            /** IMPORTE */
            pos = ((Integer) indice.get(IMPORTE)).intValue();

            if (StringUtils.isNotBlank(campos[pos])
                    && (campos[pos].length() != 15 || (campos[pos].indexOf(".") >= 0 && campos[pos]
                            .indexOf(".") != 12)) || !campos[pos].matches(PATRON_CANTIDADES)) {
                agregaErrorValidacion(msgError, " el importe ", numCampoError, pos);
                noError = false;
            }

            /** DIVISA */
            pos = ((Integer) indice.get(DIVISA)).intValue();

            if (campos[pos].length() != 3 || !campos[pos].matches(PATRON_ALFABETICO)) {
                agregaErrorValidacion(msgError, " de la divisa ", numCampoError, pos);
                noError = false;
            }
        }

        if (tam > LONGITUD_TRASP_DIV_INT_INTER) {

            /** INSTRUCCION ESPECIAL */
            pos = ((Integer) indice.get(INST_ESP)).intValue();

            if (StringUtils.isBlank(campos[pos])) {
                agregaErrorValidacion(msgError, " de las instrucciones especiales ", numCampoError,
                        pos);
                noError = false;
            }
        }
        return noError;
    }
    
    /**
     * @param msgError
     * @param mensaje
     * @param numCampoError
     * @param pos
     */
    private static void agregaErrorValidacion(StringBuffer msgError, String mensaje,
            StringBuffer numCampoError, int pos) {
    	
        log.info("Entrando a ValidatorDivIntServiceImpl.agregaErrorValidacion()");

        msgError.append("Error: Formato incorrecto.");
        msgError.append(mensaje);
        msgError.append(GUION);
        numCampoError.append(String.valueOf(pos));
        numCampoError.append(GUION);
    }
    
    /**
     * @param fecha
     * @return
     */
    private static boolean fechaValida(String fecha) {

    	log.info("Entrando a ValidatorDivIntServiceImpl.fechaValida()");

        String[] fechaSeparada = fecha.split("-");
        if (fechaSeparada.length != 3 || fechaSeparada[0].length() != 2
                || !fechaSeparada[0].matches(PATRON_NUMERICO) || fechaSeparada[1].length() != 3
                || !fechaSeparada[1].matches(PATRON_ALFABETICO) || fechaSeparada[2].length() != 4
                || !fechaSeparada[2].matches(PATRON_NUMERICO)) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param cantidad
     * @return
     */
    public boolean validaCantidades(String cantidad) {

    	log.info("Entrando a ValidatorDivIntServiceImpl.validaCantidades()");
    	
        boolean noError = true;
        BigDecimal cantidadD = null;
        if (StringUtils.isBlank(cantidad)) {
            noError = false;
        }
        else {
            try {
                cantidadD = new BigDecimal(cantidad.trim());
            }
            catch (NumberFormatException e) {
                noError = false;
            }
            if (cantidadD.compareTo(BigDecimal.ZERO) == 0) {
                noError = false;
            }
        }
        return noError;
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService#validaOperacionSicCaptura(com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic)
     */
    public void validaOperacionSicCaptura(OperacionSic operacionSic) throws BusinessException {
        log.info("Entrando a validaOperacionSicCaptura()");
        
        /* Valida objeto */
        validatorService.validaDTONoNulo(operacionSic, "OperacionSic");
        
        /* Valida tipo de operacion - E, R */
        if (StringUtils.isBlank(operacionSic.getTipoOperacion()) 
                || (!TIPO_MOVTO_E.equalsIgnoreCase(operacionSic.getTipoOperacion().trim()) 
                        && !TIPO_MOVTO_R.equalsIgnoreCase(operacionSic.getTipoOperacion().trim()))) {
            throw new BusinessException(errorResolver.getMessage("J0010"));
        }


        /* Valira tipo de traspaso - TCP, TLP */
        if (StringUtils.isBlank(operacionSic.getTipoTraspaso()) 
                || (!TRASPASO_LIBRE.equalsIgnoreCase(operacionSic.getTipoTraspaso().trim()) 
                        && !TRASPASO_CONTRA.equalsIgnoreCase(operacionSic.getTipoTraspaso().trim()))) {
            throw new BusinessException(errorResolver.getMessage("J0011"));
        }
        
        /* Valida las fechas */
        if (operacionSic.getFechaNotificacion() == null) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la fecha de notificacion"));
        }
        if (operacionSic.getFechaNotificacion().compareTo(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate())) != 0) {
            throw new BusinessException(errorResolver.getMessage("J0024"));
        }
        if (operacionSic.getFechaLiquidacion() == null) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la fecha de liquidacion"));
        }
        
        Calendar calendarFechaLiq=Calendar.getInstance();
        calendarFechaLiq.setTime(operacionSic.getFechaLiquidacion());
        
        if (calendarFechaLiq.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || calendarFechaLiq.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY) {        	
        	 throw new BusinessException(errorResolver.getMessage("J0027"));
        }

        // 15May2017 - Validacion de que la fecha de liquidacion sea a lo mas 7 dias habiles menor a la fecha de 
        //             captura de la operacion (hoy).
        if (operacionSic.getFechaLiquidacion().compareTo(dateUtilService.agregarDiasHabiles(dateUtilService.getCurrentDate(), -7)) < 0) {
            throw new BusinessException(errorResolver.getMessage("J0145"));
        }
       
        if (operacionSic.getFechaOperacion() == null) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la fecha de operacion"));
        }
        
        Calendar calendarFechaOper=Calendar.getInstance();
        calendarFechaOper.setTime(operacionSic.getFechaOperacion());
        
        if (calendarFechaOper.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || calendarFechaOper.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY){        	
        	  throw new BusinessException(errorResolver.getMessage("J0029"));
        }        
        
        if (operacionSic.getFechaOperacion().compareTo(operacionSic.getFechaLiquidacion()) > 0) {
            throw new BusinessException(errorResolver.getMessage("J0031"));
        }
        
//        if (TIPO_MOVTO_E.equalsIgnoreCase(operacionSic.getTipoOperacion().trim())
//        		&& !DateUtils.isSameDay(operacionSic.getFechaLiquidacion(), dateUtilService.getCurrentDate()) ) {
//            throw new BusinessException(errorResolver.getMessage("J0096"));
//        }
        
        /*  
		//validaciones de las fechas para operacionde tipo Entrega
        if(TIPO_MOVTO_E.equalsIgnoreCase(operacionSic.getTipoOperacion().trim())){        	  
            if (operacionSic.getFechaLiquidacion().compareTo(dateUtilService.agregarDiasHabiles(dateUtilService.getCurrentDate(), 8))>0){
    				throw new BusinessException(errorResolver.getMessage("J0090"));//*********aqui falta agregar el tipo de operacion entrega
    		}
        }*/		
		
        /*Modificacion 10/09/2012 se agrega validacion para comparar que la fecha de liquidacion no sea mayor a 8 dias habiles*/
       /* if (operacionSic.getFechaLiquidacion().compareTo(dateUtilService.agregarDiasHabiles(dateUtilService.getCurrentDate(), 8))>0
        		&& operacionSic.getTipoOperacion().equals(TIPO_MOVTO_E) ){
			throw new BusinessException(errorResolver.getMessage("J0090"));//*********aqui falta agregar el tipo de operacion entrega
		}*/
        /* Modificacion 25/05/2012 se agrega validacion que no permita guardar operaciones con fecha de liquidacion, 
         * notificacion u operacion menores a la fecha actual
         * */
        /*if (operacionSic.getFechaLiquidacion().compareTo(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()))< 0 ) {
        	
			throw new BusinessException(errorResolver.getMessage("J0091"));
		}
        
        if (operacionSic.getFechaOperacion().compareTo(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()))< 0){
			throw new BusinessException(errorResolver.getMessage("J0092"));
		}
       
        if (operacionSic.getFechaLiquidacion().compareTo(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()))> 0 
        		&& operacionSic.getTipoOperacion().equals(TIPO_MOVTO_R)){
			throw new BusinessException(errorResolver.getMessage("J0094"));
		}*/
       
        
        
        /* Valida la cantidad de titulos */
        if (operacionSic.getCantidadTitulos() == null) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la cantidad de titulos"));
        }
        if (operacionSic.getCantidadTitulos().compareTo(BigInteger.valueOf(0l)) <= 0) {
            throw new BusinessException(errorResolver.getMessage("J0004"));
        }
        
        /* Valida cuenta y decripcion contraparte */
        if (StringUtils.isBlank(operacionSic.getCuentaContraparte())) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la cuenta contraparte"));
        }
        if (StringUtils.isBlank(operacionSic.getDescContraparte())) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la descripcion contraparte"));
        }
        
        /* Valida el numero y nonbre de la cuenta del beneficiario */
        if (StringUtils.isBlank(operacionSic.getNumCtaBenef())) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el numero de cuenta del benficiario"));
        }
        if (StringUtils.isBlank(operacionSic.getNomCtaBenef())) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"el nombre de cuenta del benficiario"));
        }
        
        /* Valida agente */
        if (operacionSic.getCuentaNombrada() == null 
                || cuentaNombradaDao.getByPk(CuentaNombrada.class, operacionSic.getCuentaNombrada().getIdCuentaNombrada()) == null) {
            throw new BusinessException(errorResolver.getMessage("J0003", (Object)"traspasante"));
        }
        /*
         *se agrega la validacion para las cuentas validas en la captura de operacionesSic
         */
        if(!this.esCuentaValida(ConvertBO2VO.crearAgenteVO(operacionSic.getCuentaNombrada()))){
        	throw new BusinessException(errorResolver.getMessage("J0095"));
        	
        }
        
        /* Valida emision */
        if (operacionSic.getEmision() == null 
                || emisionDao.getByPk(Emision.class, operacionSic.getEmision().getIdEmision()) == null) {
            throw new BusinessException(errorResolver.getMessage("J0001"));
        }
        
        /* Valida custodio */
        if (operacionSic.getCatBic() == null 
                || catBicDao.getByPk(CatBic.class, operacionSic.getCatBic().getIdCatbic()) == null) {
            throw new BusinessException(errorResolver.getMessage("J0036"));
        }
        
        /* Valida depositante-liquidador */
        if (operacionSic.getSicDetalle() == null 
                || sicDetalleDao.getByPk(
                        SicDetalle.class, operacionSic.getSicDetalle().getIdSicDetalle()) == null) {
            throw new BusinessException(errorResolver.getMessage("J0038"));
        }
        
        /* Valida la boveda */
        if (operacionSic.getBoveda() == null 
                || bovedaDao.getByPk(Boveda.class, operacionSic.getBoveda().getIdBoveda()) == null) {
            throw new BusinessException(errorResolver.getMessage("J0050"));
        }
        
        /* Valida el importe y la divisa cuando es contrapago */
        if (TRASPASO_CONTRA.equalsIgnoreCase(operacionSic.getTipoTraspaso())) {
            if (operacionSic.getDivisa() == null) {
//              TODO Validar la divisa con el catalogo DivisaDao
            }
            if (operacionSic.getImporte() == null || operacionSic.getImporte().compareTo(new BigDecimal(CERO_STRING)) <= 0) {
                throw new BusinessException(errorResolver.getMessage("J0034"));
            }
        }
        
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService#validaOperacionSicConsulta(com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic)
     */
    public void validaOperacionSicConsulta(OperacionSic operacionSic) throws BusinessException {

        log.info("Entrando a validaOperacionSicConsulta");
        
        validatorService.validaDTONoNulo(operacionSic, "OperacionSic");
        if (operacionSic.getFechaConsulta() == null
                || operacionSic.getFechaConsulta().length != 2) {
            throw new BusinessException(errorResolver.getMessage("J0041"));
        }
        if (operacionSic.getFechaConsulta()[0] == null) {
            throw new BusinessException(errorResolver.getMessage("J0006"));
        }
        if (operacionSic.getFechaConsulta()[1] == null) {
            throw new BusinessException(errorResolver.getMessage("J0007"));
        }
        
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService#validaTraspasoLibrePagoVO(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO)
     */
    public void validaTraspasoLibrePagoVO(TraspasoLibrePagoVO tlpVO) throws BusinessException {
        
        log.info("Entrando a validaTraspasoLibrePagoVO()");
        
        /* Valida tlpVO */
        validatorService.validaDTONoNulo(tlpVO, "TraspasoLibrePagoVO");
        
        /* Valida agente traspasante */
        validatorService.validarClaveTipo(tlpVO.getIdFolioCtaTraspasante(), "agente traspasante");
        if (tlpVO.getIdFolioCtaTraspasante().length() > 9 || tlpVO.getIdFolioCtaTraspasante().length() < 8) {
            throw new BusinessException(errorResolver.getMessage("J0003", (Object)"traspasante"));
        }
        if (!validatorService.validaAgente(new AgenteVO(
                tlpVO.getIdFolioCtaTraspasante().substring(0, 2), 
                tlpVO.getIdFolioCtaTraspasante().substring(2, 5), 
                tlpVO.getIdFolioCtaTraspasante().substring(5)), true)) {
            throw new BusinessException(errorResolver.getMessage("J0003", (Object)"traspasante"));
        }
        
        /* Valida agente receptor */
        validatorService.validarClaveTipo(tlpVO.getIdFolioCtaReceptora(), "agente receptor");
        if (tlpVO.getIdFolioCtaReceptora().length() > 9 || tlpVO.getIdFolioCtaReceptora().length() < 8) {
            throw new BusinessException(errorResolver.getMessage("J0003", (Object)"receptor"));
        }
        if (!validatorService.validaAgente(new AgenteVO(
                tlpVO.getIdFolioCtaReceptora().substring(0, 2), 
                tlpVO.getIdFolioCtaReceptora().substring(2, 5), 
                tlpVO.getIdFolioCtaReceptora().substring(5)), true)) {
            throw new BusinessException(errorResolver.getMessage("J0003", (Object)"receptor"));
        }
        
        /* Valida emision */
        if (!validatorService.validaEmision(new EmisionVO(tlpVO.getTipoValor(), tlpVO.getEmisora(), tlpVO.getSerie(), tlpVO.getCupon()))) {
            throw new BusinessException(errorResolver.getMessage("J0001"));
        }
        
        /* Valida cantidad de titulos */
        if (tlpVO.getCantidadTitulos() == null) {
            throw new BusinessException(errorResolver.getMessage("J0026", (Object)"la cantidad de titulos"));
        }
        if (tlpVO.getCantidadTitulos().compareTo(Long.valueOf(0l)) <= 0) {
            throw new BusinessException(errorResolver.getMessage("J0004"));
        }
        
    }

	public void validaStatementDivint(StatementDivint statement) throws BusinessException {
		validatorService.validaDTONoNulo(statement, " Statement ");
		
		//valida custodio
		if ( statement.getCustodio() == null ){
			throw new BusinessException( errorResolver.getMessage("J9998", new Object[] {}) );
		}
		
		// Se valida la institucion
		validatorService.validaDTONoNulo(statement.getInstitucion(), " Institucion ");
		statement.setIdInstitucion(statement.getInstitucion().getIdInstitucion());
		
		// Se valida la emision
		if(statement.getEmision() == null) {
			throw new BusinessException( errorResolver.getMessage("J0082", new Object[] {}) );
		}
				
		// Se valida que la emision tenga tipo valor 1A o 1I
//		if(statement.getEmision().getInstrumento() == null ||
//				( !StringUtils.equals(TIPO_VALOR_SIC, statement.getEmision().getInstrumento().getClaveTipoValor()) &&
//				!StringUtils.equals(TIPO_VALOR_SIC_INDICE, statement.getEmision().getInstrumento().getClaveTipoValor()))) {
//			throw new BusinessException( errorResolver.getMessage("J0079", new Object[] {}) );
//		}
//		// Se valida que la serie tenga serie *
//		if(!StringUtils.equals(SERIE_SIC_EUA, statement.getEmision().getSerie())) {
//			throw new BusinessException( errorResolver.getMessage("J0080", new Object[] {}) );
//		}
		statement.setIdEmision(statement.getEmision().getIdEmision()); 
	
		// Fechas de pago y de registro
		validatorService.validaDTONoNulo(statement.getFechaPago(), " Fecha de Pago ");
		validatorService.validaDTONoNulo(statement.getFechaRegistro(), " Fecha de Registro ");
		// Fecha de creacion del registro en BD de Datos
		validatorService.validaDTONoNulo(statement.getFechaCreacion(), " Fecha de Creacion ");
		// Indica si el archvio se carga a la institucion
		validatorService.validaDTONoNulo(statement.getCargoInstitucion(), " Cargo a Institucion ");
		// Datos propios del beneficiario
		statement.setNombre( validatorService.validarClaveTipoLongitud(statement.getNombre(), " Nombre ", 1024) );
		if(!statement.getCargoInstitucion()) {
			// Datos de la direccion
			statement.setDireccion( validatorService.validarClaveTipoLongitud(statement.getDireccion(), " Direccion ", 1024) );
			statement.setPais( validatorService.validarClaveTipoLongitud(statement.getPais(), " Pais ", 100) );
		}
		// Validar el formato primero que sea el formato indicado y luego la longitud
		if(!BeneficiariosConstantes.FORMATO_W8_BEN.equals(statement.getFormato()) &&
				!BeneficiariosConstantes.FORMATO_W8_IMY.equals(statement.getFormato()) &&
				!BeneficiariosConstantes.FORMATO_W9.equals(statement.getFormato())) {
			throw new BusinessException( errorResolver.getMessage("J0081", new Object[] {}) );
		}
		statement.setFormato( validatorService.validarClaveTipoLongitud(statement.getFormato(), " Formato ", 5) );
		
		// Se valida el Tipo de beneficiario
		validatorService.validaDTONoNulo(statement.getTipoBeneficiario(), " Tipo de Beneficiario ");
		statement.setIdTipoBeneficiario(statement.getTipoBeneficiario().getIdTipoBeneficiario());
		
		// Las cantidades de los montos del beneficiario
		validatorService.validaDTONoNulo(statement.getPorcentajeRetencion(), " Porcentaje de Retencion ");
		validatorService.validaDTONoNulo(statement.getNumeroTitulos(), " Numero de Titulos ");
		validatorService.validaDTONoNulo(statement.getProporcion(), " Proporcion ");
		validatorService.validaDTONoNulo(statement.getDividendo(), " Dividendo ");
		validatorService.validaDTONoNulo(statement.getImpuesto(), " Impuesto "); //todo modificacion
		validatorService.validaDTONoNulo(statement.getDividendoNeto(), " Dividendo Neto ");
		validatorService.validaDTONoNulo(statement.getCustodio(), " Custodio ");
		 
		
		
		
		// Archivo de origen del registro
		statement.setArchivoOrigen( validatorService.validarClaveTipoLongitud(statement.getArchivoOrigen(), " Archivo de Origen ", 200) );
		// Campos opcionales
		statement.setAdp( validatorService.validarLongitud(statement.getAdp(), "Adp", 20) );
		statement.setRfc( validatorService.validarLongitud(statement.getRfc(), "RFC", 20) );
		statement.setTaxPayerNumber( validatorService.validarLongitud(statement.getTaxPayerNumber(), "Tax Payer Id Number", 15) );
		statement.setStatusOwner( validatorService.validarLongitud(statement.getStatusOwner(), "Status of Underlying Owner", 50) );
		statement.setArchivoZip( validatorService.validarLongitud(statement.getArchivoZip(), "Archivo ZIP", 200) );

	}

    /* Setters y Getters */
    
    /**
     * @param errorResolver the errorResolver to set
     */
    public void setErrorResolver(MessageResolver errorResolver) {
        this.errorResolver = errorResolver;
    }

    /**
     * @param validatorService the validatorService to set
     */
    public void setValidatorService(com.indeval.portaldali.middleware.services.util.ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    /**
     * @param emisionDao the emisionDao to set
     */
    public void setEmisionDao(EmisionDao emisionDao) {
        this.emisionDao = emisionDao;
    }

    /**
     * @param detalleCustodio the detalleCustodio to set
     */
    public void setDetalleCustodio(List<String> detalleCustodio) {
        this.detalleCustodio = detalleCustodio;
    }

    /**
     * @return the detalleCustodio
     */
    public List<String> getDetalleCustodio() {
        return detalleCustodio;
    }

    /**
     * @param catBicDao the catBicDao to set
     */
    public void setCatBicDao(CatBicDao catBicDao) {
        this.catBicDao = catBicDao;
    }

    /**
     * @param cuentaNombradaDao the cuentaNombradaDao to set
     */
    public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
        this.cuentaNombradaDao = cuentaNombradaDao;
    }

    /**
     * @param sicDetalleDao the sicDetalleDao to set
     */
    public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
        this.sicDetalleDao = sicDetalleDao;
    }

    /**
     * @param bovedaDao the bovedaDao to set
     */
    public void setBovedaDao(BovedaDao bovedaDao) {
        this.bovedaDao = bovedaDao;
    }

    /**
     * @param dateUtilService the dateUtilService to set
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

	/**
	 * 
	 * @param institucionDao
	 */
	public void setInstitucionDao(InstitucionDao institucionDao) {
        this.institucionDao = institucionDao;
    }
}
