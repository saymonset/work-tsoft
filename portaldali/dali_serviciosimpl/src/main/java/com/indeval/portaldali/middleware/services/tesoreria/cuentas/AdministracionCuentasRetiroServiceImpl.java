/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.cuentas;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.ClobImpl;
import org.jasypt.digest.StandardStringDigester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.ParametrosDaliDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.util.Constantes;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.admoncuentas.BitacoraEstadosCuentaDao;
import com.indeval.portaldali.persistence.dao.admoncuentas.CuentasRetiroEfectivoDao;
import com.indeval.portaldali.persistence.dao.admoncuentas.EstatusCuentaDao;
import com.indeval.portaldali.persistence.dao.admoncuentas.HistoricoAdmonCuentasRetiroDao;
import com.indeval.portaldali.persistence.dao.admonretiros.PaisDivisaDao;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DepositanteValidoBanxicoDao;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.ParametrosDaliDao;
import com.indeval.portaldali.persistence.dao.util.SecuenciaDaliDao;
import com.indeval.portaldali.persistence.model.BitacoraEstadosCuentaRetiro;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.CuentaRetiro;
import com.indeval.portaldali.persistence.model.CuentaRetiroInternacional;
import com.indeval.portaldali.persistence.model.CuentaRetiroNacional;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.EstadoInstruccionCat;
import com.indeval.portaldali.persistence.model.HistoricoAdmonCuentasRetiroInternacional;
import com.indeval.portaldali.persistence.model.HistoricoAdmonCuentasRetiroNacional;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.PaisDivisa;

/**
 * Implementacion del servicio para la administracion de cuentas de retiro de efectivo
 * nacionales e internacionales
 * 
 * @author Maria C. Buendia
 */
public class AdministracionCuentasRetiroServiceImpl implements AdministracionCuentasRetiroService{
    
	/** Log de clase */
    private static final Logger logger = LoggerFactory.getLogger(AdministracionCuentasRetiroServiceImpl.class);
    
    private static final String PRIMER_ESTADO = "19";
    
    /** Espacio para llenado en ceros al agregar el ID del participante en la secuencia. */
    private final int PADDING_LENGTH = 3;
    
    private static final BigInteger ID_ESTADO_INSTRUCCION = new BigInteger("24");
    
    private static final String CLAVE_ESTADO_INSTRUCCION = "LA";
    
    private static final String DESCRIPCION_ESTADO = "Liberada";
    
    private static final BigDecimal MONTO_MAX_MENSUAL = new BigDecimal("9999999999999");
    
    private static final BigDecimal MONTO_MAX_DIARIO = new BigDecimal("100000000000");
    
    private static final BigDecimal MONTO_MAX_TRANSAC = new BigDecimal("100000000000");
    
    private static final Long MONTO_MOVS_MENSUAL = new Long("99999");
    
    /** dao cuentas */
    private CuentasRetiroEfectivoDao cuentasDao; 

    /** dao bitacora */
    private BitacoraEstadosCuentaDao bitacoraDao; 

    /** dao historico */
    private HistoricoAdmonCuentasRetiroDao historicoDao; 
    
    /** dao estados de cuenta */
    private EstatusCuentaDao edosDao;
    
    /** dao pais divisa*/
    private PaisDivisaDao paisDivisaDao;
    
    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;
    
    /** dao depositante valido banxico */
    private DepositanteValidoBanxicoDao depositanteValidoBanxicoDao;
    
    /** Bean de acceso a UtilService */
	private UtilServices utilService;
	
	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;
	
	/** Bean de acceso a datos de DiaInhabil */
	private DiaInhabilDaliDao diaInhabilDaliDao;
	
	/** Bean de acceso a datos de parametros*/
	private ParametrosDaliDao parametrosDao;
	
	/** Encriptador */
	private StandardStringDigester jasyptStringDigester;
	
    /** dao de secuencias dali*/
    private SecuenciaDaliDao secuenciaDaliDao;
	
    /** secuencia cuentas nacionales */
    private String C_CUENTA_RETIRO_NAL_SEQ = "C_CUENTA_RETIRO_NAL_SEQ";
    
    /** secuencia cuentas nacionales */
    private String C_CUENTA_RETIRO_INT_SEQ = "C_CUENTA_RETIRO_INT_SEQ";
    
    /** DAO para la consulta del catálogo de instituciones del DALI */
    private InstitucionDaliDAO institucionDaliDAO = null;
    
    
    /*
    public String generaIso(CuentaRetiroInternacionalDTO cuentaRetiroInternacionalDTO) 
    {		
    		return creaISOCuentaRetiroInternacional(cuentaRetiroInternacionalDTO);    		
    }*/
    
    
    public CuentaRetiroEfectivoDTO getCuentaNacionalByCuentaRetiroNoEnMOS(
			Long idCuenta) {	
		return cuentasDao.getCuentaNacionalByCuentaRetiroNoEnMOS(idCuenta);
	}
    
    
    
    public Integer obtenerProyeccionCuentas(CriterioCuentaEfectivoDTO criterio,
			boolean sonNacional, boolean buscarExacto) {
		return cuentasDao.obtenerProyeccionCuentas(criterio, sonNacional, buscarExacto);
	}

    
    
    
	/**
	 * Busca el conjunto de cuentas de acuerdo a los filtros
	 * @param 
	 * @return lista de objetos  
	 */
    public List<CuentaRetiroEfectivoDTO> buscarCuentas(
    		CriterioCuentaEfectivoDTO criterio, EstadoPaginacionDTO paginacion, boolean sonNacional, boolean buscarExacto){
		logger.debug("buscando cuentas...");
		
		List<CuentaRetiroEfectivoDTO> cuentas = new ArrayList<CuentaRetiroEfectivoDTO>();

		cuentas = cuentasDao.getCuentas(criterio, sonNacional, buscarExacto,paginacion);
		boolean tieneMovimientosPE = false;	
		
		//completando datos para la pagina correspondiente
		
		Map <Long, List<Object>> movsPendientes = 
			cuentas != null && cuentas.size() != 0 ? obtenerRetirosPendientes(sonNacional, cuentas):null;
		
		for(CuentaRetiroEfectivoDTO cuenta : cuentas){
			
			tieneMovimientosPE = movsPendientes.containsKey(cuenta.getIdCuentaRetiroNal());
			
			if(cuenta.getBitacora().getFechaAutorizacion()==null
					&& cuenta.getBitacora().getFechaCancelacion()==null){
				cuenta.setEstadoActual("RE");
				cuenta.setAutorizar(true);
				cuenta.setCancelar(true);
				cuenta.setModificada(true);
			}else if(cuenta.getBitacora().getFechaAutorizacion()!=null 
					 && cuenta.getBitacora().getFechaLiberacion()==null
					 && cuenta.getBitacora().getFechaAprobacion()==null
					 && cuenta.getBitacora().getFechaCancelacion()==null){
				cuenta.setEstadoActual("AU");
				cuenta.setLiberar(true);
				cuenta.setCancelar(true);
				cuenta.setModificada(true);
			}else if(cuenta.getBitacora().getFechaAutorizacion()!=null 
					 && cuenta.getBitacora().getFechaLiberacion()!=null
					 && cuenta.getBitacora().getFechaAprobacion()==null
					 && cuenta.getBitacora().getFechaCancelacion()==null){
				cuenta.setEstadoActual("LA");
				cuenta.setAprobar(true); //para usuario internacional nada mas??
				cuenta.setCancelar(tieneMovimientosPE ? false : true); 
				cuenta.setModificada(false); //cuentas liberadas, no se pueden modificar 
			}else if(!sonNacional && cuenta.getBitacora().getFechaAprobacion()!=null
					&& cuenta.getBitacora().getFechaCancelacion()==null){
				cuenta.setEstadoActual("AP");
				//cuenta.setCancelar(tieneMovimientosPE ? false : true); 
				//cuenta.setModificada(tieneMovimientosPE ? false : true); 
			}else if(cuenta.getBitacora().getFechaCancelacion()!=null){
				cuenta.setEstadoActual("CA");
				cuenta.setModificada(false); //cuentas canceladas, no se pueden modificar
			}
		}
		
		return cuentas;
	}
    
	/**
	 * Buscar cuenta retiro completo
	 * @param id cuenta retiro
	 */
	public CuentaRetiroEfectivoDTO buscarCuenta(BigInteger id, boolean esNacional){
		logger.debug("buscando cuenta id="+id+", esNacional:"+esNacional);
		return cuentasDao.getCuentaCompletaDTO(id, esNacional);
	}
	
	public CuentaRetiroInternacionalDTO buscarCuentaRetiroInternacional(BigInteger id){
		return cuentasDao.getCuentaRetiroInternacional(id);
	}
	
	
	/**
	 * Guarda una nueva cuenta internacional
	 * @param cuenta objeto cuenta internacional 
	 */
	public void crearCuenta(CuentaRetiroEfectivoDTO cuentaDTO){
		logger.debug("creando nueva cuenta... tipo:"+cuentaDTO.getTipoCuenta());

		//divisa
		Divisa divisa = new Divisa();
		divisa.setIdDivisa(BigInteger.valueOf(cuentaDTO.getDivisa().getId()));
		//institucion
		Institucion institucion = new Institucion();
		institucion.setIdInstitucion(BigInteger.valueOf(cuentaDTO.getInstitucion().getId()));
		//estado de creacion = 19 (registrada)
		EstadoInstruccionCat estado = edosDao.getEstado(BigInteger.valueOf(Long.valueOf(PRIMER_ESTADO)));
		
		//cuenta internacional
		if(cuentaDTO.getTipoCuenta()=='I'){
			logger.debug("internacional - folioCuenta = "+cuentaDTO.getIdCuentaRetiroInt());
			//cuenta internacional
			CuentaRetiroInternacional cuentaInter = DTOAssembler.crearCuentaRetiroInternacional(cuentaDTO);
			cuentaInter.setDivisa(divisa);
			cuentaInter.setEstado(estado);			
			cuentaInter.setInstitucion(institucion);
			cuentaInter.setIdCuentaRetiroInt(cuentaDTO.getIdCuentaRetiroInt());
			//pendiente TODO
			//cuentaInter.setIdCuentaRetiroInt(cuentaDTO.getIdCuentaPorInstitucion());
			
			//pendiente TODO
			//String cadena_encrip = jasyptStringDigester.digest("esta cadena");
			//cuentaInter.setCuentaDigest(jasyptStringDigester.digest(cuentaInter.getCuentaBeneficiario()??));

			cuentasDao.save(cuentaInter);
			cuentaDTO.setCuentaRetiro(DTOAssembler.crearCuentaRetiroDTO((CuentaRetiro)cuentaInter));
		}else{
			logger.debug("nacional - folioCuenta = "+cuentaDTO.getIdCuentaRetiroNal());
			//boveda
			Boveda boveda = new Boveda();
			boveda.setIdBoveda(BigInteger.valueOf(cuentaDTO.getBoveda().getId()));
			//institucion beneficiaria
			Institucion beneficiario = new Institucion();
			beneficiario.setIdInstitucion(BigInteger.valueOf(cuentaDTO.getInstitucionBeneficiario().getId()));
			//cuenta nacional
			CuentaRetiroNacional cuentaNal = DTOAssembler.crearCuentaRetiroNacional(cuentaDTO);
			cuentaNal.setDivisa(divisa);
			cuentaNal.setEstado(estado);			
			cuentaNal.setInstitucion(institucion);
			cuentaNal.setMontoMaxMensual(cuentaDTO.getMontoMaximoMensual()==null?null:cuentaDTO.getMontoMaximoMensual());
			cuentaNal.setMontoMaxDiario(cuentaDTO.getMontoMaximoDiario()==null?null:cuentaDTO.getMontoMaximoDiario());
			cuentaNal.setMontoMaxPorTran(cuentaDTO.getMontoMaximoXTran()==null?null:cuentaDTO.getMontoMaximoXTran());
			cuentaNal.setMaxMovsMensual(cuentaDTO.getNumMaximoMovsXMes()==null?null:cuentaDTO.getNumMaximoMovsXMes().longValue());
			cuentaNal.setIdCuentaRetiroNal(cuentaDTO.getIdCuentaRetiroNal());
			cuentaNal.setInstBeneficiario(beneficiario);
			cuentaNal.setBoveda(boveda);
			//cuenta encriptada - no debera cambiar en el transcurso (solo en la pantalla de modificacion)
			cuentaNal.setCuentaDigest(jasyptStringDigester.digest(cuentaNal.getCuentaBeneficiario()));
			//id cuenta x institucion 
			cuentaNal.setIdCuentaPorInstitucion(cuentaDTO.getIdCuentaPorInstitucion());
			//salvando cuenta
			cuentasDao.save(cuentaNal);
			//completando DTO
			cuentaDTO.setCuentaRetiro(DTOAssembler.crearCuentaRetiroDTO((CuentaRetiro)cuentaNal));
		}

		//bitacora
		createBitacora(cuentaDTO);
		
	}
	
	/**
	 * Elimina una cuenta. 
	 * @cuenta id de la cuenta a eliminar
	 */
	public void eliminarCuenta(BigInteger cuenta){
		logger.debug("eliminando cuenta, id="+cuenta);
		/*Object objCuenta = cuentasDao.getCuenta(cuenta);
		if(objCuenta instanceof CuentaRetiroNacional){
			cuentasDao.delete((CuentaRetiroNacional)objCuenta);
		}else{
			cuentasDao.delete((CuentaRetiroInternacional)objCuenta);
		}*/
	}
	
	/**
	 * Realiza cambios sobre una cuenta existente
	 * @param cuenta objeto cuenta
	 */
	public void actualizarCuenta(CuentaRetiroEfectivoDTO cuentaDTO){
		logger.debug("::nuevo:: modificando cuenta tipo:"+cuentaDTO.getTipoCuenta());
		
		//divisa
		Divisa divisa = new Divisa();
		divisa.setIdDivisa(new BigInteger   (String.valueOf(cuentaDTO.getDivisa().getId()  )  ));
		
		//institucion
		Institucion institucion = new Institucion();
		institucion.setIdInstitucion(BigInteger.valueOf(cuentaDTO.getInstitucion().getId()));
		
		//estado de creacion = 19 (regresa al primer estado)
		EstadoInstruccionCat estado = edosDao.getEstado(BigInteger.valueOf(Long.valueOf(PRIMER_ESTADO)));
		
		BitacoraEstadosCuentaRetiro bitacora = null;

		//cuenta
		if(cuentaDTO.getTipoCuenta()=='N'){
			CuentaRetiroNacional cuentaNal = DTOAssembler.crearCuentaRetiroNacional(cuentaDTO);
			
			logger.debug("actualizando cuenta nacional dto.id="+cuentaDTO.getIdCuentaRetiroNal());
			cuentaNal.setIdCuentaRetiroNal(cuentaDTO.getIdCuentaRetiroNal());
			cuentaNal.setIdCuentaPorInstitucion(cuentaDTO.getIdCuentaPorInstitucion());
			cuentaNal.setIdCuentaRetiro(cuentaDTO.getIdCuentaRetiro());
			cuentaNal.setDivisa(divisa);
			cuentaNal.setEstado(estado);
			cuentaNal.setInstitucion(institucion);
			cuentaNal.setMaxMovsMensual(cuentaDTO.getNumMaximoMovsXMes()==null?null:new Long(cuentaDTO.getNumMaximoMovsXMes()));
			cuentaNal.setMontoMaxDiario(cuentaDTO.getMontoMaximoDiario()==null?null:cuentaDTO.getMontoMaximoDiario());
			cuentaNal.setMontoMaxMensual(cuentaDTO.getMontoMaximoMensual()==null?null:cuentaDTO.getMontoMaximoMensual());
			cuentaNal.setMontoMaxPorTran(cuentaDTO.getMontoMaximoXTran()==null?null:cuentaDTO.getMontoMaximoXTran());
			
			//boveda
			Boveda boveda = new Boveda();
			boveda.setIdBoveda(BigInteger.valueOf(cuentaDTO.getBoveda().getId()));
			cuentaNal.setBoveda(boveda);
			//institucion
			Institucion institucionBenef = new Institucion();
			institucionBenef.setIdInstitucion(BigInteger.valueOf(cuentaDTO.getInstitucionBeneficiario().getId()));
			cuentaNal.setInstBeneficiario(institucionBenef);
			//cuenta encriptada - no debera cambiar en el transcurso
			cuentaNal.setCuentaDigest(jasyptStringDigester.digest(cuentaNal.getCuentaBeneficiario()));
			//save
			cuentasDao.saveOrUpdate(cuentaNal);
			
			bitacora = bitacoraDao.getBitacoraPorCuenta(cuentaNal.getIdCuentaRetiro());
			
		}else{
			CuentaRetiroInternacional cuentaInt = DTOAssembler.crearCuentaRetiroInternacional(cuentaDTO);

			logger.debug("actualizando cuenta internacional dto.id="+cuentaDTO.getIdCuentaRetiroInt());
			cuentaInt.setIdCuentaRetiroInt(cuentaDTO.getIdCuentaRetiroInt());
			cuentaInt.setIdCuentaRetiro(cuentaDTO.getIdCuentaRetiro());
			cuentaInt.setDivisa(divisa);
			cuentaInt.setEstado(estado);
			cuentaInt.setInstitucion(institucion);
			//pendiente TODO
			//String cadena_encrip = jasyptStringDigester.digest("esta cadena");
			//cuentaInter.setCuentaDigest(jasyptStringDigester.digest(cuentaInter.getCuentaBeneficiario()??));
			
			cuentasDao.saveOrUpdate(cuentaInt);
			
			bitacora = bitacoraDao.getBitacoraPorCuenta(cuentaInt.getIdCuentaRetiro());
		}
		
		logger.debug("::nuevo:: actualizando bitacora...");
		bitacora.setFechaModificacion(new Date());
		bitacora.setModificacionFirmada(
				!StringUtils.isEmpty((String)cuentaDTO.getDatosFirmas().get("modificacion_isoFirmado"))?
						new ClobImpl((String)cuentaDTO.getDatosFirmas().get("modificacion_isoFirmado"))
						:null);	
		bitacora.setModificacionSerie(
				!StringUtils.isEmpty((String)cuentaDTO.getDatosFirmas().get("modificacion_serie"))?
					(String)cuentaDTO.getDatosFirmas().get("modificacion_serie")
					:null);
		bitacora.setModificacionUsuario(
				!StringUtils.isEmpty((String)cuentaDTO.getDatosFirmas().get("modificacion_usuario"))?
						(String)cuentaDTO.getDatosFirmas().get("modificacion_usuario")
						:null);
		
		bitacora.setAprobacionFirmada(null);
		bitacora.setAutorizacionFirmada(null);
		bitacora.setCancelacionFirmada(null);
		bitacora.setLiberacionFirmada(null);
		bitacora.setFechaAprobacion(null);
		bitacora.setFechaAutorizacion(null);
		bitacora.setFechaCancelacion(null);
		bitacora.setFechaLiberacion(null);
		bitacora.setAprobacionSerie(null);
		bitacora.setAprobacionUsuario(null);
		bitacora.setAutorizacionSerie(null);
		bitacora.setAutorizacionUsuario(null);
		bitacora.setCancelacionSerie(null);
		bitacora.setCancelacionUsuario(null);
		bitacora.setLiberacionSerie(null);
		bitacora.setLiberacionUsuario(null);
		bitacoraDao.saveOrUpdate(bitacora);

		//todo hitorico de modificacion
	}
	
	/**
	 * Cambia el estado de una cuenta. La firma viene en cada objeto de cuenta.
	 * @param id id de la cuenta
	 * @param cveCortaEdoNuevo clave corta del estado al que se cambiara
	 * @param datosFirma mapa con los datos que se insertaran como parte de la firma
	 */
	public boolean cambiarEstadoCuenta(long id, String cveCortaEdoNuevo, Map<String,Object> datosFirma){
		logger.debug("cambiando estado cuenta:"+id+"- edoNuevo="+cveCortaEdoNuevo);
		EstadoInstruccionCat edoNuevo = edosDao.getEstadoXClaveCorta(cveCortaEdoNuevo);
		logger.debug("edoNuevo.getDescripcion() = "+edoNuevo.getDescripcion());
		
		CuentaRetiro cuenta = (CuentaRetiro)cuentasDao.getCuenta(BigInteger.valueOf(id), 2);
			
		boolean existenMovimientosPE = existenMovimientosPendientesNalXCuenta(id);
		BitacoraEstadosCuentaRetiro bitacora = bitacoraDao.getBitacoraPorCuenta(id);
		if(cveCortaEdoNuevo.equals("AU")){
			bitacora.setFechaAutorizacion((Date)datosFirma.get("fecha"));
			bitacora.setAutorizacionFirmada(new ClobImpl((String)datosFirma.get("firma")));
			bitacora.setAutorizacionSerie((String)datosFirma.get("serie"));
			bitacora.setAutorizacionUsuario((String)datosFirma.get("usuario"));
		}else if(cveCortaEdoNuevo.equals("AP")){
			bitacora.setFechaAprobacion((Date)datosFirma.get("fecha"));
			bitacora.setAprobacionFirmada(new ClobImpl((String)datosFirma.get("firma")));
			bitacora.setAprobacionSerie((String)datosFirma.get("serie"));
			bitacora.setAprobacionUsuario((String)datosFirma.get("usuario"));
		}else if(cveCortaEdoNuevo.equals("LA")){
			bitacora.setFechaLiberacion((Date)datosFirma.get("fecha"));
			bitacora.setLiberacionFirmada(new ClobImpl((String)datosFirma.get("firma")));
			bitacora.setLiberacionSerie((String)datosFirma.get("serie"));
			bitacora.setLiberacionUsuario((String)datosFirma.get("usuario"));
			//preparando bitacora MOS. solo cuentas nuevas, cuentas modificadas = proceso nocturno 
			CuentaRetiroEfectivoDTO cuentaDTO = cuentasDao.getCuentaNacionalByCuentaRetiroNoEnMOS(cuenta.getIdCuentaRetiro());
			
//			if(bitacora.getFechaModificacion()==null && cuentaDTO != null){ //no modificada y no en MOS
			if(cuentaDTO != null){ //solo que no este en MOS
				/* 201105 PEHC> No se utiliza
				BitacoraRetiroChequera bRetiro = new BitacoraRetiroChequera();
				bRetiro.setIdCuentaRetiroNal(cuentaDTO.getIdCuentaRetiroNal());
	        	bRetiro.setMaxMovsMensualDisp(cuenta.getMaxMovsMensual()!= null?cuenta.getMaxMovsMensual():null);
	        	bRetiro.setMaxMovsMensualReal(cuenta.getMaxMovsMensual() != null?cuenta.getMaxMovsMensual():null);
	        	bRetiro.setMontoMaxDiarioDisp(cuenta.getMontoMaxDiario()!= null?cuenta.getMontoMaxDiario():null);
	        	bRetiro.setMontoMaxDiarioReal(cuenta.getMontoMaxDiario() != null?cuenta.getMontoMaxDiario():null);
	        	bRetiro.setMontoMaxMensualDisp(cuenta.getMontoMaxMensual()!= null?cuenta.getMontoMaxMensual():null);
		        bRetiro.setMontoMaxMensualReal(cuenta.getMontoMaxMensual() != null?cuenta.getMontoMaxMensual():null);
		        bRetiro.setMaxMovsMensualAct(0L);
		        bRetiro.setMontoMaxDiarioAct(BigDecimal.ZERO);
		        bRetiro.setMontoMaxMensualAct(BigDecimal.ZERO);
		        bRetiro.setVersion(0L);
				bRetiro.setFechaRegistro((Date)datosFirma.get("fecha"));
				//salvando bitacora MOS
				cuentasDao.save(bRetiro);/**/
			}
			
		}else if(cveCortaEdoNuevo.equals("CA") && !existenMovimientosPE){ //cancelacion y no hay movs pendientes
			bitacora.setFechaCancelacion((Date)datosFirma.get("fecha"));
			bitacora.setCancelacionFirmada(new ClobImpl((String)datosFirma.get("firma")));
			bitacora.setCancelacionSerie((String)datosFirma.get("serie"));
			bitacora.setCancelacionUsuario((String)datosFirma.get("usuario"));
		}else if(cveCortaEdoNuevo.equals("CA") && existenMovimientosPE){ //cancelacion y existen movs pendientes
			//no se cambia el estado
			return false;
		}
		cuenta.setEstado(edoNuevo);
		cuentasDao.saveOrUpdate(cuenta);
		
		//salvando bitacora
		bitacoraDao.saveOrUpdate(bitacora);
		return true;
	}
	
	/**
	 * Crea el objeto del historico correspondiente (nacional o internacional) 
	 * cuando se modifican las cuentas.
	 * 
	 * @param cuenta nacional o internacional
	 * @return tipoCuenta tipo cuenta
	 */
	private Object createHistorico(Object cuenta){
		
		if(cuenta instanceof CuentaRetiroNacional){
			CuentaRetiroNacional cuentaNal = (CuentaRetiroNacional)cuenta;
//			BitacoraEstadosCuentaRetiro bitacora = bitacoraDao.getBitacoraPorCuenta(cuentaNal.getCuentaRetiro().getIdCuentaRetiro());
			BitacoraEstadosCuentaRetiro bitacora = bitacoraDao.getBitacoraPorCuenta(cuentaNal.getIdCuentaRetiro());
			HistoricoAdmonCuentasRetiroNacional historico = new HistoricoAdmonCuentasRetiroNacional();
			historico.setAprobacionFirmada(bitacora.getAprobacionFirmada());
			historico.setAutorizacionFirmada(bitacora.getAutorizacionFirmada());
			historico.setBitacora(bitacora);
			historico.setCreacionFirmada(bitacora.getCreacionFirmada());
			historico.setCuentaBeneficiario(cuentaNal.getCuentaBeneficiario());
//			historico.setDivisa(cuentaNal.getCuentaRetiro().getDivisa());
//			historico.setEstado(cuentaNal.getCuentaRetiro().getEstado());
			historico.setDivisa(cuentaNal.getDivisa());
			historico.setEstado(cuentaNal.getEstado());
			historico.setFechaAprobacion(bitacora.getFechaAprobacion());
			historico.setFechaAutorizacion(bitacora.getFechaAutorizacion());
			historico.setFechaCreacion(bitacora.getFechaCreacion());
			historico.setFechaLiberacion(bitacora.getFechaLiberacion());
			historico.setInstBeneficiario(cuentaNal.getInstBeneficiario());
//			historico.setInstitucion(cuentaNal.getCuentaRetiro().getInstitucion());
			historico.setInstitucion(cuentaNal.getInstitucion());
			historico.setLiberacionFirmada(bitacora.getLiberacionFirmada());
//			historico.setMaxMovsMensual(cuentaNal.getCuentaRetiro().getMaxMovsMensual());
			historico.setMaxMovsMensual(cuentaNal.getMaxMovsMensual());
			historico.setModificacionFirmada(bitacora.getModificacionFirmada());
//			historico.setMontoMaxDiario(cuentaNal.getCuentaRetiro().getMontoMaxDiario());
//			historico.setMontoMaxMensual(cuentaNal.getCuentaRetiro().getMontoMaxMensual());
//			historico.setMontoMaxPorTran(cuentaNal.getCuentaRetiro().getMontoMaxPorTran());
			historico.setMontoMaxDiario(cuentaNal.getMontoMaxDiario());
			historico.setMontoMaxMensual(cuentaNal.getMontoMaxMensual());
			historico.setMontoMaxPorTran(cuentaNal.getMontoMaxPorTran());
			historico.setNombreBeneficiario(cuentaNal.getNombreBeneficiario());
			return historico;
		}
		
		CuentaRetiroInternacional cuentaInt = (CuentaRetiroInternacional)cuenta;
//		BitacoraEstadosCuentaRetiro bitacora = bitacoraDao.getBitacoraPorCuenta(cuentaInt.getCuentaRetiro().getIdCuentaRetiro());
		BitacoraEstadosCuentaRetiro bitacora = bitacoraDao.getBitacoraPorCuenta(cuentaInt.getIdCuentaRetiro());
		HistoricoAdmonCuentasRetiroInternacional historico = new HistoricoAdmonCuentasRetiroInternacional();
		historico.setAprobacionFirmada(bitacora.getAprobacionFirmada());
		historico.setAutorizacionFirmada(bitacora.getAutorizacionFirmada());
		historico.setBitacora(bitacora);
		historico.setCreacionFirmada(bitacora.getCreacionFirmada());
		historico.setCuentaBeneficiario(cuentaInt.getCuentaBeneficiario());
//		historico.setDivisa(cuentaInt.getCuentaRetiro().getDivisa());
//		historico.setEstado(cuentaInt.getCuentaRetiro().getEstado());
		historico.setDivisa(cuentaInt.getDivisa());
		historico.setEstado(cuentaInt.getEstado());
		historico.setFechaAprobacion(bitacora.getFechaAprobacion());
		historico.setFechaAutorizacion(bitacora.getFechaAutorizacion());
		historico.setFechaCreacion(bitacora.getFechaCreacion());
		historico.setFechaLiberacion(bitacora.getFechaLiberacion());
//		historico.setInstitucion(cuentaInt.getCuentaRetiro().getInstitucion());
		historico.setInstitucion(cuentaInt.getInstitucion());
		historico.setLiberacionFirmada(bitacora.getLiberacionFirmada());
//		historico.setMaxMovsMensual(cuentaInt.getCuentaRetiro().getMaxMovsMensual());
		historico.setMaxMovsMensual(cuentaInt.getMaxMovsMensual());
		historico.setModificacionFirmada(bitacora.getModificacionFirmada());
//		historico.setMontoMaxDiario(cuentaInt.getCuentaRetiro().getMontoMaxDiario());
//		historico.setMontoMaxMensual(cuentaInt.getCuentaRetiro().getMontoMaxMensual());
//		historico.setMontoMaxPorTran(cuentaInt.getCuentaRetiro().getMontoMaxPorTran());
		historico.setMontoMaxDiario(cuentaInt.getMontoMaxDiario());
		historico.setMontoMaxMensual(cuentaInt.getMontoMaxMensual());
		historico.setMontoMaxPorTran(cuentaInt.getMontoMaxPorTran());
		historico.setBancoBeneficiario(cuentaInt.getBancoBeneficiario());
		historico.setBancoIntermediario(cuentaInt.getBancoIntermediario());
		historico.setCuentaIntermediario(cuentaInt.getCuentaIntermediario());
		historico.setDetallesPago(cuentaInt.getDetallesPago());
		historico.setNombreBancoBeneficiario(cuentaInt.getNombreBancoBeneficiario());
		historico.setNombreCorto(cuentaInt.getNombreCorto());
		historico.setNombreIntermediario(cuentaInt.getNombreIntermediario());
		return historico;
	}
	
	/**
	 * Crea un registro en la bitacora de estados.
	 * @param cuenta Cuenta nacional o internacional
	 */
	public void createBitacoraEdoCuenta(CuentaRetiroEfectivoDTO cuenta){
			createBitacora(cuenta);	
	}
	
	/**
	 * Crea un registro en la bitacora de estados.
	 * @param cuenta Cuenta nacional o internacional
	 */
	private void createBitacora(Object cuenta){
		BitacoraEstadosCuentaRetiro bitacora = new BitacoraEstadosCuentaRetiro();
		
		CuentaRetiroEfectivoDTO cuentaDTO = (CuentaRetiroEfectivoDTO)cuenta;
		bitacora.setCreacionFirmada(
				!StringUtils.isEmpty((String)cuentaDTO.getDatosFirmas().get("creacion_isoFirmado"))?
						new ClobImpl((String)cuentaDTO.getDatosFirmas().get("creacion_isoFirmado"))
						:null);
		bitacora.setCreacionSerie(					
				!StringUtils.isEmpty((String)cuentaDTO.getDatosFirmas().get("creacion_serie"))?
						(String)cuentaDTO.getDatosFirmas().get("creacion_serie")
						:null);
		bitacora.setCreacionUsuario(
				!StringUtils.isEmpty((String)cuentaDTO.getDatosFirmas().get("creacion_usuario"))?
						(String)cuentaDTO.getDatosFirmas().get("creacion_usuario")
						:"indeval");
		
		bitacora.setCuentaRetiro(DTOAssembler.crearCuentaRetiro(cuentaDTO.getCuentaRetiro()));
		bitacora.setFechaCreacion(new Date());
		bitacoraDao.save(bitacora);
	}
	
	/**
	 * Obtiene la institucion a la cual pertenece la clave spei
	 * @param claveSpei
	 * @return Institucion
	 */
	public InstitucionDTO getInstitucionForClaveSpei(String claveSpei){
		if(StringUtils.isEmpty(claveSpei)) {
			throw new BusinessException(errorResolver.getMessage("JBR-010"), "JBR-010");
		}
		return institucionDaliDAO.buscarInstitucionPorClaveSpei(claveSpei);
	}
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de cuentas de retiro de efectivo
	 * 
	 * @param agente traspasante
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearCuentaRetiro(
			InstitucionDTO agente, InstitucionDTO beneficiario, DivisaDTO divisa, String cuentaBeneficiario, String nombreBeneficiario,
			String montoMensual, String montoDiario, String montoTransaccion, String movsMensuales, long[] esModificacion) throws BusinessException{
		logger.debug("Entrando a businessRulesCrearCuentaRetiro()");
		
		//Validacion de depositante traspasante existente para Banxico.
//		if(!depositanteValidoBanxicoDao.isDepositanteValidoBanxico(agente.getId())) {
//        	throw new BusinessException(errorResolver.getMessage("J0210", new Object[] {"No es un depositante valido para Banxico." }), "J0210");
//        }

        /*
         * Se agregan validaciones para el requerimiento R-IND-2014-010
         * 02/10/2014 Pablo Balderas.
         */
		// 1) Valida que el banco beneficiario tenga clave SPEI
		beneficiario = institucionDaliDAO.buscarInstitucionPorId(beneficiario.getId());
		if(StringUtils.isEmpty(beneficiario.getClaveSpei())) {
			throw new BusinessException(errorResolver.getMessage("JBR-012"), "JBR-012");
		}
		
		//Validacion de depositante receptor existente para Banxico no es necesaria, no interesa, lo que solamente interesa es que 
		//el depositante (traspasante) sea valido para Banxico.
		//Validacion traspasante con cuenta clabe.
		logger.debug("*** institucion:"+agente.getIdFolio()+", cuenta clabe:"+agente.getCuentaClabe());
		if(StringUtils.isEmpty(agente.getCuentaClabe())) {
        	throw new BusinessException(errorResolver.getMessage("J0143", new Object[] {"La institucion traspasante debe tener una cuenta CLABE." }), "J0143");
        }
		
/*		//Se comenta la validacion, Jaz Guerrero pidio que solo se validara que el receptor tuviera cuenta CLABE en el catalogo de instituciones.
  		//Validacion de receptor en las instituciones permitidas
		ParametrosDaliDTO parametros = parametrosDao.getAll();
		boolean tipoBenefOk = false;
		//si no esta definido pasan todos los tipos
		if(parametros != null && !StringUtils.isEmpty(parametros.getCtasBComTipoInstituciones())){
			String[] tiposOk = parametros.getCtasBComTipoInstituciones().split(",");
			for(int t=0; t<tiposOk.length; t++){
				if(beneficiario.getTipoInstitucionDTO().getClaveTipoInstitucion().equals(tiposOk[t])){
					tipoBenefOk = true;
					break;
				}
			}
			
			if(!tipoBenefOk){
				throw new BusinessException(errorResolver.getMessage("J0144", new Object[] {"El tipo de la institucion beneficiaria no esta autorizado para crear cuentas" }), "J0144");
			}
		}
*/		
		//validacion de receptor con cuenta clabe
		logger.debug("*** institucion:"+beneficiario.getIdFolio()+", cuenta clabe:"+beneficiario.getCuentaClabe());
		if(StringUtils.isEmpty(beneficiario.getCuentaClabe())) {
        	throw new BusinessException(errorResolver.getMessage("J0145", new Object[] {"La institucion beneficiaria debe tener una cuenta CLABE." }), "J0145");
        }
		
		//validacion de cuenta clabe del receptor numerico de 18 digitos
		if(!StringUtils.isEmpty(beneficiario.getCuentaClabe()) && !beneficiario.getCuentaClabe().matches("([0-9]{18})*")){
			throw new BusinessException(errorResolver.getMessage("J0146", new Object[] {"La cuenta CLABE de la institucion beneficiaria debe ser numerica y de longitud 18." }), "J0146");
		}
		
		//validacion de numero de cuenta tamanio 18 exactamente
		if(cuentaBeneficiario.length()!=18){
			throw new BusinessException(errorResolver.getMessage("J0112", new Object[] {"Longitud de numero de cuenta incorrecta (18)." }), "J0112");
		}
		
		//validacion nombreBeneficiario corresponda con la combinacion cuentaBeneficiario-bancoBeneficiario
		//se revisa por traspasante, de modo que la combinacion cuentaBeneficiario-bancoBeneficiario pueda existir con 
		//			diferente nombre siempre y cuando se trate de traspasantes distintos
		String[] nombreBeneficiarioEnBD = new String[1];
		nombreBeneficiarioEnBD[0] = "TBD";
		if(!nombreParaCuentaBancoBeneficiarioValido(agente, beneficiario, cuentaBeneficiario, nombreBeneficiario, nombreBeneficiarioEnBD, esModificacion)){
			throw new BusinessException(errorResolver.getMessage("J0136", new Object[] {nombreBeneficiarioEnBD[0]}), "J0136");
		}
		
		//validacion combinacion nombreBeneficiario-cuentaBeneficiario-bancoBeneficiario no se repita para el traspasante
		if(existeBancoNombreCuentaBeneficiario(agente, beneficiario, cuentaBeneficiario, nombreBeneficiario, esModificacion)){
			throw new BusinessException(errorResolver.getMessage("J0137", new Object[] {agente.getNombreCorto()}), "J0137");
		}
		
		//validacion de monto mensual decimal y mayor a 0 (en caso de aplicar)
		if(montoMensual != null && !montoMensual.equals("")){
			if(Constantes.CERO_BIG_DECIMAL.compareTo((new BigDecimal(montoMensual))) >= Constantes.CERO_INT){
				throw new BusinessException(errorResolver.getMessage("J0113", new Object[] {"El monto maximo mensual debe ser mayor a 0." }), "J0113");
			}
		}
		
		//validacion de monto diario decimal y mayor a 0 (en caso de aplicar)
		if(montoDiario != null && !montoDiario.equals("") 
			&& (Constantes.CERO_BIG_DECIMAL.compareTo((new BigDecimal(montoDiario))) >= Constantes.CERO_INT)){
				throw new BusinessException(errorResolver.getMessage("J0114", new Object[] {"El monto maximo diario debe ser mayor a 0." }), "J0114");
		}

		//validacion de monto diario no mayor a monto mensual
		if(montoMensual != null && !montoMensual.equals("") && montoDiario != null && !montoDiario.equals("") 
			&& Double.valueOf(montoDiario) > Double.valueOf(montoMensual)){
				throw new BusinessException(errorResolver.getMessage("J0140", new Object[] {"El monto maximo diario no debe ser mayor al monto maximo mensual." }), "J0140");
		}
		
		//validacion de monto por transaccion decimal y mayor a 0 (en caso de aplicar)
		if(montoTransaccion != null && !montoTransaccion.equals("")){
			if(Constantes.CERO_BIG_DECIMAL.compareTo((new BigDecimal(montoTransaccion))) >= Constantes.CERO_INT){
				throw new BusinessException(errorResolver.getMessage("J0115", new Object[] {"El monto maximo por transaccion debe ser mayor a 0." }), "J0115");
			}
		}
		
		//validacion de monto x transaccion no mayor a monto mensual
		if(montoMensual != null && !montoMensual.equals("") && montoTransaccion != null && !montoTransaccion.equals("") 
				&& Double.valueOf(montoTransaccion) > Double.valueOf(montoMensual)){
			throw new BusinessException(errorResolver.getMessage("J0141", new Object[] {"El monto maximo por transaccion no debe ser mayor al monto maximo mensual." }), "J0141");
		}

		//validacion de monto x transaccion no mayor a monto diario
		if(montoDiario != null && !montoDiario.equals("") && montoTransaccion != null && !montoTransaccion.equals("") 
				&& Double.valueOf(montoTransaccion) > Double.valueOf(montoDiario)){
			throw new BusinessException(errorResolver.getMessage("J0142", new Object[] {"El monto maximo por transaccion no debe ser mayor al monto maximo diario." }), "J0142");
		}
		
		//validacion de numero de movimientos mensual entero y mayor a 0 (en caso de aplicar)
		if(movsMensuales != null && !movsMensuales.equals("")){
			if(!esNumeroNaturalBigIntegerMayorACero(movsMensuales)){
				throw new BusinessException(errorResolver.getMessage("J0116", new Object[] {"El numero de movimientos mensuales debe ser numero entero y mayor a 0." }), "J0116");
			}
		}
		
		logger.debug("Saliendo de businessRulesCrearCuentaRetiro()");
		return true;
	}
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de cuentas de retiro de efectivo internacional
	 * 
	 * @param agente traspasante
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearCuentaRetiroInternacional(
			DivisaDTO divisa,
			String cuentaBeneficiarioFinal, String nombreBeneficiarioFinal,
			String bancoBeneficiario, String nombreBancoBeneficiario, String cuentaBancoBeneficiario,
			String bancoIntermediario, String nombreBancoIntermediario, long[] esModificacion)throws BusinessException {
		logger.debug("Entrando a businessRulesCrearCuentaRetiroInternacional()");
	try{
		//que no sean pesos mexicanos
		if(divisa.getId() == DaliConstants.ID_DIVISA_MEXICAN_PESO){
			throw new BusinessException(errorResolver.getMessage("J0117", new Object[] {"La divisa debe ser diferente a Pesos Mexicanos."}), "J0117");
		}
		
		//validar tamanos minimos de campos requeridos

		//obtener datos de la divisa
		PaisDivisa datosDivisa = paisDivisaDao.getDatosPorDivisa(BigInteger.valueOf(divisa.getId()));
		if(datosDivisa == null){
			throw new BusinessException(errorResolver.getMessage("J0118",
					new Object[] {"No existe la relacion Pais-Divisa para la divisa "+divisa.getClaveAlfabetica()+"." }), "J0118");
		}

		//para USD ---------------------------------------------------------------------------------------------------
		if(divisa.getId() == DaliConstants.ID_DIVISA_USD){
			//cuenta beneficiario final numerica
			if(!esNumerico(cuentaBeneficiarioFinal)){
				throw new BusinessException(errorResolver.getMessage("J0131",
						new Object[] {"La cuenta del beneficiario final debe ser numerica." }), "J0131");
			}
			
			//el banco beneficiario NO es numerico (NO es el ABA) y
			//el banco beneficiario es menor a 6 caracteres (no alcanza para validar pais BIC
			else if(!esNumerico(bancoBeneficiario) && bancoBeneficiario.length()<6){
				throw new BusinessException(errorResolver.getMessage("J0130",
						new Object[] {"El banco beneficiario debe ser numerico o mayor a 6 caracteres." }), "J0130");
			}
			
			//el banco beneficiario NO es numerico (NO es el ABA) y
			//el pais BIC del banco beneficiario NO corresponde a la divisa 
			else if(!esNumerico(bancoBeneficiario) && !esPaisBICParaDivisa(datosDivisa.getPais().getClave(), bancoBeneficiario)){
				//validar requeridos
				if(cuentaBancoBeneficiario == null || cuentaBancoBeneficiario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0119",
							new Object[] {"El BIC del banco beneficiario no es compatible con la divisa por lo que  " +
										  "la cuenta del banco beneficiario es requerida." }), "J0119");
				}
				
				//cuenta banco beneficiario numerica
				else if(!esNumerico(cuentaBancoBeneficiario)){
					throw new BusinessException(errorResolver.getMessage("J0120",
							new Object[] {"La cuenta del banco beneficiario debe ser numerica." }), "J0120");
				}
				
				else if(bancoIntermediario == null || bancoIntermediario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0121",
							new Object[] {"El BIC del banco beneficiario no es compatible con la divisa por lo que  " +
										  "el banco intermediario es requerido." }), "J0121");
				}
				
				else if(nombreBancoIntermediario == null || nombreBancoIntermediario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0122",
							new Object[] {"El BIC del banco beneficiario no es compatible con la divisa por lo que  " +
										  "el nombre del banco intermediario es requerido." }), "J0122");
				}
				
				//el banco intermediario NO es numerico (NO es el ABA) y
				//el banco intermediario es menor a 6 caracteres (no alcanza para validar pais BIC
				if(!esNumerico(bancoIntermediario) && bancoIntermediario.length()<6){
					throw new BusinessException(errorResolver.getMessage("J0123",
							new Object[] {"El banco intermediario debe ser numerico o mayor a 6 caracteres." }), "J0123");
				}
				
				//si bco intermediario NO es numerico (no es el ABA) 
				//el BIC del bco intermediario NO corresponde a divisa
				else if(!esNumerico(bancoIntermediario) && !esPaisBICParaDivisa(datosDivisa.getPais().getClave(), bancoIntermediario)){
					throw new BusinessException(errorResolver.getMessage("J0124",
							new Object[] {"El valor del banco intermediario debe ser numerico (ABA) o " +
									"el BIC debe ser compatible con la divisa." }), "J0124");
				}
			}
			
			//el banco beneficiario es numerico => es ABA
			
			//el pais BIC del banco beneficiario si corresponde a la divisa 

		}
		
		//para Euros ---------------------------------------------------------------------------------------------------
		else if(divisa.getId() == DaliConstants.ID_DIVISA_EURO){
			
			//el banco beneficiario es menor a 6 caracteres (no alcanza para validar pais BIC
			if(!esNumerico(bancoBeneficiario) && bancoBeneficiario.length()<6){
				throw new BusinessException(errorResolver.getMessage("J0130",
						new Object[] {"El banco beneficiario debe ser numerico o mayor a 6 caracteres." }), "J0130");
			}
			
			//el pais BIC del banco beneficiario NO es de la union europea 
			if(!esPaisBICUnionEuropea(BigInteger.valueOf(divisa.getId()), bancoBeneficiario)){
				//cuenta beneficiario final numerica,  no es el IBAN, debe ser numerico
				if(!esNumerico(cuentaBeneficiarioFinal)){
					throw new BusinessException(errorResolver.getMessage("J0125",
							new Object[] {"El BIC del banco beneficiario no pertenece a la union europea por lo que  " +
										  "la cuenta del beneficiario final debe ser numerica." }), "J0125");
				}
				
				//validar requeridos
				else if(cuentaBancoBeneficiario == null || cuentaBancoBeneficiario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0126",
							new Object[] {"El BIC del banco beneficiario no pertenece a la union europea por lo que  " +
										  "la cuenta del banco beneficiario es requerida." }), "J0126");
				}
				
				else if(bancoIntermediario == null || bancoIntermediario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0127",
							new Object[] {"El BIC del banco beneficiario no pertenece a la union europea por lo que  " +
										  "el banco intermediario es requerido." }), "J0127");
				}
				
				else if(nombreBancoIntermediario == null || nombreBancoIntermediario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0128",
							new Object[] {"El BIC del banco beneficiario no pertenece a la union europea por lo que  " +
										  "el nombre del banco intermediario es requerido." }), "J0128");
				}
				
				//el BIC del banco intermediario NO es de la union europea 
				else if(!esPaisBICUnionEuropea(BigInteger.valueOf(divisa.getId()), bancoIntermediario)){
					throw new BusinessException(errorResolver.getMessage("J0129",
							new Object[] {"El BIC del banco intermediario no pertenece a la union europea." }), "J0129");
				}
				
			}
			
			//else, no validar  cuentaBeneficiarioFinal numerica, puede ser el IBAN
		}

		
		//Para el resto de las divisas && (BIC_pais != divisa || BIC_pais = MX) ----------------------------------------
		else if(divisa.getId() != DaliConstants.ID_DIVISA_USD && divisa.getId() != DaliConstants.ID_DIVISA_EURO) {
			//cuenta beneficiario final numerica
			if(!esNumerico(cuentaBeneficiarioFinal)){
				throw new BusinessException(errorResolver.getMessage("J0131",
						new Object[] {"La cuenta del beneficiario final debe ser numerica." }), "J0131");
			}
			
			//el banco beneficiario NO es numerico (NO es el ABA) y
			//el banco beneficiario es menor a 6 caracteres (no alcanza para validar pais BIC
			else if(!esNumerico(bancoBeneficiario) && bancoBeneficiario.length()<6){
				throw new BusinessException(errorResolver.getMessage("J0130",
						new Object[] {"El banco beneficiario debe ser numerico o mayor a 6 caracteres." }), "J0130");
			}
			
			//el pais BIC del banco beneficiario NO corresponde a la divisa 
			else if(!esPaisBICParaDivisa(datosDivisa.getPais().getClave(), bancoBeneficiario)){
				//validar requeridos
				if(cuentaBancoBeneficiario == null || cuentaBancoBeneficiario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0119",
							new Object[] {"El BIC del banco beneficiario no es compatible con la divisa por lo que  " +
										  "la cuenta del banco beneficiario es requerida." }), "J0119");
				}
				
				else if(bancoIntermediario == null || bancoIntermediario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0121",
							new Object[] {"El BIC del banco beneficiario no es compatible con la divisa por lo que  " +
										  "el banco intermediario es requerido." }), "J0121");
				}
				
				else if(nombreBancoIntermediario == null || nombreBancoIntermediario.equals("")){
					throw new BusinessException(errorResolver.getMessage("J0122",
							new Object[] {"El BIC del banco beneficiario no es compatible con la divisa por lo que  " +
										  "el nombre del banco intermediario es requerido." }), "J0122");
				}
				
				//el banco intermediario es menor a 6 caracteres (no alcanza para validar pais BIC
				if(bancoIntermediario.length()<6){
					throw new BusinessException(errorResolver.getMessage("J0132",
							new Object[] {"El banco intermediario debe ser mayor a 6 caracteres." }), "J0132");
				}
				
				//el BIC del banco intermediario NO es compatible con la divisa
				else if(!esPaisBICParaDivisa(datosDivisa.getPais().getClave(), bancoIntermediario)){
					throw new BusinessException(errorResolver.getMessage("J0133",
							new Object[] {"El BIC del banco intermediario no es compatible con la divisa." }), "J0133");
				}
				
				//cuenta banco beneficiario numerica
				else if(!esNumerico(cuentaBancoBeneficiario)){
					throw new BusinessException(errorResolver.getMessage("J0134",
							new Object[] {"La cuenta del banco beneficiario debe ser numerica." }), "J0134");
				}
			}
        }
		
		logger.debug("Saliendo de businessRulesCrearCuentaRetiroInternacional()");
	}catch(BusinessException be){
		logger.debug("#*$%%#*%% Business Exception... ");
		throw be;
	}catch(Exception e ){
		logger.debug("#*$%%#*%% Exception... ");
		e.printStackTrace();
	}catch(Throwable t){
		logger.debug("#*$%%#*%% Throwable... ");
		t.printStackTrace();
	}
		return true;
	}
	
	/**
	 * Verifica si el pais del BIC corresponde a la divisa.
	 * @param BIC bic de la institucion
	 * @param String el pais de la divisa
	 * @param true - corresponde, false - no corresponde
	 */
	private boolean esPaisBICParaDivisa(String paisDivisa, String BIC){
		logger.debug("en esPaisBICParaDivisa paisDivisa:"+paisDivisa+", BIC:"+BIC);
		logger.debug("return:"+(paisDivisa.equals(BIC.substring(4,6))? true:false));
		return paisDivisa.equals(BIC.substring(4,6))? true:false;
	}
	
	/**
	 * Verifica si el pais del BIC corresponde a la divisa y este es de la union europea.
	 * @param BIC bic de la institucion
	 * @param idDivisa id de la divisa
	 * @param true - corresponde, false - no corresponde
	 */	
	private boolean esPaisBICUnionEuropea(BigInteger idDivisa, String BIC) throws BusinessException{
		logger.debug("en esPaisBICUnionEuropea idDivisa="+idDivisa+", BIC:"+BIC+", BIC.substring(4,6):"+BIC.substring(4,6));
		PaisDivisa datosPorPais = paisDivisaDao.getDatosPorPais(BIC.substring(4,6));
		logger.debug("datosPorPais:"+datosPorPais);
		if(datosPorPais == null){
			logger.debug("probando mi excepcion....");
			throw new BusinessException(errorResolver.getMessage("J0135", new Object[] {BIC.substring(4,6),BIC}), "J0135");
		}
		logger.debug("datosPorPais.getDeUnionEuropea():"+datosPorPais.getDeUnionEuropea());
		return datosPorPais!=null && datosPorPais.getDeUnionEuropea() == 1 ? true:false;
	}
	
	/**
	 * Valida si una cadena es numero natural o no
	 * @param snumber String a validar-96
	 * @return true - es numero natural, false - no es numero natural 
	 */
	private boolean esNumeroNaturalBigIntegerMayorACero(String snumber){
		try{
			if(snumber==null || snumber.equals("") || snumber.substring(0,1).equals("0")){
				return false;
			}
			BigInteger inumber = new BigInteger(snumber);
			if(inumber.compareTo(new BigInteger("0")) < 0){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Validar numerico 
	 * @param snumber String a validar-96
	 * @return true - es numero natural, false - no es numero natural 
	 */
	private boolean esNumerico(String snumber){
		try{
			if(snumber==null || snumber.equals("")){
				return false;
			}
			BigInteger inumber = new BigInteger(snumber);
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Llena el combo de estados para la consulta de las cuentas 
	 */
	@SuppressWarnings("unchecked")
	public List<EstadosCuentaDTO> getEstadosCuenta(){
		List<EstadoInstruccionCat> estados = edosDao.getAll();
		List<EstadosCuentaDTO> estadosDTO = new ArrayList<EstadosCuentaDTO>(0); 
		
		for(EstadoInstruccionCat estado: estados){
			estadosDTO.add(DTOAssembler.crearEstadosCuentaDTO(estado));
		}
		
		return estadosDTO;
	}
	
	/**
	 * Busca el estado por id 
	 */
	public EstadosCuentaDTO getEstadoPorId(long id){
		EstadoInstruccionCat estado = edosDao.getEstado(BigInteger.valueOf(id));
		EstadosCuentaDTO dto = DTOAssembler.crearEstadosCuentaDTO(estado);

		if (estado == null) {
			dto = new EstadosCuentaDTO();
			dto.setId(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion("TODOS");
		}

		return dto;
	}
	
	/**
	 * Obtiene el folio para la tabla CuentaRetiro
	 * @param esNacional true-obtiene la secuencia para cuentas nacionales
	 * 					 false-obtiene la secuencia para cuentas internacionales
	 * @return valor de la secuencia 
	 */
	public BigInteger obtenerFolioCuenta(boolean esNacional){
		logger.debug("en obtenerFolioCuenta, esNacional:"+esNacional);
		return esNacional?
				utilService.getFolio(C_CUENTA_RETIRO_NAL_SEQ)
				:utilService.getFolio(C_CUENTA_RETIRO_INT_SEQ);
	}
	
	/**
	 * Obtiene el folio para la tabla CuentaRetiro por institucion
	 * @param esNacional true-obtiene la secuencia para cuentas nacionales
	 * 					 false-obtiene la secuencia para cuentas internacionales
	 * @param idInstitucion id de bd de la institucion para la que se requiere el consecutivo de sus cuentas
	 * @return valor de la secuencia 
	 */
	public BigInteger obtenerFolioCuenta(boolean esNacional, Long idInstitucion){
		logger.debug("en obtenerFolioCuenta por institucion, esNacional:"+esNacional);
		BigInteger id = BigInteger.valueOf(0);
		String secuencia = "SEQ_CUENTA_RETIRO_"+org.apache.commons.lang.StringUtils.leftPad(idInstitucion.toString(), PADDING_LENGTH, '0');
		try{//existe la secuencia -> consigue el id
			id = secuenciaDaliDao.getConsecutivo(secuencia);
		}catch(Exception e){ //no existe la secuencia -> la crea
			Long lid = cuentasDao.getConsecutivoCreandoParaInstitucion(secuencia);
			id = BigInteger.valueOf(lid);
		}
		return id;
	}
	
	/**
	 * Busca entre las cuentas la combinacion nombre-cuenta-banco beneficiario para que no se repita
	 * para la misma institucion traspasante.
	 * 1. En caso de modificacion busca si alguna otra cuenta distinta a la que se esta modificando ya tiene la combinacion de pantalla 
	 *    si encuentra alguna seria un error.
     * 2. En caso de alta no se considera un folio de cuenta, solo se busca entre las existentes.
	 * @return boolean true - ya existe la combinacion para el traspasante
	 *   			   false -  no existe la combinacion para el traspasante
	 */
	public boolean existeBancoNombreCuentaBeneficiario(
			InstitucionDTO traspasante, InstitucionDTO beneficiario, String cuentaBeneficiario, String nombreBeneficiario, long[] esModificacion){
		CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
		criterio.setInstitucion(traspasante);
		criterio.setInstitucionBeneficiario(beneficiario);
		criterio.setCuentaBeneficiario(cuentaBeneficiario);
		criterio.setNombreBeneficiario(nombreBeneficiario);
		
		criterio.setFolioCuenta(esModificacion == null || esModificacion[0] == 0? 
									null				 //no es modificacion
									:""+esModificacion[1]); //es modificacion, se asigna el id de la cuenta
		
		List<CuentaRetiroEfectivoDTO> cuentas = cuentasDao.buscarCuentasBancoNombreCuentaBeneficiario(criterio);
		return cuentas==null||cuentas.size()==0? false:true;
	}
	
	/**
	 * Busca la combinacion cuenta-banco beneficiario.
	 * Si existe en la tabla, lo compara con el nombre
	 * 		- (return true) el nombre corresponde - ok
	 * 		- (return false) el nombre no corresponde
	 * No existe en la tabla
	 * 		- (return true) puede darse de alta la combinacion cuenta-banco-nombre sin problema
	 */
	public boolean nombreParaCuentaBancoBeneficiarioValido(
			InstitucionDTO traspasante, InstitucionDTO beneficiario, String cuentaBeneficiario, String nombreBeneficiarioPrueba, String[] nombreBeneficiarioEnBD,  long[] esModificacion){
		//si es modificacion cuidar que banco-cuenta no exista para otro nombre diferente 
		//en otras cuentas diferentes al que se coloca en la cuenta modificada
		String folioCuenta = esModificacion == null || esModificacion[0] == 0? 
								null				 //no es modificacion
								:""+esModificacion[1]; //es modificacion, se asigna el id de la cuenta		
		
		CuentaRetiroEfectivoDTO cuenta = cuentasDao.buscarPorCombinacionBancoCuentaBeneficiario(traspasante, beneficiario, cuentaBeneficiario, folioCuenta);
		
		//no existe, no hay problema
		if(cuenta == null){
			return true; 
		}
		//nombre beneficiario no se valida null; es requerido, nunca deberia venir en null.
		//existe para otro nombre, no deja insertar 
		else if(!cuenta.getNombreBeneficiario().equals(nombreBeneficiarioPrueba)){
			nombreBeneficiarioEnBD[0] = cuenta.getNombreBeneficiario();
			return false;
		}
		//existe y el nombre cuadra, no hay problema
		return true;
	}
	
	/**
	 * Define si es posible o no realizar modificaciones
	 * @return Map {"existenParametros"-Boolean, "aplicaModificaciones"-Boolean, "diaInhabil"-Boolean, "mensaje"-String}
	 */
	public Map<String, Object> revisarModificacionesHabilitadas(){
		
		 Map<String, Object> map = new HashMap<String, Object>(0);
		
		if(diaInhabilDaliDao.esInhabil(dateUtilsDao.getDateFechaDB())){
			map.put("diaInhabil", Boolean.valueOf(true));
			map.put("existenParametros", Boolean.valueOf(false)); //no aplica
			map.put("aplicaModificaciones", Boolean.valueOf(false)); //false, es dia inhabil
			map.put("mensaje", "Las modificaciones a las cuentas se realizan solo en dias h\u00E1biles.");
			return map;
		}
		
		ParametrosDaliDTO parametros = parametrosDao.getAll();
		if(parametros == null 
				|| parametros.getHoraInicioModificacionCuentas() == null 
				|| parametros.getHoraFinModificacionCuentas() == null){
			map.put("diaInhabil", Boolean.valueOf(false)); //no aplica
			map.put("existenParametros", Boolean.valueOf(false)); 
			map.put("aplicaModificaciones", Boolean.valueOf(false));//false, no hay parametros de horario bien definidos	
			map.put("mensaje", "Los par\u00E1metros de inicio y fin de horario para modificaci\u00f3n no han sido bien definidos.");
			return map;
		}

		String horaConsulta = dateUtilsDao.getStringHoras();
		String horaIniModif = parametros.getHoraInicioModificacionCuentas(); 
		String horaFinModif = parametros.getHoraFinModificacionCuentas();
		
		horaIniModif = horaIniModif.substring(0, horaIniModif.indexOf(":"))+horaIniModif.substring(horaIniModif.indexOf(":")+1);
		horaFinModif = horaFinModif.substring(0, horaFinModif.indexOf(":"))+horaFinModif.substring(horaFinModif.indexOf(":")+1);
		
		logger.debug("horaConsulta:"+horaConsulta);
		logger.debug("horaIniModif:"+horaIniModif);
		logger.debug("horaFinModif:"+horaFinModif);
		
		int ihoraConsulta = Integer.parseInt(horaConsulta);
		int ihoraIniModif = Integer.parseInt(horaIniModif);
		int ihoraFinModif = Integer.parseInt(horaFinModif);
		
		//el rango permitido contiene las 00hrs, horaConsulta antes de las 00hrs
		if( (ihoraFinModif < ihoraIniModif && ihoraConsulta < 2359 && ihoraConsulta <= ihoraFinModif)
				//el rango permitido contiene las 00hrs, horaConsulta despues de las 00hrs
				|| (ihoraFinModif < ihoraIniModif && ihoraConsulta < 2359 && ihoraIniModif <= ihoraConsulta)
				//rango normal
				|| (ihoraFinModif > ihoraIniModif && ihoraIniModif <= ihoraConsulta && ihoraConsulta <= ihoraFinModif) ){
			map.put("diaInhabil", Boolean.valueOf(false));
			map.put("existenParametros", Boolean.valueOf(true));
			map.put("aplicaModificaciones", Boolean.valueOf(true));//true, la hora esta dentro del parametro de modificacion
			map.put("mensaje","La modificaci\u00f3n es posible, la pantalla de modificaci\u00f3n deber\u00EDa ser mostrada.");
			
		}else{
			map.put("diaInhabil", Boolean.valueOf(false));
			map.put("existenParametros", Boolean.valueOf(true));
			map.put("aplicaModificaciones", Boolean.valueOf(false));//false, la hora no esta en el rango de modificacion
			map.put("mensaje", 					    
					    "El horario de modificaci\u00f3n es de " 
					  + horaIniModif.substring(0,2) + ":" + horaIniModif.substring(2) + " hrs a " 
					  + horaFinModif.substring(0,2) + ":" + horaFinModif.substring(2) + " hrs.");
		}
		
		return map;
	}
	
	/** 
	 * Revisar movimientos pendientes de la cuenta
	 * @param id id de la cuenta
	 * @return esNacional true-nacional, false-internacional
	 * @return boolean 
	 */
	public boolean existenMovimientosPendientesNalXCuenta(long id){
		List<RetiroEfectivoDTO> movs = cuentasDao.buscarMovimientosPendientesNal(""+id);
		return movs==null||movs.size()==0? false: true;
	}
	
	/**
	 * Obtiene todos los movimientos pendientes y los acomoda en listas de acuerdo a la cuenta 
	 * que implican. Se colocan las listas en un mapa cuya 'key' es el numero de cuenta.
	 * 
	 * @param esNacional true- es nacional, false-es internacional
	 * @param List<CuentaRetiroEfectivoDTO> lista de cuentas a revisar
	 * @return Map <Long, List<Object>>
	 */
	public Map <Long, List<Object>> obtenerRetirosPendientes(boolean esNacional, List<CuentaRetiroEfectivoDTO> cuentas){
		//completar funcionalidad para internacional

		//obteniendo la lista de cuentas
		String idCuentas = "";
		if(cuentas == null || cuentas.size() == 0){
			return null;
		}
		for(int i=0; i<cuentas.size(); i++){
			CuentaRetiroEfectivoDTO cuenta = cuentas.get(i);
			idCuentas = idCuentas + cuenta.getIdCuentaRetiro() + (i+1 == cuentas.size()? "":","); 
		}
		
		List<RetiroEfectivoDTO> movs = esNacional? cuentasDao.buscarMovimientosPendientesNal(idCuentas):null;
		Map <Long, List<Object>> map = new HashMap <Long, List<Object>>(0);
		
		logger.debug("obtenerRetirosPendientes:"+movs);
		
		if(movs == null || movs.size() == 0){
			logger.debug("no hay movimientos pendientes, se va vacio");
			return new HashMap <Long, List<Object>>(0);
		}
		
		for(RetiroEfectivoDTO mov : movs){
			List<Object> lista = new ArrayList<Object>(0);
			
			//la cuenta ya esta en el mapa
			if(map.containsKey(mov.getIdCuentaBeneficiario())){
				lista = map.get(mov.getIdCuentaBeneficiario());
				lista.add(mov);
			}
			//la cuenta no esta en el mapa
			else{
				lista.add(mov);
				map.put(mov.getIdCuentaBeneficiario(), lista);
			}
		}
		
		return map;
	}
	
	public EstatusCuentaDao getEdosDao() {
		return edosDao;
	}

	public void setEdosDao(EstatusCuentaDao edosDao) {
		this.edosDao = edosDao;
	}

	public CuentasRetiroEfectivoDao getCuentasDao() {
		return cuentasDao;
	}

	public void setCuentasDao(CuentasRetiroEfectivoDao cuentasDao) {
		this.cuentasDao = cuentasDao;
	}

	public HistoricoAdmonCuentasRetiroDao getHistoricoDao() {
		return historicoDao;
	}

	public void setHistoricoDao(HistoricoAdmonCuentasRetiroDao historicoDao) {
		this.historicoDao = historicoDao;
	}

	public BitacoraEstadosCuentaDao getBitacoraDao() {
		return bitacoraDao;
	}

	public void setBitacoraDao(BitacoraEstadosCuentaDao bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

	public DepositanteValidoBanxicoDao getDepositanteValidoBanxicoDao() {
		return depositanteValidoBanxicoDao;
	}

	public void setDepositanteValidoBanxicoDao(
			DepositanteValidoBanxicoDao depositanteValidoBanxicoDao) {
		this.depositanteValidoBanxicoDao = depositanteValidoBanxicoDao;
	}

	public MessageResolver getErrorResolver() {
		return errorResolver;
	}

	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	public PaisDivisaDao getPaisDivisaDao() {
		return paisDivisaDao;
	}

	public void setPaisDivisaDao(PaisDivisaDao paisDivisaDao) {
		this.paisDivisaDao = paisDivisaDao;
	}

	public UtilServices getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	public StandardStringDigester getJasyptStringDigester() {
		return jasyptStringDigester;
	}

	public void setJasyptStringDigester(StandardStringDigester jasyptStringDigester) {
		this.jasyptStringDigester = jasyptStringDigester;
	}

	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	public DiaInhabilDaliDao getDiaInhabilDaliDao() {
		return diaInhabilDaliDao;
	}

	public void setDiaInhabilDaliDao(DiaInhabilDaliDao diaInhabilDaliDao) {
		this.diaInhabilDaliDao = diaInhabilDaliDao;
	}

	public ParametrosDaliDao getParametrosDao() {
		return parametrosDao;
	}

	public void setParametrosDao(ParametrosDaliDao parametrosDao) {
		this.parametrosDao = parametrosDao;
	}

	public SecuenciaDaliDao getSecuenciaDaliDao() {
		return secuenciaDaliDao;
	}

	public void setSecuenciaDaliDao(SecuenciaDaliDao secuenciaDaliDao) {
		this.secuenciaDaliDao = secuenciaDaliDao;
	}



	/**
	 * @return the institucionDaliDAO
	 */
	public InstitucionDaliDAO getInstitucionDaliDAO() {
		return institucionDaliDAO;
	}



	/**
	 * @param institucionDaliDAO the institucionDaliDAO to set
	 */
	public void setInstitucionDaliDAO(InstitucionDaliDAO institucionDaliDAO) {
		this.institucionDaliDAO = institucionDaliDAO;
	}
	
}
