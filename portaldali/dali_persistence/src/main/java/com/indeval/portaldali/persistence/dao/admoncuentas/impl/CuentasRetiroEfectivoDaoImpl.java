/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.dao.admoncuentas.CuentasRetiroEfectivoDao;
import com.indeval.portaldali.persistence.model.BitacoraEstadosCuentaRetiro;
import com.indeval.portaldali.persistence.model.BitacoraOperaciones;
import com.indeval.portaldali.persistence.model.CuentaRetiro;
import com.indeval.portaldali.persistence.model.CuentaRetiroNacional;
import com.indeval.portaldali.persistence.model.CuentaRetiroInternacional;
import com.indeval.portaldali.persistence.model.InstruccionEfectivo;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;

/**
 * Implementacion de la interface CuentaRetiroDao, contiene los metodos 
 * para las acciones sobre las cuentas de retiro de efectivo. 
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class CuentasRetiroEfectivoDaoImpl  extends BaseDaoHibernateImpl implements CuentasRetiroEfectivoDao {
	
    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(CuentasRetiroEfectivoDaoImpl.class);
	
	/** 
	 * Crea una nueva cuenta 
	 * @param cuenta objeto CuentaRetiro
	 */
    
    public void save(CuentaRetiro cuenta){
		logger.info("salvando una CuentaRetiro");
		super.save(cuenta);
    }
    
	public void save(CuentaRetiroNacional cuenta){
		logger.info("salvando una CuentaRetiroNal");
		super.save(cuenta);
	}
	
	public void save(CuentaRetiroInternacional cuenta){
		logger.info("salvando una CuentaRetiroInt");
		super.save(cuenta);
	}
	
	/**
	 * Consulta la cuenta por id 
	 * @param id objeto Long que identifica una cuenta
	 * @param int 0 - nacional, 1 -internacional, 2 - cuenta retiro general
	 */
	@SuppressWarnings("unchecked")
	public Object getCuenta(BigInteger id, int tipo){
		StringBuffer query = new StringBuffer();
		
		logger.debug("Encontrando... Cuenta de retiro con id : " + id +", tipo:"+tipo);

		if(tipo==0){ //nacional
			query.append(" from ").append(CuentaRetiroNacional.class.getName()).append(" cr ");
			query.append("	     join fetch cr.institucion ");
			query.append("	     join fetch cr.estado ");
			query.append("	     join fetch cr.divisa ");
			
			query.append(" where cr.idCuentaRetiroNal = ").append(id);
		}else if(tipo==1){ //internacional
			query.append(" from ").append(CuentaRetiroInternacional.class.getName()).append(" cr ");
			query.append("	     join fetch cr.institucion ");
			query.append("	     join fetch cr.estado ");
			query.append("	     join fetch cr.divisa ");
			
			query.append(" where cr.idCuentaRetiroInt = ").append(id);
		}else{
			query.append(" from ").append(CuentaRetiro.class.getName()).append(" cr ");
			query.append("	     join fetch cr.institucion ");
			query.append("	     join fetch cr.estado ");
			query.append("	     join fetch cr.divisa ");
			query.append(" where cr.idCuentaRetiro = ").append(id);
		}	
		
		logger.debug("query:"+query.toString());
		
		List cuentas = getHibernateTemplate().find(query.toString());
		if (cuentas.size() == 0 || cuentas==null){
			return null;
		}else{
			return cuentas.get(0);
		}
		
		//return(cuentas.size() == 0? null:cuentas.get(0));
		//return(cuentas==null? null:cuentas.get(0));
	} 
	
	/**
	 * Consulta la cuenta por id 
	 * @param id objeto Long que identifica una cuenta
	 */
	@SuppressWarnings("unchecked")
	public CuentaRetiroEfectivoDTO getCuentaCompletaDTO(BigInteger id, final boolean esNacional){
		final StringBuffer query = new StringBuffer();
		
		query.append(" from ");
		query.append(BitacoraEstadosCuentaRetiro.class.getName()).append(" be, ");
		query.append(esNacional? CuentaRetiroNacional.class.getName():CuentaRetiroInternacional.class.getName()).append(" cr ");
		query.append(esNacional? " join fetch cr.boveda ":"");
		query.append(esNacional? " join fetch cr.instBeneficiario ":"");
		query.append("	     join fetch cr.institucion ");
		query.append("	     join fetch cr.estado ");
		query.append("	     join fetch cr.divisa ");
		query.append(" where be.cuentaRetiro.idCuentaRetiro = cr.idCuentaRetiro ");
		
		query.append(esNacional?
						"    and cr.idCuentaRetiroNal = "
						:"   and cr.idCuentaRetiroInt = ").append(id);	
		
		logger.debug("hquery:"+query.toString());
		
		return (CuentaRetiroEfectivoDTO) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						List<Object[]> resultadosBO = hQuery.list();
						
						if(resultadosBO == null || resultadosBO.size()==0 ){
							return null;
						}
						
						Object[] bo = resultadosBO.get(0);
						CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
						cuenta.setBitacora(DTOAssembler.crearBitacoraEdosCuentaRetiroDTO((BitacoraEstadosCuentaRetiro) bo[0]));
						
						if(esNacional){
							CuentaRetiroNacional cta = (CuentaRetiroNacional) bo[1];
							
							cuenta.setDivisa(DTOAssembler.crearDivisaDTO(cta.getDivisa()));
							cuenta.setEstado(DTOAssembler.crearEstadosCuentaDTO(cta.getEstado()));
							cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
							cuenta.setInstitucion(DTOAssembler.crearInstitucionDTO(cta.getInstitucion()));
							cuenta.setMontoMaximoDiario(cta.getMontoMaxDiario()==null? null:cta.getMontoMaxDiario());
							cuenta.setMontoMaximoMensual(cta.getMontoMaxMensual()==null?null:cta.getMontoMaxMensual());
							cuenta.setMontoMaximoXTran(cta.getMontoMaxPorTran()==null?null:cta.getMontoMaxPorTran());
							cuenta.setNumMaximoMovsXMes(cta.getMaxMovsMensual()==null? 0: new Long(""+cta.getMaxMovsMensual()));
							
							cuenta.setInstitucionBeneficiario(DTOAssembler.crearInstitucionDTO(cta.getInstBeneficiario()));
							cuenta.setBoveda(DTOAssembler.crearBovedaDTO(cta.getBoveda()));
							cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
							cuenta.setNombreBeneficiario(cta.getNombreBeneficiario());
							cuenta.setIdCuentaRetiroNal(cta.getIdCuentaRetiroNal());
							cuenta.setIdCuentaPorInstitucion(cta.getIdCuentaPorInstitucion());
						}else{
							CuentaRetiroInternacional cta = (CuentaRetiroInternacional) bo[1];
							
							cuenta.setDivisa(DTOAssembler.crearDivisaDTO(cta.getDivisa()));
							cuenta.setEstado(DTOAssembler.crearEstadosCuentaDTO(cta.getEstado()));
							cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
							cuenta.setInstitucion(DTOAssembler.crearInstitucionDTO(cta.getInstitucion()));
							
							cuenta.setBancoBeneficiario(cta.getBancoBeneficiario());
							cuenta.setBancoIntermediario(cta.getBancoIntermediario());
							cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
							cuenta.setCuentaBeneficiarioFinal(cta.getCuentaBeneficiarioFinal());
							cuenta.setCuentaIntermediario(cta.getCuentaIntermediario());
							cuenta.setDetallesPago(cta.getDetallesPago());
							cuenta.setIdCuentaRetiroInt(cta.getIdCuentaRetiroInt().longValue());
							cuenta.setNombreBancoBeneficiario(cta.getNombreBancoBeneficiario());
							cuenta.setNombreBeneficiarioFinal(cta.getNombreBeneficiarioFinal());
							cuenta.setNombreCorto(cta.getNombreCorto());
							cuenta.setNombreIntermediario(cta.getNombreIntermediario());
						}
						
						return cuenta;
					}
				});				
	}
	
	
	/**
	 * Consulta la cuenta por id 
	 * @param id objeto Long que identifica una cuenta
	 */
	@SuppressWarnings("unchecked")
	public CuentaRetiroInternacionalDTO getCuentaRetiroInternacional(BigInteger id){
		final StringBuffer query = new StringBuffer();
		
		query.append(" from ");
		query.append(BitacoraEstadosCuentaRetiro.class.getName()).append(" be, ");
		query.append(CuentaRetiroInternacional.class.getName()).append(" cr ");
		query.append("	     join fetch cr.institucion ");
		query.append("	     join fetch cr.estado ");
		query.append("	     join fetch cr.divisa ");
		query.append(" where be.cuentaRetiro.idCuentaRetiro = cr.idCuentaRetiro ");
		query.append("   and cr.idCuentaRetiroInt = ").append(id);	
		
		
		return (CuentaRetiroInternacionalDTO) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						List<Object[]> resultadosBO = hQuery.list();
						
						if(resultadosBO == null || resultadosBO.size()==0 ){
							return null;
						}
						
						Object[] bo = resultadosBO.get(0);
						CuentaRetiroInternacionalDTO cuenta = new CuentaRetiroInternacionalDTO();
						cuenta.setBitacora(DTOAssembler.crearBitacoraEdosCuentaRetiroDTO((BitacoraEstadosCuentaRetiro) bo[0]));
						
						CuentaRetiroInternacional cta = (CuentaRetiroInternacional) bo[1];
						
						cuenta.setDivisa(DTOAssembler.crearDivisaDTO(cta.getDivisa()));
						cuenta.setEstado(DTOAssembler.crearEstadosCuentaDTO(cta.getEstado()));
						cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
						cuenta.setInstitucion(DTOAssembler.crearInstitucionDTO(cta.getInstitucion()));
						
						cuenta.setBancoBeneficiario(cta.getBancoBeneficiario());
						cuenta.setBancoIntermediario(cta.getBancoIntermediario());
						cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
						cuenta.setCuentaBeneficiarioFinal(cta.getCuentaBeneficiarioFinal());
						cuenta.setCuentaIntermediario(cta.getCuentaIntermediario());
						cuenta.setDetallesPago(cta.getDetallesPago());
						cuenta.setIdCuentaRetiroInt(cta.getIdCuentaRetiroInt().longValue());
						cuenta.setNombreBancoBeneficiario(cta.getNombreBancoBeneficiario());
						cuenta.setNombreBeneficiarioFinal(cta.getNombreBeneficiarioFinal());
						cuenta.setNombreCorto(cta.getNombreCorto());
						cuenta.setNombreIntermediario(cta.getNombreIntermediario());
						
						return cuenta;
					}
				});				
	}
	
	
	
	
	public Integer obtenerProyeccionCuentas(final CriterioCuentaEfectivoDTO criterio, final boolean sonNacional, boolean buscarExacto){
		final StringBuffer query = new StringBuffer();
		
		query.append("SELECT COUNT(*) FROM ");
		
		query.append(BitacoraEstadosCuentaRetiro.class.getName()).append(" be, ");
		query.append(CuentaRetiroNacional.class.getName()).append(" cr ");		
		query.append(" where be.cuentaRetiro.idCuentaRetiro = cr.idCuentaRetiro ");
		
		crearCriteriosQueryConsultaCuentas(query,criterio,sonNacional,buscarExacto);
		
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						
						if(criterio.getFechaCreacion()!=null){
							hQuery.setDate("fechaCreacion", criterio.getFechaCreacion());
						}
						
						if(criterio.getFechaAutorizacion()!=null){
							hQuery.setDate("fechaAutorizacion", criterio.getFechaAutorizacion());
						}
						
						if(criterio.getFechaLiberacion()!=null){
							hQuery.setDate("fechaLiberacion", criterio.getFechaLiberacion());
						}
						
						if(criterio.getFechaAprobacion()!=null){
							hQuery.setDate("fechaAprobacion", criterio.getFechaAprobacion());
						}
						
						if(criterio.getFechaModificacion()!=null){
							hQuery.setDate("fechaModificacion", criterio.getFechaModificacion());
						}
						
						if(criterio.getFechaCancelacion()!=null){
							hQuery.setDate("fechaCancelacion", criterio.getFechaCancelacion());
						}																	
						Number resultado = (Number) hQuery.uniqueResult();
						return resultado.intValue();						
					}
				});		
	}
	
	
	
	/**
	 * Consulta las cuentas en base a un criterio de busqueda
	 * @param criterio filtros de busqueda
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaRetiroEfectivoDTO> getCuentas(final CriterioCuentaEfectivoDTO criterio, final boolean sonNacional, boolean buscarExacto,final EstadoPaginacionDTO paginacion){
		final StringBuffer query = new StringBuffer();
		
		query.append(" from ");
		
		query.append(BitacoraEstadosCuentaRetiro.class.getName()).append(" be, ");
		query.append(sonNacional? CuentaRetiroNacional.class.getName():CuentaRetiroInternacional.class.getName()).append(" cr ");
		query.append(sonNacional? " join fetch cr.boveda ":"");
		query.append(sonNacional? " join fetch cr.instBeneficiario ":"");
		query.append("	     join fetch cr.institucion ");
		query.append("	     join fetch cr.estado ");
		query.append("	     join fetch cr.divisa ");
		query.append(" where be.cuentaRetiro.idCuentaRetiro = cr.idCuentaRetiro ");

		crearCriteriosQueryConsultaCuentas(query,criterio,sonNacional,buscarExacto);
		
		query.append(" order by cr.institucion.nombreCorto asc, cr.idCuentaPorInstitucion desc");
		
		logger.debug("hquery:"+query.toString());
		
		return (List<CuentaRetiroEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						
						if(criterio.getFechaCreacion()!=null){
							hQuery.setDate("fechaCreacion", criterio.getFechaCreacion());
						}
						
						if(criterio.getFechaAutorizacion()!=null){
							hQuery.setDate("fechaAutorizacion", criterio.getFechaAutorizacion());
						}
						
						if(criterio.getFechaLiberacion()!=null){
							hQuery.setDate("fechaLiberacion", criterio.getFechaLiberacion());
						}
						
						if(criterio.getFechaAprobacion()!=null){
							hQuery.setDate("fechaAprobacion", criterio.getFechaAprobacion());
						}
						
						if(criterio.getFechaModificacion()!=null){
							hQuery.setDate("fechaModificacion", criterio.getFechaModificacion());
						}
						
						if(criterio.getFechaCancelacion()!=null){
							hQuery.setDate("fechaCancelacion", criterio.getFechaCancelacion());
						}
						
						if (paginacion != null) {
							hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
							hQuery.setFetchSize(paginacion.getRegistrosPorPagina());
							hQuery.setFirstResult(paginacion.getNumeroPagina() > 0 ?((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina()):0);
						}
												
						List<Object[]> resultadosBO = hQuery.list();						
						
						List<CuentaRetiroEfectivoDTO> resultados = new ArrayList<CuentaRetiroEfectivoDTO>();
						
						for (Object[] bo : resultadosBO) {
							CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
							cuenta.setBitacora(DTOAssembler.crearBitacoraEdosCuentaRetiroDTO((BitacoraEstadosCuentaRetiro) bo[0]));
							
							if(sonNacional){
								CuentaRetiroNacional cta = (CuentaRetiroNacional) bo[1];
								cuenta.setDivisa(DTOAssembler.crearDivisaDTO(cta.getDivisa()));
								cuenta.setEstado(DTOAssembler.crearEstadosCuentaDTO(cta.getEstado()));
								cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
								cuenta.setInstitucion(DTOAssembler.crearInstitucionDTO(cta.getInstitucion()));
								cuenta.setMontoMaximoDiario(cta.getMontoMaxDiario()==null? null:cta.getMontoMaxDiario());
								cuenta.setMontoMaximoMensual(cta.getMontoMaxMensual()==null? null:cta.getMontoMaxMensual());
								cuenta.setMontoMaximoXTran(cta.getMontoMaxPorTran()==null?null:cta.getMontoMaxPorTran());
								cuenta.setNumMaximoMovsXMes(cta.getMaxMovsMensual()==null?null: new Long(""+cta.getMaxMovsMensual()));
								
								cuenta.setInstitucionBeneficiario(DTOAssembler.crearInstitucionDTO(cta.getInstBeneficiario()));
								cuenta.setBoveda(DTOAssembler.crearBovedaDTO(cta.getBoveda()));
								cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
								cuenta.setNombreBeneficiario(cta.getNombreBeneficiario());
								cuenta.setIdCuentaRetiroNal(cta.getIdCuentaRetiroNal());
								cuenta.setIdCuentaPorInstitucion(cta.getIdCuentaPorInstitucion());
							}else{
								CuentaRetiroInternacional cta = (CuentaRetiroInternacional) bo[1];
								cuenta.setDivisa(DTOAssembler.crearDivisaDTO(cta.getDivisa()));
								cuenta.setEstado(DTOAssembler.crearEstadosCuentaDTO(cta.getEstado()));
								cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
								cuenta.setInstitucion(DTOAssembler.crearInstitucionDTO(cta.getInstitucion()));
								
								cuenta.setBancoBeneficiario(cta.getBancoBeneficiario());
								cuenta.setBancoIntermediario(cta.getBancoIntermediario());
								cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
								cuenta.setCuentaBeneficiarioFinal(cta.getCuentaBeneficiarioFinal());
								cuenta.setCuentaIntermediario(cta.getCuentaIntermediario());
								cuenta.setDetallesPago(cta.getDetallesPago());
								cuenta.setIdCuentaRetiroInt(cta.getIdCuentaRetiroInt());
								cuenta.setNombreBancoBeneficiario(cta.getNombreBancoBeneficiario());
								cuenta.setNombreBeneficiarioFinal(cta.getNombreBeneficiarioFinal());
								cuenta.setNombreCorto(cta.getNombreCorto());
								cuenta.setNombreIntermediario(cta.getNombreIntermediario());
							}
							
							resultados.add(cuenta);
						}
						
						return resultados;
					}
				});		
	}
	
	/**
	 * Busca cuenta clabe de la institucion
	 * @param numeroCuenta numero de cuenta
	 * @return objeto de tipo CuentaRetiroEfectivoDTO
	 */
	@SuppressWarnings("unchecked")
	public CuentaRetiroEfectivoDTO getCuentaBeneficiario(String cuentaBeneficiarioInsitucion, Long idInstitucion){
		logger.debug("Buscando cuenta clabe beneficiario: " + cuentaBeneficiarioInsitucion);
		List<CuentaRetiroNacional> cuentaRetiroEfectivo = (List<CuentaRetiroNacional>) getHibernateTemplate()
							.find("FROM " + CuentaRetiroNacional.class.getName() + " crn " +
								  " JOIN FETCH crn.cuentaRetiro "+
								  " WHERE crn.cuentaBeneficiario = '" + cuentaBeneficiarioInsitucion + "'" +
								  " AND crn.instBeneficiario.idInstitucion = " + idInstitucion);

		CuentaRetiroEfectivoDTO cuenta = null;
		if(cuentaRetiroEfectivo != null && cuentaRetiroEfectivo.size()>0){
			cuenta = new CuentaRetiroEfectivoDTO();
			CuentaRetiroNacional cuentaRetiroEfectivoBean = cuentaRetiroEfectivo.get(0);
			
			cuenta.setIdCuentaRetiroNal(cuentaRetiroEfectivoBean.getIdCuentaRetiroNal());
			cuenta.setCuentaRetiro(DTOAssembler.crearCuentaRetiroDTO(cuentaRetiroEfectivoBean.getCuentaRetiro()));
			cuenta.setInstitucionBeneficiario(DTOAssembler.crearInstitucionDTO(cuentaRetiroEfectivoBean.getInstBeneficiario()));
			cuenta.setCuentaBeneficiario(cuentaRetiroEfectivoBean.getCuentaBeneficiario());
			cuenta.setNombreBeneficiario(cuentaRetiroEfectivoBean.getNombreBeneficiario());
		}

		
		return cuenta;	
	}
	
	/**
	 * Completa el query de acuerdo a los criterios de busqueda
	 * @param query StringBuffer con el query
	 * @param criterio los criterios de busqueda 
	 */
	private void crearCriteriosQueryConsultaCuentas(StringBuffer query, CriterioCuentaEfectivoDTO criterio, boolean sonNacional, boolean buscarExacto){
		
		if(criterio.getInstitucion()!=null
				&& criterio.getInstitucion().getClaveTipoInstitucion() != null
				&& criterio.getInstitucion().getFolioInstitucion() != null){
			query.append("  and cr.institucion.tipoInstitucion.claveTipoInstitucion = '").append(criterio.getInstitucion().getClaveTipoInstitucion()).append("' ");
			query.append("  and cr.institucion.folioInstitucion = '").append(criterio.getInstitucion().getFolioInstitucion()).append("' ");
		}

		if(sonNacional 
				&& criterio.getInstitucionBeneficiario()!=null 
				&& criterio.getInstitucionBeneficiario().getClaveTipoInstitucion() != null
				&& criterio.getInstitucionBeneficiario().getFolioInstitucion() != null){
			query.append(" and cr.instBeneficiario.tipoInstitucion.claveTipoInstitucion = '").append(criterio.getInstitucionBeneficiario().getClaveTipoInstitucion()).append("' ");
			query.append(" and cr.instBeneficiario.folioInstitucion = '").append(criterio.getInstitucionBeneficiario().getFolioInstitucion()).append("' ");
		}
		
		if(criterio.getIdBancoBeneficiario()!= null && !criterio.getIdBancoBeneficiario().equals("")){
			query.append(" and cr.instBeneficiario.tipoInstitucion.claveTipoInstitucion ");
			query.append("  || cr.instBeneficiario.folioInstitucion ");
			query.append(buscarExacto?" = '":" like '").append(criterio.getIdBancoBeneficiario()).append(buscarExacto?"'":"%' ");
		}
		
		if(criterio.getIdFolioUsuario()!= null && !criterio.getIdFolioUsuario().equals("")){
			query.append("  and concat(cr.institucion.tipoInstitucion.claveTipoInstitucion,cr.institucion.folioInstitucion) ")
				.append(buscarExacto?" = '":" like '").append(criterio.getIdFolioUsuario()).append(buscarExacto?"'":"%'");
		}
		
		if(criterio.getValorEn()!= null){
			query.append(" and cr.divisa.idDivisa =").append(criterio.getValorEn().getId());
		}
		
		if(!StringUtils.isEmpty(criterio.getFolioCuenta())){
			query.append(sonNacional?" and cr.idCuentaRetiroNal = ":" and cr.idCuentaRetiroInt = ").append(criterio.getFolioCuenta());
		}
		
		if(!StringUtils.isEmpty(criterio.getFolioCuentaPorTraspasante())){
			query.append(" and cr.idCuentaPorInstitucion ")
				.append(buscarExacto?" = '":" like '").append(criterio.getFolioCuentaPorTraspasante()).append(buscarExacto?"'":"%'");
		}
		
		if(!sonNacional && !StringUtils.isEmpty(criterio.getNombreCuenta())){
			query.append(" and cr.nombreCorto ")
				 .append(buscarExacto?" = '":" like '").append(criterio.getNombreCuenta()).append(buscarExacto?"'":"%'");
		}
		
		if(!StringUtils.isEmpty(criterio.getBancoBeneficiario())){
			query.append(sonNacional?" and cr.instBeneficiario.nombreCorto ":" and cr.bancoBeneficiario ")
				 .append(buscarExacto?" = '":" like '").append(criterio.getBancoBeneficiario()).append(buscarExacto?"'":"%'");
		}
		
		if(!StringUtils.isEmpty(criterio.getCuentaBeneficiario())){
			query.append(sonNacional?" and cr.cuentaBeneficiario ":" and cr.cuentaBeneficiarioFinal ")
				 .append(buscarExacto?" = '":" like '").append(criterio.getCuentaBeneficiario()).append(buscarExacto?"'":"%'");
		}
		
		if(!StringUtils.isEmpty(criterio.getEstadoCuenta()) && !criterio.getEstadoCuenta().equals("-1")){
			query.append(" and cr.estado.idEstadoInstruccion = ").append(criterio.getEstadoCuenta());
		}
		
		if(!StringUtils.isEmpty(criterio.getNombreBeneficiario())){
			query.append(sonNacional?" and cr.nombreBeneficiario ":" and cr.nombreBeneficiarioFinal ")
				  .append(buscarExacto?" = '":" like '").append(criterio.getNombreBeneficiario()).append(buscarExacto?"'":"%'");
		}
		
		
		if(criterio.getFechaCreacion()!=null){
			query.append(" and trunc(be.fechaCreacion) =:fechaCreacion ");
		}
		
		if(criterio.getFechaAutorizacion()!=null){
			query.append(" and trunc(be.fechaAutorizacion) =:fechaAutorizacion ");
		}
		
		if(criterio.getFechaLiberacion()!=null){
			query.append(" and trunc(be.fechaLiberacion) =:fechaLiberacion ");
		}
		
		if(criterio.getFechaAprobacion()!=null){
			query.append(" and trunc(be.fechaAprobacion) =:fechaAprobacion ");
		}
		
		if(criterio.getFechaModificacion()!=null){
			query.append(" and trunc(be.fechaModificacion) =:fechaModificacion ");
		}
		
		if(criterio.getFechaCancelacion()!=null){
			query.append(" and trunc(be.fechaCancelacion) =:fechaCancelacion ");
		}
		
		
	}
	
	/**
	 * Consulta las cuentas nacionales x id de cuenta retiro
	 * @param id cuenta retiro 
	 */
	public CuentaRetiroEfectivoDTO getCuentaNacionalByCuentaRetiroNoEnMOS(Long id){
		final StringBuffer query = new StringBuffer();
		query.append(" from ");
		query.append(CuentaRetiroNacional.class.getName()).append(" cr ");
		query.append(" where cr.idCuentaRetiro = ").append(id);
//		query.append("       and not exists ( from ").append(BitacoraRetiroChequera.class.getName()).append(" mos ");
//							 query.append("   where mos.idCuentaRetiroNal = cr.idCuentaRetiroNal ");
//							 query.append("  )");	
		
		logger.debug("hquery:"+query.toString());
		
		return (CuentaRetiroEfectivoDTO) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						CuentaRetiroNacional cuenta = (CuentaRetiroNacional)hQuery.uniqueResult();
						
						if(cuenta == null){
							return null;
						}
						
						CuentaRetiroEfectivoDTO cuentaDTO = new CuentaRetiroEfectivoDTO();
						cuentaDTO.setIdCuentaPorInstitucion(cuenta.getIdCuentaPorInstitucion());
						cuenta.setIdCuentaRetiro(cuenta.getIdCuentaRetiro());
						cuentaDTO.setIdCuentaRetiroNal(cuenta.getIdCuentaRetiroNal());
						cuentaDTO.setEstadoActual(cuenta.getEstado().getClaveEstadoInstruccion());
						cuentaDTO.setTipoCuenta('N');
												
						cuentaDTO.setDivisa(DTOAssembler.crearDivisaDTO(cuenta.getDivisa()));
						cuentaDTO.setEstado(DTOAssembler.crearEstadosCuentaDTO(cuenta.getEstado()));
						cuentaDTO.setIdCuentaRetiro(cuenta.getIdCuentaRetiro());
						cuentaDTO.setInstitucion(DTOAssembler.crearInstitucionDTO(cuenta.getInstitucion()));
						cuentaDTO.setMontoMaximoDiario(cuenta.getMontoMaxDiario()==null? null:cuenta.getMontoMaxDiario());
						cuentaDTO.setMontoMaximoMensual(cuenta.getMontoMaxMensual()==null? null:cuenta.getMontoMaxMensual());
						cuentaDTO.setMontoMaximoXTran(cuenta.getMontoMaxPorTran()==null?null:cuenta.getMontoMaxPorTran());
						cuentaDTO.setNumMaximoMovsXMes(cuenta.getMaxMovsMensual()==null?null: new Long(""+cuenta.getMaxMovsMensual()));
						
						cuentaDTO.setInstitucionBeneficiario(DTOAssembler.crearInstitucionDTO(cuenta.getInstBeneficiario()));
						cuentaDTO.setBoveda(DTOAssembler.crearBovedaDTO(cuenta.getBoveda()));
						cuentaDTO.setCuentaBeneficiario(cuenta.getCuentaBeneficiario());
						cuentaDTO.setNombreBeneficiario(cuenta.getNombreBeneficiario());
						cuentaDTO.setIdCuentaRetiroNal(cuenta.getIdCuentaRetiroNal());
						
						
						
						return cuentaDTO;
					}
				});						
	}
	
	/**
	 * Actualiza cuenta 
	 * @param cuenta objeto CuentaRetiro
	 */
	public void saveOrUpdate(CuentaRetiroNacional cuenta){
		logger.debug("::nuevo:: saveOrUpdate, CuentaRetiroNacional");
		getHibernateTemplate().saveOrUpdate(cuenta);
	}
	
	public void saveOrUpdate(CuentaRetiroInternacional cuenta){
		logger.debug("::nuevo:: saveOrUpdate, CuentaRetiroInternacional");
		getHibernateTemplate().saveOrUpdate(cuenta);
	}
	
	public void saveOrUpdate(CuentaRetiro cuenta){
		logger.debug("::nuevo:: saveOrUpdate, CuentaRetiro");
		getHibernateTemplate().saveOrUpdate(cuenta);
	}
	
	/**
	 * Elimina cuenta  
	 * @param cuenta objeto CuentaRetiro
	 */
	public void delete(CuentaRetiroNacional cuenta){
		logger.debug("eliminando CuentaRetiroNacional cuentaId:"+cuenta.getIdCuentaRetiroNal());
		getHibernateTemplate().delete(cuenta);
	}
	
	public void delete(CuentaRetiroInternacional cuenta){
		logger.debug("eliminando CuentaRetiroInternacional cuentaId:"+cuenta.getIdCuentaRetiroInt());
		getHibernateTemplate().delete(cuenta);
	}
	
	/**
	 * Consulta las cuentas internacionales por divisa
	 * @param idDivisa identificador de divisa 
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaRetiroInternacional> getCuentasInterXDivisa(BigInteger idDivisa){
		logger.debug("*** #4-todo!!!-maldicion Encontrando todos las cuentas para el id de divisa:"+idDivisa);
		StringBuffer query = new StringBuffer();
		query.append(" from ").append(CuentaRetiroInternacional.class.getName()).append(" cri ");
		//query.append("       and cri.cuentaRetiro.estado = 'LIBERADA' ");
		query.append("	     join fetch cri.divisa "); //para que no truene el DTOAssembler - revisar
		query.append("	     join fetch cri.estado "); //para que no truene el DTOAssembler - revisar
		query.append("	     join fetch cri.institucion "); //para que no truene el DTOAssembler - revisar
		query.append("	     join fetch cri.institucion.tipoInstitucion "); //para que no truene el DTOAssembler - revisar
		query.append(" where cri.divisa.idDivisa = ").append(idDivisa);
		
		query.append(" order by cri.nombreCorto asc ");
		return getHibernateTemplate().find(query.toString());
	}
	
	/**
	 * Consulta las cuentas nacionales por combinaciones de banco-cuenta-nombre del beneficiario.
	 * @param criterio filtros de busqueda
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaRetiroEfectivoDTO> buscarCuentasBancoNombreCuentaBeneficiario(CriterioCuentaEfectivoDTO criterio){
		final StringBuffer query = new StringBuffer();
		
		query.append(" from ");
		query.append(CuentaRetiroNacional.class.getName()).append(" cr ");
		query.append(" join fetch cr.boveda ");
		query.append(" join fetch cr.instBeneficiario ");
		query.append("	     join fetch cr.institucion ");
		query.append("	     join fetch cr.estado ");
		query.append("	     join fetch cr.divisa ");
		query.append(" where cr.institucion.tipoInstitucion.claveTipoInstitucion = '").append(criterio.getInstitucion().getClaveTipoInstitucion()).append("' ");
		query.append("       and cr.institucion.folioInstitucion = '").append(criterio.getInstitucion().getFolioInstitucion()).append("' ");
		query.append("       and cr.instBeneficiario.idInstitucion = ").append(criterio.getInstitucionBeneficiario().getId());
		query.append("       and cr.cuentaBeneficiario = '").append(criterio.getCuentaBeneficiario()).append("'");
		query.append("       and cr.nombreBeneficiario = '").append(criterio.getNombreBeneficiario()).append("'");
		//1. en caso de modificacion busca si alguna otra cuenta distinta a la que se esta modificando ya tiene la combinacion de pantalla 
		//   si encuentra alguna seria un error.
		//2. en caso de alta no se considera un folio de cuenta, solo se busca entre las existentes.
		query.append(StringUtils.isEmpty(criterio.getFolioCuenta())? 
							 ""
							 :(" and cr.idCuentaRetiroNal != " + criterio.getFolioCuenta()));
		//busca entre las cuentas activas (estado != 'CA')
		query.append("      and cr.estado.claveEstadoInstruccion != 'CA' ");
		
		logger.debug("hquery:"+query.toString());
		
		return (List<CuentaRetiroEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						
						List<CuentaRetiroNacional> resultadosBO = hQuery.list();
						List<CuentaRetiroEfectivoDTO> resultados = new ArrayList<CuentaRetiroEfectivoDTO>();
						
						for (CuentaRetiroNacional cta : resultadosBO) {
							CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
							
							cuenta.setDivisa(DTOAssembler.crearDivisaDTO(cta.getDivisa()));
							cuenta.setEstado(DTOAssembler.crearEstadosCuentaDTO(cta.getEstado()));
							cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
							cuenta.setInstitucion(DTOAssembler.crearInstitucionDTO(cta.getInstitucion()));
							cuenta.setMontoMaximoDiario(cta.getMontoMaxDiario()==null? null:cta.getMontoMaxDiario());
							cuenta.setMontoMaximoMensual(cta.getMontoMaxMensual()==null? null:cta.getMontoMaxMensual());
							cuenta.setMontoMaximoXTran(cta.getMontoMaxPorTran()==null?null:cta.getMontoMaxPorTran());
							cuenta.setNumMaximoMovsXMes(cta.getMaxMovsMensual()==null?null: new Long(""+cta.getMaxMovsMensual()));
							
							cuenta.setInstitucionBeneficiario(DTOAssembler.crearInstitucionDTO(cta.getInstBeneficiario()));
							cuenta.setBoveda(DTOAssembler.crearBovedaDTO(cta.getBoveda()));
							cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
							cuenta.setNombreBeneficiario(cta.getNombreBeneficiario());
							cuenta.setIdCuentaRetiroNal(cta.getIdCuentaRetiroNal());
							
							resultados.add(cuenta);
						}
						
						return resultados;
					}
				});		

	}
	
	
	/**
	 * Consulta las cuentas para encontrar la combinacion de banco-cuenta del beneficiario y poder comparar el nombre
	 * @param beneficiario institucion (banco) beneficiario
	 * @param cuentaBeneficiario cuenta del beneficiario
	 */
	@SuppressWarnings("unchecked")
	public CuentaRetiroEfectivoDTO buscarPorCombinacionBancoCuentaBeneficiario(InstitucionDTO traspasante, InstitucionDTO beneficiario, String cuentaBeneficiario, String folioCuenta){
		final StringBuffer query = new StringBuffer();
		
		query.append(" from ");
		query.append(CuentaRetiroNacional.class.getName()).append(" cr ");
		query.append(" 		join fetch cr.instBeneficiario ");
		query.append(" where cr.instBeneficiario.idInstitucion = ").append(beneficiario.getId());
		query.append("      and cr.institucion.idInstitucion = ").append(traspasante.getId());
		query.append("      and cr.cuentaBeneficiario = '").append(cuentaBeneficiario).append("' ");
		query.append(StringUtils.isEmpty(folioCuenta)?
							""
							:(" and cr.idCuentaRetiroNal != " + folioCuenta)); //que no sea la cuenta actual
		query.append("      and cr.estado.claveEstadoInstruccion != 'CA' "); //busca solo entre las cuentas que esten activas
		
		logger.debug("hquery:"+query.toString());
		
		return (CuentaRetiroEfectivoDTO) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						List<CuentaRetiroNacional> resultadosBO = hQuery.list();
						
						if(resultadosBO == null || resultadosBO.size()==0 ){
							return null;
						}
						
						CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
						//en teoria puede no ser el unico, la combinacion puede existir n veces para diferentes traspasantes
						//pero solo interesa uno de ellos ya que por las validaciones, todos tienen los mismos datos en la combinacion
						CuentaRetiroNacional cta = resultadosBO.get(0);
						cuenta.setInstitucionBeneficiario(DTOAssembler.crearInstitucionDTO(cta.getInstBeneficiario()));
						cuenta.setCuentaBeneficiario(cta.getCuentaBeneficiario());
						cuenta.setNombreBeneficiario(cta.getNombreBeneficiario());
						cuenta.setIdCuentaRetiro(cta.getIdCuentaRetiro());
						cuenta.setIdCuentaRetiroNal(cta.getIdCuentaRetiroNal());
							
						return cuenta;
					}
				});				
	}

	/** 
	 * Buscar movimientos pendientes de la cuenta (retiros PE)
	 * @param id id de la cuenta (cuenta retiro), o cadena de id cuentas separadas por coma
	 * @return list objetos RetiroEfectivoDTO o RetiroEfectivoInternacionalDTO 
	 */
	@SuppressWarnings("unchecked")
	public List<RetiroEfectivoDTO> buscarMovimientosPendientesNal(String id){
		
		List<RetiroEfectivoDTO> operaciones = new ArrayList<RetiroEfectivoDTO>();
		//Se buscan las operaciones pendientes en el mos
		List<RetiroEfectivoDTO> operacionesEnMos=operacionesPendientesEnMos(id);
		if(operacionesEnMos!=null){
			operaciones.addAll(operacionesEnMos);
		}	
		//Se buscan las cuentas no liberadas en portal
		List<RetiroEfectivoDTO> operacionesNoLiberadas=operacionesNoLiberadas(id);
		
		if(operacionesNoLiberadas!=null){
			operaciones.addAll(operacionesNoLiberadas);
		}	
		
		return operaciones;
	}

	private List<RetiroEfectivoDTO>  operacionesPendientesEnMos(String id) {
		
		final StringBuffer query = new StringBuffer();
		query.append(" from ");
		query.append(RetiroEfectivo.class.getName()).append(" re ");
		query.append(" where  trim(re.referenciaOperacion) in  ");
		//movimientos pendientes liberados (ya estan en el MOS)
		query.append("  ( select ie.referenciaOperacion from ");
					   query.append(InstruccionEfectivo.class.getName()).append(" ie, ");
					   query.append(CuentaRetiroNacional.class.getName()).append(" crn ");
		   			   query.append(" where crn.idCuentaRetiroNal in (" + id + ") ");
		   			   query.append("       and crn.idCuentaRetiroNal = re.idCuentaBeneficiario ");
		   			   query.append("       and ie.institucionTraspasante.idInstitucion = re.institucion.idInstitucion ");
		   			   query.append("       and ie.referenciaOperacion = trim(re.referenciaOperacion) ");
		   			   query.append("       and ie.estadoInstruccion.claveEstadoInstruccion = 'PE' ) ");
		
		return (List<RetiroEfectivoDTO>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						List<RetiroEfectivo> resultadosBO = hQuery.list();
						List<RetiroEfectivoDTO> resultados = new ArrayList<RetiroEfectivoDTO>(0);
						
						if(resultadosBO == null || resultadosBO.size()==0 ){
							return null;
						}
						
						for (RetiroEfectivo bo : resultadosBO) {
							RetiroEfectivoDTO dto = new RetiroEfectivoDTO();
							dto.setIdRetiroEfectivoNal(bo.getIdRetiroEfectivoNal());
							//dto.setIdCuentaBeneficiario(bo.getIdCuentaBeneficiario());
							resultados.add(dto);
						}
						
						return resultados;
					}
				});	
	}
	
	private List<RetiroEfectivoDTO>  operacionesNoLiberadas(String id) {
		
		final StringBuffer query = new StringBuffer();
		query.append("Select re from ");
		query.append(RetiroEfectivo.class.getName()).append(" re , ");
		query.append(CuentaRetiroNacional.class.getName()).append(" crn ");
		query.append(" where crn.idCuentaRetiroNal = re.idCuentaBeneficiario ");
		query.append(" and crn.idCuentaRetiro in ("+id+") ");
		query.append(" and re.estado.claveEstadoInstruccion in ('RE' , 'AU') ");
	   			   
		return (List<RetiroEfectivoDTO>) getHibernateTemplate().execute(
		   					new HibernateCallback() {
		   						public Object doInHibernate(Session session) throws HibernateException, SQLException {
		   							Query hQuery = session.createQuery(query.toString());
		   							List<RetiroEfectivo> resultadosBO = hQuery.list();
		   							List<RetiroEfectivoDTO> resultados = new ArrayList<RetiroEfectivoDTO>(0);
		   							
		   							if(resultadosBO == null || resultadosBO.size()==0 ){
		   								return null;
		   							}
		   							
		   							for (RetiroEfectivo bo : resultadosBO) {
		   								RetiroEfectivoDTO dto = new RetiroEfectivoDTO();
		   								dto.setIdRetiroEfectivoNal(bo.getIdRetiroEfectivoNal());
		   								//dto.setIdCuentaBeneficiario(bo.getIdCuentaBeneficiario());
		   								resultados.add(dto);
		   							}
		   							return resultados;
		   						}
		 });
	}
	
	/**
	 * Obtiene el id por institucion correspondiente a la nueva cuenta
	 * que va a crearse
	 * @param idInstitucion Long 
	 * @return idCuentaInstitucion Long 
	 */
	public Long getConsecutivoCreandoParaInstitucion(final String nombre){
		String ret = "0";
        ret = (String) getHibernateTemplate().execute(
        		new HibernateCallback() {
        			public Object doInHibernate(Session session) throws HibernateException{
        				Query query = session.createSQLQuery("CREATE SEQUENCE "+nombre+" START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE"); 
        				query.executeUpdate();
        				query = session.createSQLQuery("SELECT "+nombre+".NEXTVAL FROM DUAL");
        				return query.uniqueResult().toString();
        			}
        		}
        );
        
        return Long.valueOf(ret);
	}
	
}
