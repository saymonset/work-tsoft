/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.BeneficiariosPaginacionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CatBicVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;

/**
 * @author Rafael Ibarra
 *
 */
@Stateless(name = "ejb.controlBeneficiariosService", mappedName = "ejb.controlBeneficiariosService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(ControlBeneficiariosService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ControlBeneficiariosServiceBean implements ControlBeneficiariosService {

	/** Servicio de beneficiarios */
	@Autowired
	private ControlBeneficiariosService controlBeneficiariosService;

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#actualizaBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaBeneficiario(Beneficiario beneficiario)
			throws BusinessException {
		controlBeneficiariosService.actualizaBeneficiario(beneficiario);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminaBeneficiario(Long idBeneficiario)
			throws BusinessException {
		controlBeneficiariosService.eliminaBeneficiario(idBeneficiario);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiarios(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaBeneficiarios(ConsultaBeneficiariosParam param,
			PaginaVO paginaVO, Boolean isPopup) throws BusinessException {
		return controlBeneficiariosService.consultaBeneficiarios(param, paginaVO, isPopup);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneCodigoBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String obtieneCodigoBeneficiario(Beneficiario beneficiario) 
		throws BusinessException {
		return controlBeneficiariosService.obtieneCodigoBeneficiario(beneficiario);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiarioById(java.lang.Long)
	 */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Beneficiario consultaBeneficiarioById(Long idBeneficiario) throws BusinessException {
		return controlBeneficiariosService.consultaBeneficiarioById(idBeneficiario);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#insertaBeneficiario(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long insertaBeneficiario(Beneficiario beneficiario,Long idInstitucion) throws BusinessException {
		return controlBeneficiariosService.insertaBeneficiario(beneficiario,idInstitucion);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#activarBeneficiario(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void preAutorizaBeneficiario(Long idBeneficiario) throws BusinessException {
		controlBeneficiariosService.preAutorizaBeneficiario(idBeneficiario);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#activarBeneficiario(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void activarBeneficiario(Long idBeneficiario) throws BusinessException {
		controlBeneficiariosService.activarBeneficiario(idBeneficiario);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#cancelaBeneficiario(java.lang.Long) 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancelaBeneficiario(Long idBeneficiario) throws BusinessException {
		controlBeneficiariosService.cancelaBeneficiario(idBeneficiario);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaBeneficiariosHistorico(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaBeneficiariosHistorico(ConsultaHistoricoBeneficiariosParam param,PaginaVO paginaVO) throws BusinessException {
		return controlBeneficiariosService.consultaBeneficiariosHistorico(param, paginaVO);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#agregaBeneficiarioInstitucion(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void agregaBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion) throws BusinessException {
		controlBeneficiariosService.agregaBeneficiarioInstitucion(idBeneficiario, idInstitucion);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminaInstitucionBeneficiario(Long idBeneficiario, Long idInstitucion) {
		controlBeneficiariosService.eliminaInstitucionBeneficiario(idBeneficiario, idInstitucion);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneCatBic()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> obtieneCatBic() throws BusinessException {
		return controlBeneficiariosService.obtieneCatBic();
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneStatusBeneficiario()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public StatusBeneficiario[] obtieneStatusBeneficiario()
			throws BusinessException {
		return controlBeneficiariosService.obtieneStatusBeneficiario();
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneTiposBeneficiario(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List obtieneTiposBeneficiario(Long idCatBic) {
		return controlBeneficiariosService.obtieneTiposBeneficiario(idCatBic);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneTiposBeneficiario()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List obtieneTiposBeneficiario() {
		return controlBeneficiariosService.obtieneTiposBeneficiario();
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneFormato(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String obtieneFormato(Long idCuentaNombrada, Long idTipoBeneficiario) {
		return controlBeneficiariosService.obtieneFormato(idCuentaNombrada, idTipoBeneficiario);
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#obtieneTiposBeneficiario(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoBeneficiario[] obtieneTiposBeneficiario(String catBic) {
		return controlBeneficiariosService.obtieneTiposBeneficiario(catBic);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Field3W8BEN> getField3W8BEN() {
		return controlBeneficiariosService.getField3W8BEN();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Field3W8IMY> getField3W8IMY() {
		return controlBeneficiariosService.getField3W8IMY();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Field3W9> getField3W9() {
		return controlBeneficiariosService.getField3W9();
	}

	/**
	 * @see ControlBeneficiariosService#getListaCustodioTipoBenef()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CustodioTipoBenef> getListaCustodioTipoBenef() {
		return controlBeneficiariosService.getListaCustodioTipoBenef();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void asignaAdpBeneficiario(Long idBeneficiario, String adp) {
		controlBeneficiariosService.asignaAdpBeneficiario(idBeneficiario, adp);
	}

	public Date obtieneFechaValida() {
		return controlBeneficiariosService.obtieneFechaValida();
	}

	public Beneficiario consultaBeneficiarioUoiInst(String uoi, Long idEstatusBeneficiario, Long idTipoInstitucion, String folioInstitucion)
			throws BusinessException {		
		return controlBeneficiariosService.consultaBeneficiarioUoiInst(uoi, idEstatusBeneficiario, idTipoInstitucion, folioInstitucion);
	}

	public List<Object[]> obtieneAllCatBic() throws BusinessException {		
		return controlBeneficiariosService.obtieneAllCatBic();
	}
	
	public List<BeneficiariosPaginacionVO> consultaPaginasBeneficiarios(ConsultaBeneficiariosParam param, Boolean isPopup)throws BusinessException {
		return  	controlBeneficiariosService.consultaPaginasBeneficiarios(param,isPopup);
	}

	public void depurarFormatosFiscalesBeneficiarios() throws BusinessException {
		controlBeneficiariosService.depurarFormatosFiscalesBeneficiarios();
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#modificaPorcentajeRetencion(java.lang.Long,java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificaPorcentajeRetencion(Long idBeneficiario, Double nuevoPorcentajeRetencion){
		controlBeneficiariosService.modificaPorcentajeRetencion(idBeneficiario,nuevoPorcentajeRetencion);		
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#asignaValorAdpNuloBeneficiario(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void asignaValorAdpNuloBeneficiario(String uoi){
		controlBeneficiariosService.asignaValorAdpNuloBeneficiario( uoi);		
	}
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#eliminaInstitucionBeneficiarioMulticarga(java.lang.Long,java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminaInstitucionBeneficiarioMulticarga(Long idBeneficiario, Long idInstitucion){
		controlBeneficiariosService.eliminaInstitucionBeneficiarioMulticarga(idBeneficiario, idInstitucion);																	
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaCatExemptPayeeW9()
	 */
	public List<ExemptPayeeW9> consultaCatExemptPayeeW9() {
		return controlBeneficiariosService.consultaCatExemptPayeeW9();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaCatExemptionFatcaW9()
	 */
	public List<ExemptionFatcaW9> consultaCatExemptionFatcaW9() {
		return controlBeneficiariosService.consultaCatExemptionFatcaW9();
	}

	public Beneficiario consultaBeneficiarioByIdEliminados(Long idBeneficiario)
			throws BusinessException {
		return controlBeneficiariosService.consultaBeneficiarioByIdEliminados(idBeneficiario);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaCatMilaSectorEconomico()
	 */
	public List<MILASectorEconomico> consultaCatMilaSectorEconomico() {
		return controlBeneficiariosService.consultaCatMilaSectorEconomico();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaCatMilaTipoDocumento()
	 */
	public List<MILATipoDocumento> consultaCatMilaTipoDocumento() {
		return controlBeneficiariosService.consultaCatMilaTipoDocumento();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaCatMilaTipoEmpresa()
	 */
	public List<MILATipoEmpresa> consultaCatMilaTipoEmpresa() {
		return controlBeneficiariosService.consultaCatMilaTipoEmpresa();
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService#consultaCatMilaEstados()
	 */
	public List<MILACodigoDepartamento> consultaCatMilaEstados() {
		return controlBeneficiariosService.consultaCatMilaEstados();
	}

	public BeneficiarioInstitucion getBeneficiarioInstitucion(Long idBeneficiario, Long idInstitucion) throws BusinessException {
		return controlBeneficiariosService.getBeneficiarioInstitucion(idBeneficiario, idInstitucion);
	}

	public Beneficiario consultaBeneficiarioByUoi(String uoiBeneficiario) throws BusinessException {
		// TODO Auto-generated method stub
		return controlBeneficiariosService.consultaBeneficiarioByUoi(uoiBeneficiario);
	}
	
	public void actualizaStatusBeneficiario(Beneficiario beneficiario, long statusActualizado, String descStatusActualizad) {
		// TODO Auto-generated method stub
		controlBeneficiariosService.actualizaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizad);
	}
	
	public void cambiaStatusBeneficiario(Beneficiario beneficiario, long statusActualizado, String descStatusActualizado) {
		// TODO Auto-generated method stub
		controlBeneficiariosService.cambiaStatusBeneficiario(beneficiario, statusActualizado, descStatusActualizado);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> obtieneCatBicActivo() throws BusinessException {
		return controlBeneficiariosService.obtieneCatBicActivo();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean esCatBicActivo(Long idCuentaNombrada) {
		return controlBeneficiariosService.esCatBicActivo(idCuentaNombrada);
	}

	public List<CatBicVO> obtieneCatBicEntities() {
		return controlBeneficiariosService.obtieneCatBicEntities();
	}

	public void modificarCustodios(CatBicVO custodiosBeneficiarios) {
		controlBeneficiariosService.modificarCustodios(custodiosBeneficiarios);
	}

	public CatBic findAbreviacionByCustodio(String abreviacionCustodio) {
		return controlBeneficiariosService.findAbreviacionByCustodio(abreviacionCustodio);
	}
	
}
