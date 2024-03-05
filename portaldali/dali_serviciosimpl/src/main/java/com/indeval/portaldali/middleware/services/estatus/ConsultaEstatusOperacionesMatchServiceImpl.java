/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaEstatusOperacionesMatchServiceImpl.java
 * 04/03/2008
 */
package com.indeval.portaldali.middleware.services.estatus;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesExportacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.middleware.services.common.PropiedadesDaliService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.EstadoInstruccionConstants;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.util.BitacoraOperacionesService;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.TradingInVO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.dao.common.PropiedadesDaliDAO;
import com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO;
import com.indeval.portaldali.persistence.model.operacionesMatch.ConsultaOperacionValor;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author Emigdio
 * 
 */
public class ConsultaEstatusOperacionesMatchServiceImpl implements ConsultaEstatusOperacionesMatchService {
	/**
	 * DAO para la consulta a las operaciones
	 */
	private EstatusOperacionesMatchDAO estatusOperacionesMatchDAO = null;
	
	/**
	 * DAO para la consulta a las posiciones
	 */
	private PosicionNombradaDaliDao posicionDaliDao = null;

	/**
	 * DAO para la consulta de institución
	 */
	InstitucionDaliDAO institucionDaliDAO = null;
	/**
	 * Servicio para la verificación de la confirmación de un registro de match
	 * (espejeo)
	 */
	BitacoraOperacionesService bitacoraOperacionesService = null;
	
	private PropiedadesDaliService propiedadesDaliService = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService#consultarOperacionesValor(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO, Map<Object, Object>)
	 */
	public List<OperacionValorMatchDTO> consultarOperacionesValor(CriterioMatchOperacionesDTO criterio, EstadoPaginacionDTO paginacion,
			Map<Object, Object> resultadosExtra) {
		Collection<OperacionValorMatchDTO> operaciones = new ArrayList<OperacionValorMatchDTO>();
		List<OperacionValorMatchDTO> match = new ArrayList<OperacionValorMatchDTO>();
		List<OperacionValorMatchDTO> parcialidades = new ArrayList<OperacionValorMatchDTO>();

		if (criterio.getEstadoInstruccion() != null
				&& ((criterio.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion() != EstadoInstruccionConstants.SIN_MATCH) || (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS))) {
			operaciones = estatusOperacionesMatchDAO.consultarOperacionesValor(criterio, null);
		}

		if (criterio.getEstadoInstruccion() != null
				&& criterio.getError() != null
				&& StringUtils.isBlank(criterio.getError().getClaveError())
				&& ((criterio.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && (criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion() == EstadoInstruccionConstants.SIN_MATCH || criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.CANCELADA)) || (criterio
						.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS))) {
			match = estatusOperacionesMatchDAO.consultarInstruccionesMatchSinOperacion(criterio, null);
		}

		if (criterio.getReferenciaPaquete() != null && StringUtils.isEmpty(criterio.getReferenciaPaquete().trim()) 
				&& criterio.getEstadoInstruccion() != null && (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS
				|| criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA
				|| StringUtils.isNotBlank(criterio.getFolioControl()) || StringUtils.isNotBlank(criterio.getCantidad()) || StringUtils.isNotBlank(criterio.getMonto()))) {
			parcialidades = estatusOperacionesMatchDAO.buscarParcialidades(criterio, null);
		}

		match.addAll(operaciones);
		match.addAll(parcialidades);
		Collections.sort(match, new FolioControlComparator());

		if (resultadosExtra != null) {
			calcularTotales(match, resultadosExtra, criterio);
		}

		if (paginacion != null) {
			paginacion.setTotalResultados(match.size());
			if (paginacion.getTotalResultados() > 0) {
				paginacion.setTotalPaginas((int) Math.ceil((double) paginacion.getTotalResultados() / (double) paginacion.getRegistrosPorPagina()));
				if (paginacion.getNumeroPagina() < 1) {
					paginacion.setNumeroPagina(1);
				}
			} else {
				paginacion.setNumeroPagina(0);
				paginacion.setTotalPaginas(0);
			}
			int pagina = paginacion.getNumeroPagina();
			int registrosPorPagina = paginacion.getRegistrosPorPagina();

			if (pagina > 0 && registrosPorPagina > 0) {
				int primerRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina();
				int ultimoRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina() + paginacion.getRegistrosPorPagina();
				if (ultimoRes > (match.size() - 1)) {
					ultimoRes = match.size();
				}

				match = match.subList(primerRes, ultimoRes);
			}

		}

		// Verifica para las operaciones pendientes de match si se pueden
		// confirmar
		verificarEspejeoMatch(match, criterio);

		// obtener información del mensaje XML para llenar campos adicionales
		obtenerInformacionAdicionalDeMensaje(match);

		return match;
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService#getOperacionesValorConVistas(
     *                                                         CriterioMatchOperacionesExportacionDTO,
     *                                                         EstadoPaginacionDTO, 
     *                                                         Map<Object, Object>)
     */
    public List<OperacionValorMatchDTO> getOperacionesValorConVistas(CriterioMatchOperacionesExportacionDTO criterio, 
                                                                     EstadoPaginacionDTO paginacion,
                                                                     Map<Object, Object> resultadosExtra) {
        Collection<ConsultaOperacionesMatch> operaciones = new ArrayList<ConsultaOperacionesMatch>();
        List<ConsultaOperacionesMatch> match = new ArrayList<ConsultaOperacionesMatch>();
        List<ConsultaOperacionesMatch> parcialidades = new ArrayList<ConsultaOperacionesMatch>();
        List<OperacionValorMatchDTO> newMatch = new ArrayList<OperacionValorMatchDTO>();
        String instOcultarFechaHoraCierreOper = this.propiedadesDaliService.obtenerInstitucionesOcultarFechaHoraCierreOper();
        

        try {
            if ((criterio.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && 
                  criterio.getIdEstadoInstruccion() != EstadoInstruccionConstants.SIN_MATCH) || 
                  criterio.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS) {
                operaciones = estatusOperacionesMatchDAO.consultarOperacionesValorExportacion(criterio, instOcultarFechaHoraCierreOper);
            }

            if (StringUtils.isBlank(criterio.getClaveError()) && 
                ((criterio.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && 
                  (criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.SIN_MATCH || 
                   criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.CANCELADA)) || 
                criterio.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS)) {
                match = estatusOperacionesMatchDAO.consultarInstruccionesMatchSinOperacionExportacion(criterio, instOcultarFechaHoraCierreOper);
            }

            if (criterio.getReferenciaPaquete() != null && StringUtils.isEmpty(criterio.getReferenciaPaquete().trim()) && 
                (criterio.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS || 
                 criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA || 
                 StringUtils.isNotBlank(criterio.getFolioControl()) || 
                 StringUtils.isNotBlank(criterio.getCantidad()) || 
                 StringUtils.isNotBlank(criterio.getMonto()))) {
                parcialidades = estatusOperacionesMatchDAO.buscarParcialidadesExportacion(criterio, instOcultarFechaHoraCierreOper);
            }

            match.addAll(operaciones);
            match.addAll(parcialidades);
            Collections.sort(match, new FolioControlComparatorExportacion());

            if (resultadosExtra != null) {
                this.calcularTotalesExportacion(match, resultadosExtra, criterio);
            }

            if (paginacion != null) {
                paginacion.setTotalResultados(match.size());
                if (paginacion.getTotalResultados() > 0) {
                    paginacion.setTotalPaginas((int) Math.ceil((double) paginacion.getTotalResultados() / (double) paginacion.getRegistrosPorPagina()));
                    if (paginacion.getNumeroPagina() < 1) {
                        paginacion.setNumeroPagina(1);
                    }
                } 
                else {
                    paginacion.setNumeroPagina(0);
                    paginacion.setTotalPaginas(0);
                }
                int pagina = paginacion.getNumeroPagina();
                int registrosPorPagina = paginacion.getRegistrosPorPagina();

                if (pagina > 0 && registrosPorPagina > 0) {
                    int primerRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina();
                    int ultimoRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina() + paginacion.getRegistrosPorPagina();
                    if (ultimoRes > (match.size() - 1)) {
                        ultimoRes = match.size();
                    }

                    match = match.subList(primerRes, ultimoRes);
                }
            }

            // Transformacion de los objetos de la lista a mostrar, de ConsultaOperacionesMatch a OperacionValorMatchDTO.
            newMatch = this.transformaObjetos(match);

            // Verifica para las operaciones pendientes de match si se pueden confirmar.
            verificarEspejeoMatchConVistas(newMatch, criterio);

            // obtener informacion del mensaje XML para llenar campos adicionales.
            obtenerInformacionAdicionalDeMensaje(newMatch);

            return newMatch;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return newMatch;
    }

    /**
     * Realiza la transformacion de los objetos encontrados de la lista a mostrar, de objetos ConsultaOperacionesMatch 
     * se transforma a objetos OperacionValorMatchDTO.
     * @param match La lista a transformar.
     * @return La lista transformada. 
     */
    private List<OperacionValorMatchDTO> transformaObjetos(List<ConsultaOperacionesMatch> match) {
        List<OperacionValorMatchDTO> newMatch = new ArrayList<OperacionValorMatchDTO>();
        OperacionValorMatchDTO newOper;
        for (ConsultaOperacionesMatch oper : match) {
            newOper = new OperacionValorMatchDTO();
            newOper.setTieneParcialidades(oper.isTieneParcialidades());
            newOper.setIdInstruccionOperacionVal(oper.getIdInstruccionOperacion());
            newOper.setIdBitacoraMatch(new BigInteger(oper.getIdBitacoraMatch() != null ? oper.getIdBitacoraMatch().toString() : null));
            InstitucionDTO instit = new InstitucionDTO();
            instit.setClaveTipoInstitucion(oper.getIdFolioInstitucionTraspasante().substring(0,2));
            instit.setFolioInstitucion(oper.getIdFolioInstitucionTraspasante().substring(2));
            instit.setNombreCorto(oper.getNombreCortoInstitTraspasante());
            newOper.setInstitucionTraspasante(instit);
            instit = new InstitucionDTO();
            instit.setClaveTipoInstitucion(oper.getIdFolioInstitucionReceptora().substring(0,2));
            instit.setFolioInstitucion(oper.getIdFolioInstitucionReceptora().substring(2));
            instit.setNombreCorto(oper.getNombreCortoInstitReceptora());
            newOper.setInstitucionReceptora(instit);
            instit = null;
            newOper.setFolioControl(oper.getFolioControl() != null ? oper.getFolioControl().toString() : null);
            newOper.setFolioUsuario(oper.getFolioUsuario());
            
            if(oper instanceof ConsultaOperacionValor) {
            	newOper.setFolioOrigen(oper.getFolioOrigen());
            }
            newOper.setPorPaquete(oper.isPorPaquete());
            newOper.setReferenciaPaquete(oper.getReferenciaPaquete());
            newOper.setTotalOperacionesPaquete(oper.getTotalOperacionesPaquete());
            newOper.setNumeroOperacionPaquete(oper.getNumeroOperacionPaquete());
            newOper.setTotalTitulosPaquete(oper.getTotalTitulosPaquete());
            newOper.setTotalImportePaquete(oper.getTotalImportePaquete());
            
            TipoInstruccionDTO tipInstr = new TipoInstruccionDTO();
            tipInstr.setNombreCorto(oper.getNombreCortoTipoInstruccion());
            tipInstr.setDescripcion(oper.getInstruccion());
            newOper.setTipoInstruccion(tipInstr);
            tipInstr = null;
            
            newOper.setConMiscelaneaFiscal(oper.isConMiscelaneaFiscal());
            newOper.setOrigen(oper.getOrigen());
            EmisionDTO emision = new EmisionDTO();
            TipoValorDTO tv = new TipoValorDTO();
            tv.setClaveTipoValor(oper.getTv());
            MercadoDTO mercado = new MercadoDTO();
            mercado.setId(oper.getIdMercado() != null ? oper.getIdMercado().longValue() : 0);
            tv.setMercado(mercado);
            emision.setTipoValor(tv);
            mercado = null;
            EmisoraDTO emisora = new EmisoraDTO();
            emisora.setDescripcion(oper.getEmisora());
            emision.setEmisora(emisora);
            SerieDTO serie = new SerieDTO();
            serie.setSerie(oper.getSerie());
            emision.setSerie(serie);
            emision.setIsin(oper.getIsin());
            newOper.setEmision(emision);
            tv = null;
            emisora = null;
            serie = null;
            emision = null;
            CuponDTO cupon = new CuponDTO();
            cupon.setClaveCupon(oper.getCupon());
            newOper.setCupon(cupon);
            newOper.setBoveda(oper.getBovedaValores());
            EstadoInstruccionDTO edoInstr = new EstadoInstruccionDTO();
            edoInstr.setIdEstadoInstruccion(oper.getIdEstadoInstruccionCat() != null ? 
                                            oper.getIdEstadoInstruccionCat().intValue() : 
                                            0);
            edoInstr.setClaveEstadoInstruccion(oper.getClaveEstadoInstruccionCat());
            edoInstr.setDescripcion(oper.getDescEstadoInstruccionCat());
            newOper.setEstadoInstruccion(edoInstr);
            edoInstr = null;
            newOper.setPuedeConfirmar(oper.isPuedeConfirmar());
            newOper.setPuedeCancelar(oper.isPuedeCancelar());
            ErrorDaliDTO error = new ErrorDaliDTO();
            error.setIdErrorDali(oper.getIdError());
            error.setClaveError(oper.getClaveError());
            error.setDescripcion(oper.getDescError());
            newOper.setErrorDali(error);
            CuentaDTO cuenta = new CuentaDTO();
            cuenta.setNombreCuenta(oper.getNombreCuentaTraspasante());
            cuenta.setCuenta(oper.getCuentaTraspasante());
            newOper.setCuentaNombradaTraspasante(cuenta);
            cuenta = new CuentaDTO();
            cuenta.setNombreCuenta(oper.getNombreCuentaReceptora());
            cuenta.setCuenta(oper.getCuentaReceptora());
            newOper.setCuentaNombradaReceptora(cuenta);
            cuenta = null;
            TipoMensajeDTO tm = new TipoMensajeDTO();
            tm.setClaveTipoMensaje(oper.getClaveTipoMensajeCat());
            tm.setDescripcion(oper.getDescTipoMensajeCat());
            newOper.setTipoMensaje(tm);
            tm = null;
            newOper.setFechaConcertacion(oper.getFechaConcertacion());
            newOper.setFechaLiquidacion(oper.getFechaLiquidacion());
            newOper.setFechaHoraCierreOperTra(oper.getFechaHoraCierreOperTra());
            newOper.setFechaHoraCierreOperRec(oper.getFechaHoraCierreOperRec());
            newOper.setFechaHoraEncolamientoTra(oper.getFechaHoraEncolamientoTra());
            newOper.setFechaHoraEncolamientoRec(oper.getFechaHoraEncolamientoRec());
            newOper.setCantidadTitulos(oper.getCantidadTitulos() != null ? oper.getCantidadTitulos().longValue() : 0);
            newOper.setImporte(oper.getImporte() != null ? oper.getImporte().doubleValue() : 0.0);
            newOper.setTieneBovedaEfectivo(false);
            newOper.setBovedaEfectivo(oper.getBovedaEfectivo());
            newOper.setPrecioTitulo(oper.getPrecioTitulo() != null ? oper.getPrecioTitulo().doubleValue() : 0.0);
            newOper.setFechaReporto(null);
            if (oper.getFechaReporto() != null) {
                java.text.SimpleDateFormat sdf = new SimpleDateFormat(DaliConstants.FORMATO_FECHA_CORTO);
                java.util.Date fr = new java.util.Date();
                try{
                    fr = sdf.parse(oper.getFechaReporto());
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                /*Calendar cal = new GregorianCalendar(new java.util.Locale("es", "MX"));
                cal.setTime(fr);
                newOper.setFechaReporto(cal.getTime());*/
                newOper.setFechaReporto(fr);
            }
            newOper.setTasaNegociada(oper.getTasaNegociada() != null ? oper.getTasaNegociada().doubleValue() : 0.0);
            newOper.setPlazoReporto(oper.getPlazoReporto());

            newOper.setInteresesGenerados(oper.getInteresesGenerados() != null ? 
                                          oper.getInteresesGenerados().doubleValue() : 
                                          0.0);
            newOper.setDivisa(oper.getClaveDivisa());
            newOper.setMensajeXmlMatch(oper.getMensajeXml());

            newMatch.add(newOper);
            newOper = null;
        }

        return newMatch;
    }

	/**
	 * Obtiene información de campos adicionales de cada mensaje XML en el
	 * registro de match.
	 * 
	 * @param listaMatch
	 *            Lista de resultados a completar
	 */
	private void obtenerInformacionAdicionalDeMensaje(List<OperacionValorMatchDTO> listaMatch) {
		TradingInVO trading = null;
		Map<String, InstitucionDTO> instituciones = new HashMap<String, InstitucionDTO>();
		// Se cargan las instituciones para ahorrar consultas al momento de
		// buscar participantes
		if (listaMatch.size() > 0) {
			List<InstitucionDTO> listaInstituciones = institucionDaliDAO.buscarInstituciones();
			for (InstitucionDTO institucionDTO : listaInstituciones) {
				instituciones.put(institucionDTO.getClaveTipoInstitucion() + institucionDTO.getFolioInstitucion(), institucionDTO);
			}
		}

		for (OperacionValorMatchDTO match : listaMatch) {
			if (match.getMensajeXmlMatch() != null) {

				trading = (TradingInVO) bitacoraOperacionesService.getDetalleOperacion(match.getMensajeXmlMatch());
				if (trading != null && trading.getParticipanteVO() != null) {

					match.setInstitucionRemitente(instituciones.get(trading.getParticipanteVO().getIdInstitucion()
							+ trading.getParticipanteVO().getFolioInstitucion()));

					match.setPlazo(NumberUtils.toLong(trading.getDiasPlazo()));

				}
				if ( match.getIdBitacoraMatch() != null  &&  trading != null && null != trading.getDivisa() ) {
					// Obtener la divisa
					match.setDivisa( trading.getDivisa());
				}

			}

		}

	}

	/**
	 * Por cada registro encontrado en MATCH se verifica si cumple con las
	 * reglas necesarias para realizar el espejeo, de ser as , se prende una
	 * bandera para indicar que es posible el espejeo.
	 * 
	 * @param listaMatch
	 *            lista de operaciones de Match localizadas
	 * @param criterio
	 *            Criterio con el que fue realizada la consulta
	 */
	private void verificarEspejeoMatch(List<OperacionValorMatchDTO> listaMatch, CriterioMatchOperacionesDTO criterio) {
		TradingInVO trading = null;
		BitacoraMatchVO bitacoraVO = new BitacoraMatchVO();
		AgenteVO agenteVO = new AgenteVO();
		agenteVO.setId(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
		agenteVO.setFolio(criterio.getInstitucionParticipante().getFolioInstitucion());
		for (OperacionValorMatchDTO match : listaMatch) {

			if (match.getIdBitacoraMatch() != null) {
				trading = (TradingInVO) bitacoraOperacionesService.getDetalleOperacion(match.getMensajeXmlMatch());
				if (trading != null) {
					bitacoraVO.setPuedeCancelar(Boolean.FALSE);
					bitacoraVO.setPuedeConfirmar(Boolean.FALSE);
					if (DaliConstants.ABREV_ESTATUS_CANCELADA.equals(match.getEstadoInstruccion().getClaveEstadoInstruccion())) {
						bitacoraVO.setEstaCancelada(Boolean.TRUE);
					} else {
						bitacoraVO.setEstaCancelada(Boolean.FALSE);
					}
					if (DaliConstants.ABREV_ESTATUS_SIN_MATCH.equals(match.getEstadoInstruccion().getClaveEstadoInstruccion())) {
						bitacoraVO.setEstaConfirmada(Boolean.FALSE);
					} else {
						bitacoraVO.setEstaConfirmada(Boolean.TRUE);
					}
					if( (DaliConstants.OPERACION_TIPO_CORE.equals(trading.getTipoOperacion()) || DaliConstants.OPERACION_TIPO_COVE.equals(trading.getTipoOperacion())
							|| DaliConstants.OPERACION_TIPO_REPO_NOMINAL.equals(trading.getTipoOperacion())) && DaliConstants.MENSAJE_VENTA.equals(trading.getTipoMensaje())){
						match.setTieneBovedaEfectivo(Boolean.TRUE);
					}
					else if( (DaliConstants.OPERACION_TIPO_CORE.equals(trading.getTipoOperacion()) || DaliConstants.OPERACION_TIPO_COVE.equals(trading.getTipoOperacion())
							|| DaliConstants.OPERACION_TIPO_REPO_NOMINAL.equals(trading.getTipoOperacion())) && DaliConstants.MENSAJE_COMPRA.equals(trading.getTipoMensaje())){
						match.setTieneBovedaEfectivo(Boolean.FALSE);
					}
					bitacoraVO = bitacoraOperacionesService.verificaPermisosConfirmacionCancelacion(trading, agenteVO, bitacoraVO);
					match.setPuedeConfirmar(bitacoraVO.getPuedeConfirmar().booleanValue());
					match.setPuedeCancelar(bitacoraVO.getPuedeCancelar().booleanValue());
					//Evita la cancelacion y confirmacion si son de paquetes
					if( DaliConstants.ABREV_ESTATUS_SIN_MATCH.equals(match.getEstadoInstruccion().getClaveEstadoInstruccion()) && 
						StringUtils.isNotBlank(match.getReferenciaPaquete()) )
					{
						match.setPuedeConfirmar(Boolean.FALSE);
						match.setPuedeCancelar(Boolean.FALSE);
					}
				}
			}//end if-bitacoranotnull

		}//end for

	}//end verificarEspejeoMatch

    /**
     * Por cada registro encontrado en MATCH se verifica si cumple con las reglas necesarias para realizar el espejeo, 
     * de ser asi , se prende una bandera para indicar que es posible el espejeo.
     * 
     * @param listaMatch lista de operaciones de Match localizadas.
     * @param criterio Criterio con el que fue realizada la consulta.
     */
    private void verificarEspejeoMatchConVistas(List<OperacionValorMatchDTO> listaMatch, 
                                                CriterioMatchOperacionesExportacionDTO criterio) {
        TradingInVO trading = null;
        BitacoraMatchVO bitacoraVO = new BitacoraMatchVO();
        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId(criterio.getClaveTipoInstitucionParticipante());
        agenteVO.setFolio(criterio.getFolioInstitucionParticipante());
        for (OperacionValorMatchDTO match : listaMatch) {

            if (match.getIdBitacoraMatch() != null) {
                trading = (TradingInVO) bitacoraOperacionesService.getDetalleOperacion(match.getMensajeXmlMatch());
                if (trading != null) {
                    bitacoraVO.setPuedeCancelar(Boolean.FALSE);
                    bitacoraVO.setPuedeConfirmar(Boolean.FALSE);
                    if (DaliConstants.ABREV_ESTATUS_CANCELADA.equals(match.getEstadoInstruccion().getClaveEstadoInstruccion())) {
                        bitacoraVO.setEstaCancelada(Boolean.TRUE);
                    } else {
                        bitacoraVO.setEstaCancelada(Boolean.FALSE);
                    }
                    if (DaliConstants.ABREV_ESTATUS_SIN_MATCH.equals(match.getEstadoInstruccion().getClaveEstadoInstruccion())) {
                        bitacoraVO.setEstaConfirmada(Boolean.FALSE);
                    } else {
                        bitacoraVO.setEstaConfirmada(Boolean.TRUE);
                    }
                    if( (DaliConstants.OPERACION_TIPO_CORE.equals(trading.getTipoOperacion()) || DaliConstants.OPERACION_TIPO_COVE.equals(trading.getTipoOperacion())
                            || DaliConstants.OPERACION_TIPO_REPO_NOMINAL.equals(trading.getTipoOperacion())) && DaliConstants.MENSAJE_VENTA.equals(trading.getTipoMensaje())){
                        match.setTieneBovedaEfectivo(Boolean.TRUE);
                    }
                    else if( (DaliConstants.OPERACION_TIPO_CORE.equals(trading.getTipoOperacion()) || DaliConstants.OPERACION_TIPO_COVE.equals(trading.getTipoOperacion())
                            || DaliConstants.OPERACION_TIPO_REPO_NOMINAL.equals(trading.getTipoOperacion())) && DaliConstants.MENSAJE_COMPRA.equals(trading.getTipoMensaje())){
                        match.setTieneBovedaEfectivo(Boolean.FALSE);
                    }
                    bitacoraVO = bitacoraOperacionesService.verificaPermisosConfirmacionCancelacion(trading, agenteVO, bitacoraVO);
                    match.setPuedeConfirmar(bitacoraVO.getPuedeConfirmar().booleanValue());
                    match.setPuedeCancelar(bitacoraVO.getPuedeCancelar().booleanValue());
                    //Evita la cancelacion y confirmacion si son de paquetes
                    if( DaliConstants.ABREV_ESTATUS_SIN_MATCH.equals(match.getEstadoInstruccion().getClaveEstadoInstruccion()) && 
                        StringUtils.isNotBlank(match.getReferenciaPaquete()) )
                    {
                        match.setPuedeConfirmar(Boolean.FALSE);
                        match.setPuedeCancelar(Boolean.FALSE);
                    }
                }
            }

        }
    }

	/**
	 * Calcula los totales de: Titulo traspasante titulos receptor neto titulos
	 * monto traspasante monto receptor neto monto
	 * 
	 * @param operaciones
	 *            lista de resultados
	 * @param resultadosExtra
	 *            Mapa donde se dejan los resultados extras
	 * @param criterio
	 *            Criterio utilizado para realizar la consulta
	 */
	private void calcularTotales(List<OperacionValorMatchDTO> operaciones, Map<Object, Object> resultadosExtra, CriterioMatchOperacionesDTO criterio) {
		long titulosTraspasante = 0;
		long titulosReceptor = 0;
		double montoTraspasante = 0;
		double montoReceptor = 0;
		for (OperacionValorMatchDTO dto : operaciones) {
			if (dto.getInstitucionTraspasante() != null && criterio.getInstitucionParticipante().getId() == dto.getInstitucionTraspasante().getId()) {
				titulosTraspasante += dto.getCantidadTitulos();
				montoTraspasante += dto.getImporte();
			}
			if (dto.getInstitucionReceptora() != null && criterio.getInstitucionParticipante().getId() == dto.getInstitucionReceptora().getId()) {
				titulosReceptor += dto.getCantidadTitulos();
				montoReceptor += dto.getImporte();
			}

		}
		resultadosExtra.put(TOTAL_TITULOS_RECEPTOR, new Long(titulosReceptor));
		resultadosExtra.put(TOTAL_TITULOS_TRASPASANTE, new Long(titulosTraspasante));
		resultadosExtra.put(TOTAL_MONTO_RECEPTOR, new Double(montoReceptor));
		resultadosExtra.put(TOTAL_MONTO_TRASPASANTE, new Double(montoTraspasante));

	}

    /**
     * Calcula los totales de: Titulo traspasante titulos receptor neto titulos monto traspasante monto receptor neto monto
     * @param operaciones lista de resultados
     * @param resultadosExtra Mapa donde se dejan los resultados extras
     * @param criterio Criterio utilizado para realizar la consulta
     */
    private void calcularTotalesExportacion(List<ConsultaOperacionesMatch> operaciones, Map<Object, Object> resultadosExtra, 
                                            CriterioMatchOperacionesExportacionDTO criterio) {
        long titulosTraspasante = 0;
        long titulosReceptor = 0;
        double montoTraspasante = 0;
        double montoReceptor = 0;
        for (ConsultaOperacionesMatch oper : operaciones) {
            if (oper.getIdInstitucionTraspasante() != null && 
                criterio.getIdInstitucionParticipante() == oper.getIdInstitucionTraspasante().longValue()) {
                titulosTraspasante += (oper.getCantidadTitulos() != null) ? oper.getCantidadTitulos() : 0;
                montoTraspasante += (oper.getImporte() != null) ? oper.getImporte() : 0;
            }
            if (oper.getIdInstitucionReceptora() != null && 
                criterio.getIdInstitucionParticipante() == oper.getIdInstitucionReceptora().longValue()) {
                titulosReceptor += (oper.getCantidadTitulos() != null) ? oper.getCantidadTitulos() : 0;
                montoReceptor += (oper.getImporte() != null) ? oper.getImporte() : 0;
            }

        }

        resultadosExtra.put(TOTAL_TITULOS_RECEPTOR, new Long(titulosReceptor));
        resultadosExtra.put(TOTAL_TITULOS_TRASPASANTE, new Long(titulosTraspasante));
        resultadosExtra.put(TOTAL_MONTO_RECEPTOR, new Double(montoReceptor));
        resultadosExtra.put(TOTAL_MONTO_TRASPASANTE, new Double(montoTraspasante));
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService#obtenerProyeccionConsultaOperacionesValor(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO)
	 */
	public long obtenerProyeccionConsultaOperacionesValor(CriterioMatchOperacionesDTO criterio, Boolean debeDejarLog) {
		long operaciones = 0;
		long match = 0;

		if (criterio.getEstadoInstruccion() != null
				&& ((criterio.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion() != EstadoInstruccionConstants.SIN_MATCH) || (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS))) {
			operaciones = estatusOperacionesMatchDAO.obtenerProyeccionConsultaOperacionesValor(criterio);
		}

		if (criterio.getEstadoInstruccion() != null
				&& criterio.getError() != null
				&& StringUtils.isBlank(criterio.getError().getClaveError())
				&& ((criterio.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && (criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion() == EstadoInstruccionConstants.SIN_MATCH || criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.CANCELADA)) || (criterio
						.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS))) {

			match = estatusOperacionesMatchDAO.obtenerProyeccionConsultaInstruccionesMatchSinOperacion(criterio);

		}

		return match + operaciones;

	}

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService#getOperacionesValorExportacion(CriterioMatchOperacionesExportacionDTO,
     *                                                                                                                               Map<Object, Object>)
     */
    public List<ConsultaOperacionesMatch> getOperacionesValorExportacion(CriterioMatchOperacionesExportacionDTO criterio, 
                                                                         Map<Object, Object> resultadosExtra) {
        Collection<ConsultaOperacionesMatch> operaciones = new ArrayList<ConsultaOperacionesMatch>();
        List<ConsultaOperacionesMatch> match = new ArrayList<ConsultaOperacionesMatch>();
        List<ConsultaOperacionesMatch> parcialidades = new ArrayList<ConsultaOperacionesMatch>();
        String instOcultarFechaHoraCierreOper = this.propiedadesDaliService.obtenerInstitucionesOcultarFechaHoraCierreOper();

        try {
            if ((criterio.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && 
                  criterio.getIdEstadoInstruccion() != EstadoInstruccionConstants.SIN_MATCH) || 
                  criterio.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS) {
                operaciones = estatusOperacionesMatchDAO.consultarOperacionesValorExportacion(criterio, instOcultarFechaHoraCierreOper);
            }

            if (StringUtils.isBlank(criterio.getClaveError()) && 
                ((criterio.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && 
                  (criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.SIN_MATCH || 
                   criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.CANCELADA)) || 
                criterio.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS)) {
                match = estatusOperacionesMatchDAO.consultarInstruccionesMatchSinOperacionExportacion(criterio, instOcultarFechaHoraCierreOper);
            }

            if (criterio.getReferenciaPaquete() != null && StringUtils.isEmpty(criterio.getReferenciaPaquete().trim()) && 
                (criterio.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS || 
                 criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA || 
                 StringUtils.isNotBlank(criterio.getFolioControl()) || 
                 StringUtils.isNotBlank(criterio.getCantidad()) || 
                 StringUtils.isNotBlank(criterio.getMonto()))) {
                parcialidades = estatusOperacionesMatchDAO.buscarParcialidadesExportacion(criterio, instOcultarFechaHoraCierreOper);
            }

            match.addAll(operaciones);
            match.addAll(parcialidades);
            Collections.sort(match, new FolioControlComparatorExportacion());

            //Se eliminan los folios origenes de los registros de instrucciones de match sin operaciones. 
            for (ConsultaOperacionesMatch oper : match) {
                if (!(oper instanceof ConsultaOperacionValor)) {
                    oper.setFolioOrigen(null);
                }
            }

            if (resultadosExtra != null) {
                this.calcularTotalesExportacion(match, resultadosExtra, criterio);
            }

            return match;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return match;
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService#consultarInstruccionOperacionValorPorId(long)
	 */
	public List<OperacionValorMatchDTO> consultarInstruccionOperacionValorPorId(long idInstruccion) {
		return estatusOperacionesMatchDAO.consultarInstruccionOperacionValorPorId(idInstruccion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService#consultarInstruccionMatchoPorId(long)
	 */
	public List<OperacionValorMatchDTO> consultarInstruccionMatchoPorId(long idBitacoraMatch) {
		return estatusOperacionesMatchDAO.consultarInstruccionesMatchSinOperacionPorId(idBitacoraMatch);
	}

	/**
	 * Obtiene el campo estatusOperacionesMatchDAO
	 * 
	 * @return estatusOperacionesMatchDAO
	 */
	public EstatusOperacionesMatchDAO getEstatusOperacionesMatchDAO() {
		return estatusOperacionesMatchDAO;
	}

	/**
	 * Asigna el campo estatusOperacionesMatchDAO
	 * 
	 * @param estatusOperacionesMatchDAO
	 *            el valor de estatusOperacionesMatchDAO a asignar
	 */
	public void setEstatusOperacionesMatchDAO(EstatusOperacionesMatchDAO estatusOperacionesMatchDAO) {
		this.estatusOperacionesMatchDAO = estatusOperacionesMatchDAO;
	}

	/**
	 * @param posicionDaliDao the posicionDaliDao to set
	 */
	public void setPosicionDaliDao(PosicionNombradaDaliDao posicionDaliDao) {
		this.posicionDaliDao = posicionDaliDao;
	}

	/**
	 * Obtiene el campo bitacoraOperacionesService
	 * 
	 * @return bitacoraOperacionesService
	 */
	public BitacoraOperacionesService getBitacoraOperacionesService() {
		return bitacoraOperacionesService;
	}

	/**
	 * Asigna el campo bitacoraOperacionesService
	 * 
	 * @param bitacoraOperacionesService
	 *            el valor de bitacoraOperacionesService a asignar
	 */
	public void setBitacoraOperacionesService(BitacoraOperacionesService bitacoraOperacionesService) {
		this.bitacoraOperacionesService = bitacoraOperacionesService;
	}

	public PropiedadesDaliService getPropiedadesDaliService() {
		return propiedadesDaliService;
	}

	public void setPropiedadesDaliService(PropiedadesDaliService propiedadesDaliService) {
		this.propiedadesDaliService = propiedadesDaliService;
	}

	/**
	 * Obtiene el campo institucionDaliDAO
	 * 
	 * @return institucionDaliDAO
	 */
	public InstitucionDaliDAO getInstitucionDAO() {
		return institucionDaliDAO;
	}

	/**
	 * Asigna el campo institucionDaliDAO
	 * 
	 * @param institucionDaliDAO
	 *            el valor de institucionDaliDAO a asignar
	 */
	public void setInstitucionDAO(InstitucionDaliDAO institucionDaliDAO) {
		this.institucionDaliDAO = institucionDaliDAO;
	}

	public BigInteger getSaldoActual(AgenteVO agenteFirmado, EmisionVO emision,
			BigInteger bovedaId) {
		
		BigInteger retorno =BigInteger.valueOf(0);
		
		if( agenteFirmado != null && emision != null && bovedaId != null ) {
			TPosicionNombradaParamsPersistence params = new TPosicionNombradaParamsPersistence();
			
			if( StringUtils.isNotBlank(agenteFirmado.getId()) ) {
				params.setIdInstitucion(agenteFirmado.getId());
			}
			
			if( StringUtils.isNotBlank(agenteFirmado.getFolio()) ) {
				params.setFolioInstitucion(agenteFirmado.getFolio());
			}
			
			if( StringUtils.isNotBlank(agenteFirmado.getCuenta()) ) {
				String[] cuentas = {agenteFirmado.getCuenta()};
				params.setCuentas(cuentas);
			}
			
			if( StringUtils.isNotBlank(emision.getIdTipoValor()) ) {
				String[] tvs = {emision.getIdTipoValor()};
				params.setTiposDeValor(tvs);
			}
			
			if( StringUtils.isNotBlank(emision.getEmisora()) ) {
				params.setEmisora(emision.getEmisora());
			}
			
			if( StringUtils.isNotBlank(emision.getSerie()) ) {
				params.setSerie(emision.getSerie());
			}
			
			if( StringUtils.isNotBlank(emision.getCupon()) ) {
				params.setCupon(emision.getCupon());
			}
			
			if( StringUtils.isNotBlank(emision.getIsin()) ) {
				params.setIsin(emision.getIsin());
			}
			
			if( bovedaId.compareTo(BigInteger.ZERO) > 0 ) {
				params.setIdBoveda(bovedaId);
			}
			
			retorno = posicionDaliDao.getSaldoActual(params);
		}
		
		return retorno;
	}

	public List<PosicionDTO> getListaPosiciones(AgenteVO agente, EmisionVO emision) {
		
		if( agente != null && emision != null ) {
			List<PosicionDTO> listaPosiciones = posicionDaliDao.getCuentasParaFondeoMatch(agente, emision);
			return listaPosiciones;
		}
		
		return new ArrayList<PosicionDTO>();
	}
	
	public List<OperacionValorMatchDTO> consultarOperacionesMiscelaneaFiscal(CriterioMatchOperacionesDTO criterio, 
			EstadoPaginacionDTO paginacion, Boolean debeDejarLog)
	{
		Collection<OperacionValorMatchDTO> operaciones = new ArrayList<OperacionValorMatchDTO>();
		List<OperacionValorMatchDTO> listOperaciones = new ArrayList<OperacionValorMatchDTO>();
		
		 operaciones = estatusOperacionesMatchDAO.consultarOperacionesValor(criterio, paginacion);
		 
		 listOperaciones.addAll(operaciones);
		
		return listOperaciones;
	}

}

class FolioControlComparator implements Comparator<OperacionValorMatchDTO> {

	public int compare(OperacionValorMatchDTO o1, OperacionValorMatchDTO o2) {
		if (o1.getFolioControl() != null) {
			return new Long(NumberUtils.toLong(o1.getFolioControl())).compareTo(NumberUtils.toLong(o2.getFolioControl()));
		} 
		else {
			return 0;
		}

	}
}

class FolioControlComparatorExportacion implements Comparator<ConsultaOperacionesMatch> {

	public int compare(ConsultaOperacionesMatch argLeft, ConsultaOperacionesMatch argRight) {
        Long l = Long.MIN_VALUE;
        Long r = Long.MIN_VALUE;
        if (argLeft.getFolioControl() != null) {
            l = argLeft.getFolioControl();
            }
        if (argRight.getFolioControl() != null) {
            r = argRight.getFolioControl();
        } 
        return l.compareTo(r);
    }
}
