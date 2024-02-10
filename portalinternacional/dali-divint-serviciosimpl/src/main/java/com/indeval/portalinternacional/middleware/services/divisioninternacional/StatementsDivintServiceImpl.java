/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DomiciliosInstitucionesDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.DomiciliosInstituciones;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatementDivint;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsTotalesVO;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;
import com.indeval.portalinternacional.persistence.dao.StatementsDivIntDao;
import com.indeval.portalinternacional.persistence.dao.TipoBeneficiarioDao;

/**
 * Implementacion del Servicio para el control de los statements
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class StatementsDivintServiceImpl implements StatementsDivintService {

	/** Log de clase. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/** Dao para las consultas de statments */
	private StatementsDivIntDao statementsDivIntDao;
	/** Dao para las consultas de instituciones */
	private InstitucionDao institucionDao;
	/**  Dao para las consultas de emisiones*/
	private EmisionDao emisionDao;
	/** Dao para las consultas del tipo beneficiario */
	private TipoBeneficiarioDao tipoBeneficiarioDao;
	/** Validaro de Internacional */
	private ValidatorDivIntService validatorDivIntService;
	/** Servicio de fechas */
	private DateUtilService dateUtilService;
	/** Dao para los domicilios de las instituciones */
	private DomiciliosInstitucionesDao domiciliosInstitucionesDao;

	public PaginaVO consultaStatements(ConsultaStatementsParam param, PaginaVO paginaVO) throws BusinessException {

		log.info("Entrando a StatementsDivintServiceImpl.guardaStatement con vo: [" + param + "]");
		if (param == null) {
			throw new BusinessException("Parametros vacios");
		}
		if (paginaVO == null) {
			throw new BusinessException("Estado de la Paginacion vacio");
		}

		paginaVO = statementsDivIntDao.findStatements(param, paginaVO);

		if (paginaVO != null
				&& paginaVO.getRegistros() != null
				&& paginaVO.getRegistros().size() > 0) {
			ConsultaStatementsTotalesVO totales = statementsDivIntDao.findTotalesStatements(param);
			if (totales != null) {
				paginaVO.getValores().put("totalPosicion", totales.getPosicion());
				paginaVO.getValores().put("totalDividendo", totales.getDividendo());
				paginaVO.getValores().put("totalImpuestos", totales.getImpuestos());
				paginaVO.getValores().put("totalDividendoNeto", totales.getDividendoNeto());
			}
			if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
				Long totalPagPosicion = 0l;
				BigDecimal totalPagDividendo = BigDecimal.ZERO;
				BigDecimal totalPagImpuestos = BigDecimal.ZERO;
				BigDecimal totalPagDividendoNeto = BigDecimal.ZERO;
				List<StatementDivintVO> resultados = paginaVO.getRegistros();
				for (StatementDivintVO reg : resultados) {
					if (reg.getNumeroTitulos() != null) {
						totalPagPosicion += reg.getNumeroTitulos();
					}
					if (reg.getDividendo() != null) {
						totalPagDividendo = totalPagDividendo.add(reg.getDividendo());
					}
					if (reg.getImpuesto() != null) {
						totalPagImpuestos = totalPagImpuestos.add(reg.getImpuesto());
					}
					if (reg.getDividendoNeto() != null) {
						totalPagDividendoNeto = totalPagDividendoNeto.add(reg.getDividendoNeto());
					}
				}
				paginaVO.getValores().put("totalPagPosicion", totalPagPosicion);
				paginaVO.getValores().put("totalPagDividendo", totalPagDividendo);
				paginaVO.getValores().put("totalPagImpuestos", totalPagImpuestos);
				paginaVO.getValores().put("totalPagDividendoNeto", totalPagDividendoNeto);
			}
		}

		return paginaVO;
	}

	public void validaStatement(StatementDivintVO vo) throws BusinessException {
		log.info("Entrando a StatementsDivintServiceImpl.validaStatement con vo: [" + vo + "]");
		StatementDivint statementsDivint = transformStatementVOToBO(vo);
		validatorDivIntService.validaStatementDivint(statementsDivint);
	}

	public Long guardaStatement(StatementDivintVO vo) throws BusinessException {
		log.info("Entrando a StatementsDivintServiceImpl.guardaStatement con vo: [" + vo + "]");

		Long retorno = 0l;
		StatementDivint statementsDivint = transformStatementVOToBO(vo);
		validatorDivIntService.validaStatementDivint(statementsDivint);
		retorno = (Long) this.statementsDivIntDao.save(statementsDivint);

		if (retorno == null || retorno <= 0l) {
			throw new BusinessException("Error al guardar el registro del Statement, se regreso un objeto null");
		}

		return retorno;
	}

	private StatementDivint transformStatementVOToBO(StatementDivintVO vo) {
		StatementDivint retorno = null;
		if (vo != null) {
			retorno = new StatementDivint();
			if (StringUtils.isNotBlank(vo.getIdFolio())) {
				retorno.setInstitucion(institucionDao.findInstitucionByClaveFolio(vo.getIdFolio()));
			}

			Emision emision = null;
			if (StringUtils.isNotBlank(vo.getIsin())
					&& StringUtils.isNotBlank(vo.getTv())
					&& StringUtils.isNotBlank(vo.getEmisora())) {

				EmisionVO emisionVO = new EmisionVO();
				emisionVO.setIsin(vo.getIsin());
				List<Emision> emisionesList = emisionDao.findEmisionGenerico(emisionVO);
				if (emisionesList.size() >= 1) {
					emision = emisionesList.get(0);
				}

				if (emision == null) {
					emisionVO = new EmisionVO(vo.getTv(), vo.getEmisora(), vo.getSerie(), null);
					emisionesList = emisionDao.findEmisionGenerico(emisionVO);
					if (emisionesList.size() >= 1) {
						emision = emisionesList.get(0);
					}
				}
			} else {
				throw new BusinessException("El ISIN, el tipo valor y la emisora de la Emision son datos obligatorios.");
			}
			retorno.setEmision(emision);

			if (StringUtils.isNotBlank(vo.getTipoBeneficiario())) {
				retorno.setTipoBeneficiario(tipoBeneficiarioDao.findTipoBeneficiarioByDesc(vo.getTipoBeneficiario()));
			}

			retorno.setFechaPago(DateUtils.preparaFechaConExtremoEnSegundos(vo.getFechaPago(), true));
			retorno.setFechaRegistro(DateUtils.preparaFechaConExtremoEnSegundos(vo.getFechaRegistro(), true));
			retorno.setAdp(transformaCadena(vo.getAdp()));
			retorno.setNombre(transformaCadena(vo.getNombre()));
			retorno.setDireccion(transformaCadena(vo.getDireccion()));
			retorno.setPais(transformaCadena(vo.getPais()));
			retorno.setRfc(transformaCadena(vo.getRfc()));
			retorno.setTaxPayerNumber(transformaCadena(vo.getTaxPayerNumber()));
			retorno.setFormato(transformaCadena(vo.getFormato()));
			if(BeneficiariosConstantes.FORMATO_W8_BEN.equals(retorno.getFormato()) &&
					StringUtils.isBlank(retorno.getPais())) {
				retorno.setPais("Mexico");
			}
			retorno.setStatusOwner(transformaCadena(vo.getStatusOwner()));
			retorno.setPorcentajeRetencion(vo.getPorcentajeRetencion());
			retorno.setNumeroTitulos(vo.getNumeroTitulos());
			retorno.setProporcion(vo.getProporcion());
			retorno.setDividendo(vo.getDividendo());
			retorno.setImpuesto(vo.getImpuesto());
			retorno.setDividendoNeto(vo.getDividendoNeto());
			retorno.setArchivoOrigen(transformaCadena(vo.getArchivoOrigen()));
			retorno.setArchivoZip(transformaCadena(vo.getArchivoZip()));
			retorno.setCargoInstitucion(Boolean.FALSE);
			// Aplica la fecha de creacion del registro
			retorno.setFechaCreacion(dateUtilService.getCurrentDate());
			
			//Se agrega el custodio
			retorno.setCustodio(StringUtils.trim(vo.getCustodio()));
			
			// Verifica si trae nombre y direccion sino pone el de la Institucion
			verificaInstitucion(retorno);
			
			
			
		}

		return retorno;
	}

	public Map<Long, DomiciliosInstituciones> consultaDomiciliosFiscales() {
		Map<Long, DomiciliosInstituciones> retorno =
				new HashMap<Long, DomiciliosInstituciones>();
		List<DomiciliosInstituciones> domicilios = domiciliosInstitucionesDao.getDomicilios();
		if (domicilios != null && domicilios.size() > 0) {
			for (DomiciliosInstituciones dom : domicilios) {
				retorno.put(dom.getIdInstitucion().longValue(), dom);
			}
		}
		return retorno;
	}

	public PaginaVO consultaArchivosStatement(String nombreArchivo, PaginaVO paginaVO) throws BusinessException {
		log.debug("Entrando a StatementsDivintServiceImpl.consultaArchivosStatement con nombreArchivo: [" + nombreArchivo + "]");
		if (paginaVO == null) {
			throw new BusinessException("Estado de la Paginacion vacio");
		}
		paginaVO = statementsDivIntDao.findArchivoStatements(nombreArchivo, paginaVO);

		return paginaVO;
	}

	public int borraArchivo(String nombreArchivo) throws BusinessException {
		log.debug("Entrando a StatementsDivintServiceImpl.borraArchivo con nombreArchivo: [" + nombreArchivo + "]");
		int retorno = 0;
		if (StringUtils.isBlank(nombreArchivo)) {
			throw new BusinessException("El nombre del archivo no puede ser null o vacio");
		}
		try {
			retorno = statementsDivIntDao.deleteArchivo(nombreArchivo);
		} catch (Exception exception) {
			log.error("Error al borrar el archivo", exception);
			throw new BusinessException("Error al borrar el archivo", exception);
		}
		return retorno;
	}

	/**
	 * Metodo para verificar si se tiene los camspos de nombre y direccion ya que sino
	 * se le ponen los de la institucion
	 * @param statement Statement a verificar
	 */
	private void verificaInstitucion(StatementDivint statement) {
		// Verifica si trae nombre y direccion sino pone el de la Institucion
		Institucion institucion = statement.getInstitucion();
		if (StringUtils.isBlank(statement.getNombre())
				&& StringUtils.isBlank(statement.getDireccion())
				&& institucion != null) {
			log.debug("No tiene nombre y direccion, se toman los datos de la institucion.");
			statement.setCargoInstitucion(Boolean.TRUE);
			statement.setNombre(institucion.getRazonSocial());
			statement.setDireccion(null);
			statement.setPais(null);
			statement.setFormato("W8BEN");
			statement.setStatusOwner("FOREIGN CORPORATION");
			TipoBeneficiario corporation = new TipoBeneficiario();
			corporation.setIdTipoBeneficiario(Constantes.PERSONA_MORAL_NO_EUA);
			statement.setTipoBeneficiario(corporation);
			statement.setRfc(null);
			log.debug("Statement modificado: [" + statement.toString() + "]");
		}
	}

	private String transformaCadena(String cadena) {
		return StringUtils.trim(StringUtils.upperCase(cadena));
	}

	/**
	 * Dao para las consultas de statments
	 * @param statementsDivIntDao the statementsDivIntDao to set
	 */
	public void setStatementsDivIntDao(StatementsDivIntDao statementsDivIntDao) {
		this.statementsDivIntDao = statementsDivIntDao;
	}

	/**
	 * Dao para las consultas de instituciones
	 * @param institucionDao the institucionDao to set
	 */
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	/**
	 * Dao para las consultas de emisiones
	 * @param emisionDao the emisionDao to set
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * Dao para las consultas del tipo beneficiario
	 * @param tipoBeneficiarioDao the tipoBeneficiarioDao to set
	 */
	public void setTipoBeneficiarioDao(TipoBeneficiarioDao tipoBeneficiarioDao) {
		this.tipoBeneficiarioDao = tipoBeneficiarioDao;
	}

	/**
	 * Validaro de Internacional
	 * @param validatorDivIntService the validatorDivIntService to set
	 */
	public void setValidatorDivIntService(ValidatorDivIntService validatorDivIntService) {
		this.validatorDivIntService = validatorDivIntService;
	}

	/**
	 * Servicio de fechas
	 * @param dateUtilService the dateUtilService to set
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public void setDomiciliosInstitucionesDao(DomiciliosInstitucionesDao domiciliosInstitucionesDao) {
		this.domiciliosInstitucionesDao = domiciliosInstitucionesDao;
	}

	public List<String> listaCustodios() throws BusinessException {
		log.debug("Entrando a StatementsDivintServiceImpl.listaCustodios");
		List<String> resultado = statementsDivIntDao.listaCustodios();
		
		return resultado;
	}
}
