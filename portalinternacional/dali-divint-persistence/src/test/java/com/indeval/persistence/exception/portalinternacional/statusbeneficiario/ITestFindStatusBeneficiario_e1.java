package com.indeval.persistence.exception.portalinternacional.statusbeneficiario;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.persistence.dao.StatusBeneficiarioDao;

/**
 * 
 * @author Oscar Garcia Granados
 *
 */
public class ITestFindStatusBeneficiario_e1 extends BaseDaoTestCase{
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindStatusBeneficiario_e1.class);
	
	/**
	  * Dao que se va a probar
	 */
	 private StatusBeneficiarioDao statusBeneficiarioDao;
	
	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		statusBeneficiarioDao = (StatusBeneficiarioDao) getBean("statusBeneficiarioDao");
	}
	
	/**
	 * 
	 *
	 */
	public void testFindStatusBeneficiario()
	{
	  log.info("Entrando a testFindStatusBeneficiario()");
	  List<StatusBeneficiario> listStatusBeneficiarios=statusBeneficiarioDao.findStatusBeneficiario();
	  StatusBeneficiario[] statusBeneficiarios=
		  listStatusBeneficiarios.toArray(new StatusBeneficiario[listStatusBeneficiarios.size()]);
	  for(int i=0;i<listStatusBeneficiarios.size();i++)
	  {
		   StatusBeneficiario element = statusBeneficiarios[i];
		   assertNotNull(element);
	       log.debug("StatusBeneficiario [" + ReflectionToStringBuilder.toString(element) + "]");
	  }
	  
	}

}
