package com.indeval.portaldali.middleware.services.mercadocapitales.ejb;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.mercadocapitales.ConfirmaTraspasoContraPagoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.ConfirmaTraspasoVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaSocInvVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaTotalVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoContraPagoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadoCuentaSociedadesInvParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadocuentaMercadoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetOperacionPendienteIncumplidaParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetPosicionValorMerCapParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaCapitalesParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaCapitalesVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaDetalleCapitalesVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionLiqFuturoTotalVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.PosicionValorTotalVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.TraspasoCapitalesVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.TraspasosContraPagosVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

@Stateless(name = "ejb.mercadoCapitales", mappedName = "ejb.mercadoCapitales")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(MercadoCapitalesService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MercadoCapitalesBean implements MercadoCapitalesService {
	
	@Autowired
	private MercadoCapitalesService mercadoCapitalesService = null;

	public MercadoCapitalesService getMercadoCapitalesService() {
		return mercadoCapitalesService;
	}

	public void setMercadoCapitalesService(
			MercadoCapitalesService mercadoCapitalesService) {
		this.mercadoCapitalesService = mercadoCapitalesService;
	}
	
	
	public BigInteger getCapturaTraspaso(GetCapturaTraspasoParams params) throws BusinessException {
		
		return mercadoCapitalesService.getCapturaTraspaso(params);
	}

    
    public PosicionValorTotalVO[] getPosicionValorPlain(GetPosicionValorMerCapParams params)
            throws BusinessException {
    	return mercadoCapitalesService.getPosicionValorPlain(params);
    }

    /**
     * Caso de uso CUMC47-14 Estado de Cuenta de Mercado de Capitales
     *
     * @param params
     * @return Un arreglo con objetos de tipo EstadoCuentaMercadoCapitalTotalVO
     * @throws BusinessException
     */
    public EstadoCuentaTotalVO[] getNuevoEstadoCuentaMercadoCapital(GetEstadocuentaMercadoParams params)
            throws BusinessException {
    	return mercadoCapitalesService.getNuevoEstadoCuentaMercadoCapital(params);
    }

    /**
     * Caso de uso CUMC47-16 Operaciones Pendientes e Incumplidas
     *
     * @param params
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO getOperacionPendienteIncumplida(GetOperacionPendienteIncumplidaParams params)
            throws BusinessException {
    	return mercadoCapitalesService.getOperacionPendienteIncumplida(params);
    }

    /**
     * Caso de uso CUMC47-21 Operaciones a Liquidar Plazo Futuro
     *
     * @param agente
     * @param tipoFecha
     * @param fecha
     * @param paginaVO
     * @return OperacionLiqFuturoTotalVO
     * @throws BusinessException
     */
    public OperacionLiqFuturoTotalVO getOperacionLiqFuturo(AgenteVO agente, String tipoFecha, Date fecha,
            PaginaVO paginaVO) throws BusinessException {
    	return mercadoCapitalesService.getOperacionLiqFuturo(agente, tipoFecha, fecha, paginaVO);
    }

    /**
     * Caso de uso CUMC47-26 ConfirmacionTraspasosMiscelaneaFiscal
     *
     * @param agenteFirmado
     * @return Un arreglo con objetos de tipo ConfirmaTraspasoVO
     */
    public ConfirmaTraspasoVO[] getConfirmacionTraspaso(AgenteVO agenteFirmado) {
    	return mercadoCapitalesService.getConfirmacionTraspaso(agenteFirmado);
    }

    /**
     * Caso de uso CUMC47-02 Captura de traspasos de Mercado de Capitales Guarda
     * informaci&oacute;n en la tabla bdcaptal..mdintran
     *
     * @param params -
     *            objeto que contiene los siguientes parametros: traspasante -
     *            Id, Folio y Cuenta de la instituci&oacute;n. receptor - Id, Folio y
     *            Cuenta de la instituci&oacute;n. emision - Objeto de tipo emision que
     *            contiene el tipo de valor, emision, serie y cupon. cantidad -
     *            Cantidad por la que se realiza el traspaso. cveReporto - Clave
     *            Reporto. precioTitulo - Precio por Titulo. fechaLiquidacion -
     *            Fecha de Liquidaci&oacute;n de la Operaci&oacute;n. liquidacion - Es el
     *            plazo de liquidaci&oacute;n del traspaso usuario - Usuario Firmado.
     *            nombreUsuario - Nombre del Usuario Firmado.
     * @return Integer - folio control
     * @throws BusinessException
     */
    public Integer capturaTraspasoContraPago(GetCapturaTraspasoContraPagoParams params)
            throws BusinessException {
    	return mercadoCapitalesService.capturaTraspasoContraPago(params);
    }

    /**
     * Caso de uso <b>CUEC 01-01 Estado de Cuenta &uacute;nico</b>, consulta el
     * estado de cuenta &uacute;nico
     *
     * @param params
     * @return EstadoCuentaUnicoVO[]
     * @throws BusinessException
     */
    public EstadoCuentaUnicoVO[] getEstadoCuentaUnico(EstadoCuentaUnicoParams params)
            throws BusinessException {
    	return mercadoCapitalesService.getEstadoCuentaUnico(params);
    }


    /**
     * Caso de uso <b>CUEC 01-01 Estado de Cuenta &uacute;nico</b>, consulta el
     * estado de cuenta &uacute;nico
     *
     * @param params
     * @return String[]
     * @throws BusinessException
     */
    public String[] getCuentasEstadoCuentaUnico(EstadoCuentaUnicoParams params) throws BusinessException {
    	return mercadoCapitalesService.getCuentasEstadoCuentaUnico(params);
    }

    /**
     * Regresa la lista de registros de traspasos contrapago en donde la fecha
     * de liquidacion el mayor o igual a la fecha actual del sistema, en el
     * estado indicado <br>
     * <b>C</b> por confirmar<br>
     * <b>P</b> pendiente de liquida<br>
     * <b>L</b> liquidado<br>
     * si no se pasa el estado el servicio regresa los tres anteriores
     *
     * @param agenteFirmado
     *            id y folio del agente firmado
     * @param estado
     *            estado del traspaso
     * @return Arreglo del tipo TraspasosContraPagosVO
     * @throws BusinessException
     */
    public TraspasosContraPagosVO[] getListaConfirmacionTraspasoContraPago(AgenteVO agenteFirmado,
            String estado) throws BusinessException {
    	return mercadoCapitalesService.getListaConfirmacionTraspasoContraPago(agenteFirmado, estado);
    }

    /**
     * Realiza la confirmacion de los registros de la captura de traspaso contra
     * pago
     *
     * @param params
     * @return True - si se actualizaron correctamente todos los registros
     *         solicitados
     * @throws BusinessException
     */
    public boolean confirmaTraspasoContraPago(ConfirmaTraspasoContraPagoParams[] params)
            throws BusinessException {
    	return mercadoCapitalesService.confirmaTraspasoContraPago(params);
    }

    /**
     * CUEC 01-02 Archivo de Conciliacion Realiza la consulta de conciliacion de
     * valores (de dinero y capitales) y genera un archivo plano en caso de
     * indicarlo
     *
     * @param agenteFirmado
     * @param emision
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO archivoConciliacion(AgenteVO agenteFirmado, EmisionVO emision, PaginaVO pagina)
            throws BusinessException {
    	return mercadoCapitalesService.archivoConciliacion(agenteFirmado, emision, pagina);
    }

    /**
     * Captura de traspasos Libres de Pago Fecha Valor Guarda informaci&oacute;n en la
     * tabla bdcaptal..mdintran
     *
     * @param params -
     *            objeto que contiene los siguientes parametros: traspasante -
     *            Id, Folio y Cuenta de la instituci&oacute;n. receptor - Id, Folio y
     *            Cuenta de la instituci&oacute;n. emision - Objeto de tipo emision que
     *            contiene el tipo de valor, emision, serie y cupon. cantidad -
     *            Cantidad por la que se realiza el traspaso. cveReporto - Clave
     *            Reporto. precioTitulo - Precio por Titulo. fechaLiquidacion -
     *            Fecha de Liquidaci&oacute;n de la Operaci&oacute;n. liquidacion - Es el
     *            plazo de liquidaci&oacute;n del traspaso usuario - Usuario Firmado.
     *            nombreUsuario - Nombre del Usuario Firmado.
     * @return Integer - folio control
     * @throws BusinessException
     */
    public Integer capturaTraspasoTLPFechaValor(GetCapturaTraspasoContraPagoParams params)
            throws BusinessException {
    	return mercadoCapitalesService.capturaTraspasoContraPago(params);
    }

    /**
     * CUEC 01-02 Archivo de Conciliacion Realiza la consulta del detalle de la
     * conciliacion de valores (de dinero y capitales) y genera un archivo plano
     * en caso de indicarlo
     *
     * @param agente
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO archivoConciliacionMovimientos(AgenteVO agente, PaginaVO pagina)
            throws BusinessException {
    	return mercadoCapitalesService.archivoConciliacionMovimientos(agente, pagina);
    }

    /**
     * CUEC 01-02 Archivo de Conciliacion Realiza la consulta de movimientos de
     * la conciliacion de valores (de dinero y capitales) y genera un archivo
     * plano en caso de indicarlo
     *
     * @param agente
     * @param emision
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    public PaginaVO archivoConciliacionDetalle(AgenteVO agente, EmisionVO emision, PaginaVO pagina)
            throws BusinessException {
    	return mercadoCapitalesService.archivoConciliacionDetalle(agente, emision, pagina);
    }


    /**
     * @param getEstadoCuentaSociedadesInvParams
     * @return EstadoCuentaSocInvVO[]
     * @throws BusinessException
     */
    public EstadoCuentaSocInvVO[] getEstadoCuentaSociedadesInversion(
            GetEstadoCuentaSociedadesInvParams getEstadoCuentaSociedadesInvParams)
            throws BusinessException {
    	return mercadoCapitalesService.getEstadoCuentaSociedadesInversion(getEstadoCuentaSociedadesInvParams);
    }

    /**
     * Servicio para recuperar las operaciones del d&iacute;a del m&oacute;dulo de capitales.
     *
     * @param operacionDiaCapitalesParams
     * @return OperacionDiaCapitalesVO
     * @throws BusinessException
     */
    public OperacionDiaCapitalesVO getOperacionDiaCapitalesVO(
            OperacionDiaCapitalesParams operacionDiaCapitalesParams) throws BusinessException {
    	return mercadoCapitalesService.getOperacionDiaCapitalesVO(operacionDiaCapitalesParams);
    }

    /**
     * Recupera un objeto OperacionDiaDetalleCapitalesVO a partir de los objetos
     * que componen la llave primaria de bdcaptal..traspasos
     *
     * @param traspasoCapitalesVO
     * @return OperacionDiaDetalleCapitalesVO
     * @throws BusinessException
     */
    public OperacionDiaDetalleCapitalesVO getDetalleOperacionDiaCapitales(
            TraspasoCapitalesVO traspasoCapitalesVO) throws BusinessException {
    	return mercadoCapitalesService.getDetalleOperacionDiaCapitales(traspasoCapitalesVO);
    }

    /**
     * Reglas de negocio para efectuar la captura de un traspaso.
     *
     * @param capturaTraspasoParams
     * @return String
     * @throws BusinessException
     */
    public String businessRulesCapturaTraspaso(GetCapturaTraspasoParams capturaTraspasoParams)
            throws BusinessException {
    	return mercadoCapitalesService.businessRulesCapturaTraspaso(capturaTraspasoParams);
    }


    /**
     * Reglas de negocio para efectuar la captura de traspasos contra pago.
     *
     * @param params
     * @return String
     * @throws BusinessException
     */
    public String businessRulesCapturaTraspasoContraPago(GetCapturaTraspasoContraPagoParams params)
            throws BusinessException {
    	return mercadoCapitalesService.businessRulesCapturaTraspasoContraPago(params);
    }

	public List getListaEmisionSociedadesInversion(AgenteVO agente,
			EmisionVO emision) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getListaEmisionSociedadesInversionRazonSocial(AgenteVO agente,
			EmisionVO emision) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getSaldoEmisionSociedadesInversionRazonSocial(AgenteVO agente,
			String emisora, Boolean debeDejarLog) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}


}
