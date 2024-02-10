package com.indeval.persistence.exito.portalinternacional.historicobenefvencido;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;
import com.indeval.portalinternacional.persistence.dao.HistoricoBeneficiarioDao;

public class ITestFindByIdBeneficiario_1 extends BaseDaoTestCase {

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindByIdBeneficiario_1.class);
	/**
	 * Dao que se va a probar
	 */
	HistoricoBeneficiarioDao historicoBeneficiarioDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		historicoBeneficiarioDao = (HistoricoBeneficiarioDao) getBean("historicoBeneficiarioDao");
	}

	public void testFindByIdBeneficiario() {
		log.info("Entrando a testFindByIdBeneficiario()");
		assertNotNull("Dao nulo", historicoBeneficiarioDao);
		ConsultaHistoricoBeneficiariosParam param = new ConsultaHistoricoBeneficiariosParam();
		//param.setIdBeneficiario(6l);
        param.setNombreRazonSocial("nava");
		PaginaVO paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(0);
		paginaVO = historicoBeneficiarioDao.findHistoricoBeneficiario(param, paginaVO);

		System.out.println("Numero de regsitros: [" + paginaVO.getTotalRegistros() + "]");
		System.out.println("Numero de regsitros de la pagina: [" + paginaVO.getRegistros().size() + "]");
		
		for(HistoricoBeneficiario h : (List<HistoricoBeneficiario>)paginaVO.getRegistros()) {
			System.out.println("-------------------------------");
			for (Institucion i : h.getBeneficiario().getInstitucion()) {
				System.out.println("instituciones: [" + i.getNombreCorto() + "]");
			}
			System.out.println("Numero de instituciones: [" + h.getBeneficiario().getInstitucion().size() + "]");
		}

	}
}
