/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.ingresos;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.ingresos.IngresosDao;
import com.indeval.portaldali.persistence.model.ConsultaIngresos;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.LogConsultaIngresos;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestIngresos_2 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestIngresos_2.class);

    
    private IngresosDao ingresosDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        ingresosDao = (IngresosDao) getBean("ingresosDao");
    }
	
	/**
	 * TestCase para probar exitosamente esInhabil()
	 * @throws Exception
	 */
	public void testGetInstitucion() throws Exception {
//		Institucion institucion = ingresosDao.getInstitucionPorNombreCorto("CBACCVAL");
//		
//		if( institucion != null ) {
//			log.info("Sistema: [" + ToStringBuilder.reflectionToString(institucion,ToStringStyle.MULTI_LINE_STYLE) + "]");
//		} else {
//			log.error("No se encontro el sistema");
//		}
		LogConsultaIngresos con = new LogConsultaIngresos();
		ConsultaIngresos consultaIngresos = new ConsultaIngresos();
		consultaIngresos.setIdConsulta(1l);
		con.setConsultaIngresos(consultaIngresos);
		con.setFechaHoraRegistro(new Date());
		con.setIdTipoConsulta(1);
		Institucion institucion = new Institucion();
		institucion.setIdInstitucion(BigInteger.ONE);
		con.setInstitucion(institucion);
		con.setNumeroRegistros(BigInteger.ZERO);
		con.setTicket("00000");
		ingresosDao.save(con);
	}

}
