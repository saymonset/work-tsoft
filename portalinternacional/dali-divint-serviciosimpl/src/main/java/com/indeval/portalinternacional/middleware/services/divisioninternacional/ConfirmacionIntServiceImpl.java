/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.util.ConfirmacionEfectivoComparator;
import com.indeval.portalinternacional.middleware.services.util.Utils;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConfirmacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;
import com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao;
import com.indeval.portalinternacional.persistence.dao.ConfirmacionesIntDao;

/**
 * @author César Hernández
 *
 */
public class ConfirmacionIntServiceImpl implements ConfirmacionIntService {

	private ConciliacionEfectivoDao conciliacionEfectivoDao;
	
	private ConfirmacionesIntDao confirmacionIntDao;
	
	private static final Logger log = LoggerFactory.getLogger(ConfirmacionIntServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#consultaBicCodes()
	 */
	public List<String> consultaBicCodes() {
		return conciliacionEfectivoDao.consultaBicCodes();
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#consultaConfirmaciones(com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaConfirmaciones(ConfirmacionIntDTO confirmacionIntDTO, PaginaVO paginaVO) {
		return confirmacionIntDao.consultaConfirmaciones(confirmacionIntDTO, paginaVO);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#consultaDivisas(java.lang.String[])
	 */
	public List<Divisa> consultaDivisas(Set<String> bicCodes) {
		return conciliacionEfectivoDao.consultaDivisas(bicCodes);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#consultaCuentas(java.lang.String[], java.lang.Long[])
	 */
	public List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas) {
		return conciliacionEfectivoDao.consultaCuentas(bicCodes, divisas);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#consultaBitacoraConfirmacion(java.lang.Long)
	 */
	public BitacoraConfirmacion consultaBitacoraConfirmacion(Long idConfirmacion) {
		return confirmacionIntDao.consultaBitacoraConfirmacion(idConfirmacion);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#agregaActualizaComentariosDali(java.lang.Long, java.lang.String)
	 */
	public void agregaActualizaComentariosDali(Long idConfirmacion, String comentariosDali) {
		confirmacionIntDao.agregaActualizaComentariosDali(idConfirmacion, comentariosDali);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#agregaActualizaIdFolio(java.lang.Long, java.lang.String)
	 */
	public void agregaActualizaIdFolio(Long idConfirmacion, String idFolio) {
		confirmacionIntDao.agregaActualizaIdFolio(idConfirmacion, idFolio);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#obtieneSumaTotalCredito(com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO)
	 */
	public Double obtieneSumaTotalCreditos(ConfirmacionIntDTO dto) {
		return confirmacionIntDao.obtieneSumaTotalCreditos(dto);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService#obtieneSumaTotalDebitos(com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO)
	 */
	public Double obtieneSumaTotalDebitos(ConfirmacionIntDTO dto) {
		return confirmacionIntDao.obtieneSumaTotalDebitos(dto);
	}
	
	public void ordernarConfirmaciones(List<ConfirmacionEfectivo> listaConfirmacion) {
		List<ConfirmacionEfectivo> listaConfirmacionOrdenada = new ArrayList<ConfirmacionEfectivo>(listaConfirmacion);
		
		Collections.sort(listaConfirmacionOrdenada, new ConfirmacionEfectivoComparator());
		List<ConfirmacionEfectivo> listaConfirmacionOrdenadaB = new ArrayList<ConfirmacionEfectivo>(listaConfirmacionOrdenada);
		
		double cantidad1 = 0;
		double cantidad2 = 0;
		Double cantidadB = null;
		ConfirmacionEfectivo confirmacionB = null;
		
		for(ConfirmacionEfectivo confirmacion : listaConfirmacionOrdenada){
			if(confirmacion.getCantidad() != null && confirmacion.getHash() == null){
				cantidad1 = confirmacion.getCantidad() - .01;
				cantidad2 = confirmacion.getCantidad() + .01;
			
				for (Iterator<ConfirmacionEfectivo> i = listaConfirmacionOrdenadaB.iterator(); i.hasNext();) {
					confirmacionB = (ConfirmacionEfectivo)i.next();
					cantidadB = confirmacionB.getCantidad();
					
					if(cantidadB != null && Utils.betweenInclusive(cantidadB, cantidad1, cantidad2) && confirmacion != confirmacionB){
						if(!confirmacion.getTipoMensaje().equals(confirmacionB.getTipoMensaje())){
							log.debug("pareja:" + confirmacion.getCantidad() + "==" + cantidadB);
							confirmacion.setHash("1");
							confirmacionB.setHash("1");
							
							if(confirmacion.getOps()!= null && confirmacion.getOps().equals("CRP")) {
								log.debug("corp:" + confirmacion.getComentariosDali());
								confirmacion.setHash("2");
								confirmacionB.setHash("2");
							}
							listaConfirmacionOrdenadaB.remove(confirmacion);
							listaConfirmacionOrdenadaB.remove(confirmacionB);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * @return the conciliacionEfectivoDao
	 */
	public ConciliacionEfectivoDao getConciliacionEfectivoDao() {
		return conciliacionEfectivoDao;
	}
	
	/**
	 * @param conciliacionEfectivoDao the conciliacionEfectivoDao to set
	 */
	public void setConciliacionEfectivoDao(
			ConciliacionEfectivoDao conciliacionEfectivoDao) {
		this.conciliacionEfectivoDao = conciliacionEfectivoDao;
	}

	/**
	 * @return the confirmacionIntDao
	 */
	public ConfirmacionesIntDao getConfirmacionIntDao() {
		return confirmacionIntDao;
	}

	/**
	 * @param confirmacionIntDao the confirmacionIntDao to set
	 */
	public void setConfirmacionIntDao(ConfirmacionesIntDao confirmacionIntDao) {
		this.confirmacionIntDao = confirmacionIntDao;
	}
}
