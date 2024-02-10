package com.indeval.persistence.exception.portalinternacional.capitales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.persistence.dao.capitales.ConsultaHistoricoCapitalesDao;

/**
 * 
 * @author Fernando Pineda
 * 
 */
public class ITestConsultaHistoricaCapitalesDao extends BaseDaoTestCase {

    /**
     * Dao que se va a probar
     */
    private ConsultaHistoricoCapitalesDao consultaHistoricoCapitalesDao;
    private static final Logger log = LoggerFactory.getLogger(ConsultaHistoricoCapitalesDao.class);

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        this.consultaHistoricoCapitalesDao =
                (ConsultaHistoricoCapitalesDao) this.getBean("consultaHistoricoCapitalesDao");
    }

    public void testConsultaHistoricoCapitales() throws Exception {
        ParamConsultaDetalleEjerDerCapTO params = new ParamConsultaDetalleEjerDerCapTO();
        params.setIdDerechoCapital(new Long(33513));
        params.setIsin("US4642872752");
        PaginaVO pagina = new PaginaVO();
        pagina =
                this.consultaHistoricoCapitalesDao
                        .consultaHistoricoCapitales(pagina, params, false);
        System.out.println("==============================pagina:" + pagina.getTotalRegistros());
        assertNotNull(pagina);
    }
}
