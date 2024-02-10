package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;


import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Derecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FactorSimulacionMav;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.ws.DerechosCapitalesWSService;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;


@Stateless(name = "ejb.controlDerechosService", mappedName = "ejb.controlDerechosService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(ControlDerechosService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ControlDerechosServiceBean  implements ControlDerechosService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ControlDerechosServiceBean.class);
	
	@Autowired
	private ControlDerechosService controlDerechosService;

    @Autowired
    private DerechosCapitalesWSService derechosCapitalesClienteWS;
	

    public PaginaVO getDerechosUsandoVista(ConsultaDerechosParam param, PaginaVO paginaVO, boolean all)
            throws BusinessException {      
        return controlDerechosService.getDerechosUsandoVista(param, paginaVO, all);
    }

	public PaginaVO getDerechos(ConsultaDerechosParam param, PaginaVO paginaVO)
			throws BusinessException {		
		return controlDerechosService.getDerechos(param,paginaVO);		
	}

	public List<DerechoBeneficiario> getBenefDerechoXCuenta(Long idDerecho,Long[] idCuentas)throws BusinessException {
		return controlDerechosService.getBenefDerechoXCuenta(idDerecho, idCuentas);
	}

	public List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizadoSimplificado(final Long idDerecho, final Long[] idCuentas,Integer idTipoInstitucion,String folioInstitucion)
    		throws BusinessException{
		return controlDerechosService.getBenefDerechoXCuentaOptimizadoSimplificado(idDerecho, idCuentas,idTipoInstitucion,folioInstitucion);
	}
	
	
	
	
    public List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizado(Long idDerecho, Long[] idCuentas) 
            throws BusinessException {
        return controlDerechosService.getBenefDerechoXCuentaOptimizado(idDerecho, idCuentas);
    }

	public List<DerechoBeneficiario> getBenefDerechoXCuenta(Long idDerecho,Long[] idCuentas,Integer idTipoInstitucion,String folioInstitucion)throws BusinessException{
		return controlDerechosService.getBenefDerechoXCuenta(idDerecho,idCuentas,idTipoInstitucion,folioInstitucion);
	}

    public List<VConsultaBeneficiarioDerechosCuenta> getBenefDerechoXCuentaOptimizado(Long idDerecho, Long[] idCuentas, 
                                                                                      Integer idTipoInstitucion, 
                                                                                      String folioInstitucion) throws BusinessException{
        return controlDerechosService.getBenefDerechoXCuentaOptimizado(idDerecho,idCuentas,idTipoInstitucion,folioInstitucion);
    }

	public void deleteBeneficiarioDerecho(Long idBeneficiarioDerecho)
			throws BusinessException {
		controlDerechosService.deleteBeneficiarioDerecho(idBeneficiarioDerecho);		
	}

	public boolean existeBeneficiarioDerecho(Long idBeneficiario,Long idCuentaNombrada, Long idDerechoCapital)throws BusinessException {
		return controlDerechosService.existeBeneficiarioDerecho(idBeneficiario, idCuentaNombrada, idDerechoCapital);
	}

	public Long getCuentaDerechoByUoi(Long idDerecho, String cuenta,Institucion institucion, Long idBeneficiario)throws BusinessException {
		return controlDerechosService.getCuentaDerechoByUoi(idDerecho, cuenta, institucion, idBeneficiario);
	}

	public void agregaBeneficiarioDerecho(DerechoBeneficiario derechoBeneficiario)throws BusinessException {
		controlDerechosService.agregaBeneficiarioDerecho(derechoBeneficiario);		
	}

	public void actualizaPosicionBeneficiario(Long idBeneficiarioDerecho,
			Long posicion) throws BusinessException {
		controlDerechosService.actualizaPosicionBeneficiario(idBeneficiarioDerecho, posicion);
		
	}

	public PaginaVO getCuentasDerecho(Long idDerecho, PaginaVO paginaVO)
			throws BusinessException {		
		return controlDerechosService.getCuentasDerecho(idDerecho, paginaVO);
	}

    public PaginaVO getCuentasDerechoOptimizado(Long idDerecho, PaginaVO paginaVO) throws BusinessException {      
        return controlDerechosService.getCuentasDerechoOptimizado(idDerecho, paginaVO);
    }

	public PaginaVO getCuentasDerecho(Long idDerechoCapital,
			Integer idTipoInstitucion, String folioInstitucion,
			PaginaVO paginaVO) throws BusinessException {
		return controlDerechosService.getCuentasDerecho(idDerechoCapital, idTipoInstitucion, folioInstitucion, paginaVO);
	}

    public PaginaVO getCuentasDerechoOptimizado(Long idDerechoCapital, Integer idTipoInstitucion, String folioInstitucion,
                                                PaginaVO paginaVO) throws BusinessException {
        return controlDerechosService.getCuentasDerechoOptimizado(idDerechoCapital, idTipoInstitucion, folioInstitucion, 
                                                                  paginaVO);
    }

	public Beneficiario getBeneficiarioCuentaByUoi(String uoi,
			Long idCuentaNombrada) throws BusinessException {		
		return controlDerechosService.getBeneficiarioCuentaByUoi(uoi, idCuentaNombrada);
	}

	public void agregaBeneficiariosDerecho(List<DerechoBeneficiario> lstBeneficiarios)throws BusinessException {
		controlDerechosService.agregaBeneficiariosDerecho(lstBeneficiarios);
		
	}

	public Long getCuentaNombradaOfCustodio(Long idEmision) {		
		return controlDerechosService.getCuentaNombradaOfCustodio(idEmision);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void enviarSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto dto) throws BusinessException {
	    LOG.info("####### Entrando a enviarSimulacionDerechoCapitalInter() de EJB de Internacional ...");
	    try {
	        LOG.info("####### Enviando simulacion al MAV...");
	        boolean simulacionExitosa = this.derechosCapitalesClienteWS.doCalculaSimulacionDerechoCapitalInter(dto);
            LOG.info("####### Terminando de enviar simulacion al MAV...");
            if (!simulacionExitosa) {
	            throw new RuntimeException("Fall\u00F3 la carga de cuentas y grupos de retenci\u00F3n en MAV.");
	        }
	    } catch (Exception ex) {  
	        LOG.error(ex.getMessage());
	        throw new BusinessException(ex.getMessage(), ex);
	    }
	}

	public void actualizarEstatusDerecho(Long idDerecho,Integer idEstatusDerecho,Boolean procesado) throws BusinessException {
		controlDerechosService.actualizarEstatusDerecho(idDerecho, idEstatusDerecho,procesado);	
	}
	
	public void generaMensajeHistoricoCapitales(Long idDerecho){
		controlDerechosService.generaMensajeHistoricoCapitales(idDerecho);
	}

	public void pagarDerecho(Long idDerecho) {
		controlDerechosService.pagarDerecho(idDerecho);		
	}

	public boolean existeDerechoNoCortado(Long idDerecho)throws BusinessException {		
		return controlDerechosService.existeDerechoNoCortado(idDerecho);
	}

	public boolean requireBeneficiariosDerecho(EmisionVO emisionVO,Long idEmisionDerecho) {		
		return controlDerechosService.requireBeneficiariosDerecho(emisionVO,idEmisionDerecho);
	}

    public boolean requireBeneficiariosDerechoOptimizado(EmisionVO emisionVO,Long idEmisionDerecho) {     
        return controlDerechosService.requireBeneficiariosDerechoOptimizado(emisionVO,idEmisionDerecho);
    }

	public HorarioBeneficiario getHorarioBenefDerecho(EmisionVO emisionVO,Long idEmisionDerecho) {		
		return controlDerechosService.getHorarioBenefDerecho(emisionVO, idEmisionDerecho);
	}

    public HorarioBeneficiario getHorarioBenefDerechoOptimizado(EmisionVO emisionVO,Long idEmisionDerecho) {      
        return controlDerechosService.getHorarioBenefDerechoOptimizado(emisionVO, idEmisionDerecho);
    }

	public Float getPorcentajeRetDefault(EmisionVO emisionVO,Long idEmisionDerecho) {
		return controlDerechosService.getPorcentajeRetDefault(emisionVO, idEmisionDerecho);
	}

    public Float getPorcentajeRetDefaultOptimizado(EmisionVO emisionVO,Long idEmisionDerecho) {
        return controlDerechosService.getPorcentajeRetDefaultOptimizado(emisionVO, idEmisionDerecho);
    }

	public void agregaBeneficiarioDefault(Long idDerecho, Integer idEstado)throws BusinessException {
		controlDerechosService.agregaBeneficiarioDefault(idDerecho, idEstado);
	}

	public boolean existeBeneficiarioDefault(Long idDerecho) {
		return controlDerechosService.existeBeneficiarioDefault(idDerecho);
	}

	public Float getPorcentajeRetFromDerecho(Long idDerechoCapital) {		
		return controlDerechosService.getPorcentajeRetFromDerecho(idDerechoCapital);
	}

	public HorarioBeneficiario getHorarioInstitucion(EmisionVO emisionVO,Long idEmisionDerecho, Integer idInst, String folioInst) {		
		return controlDerechosService.getHorarioInstitucion(emisionVO, idEmisionDerecho, idInst, folioInst);
	}

    public HorarioBeneficiario getHorarioInstitucionOptimizado(EmisionVO emisionVO,Long idEmisionDerecho, Integer idInst, String folioInst) {     
        return controlDerechosService.getHorarioInstitucionOptimizado(emisionVO, idEmisionDerecho, idInst, folioInst);
    }

	public Derecho getDerecho(ConsultaDerechosParam param, int tipo, int subtipo)throws BusinessException {		
		return controlDerechosService.getDerecho(param, tipo, subtipo);
	}

	public CuentaNombrada getCuenta(String cuenta, Integer idTipoInst,String folioInstitucion) {		
		return controlDerechosService.getCuenta(cuenta, idTipoInst, folioInstitucion);
	}

	public DerechoBeneficiario getDerechoNoCortado(Long idDerecho)throws BusinessException {		
		return controlDerechosService.getDerechoNoCortado(idDerecho);
	}

	public void guardaFactorSimulacion(FactorSimulacionMav simulacionMav) {
		controlDerechosService.guardaFactorSimulacion(simulacionMav);
		
	}

	public FactorSimulacionMav getFactorSimulacion(Long idDerechoCapital) {		
		return controlDerechosService.getFactorSimulacion(idDerechoCapital);
	}

}
