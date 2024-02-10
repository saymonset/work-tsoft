// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;

import java.util.List;

public interface MovimientoEfectivoInternacionalDao extends BaseDao{

	PaginaVO getMovimientosEfectivoInternacional(CriteriosConsultaMovEfeDivExtVO criteriosConsulta, PaginaVO paginaVO);
	
	Long getFolioControl();
	
	void saveMovimientoRetiroEfectivoInternacional(MovimientoRetiroEfectivoInternacional movimientoEfectivoInternacional);
	
	void saveMovimientoDepositoEfectivoInternacional(MovimientoDepositoEfectivoInternacional movimientoEfectivoInternacional);

	void updateEstatusRetirosEfectivoInternacional(List<Long> idsRetiros, Long estadoMovEfectivoIntAsignar);

	void updateEstatusDepositosEfectivoInternacional(List<Long> idsDepositos, Long estadoMovEfectivoIntAsignar);

	boolean esUsuarioPermitidoTransaccion(String usuario, List<Integer> idsOperacionTransaccion, Long folioControl,
                                          boolean usuarioDiferente);

}
