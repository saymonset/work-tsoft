package com.indeval.portaldali.middleware.services.liquidacionpaquetes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.mop.dto.InstruccionLiquidacionDTO;
import com.indeval.mop.dto.PaqueteDTO;
import com.indeval.mop.external.ws.service.WSMopService;
import com.indeval.portaldali.middleware.dto.MopInstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.MopPaqueteDTO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

public class DetalleLiquidacionPaqServiceImpl implements DetalleLiquidacionPaqService{
	
	private WSMopService clientWSMop;
	private DateUtilsDao dateUtilsDao;
	private static final Logger logger = LoggerFactory.getLogger(DetalleLiquidacionPaqServiceImpl.class);
	
	public MopPaqueteDTO getPaquete(MopPaqueteDTO paqueteDTO, String ticket, Long idInstitucionActual) {
		try {
			
			PaqueteDTO paqueteWS = clientWSMop.getPaqueteFechaLiquidacion(
					paqueteDTO.getReferenciaPaquete(), paqueteDTO.getTotalOperacionesPaquete(),
					paqueteDTO.getTotalTitulosPaquete(), paqueteDTO.getTotalImportePaquete(),
					dateUtilsDao.getDateFechaDB(), paqueteDTO.getFolioControl(), ticket);
			//encabezado
			if(paqueteWS != null){
				paqueteDTO.setMensaje(paqueteWS.getMensajeWebService());
				if(paqueteWS.getMensajeWebService().startsWith("S")){//si es satisfactorio
					paqueteDTO.setReferenciaPaquete(paqueteWS.getReferenciaPaquete());
					paqueteDTO.setTotalOperacionesPaquete(paqueteWS.getTotalOperacionesPaquete());
					paqueteDTO.setTotalTitulosPaquete(paqueteWS.getTotalTitulosPaquete());
					paqueteDTO.setTotalImportePaquete(paqueteWS.getTotalImportePaquete());
					
					if (paqueteWS.getInstruccionLiquidacionDTO() != null && paqueteWS.getInstruccionLiquidacionDTO().size() > 0){
						paqueteDTO.setFolioPaquete(paqueteWS.getReferenciaPaquete());
						List<MopInstruccionLiquidacionDTO> lista = new ArrayList<MopInstruccionLiquidacionDTO>();
						MopInstruccionLiquidacionDTO item = null;
						Long fkMopPaquete = null;
						//busca el campo fkMopInstrucLiq que es igual al grupo que conforman el mismo paquete
						for(InstruccionLiquidacionDTO instruccion : paqueteWS.getInstruccionLiquidacionDTO()){
							if( (paqueteDTO.getFolioControl() != null && instruccion.getFolioInstruccion() != null) 
								&& instruccion.getFolioInstruccion().compareTo(paqueteDTO.getFolioControl()) == 0 )
							{
								fkMopPaquete = instruccion.getFkMopInstrucLiq();
								break;
							}
						}
						for(InstruccionLiquidacionDTO inst : paqueteWS.getInstruccionLiquidacionDTO()){
							item = new MopInstruccionLiquidacionDTO();
							item.setIdInstruccion(inst.getIdMopInstrucLiq());
							item.setFolio(String.valueOf(inst.getFolioInstruccion()));
							item.setFolioUsuario(String.valueOf(inst.getFolioInstruccionLiquidacion()));
							item.setTraspasante(inst.getOperaciones().getOperaciones().get(0).getClaveInstitucionTraspasante());
							item.setReceptor(inst.getOperaciones().getOperaciones().get(0).getClaveInstitucionReceptor());
							item.setEstatus(inst.getClaveStatus());
							item.setFechaLiquidacion(inst.getFechaLiquidacion());
							item.setEmisorReceptor(inst.getParticipanteReceptor());
							item.setEmisorTraspasante(inst.getParticipanteTraspasante());
							item.setNumeroOperacion(inst.getNumeroOperacion());
							
							if(inst.getIdEstadoInstruccion().equals(3L)){//estado cancelado
								item.setCancelada(inst.getOperaciones().getOperaciones().get(0).getIdTraspasante().equals(idInstitucionActual));
							}else{
								item.setCancelada(false);
							}
							//determina si es operacion de valores o efectivo
							if(inst.getOperaciones().getOperaciones().get(0).getPosiciones() != null 
									&& inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones() != null
									&& inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones().size()>0){
								item.setTv(inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones().get(0).getClaveInstitucion());
								item.setEmisora(inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones().get(0).getClavePizarra());
								item.setSerie(inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones().get(0).getSerie());
								item.setCupon(inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones().get(0).getClaveCupon());
								item.setCantidad(String.valueOf(inst.getOperaciones().getOperaciones().get(0).getPosiciones().getPosiciones().get(0).getNumeroTitulos()));
							}else{
								item.setImporte(String.valueOf(inst.getOperaciones().getOperaciones().get(0).getMonto()));
							}
							//solo agrega las operaciones si pertenecen al mismo paquete
							if( fkMopPaquete != null && inst.getFkMopInstrucLiq() != null && 
								fkMopPaquete.compareTo(inst.getFkMopInstrucLiq()) == 0)
							{
								lista.add(item);
							}
							else if( fkMopPaquete == null && inst.getFkMopInstrucLiq() == null )//agrega juntas todas las no referenciadas
							{
								lista.add(item);
							}
						}//end inner for
						//se ordena la lista de instrucciones en base al numero de operacion
						Collections.sort(lista);
						paqueteDTO.setNumTraspasos(String.valueOf(lista.size()));
						paqueteDTO.setInstruccionLiquidaciones(lista);
					}
				}//end if sucess
			}//end paquete-innotnull
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return paqueteDTO;
	}

	public String cancelInstruccionLiquidacionPaquete(
			MopInstruccionLiquidacionDTO instruccionLiquidacionDTO,
			MopPaqueteDTO paqueteDTO, String ticket) {
		String ret = "";
		try {
			if(instruccionLiquidacionDTO.getCancelada()){
				ret = clientWSMop.cancelInstruccionLiquidacionPaquete(paqueteDTO.getReferenciaPaquete(),paqueteDTO.getTotalOperacionesPaquete(),paqueteDTO.getTotalTitulosPaquete(),paqueteDTO.getTotalImportePaquete(),instruccionLiquidacionDTO.getIdInstruccion(),ticket);
			}else{
				ret = "E003";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return ret;
	}

	public Boolean cancelPaquete(MopPaqueteDTO paqueteDTO, String ticket) {
		Boolean ret = false;
		try {
			//cancelar por paquete
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return ret;
	}

	public WSMopService getClientWSMop() {
		return clientWSMop;
	}

	public void setClientWSMop(WSMopService clientWSMop) {
		this.clientWSMop = clientWSMop;
	}

	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

}
