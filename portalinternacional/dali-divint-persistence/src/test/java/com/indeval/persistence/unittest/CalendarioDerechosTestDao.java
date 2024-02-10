package com.indeval.persistence.unittest;

import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.persistence.dao.CalendarioEmisionesDeudaExtDao;

public class CalendarioDerechosTestDao extends BaseDaoTestCase {
	public void testCalendarioControl() {
		CalendarioEmisionesDeudaExtDao dao =	(CalendarioEmisionesDeudaExtDao)getBean("calendarioEmisionesDeudaExtDao");
		CalendarioDerechos cal= (CalendarioDerechos)dao.getByPk(CalendarioDerechos.class, 2903l);
		Control ctrl = null; //new Control();
		ctrl = (Control)dao.getByPk(Control.class, 1l);
		
		assertNotNull(ctrl);
		//System.out.println(ctrl.getDescripcion()+" "+ctrl.getEstado());
		System.out.println(cal.getControl().getDescripcion()+" "+cal.getControl().getEstado());
		assertNotNull(cal);
		assertNotNull(cal.getControl());
	}
}
