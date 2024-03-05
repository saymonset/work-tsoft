/**
 * 
 */
package com.indeval.portaldali.middleware.services.tesoreria.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosService;

/**
 * @author javiles
 *
 */
public class ListaTiposEjercicioTest extends BaseTestCase {

    public void testGetListaTiposEjercicio() {
	/** Representa el detalle del usuario logueado */
	
	LiquidacionDecretosService servicio = (LiquidacionDecretosService)getBean("decretosEjercicioDerechos");
	
	
	    List lista = servicio.getListaTiposEjercicio();
	    assertNotNull(lista);
	    assertTrue(!lista.isEmpty());
	    for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
//		Object object = (Object) iterator.next();
		System.out.println("LISTA ["+iterator.next().getClass()+"]");
	    }
	
	
    }
    
}
    
