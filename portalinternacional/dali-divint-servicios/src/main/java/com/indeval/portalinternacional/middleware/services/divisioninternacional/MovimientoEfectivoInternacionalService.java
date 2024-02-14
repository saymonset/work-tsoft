// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaTransitoria;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;

import java.util.List;

public interface MovimientoEfectivoInternacionalService {

	void saveMovimientoEfectivoInternacional(MovimientoEfectivoInternacionalVO efectivoInternacionalVO);

	void deleteMovimientoEfectivoInternacional(MovimientoEfectivoInternacionalVO efectivoInternacionalVO);

	void updateMovimientoEfectivoInternacional(MovimientoEfectivoInternacionalVO efectivoInternacionalVO);

	PaginaVO getMovimientosEfectivoInternacional(CriteriosConsultaMovEfeDivExtVO criteriosConsulta, PaginaVO paginaVO);

	MovimientoRetiroEfectivoInternacional getMovimientoRetiroEfectivoInternacionalByPk(Long idMovimientoRetiroEfectivoInternacional);
	
	MovimientoDepositoEfectivoInternacional getMovimientoDepositoEfectivoInternacionalByPk(Long idMovimientoDepositoEfectivoInternacional);

	void updateMovimientoRetiroEfectivoInternacional(MovimientoRetiroEfectivoInternacional movimientoEfectivoInternacional);

	void updateEstatusMovimientosEfectivoInternacional(List<String> idsMovimientosEfectivo,
                                                       Long estadoMovEfectivoIntAsignar);

	void updateMovimientoDepositoEfectivoInternacional(
            MovimientoDepositoEfectivoInternacional movimientoDepositoEfectivoInternacional);
	
	Long getFolioControl();

	boolean esUsuarioPermitidoAutorizar(String usuario, Long folioControl);

	boolean esUsuarioPermitidoLiberar(String usuario, Long folioControl);

	boolean esUsuarioPermitidoCancelarAutorizado(String usuario, Long folioControl);

    boolean esUsuarioPermitidoCancelar(String usuario, Long folioControl);

	CuentaTransitoria validaCuentaTransitoria(MovimientoEfectivoInternacionalVO mov);
}
