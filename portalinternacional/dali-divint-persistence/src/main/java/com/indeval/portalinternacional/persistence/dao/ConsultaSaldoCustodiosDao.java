// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoPorCuenta;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoCustodio;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;

import java.util.List;

public interface ConsultaSaldoCustodiosDao extends BaseDao{

	PaginaVO getMovimientosEfectivoInternacional(CriteriosConsultaMovEfeDivExtVO criteriosConsulta, PaginaVO paginaVO);
	
	Long getFolioControl();

	public List<SaldoCustodio> consultaGeneral(Divisa div, Boveda boveda);

	public List<SaldoPorCuenta> consultaPorCuenta(Divisa div, Boveda boveda);

}
