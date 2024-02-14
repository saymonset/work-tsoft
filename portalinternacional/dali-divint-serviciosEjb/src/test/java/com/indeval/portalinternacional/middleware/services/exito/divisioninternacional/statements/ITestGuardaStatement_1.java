package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.statements;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.StatementsDivintService;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class ITestGuardaStatement_1 extends BaseITestService{
	
	 /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private StatementsDivintService statementsDivintService;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
	@Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (statementsDivintService == null) {
        	statementsDivintService = (StatementsDivintService) applicationContext.getBean("statementsDivintServiceEJB");
        }
    }
    
    public void testGuardaStatement() throws Exception {
    	log.debug("Entrando a ITestGuardaStatement_1.testGuardaStatement()");
    	assertNotNull("Servicio nulo", statementsDivintService);

		StatementDivintVO vo = new StatementDivintVO();
		vo.setIdFolio("01001");
		vo.setTv("1I");
		vo.setEmisora("LQD");
		vo.setSerie("*");
		vo.setIsin("US4642872422");
		vo.setFechaPago(sdf.parse("07-10-2010 15:52:52.999"));
		vo.setFechaRegistro(sdf.parse("05-10-2009 12:45:57.999"));
		vo.setAdp("13470021");
		vo.setNombre("CASA DE BOLSA BANORTE, S.A. DE C.V.");
		vo.setDireccion("PERIFERICO SUR 4355 COL. JARDÍNES EN LA MONTAÑA . ,14210,DEL. TLALPAN,MEXICO D.F.");
		vo.setTaxPayerNumber(null);
		vo.setFormato("W8BEN");
		vo.setStatusOwner("Foreign Corporation");
		vo.setTipoBeneficiario("Persona Moral extranjera a EUA");
		vo.setPais("MEXICO");
		vo.setPorcentajeRetencion(new BigDecimal("10.0"));
		vo.setNumeroTitulos(150l);
		vo.setRfc("CBB940426P9A");
		vo.setProporcion(new BigDecimal("0.433672"));
		vo.setDividendo(new BigDecimal("65.05"));
		vo.setImpuesto(new BigDecimal("6.51"));
		vo.setDividendoNeto(new BigDecimal("58.55"));
		vo.setArchivoOrigen("071010 Statement LQD US4642872422.xls");

		Long id = statementsDivintService.guardaStatement(vo);

		log.info("Valor de id regresado;: [" + id + "]");
    		
    }

}
