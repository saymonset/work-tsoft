package com.indeval.portaldali.middleware.services.reportos;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indeval.portaldali.middleware.dto.InstruccionOperacionDTO;
import com.indeval.portaldali.middleware.dto.VencimientoAnticipadoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioVencimientoReportosDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.vencimientoreportos.InstruccionOperacionValDAO;
import com.indeval.portaldali.persistence.dao.vencimientoreportos.ReportosDAO;
import com.indeval.portaldali.persistence.dao.vencimientoreportos.VencimientoAnticipadoDAO;
import com.indeval.portaldali.persistence.model.InstruccionOperacionVal;
import com.indeval.portaldali.persistence.model.VencimientoAnticipado;
import com.indeval.portaldali.persistence.util.DateUtil;

public class VencimientoReportosServiceImpl implements VencimientoReportosService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private DiaInhabilDaliDao diaInhabilDao;
	private ReportosDAO reportosDao;
	private VencimientoAnticipadoDAO vencimientoAnticipadoDao;
	private InstruccionOperacionValDAO instruccionOperacionValDao;
	private FechasUtilService fechasUtilService;
	
	public Date getCurrentDate(){
		return fechasUtilService.getCurrentDate();
	}
	
	public Date agregaDiasHabiles(Date fecha, int offset) {
		return diaInhabilDao.agregaDiasHabiles(DateUtil.preparaFechaConExtremoEnSegundos(fecha, true), offset);
	}
	
	public boolean esFechaInhabil(Date fecha) {
		return diaInhabilDao.esInhabil(fecha);
	}
	
	@Override
	public boolean esInhabil(Date fechaVencimiento) {
		LOGGER.debug("esInhabil");
		
		return diaInhabilDao.esInhabil(fechaVencimiento);
	}
	
	@Override
	public void validaDiaHabil(Date fechaVencimiento) {
		LOGGER.debug("validaDiaHabil");
		
		if(diaInhabilDao.esInhabil(fechaVencimiento)) {
			LOGGER.debug("esInhabil");
			
			throw new BusinessException("La fecha proporcionada es Inhabil");
		}
	}

	@Override
	public long countReportos(CriterioVencimientoReportosDTO criterio) {
		LOGGER.debug("countReportos");
		long reportos = reportosDao.countReportos(criterio); 
		
		return reportos;
	}

	@Override
	public List<ConsultaOperacionesMatch> findReportos(CriterioVencimientoReportosDTO criterio,
			EstadoPaginacionDTO paginacion) {
		LOGGER.debug("findReportos");
		List<ConsultaOperacionesMatch> result = reportosDao.findReportos(criterio);
		
		Date fechaHoy = fechasUtilService.getCurrentDate();
		Date fecha2doDiaHabil =  diaInhabilDao.agregaDiasHabiles(fechaHoy, 2);
		
		for(ConsultaOperacionesMatch oper: result) {
			if(oper.getFechaVencimiento()== null ) continue;
			oper.setPosibleVencerAnticipadamente(fecha2doDiaHabil.compareTo(oper.getFechaVencimiento())<=0);
		}
		
		
		if (paginacion != null) {
            paginacion.setTotalResultados(result.size());
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
                if (ultimoRes > (result.size() - 1)) {
                    ultimoRes = result.size();
                }

                result = result.subList(primerRes, ultimoRes);
            }
        }
		return result;
	}
	
	
	@Override
	public InstruccionOperacionDTO findReporto(long idInstruccion) {
		LOGGER.trace("findReporto");
		
		InstruccionOperacionVal oper = instruccionOperacionValDao.findById(idInstruccion);
		
		if(oper == null) {
			return null;
		}
		
		InstruccionOperacionDTO dto = new InstruccionOperacionDTO();
		//
		dto.setIdInstruccionOperacionVal(oper.getIdInstruccionOperacionVal().longValue());
		// plazo
		if(oper.getPlazoReporto() != null)
			dto.setPlazoReporto(oper.getPlazoReporto().intValue());
		// titulos
		if(oper.getCantidadTitulos() != null)
			dto.setCantidadTitulos(oper.getCantidadTitulos().intValue());
		// concertacion
		if(oper.getFechaConcertacion() != null)
			dto.setFechaConcertacion(oper.getFechaConcertacion());
		// liquidacion
		if(oper.getFechaLiquidacion() != null)
			dto.setFechaLiquidacion(oper.getFechaLiquidacion());
		// importe
		if(oper.getImporte() != null)
			dto.setImporte(oper.getImporte().doubleValue());
		
		return dto;
	}
	
	
	@Override
	public VencimientoAnticipadoDTO findSolicitud(long idVencimiento) {
		if(idVencimiento <= 0)throw new IllegalArgumentException("idVencimiento is required.");
		
		VencimientoAnticipado vto = vencimientoAnticipadoDao.findById(idVencimiento);
		
		VencimientoAnticipadoDTO dto = null;
		
		if(vto == null) {
			LOGGER.warn("No se encontro la solicitud de vencimiento anticipado con id " + idVencimiento);
			
			return dto;
		}
		
		
		try {
			dto = new VencimientoAnticipadoDTO();
			BeanUtils.copyProperties(dto, vto);
		}catch(Exception e) {
			LOGGER.error("Error al transformar entity to Object");
			LOGGER.warn(vto.toString());
		}
		
		return dto;
	}
	
	
	@Override
	public List<String> validarSolicitudVencimiento(InstruccionOperacionDTO instruccion, int nuevoPlazo) {
		LOGGER.trace("validarSolicitudVencimiento");
		if(instruccion == null)throw new IllegalArgumentException("instruccion no puede ser null");
		
		List<String> errors = new ArrayList<>();
		
		long idInstruccion = instruccion.getIdInstruccionOperacionVal();
		
		// buscar todas las solicitudes pendientes, si existe al menos una enviar error
		List<VencimientoAnticipado> vtosPendientes = vencimientoAnticipadoDao.findByInstruccionAndStatus(idInstruccion, VencimientoAnticipado.ESTATUS_PENDIENTE);
		
		if(!vtosPendientes.isEmpty()) {
			errors.add("Esta operaci\u00f3n tiene solicitudes de vencimiento pendientes, debe autorizarlas o rechazarlas.");
			
			return errors;
		}
		
		Date fechaHoy = fechasUtilService.getCurrentDate();
		Date fechaManana = agregaDiasHabiles(fechaHoy, 1);
		
		int plazo = instruccion.getPlazoReporto();
		Date fechaLiquidacion = DateUtil.preparaFechaConExtremoEnSegundos(instruccion.getFechaLiquidacion(), true);
		
		Date fechaVencimientoSolicitud = DateUtil.addDays(fechaLiquidacion, nuevoPlazo);
		
		// EL PLAZO SOLICITADO DEBE SER MENOR AL PLAZO ORIGINAL
		if(nuevoPlazo >= plazo ) {
			errors.add("El plazo solicitado debe ser menor al plazo actual.");
		}
		// EL PLAZO SOLICITADO DEBE SER MENOR AL PLAZO ORIGINAL, Y EL PLAZO SOLICITADO DEBE SER MAYOR A LOS DIAS TRANSCURRIDOS
		
		LOGGER.debug("fechaManana: " + fechaManana);
		LOGGER.debug("fechaSolicitud: " + fechaVencimientoSolicitud);
		// FECHA DE VENCIMIENTO DEBE SER AL MENOS AL SIGUIENTE DIA HABIL
		if(fechaVencimientoSolicitud.compareTo(fechaManana) < 0) {
			errors.add("La fecha de vencimiento debe ser mayor al d\u00eda de hoy.");
		}
		// DEBE SER UN DIA HABIL
		if(diaInhabilDao.esInhabil(fechaVencimientoSolicitud)) {
			errors.add("La fecha de vencimiento es un d\u00eda inhabil.");
		}
		
		return errors;
	}
	
	@Override
	@Transactional
	public void guardarSolicitudVencimiento(InstruccionOperacionDTO instruccion, long idInstitucion, int nuevoPlazo, String firmaSolicitud, String usuario, String ip){
		LOGGER.debug("guardarSolicitudVencimiento");
		if(instruccion == null)throw new IllegalArgumentException("instruccion no puede ser null");
		
		long idInstruccion = instruccion.getIdInstruccionOperacionVal();
		int plazo = instruccion.getPlazoReporto();
		Date fechaLiquidacion = instruccion.getFechaLiquidacion();
		Date fechaVencimiento = DateUtil.addDays(fechaLiquidacion, plazo);
		Date fechaVencimientoSolicitud = DateUtil.addDays(fechaLiquidacion, nuevoPlazo);
		// Fecha de vigencia es un dia antes del nuevo plazo, de esta forma pueden ser limpiadas por el proceso depura_indeval
		Date fechaVigenciaSolicitud = agregaDiasHabiles(fechaVencimientoSolicitud, -1);
		
		VencimientoAnticipado vto = new VencimientoAnticipado();
		vto.setIdInstruccionOperacion(idInstruccion);
		vto.setPlazoVigente(plazo);
		vto.setFechaVencimientoVigente(fechaVencimiento);
		
		vto.setEstatusSolicitud(VencimientoAnticipado.ESTATUS_PENDIENTE);
		vto.setPlazoSolicitud(nuevoPlazo);
		vto.setFechaVencimientoSolicitud(fechaVencimientoSolicitud);
		vto.setFechaVigenciaSolicitud(fechaVigenciaSolicitud);
		
		vto.setIdInstitucionSolicitud(idInstitucion);
		vto.setUsuarioSolicitud(usuario);
		vto.setIpSolicitud(ip);
		vto.setFechaSolicitud(new Date());
		vto.setFirmaSolicitud(firmaSolicitud);
		
		vencimientoAnticipadoDao.save(vto);
	}	
	
	
	@Override
	public long findVencimientoPendiente(long idInstruccion) {
		// buscar la solicitud de vencimiento
		List<VencimientoAnticipado> vtosPendientes = vencimientoAnticipadoDao.findByInstruccionAndStatus(idInstruccion, VencimientoAnticipado.ESTATUS_PENDIENTE);
		if(vtosPendientes.size() != 1 || vtosPendientes.get(0) == null) {
			LOGGER.warn("No se encontro la solicitud de vencimiento de la instruccion seleccionada.");
			
			return -1;
		}
		
		return vtosPendientes.get(0).getId();
	}

	@Override
	public List<String> validarAutorizacionVencimiento(VencimientoAnticipadoDTO vto) {
		LOGGER.debug("validarAutorizacionVencimiento");
		
		List<String> errors = new ArrayList<>();
		
		Date fechaHoy = fechasUtilService.getCurrentDate();
		Date fechaManana = agregaDiasHabiles(fechaHoy, 1);
		
		//VencimientoAnticipado vto = vencimientoAnticipadoDao.findById(idVencimiento);
		int plazo = vto.getPlazoVigente();
		
		int plazoSolicitud = vto.getPlazoSolicitud();
		Date fechaVencimientoSolicitud = vto.getFechaVencimientoSolicitud();
		
		// EL PLAZO SOLICITADO DEBE SER MENOR AL PLAZO ORIGINAL
		if(plazoSolicitud >= plazo ) {
			errors.add("El plazo solicitado debe ser menor al plazo actual.");
		}
		
		// FECHA DE VENCIMIENTO DEBE SER AL MENOS AL SIGUIENTE DIA HABIL
		if(fechaVencimientoSolicitud.compareTo(fechaManana) < 0) {
			errors.add("La fecha de vencimiento solicitada ya no es valida.");
		}
		
		return errors;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void autorizarVencimientoAnticipado(long idVencimiento, long idInstitucion, String firmaRespuesta, String usuario, String ip) {
		LOGGER.debug("autorizarVencimientoAnticipado");
		
		VencimientoAnticipado vto = vencimientoAnticipadoDao.findById(idVencimiento);
		int plazoSolicitud = vto.getPlazoSolicitud();
		long idInstruccion = vto.getIdInstruccionOperacion();
				
		vto.setEstatusSolicitud(VencimientoAnticipado.ESTATUS_AUTORIZADA);
		vto.setIdInstitucionRespuesta(idInstitucion);
		vto.setFechaRespuesta(new Date());
		vto.setUsuarioRespuesta(usuario);
		vto.setIpRespuesta(ip);
		vto.setFirmaRespuesta(firmaRespuesta);
		
		// ACTUALIZA INFORMACION
		instruccionOperacionValDao.actualizaPlazo(idInstruccion, plazoSolicitud);
		vencimientoAnticipadoDao.update(vto);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void rechazarVencimientoAnticipado(long idVencimiento, long idInstitucion, String firmaRespuesta, String usuario, String ip) {
		LOGGER.debug("cancelarVencimientoAnticipado");
		
		VencimientoAnticipado vto = vencimientoAnticipadoDao.findById(idVencimiento);
		
		vto.setEstatusSolicitud(VencimientoAnticipado.ESTATUS_CANCELADA);
		vto.setIdInstitucionRespuesta(idInstitucion);
		vto.setFechaRespuesta(new Date());
		vto.setUsuarioRespuesta(usuario);
		vto.setIpRespuesta(ip);
		vto.setFirmaRespuesta(firmaRespuesta);
		
		vencimientoAnticipadoDao.update(vto);
		
	}
	
	public void setReportosDao(ReportosDAO reportosDao) {
		this.reportosDao = reportosDao;
	}

	public void setVencimientoAnticipadoDao(VencimientoAnticipadoDAO vencimientoAnticipadoDao) {
		this.vencimientoAnticipadoDao = vencimientoAnticipadoDao;
	}

	public void setInstruccionOperacionValDao(InstruccionOperacionValDAO instruccionOperacionValDao) {
		this.instruccionOperacionValDao = instruccionOperacionValDao;
	}

	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDao) {
		this.diaInhabilDao = diaInhabilDao;
	}

	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}
	
}