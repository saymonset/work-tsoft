package com.indeval.portaldali.middleware.services.reportos;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.InstruccionOperacionDTO;
import com.indeval.portaldali.middleware.dto.VencimientoAnticipadoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioVencimientoReportosDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;

public interface VencimientoReportosService {
	
	long countReportos(CriterioVencimientoReportosDTO criterio);

	List<ConsultaOperacionesMatch> findReportos(CriterioVencimientoReportosDTO criterio, EstadoPaginacionDTO paginacion);

	List<String> validarSolicitudVencimiento(InstruccionOperacionDTO instruccion, int nuevoPlazo);
	void guardarSolicitudVencimiento(InstruccionOperacionDTO instruccion, long idInstitucion, int nuevoPlazo, String firmaSolicitud, String usuario, String ip);
	
	List<String> validarAutorizacionVencimiento(VencimientoAnticipadoDTO dto);
	void autorizarVencimientoAnticipado(long idVencimiento, long idInstitucion, String firmaRespuesta, String usuario, String ip);
	void rechazarVencimientoAnticipado(long idVencimiento, long idInstitucion, String firmaRespuesta, String usuario, String ip);
	
	
	public long findVencimientoPendiente(long idInstruccion);
	
	public InstruccionOperacionDTO findReporto(long idInstruccion);
	public VencimientoAnticipadoDTO findSolicitud(long idInstruccion);
	
	
	Date getCurrentDate();
	Date agregaDiasHabiles(Date fecha, int offset);
	void validaDiaHabil(Date fechaVencimiento);
	boolean esInhabil(Date fechaVencimiento); 
}
