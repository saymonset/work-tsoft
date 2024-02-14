package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.statements;


import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.StatementsDivintService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;

public class ITestConsultaStatement_1 extends BaseITestService{
	
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
    
    public void testConsultaStatement() throws Exception {
    	log.debug("Entrando a ITestConsultaStatement_1.testConsultaStatement()");
    	assertNotNull("Servicio nulo", statementsDivintService);

		int registrosPorPagina = 200;
		// Estado de la paginacion
		PaginaVO paginaVO = new PaginaVO();
		paginaVO.setRegistrosXPag(registrosPorPagina);

		// Parametros de consulta
		ConsultaStatementsParam param = new ConsultaStatementsParam();
		param.setNombre("bolBRUGGE");

		paginaVO = statementsDivintService.consultaStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina);
		paginaVO = statementsDivintService.consultaStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina*2);
		paginaVO = statementsDivintService.consultaStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina*3);
		paginaVO = statementsDivintService.consultaStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

		paginaVO.setRegistros(null);
		paginaVO.setOffset(registrosPorPagina*4);
		paginaVO = statementsDivintService.consultaStatements(param, paginaVO);
		imprimeInfoPagina(paginaVO);

	}

	private void imprimeInfoPagina(PaginaVO paginaVO) {
		assertNotNull("Pagina nula", paginaVO);
		log.info("-----INFORMACION PAGINA");
		log.info("Total de registros: [" + paginaVO.getTotalRegistros() + "]");
		log.info("Registros por pagina: [" + paginaVO.getRegistrosXPag() + "]");
		log.info("Offset: [" + paginaVO.getOffset() + "]");
		assertNotNull("Lista nula", paginaVO.getRegistros());
		assertFalse("Lista vacia", paginaVO.getRegistros().isEmpty());
		log.info("Registros encontrados: [" + paginaVO.getRegistros().size() + "]");
	}

}
