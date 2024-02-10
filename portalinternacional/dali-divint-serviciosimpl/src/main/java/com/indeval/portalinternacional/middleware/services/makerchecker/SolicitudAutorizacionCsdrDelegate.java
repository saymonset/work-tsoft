package com.indeval.portalinternacional.middleware.services.makerchecker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.SettlementDisciplineRegimeService;
import com.indeval.portalinternacional.middleware.services.util.JsonUtil;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;
import com.indeval.portalinternacional.middleware.servicios.modelo.makercheker.SolicitudAutorizacion;
import com.indeval.portalinternacional.persistence.dao.makercheker.SolicitudAutorizacionDao;
import com.indeval.workflow.xml.AuthorizationResponse;

public class SolicitudAutorizacionCsdrDelegate {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private SolicitudAutorizacionDao solicitudAutorizacionDao;

	private SettlementDisciplineRegimeService settlementDisciplineRegimeService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void receive(AuthorizationResponse response) {
		LOGGER.info("SolicitudAutorizacionCsdrDelegate :: receive solicitud");
		long idProceso = Long.parseLong(response.getProcessId());
		String claveFlujoOperativo = response.getWorkflow();
		String estatus = response.getStatus();

		List<SolicitudAutorizacion> solicitudes = solicitudAutorizacionDao.findByIdProcesoAutorizacion(idProceso);

		if (solicitudes.isEmpty()) {
			LOGGER.warn(String.format("La lista de solicitudes con idProceso=[%s] se encuentra vacia.", idProceso));
			return;
		}

		for (SolicitudAutorizacion solicitud : solicitudes) {
			// Validar estatus localmente
			if (!Constantes.ESTATUS_SOLICITUD_PENDIENTE.equals(solicitud.getEstatus())) {
				LOGGER.warn(String.format("La solicitud [%d] tiene un estatus invalido [%s].",
						solicitud.getIdSolicitud(), solicitud.getEstatus()));
				continue;
			}

			LOGGER.debug("SolicitudAutorizacionCuentaEfectivoInterDelegate :: claveFlujoOperativo: " + claveFlujoOperativo);
			switch (claveFlujoOperativo) {
			case Constantes.CLAVE_WORKFLOW_ALTA_CUSTODIO_CSDR:
				saveCustodioSettlementDisciplineRegime(solicitud, estatus);
				break;
			case Constantes.CLAVE_WORKFLOW_EDICION_CUSTODIO_CSDR:
				actualizaCustodioSettlementDisciplineRegime(solicitud, estatus);
				break;
			case Constantes.CLAVE_WORKFLOW_ELIMINAR_CUSTODIO_CSDR:
				eliminaCustodioSettlementDisciplineRegime(solicitud, estatus);
				break;
			default:
				LOGGER.warn(String.format("claveFlujoOperativo [%s] no implementado.", claveFlujoOperativo));
			}

			solicitudAutorizacionDao.actualizaEstatus(solicitud.getIdSolicitud(), estatus);
		}

	}

	private void saveCustodioSettlementDisciplineRegime(SolicitudAutorizacion solicitud, String estatus) {
		LOGGER.debug("SolicitudAutorizacionCsdrDelegate :: saveCustodioSettlementDisciplineRegime");
		String jsonCuentaEfectivo = solicitud.getValorNuevo();
		if(jsonCuentaEfectivo!= null){
			SettlementDisciplineRegime settlementDisciplineRegime = JsonUtil.readObject(jsonCuentaEfectivo, SettlementDisciplineRegime.class);
			// Valido que no exista Custodio
			if(settlementDisciplineRegime!= null){
				try {
					if(settlementDisciplineRegimeService.findConfiguration(settlementDisciplineRegime)){
						LOGGER.info("La configuración ya se encuentra dada de alta.");
					} else {
						if (Constantes.ESTATUS_SOLICITUD_AUTORIZADO.equals(estatus)) {
							settlementDisciplineRegimeService.saveConfiguracion(settlementDisciplineRegime);
						}
					}

				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		} else {
			LOGGER.error("El valor: solicitud.getValorNuevo() es NULL y es necesario para llevar a cabo la actualizacion.");
		}

	}

	private void actualizaCustodioSettlementDisciplineRegime(SolicitudAutorizacion solicitud, String estatus) {
		LOGGER.debug("SolicitudAutorizacionCsdrDelegate :: actualizaCustodioSettlementDisciplineRegime");
		String jsonSettlementDRNuevo = solicitud.getValorNuevo();
		String jsonSettlementDRAnterior = solicitud.getValorAnterior();
		
		if(jsonSettlementDRNuevo!= null && jsonSettlementDRAnterior != null){
			SettlementDisciplineRegime settlementDisciplineRegimeAnterior = JsonUtil.readObject(jsonSettlementDRAnterior, SettlementDisciplineRegime.class);
			SettlementDisciplineRegime settlementDisciplineRegimeNuevo = JsonUtil.readObject(jsonSettlementDRNuevo, SettlementDisciplineRegime.class);
			SettlementDisciplineRegimeVO csdrUpdate = settlementDisciplineRegimeService.getSettlementDisciplineRegimeByID(settlementDisciplineRegimeAnterior.getIdConfigCsdr());
			if(csdrUpdate!= null){
				csdrUpdate.actualizaConfiguracion(settlementDisciplineRegimeNuevo);
				
				try {
					if (Constantes.ESTATUS_SOLICITUD_AUTORIZADO.equals(estatus)) {
						settlementDisciplineRegimeService.modificarSettlementDisciplineRegime(csdrUpdate);
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		} else {
			LOGGER.error("El valor: solicitud.getValorNuevo() o  solicitud.getValorAnterior() es NULL y son necesarios para hacer la actualizacion del registro.");
		}

	}
	
	private void eliminaCustodioSettlementDisciplineRegime(SolicitudAutorizacion solicitud, String estatus) {
		LOGGER.debug("SolicitudAutorizacionCsdrDelegate :: eliminaCustodioSettlementDisciplineRegime");
		String jsonCuentaEfectivo = solicitud.getValorAnterior();
		if(jsonCuentaEfectivo!= null){
			SettlementDisciplineRegime settlementDisciplineRegime = JsonUtil.readObject(jsonCuentaEfectivo, SettlementDisciplineRegime.class);
			try {
				if (Constantes.ESTATUS_SOLICITUD_AUTORIZADO.equals(estatus)) {
					settlementDisciplineRegimeService.eliminaSettlementDisciplineRegime(settlementDisciplineRegime);
				}

			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}			
		} else {
			LOGGER.error("El valor: solicitud.getValorAnterior() es NULL y es necesario para proceder con la eliminacion del registro.");
		}
	}

	public SolicitudAutorizacionDao getSolicitudAutorizacionDao() {
		return solicitudAutorizacionDao;
	}

	public void setSolicitudAutorizacionDao(SolicitudAutorizacionDao solicitudAutorizacionDao) {
		this.solicitudAutorizacionDao = solicitudAutorizacionDao;
	}

	/**
	 * @return the settlementDisciplineRegimeService
	 */
	public SettlementDisciplineRegimeService getSettlementDisciplineRegimeService() {
		return settlementDisciplineRegimeService;
	}

	/**
	 * @param settlementDisciplineRegimeService the settlementDisciplineRegimeService to set
	 */
	public void setSettlementDisciplineRegimeService(SettlementDisciplineRegimeService settlementDisciplineRegimeService) {
		this.settlementDisciplineRegimeService = settlementDisciplineRegimeService;
	}

}
