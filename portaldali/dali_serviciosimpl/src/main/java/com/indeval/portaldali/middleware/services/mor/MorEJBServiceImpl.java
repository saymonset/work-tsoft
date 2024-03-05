package com.indeval.portaldali.middleware.services.mor;

import java.util.List;

import com.indeval.mor.dto.ConsultaRecuperacionDTO;
import com.indeval.mor.exception.MorException;
import com.indeval.mor.service.MorExposedService;
import com.indeval.mor.vo.DetalleMorVo;
import com.indeval.mor.vo.EmisoraVo;
import com.indeval.mor.vo.EstadoRecuperacionMorVo;
import com.indeval.mor.vo.InstrumentoVo;
import com.indeval.mor.vo.ModuloOrigenVo;
import com.indeval.mor.vo.PaginaVo;
import com.indeval.mor.vo.RecuperacionMorVo;
import com.indeval.mor.vo.TipoMovimientoVo;

@SuppressWarnings("rawtypes")
public class MorEJBServiceImpl implements MorEJBService{
	
	MorExposedService morExposedService = null;
	
	public PaginaVo obtenerConsultaRecuperacion(
			ConsultaRecuperacionDTO consultaRecuperacionDTO)
			throws MorException {
		return morExposedService.obtenerConsultaRecuperacion(consultaRecuperacionDTO);
	}
	
	public PaginaVo<DetalleMorVo> obtenerConsultaRecuperacionPortalDali(
			ConsultaRecuperacionDTO consultaRecuperacionDTO)
			throws MorException {
		return morExposedService.obtenerConsultaRecuperacionPortalDali(consultaRecuperacionDTO);
	}
	
	public List<EmisoraVo> obtenerEmisorasPorPrefijo(String prefijo) {
		return morExposedService.obtenerEmisorasPorPrefijo(prefijo);
	}

	public List<EstadoRecuperacionMorVo> obtenerEstadosRecuperacionMor() {
		return morExposedService.obtenerEstadosRecuperacionMor();
	}

	public List<InstrumentoVo> obtenerInstrumentosPorPrefijo(String prefijo) {
		return morExposedService.obtenerInstrumentosPorPrefijo(prefijo);
	}

	public List<RecuperacionMorVo> obtenerRecuperacionMorPorPrefijos(
			String prefijoTV, String prefijoEmisora, String prefijoSerie) {
		return morExposedService.obtenerRecuperacionMorPorPrefijos(prefijoTV, prefijoEmisora, prefijoSerie);
	}

	public List<ModuloOrigenVo> obtenerModulosOrigen() {
		return morExposedService.obtenerModulosOrigen();
	}

	public List<TipoMovimientoVo> obtenerTiposMovimiento() {
		return morExposedService.obtenerTiposMovimiento();
	}

	/**
	 * @return the morExposedService
	 */
	public MorExposedService getMorExposedService() {
		return morExposedService;
	}

	/**
	 * @param morExposedService the morExposedService to set
	 */
	public void setMorExposedService(MorExposedService morExposedService) {
		this.morExposedService = morExposedService;
	}

}