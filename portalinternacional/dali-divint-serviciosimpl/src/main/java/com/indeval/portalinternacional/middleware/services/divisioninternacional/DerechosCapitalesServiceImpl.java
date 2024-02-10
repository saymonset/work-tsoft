/**
 * Bursatec - Portal Internacional Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.Date;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.services.validador.ValidadorDerechosCapital;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.persistence.dao.capitales.ConsultaHistoricoCapitalesDao;

/**
 * Clase que implementa la interfaz DerechosCapitalesService
 * 
 * @author Pablo Balderas
 */
public class DerechosCapitalesServiceImpl implements DerechosCapitalesService {

    /** Validador */
    private ValidadorDerechosCapital validadorDerechosCapital;
    
    /** DAO para realizar las consultas */
    private ConsultaHistoricoCapitalesDao consultaHistoricoCapitalesDao;

    /* SE COBRA ESTA CONSULTA
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService#consultaDetalleDerechosCapital(com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO, boolean)
     */
    public PaginaVO consultaDetalleDerechosCapital(PaginaVO paginaVO, final ParamConsultaDetalleEjerDerCapTO parametros, 
    		Boolean esExportacion, Boolean debeDejarLog) throws BusinessException {
    	validadorDerechosCapital.validarParametrosConsultaDerechosBeneficiarios(parametros);
    	prepararFechas(parametros);
        paginaVO = consultaHistoricoCapitalesDao.consultaHistoricoCapitales(paginaVO, parametros, esExportacion);
        return paginaVO;
    }

    /*SE COBRA ESTA CONSULTA
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService#consultaDetalleDerechosCapitalCuenta(com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO, boolean)
     */
    public PaginaVO consultaDetalleDerechosCapitalCuenta(PaginaVO paginaVO, ParamConsultaDetalleEjerDerCapTO parametros,
    		Boolean esExportacion, Boolean debeDejarLog) throws BusinessException {
    	validadorDerechosCapital.validarParametrosConsultaDerechosCuenta(parametros);
    	prepararFechas(parametros);
    	return consultaHistoricoCapitalesDao.consultaHistoricoCapitalesCuenta(paginaVO, parametros, esExportacion);
    }
    
    /**
     * Método que prepara las fechas de corte y pago de acuerdo a las reglas de negocio.
     * @param parametros Objeto que contiene las fechas para la consulta.
     */
    private void prepararFechas(ParamConsultaDetalleEjerDerCapTO parametros) {
    	// Caso 1: Si no se capturo ninguna fecha, se establecen la fechas de pago
    	// Fecha final = fecha actual
    	// Fecha inicial = fecha actual - 90 dias
    	if(parametros.getFechaCorteInicial() == null && parametros.getFechaCorteFinal() == null &&
    			parametros.getFechaPagoInicial() == null && parametros.getFechaPagoFinal() == null) {
    		parametros.setFechaPagoFinal(new Date());
    		parametros.setFechaPagoInicial(DateUtils.addDays(new Date(), -90));
    	}
    	// Caso 2: Si se captura la fecha inicial (corte o pago) y no se captura la fecha final (corte o pago)
    	// la fecha final será igual a la fecha inicial + 90 días
    	if(parametros.getFechaCorteInicial() != null && parametros.getFechaCorteFinal() == null) {
    		parametros.setFechaCorteFinal(DateUtils.addDays(parametros.getFechaCorteInicial(), 90));
    	}
    	if(parametros.getFechaPagoInicial() != null && parametros.getFechaPagoFinal() == null) {
    		parametros.setFechaPagoFinal(DateUtils.addDays(parametros.getFechaPagoInicial(), 90));
    	}
    	// Caso 3: Si no se captura la fecha inicial (corte o pago) y se captura la fecha final (corte o pago)
    	// la fecha inicial será igual a la fecha inicial - 90 días
    	if(parametros.getFechaCorteInicial() == null && parametros.getFechaCorteFinal() != null) {
    		parametros.setFechaCorteInicial(DateUtils.addDays(parametros.getFechaCorteFinal(), -90));  		
    	}
    	if(parametros.getFechaPagoInicial() == null && parametros.getFechaPagoFinal() != null) {
    		parametros.setFechaPagoInicial(DateUtils.addDays(parametros.getFechaPagoFinal(), -90));
    	}
    }

    /**
     * Método para establecer el atributo validadorDerechosCapital
     * 
     * @param validadorDerechosCapital El valor del atributo validadorDerechosCapital a establecer.
     */
    public void setValidadorDerechosCapital(final ValidadorDerechosCapital validadorDerechosCapital) {
        this.validadorDerechosCapital = validadorDerechosCapital;
    }

    /** @param consultaHistoricoCapitalesDao to be set in this.consultaHistoricoCapitalesDao */
    public void setConsultaHistoricoCapitalesDao(
            final ConsultaHistoricoCapitalesDao consultaHistoricoCapitalesDao) {
        this.consultaHistoricoCapitalesDao = consultaHistoricoCapitalesDao;
    }

}
