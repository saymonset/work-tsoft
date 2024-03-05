/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 2, 2008
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionServiceImpl;

/**
 * Prueba unitaria para el servicio {@link ConsultaPosicionServiceImpl}
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ConsultaPosicionTest extends BaseTestCase {

	/**
	 * Prueba unitaria para el método de ConsultaDePosicionesYEmisionesVigentes
	 */
	public void testConsultaPosicionesYEmisionesVigenter() {

		ConsultaPosicionService consultaPosicionService = (ConsultaPosicionService) applicationContext.getBean("consultaPosicionService");
		assertNotNull("No se inyectó el servicio a la prueba unitaria", consultaPosicionService);

		PosicionDTO criterio = new PosicionDTO();

		criterio.getCuenta().getInstitucion().setId(3);
		criterio.getCuenta().getInstitucion().setClaveTipoInstitucion("01");
		criterio.getCuenta().getInstitucion().setFolioInstitucion("003");
		
		criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
		criterio.getCuenta().getTipoTenencia().getTipoCuenta().setId("N");
		
		criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().setId("P");
		
		criterio.getEmision().getTipoValor().setClaveTipoValor("2U");
		criterio.getEmision().getTipoValor().setId(1);
		criterio.getEmision().getTipoValor().getMercado().setId(2);
		criterio.getEmision().getTipoValor().getMercado().setClaveMercado("PB");

		int registros = consultaPosicionService.obtenerProyeccionDeConsultaPosicionesYEmisionesVigentes(criterio, new Long[] {
				DaliConstants.ID_MERCADO_PAPEL_BANCARIO, DaliConstants.ID_MERCADO_PAPEL_GUBER });

		EstadoPaginacionExtended paginacion = new EstadoPaginacionExtended();

		paginacion.setRegistrosPorPagina(10);
		paginacion.setTotalResultados(registros);
		
		paginacion.setTotalPaginas(Double.valueOf(Math.ceil((double)registros / (double)paginacion.getRegistrosPorPagina())).intValue());
		
		for(int pag = 1; pag <= paginacion.getTotalPaginas(); pag++) {
			paginacion.setNumeroPagina(pag);
			List<PosicionDTO> posiciones = consultaPosicionService.consultarPosicionesYEmisionesVigentes(criterio, paginacion, new Long[] { DaliConstants.ID_MERCADO_PAPEL_BANCARIO,
					DaliConstants.ID_MERCADO_PAPEL_GUBER });
			
			System.out.println("Pagina: " + pag);
			System.out.println("Emisiones: ---------------------------------- ");
			
			for(PosicionDTO posicion : posiciones) {
				System.out.println("Elemento: " +  posicion.getEmision().getId());
			}
			
			System.out.println("Fin Emisiones ----------------------------------");
			assertTrue(posiciones != null && posiciones.size() > 0);
		}
		
	}

	/**
	 * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	 */
	@Override
	protected String[] getConfigLocations() {

		return new String[] { "/com/indeval/portaldali/conf/applicationContext-test.xml"};
	}
}
