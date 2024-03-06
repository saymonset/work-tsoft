/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.rete;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.persistence.dao.admoncuentas.CuentasRetiroEfectivoDao;
import com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO;
import com.indeval.portaldali.persistence.model.MensajePortal;

public class ITestAdministracionCuentas extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestAdministracionCuentas.class);

    private CuentasRetiroEfectivoDao cuentasRetiroEfectivoDao; 	
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        cuentasRetiroEfectivoDao = (CuentasRetiroEfectivoDao) getBean("cuentasRetiroEfectivoDao");
    }
    
    public void testGetCuentas() {
    	
    	CriterioCuentaEfectivoDTO criterioCuentaEfectivoDTO = new CriterioCuentaEfectivoDTO();
    	   	 
    	    	
    	 Integer num =cuentasRetiroEfectivoDao.obtenerProyeccionCuentas(criterioCuentaEfectivoDTO, true, false); 
    	
      	
    }
    
}
