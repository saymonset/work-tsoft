/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaInstruccionEfectivoServiceImpl.java
 * Mar 7, 2008
 */
package com.indeval.portaldali.middleware.services.estatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.ParametrosLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.tesoreria.AdmonRetirosEfectivoService;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.persistence.dao.admonretiros.RetiroEfectivoDao;
import com.indeval.portaldali.persistence.dao.common.CuentaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.ParametrosLiquidacionDaliDAO;
import com.indeval.portaldali.persistence.dao.estatus.ConsultaInstruccionesEfectivoDAO;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;


/**
 * Implementacion del servicio de negocio para realizar las consultas de estatus
 * de operaciones de efectivo.
 * 
 * @author 
 * @version 1.0
 * 
 */
public class ConsultaInstruccionEfectivoServiceImpl implements
		ConsultaInstruccionesEfectivoService {

	
	private ParametrosLiquidacionDaliDAO parametrosLiquidacionDaliDAO;
	
	
	/** Servicio para las cuentas de retiros de efectivo*/
	private AdministracionCuentasRetiroService admonCuentasRetiroEfectivoService = null;
	
	/** Servicio para los retiros de efectivo*/
	private AdmonRetirosEfectivoService admonRetirosEfectivoService;
	
	/** DAO para ejecutar la consulta de instrucciones de efectivo */
	private ConsultaInstruccionesEfectivoDAO consultaInstruccionesEfectivoDAO = null;
	
	/** Servicio para el envio de operaciones. */
	private EnvioOperacionesService envioOperacionesService = null;
	
	private RetiroEfectivoDao retiroEfectivoDao;
	
	private CuentaDaliDAO cuentaDaliDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.indeval.portaldali.middleware.services.estatus.
	 * ConsultaInstruccionesEfectivoService
	 * #consultarInstruccionesEfectivo(com.indeval
	 * .portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO,
	 * com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO)  -
	 * Cambio realizado para leer registros CON match del modulo del match.
	 */
	public List<InstruccionEfectivoDTO> consultarInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta,
			EstadoPaginacionDTO estadoPaginacion) {

		List<InstruccionEfectivoDTO> listInstruccionEfectivo = new ArrayList<InstruccionEfectivoDTO>(0);
		
		listInstruccionEfectivo = consultaInstruccionesEfectivoDAO.consultarInstruccionesEfectivo(criterioConsulta,null);
		List<RetiroEfectivo> retiros = retiroEfectivoDao.find(criterioConsulta);
		
		//los que tienen folioOrigen = 12001 son los que vienen del portal, solo a esos se les puede obtener cuenta
		//el resto pueden venir del PFI
		for (InstruccionEfectivoDTO bo : listInstruccionEfectivo) {
			if(bo.getFolioOrigen()!=null && bo.getFolioOrigen().equals("12001")){ 
				
				RetiroEfectivo retiro = retiroEfectivoDao.findByReferencia(bo.getReferenciaOperacion());
				
				if(retiro!=null){
					bo.setFechaAutorizacion(retiro.getFechaAutorizacion());
					bo.setFechaLiberacion(retiro.getFechaLiberacion());
					
					if(retiro.getTipoOperacion().equals("CCS")){
						CuentaDTO cuentaDTO = new CuentaDTO();
						cuentaDTO.setCuenta(retiro.getCuentaBeneficiario());
						cuentaDTO.setNombreCuenta(retiro.getIdInstReceptor().getNombreCorto());
						bo.setCuentaReceptora(cuentaDTO);
					}
				}
			}
			
		}// FOR
		
		for (RetiroEfectivo retiro : retiros) {
			InstruccionEfectivoDTO instruccionEfectivoDTO = DTOAssembler.setOperacionEfectivoByOperacionEfectivoNal(retiro);
			
			CuentaDTO cuentaTraspasanteDto = obtenerCuenta(instruccionEfectivoDTO.getInstitucionTraspasante());
			if(cuentaTraspasanteDto!=null) instruccionEfectivoDTO.setCuentaTraspasante(cuentaTraspasanteDto);
			
			
			if("CCS".equals(retiro.getTipoOperacion()) ) {
				instruccionEfectivoDTO.getTipoMensaje().setClaveTipoMensaje("103");
			}else {
				instruccionEfectivoDTO.getTipoMensaje().setClaveTipoMensaje("202");
				
				if("TREF".equals(retiro.getTipoOperacion())) {
					instruccionEfectivoDTO.setTipoRetiro("");
					instruccionEfectivoDTO.setConcepto(instruccionEfectivoDTO.getTipoInstruccion().getInstruccion());
				}
				
				// Obtener cuenta de efectivo para TREF y Retiros(SIAC, SPEI)
				CuentaDTO cuentaReceptorDto = obtenerCuenta(instruccionEfectivoDTO.getInstitucionReceptora());
				if(cuentaReceptorDto!=null) instruccionEfectivoDTO.setCuentaReceptora(cuentaReceptorDto);
				
			}
			
			listInstruccionEfectivo.add(instruccionEfectivoDTO);
		}// FOR
		
		return listInstruccionEfectivo;
	}

	private CuentaDTO obtenerCuenta(InstitucionDTO institucionDTO) {
		CuentaDTO cuentaDTO = null;
		
		CuentaEfectivoDTO cuentaEfectivoDto = buscarCuentaEfectivo(institucionDTO);
		if(cuentaEfectivoDto!=null) {
			cuentaDTO = new CuentaDTO();
			cuentaDTO.setCuenta(cuentaEfectivoDto.getCuenta());
			cuentaDTO.setNombreCuenta(cuentaEfectivoDto.getNombreCuenta());
			cuentaDTO.setNumeroCuenta(cuentaEfectivoDto.getNumeroCuenta());
		}
		
		return cuentaDTO;
	}
	
	
	public List<RetiroEfectivoInternacionalDTO> getInstEfecIntl(CriterioEstatusOpEfectivoDTO criterioConsulta,EstadoPaginacionDTO estadoPaginacion) {
		//TODO  corregir la paginacion en match y efectivo		
		List<RetiroEfectivoInternacionalDTO> listRetiroFondosIntlEnMoi = consultaInstruccionesEfectivoDAO.getListRetiroFondosIntlEnMoi(criterioConsulta,null);

		
		 List<RetiroEfectivoInternacionalDTO> listRetiroFondosIntlSinComprobacion =
			 		consultaInstruccionesEfectivoDAO.getListRetiroFondosIntlSinComprobacion(criterioConsulta,				null);		
		for (RetiroEfectivoInternacionalDTO bo : listRetiroFondosIntlSinComprobacion) {
				String strId =  String.valueOf( bo.getIdRetiroEfectivoInt());
				CuentaRetiroInternacionalDTO cuentaRetiroInternacionalDTO = admonCuentasRetiroEfectivoService.buscarCuentaRetiroInternacional(new BigInteger( strId ));
				if (cuentaRetiroInternacionalDTO!= null){
					bo.setCuentaBeneficiarioFinal(cuentaRetiroInternacionalDTO.getCuentaBeneficiarioFinal());
				}else{
					bo.setCuentaBeneficiarioFinal(null);
				}
				listRetiroFondosIntlEnMoi.add(bo);
				
		}
		
		if (estadoPaginacion != null) {			
			estadoPaginacion.setTotalResultados(listRetiroFondosIntlEnMoi.size());
			if (estadoPaginacion.getTotalResultados() > 0) {
				estadoPaginacion.setTotalPaginas((int) Math
						.ceil((double) estadoPaginacion.getTotalResultados()
								/ (double) estadoPaginacion
										.getRegistrosPorPagina()));
				if (estadoPaginacion.getNumeroPagina() < 1) {
					estadoPaginacion.setNumeroPagina(1);
				}
			} else {
				estadoPaginacion.setNumeroPagina(0);
				estadoPaginacion.setTotalPaginas(0);
			}
			int pagina = estadoPaginacion.getNumeroPagina();
			int registrosPorPagina = estadoPaginacion.getRegistrosPorPagina();
			if (pagina > 0 && registrosPorPagina > 0) {
				int primerRes = (estadoPaginacion.getNumeroPagina() - 1)
						* estadoPaginacion.getRegistrosPorPagina();
				int ultimoRes = (estadoPaginacion.getNumeroPagina() - 1)
						* estadoPaginacion.getRegistrosPorPagina()
						+ estadoPaginacion.getRegistrosPorPagina();
				if (ultimoRes > (listRetiroFondosIntlEnMoi.size() - 1)) {
					ultimoRes = listRetiroFondosIntlEnMoi.size();
				}



					
				listRetiroFondosIntlEnMoi = listRetiroFondosIntlEnMoi.subList(primerRes, ultimoRes);
			}

		}			
		return listRetiroFondosIntlEnMoi;
	}

	
	
	
	/*
	 * 
	 * (non-Javadoc)
	 *Permite la lectura de un registro del retiro internacion de efectivo. por ID
	 *	 */
	public InstruccionEfectivoDTO consultarInstEfecIntlById(String id){
		
		CriterioEstatusOpEfectivoDTO criterioEstatusOpEfectivoDTO = new CriterioEstatusOpEfectivoDTO ();
		InstruccionEfectivoDTO instruccionEfectivoDTO = new InstruccionEfectivoDTO();
		criterioEstatusOpEfectivoDTO.setId(id);
		List<InstruccionEfectivoDTO> listInstruccionEfectivo = consultaInstruccionesEfectivoDAO.consultarInstRetiroEfecIntl(criterioEstatusOpEfectivoDTO,null);
		if (!listInstruccionEfectivo.isEmpty() ){
			instruccionEfectivoDTO = (InstruccionEfectivoDTO) listInstruccionEfectivo.get(0);
		}	
		return instruccionEfectivoDTO;
	}
	
	public RetiroEfectivoInternacionalDTO consultarInstEfecIntlByIdRetREI(String id){
		
		CriterioEstatusOpEfectivoDTO criterioEstatusOpEfectivoDTO = new CriterioEstatusOpEfectivoDTO ();
		RetiroEfectivoInternacionalDTO retiroEfectivoIntlDTO = null; 
		criterioEstatusOpEfectivoDTO.setId(id);
		List<RetiroEfectivoInternacionalDTO> listRetiroEfectivoIntlVO = consultaInstruccionesEfectivoDAO.consultarInstRetiroEfecIntlRetREI(criterioEstatusOpEfectivoDTO,null);
		if (!listRetiroEfectivoIntlVO.isEmpty() ){
			retiroEfectivoIntlDTO = listRetiroEfectivoIntlVO.get(0);
		}	
		return retiroEfectivoIntlDTO;
	}

	
	public RetiroEfectivoDTO consultarRetiroEfectivoById(String id){
		CriterioEstatusOpEfectivoDTO criterioEstatusOpEfectivoDTO = new CriterioEstatusOpEfectivoDTO ();
		RetiroEfectivoDTO retiroEfectivoDTO = null; 
		criterioEstatusOpEfectivoDTO.setId(id);
		List<RetiroEfectivoDTO> listRetiroEfectivoDTO = consultaInstruccionesEfectivoDAO.consultarRetiroEfectivo(criterioEstatusOpEfectivoDTO,null);
		if (!listRetiroEfectivoDTO.isEmpty() ){
			retiroEfectivoDTO = listRetiroEfectivoDTO.get(0);
		}	
		return retiroEfectivoDTO;
	}
	
	public CuentaEfectivoDTO buscarCuentaEfectivo(InstitucionDTO institucion) {
		CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
		criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		criterio.setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterio.setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
		criterio.setInstitucion(institucion);
		criterio.setNumeroCuenta(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
		
		List<CuentaEfectivoDTO> cuentas = cuentaDaliDao.buscarCuentasNombradasEfectivo(criterio, null);
		if(cuentas == null || cuentas.isEmpty()) {
			return null;
		}
		
		return cuentas.get(0);
	}
	
	/**
	 * 
	 * 
	 * Permite la lectura de un registro del retiro internacion de efectivo. por ID
	 * 
	 *	 */
	/*
	public InstruccionEfectivoDTO consultarInstEfecIntlByIdRetREI(String id){
		
		CriterioEstatusOpEfectivoDTO criterioEstatusOpEfectivoDTO = new CriterioEstatusOpEfectivoDTO ();
		RetiroEfectivoInternacionalDTO instruccionEfectivoDTO = new RetiroEfectivoInternacionalDTO();
		criterioEstatusOpEfectivoDTO.setId(id);
		List<InstruccionEfectivoDTO> listInstruccionEfectivo = consultaInstruccionesEfectivoDAO.consultarInstRetiroEfecIntl(criterioEstatusOpEfectivoDTO,null);
		if (!listInstruccionEfectivo.isEmpty() ){
			instruccionEfectivoDTO = listInstruccionEfectivo.get(0);
		}	
		return InstruccionEfectivoDTO;
	}
	*/
	
	/*
	 * 
	 * (non-Javadoc)
	 *Permite la lectura de un registro del match por ID
	 *	 */
	public InstruccionEfectivoDTO consultarInstruccionMatchById(String idMatch){
		
		CriterioEstatusOpEfectivoDTO criterioEstatusOpEfectivoDTO = new CriterioEstatusOpEfectivoDTO ();
		InstruccionEfectivoDTO instruccionEfectivoDTO = new InstruccionEfectivoDTO();
		criterioEstatusOpEfectivoDTO.setId(idMatch);
		List<InstruccionEfectivoDTO> listInstruccionEfectivo = 
			consultaInstruccionesEfectivoDAO.consultarInstruccionesEnModuloMatchSinMatch(criterioEstatusOpEfectivoDTO,null);

		
		
		if (!listInstruccionEfectivo.isEmpty() ){
			instruccionEfectivoDTO = listInstruccionEfectivo.get(0);
		}	
		
	
		
		return instruccionEfectivoDTO;
	}

/**
 * @author 2009-11-09
 * Metodo que permite la actualizacion de la fecha para aprobacion  de las operactiones de retiro en efectivo
 */
	public void aprobarRetiroEfectivoInternacionalById(String id){		
			consultaInstruccionesEfectivoDAO.aprobarRetiroEfectivoInternacionalById(id);
	}

	/**
	 * @author 2009-11-10
	 * Metodo que permite la actualizacion de la fecha para liberacion  de las operactiones de retiro en efectivo
	 */
		public void liberarRetiroEfecIntlById(String id){	
			consultaInstruccionesEfectivoDAO.liberarRetiroEfecIntlById(id);
		}
	

		public void liberarRetiroEfectivoById(String id,Map<String, Object> datosFirma){			
			consultaInstruccionesEfectivoDAO.liberarRetiroEfectivoById(id,datosFirma);

	}

		public void aprobarRetiroEfectivoById(String id, Map<String, Object> datosFirma){		
			consultaInstruccionesEfectivoDAO.aprobarRetiroEfectivoById(id,datosFirma);
	
	}
		
		
		
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.indeval.portaldali.middleware.services.estatus.
	 * ConsultaInstruccionesEfectivoService
	 * #obtenerTotalesDeConsultaInstruccionesEfectivo
	 * (com.indeval.portaldali.middleware
	 * .dto.criterio.CriterioEstatusOpEfectivoDTO, java.util.Map)
	 */
	public Map<Object, Object> obtenerTotalesDeConsultaInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		Map<Object,Object> lista =  consultaInstruccionesEfectivoDAO.obtenerTotalesDeConsultaInstruccionesEfectivo(criterioConsulta);
		//Map<Object,Object> lista2 = consultaInstruccionesEfectivoDAO.obtenerTotalesDeConsultaInstruccionesEfectivoMatch(criterioConsulta); 
		//lista.putAll(lista2);
		lista.put(TOTAL_IMPORTE_NAL, retiroEfectivoDao.sumImporte(criterioConsulta));
		return lista;

	}

	public Map<Object, Object> sumTotDeConsInstEfec(
			CriterioEstatusOpEfectivoDTO criterioConsulta) {
		Map<Object,Object> lista =  consultaInstruccionesEfectivoDAO.getTotDeConsInstEfecIntlMoi(criterioConsulta);
		Map<Object,Object> lista2 = consultaInstruccionesEfectivoDAO.getTotDeConsInstEfecIntl(criterioConsulta);
		lista.putAll(lista2);
		return lista;

	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.indeval.portaldali.middleware.services.estatus.
	 * ConsultaInstruccionesEfectivoService
	 * #obtenerProyeccionConsultaInstruccionesEfectivo
	 * (com.indeval.portaldali.middleware
	 * .dto.criterio.CriterioEstatusOpEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaInstruccionesEfectivo(
			CriterioEstatusOpEfectivoDTO criterioConsulta, Boolean debeDejarLog) {
		int importe = 0;
		importe = consultaInstruccionesEfectivoDAO.obtenerProyeccionConsultaInstruccionesEfectivo(criterioConsulta);
		importe += retiroEfectivoDao.count(criterioConsulta);
		
		return importe;
	}

	public int getProyeccionConsultaInstEfecIntl(
			CriterioEstatusOpEfectivoDTO criterioConsulta, Boolean debeDejarLog) {
		// TODO Auto-generated method stub
		return consultaInstruccionesEfectivoDAO.getProyeccionConsultaInstEfecIntl(criterioConsulta);
	}
	
	

	public boolean validaLiberacion() {
		ParametrosLiquidacionDTO dto=parametrosLiquidacionDaliDAO.buscarParametroByID(BigInteger.ONE);		
		if(dto!=null && dto.getProcesoFin()==1){
			return false;				
		}		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.estatus.ConsultaInstruccionesEfectivoService#consultarReteSiacAprobadas()
	 */
	public List<InstruccionEfectivoDTO> consultarReteAprobadas() {
		List<InstruccionEfectivoDTO> result = consultaInstruccionesEfectivoDAO.consultarReteAprobadas();
		return result;
	}
	
	/**
	 * Obtiene el campo consultaInstruccionesEfectivoDAO
	 * 
	 * @return consultaInstruccionesEfectivoDAO
	 */
	public ConsultaInstruccionesEfectivoDAO getConsultaInstruccionesEfectivoDAO() {
		return consultaInstruccionesEfectivoDAO;
	}

	/**
	 * Asigna el campo consultaInstruccionesEfectivoDAO
	 * 
	 * @param consultaInstruccionesEfectivoDAO
	 *            el valor de consultaInstruccionesEfectivoDAO a asignar
	 */
	public void setConsultaInstruccionesEfectivoDAO(
			ConsultaInstruccionesEfectivoDAO consultaInstruccionesEfectivoDAO) {
		this.consultaInstruccionesEfectivoDAO = consultaInstruccionesEfectivoDAO;
	}


	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}


	public void setEnvioOperacionesService(
			EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}




	public AdministracionCuentasRetiroService getAdmonCuentasRetiroEfectivoService() {
		return admonCuentasRetiroEfectivoService;
	}

	public void setAdmonCuentasRetiroEfectivoService(
			AdministracionCuentasRetiroService admonCuentasRetiroEfectivoService) {
		this.admonCuentasRetiroEfectivoService = admonCuentasRetiroEfectivoService;
	}

	public AdmonRetirosEfectivoService getAdmonRetirosEfectivoService() {
		return admonRetirosEfectivoService;
	}
	
	public void setAdmonRetirosEfectivoService(
			AdmonRetirosEfectivoService admonRetirosEfectivoService) {
		this.admonRetirosEfectivoService = admonRetirosEfectivoService;
	}
	
	public void setParametrosLiquidacionDaliDAO(
			ParametrosLiquidacionDaliDAO parametrosLiquidacionDaliDAO) {
		this.parametrosLiquidacionDaliDAO = parametrosLiquidacionDaliDAO;
	}

	public void setRetiroEfectivoDao(RetiroEfectivoDao retiroEfectivoDao) {
		this.retiroEfectivoDao = retiroEfectivoDao;
	}

	public void setCuentaDaliDao(CuentaDaliDAO cuentaDaliDao) {
		this.cuentaDaliDao = cuentaDaliDao;
	}
}
