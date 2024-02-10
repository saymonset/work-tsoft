package com.indeval.persistence.exception.portalinternacional.beneficiario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

/**
 *
 * @author Oscar Garcia Granados
 *
 */
public class ITestFindBeneficiario_e1 extends BaseDaoTestCase{

	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindBeneficiario_e1.class);

	/**
	 * Dao que se va a probar
	 */
	private BeneficiarioDao beneficiarioDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		beneficiarioDao = (BeneficiarioDao) getBean("beneficiarioDao");
	}

	/**
	 * Metodo para probar
	 */
	public void testFindBeneficiario_e1() {

		log.info("Entrando a ITestFindBeneficiario_e1.testFindBeneficiario_e1()");

		assertNotNull(beneficiarioDao);

		/* Objeto de prueba */
		Beneficiario beneficiario = new Beneficiario();

		/* Prueba de consulta */
		try {
			beneficiarioDao.findBeneficiarios(null,new PaginaVO(),false);
			log.debug("Error: Revisar la validacion de CatBic en el Beneficiario");
			assertTrue(false);
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		}

	}

}
